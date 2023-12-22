package gui_NhanVien;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.ui.tabbedui.RootPanel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.BangChamCong_DAO;
import dao.BangPhanCa_DAO;
import dao.CaLam_DAO;
import dao.NhanVien_DAO;
import entity.BangPhanCa;
import entity.CaLam;
import entity.NhanVien;
import utils.Contains;
import utils.Format;
import utils.Function;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class BangChamCong extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaNV, txtHoTen;
	private JTable tblChamCong, tblNhanVien;
	private JButton btnTimKiem, btnChmCng, btnXoaTrang, btnCapNhatChamCong;
	private DefaultTableModel dfChamCong, dfNhanVien;
	private JDateChooser jdcThoiGianChamCong, jdcNgayBatDau, jdcNgayKetThuc;
	private BangChamCong_DAO dsBangChamCong;
	private JScrollPane scrChamCongNhanVien;
	private JCheckBox chckbxCoDiLam, chckbxCoPhep;
	private JTextArea textArea;
	private JComboBox<String> cmbChucVu, cmbCaLam, cmbKieuPhanCa;
	private JTextField txtSDT;
	private JTextField txtMaPhanCa;

	public BangChamCong() {
		dsBangChamCong = new BangChamCong_DAO();

		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(null);

		JLabel lblTitle = new JLabel("BẢNG CHẤM CÔNG");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTitle.setBounds(503, 10, 330, 42);
		add(lblTitle);

		JPanel pnFormTimKiemNhanVien = new JPanel();
		pnFormTimKiemNhanVien.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Nhân Viên", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormTimKiemNhanVien.setBounds(10, 64, 1174, 143);
		add(pnFormTimKiemNhanVien);
		pnFormTimKiemNhanVien.setLayout(null);

		JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMaNhanVien.setBounds(250, 23, 99, 24);
		pnFormTimKiemNhanVien.add(lblMaNhanVien);

		txtMaNV = new JTextField();
		txtMaNV.setBounds(359, 23, 105, 24);
		pnFormTimKiemNhanVien.add(txtMaNV);
		txtMaNV.setColumns(10);

		JLabel lbl = new JLabel("Họ tên:");
		lbl.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lbl.setBounds(474, 23, 63, 24);
		pnFormTimKiemNhanVien.add(lbl);

		txtHoTen = new JTextField();
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(547, 24, 105, 24);
		pnFormTimKiemNhanVien.add(txtHoTen);

		JLabel lblChcV = new JLabel("Chức vụ:");
		lblChcV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChcV.setBounds(984, 21, 70, 24);
		pnFormTimKiemNhanVien.add(lblChcV);

		JLabel lblaCh = new JLabel("Kiểu phân ca:");
		lblaCh.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblaCh.setBounds(10, 72, 80, 24);
		pnFormTimKiemNhanVien.add(lblaCh);

		cmbChucVu = new JComboBox<String>();
		cmbChucVu.setBackground(Color.WHITE);
		cmbChucVu.setBounds(1064, 24, 100, 24);
		cmbChucVu.addItem(Contains.NHAN_VIEN_QUAN_LY);
		cmbChucVu.addItem(Contains.NHAN_VIEN_BAN_HANG);
		pnFormTimKiemNhanVien.add(cmbChucVu);

		JLabel lblCaLam = new JLabel("Ca làm:");
		lblCaLam.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCaLam.setBounds(310, 72, 70, 24);
		pnFormTimKiemNhanVien.add(lblCaLam);

		cmbCaLam = new JComboBox<String>();
		cmbCaLam.setBackground(Color.WHITE);
		cmbCaLam.setBounds(390, 75, 100, 24);
		pnFormTimKiemNhanVien.add(cmbCaLam);

		cmbKieuPhanCa = new JComboBox<String>();
		cmbKieuPhanCa.setBackground(Color.WHITE);
		cmbKieuPhanCa.addItem(Contains.KIEU_PHAN_CA_CO_DINH);
		cmbKieuPhanCa.addItem(Contains.KIEU_PHAN_CA_TUY_CHINH);
		cmbKieuPhanCa.setBounds(100, 75, 100, 24);
		pnFormTimKiemNhanVien.add(cmbKieuPhanCa);

		JLabel lblSinThoi = new JLabel("Số điện thoại:");
		lblSinThoi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSinThoi.setBounds(662, 23, 99, 24);
		pnFormTimKiemNhanVien.add(lblSinThoi);

		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(771, 25, 201, 24);
		pnFormTimKiemNhanVien.add(txtSDT);

		JLabel lblNgyBtu = new JLabel("Ngày bắt đầu:");
		lblNgyBtu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgyBtu.setBounds(542, 72, 99, 24);
		pnFormTimKiemNhanVien.add(lblNgyBtu);

		jdcNgayBatDau = new JDateChooser();
		jdcNgayBatDau.setBounds(662, 72, 100, 24);
		pnFormTimKiemNhanVien.add(jdcNgayBatDau);

		JLabel lblNgyKtThc = new JLabel("Ngày kết thúc:");
		lblNgyKtThc.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgyKtThc.setBounds(852, 75, 110, 24);
		pnFormTimKiemNhanVien.add(lblNgyKtThc);

		jdcNgayKetThuc = new JDateChooser();
		jdcNgayKetThuc.setBounds(972, 75, 100, 24);
		pnFormTimKiemNhanVien.add(jdcNgayKetThuc);

		JLabel lblMPhnCa = new JLabel("Mã phân ca:");
		lblMPhnCa.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMPhnCa.setBounds(10, 23, 99, 24);
		pnFormTimKiemNhanVien.add(lblMPhnCa);

		txtMaPhanCa = new JTextField();
		txtMaPhanCa.setColumns(10);
		txtMaPhanCa.setBounds(119, 23, 105, 24);
		pnFormTimKiemNhanVien.add(txtMaPhanCa);

		btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnTimKiem.setBounds(1194, 75, 100, 30);
		add(btnTimKiem);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnXoaTrang.setBounds(1194, 113, 100, 30);
		add(btnXoaTrang);

		String cols[] = { "Mã phân ca", "Tên nhân viên", "Ca làm", "Thời gian chấm công", "Trạng thái đi làm",
				"Có phép/không", "Ghi chú" };

		dfChamCong = new DefaultTableModel(cols, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		};
		tblChamCong = new JTable(dfChamCong) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		};
		scrChamCongNhanVien = new JScrollPane(tblChamCong, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrChamCongNhanVien.setBounds(10, 441, 1300, 354);
		add(scrChamCongNhanVien);

		btnChmCng = new JButton("Chấm Công");
		btnChmCng.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnChmCng.setBounds(1202, 399, 108, 32);
		add(btnChmCng);

		JPanel pnFormChamCong = new JPanel();
		pnFormChamCong.setBorder(
				new TitledBorder(null, "Form Chấm Công", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnFormChamCong.setBounds(1015, 217, 295, 172);
		add(pnFormChamCong);
		pnFormChamCong.setLayout(null);

		JLabel lblTinhTrangLamViec = new JLabel("Có đi làm:");
		lblTinhTrangLamViec.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTinhTrangLamViec.setBounds(10, 56, 53, 24);
		pnFormChamCong.add(lblTinhTrangLamViec);

		JLabel lblTinhTrangCoPhep = new JLabel("Có phép:");
		lblTinhTrangCoPhep.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTinhTrangCoPhep.setBounds(130, 56, 53, 24);
		pnFormChamCong.add(lblTinhTrangCoPhep);

		chckbxCoDiLam = new JCheckBox("");
		chckbxCoDiLam.setBounds(69, 58, 21, 21);
		pnFormChamCong.add(chckbxCoDiLam);

		chckbxCoPhep = new JCheckBox("");
		chckbxCoPhep.setBounds(189, 58, 21, 21);
		pnFormChamCong.add(chckbxCoPhep);

		JLabel lblGhiCh = new JLabel("Ghi chú:");
		lblGhiCh.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblGhiCh.setBounds(10, 90, 53, 24);
		pnFormChamCong.add(lblGhiCh);

		textArea = new JTextArea();
		textArea.setBounds(79, 90, 206, 72);
		pnFormChamCong.add(textArea);

		jdcThoiGianChamCong = new JDateChooser();
		jdcThoiGianChamCong.setBounds(130, 22, 100, 24);
		jdcThoiGianChamCong.setDate(new Date());
		pnFormChamCong.add(jdcThoiGianChamCong);

		JLabel lblThiGianChm = new JLabel("Thời gian chấm công:");
		lblThiGianChm.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblThiGianChm.setBounds(10, 22, 110, 24);
		pnFormChamCong.add(lblThiGianChm);

		String colsNhanVien[] = { "Mã phân ca", "Mã Nhân viên", "Họ tên", "Số điện thoại", "Chức vụ", "Ca làm",
				"Kiểu phân ca", "Ngày bắt đầu", "Ngày kết thúc" };

		dfNhanVien = new DefaultTableModel(colsNhanVien, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		};
		tblNhanVien = new JTable(dfNhanVien) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		};
		JScrollPane scrNhanVien = new JScrollPane(tblNhanVien, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrNhanVien.setBounds(10, 217, 995, 214);
		add(scrNhanVien);

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btnTimKiem.addActionListener(this);
		tblChamCong.addMouseListener(this);
		btnXoaTrang.addActionListener(this);
		btnChmCng.addActionListener(this);
		tblNhanVien.addMouseListener(this);

		btnCapNhatChamCong = new JButton("Cập Nhật");
		btnCapNhatChamCong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnCapNhatChamCong.setBounds(1015, 399, 108, 32);
		add(btnCapNhatChamCong);

		btnCapNhatChamCong.addActionListener(this);
		jdcThoiGianChamCong.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if (tblChamCong.getModel().getRowCount() > 0) {
					xoaDuLieuTrongBangChamCong(tblChamCong);
				}
				capNhatTBLBangChamCong();
			}
		});

		capNhatBangNhanVien();
		capNhatCMBCaLam();
	}

	public void capNhatCMBCaLam() {
		CaLam_DAO dsCaLam = new CaLam_DAO();
		List<CaLam> dsTimDuoc = dsCaLam.getAllCaLam();
		for (CaLam caLam : dsTimDuoc) {
			cmbCaLam.addItem(Format.formatDate(caLam.getGioBatDau()) + "-" + Format.formatDate(caLam.getGioKetThuc()));
		}
	}

	public void xoaDuLieuTrongBangChamCong(JTable tblBangCanCapNhat) {
		DefaultTableModel dfModel = (DefaultTableModel) tblBangCanCapNhat.getModel();
		dfModel.getDataVector().removeAllElements();
		dfModel.fireTableDataChanged();
	}

	public void capNhatTBLBangChamCong() {
		LocalDate ngayChon = Function.convertToLocalDateViaMilisecond(jdcThoiGianChamCong.getDate());
		BangChamCong_DAO ds = new BangChamCong_DAO();
		List<entity.BangChamCong> dsNhanDuoc = ds.getBangChamCongTheoThoiGianDaChon(ngayChon);
		if (!dsNhanDuoc.isEmpty()) {
			for (entity.BangChamCong bcc : dsNhanDuoc) {
				Object row[] = { bcc.getPhanCa().getMaBangPhanCa(), bcc.getPhanCa().getNhanVien().getTen(),
						Format.formatDate(bcc.getPhanCa().getCaLam().getGioBatDau()) + "-"
								+ Format.formatDate(bcc.getPhanCa().getCaLam().getGioKetThuc()),
						bcc.getThoiGianChamCong(), bcc.isTrangThaiLamViec() == true ? "Có mặt" : "Vắng",
						bcc.isTrangThaiCoPhep() == true ? "Có" : "Không", bcc.getGhiChu() };
				dfChamCong.addRow(row);
			}
			dfChamCong.fireTableDataChanged();
		}
	}

	public void capNhatBangNhanVien() {
		BangPhanCa_DAO dsPhanCa = new BangPhanCa_DAO();
		List<BangPhanCa> dsPhanCaTimDuoc = dsPhanCa.getAllPhanCaDeChamCong();
		for (BangPhanCa bpc : dsPhanCaTimDuoc) {
			Object row[] = { bpc.getMaBangPhanCa(), bpc.getNhanVien().getMaNhanVien(), bpc.getNhanVien().getTen(),
					bpc.getNhanVien().getSoDienThoai(),
					bpc.getNhanVien().getChucVu().equalsIgnoreCase("QL") ? "QL" : "BH",
					Format.formatDate(bpc.getCaLam().getGioBatDau()) + "-"
							+ Format.formatDate(bpc.getCaLam().getGioKetThuc()),
					bpc.getKieuPhanCa(), bpc.getNgayBatDau(), bpc.getNgayKetThuc() };
			dfNhanVien.addRow(row);
		}
		dfNhanVien.fireTableDataChanged();
	}

	public void chamCong() {
		int rowSelected = tblNhanVien.getSelectedRow();
		if (rowSelected != -1) {
			String maBangPhanCa = tblNhanVien.getValueAt(rowSelected, 0).toString();
			BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
			BangPhanCa bpc = dsBPC.getBangPhanCaTheoMa(maBangPhanCa);
			LocalDate ngayChamCong = Function.convertToLocalDateViaMilisecond(jdcThoiGianChamCong.getDate());
			if (!ngayChamCong.isEqual(LocalDate.now())) {
				JOptionPane.showMessageDialog(null, "Lỗi: ngày chấm công phải là ngày hôm nay!");
				return;
			}
			boolean trangThaiDiLam = chckbxCoDiLam.isSelected();
			boolean coXinPhep = chckbxCoPhep.isSelected();
			String ghiChu = textArea.getText().trim();
			entity.BangChamCong bcc = new entity.BangChamCong(bpc, ngayChamCong, trangThaiDiLam, coXinPhep, ghiChu);
			if (dsBangChamCong.themBangChamCong(bcc)) {
				Object row[] = { bcc.getPhanCa().getMaBangPhanCa(), bcc.getPhanCa().getNhanVien().getTen(),
						Format.formatDate(bcc.getPhanCa().getCaLam().getGioBatDau()) + "-"
								+ Format.formatDate(bcc.getPhanCa().getCaLam().getGioKetThuc()),
						bcc.getThoiGianChamCong(), bcc.isTrangThaiLamViec() == true ? "Có mặt" : "Vắng",
						bcc.isTrangThaiCoPhep() == true ? "Có" : "Không", bcc.getGhiChu() };
				dfChamCong.addRow(row);
				dfChamCong.fireTableDataChanged();
				JOptionPane.showMessageDialog(null, "Chấm công cho phân ca " + maBangPhanCa + " thành công!");
			} else {
				JOptionPane.showMessageDialog(null,
						"Lỗi: Chấm công cho phân ca " + maBangPhanCa + " không thành công!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn phân ca để chấm công!");
		}
	}

	public void capNhatChamCong() {
		int row = tblChamCong.getSelectedRow();
		if (row != -1) {
			String maBangPhanCa = tblChamCong.getValueAt(row, 0).toString();
			BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
			BangPhanCa bpc = dsBPC.getBangPhanCaTheoMa(maBangPhanCa);
			LocalDate ngayChamCong = Function.convertToLocalDateViaMilisecond(jdcThoiGianChamCong.getDate());
			boolean trangThaiDiLam = chckbxCoDiLam.isSelected();
			boolean coXinPhep = chckbxCoPhep.isSelected();
			String ghiChu = textArea.getText().trim();
			entity.BangChamCong bangChamCong = new entity.BangChamCong(bpc, ngayChamCong, trangThaiDiLam, coXinPhep,
					ghiChu);
			if (dsBangChamCong.capNhatBangChamCong(bangChamCong)) {
				tblChamCong.setValueAt(bangChamCong.isTrangThaiLamViec() == true ? "Có mặt" : "Vắng", row, 4);
				tblChamCong.setValueAt(bangChamCong.isTrangThaiCoPhep() == true ? "Có" : "Không", row, 5);
				tblChamCong.setValueAt(bangChamCong.getGhiChu(), row, 6);
				JOptionPane.showMessageDialog(null, "Cập nhật chấm công cho phân ca " + maBangPhanCa + " thành công");
			} else {
				JOptionPane.showMessageDialog(null,
						"Lỗi: Cập nhật chấm công cho phân ca " + maBangPhanCa + " không thành công!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu chấm công để chỉnh sửa!");
		}
	}

	public CaLam timCaLamTheoThoiGianLam(String gioBatDau, String gioKetThuc) {
		CaLam_DAO ds = new CaLam_DAO();
		return ds.getCaLamTheoThoiGian(gioBatDau, gioKetThuc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiem)) {
			String maPhanCa = txtMaPhanCa.getText().toString().trim();
			String maNV = txtMaNV.getText().toString().trim();
			String hoTen = txtHoTen.getText().toString().trim();
			String chucVu = cmbChucVu.getSelectedItem().toString();
			String sdt = txtSDT.getText().toString().trim();
			String kieuPhanCa = cmbKieuPhanCa.getSelectedItem().toString().trim();
			String thoiGianCaLam = cmbCaLam.getSelectedItem().toString();
			String gioBatDau = thoiGianCaLam.split("-")[0].toString().trim();
			String gioKetThuc = thoiGianCaLam.split("-")[1].toString().trim();
			CaLam cl = timCaLamTheoThoiGianLam(gioBatDau, gioKetThuc);
			LocalDate ngayBatDau = null;
			if (jdcNgayBatDau.getDate() != null) {
				ngayBatDau = Function.convertToLocalDateViaMilisecond(jdcNgayBatDau.getDate());
			}
			LocalDate ngayKetThuc = null;
			if (jdcNgayKetThuc.getDate() != null) {
				ngayKetThuc = Function.convertToLocalDateViaMilisecond(jdcNgayKetThuc.getDate());
			}
			BangPhanCa_DAO dsBPC = new BangPhanCa_DAO();
			List<BangPhanCa> dsTimDuoc = dsBPC.timKiemNhanVienTheoCacTieuChi(maPhanCa, maNV, hoTen, sdt, chucVu,
					kieuPhanCa, cl.getMaCaLam(), ngayBatDau, ngayKetThuc);
			if (!dsTimDuoc.isEmpty()) {
				xoaDuLieuTrongBangChamCong(tblNhanVien);
				for (BangPhanCa bpc : dsTimDuoc) {
					Object row[] = { bpc.getMaBangPhanCa(), bpc.getNhanVien().getMaNhanVien(),
							bpc.getNhanVien().getTen(), bpc.getNhanVien().getSoDienThoai(),
							bpc.getNhanVien().getChucVu().equalsIgnoreCase("QL") ? "QL" : "BH",
							Format.formatDate(bpc.getCaLam().getGioBatDau()) + "-"
									+ Format.formatDate(bpc.getCaLam().getGioKetThuc()),
							bpc.getKieuPhanCa(), bpc.getNgayBatDau(), bpc.getNgayKetThuc() };
					dfNhanVien.addRow(row);
				}
				dfNhanVien.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Không tìm thấy nhân viên!");
			}
		} else if (o.equals(btnXoaTrang)) {
			txtMaNV.setText("");
			txtHoTen.setText("");
			txtSDT.setText("");
			txtMaPhanCa.setText("");
			txtMaPhanCa.requestFocus();
			xoaDuLieuTrongBangChamCong(tblNhanVien);
			capNhatBangNhanVien();
		} else if (o.equals(btnChmCng)) {
			chamCong();
		} else if (o.equals(btnCapNhatChamCong)) {
			capNhatChamCong();
		}
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tblNhanVien)) {
			int row = tblNhanVien.getSelectedRow();
			if (row != -1) {
				String format = "yyyy-MM-dd";
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				txtMaPhanCa.setText(tblNhanVien.getValueAt(row, 0).toString());
				txtMaNV.setText(tblNhanVien.getValueAt(row, 1).toString());
				txtHoTen.setText(tblNhanVien.getValueAt(row, 2).toString());
				txtSDT.setText(tblNhanVien.getValueAt(row, 3).toString());
				cmbChucVu.setSelectedItem(tblNhanVien.getValueAt(row, 4).toString());
				cmbKieuPhanCa.setSelectedItem(tblNhanVien.getValueAt(row, 5).toString());
				cmbCaLam.setSelectedItem(tblNhanVien.getValueAt(row, 6).toString());
				try {
					Date ngayBatDau = dateFormat.parse(tblNhanVien.getValueAt(row, 7).toString().trim());
					jdcNgayBatDau.setDate(ngayBatDau);

					Date ngayKetThuc = dateFormat.parse(tblNhanVien.getValueAt(row, 8).toString().trim());
					jdcNgayKetThuc.setDate(ngayKetThuc);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} else if (o.equals(tblChamCong)) {
			int row = tblChamCong.getSelectedRow();
			if (row != -1) {
				chckbxCoDiLam.setSelected(
						tblChamCong.getValueAt(row, 4).toString().equalsIgnoreCase("Có mặt") ? true : false);
				chckbxCoPhep
						.setSelected(tblChamCong.getValueAt(row, 5).toString().equalsIgnoreCase("Có") ? true : false);
				textArea.setText(tblChamCong.getValueAt(row, 6).toString());
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
