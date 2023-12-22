package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;

public class ChuongTrinhKhuyenMai_DAO {
	private List<ChuongTrinhKhuyenMai> danhSachCTKhuyenMai;

	public ChuongTrinhKhuyenMai_DAO() {
		this.danhSachCTKhuyenMai = new ArrayList<>();
	}

	/**
	 * Lấy một chương trình khuyến mãi từ cở sở dữ liệu bằng mã khuyến mãi
	 *
	 * @param maKhuyenMai Là mã khuyến mãi cần tìm.
	 * @return ChuongTrinhKhuyenMai hoặc null nếu không tìm thấy.
	 */

	public ChuongTrinhKhuyenMai getKhuyenMaiTheoMa(String maKhuyenMai) {
		ChuongTrinhKhuyenMai ctKhuyenMai = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from ChuongTrinhKhuyenMai where maKhuyenMai = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKhuyenMai);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenKhuyenMai = rs.getString(2);
				String moTa = rs.getString(3);
				Date ngayBatDau = rs.getDate(4);
				Date ngayKetThuc = rs.getDate(5);
				int soLuong = rs.getInt(6);
				double giaGiam = rs.getDouble(7);
				String maCaptcha = rs.getString(8);

				ctKhuyenMai = new ChuongTrinhKhuyenMai(maKhuyenMai, tenKhuyenMai, moTa, ngayBatDau, ngayKetThuc, soLuong, giaGiam, maCaptcha);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ctKhuyenMai;
	}

	/**
	 * Lấy danh sách tất cả các chương trình khuyến mãi từ cơ sở dữ liệu.
	 *
	 * @return Danh sách các chương trình khuyến mãi.
	 */

	public List<ChuongTrinhKhuyenMai> getListChuongTrinhKhuyenMai() {

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from ChuongTrinhKhuyenMai";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maKhuyenMai = rs.getString(1);
				String tenKhuyenMai = rs.getString(2);
				String moTa = rs.getString(3);
				Date ngayBatDau = rs.getDate(4);
				Date ngayKetThuc = rs.getDate(5);
				int soLuong = rs.getInt(6);
				double giaGiam = rs.getDouble(7);
				String maCap = rs.getString(8);

				ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(maKhuyenMai, tenKhuyenMai, moTa, ngayBatDau, ngayKetThuc,
						soLuong, giaGiam, maCap);
				danhSachCTKhuyenMai.add(ctkm);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return danhSachCTKhuyenMai;
	}

	/**
	 * Cập nhật thông tin của một chương trình khuyến mãi trong cơ sở dữ liệu.
	 *
	 * @param ctkm Đối tượng ChuongTrinhKhuyenMai chứa thông tin cần cập nhật.
	 * @return true nếu cập nhật thành công, false nếu có lỗi hoặc không thể cập nhật.
	 */

	public boolean capNhatChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai ctkm){
		int n = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "update ChuongTrinhKhuyenMai set tenKhuyenMai = ?, moTa = ?, ngayBatDau = CONVERT(date, ?, 105), ngayKetThuc = CONVERT(date, ?, 105), soLuong = ?, giaGiam = ?, maCaptcha = ? where maKhuyenMai=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ctkm.getTenKhuyenMai());
			stmt.setString(2, ctkm.getMoTa());
			stmt.setString(3, sdf.format(ctkm.getNgayBatDau()));
			stmt.setString(4, sdf.format(ctkm.getNgayKetThuc()));
			stmt.setInt(5, ctkm.getSoLuong());
			stmt.setFloat(6, (float) ctkm.getGiaGiam());
			stmt.setString(7, ctkm.getMaCaptcha());
			stmt.setString(8, ctkm.getMaKhuyenMai());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	/**
	 *
	 * @param maCaptcha
	 * @return
	 */


	public ChuongTrinhKhuyenMai timChuongTrinhKhuyenMaiTheoMaCaptcha(String maCaptcha){
		ChuongTrinhKhuyenMai ctkm=null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql="select * from ChuongTrinhKhuyenMai where maCaptcha = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, maCaptcha);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maKhuyenMai=rs.getString(1);
				String tenKhuyenMai=rs.getString(2);
				String moTa=rs.getString(3);
				Date ngayBatDau=rs.getDate(4);
				Date ngayKetThuc=rs.getDate(5);
				int soLuong=rs.getInt(6);
				double giaGiam=rs.getFloat(7);
				String maCaptchaStr=rs.getString(8);

				ctkm=new ChuongTrinhKhuyenMai(maKhuyenMai, tenKhuyenMai, moTa, ngayBatDau, ngayKetThuc, soLuong, giaGiam, maCaptcha);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctkm;
	}



	public boolean capNhatSoLuongMaKhuyenMai(String maCaptcha) {

		String sql = "UPDATE ChuongTrinhKhuyenMai SET soLuong = soLuong-1 WHERE maCaptcha = ?";

		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maCaptcha);
		//	statement.setInt(2, soLuongTon);
		//	statement.setString(3, maSP);

			int result = statement.executeUpdate();

			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	}
	/**
	 * Thêm một chương trình khuyến mãi mới vào cơ sở dữ liệu.
	 *
	 * @param ctkm Đối tượng chương trình khuyến mãi cần thêm.
	 * @return true nếu thêm thành công, false nếu có lỗi hoặc không thể thêm.
	 */

	public boolean themChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai ctkm){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO ChuongTrinhKhuyenMai (tenKhuyenMai, moTa, ngayBatDau, ngayKetThuc, soLuong, giaGiam, maCaptcha) VALUES (?, ?, CONVERT(date, ?, 105), CONVERT(date, ?, 105), ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ctkm.getTenKhuyenMai());
			stmt.setString(2, ctkm.getMoTa());
			stmt.setString(3, sdf.format(ctkm.getNgayBatDau()));
			stmt.setString(4, sdf.format(ctkm.getNgayKetThuc()));
			stmt.setInt(5, ctkm.getSoLuong());
			stmt.setFloat(6, (float) ctkm.getGiaGiam());
			stmt.setString(7, ctkm.getMaCaptcha());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
	/**
	 * Lấy thông tin của chương trình khuyến mãi vừa được cập nhật gần đây nhất trong cơ sở dữ liệu.
	 *
	 * @return Đối tượng ChuongTrinhKhuyenMai chứa thông tin chương trình khuyến mãi vừa được cập nhật, hoặc null nếu không có dữ liệu.
	 */

	public ChuongTrinhKhuyenMai getChuongTrinhKhuyenMaiVuaCapNhat(){
		ChuongTrinhKhuyenMai ctkm = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 * FROM ChuongTrinhKhuyenMai ORDER BY time_stamp DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maKhuyenMai = rs.getString(1);
				String tenKhuyenMai = rs.getString(2);
				String moTa = rs.getString(3);
				Date ngayBatDau = rs.getDate(4);
				Date ngayKetThuc = rs.getDate(5);
				int soLuong = rs.getInt(6);
				double giaGiam = rs.getDouble(7);
				String maCap = rs.getString(8);
				ctkm = new ChuongTrinhKhuyenMai(maKhuyenMai, tenKhuyenMai, moTa, ngayBatDau, ngayKetThuc, soLuong, giaGiam, maCap);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ctkm;
	}
}
