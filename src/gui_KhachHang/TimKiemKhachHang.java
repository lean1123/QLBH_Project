package gui_KhachHang;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.KhachHang;

public class TimKiemKhachHang extends JPanel implements ActionListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblKhachHang;
	private JTextField txtHoTen, txtSDT, txtDiaChi, txtMaKH;
	private JLabel lblKhachHangTitle, lblMaKH, lblHoTen, lblGioiTinh, lblSDT, lblDiaChi;
	private JScrollPane scPanel;
	private JButton btnXoaTrang, btnTimKiem;
	private JComboBox<String> cmbGioiTinh;
	private DefaultTableModel dataModel;
	private KhachHang_DAO dskh;
	private JPanel panel;

	public TimKiemKhachHang() {
		dskh = new KhachHang_DAO();

		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		scPanel = new JScrollPane();
		scPanel.setBounds(114, 282, 746, 309);

		String columns[] = { "Mã khách hàng", "Họ tên", "Giới tính", "Số điện thoại", "Địa chỉ" };
		dataModel = new DefaultTableModel(columns, 0);

		tblKhachHang = new JTable(dataModel);
		scPanel = new JScrollPane(tblKhachHang, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPanel.setViewportView(tblKhachHang);
		scPanel.setBounds(10, 326, 1300, 377);
		add(scPanel);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoaTrang.setBounds(487, 229, 110, 30);
		add(btnXoaTrang);

		btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTimKiem.setBounds(694, 229, 110, 30);
		add(btnTimKiem);
		btnXoaTrang.addActionListener(this);
		btnTimKiem.addActionListener(this);

		tblKhachHang.addMouseListener(this);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Khách Hàng", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(10, 59, 1300, 114);
		add(panel);
		panel.setLayout(null);

		lblMaKH = new JLabel("Mã khách hàng");
		lblMaKH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaKH.setBounds(83, 44, 120, 20);
		panel.add(lblMaKH);

		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSDT.setBounds(722, 47, 96, 20);
		panel.add(lblSDT);

		txtMaKH = new JTextField();
		txtMaKH.setBounds(213, 46, 96, 21);
		panel.add(txtMaKH);

		txtSDT = new JTextField();
		txtSDT.setBounds(828, 48, 138, 19);
		panel.add(txtSDT);
		txtSDT.setColumns(10);

		lblHoTen = new JLabel("Họ tên:");
		lblHoTen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHoTen.setBounds(347, 47, 52, 20);
		panel.add(lblHoTen);

		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiaChi.setBounds(999, 47, 52, 20);
		panel.add(lblDiaChi);

		txtHoTen = new JTextField();
		txtHoTen.setBounds(403, 47, 96, 19);
		panel.add(txtHoTen);
		txtHoTen.setColumns(10);

		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(1061, 47, 156, 19);
		panel.add(txtDiaChi);
		txtDiaChi.setColumns(10);

		lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGioiTinh.setBounds(525, 47, 60, 20);
		panel.add(lblGioiTinh);

		cmbGioiTinh = new JComboBox<>();
		cmbGioiTinh.setBounds(595, 46, 96, 21);
		panel.add(cmbGioiTinh);

				lblKhachHangTitle = new JLabel("TÌM KIẾM KHÁCH HÀNG");
				lblKhachHangTitle.setForeground(Color.BLUE);
				lblKhachHangTitle.setBounds(499, 24, 321, 24);
				add(lblKhachHangTitle);
				lblKhachHangTitle.setHorizontalAlignment(SwingConstants.CENTER);
				lblKhachHangTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");
//		Kết nối cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

//		Load dữ liệu từ database
		capNhatDuLieuChoBang();
	}

	/**
	 * Thực hiện lấy dữ liệu từ cơ sở dữ liệu và hiển thị ra table khách hàng
	 */

	public void capNhatDuLieuChoBang() {
		KhachHang_DAO ds = new KhachHang_DAO();
		String gt;
		for (KhachHang kh : ds.getListKhachHang()) {
			if (kh.isGioiTinh()) {
				gt = "Nam";
			} else {
				gt = "Nữ";
			}
			Object[] row = { kh.getMaKhachHang(), kh.getHoTen(), gt, kh.getSoDienThoai(), kh.getDiaChi() };
			dataModel.addRow(row);
		}
		// Thông báo cho JTable cập nhật dữ liệu
		dataModel.fireTableDataChanged();
	}

	public void xoaTrang() {
		txtMaKH.setText("");
		txtHoTen.setText("");
		cmbGioiTinh.setSelectedItem("Nam");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtHoTen.requestFocus();
		dataModel.getDataVector().removeAllElements();
		capNhatDuLieuChoBang();
	}

	/**
	 * Hàm xử lý sự kiện cho btn Tìm Kiếm Khách Hàng
	 */

	public void timKiemKhachHang() {

		String ma = txtMaKH.getText().toString().trim();
		String ten = txtHoTen.getText();
		boolean gioiTinh;
		if (cmbGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam")) {
			gioiTinh = true;
		} else {
			gioiTinh = false;
		}
		String soDienThoai = txtSDT.getText().toString().trim();
		String diaChi = txtDiaChi.getText().toString().trim();

		List<KhachHang> dstk = dskh.timKiemKhachHang(ma, ten, gioiTinh, soDienThoai, diaChi);
		if (!dstk.isEmpty()) {
			dataModel.getDataVector().removeAllElements();
			String gt;
			for (KhachHang kh : dstk) {
				if (kh.isGioiTinh()) {
					gt = "Nam";
				} else {
					gt = "Nữ";
				}
				Object[] row = { kh.getMaKhachHang(), kh.getHoTen(), gt, kh.getSoDienThoai(), kh.getDiaChi() };
				dataModel.addRow(row);
			}
//			Thông báo cho JTable cập nhật lại dữ liệu
			dataModel.fireTableDataChanged();
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy!");
			int op = JOptionPane.showConfirmDialog(this, "Bạn có muốn thực hiện lại thao tác!",
					"", JOptionPane.YES_NO_OPTION);
			if (op == 0) {
				xoaTrang();
			} else {
				return;
			}
		}
	}

	/**
	 * Xử lý sự kiện khi nhấn nút.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnXoaTrang)) {
			xoaTrang();
		} else if (o.equals(btnTimKiem)) {
			timKiemKhachHang();
		}
	}

	/**
	 * Xử lý sự kiện khi click vào bất kì dòng dữ liệu nào trên bảng khách hàng.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblKhachHang)){
			int row = tblKhachHang.getSelectedRow();
			txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
			txtHoTen.setText(tblKhachHang.getValueAt(row, 1).toString());
			cmbGioiTinh.setSelectedItem(tblKhachHang.getValueAt(row, 2).toString());
			txtDiaChi.setText(tblKhachHang.getValueAt(row, 4).toString());
			txtSDT.setText(tblKhachHang.getValueAt(row, 3).toString());
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
