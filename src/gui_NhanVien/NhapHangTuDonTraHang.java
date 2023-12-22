package gui_NhanVien;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietDonTraHang_DAO;
import dao.ChiTietHoaDon_Dao;
import dao.DonTraHang_DAO;
import dao.HoaDon_DAO;
import dao.SanPham_DAO;
import entity.ChiTietDonTraHang;
import entity.DonTraHang;
import entity.HoaDon;
import entity.SanPham;
import utils.Format;
import utils.Function;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class NhapHangTuDonTraHang extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtSDTKH;
	private JTextField txtMaNhanVien;
	private JTextField txtMaHoaDon;
	private JTextField txtMaDonTraHang;
	private JTextField txtSoLuongNhap;
	private JTable tblDonTraHang, tblChiTietDonTra;
	private DefaultTableModel dfDonTraHang, dfChiTietDonTra;
	private JScrollPane scrDonTraHang, scrChiTietDonTraHang;
	private JTextArea txaLyDoTraHang;
	private JDateChooser jdcNgayTra;
	private JButton btnNhapKho, btnXoaTrang, btnTimKiem;
	private DonTraHang_DAO dsDTH;
	private ChiTietDonTraHang_DAO listChiTietDonTra;
	private HoaDon_DAO dsHD;
	private SanPham_DAO dsSP;

	public NhapHangTuDonTraHang() {
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dsDTH = new DonTraHang_DAO();
		listChiTietDonTra = new ChiTietDonTraHang_DAO();
		dsHD = new HoaDon_DAO();
		dsSP = new SanPham_DAO();

		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		setLayout(null);

		JPanel pnThanhToan = new JPanel();
		pnThanhToan.setLayout(null);
		pnThanhToan.setBorder(new TitledBorder(null, "Form Nhập Thông Tin Đơn Trả Hàng", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnThanhToan.setBackground(SystemColor.menu);
		pnThanhToan.setBounds(10, 75, 1298, 159);
		add(pnThanhToan);

		JLabel lblTongTien = new JLabel("Số điện thoại khách hàng:");
		lblTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTongTien.setBounds(450, 63, 218, 34);
		pnThanhToan.add(lblTongTien);

		btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setBounds(958, 114, 134, 34);
		pnThanhToan.add(btnTimKiem);

		txtSDTKH = new JTextField();
		txtSDTKH.setColumns(10);
		txtSDTKH.setBackground(Color.WHITE);
		txtSDTKH.setBounds(678, 68, 183, 29);
		pnThanhToan.add(txtSDTKH);

		JLabel lblTngTinMi = new JLabel("Mã nhân viên:");
		lblTngTinMi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTngTinMi.setBounds(985, 63, 118, 34);
		pnThanhToan.add(lblTngTinMi);

		txtMaNhanVien = new JTextField();
		txtMaNhanVien.setColumns(10);
		txtMaNhanVien.setBackground(Color.WHITE);
		txtMaNhanVien.setBounds(1123, 68, 120, 29);
		pnThanhToan.add(txtMaNhanVien);

		JLabel lblLDo = new JLabel("Lý do:");
		lblLDo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLDo.setBounds(56, 63, 62, 34);
		pnThanhToan.add(lblLDo);

		txaLyDoTraHang = new JTextArea();
		txaLyDoTraHang.setWrapStyleWord(true);
		txaLyDoTraHang.setLineWrap(true);
		txaLyDoTraHang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txaLyDoTraHang.setBackground(Color.WHITE);
		txaLyDoTraHang.setBounds(128, 70, 274, 61);
		pnThanhToan.add(txaLyDoTraHang);

		JLabel lblNgyt = new JLabel("Ngày trả:");
		lblNgyt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNgyt.setBounds(985, 10, 88, 34);
		pnThanhToan.add(lblNgyt);

		jdcNgayTra = new JDateChooser();
		jdcNgayTra.setBounds(1083, 10, 163, 29);
		pnThanhToan.add(jdcNgayTra);

		JLabel lblMaDonHang_1 = new JLabel("Mã hóa đơn:");
		lblMaDonHang_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaDonHang_1.setBounds(550, 10, 102, 34);
		pnThanhToan.add(lblMaDonHang_1);

		txtMaHoaDon = new JTextField();
		txtMaHoaDon.setColumns(10);
		txtMaHoaDon.setBounds(678, 15, 154, 29);
		pnThanhToan.add(txtMaHoaDon);

		JLabel lblMaDonHang_1_1 = new JLabel("Mã đơn trả hàng:");
		lblMaDonHang_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaDonHang_1_1.setBounds(56, 10, 158, 34);
		pnThanhToan.add(lblMaDonHang_1_1);

		txtMaDonTraHang = new JTextField();
		txtMaDonTraHang.setColumns(10);
		txtMaDonTraHang.setBounds(224, 10, 154, 29);
		pnThanhToan.add(txtMaDonTraHang);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(1109, 114, 134, 34);
		pnThanhToan.add(btnXoaTrang);

		JLabel lblTieuDe1 = new JLabel("Danh Sách Đơn Trả Hàng");
		lblTieuDe1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe1.setBounds(480, 244, 295, 41);
		add(lblTieuDe1);

		String cols[] = { "Mã đơn trả hàng", "Mã hóa đơn", "Khách Hàng", "Số điện thoại khách hàng", "Mã nhân viên",
				"Lý do trả hàng", "Ngày trả hàng" };

		dfDonTraHang = new DefaultTableModel(cols, 0);
		tblDonTraHang = new JTable(dfDonTraHang);

		scrDonTraHang = new JScrollPane(tblDonTraHang, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrDonTraHang.setBounds(10, 295, 1298, 222);
		add(scrDonTraHang);

		JLabel lblDanhSachn = new JLabel("Chi Tiết Đơn Trả");
		lblDanhSachn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachn.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachn.setBounds(495, 520, 295, 41);
		add(lblDanhSachn);

		String colsChiTiet[] = { "Mã sản phẩm", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Size", "Giá bán",
				"Số Lượng trả", "Số lượng nhập kho", "Tình trạng nhập" };

		dfChiTietDonTra = new DefaultTableModel(colsChiTiet, 0);
		tblChiTietDonTra = new JTable(dfChiTietDonTra);

		scrChiTietDonTraHang = new JScrollPane(tblChiTietDonTra, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrChiTietDonTraHang.setBounds(10, 571, 1298, 224);
		add(scrChiTietDonTraHang);

		btnNhapKho = new JButton("Nhập Kho");
		btnNhapKho.setBounds(1123, 527, 134, 34);
		add(btnNhapKho);

		JLabel lblSLngNhp = new JLabel("Số lượng nhập:");
		lblSLngNhp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSLngNhp.setBounds(849, 525, 134, 34);
		add(lblSLngNhp);

		txtSoLuongNhap = new JTextField();
		txtSoLuongNhap.setColumns(10);
		txtSoLuongNhap.setBackground(Color.WHITE);
		txtSoLuongNhap.setBounds(993, 530, 120, 29);
		add(txtSoLuongNhap);

		JLabel lblNhpHng = new JLabel("Nhập Hàng");
		lblNhpHng.setForeground(Color.BLUE);
		lblNhpHng.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhpHng.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNhpHng.setBounds(480, 10, 295, 41);
		add(lblNhpHng);

		tblDonTraHang.addMouseListener(this);
		btnNhapKho.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		capNhatBangDonTraHang();
	}

	public void capNhatBangDonTraHang() {
		List<entity.DonTraHang> ds = dsDTH.getDanhSachDonTraHang();
		for (entity.DonTraHang donTraHang : ds) {
			Object row[] = { donTraHang.getMaDonTraHang(), donTraHang.getHoaDon().getMaHoaDon(),
					donTraHang.getHoaDon().getKhachHang().getHoTen(),
					donTraHang.getHoaDon().getKhachHang().getSoDienThoai(),
					donTraHang.getNhanVienThucHien().getMaNhanVien(), donTraHang.getLyDo(),
					donTraHang.getNgayTraHang() };
			dfDonTraHang.addRow(row);
		}
		dfDonTraHang.fireTableDataChanged();
	}

	public void capNhatChiTietDonTraHang(String maDonTraHang) {
		List<ChiTietDonTraHang> ds = listChiTietDonTra.danhSachDonTraHang(maDonTraHang);
		for (ChiTietDonTraHang ct : ds) {
			Object row[] = { ct.getSanPham().getMaSanPham(), ct.getSanPham().getTenSanPham(),
					ct.getSanPham().getChatLieu(), ct.getSanPham().getMauSac(), ct.getSanPham().getKichCo(),
					Format.formatAmout(ct.getSanPham().getGiaBan()), ct.getSoLuong(), ct.getSoLuongNhapKho(),
					ct.isTinhTrangNhap() == true ? "Đã nhập đủ" : "Chưa nhập đủ" };
			dfChiTietDonTra.addRow(row);
		}
		dfChiTietDonTra.fireTableDataChanged();
	}

	public void timKiemDonTraHang() {
		String maDonTra = txtMaDonTraHang.getText().toString().trim();
		String maHoaDon = txtMaHoaDon.getText().toString().trim();
		String soDienThoaiKH = txtSDTKH.getText().toString().trim();
		String maNhanVien = txtMaNhanVien.getText().trim();
		String lyDoTraHang = txaLyDoTraHang.getText().trim();
		
		LocalDate ngayTra = null;  // Mặc định là null
		if (jdcNgayTra.getDate() != null) {
		    ngayTra = Function.convertToLocalDateViaMilisecond(jdcNgayTra.getDate());
		}
		
		List<DonTraHang> dsDonTra = dsDTH.timKiemDonTraHang(maDonTra, maHoaDon, soDienThoaiKH, maNhanVien, lyDoTraHang,
				ngayTra);
		for (DonTraHang donTraHang : dsDonTra) {
			Object row[] = { donTraHang.getMaDonTraHang(), donTraHang.getHoaDon().getMaHoaDon(),
					donTraHang.getHoaDon().getKhachHang().getHoTen(),
					donTraHang.getHoaDon().getKhachHang().getSoDienThoai(),
					donTraHang.getNhanVienThucHien().getMaNhanVien(), donTraHang.getLyDo(),
					donTraHang.getNgayTraHang() };
			dfDonTraHang.addRow(row);
		}
		dfDonTraHang.fireTableDataChanged();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnNhapKho)) {
			int row = tblChiTietDonTra.getSelectedRow();
			if (row != -1) {
				String maSP = tblChiTietDonTra.getValueAt(row, 0).toString();
				int soLuongNhap = Integer.parseInt(txtSoLuongNhap.getText().trim());
				int soLuongTra = Integer.parseInt(tblChiTietDonTra.getValueAt(row, 6).toString());
				int soLuongTraDaNhap = Integer.parseInt(tblChiTietDonTra.getValueAt(row, 7).toString());
				int tongSoLuongNhapKho = soLuongNhap + soLuongTraDaNhap;
				if (tongSoLuongNhapKho <= soLuongTra) {
					if (dsSP.capNhatSoLuongTonCuaSanPham(tongSoLuongNhapKho, maSP)) {
						String maDonTraHang = txtMaDonTraHang.getText().toString();
						entity.DonTraHang donTra = dsDTH.getDonTraHangTheoMa(maDonTraHang);
						SanPham sp = dsSP.getSanPhamTheoMa(maSP);
						boolean tinhTrangNhap = tongSoLuongNhapKho == soLuongTra ? true : false;
						ChiTietDonTraHang ctdt = new ChiTietDonTraHang(donTra, sp, soLuongTra, tongSoLuongNhapKho,
								tinhTrangNhap);
						if (listChiTietDonTra.CapNhatChiTietDonTraHang(ctdt)) {
							tblChiTietDonTra.setValueAt(ctdt.getSoLuongNhapKho(), row, 7);
							tblChiTietDonTra.setValueAt(ctdt.isTinhTrangNhap() == true ? "Đã nhập đủ" : "Chưa nhập đủ",
									row, 8);
							JOptionPane.showMessageDialog(null, "Nhập kho thành công");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Lỗi: số lượng nhập kho phải nhỏ hơn hoặc bằng số lượng khách đã trả!");
					return;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Lỗi: vui lòng chọn sản phẩm để nhập vào kho!");
				return;
			}

		}else if(o.equals(btnTimKiem)) {
			if(dfDonTraHang.getRowCount() > 0) {
				dfDonTraHang.getDataVector().removeAllElements();
				dfDonTraHang.fireTableDataChanged();
				dfChiTietDonTra.setRowCount(0);
			}
			timKiemDonTraHang();
		}else if(o.equals(btnXoaTrang)) {
			txtMaDonTraHang.setText("");
			txtMaHoaDon.setText("");
			txtSDTKH.setText("");
			txtMaNhanVien.setText("");
			txaLyDoTraHang.setText("");
			jdcNgayTra.setDate(new Date());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tblDonTraHang)) {
			int row = tblDonTraHang.getSelectedRow();
			if (row != -1) {
				String maDonTraHang = tblDonTraHang.getValueAt(row, 0).toString();
				if (dfChiTietDonTra.getRowCount() > 0) {
					dfChiTietDonTra.getDataVector().removeAllElements();
					dfChiTietDonTra.fireTableDataChanged();
				}
				capNhatChiTietDonTraHang(maDonTraHang);
				txtMaDonTraHang.setText(maDonTraHang);
				txtMaHoaDon.setText(tblDonTraHang.getValueAt(row, 1).toString());
				txtSDTKH.setText(tblDonTraHang.getValueAt(row, 3).toString());
				txtMaNhanVien.setText(tblDonTraHang.getValueAt(row, 4).toString());
				txaLyDoTraHang.setText(tblDonTraHang.getValueAt(row, 5).toString());

				SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date ngayTra = dfm.parse(tblDonTraHang.getValueAt(row, 6).toString());
					jdcNgayTra.setDate(ngayTra);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
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
