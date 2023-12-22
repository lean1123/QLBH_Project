package gui_NhaCungCap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

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

import connectDB.ConnectDB;
import dao.NhaCungCap_DAO;
import entity.NhaCungCap;
import utils.Contains;

public class CapNhatNhaCungCap extends JPanel implements ActionListener, MouseListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNhaCungCap;
	private JTextField txtMaNhaCungCap;
	private JTextField txtTenNhaCungCap;
	private JTextField txtSoDienThoai;
	private JTextField txtEmail;
	private JTextField txtDiaChi;
	private JTable tblNhaCungCap;
	private DefaultTableModel dataModel;
	private JScrollPane scrTableNhaCungCap;
	private NhaCungCap_DAO dsNhaCungCap;
	private JButton btnThem, btnSua, btnXoaTrang;
	private JLabel lblRegexMessege;

	public CapNhatNhaCungCap() {
		dsNhaCungCap = new NhaCungCap_DAO();
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Nhà Cung Cấp", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 85, 1300, 156);
		add(panel);
		panel.setLayout(null);

		JLabel lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
		lblMaNhaCungCap.setBounds(209, 24, 105, 20);
		panel.add(lblMaNhaCungCap);
		lblMaNhaCungCap.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtMaNhaCungCap = new JTextField();
		txtMaNhaCungCap.setBounds(334, 24, 122, 33);
		panel.add(txtMaNhaCungCap);
		txtMaNhaCungCap.setColumns(10);

		JLabel lblTnNhCung = new JLabel("Tên nhà cung cấp:");
		lblTnNhCung.setBounds(485, 24, 134, 20);
		panel.add(lblTnNhCung);
		lblTnNhCung.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtTenNhaCungCap = new JTextField();
		txtTenNhaCungCap.setBounds(629, 25, 122, 33);
		panel.add(txtTenNhaCungCap);
		txtTenNhaCungCap.setColumns(10);

		JLabel lblSinThoi = new JLabel("Số điện thoại");
		lblSinThoi.setBounds(787, 24, 105, 20);
		panel.add(lblSinThoi);
		lblSinThoi.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setBounds(902, 25, 156, 33);
		panel.add(txtSoDienThoai);
		txtSoDienThoai.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEmail.setBounds(209, 89, 105, 20);
		panel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(334, 89, 257, 33);
		panel.add(txtEmail);

		JLabel lblaCh = new JLabel("Địa chỉ:");
		lblaCh.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblaCh.setBounds(629, 89, 97, 20);
		panel.add(lblaCh);

		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(752, 89, 304, 33);
		panel.add(txtDiaChi);

		btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnThem.setBounds(422, 303, 110, 30);
		add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnSua.setBounds(568, 303, 110, 30);
		add(btnSua);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXoaTrang.setBounds(718, 303, 110, 30);
		add(btnXoaTrang);

		String colHeader[] = { "Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Email", "Địa chỉ" };

		dataModel = new DefaultTableModel(colHeader, 0);
		tblNhaCungCap = new JTable(dataModel);
		scrTableNhaCungCap = new JScrollPane(tblNhaCungCap, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrTableNhaCungCap.setViewportView(tblNhaCungCap);
		scrTableNhaCungCap.setBounds(10, 394, 1300, 390);
		add(scrTableNhaCungCap);

//		Kết nối cơ dở dữ liệu

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		capNhatDuLieuChoBang();
		tblNhaCungCap.addMouseListener(this);
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		txtMaNhaCungCap.setEditable(false);

		lblRegexMessege = new JLabel("Nhập đầy đủ thông tin nhà cung cấp");
		lblRegexMessege.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		lblRegexMessege.setBounds(901, 133, 304, 13);
		panel.add(lblRegexMessege);

				txtNhaCungCap = new JTextField();
				txtNhaCungCap.setForeground(Color.BLUE);
				txtNhaCungCap.setBounds(537, 27, 228, 48);
				add(txtNhaCungCap);
				txtNhaCungCap.setBackground(new Color(255, 255, 255));
				txtNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
				txtNhaCungCap.setFont(new Font("Times New Roman", Font.BOLD, 20));
				txtNhaCungCap.setText("Cập Nhật Nhà Cung Cấp");
				txtNhaCungCap.setColumns(10);
	}

	/**
	 * Phương thức kiểm tra dữ liệu nhập vào từ người dùng.
	 *
	 * @return true nếu dữ liệu hợp lệ, ngược lại là false.
	 */

	public boolean kiemTraNhapLieu() {
		String tenNhaCungCap = txtTenNhaCungCap.getText();
		String dc = txtDiaChi.getText();
		String email = txtEmail.getText();
		String sdt = txtSoDienThoai.getText();
		if (tenNhaCungCap.length() == 0 || (!(tenNhaCungCap.matches(Contains.HO_TEN_REGEX)))) {
			JOptionPane.showMessageDialog(null, "Tên nhà cung cấp phải viết hoa chữ cái đầu của mỗi từ!");
			return false;
		} else if (dc.length() == 0 || (!(dc.matches(Contains.DIA_CHI_REGEX)))) {
			JOptionPane.showMessageDialog(null, "Lỗi: địa chỉ phải nhập theo 'Số Nhà Tên đường (nếu có), Tên Quận(Huyện), Tên Thành Phố(Tỉnh)'!");
			return false;
		} else if (email.length() == 0 || !(email.matches(Contains.EMAIL_REGEX))) {
			JOptionPane.showMessageDialog(null, "Email phải được nhập theo mẫu sau: EmaiA@example.com!");
			return false;
		} else if (sdt.length() == 0 || !(sdt.matches(Contains.SDT_REGEX))) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không quá 10 ký tự và không chứa các ký tự khác số!");
			return false;
		}
		return true;
	}

	/**
	 * Phương thức chuyển đổi thông tin nhà cung cấp từ các ô nhập liệu thành một
	 * đối tượng NhaCungCap.
	 *
	 * @return Đối tượng NhaCungCap được tạo từ dữ liệu nhập vào.
	 */

	public NhaCungCap taoNhaCungCapTuNhapLieu() {
		String maNhaCungCap = txtMaNhaCungCap.getText();
		String tenNhaCungCap = txtTenNhaCungCap.getText();
		String dc = txtDiaChi.getText();
		String email = txtEmail.getText();
		String sdt = txtSoDienThoai.getText();
		return new NhaCungCap(maNhaCungCap, tenNhaCungCap, dc, email, sdt);
	}

	/**
	 * Phương thức xóa hết dữ liệu trên TableModel.
	 */

	public void xoaHetDuLieuTrenBang() {
		dataModel.getDataVector().removeAllElements();
	}

	/**
	 * Cập nhật dữ liệu trên bảng từ danh sách nhà cung cấp.
	 */

	public void capNhatDuLieuChoBang() {
		NhaCungCap_DAO ds = new NhaCungCap_DAO();
		for (NhaCungCap ncc : ds.getListNhaCungCap()) {
			Object[] row = { ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSoDienThoai(), ncc.getEmail(),
					ncc.getDiaChi() };
			dataModel.addRow(row);
		}
		tblNhaCungCap.setModel(dataModel);
	}

	/**
	 * Xóa sạch thông tin trên giao diện của form nhập nhà cung cấp.
	 */

	public void xoaTrang() {
		txtMaNhaCungCap.setText("");
		txtTenNhaCungCap.setText("");
		txtDiaChi.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		lblRegexMessege.setText("Nhập đầy đủ thông tin nhà cung cấp");
		lblRegexMessege.setForeground(Color.BLACK);
		txtTenNhaCungCap.requestFocus();
	}

	/**
	 * Thực hiện thêm thông tin nhà cung cấp vào CSDL và cập nhật giao diện.
	 */

	public void themNhaCungCap() {
		if (kiemTraNhapLieu()) {
			String hoTen = txtTenNhaCungCap.getText().toString();
			String dc = txtDiaChi.getText().toString();
			String email = txtEmail.getText().toString();
			String sdt = txtSoDienThoai.getText().toString();
			NhaCungCap NhaCungCap = new NhaCungCap(hoTen, dc, email, sdt);
			if (dsNhaCungCap.themNhaCungCap(NhaCungCap)) {
				NhaCungCap nccVuaThem = dsNhaCungCap.getNhaCungCapVuaCapNhat();
				Object dataRow[] = { nccVuaThem.getMaNhaCungCap(), nccVuaThem.getTenNhaCungCap(),
						nccVuaThem.getSoDienThoai(), nccVuaThem.getEmail(), nccVuaThem.getDiaChi() };
				dataModel.addRow(dataRow);
				dataModel.fireTableDataChanged();
				JOptionPane.showMessageDialog(null, "Thêm thành công!");
				xoaTrang();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Thêm không thành công!");
			}
		}
	}

	/**
	 * Thực hiện cập nhật thông tin nhà cung cấp vào CSDL và cập nhật giao diện.
	 */

	public void capNhatNhaCungCap() {
		int row = tblNhaCungCap.getSelectedRow();
		if (row >= 0) {
			NhaCungCap NhaCungCap = taoNhaCungCapTuNhapLieu();
			if (dsNhaCungCap.capNhatNhaCungCap(NhaCungCap)) {
				tblNhaCungCap.setValueAt(txtTenNhaCungCap.getText().toString(), row, 1);
				tblNhaCungCap.setValueAt(txtSoDienThoai.getText().toString(), row, 2);
				tblNhaCungCap.setValueAt(txtEmail.getText().toString(), row, 3);
				tblNhaCungCap.setValueAt(txtDiaChi.getText().toString(), row, 4);
				JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công");
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp không thành công!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Bạn phải chọn nhà cung cấp cần cập nhật!");
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
			if (btnThem.getText().toString().equals("Hủy")) {
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
				return;
			}
			btnThem.setText("Hủy");
			btnSua.setText("Lưu");
		} else if (o.equals(btnSua)) {
			if (btnSua.getText().toString().equals("Lưu")) {
				themNhaCungCap();
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
			} else {
				capNhatNhaCungCap();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaTrang();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tblNhaCungCap.getSelectedRow();
		txtMaNhaCungCap.setText(dataModel.getValueAt(row, 0).toString());
		txtTenNhaCungCap.setText(dataModel.getValueAt(row, 1).toString());
		txtSoDienThoai.setText(dataModel.getValueAt(row, 2).toString());
		txtEmail.setText(dataModel.getValueAt(row, 3).toString());
		txtDiaChi.setText(dataModel.getValueAt(row, 4).toString());
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
