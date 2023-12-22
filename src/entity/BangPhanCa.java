package entity;

import java.time.LocalDate;
import java.util.Objects;

public class BangPhanCa {
	private String maBangPhanCa;
	private NhanVien nhanVien;
	private CaLam caLam;
	private LocalDate ngayBatDau;
	private LocalDate ngayKetThuc;
	private String kieuPhanCa;

	public String getMaBangPhanCa() {
		return maBangPhanCa;
	}

	public void setMaBangPhanCa(String maBangPhanCa) {
		this.maBangPhanCa = maBangPhanCa;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public CaLam getCaLam() {
		return caLam;
	}

	public void setCaLam(CaLam caLam) {
		this.caLam = caLam;
	}

	public LocalDate getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(LocalDate ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public LocalDate getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(LocalDate ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getKieuPhanCa() {
		return kieuPhanCa;
	}

	public void setKieuPhanCa(String kieuPhanCa) {
		this.kieuPhanCa = kieuPhanCa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maBangPhanCa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangPhanCa other = (BangPhanCa) obj;
		return Objects.equals(maBangPhanCa, other.maBangPhanCa);
	}

	public BangPhanCa(String maBangPhanCa, NhanVien nhanVien, CaLam caLam, LocalDate ngayBatDau, LocalDate ngayKetThuc,
			String kieuPhanCa) {
		super();
		this.maBangPhanCa = maBangPhanCa;
		this.nhanVien = nhanVien;
		this.caLam = caLam;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.kieuPhanCa = kieuPhanCa;
	}

	public BangPhanCa(NhanVien nhanVien, CaLam caLam, LocalDate ngayBatDau, LocalDate ngayKetThuc, String kieuPhanCa) {
		super();
		this.nhanVien = nhanVien;
		this.caLam = caLam;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.kieuPhanCa = kieuPhanCa;
	}

	public BangPhanCa(LocalDate ngayBatDau, LocalDate ngayKetthuc, String kieuPhanCa, CaLam caLam) {
		super();
		this.ngayBatDau=ngayBatDau;
		this.ngayKetThuc=ngayKetthuc;
		this.kieuPhanCa=kieuPhanCa;
		this.caLam=caLam;
	}
	
	public BangPhanCa() {
		super();
	}

}
