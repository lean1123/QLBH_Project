package gui_KhachHang;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.ss.formula.functions.Today;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietDonHang_DAO;
import dao.ChiTietHoaDon_Dao;
import dao.ChuongTrinhKhuyenMai_DAO;
import dao.DonHang_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.SanPham_DAO;
import entity.ChiTietDonHang;
import entity.ChuongTrinhKhuyenMai;
//import entity.DonHang;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import utils.Contains;
import utils.Function;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class LapHoaDon extends JPanel implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnTimKiemDatHang,pnThanhToan;
	private JLabel lblTimKiemDonDatHang,lblMaDonHang,lblTenNhanVien,lblTenKhachHang,lblNgayDat,lblThanhToan,lblTongTien,lblTienKhachDua,
	lblTienTraLai,lblDonVi_2,lblDonVi_3,lblDonVi_1,lblTieuDe1,lblTieuDe2,lblMaKhuyenMai,lblDonVi_4,lblSoTienGiam;
	private JButton btnThanhToan,btnMuaThem,btnTimKiem,btnOK;
	private JTextField txtTenNhanVien,txtTenKhachHang,txtDiaChi,txtTongTien,txtTienKhachDua,txtTienTraLai,txtMaKM,txtSoTienGiam;
	private JCheckBox checkBoxXuatHoaDon;
	private JTable tblDonHang, tblChiTietDonHang;
	private DefaultTableModel modelTBLDonHang,modelTBLChiTietDonHang;
	private JScrollPane scrPanel,scrPanelNew;
	private JDateChooser dateChooserNgayDat;
	private JComboBox cbbMaDonHang;
	private JRadioButton rdbtnKhongKhuyenMai; 
	private DonHang_DAO dsdh;
	private ChiTietDonHang_DAO dsctdh;
	private JButton btnXoaTrang,btnXoa;
	private HoaDon_DAO dshd;
	private ChuongTrinhKhuyenMai_DAO dsctkm;
	private ChiTietHoaDon_Dao dscthd;
	private KhachHang_DAO dskh;
	private NhanVien_DAO dsnv;
	private SanPham_DAO dssp;
	
	
	/**
	 * Create the panel.
	 */
	public LapHoaDon() {
		//kết nối với cơ sở dữ liệu
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dsdh=new DonHang_DAO();
		dsctdh=new ChiTietDonHang_DAO();
		dshd=new HoaDon_DAO();
		dsctkm=new ChuongTrinhKhuyenMai_DAO();
		dscthd=new ChiTietHoaDon_Dao();
		dskh=new KhachHang_DAO();
		dsnv=new NhanVien_DAO();
		dssp=new SanPham_DAO();
		
	
		//formatter=new DecimalFormat("###,###.###");
		//Tạo GUI
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		
		pnTimKiemDatHang = new JPanel();
		pnTimKiemDatHang.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnTimKiemDatHang.setBackground(new Color(255, 255, 255));
		pnTimKiemDatHang.setBounds(10, 21, 603, 188);
		add(pnTimKiemDatHang);
		pnTimKiemDatHang.setLayout(null);
		
		lblTimKiemDonDatHang = new JLabel("Tìm kiếm đơn hàng");
		lblTimKiemDonDatHang.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTimKiemDonDatHang.setBounds(214, 10, 208, 34);
		pnTimKiemDatHang.add(lblTimKiemDonDatHang);
		
		lblMaDonHang = new JLabel("Mã đơn hàng");
		lblMaDonHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaDonHang.setBounds(10, 54, 127, 34);
		pnTimKiemDatHang.add(lblMaDonHang);
		
		lblTenNhanVien = new JLabel("Tên nhân viên");
		lblTenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenNhanVien.setBounds(10, 98, 127, 34);
		pnTimKiemDatHang.add(lblTenNhanVien);
		
		lblTenKhachHang = new JLabel("Tên khách hàng");
		lblTenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenKhachHang.setBounds(295, 54, 127, 34);
		pnTimKiemDatHang.add(lblTenKhachHang);
		
		lblNgayDat = new JLabel("Ngày đặt");
		lblNgayDat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNgayDat.setBounds(295, 98, 88, 34);
		pnTimKiemDatHang.add(lblNgayDat);
		
		txtTenNhanVien = new JTextField();
		txtTenNhanVien.setColumns(10);
		txtTenNhanVien.setBounds(126, 103, 154, 29);
		pnTimKiemDatHang.add(txtTenNhanVien);
		
		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setColumns(10);
		txtTenKhachHang.setBounds(427, 59, 163, 29);
		pnTimKiemDatHang.add(txtTenKhachHang);
		
//		txtDiaChi = new JTextField();
//		txtDiaChi.setColumns(10);
//		txtDiaChi.setBounds(464, 101, 163, 29);
//		pnTimKiemDatHang.add(txtDiaChi);
		dateChooserNgayDat = new JDateChooser();
		dateChooserNgayDat.setBounds(427, 98, 163, 29);
		pnTimKiemDatHang.add(dateChooserNgayDat);
		
		btnTimKiem = new JButton("Tìm kiếm");
		//btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		//btnTimKiem.setBackground(new Color(255, 0, 138));
		btnTimKiem.setBounds(431, 144, 134, 34);
		pnTimKiemDatHang.add(btnTimKiem);
		
		pnThanhToan = new JPanel();
		pnThanhToan.setLayout(null);
		pnThanhToan.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnThanhToan.setBackground(Color.WHITE);
		pnThanhToan.setBounds(623, 21, 685, 188);
		add(pnThanhToan);
		
		lblThanhToan = new JLabel("Thanh toán");
		lblThanhToan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblThanhToan.setBounds(274, 10, 105, 34);
		pnThanhToan.add(lblThanhToan);
		
		lblTongTien = new JLabel("Tổng tiền");
		lblTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTongTien.setBounds(10, 144, 127, 34);
		pnThanhToan.add(lblTongTien);
		
		lblTienKhachDua = new JLabel("Tiền khách đưa");
		lblTienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblTienKhachDua.setBounds(371, 47, 113, 34);
		pnThanhToan.add(lblTienKhachDua);
		
		lblTienTraLai = new JLabel("Tiền trả lại");
		lblTienTraLai.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblTienTraLai.setBounds(371, 83, 105, 34);
		pnThanhToan.add(lblTienTraLai);
		
		btnThanhToan = new JButton("Thanh toán");
		//btnThanhToan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThanhToan.setBounds(493, 127, 134, 34);
		//btnThanhToan.setBackground(Color.decode("#FF008A"));
		pnThanhToan.add(btnThanhToan);
		
		checkBoxXuatHoaDon = new JCheckBox("Xuất hóa đơn");
		checkBoxXuatHoaDon.setBounds(371, 131, 105, 26);
		pnThanhToan.add(checkBoxXuatHoaDon);
		
		txtTongTien = new JTextField();
		txtTongTien.setColumns(10);
		txtTongTien.setBounds(138, 149, 120, 29);
		pnThanhToan.add(txtTongTien);
		txtTongTien.setEditable(false);
		
		txtTienKhachDua = new JTextField();
		txtTienKhachDua.setColumns(10);
		txtTienKhachDua.setBounds(486, 52, 120, 29);
		pnThanhToan.add(txtTienKhachDua);
		
		lblDonVi_2 = new JLabel("VNĐ");
		lblDonVi_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDonVi_2.setBounds(263, 110, 45, 26);
		pnThanhToan.add(lblDonVi_2);
		
		lblDonVi_3 = new JLabel("VNĐ");
		lblDonVi_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDonVi_3.setBounds(616, 52, 45, 26);
		pnThanhToan.add(lblDonVi_3);
		
		lblDonVi_1 = new JLabel("VNĐ");
		lblDonVi_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDonVi_1.setBounds(263, 149, 45, 26);
		pnThanhToan.add(lblDonVi_1);
		
		lblTieuDe1 = new JLabel("Danh sách đơn đặt hàng");
		lblTieuDe1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe1.setBounds(521, 221, 295, 41);
		add(lblTieuDe1);
		
		//Bảng đơn hàng
		String columns[] = { "Mã đơn hàng","Tên nhân viên","Tên khách hàng","Số điện thoại","Địa chỉ","Ngày lập","Tổng tiền"};
		modelTBLDonHang = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblDonHang = new JTable(modelTBLDonHang);
		scrPanel = new JScrollPane(tblDonHang, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblDonHang);
		scrPanel.setBounds(10, 272, 1298, 222);
		add(scrPanel);
		
		//Bảng chi tiết đơn hàng
		String[] columnsNew = {"Mã sản phẩm","Tên sản phẩm","Danh mục","Màu sắc","Chất liệu","Kích cỡ", "Nhà cung cấp","Số lượng","Đơn giá"};
		modelTBLChiTietDonHang = new DefaultTableModel(columnsNew, 0);
		tblChiTietDonHang = new JTable(modelTBLChiTietDonHang);
		scrPanelNew = new JScrollPane(tblChiTietDonHang);
		scrPanelNew.setBounds(10, 550, 1298, 237);
		add(scrPanelNew);
		
		lblTieuDe2 = new JLabel("Chi tiết đơn hàng");
		lblTieuDe2.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe2.setBounds(574, 499, 193, 41);
		add(lblTieuDe2);
		
//		btnMuaThem = new JButton("Mua thêm");
//		btnMuaThem.setBounds(993, 199, 134, 34);
//		btnMuaThem.setBackground(Color.decode("#FF008A"));
//		add(btnMuaThem);
//		btnMuaThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				
//		txtMaKM = new JTextField();
//		txtMaKM.setBounds(159, 47, 120, 29);
//		pnThanhToan.add(txtMaKM);
//		txtMaKM.setColumns(10);
		
		txtMaKM = new JTextField();
		txtMaKM.setBounds(138, 47, 120, 29);
		pnThanhToan.add(txtMaKM);
		txtMaKM.setColumns(10);
		
		lblMaKhuyenMai = new JLabel("Mã khuyến mãi");
		lblMaKhuyenMai.setBounds(10, 42, 127, 34);
		pnThanhToan.add(lblMaKhuyenMai);
		lblMaKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		btnOK = new JButton("√");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOK.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnOK.setBounds(263, 47, 45, 31);
		pnThanhToan.add(btnOK);
		
		rdbtnKhongKhuyenMai = new JRadioButton("Không có");
		rdbtnKhongKhuyenMai.setBounds(138, 83, 81, 21);
		pnThanhToan.add(rdbtnKhongKhuyenMai);
		
		lblDonVi_4 = new JLabel("VNĐ");
		lblDonVi_4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDonVi_4.setBounds(616, 88, 45, 26);
		pnThanhToan.add(lblDonVi_4);
		
		lblSoTienGiam = new JLabel("Số tiền giảm");
		lblSoTienGiam.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSoTienGiam.setBounds(10, 105, 127, 34);
		pnThanhToan.add(lblSoTienGiam);
		
		txtSoTienGiam = new JTextField();
		txtSoTienGiam.setColumns(10);
		txtSoTienGiam.setBounds(138, 110, 120, 29);
		pnThanhToan.add(txtSoTienGiam);
		
		txtTienTraLai = new JTextField();
		txtTienTraLai.setEditable(false);
		txtTienTraLai.setColumns(10);
		txtTienTraLai.setBounds(486, 88, 120, 29);
		pnThanhToan.add(txtTienTraLai);
		
		cbbMaDonHang = new JComboBox();
		cbbMaDonHang.setBounds(126, 59, 154, 29);
		pnTimKiemDatHang.add(cbbMaDonHang);
		cbbMaDonHang.addItem("Tất cả");
		
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBounds(271, 144, 134, 34);
		pnTimKiemDatHang.add(btnXoaTrang);
		
		btnXoa = new JButton("Xóa");
		btnXoa.setBounds(1156, 228, 134, 34);
		add(btnXoa);
		
		txtSoTienGiam.setEditable(false);
		//cài đặt định dạng cho button
		utils.Format.setButtonEvent(btnThanhToan,btnTimKiem);

		
		docDuLieuTuDatabase(); // hiển thị thông tin các đơn hàng lên bảng đơn hàng
		updateCbbMaDonHang(); // cập nhật mã đơn hàng vào cbb
		tblDonHang.addMouseListener(this);
		//btnOK.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnOK.addActionListener(this);
		btnXoa.addActionListener(this);
		rdbtnKhongKhuyenMai.addActionListener(new ActionListener() {
			
			@Override
			// cập nhật mã khuyến mãi là KhongCo nếu không dùng mã khuyến mãi
			public void actionPerformed(ActionEvent e) {
				if(rdbtnKhongKhuyenMai.isSelected()) {
					txtMaKM.setText("KhongCo");
				}
			}
		});
		
		// tính toán tiền trả lại dựa vào tiền khách đưa và tổng tiền của hóa đơn, nếu đủ hoặc dư thì sẽ tính tiền trả lại và hiển thị trên txtTienTraLai, thiếu thì hiển thị là chưa đủ
		txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        updateChange();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        updateChange();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		    }

		    private void updateChange() {
		        try {
		            float soTienTraLai;
		            float tongTien = Float.parseFloat(txtTongTien.getText().toString().replaceAll("[,.]", ""));
		            float tienKhachDua = Float.parseFloat(txtTienKhachDua.getText().toString().replaceAll("[,.]", ""));
		            soTienTraLai = tienKhachDua - tongTien;
		            if (soTienTraLai >= 0) {
		    			DecimalFormat formatter=new DecimalFormat("###,###");
		  
		             //   String sotienTraLaiString = Float.toString(soTienTraLai);
		              //  txtTienTraLai.setText(sotienTraLaiString);
		                txtTienTraLai.setText(formatter.format(soTienTraLai));
		            } else {
		                txtTienTraLai.setText("Chưa đủ");
		            }
		        } catch (NumberFormatException ex) {
		            txtTienTraLai.setText("");
		        }
		    }
		});
		

	}
	
	@Override
	//gắn sự kiện vào các nút
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();		
		if(o.equals(btnTimKiem)) {
			if(dateChooserNgayDat.getDate()==null){// tìm kiếm nếu không chọn dữ liệu ngày
				timKiemKhongCoNgay();
			} else {
				timKiemCoNgay();		// tìm kiếm nếu chọn cả dữ liệu ngày	
			}
		}else if(o.equals(btnXoaTrang)) {
			xoaTrang();					// xóa trắng các thông tin trên textField
		}else if(o.equals(btnThanhToan)) {
			if(kiemTraDieuKienThanhToan()) {
				thanhToan();			// kiểm tra điều kiện thanh tóan xong thì thực hiện thanh toán
			}
		}else if(o.equals(btnOK)) {		// tìm kiếm khuyến mãi và áp dụng
			tinhKhuyenMai();
		}else if(o.equals(btnXoa)) {	// xóa đơn hàng
			int xacNhan=JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa đơn hàng này", "Xác nhận xóa",JOptionPane.YES_NO_OPTION);
			if(xacNhan==JOptionPane.YES_OPTION) {
				xoaDonHang();
				capNhatSoLuongSanPhamNeuHuyDonHang();
			}
					
		}	
	}
	
	// đọc dữ liệu từ database và hiển thị lên table Đơn hàng
	public void docDuLieuTuDatabase() {
		List<String[]> donHang=dsdh.getListDonHangChoGui2();
		for (String[] row : donHang) {
            modelTBLDonHang.addRow(row);
        }	
		
		
	}

	// đọc dữ liệu mã đơn hàng từ database sau đó thêm vào combobox mã đơn hàng
	public void updateCbbMaDonHang() {
		for(entity.DonHang donHang : dsdh.getDanhSachDonHang()) {
			cbbMaDonHang.addItem(donHang.getMaDonHang()+"");
		}
	}
	
//	public void mouseClicked(MouseEvent e) {
//		int selectedRow=tblDonHang.getSelectedRow();
//		String maDH=tblDonHang.getValueAt(selectedRow, 0).toString()+"";
//		List<String[]> chiTietDonHang=dsctdh.getListChiTietDonHangraGUI(maDH);
//		for(String[] row : chiTietDonHang) {
//			xoaTrangTBChiTietDonHang();
//			modelTBLChiTietDonHang.addRow(row);
//		}
//		
//
//	}
	@Override
	// xử lý sự kiện khi bấm vào 1 đơn hàng thì sẽ hiển thị thông tin chi tiết đơn hàng vào bảng chi tiết đơn hàng và các thông tin của đơn hàng lên textField, combobox,...
	public void mouseClicked(MouseEvent e) {
	    int selectedRow = tblDonHang.getSelectedRow();
	    String maDH = tblDonHang.getValueAt(selectedRow, 0).toString() + ""; // lấy dữ liệu mã đơn hàng từ dòng mình chọn

	    // Xóa toàn bộ dòng trong bảng chi tiết đơn hàng trước khi thêm dòng mới
	    int rowCount = modelTBLChiTietDonHang.getRowCount();
	    for (int i = rowCount - 1; i >= 0; i--) {
	        modelTBLChiTietDonHang.removeRow(i);
	    }

	    List<String[]> chiTietDonHang = dsctdh.getListChiTietDonHangraGUI(maDH);
	    for (String[] row : chiTietDonHang) {
	        modelTBLChiTietDonHang.addRow(row); // Thêm dữ liệu mới vào bảng
	    }
	    
	    // set các dữ liệu của đơn hàng đó lên các ô thông tin
	    cbbMaDonHang.setSelectedItem((String) tblDonHang.getValueAt(selectedRow, 0));
	    txtTenNhanVien.setText((String) tblDonHang.getValueAt(selectedRow, 1));
	    txtTenKhachHang.setText((String) tblDonHang.getValueAt(selectedRow, 2));
	    txtMaKM.setText("");
	    txtSoTienGiam.setText("");
	  //  dateChooserNgayDat.setDateFormatString((String) tblDonHang.getValueAt(selectedRow, 5));
	  //  String tongTienfm=tblDonHang.getValueAt(selectedRow, 6).toString().replaceAll("\\.", "");
	    String tongTienfm=tblDonHang.getValueAt(selectedRow, 6).toString();
	    txtTongTien.setText(tongTienfm);
	 //   txtTongTien.setText((String) tblDonHang.getValueAt(selectedRow, 6));
	    
	    // chuyển đổi dữ liệu string từ table thành dữ liệu date và set cho ô dateChoooser Ngày đặt
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	    String dateString =(String) tblDonHang.getValueAt(selectedRow, 5);
	    Date date = null;
	    try {
	        date = sdf.parse(dateString);
	    } catch (ParseException e1) {
	        e1.printStackTrace();
	    }

	    if (date != null) {
	        dateChooserNgayDat.setDate(date);
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
	
	public void xoaTrangTBChiTietDonHang() {
		modelTBLChiTietDonHang.getDataVector().removeAllElements(); // xóa table chi tiết đơn hàng
	}
	
	
	
	// tính toán tiền trả lại dựa vào tiền khách đưa và tổng tiền của hóa đơn, nếu đủ hoặc dư thì sẽ tính tiền trả lại và hiển thị trên txtTienTraLai, thiếu thì hiển thị là chưa đủ
//	public void tinhTienTraLai() {
//		//if(txtTienTraLai.getText().toString() != null) {
//			float soTienTraLai;
//			float tongTien=Float.parseFloat(txtTongTien.getText().toString().replaceAll("\\.", ""));
//			float tienKhachDua=Float.parseFloat(txtTienKhachDua.getText().toString());
//			soTienTraLai=tienKhachDua-tongTien;
//			if(soTienTraLai>=0) {
//				String sotienTraLaiString= Float.toString(soTienTraLai);
//				txtTienTraLai.setText(sotienTraLaiString);
//			} else {
//				txtTienTraLai.setText("Chưa đủ");
//			}		
//	}

	
	// tìm kiếm đơn hàng nếu không chọn dữ liệu ngày
	public void timKiemKhongCoNgay() {
		String maDH=cbbMaDonHang.getSelectedItem().toString()+"";
		if(maDH.equals("Tất cả"))
			maDH="";
		String tenKH=txtTenKhachHang.getText().trim().toString()+"";
		String tenNV=txtTenNhanVien.getText().trim().toString()+"";
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
////        String strDate = dateFormat.format(dateChooserNgayDat.getDate());
//		String strDate ;
//
//        if(dateChooserNgayDat.getDate()==null) {
//        	strDate="";
//        }else {
//        	strDate=dateFormat.format(dateChooserNgayDat.getDate());
//        }
        
        // gọi hàm tìm kiếm đơn hàng theo mã đơn hàng, tên nhân viên, tên khách hàng và thêm nó vào list
		List<String[]> listDonHang=dsdh.timKiemDonHangKhongCoNgay(maDH, tenNV, tenKH);
        if(listDonHang.size()>0) { // nếu tìm thấy thì cái size của list sẽ lớn hơn 0 
        	xoaHetDuLieuTrenTableDonHang(); // xóa dữ liệu trên table đơn hàng
        	xoaHetDuLieuTrenTableChiTietDonHang(); // xóa dữ liệu trên table chi tiết đơn hàng
        	for (String[] row : listDonHang) {
                modelTBLDonHang.addRow(row);  // thêm đơn hàng tìm thấy được vào bảng đơn hàng
            }       
        } else {
        	JOptionPane.showMessageDialog(null, "Không tìm thấy"); // không tìm thấy thì hiện thông báo ra màn hình
        }
	}
	
	// tìm kiếm đơn hàng nếu chọn dữ liệu ngày
	public void timKiemCoNgay() {
		//lấy cái dữ liệu cần tìm kiếm ra
		String maDH=cbbMaDonHang.getSelectedItem().toString()+"";
		if(maDH.equals("Tất cả"))
			maDH="";
		String tenKH=txtTenKhachHang.getText().trim().toString()+"";
		String tenNV=txtTenNhanVien.getText().trim().toString()+"";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        String strDate = dateFormat.format(dateChooserNgayDat.getDate());

        //  gọi hàm tìm kiếm đơn hàng theo mã đơn hàng, tên nhân viên, tên khách hàng, ngày đặt và thêm nó vào list
        List<String[]> listDonHang=dsdh.timKiemDonHangCoNgay(maDH, tenNV, tenKH, strDate);
        if(listDonHang.size()>0) { //nếu tìm thấy thì cái size của list sẽ lớn hơn 0
        	xoaHetDuLieuTrenTableDonHang();  // xóa dữ liệu trên table đơn hàng
        	xoaHetDuLieuTrenTableChiTietDonHang();  // xóa dữ liệu trên table chi tiết đơn hàng
        	for (String[] row : listDonHang) {
                modelTBLDonHang.addRow(row);  // thêm đơn hàng tìm thấy được vào bảng đơn hàng
            }       
        } else {
        	JOptionPane.showMessageDialog(null, "Không tìm thấy");  // không tìm thấy thì hiện thông báo ra màn hình
        }
        
	}

	// xóa dữ liệu trên table đơn hàng
	public void xoaHetDuLieuTrenTableDonHang() {
		 DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
		    int rowCount = model.getRowCount();
		    for (int i = rowCount - 1; i >= 0; i--) {
		        model.removeRow(i);
		    }

	}

	// xóa dữ liệu trên table chi tiết đơn hàng
	public void xoaHetDuLieuTrenTableChiTietDonHang() {
		 DefaultTableModel model = (DefaultTableModel) tblChiTietDonHang.getModel();
		    int rowCount = model.getRowCount();
		    for (int i = rowCount - 1; i >= 0; i--) {
		        model.removeRow(i);
		    }

	}
	
	// xóa trắng
	public void xoaTrang() {
		cbbMaDonHang.setSelectedItem("Tất cả");
		txtTenKhachHang.setText("");
		txtTenNhanVien.setText("");
		dateChooserNgayDat.setDate(null);
		txtTongTien.setText("");
		txtMaKM.setText("");
		txtSoTienGiam.setText("");
		txtTienKhachDua.setText("");
		txtTienTraLai.setText("");
	}
	
	// thánh toán
	public void thanhToan() {
		
		String maDH=cbbMaDonHang.getSelectedItem().toString()+"";// lấy mã đơn hàng cần thanh toán
		
		entity.DonHang donHangThanhToan=null; 
		donHangThanhToan=dsdh.timDonHangTheoMa(maDH); // tìm kiếm đơn hàng cần thanh toán bằng mã
		NhanVien nhanVien=donHangThanhToan.getNhanVien(); // lấy ra Nhân Viên để lát cho vào hóa đơn
		KhachHang khachHang=donHangThanhToan.getKhachHang(); // lấy ra Khách Hàng để lát cho vào hóa đơn
		Date ngayLapHD=new Date(); // lấy ra ngày lập hóa đơn sẽ là ngày hôm nay
		
		String maCaptcha=txtMaKM.getText().trim().toString()+""; // lấy mã Captcha để áp dụng khuyến mãi
		ChuongTrinhKhuyenMai chuongTrinhKhuyenMai=dsctkm.timChuongTrinhKhuyenMaiTheoMaCaptcha(maCaptcha); // tìm kiếm khuyến mãi theo Captcha
		
		
		HoaDon hoaDon=new HoaDon(null, nhanVien, khachHang, ngayLapHD, chuongTrinhKhuyenMai); // tạo hóa đơn mới với Nhân Viên, Khách Hàng và Chương trình khuyến mãi (mã phát sinh tự động)
		dshd.themHoaDon(hoaDon, chuongTrinhKhuyenMai.getMaKhuyenMai()); // thêm hóa đơn vào database
		
		String maHD=dshd.layDuLieuCotMaHD();  // lấy dữ liệu mã hóa đơn mới tạo
		
		int rowCount=modelTBLChiTietDonHang.getRowCount(); 
		// đọc thông tin trên table chi tiết hóa đơn bằng vòng lặp for và lấy ra các thông tin cần thiết
		for(int i=0;i<rowCount;i++) {
			String maSP=(String) tblChiTietDonHang.getValueAt(i, 0);
			float donGia=Float.parseFloat(tblChiTietDonHang.getValueAt(i, 8).toString().replaceAll("[,.]", "")+"");
			int soLuong=Integer.parseInt(tblChiTietDonHang.getValueAt(i, 7).toString().replaceAll("[,.]", "")+"");
			
			dscthd.themChiTietHoaDon(maHD, maSP, donGia, soLuong); // thêm các đơn hàng trong chi tiết đơn hàng vào chi tiết hóa đơn với mã hóa đơn vừa tạo
		}
		
		// nếu nút xuất hóa đơn được chọn thì sau khi thanh toán thành công sẽ xuất hóa đơn ra màn hình 
		if(checkBoxXuatHoaDon.isSelected()) {	
			String maHDVuaTao=dshd.layDuLieuCotMaHD();
			DecimalFormat currencyFormat=new DecimalFormat("###,###.###");
			HoaDon hoaDonTimDuoc=dshd.getHoaDonTheoMa(maHDVuaTao);
			System.out.println(hoaDonTimDuoc.getMaHoaDon());
////			double tongTienThanhToan=dshd.tinhTongTien(maHDVuaTao);
////			double giaKhuyenMai=dshd.tinhGiaKhuyenMaiCuaHoaDon(maHDVuaTao);
//			double giaKhuyenMai=Double.parseDouble(txtSoTienGiam.getText().toString());
////			double tongTienThanhToan=dshd.tinhTongTien(maHDVuaTao)-giaKhuyenMai;
//			double tongTienThanhToan= Double.parseDouble(txtTongTien.getText().toString());
//			if(tongTienThanhToan==0) {
//				JOptionPane.showMessageDialog(null, "Không tồn tại sản phẩm trong hóa đơn");
//				return;
//			}
//			String giaGiamStr=currencyFormat.format(giaKhuyenMai);
//			String tongTienString=currencyFormat.format(tongTienThanhToan);
			String giaGiamStr=txtSoTienGiam.getText().toString();
			System.out.println(giaGiamStr);
			
			String tongTienString=txtTongTien.getText().toString();
			System.out.println(tongTienString);
			//Function.xuatHoaDonPDF("sMaHD", hoaDonTimDuoc.getMaHD(), "sTongTien", tongTienString, Contains.getPathOfReportFiles(), isPaintingForPrint());	
			Function.xuatHoaDonPDF("sMaHoaDon", hoaDonTimDuoc.getMaHoaDon().toString(), "sGiaGiam", giaGiamStr, "sTongTien", tongTienString, Contains.getPathOfReportFiles(), false);
			
			dsctkm.capNhatSoLuongMaKhuyenMai(maCaptcha); // cập nhật lại số lượng của khuyến mãi sau khi được áp dụng thành công
			xoaDonHang(); // xóa đơn hàng sau khi thanh toán thành công
			xoaTrang(); // xóa trắng
		}
		
	}
	
	// tính khuyến mãi
	public void tinhKhuyenMai() {
		ChuongTrinhKhuyenMai khuyenMai=null;
	
		String maCaptcha=txtMaKM.getText().trim().toString(); // lấy ra mã Captcha được nhập ở txtMaKM
		if(maCaptcha.equals("")) {
//			JOptionPane.showMessageDialog(null, "Chưa nhập mã khuyến mãi");
//			//txtSoTienGiam.setText("KhongCo");
//			maCaptcha="KhongCo";
			rdbtnKhongKhuyenMai.setSelected(true);
			txtMaKM.setText("KhongCo");
			maCaptcha=txtMaKM.getText().trim().toString();
		}
		khuyenMai=dsctkm.timChuongTrinhKhuyenMaiTheoMaCaptcha(maCaptcha); // tìm chương trình khuyến mãi theo mã Captcha
		if(khuyenMai==null) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy mã khuyến mãi"); // không tìm thấy thì báo ra màn hình
		} else{
			DecimalFormat formatter=new DecimalFormat("###,###.###");
			if(kiemTraKhuyenMai(maCaptcha)) { // nếu tìm thấy
				rdbtnKhongKhuyenMai.setSelected(false);
				double soPhanTramGiam=khuyenMai.getGiaGiam(); // lấy ra số phần trăm giảm của mã khuyến mãi đó
				float soPhanTramGiamFloat=(float) soPhanTramGiam; 
				int row=tblDonHang.getSelectedRow();
				String tongTienTruocKMChuaFM=tblDonHang.getValueAt(row, 6).toString().replaceAll("[,.]", "");
				float tongTienTruocKM=Float.parseFloat(tongTienTruocKMChuaFM);
			//	float tongTienTruocKM=Float.parseFloat(tblDonHang.getValueAt(row, 6).toString()); // lấy ra tổng tiền của đơn hàng ở trên table trước khi áp dụng khuyến mãi
				float soTienGiam=soPhanTramGiamFloat * tongTienTruocKM; // tính số tiền được giảm bằng cách nhân tổng tiền với số phần trắm giảm
			//	soTienGiam=formatter.format(soTienGiam);
				txtSoTienGiam.setText(formatter.format(soTienGiam));
			//	txtSoTienGiam.setText(Float.toString(soTienGiam)); // hiện số tiên giảm lên txtSoTienGiam
				float tongTienSauKM= tongTienTruocKM-soTienGiam; // tính toán lại tổng tiền sau khi được áp dụng khuyến mãi bằng cách lấy tổng tiền trước KM - số tiền được giảm
				txtTongTien.setText(formatter.format(tongTienSauKM));
			//	txtTongTien.setText(Float.toString(tongTienSauKM)); // hiện tổng tiền lên txtTongTien
			} 		
		}						
	}
	
	// kiểm tra điều kiện khuyến mãi
	public boolean kiemTraKhuyenMai(String maCaptcha) {
		
		Date ngayDat=dateChooserNgayDat.getDate(); // lấy ra ngày đặt hàng
		ChuongTrinhKhuyenMai khuyenMai=dsctkm.timChuongTrinhKhuyenMaiTheoMaCaptcha(maCaptcha); // tìm chương trình KM bằng mã Captcha
		
		Date ngayBatDau=khuyenMai.getNgayBatDau(); //lấy ra ngày bắt đầu của KM
		Date ngayKetThuc=khuyenMai.getNgayKetThuc(); // lấy ra ngày kết thúc của KM
		if(ngayDat.compareTo(ngayBatDau)<0 || ngayDat.compareTo(ngayKetThuc)>0) { // nếu mà trước ngày bắt đầu KM hoặc sau ngày kết thúc KM thì báo là không nằm trong thời gian KM và trả về false 
			JOptionPane.showMessageDialog(null, "Không nằm trong thời gian khuyến mãi");
			txtMaKM.setText("");   // set text lại mã khuyến mãi
			txtMaKM.requestFocus();
			return false;
		}
		int soLuongKM=khuyenMai.getSoLuong(); // lấy ra số lượng mã khuyến mãi
		// nếu số lượng mã khuyến mãi =0 thì báo hết lượt KM và trả về false
		if(soLuongKM==0) {
			JOptionPane.showMessageDialog(null, "Mã này đã hết lượt khuyến mãi");
			txtMaKM.setText("");
			txtMaKM.requestFocus();
			return false;
		}
		return true;
	}
	
	// kiểm tra các dữ liệu đã đầy đủ trước khi thanh toán chưa
	public boolean kiemTraDieuKienThanhToan() {
		int row=tblDonHang.getSelectedRow();
		if(row==-1) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn đơn hàng cần thanh toán");
			return false;
		} else if(txtMaKM.getText().trim().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Mã khuyến mãi không được để trống");
			txtMaKM.requestFocus();
			return false;
		} else if(txtTienKhachDua.getText().trim().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Tiền khách đưa không được để trống");
			txtTienKhachDua.requestFocus();
			return false;
		}
		return true;
	}
	
	public void xoaDonHang() {
	    int row = tblDonHang.getSelectedRow(); // Lấy hàng đang được chọn
	    if (row != -1) { // Kiểm tra xem hàng có được chọn không
	        String maDH = tblDonHang.getValueAt(row, 0).toString(); // Lấy mã đơn hàng từ hàng được chọn
	        dsctdh.xoaChiTietDonHang(maDH); // xóa chi tiết đơn hàng trước
	        dsdh.xoaDonHang(maDH); // Gọi phương thức xóa đơn hàng từ đối tượng DonHang_DAO
	        modelTBLDonHang.removeRow(row); // Xóa hàng được chọn từ model
	        xoaHetDuLieuTrenTableChiTietDonHang(); // Xóa dữ liệu trên bảng chi tiết đơn hàng
	        xoaTrang(); // Xóa trắng các trường dữ liệu nhập vào
	    }
	}
	
	// cập nhật lại số lượng sản phẩm nếu hủy đơn hàng
	public void capNhatSoLuongSanPhamNeuHuyDonHang() {
		 int rowCount = modelTBLChiTietDonHang.getRowCount();
		    for (int i = rowCount - 1; i >= 0; i--) {
		        String maSP=modelTBLChiTietDonHang.getValueAt(i, 0).toString(); // lấy mã của từng sản phẩm trong bảng chi tiết đơn hàng
		        int soLuong=Integer.parseInt(modelTBLChiTietDonHang.getValueAt(i, 7).toString()); // lấy số lượng sản phẩm đó trong đơn hàng
		        dssp.capNhatSoLuongSanPhamNeuHuyDonHang(maSP, soLuong); // cập nhật lại số lượng sản phẩm 
		    }
	}
}
