package entity;

public class NhanVien {
	private String maNhanVien;
	private String ten;
	private String chucVu;
	private Boolean gioiTinh;
	private String soDienThoai;
	private String diaChi;

	public NhanVien() {

	}
	public NhanVien(String maNhanVien, String ten,Boolean gioiTinh, String chucVu,  String soDienThoai, String diaChi) {

		this.maNhanVien = maNhanVien;
		this.ten = ten;
		this.chucVu = chucVu;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
	}
	public NhanVien(String maNV) {
		this.maNhanVien = maNV;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public Boolean getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}



}
