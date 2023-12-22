package entity;

import java.time.LocalDate;
import java.util.Objects;

public class DonTraHang {
	private String maDonTraHang;
	private HoaDon hoaDon;
	private NhanVien nhanVienThucHien;
	private LocalDate ngayTraHang;
	private String lyDo;

	public String getMaDonTraHang() {
		return maDonTraHang;
	}

	public void setMaDonTraHang(String maDonTraHang) {
		this.maDonTraHang = maDonTraHang;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public LocalDate getNgayTraHang() {
		return ngayTraHang;
	}

	public void setNgayTraHang(LocalDate ngayTraHang) {
		this.ngayTraHang = ngayTraHang;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public DonTraHang(HoaDon hoaDon, NhanVien nhanVienThucHien, LocalDate ngayTraHang, String lyDo) {
		super();
		this.hoaDon = hoaDon;
		this.nhanVienThucHien = nhanVienThucHien;
		this.ngayTraHang = ngayTraHang;
		this.lyDo = lyDo;
	}

	public DonTraHang(String maDonTraHang, HoaDon hoaDon, NhanVien nhanVienThucHien, LocalDate ngayTraHang,
			String lyDo) {
		super();
		this.maDonTraHang = maDonTraHang;
		this.hoaDon = hoaDon;
		this.nhanVienThucHien = nhanVienThucHien;
		this.ngayTraHang = ngayTraHang;
		this.lyDo = lyDo;
	}

	public NhanVien getNhanVienThucHien() {
		return nhanVienThucHien;
	}

	public void setNhanVienThucHien(NhanVien nhanVienThucHien) {
		this.nhanVienThucHien = nhanVienThucHien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maDonTraHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonTraHang other = (DonTraHang) obj;
		return Objects.equals(maDonTraHang, other.maDonTraHang);
	}

}
