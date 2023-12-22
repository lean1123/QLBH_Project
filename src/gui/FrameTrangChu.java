package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

public class FrameTrangChu extends JFrame implements ActionListener{
	//icon
	private final ImageIcon ICON_DOWN = new ImageIcon(getClass().getResource("/Images/Icons/down-arrow.png"));
	private final ImageIcon ICON_UPPER = new ImageIcon(getClass().getResource("/Images/Icons/upper-arrow.png"));
	private final ImageIcon ICON_BACKGROUND = new ImageIcon(getClass().getResource("/Images/Icons/back3.jpg"));
	//menu cha
	private CustomButton btnThongKe;
	private CustomButton btnCaLam;
	private CustomButton btnHoaDon;
	private CustomButton btnKhachHang;
	private CustomButton btnNhanVien;
	private CustomButton btnNhaCungCap;
	private CustomButton btnSanPham;
	private CustomButton btnTaiKhoan;
	private CustomButton btnKhuyenMai;

	//item
	//btnThongKe
	private CustomButton btnThongKeDoanhThu;
	private CustomButton btnThongKeChiTieuKhachHang;
	//btnCaLam
	private CustomButton btnCapNhatCaLam;
	//btnHoaDon
	private CustomButton btnTimKiemHoaDon;
	//btnKhachHang
	private CustomButton btnLapHoaDon;
	private CustomButton btnCapNhatKhachHang;
	private CustomButton btnTimKiemKhachHang;

	//btnNhanVien
	private CustomButton btnDatHang;
	private CustomButton btnCapNhatNhanVien;
	private CustomButton btnTimKiemNhanVien;
	private CustomButton btnTheoDoiLichLam;
	private CustomButton btnPhanCongCaLam;
	private CustomButton btnChamCong;
	private CustomButton btnDonTraHang;
	private CustomButton btnNhapHang;
	//btnNhaCungCap
	private CustomButton btnCapNhatNCC;
	private CustomButton btnTimKiemNCC;
	//btnSanPham
	private CustomButton btnCapNhatSanPham;
	private CustomButton btnTimKiemSanPham;
	private CustomButton btnThongKeSanPham;

	//btnTaiKhoan
	private CustomButton btnCapNhatTaiKhoan;
	//btn logout
	private JButton btnLogout;

	private JPanel pnlContent;

	private JButton btnChatLieu;
	private JButton btnMauSac;
	private JButton btnDanhMuc;
	private JButton btnKichCo;

	private CustomButton btnCapNhatChuongTrinhKhuyenMai;

	public FrameTrangChu() {
		 // Lấy thông tin về màn hình
		anhXa();
		setTitle("Trang Chủ");
		setExtendedState(Frame.MAXIMIZED_BOTH);
		createGUI();

	}
	/*
	 * function createGUI dùng để tạo giao diện người dùng
	 */
	private void createGUI() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		//lbl
		JLabel lblMenu = new JLabel("Menu");
		JLabel lblQuanLyBanQuanAo = new JLabel("Quản Lý Bán Quần Áo");
		JLabel lblTenLogin = new JLabel(utils.Contains.getRole().endsWith("KH")?"Khách Hàng":"Họ và Tên: "+utils.Contains.getTenNV());
		JLabel lblChucVu = new JLabel(utils.Contains.getRole().equals("KH")?"Chức Vụ  : Khách Hàng":utils.Contains.getRole().equals("NV")?"Chức Vụ  : Nhân Viên Bán Hàng":"Chức Vụ  : Nhân Viên Quản Lý");

		//setLaybel
		lblMenu.setFont(new Font("",Font.BOLD,20));
		lblMenu.setForeground(Color.white);
		lblMenu.setBackground(Color.decode("#FF008A"));
		lblMenu.setBounds(81, 11, 56, 24);

		lblQuanLyBanQuanAo.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lblQuanLyBanQuanAo.setForeground(Color.white);
		lblQuanLyBanQuanAo.setBounds(35, 42, 164, 20);

		lblTenLogin.setFont(new Font("",Font.PLAIN,12));
		lblChucVu.setFont(new Font("",Font.PLAIN,12));

		lblTenLogin.setBounds(10, 5,217,20);
		lblChucVu.setBounds(10, 25,217,30);

		//pnl
		JPanel pnlTilteMenu = new JPanel();
		JPanel pnlWest = new JPanel();
		JPanel pnlMenu = new JPanel();
		JPanel pnlLogout = new JPanel();


		//set pnl
		pnlWest.setLayout(null);
		pnlWest.setPreferredSize(new Dimension(217,705));
		pnlWest.setBackground(Color.black);
		pnlWest.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));

		pnlMenu.setLayout(new BoxLayout(pnlMenu, BoxLayout.Y_AXIS));
		pnlMenu.setBounds(0,0,216,705);

		pnlLogout.setBounds(0,705,216,100);
		pnlLogout.setLayout(null);

		pnlContent.setLayout(null);
		pnlContent.setBackground(Color.green);
		pnlContent.setPreferredSize(new Dimension(utils.Contains.WIDTH_PANEL_CONTENT,utils.Contains.HEIGHT_PANEL_CONTENT));

		Image scaled = utils.Function.scaleImage(ICON_BACKGROUND.getImage(), utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		ImageIcon icon = new ImageIcon(scaled);
		JLabel lblImage = new JLabel(icon);
		lblImage.setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		pnlContent.add(lblImage);

		pnlTilteMenu.setLayout(null);
		pnlTilteMenu.setOpaque(true);
		pnlTilteMenu.setBackground(Color.decode("#FF008A"));
		pnlTilteMenu.setBorder(BorderFactory.createMatteBorder(0, 0,1,0,Color.black));
		pnlTilteMenu.setMaximumSize(new Dimension(434,84));

		//set btn
		btnLogout.setBounds(110,55,92,26);
		btnLogout.setBackground(Color.decode("#FF008A"));
		btnLogout.setFont(new Font("", Font.BOLD,12));
		btnLogout.setForeground(Color.white);
		btnLogout.setBorder(null);

		//add pnl
		pnlTilteMenu.add(lblMenu);
		pnlTilteMenu.add(lblQuanLyBanQuanAo);

		pnlWest.add(pnlMenu);
		pnlWest.add(pnlLogout);

		pnlLogout.add(lblTenLogin);
		pnlLogout.add(lblChucVu);
		pnlLogout.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.decode("#616161")));
		pnlLogout.add(btnLogout);

		add(pnlWest,BorderLayout.WEST);
		add(pnlContent,BorderLayout.CENTER);





		if(utils.Contains.getRole().equals(utils.Contains.NHAN_VIEN_QUAN_LY)) {
			pnlMenu.add(pnlTilteMenu);

			pnlMenu.add(btnNhanVien);
			pnlMenu.add(btnDatHang);
			pnlMenu.add(btnCapNhatNhanVien);
			pnlMenu.add(btnTimKiemNhanVien);
			pnlMenu.add(btnTheoDoiLichLam);
			pnlMenu.add(btnPhanCongCaLam);
			pnlMenu.add(btnChamCong);
			pnlMenu.add(btnNhapHang);

			pnlMenu.add(btnKhachHang);
			pnlMenu.add(btnLapHoaDon);
			pnlMenu.add(btnCapNhatKhachHang);
			pnlMenu.add(btnTimKiemKhachHang);
			pnlMenu.add(btnDonTraHang);
			

			pnlMenu.add(btnCaLam);
			pnlMenu.add(btnCapNhatCaLam);

			pnlMenu.add(btnHoaDon);
			pnlMenu.add(btnTimKiemHoaDon);

			pnlMenu.add(btnNhaCungCap);
			pnlMenu.add(btnCapNhatNCC);
			pnlMenu.add(btnTimKiemNCC);

			pnlMenu.add(btnSanPham);
			pnlMenu.add(btnCapNhatSanPham);
			pnlMenu.add(btnTimKiemSanPham);
			pnlMenu.add(btnMauSac);
			pnlMenu.add(btnKichCo);
			pnlMenu.add(btnChatLieu);
			pnlMenu.add(btnDanhMuc);

			pnlMenu.add(btnThongKe);
			pnlMenu.add(btnThongKeChiTieuKhachHang);
			pnlMenu.add(btnThongKeDoanhThu);
			pnlMenu.add(btnThongKeSanPham);

			pnlMenu.add(btnTaiKhoan);
			pnlMenu.add(btnCapNhatTaiKhoan);

			pnlMenu.add(btnKhuyenMai);
			pnlMenu.add(btnCapNhatChuongTrinhKhuyenMai);
		}else if(utils.Contains.getRole().equals(utils.Contains.NHAN_VIEN_BAN_HANG)){
			pnlMenu.add(pnlTilteMenu);

			pnlMenu.add(btnNhanVien);
			pnlMenu.add(btnDatHang);
			pnlMenu.add(btnTimKiemNhanVien);
			pnlMenu.add(btnTheoDoiLichLam);

			pnlMenu.add(btnKhachHang);
			pnlMenu.add(btnLapHoaDon);
			pnlMenu.add(btnCapNhatKhachHang);
			pnlMenu.add(btnTimKiemKhachHang);
			pnlMenu.add(btnDonTraHang);


			pnlMenu.add(btnHoaDon);
			pnlMenu.add(btnTimKiemHoaDon);


			pnlMenu.add(btnSanPham);
			pnlMenu.add(btnTimKiemSanPham);

			pnlMenu.add(btnThongKe);
			pnlMenu.add(btnThongKeChiTieuKhachHang);
			pnlMenu.add(btnThongKeDoanhThu);
			pnlMenu.add(btnThongKeSanPham);



		}else {
			pnlMenu.add(btnSanPham);
			pnlMenu.add(btnTimKiemSanPham);
		}


		 btnThongKe.addActionListener(this);
		 btnCaLam.addActionListener(this);
		 btnKhachHang.addActionListener(this);
		 btnNhanVien.addActionListener(this);
		 btnNhaCungCap.addActionListener(this);
		 btnSanPham.addActionListener(this);
		 btnTaiKhoan.addActionListener(this);
		 btnHoaDon.addActionListener(this);
		 btnKhuyenMai.addActionListener(this);
		 btnNhapHang.addActionListener(this);



		//add event
		btnCapNhatNhanVien.addActionListener(this);
		btnTimKiemNhanVien.addActionListener(this);
		btnTheoDoiLichLam.addActionListener(this);
		btnDatHang.addActionListener(this);
		btnPhanCongCaLam.addActionListener(this);
		btnChamCong.addActionListener(this);

		//khach Hang
		btnLapHoaDon.addActionListener(this);
		btnCapNhatKhachHang.addActionListener(this);
		btnTimKiemKhachHang.addActionListener(this);
		btnDonTraHang.addActionListener(this);

		//Ca Lam
		btnCapNhatCaLam.addActionListener(this);

		//Nha cung cap
		btnCapNhatNCC.addActionListener(this);
		btnTimKiemNCC.addActionListener(this);

		//san pham
		btnCapNhatSanPham.addActionListener(this);
		btnTimKiemSanPham.addActionListener(this);
		btnMauSac.addActionListener(this);
		btnKichCo.addActionListener(this);
		btnDanhMuc.addActionListener(this);
		btnChatLieu.addActionListener(this);
		//thong ke
		btnThongKeChiTieuKhachHang.addActionListener(this);
		btnThongKeDoanhThu.addActionListener(this);
		btnThongKeSanPham.addActionListener(this);

		//tai khoan
		btnCapNhatTaiKhoan.addActionListener(this);

		//hoa don
		btnTimKiemHoaDon.addActionListener(this);

		btnCapNhatChuongTrinhKhuyenMai.addActionListener(this);

		btnLogout.addActionListener(this);
	}

	/*
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		String cha = "CHA";
		String con = "CON";
		Dimension dimensionCha = new Dimension(217,42);
		Dimension dimensionCon = new Dimension(217,28);

		pnlContent = new JPanel();
		btnLogout = new JButton("Đăng Xuất");
		//btn cha
		btnHoaDon = new CustomButton("  Hóa Đơn", cha, dimensionCha,ICON_DOWN);
		btnThongKe = new CustomButton("  Thống Kê",cha,dimensionCha,ICON_DOWN);
		btnCaLam = new CustomButton("  Ca Làm",cha,dimensionCha,ICON_DOWN);
		btnKhachHang = new CustomButton("  Khách Hàng",cha,dimensionCha,ICON_DOWN);
		btnNhanVien = new CustomButton("  Nhân Viên",cha,dimensionCha,ICON_DOWN);
		btnNhaCungCap = new CustomButton("  Nhà Cung Cấp",cha,dimensionCha,ICON_DOWN);
		btnSanPham = new CustomButton("  Sản Phẩm",cha,dimensionCha,ICON_DOWN);
		btnTaiKhoan = new CustomButton("  Tài Khoản",cha,dimensionCha,ICON_DOWN);
		btnKhuyenMai = new CustomButton("  Khuyến mãi", cha, dimensionCha, ICON_DOWN);

		//item
		//btnThongKe
		btnThongKeDoanhThu = new CustomButton("Thống Kê Doanh Thu",con,dimensionCon);
		btnThongKeChiTieuKhachHang = new CustomButton("Thống Kê Chi Tiêu Khách Hàng",con,dimensionCon);
		//btnCaLam
		btnCapNhatCaLam = new CustomButton("Cập Nhật Ca Làm",con,dimensionCon);
		btnTimKiemHoaDon = new CustomButton("Tìm Kiếm Hóa Đơn",con,dimensionCon);
		//btnKhachHang
		btnLapHoaDon = new CustomButton("Lập Hóa Đơn",con,dimensionCon);
		btnCapNhatKhachHang = new CustomButton("Cập Nhât Khách Hàng",con,dimensionCon);
		btnTimKiemKhachHang = new CustomButton("Tìm Kiếm Khách Hàng",con,dimensionCon);
		btnDonTraHang = new CustomButton("Đơn trả hàng", con, dimensionCon);
		btnNhapHang = new CustomButton("Nhập hàng từ đơn trả", con, dimensionCon);
		//btnNhanVien
		btnDatHang = new CustomButton("Đặt Hàng",con,dimensionCon);
		btnCapNhatNhanVien = new CustomButton("Cập Nhật Nhân Viên",con,dimensionCon);
		btnTimKiemNhanVien = new CustomButton("Tìm Kiếm Nhân Viên",con,dimensionCon);
		btnTheoDoiLichLam = new CustomButton("Theo Dõi Lịch Làm",con,dimensionCon);
		btnPhanCongCaLam = new CustomButton("Phân Công Ca Làm",con,dimensionCon);
		btnChamCong = new CustomButton("Chấm công", con, dimensionCon);
		//btnNhaCungCap
		btnCapNhatNCC = new CustomButton("Cập Nhật Nhà Cung Cấp",con,dimensionCon);
		btnTimKiemNCC = new CustomButton("Tìm Kiếm Nhà Cung Cấp",con,dimensionCon);
		//btnSanPham
		btnCapNhatSanPham = new CustomButton("Cập Nhật Sản Phẩm",con,dimensionCon);
		btnTimKiemSanPham = new CustomButton("Tìm Kiếm Sản Phẩm",con,dimensionCon);
		btnThongKeSanPham = new CustomButton("Thống Kê Sản Phẩm",con,dimensionCon);
		 //btn taiKhoan
		btnCapNhatTaiKhoan = new CustomButton("Cập nhật tài khoản",con,dimensionCon);

		btnChatLieu = new CustomButton("Chất Liệu",con,dimensionCon);
		btnMauSac = new CustomButton("Màu Sắc",con,dimensionCon);
		btnDanhMuc = new CustomButton("Danh Mục",con,dimensionCon);
		btnKichCo = new CustomButton("Kích Cỡ",con,dimensionCon);

		btnCapNhatChuongTrinhKhuyenMai = new CustomButton("Cập nhật khuyến mãi", con, dimensionCon);
	}
	/*
	 * function closeItemMenu được sử dụng để đóng các button con của những
	 * button cha nào không được chọn
	 * @param btn button cha được chọn
	 */
	private void closeItemMenu(JButton btn) {
		if(!btn.equals(btnThongKe)) {
			btnThongKe.setShowIcon(true);
			btnThongKe.setLBLIcon(new JLabel(ICON_DOWN));
			btnThongKeDoanhThu.setVisible(false);
			btnThongKeChiTieuKhachHang.setVisible(false);
			btnThongKeSanPham.setVisible(false);
		}
		if(!btn.equals(btnHoaDon)) {
			btnHoaDon.setShowIcon(true);
			btnHoaDon.setLBLIcon(new JLabel(ICON_DOWN));
			btnTimKiemHoaDon.setVisible(false);
		}
		if(!btn.equals(btnCaLam)) {
			btnCaLam.setShowIcon(true);
			btnCaLam.setLBLIcon(new JLabel(ICON_DOWN));
			btnCapNhatCaLam.setVisible(false);
		}
		if(!btn.equals(btnKhachHang)) {
			btnKhachHang.setShowIcon(true);
			btnKhachHang.setLBLIcon(new JLabel(ICON_DOWN));
			btnLapHoaDon.setVisible(false);
			btnCapNhatKhachHang.setVisible(false);
			btnTimKiemKhachHang.setVisible(false);
			btnDonTraHang.setVisible(false);
		}
		if(!btn.equals(btnNhaCungCap)) {
			btnNhaCungCap.setShowIcon(true);
			btnNhaCungCap.setLBLIcon(new JLabel(ICON_DOWN));
			btnCapNhatNCC.setVisible(false);
			btnTimKiemNCC.setVisible(false);
		}
		if(!btn.equals(btnNhanVien)) {
			btnNhanVien.setShowIcon(true);
			btnNhanVien.setLBLIcon(new JLabel(ICON_DOWN));
			btnTimKiemNhanVien.setVisible(false);
			btnTheoDoiLichLam.setVisible(false);
			btnDatHang.setVisible(false);
			if(utils.Contains.getRole().equals("QL")) {
				btnPhanCongCaLam.setVisible(false);
				btnCapNhatNhanVien.setVisible(false);
				btnChamCong.setVisible(false);
				btnNhapHang.setVisible(false);
			}
		}
		if(!btn.equals(btnSanPham)) {
			btnSanPham.setShowIcon(true);
			btnSanPham.setLBLIcon(new JLabel(ICON_DOWN));
			btnTimKiemSanPham.setVisible(false);
			btnThongKeSanPham.setVisible(false);
			if(utils.Contains.getRole().equals("QL")) {
				btnCapNhatSanPham.setVisible(false);
				btnMauSac.setVisible(false);
				btnKichCo.setVisible(false);
				btnChatLieu.setVisible(false);
				btnDanhMuc.setVisible(false);
			}
		}
		if(!btn.equals(btnTaiKhoan)) {
			btnTaiKhoan.setShowIcon(true);
			btnTaiKhoan.setLBLIcon(new JLabel(ICON_DOWN));
			btnCapNhatTaiKhoan.setVisible(false);
		}
		if(!btn.equals(btnHoaDon)) {
			btnHoaDon.setShowIcon(true);
			btnHoaDon.setLBLIcon(new JLabel(ICON_DOWN));
			btnTimKiemHoaDon.setVisible(false);
		}
		if(!btn.equals(btnKhuyenMai)) {
			btnKhuyenMai.setShowIcon(true);
			btnKhuyenMai.setLBLIcon(new JLabel(ICON_DOWN));
			btnCapNhatChuongTrinhKhuyenMai.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();

		if(o.equals(btnThongKe)) {
			if(btnThongKe.getShowIcon()) {
				closeItemMenu(btnThongKe);
				btnThongKeDoanhThu.setVisible(true);
				btnThongKeChiTieuKhachHang.setVisible(true);
				btnThongKeSanPham.setVisible(true);
				btnThongKe.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnThongKeDoanhThu.setVisible(false);
				btnThongKeChiTieuKhachHang.setVisible(false);
				btnThongKeSanPham.setVisible(false);
				btnThongKe.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnThongKe.setShowIcon(!btnThongKe.getShowIcon());

		}else if(o.equals(btnCaLam)) {
			if(btnCaLam.getShowIcon()) {
				closeItemMenu(btnCaLam);
				btnCapNhatCaLam.setVisible(true);
				btnCaLam.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnCapNhatCaLam.setVisible(false);
				btnCaLam.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnCaLam.setShowIcon(!btnCaLam.getShowIcon());

		}else if(o.equals(btnKhachHang)) {
			if(btnKhachHang.getShowIcon()) {
				closeItemMenu(btnKhachHang);
				btnLapHoaDon.setVisible(true);
				btnCapNhatKhachHang.setVisible(true);
				btnTimKiemKhachHang.setVisible(true);
				btnDonTraHang.setVisible(true);
				btnKhachHang.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnDonTraHang.setVisible(false);
				btnLapHoaDon.setVisible(false);
				btnCapNhatKhachHang.setVisible(false);
				btnTimKiemKhachHang.setVisible(false);
				btnKhachHang.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnKhachHang.setShowIcon(!btnKhachHang.getShowIcon());

		}else if(o.equals(btnNhanVien)) {
			if(btnNhanVien.getShowIcon()) {
				closeItemMenu(btnNhanVien);
				btnDatHang.setVisible(true);
				btnTimKiemNhanVien.setVisible(true);
				btnTheoDoiLichLam.setVisible(true);
				if(utils.Contains.getRole().equals("QL")) {
					btnPhanCongCaLam.setVisible(true);
					btnCapNhatNhanVien.setVisible(true);
					btnChamCong.setVisible(true);
					btnNhapHang.setVisible(true);
				}
				btnNhanVien.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnDatHang.setVisible(false);
				btnTimKiemNhanVien.setVisible(false);
				btnTheoDoiLichLam.setVisible(false);
				if(utils.Contains.getRole().equals("QL")) {
					btnPhanCongCaLam.setVisible(false);
					btnCapNhatNhanVien.setVisible(false);
					btnChamCong.setVisible(false);
					btnNhapHang.setVisible(false);
				}
				btnNhanVien.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnNhanVien.setShowIcon(!btnNhanVien.getShowIcon());


		}else if(o.equals(btnNhaCungCap)) {
			if(btnNhaCungCap.getShowIcon()) {
				closeItemMenu(btnNhaCungCap);
				btnCapNhatNCC.setVisible(true);
				btnTimKiemNCC.setVisible(true);
				btnNhaCungCap.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnCapNhatNCC.setVisible(false);
				btnTimKiemNCC.setVisible(false);
				btnNhaCungCap.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnNhaCungCap.setShowIcon(!btnNhaCungCap.getShowIcon());

		}else if(o.equals(btnSanPham)) {
			if(btnSanPham.getShowIcon()) {
				closeItemMenu(btnSanPham);
				btnTimKiemSanPham.setVisible(true);
				if(utils.Contains.getRole().equals("QL")) {
					btnCapNhatSanPham.setVisible(true);
					btnMauSac.setVisible(true);
					btnKichCo.setVisible(true);
					btnChatLieu.setVisible(true);
					btnDanhMuc.setVisible(true);
				}
				btnSanPham.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnTimKiemSanPham.setVisible(false);
				btnMauSac.setVisible(false);
				btnKichCo.setVisible(false);
				btnChatLieu.setVisible(false);
				btnDanhMuc.setVisible(false);
				if(utils.Contains.getRole().equals("QL")) {
					btnCapNhatSanPham.setVisible(false);
				}
				btnSanPham.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnSanPham.setShowIcon(!btnSanPham.getShowIcon());

		}else if(o.equals(btnTaiKhoan)) {
			if(btnTaiKhoan.getShowIcon()) {
				closeItemMenu(btnTaiKhoan);
				btnCapNhatTaiKhoan.setVisible(true);
				btnTaiKhoan.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnCapNhatTaiKhoan.setVisible(false);
				btnTaiKhoan.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnTaiKhoan.setShowIcon(!btnTaiKhoan.getShowIcon());
		}else if(o.equals(btnThongKe)) {
			if(btnThongKe.getShowIcon()) {
				closeItemMenu(btnThongKe);
				btnThongKeChiTieuKhachHang.setVisible(true);
				btnThongKeDoanhThu.setVisible(true);
				btnThongKeSanPham.setVisible(true);
				btnThongKe.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnThongKeChiTieuKhachHang.setVisible(false);
				btnThongKeDoanhThu.setVisible(false);
				btnThongKeSanPham.setVisible(false);
				btnThongKe.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnThongKe.setShowIcon(!btnThongKe.getShowIcon());

		}else if(o.equals(btnHoaDon)) {
			if(btnHoaDon.getShowIcon()) {
				closeItemMenu(btnHoaDon);
				btnTimKiemHoaDon.setVisible(true);
				btnHoaDon.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnTimKiemHoaDon.setVisible(false);
				btnHoaDon.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnHoaDon.setShowIcon(!btnHoaDon.getShowIcon());

		}else if(o.equals(btnKhuyenMai)) {
			if(btnKhuyenMai.getShowIcon()) {
				closeItemMenu(btnKhuyenMai);
				btnCapNhatChuongTrinhKhuyenMai.setVisible(true);
				btnKhuyenMai.setLBLIcon(new JLabel(ICON_UPPER));
			}else {
				btnCapNhatChuongTrinhKhuyenMai.setVisible(false);
				btnKhuyenMai.setLBLIcon(new JLabel(ICON_DOWN));
			}
			btnKhuyenMai.setShowIcon(!btnKhuyenMai.getShowIcon());

		}else if(o.equals(btnDatHang)) {
			swithPanel(new gui_NhanVien.DatHang());
		}else if(o.equals(btnCapNhatNhanVien)) {
			swithPanel(new gui_NhanVien.CapNhatNhanVien());
		}else if(o.equals(btnTimKiemNhanVien)) {
			swithPanel(new gui_NhanVien.TimKiemNhanVien());
		}else if(o.equals(btnTheoDoiLichLam)) {
			swithPanel(new gui_NhanVien.TheoDoiLichLam());
		}else if(o.equals(btnPhanCongCaLam)) {
			swithPanel(new gui_NhanVien.PhanCongCaLam());
		}else if(o.equals(btnChamCong)) {
			swithPanel(new gui_NhanVien.BangChamCong());
		}else if(o.equals(btnNhapHang)) {
			swithPanel(new gui_NhanVien.NhapHangTuDonTraHang());
		}else if(o.equals(btnLapHoaDon)) {
			swithPanel(new gui_KhachHang.LapHoaDon());
		}else if(o.equals(btnCapNhatKhachHang)) {
			swithPanel(new gui_KhachHang.CapNhatKhachHang());
		}else if(o.equals(btnTimKiemKhachHang)) {
			swithPanel(new gui_KhachHang.TimKiemKhachHang());
		}else if(o.equals(btnDonTraHang)) {
			swithPanel(new gui_KhachHang.DonTraHang());
		}else if(o.equals(btnCapNhatCaLam)) {
			swithPanel(new gui_CaLam.CapNhatCaLam());
		}else if(o.equals(btnTimKiemHoaDon)) {
			swithPanel(new gui_HoaDon.TimKiemHoaDon());
		}else if(o.equals(btnCapNhatNCC)) {
			swithPanel(new gui_NhaCungCap.CapNhatNhaCungCap());
		}else if(o.equals(btnTimKiemNCC)) {
			swithPanel(new gui_NhaCungCap.TimKiemNhaCungCap());
		}else if(o.equals(btnCapNhatSanPham)) {
			swithPanel(new gui_SanPham.CapNhatSanPham());
		}else if(o.equals(btnTimKiemSanPham)) {
			swithPanel(new gui_SanPham.TimKiemSanPham());
		}else if(o.equals(btnMauSac)) {
			swithPanel(new gui_SanPham.MauSac());
		}else if(o.equals(btnKichCo)) {
			swithPanel(new gui_SanPham.KichCo());
		}else if(o.equals(btnChatLieu)) {
			swithPanel(new gui_SanPham.ChatLieu());
		}else if(o.equals(btnDanhMuc)) {
			swithPanel(new gui_SanPham.DanhMuc());
		}else if(o.equals(btnThongKeChiTieuKhachHang)) {
			swithPanel(new gui_ThongKe.ThongKeDoanhThuTheoKhachHang());
		}else if(o.equals(btnThongKeDoanhThu)) {
			swithPanel(new gui_ThongKe.ThongKeDoanhThu());
		}else if(o.equals(btnThongKeSanPham)) {
			swithPanel(new gui_ThongKe.ThongKeSanPham());
		}else if(o.equals(btnCapNhatTaiKhoan)) {
			swithPanel(new gui_TaiKhoan.CapNhatTaiKhoan());
		}else if(o.equals(btnKhuyenMai)) {
			swithPanel(new gui_KhuyenMai.CapNhatKhuyenMai());
		}
		else if(o.equals(btnLogout)) {
			int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất hay không ?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(result == 0) {
				utils.Contains.setMaNV("");
				utils.Contains.setTenNV("");
				FrameDangNhap frameDangNhap = new FrameDangNhap();
				FrameTrangChu.this.dispose();
				frameDangNhap.setVisible(true);
			}

		}

	}

	/*
	 * function AddSwithPanel được sử dụng để thêm sự kiện cho 1 panel
	 * dùng để thay đổi content của pnlContent
	 *
	 * @param pnl panel cần thay thế
	 * @return trả về đối tượng ActionListener
	 */
	public ActionListener AddSwithPanel(JPanel pnl) {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swithPanel(pnl);
			}
		};
		return action;
	}

	/*
	 * function swithPanel được sử dụng để thay xóa nội dụng trong pnlContent thay bằng
	 * pnl mới
	 *  @param panel mới
	 */
	public void swithPanel(JPanel pnl) {
		pnlContent.removeAll();
		pnlContent.add(pnl);
		pnlContent.validate();
		pnlContent.repaint();
	}


}

/*
 * custom lại button để làm menu button bên phải màng hình
 */
class CustomButton extends JButton{
	private JLabel lblIcon;
	private boolean showIcon =true;
	/*Sử dụng tạo btn cho menu, với 3 tham số đầu vào
	 *@param text text trong btn
	 *@param type type nhận giá trị Cha và Con được sử dụng để cấu hình kích thước của btn
	 *@param de kích thước của button
	 */
	public CustomButton(String text,String type,Dimension de) {
		Color colorOdd = Color.decode("#E3E3E3");
		Color colorEven = Color.WHITE;
		Font fontCha = new Font("`",Font.BOLD,16);
		Font fontCon = new FontUIResource("",Font.PLAIN, 14);

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#A7A7A7")));
		setBorder(null);
		setMaximumSize(de);
		setHorizontalAlignment(SwingConstants.LEFT);

		if(type.equals("CHA")) {
			setFont(fontCha);
			setText(text);

		}else {
			setText("      "+text);
			setFont(fontCon);
			setVisible(false);
		}
		 MouseListener mouseListener = new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                // Xử lý sự kiện khi chuột lướt vào JButton
	            	setBackground(Color.decode("#FF58B2"));
					setForeground(Color.white);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                // Xử lý sự kiện khi chuột lướt ra khỏi JButton
	            	setBackground(Color.white);
					setForeground(Color.BLACK);
	            }
	        };

		addMouseListener(mouseListener);
	}
	/*Sử dụng tạo btn cho menu, với 4 tham số đầu vào
	 *@param text text trong btn
	 *@param type type nhận giá trị Cha và Con được sử dụng để cấu hình kích thước của btn
	 *@param de kích thước của button
	 *@param iconDown icon down của button cha
	 */
	public CustomButton(String text, String type, Dimension de, ImageIcon iconDown) {
		Color colorOdd = Color.decode("#E3E3E3");
		Color colorEven = Color.WHITE;
		Font fontCha = new Font("",Font.BOLD,16);
		Font fontCon = new FontUIResource("",Font.PLAIN, 14);

		setBorder(null);
		setMaximumSize(de);
		setHorizontalAlignment(SwingConstants.LEFT);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#616161")));

		JLabel labelText = new JLabel(text);
		lblIcon = new JLabel(iconDown);

		lblIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblIcon,BorderLayout.EAST);
		add(labelText,BorderLayout.WEST);

		if(type.equals("CHA")) {
			labelText.setFont(fontCha);
			labelText.setText(text);


		}else {
			labelText.setText("      "+text);
			labelText.setFont(fontCon);
			setVisible(false);
		}

		 MouseListener mouseListener = new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                // Xử lý sự kiện khi chuột lướt vào JButton
	            	setBackground(Color.decode("#FF58B2"));
					labelText.setForeground(Color.white);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                // Xử lý sự kiện khi chuột lướt ra khỏi JButton
	            	setBackground(Color.white);
					labelText.setForeground(Color.BLACK);
	            }
	        };

		addMouseListener(mouseListener);

	}


	public JLabel getLBLIcon() {
		return lblIcon;
	}

	public void setLBLIcon(JLabel lblIcon) {
		remove(this.lblIcon);
		this.lblIcon = lblIcon;
		this.lblIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		add(this.lblIcon,BorderLayout.EAST);
	}

	public boolean getShowIcon() {
		return showIcon;
	}

	public void setShowIcon(boolean showIcon) {
		this.showIcon = showIcon;
	}


}