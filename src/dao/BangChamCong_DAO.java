package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DateFormatter;

import connectDB.ConnectDB;
import entity.BangChamCong;
import entity.BangPhanCa;
import entity.NhanVien;
import utils.Function;

public class BangChamCong_DAO {
	private List<BangChamCong> dsBangCong;

	public BangChamCong_DAO() {
		super();
		dsBangCong = new ArrayList<BangChamCong>();
	}
	
	public List<BangChamCong> getBangChamCongTheoThoiGianDaChon(LocalDate ngayChon) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String sql = "select BangChamCong.*"
					+ " from BangChamCong"
					+ " where convert(date, thoiGianChamCong, 120) = convert(date, ?, 120)";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, sdf.format(Date.valueOf(ngayChon)));
			ResultSet rs = stmt.executeQuery();
			BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
			while(rs.next()) {
				String maBangPhanCa = rs.getString("maBangPhanCa");
				LocalDate ngayChamCong = Function.convertToLocalDateViaMilisecond(rs.getDate("thoiGianChamCong"));
				boolean trangThaiLamViec = rs.getBoolean("trangThaiDiLam");
				boolean trangThaiCoPhep = rs.getBoolean("trangThaiXinPhep");
				String ghiChu = rs.getString("ghiChu");
				BangPhanCa pc = dsBPC.getBangPhanCaTheoMa(maBangPhanCa);
				dsBangCong.add(new BangChamCong(pc, ngayChamCong, trangThaiLamViec, trangThaiCoPhep, ghiChu));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsBangCong;
	}
	
	public boolean themBangChamCong(BangChamCong bangChamCong) {
		int added = 0;
		try {
			String sql = "insert into BangChamCong (maBangPhanCa, thoiGianChamCong, trangThaiDiLam, trangThaiXinPhep, ghiChu)"
					+ " values (?, ?, ?, ?, ?)";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, bangChamCong.getPhanCa().getMaBangPhanCa());
			stmt.setDate(2, Date.valueOf(bangChamCong.getThoiGianChamCong()));
			stmt.setBoolean(3, bangChamCong.isTrangThaiLamViec());
			stmt.setBoolean(4, bangChamCong.isTrangThaiCoPhep());
			stmt.setString(5, bangChamCong.getGhiChu());
			added = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return added > 0; 
	}
	
	public boolean capNhatBangChamCong(BangChamCong bangChamCong) {
		int updated = 0;
		try {
			String sql = "update BangChamCong set trangThaiDiLam = ?, trangThaiXinPhep = ?, ghiChu = ?"
					+ " where maBangPhanCa = ? and thoiGianChamCong = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setDate(5, Date.valueOf(bangChamCong.getThoiGianChamCong()));
			stmt.setBoolean(1, bangChamCong.isTrangThaiLamViec());
			stmt.setBoolean(2, bangChamCong.isTrangThaiCoPhep());
			stmt.setString(3, bangChamCong.getGhiChu());
			stmt.setString(4, bangChamCong.getPhanCa().getMaBangPhanCa());
			updated = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return updated > 0; 
	}
}
