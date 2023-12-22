package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_DAO {
	/**
	 * Function getAllNhanVien được sử dụng để lây tất cả nhân viên
	 *
	 * @return trả về đối tượng List<NhanVien>
	 */
	public List<NhanVien> getAllNhanVien() {
		String sql = "Select * From NhanVien";
		// kết nối db
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			List<NhanVien> list = new ArrayList<>();
			while (result.next()) {
				NhanVien n = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				list.add(n);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**	
	 * Function getAllNhanVienChuaCoTaiKhoan được sử dụng để lấy tất cả nhân viên
	 * chưa có tài khoản ra
	 * 
	 * @return trả về đối tượng List<NhanVien>
	 */
	public List<NhanVien> getAllNhanVienChuaCoTaiKhoan() {
		String sql = "Select * From NhanVien LEFT JOIN TaiKhoan ON NhanVien.maNhanVien = TaiKhoan.maNhanVien WHERE taiKhoan IS NULL";
		// kết nối db
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			List<NhanVien> list = new ArrayList<>();
			while (result.next()) {
				NhanVien n = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				list.add(n);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Function timKiemNhanVien được sử dụng để tìm kiếm nhân viên
	 * 
	 * @param nv đối tượng nhân viên(NhanVien)
	 * @return trả về đối tượng List<NhanVien>
	 */
	public List<NhanVien> timKiemNhanVien(NhanVien nv) {
		String sql = "Select * From NhanVien where maNhanVien LIKE ? AND ten LIKE ? AND soDienThoai LIKE ? AND diaChi LIKE ? AND chucVu LIKE ? ";
		if (nv.getGioiTinh() != null) {
			sql += "AND gioiTinh = ?";
		}
		// kết nối db
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, "%" + nv.getMaNhanVien() + "%");
			statement.setString(2, "%" + nv.getTen() + "%");
			statement.setString(3, "%" + nv.getSoDienThoai() + "%");
			statement.setString(4, "%" + nv.getDiaChi() + "%");
			statement.setString(5, "%" + nv.getChucVu() + "%");
			if (nv.getGioiTinh() != null) {
				statement.setInt(6, nv.getGioiTinh() ? 1 : 0);
			}
			ResultSet result = statement.executeQuery();
			List<NhanVien> list = new ArrayList<>();
			while (result.next()) {
				NhanVien n = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? true : false, result.getString("chucVu"),
						result.getString("soDienThoai"), result.getString("diaChi"));
				list.add(n);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Function themNhanVien được sử dụng thêm nhân viên vào database
	 * 
	 * @param nv là đối tượng NhanVien
	 * @return trả về true khi thêm thành công hoặc ngược lại
	 */
	public boolean themNhanVien(NhanVien nv) {
		String sql = "INSERT INTO NhanVien(ten,gioiTinh,chucVu,soDienThoai,diaChi) Values(?,?,?,?,?)";
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, nv.getTen());
			statement.setInt(2, nv.getGioiTinh() ? 1 : 0);
			statement.setString(3, nv.getChucVu());
			statement.setString(4, nv.getSoDienThoai());
			statement.setString(5, nv.getDiaChi());
			int result = statement.executeUpdate();
			ConnectDB.getInstance().disconnect();
			return result > 0 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConnectDB.getInstance().disconnect();
		return false;
	}

	/**
	 * Function capNhatNhanVien được sử dụng để cập nhật nhân viên vào db
	 * 
	 * @param nv là đối tượng NhanVien cần sửa
	 * @return trả về true khi sửa thành công hoặc ngược lại
	 */
	public boolean capNhatNhanVien(NhanVien nv) {
		String sql = "UPDATE NhanVien SET ten = ? , gioiTinh = ? , chucVu = ? , soDienThoai = ? , diaChi = ? WHERE maNhanVien = ?";
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, nv.getTen());
			statement.setInt(2, nv.getGioiTinh() ? 1 : 0);
			statement.setString(3, nv.getChucVu());
			statement.setString(4, nv.getSoDienThoai());
			statement.setString(5, nv.getDiaChi());
			statement.setString(6, nv.getMaNhanVien());
			int result = statement.executeUpdate();
			ConnectDB.getInstance().disconnect();
			return result > 0 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConnectDB.getInstance().disconnect();
		return false;
	}

	/**
	 * Function getNhanVienTheoMa được sử dụng để lấy nhân viên theo mã nhân viên
	 * 
	 * @param maNhanVien là mã nhân viên cần tìm
	 * @return trả về đối tượng NhanVien khi tìm thấy hoặc trả về null khi không tìm
	 *         thấy
	 */
	public NhanVien getNhanVienTheoMa(String maNhanVien) {
		String sql = "Select * From NhanVien where maNhanVien LIKE ?";
		// kết nối db
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNhanVien + "%");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String ma = result.getString(1);
				String chucVu = result.getString(2);
				String hoTen = result.getString(3);
				boolean gioiTinh = result.getBoolean(4);
				String sdt = result.getString(5);
				String dc = result.getString(6);

				return new NhanVien(ma, hoTen, gioiTinh, chucVu, sdt, dc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<NhanVien> timKiemNhanVienTheoCacTieuChi(String maNV, String hoTen, String chucVu, boolean gt,
			String sdt, String diaChi) {
		String sql = "Select * From NhanVien where maNhanVien LIKE ? AND ten LIKE ? AND soDienThoai LIKE ? AND diaChi LIKE ? AND chucVu LIKE ? And gioiTinh = ?";
		// kết nối db
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, "%" + maNV + "%");
			statement.setString(2, "%" + hoTen + "%");
			statement.setString(3, "%" + sdt + "%");
			statement.setString(4, "%" + diaChi + "%");
			statement.setString(5, "%" + chucVu + "%");
			statement.setBoolean(6, gt);
			ResultSet result = statement.executeQuery();
			List<NhanVien> list = new ArrayList<>();
			while (result.next()) {
				NhanVien n = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getBoolean("gioiTinh"), result.getString("chucVu"), result.getString("soDienThoai"),
						result.getString("diaChi"));
				list.add(n);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
