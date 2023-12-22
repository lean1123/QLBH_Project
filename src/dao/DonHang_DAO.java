package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import connectDB.ConnectDB;
import entity.DonHang;
import entity.KhachHang;
import entity.NhanVien;

public class DonHang_DAO {
	/**
	 * Function taoDonHang được sử dụng để tạo mới đon hàng
	 * @param donHang là thông tin của đơn hàng cần tạo
	 * @return trả về true khi tạo thành công hoặc ngược lại
	 */
	public boolean taoDonHang(DonHang donHang) {
		String sql = "INSERT INTO DonHang(maKhachHang, maNhanVien, ngayDat) VALUES(?,?,?)";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			Date ngayDat = Date.valueOf(donHang.getNgayDat());
			
			statement.setString(1, donHang.getKhachHang().getMaKhachHang());
			statement.setString(2, donHang.getNhanVien().getMaNhanVien());
			statement.setDate(3, ngayDat);
			
			int result = statement.executeUpdate();
			return result > 0? true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Function getMaDonHangVuaTao được sử dụng để lấy mã đơn hàng vừa tạo để thêm chi tiết đơn hàng vào
	 * @return trả về đối tượng String là mã đơn hàng
	 */
	public String getMaDonHangVuaTao() {
		// TODO Auto-generated method stub
		String sql = "SELECT TOP(1) maDonHang FROM DonHang ORDER BY maDonHang DESC";
		String maDonHang = null;
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				maDonHang = result.getString("maDonHang");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maDonHang;
	}
	
	public List<DonHang> getDanhSachDonHang(){
		List<DonHang> dsDonHang=new ArrayList<DonHang>();
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			String sql="select DonHang.maDonHang,"
					+ "DonHang.maNhanVien,"
					+ "DonHang.maKhachHang,"
					+ "DonHang.ngayDat"
					+ " from DonHang";
					
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDonHang=rs.getString(1);
				NhanVien tenNhanVien=new NhanVien(null, rs.getString(2), true, null, null, null);
				KhachHang tenKhachHang=new KhachHang(null, rs.getString(3), true, null, null);
				LocalDate ngayDat=rs.getDate(4).toLocalDate();
				
				DonHang donHang=new DonHang(maDonHang, tenNhanVien, tenKhachHang, ngayDat);
				dsDonHang.add(donHang);
			}
			
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	
	public List<DonHang> getListDonHangTheoDK(String maDonHang, String tenNhanVien, String tenKhachHang, String ngayDat){
		List<DonHang> dsDonHang=new ArrayList<DonHang>();
		
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			
			String sql="select DonHang.maDonHang,"
					+ "NhanVien.ten,"
					+ "KhachHang.ten,"
					+ "DonHang.ngayDat"
					+ " from DonHang"
					+ " inner join NhanVien on NhanVien.maNhanVien=DonHang.maNhanVien"
					+ " inner join KhachHang on KhachHang.maKhachHang=DonHang.maKhachHang"
					+ " where DonHang.maDonHang LIKE ? and NhanVien.ten LIKE ? and KhachHang.ten LIKE ? and DonHang.ngayDat = ?";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, maDonHang + "%");
			stmt.setString(2, tenNhanVien + "%");
			stmt.setString(3, tenKhachHang + "%");
			//stmt.setString(4, ngayDat + "%");
			if (!ngayDat.isEmpty()) {
			    DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
			    java.util.Date parsedDate = dateFormat.parse(ngayDat);
			    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
			    stmt.setDate(4, sqlDate);
			} else {
			    stmt.setNull(4, java.sql.Types.DATE);
			}
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String maDonHang2=rs.getString(1);
				NhanVien tenNhanVien2=new NhanVien(null, rs.getString(2), true, null, null, null);
				KhachHang tenKhachHang2=new KhachHang(null, rs.getString(3), true, null, null);
				LocalDate ngayDat2=rs.getDate(4).toLocalDate();
				
				DonHang donHang=new DonHang(maDonHang2, tenNhanVien2, tenKhachHang2, ngayDat2);
				dsDonHang.add(donHang);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	public List<String[]> timKiemDonHangKhongCoNgay(String maDH, String tenNV, String tenKH){
		List<String[]> dsDonHang=new ArrayList<>();		
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			
			String sql="select DonHang.maDonHang,"
					+ "NhanVien.ten,"
					+ "KhachHang.ten,"
					+ "KhachHang.soDienThoai,"
					+ "KhachHang.diaChi,"
					+ "DonHang.ngayDat"
					+ " from DonHang"
					+ " inner join NhanVien on NhanVien.maNhanVien=DonHang.maNhanVien"
					+ " inner join KhachHang on KhachHang.maKhachHang=DonHang.maKhachHang"
					+ " where DonHang.maDonHang LIKE ? and NhanVien.ten LIKE ? and KhachHang.ten LIKE ? ";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, maDH + "%");
			stmt.setString(2, "%"+ tenNV + "%");
			stmt.setString(3, "%"+ tenKH + "%");
			//stmt.setString(4, ngayDat + "%");
			
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String maDH2=rs.getString(1);									
				String tenNVStr=rs.getString(2);
				String tenKHStr=rs.getString(3);
				String sdtKHStr = rs.getString(4); 
				String diaChiStr = rs.getString(5);
				String ngayDatStr =rs.getString(6);
				float total = calculateTotal(maDH2); // Gọi hàm calculateTotal để tính tổng
	            String totalStr = utils.Format.formatAmout(total);
				String [] row= {maDH2,tenNVStr,tenKHStr,sdtKHStr,diaChiStr,ngayDatStr,totalStr};
				dsDonHang.add(row);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	public List<String[]> timKiemDonHangCoNgay(String maDH, String tenNV, String tenKH,String ngayDat){
		List<String[]> dsDonHang=new ArrayList<>();		
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			
			String sql="select DonHang.maDonHang,"
					+ "NhanVien.ten,"
					+ "KhachHang.ten,"
					+ "KhachHang.soDienThoai,"
					+ "KhachHang.diaChi,"
					+ "DonHang.ngayDat"
					+ " from DonHang"
					+ " inner join NhanVien on NhanVien.maNhanVien=DonHang.maNhanVien"
					+ " inner join KhachHang on KhachHang.maKhachHang=DonHang.maKhachHang"
					+ " where DonHang.maDonHang LIKE ? and NhanVien.ten LIKE ? and KhachHang.ten LIKE ? and DonHang.ngayDat = ?";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, maDH + "%");
			stmt.setString(2, "%"+ tenNV + "%");
			stmt.setString(3, "%"+ tenKH + "%");
			//stmt.setString(4, ngayDat);
			if (!ngayDat.isEmpty()) {
			    DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
			    java.util.Date parsedDate = dateFormat.parse(ngayDat);
			    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
			    stmt.setDate(4, sqlDate);
			} else {
			    stmt.setNull(4, java.sql.Types.DATE);
			}
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String maDH2=rs.getString(1);									
				String tenNVStr=rs.getString(2);
				String tenKHStr=rs.getString(3);
				String sdtKHStr = rs.getString(4); 
				String diaChiStr = rs.getString(5);
				String ngayDatStr =rs.getString(6);
				float total = calculateTotal(maDH2); // Gọi hàm calculateTotal để tính tổng
	            String totalStr = utils.Format.formatAmout(total);
				String [] row= {maDH2,tenNVStr,tenKHStr,sdtKHStr,diaChiStr,ngayDatStr,totalStr};
				dsDonHang.add(row);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	
	public List<DonHang> getListDonHangChoGui(){
		List<DonHang> dsDonHang=new ArrayList<DonHang>();
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			
			String sql="select DonHang.maDonHang,"
					+ "NhanVien.ten,"
					+ "KhachHang.ten,"
					+ "KhachHang.soDienThoai,"
					+ "KhachHang.diaChi,"
					+ "DonHang.ngayDat"
					+ " from DonHang"
					+ " inner join NhanVien on NhanVien.maNhanVien=DonHang.maNhanVien"
					+ " inner join KhachHang on KhachHang.maKhachHang=DonHang.maKhachHang";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDonHang=rs.getString(1);
				NhanVien tenNhanVien=new NhanVien(null, rs.getString(2), true, null, null, null);
				KhachHang tenKhachHang=new KhachHang(null, rs.getString(3), true, null, null);
				KhachHang sdtKhachHang=new KhachHang(null, null, true, rs.getString(4), null);
				KhachHang diaChi=new KhachHang(null, null, true, null, rs.getString(5));
				LocalDate ngayDat=rs.getDate(6).toLocalDate();
				
				DonHang donHang=new DonHang(maDonHang, tenNhanVien, tenKhachHang, ngayDat);
				dsDonHang.add(donHang);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	
	public List<String[]> getListDonHangChoGui2(){
		List<String[]> dsDonHang=new ArrayList<>();
		try {
			Connection con=ConnectDB.getInstance().getConnection();
			
			String sql="select DonHang.maDonHang,"
					+ "NhanVien.ten,"
					+ "KhachHang.ten,"
					+ "KhachHang.soDienThoai,"
					+ "KhachHang.diaChi,"
					+ "DonHang.ngayDat"
					+ " from DonHang"
					+ " inner join NhanVien on NhanVien.maNhanVien=DonHang.maNhanVien"
					+ " inner join KhachHang on KhachHang.maKhachHang=DonHang.maKhachHang";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maDonHang=rs.getString(1);
//			
				
				String tenNhanVienStr=rs.getString(2);
				String tenKhachHangStr=rs.getString(3);
				String sdtKhachHangStr = rs.getString(4); 
				String diaChiStr = rs.getString(5);
				String ngayDatStr =rs.getString(6);
				float total = calculateTotal(maDonHang); // Gọi hàm calculateTotal để tính tổng
	            String totalStr = utils.Format.formatAmout(total);
				String [] row= {maDonHang,tenNhanVienStr,tenKhachHangStr,sdtKhachHangStr,diaChiStr,ngayDatStr,totalStr};
				dsDonHang.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDonHang;
	}
	
	
	
	public float calculateTotal(String maDonHang) {
	    float total = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT (ChiTietDonHang.soLuong * ChiTietDonHang.donGia) AS thanhTien FROM ChiTietDonHang WHERE maDonHang = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maDonHang);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            total += rs.getFloat("thanhTien");
	        }
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return total;
	}


	
	public boolean xoaDonHang(String maDonHang) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("DELETE FROM DonHang WHERE maDonHang = ?");
			stmt.setString(1, maDonHang);
			n = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0; 
	}
	public DonHang timDonHangTheoMa(String maDH) {
		DonHang donHang=null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql="select DonHang.maDonHang, " +
	                "KhachHang.maKhachHang, " +
	                "NhanVien.maNhanVien, " +
	                "DonHang.ngayDat " +
	                "from DonHang " +
	                "inner join KhachHang on KhachHang.maKhachHang = DonHang.maKhachHang " +
	                "inner join NhanVien on NhanVien.maNhanVien = NhanVien.maNhanVien " +
	                "where maDonHang like ?";
			
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1,  maDH+ "%");
			
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				String maDHStr=rs.getString(1);
				String maKH=rs.getString(2);
				KhachHang khachHang=new KhachHang(maKH);
				String maNV=rs.getString(3);
				NhanVien nhanVien=new NhanVien(maNV);
				LocalDate ngayDat=rs.getDate(4).toLocalDate();
				
				donHang=new DonHang(maDHStr, nhanVien, khachHang, ngayDat);
				
				
			}
			stmt.close();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return donHang;
	}
	
}
