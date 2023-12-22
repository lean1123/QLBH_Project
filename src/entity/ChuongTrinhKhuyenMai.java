package entity;

import java.util.Date;

public class ChuongTrinhKhuyenMai {
	private String maKhuyenMai;
	private String tenKhuyenMai;
	private String moTa;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	private int soLuong;
	private double giaGiam;
	private String maCaptcha;

	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}

	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaGiam() {
		return giaGiam;
	}

	public void setGiaGiam(double giaGiam) {
		this.giaGiam = giaGiam;
	}

	public String getMaCaptcha() {
		return maCaptcha;
	}

	public void setMaCaptcha(String maCaptcha) {
		this.maCaptcha = maCaptcha;
	}

	public ChuongTrinhKhuyenMai(String maKhuyenMai, String tenKhuyenMai, String moTa, Date ngayBatDau, Date ngayKetThuc, int soLuong,
			double giaGiam, String maCaptcha) {
		super();
		this.maKhuyenMai = maKhuyenMai;
		this.tenKhuyenMai = tenKhuyenMai;
		this.moTa = moTa;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.soLuong = soLuong;
		this.giaGiam = giaGiam;
		this.maCaptcha = maCaptcha;
	}

	public ChuongTrinhKhuyenMai(String tenKhuyenMai, String moTa, Date ngayBatDau, Date ngayKetThuc, int soLuong,
			double giaGiam, String maCaptcha) {
		super();
		this.tenKhuyenMai = tenKhuyenMai;
		this.moTa = moTa;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.soLuong = soLuong;
		this.giaGiam = giaGiam;
		this.maCaptcha = maCaptcha;
	}

}
