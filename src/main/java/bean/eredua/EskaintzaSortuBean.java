package bean.eredua;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named("eskaintzaSortu")
@SessionScoped
public class EskaintzaSortuBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String titulua;
	private String description;
	private erabiltzaileEgoera status;
	private List<erabiltzaileEgoera> egoerak = new ArrayList<erabiltzaileEgoera>();
	private float price;
	private Date date;
	private String sellerEmail;
	private File file;
	private Part uploadedFile;

	public EskaintzaSortuBean() {
		if (egoerak.isEmpty()) {
			egoerak.add(new erabiltzaileEgoera("OSO ONA", 1));
			egoerak.add(new erabiltzaileEgoera("ONA", 2));
			egoerak.add(new erabiltzaileEgoera("NAHIKOA", 3));
			egoerak.add(new erabiltzaileEgoera("TXARRA", 4));
			egoerak.add(new erabiltzaileEgoera("OSO TXARRA", 5));
		}
	}

	public String getTitulua() {
		return titulua;
	}

	public void setTitulua(String titulua) {
		this.titulua = titulua;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<erabiltzaileEgoera> getEgoerak() {
		return egoerak;
	}

	public void setEgoerak(List<erabiltzaileEgoera> egoerak) {
		this.egoerak = egoerak;
	}

	public void setStatus(erabiltzaileEgoera status) {
		this.status = status;
	}

	public erabiltzaileEgoera getStatus() {
		return status;
	}

	public void balidatuData(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}
		Date aukeratutakoData = (Date) value;
		Date gaur = new Date();

		if (!aukeratutakoData.after(gaur)) {
			throw new ValidatorException(
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datak gaur baino beranduagokoa izan behar du.", null)
			);
		}
	}

	public String itxi() {
		return "OK";
	}

	public String eskaintzaIgo() {
		try {
			if (uploadedFile != null && uploadedFile.getSize() > 0) {
				file = new File(System.getProperty("java.io.tmpdir"), uploadedFile.getSubmittedFileName());
				try (InputStream input = uploadedFile.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			}

			BLFacade facade = FacadeBean.getBusinessLogic();
			facade.createSale(titulua, description, status.getBalioa(), price, date, sellerEmail, file);

			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Eskaintza ondo sortu da!", null));
			return null;
		} catch (MustBeLaterThanTodayException e) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: Datak gaur baino beranduagokoa izan behar du.", null));
		} catch (ParamNullException e) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: Eremu guztiak bete behar dira.", null));
		} catch (SaleAlreadyExistException e) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: Eskaintza hori badago lehendik.", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore ezezaguna: " + e.getMessage(), null));
		}
		return null;
	}
}