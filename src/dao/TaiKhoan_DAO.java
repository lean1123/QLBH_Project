package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;
import utils.Contains;

public class TaiKhoan_DAO {
	/**
	 * Function dangNhap được sử dụng để đăng nhập vào hệ thống
	 * @param taiKhoan là tên tài khoản cần đăng nhập
	 * @param matKhau là mật khẩu của tài khoản cần đăng nhập
	 * @return trả về đối tượng NhanVien khi đăng nhập thành công
	 */
	public NhanVien dangNhap(String taiKhoan, String matKhau) {
		String sql = "SELECT * from TaiKhoan INNER JOIN NhanVien on TaiKhoan.maNhanVien = NhanVien.maNhanVien WHERE TaiKhoan.taiKhoan = ? and TaiKhoan.matKhau = ? and NhanVien.chucVu = ?";

		ConnectDB instance = ConnectDB.getInstance();
		try {
			instance.connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement;
		try {
			statement = con.prepareStatement(sql);
			statement.setNString(1, taiKhoan);
			statement.setNString(2, matKhau);
			statement.setNString(3, Contains.getRole());
			ResultSet result = statement.executeQuery();
			result.next();
			if(result.getRow() > 0) {
				NhanVien n = new NhanVien(result.getString("maNhanVien"),result.getString("ten"),result.getInt("gioiTinh") == 1 ? true:false,result.getString("chucVu"),result.getString("soDienThoai"),result.getString("diaChi") );
				con.close();
				return n;
			}else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Function getAllTaiKhoan được sử dụng để lấy tất cả thông tin tài khoản
	 * @return trả về thông tin tài khoản khi có tài khoản và trả về null khi không có tài khoản
	 */
	public List<TaiKhoan> getAllTaiKhoan() {
		String sql = "SELECT * from TaiKhoan INNER JOIN NhanVien on TaiKhoan.maNhanVien = NhanVien.maNhanVien ORDER BY TaiKhoan.maNhanVien ASC";
		List<TaiKhoan> list = new ArrayList<>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement;
		try {
			statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				NhanVien nv = new NhanVien(result.getString("maNhanVien"),result.getString("ten"),result.getInt("gioiTinh") == 1 ? true:false,result.getString("chucVu"),result.getString("soDienThoai"),result.getString("diaChi") );
				TaiKhoan tk = new TaiKhoan(result.getString("taiKhoan"), result.getString("matKhau"), nv);
				list.add(tk);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Function themTaiKhoan được sử dụng để thêm tài khoản mới và database
	 * @param tk là đối tượng tài khoản cần thêm
	 * @return trả về  true khi thêm tài khoản thành công hoặc trả về false khi thêm tài khoản không thành công
	 */
	public boolean themTaiKhoan(TaiKhoan tk) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO TaiKhoan(maNhanVien,taiKhoan,matKhau) VALUES(?,?,?)";
		Connection con = ConnectDB.getConnection();

		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tk.getNhanVien().getMaNhanVien());
			statement.setString(2, tk.getTaiKhoan());
			statement.setString(3, tk.getMatKhau());

			int result = statement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Function capNhatTaiKhoan được sử dụng để sửa thông tin tài khoản khi quên
	 * @param tk là đối tượng TaiKhoan cần sửa
	 * @return trả về  true khi sửa tài khoản thành công hoặc trả về false khi sửa tài khoản không thành công
	 */
	public boolean capNhatTaiKhoan(TaiKhoan tk) {
		// TODO Auto-generated method stub
		String sql = "UPDATE TaiKhoan set taiKhoan = ?, matKhau =? WHERE  maNhanVien  = ?";
		Connection con = ConnectDB.getConnection();

		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(3, tk.getNhanVien().getMaNhanVien());
			statement.setString(1, tk.getTaiKhoan());
			statement.setString(2, tk.getMatKhau());

			int result = statement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
