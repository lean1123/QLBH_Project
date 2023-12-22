package gui_KhuyenMai;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChuongTrinhKhuyenMai_DAO;
import entity.ChuongTrinhKhuyenMai;
import utils.Contains;

public class CapNhatKhuyenMai extends JPanel implements ActionListener, MouseListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaKhuyenMai;
	private JTextField txtTenKhuyenMai;
	private JTextField txtGiaGiam;
	private JLabel lblTitleKhuyenMai, lblMKhuynMi, lblTnKhuynMi, lblGiGim, lblNgayBatDau, lblNgyKtThc, lblMT;
	private JPanel pnFormNhapThongTin;
	private JButton btnThem, btnSua, btnXoaTrang;
	private JTextArea txaMoTa;
	private JDateChooser ngayBatDau, ngayKetThuc;
	private JSpinner spnSoLuong;
	private DefaultTableModel dfKhuyenMai;
	private JTable tblKhuyenMai;
	private JLabel lblRegexMessege;
	private JTextField txtMaCaptcha;
	private ChuongTrinhKhuyenMai_DAO dsCTKhuyenMai;

	public CapNhatKhuyenMai() {
		dsCTKhuyenMai = new ChuongTrinhKhuyenMai_DAO();
		setSize(utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);

		pnFormNhapThongTin = new JPanel();
		pnFormNhapThongTin.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Khuyến Mãi", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnFormNhapThongTin.setBounds(10, 74, 1300, 160);
		add(pnFormNhapThongTin);
		pnFormNhapThongTin.setLayout(null);

		lblMKhuynMi = new JLabel("Mã Khuyến Mãi:");
		lblMKhuynMi.setBounds(10, 21, 105, 33);
		pnFormNhapThongTin.add(lblMKhuynMi);
		lblMKhuynMi.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		txtMaKhuyenMai = new JTextField();
		txtMaKhuyenMai.setBounds(125, 22, 88, 33);
		pnFormNhapThongTin.add(txtMaKhuyenMai);
		txtMaKhuyenMai.setEditable(false);
		txtMaKhuyenMai.setColumns(10);

		lblTnKhuynMi = new JLabel("Tên Khuyến Mãi:");
		lblTnKhuynMi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTnKhuynMi.setBounds(235, 20, 105, 33);
		pnFormNhapThongTin.add(lblTnKhuynMi);

		txtTenKhuyenMai = new JTextField();
		txtTenKhuyenMai.setEditable(true);
		txtTenKhuyenMai.setColumns(10);
		txtTenKhuyenMai.setBounds(350, 21, 122, 33);
		pnFormNhapThongTin.add(txtTenKhuyenMai);

		lblGiGim = new JLabel("Giá Giảm:");
		lblGiGim.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblGiGim.setBounds(724, 18, 64, 33);
		pnFormNhapThongTin.add(lblGiGim);

		txtGiaGiam = new JTextField();
		txtGiaGiam.setEditable(true);
		txtGiaGiam.setColumns(10);
		txtGiaGiam.setBounds(798, 20, 146, 33);
		pnFormNhapThongTin.add(txtGiaGiam);

		lblNgayBatDau = new JLabel("Ngày bắt đầu:");
		lblNgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgayBatDau.setBounds(966, 25, 105, 13);
		pnFormNhapThongTin.add(lblNgayBatDau);

		ngayBatDau = new JDateChooser();
		ngayBatDau.setBounds(1104, 21, 116, 30);
		pnFormNhapThongTin.add(ngayBatDau);

		lblNgyKtThc = new JLabel("Ngày kết thúc:");
		lblNgyKtThc.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNgyKtThc.setBounds(966, 79, 105, 13);
		pnFormNhapThongTin.add(lblNgyKtThc);

		ngayKetThuc = new JDateChooser();
		ngayKetThuc.setBounds(1104, 75, 116, 30);
		pnFormNhapThongTin.add(ngayKetThuc);

		lblMT = new JLabel("Mô Tả:");
		lblMT.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMT.setBounds(482, 69, 64, 33);
		pnFormNhapThongTin.add(lblMT);

		txaMoTa = new JTextArea();
		txaMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txaMoTa.setForeground(new Color(0, 0, 0));
		txaMoTa.setBounds(573, 73, 371, 51);
		pnFormNhapThongTin.add(txaMoTa);

		JLabel lblSLng = new JLabel("Số Lượng:");
		lblSLng.setBounds(10, 64, 68, 33);
		pnFormNhapThongTin.add(lblSLng);
		lblSLng.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		spnSoLuong = new JSpinner();
		spnSoLuong.setBounds(97, 65, 122, 33);
		pnFormNhapThongTin.add(spnSoLuong);

		lblRegexMessege = new JLabel("Nhập đầy đủ thông tin khuyến mãi");
		lblRegexMessege.setBounds(966, 137, 324, 13);
		pnFormNhapThongTin.add(lblRegexMessege);

		JLabel lblMCaptcha = new JLabel("Mã Captcha:");
		lblMCaptcha.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMCaptcha.setBounds(482, 21, 77, 33);
		pnFormNhapThongTin.add(lblMCaptcha);

		txtMaCaptcha = new JTextField();
		txtMaCaptcha.setEditable(true);
		txtMaCaptcha.setColumns(10);
		txtMaCaptcha.setBounds(573, 22, 122, 33);
		pnFormNhapThongTin.add(txtMaCaptcha);
		String colKhuyenMai[] = { "Mã khuyến mãi", "Tên khuyến mãi", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc",
				"Số Lượng", "Giá Giảm", "Mã Captcha" };

		dfKhuyenMai = new DefaultTableModel(colKhuyenMai, 0);

		tblKhuyenMai = new JTable(dfKhuyenMai);

		JScrollPane scKhuyenMai = new JScrollPane(tblKhuyenMai, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scKhuyenMai.setBounds(10, 330, 1300, 454);
		add(scKhuyenMai);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(483, 265, 114, 31);
		add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.setBounds(629, 265, 114, 31);
		add(btnSua);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(785, 265, 114, 31);
		add(btnXoaTrang);

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		tblKhuyenMai.addMouseListener(this);

		lblTitleKhuyenMai = new JLabel("CẬP NHẬT CHƯƠNG TRÌNH KHUYẾN MÃI");
		lblTitleKhuyenMai.setForeground(Color.BLUE);
		lblTitleKhuyenMai.setBounds(452, 10, 418, 54);
		add(lblTitleKhuyenMai);
		lblTitleKhuyenMai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleKhuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 20));
//		Kết nối cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
//		Cập nhật dữ liệu cho bảng Chuong trình khuyến mãi
		capNhatDuLieuChoBang();

	}

	/**
	 * Kiểm tra tính hợp lệ của dữ liệu nhập cho một khuyến mãi.
	 *
	 * @return True nếu dữ liệu nhập hợp lệ, ngược lại trả về false.
	 */

	public boolean kiemTraDuLieuNhap() {
		boolean checkedInput = true;
		String tenKhuyenMai = txtTenKhuyenMai.getText().toString().trim();
		String moTa = txaMoTa.getText().toString().trim();
		Date ngayBD = ngayBatDau.getDate();
		Date ngayKT = ngayKetThuc.getDate();
		int soLuong = (int) spnSoLuong.getValue();
		String giaGiam = txtGiaGiam.getText().toString().trim();
		double giaGiamDouble = 0;
		if (giaGiam.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "Giá giảm không được để rỗng!");
			return false;
		} else {
//			Gán giá trị đã nhập cho textfield giá giảm
			giaGiamDouble = Double.parseDouble(giaGiam);
		}
		String maCaptcha = txtMaCaptcha.getText().toString().trim();
		if (tenKhuyenMai.equalsIgnoreCase("") || !(tenKhuyenMai.matches(Contains.HO_TEN_REGEX))) {
			JOptionPane.showMessageDialog(null, "Tên chương trình khuyến mãi phải viết hoa chữ cái đầu mỗi từ!");
			return false;
		} else if (maCaptcha.equalsIgnoreCase("") || !(maCaptcha.matches("[A-Z]+"))) {
			JOptionPane.showMessageDialog(null, "Mã captcha phải là chuỗi ký tự in hoa!");
			return false;
		} else if (giaGiamDouble == 0 || !(giaGiam.matches("\\d+(\\.\\d)?"))) {
			JOptionPane.showMessageDialog(null, "Giá giảm phải lớn hơn 0 và là số");
			return false;
		} else if (ngayBD.compareTo(new Date()) == -1) {
			JOptionPane.showMessageDialog(null, "Ngày bắt đầu khuyến mãi phải sau hoặc bắt đầu từ ngày hôm nay!");
			return false;
		} else if (ngayKT.compareTo(ngayBD) == -1) {
			JOptionPane.showMessageDialog(null, "Ngày kết thúc khuyến mãi phải sau ngày bắt đầu!");
			return false;
		} else if (moTa.length() == 0) {
			JOptionPane.showMessageDialog(null, "Mô tả không được đặt rỗng!");
			return false;
		} else if (soLuong == 0 || !(String.valueOf(soLuong).matches("[0-9]+"))) {
			JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 và là số");
			return false;
		}
		return checkedInput;
	}

	/**
	 * Lấy thông tin chương trình khuyến mãi từ dữ liệu nhập liệu.
	 *
	 * @return Đối tượng ChuongTrinhKhuyenMai chứa thông tin nhập liệu.
	 */

	public ChuongTrinhKhuyenMai getChuongTrinhKhuyenMaiTuNhapLieu() {
		String maKhuyenMai = txtMaKhuyenMai.getText();
		String tenKhuyenMai = txtTenKhuyenMai.getText();
		String moTa = txaMoTa.getText();
		Date ngayBD = ngayBatDau.getDate();
		Date ngayKT = ngayKetThuc.getDate();
		int soLuong = (int) spnSoLuong.getValue();
		double giaGiam = Double.parseDouble(txtGiaGiam.getText());
		String maCap = txtMaCaptcha.getText();
		return new ChuongTrinhKhuyenMai(maKhuyenMai, tenKhuyenMai, moTa, ngayBD, ngayKT, soLuong, giaGiam, maCap);
	}

	/**
	 * Xóa toàn bộ dữ liệu của bảng khuyến mãi.
	 */

	public void xoaHetDuLieuTrenTableModel() {
		dfKhuyenMai.getDataVector().removeAllElements();
	}

	/**
	 * Cập nhật dữ liệu cho bảng hiển thị chương trình khuyến mãi.
	 */

	public void capNhatDuLieuChoBang() {
		ChuongTrinhKhuyenMai_DAO ds = new ChuongTrinhKhuyenMai_DAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (ChuongTrinhKhuyenMai ctkm : ds.getListChuongTrinhKhuyenMai()) {
			Object[] row = { ctkm.getMaKhuyenMai(), ctkm.getTenKhuyenMai(), ctkm.getMoTa(),
					sdf.format(ctkm.getNgayBatDau()),
					sdf.format(ctkm.getNgayKetThuc()), ctkm.getSoLuong(),
					ctkm.getGiaGiam(), ctkm.getMaCaptcha() };
			dfKhuyenMai.addRow(row);
		}
		tblKhuyenMai.setModel(dfKhuyenMai);
	}

	/**
	 * Xóa trắng các trường nhập liệu trong giao diện quản lý chương trình khuyến
	 * mãi.
	 */

	public void xoaTrang() {
		txtMaKhuyenMai.setText("");
		txtTenKhuyenMai.setText("");
		txaMoTa.setText("");
		spnSoLuong.setValue(0);
		txtGiaGiam.setText("");
		txtMaCaptcha.setText("");
		ngayBatDau.setDate(new Date());
		ngayKetThuc.setDate(new Date());
		lblRegexMessege.setText("Nhập đầy đủ thông tin khuyến mãi");
		lblRegexMessege.setForeground(Color.BLACK);
		txtTenKhuyenMai.requestFocus();
	}

	/**
	 * Thêm chương trình khuyến mãi mới vào danh sách và cập nhật bảng hiển thị.
	 */

	public void themChuongTrinhKhuyenMai() {
		if (kiemTraDuLieuNhap()) {
			String tenKhuyenMai = txtTenKhuyenMai.getText();
			String moTa = txaMoTa.getText();
			Date ngayBD = ngayBatDau.getDate();
			Date ngayKT = ngayKetThuc.getDate();
			int soLuong = (int) spnSoLuong.getValue();
			double giaGiam = Double.parseDouble(txtGiaGiam.getText());
			String maCap = txtMaCaptcha.getText();
			ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(tenKhuyenMai, moTa, ngayBD, ngayKT, soLuong, giaGiam,
					maCap);
			if (dsCTKhuyenMai.themChuongTrinhKhuyenMai(ctkm)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ChuongTrinhKhuyenMai ctkmVuaThem = dsCTKhuyenMai.getChuongTrinhKhuyenMaiVuaCapNhat();
				Object[] row = { ctkmVuaThem.getMaKhuyenMai(), ctkmVuaThem.getTenKhuyenMai(), ctkmVuaThem.getMoTa(),
						sdf.format(ctkmVuaThem.getNgayBatDau()), sdf.format(ctkmVuaThem.getNgayKetThuc()), ctkmVuaThem.getSoLuong(),
						ctkmVuaThem.getGiaGiam(), ctkmVuaThem.getMaCaptcha() };
				dfKhuyenMai.addRow(row);
				JOptionPane.showMessageDialog(null, "Thêm thành công!");
				xoaTrang();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Thêm không thành công!");
				return;
			}
		} else {
			return;
		}

	}

	/**
	 * Cập nhật thông tin chương trình khuyến mãi được chọn trong bảng hiển thị.
	 */

	public void capNhatChuongTrinhKhuyenMai() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int row = tblKhuyenMai.getSelectedRow();
		if (row != -1) {
			ChuongTrinhKhuyenMai ctkm = getChuongTrinhKhuyenMaiTuNhapLieu();
			if (dsCTKhuyenMai.capNhatChuongTrinhKhuyenMai(ctkm)) {
				tblKhuyenMai.setValueAt(txtTenKhuyenMai.getText(), row, 1);
				tblKhuyenMai.setValueAt(txaMoTa.getText(), row, 2);
				tblKhuyenMai.setValueAt(sdf.format(ngayBatDau.getDate()), row, 3);
				tblKhuyenMai.setValueAt(sdf.format(ngayKetThuc.getDate()), row, 4);
				tblKhuyenMai.setValueAt(spnSoLuong.getValue(), row, 5);
				tblKhuyenMai.setValueAt(txtGiaGiam.getText(), row, 6);
				tblKhuyenMai.setValueAt(txtMaCaptcha.getText(), row, 7);
				JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
				xoaTrang();
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: Cập nhật không thành công!");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Chọn chương trình khuyến mãi cần cập nhật!");
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (btnThem.getText().equalsIgnoreCase("Thêm")) {
				if (btnThem.getText().toString().equals("Hủy")) {
					btnThem.setText("Thêm");
					btnSua.setText("Sửa");
					return;
				}
				btnThem.setText("Hủy");
				btnSua.setText("Lưu");
			}
		} else if (o.equals(btnSua)) {
			if (btnSua.getText().toString().equals("Lưu")) {
				themChuongTrinhKhuyenMai();
				btnThem.setText("Thêm");
				btnSua.setText("Sửa");
			} else {
				capNhatChuongTrinhKhuyenMai();
			}
		} else if (o.equals(btnXoaTrang)) {
			xoaTrang();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// TODO Auto-generated method stub
		int row = tblKhuyenMai.getSelectedRow();
		txtMaKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 0).toString());
		txtTenKhuyenMai.setText(tblKhuyenMai.getValueAt(row, 1).toString());
		txaMoTa.setText(tblKhuyenMai.getValueAt(row, 2).toString());

		try {
			ngayBatDau.setDate(sdf.parse(tblKhuyenMai.getValueAt(row, 3).toString()));
			ngayKetThuc.setDate(sdf.parse(tblKhuyenMai.getValueAt(row, 4).toString()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		spnSoLuong.setValue(tblKhuyenMai.getValueAt(row, 5));
		txtGiaGiam.setText(tblKhuyenMai.getValueAt(row, 6).toString());
		txtMaCaptcha.setText(tblKhuyenMai.getValueAt(row, 7).toString());
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
