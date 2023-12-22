package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhaCungCap;

public class NhaCungCap_DAO {
	private List<NhaCungCap> danhSachNhaCungCap;

	public NhaCungCap_DAO() {
		super();
		this.danhSachNhaCungCap = new ArrayList<>();
	}

	/**
	 * Lấy danh sách nhà cung cấp theo tên nhà cung cấp từ cơ sở dữ liệu.
	 *
	 * @param ten Tên nhà cung cấp cần tìm kiếm.
	 * @return Danh sách nhà cung cấp theo tên.
	 */

	public List<NhaCungCap> getListNhaCungCapTheoTenNhaCungCap(String ten) {
		List<NhaCungCap> dsNhaCungCap = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhaCungCap where tenNhaCungCap=?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, ten);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String maNhaCungCap = rs.getString(1);
				String tenNhaCungCap = rs.getString(2);
				String diaChi = rs.getString(3);
				String email = rs.getString(4);
				String sdt = rs.getString(5);
				NhaCungCap nhaCungCap = new NhaCungCap(maNhaCungCap, tenNhaCungCap, diaChi, email, sdt);
				dsNhaCungCap.add(nhaCungCap);
			}
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsNhaCungCap;
	}

	/**
	 * Lấy danh sách tất cả nhà cung cấp từ cơ sở dữ liệu.
	 *
	 * @return Danh sách tất cả nhà cung cấp.
	 */

	public List<NhaCungCap> getListNhaCungCap() {

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from NhaCungCap";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maNhaCungCap = rs.getString(1);
				String tenNhaCungCap = rs.getString(2);
				String DC = rs.getString(3);
				String email = rs.getString(4);
				String sdt = rs.getString(5);

				NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, DC, email, sdt);
				danhSachNhaCungCap.add(ncc);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return danhSachNhaCungCap;
	}

	/**
	 * Thêm một nhà cung cấp mới vào cơ sở dữ liệu.
	 *
	 * @param ncc Đối tượng NhaCungCap chứa thông tin nhà cung cấp mới.
	 * @return True nếu thêm thành công, ngược lại là False.
	 */

	public boolean themNhaCungCap(NhaCungCap ncc) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO NhaCungCap (tenNhaCungCap, diaChi, email, soDienThoai) VALUES (?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ncc.getTenNhaCungCap());
			stmt.setString(2, ncc.getDiaChi());
			stmt.setString(3, ncc.getEmail());
			stmt.setString(4, ncc.getSoDienThoai());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	/**
	 * Cập nhật thông tin của nhà cung cấp trong cơ sở dữ liệu.
	 *
	 * @param ncc Đối tượng NhaCungCap chứa thông tin cần cập nhật.
	 * @return True nếu cập nhật thành công, ngược lại là False.
	 */

	public boolean capNhatNhaCungCap(NhaCungCap ncc) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "update NhaCungCap set tenNhaCungCap = ?, diaChi = ?, email = ?, soDienThoai = ? where maNhaCungCap=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ncc.getTenNhaCungCap());
			stmt.setString(2, ncc.getDiaChi());
			stmt.setString(3, ncc.getEmail());
			stmt.setString(4, ncc.getSoDienThoai());
			stmt.setString(5, ncc.getMaNhaCungCap());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	/**
	 * Lấy thông tin của nhà cung cấp vừa được cập nhật gần đây nhất từ cơ sở dữ
	 * liệu.
	 *
	 * @return Đối tượng NhaCungCap chứa thông tin của nhà cung cấp vừa cập nhật,
	 *         hoặc null nếu không có thông tin.
	 */

	public NhaCungCap getNhaCungCapVuaCapNhat() {
		NhaCungCap ncc = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 * FROM NhaCungCap ORDER BY time_stamp DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maNhaCungCap = rs.getString(1);
				String tenNhaCungCap = rs.getString(2);
				String dc = rs.getString(3);
				String email = rs.getString(4);
				String sdt = rs.getString(5);
				ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, dc, email, sdt);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ncc;
	}

	/**
	 * Tìm kiếm nhà cung cấp dựa vào các tiêu chí mã, tên, địa chỉ, email, số điện
	 * thoại của nhà cung cấp.
	 *
	 * @param maNhaCungCap  Mã nhà cung cấp cần tìm.
	 * @param tenNhaCungCap Tên nhà cung cấp cần tìm.
	 * @param diaChi        Địa chỉ nhà cung cấp cần tìm.
	 * @param email         Email của nhà cung cấp.
	 * @param sdt           Số điện thoại của nhà cung cấp.
	 *
	 * @return List<NhaCungCap> nếu tồn tại nhiều nhà cung cấp cùng tiêu chí tìm
	 *         kiếm hoặc một NhaCungCap nếu ngược lại.
	 */

	public List<NhaCungCap> timKiemNhaCungCap(String maNhaCungCap, String tenNhaCungCap, String diaChi, String email,
			String sdt) {
		List<NhaCungCap> dsTK = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhaCungCap " + "WHERE maNhaCungCap LIKE ?" + "  AND tenNhaCungCap LIKE ?"
					+ "  AND diaChi LIKE ?" + "  AND email LIKE ?" + "  AND soDienThoai LIKE ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + maNhaCungCap + "%");
			stmt.setString(2, "%" + tenNhaCungCap + "%");
			stmt.setString(3, "%" + diaChi + "%");
			stmt.setString(4, "%" + email + "%");
			stmt.setString(5, "%" + sdt + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTimDuoc = rs.getString(1);
				String tenTimDuoc = rs.getString(2);
				String diaChiTimDuoc = rs.getString(3);
				String emailTimDuoc = rs.getString(4);
				String sdtTimDuoc = rs.getString(5);

				NhaCungCap ncc = new NhaCungCap(maTimDuoc, tenTimDuoc, diaChiTimDuoc, emailTimDuoc, sdtTimDuoc);
				dsTK.add(ncc);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsTK;
	}

	public NhaCungCap getListNhaCungCapTheoMa(String maNhaCungCap) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhaCungCap where maNhaCungCap=?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNhaCungCap);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				String maNCCTimDuoc = rs.getString(1);
				String tenNhaCungCap = rs.getString(2);
				String diaChi = rs.getString(3);
				String email = rs.getString(4);
				String sdt = rs.getString(5);
				NhaCungCap nhaCungCap = new NhaCungCap(maNCCTimDuoc, tenNhaCungCap, diaChi, email, sdt);
				return nhaCungCap;
			}
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
