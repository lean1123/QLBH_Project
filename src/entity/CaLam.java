package entity;

import java.sql.Time;

public class CaLam {
	private String maCaLam;
	private Time gioBatDau;
	private Time gioKetThuc;

	public CaLam() {
		super();
	}
	
	public CaLam( String maCaLam) {
		super();
		this.maCaLam=maCaLam;
	}
	
	public CaLam(String maCaLam, Time gioBatDau, Time gioKetThuc) {
		super();
		this.maCaLam = maCaLam;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
	}
	public String getMaCaLam() {
		return maCaLam;
	}
	public void setMaCaLam(String maCaLam) {
		this.maCaLam = maCaLam;
	}
	public Time getGioBatDau() {
		return gioBatDau;
	}
	public void setGioBatDau(Time gioBatDau) {
		this.gioBatDau = gioBatDau;
	}
	public Time getGioKetThuc() {
		return gioKetThuc;
	}
	public void setGioKetThuc(Time gioKetThuc) {
		this.gioKetThuc = gioKetThuc;
	}

}
