package bean.eredua;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
@Named("eskaintzaSortu")
@SessionScoped
public class EskaintzaSortuBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String titulua;
	private String description;
	private erabiltzaileEgoera status;
	private static List<erabiltzaileEgoera> egoerak=new ArrayList<erabiltzaileEgoera>();
	private float price;
	private  Date date;
	private String sellerEmail;
	private File file;
	
	public EskaintzaSortuBean() {
		if(egoerak.isEmpty()) {
			egoerak.add(new erabiltzaileEgoera("OSO ONA",1));
			egoerak.add(new erabiltzaileEgoera("ONA",2));
			egoerak.add(new erabiltzaileEgoera("NAHIKOA",3));
			egoerak.add(new erabiltzaileEgoera("TXARRA",4));
			egoerak.add(new erabiltzaileEgoera("OSO TXARRA",5));
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
	
	public List<erabiltzaileEgoera> getEgoerak() {
		return egoerak;
	}

	public static void setEgoerak(List<erabiltzaileEgoera> egoerak) {
		EskaintzaSortuBean.egoerak = egoerak;
	}

	public void setStatus(erabiltzaileEgoera status) {
		this.status = status;
	}
	public erabiltzaileEgoera getStatus() {
		return status;
	}
	public String itxi() {
		return "OK";
	}
	public String eskaintzaIgo() {
	    try {
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
