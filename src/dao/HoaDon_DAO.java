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
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import utils.Function;

public class HoaDon_DAO {
	private List<HoaDon> dsHoaDon;

	public HoaDon_DAO() {
		super();
		this.dsHoaDon = new ArrayList<>();
	}

	/**
	 * Lấy danh sách các hóa đơn từ cơ sở dữ liệu.
	 *
	 * @return Danh sách các hóa đơn có trong cơ sở dữ liệu.
	 */

	public List<HoaDon> getListHoaDon() {
		NhanVien_DAO dsnv = new NhanVien_DAO();
		KhachHang_DAO dskh = new KhachHang_DAO();
		ChuongTrinhKhuyenMai_DAO dsKM = new ChuongTrinhKhuyenMai_DAO();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "Select * from HoaDon";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maHD = rs.getString(1);
				String maKH = rs.getString(2);
				String maNV = rs.getString(3);
				Date ngayLap = rs.getDate(4);
				String maKM = rs.getString(5);
				NhanVien nv = dsnv.getNhanVienTheoMa(maNV);
				KhachHang kh = dskh.getKhachHangTheoMa(maKH);
				ChuongTrinhKhuyenMai ctkm = dsKM.getKhuyenMaiTheoMa(maKM);
				HoaDon HD = new HoaDon(maHD, nv, kh, ngayLap, ctkm);
				dsHoaDon.add(HD);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHoaDon;
	}

	/**
	 * Tìm kiếm hóa đơn trong cơ sở dữ liệu dựa trên các tiêu chí tìm kiếm.
	 *
	 * @param maHD    Mã hóa đơn cần tìm kiếm (có thể là một phần của mã).
	 * @param hoTenNV Họ tên của nhân viên lập hóa đơn (có thể là một phần của họ
	 *                tên).
	 * @param tenKH   Tên của khách hàng (có thể là một phần của tên).
	 * @param sdtKH   Số điện thoại của khách hàng (có thể là một phần của số điện
	 *                thoại).
	 * @param ngayLap Ngày lập hóa đơn.
	 * @return Danh sách hóa đơn thỏa mãn các tiêu chí tìm kiếm.
	 */

	public List<HoaDon> timKiemHoaDon(String maHD, String hoTenNV, String tenKH, String sdtKH, Date ngayLap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		KhachHang_DAO dskh = new KhachHang_DAO();
		NhanVien_DAO dsnv = new NhanVien_DAO();
		ChuongTrinhKhuyenMai_DAO dskm = new ChuongTrinhKhuyenMai_DAO();
		List<HoaDon> dstk = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select HoaDon.*, NhanVien.*, KhachHang.*" + " FROM HoaDon"
					+ " INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang"
					+ " INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien"
					+ " where HoaDon.maHoaDon like ? and NhanVien.ten like ? and KhachHang.ten like ? and KhachHang.soDienThoai like ?"
					+ " and CONVERT(DATE, HoaDon.ngayLap, 120) like ?";

			String ngayLapFormat = "";
			if (ngayLap != null) {
				ngayLapFormat = sdf.format(ngayLap);
			}

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + maHD + "%");
			stmt.setString(2, "%" + hoTenNV + "%");
			stmt.setString(3, "%" + tenKH + "%");
			stmt.setString(4, "%" + sdtKH + "%");
			stmt.setString(5, "%" + ngayLapFormat + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHDTimDuoc = rs.getString(1);
				String maNV = rs.getString(3);
				String maKhachHang = rs.getString(2);
				Date ngayLapHD = rs.getDate(4);
				String maKM = rs.getString(5);
				NhanVien nv = dsnv.getNhanVienTheoMa(maNV);
				KhachHang kh = dskh.getKhachHangTheoMa(maKhachHang);
				ChuongTrinhKhuyenMai ctkm = dskm.getKhuyenMaiTheoMa(maKM);

				// Thêm hóa đơn vào danh sách kết quả
				dstk.add(new HoaDon(maHDTimDuoc, nv, kh, ngayLapHD, ctkm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dstk;
	}

	/**
	 * Tính giá được khuyến theo chương trình khuyến mãi của hóa mỗi hóa đơn.
	 *
	 * @param maHD Mã hóa đơn cần tính giá khuyến mãi.
	 * @return Giá được giảm theo chương trình khuyến mãi.
	 */

	public double tinhGiaKhuyenMaiCuaHoaDon(String maHD) {
		double tongTien = tinhTongTien(maHD);
		double soPhanTramGiamGia = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select ChuongTrinhKhuyenMai.giaGiam" + " from HoaDon"
					+ " inner join ChuongTrinhKhuyenMai on ChuongTrinhKhuyenMai.maKhuyenMai = HoaDon.maKhuyenMai"
					+ " where HoaDon.maHoaDon = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				soPhanTramGiamGia = rs.getFloat(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tongTien * soPhanTramGiamGia;
	}

	/**
	 * Tính tổng tiền của một hóa đơn dựa trên các chi tiết hóa đơn.
	 *
	 * @param maHD Mã hóa đơn cần tính tổng tiền.
	 * @return Tổng tiền của hóa đơn.
	 */

	public double tinhTongTien(String maHD) {
		double tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT ChiTietHoaDon.soLuong, ChiTietHoaDon.donGia" + " FROM ChiTietHoaDon"
					+ " INNER JOIN HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon" + " WHERE HoaDon.maHoaDon = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				float donGia = rs.getFloat(1);
				int soLuong = rs.getInt(2);

				tongTien += donGia * soLuong;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tongTien;
	}

	/**
	 * Lấy thông tin của một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param maHD Mã hóa đơn cần tìm kiếm.
	 * @return Đối tượng HoaDon chứa thông tin của hóa đơn tìm kiếm, hoặc null nếu
	 *         không tìm thấy.
	 */

	public HoaDon getHoaDonTheoMa(String maHD) {
		NhanVien_DAO dsnv = new NhanVien_DAO();
		KhachHang_DAO dskh = new KhachHang_DAO();
		ChuongTrinhKhuyenMai_DAO dsKM = new ChuongTrinhKhuyenMai_DAO();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from HoaDon where maHoaDon = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHoaDonTimDuoc = rs.getString(1);
				String maKH = rs.getString(2);
				String maNV = rs.getString(3);
				Date ngayLap = rs.getDate(4);
				String maKM = rs.getString(5);
				NhanVien nv = dsnv.getNhanVienTheoMa(maNV);
				KhachHang kh = dskh.getKhachHangTheoMa(maKH);
				ChuongTrinhKhuyenMai ctkm = dsKM.getKhuyenMaiTheoMa(maKM);
				return new HoaDon(maHoaDonTimDuoc, nv, kh, ngayLap, ctkm);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Lấy thông tin của khách hàng dựa trên mã hóa đơn.
	 *
	 * @param maHD Mã hóa đơn cần tìm kiếm thông tin khách hàng.
	 * @return Đối tượng KhachHang chứa thông tin của khách hàng tìm kiếm, hoặc null
	 *         nếu không tìm thấy.
	 */

	public KhachHang getKhachHangTheoHoaDon(String maHD) {
		KhachHang_DAO dskh = new KhachHang_DAO();
		KhachHang KH = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select HoaDon.maKhachHang" + " from HoaDon" + " where HoaDon.maHoaDon like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maKH = rs.getString(1);
				KH = dskh.getKhachHangTheoMa(maKH);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return KH;
	}

	/**
	 * Xây dựng lệnh điều kiện SQL dựa trên loại thống kê khác nhau.
	 *
	 * @param loaiThongKe Loại thống kê cần xây dựng điều kiện (Ngày hôm nay, Tháng
	 *                    này, Năm hiện tại).
	 * @return Chuỗi điều kiện SQL dựa trên loại thống kê được chỉ định.
	 */

	public String getLenhDieuKienTheoLoaiThongKeKhac(String loaiThongKe) {
		String condition = "";
		if (loaiThongKe.equalsIgnoreCase("Ngày hôm nay")) {
			condition += " WHERE CONVERT(DATE, HoaDon.ngayLap) = CONVERT(DATE, GETDATE() , 105) ";
		} else if (loaiThongKe.equalsIgnoreCase("Tháng hiện tại")) {
			condition += " WHERE DATEPART(MONTH, HoaDon.ngayLap) = DATEPART(MONTH, GETDATE()) ";
		} else if (loaiThongKe.equalsIgnoreCase("Năm hiện tại")) {
			condition += " WHERE DATEPART(YEAR, HoaDon.ngayLap) = DATEPART(YEAR, GETDATE()) ";
		}
		return condition;
	}

	/**
	 * Xây dựng điều kiện SQL dựa trên thống kê tùy chỉnh với khoảng ngày bắt đầu và
	 * kết thúc.
	 *
	 * @param ngayBatDau  Ngày bắt đầu của khoảng thời gian thống kê.
	 * @param ngayKetThuc Ngày kết thúc của khoảng thời gian thống kê.
	 * @return Chuỗi điều kiện SQL dựa trên thống kê tùy chỉnh.
	 */

	public String getLenhDieuKienTheoThongKeTuyChinh(Date ngayBatDau, Date ngayKetThuc) {
		String condition = "";
		SimpleDateFormat simpleDateFortmat = new SimpleDateFormat("dd-MM-yyyy");
		if (ngayBatDau != null && ngayKetThuc != null) {
			String ngayBatDauStr = simpleDateFortmat.format(ngayBatDau);
			String ngayKetThucStr = simpleDateFortmat.format(ngayKetThuc);

			// Xây dựng lệnh điều kiện SQL với khoảng thời gian từ ngày bắt đầu đến ngày kết
			// thúc
			condition += " WHERE CONVERT(DATE, HoaDon.ngayLap) >= CONVERT(DATE, '" + ngayBatDauStr + "' , 105) "
					+ " AND CONVERT(DATE, HoaDon.ngayLap) <= CONVERT(DATE, '" + ngayKetThucStr + "', 105) ";
		}
		return condition;
	}

	/**
	 * Lấy danh sách hóa đơn dựa trên loại thống kê khác nhau.
	 *
	 * @param loaiThongKe Loại thống kê cần áp dụng (Ngày hôm nay, Tháng hiện tại, Năm
	 *                    này, ...).
	 * @return Danh sách hóa đơn theo loại thống kê được chỉ định.
	 */

	public List<HoaDon> getDanhSachHoaDonTheoThongKeKhac(String loaiThongKe) {
		List<HoaDon> dsHD = new ArrayList<>();
		NhanVien_DAO dsnv = new NhanVien_DAO();
		KhachHang_DAO dskh = new KhachHang_DAO();
		ChuongTrinhKhuyenMai_DAO dskm = new ChuongTrinhKhuyenMai_DAO();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select *" + " FROM HoaDon" + getLenhDieuKienTheoLoaiThongKeKhac(loaiThongKe);
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNV = rs.getString(3);
				Date ngayLapHD = rs.getDate(4);
				String maKM = rs.getString(5);
				NhanVien nv = dsnv.getNhanVienTheoMa(maNV);
				KhachHang kh = dskh.getKhachHangTheoMa(maKhachHang);
				ChuongTrinhKhuyenMai ctkm = dskm.getKhuyenMaiTheoMa(maKM);

				dsHD.add(new HoaDon(maHoaDon, nv, kh, ngayLapHD, ctkm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsHD;
	}

	/**
	 * Lấy danh sách hóa đơn dựa trên thống kê tùy chỉnh với khoảng ngày bắt đầu và
	 * kết thúc.
	 *
	 * @param ngayBatDau  Ngày bắt đầu của khoảng thời gian thống kê.
	 * @param ngayKetThuc Ngày kết thúc của khoảng thời gian thống kê.
	 * @return Danh sách hóa đơn theo thống kê tùy chỉnh.
	 */

	public List<HoaDon> getDanhSachHoaDonTheoThongKeTuyChinh(Date ngayBatDau, Date ngayKetThuc) {
		List<HoaDon> dsHD = new ArrayList<>();
		NhanVien_DAO dsnv = new NhanVien_DAO();
		KhachHang_DAO dskh = new KhachHang_DAO();
		ChuongTrinhKhuyenMai_DAO dskm = new ChuongTrinhKhuyenMai_DAO();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select *" + " FROM HoaDon" + getLenhDieuKienTheoThongKeTuyChinh(ngayBatDau, ngayKetThuc);
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNV = rs.getString(3);
				Date ngayLapHD = rs.getDate(4);
				String maKM = rs.getString(5);
				NhanVien nv = dsnv.getNhanVienTheoMa(maNV);
				KhachHang kh = dskh.getKhachHangTheoMa(maKhachHang);
				ChuongTrinhKhuyenMai ctkm = dskm.getKhuyenMaiTheoMa(maKM);

				dsHD.add(new HoaDon(maHoaDon, nv, kh, ngayLapHD, ctkm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsHD;
	}

	/**
	 * Tính tổng số tiền chi của khách hàng dựa trên thời gian của loại thống kê
	 * khác nhau (ngày hôm nay, Tháng hiện tại, Năm hiện tại).
	 *
	 * @param maKH        Mã khách hàng cần tính chi tiêu.
	 * @param loaiThongKe Loại thống kê cần áp dụng (Ngày hôm nay, Tháng hiện tại, Năm
	 *                    này).
	 * @return Tổng số tiền chi của khách hàng trong thời gian ngày hôm nay, hoặc
	 *         Tháng hiện tại và Năm hiện tại.
	 */

	public double tinhTongSoTienChiCuaKhachHangTheoThongKeKhac(String maKH, String loaiThongKe) {

		double tongTien = 0;
		double tienKhuyenMai = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select HoaDon.maHoaDon" + " from HoaDon" + getLenhDieuKienTheoLoaiThongKeKhac(loaiThongKe)
					+ " and HoaDon.maKhachHang like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				tongTien += tinhTongTien(maHD);
				tienKhuyenMai += tinhGiaKhuyenMaiCuaHoaDon(maHD);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tongTien - tienKhuyenMai;
	}

	/**
	 * Tính tổng số tiền chi của khách hàng dựa trên thống kê tùy chỉnh với khoảng
	 * thời gian ngày bắt đầu và kết thúc.
	 *
	 * @param maKH        Mã khách hàng cần thống kê chi phí.
	 * @param ngayBatDau  Ngày bắt đầu của khoảng thời gian thống kê.
	 * @param ngayKetThuc Ngày kết thúc của khoảng thời gian thống kê.
	 * @return Tổng số tiền chi của khách hàng trong thời gian thống kê tùy chỉnh.
	 */

	public double tinhTongSoTienChiCuaKhachHangTheoThongKeTuyChinh(String maKH, Date ngayBatDau, Date ngayKetThuc) {

		double tongTien = 0;
		double tienKhuyenMai = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select HoaDon.maHoaDon" + " from HoaDon"
					+ getLenhDieuKienTheoThongKeTuyChinh(ngayBatDau, ngayKetThuc) + " and HoaDon.maKhachHang like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				tongTien += tinhTongTien(maHD);
				tienKhuyenMai += tinhGiaKhuyenMaiCuaHoaDon(maHD);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tongTien - tienKhuyenMai;
	}

	/**
	 * Tính số hóa đơn của khách hàng trong thời gian ngày hôm nay hay Tháng hiện tại,
	 * Năm hiện tại.
	 *
	 * @param maKH        mã khách hàng cần tính số hóa đơn
	 * @param loaiThongKe thời gian thống kê ngày hôm này, Năm hiện tại, ngày hôm này
	 * @return số hóa đơn của khách hàng trong thời gian thuộc loại thống kê đã
	 *         chọn.
	 */

	public int getSoHoaDonTheoKhachHangTheoThongKeKhac(String maKH, String loaiThongKe) {
		int soHD = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select HoaDon.maHoaDon, HoaDon.maKhachHang" + " from HoaDon"
					+ getLenhDieuKienTheoLoaiThongKeKhac(loaiThongKe) + " and HoaDon.maKhachHang = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				soHD++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return soHD;
	}

	/**
	 * Tính số hóa đơn của khách hàng trong thời gian từ ngày bắt đầu đến ngày kết
	 * thúc
	 *
	 * @param maKH        mã khách hàng cần tính số hóa đơn
	 * @param ngayBatDau  ngày bắt đầu
	 * @param ngayKetThuc ngày kết thúc
	 * @return số hóa đơn của khách hàng
	 */

	public int getSoHoaDonTheoKhachHangTheoThongKeTuyChinh(String maKH, Date ngayBatDau, Date ngayKetThuc) {
		int soHD = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select HoaDon.maHoaDon, HoaDon.maKhachHang" + " from HoaDon"
					+ getLenhDieuKienTheoThongKeTuyChinh(ngayBatDau, ngayKetThuc) + " and HoaDon.maKhachHang = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				soHD++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return soHD;
	}

	/**
	 * Tính doanh thu của cửa hàng dựa trên tháng cụ thể trong năm hiện tại.
	 *
	 * @param month Tháng cần thống kê doanh thu.
	 * @return Tổng doanh thu của cửa hàng trong tháng đã chọn.
	 */

	public double tinhDoanhThuTheoThang(int month) {
		double tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select *" + " from HoaDon"
					+ " where datepart(YEAR, HoaDon.ngayLap) = datepart(YEAR, GETDATE())"
					+ " and DATEPART(month, HoaDon.ngayLap) = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, month);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				tongTien += tinhTongTien(maHD) - tinhGiaKhuyenMaiCuaHoaDon(maHD);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tongTien;
	}

	public boolean themHoaDon(HoaDon hoaDon, String maKM) {
		int n=0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql="insert into HoaDon ([maKhachHang],[maNhanVien],[ngayLap],[maKhuyenMai]) values (?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, hoaDon.getKhachHang().getMaKhachHang());
			statement.setString(2, hoaDon.getNhanVien().getMaNhanVien());
//			java.util.Date utilDate = new java.util.Date();
//	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        statement.setDate(3, java.sql.Date.valueOf(Function.convertToLocalDateViaMilisecond(hoaDon.getNgayLap())));
			statement.setString(4, maKM);

			n = statement.executeUpdate();
			statement.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return n>0;

		}

		public String layDuLieuCotMaHD() {
			String ketQua=null;
			try {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql="select top 1 maHoaDon from HoaDon order by maHoaDon DESC";
				Statement statement = con.createStatement();
		        ResultSet rs = statement.executeQuery(sql);

		        while(rs.next()) {
		        	ketQua=rs.getString("maHoaDon");
		        }
		        statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return ketQua;
		}


}
