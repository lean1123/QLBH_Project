package gui_HoaDon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietHoaDon_Dao;
import dao.HoaDon_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import utils.Contains;
import utils.Function;

public class TimKiemHoaDon extends JPanel implements ActionListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField sDTKHtxt, tenNVtxt;
	private JLabel lblSDTKH, lblTenNV, lblTieuDeTBHoaDon, lblTieuDeTBDH;
	private JButton btnTimKiem, btnXoaTrang, btnXemCT;
	private JTable donHangTB, hoaDonTB;
	private DefaultTableModel dmHoaDon, dmSanPham;
	private JScrollPane scPanel, scPanel2;
	private JTextField txtMaHoaDon;
	private JDateChooser dcsNgayLap;
	private JLabel lblNgayLap;
	private JLabel lblTnKhchHng;
	private JTextField txtTenKH;
	private HoaDon_DAO dshd;
	private ChiTietHoaDon_Dao listCTHoaDon;

	/**
	 * Create the panel.
	 */
	public TimKiemHoaDon() {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dshd = new HoaDon_DAO();
		listCTHoaDon = new ChiTietHoaDon_Dao();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnTimKiem.setBounds(1184, 69, 126, 38);
		add(btnTimKiem);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXoaTrang.setBounds(1184, 117, 126, 38);
		add(btnXoaTrang);

		lblTieuDeTBHoaDon = new JLabel("Hóa đơn");
		lblTieuDeTBHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTieuDeTBHoaDon.setBounds(8, 212, 73, 24);
		add(lblTieuDeTBHoaDon);

		String[] columns2 = { "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Số điện thoại", "Ngày lập hóa đơn",
				"Tổng tiền ban đầu", "Số phần trăm giảm giá", "Tồng tiền" };
		dmHoaDon = new DefaultTableModel(columns2, 0);

		scPanel2 = new JScrollPane();
		hoaDonTB = new JTable(dmHoaDon);
		scPanel2 = new JScrollPane(hoaDonTB, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPanel2.setViewportView(hoaDonTB);
		scPanel2.setBounds(8, 246, 1302, 203);
		add(scPanel2);

		String columns[] = { "Mã sản phẩm", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Size", "Giá bán", "Số Lượng",
				"Thành tiền" };
		dmSanPham = new DefaultTableModel(columns, 0);

		scPanel = new JScrollPane();

		donHangTB = new JTable(dmSanPham);
		scPanel = new JScrollPane(donHangTB, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPanel.setViewportView(donHangTB);
		scPanel.setBounds(10, 493, 1300, 291);
		add(scPanel);

		lblTieuDeTBDH = new JLabel("Chi tiết hóa đơn");
		lblTieuDeTBDH.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTieuDeTBDH.setBounds(10, 459, 116, 24);
		add(lblTieuDeTBDH);

		JPanel pnFormNhap = new JPanel();
		pnFormNhap.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Hóa Đơn", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormNhap.setBounds(8, 69, 1166, 133);
		add(pnFormNhap);
		pnFormNhap.setLayout(null);

		lblSDTKH = new JLabel("Số điện thoại khách hàng :");
		lblSDTKH.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSDTKH.setBounds(766, 27, 161, 24);
		pnFormNhap.add(lblSDTKH);

		sDTKHtxt = new JTextField();
		sDTKHtxt.setBounds(937, 27, 219, 24);
		pnFormNhap.add(sDTKHtxt);
		sDTKHtxt.setColumns(10);

		lblTenNV = new JLabel("Tên nhân viên :");
		lblTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTenNV.setBounds(27, 73, 90, 24);
		pnFormNhap.add(lblTenNV);

		tenNVtxt = new JTextField();
		tenNVtxt.setBounds(141, 73, 209, 24);
		pnFormNhap.add(tenNVtxt);
		tenNVtxt.setColumns(10);

		JLabel lblMHan = new JLabel("Mã hóa đơn:");
		lblMHan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMHan.setBounds(27, 27, 90, 24);
		pnFormNhap.add(lblMHan);

		txtMaHoaDon = new JTextField();
		txtMaHoaDon.setColumns(10);
		txtMaHoaDon.setBounds(141, 28, 193, 24);
		pnFormNhap.add(txtMaHoaDon);

		dcsNgayLap = new JDateChooser();
		dcsNgayLap.setBounds(937, 73, 219, 24);
		pnFormNhap.add(dcsNgayLap);

		lblNgayLap = new JLabel("Ngày lập hóa đơn:");
		lblNgayLap.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayLap.setBounds(766, 73, 126, 24);
		pnFormNhap.add(lblNgayLap);

		lblTnKhchHng = new JLabel("Tên khách hàng:");
		lblTnKhchHng.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTnKhchHng.setBounds(360, 27, 112, 24);
		pnFormNhap.add(lblTnKhchHng);

		txtTenKH = new JTextField();
		txtTenKH.setColumns(10);
		txtTenKH.setBounds(495, 28, 255, 24);
		pnFormNhap.add(txtTenKH);

		btnXemCT = new JButton("Xem chi tiết");
		btnXemCT.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXemCT.setBounds(1184, 165, 126, 38);
		add(btnXemCT);

		
		hoaDonTB.addMouseListener(this);
		donHangTB.addMouseListener(this);
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnXemCT.addActionListener(this);

		JLabel lblNewLabel = new JLabel("TÌM KIẾM HÓA ĐƠN");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(537, 29, 255, 30);
		add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		capNhatDuLieuBangHoaDon();
	}

	/**
	 * Cập nhật dữ liệu của bảng Hóa Đơn.
	 */

	public void capNhatDuLieuBangHoaDon() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		HoaDon_DAO ds = new HoaDon_DAO();
		List<HoaDon> dsTimDuoc = ds.getListHoaDon();
		for (HoaDon hd : dsTimDuoc) {
			Object row[] = { hd.getMaHoaDon(), hd.getNhanVien().getTen(), hd.getKhachHang().getHoTen(),
					hd.getKhachHang().getSoDienThoai(), hd.getNgayLap(),
					currencyFormat.format(ds.tinhTongTien(hd.getMaHoaDon())), hd.getChuongTrinhKhuyenMai().getGiaGiam(),
					currencyFormat.format(
							ds.tinhTongTien(hd.getMaHoaDon()) - ds.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())) };
			dmHoaDon.addRow(row);
		}
		dmHoaDon.fireTableDataChanged();
	}

	/**
	 * Cập nhật dữ liệu của bảng Sản Phẩm trong Hóa Đơn.
	 *
	 * @param maHoaDon Mã Hóa Đơn để lấy danh sách sản phẩm.
	 */

	public void capNhatDuLieuBangSanPham(String maHoaDon) {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		ChiTietHoaDon_Dao ds = new ChiTietHoaDon_Dao();
		for (ChiTietHoaDon CTHoaDon : ds.getListSanPhamTuHoaDon(maHoaDon)) {
			Object row[] = { CTHoaDon.getSanPham().getMaSanPham(), CTHoaDon.getSanPham().getTenSanPham(),
					CTHoaDon.getSanPham().getChatLieu(), CTHoaDon.getSanPham().getMauSac(),
					CTHoaDon.getSanPham().getKichCo(), currencyFormat.format(CTHoaDon.getDonGia()),
					CTHoaDon.getSoLuong(), currencyFormat.format(CTHoaDon.getDonGia() * CTHoaDon.getSoLuong()) };
			dmSanPham.addRow(row);
		}
		donHangTB.setModel(dmSanPham);
	}

	/**
	 * Xóa tất cả các dòng trong bảng Sản Phẩm của Hóa Đơn.
	 */

	private void loaiBoDuLieuCuaBangSanPham() {
		int rowCount = dmSanPham.getRowCount();
		if (rowCount > 0) {
			dmSanPham.getDataVector().removeAllElements(); // Xóa tất cả dữ liệu trong mô hình
			dmSanPham.fireTableDataChanged(); // Thông báo cho bảng cập nhật dữ liệu
		}
	}

	/**
	 * Xóa sạch các trường và dữ liệu trong bảng của form Hóa Đơn.
	 */

	public void xoaTrang() {
		txtMaHoaDon.setText("");
		txtTenKH.setText("");
		sDTKHtxt.setText("");
		tenNVtxt.setText("");
		dcsNgayLap.setDate(new Date());
		dmHoaDon.getDataVector().removeAllElements();
		loaiBoDuLieuCuaBangSanPham();
		capNhatDuLieuBangHoaDon();
	}

	/**
	 * Xử lý tìm kiếm hóa đơn dựa trên các trường thông tin mã hóa đơn, tên khách
	 * hàng, tên nhân viên, ngày lập.
	 */

	private void timKiemHoaDon() {

		String ma = txtMaHoaDon.getText().toString().trim();
		String tenKH = txtTenKH.getText().toString().trim();
		String sdt = sDTKHtxt.getText().toString().trim();
		String tenNV = tenNVtxt.getText().toString().trim();
		Date ngayLap = dcsNgayLap.getDate();
		List<HoaDon> dstk = dshd.timKiemHoaDon(ma, tenNV, tenKH, sdt, ngayLap);

		if (dstk.size() > 0) {
			DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
			dmHoaDon.getDataVector().removeAllElements();
			dmSanPham.getDataVector().removeAllElements();
			for (HoaDon hd : dstk) {
				Object row[] = { hd.getMaHoaDon(), hd.getNhanVien().getTen(), hd.getKhachHang().getHoTen(),
						hd.getKhachHang().getSoDienThoai(), hd.getNgayLap(),
						currencyFormat.format(dshd.tinhTongTien(hd.getMaHoaDon())),
						currencyFormat.format(dshd.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())),
						currencyFormat.format(dshd.tinhTongTien(hd.getMaHoaDon())
								- dshd.tinhGiaKhuyenMaiCuaHoaDon(hd.getMaHoaDon())) };
				dmHoaDon.addRow(row);
//				Cập nhật dữ liệu trên bảng
				dmSanPham.fireTableDataChanged();
			}
		} else {
			int op = JOptionPane.showConfirmDialog(this, "Ban co muon thuc hien lai thao tac!",
					"Thong Bao loi nhap lieu", JOptionPane.YES_NO_OPTION);
			if (op == 0) {
				xoaTrang();
				return;
			} else {
				return;
			}
		}
	}

	/**
	 * Xem chi tiết hóa đơn và xuất hóa đơn ra file PDF nếu isPrinted là true.
	 *
	 * @param isPrinted Xác định xem hóa đơn có được in ra file PDF hay không.
	 */

	public void xemChiTietHoaDon(boolean isPrinted) {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		int row = hoaDonTB.getSelectedRow();
		if (row != -1) {
			String maHoaDon = hoaDonTB.getValueAt(row, 0).toString().trim();
			HoaDon hoaDonTimDuoc = dshd.getHoaDonTheoMa(maHoaDon);
			double giaKhuyenMai = dshd.tinhGiaKhuyenMaiCuaHoaDon(maHoaDon);
			double tongTien = dshd.tinhTongTien(maHoaDon) - giaKhuyenMai;

			if (tongTien == 0) {
				JOptionPane.showMessageDialog(null, "Không tồn tại sản phẩm trong hóa đơn!");
				return;
			}
			String tongTienStr = currencyFormat.format(tongTien);
			String giaGiamStr = currencyFormat.format(giaKhuyenMai);
			Function.xuatHoaDonPDF("sMaHoaDon", hoaDonTimDuoc.getMaHoaDon(), "sGiaGiam", giaGiamStr, "sTongTien",
					tongTienStr, Contains.getPathOfReportFiles(), isPrinted);
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn hóa đơn !");
			return;
		}
	}

	/**
	 * Cập nhật các trường dữ liệu trên giao diện với thông tin từ dòng được chọn
	 * trong bảng Hóa Đơn.
	 *
	 * @param row Chỉ mục của dòng được chọn trong bảng Hóa Đơn.
	 */

	public void capNhatChoCacTruongNhapDuLieu(int row) {
		txtMaHoaDon.setText(hoaDonTB.getValueAt(row, 0).toString());
		txtTenKH.setText(hoaDonTB.getValueAt(row, 2).toString());
		sDTKHtxt.setText(hoaDonTB.getValueAt(row, 3).toString());
		tenNVtxt.setText(hoaDonTB.getValueAt(row, 1).toString());
		dcsNgayLap.setDate((Date) hoaDonTB.getValueAt(row, 4));
	}

	/**
	 * Xử lý sự kiện khi các nút được nhấn.
	 *
	 * @param e Sự kiện hành động.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiem)) {
			timKiemHoaDon();
		} else if (o.equals(btnXoaTrang)) {
			xoaTrang();
		} else if (o.equals(btnXemCT)) {
//			True xuất hóa đơn sang PDF và false là ngược lại.

			xemChiTietHoaDon(false);
		}
	}

	/**
	 * Xử lý sự kiện khi chuột được nhấp vào một thành phần.
	 *
	 * @param e Sự kiện chuột.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(hoaDonTB)) {
			int rowSelected = hoaDonTB.getSelectedRow();
			String maHoaDon = hoaDonTB.getValueAt(rowSelected, 0).toString().trim();
			loaiBoDuLieuCuaBangSanPham();
			capNhatDuLieuBangSanPham(maHoaDon);
			capNhatChoCacTruongNhapDuLieu(rowSelected);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
