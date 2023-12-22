package entity;

import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String hoTen;
	private boolean gioiTinh;
	private String SoDienThoai;
	private String diaChi;

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(String SoDienThoai) {
		this.SoDienThoai = SoDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public KhachHang(String maKhachHang, String hoTen, boolean gioiTinh, String SoDienThoai, String diaChi) {
		super();
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.SoDienThoai = SoDienThoai;
		this.diaChi = diaChi;
	}

	public KhachHang(String hoTen, boolean gioiTinh, String SoDienThoai, String diaChi) {
		super();
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.SoDienThoai = SoDienThoai;
		this.diaChi = diaChi;
	}

	public KhachHang(String maKH) {
		this.maKhachHang = maKH;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang, SoDienThoai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang) && Objects.equals(SoDienThoai, other.SoDienThoai);
	}

}
