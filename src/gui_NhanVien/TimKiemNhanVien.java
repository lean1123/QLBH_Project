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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import entity.NhanVien;

public class TimKiemNhanVien extends JPanel implements ActionListener{

	private JTextField textFieldMa;
	private JTextField textFieldTen;
	private JTextField textFieldSDT;
	private JTextField textFieldDiaChi;
	private JComboBox<String> comboBoxGioiTinh;
	private JComboBox<String> comboBoxChucVu;

	private JButton buttonXoaTrang;
	private JButton buttonTinKiem;


	private JTable table;
	private DefaultTableModel defaultTableModel;

	private List<NhanVien> listNhanVien;
	private NhanVien_DAO nhanVien_DAO;

	public TimKiemNhanVien() {
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
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
		// TODO Auto-generated method stub
		//lbl
		JLabel lblTitle = new JLabel("TÌM KIẾM NHÂN VIÊN");
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
		//setTextField an comboBox
		textFieldMa.setMaximumSize(new Dimension(175,32));
		textFieldTen.setMaximumSize(new Dimension(175,32));
		textFieldSDT.setMaximumSize(new Dimension(155,32));
		textFieldDiaChi.setMaximumSize(new Dimension(155,32));
		comboBoxChucVu.setMaximumSize(new Dimension(190,32));
		comboBoxGioiTinh.setMaximumSize(new Dimension(190,32));

		textFieldMa.setMargin(new Insets(0,10,0,0));
		textFieldTen.setMargin(new Insets(0,10,0,0));
		textFieldSDT.setMargin(new Insets(0,10,0,0));
		textFieldDiaChi.setMargin(new Insets(0,10,0,0));

		//set Button
		int wid = 100, hei = 31;
		buttonXoaTrang.setMaximumSize(new Dimension(wid,hei));
		buttonTinKiem.setMaximumSize(new Dimension(wid,hei));

		utils.Format.setButtonEvent(buttonXoaTrang,buttonTinKiem);

		//set ComboBox
		comboBoxChucVu.setBackground(Color.white);
		comboBoxGioiTinh.setBackground(Color.white);
		//row title
		Box rowTitle = Box.createHorizontalBox();
		rowTitle.add(lblTitle);
		add(rowTitle);
		//row 1
		Box row1 = Box.createHorizontalBox();
		add(Box.createRigidArea(new Dimension(0,20)));
		row1.add(lblMa);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(textFieldMa);
		row1.add(Box.createRigidArea(new Dimension(30,0)));
		row1.add(lblSDT);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(textFieldSDT);
		row1.add(Box.createRigidArea(new Dimension(30,0)));
		row1.add(lblGioiTinh);
		row1.add(Box.createRigidArea(new Dimension(15,0)));
		row1.add(comboBoxGioiTinh);
		add(row1);

		Box row2 = Box.createHorizontalBox();
		row2.add(lblTen);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(textFieldTen);
		row2.add(Box.createRigidArea(new Dimension(30,0)));
		row2.add(lblDiaChi);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(textFieldDiaChi);
		row2.add(Box.createRigidArea(new Dimension(30,0)));
		row2.add(lblChucVu);
		row2.add(Box.createRigidArea(new Dimension(15,0)));
		row2.add(comboBoxChucVu);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(row2);

		//row3
		Box row3 = Box.createHorizontalBox();
		row3.add(Box.createRigidArea(new Dimension(510,0)));
		row3.add(buttonXoaTrang);
		row3.add(Box.createRigidArea(new Dimension(20,0)));
		row3.add(buttonTinKiem);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(row3);

		//set table
		defaultTableModel.addColumn("Mã Nhân Viên");
		defaultTableModel.addColumn("Tên Nhân Viên");
		defaultTableModel.addColumn("Giới Tính");
		defaultTableModel.addColumn("Chức Vụ");
		defaultTableModel.addColumn("Số Điện Thoại");
		defaultTableModel.addColumn("Địa Chỉ");


		Box row4 = Box.createHorizontalBox();
		JScrollPane scrTBLNhanVien = new JScrollPane(table);
		scrTBLNhanVien.setMaximumSize(new Dimension(970,550));
		row4.add(scrTBLNhanVien);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(row4);

		listNhanVien = nhanVien_DAO.getAllNhanVien();
		loadTableStaff(null);

		buttonTinKiem.addActionListener(this);
		buttonXoaTrang.addActionListener(this);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				textFieldTen.setText(table.getValueAt(row, 1)+"");
				textFieldSDT.setText(table.getValueAt(row, 4)+"");
				textFieldDiaChi.setText(table.getValueAt(row, 5)+"");
				comboBoxChucVu.setSelectedItem(table.getValueAt(row, 3));
				comboBoxGioiTinh.setSelectedItem(table.getValueAt(row, 2));

				textFieldMa.setText(table.getValueAt(row, 0)+"");

			}
		});

	}

	/**
	 * function loadTableStaff được sử dụng để load dữ liệu từ list lên table
	 *
	 * @param list danh sách nhân viên cần load lên table, show ra giao diện người dùng
	 */
	private void loadTableStaff(List<NhanVien> list) {
		defaultTableModel.setRowCount(0);
		for (NhanVien nhanVien : list!=null?list:listNhanVien) {
			Object[] row = {nhanVien.getMaNhanVien(),nhanVien.getTen(),nhanVien.getGioiTinh() ? "Nam":"Nữ",nhanVien.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY)?"Nhân Viên Quản Lý":"Nhân Viên Bán Hàng",nhanVien.getSoDienThoai(),nhanVien.getDiaChi()};
			defaultTableModel.addRow(row);
		}



	}
	/**
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		String[] arrGioiTinh = {"","Nam","Nữ"};
		String[] arrChucVu = {"","Nhân Viên Bán Hàng","Nhân Viên Quản Lý"};
		nhanVien_DAO = new NhanVien_DAO();
		listNhanVien = new ArrayList<>();
		textFieldMa = new JTextField();
		textFieldTen= new JTextField();
		textFieldSDT= new JTextField();
		textFieldDiaChi= new JTextField();
		comboBoxGioiTinh= new JComboBox<>(arrGioiTinh);
		comboBoxChucVu= new JComboBox<>(arrChucVu);

		buttonXoaTrang= new JButton("Xóa Trắng");
		buttonTinKiem= new JButton("Tìm Kiếm");

		defaultTableModel = new DefaultTableModel();
		table = new JTable(defaultTableModel) {
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

		if(o.equals(buttonXoaTrang)) {
			xoaTrang();
		}else if(o.equals(buttonTinKiem)) {
			String ma = textFieldMa.getText().trim();
			String ten = textFieldTen.getText().trim();
			String gioiTinh = (comboBoxGioiTinh.getSelectedItem()+"").trim();
			String sdt = textFieldSDT.getText().trim();
			String diaChi = textFieldDiaChi.getText().trim();
			System.out.println(diaChi);
			String chucVu = (comboBoxChucVu.getSelectedItem()+"").trim();

			NhanVien nv = new NhanVien(ma,ten,gioiTinh.equals("")?null:gioiTinh.equals("Nam")?utils.Contains.NAM:utils.Contains.NU,chucVu.equals("")?"":chucVu.equals("Nhân Viên Quản Lý")?utils.Contains.NHAN_VIEN_QUAN_LY:utils.Contains.NHAN_VIEN_BAN_HANG,sdt,diaChi);

			List<NhanVien> list = new ArrayList<>();
			for (NhanVien nhanVien : listNhanVien) {
				if(nhanVien.getMaNhanVien().contains(nv.getMaNhanVien()) && nhanVien.getTen().toLowerCase().contains(nv.getTen().toLowerCase()) &&nhanVien.getChucVu().contains(nv.getChucVu())
						&& nhanVien.getDiaChi().toLowerCase().contains(nv.getDiaChi().toLowerCase()) && nhanVien.getSoDienThoai().contains(nv.getSoDienThoai())) {
					if(nv.getGioiTinh() != null) {
						if((nv.getGioiTinh() && nhanVien.getGioiTinh()) || (!nv.getGioiTinh() && !nhanVien.getGioiTinh())) {
							list.add(nhanVien);
						}
					}else {
						list.add(nhanVien);
					}
				}
			}
			loadTableStaff(list);

		}

	}

	/**
	 * function xoaTrang được sử dụng để đặt lại các input về mặc định
	 */
	private void xoaTrang() {
		// TODO Auto-generated method stub
		textFieldDiaChi.setText("");
		textFieldMa.setText("");
		textFieldSDT.setText("");
		textFieldTen.setText("");
		comboBoxGioiTinh.setSelectedIndex(0);
		comboBoxChucVu.setSelectedIndex(0);

		textFieldMa.requestFocus();
	}
}
