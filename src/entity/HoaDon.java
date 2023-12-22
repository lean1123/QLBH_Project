package entity;

import java.util.Date;

public class HoaDon {
	private String maHoaDon;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private Date ngayLap;
	private ChuongTrinhKhuyenMai chuongTrinhKhuyenMai;

	public ChuongTrinhKhuyenMai getChuongTrinhKhuyenMai() {
		return chuongTrinhKhuyenMai;
	}

	public void setChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
		this.chuongTrinhKhuyenMai = chuongTrinhKhuyenMai;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
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

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public HoaDon(String maHoaDon, NhanVien nhanVien, KhachHang khachHang, Date ngayLap) {
		super();
		this.maHoaDon = maHoaDon;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.ngayLap = ngayLap;
	}

	public HoaDon(NhanVien nhanVien, KhachHang khachHang, Date ngayLap, ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.ngayLap = ngayLap;
	}

	public HoaDon(String maHoaDon, NhanVien nv, KhachHang kh, Date ngayLapHD, ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
		this.maHoaDon = maHoaDon;
		this.nhanVien = nv;
		this.khachHang = kh;
		this.ngayLap = ngayLapHD;
		this.chuongTrinhKhuyenMai = chuongTrinhKhuyenMai;
	}
}
