package gui_NhaCungCap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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

import connectDB.ConnectDB;
import dao.NhaCungCap_DAO;
import entity.NhaCungCap;

public class TimKiemNhaCungCap extends JPanel implements ActionListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenNhaCungCap, txtDiaChi, txtEmail, txtSoDienThoai;
	private JLabel lblTieuDe, lblMaNhaCungCap, lblSDT, lblbTenNhaCungCap, lblEmail, lblDiaChi;
	private JButton btnXoaTrang, btnTimKiem;
	private JTable tblNhaCungCap;
	private DefaultTableModel dataModel;
	private JScrollPane scrNhaCungCap;
	private JPanel pnFormNhap;
	private JTextField txtMaNhaCungCap;
	private NhaCungCap_DAO dsNhaCungCap;
	private JLabel lblRegexMessage;

	/**
	 * Create the panel.
	 */
	public TimKiemNhaCungCap() {
		dsNhaCungCap = new NhaCungCap_DAO();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXoaTrang.setBounds(447, 311, 110, 30);
		add(btnXoaTrang);

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnTimKiem.setBounds(682, 311, 110, 30);
		add(btnTimKiem);

		String columns[] = { "Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Email", "Địa chỉ" };
		dataModel = new DefaultTableModel(columns, 0);
		tblNhaCungCap = new JTable(dataModel);
		scrNhaCungCap = new JScrollPane(tblNhaCungCap, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrNhaCungCap.setViewportView(tblNhaCungCap);
		scrNhaCungCap.setBounds(10, 393, 1300, 391);
		add(scrNhaCungCap);

		pnFormNhap = new JPanel();
		pnFormNhap.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Nhà Cung Cấp", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormNhap.setBackground(new Color(255, 255, 255));
		pnFormNhap.setBounds(10, 92, 1300, 164);
		add(pnFormNhap);
		pnFormNhap.setLayout(null);

		lblMaNhaCungCap = new JLabel("Mã nhà cung cấp :");
		lblMaNhaCungCap.setBounds(164, 48, 109, 24);
		pnFormNhap.add(lblMaNhaCungCap);
		lblMaNhaCungCap.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		lblEmail = new JLabel("Email :");
		lblEmail.setBounds(164, 99, 64, 24);
		pnFormNhap.add(lblEmail);
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtEmail = new JTextField();
		txtEmail.setBounds(283, 101, 291, 24);
		pnFormNhap.add(txtEmail);
		txtEmail.setColumns(10);

		lblbTenNhaCungCap = new JLabel("Tên nhà cung cấp :");
		lblbTenNhaCungCap.setBounds(449, 48, 125, 24);
		pnFormNhap.add(lblbTenNhaCungCap);
		lblbTenNhaCungCap.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtTenNhaCungCap = new JTextField();
		txtTenNhaCungCap.setBounds(584, 50, 170, 24);
		pnFormNhap.add(txtTenNhaCungCap);
		txtTenNhaCungCap.setColumns(10);

		lblDiaChi = new JLabel("Địa chỉ :");
		lblDiaChi.setBounds(807, 48, 70, 24);
		pnFormNhap.add(lblDiaChi);
		lblDiaChi.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(887, 50, 181, 24);
		pnFormNhap.add(txtDiaChi);
		txtDiaChi.setColumns(10);

		lblSDT = new JLabel("Số điện thoại :");
		lblSDT.setBounds(658, 99, 96, 24);
		pnFormNhap.add(lblSDT);
		lblSDT.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setBounds(781, 102, 287, 24);
		pnFormNhap.add(txtSoDienThoai);
		txtSoDienThoai.setColumns(10);

		txtMaNhaCungCap = new JTextField();
		txtMaNhaCungCap.setColumns(10);
		txtMaNhaCungCap.setBounds(283, 50, 132, 24);
		pnFormNhap.add(txtMaNhaCungCap);

		lblRegexMessage = new JLabel("Nhập đầy đủ thông tin nhà cung cấp");
		lblRegexMessage.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblRegexMessage.setBounds(971, 141, 319, 14);
		pnFormNhap.add(lblRegexMessage);

//		Kết nối cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		capNhatDuLieuChoBang();
		tblNhaCungCap.addMouseListener(this);
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		lblTieuDe = new JLabel("TÌM KIẾM NHÀ CUNG CẤP");
		lblTieuDe.setBounds(496, 30, 284, 52);
		add(lblTieuDe);
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(new Color(255, 255, 255));
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTieuDe.setBorder(null);
	}

	/**
	 * Phương thức cập nhật dữ liệu của bảng
	 */

	public void capNhatDuLieuChoBang() {
		NhaCungCap_DAO ds = new NhaCungCap_DAO();
		for (NhaCungCap ncc : ds.getListNhaCungCap()) {
			Object rowData[] = { ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSoDienThoai(), ncc.getEmail(),
					ncc.getDiaChi() };
			dataModel.addRow(rowData);
		}
		dataModel.fireTableDataChanged();
	}

	/**
	 * Phương thức xóa trắng các ô nhập liệu và cập nhật lại bảng
	 */

	public void xoaTrang() {
		txtMaNhaCungCap.setText("");
		txtTenNhaCungCap.setText("");
		txtDiaChi.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		dataModel.getDataVector().removeAllElements();
		dataModel.fireTableDataChanged();
		txtMaNhaCungCap.requestFocus();
		capNhatDuLieuChoBang();
	}

	/**
	 * Phương thức thực hiện tìm kiếm và hiển thị kết quả trên bảng
	 */

	private void timKiemNhaCungCap() {
		String maNhaCungCap = txtMaNhaCungCap.getText().toString().trim();
		String tenNhaCungCap = txtTenNhaCungCap.getText().toString().trim();
		String dc = txtDiaChi.getText().toString().trim();
		String email = txtEmail.getText().toString().trim();
		String sdt = txtSoDienThoai.getText().toString().trim();
		List<NhaCungCap> dstk = dsNhaCungCap.timKiemNhaCungCap(maNhaCungCap, tenNhaCungCap, dc, email, sdt);
		System.out.println(dstk.isEmpty());
		if (dstk.size() > 0) {
			dataModel.getDataVector().removeAllElements();
			dataModel.fireTableDataChanged();
			for (NhaCungCap ncc : dstk) {
				Object[] row = { ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSoDienThoai(), ncc.getEmail(),
						ncc.getDiaChi() };
				dataModel.addRow(row);
			}
			dataModel.fireTableDataChanged();
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy!");
			int op = JOptionPane.showConfirmDialog(this, "Bạn có muốn thực hiện lại thao tác!", "",
					JOptionPane.YES_NO_OPTION);
			if (op == 0) {
				xoaTrang();
			} else {
				return;
			}
		}
	}

	/**
	 * Xử lý sự kiện khi người dùng thực hiện các hành động
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnXoaTrang)) {
			xoaTrang();
		} else if (o.equals(btnTimKiem)) {
			timKiemNhaCungCap();
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
