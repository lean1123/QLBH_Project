package entity;

import java.time.LocalDate;

public class DonHang {
	private String maDonHang;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private LocalDate ngayDat;

	public DonHang() {

	}
	public DonHang(String maDonHang) {
		super();
		this.maDonHang=maDonHang;
	}

	public DonHang(String maDonHang, NhanVien nhanVien, KhachHang khachHang, LocalDate ngayDat) {
		super();
		this.maDonHang = maDonHang;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.ngayDat = ngayDat;
	}


	public void setMaDonHang(String maDonHang) {
		this.maDonHang = maDonHang;
	}
	public String getMaDonHang() {
		return maDonHang;
	}
	public void setMaDDonHang(String maDonHang) {
		this.maDonHang = maDonHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public LocalDate getNgayDat() {
		return ngayDat;
	}
	public void setNgayDat(LocalDate ngayDat) {
		this.ngayDat = ngayDat;
	}


}
