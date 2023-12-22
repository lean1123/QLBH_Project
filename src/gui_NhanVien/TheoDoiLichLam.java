package gui_NhanVien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.BangPhanCa_DAO;
import entity.BangPhanCa;
import entity.CaLam;
import entity.NhanVien;

public class TheoDoiLichLam extends JPanel implements ActionListener,PropertyChangeListener, FocusListener{
	private JTable tblLichLamViec;
	private DefaultTableModel modelTBLLichLamViec;

	private JComboBox<String> cmbChucVu;
	private DefaultComboBoxModel<String> modelCMBChucVu;

	private JTextField txtMaNhanVien;
	private JTextField txtTenNhanVien;
	private JTextField txtSoDienThoai;
	private JDateChooser dateChooserStart;
	private JDateChooser dateChooserEnd;

	private JButton btnTimKiem;

	private List<BangPhanCa> listBPC;
	private BangPhanCa_DAO bangPhanCa_DAO;

	private JLabel lblErrorMaNhanVien;
	private JLabel lblErrorSDT;

	private	JTextField txtdateStart;
	private JTextField txtdateEnd;


	public TheoDoiLichLam() {
		setSize(utils.Contains.WIDTH_PANEL_CONTENT,utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
		BangPhanCa bangPhanCa = new BangPhanCa();
		LocalDate ngayBatDau = LocalDate.now();
		LocalDate ngayKetThuc = ngayBatDau.plusDays(7);

		bangPhanCa.setNgayBatDau(ngayBatDau);
		bangPhanCa.setNgayKetThuc(ngayKetThuc);

		try {
			ConnectDB.getInstance().connect();
			listBPC = bangPhanCa_DAO.timKiemPhanCa(bangPhanCa);
			LoadTablePhanCaLam(listBPC, ngayBatDau, ngayKetThuc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		modelCMBChucVu.addElement("");
		modelCMBChucVu.addElement("Nhân Viên Bán Hàng");
		modelCMBChucVu.addElement("Nhân Viên Quản Lý");
	}
	/**
	 * function createGUI dùng để tạo giao diện người dùng
	 */
	private void createGUI() {
		// TODO Auto-generated method stub

		//create JLabel
		JLabel lblTitle = new JLabel("THEO DÕI LỊCH LÀM");
		JLabel lblMaNhanVien = new JLabel("Mã Nhân Viên");
		JLabel lblTenNhanVien = new JLabel("Tên Nhân Viên");
		JLabel lblSoDienThoai = new JLabel("Số Điện Thoại");
		JLabel lblNgayBatDau = new JLabel("Ngày Bắt Đầu");
		JLabel lblNgayKetThuc = new JLabel("Ngày Kết Thúc");
		JLabel lblChucVu= new JLabel("Chức Vụ");

		//set JLabel
		lblTitle.setFont(new Font("", Font.BOLD, 22));
		lblTitle.setForeground(Color.decode("#0500FF"));

		lblErrorMaNhanVien.setForeground(Color.red);
		lblErrorSDT.setForeground(Color.red);

		lblChucVu.setMaximumSize(lblSoDienThoai.getMaximumSize());
		// add Column
		modelTBLLichLamViec.addColumn("Mã Nhân Viên");
		modelTBLLichLamViec.addColumn("Tên Nhân Viên");
		modelTBLLichLamViec.addColumn("Số Điện Thoại");
		modelTBLLichLamViec.addColumn("Chức Vụ");
		modelTBLLichLamViec.addColumn("Ca Làm");
		modelTBLLichLamViec.addColumn("Kiểu Phân Ca Làm");
		modelTBLLichLamViec.addColumn("Ngày Làm");

		//set size Component

		txtMaNhanVien.setMaximumSize(new Dimension(155,32));
		txtTenNhanVien.setMaximumSize(new Dimension(155,32));
		txtSoDienThoai.setMaximumSize(new Dimension(155,32));
		cmbChucVu.setMaximumSize(new Dimension(155,32));

		txtMaNhanVien.setMargin(new Insets(0,10,0,0));
		txtTenNhanVien.setMargin(new Insets(0,10,0,0));
		txtSoDienThoai.setMargin(new Insets(0,10,0,0));

		dateChooserEnd.setMaximumSize(new Dimension(153,32));
		dateChooserStart.setMaximumSize(new Dimension(153,32));

		lblErrorMaNhanVien.setMaximumSize(new Dimension(180,15));
		lblErrorSDT.setMaximumSize(new Dimension(155,15));

		utils.Format.setButtonEvent(btnTimKiem);
		btnTimKiem.setMaximumSize(new Dimension(89,32));
		//row title
		Box rowTitle = Box.createHorizontalBox();
		rowTitle.add(lblTitle);
		//row
		Box row1 = Box.createHorizontalBox();
		row1.add(lblMaNhanVien);
		row1.add(Box.createRigidArea(new Dimension(20,0)));
		row1.add(txtMaNhanVien);
		row1.add(Box.createRigidArea(new Dimension(40,0)));
		row1.add(lblTenNhanVien);
		row1.add(Box.createRigidArea(new Dimension(20,0)));
		row1.add(txtTenNhanVien);
		row1.add(Box.createRigidArea(new Dimension(40,0)));
		row1.add(lblSoDienThoai);
		row1.add(Box.createRigidArea(new Dimension(20,0)));
		row1.add(txtSoDienThoai);

		Box row1Error = Box.createHorizontalBox();
		row1Error.add(Box.createRigidArea(new Dimension(115,0)));
		row1Error.add(lblErrorMaNhanVien);
		row1Error.add(Box.createRigidArea(new Dimension(460,0)));
		row1Error.add(lblErrorSDT);

		Box row2 = Box.createHorizontalBox();
		row2.add(lblNgayBatDau);
		row2.add(Box.createRigidArea(new Dimension(20,0)));
		row2.add(dateChooserStart);
		row2.add(Box.createRigidArea(new Dimension(45,0)));
		row2.add(lblNgayKetThuc);
		row2.add(Box.createRigidArea(new Dimension(20,0)));
		row2.add(dateChooserEnd);
		row2.add(Box.createRigidArea(new Dimension(45,0)));
		row2.add(lblChucVu);
		row2.add(Box.createRigidArea(new Dimension(20,0)));
		row2.add(cmbChucVu);

		Box row3 = Box.createHorizontalBox();
		row3.add(Box.createRigidArea(new Dimension(0,70)));
		row3.add(Box.createRigidArea(new Dimension(600,0)));
		row3.add(btnTimKiem);

		Box row4 = Box.createHorizontalBox();
		JScrollPane scrTBLLichLamViec = new JScrollPane(tblLichLamViec);
		scrTBLLichLamViec.setMaximumSize(new Dimension(1115,480));
		row4.add(Box.createRigidArea(new Dimension(0,70)));
		row4.add(scrTBLLichLamViec);

		add(rowTitle);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(row1);
		add(row1Error);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(row2);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(row3);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(row4);

		txtdateStart = (JTextField) dateChooserStart.getDateEditor().getUiComponent();
		txtdateEnd = (JTextField) dateChooserEnd.getDateEditor().getUiComponent();

		dateChooserStart.addPropertyChangeListener(this);
		dateChooserEnd.addPropertyChangeListener(this);
		txtdateStart.addFocusListener(this);
		txtdateEnd.addFocusListener(this);

		btnTimKiem.addActionListener(this);


	}

	/**
	 * function LoadTablePhanCaLam được sử dụng để xuất danh sách lịch làm ra giao diện người dùng
	 *
	 * @param list danh sách phân ca làm
	 * @param ngayBatDauTimKiem ngày bắt đầu tìm kiếm
	 * @param ngayKetThucTimKiem ngày kết thúc tìm kiếm
	 *
	 */
	private void LoadTablePhanCaLam(List<BangPhanCa> list, LocalDate ngayBatDauTimKiem, LocalDate ngayKetThucTimKiem) {
		modelTBLLichLamViec.setRowCount(0);
		int i = 1;
		while(ngayBatDauTimKiem.compareTo(ngayKetThucTimKiem) <= 0){
			for (BangPhanCa bpc : list) {
				LocalDate ngayBatDau = bpc.getNgayBatDau();
				LocalDate ngayKetThuc = bpc.getNgayKetThuc();
				if(ngayKetThuc == null) {
					ngayKetThuc = ngayKetThucTimKiem;
					bpc.setNgayKetThuc(ngayKetThucTimKiem);
				}
				if(ngayBatDau.compareTo(ngayBatDauTimKiem) < 0) {
					ngayBatDau = ngayBatDauTimKiem;
					bpc.setNgayBatDau(ngayBatDau);
				}
				if(ngayBatDau.compareTo(ngayBatDauTimKiem) == 0) {
					if(ngayBatDau.compareTo(ngayKetThuc) <= 0) {
						NhanVien nv = bpc.getNhanVien();
						CaLam cl = bpc.getCaLam();
						String strCL = utils.Format.formatDate(cl.getGioBatDau())+"-"+utils.Format.formatDate(cl.getGioKetThuc());
						String[] row = {nv.getMaNhanVien(),nv.getTen(),nv.getSoDienThoai(),nv.getChucVu().equals(utils.Contains.NHAN_VIEN_QUAN_LY)?"Nhân Viên Quản Lý":"Nhân Viên Bán Hàng",strCL,bpc.getKieuPhanCa(),bpc.getNgayBatDau().toString()};
						modelTBLLichLamViec.addRow(row);
						ngayBatDau  = ngayBatDau.plusDays(i);
						bpc.setNgayBatDau(ngayBatDau);
					}
				}

			}
			ngayBatDauTimKiem = ngayBatDauTimKiem.plusDays(i);
		}
	}

	/**
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		// TODO Auto-generated method stub
		modelCMBChucVu = new DefaultComboBoxModel<>();
		modelTBLLichLamViec = new DefaultTableModel();

		tblLichLamViec = new JTable(modelTBLLichLamViec){
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa dữ liệu trong JTable
            }
		};

		txtMaNhanVien = new JTextField();
		txtTenNhanVien  = new JTextField();
		txtSoDienThoai  = new JTextField();
		dateChooserStart = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		dateChooserEnd = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');

		btnTimKiem = new JButton("Tìm Kiếm");

		cmbChucVu = new JComboBox<>(modelCMBChucVu);

		listBPC = new ArrayList<>();
		bangPhanCa_DAO = new BangPhanCa_DAO();

		lblErrorMaNhanVien = new JLabel();
		lblErrorSDT = new JLabel();

	}
	/*
	 * function kiemTraKhiNguoiDungCapNhatNgayBatDau được sử dụng nếu người dùng chọn ngày
	 * bắt đầu thì enable ngày kết thúc cho người dùng chọn
	 */
	private void kiemTraKhiNguoiDungCapNhatNgayBatDau() {
		if(dateChooserStart.getDate() != null) {
			dateChooserEnd.setEnabled(true);
		}else {
			dateChooserEnd.setEnabled(false);
			btnTimKiem.setEnabled(false);
		}
	}
	/**
	 * function kiemTraKhiNguoiDungCapNhatNgayKetThuc được sử dụng để kiểm tra
	 * người dùng chọn ngày kết thúc có lơn hơn ngày bắt đầu hay không
	 *
	 * @return trả về true khi ngày bắt đầu >= ngày bắt đầu và ngược lại
	 */
	private boolean kiemTraKhiNguoiDungCapNhatNgayKetThuc() {
		if(dateChooserEnd.getDate() != null && dateChooserStart.getDate()!= null) {
			Date dateStart = dateChooserStart.getDate();
			Date dateEnd = dateChooserEnd.getDate();

			String strDateStart = dateStart.toString();
			String strDateEnd = dateEnd.toString();

			LocalDate localDateStart = LocalDate.parse(strDateStart, DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
			LocalDate localDateEnd = LocalDate.parse(strDateEnd, DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));

			localDateStart.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			localDateEnd.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

			if(localDateStart.compareTo(localDateEnd) <= 0) {
				btnTimKiem.setEnabled(true);
				return true;
			}else {
				btnTimKiem.setEnabled(false);
				dateChooserEnd.setDate(null);
				return false;

			}
		}
		btnTimKiem.setEnabled(false);
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnTimKiem)) {
			Date dateStart = dateChooserStart.getDate();
			Date dateEnd = dateChooserEnd.getDate();

			if(dateStart != null || dateEnd != null) {
				String strDateStart = dateStart.toString();
				String strDateEnd = dateEnd.toString();

				LocalDate localDateStart = LocalDate.parse(strDateStart, DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
				LocalDate localDateEnd = LocalDate.parse(strDateEnd, DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));

				String maNhanVien = txtMaNhanVien.getText().toString().trim();
				String tenNhanVien = txtTenNhanVien.getText().toString().trim();
				String soDienThoai = txtSoDienThoai.getText().toString().trim();
				String chucVu = cmbChucVu.getSelectedItem().toString();
				chucVu = chucVu.equals("Nhân Viên Quản Lý")?utils.Contains.NHAN_VIEN_QUAN_LY: chucVu.equals("Nhân Viên Bán Hàng") ? utils.Contains.NHAN_VIEN_BAN_HANG : "";

				if(soDienThoai.matches("^[0-9]{10}$") || soDienThoai.equals("")) {
					lblErrorSDT.setText("");
					txtSoDienThoai.setForeground(null);
					if(maNhanVien.matches("^NV[0-9]{6}$") || maNhanVien.equals("")) {
						lblErrorMaNhanVien.setText("");
						txtMaNhanVien.setForeground(null);

						BangPhanCa bpc = new BangPhanCa();
						NhanVien nv = new NhanVien();
						nv.setMaNhanVien(maNhanVien);
						nv.setTen(tenNhanVien);
						nv.setChucVu(chucVu);
						nv.setSoDienThoai(soDienThoai);

						bpc.setNhanVien(nv);
						bpc.setNgayBatDau(localDateStart);
						bpc.setNgayKetThuc(localDateEnd);

						listBPC.clear();
						listBPC = bangPhanCa_DAO.timKiemPhanCa(bpc);
						LoadTablePhanCaLam(listBPC, localDateStart, localDateEnd);

					}else {
						lblErrorMaNhanVien.setText("Format: NVxxxxxx (x là số)");
						txtMaNhanVien.setForeground(Color.red);
						txtMaNhanVien.requestFocus();
						txtMaNhanVien.selectAll();
					}
				}else {
					lblErrorSDT.setText("Số điện thoại phải đủ 10 ký tự số");
					txtSoDienThoai.setForeground(Color.red);
					txtSoDienThoai.requestFocus();
					txtSoDienThoai.selectAll();
				}


			}else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và ngày kết thúc trước khi tìm kiếm", "ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		Object o  = e.getSource();
		if(o.equals(txtdateStart)) {
			kiemTraKhiNguoiDungCapNhatNgayBatDau();
		}else if(o.equals(txtdateEnd)) {
			kiemTraKhiNguoiDungCapNhatNgayKetThuc();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		Object o  = evt.getSource();
		if(o.equals(dateChooserStart)) {
			kiemTraKhiNguoiDungCapNhatNgayBatDau();
		}else if(o.equals(dateChooserEnd)) {
			if(dateChooserStart.getDate()!=null && dateChooserEnd.getDate()!=null) {
				if(!kiemTraKhiNguoiDungCapNhatNgayKetThuc()) {
					JOptionPane.showMessageDialog(TheoDoiLichLam.this, "Vui lòng chọn ngày kết thúc và Ngày kế thúc phải sau hoặc bằng ngày bắt đầu", "ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}


		}
	}
}
