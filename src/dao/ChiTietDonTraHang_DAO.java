package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDonTraHang;
import entity.DonTraHang;
import entity.SanPham;

public class ChiTietDonTraHang_DAO {
	private List<ChiTietDonTraHang> dsCTDTH;

	public ChiTietDonTraHang_DAO() {
		super();
		dsCTDTH = new ArrayList<ChiTietDonTraHang>();
	}
	
	public List<ChiTietDonTraHang> danhSachDonTraHang(String maDonTraHang){
		List<ChiTietDonTraHang> danhSach = new ArrayList<>();
		try {
			String sql = "select * from DonTraHang"
					+ " inner join ChiTietDonTraHang on DonTraHang.maDonTraHang = ChiTietDonTraHang.maDonTraHang"
					+ " where ChiTietDonTraHang.maDonTraHang = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maDonTraHang);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maDonTraHangTimDuoc = rs.getString("maDonTraHang");
				DonTraHang_DAO dsDTH = new DonTraHang_DAO();
				DonTraHang donTra = dsDTH.getDonTraHangTheoMa(maDonTraHang);
				String maSanPham = rs.getString("maSanPham");
				int soLuongTra = rs.getInt("soLuongTra");
				int soLuongNhap = rs.getInt("soLuongNhapKho");
				boolean tinhTrangNhap = rs.getBoolean("tinhTrangNhap");
				SanPham_DAO dsSP = new SanPham_DAO();
				SanPham sp = dsSP.getSanPhamTheoMa(maSanPham);
				danhSach.add(new ChiTietDonTraHang(donTra, sp, soLuongTra, soLuongNhap, tinhTrangNhap));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return danhSach;
	}

	public boolean themChiTietDonTraHang(ChiTietDonTraHang chiTiet) {
		int n = 0;
		
		try {
			String sql = "insert into ChiTietDonTraHang (maDonTraHang, maSanPham, soLuongTra, soLuongNhapKho, tinhTrangNhap) values (?,?,?,?,?)";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, chiTiet.getDonTraHang().getMaDonTraHang());
			stmt.setString(2, chiTiet.getSanPham().getMaSanPham());
			stmt.setInt(3, chiTiet.getSoLuong());
			stmt.setInt(4, chiTiet.getSoLuongNhapKho());
			stmt.setBoolean(5, chiTiet.isTinhTrangNhap());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n>0;
	}
	
	public boolean CapNhatChiTietDonTraHang(ChiTietDonTraHang ctDTH) {
		int n = 0;
		try {
			String sql = "update ChiTietDonTraHang set maSanPham = ?, soLuongTra = ?, soLuongNhapKho = ?, tinhTrangNhap = ? where maDonTraHang = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(5, ctDTH.getDonTraHang().getMaDonTraHang());
			stmt.setString(1, ctDTH.getSanPham().getMaSanPham());
			stmt.setInt(2, ctDTH.getSoLuong());
			stmt.setInt(3, ctDTH.getSoLuongNhapKho());
			stmt.setBoolean(4, ctDTH.isTinhTrangNhap());
			n = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n>0;
	}
}
