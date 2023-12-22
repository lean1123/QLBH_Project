package gui_KhachHang;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietDonTraHang_DAO;
import dao.ChiTietHoaDon_Dao;
import dao.DonTraHang_DAO;
import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import dao.SanPham_DAO;
import dao.TaiKhoan_DAO;
import entity.ChiTietDonTraHang;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.SanPham;
import utils.Contains;
import utils.Function;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DonTraHang extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenNhanVien;
	private JTextField txtTenKhachHang;
	private JTextField txtTongTien;
	private JTextField txtTienTraLai;
	private JTextField txtTongTienMoi;
	private JTextField txtSoLuongTra;
	private JTextField txtmaHoaDon;
	private JDateChooser jdcNgayLap, jdcNgayTra;
	private JButton btnTimKiem, btnXoaTrang, btnThanhToan, btnHonTr, btnLamMoi;
	private JCheckBox checkBoxXuatHoaDon;
	private JTextArea txaLyDoTraHang;
	private JScrollPane scrHoaDon, scrCTHD;
	private JTable tblHoaDon, tblChiTietHoaDon, tblSanPhamTra;
	private DefaultTableModel dfHoaDon, dfChiTietHoaDon, dfSanPhamTra;
	private HoaDon_DAO dsHD;
	private ChiTietHoaDon_Dao dsCTHD;
	private JTextField txtSDTKhachHang;
	private JLabel lblSnPhmTr;
	private JScrollPane scrSanPhamTra;
	private NhanVien_DAO dsNV;
	private DonTraHang_DAO dsDonTraHang;
	private ChiTietDonTraHang_DAO dsCTDonTraHang;
	private SanPham_DAO dsSanPham;

	public DonTraHang() {

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dsHD = new HoaDon_DAO();
		dsCTHD = new ChiTietHoaDon_Dao();
		dsNV = new NhanVien_DAO();
		dsDonTraHang = new DonTraHang_DAO();
		dsCTDonTraHang = new ChiTietDonTraHang_DAO();
		dsSanPham = new SanPham_DAO();

		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(null);

		JPanel pnTimKiemDatHang = new JPanel();
		pnTimKiemDatHang.setBounds(10, 10, 603, 188);
		pnTimKiemDatHang.setLayout(null);
		pnTimKiemDatHang.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnTimKiemDatHang.setBackground(Color.WHITE);
		add(pnTimKiemDatHang);

		JLabel lblTmKimHa = new JLabel("Tìm Kiếm Hóa Đơn");
		lblTmKimHa.setHorizontalAlignment(SwingConstants.CENTER);
		lblTmKimHa.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTmKimHa.setBounds(214, 10, 208, 34);
		pnTimKiemDatHang.add(lblTmKimHa);

		JLabel lblMaDonHang = new JLabel("Mã hóa đơn:");
		lblMaDonHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaDonHang.setBounds(10, 54, 102, 34);
		pnTimKiemDatHang.add(lblMaDonHang);

		JLabel lblTenNhanVien = new JLabel("Tên nhân viên:");
		lblTenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenNhanVien.setBounds(10, 98, 118, 34);
		pnTimKiemDatHang.add(lblTenNhanVien);

		JLabel lblTenKhachHang = new JLabel("Tên khách hàng:");
		lblTenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenKhachHang.setBounds(295, 54, 137, 34);
		pnTimKiemDatHang.add(lblTenKhachHang);

		JLabel lblNgayDat = new JLabel("Ngày đặt");
		lblNgayDat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNgayDat.setBounds(295, 98, 88, 34);
		pnTimKiemDatHang.add(lblNgayDat);

		txtTenNhanVien = new JTextField();
		txtTenNhanVien.setColumns(10);
		txtTenNhanVien.setBounds(131, 103, 154, 29);
		pnTimKiemDatHang.add(txtTenNhanVien);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setColumns(10);
		txtTenKhachHang.setBounds(442, 59, 148, 29);
		pnTimKiemDatHang.add(txtTenKhachHang);

		jdcNgayLap = new JDateChooser();
		jdcNgayLap.setBounds(427, 98, 163, 29);
		pnTimKiemDatHang.add(jdcNgayLap);

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(488, 144, 102, 34);
		pnTimKiemDatHang.add(btnTimKiem);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBounds(378, 144, 100, 34);
		pnTimKiemDatHang.add(btnXoaTrang);

		txtmaHoaDon = new JTextField();
		txtmaHoaDon.setColumns(10);
		txtmaHoaDon.setBounds(122, 54, 154, 29);
		pnTimKiemDatHang.add(txtmaHoaDon);

		JLabel lblSdtKhachHang = new JLabel("SDT Khách Hàng:");
		lblSdtKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSdtKhachHang.setBounds(10, 144, 148, 34);
		pnTimKiemDatHang.add(lblSdtKhachHang);

		txtSDTKhachHang = new JTextField();
		txtSDTKhachHang.setColumns(10);
		txtSDTKhachHang.setBounds(168, 147, 198, 29);
		pnTimKiemDatHang.add(txtSDTKhachHang);

		JPanel pnThanhToan = new JPanel();
		pnThanhToan.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnThanhToan.setBackground(SystemColor.control);
		pnThanhToan.setBounds(623, 10, 685, 222);
		add(pnThanhToan);
		pnThanhToan.setLayout(null);

		JLabel lblThanhToan = new JLabel("Thanh toán");
		lblThanhToan.setBounds(288, 10, 105, 34);
		lblThanhToan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pnThanhToan.add(lblThanhToan);

		JLabel lblTongTien = new JLabel("Tổng tiền:");
		lblTongTien.setBounds(377, 50, 118, 34);
		lblTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		pnThanhToan.add(lblTongTien);

		JLabel lblTienTraLai = new JLabel("Tiền trả lại:");
		lblTienTraLai.setBounds(377, 134, 105, 34);
		lblTienTraLai.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		pnThanhToan.add(lblTienTraLai);

		btnThanhToan = new JButton("Thanh toán");
		btnThanhToan.setBounds(499, 178, 134, 34);
		pnThanhToan.add(btnThanhToan);

		checkBoxXuatHoaDon = new JCheckBox("Xuất hóa đơn");
		checkBoxXuatHoaDon.setBounds(377, 182, 105, 26);
		pnThanhToan.add(checkBoxXuatHoaDon);

		txtTongTien = new JTextField();
		txtTongTien.setBackground(SystemColor.controlLtHighlight);
		txtTongTien.setBounds(505, 55, 120, 29);
		txtTongTien.setEditable(false);
		txtTongTien.setColumns(10);
		pnThanhToan.add(txtTongTien);

		JLabel lblDonVi_1 = new JLabel("VNĐ");
		lblDonVi_1.setBounds(630, 55, 45, 26);
		lblDonVi_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnThanhToan.add(lblDonVi_1);

		JLabel lblDonVi_4 = new JLabel("VNĐ");
		lblDonVi_4.setBounds(630, 139, 45, 26);
		lblDonVi_4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnThanhToan.add(lblDonVi_4);

		txtTienTraLai = new JTextField();
		txtTienTraLai.setBackground(SystemColor.controlLtHighlight);
		txtTienTraLai.setBounds(505, 139, 120, 29);
		txtTienTraLai.setEditable(false);
		txtTienTraLai.setColumns(10);
		pnThanhToan.add(txtTienTraLai);

		JLabel lblTngTinMi = new JLabel("Tổng tiền mới:");
		lblTngTinMi.setBounds(377, 94, 118, 34);
		lblTngTinMi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		pnThanhToan.add(lblTngTinMi);

		txtTongTienMoi = new JTextField();
		txtTongTienMoi.setBackground(SystemColor.controlLtHighlight);
		txtTongTienMoi.setBounds(505, 99, 120, 29);
		txtTongTienMoi.setEditable(false);
		txtTongTienMoi.setColumns(10);
		pnThanhToan.add(txtTongTienMoi);

		JLabel lblDonVi_1_1 = new JLabel("VNĐ");
		lblDonVi_1_1.setBounds(630, 99, 45, 26);
		lblDonVi_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnThanhToan.add(lblDonVi_1_1);

		JLabel lblLDo = new JLabel("Lý do:");
		lblLDo.setBounds(10, 54, 62, 34);
		lblLDo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		pnThanhToan.add(lblLDo);

		txaLyDoTraHang = new JTextArea();
		txaLyDoTraHang.setWrapStyleWord(true);
		txaLyDoTraHang.setBackground(new Color(255, 255, 255));
		txaLyDoTraHang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txaLyDoTraHang.setLineWrap(true);
		txaLyDoTraHang.setBounds(82, 61, 274, 61);
		pnThanhToan.add(txaLyDoTraHang);

		JLabel lblNgyt = new JLabel("Ngày trả:");
		lblNgyt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNgyt.setBounds(10, 132, 88, 34);
		pnThanhToan.add(lblNgyt);

		jdcNgayTra = new JDateChooser();
		jdcNgayTra.setBounds(108, 132, 163, 29);
		pnThanhToan.add(jdcNgayTra);
		
		JLabel lblMess = new JLabel("Tổng tiền hiển thị chưa bao gồm số tiền khuyến mãi");
		lblMess.setForeground(Color.BLUE);
		lblMess.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMess.setFont(new Font("Times New Roman", Font.ITALIC, 10));
		lblMess.setBounds(405, 31, 255, 13);
		pnThanhToan.add(lblMess);

		JLabel lblTieuDe1 = new JLabel("Danh Sách Hóa Đơn");
		lblTieuDe1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTieuDe1.setBounds(10, 208, 150, 20);
		add(lblTieuDe1);

		String cols[] = { "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Số điện thoại", "Ngày lập hóa đơn",
				"Tổng tiền ban đầu", "Số phần trăm giảm giá", "Tồng tiền" };
		dfHoaDon = new DefaultTableModel(cols, 0);
		tblHoaDon = new JTable(dfHoaDon);

		scrHoaDon = new JScrollPane(tblHoaDon, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrHoaDon.setBounds(10, 242, 1298, 147);
		add(scrHoaDon);

		JLabel lblTieuDe2 = new JLabel("Chi Tiết Hóa Đơn");
		lblTieuDe2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTieuDe2.setBounds(10, 399, 150, 20);
		add(lblTieuDe2);

		String colsCTHD[] = { "Mã sản phẩm", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Size", "Giá bán", "Số Lượng",
				"Thành tiền" };

		dfChiTietHoaDon = new DefaultTableModel(colsCTHD, 0);
		tblChiTietHoaDon = new JTable(dfChiTietHoaDon);

		scrCTHD = new JScrollPane(tblChiTietHoaDon, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrCTHD.setBounds(10, 445, 1298, 147);
		add(scrCTHD);

		JLabel lblSLngHon = new JLabel("Số lượng hoàn trả:");
		lblSLngHon.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblSLngHon.setBounds(683, 401, 136, 34);
		add(lblSLngHon);

		txtSoLuongTra = new JTextField();
		txtSoLuongTra.setColumns(10);
		txtSoLuongTra.setBounds(829, 399, 120, 36);
		add(txtSoLuongTra);

		btnHonTr = new JButton("Hoàn trả");
		btnHonTr.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnHonTr.setBounds(978, 399, 134, 34);
		add(btnHonTr);

		tblHoaDon.addMouseListener(this);
		tblChiTietHoaDon.addMouseListener(this);
		btnTimKiem.addActionListener(this);
		btnHonTr.addActionListener(this);
		btnThanhToan.addActionListener(this);

		lblSnPhmTr = new JLabel("Sản phẩm trả");
		lblSnPhmTr.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSnPhmTr.setBounds(10, 602, 150, 20);
		add(lblSnPhmTr);

		String colsCTDTH[] = { "Mã sản phẩm", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Size", "Giá bán", "Số Lượng trả",
				"Thành tiền" };

		dfSanPhamTra = new DefaultTableModel(colsCTDTH, 0);
		tblSanPhamTra = new JTable(dfSanPhamTra);

		scrSanPhamTra = new JScrollPane(tblSanPhamTra, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrSanPhamTra.setBounds(10, 633, 1298, 162);
		add(scrSanPhamTra);
		
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnLamMoi.setBounds(1012, 598, 100, 30);
		add(btnLamMoi);
		
		btnLamMoi.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		capNhatBangHoaDon();
	}

	public void xoaTrang() {
		txtmaHoaDon.setText("");
		txtTenKhachHang.setText("");
		txtTenNhanVien.setText("");
		jdcNgayLap.setDate(new Date());
		dfHoaDon.getDataVector().removeAllElements();
		loaiBoDuLieuCuaBangSanPham();
		capNhatBangHoaDon();
	}

	public void capNhatBangHoaDon() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		HoaDon_DAO ds = new HoaDon_DAO();
		List<HoaDon> listTimDuoc = ds.getListHoaDon();
		if (!listTimDuoc.isEmpty()) {
			for (HoaDon hd : listTimDuoc) {
				Object row[] = { hd.getMaHoaDon(), hd.getNhanVien().getTen(), hd.getKhachHang().getHoTen(),
						hd.getKhachHang().getSoDienThoai(), hd.getNgayLap(),
						currencyFormat.format(ds.tinhTongTien(hd.getMaHoaDon())),
						hd.getChuongTrinhKhuyenMai().getGiaGiam(), currencyFormat.format(
								ds.tinhTongTien(hd.getMaHoaDon()) - ds.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())) };
				dfHoaDon.addRow(row);
			}
			dfHoaDon.fireTableDataChanged();
		}
	}

	public void capNhatDuLieuBangSanPham(String maHoaDon) {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		ChiTietHoaDon_Dao ds = new ChiTietHoaDon_Dao();
		for (ChiTietHoaDon CTHoaDon : ds.getListSanPhamTuHoaDon(maHoaDon)) {
			Object row[] = { CTHoaDon.getSanPham().getMaSanPham(), CTHoaDon.getSanPham().getTenSanPham(),
					CTHoaDon.getSanPham().getChatLieu(), CTHoaDon.getSanPham().getMauSac(),
					CTHoaDon.getSanPham().getKichCo(), currencyFormat.format(CTHoaDon.getDonGia()),
					CTHoaDon.getSoLuong(), currencyFormat.format(CTHoaDon.getDonGia() * CTHoaDon.getSoLuong()) };
			dfChiTietHoaDon.addRow(row);
		}
		dfChiTietHoaDon.fireTableDataChanged();
	}

	public void capNhatChoCacTruongNhapDuLieu(int row) {
		txtmaHoaDon.setText(tblHoaDon.getValueAt(row, 0).toString());
		txtTenKhachHang.setText(tblHoaDon.getValueAt(row, 2).toString());
		txtTenNhanVien.setText(tblHoaDon.getValueAt(row, 1).toString());
		jdcNgayLap.setDate((Date) tblHoaDon.getValueAt(row, 4));
		txtSDTKhachHang.setText(tblHoaDon.getValueAt(row, 3).toString());
		txtTongTien.setText(tblHoaDon.getValueAt(row, 5).toString());
	}

	private void loaiBoDuLieuCuaBangSanPham() {
		int rowCount = dfChiTietHoaDon.getRowCount();
		if (rowCount > 0) {
			dfChiTietHoaDon.getDataVector().removeAllElements(); // Xóa tất cả dữ liệu trong mô hình
			dfChiTietHoaDon.fireTableDataChanged(); // Thông báo cho bảng cập nhật dữ liệu
		}
	}

	private void timKiemHoaDon() {
		HoaDon_DAO ds = new HoaDon_DAO();
		String ma = txtmaHoaDon.getText().toString().trim();
		String tenKH = txtTenKhachHang.getText().toString().trim();
		String tenNV = txtTenNhanVien.getText().toString().trim();
		Date ngayLap = jdcNgayLap.getDate();
		String sdt = txtSDTKhachHang.getText().toString().trim();
		List<HoaDon> dstk = ds.timKiemHoaDon(ma, tenNV, tenKH, sdt, ngayLap);

		if (dstk.size() > 0) {
			DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
			dfHoaDon.getDataVector().removeAllElements();
			dfChiTietHoaDon.getDataVector().removeAllElements();
			for (HoaDon hd : dstk) {
				Object row[] = { hd.getMaHoaDon(), hd.getNhanVien().getTen(), hd.getKhachHang().getHoTen(),
						hd.getKhachHang().getSoDienThoai(), hd.getNgayLap(),
						currencyFormat.format(ds.tinhTongTien(hd.getMaHoaDon())),
						currencyFormat.format(ds.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())), currencyFormat.format(
								ds.tinhTongTien(hd.getMaHoaDon()) - ds.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())) };
				dfHoaDon.addRow(row);
//				Cập nhật dữ liệu trên bảng
				dfHoaDon.fireTableDataChanged();
			}
		} else {
			int op = JOptionPane.showConfirmDialog(this, "Không tìm thấy! Bạn có muốn thực hiện lại thao tác.",
					"Thông báo", JOptionPane.YES_NO_OPTION);
			if (op == 0) {
				xoaTrang();
				return;
			} else {
				return;
			}
		}
	}
	
	public void capNhatTongTienMoiNhat() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		Vector<Vector> data = dfSanPhamTra.getDataVector();
		double tongTienCu = Double.parseDouble(txtTongTien.getText().replaceAll("[,.]", ""));
		double thanhTien, tienHoanTra = 0;
		for (Vector vector : data) {
			thanhTien = Double.parseDouble(vector.get(7).toString().replaceAll("[,.]", ""));
			tienHoanTra += thanhTien;
		}
		txtTienTraLai.setText(currencyFormat.format(tienHoanTra));
		txtTongTienMoi.setText(currencyFormat.format(tongTienCu - tienHoanTra));
	}

	public void hoanTien() {
		int sanPhamSelected = tblChiTietHoaDon.getSelectedRow();
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		if (sanPhamSelected != -1) {
			int soLuongTra = Integer.parseInt(txtSoLuongTra.getText().toString());
			int soLuongTrongHoaDon = Integer.parseInt(tblChiTietHoaDon.getValueAt(sanPhamSelected, 6).toString());
			if (soLuongTra <= soLuongTrongHoaDon) {
				tblChiTietHoaDon.setValueAt(soLuongTrongHoaDon - soLuongTra, sanPhamSelected, 6);
				String donGiaBan = tblChiTietHoaDon.getValueAt(sanPhamSelected, 5).toString().replaceAll("[.,]", "");
				double donGiaBanDou = Double.parseDouble(donGiaBan);
				tblChiTietHoaDon.setValueAt(currencyFormat.format(donGiaBanDou * (soLuongTrongHoaDon - soLuongTra)),
						sanPhamSelected, 7);
				String maSanPhamTra = tblChiTietHoaDon.getValueAt(sanPhamSelected, 0).toString();
				SanPham_DAO dsSanPham = new SanPham_DAO();
				SanPham sanPhamTra = dsSanPham.getSanPhamTheoMa(maSanPhamTra);
				double thanhTien = donGiaBanDou * soLuongTra;
				String thanhTienStr = currencyFormat.format(thanhTien);
				Vector<Vector> dataSanPhamTra = dfSanPhamTra.getDataVector();
				for (Vector vector : dataSanPhamTra) {
					if(vector.get(0).equals(sanPhamTra.getMaSanPham())) {
						JOptionPane.showMessageDialog(null, "Lỗi: Sản phầm đã tồn tại!");
						return;
					}
				}
				Object row[] = { sanPhamTra.getMaSanPham(), sanPhamTra.getTenSanPham(), sanPhamTra.getChatLieu(),
						sanPhamTra.getMauSac(), sanPhamTra.getKichCo(), donGiaBan, soLuongTra, thanhTienStr };
				dfSanPhamTra.addRow(row);
				dfSanPhamTra.fireTableDataChanged();
				capNhatTongTienMoiNhat();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: số lượng hoàn trả lớn hơn số lượng trong hóa đơn!");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Lỗi: chưa chọn sản phẩm cần hoàn trả!");
			return;
		}
	}
	
	public boolean kiemTraDauVao() {
		String lyDoTraHang = txaLyDoTraHang.getText().toString().trim();
		Date ngayTraHang = jdcNgayTra.getDate();
		Date ngayHomNay = new Date();
		if(lyDoTraHang.equals("")) {
			JOptionPane.showMessageDialog(null, "Lỗi: lý do trả hàng rỗng!");
			return false;
		}else if(ngayTraHang == null || (ngayTraHang.compareTo(ngayHomNay)==0)) {
			JOptionPane.showMessageDialog(null, "Lỗi: ngày trả hàng phải là ngày hôm nay!");
			return false;
		}
		return true;
	}

	public void thanhToan() {
		if(kiemTraDauVao()) {
			String lyDoTraHang = txaLyDoTraHang.getText().toString().trim();
			LocalDate ngayTraHang = Function.convertToLocalDateViaMilisecond(jdcNgayTra.getDate());
			String maHoaDon = txtmaHoaDon.getText().toString().trim();
			HoaDon hd = dsHD.getHoaDonTheoMa(maHoaDon);
			entity.DonTraHang donTraHang = new entity.DonTraHang(hd, gui.FrameDangNhap.getNhanVienDangNhap(), ngayTraHang, lyDoTraHang);
			if (dsDonTraHang.themDonTraHang(donTraHang)) {
				// Lấy dữ liệu từ DefaultTableModel dưới dạng Vector
				entity.DonTraHang donTraHangVuaThem = dsDonTraHang.getDonTraHangVuaCapNhat();
				Vector<Vector> danhSachSanPhamTra = dfSanPhamTra.getDataVector();
				int soLuongTra;
				String maSanPham;
				for (Vector<Object> vector : danhSachSanPhamTra) {
					maSanPham = vector.get(0).toString();
					soLuongTra = Integer.parseInt(vector.get(6).toString());
					ChiTietDonTraHang chiTiet = new ChiTietDonTraHang(donTraHangVuaThem,
							dsSanPham.getSanPhamTheoMa(maSanPham), soLuongTra, 0, false);
					dsCTDonTraHang.themChiTietDonTraHang(chiTiet);
					dsCTHD.capNhatSoLuongTrongHoaDon(soLuongTra, maHoaDon, maSanPham);
				}
				JOptionPane.showMessageDialog(null, "Đã tạo đơn trả hàng " + donTraHangVuaThem.getMaDonTraHang() + " thành công");
			}else {
				JOptionPane.showMessageDialog(null, "Lỗi: tạo đơn trả hàng thất bại!");
				return;
			}
		}else {
			return;
		}
	}
	
	

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tblHoaDon)) {
			int rowSelected = tblHoaDon.getSelectedRow();
			String maHoaDon = tblHoaDon.getValueAt(rowSelected, 0).toString().trim();
			loaiBoDuLieuCuaBangSanPham();
			capNhatDuLieuBangSanPham(maHoaDon);
			capNhatChoCacTruongNhapDuLieu(rowSelected);
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiem)) {
			timKiemHoaDon();
		} else if (o.equals(btnHonTr)) {
			hoanTien();
		} else if (o.equals(btnThanhToan)) {
			if(checkBoxXuatHoaDon.isSelected()) {
				thanhToan();
				DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
				int row = tblHoaDon.getSelectedRow();
				if (row != -1) {
					String maHoaDon = tblHoaDon.getValueAt(row, 0).toString().trim();
					HoaDon hoaDonTimDuoc = dsHD.getHoaDonTheoMa(maHoaDon);
					double giaKhuyenMai = dsHD.tinhGiaKhuyenMaiCuaHoaDon(maHoaDon);
					double tongTien = dsHD.tinhTongTien(maHoaDon) - giaKhuyenMai;

					if (tongTien == 0) {
						JOptionPane.showMessageDialog(null, "Không tồn tại sản phẩm trong hóa đơn!");
						return;
					}
					String tongTienStr = currencyFormat.format(tongTien);
					String giaGiamStr = currencyFormat.format(giaKhuyenMai);
					Function.xuatHoaDonPDF("sMaHoaDon", hoaDonTimDuoc.getMaHoaDon(), "sGiaGiam", giaGiamStr, "sTongTien",
							tongTienStr, Contains.getPathOfReportFiles(), false);
				} else {
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn hóa đơn !");
					return;
				}
			}
		} else if(o.equals(btnXoaTrang)) {
			xoaTrang();
		} else if(o.equals(btnLamMoi)) {
			int row = tblHoaDon.getSelectedRow();
			String maHoaDon = tblHoaDon.getValueAt(row, 0).toString();
			if(dfChiTietHoaDon.getRowCount()>0) {
				dfChiTietHoaDon.getDataVector().removeAllElements();
				dfChiTietHoaDon.fireTableDataChanged();
			}
			capNhatDuLieuBangSanPham(maHoaDon);
			dfSanPhamTra.getDataVector().removeAllElements();
			dfSanPhamTra.fireTableDataChanged();
			txtSoLuongTra.setText("");
			capNhatTongTienMoiNhat();
		} 
	}
}
