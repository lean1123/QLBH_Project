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

public class MauSac extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnQuanLyMauSac;
	private JLabel lblTieuDe, lblTenMauSac, lblDanhSchDanh;
	private JTextField txtTenMauSac;
	private JButton btnXoaTrang, btnLuu, btnThem;
	private JTable tblMauSac;
	private DefaultTableModel modelTBLMauSac;
	private JScrollPane scrPanel;

	private SanPham_DAO dssp;

	/**
	 * Create the panel.
	 */
	public MauSac() {
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

		lblTieuDe = new JLabel("QUẢN LÝ MÀU SẮC");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);

		pnQuanLyMauSac = new JPanel();
		pnQuanLyMauSac.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnQuanLyMauSac.setBackground(new Color(255, 255, 255));
		pnQuanLyMauSac.setBounds(269, 82, 696, 137);
		add(pnQuanLyMauSac);
		pnQuanLyMauSac.setLayout(null);

		lblTenMauSac = new JLabel("Tên màu sắc ");
		lblTenMauSac.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenMauSac.setBounds(210, 20, 117, 24);
		pnQuanLyMauSac.add(lblTenMauSac);

		txtTenMauSac = new JTextField();
		txtTenMauSac.setColumns(10);
		txtTenMauSac.setBounds(321, 19, 196, 31);
		pnQuanLyMauSac.add(txtTenMauSac);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(445, 78, 123, 31);
		pnQuanLyMauSac.add(btnXoaTrang);

		btnLuu = new JButton("Sửa");
		btnLuu.setBounds(312, 78, 123, 31);
		pnQuanLyMauSac.add(btnLuu);

		lblDanhSchDanh = new JLabel("Danh sách màu sắc");
		lblDanhSchDanh.setOpaque(true);
		lblDanhSchDanh.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchDanh.setForeground(new Color(0, 0, 0));
		lblDanhSchDanh.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSchDanh.setBackground(Color.WHITE);
		lblDanhSchDanh.setBounds(468, 334, 338, 44);
		add(lblDanhSchDanh);

		String columns[] = { "Tên màu sắc" };
		modelTBLMauSac = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblMauSac = new JTable(modelTBLMauSac);
		scrPanel = new JScrollPane(tblMauSac, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblMauSac);
		scrPanel.setBounds(106, 388, 1075, 292);
		add(scrPanel);

		int fontSize = 16;
		Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
		tblMauSac.setFont(newFont);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(179, 78, 123, 31);
		pnQuanLyMauSac.add(btnThem);

		utils.Format.setButtonEvent(btnLuu, btnXoaTrang, btnThem);

		btnXoaTrang.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		tblMauSac.addMouseListener(this);
		docDuLieuTuDatabase();
	}

	// đọc dữ liệu từ database sau đó add vào table
	public void docDuLieuTuDatabase() {
		for (String mauSac : dssp.getDanhSachMauSac()) {
			Object[] row = { mauSac };

			modelTBLMauSac.addRow(row);
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
		int row = tblMauSac.getSelectedRow();
		txtTenMauSac.setText(tblMauSac.getValueAt(row, 0).toString()); // nhấn vào hàng nào thì dữ liệu sẽ được hiển
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
		String tenMS = txtTenMauSac.getText().trim();

		// kiểm tra để trống
		if (tenMS.length() == 0) {
			JOptionPane.showMessageDialog(null, "Tên không được bỏ trống");
			txtTenMauSac.requestFocus();
			return false;

			// kiểm tra dữ liệu nhập vào đúng định dạng tiếng Việt không
		} else if (!(tenMS.length() > 0 && tenMS.matches("^[\\p{L}À-ỹđĐ ]*\\b$"))) {

			JOptionPane.showMessageDialog(null, "Tên màu sắc phải viết hoa chữ cái đầu ");
			txtTenMauSac.requestFocus();
			return false;
		}
		return true;
	}

	// xóa trắng và bỏ chọn hàng trên table
	private void xoaTrang() {
		txtTenMauSac.setText("");
		txtTenMauSac.requestFocus();
		tblMauSac.clearSelection();
		btnThem.setEnabled(true);
	}

	// lưu dữ liệu mới
	private void luuAction() {
		String tenMS = txtTenMauSac.getText().trim().toString();
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.themMauSac(tenMS)) { // đúng thì thêm mới
				String ketQua = dssp.layDuLieuCotMauSac();
				Object[] row = { ketQua };
				modelTBLMauSac.addRow(row); // lấy tên dữ liệu mới thêm vào và add vào table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Thêm thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên màu sắc bị trùng");
			}
		}
	}

	// cập nhật dữ liệu
	private void capNhatDuLieu() {
		String tenMSCu = tblMauSac.getValueAt(tblMauSac.getSelectedRow(), 0).toString(); // lấy từ table
		String tenMSMoi = txtTenMauSac.getText().trim().toString(); // lấy từ textField
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.capNhatMauSac(tenMSCu, tenMSMoi)) { // thực hiện cập nhật bằng hàm cập nhật
				int row = tblMauSac.getSelectedRow();
				modelTBLMauSac.setValueAt(tenMSMoi, row, 0); // sửa lại dữ liệu hiển thị trên table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên màu sắc bị trùng");
			}
		}
	}
}
