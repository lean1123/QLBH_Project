package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DonTraHang;
import entity.HoaDon;
import entity.NhanVien;
import utils.Function;

public class DonTraHang_DAO {
	private List<DonTraHang> dsDonTraHang;

	public DonTraHang_DAO() {
		super();
		dsDonTraHang = new ArrayList<DonTraHang>();
	}

	public List<DonTraHang> getDanhSachDonTraHang() {

		try {
			String sql = "SELECT * FROM DonTraHang";
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			NhanVien_DAO dsNV = new NhanVien_DAO();
			HoaDon_DAO dsHD = new HoaDon_DAO();
			while (rs.next()) {
				String maDonTraHang = rs.getString("maDonTraHang");
				String maNhanVien = rs.getString("maNhanVien");
				NhanVien nv = dsNV.getNhanVienTheoMa(maNhanVien);
				String maHoaDon = rs.getString("maHoaDon");
				HoaDon hoaDon = dsHD.getHoaDonTheoMa(maHoaDon);
				String lyDo = rs.getString("lyDoTraHang");
				LocalDate ngayTraHang = Function.convertToLocalDateViaMilisecond(rs.getDate("ngayTraHang"));

				dsDonTraHang.add(new DonTraHang(maDonTraHang, hoaDon, nv, ngayTraHang, lyDo));

			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsDonTraHang;
	}

	public boolean themDonTraHang(DonTraHang donTraHang) {
		int n = 0;
		try {
			String sql = "insert into DonTraHang (maHoaDon, maNhanVien, lyDoTraHang, ngayTraHang) values(?,?,?,?)";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, donTraHang.getHoaDon().getMaHoaDon());
			stmt.setString(2, donTraHang.getNhanVienThucHien().getMaNhanVien());
			stmt.setString(3, donTraHang.getLyDo());
			stmt.setDate(4, Date.valueOf(donTraHang.getNgayTraHang()));
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n > 0;
	}

	public DonTraHang getDonTraHangVuaCapNhat() {
		DonTraHang donTraHang = null;

		try {
			String sql = "SELECT TOP 1 * FROM DonTraHang ORDER BY time_stamp DESC";
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			NhanVien_DAO dsNV = new NhanVien_DAO();
			HoaDon_DAO dsHD = new HoaDon_DAO();
			if (rs.next()) {
				String maDonTraHang = rs.getString("maDonTraHang");
				String maNhanVien = rs.getString("maNhanVien");
				NhanVien nv = dsNV.getNhanVienTheoMa(maNhanVien);
				String maHoaDon = rs.getString("maHoaDon");
				HoaDon hoaDon = dsHD.getHoaDonTheoMa(maHoaDon);
				String lyDo = rs.getString("lyDoTraHang");
				LocalDate ngayTraHang = Function.convertToLocalDateViaMilisecond(rs.getDate("ngayTraHang"));
				donTraHang = new DonTraHang(maDonTraHang, hoaDon, nv, ngayTraHang, lyDo);
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return donTraHang;
	}

	public DonTraHang getDonTraHangTheoMa(String maDonTraHang) {
		DonTraHang donTraHang = null;

		try {
			String sql = "SELECT * FROM DonTraHang WHERE maDonTraHang = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maDonTraHang);
			ResultSet rs = stmt.executeQuery();
			NhanVien_DAO dsNV = new NhanVien_DAO();
			HoaDon_DAO dsHD = new HoaDon_DAO();
			if (rs.next()) {
				String maDonTraHangTimDuoc = rs.getString("maDonTraHang");
				String maNhanVien = rs.getString("maNhanVien");
				NhanVien nv = dsNV.getNhanVienTheoMa(maNhanVien);
				String maHoaDon = rs.getString("maHoaDon");
				HoaDon hoaDon = dsHD.getHoaDonTheoMa(maHoaDon);
				String lyDo = rs.getString("lyDoTraHang");
				LocalDate ngayTraHang = Function.convertToLocalDateViaMilisecond(rs.getDate("ngayTraHang"));
				donTraHang = new DonTraHang(maDonTraHangTimDuoc, hoaDon, nv, ngayTraHang, lyDo);
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return donTraHang;
	}

	public List<DonTraHang> timKiemDonTraHang(String maDonTra, String maHoaDon, String soDienThoaiKH, String maNV,
			String lyDoTraHang, LocalDate ngayTra) {
		List<DonTraHang> dsTimKiem = new ArrayList<DonTraHang>();
		try {
			String sql = "select * from DonTraHang" + " inner join HoaDon on HoaDon.maHoaDon = DonTraHang.maHoaDon"
					+ " inner join KhachHang on KhachHang.maKhachHang = HoaDon.maKhachHang"
					+ " where maDonTraHang like ? and DonTraHang.maHoaDon like ? and DonTraHang.maNhanVien like ?"
					+ " and convert(date, ngayTraHang, 120) like ? and lyDoTraHang like ? and KhachHang.soDienThoai like ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + maDonTra + "%");
			stmt.setString(2, "%" + maHoaDon + "%");
			stmt.setString(6, "%" + soDienThoaiKH + "%");
			stmt.setString(3, "%" + maNV + "%");
			stmt.setString(5, "%" + lyDoTraHang + "%");
			String ngayTraStr = "";
			if (ngayTra != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ngayTraStr = sdf.format(Date.valueOf(ngayTra));
			} 
			stmt.setString(4, "%" + ngayTraStr + "%");
			ResultSet rs = stmt.executeQuery();
			NhanVien_DAO dsNV = new NhanVien_DAO();
			HoaDon_DAO dsHD = new HoaDon_DAO();
			while (rs.next()) {
				String maDonTraHang = rs.getString("maDonTraHang");
				String maNhanVien = rs.getString("maNhanVien");
				NhanVien nv = dsNV.getNhanVienTheoMa(maNhanVien);
				String maHoaDonTimDuoc = rs.getString("maHoaDon");
				HoaDon hoaDon = dsHD.getHoaDonTheoMa(maHoaDonTimDuoc);
				String lyDo = rs.getString("lyDoTraHang");
				LocalDate ngayTraHang = Function.convertToLocalDateViaMilisecond(rs.getDate("ngayTraHang"));

				dsTimKiem.add(new DonTraHang(maDonTraHang, hoaDon, nv, ngayTraHang, lyDo));

			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsTimKiem;
	}
}
