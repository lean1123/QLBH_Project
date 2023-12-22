package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhaCungCap;
import entity.SanPham;

public class ChiTietHoaDon_Dao {
	private List<ChiTietHoaDon> dsCTHoaDon;



	public ChiTietHoaDon_Dao() {
		super();
		dsCTHoaDon = new ArrayList<>();
	}

	/**
	 * Lấy danh sách các sản phẩm từ một hóa đơn.
	 *
	 * @param maHoaDon Mã hóa đơn cần lấy thông tin sản phẩm.
	 * @return Danh sách chi tiết hóa đơn chứa thông tin sản phẩm trong hóa đơn, hoặc danh sách rỗng nếu không có sản phẩm nào.
	 */

	public List<ChiTietHoaDon> getListSanPhamTuHoaDon(String maHoaDon) {
		HoaDon_DAO dsHoaDon = new HoaDon_DAO();
		SanPham_DAO dsSanPham = new SanPham_DAO();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT ChiTietHoaDon.*"
					+ " FROM ChiTietHoaDon"
					+ " inner join SanPham on ChiTietHoaDon.maSanPham = SanPham.maSanPham"
					+ " WHERE ChiTietHoaDon.maHoaDon LIKE ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHoaDon);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maHoaDonTimDuoc = rs.getString(1);
				String maSanPham = rs.getString(2);
				double donGia = rs.getDouble(3);
				int soLuong = rs.getInt(4);
				HoaDon HoaDon = dsHoaDon.getHoaDonTheoMa(maHoaDonTimDuoc);
				SanPham SanPham = dsSanPham.timKiemSanPham(maSanPham);
				ChiTietHoaDon CTHoaDon = new ChiTietHoaDon(HoaDon, SanPham, donGia, soLuong);
				dsCTHoaDon.add(CTHoaDon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsCTHoaDon;
	}


	public boolean themChiTietHoaDon(String maHD,String maSP, float donGia, int soLuong) {
		int n=0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql="insert into ChiTietHoaDon ([maHoaDon],[maSanPham],[donGia],[soLuong]) values (?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			statement.setString(2, maSP);
			statement.setFloat(3, donGia);
			statement.setInt(4, soLuong);

			n = statement.executeUpdate();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;

	}
	
	public boolean capNhatChiTietHoaDon(ChiTietHoaDon cthd) {
		int n=0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql="update ChiTietHoaDon set maHoaDon = ?, maSanPham = ?, donGia = ?, soLuong where maHoaDon = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, cthd.getHoaDon().getMaHoaDon());
			statement.setString(2, cthd.getSanPham().getMaSanPham());
			statement.setDouble(3, cthd.getDonGia());
			statement.setInt(4, cthd.getSoLuong());

			n = statement.executeUpdate();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;

	}

	public boolean capNhatSoLuongTrongHoaDon(int soLuongTra, String maHoaDon, String maSP) {
		int n=0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql="update ChiTietHoaDon set soLuong = soLuong - ? where maHoaDon = ? and maSanPham = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, soLuongTra);
			statement.setString(2, maHoaDon);
			statement.setString(3, maSP);

			n = statement.executeUpdate();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;

	}
	
	public List<SanPham> getDanhSachSanPhamGuiThongKeSanPham2(LocalDate ngayBatDau, LocalDate ngayKetThuc){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String ngayBatDauStr = ngayBatDau.format(formatter);
        String ngayKetThucStr= ngayKetThuc.format(formatter);
        List<SanPham> list = new ArrayList<SanPham>();
		String sql = "SELECT "
				+ "    ChiTietHoaDon.maSanPham,"
				+ "    SanPham.tenSanPham,"
				+ "    SUM(ChiTietHoaDon.soLuong) AS SoLuongBan,"
				+ "    SanPham.soLuongTon,"
				+ "    SanPham.giaBan,"
				+ "    DanhMuc.tenDanhMuc,"
				+ "	   KichCo.tenKichCo,"
				+ "	   ChatLieu.tenChatLieu,"
				+ "	   MauSac.tenMauSac,"
				+ "	   NhaCungCap.tenNhaCungCap,"
				+ "	   NhaCungCap.maNhaCungCap"
				+ "	from ChiTietHoaDon"
				+ " INNER JOIN  SanPham ON ChiTietHoaDon.maSanPham = SanPham.maSanPham"
				+ " INNER JOIN HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon"
				+ " LEFT JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc"
				+ " LEFT JOIN KichCo ON SanPham.maKichCo = KichCo.maKichCo "
				+ " LEFT JOIN ChatLieu ON sanPham.maChatLieu = ChatLieu.maChatLieu "
				+ " LEFT JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap"
				+ " LEFT JOIN MauSac ON SanPham.maMauSac = MauSac.maMauSac "
				+ " WHERE HoaDon.ngayLap >= ? AND HoaDon.ngayLap <= ?"
				+ " GROUP BY ChiTietHoaDon.maSanPham, SanPham.tenSanPham, SanPham.soLuongTon, SanPham.giaBan, DanhMuc.tenDanhMuc, MauSac.tenMauSac,"
				+ "	ChatLieu.tenChatLieu, KichCo.tenKichCo, NhaCungCap.tenNhaCungCap, NhaCungCap.maNhaCungCap ";
				
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, ngayBatDauStr);
			statement.setString(2, ngayKetThucStr);
			
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
				

				NhaCungCap ncc= new NhaCungCap(maNhaCungCap,tenNhaCungCap);
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
}
