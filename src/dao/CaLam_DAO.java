package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.CaLam;

public class CaLam_DAO {
	private List<CaLam> dsCaLam;

	public CaLam_DAO() {
		dsCaLam = new ArrayList<>();
	}

	/**
	 * Function getAllCaLam được sử dụng để lấy tất cả ca làm đã được thêm
	 * 
	 * @return trả về đối tượng List<CaLan>
	 */
	public List<CaLam> getAllCaLam() {
		String sql = "SELECT * FROM CaLam";
		List<CaLam> list = new ArrayList<>();
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				list.add(cl);
			}
			statement.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Function themCaLam được sử dụng để thêm ca làm mới vào database
	 * 
	 * @param caLam là đối tượng CaLam cần thêm
	 * @return trả về true khi thêm thành công hoặc ngược lại
	 */
	public boolean themCaLam(CaLam caLam) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO CaLam (gioBatDau, gioKetThuc) VALUES (?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setTime(1, caLam.getGioBatDau());
			stmt.setTime(2, caLam.getGioKetThuc());
			n = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public CaLam getCaLamTheoThoiGian(String gioBatDau, String gioKetThuc) {
		CaLam cl = null;
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				String sql = "select * from CaLam"
						+ " where gioBatDau = ? and gioKetThuc = ?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, gioBatDau);
				stmt.setString(2, gioKetThuc);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String maCL = rs.getString("maCaLam");
					Time gioBatDauTimDuoc = rs.getTime("gioBatDau");
					Time gioKetThucTimDuoc = rs.getTime("gioKetThuc");
					cl = new CaLam(maCL, gioBatDauTimDuoc, gioKetThucTimDuoc);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cl;
	}
	
	public CaLam getCaLamTheoMa(String maCaLam) {
		CaLam cl = null;
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				String sql = "select * from CaLam where maCaLam = ?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, maCaLam);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String maCL = rs.getString("maCaLam");
					Time gioBatDauTimDuoc = rs.getTime("gioBatDau");
					Time gioKetThucTimDuoc = rs.getTime("gioKetThuc");
					cl = new CaLam(maCL, gioBatDauTimDuoc, gioKetThucTimDuoc);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cl;
	}
}
