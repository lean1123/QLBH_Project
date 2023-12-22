package gui_ThongKe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChuongTrinhKhuyenMai_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.ChuongTrinhKhuyenMai;
import entity.HoaDon;
import entity.KhachHang;
import utils.Function;

public class ThongKeDoanhThuTheoKhachHang extends JPanel implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnFormNhap, pnChart;
	private JComboBox<String> cmbLoaiThongKe, cmbChonKhuyenMai;
	private JLabel lblThoiGianThongKe, lblNgayBatDau, lblNgayKetThuc, lblThongKeDoanhThu;
	private JDateChooser ngayBatDau, ngayKetThuc;
	private JScrollPane scPanel;
	private DefaultTableModel dfKhachHang;
	private JTable tblKhachHang;
	private JButton btnThongKe, btnXuatExcel, btnGuiTinNhanKhuyenMai;
	private HoaDon_DAO dsHD;
	private KhachHang_DAO dskh;
	private ChuongTrinhKhuyenMai_DAO chuongTrinhKhuyenMai_DAO;
	private ChartPanel chartPN;
	private String cols[];
	private JTextField txtTongDoanhThu;

	public ThongKeDoanhThuTheoKhachHang() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		dsHD = new HoaDon_DAO();
		dskh = new KhachHang_DAO();
		new NhanVien_DAO();
		setBackground(Color.WHITE);
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(null);

		pnFormNhap = new JPanel();
		pnFormNhap.setLayout(null);
		pnFormNhap.setBorder(new TitledBorder(null, "Form Nhập Thời Gian Cần Thống Kê", TitledBorder.LEADING,

				TitledBorder.TOP, null, null));
		pnFormNhap.setBounds(10, 57, 1176, 79);
		add(pnFormNhap);

		lblThoiGianThongKe = new JLabel("Thời gian:");
		lblThoiGianThongKe.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblThoiGianThongKe.setBounds(209, 33, 70, 20);
		pnFormNhap.add(lblThoiGianThongKe);

		cmbLoaiThongKe = new JComboBox<>();
		cmbLoaiThongKe.setBounds(289, 34, 105, 20);
		cmbLoaiThongKe.addItem("Tùy chỉnh");
		cmbLoaiThongKe.addItem("Ngày hôm nay");
		cmbLoaiThongKe.addItem("Tháng hiện tại");
		cmbLoaiThongKe.addItem("Năm hiện tại");
		pnFormNhap.add(cmbLoaiThongKe);

		lblNgayBatDau = new JLabel("Ngày bắt đầu:");
		lblNgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayBatDau.setBounds(495, 33, 100, 20);
		pnFormNhap.add(lblNgayBatDau);

		ngayBatDau = new JDateChooser();
		ngayBatDau.setBounds(602, 32, 123, 21);
		pnFormNhap.add(ngayBatDau);

		lblNgayKetThuc = new JLabel("Ngày kết thúc:");
		lblNgayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayKetThuc.setBounds(819, 33, 100, 20);
		pnFormNhap.add(lblNgayKetThuc);

		ngayKetThuc = new JDateChooser();
		ngayKetThuc.setBounds(931, 32, 123, 21);
		pnFormNhap.add(ngayKetThuc);

		cols = new String[] { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Số hóa đơn",
				"Tổng tiền chi" };

		dfKhachHang = new DefaultTableModel(cols, 0);
		tblKhachHang = new JTable(dfKhachHang);

		scPanel = new JScrollPane(tblKhachHang, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPanel.setBounds(10, 186, 1300, 155);
		add(scPanel);

		btnThongKe = new JButton("Thống kê");
		btnThongKe.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnThongKe.setBounds(1196, 84, 100, 30);
		add(btnThongKe);

		pnChart = new JPanel();
		pnChart.setLayout(null);
		pnChart.setBounds(10, 363, 1300, 421);
		add(pnChart);

		btnXuatExcel = new JButton("Xuất Excel");
		btnXuatExcel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXuatExcel.setBounds(10, 146, 100, 30);
		add(btnXuatExcel);
		JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
		lblTongDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTongDoanhThu.setBounds(951, 154, 104, 20);
		add(lblTongDoanhThu);

		txtTongDoanhThu = new JTextField();
		txtTongDoanhThu.setEditable(false);
		txtTongDoanhThu.setColumns(10);
		txtTongDoanhThu.setBounds(1068, 156, 118, 20);
		add(txtTongDoanhThu);
		btnGuiTinNhanKhuyenMai = new JButton("Gửi Tin Nhắn Khuyến Mãi");
		btnGuiTinNhanKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnGuiTinNhanKhuyenMai.setBounds(120, 146, 200, 30);
		add(btnGuiTinNhanKhuyenMai);
//		Connect cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		btnThongKe.addActionListener(this);

		btnXuatExcel.addActionListener(this);

		cmbLoaiThongKe.addActionListener(this);

		lblThongKeDoanhThu = new JLabel("THỐNG KÊ DOANH THU THEO KHÁCH HÀNG");
		lblThongKeDoanhThu.setForeground(Color.BLUE);
		lblThongKeDoanhThu.setBounds(424, 10, 445, 37);
		add(lblThongKeDoanhThu);
		lblThongKeDoanhThu.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongKeDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 20));

		cmbChonKhuyenMai = new JComboBox<>();
		cmbChonKhuyenMai.setBounds(512, 146, 170, 30);
		add(cmbChonKhuyenMai);

		JLabel lblNewLabel = new JLabel("Chương trình khuyến mãi:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(339, 146, 151, 30);
		add(lblNewLabel);
		loadComboboxKhuyenMai();

		btnGuiTinNhanKhuyenMai.addActionListener(this);

	}

	/**
	 * Function loadComboboxKhuyenMai được sử dụng đẻ load mã khuyến mãi lên giao diện người dùng để gửi sms
	 */
	public void loadComboboxKhuyenMai() {
		chuongTrinhKhuyenMai_DAO = new ChuongTrinhKhuyenMai_DAO();
		for (ChuongTrinhKhuyenMai chuongTrinhKhuyenMai : chuongTrinhKhuyenMai_DAO.getListChuongTrinhKhuyenMai()) {
			cmbChonKhuyenMai.addItem(chuongTrinhKhuyenMai.getMaKhuyenMai());
		}
	}
	/**
	 * Kiêm tra đầu vào của thống kê theo thời gian tùy chỉnh.
	 *
	 * @return true nếu nhập thời gian đúng với điều kiện và false nếu ngược lại.
	 */

	public boolean kiemTraDauVaoChoThongKeTuyChinh() {
		Date ngayBD = ngayBatDau.getDate();
		Date ngayKT = ngayKetThuc.getDate();
		if (ngayBD == null || ngayBD.after(new Date())) {
			JOptionPane.showMessageDialog(null, "Lỗi: Ngày bắt đầu phải trước hoặc là ngày hôm nay!");
			return false;
		} else if (ngayKT == null || ngayKT.before(ngayBatDau.getDate()) || ngayKT.after(new Date())) {
			JOptionPane.showMessageDialog(null, "Lỗi: Ngày kết thúc phải từ ngày bắt đầu đến ngày hôm nay!");
			return false;
		}
		return true;
	}

	/**
	 * Truy xuất một tập hợp các mã khách hàng duy nhất dựa trên thời gian của loại
	 * thống kê cụ thể.
	 *
	 * @param loaiThongKe Loại thời gian thống kê theo (ngày hôm nay, Tháng hiện
	 *                    tại, năm này).
	 * @return Một tập hợp mã khách hàng duy nhất.
	 */

	public HashSet<String> getDanhSachKhachHangTheoThoiGianThongKeKhac(String loaiThongKe) {
		List<HoaDon> dsTimDuoc = dsHD.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		// Tạo một HashSet để lưu trữ các giá trị "maKhachHang" duy nhất
		HashSet<String> uniqueMaKhachHangs = new HashSet<>();
		if (dsTimDuoc.size() > 0) {

			for (HoaDon hoaDon : dsTimDuoc) {
				String maKhachHang = hoaDon.getKhachHang().getMaKhachHang();
				uniqueMaKhachHangs.add(maKhachHang); // Thêm giá trị "maKhachHang" vào HashSet
			}

		}
		return uniqueMaKhachHangs;

	}

	/**
	 * Truy xuất một tập hợp các mã khách hàng duy nhất dựa trên một khoảng thời
	 * gian tùy chỉnh của các hóa đơn.
	 *
	 * @param ngayBatDau  Ngày bắt đầu của khoảng thời gian.
	 * @param ngayKetThuc Ngày kết thúc của khoảng thời gian.
	 * @return Một HashSet chứa các ID khách hàng duy nhất trong khoảng thời gian
	 *         tùy chỉnh.
	 */

	public HashSet<String> getDanhSachKhachHangTheoThoiGianThongKeTuyChinh(Date ngayBatDau, Date ngayKetThuc) {
		List<HoaDon> dsTimDuoc = dsHD.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDau, ngayKetThuc);
		// Tạo một HashSet để lưu trữ các giá trị "maKhachHang" duy nhất
		HashSet<String> uniqueMaKhachHangs = new HashSet<>();
		if (dsTimDuoc.size() > 0) {

			for (HoaDon hoaDon : dsTimDuoc) {
				String maKhachHang = hoaDon.getKhachHang().getMaKhachHang();
				uniqueMaKhachHangs.add(maKhachHang); // Thêm giá trị "maKhachHang" vào HashSet
			}

		}
		return uniqueMaKhachHangs;

	}

	/**
	 * Cập nhật dữ liệu trên bảng hiển thị doanh thu theo khách hàng dựa trên loại
	 * thống kê và khoảng thời gian được chọn.
	 */

	public void capNhatBangThongKe() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString().trim();
		HashSet<String> dsMaKhachHang = null;
		if (loaiThongKe.equalsIgnoreCase("Tùy chỉnh")) {
			Date ngayBatDauD = ngayBatDau.getDate();
			Date ngayKetThucD = ngayKetThuc.getDate();
//			Trường hợp thống kê theo loại thời gian tùy chỉnh
			dsMaKhachHang = getDanhSachKhachHangTheoThoiGianThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
			if (dsMaKhachHang.size() > 0) {
				for (String maKhachHang : dsMaKhachHang) {
					KhachHang kh = dskh.getKhachHangTheoMa(maKhachHang);
					Object rowData[] = { kh.getMaKhachHang(), kh.getHoTen(), kh.getSoDienThoai(), kh.getDiaChi(),
							dsHD.getSoHoaDonTheoKhachHangTheoThongKeTuyChinh(maKhachHang, ngayBatDauD, ngayKetThucD),
							currencyFormat.format(dsHD.tinhTongSoTienChiCuaKhachHangTheoThongKeTuyChinh(maKhachHang,
									ngayBatDauD, ngayKetThucD)) };
					dfKhachHang.addRow(rowData);
				}
				dfKhachHang.fireTableDataChanged();
			}
		} else {
//			Trường hợp thống kê khác loại thời gian tùy chỉnh
			dsMaKhachHang = getDanhSachKhachHangTheoThoiGianThongKeKhac(loaiThongKe);
			if (dsMaKhachHang.size() > 0) {
				for (String maKhachHang : dsMaKhachHang) {
					KhachHang kh = dskh.getKhachHangTheoMa(maKhachHang);
					Object rowData[] = { kh.getMaKhachHang(), kh.getHoTen(), kh.getSoDienThoai(), kh.getDiaChi(),
							dsHD.getSoHoaDonTheoKhachHangTheoThongKeKhac(maKhachHang, loaiThongKe),
							currencyFormat.format(
									dsHD.tinhTongSoTienChiCuaKhachHangTheoThongKeKhac(maKhachHang, loaiThongKe)) };
					dfKhachHang.addRow(rowData);
				}
				dfKhachHang.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Không tồn tại doanh thu trong thời gian này!");
				return;
			}
		}

	}

	/**
	 * Cập nhật hiển thị tổng doanh thu dựa trên loại thống kê và khoảng thời gian
	 * được chọn.
	 */

	public void capNhatTongDoanhThuTXT() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString().trim();
		double tongDoanhThu = 0;
		List<HoaDon> dshdTimDuoc;
		if (loaiThongKe.equalsIgnoreCase("Tùy Chỉnh")) {
			Date ngayBatDauD = ngayBatDau.getDate();
			Date ngayKetThucD = ngayKetThuc.getDate();
			dshdTimDuoc = dsHD.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
		} else {
			dshdTimDuoc = dsHD.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		}
		if (dshdTimDuoc.isEmpty()) {
			return;
		}
		for (HoaDon hoaDon : dshdTimDuoc) {
			tongDoanhThu += dsHD.tinhTongTien(hoaDon.getMaHoaDon())
					- dsHD.tinhGiaKhuyenMaiCuaHoaDon(hoaDon.getMaHoaDon());
		}
		txtTongDoanhThu.setText(currencyFormat.format(tongDoanhThu));
	}

	/**
	 * Tạo và trả về dữ liệu tập hợp cho biểu đồ dạng cột dựa trên thống kê tùy
	 * chỉnh với các thông tin được chọn.
	 *
	 * @param xlbl Nhãn cho trục x của biểu đồ.
	 * @return Tập dữ liệu cho biểu đồ dạng cột hoặc null nếu không có dữ liệu.
	 */

	public CategoryDataset getTapDuLieuChoBieuDoThongKeTuyChinh(String xlbl) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		Date ngayBatDauD = ngayBatDau.getDate();
		Date ngayKetThucD = ngayKetThuc.getDate();
		HashSet<String> dsMaKhachHang = getDanhSachKhachHangTheoThoiGianThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
		if (dsMaKhachHang.size() > 0) {
			for (String maKhachHang : dsMaKhachHang) {
				double tongTien = dsHD.tinhTongSoTienChiCuaKhachHangTheoThongKeTuyChinh(maKhachHang, ngayBatDauD,
						ngayKetThucD);
				data.addValue(tongTien, xlbl, maKhachHang);
			}
		} else {
			return null;
		}
		return data;
	}

	/**
	 * Tạo và trả về dữ liệu tập hợp cho biểu đồ dạng cột dựa trên loại thống kê
	 * khác với các thông tin được chọn.
	 *
	 * @param xlbl        Nhãn cho trục x của biểu đồ.
	 * @param loaiThongKe Loại thống kê khác được chọn (ngày hôm nay, Tháng hiện
	 *                    tại, năm này).
	 * @return Tập dữ liệu cho biểu đồ dạng cột hoặc null nếu không có dữ liệu.
	 */

	public CategoryDataset getTapDuLieuChoBieuDoThongKeKhac(String xlbl, String loaiThongKe) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		HashSet<String> dsMaKhachHang = getDanhSachKhachHangTheoThoiGianThongKeKhac(loaiThongKe);
		if (dsMaKhachHang.size() > 0) {
			for (String maKhachHang : dsMaKhachHang) {
				double tongTien = dsHD.tinhTongSoTienChiCuaKhachHangTheoThongKeKhac(maKhachHang, loaiThongKe);
				data.addValue(tongTien, xlbl, maKhachHang);
			}
		} else {
			return null;
		}
		return data;
	}

	/**
	 * Tạo và trả về biểu đồ cột JFreeChart dựa trên loại thống kê và danh sách mã
	 * khách hàng đã chọn.
	 *
	 * @param xLbl Nhãn cho trục x của biểu đồ.
	 * @return Đối tượng biểu đồ cột JFreeChart nếu có dữ liệu từ DataSet và null
	 *         nếu ngược lại.
	 */

	public JFreeChart taoBieuDo(String xLbl) {
		SimpleDateFormat simpleDateFortmat = new SimpleDateFormat("dd-MM-yyyy");
		JFreeChart barChart = null;
		CategoryDataset data = null;
		String loaiTK = cmbLoaiThongKe.getSelectedItem().toString();
		Date now = new Date();
		String date = simpleDateFortmat.format(now);
		String titleOfBarChart = "";
		if (loaiTK.equalsIgnoreCase("Ngày hôm nay")) {
			titleOfBarChart += "BIỂU ĐỒ DOANH THU THEO KHÁCH HÀNG NGÀY " + simpleDateFortmat.format(now);
			data = getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK);
		} else if (loaiTK.equalsIgnoreCase("Tháng hiện tại")) {
			titleOfBarChart = "BIỂU ĐỒ DOANH THU THEO KHÁCH HÀNG THÁNG " + date.split("-")[1] + " NĂM "
					+ date.split("-")[2];
			data = getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK);
		} else if (loaiTK.equalsIgnoreCase("Năm hiện tại")) {
			titleOfBarChart += "BIỂU ĐỒ DOANH THU THEO KHÁCH HÀNG NĂM " + date.split("-")[2];
			data = getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK);
		} else if (loaiTK.equalsIgnoreCase("Tùy chỉnh")) {
			Date startDay = ngayBatDau.getDate();
			Date closeDay = ngayKetThuc.getDate();
			if (startDay != null && closeDay != null) {
				String ngayBatDau = simpleDateFortmat.format(startDay);
				String ngayKetThuc = simpleDateFortmat.format(closeDay);
				titleOfBarChart += "BIỂU ĐỒ DOANH THU TỪ " + ngayBatDau + " ĐẾN " + ngayKetThuc;
				data = getTapDuLieuChoBieuDoThongKeTuyChinh(xLbl);
			}
		}
		if (data != null) {
			barChart = ChartFactory.createBarChart(titleOfBarChart, xLbl, "Doanh thu", data, PlotOrientation.VERTICAL,
					true, false, false);
		}

		return barChart;
	}

	/**
	 * Làm mới JPanel bằng cách validate và repaint nó.
	 *
	 * @param panel JPanel cần được làm mới.
	 */

	public void lamMoiDieuDo(JPanel panel) {
		panel.validate();
		panel.repaint();
	}

	/**
	 * Loại bỏ các dòng dữ liệu trên tblKhachHang
	 */

	public void XoaHetDuLieuTrenBang() {
		dfKhachHang.getDataVector().removeAllElements();
		dfKhachHang.fireTableDataChanged();
	}

	/**
	 * Xử lý sự kiện cho btnThongKe
	 */

	public void thongKe() {
		String loaiTK = cmbLoaiThongKe.getSelectedItem().toString();
		if (pnChart.isAncestorOf(chartPN)) {
			pnChart.remove(chartPN);
		}
		String xlbl = "Khách hàng";
		if (loaiTK.equalsIgnoreCase("Tùy chỉnh")) {
			if (!kiemTraDauVaoChoThongKeTuyChinh()) {
				return;
			}
		}
		capNhatBangThongKe();
		capNhatTongDoanhThuTXT();
		if (taoBieuDo(xlbl) == null) {
			return;
		}
		chartPN = new ChartPanel(taoBieuDo(xlbl));
		chartPN.setBounds(0, 0, 1300, 421);
		pnChart.add(chartPN);

		pnChart.revalidate();
		pnChart.repaint();

	}

	/**
	 * Thực hiện hành động trên các thành phần ngày tháng (ngày bắt đầu và ngày kết
	 * thúc).
	 *
	 * @param isShow True nếu muốn hiển thị các thành phần, False nếu muốn ẩn chúng.
	 */

	public void setHienThiChoThongKeTuyChinh(boolean isShow) {
		lblNgayBatDau.setVisible(isShow);
		ngayBatDau.setVisible(isShow);
		lblNgayKetThuc.setVisible(isShow);
		ngayKetThuc.setVisible(isShow);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThongKe)) {
			if (dfKhachHang.getRowCount() > 0) {
				XoaHetDuLieuTrenBang();
			}
			thongKe();
		} else if (o.equals(btnXuatExcel)) {
//			Xuất bảng thống kê với tên file tự phát sinh bắt đầu bằng "ThongKeDoanhThuTheoKhachHang...", tên sheet "Bao Cao Doanh Thu Theo Khach Hang"
//			cols là danh sách column của bẳng và dfKhachHang là DefaultTableModel của bảng khách hàng

			if (Function.xuatExcel("ThongKeDoanhThuTheoKhachHang", "Bao Cao Doanh Thu Theo Khach Hang", cols,
					dfKhachHang)) {
				JOptionPane.showMessageDialog(null, "Xuất file thành công!");
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Xuất file không thành công!");
			}
		} else if (o.equals(cmbLoaiThongKe)) {
			String selectedItem = cmbLoaiThongKe.getSelectedItem().toString().trim();
			if (!selectedItem.equalsIgnoreCase("Tùy chỉnh")) {
				setHienThiChoThongKeTuyChinh(false);
			} else {
				setHienThiChoThongKeTuyChinh(true);
			}
		}else if(o.equals(btnGuiTinNhanKhuyenMai)) {
			int row[] = tblKhachHang.getSelectedRows();
			ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chuongTrinhKhuyenMai_DAO.getKhuyenMaiTheoMa(cmbChonKhuyenMai.getSelectedItem().toString());
			String content = "\n"+chuongTrinhKhuyenMai.getMoTa() + "  \nNgày có hiệu lực: " + chuongTrinhKhuyenMai.getNgayBatDau() + " - "+ chuongTrinhKhuyenMai.getNgayKetThuc()+" \nMã cáp cha: "+chuongTrinhKhuyenMai.getMaCaptcha();
			for (Integer i : row) {

				String soDienThoai = tblKhachHang.getValueAt(i, 2).toString();
				String re = utils.Function.sendSMSWithTwilio(soDienThoai, content);
				if(i == row.length) {
					System.out.println(i+"-"+soDienThoai);
					JOptionPane.showMessageDialog(this,re );
				}
			}

		}
	}
}
