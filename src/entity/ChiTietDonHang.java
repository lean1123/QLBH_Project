package entity;

public class ChiTietDonHang {
	private SanPham sanPham;
	private DonHang donHang;
	private float donGia;
	private int soLuong;

	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public DonHang getDonHang() {
		return donHang;
	}
	public void setDonHang(DonHang donHang) {
		this.donHang = donHang;
	}
	public float getDonGia() {
		return donGia;
	}
	public void setDonGia(float donGia) {
		this.donGia = donGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public ChiTietDonHang(SanPham sanPham, DonHang donHang, float donGia, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.donHang = donHang;
		this.donGia = donGia;
		this.soLuong = soLuong;
	}
	public ChiTietDonHang() {
		super();
	}

}
