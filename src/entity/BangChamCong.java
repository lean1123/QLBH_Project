package entity;

import java.time.LocalDate;
import java.util.Objects;

public class BangChamCong {
	private BangPhanCa phanCa;
	private LocalDate thoiGianChamCong;
	private boolean trangThaiLamViec;
	private boolean trangThaiCoPhep;
	private String ghiChu;

	public BangPhanCa getPhanCa() {
		return phanCa;
	}

	public void setPhanCa(BangPhanCa phanCa) {
		this.phanCa = phanCa;
	}

	public LocalDate getThoiGianChamCong() {
		return thoiGianChamCong;
	}

	public void setThoiGianChamCong(LocalDate thoiGianChamCong) {
		this.thoiGianChamCong = thoiGianChamCong;
	}

	public boolean isTrangThaiLamViec() {
		return trangThaiLamViec;
	}

	public void setTrangThaiLamViec(boolean trangThaiLamViec) {
		this.trangThaiLamViec = trangThaiLamViec;
	}

	public boolean isTrangThaiCoPhep() {
		return trangThaiCoPhep;
	}

	public void setTrangThaiCoPhep(boolean trangThaiCoPhep) {
		this.trangThaiCoPhep = trangThaiCoPhep;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public BangChamCong(BangPhanCa phanCa, LocalDate thoiGianChamCong, boolean trangThaiLamViec,
			boolean trangThaiCoPhep, String ghiChu) {
		super();
		this.phanCa = phanCa;
		this.thoiGianChamCong = thoiGianChamCong;
		this.trangThaiLamViec = trangThaiLamViec;
		this.trangThaiCoPhep = trangThaiCoPhep;
		this.ghiChu = ghiChu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(phanCa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangChamCong other = (BangChamCong) obj;
		return Objects.equals(phanCa, other.phanCa);
	}

}
