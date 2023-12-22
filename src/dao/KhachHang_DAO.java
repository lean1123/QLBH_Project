package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {
	private List<KhachHang> dskh;

	public KhachHang_DAO() {
		this.dskh = new ArrayList<>();
	}

	/**
	 * Lấy danh sách khách hàng từ cơ sở dữ liệu.
	 *
	 * @return Danh sách khách hàng.
	 */

	public List<KhachHang> getListKhachHang() {

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from KhachHang";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maKhachHang = rs.getString(1);
				String hoTen = rs.getString(2);
				boolean gt = rs.getBoolean(3);
				String sdt = rs.getString(4);
				String diaChi = rs.getString(5);

				KhachHang kh = new KhachHang(maKhachHang, hoTen, gt, sdt, diaChi);
				dskh.add(kh);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dskh;
	}

	/**
	 * Hàm thêm một khách hàng vào database
	 *
	 * @param kh là đối tượng khách hàng cần thêm vào database
	 * @return true nếu thêm khách hàng thành công và false nếu ngược lại.
	 */

	public boolean themKhachHang(KhachHang kh) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
			try {
				String sql = "INSERT INTO KhachHang (ten, gioiTinh, soDienThoai, diaChi) VALUES (?, ?, ?, ?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, kh.getHoTen());
				stmt.setBoolean(2, kh.isGioiTinh());
				stmt.setString(3, kh.getSoDienThoai());
				stmt.setString(4, kh.getDiaChi());
				n = stmt.executeUpdate();
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		return n > 0;
	}

	/**
	 * Hàm thực hiện xóa một khách hàng trong database
	 *
	 * @param maKH là mã khách hàng muốn xóa trong database
	 * @return true nếu xóa thành công và false nếu ngược lại
	 */

	public boolean xoaKhachHang(String maKhachHang) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maKhachHang);
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	/**
	 * Hàm thực hiện cập nhật dữ liệu cho một khách hàng trong database
	 *
	 * @param kh là đối tượng khách hàng chứa thông tin cần cập nhật
	 * @return true nếu quá trình cập nhật thành công, ngược lại là false.
	 */

	public boolean capNhatKhachHang(KhachHang kh) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "update KhachHang set ten=?, gioiTinh=?, soDienThoai=?, diaChi=? where maKhachHang=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, kh.getHoTen());
			stmt.setBoolean(2, kh.isGioiTinh());
			stmt.setString(3, kh.getSoDienThoai());
			stmt.setString(4, kh.getDiaChi());
			stmt.setString(5, kh.getMaKhachHang());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	/**
	 * Hàm thực hiện lấy một khách hàng vừa thêm, cập nhật trong database dựa trên
	 * cột thời gian trong database
	 *
	 * @return khách hàng lấy được từ database
	 */

	public KhachHang getKhachHangVuaCapNhat() {
		KhachHang kh = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 * FROM KhachHang ORDER BY time_stamp DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String maKhachHang = rs.getString(1);
				String tenKhachHang = rs.getString(2);
				boolean gt = rs.getBoolean(3);
				String sdt = rs.getString(4);
				String dc = rs.getString(5);
				kh = new KhachHang(maKhachHang, tenKhachHang, gt, sdt, dc);
			}
			stmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kh;
	}

	/**
	 * Tìm kiếm khách hàng hoặc danh khách khách hàng trong cơ sở dữ liệu nếu trùng
	 * hợp 1 trong các tiêu chí mã khách hàng, họ tên, giới tính, số điện thoại, địa
	 * chỉ
	 *
	 * @param maKH  Mã khách hàng cần tìm kiếm (có thể là một phần của mã).
	 * @param hoTen Họ tên khách hàng cần tìm kiếm (có thể là một phần của họ tên).
	 * @param gt    Giới tính của khách hàng cần tìm kiếm.
	 * @param sdt   Số điện thoại của khách hàng cần tìm kiếm (có thể là một phần
	 *              của số điện thoại).
	 * @param dc    Địa chỉ của khách hàng cần tìm kiếm (có thể là một phần của địa
	 *              chỉ).
	 * @return Danh sách khách hàng thỏa mãn các tiêu chí tìm kiếm.
	 */

	public List<KhachHang> timKiemKhachHang(String maKhachHang, String hoTen, boolean gt, String sdt, String dc){
		List<KhachHang> dstk = new ArrayList<>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where maKhachHang like ? and ten like ? and gioiTinh = ? and diaChi like ? and soDienThoai like ?  " ;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + maKhachHang + "%");
			stmt.setString(2, "%" + hoTen + "%");
			stmt.setBoolean(3, gt);
			stmt.setString(4, "%" + dc + "%");
			stmt.setString(5, "%" + sdt + "%");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				boolean gioiTinh = rs.getBoolean(3);
				String soDienThoai = rs.getString(4);
				String diaChi = rs.getString(5);

				dstk.add(new KhachHang(ma, ten, gioiTinh, soDienThoai, diaChi));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dstk;
	}

	/**
	 * Tìm kiếm khách hàng trong cơ sở dữ liệu dựa trên số điện thoại.
	 *
	 * @param sdt Số điện thoại của khách hàng cần tìm kiếm.
	 * @return Đối tượng KhachHang chứa thông tin của khách hàng tìm kiếm, hoặc null
	 *         nếu không tìm thấy.
	 */

	public KhachHang timKiemKhachHang(String sdt){
		KhachHang kh = null;

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where  soDienThoai = ?  " ;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, sdt);
			String query = stmt.toString();
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				boolean gioiTinh = rs.getBoolean(3);
				String soDienThoai = rs.getString(4);
				String diaChi = rs.getString(5);

				kh = new KhachHang(ma, ten, gioiTinh, soDienThoai, diaChi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return kh;
	}


	/**
	 * Lấy thông tin khách hàng dựa trên mã khách hàng.
	 *
	 * @param maKH Mã khách hàng cần tìm kiếm (có thể là một phần của mã).
	 * @return Đối tượng KhachHang chứa thông tin của khách hàng tìm kiếm, hoặc null
	 *         nếu không tìm thấy.
	 */

	public KhachHang getKhachHangTheoMa(String maKhachHang){
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where maKhachHang = ? " ;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKhachHang);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				boolean gioiTinh = rs.getBoolean(3);
				String soDienThoai = rs.getString(4);
				String diaChi = rs.getString(5);

				return new KhachHang(ma, ten, gioiTinh, soDienThoai, diaChi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
