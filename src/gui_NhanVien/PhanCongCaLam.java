package gui_NhanVien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.BangPhanCa_DAO;
import dao.CaLam_DAO;
import dao.NhanVien_DAO;
import entity.BangPhanCa;
import entity.CaLam;
import entity.NhanVien;
import gui_SanPham.DanhMuc;
import utils.Contains;
import utils.Format;
import utils.Function;

public class PhanCongCaLam extends JPanel implements ActionListener, MouseListener, FocusListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int WIDTH;
	private int HEIGHT;

	private JComboBox<String> cmbKieuPhanCa;
	private JComboBox<String> cmbCaLam;
	private JComboBox<String> cmbGioiTinh;
	private JComboBox<String> cmbChucVu;

	private JTextField txtTimKiemMaNhanVien;
	private JTextField txtTimKiemTenNhanVien;
	private JTextField txtTimKiemSoDienThoai;

	private JTextField txtPhanCaMaNhanVien;

	private JDateChooser dateChooserStart;
	private JDateChooser dateChooserEnd;

	private JButton btnXoa;
	private JButton btnPhanCa;
	private JButton btnCapNhat;
	private JButton btnTimKiem;

	private JTable tblPhanCaLam;
	private JTable tblTimKiemNhanVien;

	private DefaultTableModel modelTBLPhanCaLam;
	private DefaultTableModel modelTBLTimKiemNhanVien;

	private List<NhanVien> listNV;
	private List<CaLam> listCL;
	private List<BangPhanCa> listBPC;

	private NhanVien_DAO nhanVien_DAO;
	private CaLam_DAO caLam_DAO;
	private BangPhanCa_DAO bangPhanCa_DAO;

	private JTextField txtdateStart;
	private JTextField txtdateEnd;

	/*
	 * Chưa hoàn thiện cập nhật và xóa phân ca
	 */
	public PhanCongCaLam() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		nhanVien_DAO = new NhanVien_DAO();
		caLam_DAO = new CaLam_DAO();
		bangPhanCa_DAO = new BangPhanCa_DAO();
		this.WIDTH = utils.Contains.WIDTH_PANEL_CONTENT;
		this.HEIGHT = utils.Contains.HEIGHT_PANEL_CONTENT;
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.white);
		// TODO Auto-generated method stub
		dateChooserStart = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		dateChooserEnd = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');

		txtPhanCaMaNhanVien = new JTextField();
		txtTimKiemMaNhanVien = new JTextField();
		txtTimKiemTenNhanVien = new JTextField();
		txtTimKiemSoDienThoai = new JTextField();

		btnCapNhat = new JButton("Cập Nhật");
		btnXoa = new JButton("Xóa Phân Ca");
		btnPhanCa = new JButton("Phân Ca");
		btnTimKiem = new JButton("Tìm Kiếm");

		modelTBLPhanCaLam = new DefaultTableModel();
		modelTBLTimKiemNhanVien = new DefaultTableModel();

		cmbCaLam = new JComboBox<>();
		cmbKieuPhanCa = new JComboBox<>();
		cmbKieuPhanCa.addItem(utils.Contains.KIEU_PHAN_CA_CO_DINH);
		cmbKieuPhanCa.addItem(utils.Contains.KIEU_PHAN_CA_TUY_CHINH);
		cmbGioiTinh = new JComboBox<>();
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");
		cmbChucVu = new JComboBox<>();
		cmbChucVu.addItem("Nhân Viên Quản Lý");
		cmbChucVu.addItem("Nhân Viên Bán Hàng");

		tblPhanCaLam = new JTable(modelTBLPhanCaLam) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
			}
		};

		tblTimKiemNhanVien = new JTable(modelTBLTimKiemNhanVien) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
			}
		};

		// TODO Auto-generated method stub
		// create Panel
		JPanel pnlPhanCa = new JPanel();
		JPanel pnlRow4 = new JPanel();
		// create JScrollPane
		JScrollPane scrTBLPhanCaLam = new JScrollPane(tblPhanCaLam);
		JScrollPane scrTBLTimKiemNhanVien = new JScrollPane(tblTimKiemNhanVien);

		// set Panel
		pnlRow4.setMaximumSize(new Dimension(1182, 319));
		pnlRow4.setLayout(null);
		pnlRow4.setBackground(Color.white);

		pnlPhanCa.setBounds(899, 10, 273, 302);

		pnlPhanCa.setBorder(BorderFactory.createLineBorder(Color.decode("#9B9B9B")));
		pnlPhanCa.setBackground(Color.WHITE);
		pnlPhanCa.setLayout(new BoxLayout(pnlPhanCa, BoxLayout.Y_AXIS));

		pnlRow4.add(scrTBLTimKiemNhanVien);
		pnlRow4.add(pnlPhanCa);

		// set JScrollPane
		scrTBLTimKiemNhanVien.setBounds(10, 10, 825, 302);

		scrTBLPhanCaLam.setMaximumSize(new Dimension(1162, 257));
		scrTBLPhanCaLam.setBackground(Color.white);
		scrTBLTimKiemNhanVien.setBackground(Color.white);

		// add column
		modelTBLTimKiemNhanVien.addColumn("Mã Nhân Viên");
		modelTBLTimKiemNhanVien.addColumn("Tên Nhân Viên");
		modelTBLTimKiemNhanVien.addColumn("Giới Tính");
		modelTBLTimKiemNhanVien.addColumn("Chức Vụ");
		modelTBLTimKiemNhanVien.addColumn("Số Điện Thoại");

		modelTBLPhanCaLam.addColumn("Mã phân ca");
		modelTBLPhanCaLam.addColumn("Mã Nhân Viên");
		modelTBLPhanCaLam.addColumn("Tên Nhân Viên");
		modelTBLPhanCaLam.addColumn("Số Điện Thoại");
		modelTBLPhanCaLam.addColumn("Chức Vụ");
		modelTBLPhanCaLam.addColumn("Ca Làm");
		modelTBLPhanCaLam.addColumn("Kiểu Phân Ca Làm");
		modelTBLPhanCaLam.addColumn("Ngày Bắt Đầu");
		modelTBLPhanCaLam.addColumn("Ngày Kết Thúc");

		// create label
		JLabel lblTitle = new JLabel("PHÂN CÔNG CA LÀM");
		JLabel lblPhanCa = new JLabel("Phân Ca");
		JLabel lblTimKiemMaNhanVien = new JLabel("Mã Nhân Viên");
		JLabel lblTimKiemTenNhanVien = new JLabel("Tên Nhân Viên");
		JLabel lblTimKiemSoDienThoai = new JLabel("Số Điện Thoại");
		JLabel lblTimKiemGioiTinh = new JLabel("Giới Tính");
		JLabel lblTimKiemChucVu = new JLabel("Chức Vụ");
		JLabel lblMaNhanVien = new JLabel("Mã Nhân Viên");
		JLabel lblKieuPhanCa = new JLabel("Kiểu Phân Ca");
		JLabel lblNgayBatDau = new JLabel("Ngày Bắt Đầu");
		JLabel lblNgayKetThuc = new JLabel("Ngày Kết Thúc");
		JLabel lblCaLam = new JLabel("Ca Làm");
		JLabel lblBanPhanCong = new JLabel("Bản Phân Công");

		// set label
		lblBanPhanCong.setFont(new Font("", Font.BOLD, 22));

		lblTitle.setFont(new Font("", Font.BOLD, 22));
		lblTitle.setForeground(Color.decode("#0500FF"));

		lblPhanCa.setFont(new Font("", Font.BOLD, 16));

		lblTimKiemMaNhanVien.setMaximumSize(new Dimension(108, 20));
		lblTimKiemTenNhanVien.setMaximumSize(new Dimension(108, 20));
		lblTimKiemSoDienThoai.setMaximumSize(new Dimension(100, 20));
		lblTimKiemChucVu.setMaximumSize(new Dimension(70, 20));
		lblTimKiemGioiTinh.setMaximumSize(new Dimension(70, 20));

		lblCaLam.setMaximumSize(lblNgayKetThuc.getMaximumSize());
		lblMaNhanVien.setMaximumSize(lblNgayKetThuc.getMaximumSize());
		lblKieuPhanCa.setMaximumSize(lblNgayKetThuc.getMaximumSize());
		lblNgayBatDau.setMaximumSize(lblNgayKetThuc.getMaximumSize());

		// set Button
		btnTimKiem.setMaximumSize(new Dimension(89, 31));
		btnCapNhat.setMaximumSize(new Dimension(99, 31));
		btnPhanCa.setMaximumSize(new Dimension(99, 31));
		btnXoa.setMaximumSize(new Dimension(99, 31));

		// format button event
		utils.Format.setButtonEvent(btnCapNhat, btnXoa, btnPhanCa, btnTimKiem);

		// set textField
		txtTimKiemMaNhanVien.setMaximumSize(new Dimension(175, 32));
		txtTimKiemTenNhanVien.setMaximumSize(new Dimension(175, 32));
		txtTimKiemSoDienThoai.setMaximumSize(new Dimension(175, 32));
		cmbChucVu.setMaximumSize(new Dimension(175, 32));
		cmbGioiTinh.setMaximumSize(new Dimension(175, 32));

		txtPhanCaMaNhanVien.setMaximumSize(new Dimension(105, 22));
		txtPhanCaMaNhanVien.setEditable(false);

		// set combobox
		cmbCaLam.setMaximumSize(new Dimension(100, 22));
		cmbKieuPhanCa.setMaximumSize(new Dimension(103, 22));

		// set JDateChooser
		dateChooserStart.setMaximumSize(new Dimension(105, 22));
		dateChooserEnd.setMaximumSize(new Dimension(105, 22));

		// row tile
		Box rowTiltle = Box.createHorizontalBox();
		rowTiltle.add(lblTitle);
		// row1
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
		row2.add(Box.createRigidArea(new Dimension(15, 0)));
		row2.add(Box.createRigidArea(new Dimension(30, 0)));
		row2.add(lblTimKiemChucVu);
		row2.add(Box.createRigidArea(new Dimension(15, 0)));
		row2.add(cmbChucVu);

		Box row3 = Box.createHorizontalBox();
		row3.add(Box.createRigidArea(new Dimension(700, 0)));
		row3.add(btnTimKiem);

		Box row5 = Box.createHorizontalBox();
		row5.add(lblBanPhanCong);
		// add component vào panel phân công

		Box row1PNLPhanCa = Box.createHorizontalBox();
		row1PNLPhanCa.add(lblPhanCa);

		Box row2PNLPhanCa = Box.createHorizontalBox();
		row2PNLPhanCa.setMaximumSize(new Dimension(225, 22));
		row2PNLPhanCa.add(lblMaNhanVien);
		row2PNLPhanCa.add(Box.createHorizontalStrut(33));
		row2PNLPhanCa.add(txtPhanCaMaNhanVien);

		Box row3PNLPhanCa = Box.createHorizontalBox();
		row3PNLPhanCa.setMaximumSize(new Dimension(225, 22));
		row3PNLPhanCa.add(lblCaLam);
		row3PNLPhanCa.add(Box.createHorizontalStrut(33));
		row3PNLPhanCa.add(cmbCaLam);

		Box row4PNLPhanCa = Box.createHorizontalBox();
		row4PNLPhanCa.setMaximumSize(new Dimension(225, 22));
		row4PNLPhanCa.add(lblKieuPhanCa);
		row4PNLPhanCa.add(Box.createHorizontalStrut(33));
		row4PNLPhanCa.add(cmbKieuPhanCa);

		Box row5PNLPhanCa = Box.createHorizontalBox();
		row5PNLPhanCa.setMaximumSize(new Dimension(225, 22));
		row5PNLPhanCa.add(lblNgayBatDau);
		row5PNLPhanCa.add(Box.createHorizontalStrut(33));
		row5PNLPhanCa.add(dateChooserStart);

		Box row6PNLPhanCa = Box.createHorizontalBox();
		row6PNLPhanCa.setMaximumSize(new Dimension(225, 22));
		row6PNLPhanCa.add(lblNgayKetThuc);
		row6PNLPhanCa.add(Box.createHorizontalStrut(33));
		row6PNLPhanCa.add(dateChooserEnd);

		Box row7PNLPhanCa = Box.createHorizontalBox();
		row7PNLPhanCa.setMaximumSize(new Dimension(225, 33));
		row7PNLPhanCa.add(btnPhanCa);
		row7PNLPhanCa.add(Box.createHorizontalStrut(33));
		row7PNLPhanCa.add(btnCapNhat);

		Box row8PNLPhanCa = Box.createHorizontalBox();
		row8PNLPhanCa.setMaximumSize(new Dimension(225, 33));
		row8PNLPhanCa.add(Box.createRigidArea(new Dimension(99, 31)));
		row8PNLPhanCa.add(Box.createHorizontalStrut(30));
		row8PNLPhanCa.add(btnXoa);

		pnlPhanCa.add(row1PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(25));
		pnlPhanCa.add(row2PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(7));
		pnlPhanCa.add(row3PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(7));
		pnlPhanCa.add(row4PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(7));
		pnlPhanCa.add(row5PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(7));
		pnlPhanCa.add(row6PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(30));
		pnlPhanCa.add(row7PNLPhanCa);
		pnlPhanCa.add(Box.createVerticalStrut(10));
		pnlPhanCa.add(row8PNLPhanCa);

		// add component
		add(rowTiltle);
		add(Box.createRigidArea(new Dimension(0, 17)));
		add(row1);
		add(Box.createRigidArea(new Dimension(0, 8)));
		add(row2);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(row3);
		add(Box.createRigidArea(new Dimension(0, 15)));
		add(pnlRow4);
		add(row5);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scrTBLPhanCaLam);

		txtdateStart = (JTextField) dateChooserStart.getDateEditor().getUiComponent();
		txtdateEnd = (JTextField) dateChooserEnd.getDateEditor().getUiComponent();

		btnTimKiem.addActionListener(this);
		tblTimKiemNhanVien.addMouseListener(this);
		tblPhanCaLam.addMouseListener(this);

		cmbKieuPhanCa.addActionListener(this);
		// add event
		btnCapNhat.addActionListener(this);
		btnPhanCa.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXoa.addActionListener(this);

		txtdateStart.addFocusListener(this);
		txtdateEnd.addFocusListener(this);
		// TODO Auto-generated method stub
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateCmbCaLam();
		loadTableNhanVien();
		loadTablePhanCaLam();
	}

	/**
	 * Update table Nhan Vien
	 */

	private void loadTableNhanVien() {
		NhanVien_DAO dsnv = new NhanVien_DAO();
		List<NhanVien> dsNhanVienTimDuoc = dsnv.getAllNhanVien();
		for (NhanVien nhanVien : dsNhanVienTimDuoc) {
			String[] row = { nhanVien.getMaNhanVien(), nhanVien.getTen(),
					nhanVien.getGioiTinh().equals(utils.Contains.NAM) ? "Nam" : "Nữ",
					nhanVien.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY) ? "Nhân Viên Quản Lý"
							: "Nhân Viên Bán Hàng",
					nhanVien.getSoDienThoai() };
			modelTBLTimKiemNhanVien.addRow(row);
		}
		modelTBLTimKiemNhanVien.fireTableDataChanged();
	}

	/**
	 * Cập nhật dữ liệu cho bảng phân ca.
	 */

	private void loadTablePhanCaLam() {
		modelTBLPhanCaLam.setRowCount(0);
		BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
		List<BangPhanCa> dsBangPhanCaTimDuoc = dsBPC.getBangPhanCaTuHomNay();
		for (BangPhanCa bpc : dsBangPhanCaTimDuoc) {
			CaLam cl = bpc.getCaLam();
			String strCL = utils.Format.formatDate(cl.getGioBatDau()) + "-"
					+ utils.Format.formatDate(cl.getGioKetThuc());
			Object row[] = { bpc.getMaBangPhanCa(), bpc.getNhanVien().getMaNhanVien(), bpc.getNhanVien().getTen(),
					bpc.getNhanVien().getSoDienThoai(),
					bpc.getNhanVien().getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY) ? "Nhân Viên Quản Lý"
							: "Nhân Viên Bán Hàng",
					strCL, bpc.getKieuPhanCa(), bpc.getNgayBatDau().toString(), bpc.getNgayKetThuc().toString() };
			modelTBLPhanCaLam.addRow(row);
		}
		modelTBLPhanCaLam.fireTableDataChanged();
	}

	public void updateCmbCaLam() {
		List<CaLam> dsCL = caLam_DAO.getAllCaLam();
		for (CaLam caLam : dsCL) {
			cmbCaLam.addItem(Format.formatDate(caLam.getGioBatDau()) + "-" + Format.formatDate(caLam.getGioKetThuc()));
		}
	}

	/**
	 * Kiểm tra phân ca đã bắt đầu hay chưa.
	 *
	 * @param bpc là bảng phân ca cần kiểm tra
	 * @return true nếu bắt đầu và false nếu ngược lại.
	 */

	public boolean kiemTraNgayBatDauCaLamDaLamChua(BangPhanCa bpc) {
		LocalDate ngayBatDau = bpc.getNgayBatDau();
		LocalDate now = LocalDate.now();
		return ngayBatDau.isBefore(now) || ngayBatDau.isEqual(now);
	}

	/**
	 * Kiểm tra đầu vào của thời gian đã chọn.
	 *
	 * @param ngayBatDau  Ngày bắt đầu được chọn.
	 * @param ngayKetThuc Ngày kết thúc được chọn.
	 * @return True nếu thời gian đầu vào hợp lệ, ngược lại là False.
	 */

	public boolean kiemTraDauVaoCuaThoiGianDaChon(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		LocalDate now = LocalDate.now();
		if (ngayBatDau.isBefore(now) || ngayBatDau.equals(now)) {
			JOptionPane.showMessageDialog(null, "Lỗi: Ngày bắt đầu phải sau ngày hiện tại!");
			return false;
		} else if (ngayKetThuc.isBefore(ngayBatDau)) {
			JOptionPane.showMessageDialog(null, "Lỗi: ngày kết thúc phải sau ngày bắt đầu!");
			return false;
		}
		return true;
	}

	/**
	 * Phân ca làm cho nhân viên được chọn và thời gian cụ thể.
	 */

//	public boolean kiemTraTrungCa(LocalDate ngayBatDau, ngayKetThuc) {
//		if()
//		return true;
//	}

//	public boolean kiemTraTrungCa(LocalDate ngayBatDau, LocalDate ngayKetThuc, String kieuPhanCa, CaLam caLam) {
//		BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
//		List<BangPhanCa> danhsachbpc = dsBPC.getThongTinCheckTrungPhanCa();
//		if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_CO_DINH)) {
//			for (BangPhanCa bpc : danhsachbpc) {
//				if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_CO_DINH)) {
//					if ((ngayBatDau.isAfter(bpc.getNgayBatDau())
//							|| ngayBatDau.equals(bpc.getNgayBatDau())) && ngayBatDau.isBefore(bpc.getNgayKetThuc()) || ngayKetThuc.equals(bpc.getNgayBatDau()) || ngayBatDau.equals(bpc.getNgayKetThuc())) {
//						if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam())) {
//							System.out.println("Trung A1");
//							System.out.println(bpc.getCaLam().getMaCaLam());
//							System.out.println(caLam.getMaCaLam());
//							//System.out.println();
//							return false;
//						}
//					}
//
//				} else if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
//					if ((bpc.getNgayBatDau().isAfter(ngayBatDau) || bpc.getNgayBatDau().equals(ngayBatDau))
//							&& (bpc.getNgayBatDau().isBefore(ngayKetThuc) || bpc.getNgayBatDau().equals(ngayKetThuc))) {
//						if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam())) {
//							System.out.println("Trung A2");
//							return false;
//						}
//					}
//
//				}
//			}
//		} else if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
//			for (BangPhanCa bpc : danhsachbpc) {
//				if (bpc.equals(Contains.KIEU_PHAN_CA_CO_DINH)) {
//					if ((ngayBatDau.isAfter(bpc.getNgayBatDau()) || ngayBatDau.equals(bpc.getNgayBatDau()))
//							&& (ngayBatDau.isBefore(bpc.getNgayKetThuc()) || ngayBatDau.equals(bpc.getNgayKetThuc()))) {
//						if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam())) {
//							System.out.println("Trung A3");
//							return false;
//						}
//
//					} else if (bpc.equals(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
//						if (ngayBatDau.equals(bpc.getNgayBatDau())) {
//							if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam())) {
//								System.out.println("Trung A4");
//								return false;
//							}
//
//						}
//
//					}
//				}
//			}
//		}
//
//		return true;
//	}

	public boolean kiemTraTrungCa(LocalDate ngayBatDau, LocalDate ngayKetThuc, String kieuPhanCa, CaLam caLam,
			String type) {
		BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
		// List<BangPhanCa> danhsachbpc = dsBPC.getThongTinCheckTrungPhanCa();
		List<BangPhanCa> danhsachbpc = dsBPC.getAllBangPhanCa();
		int rowSelect = tblPhanCaLam.getSelectedRow();

		String maBPC = rowSelect >= 0 ? tblPhanCaLam.getValueAt(rowSelect, 0).toString() : "";
		String maNV = tblTimKiemNhanVien.getSelectedRow() >= 0
				? tblTimKiemNhanVien.getValueAt(tblTimKiemNhanVien.getSelectedRow(), 0).toString()
				: "";
		System.out.println(maNV + "a");
		if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_CO_DINH)) {
			for (BangPhanCa bpc : danhsachbpc) {
				System.out.println(bpc.getNhanVien().getMaNhanVien()+"b");
				if (!bpc.getMaBangPhanCa().equals(maBPC)
						|| type.equals("Them") ) {
					if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_CO_DINH)) {
						if ((ngayBatDau.isBefore(bpc.getNgayBatDau()) && ngayKetThuc.isBefore(bpc.getNgayBatDau()))
								|| (ngayBatDau.isAfter(bpc.getNgayKetThuc()))) {

						} else {
							if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam()) && maNV.equals(bpc.getNhanVien().getMaNhanVien())) {
								JOptionPane.showMessageDialog(null, "Trung ca co dinh voi ca co dinh");
								// System.out.println();
								return false;
							}
						}

					} else if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
						if ((bpc.getNgayBatDau().isAfter(ngayBatDau) || bpc.getNgayBatDau().equals(ngayBatDau))
								&& (bpc.getNgayBatDau().isBefore(ngayKetThuc)
										|| bpc.getNgayBatDau().equals(ngayKetThuc))) {
							if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam()) && maNV.equals(bpc.getNhanVien().getMaNhanVien())) {
								JOptionPane.showMessageDialog(null, "Trung ca co dinh voi ca tuy chinh");
								return false;
							}
						}

					}
				}

			}
		} else if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
			for (BangPhanCa bpc : danhsachbpc) {
				System.out.println(bpc.getMaBangPhanCa());
				if (!bpc.getMaBangPhanCa().equals(maBPC)
						|| (type.equals("Them") && !bpc.getNhanVien().getMaNhanVien().equals(maNV))) {
					if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_CO_DINH)) {
						if ((ngayBatDau.isBefore(bpc.getNgayBatDau()) && ngayBatDau.isBefore(bpc.getNgayKetThuc()))
								|| (ngayBatDau.isAfter(bpc.getNgayBatDau())
										&& ngayBatDau.isAfter(bpc.getNgayKetThuc()))) {

						} else {
							if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam()) && maNV.equals(bpc.getNhanVien().getMaNhanVien())) {
								JOptionPane.showMessageDialog(null, "Trung ca tuy chinh voi ca co dinh");
								return false;
							}

						}
					} else if (bpc.getKieuPhanCa().equals(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
						System.out.println(ngayBatDau);
						System.out.println(bpc.getNgayBatDau());
						if (ngayBatDau.equals(bpc.getNgayBatDau())) {
							if (caLam.getMaCaLam().equalsIgnoreCase(bpc.getCaLam().getMaCaLam()) && maNV.equals(bpc.getNhanVien().getMaNhanVien())) {
								JOptionPane.showMessageDialog(null, "Trung ca tuy chinh voi ca tuy chinh");
								return false;
							}

						}

					}
				}

			}
		}

		return true;
	}

	public void phanCaLam() {
		int rowSelected = tblTimKiemNhanVien.getSelectedRow();
		if (rowSelected != -1) {
			String maNV = tblTimKiemNhanVien.getValueAt(rowSelected, 0).toString();
			String thoiGianCuaCaLam = cmbCaLam.getSelectedItem().toString();

			NhanVien nv = nhanVien_DAO.getNhanVienTheoMa(maNV);
			CaLam caLam = timKiemCaLam(thoiGianCuaCaLam);

//			Đổi Date to LocalDateDate dateToConvert = dateChooserStart.getDate();
			LocalDate ngayBatDau = null;
			LocalDate ngayKetThuc = null;

			if (dateChooserStart.getDate() != null) {
				// Tiếp tục chuyển đổi sang LocalDate
				ngayBatDau = Function.convertToLocalDateViaMilisecond(dateChooserStart.getDate());
				// Thực hiện các bước tiếp theo với ngayBatDau
			} else {
				// Xử lý khi dateToConvert là null
				JOptionPane.showMessageDialog(null, "Lỗi: Ngày bắt đầu không được để trống!");
				// Hoặc thực hiện hành động phù hợp khi ngày bắt đầu không có giá trị
			}

			String kieuPhanCa = cmbKieuPhanCa.getSelectedItem().toString();
			if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
				ngayKetThuc = ngayBatDau;
			} else if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_CO_DINH)) {
				if (dateChooserEnd.getDate() != null) {
					ngayKetThuc = Function.convertToLocalDateViaMilisecond(dateChooserEnd.getDate());
				} else {
					// Xử lý khi dateToConvert là null
					JOptionPane.showMessageDialog(null, "Lỗi: Ngày kết thúc không được để trống!");
					// Hoặc thực hiện hành động phù hợp khi ngày bắt đầu không có giá trị
				}
			}

//			LocalDate ngayBatDau = utils.Function.convertToLocalDateViaMilisecond(dateChooserStart.getDate());
//			LocalDate ngayKetThuc = utils.Function.convertToLocalDateViaMilisecond(dateChooserEnd.getDate());

			if (dateChooserStart.getDate() != null && dateChooserEnd.getDate() != null || !dateChooserEnd.isEnabled()) {
				if (kiemTraDauVaoCuaThoiGianDaChon(ngayBatDau, ngayKetThuc)) {
					if (kiemTraTrungCa(ngayBatDau, ngayKetThuc, kieuPhanCa, caLam, "Them")) {
						BangPhanCa bangPhanCa = new BangPhanCa(nv, caLam, ngayBatDau, ngayKetThuc, kieuPhanCa);
						if (bangPhanCa_DAO.themBangPhanCa(bangPhanCa)) {
//							Object row[] = { bangPhanCa_DAO.getMaBangPhanCaVuaTao(),
//									bangPhanCa.getNhanVien().getMaNhanVien(), bangPhanCa.getNhanVien().getTen(),
//									bangPhanCa.getNhanVien().getSoDienThoai(),
//									bangPhanCa.getNhanVien().getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY)
//											? "Nhân Viên Quản Lý"
//											: "Nhân Viên Bán Hàng",
//									Format.formatDate(bangPhanCa.getCaLam().getGioBatDau()) + "-"
//											+ Format.formatDate(bangPhanCa.getCaLam().getGioKetThuc()),
//									bangPhanCa.getKieuPhanCa(), bangPhanCa.getNgayBatDau(),
//									bangPhanCa.getNgayKetThuc() };
//							// System.out.println(bangPhanCa.getMaBangPhanCa());
//							modelTBLPhanCaLam.addRow(row);
//							modelTBLPhanCaLam.fireTableDataChanged();
							loadTablePhanCaLam();
							JOptionPane.showMessageDialog(null, "Thêm phân ca thành công.");
						} else {
							JOptionPane.showMessageDialog(null, "Thêm không thành công!");
							return;
						}
					}
//					else {
//						JOptionPane.showMessageDialog(null, "Trung ca");
//					}

				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên");
			return;
		}

	}

	/**
	 * Tìm kiếm đối tượng CaLam dựa trên thời gian.
	 *
	 * @param thoiGian Thời gian cần tìm kiếm, được biểu diễn dưới dạng chuỗi
	 *                 "HH:mm-HH:mm".
	 * @return Đối tượng CaLam tương ứng với thời gian hoặc null nếu không tìm thấy.
	 */

	public CaLam timKiemCaLam(String thoiGian) {
		List<CaLam> dsCaLam = caLam_DAO.getAllCaLam();
		for (CaLam caLam : dsCaLam) {
			if ((utils.Format.formatDate(caLam.getGioBatDau()) + "-" + utils.Format.formatDate(caLam.getGioKetThuc()))
					.equalsIgnoreCase(thoiGian)) {
				return caLam;
			}
		}
		return null;
	}

	/**
	 * Cập nhật thông tin về phân ca làm cho nhân viên được chọn.
	 * 
	 * chưa được sửa
	 */

	public void capNhatPhanCa() {
		int row = tblPhanCaLam.getSelectedRow();
		if (row != -1) {
			String maBangPhanCa = tblPhanCaLam.getValueAt(row, 0).toString();
			String maNV = tblPhanCaLam.getValueAt(row, 1).toString();
			NhanVien nv = nhanVien_DAO.getNhanVienTheoMa(maNV);
			String thoiGianLam = cmbCaLam.getSelectedItem().toString().trim();
			CaLam caLam = timKiemCaLam(thoiGianLam);

			String kieuPhanCa = cmbKieuPhanCa.getSelectedItem().toString();

			LocalDate ngayBatDau = null;
			LocalDate ngayKetThuc = null;

			if (dateChooserStart.getDate() != null) {
				// Tiếp tục chuyển đổi sang LocalDate
				ngayBatDau = Function.convertToLocalDateViaMilisecond(dateChooserStart.getDate());
				// Thực hiện các bước tiếp theo với ngayBatDau
			} else {
				// Xử lý khi dateToConvert là null
				JOptionPane.showMessageDialog(null, "Lỗi: Ngày bắt đầu không được để trống!");
				// Hoặc thực hiện hành động phù hợp khi ngày bắt đầu không có giá trị
			}

			if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
				ngayKetThuc = ngayBatDau;
			} else if (kieuPhanCa.equalsIgnoreCase(Contains.KIEU_PHAN_CA_CO_DINH)) {
				if (dateChooserEnd.getDate() != null) {
					ngayKetThuc = Function.convertToLocalDateViaMilisecond(dateChooserEnd.getDate());
				} else {
					// Xử lý khi dateToConvert là null
					JOptionPane.showMessageDialog(null, "Lỗi: Ngày kết thúc không được để trống!");
					// Hoặc thực hiện hành động phù hợp khi ngày bắt đầu không có giá trị
				}
			}

//				LocalDate ngayBatDau = utils.Function.convertToLocalDateViaMilisecond(dateChooserStart.getDate());
//				LocalDate ngayKetThuc = utils.Function.convertToLocalDateViaMilisecond(dateChooserEnd.getDate());

			if (dateChooserStart.getDate() != null && dateChooserEnd.getDate() != null || !dateChooserEnd.isEnabled()) {
				if (kiemTraDauVaoCuaThoiGianDaChon(ngayBatDau, ngayKetThuc)) {
					if (kiemTraTrungCa(ngayBatDau, ngayKetThuc, kieuPhanCa, caLam, "CapNhat")) {
						BangPhanCa bpcCanCapNhat = new BangPhanCa(maBangPhanCa, nv, caLam, ngayBatDau, ngayKetThuc,
								kieuPhanCa);
						if (bangPhanCa_DAO.capNhatBangPhanCaLam(bpcCanCapNhat)) {
//							tblPhanCaLam.setValueAt(cmbKieuPhanCa.getSelectedItem().toString(), row, 6);
//							tblPhanCaLam.setValueAt(bpcCanCapNhat.getNgayBatDau(), row, 7);
//							tblPhanCaLam.setValueAt(bpcCanCapNhat.getNgayKetThuc(), row, 8);
							loadTablePhanCaLam();
							JOptionPane.showMessageDialog(null, "Cập nhật phân ca thành công.");
						} else {
							JOptionPane.showMessageDialog(null, "Trùng ca !");
							return;
						}
					} else {
						return;
					}
				} else {
					JOptionPane.showMessageDialog(PhanCongCaLam.this,
							"Vui lòng chọn nhân viên trước khi cập nhật ca", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiem)) {
			List<NhanVien> list = new ArrayList<>();
			String maNV = txtTimKiemMaNhanVien.getText().toString().trim();
			String tenNV = txtTimKiemTenNhanVien.getText().toString().trim();
			for (NhanVien nhanVien : listNV) {
				if (nhanVien.getMaNhanVien().contains(maNV) && nhanVien.getTen().contains(tenNV)) {
					list.add(nhanVien);
				}
			}
			loadTableNhanVien();
		} else if (o.equals(btnPhanCa)) {
			phanCaLam();
		} else if (o.equals(cmbKieuPhanCa)) {
			String strItem = cmbKieuPhanCa.getSelectedItem().toString();
			if (strItem.equalsIgnoreCase(Contains.KIEU_PHAN_CA_TUY_CHINH)) {
				dateChooserEnd.setEnabled(false);
				System.out.println(strItem);
			} else {
				dateChooserEnd.setEnabled(true);
			}
		} else if (o.equals(btnCapNhat)) {
			txtPhanCaMaNhanVien.setEditable(false);
			txtPhanCaMaNhanVien.setEditable(false);
			capNhatPhanCa();
			txtPhanCaMaNhanVien.setEditable(true);
			txtPhanCaMaNhanVien.setEditable(true);

		} else if (o.equals(btnXoa)) {
			int row = tblPhanCaLam.getSelectedRow();
			if (row != -1) {
				String maBangPhanCa = tblPhanCaLam.getValueAt(row, 0).toString();
				String maNV = tblPhanCaLam.getValueAt(row, 1).toString();
				NhanVien nv = nhanVien_DAO.getNhanVienTheoMa(maNV);
				String thoiGianLam = tblPhanCaLam.getValueAt(row, 4).toString();
				CaLam cl = timKiemCaLam(thoiGianLam);
				LocalDate ngayBatDau = Function.convertToLocalDateViaMilisecond(dateChooserStart.getDate());
				LocalDate ngayKetThuc = Function.convertToLocalDateViaMilisecond(dateChooserEnd.getDate());
				String kieuPhanCa = tblPhanCaLam.getValueAt(row, 5).toString();
				BangPhanCa bangPhanCaCanXoa = new BangPhanCa(nv, cl, ngayBatDau, ngayKetThuc, kieuPhanCa);
				boolean resultkiemTraNgayBatDauCaLamDaLamChua = kiemTraNgayBatDauCaLamDaLamChua(bangPhanCaCanXoa);
				if (!resultkiemTraNgayBatDauCaLamDaLamChua) {
					int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phân ca hay không?",
							"WRONG", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						if (bangPhanCa_DAO.xoaBangPhanCa(maBangPhanCa)) {
							modelTBLPhanCaLam.removeRow(row);
							modelTBLPhanCaLam.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "Đã xóa phân ca được chọn!");
						} else {
							JOptionPane.showMessageDialog(null, "Không thể xóa ca làm được chọn!");
						}

					}
				} else {
					JOptionPane.showMessageDialog(PhanCongCaLam.this,
							"Không thể xóa phân ca, Khi ca làm đã được làm hoặc đang làm", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(PhanCongCaLam.this, "Vui lòng chọn nhân viên trước khi cập nhật ca",
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tblTimKiemNhanVien)) {
			int row = tblTimKiemNhanVien.getSelectedRow();
			txtPhanCaMaNhanVien.setText(tblTimKiemNhanVien.getValueAt(row, 0).toString());
			txtTimKiemMaNhanVien.setText(tblTimKiemNhanVien.getValueAt(row, 0).toString());
			txtTimKiemTenNhanVien.setText(tblTimKiemNhanVien.getValueAt(row, 1).toString());
			cmbGioiTinh.setSelectedItem(tblTimKiemNhanVien.getValueAt(row, 2).toString());
			cmbChucVu.setSelectedItem(tblTimKiemNhanVien.getValueAt(row, 3).toString());
			txtTimKiemSoDienThoai.setText(tblTimKiemNhanVien.getValueAt(row, 4).toString());

			btnCapNhat.setEnabled(false);
			btnXoa.setEnabled(false);
			btnPhanCa.setEnabled(true);
			dateChooserStart.setEnabled(true);
			
		} else if (o.equals(tblPhanCaLam)) {

			btnCapNhat.setEnabled(true);
			btnXoa.setEnabled(true);
			btnPhanCa.setEnabled(false);

			String format = "yyyy-MM-dd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			int row = tblPhanCaLam.getSelectedRow();
			try {
				Date ngayBatDau = dateFormat.parse(tblPhanCaLam.getValueAt(row, 7).toString().trim());
				dateChooserStart.setDate(ngayBatDau);

				Date ngayKetThuc = dateFormat.parse(tblPhanCaLam.getValueAt(row, 8).toString().trim());
				dateChooserEnd.setDate(ngayKetThuc);

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			txtPhanCaMaNhanVien.setText(tblPhanCaLam.getValueAt(row, 1).toString());
			cmbCaLam.setSelectedItem(tblPhanCaLam.getValueAt(row, 5).toString());
			cmbKieuPhanCa.setSelectedItem(tblPhanCaLam.getValueAt(row, 6).toString());
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}
}
