package gui_TaiKhoan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;


public class CapNhatTaiKhoan extends JPanel implements ActionListener, MouseListener{
	private int WIDTH;
	private int HEIGHT;


	private JComboBox<String> cmbGioiTinh;
	private JComboBox<String> cmbChucVu;

	private JTextField txtMatKhau;
	private JTextField txtTaiKhoan;
	private JTextField txtTimKiemMaNhanVien;
	private JTextField txtTimKiemTenNhanVien;
	private JTextField txtTimKiemSoDienThoai;
	private JTextField txtTimKiemDiaChi;

	private JTextField txtTaiKhoanMaNhanVien;

	private JButton btnXoa;
	private JButton btnThemTaiKhoan;
	private JButton btnCapNhat;
	private JButton btnTimKiem;

	private JTable tblTaiKhoan;
	private JTable tblTimKiemNhanVien;

	private DefaultTableModel modelTBLTaiKhoan;
	private DefaultTableModel modelTBLTimKiemNhanVien;

	private List<NhanVien> listNhanVien;
	private List<TaiKhoan> listTK;

	private NhanVien_DAO nhanVien_DAO;
	private TaiKhoan_DAO taiKhoan_DAO;


	public CapNhatTaiKhoan() {
		this.WIDTH = utils.Contains.WIDTH_PANEL_CONTENT;
		this.HEIGHT = utils.Contains.HEIGHT_PANEL_CONTENT;
		setSize(utils.Contains.WIDTH_PANEL_CONTENT,utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.white);
		anhXa();
		createGUI();
		initial();
	}

	/**
	 * function createGUI dùng để tạo giao diện người dùng
	 */
	private void createGUI() {
		//create Panel
		JPanel pnlPhanCa = new JPanel();
		JPanel pnlRow4 = new JPanel();
		//create JScrollPane
		JScrollPane scrTBLPhanCaLam = new JScrollPane(tblTaiKhoan);
		JScrollPane scrTBLTimKiemNhanVien = new JScrollPane(tblTimKiemNhanVien);

		//set Panel
		pnlRow4.setMaximumSize(new Dimension(1182, 319));
		pnlRow4.setLayout(null);
		pnlRow4.setBackground(Color.white);

		pnlPhanCa.setBounds(899, 7, 273, 302);
		pnlPhanCa.setBorder(BorderFactory.createLineBorder(Color.decode("#9B9B9B")));
		pnlPhanCa.setBackground(Color.WHITE);
		pnlPhanCa.setLayout(new BoxLayout(pnlPhanCa,BoxLayout.Y_AXIS));

		pnlRow4.add(scrTBLTimKiemNhanVien);
		pnlRow4.add(pnlPhanCa);

		//set JScrollPane
		scrTBLTimKiemNhanVien.setBounds(10, 10, 825, 302);
		scrTBLPhanCaLam.setMaximumSize(new Dimension( 1162, 257));
		scrTBLPhanCaLam.setBackground(Color.white);
		scrTBLTimKiemNhanVien.setBackground(Color.white);

		//add column
		modelTBLTimKiemNhanVien.addColumn("Mã Nhân Viên");
		modelTBLTimKiemNhanVien.addColumn("Tên Nhân Viên");
		modelTBLTimKiemNhanVien.addColumn("Giới Tính");
		modelTBLTimKiemNhanVien.addColumn("Chức Vụ");
		modelTBLTimKiemNhanVien.addColumn("Số Điện Thoại");

		modelTBLTaiKhoan.addColumn("Mã Nhân Viên");
		modelTBLTaiKhoan.addColumn("Tên Nhân Viên");
		modelTBLTaiKhoan.addColumn("Số Điện Thoại");
		modelTBLTaiKhoan.addColumn("Chức Vụ");
		modelTBLTaiKhoan.addColumn("Tài Khoản");
		modelTBLTaiKhoan.addColumn("Mật Khẩu");

		//create label
		JLabel lblTitle = new JLabel("CẬP NHẬT TÀI KHOẢN");
		JLabel lblCapNhatTaiKhoan = new JLabel("Cập Nhật Tài Khoản");
		JLabel lblTimKiemMaNhanVien = new JLabel("Mã Nhân Viên");
		JLabel lblTimKiemTenNhanVien = new JLabel("Tên Nhân Viên");
		JLabel lblTimKiemSoDienThoai = new JLabel("Số Điện Thoại");
		JLabel lblTimKiemGioiTinh = new JLabel("Giới Tính");
		JLabel lblTimKiemDiaChi = new JLabel("Địa Chỉ");
		JLabel lblTimKiemChucVu = new JLabel("Chức Vụ");
		JLabel lblMaNhanVien = new JLabel("Mã Nhân Viên");
		JLabel lblTenTaiKhoan = new JLabel("Tên Tài Khoản");
		JLabel lblMatKhau = new JLabel("Mật Khẩu");
		JLabel lblTaiKhoan = new JLabel("Tài Khoản");


		//set label
		lblTaiKhoan.setFont(new Font("",Font.BOLD,22));

		lblTitle.setFont(new Font("", Font.BOLD, 22));
		lblTitle.setForeground(Color.decode("#0500FF"));

		lblCapNhatTaiKhoan.setFont(new Font("",Font.BOLD, 16));

		lblTimKiemMaNhanVien.setMaximumSize(new Dimension(108, 20));
		lblTimKiemTenNhanVien.setMaximumSize(new Dimension(108, 20));
		lblTimKiemDiaChi.setMaximumSize(new Dimension(100,20) );
		lblTimKiemSoDienThoai.setMaximumSize(new Dimension(100,20) );
		lblTimKiemChucVu.setMaximumSize(new Dimension(70,20));
		lblTimKiemGioiTinh.setMaximumSize(new Dimension(70,20));

		lblMatKhau.setMaximumSize(lblTenTaiKhoan.getMaximumSize());
		lblMaNhanVien.setMaximumSize(lblTenTaiKhoan.getMaximumSize());

		//set Button
		btnTimKiem.setMaximumSize(new Dimension(89,31));
		btnCapNhat.setMaximumSize(new Dimension(99,31));
		btnThemTaiKhoan.setMaximumSize(new Dimension(99,31));
		//format button event
		utils.Format.setButtonEvent(btnCapNhat,btnXoa,btnThemTaiKhoan,btnTimKiem);

		//set textField
		txtTimKiemMaNhanVien.setMaximumSize(new Dimension(175,32));
		txtTimKiemTenNhanVien.setMaximumSize(new Dimension(175,32));
		txtTimKiemDiaChi.setMaximumSize(new Dimension(175,32));
		txtTimKiemSoDienThoai.setMaximumSize(new Dimension(175,32));
		cmbChucVu.setMaximumSize(new Dimension(175,32));
		cmbGioiTinh.setMaximumSize(new Dimension(175,32));


		txtTaiKhoanMaNhanVien.setMaximumSize(new Dimension(105,22));
		txtTaiKhoanMaNhanVien.setEditable(false);

		//set combobox
		txtTaiKhoan.setMaximumSize(new Dimension(101,22));
		txtMatKhau.setMaximumSize(new Dimension(101,22));


		//row tile
		Box rowTiltle = Box.createHorizontalBox();
		rowTiltle.add(lblTitle);
		//row1
		Box row1 = Box.createHorizontalBox();
		row1.add(lblTimKiemMaNhanVien);
		row1.add(Box.createRigidArea(new Dimension(15, 0)));
		row1.add(txtTimKiemMaNhanVien);
		row1.add(Box.createRigidArea(new Dimension(30, 0)));
		row1.add(lblTimKiemSoDienThoai);
		row1.add(Box.createRigidArea(new Dimension(15, 0)));
		row1.add(txtTimKiemSoDienThoai);
		row1.add(Box.createRigidArea(new Dimension(30, 0)));
		row1.add(lblTimKiemGioiTinh);
		row1.add(Box.createRigidArea(new Dimension(15, 0)));
		row1.add(cmbGioiTinh);

		Box row2 = Box.createHorizontalBox();
		row2.add(lblTimKiemTenNhanVien);
		row2.add(Box.createRigidArea(new Dimension(15, 0)));
		row2.add(txtTimKiemTenNhanVien);
		row2.add(Box.createRigidArea(new Dimension(30, 0)));
		row2.add(lblTimKiemDiaChi);
		row2.add(Box.createRigidArea(new Dimension(15, 0)));
		row2.add(txtTimKiemDiaChi);
		row2.add(Box.createRigidArea(new Dimension(30, 0)));
		row2.add(lblTimKiemChucVu);
		row2.add(Box.createRigidArea(new Dimension(15, 0)));
		row2.add(cmbChucVu);

		Box row3 = Box.createHorizontalBox();
		row3.add(Box.createRigidArea(new Dimension(700, 0)));
		row3.add(btnTimKiem);

		Box row5 = Box.createHorizontalBox();
		row5.add(lblTaiKhoan);
		//add component vào panel phân công

		Box row1PNLPhanCa = Box.createHorizontalBox();
		row1PNLPhanCa.add(lblCapNhatTaiKhoan);

		Box row2PNLPhanCa = Box.createHorizontalBox();
		row2PNLPhanCa.setMaximumSize(new Dimension(225,22));
		row2PNLPhanCa.add(lblMaNhanVien);
		row2PNLPhanCa.add(Box.createHorizontalStrut(33));
		row2PNLPhanCa.add(txtTaiKhoanMaNhanVien);

		Box row3PNLPhanCa = Box.createHorizontalBox();
		row3PNLPhanCa.setMaximumSize(new Dimension(225,22));
		row3PNLPhanCa.add(lblTenTaiKhoan);
		row3PNLPhanCa.add(Box.createHorizontalStrut(33));
		row3PNLPhanCa.add(txtTaiKhoan);

		Box row4PNLPhanCa = Box.createHorizontalBox();
		row4PNLPhanCa.setMaximumSize(new Dimension(225,22));
		row4PNLPhanCa.add(lblMatKhau);
		row4PNLPhanCa.add(Box.createHorizontalStrut(33));
		row4PNLPhanCa.add(txtMatKhau);

		Box row7PNLPhanCa = Box.createHorizontalBox();
		row7PNLPhanCa.setMaximumSize(new Dimension(225,33));
		row7PNLPhanCa.add(btnThemTaiKhoan);
		row7PNLPhanCa.add(Box.createHorizontalStrut(33));
		row7PNLPhanCa.add(btnCapNhat);

		pnlPhanCa.add(Box.createRigidArea(new Dimension(0,30)));
		pnlPhanCa.add(row1PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(25));
		pnlPhanCa.add(row2PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(14));
		pnlPhanCa.add(row3PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(14));
		pnlPhanCa.add(row4PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(38));
		pnlPhanCa.add(row7PNLPhanCa);


		//add component
		add(rowTiltle);
		add(Box.createRigidArea(new Dimension(0,17)));
		add(row1);
		add(Box.createRigidArea(new Dimension(0,8)));
		add(row2);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(row3);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(pnlRow4);
		add(row5);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(scrTBLPhanCaLam);



		btnTimKiem.addActionListener(this);
		tblTimKiemNhanVien.addMouseListener(this);
		tblTaiKhoan.addMouseListener(this);

		txtMatKhau.addActionListener(this);
		//add event
		btnCapNhat.addActionListener(this);
		btnThemTaiKhoan.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXoa.addActionListener(this);
	}
	/**
	 * function initial là hàm khởi tạo khi giao diện được chạy hoàn thành
	 */
	private void initial() {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listNhanVien = nhanVien_DAO.getAllNhanVienChuaCoTaiKhoan();
		listTK = taiKhoan_DAO.getAllTaiKhoan();

		loadTableStaff(listNhanVien);
		loadTableAccount(listTK);
	}

	/**
	 * function loadTableAccount được sử dụng để load dữ liệu từ list lên table
	 *
	 * @param listTK danh sách tài khoản cần load lên table, show ra giao diện người dùng
	 */
	private void loadTableAccount(List<TaiKhoan> listTK) {
		for (TaiKhoan taiKhoan : listTK) {
			NhanVien nv = taiKhoan.getNhanVien();
			String row[] = {nv.getMaNhanVien(),nv.getTen(),nv.getSoDienThoai(),nv.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY) ?"Nhân Viên Quản Lý":"Nhân Viên Bán Hàng",taiKhoan.getTaiKhoan(),taiKhoan.getMatKhau()};
			modelTBLTaiKhoan.addRow(row);
		}
	}

	/**
	 * function loadTableStaff được sử dụng để load dữ liệu từ list lên table
	 *
	 * @param list danh sách nhân viên cần load lên table, show ra giao diện người dùng
	 */
	private void loadTableStaff( List<NhanVien> list) {
		modelTBLTimKiemNhanVien.setRowCount(0);
		for (NhanVien nhanVien : list) {
			String[] row = {nhanVien.getMaNhanVien(),nhanVien.getTen(),nhanVien.getGioiTinh().equals(utils.Contains.NAM)?"Nam":"Nữ",nhanVien.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY)?"Nhân Viên Quản Lý":"Nhân Viên Bán Hàng",nhanVien.getSoDienThoai()};
			modelTBLTimKiemNhanVien.addRow(row);
		}
	}
	/**
	 * được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		txtTaiKhoanMaNhanVien = new JTextField();
		txtTimKiemMaNhanVien = new JTextField();
		txtTimKiemTenNhanVien = new JTextField();
		txtTimKiemDiaChi = new JTextField();
		txtTimKiemSoDienThoai = new JTextField();
		txtTaiKhoan = new JTextField();
		txtMatKhau = new JTextField();

		btnCapNhat = new JButton("Cập Nhật");
		btnXoa = new JButton("Xóa");
		btnThemTaiKhoan = new JButton("Thêm");
		btnTimKiem = new JButton("Tìm Kiếm");

		modelTBLTaiKhoan = new DefaultTableModel();
		modelTBLTimKiemNhanVien = new DefaultTableModel();

		cmbGioiTinh = new JComboBox<>();
		cmbChucVu = new JComboBox<>();

		tblTaiKhoan = new JTable(modelTBLTaiKhoan){
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};

		tblTimKiemNhanVien = new JTable(modelTBLTimKiemNhanVien){
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};

		listNhanVien = new ArrayList<>();
		listTK = new ArrayList<>();
		nhanVien_DAO = new NhanVien_DAO();
		taiKhoan_DAO = new TaiKhoan_DAO();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTimKiem)) {
			List<NhanVien> list = new ArrayList<>();
			String maNhanVien = txtTimKiemMaNhanVien.getText().toString().trim();
			String tenNhanVien = txtTimKiemTenNhanVien.getText().toString().trim();
			String sdt = txtTimKiemSoDienThoai.getText().toString().trim();
			String diaChi = txtTimKiemDiaChi.getText().toString().trim();
			String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
			String chucVu = cmbChucVu.getSelectedItem().toString();

			for (NhanVien nhanVien : listNhanVien) {
				String itemGioiTinh = nhanVien.getGioiTinh().equals(utils.Contains.NAM) ? "Nam" :"Nữ" ;
				String itemChucVu = nhanVien.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY) ?"Nhân Viên Quản Lý" :"Nhân Viên Bán Hàng";
				if(maNhanVien.contains(nhanVien.getMaNhanVien()) && tenNhanVien.contains(nhanVien.getTen())
						&& sdt.contains(nhanVien.getSoDienThoai()) && diaChi.contains(nhanVien.getDiaChi())
						&& gioiTinh.contains(itemGioiTinh) && chucVu.contains(itemChucVu)) {
								list.add(nhanVien);
				}
			}
			loadTableStaff(list);
		}else if(o.equals(btnThemTaiKhoan)) {
			String maNhanVien = txtTaiKhoanMaNhanVien.getText().toString().trim();
			if(maNhanVien.equals("")) {
				JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Vui lòng chọn nhân viên trước khi tạo tài khoản", "ERROR",JOptionPane.ERROR_MESSAGE);
			}else {
				String tenTK = txtTaiKhoan.getText().toString().trim();
				String matKhau = txtMatKhau.getText().toString().trim();
				if(tenTK.equals("")||matKhau.equals("")){
					JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Vui lòng nhập đầy đủ thông tin tài khoản", "ERROR",JOptionPane.ERROR_MESSAGE);
				}else {
					NhanVien nv = new NhanVien();
					nv.setMaNhanVien(maNhanVien);
					TaiKhoan tk = new TaiKhoan(tenTK, matKhau, nv);
					boolean result = taiKhoan_DAO.themTaiKhoan(tk);
					if(result) {
						listTK.clear();
						listTK = taiKhoan_DAO.getAllTaiKhoan();
						loadTableAccount(listTK);
						JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Thêm tài khoản thành công", "ERROR",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Tên tài khoản đã tồn tại", "ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}else if(o.equals(btnCapNhat)) {
			String maNhanVien = txtTaiKhoanMaNhanVien.getText().toString().trim();
			if(maNhanVien.equals("")) {
				JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Vui lòng chọn nhân viên trước khi cập nhật tài khoản", "ERROR",JOptionPane.ERROR_MESSAGE);
			}else {
				String tenTK = txtTaiKhoan.getText().toString().trim();
				String matKhau = txtMatKhau.getText().toString().trim();
				if(tenTK.equals("")||matKhau.equals("")){
					JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Vui lòng nhập đầy đủ thông tin tài khoản", "ERROR",JOptionPane.ERROR_MESSAGE);
				}else {
					NhanVien nv = new NhanVien();
					nv.setMaNhanVien(maNhanVien);
					TaiKhoan tk = new TaiKhoan(tenTK, matKhau, nv);
					boolean result = taiKhoan_DAO.capNhatTaiKhoan(tk);
					if(result) {
						listTK.clear();
						listTK = taiKhoan_DAO.getAllTaiKhoan();
						loadTableAccount(listTK);
						JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Cập nhật tài khoản thành công", "ERROR",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(CapNhatTaiKhoan.this, "Cập nhật tài khoản không thành công", "ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(tblTimKiemNhanVien)) {
			int row = tblTimKiemNhanVien.getSelectedRow();
			txtTaiKhoanMaNhanVien.setText( tblTimKiemNhanVien.getValueAt(row, 0).toString());

			btnCapNhat.setEnabled(false);
			btnThemTaiKhoan.setEnabled(true);
		}else if(o.equals(tblTaiKhoan)) {
			int row = tblTaiKhoan.getSelectedRow();
			txtTaiKhoanMaNhanVien.setText( tblTimKiemNhanVien.getValueAt(row, 0).toString());

			btnCapNhat.setEnabled(true);
			btnThemTaiKhoan.setEnabled(false);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
}
