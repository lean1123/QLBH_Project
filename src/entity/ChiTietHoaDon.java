package entity;

import java.io.Serializable;
import java.util.Objects;

public class ChiTietHoaDon implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private HoaDon HoaDon;
	private SanPham SanPham;
	private double donGia;
	private int soLuong;

	public HoaDon getHoaDon() {
		return HoaDon;
	}

	public void setHoaDon(HoaDon HoaDon) {
		this.HoaDon = HoaDon;
	}

	public SanPham getSanPham() {
		return SanPham;
	}

	public void setSanPham(SanPham SanPham) {
		this.SanPham = SanPham;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ChiTietHoaDon(HoaDon HoaDon, SanPham SanPham, double donGia, int soLuong) {
		super();
		this.HoaDon = HoaDon;
		this.SanPham = SanPham;
		this.donGia = donGia;
		this.soLuong = soLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(HoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(HoaDon, other.HoaDon);
	}

}
