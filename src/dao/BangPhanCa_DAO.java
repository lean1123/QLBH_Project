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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import connectDB.ConnectDB;
import entity.BangPhanCa;
import entity.CaLam;
import entity.NhanVien;
import utils.Function;

public class BangPhanCa_DAO {

	private List<BangPhanCa> dsBangPhanCa;

	public BangPhanCa_DAO() {
		dsBangPhanCa = new ArrayList<>();
	}

	/**
	 * Lấy tất cả các bản ghi từ bảng BangPhanCa, bao gồm thông tin liên quan từ các bảng CaLam và NhanVien.
	 *
	 * @return Danh sách các đối tượng BangPhanCa chứa thông tin về lịch làm việc được phân công.
	 */

	public List<BangPhanCa> getAllPhanCaDeChamCong() {
		String sql = "SELECT * FROM BangPhanCa "
				+ "INNER JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "INNER JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien "
				+ "WHERE CONVERT(DATE, GETDATE(), 120) >= CONVERT(DATE, ngayBatDau, 120) and "
				+ "CONVERT(DATE, GETDATE(), 120) <= CONVERT(DATE, ngayKetThuc, 120)";
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc.toLocalDate();

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				dsBangPhanCa.add(bpc);
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsBangPhanCa;
	}
	
	public List<BangPhanCa> getBangPhanCaTuHomNay() {
		String sql = "SELECT * FROM BangPhanCa "
				+ "INNER JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "INNER JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien "
				+ " WHERE BangPhanCa.ngayBatDau >= GETDATE()"
				+ " ORDER BY BangPhanCa.ngayBatDau";
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc.toLocalDate();

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				dsBangPhanCa.add(bpc);
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsBangPhanCa;
	}
	
	public List<BangPhanCa> getAllBangPhanCa() {
		String sql = "SELECT * FROM BangPhanCa "
				+ "INNER JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "INNER JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien ";
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc.toLocalDate();

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				dsBangPhanCa.add(bpc);
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsBangPhanCa;
	}

	/**
	 * Tìm kiếm các bản ghi trong bảng BangPhanCa dựa trên ngày được chỉ định.
	 *
	 * @param n Ngày cần tìm kiếm.
	 * @return Danh sách các đối tượng BangPhanCa thỏa mãn điều kiện tìm kiếm.
	 */

	public List<BangPhanCa> timKiemBangPhanCa(LocalDate n) {
		String sqlKieuPhanCaTuyChinh = "SELECT * FROM BangPhanCa JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien WHERE (ngayBatDau >= ? OR ngayKetThuc >= ? ) AND kieuPhanCa = ? ORDER BY BangPhanCa.ngayBatDau ASC";
		String sqlKieuPhanCaCoDinh = "SELECT * "
				+ "FROM BangPhanCa "
				+ "JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien "
				+ "WHERE convert(date, ngayKetThuc, 120) = convert(date, '9999-12-31', 120);";
		List<BangPhanCa> dsTimKiem = new ArrayList<>();

		Date dateNgayLam = Date.valueOf(n);
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();

			PreparedStatement statementKieuPhanCaTuyChinh = con.prepareStatement(sqlKieuPhanCaTuyChinh);
			statementKieuPhanCaTuyChinh.setDate(1, dateNgayLam);
			statementKieuPhanCaTuyChinh.setDate(2, dateNgayLam);
			statementKieuPhanCaTuyChinh.setString(3, utils.Contains.KIEU_PHAN_CA_TUY_CHINH);
			ResultSet result = statementKieuPhanCaTuyChinh.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc.toLocalDate();

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				dsTimKiem.add(bpc);
			}

			PreparedStatement statementKieuPhanCaCoDinh = con.prepareStatement(sqlKieuPhanCaCoDinh);
			result = statementKieuPhanCaCoDinh.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc != null ? ngayKetThuc.toLocalDate() : null;

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				dsTimKiem.add(bpc);
			}

			// Định nghĩa một bộ so sánh (Comparator) dựa trên thuộc tính NgayBatDau
			Comparator<BangPhanCa> ngayBatDauComparator = new Comparator<>() {
				@Override
				public int compare(BangPhanCa itemI, BangPhanCa itemJ) {
					return itemI.getNgayBatDau().compareTo(itemJ.getNgayBatDau());
				}
			};

			// Sử dụng Collections.sort() để sắp xếp danh sách list
			Collections.sort(dsTimKiem, ngayBatDauComparator);

			return dsTimKiem;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<BangPhanCa> getThongTinCheckTrungPhanCa(){
		List<BangPhanCa> dsPhanCa=new ArrayList<>();
		String sql="Select BangPhanCa.maCaLam,"
				+ " BangPhanCa.ngayBatDau,"
				+ " BangPhanCa.ngayKetThuc,"
				+ " BangPhanCa.kieuPhanCa"
				+ " from BangPhanCa"
				+ " inner join CaLam on BangPhanCa.maCaLam=CaLam.maCaLam";
				
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				LocalDate ngayBatDau=result.getDate("ngayBatDau").toLocalDate();
				LocalDate ngayKetThu=result.getDate("ngayKetThuc").toLocalDate();
				String kieuPhanCa=result.getString("kieuPhanCa");
				String maCaLam=result.getString("maCaLam");
				
				CaLam caLam=new CaLam(maCaLam);
				
				BangPhanCa banPhanCa=new BangPhanCa(ngayBatDau, ngayKetThu, kieuPhanCa, caLam);
				dsPhanCa.add(banPhanCa);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPhanCa;
	}
	
	/**
	 * Thêm một bản ghi mới vào bảng BangPhanCa.
	 *
	 * @param bpc Đối tượng BangPhanCa cần thêm.
	 * @return True nếu thêm thành công, false nếu thất bại.
	 */

//	public boolean themBangPhanCa(BangPhanCa bpc) {
//		String sql = "INSERT INTO BangPhanCa(maNhanVien, maCaLam, ngayBatDau, ngayKetThuc, kieuPhanCa) VALUES(?,?,?,?,?)";
//		LocalDate ngayBatDau = bpc.getNgayBatDau();
//		LocalDate ngayKetThuc = bpc.getNgayKetThuc();
//		Date dateNgayBatDau = Date.valueOf(ngayBatDau);
//		Date dateNgayKetThuc;
//		if (bpc.getKieuPhanCa().equalsIgnoreCase(utils.Contains.KIEU_PHAN_CA_CO_DINH)) {
//			dateNgayKetThuc = Date.valueOf("9999-12-31");
//		} else {
//			dateNgayKetThuc = Date.valueOf(ngayKetThuc);
//		}
//
//		try {
//			ConnectDB.getInstance().connect();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = con.prepareStatement(sql);
//			statement.setString(1, bpc.getNhanVien().getMaNhanVien());
//			statement.setString(2, bpc.getCaLam().getMaCaLam());
//			statement.setDate(3, dateNgayBatDau);
//			statement.setDate(4, dateNgayKetThuc);
//			statement.setString(5, bpc.getKieuPhanCa());
//
//			int result = statement.executeUpdate();
//			statement.close();
//
//			return result > 0 ? true : false;
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}

	public boolean themBangPhanCa(BangPhanCa bpc) {
		String sql = "INSERT INTO BangPhanCa(maNhanVien, maCaLam, ngayBatDau, ngayKetThuc, kieuPhanCa) VALUES(?,?,?,?,?)";
		LocalDate ngayBatDau = bpc.getNgayBatDau();
		LocalDate ngayKetThuc = bpc.getNgayKetThuc();
		Date dateNgayBatDau = Date.valueOf(ngayBatDau);
		Date dateNgayKetThuc = Date.valueOf(ngayKetThuc);
//		if (bpc.getKieuPhanCa().equalsIgnoreCase(utils.Contains.KIEU_PHAN_CA_CO_DINH)) {
//			dateNgayKetThuc = Date.valueOf("9999-12-31");
//		} else {
//			dateNgayKetThuc = Date.valueOf(ngayKetThuc);
//		}

		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, bpc.getNhanVien().getMaNhanVien());
			statement.setString(2, bpc.getCaLam().getMaCaLam());
			statement.setDate(3, dateNgayBatDau);
			statement.setDate(4, dateNgayKetThuc);
			statement.setString(5, bpc.getKieuPhanCa());

			int result = statement.executeUpdate();
			statement.close();

			return result > 0 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Kiểm tra và trả về thông tin về bảng phân ca gần nhất của một nhân viên và ca làm cụ thể.
	 *
	 * @param maNV Mã nhân viên cần kiểm tra.
	 * @param maCL Mã ca làm cần kiểm tra.
	 * @return Đối tượng BangPhanCa đại diện cho bảng phân ca gần nhất của nhân viên và ca làm đã cho.
	 */


	public BangPhanCa kiemTraKieuPhanCaGanNhat(String maNV, String maCL) {
		String sql = "SELECT * FROM BangPhanCa "
				+ "JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien  "
				+ "WHERE BangPhanCa.maNhanVien = ? AND BangPhanCa.maCaLam = ? "
				+ "ORDER BY ngayBatDau DESC";
		BangPhanCa bpc = null;
		try {
			ConnectDB.getInstance().connect();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNV);
			statement.setString(2, maCL);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayBatDau");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc.toLocalDate();

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				bpc = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc, result.getString("kieuPhanCa"));
			}
			return bpc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Xóa thông tin bảng phân ca của một nhân viên và một ca làm cụ thể.
	 *
	 * @param maNV Mã nhân viên cần xóa thông tin phân ca.
	 * @param maCL Mã ca làm cần xóa thông tin phân ca.
	 * @return True nếu xóa thành công, ngược lại là false.
	 */

	public boolean xoaBangPhanCa(String maBangPhanCa) {
		String sql = "DELETE FROM BangPhanCa WHERE maBangPhanCa = ?";
		int deleted = 0;

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maBangPhanCa);
			deleted = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleted > 0;
	}

	/**
	 * Tìm kiếm thông tin phân ca dựa trên các điều kiện nhất định.
	 *
	 * @param bpc Thông tin phân ca cần tìm kiếm.
	 * @return Danh sách các bản ghi phân ca thỏa mãn điều kiện tìm kiếm.
	 */

	public List<BangPhanCa> timKiemPhanCa(BangPhanCa bpc) {
		String sql = "SELECT * FROM BangPhanCa "
				+ "JOIN CaLam ON BangPhanCa.maCaLam = CaLam.maCaLam "
				+ "JOIN NhanVien ON NhanVien.maNhanVien = BangPhanCa.maNhanVien "
				+ "WHERE ((BangPhanCa.ngayBatDau >= ? AND BangPhanCa.ngayBatDau <= ?  ) "
				+ "OR (BangPhanCa.ngayKetThuc >= ?  AND BangPhanCa.ngayKetThuc <= ?  ) "
				+ "OR (BangPhanCa.ngayBatDau <= ? AND BangPhanCa.ngayKetThuc >= ?)) ";
		List<BangPhanCa> list = new ArrayList<>();

		if (bpc.getNhanVien() != null) {
			if (!bpc.getNhanVien().getMaNhanVien().equals("")) {
				sql += " AND BangPhanCa.maNhanVien = ? ";
			}
			if (!bpc.getNhanVien().getSoDienThoai().equals("")) {
				sql += " AND NhanVien.soDienThoai = ? ";
			}
			if (!bpc.getNhanVien().getChucVu().equals("")) {
				sql += " AND NhanVien.chucVu = ? ";
			}
			if (!bpc.getNhanVien().getTen().trim().equals("")) {
				sql += " AND NhanVien.tenNhanVien LIKE  ?";
			}
		}
		sql += " ORDER BY BangPhanCa.ngayBatDau ASC";

		System.out.println(sql);
		Connection con = ConnectDB.getConnection();
		try {
			Date ngayBatDauTimKiem = Date.valueOf(bpc.getNgayBatDau());
			Date ngayKetThucTimKiem = Date.valueOf(bpc.getNgayKetThuc());

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setDate(1, ngayBatDauTimKiem);
			statement.setDate(2, ngayKetThucTimKiem);
			statement.setDate(3, ngayBatDauTimKiem);
			statement.setDate(4, ngayKetThucTimKiem);
			statement.setDate(5, ngayBatDauTimKiem);
			statement.setDate(6, ngayKetThucTimKiem);
			if (bpc.getNhanVien() != null) {
				int i = 7;
				if (!bpc.getNhanVien().getMaNhanVien().equals("")) {
					statement.setString(i, bpc.getNhanVien().getMaNhanVien());
					i++;
				}
				if (!bpc.getNhanVien().getSoDienThoai().equals("")) {
					bpc.getNhanVien().getSoDienThoai();
					i++;
				}
				if (!bpc.getNhanVien().getChucVu().equals("")) {
					statement.setString(i, bpc.getNhanVien().getChucVu());
					i++;
				}
				if (!bpc.getNhanVien().getTen().trim().equals("")) {
					statement.setString(i, "%" + bpc.getNhanVien().getTen() + "%");
				}
			}
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				Date ngayBatDau = result.getDate("ngayBatDau");
				Date ngayKetThuc = result.getDate("ngayKetThuc");
				LocalDate localDateBatDau = ngayBatDau.toLocalDate();
				LocalDate localDateKetThuc = ngayKetThuc != null ? ngayKetThuc.toLocalDate() : null;

				CaLam cl = new CaLam(result.getString("maCaLam"), result.getTime("gioBatDau"),
						result.getTime("gioKetThuc"));
				NhanVien nv = new NhanVien(result.getString("maNhanVien"), result.getString("ten"),
						result.getInt("gioiTinh") == 1 ? utils.Contains.NAM : utils.Contains.NU,
						result.getString("chucVu"), result.getString("soDienThoai"), result.getString("diaChi"));
				BangPhanCa bangPhanCa = new BangPhanCa(maBangPhanCa, nv, cl, localDateBatDau, localDateKetThuc,
						result.getString("kieuPhanCa"));
				list.add(bangPhanCa);

			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Cập nhật thông tin về ca làm của một nhân viên trong bảng BangPhanCa.
	 *
	 * @param BPC Đối tượng BangPhanCa chứa thông tin cần cập nhật.
	 * @return True nếu cập nhật thành công, False nếu có lỗi hoặc không có dữ liệu cần cập nhật.
	 */

	public boolean capNhatBangPhanCaLam(BangPhanCa BPC) {
		String sql = "UPDATE BangPhanCa set maCaLam = ?, kieuPhanCa = ?, ngayBatDau = ?, ngayKetThuc = ? where maBangPhanCa = ?";
		int updated = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setDate(3, Date.valueOf(BPC.getNgayBatDau()));
			statement.setDate(4, Date.valueOf(BPC.getNgayKetThuc()));
			statement.setString(2, BPC.getKieuPhanCa());
			statement.setString(1, BPC.getCaLam().getMaCaLam());
			statement.setString(5, BPC.getMaBangPhanCa());
			updated = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updated > 0;
	}
	
	public List<BangPhanCa> timKiemNhanVienTheoCacTieuChi(String maBPC, String maNV, String hoTen, String sdt,
			String chucVu, String kieuPhanCa, String maCL, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		NhanVien_DAO dsnv = new NhanVien_DAO();
		CaLam_DAO dscl = new CaLam_DAO(); 
		String sql = "SELECT * from BangPhanCa"
				+ " inner join NhanVien on NhanVien.maNhanVien = BangPhanCa.maNhanVien"
				+ " where BangPhanCa.maBangPhanCa like ? AND BangPhanCa.maNhanVien LIKE ? AND ten LIKE ? AND soDienThoai LIKE ?"
				+ " AND chucVu LIKE ? And kieuPhanCa Like ? and maCaLam like ? and"
				+ " CONVERT(date, BangPhanCa.ngayBatDau, 120) like ? and"
				+ " CONVERT(date, BangPhanCa.ngayKetThuc, 120) like ?";
		// kết nối db
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, "%" + maBPC + "%");
			statement.setString(2, "%" + maNV + "%");
			statement.setString(3, "%" + hoTen + "%");
			statement.setString(4, "%" + sdt + "%");
			statement.setString(5, "%" + chucVu + "%");
			statement.setString(6, "%" + kieuPhanCa + "%");
			statement.setString(7, "%" + maCL + "%");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String ngayBatDauFormat = "";
			String ngayKetThucFormat = "";
			if(ngayBatDau != null) {
				ngayBatDauFormat = sdf.format(Date.valueOf(ngayBatDau));
			}
			if(ngayKetThuc != null) {
				ngayKetThucFormat = sdf.format(Date.valueOf(ngayKetThuc));
			}
			statement.setString(8, "%"+ngayBatDauFormat+"%");
			statement.setString(9, "%"+ngayKetThucFormat+"%");
			ResultSet result = statement.executeQuery();
			List<BangPhanCa> list = new ArrayList<>();
			while (result.next()) {
				String maBangPhanCa = result.getString("maBangPhanCa");
				String maNhanVien = result.getString("maNhanVien");
				NhanVien nv = dsnv.getNhanVienTheoMa(maNhanVien);
				String maCaLamTimDuoc = result.getString("maCaLam");
				CaLam caLamTimDuoc = dscl.getCaLamTheoMa(maCaLamTimDuoc);
				LocalDate ngayBDTimDuoc = Function.convertToLocalDateViaMilisecond(result.getDate("ngayBatDau"));
				LocalDate ngayKTTimDuoc = Function.convertToLocalDateViaMilisecond(result.getDate("ngayKetThuc"));
				String kieuPhanCaTimDuoc = result.getString("kieuPhanCa");
				list.add(new BangPhanCa(maBangPhanCa, nv, caLamTimDuoc, ngayBDTimDuoc, ngayKTTimDuoc, kieuPhanCaTimDuoc));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public BangPhanCa getBangPhanCaTheoMa(String maBangPhanCa) {
		NhanVien_DAO dsnv = new NhanVien_DAO();
		CaLam_DAO dscl = new CaLam_DAO();
		try {
			String sql = "select * from BangPhanCa where maBangPhanCa = ?";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maBangPhanCa);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				String maBangPhanCaTimDuoc = result.getString("maBangPhanCa");
				String maNhanVien = result.getString("maNhanVien");
				NhanVien nv = dsnv.getNhanVienTheoMa(maNhanVien);
				String maCaLamTimDuoc = result.getString("maCaLam");
				CaLam caLamTimDuoc = dscl.getCaLamTheoMa(maCaLamTimDuoc);
				LocalDate ngayBDTimDuoc = Function.convertToLocalDateViaMilisecond(result.getDate("ngayBatDau"));
				LocalDate ngayKTTimDuoc = Function.convertToLocalDateViaMilisecond(result.getDate("ngayKetThuc"));
				String kieuPhanCaTimDuoc = result.getString("kieuPhanCa");
				return new BangPhanCa(maBangPhanCaTimDuoc, nv, caLamTimDuoc, ngayBDTimDuoc, ngayKTTimDuoc, kieuPhanCaTimDuoc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getMaBangPhanCaVuaTao() {
		String ketQua=null;
		try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maBangPhanCa FROM BangPhanCa ORDER BY maBangPhanCa DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                ketQua = rs.getString("maBangPhanCa");
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
	}
}
