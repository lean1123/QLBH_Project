package entity;

import java.util.Objects;

public class ChiTietDonTraHang {
	private DonTraHang donTraHang;
	private SanPham sanPham;
	private int soLuong;
	private int soLuongNhapKho;
	private boolean tinhTrangNhap;

	public DonTraHang getDonTraHang() {
		return donTraHang;
	}

	public void setDonTraHang(DonTraHang donTraHang) {
		this.donTraHang = donTraHang;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ChiTietDonTraHang(DonTraHang donTraHang, SanPham sanPham, int soLuong, int soLuongNhapKho,
			boolean tinhTrangNhap) {
		super();
		this.donTraHang = donTraHang;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.soLuongNhapKho = soLuongNhapKho;
		this.tinhTrangNhap = tinhTrangNhap;
	}

	public int getSoLuongNhapKho() {
		return soLuongNhapKho;
	}

	public void setSoLuongNhapKho(int soLuongNhapKho) {
		this.soLuongNhapKho = soLuongNhapKho;
	}

	public boolean isTinhTrangNhap() {
		return tinhTrangNhap;
	}

	public void setTinhTrangNhap(boolean tinhTrangNhap) {
		this.tinhTrangNhap = tinhTrangNhap;
	}

	@Override
	public int hashCode() {
		return Objects.hash(donTraHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietDonTraHang other = (ChiTietDonTraHang) obj;
		return Objects.equals(donTraHang, other.donTraHang);
	}

}
