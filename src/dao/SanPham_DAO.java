package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.SanPham;

public class SanPham_DAO {
	/**
	 * Function getDanhSachDanhMuc được sử dụng để lấy tất cả danh mục trong database
	 * @return trả về đối tượng List<String> tên của danh mục
	 */
	public List<String> getDanhSachDanhMuc(){
		String sql = "SELECT * FROM DanhMuc";
		List<String> list = new ArrayList<String>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement  = con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String itemDanhMuc = resultSet.getString("tenDanhMuc");
				list.add(itemDanhMuc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Function getDanhSachMauSac được sử dụng để lấy tất cả màu sắc trong database
	 * @return trả về đối tượng List<String> tên của màu sắc
	 */
	public List<String> getDanhSachMauSac(){
		String sql = "SELECT * FROM MauSac";
		List<String> list = new ArrayList<String>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement  = con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String itemDauSac = resultSet.getString("tenMauSac");
				list.add(itemDauSac);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Function getDanhSachKichCo được sử dụng để lấy tất cả kích cỡ trong database
	 * @return trả về đối tượng List<String> tên của kích cỡ
	 */
	public List<String> getDanhSachKichCo(){
		String sql = "SELECT * FROM KichCo";
		List<String> list = new ArrayList<String>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement  = con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String itemKichCo = resultSet.getString("tenKichCo");
				list.add(itemKichCo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Function getDanhSachChatLieu được sử dụng để lấy tất cả chất liệu trong database
	 * @return trả về đối tượng List<String> tên của chất liệu
	 */
	public List<String> getDanhSachChatLieu(){
		String sql = "SELECT * FROM ChatLieu";
		List<String> list = new ArrayList<String>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement  = con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String itemChatLieu = resultSet.getString("tenChatLieu");
				list.add(itemChatLieu);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Function getMaDanhMuc được sử dụng để lấy mã danh mục theo tên
	 * @param tenDanhMuc tên danh mục cần lấy mã
	 * @return trả về mã danh mục nếu có hoắc trả về null khi không có
	 */
	public String getMaDanhMuc(String tenDanhMuc) {
		String sql = "SELECT maDanhMuc FROM DanhMuc WHERE tenDanhMuc = ?";
		Connection con = ConnectDB.getConnection();
		String maDanhMuc = null;
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenDanhMuc);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				maDanhMuc = resultSet.getString("maDanhMuc");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maDanhMuc;
		
	}
	/**
	 * Function getMaMauSac được sử dụng để lấy mã màu sắc theo tên
	 * @param tenMauSac tên màu sắc cần lấy mã
	 * @return trả về mã màu sắc nếu có hoắc trả về null khi không có
	 */
	public String getMaMauSac(String tenMauSac) {
		String sql = "SELECT maMauSac FROM MauSac WHERE tenMauSac = ?";
		Connection con = ConnectDB.getConnection();
		String maMauSac = null;
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenMauSac);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				maMauSac = resultSet.getString("maMauSac");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maMauSac;
		
	}
	
	public String getMaChatLieu(String tenChatLieu) {
		String sql = "SELECT maChatLieu FROM ChatLieu WHERE tenChatLieu = ?";
		Connection con = ConnectDB.getConnection();
		String maChatLieu = null;
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenChatLieu);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				maChatLieu = resultSet.getString("maChatLieu");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maChatLieu;
		
	}
	public String getMaKichCo(String tenKichCo) {
		String sql = "SELECT maKichCo FROM KichCo WHERE tenKichCo = ?";
		Connection con = ConnectDB.getConnection();
		String maKichCo = null;
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maKichCo);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				maKichCo = resultSet.getString("maKichCo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maKichCo;
		
	}
	
	public List<SanPham> getDanhSachSanPham(){
		List<SanPham> list = new ArrayList<SanPham>();
		String sql = "SELECT * FROM SanPham "
				+ " LEFT JOIN MauSac On SanPham.maMauSac = MauSac.maMauSac "
				+ " LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
				+ " LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
				+ " LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
				+ " LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				String mauSac = result.getString("tenMauSac");
				String chatLieu = result.getString("tenChatLieu");
				String kichCo = result.getString("tenKichCo");
				String danhMuc = result.getString("tenDanhMuc");
				String maSanPham = result.getString("maSanPham");
				String tenSanPham = result.getString("tenSanPham");
				float giaNhap = result.getFloat("giaNhap");
				float giaBan = result.getFloat("giaBan");
				int soLuongTon = result.getInt("soLuongTon");
				int soLuongBan = result.getInt("SoLuongBan");
				String urlAvt = result.getString("urlAvt");
				
				String maNhaCungCap = result.getString("maNhaCungCap");
				String tenNhaCungCap = result.getString("tenNhaCungCap");
				String DC = result.getString("diaChi");
				String email = result.getString("email");
				String sdt = result.getString("soDienThoai");
				

				NhaCungCap NhaCungCap = new NhaCungCap(maNhaCungCap, tenNhaCungCap, DC, email, sdt);
				
				SanPham SanPham = new SanPham(maSanPham, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, urlAvt, danhMuc, mauSac, chatLieu, kichCo, NhaCungCap);
				list.add(SanPham);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean capNhatSanPham(SanPham sanPham){
		String sql = "UPDATE SanPham "
				+ " SET"
				+ " maNhaCungCap = ?,"
				+ " tenSanPham = ?,"
				+ " giaBan = ?,"
				+ " giaNhap = ?,"
				+ " urlAvt = ? ,"
				+ " soLuongTon = ?,"
				+ " soLuongBan = ?,"
				+ " maMauSac = (SELECT TOP 1 maMauSac FROM MauSac WHERE tenMauSac = ?),"
				+ " maDanhMuc = (SELECT TOP 1 maDanhMuc FROM DanhMuc WHERE tenDanhMuc = ?),"
				+ " maKichCo = (SELECT TOP 1 maKichCo FROM KichCo WHERE tenKichCo = ? ),"
				+ " maChatLieu = (SELECT TOP 1 maChatLieu FROM ChatLieu WHERE tenChatLieu = ?)"
				+ " WHERE "
				+ " maSanPham = ?";
		
		String tenSanPham = sanPham.getTenSanPham();
		String mauSac = sanPham.getMauSac();
		String chatLieu = sanPham.getChatLieu();
		String kichCo = sanPham.getKichCo();
		String danhMuc = sanPham.getDanhMuc();
		float giaNhap = sanPham.getGiaNhap();
		float giaBan = sanPham.getGiaBan();
		int soLuongTon = sanPham.getSoLuongTon();
		int soLuongBan = sanPham.getSoLuongBan();
		String urlAvt = sanPham.getUrlAvt();
		String maNhaCungCap = sanPham.getNhaCungCap().getMaNhaCungCap();
		Connection con = ConnectDB.getConnection();
		
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNhaCungCap);
			statement.setString(2, tenSanPham);
			statement.setFloat(3, giaBan);
			statement.setFloat(4, giaNhap);
			statement.setString(5, urlAvt);
			statement.setInt(6, soLuongTon);
			statement.setInt(7, soLuongBan);
			statement.setString(8, mauSac);
			statement.setString(9, danhMuc);
			statement.setString(10, kichCo);
			statement.setString(11, chatLieu);
			statement.setString(12, sanPham.getMaSanPham());
			
			int result = statement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean themMauSacSanPham(SanPham sanPham) {
		String sql = "INSERT INTO SanPham (maNhaCungCap, tenSanPham, giaBan, giaNhap, urlAvt, soLuongTon, soLuongBan, maMauSac, maDanhMuc, maKichCo, maChatLieu) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, "
	            + "(SELECT TOP 1 maMauSac FROM MauSac WHERE tenMauSac = ?), "
	            + "(SELECT TOP 1 maDanhMuc FROM DanhMuc WHERE tenDanhMuc = ?), "
	            + "(SELECT TOP 1 maKichCo FROM KichCo WHERE tenKichCo = ?), "
	            + "(SELECT TOP 1 maChatLieu FROM ChatLieu WHERE tenChatLieu = ?) )";
		
		String tenSanPham = sanPham.getTenSanPham();
		String mauSac = sanPham.getMauSac();
		String chatLieu = sanPham.getChatLieu();
		String kichCo = sanPham.getKichCo();
		String danhMuc = sanPham.getDanhMuc();
		float giaNhap = sanPham.getGiaNhap();
		float giaBan = sanPham.getGiaBan();
		int soLuongTon = sanPham.getSoLuongTon();
		int soLuongBan = sanPham.getSoLuongBan();
		String urlAvt = sanPham.getUrlAvt();
		String maNhaCungCap = sanPham.getNhaCungCap().getMaNhaCungCap();
		Connection con = ConnectDB.getConnection();
		
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNhaCungCap);
			statement.setString(2, tenSanPham);
			statement.setFloat(3, giaBan);
			statement.setFloat(4, giaNhap);
			statement.setString(5, urlAvt);
			statement.setInt(6, soLuongTon);
			statement.setInt(7, soLuongBan);
			statement.setString(8, mauSac);
			statement.setString(9, danhMuc);
			statement.setString(10, kichCo);
			statement.setString(11, chatLieu);
			
			int result = statement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public boolean capNhatSoLuongSanPham(String maSanPham, int soLuongBan, int soLuongTon) {
		
		String sql = "UPDATE SanPham SET soLuongBan = ? , soLuongTon = ? WHERE maSanPham = ?";
		
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, soLuongBan);
			statement.setInt(2, soLuongTon);
			statement.setString(3, maSanPham);
			
			int result = statement.executeUpdate();
			
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	public String getAnhSanPham(String maSanPham) {
	    String pathImage = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();

	        String sql = "SELECT SanPham.urlAvt FROM SanPham WHERE maSanPham = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, maSanPham);
	        ResultSet rs = statement.executeQuery();

	        if (rs.next()) {
	            pathImage = rs.getString("urlAvt");
	        }
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pathImage;
	}
	public String layDuLieuCotSanPham() {
		String ketQua=null;
		try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maSanPham FROM SanPham ORDER BY maSanPham DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                ketQua = rs.getString("maSanPham");
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
	}
	
	public String layDuLieuCotChatLieu() {
		String ketQua = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 tenChatLieu FROM ChatLieu ORDER BY maChatLieu DESC";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				ketQua = rs.getString("tenChatLieu");
			}
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}
	public boolean themChatLieu (String tenChatLieu){
		int n = 0 ;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "INSERT INTO ChatLieu ([tenChatLieu]) VALUES (?)" ;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenChatLieu);
			
			n = statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return n > 0 ;
	}
	public boolean capNhatChatLieu(String tenChatLieuCu, String tenChatLieuMoi) {
	    int n = 0;
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();

	        String sql = "update ChatLieu set tenChatLieu = ? where tenChatLieu = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, tenChatLieuMoi);
	        statement.setString(2, tenChatLieuCu);

	        n = statement.executeUpdate();
	        statement.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public boolean themDanhMuc(String tenDanhMuc) {
		int n = 0 ;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "INSERT INTO DanhMuc ([tenDanhMuc]) VALUES (?)" ;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenDanhMuc);
			
			n = statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return n > 0 ;
	}
	public String layDuLieuCotDanhMuc() {
		String ketQua = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 tenDanhMuc FROM DanhMuc ORDER BY maDanhMuc DESC";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				ketQua = rs.getString("tenDanhMuc");
			}
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}
	public boolean capNhatDanhMuc(String tenDanhMucCu, String tenDanhMucMoi) {
		 int n = 0;
		    try {
		        ConnectDB.getInstance();
		        Connection con = ConnectDB.getConnection();

		        String sql = "update DanhMuc set tenDanhMuc = ? where tenDanhMuc = ?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setString(1, tenDanhMucMoi);
		        statement.setString(2, tenDanhMucCu);

		        n = statement.executeUpdate();
		        statement.close();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return n > 0;
	}
	public boolean themKichCo(String tenKichCo) {
		int n = 0 ;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "INSERT INTO KichCo ([tenKichCo]) VALUES (?)" ;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenKichCo);
			
			n = statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return n > 0 ;
	}
	public String layDuLieuCotKichCo() {
		String ketQua = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 tenKichCo FROM KichCo ORDER BY maKichCo DESC";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				ketQua = rs.getString("tenKichCo");
			}
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}
	public boolean capNhatKichCo(String tenKichCoCu, String tenKichCoMoi) {
		int n = 0;
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();

	        String sql = "update KichCo set tenKichCo = ? where tenKichCo = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, tenKichCoMoi);
	        statement.setString(2, tenKichCoCu);

	        n = statement.executeUpdate();
	        statement.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public boolean themMauSac(String tenMauSac) {
		int n = 0 ;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "INSERT INTO MauSac ([tenMauSac]) VALUES (?)" ;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, tenMauSac);
			
			n = statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return n > 0 ;
	}
	public String layDuLieuCotMauSac() {
		String ketQua = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT TOP 1 tenMauSac FROM MauSac ORDER BY maMauSac DESC";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				ketQua = rs.getString("tenMauSac");
			}
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}
	public boolean capNhatMauSac(String tenMauSacCu, String tenMauSacMoi) {
		int n = 0;
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();

	        String sql = "update MauSac set tenMauSac = ? where tenMauSac = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, tenMauSacMoi);
	        statement.setString(2, tenMauSacCu);

	        n = statement.executeUpdate();
	        statement.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public List<SanPham> getlistSanPhamTheoDK(String maSanPham, String tenSanPham, String giaNhap, String giaBan, String soLuongTon, String soLuongBan, String tenDanhMuc, String tenMauSac, String tenChatLieu, String tenKichCo, String tenNhaCungCap){
		ArrayList<SanPham> dsSanPham = new ArrayList<SanPham>();

		try {
			Connection con=ConnectDB.getInstance().getConnection();
			String sql="select SanPham.maSanPham,"
					+ "SanPham.tenSanPham,"
					+ "SanPham.giaNhap,"
					+ "SanPham.giaBan,"
					+ "SanPham.soLuongTon,"
					+ "SanPham.soLuongBan,"
					+ "DanhMuc.tenDanhMuc,"
					+ "MauSac.tenMauSac,"
					+ "ChatLieu.tenChatLieu,"
					+ "KichCo.tenKichCo,"
					+ "NhaCungCap.tenNhaCungCap"
					+ " from SanPham"
					+" LEFT JOIN   DanhMuc "
					+ " on SanPham.maDanhMuc = DanhMuc.maDanhMuc "
					+ " LEFT JOIN  MauSac "
					+ " on SanPham.maMauSac = MauSac.maMauSac "
					+ " LEFT JOIN  ChatLieu "
					+ " on SanPham.maChatLieu = ChatLieu.maChatLieu "
					+ " LEFT JOIN  KichCo "
					+ " on SanPham.maKichCo = KichCo.maKichCo"
					+ " LEFT JOIN NhaCungCap "
					+ " on SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap"
					+ " where SanPham.maSanPham LIKE ? and SanPham.tenSanPham LIKE ? and DanhMuc.tenDanhMuc LIKE ? and MauSac.tenMauSac LIKE ? and ChatLieu.tenChatLieu LIKE ? and KichCo.tenKichCo LIKE ? and NhaCungCap.tenNhaCungCap LIKE ? and SanPham.giaNhap " + giaNhap + " and SanPham.giaBan" + giaBan + " and SanPham.soLuongTon" + soLuongTon + " and SanPham.soLuongBan" + soLuongBan ;

			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, maSanPham + "%");
			stmt.setString(2, tenSanPham + "%");
			stmt.setString(3, tenDanhMuc + "%");
			stmt.setString(4, tenMauSac + "%");
			stmt.setString(5, tenChatLieu + "%");
			stmt.setString(6, tenKichCo + "%");
			stmt.setString(7, tenNhaCungCap + "%");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String maSP2 = rs.getString(1) ;
				String tenSP2 = rs.getString(2) ;
				float giaNhap2=rs.getFloat(3);
				float giaBan2=rs.getFloat(4);
				int soLuongTon2=rs.getInt(5);
				int soLuongBan2=rs.getInt(6);
				String tenDM2=rs.getString(7);
				String tenMS2=rs.getString(8);
				String tenCL2=rs.getString(9);
				String tenKC2=rs.getString(10);
				//String tenNCC2=rs.getString(11);
//				DanhMuc tenDM2=new DanhMuc(null, rs.getString(7));
//				MauSac tenMS2=new MauSac(null, rs.getString(8));
//				ChatLieu tenCL2=new ChatLieu(null, rs.getString(9));
//				KichCo tenKC2=new KichCo(null, rs.getString(10));
				NhaCungCap tenNCC2=new NhaCungCap(null, rs.getString(11), null, null, null);
				
				SanPham sanPham=new SanPham(maSP2, tenSP2, giaNhap2, giaBan2, soLuongTon2, soLuongBan2, tenDM2, tenMS2, tenCL2, tenKC2, tenNCC2);
				dsSanPham.add(sanPham);
			}
			
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsSanPham;
	}
	public SanPham timKiemSanPham(String maSanPham) {
		String sql ="SELECT * FROM SanPham "
				+ " LEFT JOIN MauSac On SanPham.maMauSac = MauSac.maMauSac "
				+ " LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
				+ " LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
				+ " LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
				+ " LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap "
				+ " WHERE maSanPham = ? ";
		SanPham SanPham = null;
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maSanPham);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				String mauSac = result.getString("tenMauSac");
				String chatLieu = result.getString("tenChatLieu");
				String kichCo = result.getString("tenKichCo");
				String danhMuc = result.getString("tenDanhMuc");
				String maSanPhamReturn = result.getString("maSanPham");
				String tenSanPham = result.getString("tenSanPham");
				float giaNhap = result.getFloat("giaNhap");
				float giaBan = result.getFloat("giaBan");
				int soLuongTon = result.getInt("soLuongTon");
				int soLuongBan = result.getInt("SoLuongBan");
				String urlAvt = result.getString("urlAvt");
				
				String maNhaCungCap = result.getString("maNhaCungCap");
				String tenNhaCungCap = result.getString("tenNhaCungCap");
				String DC = result.getString("diaChi");
				String email = result.getString("email");
				String sdt = result.getString("soDienThoai");
				

				NhaCungCap NhaCungCap = new NhaCungCap(maNhaCungCap, tenNhaCungCap, DC, email, sdt);
				
				SanPham = new SanPham(maSanPhamReturn, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, urlAvt, danhMuc, mauSac, chatLieu, kichCo, NhaCungCap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SanPham;
		
	}
	public void capNhatSoLuongSanPhamNeuHuyDonHang(String maSanPham, int soLuong) {
	    
	    try {
	    	Connection con = ConnectDB.getConnection();
    	    String sql = "UPDATE SanPham SET soLuongBan = soLuongBan - ? , soLuongTon = soLuongTon + ? WHERE maSanPham = ?";

	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setInt(1, soLuong);
	        statement.setInt(2, soLuong);
	        statement.setString(3, maSanPham);
	        
	        statement.executeUpdate();
	        
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception if needed
	    }
	}
//	public List<SanPham> getDanhSachSanPhamGuiThongKeSanPham(){
//		List<SanPham> list = new ArrayList<SanPham>();
//		String sql = "select *"
//				+ "		from SanPham"
//				+ "		LEFT JOIN MauSac On SanPham.maMauSac = MauSac.maMauSac "
//				+ "		LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
//				+ "		LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
//				+ "		LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
//				+ "		LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap";
//		
//		try {
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = con.prepareStatement(sql);
//			ResultSet result = statement.executeQuery();
//			while(result.next()) {
//				String mauSac = result.getString("tenMauSac");
//				String chatLieu = result.getString("tenChatLieu");
//				String kichCo = result.getString("tenKichCo");
//				String danhMuc = result.getString("tenDanhMuc");
//				String maSanPham = result.getString("maSanPham");
//				String tenSanPham = result.getString("tenSanPham");
//				float giaBan = result.getFloat("giaBan");
//				int soLuongTon = result.getInt("soLuongTon");
//				int soLuongBan = result.getInt("SoLuongBan");
//				
//				String maNhaCungCap = result.getString("maNhaCungCap");
//				String tenNhaCungCap = result.getString("tenNhaCungCap");
//				String DC = result.getString("diaChi");
//				String email = result.getString("email");
//				String sdt = result.getString("soDienThoai");
//				
//
//				NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, DC, email, sdt);
//				
//				SanPham sp = new SanPham(maSanPham, tenSanPham, giaBan, soLuongTon,0 , soLuongBan, danhMuc, mauSac, chatLieu, kichCo, ncc);
//				list.add(sp);
//			}
//			return list;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	public boolean themSanPham(SanPham sanPham) {
		String sql = "INSERT INTO SanPham (maNhaCungCap, tenSanPham, giaBan, giaNhap, urlAvt, soLuongTon, soLuongBan, maMauSac, maDanhMuc, maKichCo, maChatLieu) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, "
	            + "(SELECT TOP 1 maMauSac FROM MauSac WHERE tenMauSac = ?), "
	            + "(SELECT TOP 1 maDanhMuc FROM DanhMuc WHERE tenDanhMuc = ?), "
	            + "(SELECT TOP 1 maKichCo FROM KichCo WHERE tenKichCo = ?), "
	            + "(SELECT TOP 1 maChatLieu FROM ChatLieu WHERE tenChatLieu = ?))";
		
		String tenSanPham = sanPham.getTenSanPham();
		String mauSac = sanPham.getMauSac();
		String chatLieu = sanPham.getChatLieu();
		String kichCo = sanPham.getKichCo();
		String danhMuc = sanPham.getDanhMuc();
		float giaNhap = sanPham.getGiaNhap();
		float giaBan = sanPham.getGiaBan();
		int soLuongTon = sanPham.getSoLuongTon();
		int soLuongBan = sanPham.getSoLuongBan();
		String urlAvt = sanPham.getUrlAvt();
		String maNhaCungCap = sanPham.getNhaCungCap().getMaNhaCungCap();
		Connection con = ConnectDB.getConnection();
		
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNhaCungCap);
			statement.setString(2, tenSanPham);
			statement.setFloat(3, giaBan);
			statement.setFloat(4, giaNhap);
			statement.setString(5, urlAvt);
			statement.setInt(6, soLuongTon);
			statement.setInt(7, soLuongBan);
			statement.setString(8, mauSac);
			statement.setString(9, danhMuc);
			statement.setString(10, kichCo);
			statement.setString(11, chatLieu);
			
			int result = statement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public List<SanPham> getDanhSachSanPhamGuiThongKeSP(){
		List<SanPham> list = new ArrayList<SanPham>();
		String sql = "select *"
				+ "		from SanPham"
				+ "		LEFT JOIN MauSac On SanPham.maMauSac = MauSac.maMauSac "
				+ "		LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
				+ "		LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
				+ "		LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
				+ "		LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				String mauSac = result.getString("tenMauSac");
				String chatLieu = result.getString("tenChatLieu");
				String kichCo = result.getString("tenKichCo");
				String danhMuc = result.getString("tenDanhMuc");
				String maSP = result.getString("maSanPham");
				String tenSP = result.getString("tenSanPham");
				float giaBan = result.getFloat("giaBan");
				int soLuongTon = result.getInt("soLuongTon");
				int soLuongBan = result.getInt("SoLuongBan");
				
				String maNCC = result.getString("maNhaCungCap");
				String tenNCC = result.getString("tenNhaCungCap");
				String DC = result.getString("diaChi");
				String email = result.getString("email");
				String sdt = result.getString("soDienThoai");
				

				NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, DC, email, sdt);
				
				SanPham sp = new SanPham(maSP, tenSP, giaBan, soLuongTon, 0, soLuongBan, danhMuc, mauSac, chatLieu, kichCo, ncc);
				list.add(sp);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SanPham> getDanhSachSanPhamGuiThongKeSanPham(){
		List<SanPham> list = new ArrayList<SanPham>();
		String sql = "select *"
				+ "		from SanPham"
				+ "		LEFT JOIN MauSac On SanPham.maMauSac = MauSac.maMauSac "
				+ "		LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
				+ "		LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
				+ "		LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
				+ "		LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				String mauSac = result.getString("tenMauSac");
				String chatLieu = result.getString("tenChatLieu");
				String kichCo = result.getString("tenKichCo");
				String danhMuc = result.getString("tenDanhMuc");
				String maSanPham = result.getString("maSanPham");
				String tenSanPham = result.getString("tenSanPham");
				float giaBan = result.getFloat("giaBan");
				int soLuongTon = result.getInt("soLuongTon");
				int soLuongBan = result.getInt("SoLuongBan");
				
				String maNhaCungCap = result.getString("maNhaCungCap");
				String tenNhaCungCap = result.getString("tenNhaCungCap");
				String DC = result.getString("diaChi");
				String email = result.getString("email");
				String sdt = result.getString("soDienThoai");
				

				NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, DC, email, sdt);
				
				SanPham sp = new SanPham(maSanPham, tenSanPham, giaBan, soLuongTon, soLuongBan, danhMuc, mauSac, chatLieu, kichCo, ncc);
				list.add(sp);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public SanPham getSanPhamTheoMa(String maSanPham) {
		SanPham sp = null;
		try {
			String sql = "select * from SanPham"
					+ " inner join DanhMuc on DanhMuc.maDanhMuc = SanPham.maDanhMuc"
					+ " inner join MauSac on MauSac.maMauSac = SanPham.maMauSac"
					+ " inner join ChatLieu on ChatLieu.maChatLieu = SanPham.maChatLieu"
					+ " inner join KichCo on KichCo.maKichCo = SanPham.maKichCo"
					+ " where maSanPham = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maSanPham);
			ResultSet rs = stmt.executeQuery();
			NhaCungCap_DAO dsNCC = new NhaCungCap_DAO();
			if(rs.next()) {
				String maSPTimDuoc = rs.getString("maSanPham");
				String maNhaCungCap = rs.getString("maNhaCungCap");
				NhaCungCap ncc = dsNCC.getListNhaCungCapTheoMa(maNhaCungCap);
				String tenSanPham = rs.getString("tenSanPham");
				float giaBan = rs.getFloat("giaBan");
				float giaNhap = rs.getFloat("giaNhap");
				String urlAvt = rs.getString("urlAvt");
				int soLuongTon = rs.getInt("soLuongTon");
				int soLuongBan = rs.getInt("soLuongBan");
				String tenMauSac = rs.getString("tenMauSac");
				String tenKichCo = rs.getString("tenKichCo");
				String tenDanhMuc = rs.getString("tenDanhMuc");
				String tenChatLieu = rs.getString("tenChatLieu");
				
				sp = new SanPham(maSPTimDuoc, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, urlAvt, tenDanhMuc, tenMauSac, tenChatLieu, tenKichCo, ncc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sp;
	}
	
	public boolean capNhatSoLuongTonCuaSanPham(int soLuongTra, String maSP) {
		int n=0;
		try {
			String sql = "update SanPham set soLuongTon = soLuongTon + ?, soLuongBan = soLuongBan - ? where maSanPham = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, soLuongTra);
			stmt.setInt(2, soLuongTra);
			stmt.setString(3, maSP);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n>0;
	}
	
}


