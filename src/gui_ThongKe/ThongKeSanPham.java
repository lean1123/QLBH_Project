package gui_ThongKe;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietHoaDon_Dao;
import dao.SanPham_DAO;
import entity.SanPham;
import utils.Function;

import org.jfree.chart.ChartFactory;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongKeSanPham extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblTieuDe, lblTieuDeBieuDo, lblLoaiThongKe, lblNgayBatDau, lblNgayKetThuc;
	private JPanel pnBieuDo;
	private JComboBox cbbLoaiThongKe;
	private JButton btnXuatExcel, btnThongKe;
	private JDateChooser dateChooserBatDau;
	private JDateChooser dateChooserKetThuc;
	private JTable tblSanPham;
	private DefaultTableModel modelTBLSanPham;
	private JScrollPane scrPanel;
	private SanPham_DAO dssp;
	private ChiTietHoaDon_Dao dscthd;
	private String columns[];

	/**
	 * Create the panel.
	 */
	public ThongKeSanPham() {
		// kết nối với cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dssp = new SanPham_DAO();
		dscthd = new ChiTietHoaDon_Dao();
		// Tạo GUI
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);

		lblTieuDe = new JLabel("THỐNG KÊ SẢN PHẨM");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 0, 338, 44);
		add(lblTieuDe);

		pnBieuDo = new JPanel();
		pnBieuDo.setBounds(40, 107, 1201, 375);
		add(pnBieuDo);
		pnBieuDo.setLayout(null);

		columns = new String[] { "Mã sản phẩm", "Tên sản phẩm", "Số lượng bán", "Số lượng tồn", "Đơn giá", "Danh mục",
				"Kích cỡ", "Chất liệu", "Màu sắc", "Nhà cung cấp" };
		modelTBLSanPham = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblSanPham = new JTable(modelTBLSanPham);
		scrPanel = new JScrollPane(tblSanPham, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblSanPham);
		scrPanel.setBounds(10, 539, 1298, 237);
		add(scrPanel);

		lblLoaiThongKe = new JLabel("Loại thống kê");
		lblLoaiThongKe.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLoaiThongKe.setBounds(24, 51, 108, 23);
		add(lblLoaiThongKe);

		cbbLoaiThongKe = new JComboBox();
		cbbLoaiThongKe.setBounds(128, 53, 120, 21);
		cbbLoaiThongKe.addItem("Số Lượng Bán");
		cbbLoaiThongKe.addItem("Số Lượng Tồn");
		add(cbbLoaiThongKe);

		btnXuatExcel = new JButton("Xuất excel");
		btnXuatExcel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXuatExcel.setBounds(65, 503, 101, 26);
		add(btnXuatExcel);

		btnThongKe = new JButton("Thống kê");
		btnThongKe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThongKe.setBounds(1098, 46, 101, 34);
		add(btnThongKe);

		lblNgayBatDau = new JLabel("Ngày bắt đầu");
		lblNgayBatDau.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNgayBatDau.setBounds(363, 50, 108, 23);
		add(lblNgayBatDau);

		dateChooserBatDau = new JDateChooser();
		dateChooserBatDau.setDateFormatString("dd-MM-yyyy"); // Set your desired date format
		dateChooserBatDau.setBounds(481, 50, 150, 23);
		add(dateChooserBatDau);

		lblNgayKetThuc = new JLabel("Ngày kết thúc");
		lblNgayKetThuc.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNgayKetThuc.setBounds(698, 51, 108, 23);
		add(lblNgayKetThuc);

		dateChooserKetThuc = new JDateChooser();
		dateChooserKetThuc.setDateFormatString("dd-MM-yyyy"); // Set your desired date format
		dateChooserKetThuc.setBounds(826, 51, 150, 23);
		add(dateChooserKetThuc);

		utils.Format.setButtonEvent(btnThongKe, btnXuatExcel);

		btnThongKe.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		cbbLoaiThongKe.addActionListener(this);

	}



	// tạo biểu đồ cột với các dữ liệu và tiêu đề chỉ định
	public JFreeChart createBarChart(CategoryDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createBarChart(title, "Tên sản phẩm", "Số lượng", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		// Xoay chú thích (legend)
		chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 10));

		// Xoay trục x
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0));

		return chart;
	}

	public void updateChart(String type) {
		JFreeChart updatedChart;
		if (type.equalsIgnoreCase("Số Lượng Bán")) {
			Date ngayBatDauDate = dateChooserBatDau.getDate();
			LocalDate ngayBatDauLocalDate = DateToLocalDateConverter.convertToLocalDate(ngayBatDauDate);
			Date ngayKetThucDate = dateChooserKetThuc.getDate();
			LocalDate ngayKetThucLocalDate = DateToLocalDateConverter.convertToLocalDate(ngayKetThucDate);
			updatedChart = createBarChart(createBarDataset(
					dscthd.getDanhSachSanPhamGuiThongKeSanPham2(ngayBatDauLocalDate, ngayKetThucLocalDate), type),
					"THỐNG KÊ " + type.toUpperCase());
		} else {
			updatedChart = createBarChart(createBarDataset(dssp.getDanhSachSanPham(), type),
					"THỐNG KÊ " + type.toUpperCase());
		}

		pnBieuDo.removeAll();
		ChartPanel updatedChartPanel = new ChartPanel(updatedChart);
		updatedChartPanel.setBounds(0, 0, 1200, 375);
		pnBieuDo.add(updatedChartPanel);
		pnBieuDo.revalidate();
		pnBieuDo.repaint();
	}

	
	public CategoryDataset createBarDataset(List<SanPham> list, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (SanPham sanPham : list) {
			if (type.equals("Số lượng bán")) {

				dataset.addValue(sanPham.getSoLuongBan(), "Số lượng bán", sanPham.getTenSanPham());
			} else if (type.equals("Số lượng tồn")) {
				dataset.addValue(sanPham.getSoLuongTon(), "Số lượng tồn", sanPham.getTenSanPham());
			}
		}
		return dataset;
	}

	// load dữ liệu từ database lên bảng cho thống kê số lượng tồn
	public void docDuLieuDatabaseChoSoLuongTon(List<SanPham> list) {
		modelTBLSanPham.setRowCount(0);
		DecimalFormat formatter = new DecimalFormat("###,###.###");
		for (SanPham sanPham : list) {
			Object[] row = { sanPham.getMaSanPham(), sanPham.getTenSanPham(), "---", sanPham.getSoLuongTon(),
					formatter.format(sanPham.getGiaBan()), sanPham.getDanhMuc(), sanPham.getKichCo(),
					sanPham.getChatLieu(), sanPham.getMauSac(), sanPham.getNhaCungCap().getTenNhaCungCap(), };
			modelTBLSanPham.addRow(row);
		}
	}
	
	// load dữ liệu từ database lên bảng cho thống kê số lượng bán
	public void docDuLieuDatabaseChoSoLuongBan(List<SanPham> list) {
		modelTBLSanPham.setRowCount(0);
		DecimalFormat formatter = new DecimalFormat("###,###.###");
		for (SanPham sanPham : list) {
			Object[] row = { sanPham.getMaSanPham(), sanPham.getTenSanPham(), sanPham.getSoLuongBan(), "--",
					formatter.format(sanPham.getGiaBan()), sanPham.getDanhMuc(), sanPham.getKichCo(),
					sanPham.getChatLieu(), sanPham.getMauSac(), sanPham.getNhaCungCap().getTenNhaCungCap(), };
			modelTBLSanPham.addRow(row);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThongKe)) {
			String selectedOption = cbbLoaiThongKe.getSelectedItem().toString().trim();
			if (selectedOption.equalsIgnoreCase("Số Lượng Tồn")) {
				docDuLieuDatabaseChoSoLuongTon(dssp.getDanhSachSanPhamGuiThongKeSanPham());
				updateChart("Số lượng tồn");

			} else if (selectedOption.equalsIgnoreCase("Số Lượng Bán")) {
				Date ngayBatDauDate = dateChooserBatDau.getDate();
				Date ngayKetThucDate = dateChooserKetThuc.getDate();
				LocalDate ngayBatDauLocalDate;
				LocalDate ngayKetThucLocalDate;
				if(ngayBatDauDate==null || ngayKetThucDate==null) {
					JOptionPane.showMessageDialog(null, "Hãy nhập đủ ngày bắt đầu và ngày kết thúc");
				} else {
					ngayBatDauLocalDate = DateToLocalDateConverter.convertToLocalDate(ngayBatDauDate);
					ngayKetThucLocalDate = DateToLocalDateConverter.convertToLocalDate(ngayKetThucDate);
					if(kiemTraNgay(ngayBatDauLocalDate, ngayKetThucLocalDate)) {
						docDuLieuDatabaseChoSoLuongBan(dscthd.getDanhSachSanPhamGuiThongKeSanPham2(ngayBatDauLocalDate, ngayKetThucLocalDate));
						updateChart("Số lượng bán");
					}
				}
				
				
				

			}
		} else if (o.equals(btnXuatExcel)) {
			if (Function.xuatExcel("ThongKeSanPham", "THONG KE SO LUONG SAN PHAM", columns, modelTBLSanPham)) {
				JOptionPane.showMessageDialog(null, "Xuất file thành công!");
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Xuất file không thành công!");
			}
		} else if (o.equals(cbbLoaiThongKe)) {
			String selectedOption = cbbLoaiThongKe.getSelectedItem().toString().trim();
			if (selectedOption.equalsIgnoreCase("Số Lượng Tồn")) {
				lblNgayBatDau.setVisible(false);
				lblNgayKetThuc.setVisible(false);
				dateChooserBatDau.setVisible(false);
				dateChooserKetThuc.setVisible(false);
			} else if (selectedOption.equalsIgnoreCase("Số Lượng Bán")) {
				lblNgayBatDau.setVisible(true);
				lblNgayKetThuc.setVisible(true);
				dateChooserBatDau.setVisible(true);
				dateChooserKetThuc.setVisible(true);
			}
		}

	}

	public class DateToLocalDateConverter {
		public static LocalDate convertToLocalDate(Date dateToConvert) {
			return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}

	public boolean kiemTraNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		LocalDate now = LocalDate.now();
		if (ngayBatDau.isAfter(now)) {
			JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước ngày hiện tại");
			return false;
		} else if (ngayBatDau.isAfter(ngayKetThuc)) {
			JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu");
			return false;
		} 
		return true;
	}
}