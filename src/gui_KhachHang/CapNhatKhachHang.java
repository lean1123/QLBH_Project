package gui_KhachHang;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

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
import utils.Contains;

public class CapNhatKhachHang extends JPanel implements ActionListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblKhachHang;
	private JTextField txtMaKH, txtHoTen, txtSDT, txtDiaChi;
	private JLabel lblKhachHang, lblMaKH, lblHoTen, lblGioiTinh, lblSDT, lblDiaChi;
	private JScrollPane scrBangKhachHang;
	private JButton btnThem, btnSua, btnXoaTrang;
	private JComboBox<String> cmbGioiTinh;
	private DefaultTableModel dataModel;
	private KhachHang_DAO dskh;
	private JPanel pnFormNhap;

	public CapNhatKhachHang() {
		dskh = new KhachHang_DAO();
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		scrBangKhachHang = new JScrollPane();
		scrBangKhachHang.setBounds(114, 282, 746, 309);

		String columns[] = { "Mã khách hàng", "Họ tên", "Giới tính", "Số điện thoại", "Địa chỉ" };
		dataModel = new DefaultTableModel(columns, 0);

		tblKhachHang = new JTable(dataModel);
		scrBangKhachHang = new JScrollPane(tblKhachHang, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrBangKhachHang.setViewportView(tblKhachHang);
		scrBangKhachHang.setBounds(10, 326, 1300, 404);
		add(scrBangKhachHang);

		btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThem.setBounds(454, 222, 90, 30);
		add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSua.setBounds(569, 222, 90, 30);
		add(btnSua);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoaTrang.setBounds(684, 222, 110, 30);
		add(btnXoaTrang);

		btnThem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);

		tblKhachHang.addMouseListener(this);

		pnFormNhap = new JPanel();
		pnFormNhap.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Khách Hàng", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormNhap.setBounds(10, 60, 1300, 114);
		add(pnFormNhap);
		pnFormNhap.setLayout(null);

		lblMaKH = new JLabel("Mã khách hàng:");
		lblMaKH.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaKH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaKH.setBounds(39, 44, 107, 27);
		pnFormNhap.add(lblMaKH);

		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSDT.setBounds(708, 48, 96, 18);
		pnFormNhap.add(lblSDT);

		txtMaKH = new JTextField();
		txtMaKH.setBounds(156, 49, 104, 21);
		pnFormNhap.add(txtMaKH);
		txtMaKH.setColumns(10);
		txtMaKH.setEditable(false);

		txtSDT = new JTextField();
		txtSDT.setBounds(814, 49, 143, 21);
		pnFormNhap.add(txtSDT);
		txtSDT.setColumns(10);

		lblHoTen = new JLabel("Họ tên:");
		lblHoTen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHoTen.setBounds(287, 48, 58, 18);
		pnFormNhap.add(lblHoTen);

		txtHoTen = new JTextField();
		txtHoTen.setBounds(355, 49, 115, 21);
		pnFormNhap.add(txtHoTen);
		txtHoTen.setColumns(10);

		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(1062, 49, 156, 21);
		pnFormNhap.add(txtDiaChi);
		txtDiaChi.setColumns(10);

		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiaChi.setBounds(984, 48, 69, 18);
		pnFormNhap.add(lblDiaChi);

		lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGioiTinh.setBounds(499, 48, 71, 18);
		pnFormNhap.add(lblGioiTinh);

		cmbGioiTinh = new JComboBox<>();
		cmbGioiTinh.setBounds(580, 49, 96, 21);
		pnFormNhap.add(cmbGioiTinh);
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");
		cmbGioiTinh.setSelectedItem("Nam");

				lblKhachHang = new JLabel("CẬP NHẬT KHÁCH HÀNG");
				lblKhachHang.setForeground(Color.BLUE);
				lblKhachHang.setBounds(504, 25, 258, 24);
				add(lblKhachHang);
				lblKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
				lblKhachHang.setFont(new Font("Times New Roman", Font.BOLD, 20));

//		Kết nối cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

//		Load dữ liệu khách hàng từ cơ sở dữ liệu
		capNhatDuLieuChoBang();
	}

	/**
	 * Kiểm tra tính hợp lệ của thông tin nhập vào.
	 *
	 * @return true nếu thông tin hợp lệ, ngược lại trả về false.
	 */

	public boolean kiemTraNhapLieu() {
		String tenKH = txtHoTen.getText().toString().trim();
		String diaChi = txtDiaChi.getText().toString().trim();
		String sdt = txtSDT.getText().toString().trim();
		if (tenKH.length() == 0 || (!(tenKH.matches(Contains.HO_TEN_REGEX)))) {
			JOptionPane.showMessageDialog(null,
					"Lỗi: Tên khách hàng không được rỗng và phải viết hoa chữ cái đầu mỗi từ!");
			return false;
		} else if (diaChi.length() == 0 || (!(diaChi.matches(Contains.DIA_CHI_REGEX)))) {
			JOptionPane.showMessageDialog(null, "Lỗi: địa chỉ phải nhập theo 'Số Nhà Tên đường (nếu có), Tên Quận(Huyện), Tên Thành Phố(Tỉnh)'!");
			return false;
		} else if (sdt.length() == 0 || !(sdt.matches(Contains.SDT_REGEX))) {
			JOptionPane.showConfirmDialog(null,
					"Số điện thoại không được rỗng, không quá 10 ký tự và không chứa các ký tự khác số!");
			return false;
		}
		return true;
	}

	/**
	 * Chuyển đổi thông tin từ các trường nhập liệu sang đối tượng KhachHang.
	 *
	 * @return Đối tượng KhachHang được tạo từ các trường nhập liệu.
	 */

	public KhachHang getKhachHangTuNhapLieu() {
		String maKH = txtMaKH.getText().toString();
		String hoTen = txtHoTen.getText().toString();
		String gt = cmbGioiTinh.getSelectedItem().toString();
		boolean gioiTinh;
		if (gt == "Nam") {
			gioiTinh = true;
		} else {
			gioiTinh = false;
		}
		String sdt = txtSDT.getText();
		String diaChi = txtDiaChi.getText();
		return new KhachHang(maKH, hoTen, gioiTinh, sdt, diaChi);
	}

	/**
	 * Chuyển đổi thông tin từ các trường nhập liệu sang đối tượng KhachHang.
	 * @return Đối tượng KhachHang được tạo từ các trường nhập liệu.
	 */

	public void xoaDuLieuCuaBang() {
		dataModel.getDataVector().removeAllElements();
	}

	/**
	 * Xóa tất cả dữ liệu trong TableModel.
	 */

	public void capNhatDuLieuChoBang() {
		KhachHang_DAO ds = new KhachHang_DAO();
		String gt;
		for (KhachHang kh : ds.getListKhachHang()) {
			if (kh.isGioiTinh()) {
				gt = "Nam";
			} else {
				gt = "Nu";
			}
			Object[] row = { kh.getMaKhachHang(), kh.getHoTen(), gt, kh.getSoDienThoai(), kh.getDiaChi() };
			dataModel.addRow(row);
		}
		tblKhachHang.setModel(dataModel);
	}

	/**
	 * Xóa trắng các trường nhập liệu trên giao diện.
	 */

	public void xoaTrang() {
		txtMaKH.setText("");
		txtHoTen.setText("");
		cmbGioiTinh.setSelectedItem("Nam");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtHoTen.requestFocus();
	}

	/**
	 * Thực hiện thêm khách hàng vào cơ sở dữ liệu và cập nhật giao diện.
	 */

	public void themKhachHang() {
		if(kiemTraNhapLieu()) {
			String hoTen = txtHoTen.getText().toString();
			boolean gioiTinh;
			if (cmbGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam")) {
				gioiTinh = true;
			} else {
				gioiTinh = false;
			}
			String sdt = txtSDT.getText().toString();
			String dc = txtDiaChi.getText().toString();
			KhachHang kh = new KhachHang(hoTen, gioiTinh, sdt, dc);
			if(dskh.themKhachHang(kh)) {
//				 Get khách hàng vừa thêm vào database
				KhachHang khUpdated = dskh.getKhachHangVuaCapNhat();
				Object dataRow[] = { khUpdated.getMaKhachHang(), khUpdated.getHoTen(), cmbGioiTinh.getSelectedItem().toString(), khUpdated.getSoDienThoai(),
						khUpdated.getDiaChi() };
				dataModel.addRow(dataRow);
				dataModel.fireTableDataChanged();
				JOptionPane.showMessageDialog(null, "Thêm Thành Công!");
				xoaTrang();
			}else {
				JOptionPane.showMessageDialog(null, "Lỗi! Thêm Không thành công.");
			}
		}else {
			return;
		}
	}

	/**
	 * Thực hiện cập nhật thông tin Khách hàng vào cơ sở dữ liệu và cập nhật giao diện.
	 */

	public void capNhatKhachHang() {
		int row = tblKhachHang.getSelectedRow();
		if (row >= 0) {
			KhachHang kh = getKhachHangTuNhapLieu();
			if (dskh.capNhatKhachHang(kh)) {
				tblKhachHang.setValueAt(txtHoTen.getText().toString(), row, 1);
				tblKhachHang.setValueAt(cmbGioiTinh.getSelectedItem().toString(), row, 2);
				tblKhachHang.setValueAt(txtSDT.getText().toString(), row, 3);
				tblKhachHang.setValueAt(txtDiaChi.getText().toString(), row, 4);
				JOptionPane.showMessageDialog(null, "Cập nhật thành công.");
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Bạn phải chọn khách hàng cần cập nhật!");
		}
	}

	/**
	 * Xử lý sự kiện khi các nút được nhấn.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if(btnThem.getText().toString().equals("Hủy")) {
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
				return;
			}
			btnThem.setText("Hủy");
			btnSua.setText("Lưu");
		} else if (o.equals(btnSua)) {
			if (btnSua.getText().toString().equals("Lưu")) {
				themKhachHang();
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
			} else {
				capNhatKhachHang();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaTrang();
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

	/**
	 * Xử lý sự kiện khi một dòng trên bảng khách hàng được nhấp chuột.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tblKhachHang.getSelectedRow();
		txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
		txtHoTen.setText(tblKhachHang.getValueAt(row, 1).toString());
		cmbGioiTinh.setSelectedItem(tblKhachHang.getValueAt(row, 2).toString());
		txtSDT.setText(tblKhachHang.getValueAt(row, 3).toString());
		txtDiaChi.setText(tblKhachHang.getValueAt(row, 4).toString());
	}

}
