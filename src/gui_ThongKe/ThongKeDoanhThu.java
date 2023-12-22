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
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.HoaDon;
import utils.Function;

public class ThongKeDoanhThu extends JPanel implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnThongKe, btnXuatExcel;
	private ChartPanel chartPanel;
	private JComboBox<String> cmbLoaiThongKe, cmbLoaiBaoCao;
	private DefaultTableModel dataModel;
	private JTable tblHoaDon;
	private JScrollPane scrPanel;
	private JPanel pnChart;
	private JDateChooser ngayBatDau, ngayKetThuc;
	private NhanVien_DAO dsnv;
	private HoaDon_DAO dsHoaDon;
	private KhachHang_DAO dskh;
	private JTextField txtTongDoanhThu;
	private String[] columns;
	private JLabel lblNgayBatDau, lblNgayKetThuc, lblLoaiThoiGian;

	public ThongKeDoanhThu() {
		dsHoaDon = new HoaDon_DAO();
		dskh = new KhachHang_DAO();
		dsnv = new NhanVien_DAO();
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		columns = new String[] { "Mã hóa đơn", "Nhân viên lập", "Khách hàng", "Ngày lập", "Tổng tiền" };
		dataModel = new DefaultTableModel(columns, 0);
		tblHoaDon = new JTable(dataModel);
		scrPanel = new JScrollPane(tblHoaDon, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblHoaDon);
		scrPanel.setBounds(10, 186, 1300, 187);
		add(scrPanel);

		pnChart = new JPanel();
		pnChart.setBounds(10, 383, 1300, 401);
		add(pnChart);

		JPanel pnFormNhap = new JPanel();
		pnFormNhap.setBorder(new TitledBorder(null, "Form Nhập Thời Gian Cần Thống Kê", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormNhap.setBounds(10, 57, 1147, 79);
		add(pnFormNhap);
		pnFormNhap.setLayout(null);

		JLabel thoiGianLB = new JLabel("Thời gian:");
		thoiGianLB.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		thoiGianLB.setBounds(10, 31, 70, 20);
		pnFormNhap.add(thoiGianLB);

		cmbLoaiThongKe = new JComboBox<>();
		cmbLoaiThongKe.setBounds(90, 32, 105, 20);
		pnFormNhap.add(cmbLoaiThongKe);
		cmbLoaiThongKe.addItem("Tùy chỉnh");
		cmbLoaiThongKe.addItem("Ngày hôm nay");
		cmbLoaiThongKe.addItem("Tháng hiện tại");
		cmbLoaiThongKe.addItem("Năm hiện tại");

		cmbLoaiThongKe.addActionListener(this);

		lblNgayBatDau = new JLabel("Ngày bắt đầu:");
		lblNgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayBatDau.setBounds(233, 31, 100, 20);
		pnFormNhap.add(lblNgayBatDau);

		ngayBatDau = new JDateChooser();
		ngayBatDau.setBounds(340, 30, 123, 21);
		pnFormNhap.add(ngayBatDau);

		lblNgayKetThuc = new JLabel("Ngày kết thúc:");
		lblNgayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayKetThuc.setBounds(497, 32, 100, 20);
		pnFormNhap.add(lblNgayKetThuc);

		ngayKetThuc = new JDateChooser();
		ngayKetThuc.setBounds(609, 31, 123, 21);
		pnFormNhap.add(ngayKetThuc);

		lblLoaiThoiGian = new JLabel("Loại thời gian:");
		lblLoaiThoiGian.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblLoaiThoiGian.setBounds(779, 30, 100, 20);
		pnFormNhap.add(lblLoaiThoiGian);

		cmbLoaiBaoCao = new JComboBox<>();
		cmbLoaiBaoCao.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		cmbLoaiBaoCao.setBounds(899, 32, 160, 20);

		cmbLoaiBaoCao.addItem("Báo cáo theo ngày");
		cmbLoaiBaoCao.addItem("Báo cáo theo tháng");
		pnFormNhap.add(cmbLoaiBaoCao);

		btnThongKe = new JButton("Thống kê");
		btnThongKe.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnThongKe.setBounds(1167, 82, 143, 30);
		add(btnThongKe);

		btnThongKe.addActionListener(this);

		JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
		lblTongDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTongDoanhThu.setBounds(922, 154, 104, 20);
		add(lblTongDoanhThu);

		txtTongDoanhThu = new JTextField();
		txtTongDoanhThu.setEditable(false);
		txtTongDoanhThu.setBounds(1039, 156, 118, 20);
		add(txtTongDoanhThu);
		txtTongDoanhThu.setColumns(10);

		btnXuatExcel = new JButton("Xuất Excel");
		btnXuatExcel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXuatExcel.setBounds(10, 146, 143, 30);
		add(btnXuatExcel);
		btnXuatExcel.addActionListener(this);

				JLabel titleHeader = new JLabel("THỐNG KÊ DOANH THU");
				titleHeader.setForeground(Color.BLUE);
				titleHeader.setBounds(512, 10, 257, 37);
				add(titleHeader);
				titleHeader.setHorizontalAlignment(SwingConstants.CENTER);
				titleHeader.setFont(new Font("Times New Roman", Font.BOLD, 20));

//		Kết nối hệ cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	/**
	 * Kiểm tra đầu vào cho thống kê tùy chỉnh
	 *
	 * @return true nếu đầu vào thõa mãn hết các điều kiện, flase nếu ngược lại.
	 */

	public boolean kiemTraDauVaoChoThongKeTuyChinh() {
		boolean checked = true;
		Date ngayBD = ngayBatDau.getDate();
		Date ngayKT = ngayKetThuc.getDate();
		if (ngayBD == null || ngayBD.after(new Date())) {
			JOptionPane.showMessageDialog(null, "Lỗi: Ngày bắt đầu phải trước hoặc là ngày hôm nay!");
			return false;
		} else if (ngayKT == null || ngayKT.before(ngayBatDau.getDate()) || ngayKT.after(new Date())) {
			JOptionPane.showMessageDialog(null, "Lỗi: Ngày kết thúc phải từ ngày bắt đầu đến ngày hôm nay!");
			return false;
		}
		return checked;
	}

	/**
	 * Phương thức cập nhật bảng dữ liệu hiển thị thông tin hoá đơn dựa trên loại
	 * thống kê được chọn.
	 */

	public void capNhatDuLieuChoBangThongKe() {
		String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString().trim();
		List<HoaDon> dshdTimDuoc;
		if (loaiThongKe.equalsIgnoreCase("Tùy Chỉnh")) {
			Date ngayBatDauD = ngayBatDau.getDate();
			Date ngayKetThucD = ngayKetThuc.getDate();
			dshdTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
		} else {
			dshdTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		}

		if (!dshdTimDuoc.isEmpty()) {
			DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
			for (HoaDon hoaDon : dshdTimDuoc) {
				Object rowData[] = { hoaDon.getMaHoaDon(), hoaDon.getNhanVien().getMaNhanVien(), hoaDon.getKhachHang().getMaKhachHang(),
						hoaDon.getNgayLap(), currencyFormat.format(dsHoaDon.tinhTongTien(hoaDon.getMaHoaDon())
								- dsHoaDon.tinhGiaKhuyenMaiCuaHoaDon(hoaDon.getMaHoaDon())) };
				dataModel.addRow(rowData);
			}
			dataModel.fireTableDataChanged();
		} else {
			JOptionPane.showMessageDialog(null, "Lỗi không tồn tại doanh thu trong thời gian này!");
			return;
		}

	}

	/**
	 * Phương thức cập nhật tổng doanh thu text field dựa trên loại thống kê được
	 * chọn.
	 */

	public void capNhatTongDoanhThuTXT() {
		DecimalFormat currencyFormat = new DecimalFormat("###,###.##");
		String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString().trim();
		List<HoaDon> dshdTimDuoc;
		double tongDoanhThu = 0;
		if (loaiThongKe.equalsIgnoreCase("Tùy chỉnh")) {
			Date ngayBatDauD = ngayBatDau.getDate();
			Date ngayKetThucD = ngayKetThuc.getDate();
			dshdTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
		} else {
			dshdTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		}

		if (!dshdTimDuoc.isEmpty()) {
			for (HoaDon hoaDon : dshdTimDuoc) {
				tongDoanhThu += dsHoaDon.tinhTongTien(hoaDon.getMaHoaDon()) - dsHoaDon.tinhGiaKhuyenMaiCuaHoaDon(hoaDon.getMaHoaDon());
			}
		} else {
			return;
		}
		txtTongDoanhThu.setText(currencyFormat.format(tongDoanhThu));
	}

	/**
	 * Hàm lấy tất cả các tháng có tồn tại hóa đơn của năm hiện tại.
	 *
	 * @param loaiThongKe Loại thống kê cho thời gian trong năm hiện tại.
	 * @return tập hợp các tháng duy nhất có tồn tại hóa đơn.
	 */

	public HashSet<Integer> getDanhSachThangCoTonTaiDoanhThuTrongNam(String loaiThongKe) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<HoaDon> dsTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		HashSet<Integer> uniqueMonths = new HashSet<>();
		if (dsTimDuoc.size() > 0) {
			for (HoaDon hoaDon : dsTimDuoc) {
				int thang = Integer.parseInt(sdf.format(hoaDon.getNgayLap()).split("-")[1]);
				uniqueMonths.add(thang);
			}
		}
		return uniqueMonths;

	}

	/**
	 * Hàm lấy một tập các tháng có tồn tại hóa đơn trong thời gian tỳ chỉnh.
	 *
	 * @param ngayBatDau  Ngày bắt đầu của thời gian được chọn.
	 * @param ngayKetThuc Ngày kết thúc của thời gian được chọn
	 * @return Tập hợp các tháng duy nhất có tồn tại hóa đơn trong thời gian này.
	 */

	public HashSet<Integer> getDanhSachThangCoTonTaiDoanhThuChoThongKeTuyChinh(Date ngayBatDau, Date ngayKetThuc) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<HoaDon> dsTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDau, ngayKetThuc);
		HashSet<Integer> uniqueMonths = new HashSet<>();
		if (dsTimDuoc.size() > 0) {
			for (HoaDon hoaDon : dsTimDuoc) {
				int thang = Integer.parseInt(sdf.format(hoaDon.getNgayLap()).split("-")[1]);
				uniqueMonths.add(thang);
			}
		}
		return uniqueMonths;

	}

	/**
	 * Lấy tập dữ liệu cho biểu đồ theo thời gian tùy chỉnh.
	 *
	 * @param ngayBatDauD  Ngày bắt đầu của thời gian.
	 * @param ngayKetThucD Ngày kết thúc của thời gian.
	 * @return Tập dữ liệu cho biểu đồ và null nếu không có hóa đơn nào tồn tại
	 *         trong thời gian thống kê.
	 */

	public CategoryDataset getTapDuLieuChoBieuDoThongKeTuyChinh(Date ngayBatDauD, Date ngayKetThucD) {
		String xLbl;
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		HashSet<Integer> dsThang;
		String loaiBaoCao = cmbLoaiBaoCao.getSelectedItem().toString().trim();
		double doanhthu;
		if (loaiBaoCao.equalsIgnoreCase("Báo cáo theo ngày")) {
			List<HoaDon> dsHoaDonTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
			if (!dsHoaDonTimDuoc.isEmpty()) {
				xLbl = "Ngày";
				for (HoaDon hoaDon : dsHoaDonTimDuoc) {
					doanhthu = dsHoaDon.tinhTongTien(hoaDon.getMaHoaDon()) - dsHoaDon.tinhGiaKhuyenMaiCuaHoaDon(hoaDon.getMaHoaDon());
					data.addValue(doanhthu, xLbl, hoaDon.getNgayLap());
				}
			}else {
				return null;
			}
		} else {
			dsThang = getDanhSachThangCoTonTaiDoanhThuChoThongKeTuyChinh(ngayBatDauD, ngayKetThucD);
			if (!dsThang.isEmpty()) {
				xLbl = "Tháng";
				for (Integer integer : dsThang) {
					doanhthu = dsHoaDon.tinhDoanhThuTheoThang(integer);
					data.addValue(doanhthu, xLbl, integer);
				}
			}else {
				return null;
			}
		}

		return data;
	}

	/**
	 * Tạo DataSet cho biểu đồ dạng cột dựa trên loại thống kê khác nhau.
	 *
	 * @param xLbl        Nhãn trục x của biểu đồ.
	 * @param loaiThongKe Loại thống kê cần áp dụng (Ngày hôm nay, Năm hiện tại, ...).
	 * @return DataSet cho biểu đồ dạng cột, trả về null nếu không tồn tại .
	 */

	public CategoryDataset getTapDuLieuChoBieuDoThongKeKhac(String xLbl, String loaiThongKe) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		List<HoaDon> dsHoaDonTimDuoc = dsHoaDon.getDanhSachHoaDonTheoThongKeKhac(loaiThongKe);
		if (!dsHoaDonTimDuoc.isEmpty()) {
			double doanhThu;
			if (loaiThongKe.equalsIgnoreCase("Năm hiện tại")) {
				HashSet<Integer> dsThang = getDanhSachThangCoTonTaiDoanhThuTrongNam(loaiThongKe);
				if (dsThang.size() > 0) {
					for (Integer integer : dsThang) {
						int thang = integer;
						doanhThu = dsHoaDon.tinhDoanhThuTheoThang(thang);
						data.addValue(doanhThu, xLbl, integer);
					}
				}
			} else if (loaiThongKe.equalsIgnoreCase("Ngày hôm nay")) {
				if (dsHoaDonTimDuoc.size() > 0) {
					for (HoaDon hoaDon : dsHoaDonTimDuoc) {
						doanhThu = dsHoaDon.tinhTongTien(hoaDon.getMaHoaDon());
						data.addValue(doanhThu, xLbl, sdf.format(hoaDon.getNgayLap()).split(" ")[1]);
					}
				}
			} else if (dsHoaDonTimDuoc.size() > 0) {
				for (HoaDon hoaDon : dsHoaDonTimDuoc) {
					doanhThu = dsHoaDon.tinhTongTien(hoaDon.getMaHoaDon());
					data.addValue(doanhThu, xLbl, hoaDon.getNgayLap());
				}
			}
		} else {
			return null;
		}
		return data;
	}

	/**
	 * Tạo đối tượng JFreeChart cho biểu đồ dạng cột dựa trên loại thống kê.
	 *
	 * @param loaiTK Loại thống kê cần áp dụng (Ngày hôm nay, Tháng hiện tại, Năm hiện tại,
	 *               Tùy chỉnh).
	 * @return Đối tượng JFreeChart cho biểu đồ dạng cột.
	 */

	public JFreeChart taoBieuDo(String loaiTK) {
		String xLbl;
		SimpleDateFormat simpleDateFortmat = new SimpleDateFormat("dd-MM-yyyy");
		JFreeChart barChart = null;
		Date now = new Date();
		String date = simpleDateFortmat.format(now);
		if (loaiTK.equalsIgnoreCase("Ngày hôm nay")) {
			xLbl = "Giờ";
			barChart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU NGÀY " + simpleDateFortmat.format(now), xLbl,
					"Doanh thu", getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK), PlotOrientation.VERTICAL, true, false, false);
		}

		else if (loaiTK.equalsIgnoreCase("Tháng hiện tại")) {
			xLbl = "Ngày";
			barChart = ChartFactory.createBarChart(
					"BIỂU ĐỒ DOANH THU THÁNG " + date.split("-")[1] + " NĂM " + date.split("-")[2], xLbl, "Doanh thu",
					getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK), PlotOrientation.VERTICAL, true, false, false);
		}

		else if (loaiTK.equalsIgnoreCase("Năm hiện tại")) {
			xLbl = "Tháng";
			barChart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU NĂM " + date.split("-")[2], xLbl, "Doanh thu",
					getTapDuLieuChoBieuDoThongKeKhac(xLbl, loaiTK), PlotOrientation.VERTICAL, true, false, false);

		}

		else if (loaiTK.equalsIgnoreCase("Tùy chỉnh")) {

			String loaiBaoCao = cmbLoaiBaoCao.getSelectedItem().toString();
			Date ngayBatDauD = ngayBatDau.getDate();
			Date ngayKetThucD = ngayKetThuc.getDate();
			String ngayBatDau = simpleDateFortmat.format(ngayBatDauD);
			String ngayKetThuc = simpleDateFortmat.format(ngayKetThucD);
			if (loaiBaoCao.equalsIgnoreCase("Báo cáo theo ngày")) {
				xLbl = "Ngày";
			} else {
				xLbl = "Tháng";
			}
			barChart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU TỪ " + ngayBatDau + " ĐẾN " + ngayKetThuc, xLbl,
					"Doanh thu", getTapDuLieuChoBieuDoThongKeTuyChinh(ngayBatDauD, ngayKetThucD), PlotOrientation.VERTICAL,
					true, false, false);
		}

		return barChart;
	}

	/**
	 * Xử lý sự kiện khi người dùng chọn thống kê.
	 */

	public void thongKe() {
		String loaiTK = cmbLoaiThongKe.getSelectedItem().toString();
		if (pnChart.isAncestorOf(chartPanel)) {
			pnChart.remove(chartPanel);
		}
		if (loaiTK.equalsIgnoreCase("Tùy chỉnh")) {
			if (!kiemTraDauVaoChoThongKeTuyChinh()) {
				return;
			}
		}
		capNhatDuLieuChoBangThongKe();
		capNhatTongDoanhThuTXT();
		JFreeChart chartDoanhThu = taoBieuDo(loaiTK);
		if (chartDoanhThu == null) {
			JOptionPane.showMessageDialog(null, "Tạo biểu đồ thất bại!");
			return;
		}
		chartPanel = new ChartPanel(chartDoanhThu);

		chartPanel.setBounds(0, 0, 1300, 400);
		pnChart.add(chartPanel);

		pnChart.revalidate();
		pnChart.repaint();
	}

	/**
	 * Phương thức làm mới cho JPanel được truyền vào.
	 *
	 * @param panel là panel truyền vào để làm mới.
	 */

	public void lamMoiPanel(JPanel panel) {
		panel.validate();
		panel.repaint();
	}

	/**
	 * Gỡ bỏ hết tất cả dữ liệu trong bảng thống kê.
	 */

	public void xoaHetDuLieuTrenBangThongKe() {
		DefaultTableModel dm = (DefaultTableModel) tblHoaDon.getModel();
		dm.getDataVector().removeAllElements();
		tblHoaDon.validate();
		tblHoaDon.repaint();
	}

	/**
	 * Thiết lập hiển thị cho tùy chọn ngày tháng của JDateChosser.
	 *
	 * @param isShow là tham số truyền vào true là hiển thị và false là ngược lại.
	 */

	public void setHienThiChoThongKeTuyChinh(boolean isShow) {
		lblNgayBatDau.setVisible(isShow);
		ngayBatDau.setVisible(isShow);
		lblNgayKetThuc.setVisible(isShow);
		ngayKetThuc.setVisible(isShow);
		lblLoaiThoiGian.setVisible(isShow);
		cmbLoaiBaoCao.setVisible(isShow);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThongKe)) {
			if (dataModel.getRowCount() > 0) {
				xoaHetDuLieuTrenBangThongKe();
			}
			thongKe();
		} else if (o.equals(btnXuatExcel)) {

//			Xuất bảng thống kê với tên file tự phát sinh bắt đầu bằng "ThongKeDoanhThuTheoKhachHang...", tên sheet "Bao Cao Doanh Thu Theo Khach Hang"
//			cols là danh sách column của bẳng và dfKhachHang là DefaultTableModel của bảng khách hàng

			if (Function.xuatExcel("ThongKeDoanhThu", "Bao Cao doanh thu", columns, dataModel)) {
				JOptionPane.showMessageDialog(null, "Xuất file excel thành công!");
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
		}
	}
}
