package gui_NhanVien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import entity.NhanVien;

public class CapNhatNhanVien extends JPanel implements ActionListener, MouseListener{

	private JTextField txtMa;
	private JTextField txtTen;
	private JTextField txtSDT;
	private JTextField txtDiaChi;
	private JComboBox<String> cmbGioiTinh;
	private JComboBox<String> cmbChucVu;

	private JButton btnXoaTrang;
	private JButton btnThem;
	private JButton btnSua;

	private JTable tblNhanVien;
	private DefaultTableModel modelTBLNhanVien;

	private List<NhanVien> listNV;
	private NhanVien_DAO nhanVien_DAO;

	public CapNhatNhanVien() {
		setSize(utils.Contains.WIDTH_PANEL_CONTENT,utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(Color.white);
		anhXa();
		createGUI();
		initial();
	}

	/**
	 * function initial là hàm khởi tạo khi giao diện được chạy hoàn thành
	 */
	private void initial() {
		// TODO Auto-generated method stub
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * function createGUI dùng để tạo giao diện người dùng
	 */
	private void createGUI() {
		//lbl
		JLabel lblTitle = new JLabel("CẬP NHẬT NHÂN VIÊN");
		JLabel lblMa = new JLabel("Mã Nhân Viên");
		JLabel lblTen = new JLabel("Tên Nhân Viên");
		JLabel lblSDT = new JLabel("Số Điện Thoại");
		JLabel lblDiaChi = new JLabel("Địa Chỉ");
		JLabel lblGioiTinh = new JLabel("Giới Tính");
		JLabel lblChucVu = new JLabel("Chức Vụ");

		//setLabel
		lblTitle.setFont(new Font("", Font.BOLD, 22));
		lblTitle.setForeground(Color.decode("#0500FF"));

		lblDiaChi.setMaximumSize(new Dimension(100,17));
		lblMa.setMaximumSize(new Dimension(108,17));
		lblChucVu.setMaximumSize(new Dimension(69,17));
		lblTen.setMaximumSize(new Dimension(108,17));
		lblSDT.setMaximumSize(new Dimension(100,17));
		lblGioiTinh.setMaximumSize(new Dimension(69,17));
		//setTextField an cmb
		txtMa.setMaximumSize(new Dimension(175,32));
		txtTen.setMaximumSize(new Dimension(175,32));
		txtSDT.setMaximumSize(new Dimension(155,32));
		txtDiaChi.setMaximumSize(new Dimension(155,32));
		cmbChucVu.setMaximumSize(new Dimension(190,32));
		cmbGioiTinh.setMaximumSize(new Dimension(190,32));

		txtMa.setMargin(new Insets(0,10,0,0));
		txtTen.setMargin(new Insets(0,10,0,0));
		txtSDT.setMargin(new Insets(0,10,0,0));
		txtDiaChi.setMargin(new Insets(0,10,0,0));

		//set Button
		int wid = 100, hei = 31,x=370,y=0;
		btnXoaTrang.setBounds(x,y,wid,hei);
		btnThem.setBounds(x+=wid+10,y,wid,hei);
		btnSua.setBounds(x+=wid+10,y,wid,hei);

		utils.Format.setButtonEvent(btnXoaTrang,btnThem,btnSua);

		//set ComboBox
		cmbChucVu.setBackground(Color.white);
		cmbGioiTinh.setBackground(Color.white);
		//row title
		Box rowTitle = Box.createHorizontalBox();
		rowTitle.add(lblTitle);
		add(rowTitle);
		//row 1
		Box row1 = Box.createHorizontalBox();
		add(Box.createRigidArea(new Dimension(0,20)));
		row1.add(lblMa);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(txtMa);
		row1.add(Box.createRigidArea(new Dimension(30,0)));
		row1.add(lblSDT);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(txtSDT);
		row1.add(Box.createRigidArea(new Dimension(30,0)));
		row1.add(lblGioiTinh);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(cmbGioiTinh);
		add(row1);

		Box row2 = Box.createHorizontalBox();
		row2.add(lblTen);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(txtTen);
		row2.add(Box.createRigidArea(new Dimension(30,0)));
		row2.add(lblDiaChi);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(txtDiaChi);
		row2.add(Box.createRigidArea(new Dimension(30,0)));
		row2.add(lblChucVu);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(cmbChucVu);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(row2);

		//row3
		JPanel row3 = new JPanel();
		row3.setLayout(null);
		row3.setBackground(Color.white);
		row3.setMaximumSize(row2.getMaximumSize());
		row3.add(btnThem);
		row3.add(btnSua);
		row3.add(btnXoaTrang);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(row3);

		//set table
		modelTBLNhanVien.addColumn("Mã Nhân Viên");
		modelTBLNhanVien.addColumn("Tên Nhân Viên");
		modelTBLNhanVien.addColumn("Giới Tính");
		modelTBLNhanVien.addColumn("Chức Vụ");
		modelTBLNhanVien.addColumn("Số Điện Thoại");
		modelTBLNhanVien.addColumn("Địa Chỉ");


		Box row4 = Box.createHorizontalBox();
		JScrollPane scrTBLNhanVien = new JScrollPane(tblNhanVien);
		scrTBLNhanVien.setMaximumSize(new Dimension(970,550));
		row4.add(scrTBLNhanVien);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(row4);

		listNV = nhanVien_DAO.getAllNhanVien();
		loadTableStaff(null);

		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnThem.addActionListener(this);
		tblNhanVien.addMouseListener(this);

	}

	/**
	 * function loadTableStaff được sử dụng để load dữ liệu từ list lên table
	 *
	 * @param list danh sách nhân viên cần load lên table, show ra giao diện người dùng
	 */
	private void loadTableStaff(List<NhanVien> list) {
		modelTBLNhanVien.setRowCount(0);
		for (NhanVien nhanVien : list!=null?list:listNV) {
			Object[] row = {nhanVien.getMaNhanVien(),nhanVien.getTen(),nhanVien.getGioiTinh() ? "Nam":"Nữ",nhanVien.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY)?"Nhân Viên Quản Lý":"Nhân Viên Bán Hàng",nhanVien.getSoDienThoai(),nhanVien.getDiaChi()};
			modelTBLNhanVien.addRow(row);
		}



	}

	/**
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		String[] arrGioiTinh = {"","Nam","Nữ"};
		String[] arrChucVu = {"","Nhân Viên Bán Hàng","Nhân Viên Quản Lý"};
		nhanVien_DAO = new NhanVien_DAO();
		listNV = new ArrayList<>();
		txtMa = new JTextField();
		txtTen= new JTextField();
		txtSDT= new JTextField();
		txtDiaChi= new JTextField();
		cmbGioiTinh= new JComboBox<>(arrGioiTinh);
		cmbChucVu= new JComboBox<>(arrChucVu);

		btnXoaTrang= new JButton("Xóa Trắng");
		btnThem= new JButton("Thêm");
		btnSua= new JButton("Sửa");

		modelTBLNhanVien = new DefaultTableModel();
		tblNhanVien = new JTable(modelTBLNhanVien) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			xoaTrang();
			if(btnThem.getText().equals("Thêm")) {
				txtMa.setEnabled(false);
				btnThem.setText("Hủy");
				btnSua.setText("Lưu");
				txtTen.requestFocus();

			}else {
				txtMa.setEnabled(true);
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
			}

		}else if(o.equals(btnXoaTrang)) {
			xoaTrang();
		}else if(o.equals(btnSua)) {
			if(btnSua.getText().toString().equals("Sửa")) {
				capNhatNhanVien("CAPNHAT");
			}else if(btnSua.getText().toString().equals("Lưu")) {
				String ten = txtTen.getText().trim();
				String gioiTinh = (cmbGioiTinh.getSelectedItem()+"").trim();
				String sdt = txtSDT.getText().trim();
				String diaChi = txtDiaChi.getText().trim();
				String chucVu = (cmbChucVu.getSelectedItem()+"").trim();
				if(ten.equals("") || gioiTinh.equals("") || diaChi.equals("") || chucVu.equals("")) {
					JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin nhân viên", "ERROR",JOptionPane.ERROR_MESSAGE);
				}else {
					if(sdt.matches("^[0-9]{10}$")) {
						if(!kiemTraTrungLapSoDienThoai(sdt)) {
							capNhatNhanVien("THEM");
						}else {
							JOptionPane.showMessageDialog(this,"Số điện thoại đã được đăng ký", "ERROR",JOptionPane.ERROR_MESSAGE);
						}

					}else {
						JOptionPane.showMessageDialog(this,"Số điện thoại phải đủ 10 ký tự số", "ERROR",JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		}

	}
	/**
	* kiểm tra số điện thoại đã được đăng ký hay chưa
	* nếu số điện thoại đã được đăng ký => return true
	* còn không thì ngược lại
	*
	* @param sdt là số điện thoại cần kiểm tra
	* @return nếu số điện thoại đã được đăng ký => return true
	* 		  còn không thì ngược lại
	*/
	private boolean kiemTraTrungLapSoDienThoai(String sdt) {
		for (NhanVien nhanVien : listNV) {
			if(nhanVien.getSoDienThoai().equals(sdt)) {
				return true;
			}
		}
		return false;
	}

	private void capNhatNhanVien(String type) {
		String ma = txtMa.getText().trim();
		String ten = txtTen.getText().trim();
		String gioiTinh = (cmbGioiTinh.getSelectedItem()+"").trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String chucVu = (cmbChucVu.getSelectedItem()+"").trim();

		NhanVien nv = new NhanVien(ma,ten,gioiTinh.equals("")?null:gioiTinh.equals("Nam")?utils.Contains.NAM:utils.Contains.NU,chucVu.equals("")?"":chucVu.equals("Nhân Viên Quản Lý")?utils.Contains.NHAN_VIEN_QUAN_LY:utils.Contains.NHAN_VIEN_BAN_HANG,sdt,diaChi);

		if("THEM".equals(type)) {
			nv.setMaNhanVien(null);
			boolean result = nhanVien_DAO.themNhanVien(nv);
			if(result) {
				this.listNV.clear();
				this.listNV = nhanVien_DAO.getAllNhanVien();
				loadTableStaff(listNV);
				btnThem.doClick();
				JOptionPane.showMessageDialog(this,"Lưu thành công", "SUCCESS",JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this,"Lưu không thành công", "ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else if("CAPNHAT".equals(type)) {
			boolean isTimKiem = false;
			int i = 0;
			if(ma.equals("") || nv.getGioiTinh() == null || nv.getChucVu().isEmpty() || nv.getDiaChi().isEmpty() || nv.getSoDienThoai().isEmpty() || nv.getTen().isEmpty() ) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "ERROR",JOptionPane.ERROR_MESSAGE);
			}else {
				for (NhanVien nhanVien : listNV) {
					if(nhanVien.getMaNhanVien().equals(ma)) {
						isTimKiem = true;
						boolean result = nhanVien_DAO.capNhatNhanVien(nv);
						if(result) {
							listNV.set(i, nv);
							loadTableStaff(listNV);
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công", "SUCCESS",JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(this, "Cập nhật nhân viên không thành công", "ERROR",JOptionPane.ERROR_MESSAGE);
						}


						break;
					}
					i++;
				}
				if(!isTimKiem) {
					JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại", "ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if("XOA".equals(type)) {
			int row = tblNhanVien.getSelectedRow();
			modelTBLNhanVien.removeRow(row);
		}

	}

	/**
	 * function xoaTrang được sử dụng để đặt lại các input về mặc định
	 */
	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtDiaChi.setText("");
		txtMa.setText("");
		txtSDT.setText("");
		txtTen.setText("");
		cmbGioiTinh.setSelectedIndex(0);
		cmbChucVu.setSelectedIndex(0);

		btnThem.setEnabled(true);
		txtMa.setEditable(true);
		txtMa.requestFocus();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblNhanVien)) {
			int row = tblNhanVien.getSelectedRow();
			txtTen.setText(tblNhanVien.getValueAt(row, 1)+"");
			txtSDT.setText(tblNhanVien.getValueAt(row, 4)+"");
			txtDiaChi.setText(tblNhanVien.getValueAt(row, 5)+"");
			cmbChucVu.setSelectedItem(tblNhanVien.getValueAt(row, 3));
			cmbGioiTinh.setSelectedItem(tblNhanVien.getValueAt(row, 2));
			txtMa.setText(tblNhanVien.getValueAt(row, 0).toString());
			txtMa.setEditable(false);
			btnThem.setText("Thêm");
			btnSua.setText("Sửa");
			btnThem.setEnabled(false);
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
