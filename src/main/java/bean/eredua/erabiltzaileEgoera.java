package bean.eredua;

import java.io.Serializable;

public class erabiltzaileEgoera implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mota;
	private int balioa;

	public erabiltzaileEgoera() {
	}

	public erabiltzaileEgoera(String mota, int balioa) {
		this.mota = mota;
		this.balioa = balioa;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public int getBalioa() {
		return balioa;
	}

	public void setBalioa(int balioa) {
		this.balioa = balioa;
	}
}