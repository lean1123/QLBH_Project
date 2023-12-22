package entity;

import java.io.Serializable;


public class SanPham implements Serializable{
	
	private String maSanPham ;
	private String tenSanPham ;
	private float giaNhap;
	private float giaBan ;
	private int soLuongTon ;
	private int soLuongBan;
	private String urlAvt;
	private String danhMuc;
	private String mauSac;
	private String chatLieu;
	private String kichCo;
	private NhaCungCap nhaCungCap;
	
	
	public SanPham(String maSanPham) {
		super();
		this.maSanPham = maSanPham;
	}

	public SanPham(String maSanPham, String tenSanPham, float giaNhap, float giaBan, int soLuongTon, int soLuongBan, String urlAvt,
			String tenDM, String tenMS, String tenCL, String tenKC, NhaCungCap nhaCungCap) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.giaNhap=giaNhap;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.soLuongBan = soLuongBan;
		this.urlAvt = urlAvt;
		this.danhMuc = tenDM;
		this.mauSac = tenMS;
		this.chatLieu = tenCL;
		this.kichCo = tenKC;
		this.nhaCungCap = nhaCungCap;
	}

	
	
	public SanPham(String maSanPham, String tenSanPham, float giaNhap, float giaBan, int soLuongTon, int soLuongBan,
			String danhMuc, String mauSac, String chatLieu, String kichCo, NhaCungCap nhaCungCap) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.giaNhap = giaNhap;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.soLuongBan = soLuongBan;
		this.danhMuc = danhMuc;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
		this.kichCo = kichCo;
		this.nhaCungCap = nhaCungCap;
	}

	public SanPham(String maSanPham, String tenSanPham, float giaBan, int soLuongTon, int soLuongBan, String danhMuc,
			String mauSac, String chatLieu, String kichCo, NhaCungCap nhaCungCap) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.soLuongBan = soLuongBan;
		this.danhMuc = danhMuc;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
		this.kichCo = kichCo;
		this.nhaCungCap = nhaCungCap;
	}
	
	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	
	public float getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(float giaNhap) {
		this.giaNhap = giaNhap;
	}

	public float getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(float giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public int getSoLuongBan() {
		return soLuongBan;
	}

	public void setSoLuongBan(int soLuongBan) {
		this.soLuongBan = soLuongBan;
	}

	public String getUrlAvt() {
		return urlAvt;
	}

	public void setUrlAvt(String urlAvt) {
		this.urlAvt = urlAvt;
	}

	public String getDanhMuc() {
		return danhMuc;
	}

	public void setDanhMuc(String danhMuc) {
		this.danhMuc = danhMuc;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}

	public String getKichCo() {
		return kichCo;
	}

	public void setKichCo(String kichCo) {
		this.kichCo = kichCo;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}
	
	
	
}
