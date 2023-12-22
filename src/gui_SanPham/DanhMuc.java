package gui_SanPham;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import connectDB.ConnectDB;
import dao.SanPham_DAO;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class DanhMuc extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnQuanLyDanhMuc;
	private JLabel lblTieuDe, lblTenDanhMuc, lblDanhSachDanhMuc;
	private JTextField txtTenDanhMuc;
	private JButton btnXoaTrang, btnLuu, btnThem;
	private JTable tblDanhMuc;
	private DefaultTableModel modelTBLDanhMuc;
	private JScrollPane scrPanel;

	private SanPham_DAO dssp;

	/**
	 * Create the panel.
	 */
	public DanhMuc() {
		// kết nối
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dssp = new SanPham_DAO();

		// tạo GUI
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, 1332, 797);

		lblTieuDe = new JLabel("QUẢN LÝ DANH MỤC");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);

		pnQuanLyDanhMuc = new JPanel();
		pnQuanLyDanhMuc.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnQuanLyDanhMuc.setBackground(new Color(255, 255, 255));
		pnQuanLyDanhMuc.setBounds(269, 82, 696, 137);
		add(pnQuanLyDanhMuc);
		pnQuanLyDanhMuc.setLayout(null);

		lblTenDanhMuc = new JLabel("Tên danh mục ");
		lblTenDanhMuc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenDanhMuc.setBounds(210, 20, 125, 24);
		pnQuanLyDanhMuc.add(lblTenDanhMuc);

		txtTenDanhMuc = new JTextField();
		txtTenDanhMuc.setColumns(10);
		txtTenDanhMuc.setBounds(337, 19, 196, 31);
		pnQuanLyDanhMuc.add(txtTenDanhMuc);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(445, 78, 123, 31);
		pnQuanLyDanhMuc.add(btnXoaTrang);

		btnLuu = new JButton("Sửa");
		btnLuu.setBounds(312, 78, 123, 31);
		pnQuanLyDanhMuc.add(btnLuu);

		lblDanhSachDanhMuc = new JLabel("Danh sách danh mục");
		lblDanhSachDanhMuc.setOpaque(true);
		lblDanhSachDanhMuc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachDanhMuc.setForeground(new Color(0, 0, 0));
		lblDanhSachDanhMuc.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachDanhMuc.setBackground(Color.WHITE);
		lblDanhSachDanhMuc.setBounds(468, 334, 338, 44);
		add(lblDanhSachDanhMuc);

		String columns[] = { "Tên danh mục" };
		modelTBLDanhMuc = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblDanhMuc = new JTable(modelTBLDanhMuc);
		scrPanel = new JScrollPane(tblDanhMuc, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblDanhMuc);
		scrPanel.setBounds(106, 388, 1075, 292);
		add(scrPanel);

		int fontSize = 16;
		Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
		tblDanhMuc.setFont(newFont);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(179, 78, 123, 31);
		pnQuanLyDanhMuc.add(btnThem);

		utils.Format.setButtonEvent(btnLuu, btnXoaTrang, btnThem);

		btnXoaTrang.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		tblDanhMuc.addMouseListener(this);
		docDuLieuTuDatabase();

	}

	// đọc dữ liệu từ database sau đó add vào table
	public void docDuLieuTuDatabase() {
		for (String danhMuc : dssp.getDanhSachDanhMuc()) {
			Object[] row = { danhMuc };
			modelTBLDanhMuc.addRow(row);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnXoaTrang)) {
			xoaTrang();
		} else if (o.equals(btnThem)) {
			if (btnThem.getText().trim().equals("Hủy")) {
				btnLuu.setText("Sửa");
				btnThem.setText("Thêm");
				xoaTrang();
				return;
			} else {
				btnLuu.setText("Lưu");
				btnThem.setText("Hủy");
			}
		} else if (o.equals(btnLuu)) {
			if (btnLuu.getText().toString().equals("Lưu")) {
				luuAction(); // Thực hiện lưu mới
				btnLuu.setText("Sửa");
				btnThem.setText("Thêm");
			} else {
				capNhatDuLieu(); // Nếu có, thực hiện cập nhật dữ liệu
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tblDanhMuc.getSelectedRow();
		txtTenDanhMuc.setText(tblDanhMuc.getValueAt(row, 0).toString()); // nhấn vào hàng nào thì dữ liệu sẽ được hiển
																			// thị lên textField
		btnThem.setEnabled(false);
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

	// kiểm tra dữ liệu nhập vào textField
	private boolean kiemTraDuLieu() {
		String tenDM = txtTenDanhMuc.getText().trim();

		// kiểm tra để trống
		if (tenDM.length() == 0) {
			JOptionPane.showMessageDialog(null, "Tên không được bỏ trống");
			txtTenDanhMuc.requestFocus();
			return false;

			// kiểm tra dữ liệu nhập vào đúng định dạng tiếng Việt không
		} else if (!(tenDM.length() > 0 && tenDM.matches("^[\\p{L}À-ỹđĐ ]*\\b$"))) {

			JOptionPane.showMessageDialog(null, "Tên danh mục phải là chữ, không chứa ký tự đặc biệt ");
			txtTenDanhMuc.requestFocus();
			return false;
		}
		return true;
	}

	// xóa trắng và bỏ chọn hàng trên table
	private void xoaTrang() {
		txtTenDanhMuc.setText("");
		txtTenDanhMuc.requestFocus();
		tblDanhMuc.clearSelection();
		btnThem.setEnabled(true);
	}

	// lưu dữ liệu mới
	private void luuAction() {
		String tenDM = txtTenDanhMuc.getText().trim().toString();
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.themDanhMuc(tenDM)) { // đúng thì thêm mới
				String ketQua = dssp.layDuLieuCotDanhMuc();
				Object[] row = { ketQua };
				modelTBLDanhMuc.addRow(row); // lấy tên dữ liệu mới thêm vào và add vào table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Thêm thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên danh mục bị trùng");
			}
		}
	}

	// cập nhật dữ liệu
	private void capNhatDuLieu() {
		String tenDMCu = tblDanhMuc.getValueAt(tblDanhMuc.getSelectedRow(), 0).toString(); // lấy từ table
		String tenDMMoi = txtTenDanhMuc.getText().trim().toString(); // lấy từ textField
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.capNhatDanhMuc(tenDMCu, tenDMMoi)) { // thực hiện cập nhật bằng hàm cập nhật
				int row = tblDanhMuc.getSelectedRow();
				modelTBLDanhMuc.setValueAt(tenDMMoi, row, 0); // sửa lại dữ liệu hiển thị trên table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên danh mục bị trùng");
			}
		}
	}
}
