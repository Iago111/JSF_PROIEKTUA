package bean.eredua;

public class erabiltzaileEgoera {
	public String mota;
	public int balioa;
	public erabiltzaileEgoera(String pMota, int pBalioa) {
		this.mota=pMota;
		this.balioa=pBalioa;
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
	@Override
	public String toString() {
		return mota;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(balioa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		erabiltzaileEgoera other = (erabiltzaileEgoera) obj;
		return balioa == other.balioa;
	}
}
