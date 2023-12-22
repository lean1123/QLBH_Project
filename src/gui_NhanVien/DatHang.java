package gui_NhanVien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.ChiTietDonHang_DAO;
import dao.DonHang_DAO;
import dao.KhachHang_DAO;
import dao.NhaCungCap_DAO;
import dao.SanPham_DAO;
import entity.ChiTietDonHang;
import entity.DonHang;
import entity.KhachHang;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.SanPham;

public class DatHang extends JPanel implements FocusListener, ActionListener, MouseListener{
	private JTextField txtTenSanPham;
	private JTextField txtSoDienThoai;
	private JTextField txtTenKhachHang;
	private JTextField txtDiaChi;
	private JTextField txtSoLuong;
	private JTextField txtSoTienDua;

	private JComboBox<String> cmbLoaiSanPham;
	private JComboBox<String> cmbMau;
	private JComboBox<String> cmbNhaCungCap;
	private JComboBox<String> cmbChatLieu;
	private JComboBox<String> cmbKichCo;
	private JComboBox<String> cmbGioiTinh;

	private JButton btnTimKiem;
	private JButton btnThemVaoDonHang;
	private JButton btnCapNhatSoLuong;
	private JButton btnLamMoi;
	private JButton btnDatHang;
	private JButton btnThanhToan;
	private JButton btnIconTimKiem;
	private JButton btnIconBang;
	private JButton btnXoaTrang;

	private JCheckBox  chkXuaHoaDon;

	private JLabel lblSoTienTraLai;
	private JLabel lblAnhSanPham;
	private JLabel lblShowTongTien;

	private JTable tblSanPham;
	private JTable tblDonHang;

	private DefaultTableModel modelTBLSanPham;
	private DefaultTableModel modelTBLDonHang;
	private DefaultComboBoxModel<String> modelCMBLoaiSanPham;
	private DefaultComboBoxModel<String> modelCMBMau;
	private DefaultComboBoxModel<String> modelCMBNhaCungCap;
	private DefaultComboBoxModel<String> modelCMBChatLieu;
	private DefaultComboBoxModel<String> modelCMBKichCo;

	private List<String> listCL;
	private List<String> listMS;
	private List<String> listKC;
	private List<String> listDM;
	private List<SanPham> listSanPham;
	private List<SanPham> listSanPhamInDonHang;
	private List<ChiTietDonHang> listCTDonHang;
	private List<NhaCungCap> listNhaCungCap;

	private SanPham_DAO sanPham_DAO;
	private KhachHang_DAO khachHang_DAO;
	private NhaCungCap_DAO nhaCungCap_DAO;
	private ChiTietDonHang_DAO chiTietDonHang_Dao;
	private DonHang_DAO donHang_DAO;


	private DonHang donHang;


	public DatHang() {
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
		// initial image
		ImageIcon image_avt = new ImageIcon(getClass().getResource("/images/icons/Frame32.png"));
		ImageIcon image_search = new ImageIcon(getClass().getResource("/images/icons/Frame171.png"));

		Image scaled = utils.Function.scaleImage(image_avt.getImage(), 160, 149);
		image_avt.setImage(scaled);
		lblAnhSanPham.setIcon(image_avt);

		scaled = utils.Function.scaleImage(image_search.getImage(), 38, 32);
		image_search.setImage(scaled);
		btnIconTimKiem.setIcon(image_search);

		//add value combobox giới tính
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");

		//connect db
		try {
			ConnectDB.getInstance().connect();
			//load list
			listKC = sanPham_DAO.getDanhSachKichCo();
			listCL = sanPham_DAO.getDanhSachChatLieu();
			listMS = sanPham_DAO.getDanhSachMauSac();
			listDM = sanPham_DAO.getDanhSachDanhMuc();
			listSanPham = sanPham_DAO.getDanhSachSanPham();
			listNhaCungCap = nhaCungCap_DAO.getListNhaCungCap();
			// load combobox
			loadCombobox();
			loadTableProduct(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * function loadTableProduct được sử dụng để load dữ liệu từ list lên table
	 *
	 * @param list danh sách sản phẩm cần load lên table, show ra giao diện người dùng
	 */
	private void loadTableProduct(List list) {
		modelTBLSanPham.setRowCount(0);

		List<SanPham> listSanPham = new ArrayList<>();
		if(list == null) {
			listSanPham = this.listSanPham;
		}else {
			listSanPham = list;
		}
		for (SanPham sanPham : listSanPham) {
			String mau = sanPham.getMauSac();
			String kichCo = sanPham.getKichCo();
			String danhMuc = sanPham.getDanhMuc();
			String chatLieu = sanPham.getChatLieu();
			int soLuongTon = sanPham.getSoLuongTon();
			String row[] = {sanPham.getMaSanPham(),sanPham.getTenSanPham(),utils.Format.formatAmout(sanPham.getGiaBan()),danhMuc,mau,kichCo,chatLieu,soLuongTon+"", sanPham.getNhaCungCap().getTenNhaCungCap()};

			modelTBLSanPham.addRow(row);
		}

	}

	/**
	 * function loadCombobox được sử dụng để load các giá trị của combobox
	 */
	private void loadCombobox() {
		// TODO Auto-generated method stub
		modelCMBChatLieu.addElement("");
		modelCMBLoaiSanPham.addElement("");
		modelCMBMau.addElement("");
		modelCMBKichCo.addElement("");
		modelCMBNhaCungCap.addElement("");

		for (String chatLieu : listCL) {
			modelCMBChatLieu.addElement(chatLieu);
		}
		for (String danhMuc : listDM) {
			modelCMBLoaiSanPham.addElement(danhMuc);
		}
		for (String mauSac : listMS) {
			modelCMBMau.addElement(mauSac);
		}
		for (String kichCo : listKC) {
			modelCMBKichCo.addElement(kichCo);
		}
		for (NhaCungCap ncc : listNhaCungCap) {
			modelCMBNhaCungCap.addElement(ncc.getTenNhaCungCap());
		}

	}


	/**
	 * function createGUI dùng để tạo giao diện người dùng
	 */
	private void createGUI() {
		//set btn
		utils.Format.setButtonEvent(btnCapNhatSoLuong,btnDatHang,btnThanhToan,btnLamMoi,btnThanhToan,btnThemVaoDonHang,btnTimKiem,btnXoaTrang);
		btnIconTimKiem.setBorder(BorderFactory.createLineBorder(Color.black));

		//set textField
		txtSoDienThoai.setForeground(Color.decode("#A8A8B7"));
		txtTenKhachHang.setForeground(Color.decode("#A8A8B7"));
		txtDiaChi.setForeground(Color.decode("#A8A8B7"));
		txtSoTienDua.setForeground(Color.decode("#A8A8B7"));

		txtSoDienThoai.setText("Số Điện Thoại Khách Hàng");
		txtTenKhachHang.setText("Tên Khách Hàng");
		txtDiaChi.setText("Địa Chỉ");
		txtSoTienDua.setText("Số Tiền Khách Đưa");

		//set margin
		txtTenSanPham.setMargin(new Insets(0, 10, 0, 0));
		txtSoLuong.setMargin(new Insets(0, 10, 0, 0));
		txtSoDienThoai.setMargin(new Insets(0, 10, 0, 0));
		txtDiaChi.setMargin(new Insets(0, 10, 0, 0));
		txtTenKhachHang.setMargin(new Insets(0, 10, 0, 0));
		txtSoTienDua.setMargin(new Insets(0, 10, 0, 0));

		//create JLable
		JLabel lblTitle = new JLabel("Đặt Hàng");
		JLabel lblTenSanPham = new JLabel("Tên Sản Phẩm");
		JLabel lblLoaiSanPham = new JLabel("Loại Sản Phẩm");
		JLabel lblMau = new JLabel("Màu");
		JLabel lblNhaCungCap = new JLabel("Nhà Cung Cấp");
		JLabel lblChatLieu = new JLabel("Chất Liệu");
		JLabel lblKichCo = new JLabel("Kích Cỡ");
		JLabel lblTongTien = new JLabel("Tổng Tiền: ");
		JLabel lblSoLuong = new JLabel("Số Lượng");
		JLabel lblTitleDonHang = new JLabel("Đơn Hàng");
		JLabel lblTitleThanhToan = new JLabel("Đặt Hàng");
		JLabel lblTienTraLai = new JLabel("Tiền Trả Lại: ");


		//set lable
		lblTitle.setFont(new Font("", Font.BOLD, 26));
		lblTitle.setForeground(Color.decode("#0500FF"));

		lblTitleDonHang.setFont(new Font("",Font.BOLD,20));
		lblTitleThanhToan.setFont(new Font("",Font.BOLD,20));
		//row1
		JPanel pnlRow1 = new JPanel();
		pnlRow1.setLayout(null);
		pnlRow1.setMaximumSize(new Dimension(3000,168));
		pnlRow1.setBackground(Color.white);

		lblTitle.setBounds(521, 6, 122, 31);
		lblTenSanPham.setBounds(47, 64, 103, 20);
		txtTenSanPham.setBounds(159,56, 212, 32);
		lblLoaiSanPham.setBounds(406, 64, 106, 20);
		cmbLoaiSanPham.setBounds(523, 56, 141, 32);
		lblMau.setBounds(715, 64, 56, 20);
		cmbMau.setBounds(774, 56, 136, 32);
		lblNhaCungCap.setBounds(47, 110, 103, 20);
		cmbNhaCungCap.setBounds(159,102, 212, 32);
		lblChatLieu.setBounds(406, 110, 106, 20);
		cmbChatLieu.setBounds(523, 102, 141, 32);
		lblKichCo.setBounds(715, 110, 56, 20);
		cmbKichCo.setBounds(774, 102, 136, 32);
		btnTimKiem.setBounds(926, 129, 86, 32);
		btnXoaTrang.setBounds(926, 95, 86, 30);
		lblAnhSanPham.setBounds(1029, 11, 160, 149);


		pnlRow1.add(lblTitle);
		pnlRow1.add(lblTenSanPham);
		pnlRow1.add(txtTenSanPham);
		pnlRow1.add(lblLoaiSanPham);
		pnlRow1.add(cmbLoaiSanPham);
		pnlRow1.add(lblMau);
		pnlRow1.add(cmbMau);
		pnlRow1.add(lblNhaCungCap);
		pnlRow1.add(cmbNhaCungCap);
		pnlRow1.add(lblChatLieu);
		pnlRow1.add(cmbChatLieu);
		pnlRow1.add(lblKichCo);
		pnlRow1.add(cmbKichCo);
		pnlRow1.add(btnXoaTrang);
		pnlRow1.add(btnTimKiem);
		pnlRow1.add(lblAnhSanPham);

		add(pnlRow1);

		//add column
		String product_names[] = {"Mã Sản Phẩm","Tên Sản Phẩm","Đơn Giá","Danh Mục","Màu","Kích Cỡ","Chất Liệu","Số Lượng Tồn","Nhà Cung Cấp"};
		String order_names[]= {"Mã Sản Phẩm","Tên Sản Phẩm","Màu","Kích Cỡ","Chất Liệu","Đơn Giá","Số Lượng","Tổng Tiền"};
		for (String string : order_names) {
			modelTBLDonHang.addColumn(string);
		}
		for (String string : product_names) {
			modelTBLSanPham.addColumn(string);
		}

		//row2
		JScrollPane scrTBLSanPham = new JScrollPane(tblSanPham);
		scrTBLSanPham.setMaximumSize(new Dimension(1226, 245));
		add(scrTBLSanPham);

		//row3
		JPanel pnlRow3 = new JPanel();
		pnlRow3.setLayout(null);
		pnlRow3.setMaximumSize(new Dimension(1292, 58));

		lblSoLuong.setBounds(89, 24, 72, 20);
		txtSoLuong.setBounds(174, 13, 108, 32);
		btnThemVaoDonHang.setBounds(311, 18, 156, 30);
		btnCapNhatSoLuong.setBounds(496, 18, 156, 30);

		pnlRow3.add(lblSoLuong);
		pnlRow3.add(txtSoLuong);
		pnlRow3.add(btnThemVaoDonHang);
		pnlRow3.add(btnCapNhatSoLuong);

		add(Box.createRigidArea(new Dimension(0,10)));
		add(pnlRow3);

		//row4
		JPanel pnlRow4 = new JPanel();
		pnlRow4.setBackground(Color.white);
		pnlRow4.setLayout(null);
		pnlRow4.setMaximumSize(new Dimension(1295,318));

		JPanel pnlDonHang = new JPanel();
		JScrollPane scrTBLDonHang = new JScrollPane(tblDonHang);

		pnlDonHang.setBackground(Color.white);
		pnlDonHang.setLayout(null);
		pnlDonHang.setBounds(0, 8,841, 318);
		pnlDonHang.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#D9D9D9")));


		lblTitleDonHang.setBounds(358, 2, 96, 30);
		btnLamMoi.setBounds(649, 8, 91, 30);
		scrTBLDonHang.setBounds(1, 48, 829, 250);

		pnlDonHang.add(lblTitleDonHang);
		pnlDonHang.add(btnLamMoi);
		pnlDonHang.add(scrTBLDonHang);

		JPanel pnlThanhToan = new JPanel();
		pnlThanhToan.setBackground(Color.white);
		pnlThanhToan.setLayout(null);
		pnlThanhToan.setBounds(842, 8,454, 314);
		chkXuaHoaDon.setBackground(Color.white);

		lblTitleThanhToan.setBounds(163, 3, 116, 30);
		txtSoDienThoai.setBounds(17, 55, 182, 32);
		btnIconTimKiem.setBounds(198, 55, 38, 30);
		txtTenKhachHang.setBounds(260, 55, 176, 32);
		txtDiaChi.setBounds(17, 96, 182, 32);
		cmbGioiTinh.setBounds(260, 96, 176, 32);
		lblTongTien.setBounds(17, 180, 70, 20);
		lblShowTongTien.setBounds(90, 180, 150, 20);
		btnDatHang.setBounds(322, 175, 86, 32);
//		lblShowTongTien.setBounds(90, 160, 150, 20);
//		btnDatHang.setBounds(322, 155, 86, 32);
		txtSoTienDua.setBounds(17, 205, 182, 32);
		lblTienTraLai.setBounds(250, 212, 88, 20);


		pnlThanhToan.add(lblTitleThanhToan);
		pnlThanhToan.add(txtSoDienThoai);
		pnlThanhToan.add(txtTenKhachHang);
		pnlThanhToan.add(txtDiaChi);
		pnlThanhToan.add(cmbGioiTinh);
		pnlThanhToan.add(lblTongTien);
		pnlThanhToan.add(lblShowTongTien);
		pnlThanhToan.add(btnDatHang);
//		pnlThanhToan.add(txtSoTienDua);
//		pnlThanhToan.add(lblTienTraLai);
		pnlThanhToan.add(btnIconTimKiem);

		pnlRow4.add(pnlDonHang);
		pnlRow4.add(pnlThanhToan);

		add(pnlRow4);

		txtSoDienThoai.addFocusListener(this);
		txtTenKhachHang.addFocusListener(this);
		txtDiaChi.addFocusListener(this);
		txtSoTienDua.addFocusListener(this);

		btnCapNhatSoLuong.addActionListener(this);
		btnDatHang.addActionListener(this);
		btnIconTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnThemVaoDonHang.addActionListener(this);
		btnTimKiem.addActionListener(this);

		tblSanPham.addMouseListener(this);
		tblDonHang.addMouseListener(this);
		btnXoaTrang.addActionListener(this);

	}

	/**
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {

		modelTBLSanPham = new DefaultTableModel();
		modelTBLDonHang = new DefaultTableModel();
		modelCMBLoaiSanPham = new DefaultComboBoxModel<>();
		modelCMBMau = new DefaultComboBoxModel<>();
		modelCMBNhaCungCap = new DefaultComboBoxModel<>();
		modelCMBChatLieu = new DefaultComboBoxModel<>();
		modelCMBKichCo = new DefaultComboBoxModel<>();

		txtTenSanPham = new JTextField();
		txtSoDienThoai= new JTextField();
		txtTenKhachHang= new JTextField();
		txtDiaChi= new JTextField();
		txtSoLuong= new JTextField();
		txtSoTienDua= new JTextField();

		cmbLoaiSanPham =new JComboBox<>(modelCMBLoaiSanPham);
		cmbMau = new JComboBox<>(modelCMBMau);
		cmbNhaCungCap= new JComboBox<>(modelCMBNhaCungCap);
		cmbChatLieu= new JComboBox<>(modelCMBChatLieu);
		cmbKichCo= new JComboBox<>(modelCMBKichCo);
		cmbGioiTinh= new JComboBox<>();

		btnTimKiem = new JButton("Tìm Kiếm");
		btnThemVaoDonHang= new JButton("Thêm Vào Đơn Hàng");
		btnCapNhatSoLuong= new JButton("Cập Nhật Số Lượng");
		btnLamMoi= new JButton("Làm Mới");
		btnDatHang= new JButton("Đặt Hàng");
		btnThanhToan= new JButton("Thanh Toán");
		btnIconTimKiem= new JButton("");
		btnIconBang= new JButton("");
		btnXoaTrang = new JButton("Xóa Trắng");
		chkXuaHoaDon = new JCheckBox("Xuất Hóa Đơn");

		lblSoTienTraLai = new JLabel();
		lblAnhSanPham = new JLabel();
		lblShowTongTien = new JLabel("0.000 VND");

		tblSanPham = new JTable(modelTBLSanPham){
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};
		tblDonHang = new JTable(modelTBLDonHang){
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};

		listMS = new ArrayList<>();
		listDM = new ArrayList<>();
		listKC = new ArrayList<>();
		listCL = new ArrayList<>();
		listSanPham = new ArrayList<>();

		sanPham_DAO = new SanPham_DAO();
		nhaCungCap_DAO = new NhaCungCap_DAO();
		chiTietDonHang_Dao = new ChiTietDonHang_DAO();
		donHang_DAO = new DonHang_DAO();

		khachHang_DAO = new KhachHang_DAO();
		listCTDonHang = new ArrayList<>();
		listNhaCungCap = new ArrayList<>();
		listSanPhamInDonHang = new ArrayList<>();

		donHang= new DonHang();
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(txtSoDienThoai)) {
			if(txtSoDienThoai.getText().toString().trim().equals("Số Điện Thoại Khách Hàng")) {
				txtSoDienThoai.setText("");
				txtSoDienThoai.setForeground(Color.black);
			}
		}else if(o.equals(txtDiaChi)) {
			if(txtDiaChi.getText().toString().trim().equals("Địa Chỉ")) {
				txtDiaChi.setText("");
				txtDiaChi.setForeground(Color.black);
			}
		}else if(o.equals(txtTenKhachHang)) {
			if(txtTenKhachHang.getText().toString().trim().equals("Tên Khách Hàng")) {
				txtTenKhachHang.setText("");
				txtTenKhachHang.setForeground(Color.black);
			}
		}else if(o.equals(txtSoTienDua)) {
			if(txtSoTienDua.getText().toString().trim().equals("Số Tiền Khách Đưa")) {
				txtSoTienDua.setText("");
				txtSoTienDua.setForeground(Color.black);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(txtSoDienThoai)) {
			if(txtSoDienThoai.getText().toString().trim().equals("")) {
				txtSoDienThoai.setText("Số Điện Thoại Khách Hàng");
				txtSoDienThoai.setForeground(Color.decode("#A8A8B7"));
			}
		}else if(o.equals(txtDiaChi)) {
			if(txtDiaChi.getText().toString().trim().equals("")) {
				txtDiaChi.setText("Địa Chỉ");
				txtDiaChi.setForeground(Color.decode("#A8A8B7"));
			}
		}else if(o.equals(txtTenKhachHang)) {
			if(txtTenKhachHang.getText().toString().trim().equals("")) {
				txtTenKhachHang.setText("Tên Khách Hàng");
				txtTenKhachHang.setForeground(Color.decode("#A8A8B7"));
			}
		}else if(o.equals(txtSoTienDua)) {
			if(txtSoTienDua.getText().toString().trim().equals("")) {
				txtSoTienDua.setText("Số Tiền Khách đưa");
				txtSoTienDua.setForeground(Color.decode("#A8A8B7"));
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnTimKiem)) {
			String TKTenSanPham = txtTenSanPham.getText().toString().trim();
			String TKLoai = cmbLoaiSanPham.getSelectedItem().toString() != "" ? cmbLoaiSanPham.getSelectedItem().toString() :"";
			String TKChatLieu = cmbChatLieu.getSelectedItem().toString() != "" ? cmbChatLieu.getSelectedItem().toString() :"";
			String TKMau = cmbMau.getSelectedItem().toString()!= "" ? cmbMau.getSelectedItem().toString() :"";
			String TKKichCo = cmbKichCo.getSelectedItem().toString() != "" ? cmbKichCo.getSelectedItem().toString() :"";

			List<SanPham> list = new ArrayList<>();
			for (SanPham sp : listSanPham) {
				String tenSanPham = sp.getTenSanPham();
				String loai = sp.getDanhMuc();
				String chatLieu = sp.getChatLieu();
				String mau = sp.getMauSac();
				String kichCo = sp.getKichCo();

				if(tenSanPham.contains(TKTenSanPham) && loai.contains(TKLoai) && chatLieu.contains(TKChatLieu) && mau.contains(TKMau) && kichCo.contains(TKKichCo)) {
					list.add(sp);
				}
			}
			loadTableProduct(list);
		}else if(o.equals(this.btnThemVaoDonHang)) {
			int row = tblSanPham.getSelectedRow();
			if(row >=0) {
				int soLuong = 0;
				try {
					 soLuong = Integer.parseInt(txtSoLuong.getText().toString().trim());
					 int soLuongTon = Integer.parseInt(tblSanPham.getValueAt(row, 7).toString().trim());
					 if(soLuong > 0 && soLuong <= soLuongTon) {
						 String maSanPham = tblSanPham.getValueAt(row, 0).toString();
						 int indexListDonHang = -1;
						 int i = 0;
						 //kiểm tra xem sản phẩm đã có trong đơn hàng chưa
						 // nếu có thì indexListDonHang sẽ bằng vị trí tìm thấy trong listSanPhamInDonHang
						 for(SanPham spInDonHang : listSanPhamInDonHang) {
							 if(spInDonHang.getMaSanPham().equals(maSanPham)) {
								 indexListDonHang = i;
								 break;
							 }
							 i++;
						 }
						
						 //nếu indexListDonHang >= 0 thì cập nhật lại số lượng của sản phẩm trong danh sách chi tiết đơn hàng(listCTDonHang)
						 //ngược lại thì, thêm sản phẩm đã chọn vào listSanPhamInCTDonHang và tạo ctdh rồi thêm vào listCTDonHang
						 if( indexListDonHang >= 0) {
							 ChiTietDonHang ctdh = listCTDonHang.get(indexListDonHang);
							
							 int soLuongCu = ctdh.getSoLuong();
							 
							 modelTBLDonHang.setValueAt(soLuong+ctdh.getSoLuong(), i, 6);
							 modelTBLDonHang.setValueAt(utils.Format.formatAmout((soLuong+soLuongCu)*ctdh.getDonGia()), i, 7);

							 ctdh.setSoLuong(soLuongCu+soLuong);
							 listCTDonHang.set(indexListDonHang, ctdh);
							 
							 //cập nhật lại tổng tiền
							 String strTongTien = lblShowTongTien.getText().toString();
							 strTongTien = strTongTien.replaceAll("[,.]", "");
							 strTongTien = strTongTien.replaceAll(" VND", "");
							 System.out.println(strTongTien);

							 float tongTien = Float.parseFloat(strTongTien);
							 
							 tongTien += soLuong*ctdh.getDonGia();
							 
							 lblShowTongTien.setText(utils.Format.formatAmout(tongTien)  +" VND");
						 }else {

							 for (SanPham sp : this.listSanPham) {
								 if(sp.getMaSanPham().equals(maSanPham)) {

									 if(soLuong>0) {
										 String loai = sp.getDanhMuc();
										 String chatLieu = sp.getChatLieu();
										 String mau = sp.getMauSac();
										 String kichCo = sp.getKichCo();
										 String rowDonHang[]= {sp.getMaSanPham(),sp.getTenSanPham(),mau,kichCo,chatLieu,utils.Format.formatAmout(sp.getGiaBan()), soLuong+"",utils.Format.formatAmout(sp.getGiaBan()*soLuong)};
										 modelTBLDonHang.addRow(rowDonHang);

										 ChiTietDonHang ctddh = new ChiTietDonHang(sp, this.donHang, sp.getGiaBan(), soLuong);

										 listSanPhamInDonHang.add(sp);
										 listCTDonHang.add(ctddh);

										//cập nhật lại tổng tiền
										 String strTongTien = lblShowTongTien.getText().toString();
										 strTongTien = strTongTien.replaceAll("[,.]", "");
										 strTongTien = strTongTien.replaceAll(" VND", "");

										 float tongTien = Float.parseFloat(strTongTien);
										 tongTien += soLuong*sp.getGiaBan();
										 lblShowTongTien.setText(utils.Format.formatAmout(tongTien) +" VND");
									 }else {
										 JOptionPane.showMessageDialog(this,"Vui lòng nhập số lượng cần mua","ERROR",JOptionPane.ERROR_MESSAGE);
									 }
									 break;

								 }
							 }
						 }
					 }else {
						 JOptionPane.showMessageDialog(this,"Khi thêm, số lượng sản phẩm phải > 0 và phải < số lượng tồn","ERROR",JOptionPane.ERROR_MESSAGE);
					 }
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(this,"Số lượng chỉ chứa ký tự số và không được để trống!","ERROR",JOptionPane.ERROR_MESSAGE);
				}


			}else {
				JOptionPane.showMessageDialog(this,"Vui lòng chọn sản phẩm.","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else if(o.equals(btnCapNhatSoLuong)) {
			int row = tblDonHang.getSelectedRow();
			if(row>=0) {
				int soLuongMoi = 0;
				try {
					soLuongMoi = Integer.parseInt(txtSoLuong.getText().toString().trim());
					int soLuongCu = Integer.parseInt(tblDonHang.getValueAt(row, 6).toString());
					int soLuongTon = Integer.parseInt(tblSanPham.getValueAt(row, 7).toString().trim());

					 if(soLuongMoi>=0 && soLuongMoi + soLuongCu <= soLuongTon) {

						float donGia = listCTDonHang.get(row).getDonGia();
						//nếu số lượng == 0 thì xóa sản phẩm đó ra đơn hàng
						if(soLuongMoi == 0) {


							modelTBLDonHang.removeRow(row);
							listCTDonHang.remove(row);
							listSanPhamInDonHang.remove(row);

							//cập nhật lại tổng tiền
							String strTongTien = lblShowTongTien.getText().toString();
							strTongTien = strTongTien.replaceAll("[,.]", "");
							strTongTien = strTongTien.replaceAll(" VND", "");

							float tongTien = Float.parseFloat(strTongTien);
							tongTien -= soLuongCu*donGia;
							lblShowTongTien.setText(utils.Format.formatAmout(tongTien) +" VND");
						}else {

							modelTBLDonHang.setValueAt(soLuongMoi+"", row, 6);
							modelTBLDonHang.setValueAt(utils.Format.formatAmout(donGia*soLuongMoi), row, 7);

							ChiTietDonHang ctddh = listCTDonHang.get(row);
							ctddh.setSoLuong(soLuongMoi);
							listCTDonHang.set(row, ctddh);

							//cập nhật lại tổng tiền
							String strTongTien = lblShowTongTien.getText().toString();
							strTongTien = strTongTien.replaceAll("[,.]", "");
							strTongTien = strTongTien.replaceAll(" VND", "");

							float tongTien = Float.parseFloat(strTongTien);
							tongTien -= soLuongCu*donGia;
							tongTien += soLuongMoi*donGia;

							lblShowTongTien.setText(utils.Format.formatAmout(tongTien)  +" VND");
						}
					 }else {

						 JOptionPane.showMessageDialog(this,"Khi cập nhật, tổng số lượng sản phẩm phải > 0 và phải < số lượng tồn","ERROR",JOptionPane.ERROR_MESSAGE);
					 }
				} catch (Exception e2) {
					// TODO: handle exception

					JOptionPane.showMessageDialog(this,"Vui lòng nhập số lượng cần mua","ERROR",JOptionPane.ERROR_MESSAGE);
				}

			}else {
				JOptionPane.showMessageDialog(this,"Vui lòng chọn sản phẩm ở bản ĐƠN HÀNG.","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else if(o.equals(btnIconTimKiem)) {
			String soDienThoaiKhachHang = txtSoDienThoai.getText().trim();
			KhachHang kh = khachHang_DAO.timKiemKhachHang(soDienThoaiKhachHang);
			if(kh != null) {
				txtTenKhachHang.setText(kh.getHoTen());
				txtDiaChi.setText(kh.getDiaChi());
				cmbGioiTinh.setSelectedItem(kh.isGioiTinh() && utils.Contains.NAM ? "Nam":"Nữ");

				txtTenKhachHang.setForeground(Color.black);
				txtDiaChi.setForeground(Color.black);
				cmbGioiTinh.setForeground(Color.black);

				txtTenKhachHang.setEditable(false);
				txtDiaChi.setEditable(false);
				cmbGioiTinh.setEditable(false);
				cmbGioiTinh.setEditable(false);
			}else {
				txtTenKhachHang.setEditable(true);
				txtDiaChi.setEditable(true);
				cmbGioiTinh.setEditable(true);
				cmbGioiTinh.setEditable(true);

				JOptionPane.showMessageDialog(this,"Khách hàng không tồn tại","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else if(o.equals(btnDatHang)) {
			if(listCTDonHang.size() > 0) {
				String sdt = txtSoDienThoai.getText().toString().trim();
				String tenKH = txtTenKhachHang.getText().toString().trim();
				String diaChi = txtDiaChi.getText().toString().trim();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString();

				if(sdt.matches("^[0-9]{10}$")) {

					if(tenKH.equals("") || diaChi.equals("")) {
						JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin khách hàng","ERROR",JOptionPane.ERROR_MESSAGE);
					}else {
						KhachHang resultTimKiemKH = khachHang_DAO.timKiemKhachHang(sdt);
						if(resultTimKiemKH == null) {
							resultTimKiemKH  = new KhachHang(tenKH, gioiTinh.equals("Nam") ? utils.Contains.NAM :utils.Contains.NU , sdt, diaChi);
							khachHang_DAO.themKhachHang(resultTimKiemKH);

							resultTimKiemKH = khachHang_DAO.timKiemKhachHang(sdt);
						}


						NhanVien nv = new  NhanVien();
						LocalDate ngayDat = LocalDate.now();
						System.out.println(ngayDat);
						nv.setMaNhanVien(utils.Contains.getMaNV());

						this.donHang = new DonHang(null, nv, resultTimKiemKH,ngayDat );
						datHang(this.donHang,listCTDonHang);

					}

				}else {
					JOptionPane.showMessageDialog(this,"Số điện thoại phải có đủ 10 số","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this,"Vui lòng thêm sản phẩm vào đơn hàng.","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else if(o.equals(btnLamMoi)) {
			listCTDonHang.clear();
			listSanPhamInDonHang.clear();
			modelTBLDonHang.setRowCount(0);
			lblShowTongTien.setText("0.000 VND");

		}else if(o.equals(btnXoaTrang)) {
			txtTenSanPham.setText("");
			cmbChatLieu.setSelectedIndex(0);
			cmbKichCo.setSelectedIndex(0);
			cmbLoaiSanPham.setSelectedIndex(0);
			cmbMau.setSelectedIndex(0);
			cmbNhaCungCap.setSelectedIndex(0);
		}
	}

	/**
	 * function datHang được sử dụng để insert dữ liệu vào database
	 * @param donHang đơn hàng
	 * @param listCTDonHang danh sách chi tiết đơn hàng
	 */
	private void datHang(DonHang donHang, List<ChiTietDonHang> listCTDonHang) {

		boolean resultTaoDonDatHang = donHang_DAO.taoDonHang(donHang);

		if(resultTaoDonDatHang) {
			String maDonHang = donHang_DAO.getMaDonHangVuaTao();
			donHang.setMaDonHang(maDonHang);

			for (ChiTietDonHang chiTietDonHang : listCTDonHang) {
				chiTietDonHang.setDonHang(donHang);
				chiTietDonHang_Dao.themChiTietDonHang(chiTietDonHang);
				sanPham_DAO.capNhatSoLuongSanPham(chiTietDonHang.getSanPham().getMaSanPham(), chiTietDonHang.getSanPham().getSoLuongBan() + chiTietDonHang.getSoLuong(),chiTietDonHang.getSanPham().getSoLuongTon() - chiTietDonHang.getSoLuong());
			}
			btnLamMoi.doClick();
			xoaTrangThanhToan();
			this.listSanPham.clear();
			this.listSanPham = sanPham_DAO.getDanhSachSanPham();
			loadTableProduct(this.listSanPham);
			JOptionPane.showMessageDialog(this, "Tạo đơn hàng thành công với mã ĐƠN HÀNG "+maDonHang, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}

	}


	/**
	 * function xoaTrangThanhToan được sửa dụng để xóa các giá trị đã
	 * nhập vào tại khung thanh toán
	 */
	private void xoaTrangThanhToan() {
		// TODO Auto-generated method stub
		txtSoDienThoai.setText("Số Điện Thoại Khách Hàng");
		txtTenKhachHang.setText("Tên Khách Hàng");
		txtDiaChi.setText("Địa Chỉ");
		txtSoTienDua.setText("Số Tiền Khách Đưa");

		txtTenKhachHang.setForeground(Color.decode("#A8A8B7"));
		txtSoDienThoai.setForeground(Color.decode("#A8A8B7"));
		txtDiaChi.setForeground(Color.decode("#A8A8B7"));
		txtSoTienDua.setForeground(Color.decode("#A8A8B7"));

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblSanPham)) {
			int row = tblSanPham.getSelectedRow();
			String maSanPham = tblSanPham.getValueAt(row, 0).toString().trim();
			String urlAvt = null;
			for (SanPham sp : this.listSanPham) {
				if(sp.getMaSanPham().equals(maSanPham)) {
					urlAvt = sp.getUrlAvt();
					break;
				}
			}
			try {
				ImageIcon image_avt = new ImageIcon(getClass().getResource(urlAvt));
				Image scaled = utils.Function.scaleImage(image_avt.getImage(), 160, 149);
				image_avt.setImage(scaled);
				lblAnhSanPham.setIcon(image_avt);
			} catch (Exception e2) {
				// TODO: handle exception
			}


			txtTenSanPham.setText(tblSanPham.getValueAt(row, 1).toString());
			cmbNhaCungCap.setSelectedItem(tblSanPham.getValueAt(row, 8).toString());
			cmbLoaiSanPham.setSelectedItem(tblSanPham.getValueAt(row, 3).toString());
			cmbChatLieu.setSelectedItem(tblSanPham.getValueAt(row, 6).toString());
			cmbMau.setSelectedItem(tblSanPham.getValueAt(row, 4).toString());
			cmbKichCo.setSelectedItem(tblSanPham.getValueAt(row, 5).toString());
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
