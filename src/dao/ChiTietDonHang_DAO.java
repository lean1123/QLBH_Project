package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDonHang;
import entity.DonHang;
import entity.SanPham;

public class ChiTietDonHang_DAO {
	public List<ChiTietDonHang> getListChiTietDonHang(){
		List<ChiTietDonHang> dsChiTietDonHang=new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();
			String sql = "select * from ChiTietDonHang" ;
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				SanPham maSanPham=new SanPham(rs.getString(1));
				DonHang maDonHang=new DonHang(rs.getString(2));
				float donGia= rs.getFloat(3);
				int soLuong=rs.getInt(4);

				ChiTietDonHang chiTietDonHang=new ChiTietDonHang(maSanPham, maDonHang, donGia, soLuong);
				dsChiTietDonHang.add(chiTietDonHang);
		}
			statement.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
		return dsChiTietDonHang;
	}

	public List<String[]> getListChiTietDonHangraGUI(String maDonHang){
		List<String[]> dsChiTietDonHang=new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();

			String sql="SELECT "
					+ " ChiTietDonHang.maSanPham, "
					+ " SanPham.tenSanPham, "
					+ " DanhMuc.tenDanhMuc, "
					+ " MauSac.tenMauSac, "
					+ " ChatLieu.tenChatLieu, "
					+ "	KichCo.tenKichCo,"
					+ " NhaCungCap.tenNhaCungCap,"
					+ " ChiTietDonHang.soLuong, "
					+ " ChiTietDonHang.donGia"
					+ " FROM ChiTietDonHang"
					+ " INNER JOIN SanPham ON ChiTietDonHang.maSanPham = SanPham.maSanPham"
					+ " INNER JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc"
					+ " INNER JOIN MauSac ON SanPham.maMauSac = MauSac.maMauSac"
					+ " INNER JOIN ChatLieu ON SanPham.maChatLieu = ChatLieu.maChatLieu"
					+ " INNER JOIN KichCo on SanPham.maKichCo=KichCo.maKichCo"
					+ " INNER JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap"
					+ " WHERE ChiTietDonHang.maDonHang = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maDonHang);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String maSanPham=rs.getString(1);
				System.out.println(maSanPham);
				String tenSanPham=rs.getString(2);
				String tenDanhMuc=rs.getString(3);
				String tenMauSac=rs.getString(4);
				String tenChatLieu=rs.getString(5);
				String tenKichCo=rs.getString(6);
				String tenNhaCungCap=rs.getString(7);
				int soLuong=rs.getInt(8);
				float donGia=rs.getFloat(9);

				String[] row= {maSanPham,tenSanPham,tenDanhMuc,tenMauSac,tenChatLieu,tenKichCo,tenNhaCungCap,soLuong+"",utils.Format.formatAmout(donGia),utils.Format.formatAmout(donGia*soLuong)};
				dsChiTietDonHang.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsChiTietDonHang;
	}
	public boolean themChiTietDonHang(ChiTietDonHang chiTietDonHang) {
		int n=0;
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();

			String sql="insert into ChiTietDonHang(maSanPham,maDonHang,donGia,soLuong) values(?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, chiTietDonHang.getSanPham().getMaSanPham());
			statement.setString(2, chiTietDonHang.getDonHang().getMaDonHang());
			statement.setFloat(3, chiTietDonHang.getDonGia());
			statement.setInt(4, chiTietDonHang.getSoLuong());

			n = statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public boolean xoaChiTietDonHang(String maDonHang) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("DELETE FROM ChiTietDonHang WHERE maDonHang = ?");
			stmt.setString(1, maDonHang);
			n = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

}
