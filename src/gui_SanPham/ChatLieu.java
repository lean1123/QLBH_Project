
package gui_SanPham;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.SanPham_DAO;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class ChatLieu extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnQuanLyChatLieu;
	private JLabel lblTieuDe, lblTenDanhMuc, lblDanhSachChatLieu;
	private JTextField txtTenChatLieu;
	// private JButton btnXoa,btnXoaTrang;
	private JButton btnXoaTrang, btnLuu, btnThem;
	private JTable tblChatLieu;
	private DefaultTableModel modelTBLChatLieu;
	private JScrollPane scrPanel;

	private SanPham_DAO dssp;

	/**
	 * Create the panel.
	 */
	public ChatLieu() {
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

		lblTieuDe = new JLabel("QUẢN LÝ CHẤT LIỆU");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);

		pnQuanLyChatLieu = new JPanel();
		pnQuanLyChatLieu.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnQuanLyChatLieu.setBackground(new Color(255, 255, 255));
		pnQuanLyChatLieu.setBounds(269, 82, 696, 137);
		add(pnQuanLyChatLieu);
		pnQuanLyChatLieu.setLayout(null);

		lblTenDanhMuc = new JLabel("Tên chất liệu");
		lblTenDanhMuc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenDanhMuc.setBounds(210, 20, 117, 24);
		pnQuanLyChatLieu.add(lblTenDanhMuc);

		txtTenChatLieu = new JTextField();
		txtTenChatLieu.setColumns(10);
		txtTenChatLieu.setBounds(321, 19, 196, 31);
		pnQuanLyChatLieu.add(txtTenChatLieu);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(445, 78, 123, 31);
		pnQuanLyChatLieu.add(btnXoaTrang);

		btnLuu = new JButton("Sửa");
		btnLuu.setBounds(312, 78, 123, 31);
		pnQuanLyChatLieu.add(btnLuu);

		lblDanhSachChatLieu = new JLabel("Danh sách chất liệu");
		lblDanhSachChatLieu.setOpaque(true);
		lblDanhSachChatLieu.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachChatLieu.setForeground(new Color(0, 0, 0));
		lblDanhSachChatLieu.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachChatLieu.setBackground(Color.WHITE);
		lblDanhSachChatLieu.setBounds(468, 334, 338, 44);
		add(lblDanhSachChatLieu);

		String columns[] = { "Tên chất liệu" };
		modelTBLChatLieu = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblChatLieu = new JTable(modelTBLChatLieu);
		scrPanel = new JScrollPane(tblChatLieu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblChatLieu);
		scrPanel.setBounds(106, 388, 1075, 292);
		add(scrPanel);

		int fontSize = 16;
		Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
		tblChatLieu.setFont(newFont);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(179, 78, 123, 31);
		pnQuanLyChatLieu.add(btnThem);

		utils.Format.setButtonEvent(btnLuu, btnXoaTrang, btnThem);

		btnXoaTrang.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		tblChatLieu.addMouseListener(this);

		docDuLieuTuDatabase();
	}

	// đọc dữ liệu từ database sau đó add vào table
	public void docDuLieuTuDatabase() {
		for (String chatLieu : dssp.getDanhSachChatLieu()) {
			Object[] row = { chatLieu };
			modelTBLChatLieu.addRow(row);
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
		int row = tblChatLieu.getSelectedRow();
		txtTenChatLieu.setText(tblChatLieu.getValueAt(row, 0).toString());
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
		String tenCL = txtTenChatLieu.getText().trim();

		// kiểm tra để trống
		if (tenCL.length() == 0) {
			JOptionPane.showMessageDialog(null, "Tên không được bỏ trống");
			txtTenChatLieu.requestFocus();
			return false;

//		} else if (!(tenCL.length() > 0 && tenCL.matches("^[A-ZÀÁẢẠÃĂẮẰẤẨẬẪĂÁÀẢẠÃẦẤẨẬẪẦĨÍÌỈỊĨÓÒỎỌÕÔỐỒỔỖỘỚỜỔỢỠỜƠỚỜỞỠỢỤỦŨỨỪỬỰỮÝỲỶỴỸĐ][a-zàáảạãăắằấẩậẫăáàảạãầấẩậẫẫỉíìỉịĩóòỏọõôốồổỗộơớờởỡợọụủũưứừửựữýỳỷỵỹđ ]*\\b$"))) {
			// kiểm tra dữ liệu nhập vào đúng định dạng tiếng Việt không
		} else if (!(tenCL.length() > 0 && tenCL.matches("^[\\p{L}À-ỹđĐ ]*\\b$"))) {

			JOptionPane.showMessageDialog(null, "Tên chất liệu  phải là chữ, không chứa ký tự đặc biệt ");
			txtTenChatLieu.requestFocus();
			return false;
		}
		return true;
	}

	// xóa trắng và bỏ chọn hàng trên table
	private void xoaTrang() {
		txtTenChatLieu.setText("");
		txtTenChatLieu.requestFocus();
		tblChatLieu.clearSelection();
		btnThem.setEnabled(true);
	}

	// lưu dữ liệu mới
	private void luuAction() {
		String tenCL = txtTenChatLieu.getText().toString().trim();
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.themChatLieu(tenCL)) { // đúng thì thêm mới
				String ketQua = dssp.layDuLieuCotChatLieu();
				Object[] row = { ketQua };
				modelTBLChatLieu.addRow(row); // lấy tên dữ liệu mới thêm vào và add vào table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Thêm thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên chất liệu bị trùng");
			}
		}
	}

	// cập nhật dữ liệu
	private void capNhatDuLieu() {
		// String tenCLCu = tblChatLieu.getValueAt(tblChatLieu.getSelectedRow(),
		// 0).trim().toString()+"";
		String tenCLCu = tblChatLieu.getValueAt(tblChatLieu.getSelectedRow(), 0).toString(); // lấy từ table
		String tenCLMoi = txtTenChatLieu.getText().trim().toString(); // lấy từ textField
		if (kiemTraDuLieu()) { // kiểm tra dữ liệu đúng không
			if (dssp.capNhatChatLieu(tenCLCu, tenCLMoi)) { // thực hiện cập nhật bằng hàm cập nhật
				int row = tblChatLieu.getSelectedRow();
				modelTBLChatLieu.setValueAt(tenCLMoi, row, 0); // sửa lại dữ liệu hiển thị trên table
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Tên chất liệu bị trùng");
			}
		}
	}
}
