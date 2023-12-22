package entity;

import java.io.Serializable;

public class NhaCungCap implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String maNhaCungCap;
	private String tenNhaCungCap;
	private String diaChi;
	private String email;
	private String soDienThoai;

	public String getMaNhaCungCap() {
		return maNhaCungCap;
	}

	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}

	public String getTenNhaCungCap() {
		return tenNhaCungCap;
	}

	public void setTenNhaCungCap(String tenNhaCungCap) {
		this.tenNhaCungCap = tenNhaCungCap;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String diaChi, String email, String soDienThoai) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.tenNhaCungCap = tenNhaCungCap;
		this.diaChi = diaChi;
		this.email = email;
		this.soDienThoai = soDienThoai;
	}

	public NhaCungCap(String tenNhaCungCap, String diaChi, String email, String soDienThoai) {
		super();
		this.tenNhaCungCap = tenNhaCungCap;
		this.diaChi = diaChi;
		this.email = email;
		this.soDienThoai = soDienThoai;
	}

	public NhaCungCap(String maNhaCungCap) {
		super();
		this.maNhaCungCap = maNhaCungCap;
	}

	public NhaCungCap(String maNhaCungCap, String tenNhaCungCap) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.tenNhaCungCap=tenNhaCungCap;
	}
}
