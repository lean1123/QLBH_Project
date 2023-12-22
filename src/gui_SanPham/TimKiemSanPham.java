package gui_SanPham;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhaCungCap_DAO;
import dao.SanPham_DAO;
import entity.NhaCungCap;
import entity.SanPham;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class TimKiemSanPham extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnTimKiemSanPham;
	private JLabel lblTieuDe,lblMaSanPham,lblDanhMuc,lblTenSanPham,lblNhaCungCap,lblChatLieu,
	lblMauSac,lblKichCo,lblSoLuongTon,lblGiaBan,lblDanhSachSanPham,lblSoLuongBan,lblGiaNhap,lblHinhAnh;
	private JButton btnXoaTrang,btnTimKiem;
	private JTextField txtTenSanPham;
	private JComboBox cbbMaSanPham,cbbKichCo,cbbChatLieu,cbbDanhMuc,cbbMauSac,cbbGiaBan,cbbNhaCungCap,cbbGiaNhap,cbbSoLuongTon,cbbSoLuongBan;
	private static JTable tblSanPham;
	private DefaultTableModel modelTBLSanPham;
	private JScrollPane scrPanel;
	private String pathImage;
	private SanPham_DAO dssp;
	private NhaCungCap_DAO dsncc;
	
	/**
	 * Create the panel.
	 */
	public TimKiemSanPham() {
		
		// kết nối
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dssp=new SanPham_DAO();
		dsncc=new NhaCungCap_DAO();
		
		// tạo GUI
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, 1332, 797);
		
		lblTieuDe = new JLabel("TÌM KIẾM SẢN PHẨM");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);
		
		pnTimKiemSanPham = new JPanel();
		pnTimKiemSanPham.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnTimKiemSanPham.setBackground(new Color(255, 255, 255));
		pnTimKiemSanPham.setBounds(10, 64, 1298, 260);
		add(pnTimKiemSanPham);
		pnTimKiemSanPham.setLayout(null);
		
		lblMaSanPham = new JLabel("Mã sản phẩm ");
		lblMaSanPham.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaSanPham.setBounds(33, 20, 123, 24);
		pnTimKiemSanPham.add(lblMaSanPham);
		
		txtTenSanPham = new JTextField();
		txtTenSanPham.setBounds(166, 63, 190, 31);
		pnTimKiemSanPham.add(txtTenSanPham);
		txtTenSanPham.setColumns(10);
		
		lblDanhMuc = new JLabel("Danh mục");
		lblDanhMuc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDanhMuc.setBounds(382, 21, 93, 24);
		pnTimKiemSanPham.add(lblDanhMuc);
		
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBounds(699, 219, 123, 31);
		pnTimKiemSanPham.add(btnXoaTrang);
		
		lblTenSanPham = new JLabel("Tên sản phẩm ");
		lblTenSanPham.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenSanPham.setBounds(33, 64, 123, 24);
		pnTimKiemSanPham.add(lblTenSanPham);
		
		lblNhaCungCap = new JLabel("Nhà cung cấp ");
		lblNhaCungCap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNhaCungCap.setBounds(708, 106, 123, 24);
		pnTimKiemSanPham.add(lblNhaCungCap);
		
		lblChatLieu = new JLabel("Chất liệu ");
		lblChatLieu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblChatLieu.setBounds(382, 64, 83, 24);
		pnTimKiemSanPham.add(lblChatLieu);
		
		lblMauSac = new JLabel("Màu sắc");
		lblMauSac.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMauSac.setBounds(382, 112, 83, 24);
		pnTimKiemSanPham.add(lblMauSac);
		
		lblKichCo = new JLabel("Kích cỡ");
		lblKichCo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblKichCo.setBounds(708, 21, 93, 24);
		pnTimKiemSanPham.add(lblKichCo);
		
		lblSoLuongTon = new JLabel("Số lượng tồn");
		lblSoLuongTon.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSoLuongTon.setBounds(708, 64, 114, 24);
		pnTimKiemSanPham.add(lblSoLuongTon);
		
		lblGiaBan = new JLabel("Giá bán");
		lblGiaBan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGiaBan.setBounds(33, 112, 93, 24);
		pnTimKiemSanPham.add(lblGiaBan);
		
		btnTimKiem = new JButton("Tìm  kiếm");
		btnTimKiem.setBounds(851, 219, 123, 31);
		pnTimKiemSanPham.add(btnTimKiem);
		
		cbbMaSanPham = new JComboBox();
		cbbMaSanPham.setBounds(166, 19, 190, 31);
		pnTimKiemSanPham.add(cbbMaSanPham);
		
		cbbKichCo = new JComboBox();
		cbbKichCo.setBounds(825, 20, 190, 31);
		pnTimKiemSanPham.add(cbbKichCo);
		
		cbbChatLieu = new JComboBox();
		cbbChatLieu.setBounds(480, 63, 190, 31);
		pnTimKiemSanPham.add(cbbChatLieu);
		
		cbbDanhMuc = new JComboBox();
		cbbDanhMuc.setBounds(480, 20, 190, 31);
		pnTimKiemSanPham.add(cbbDanhMuc);
		
		cbbMauSac = new JComboBox();
		cbbMauSac.setBounds(480, 111, 190, 31);
		pnTimKiemSanPham.add(cbbMauSac);
		
		cbbGiaBan = new JComboBox();
		cbbGiaBan.setBounds(166, 111, 190, 31);
		pnTimKiemSanPham.add(cbbGiaBan);
		
		lblDanhSachSanPham = new JLabel("Danh sách sản phẩm");
		lblDanhSachSanPham.setOpaque(true);
		lblDanhSachSanPham.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachSanPham.setForeground(new Color(0, 0, 0));
		lblDanhSachSanPham.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachSanPham.setBackground(Color.WHITE);
		lblDanhSachSanPham.setBounds(468, 334, 338, 44);
		add(lblDanhSachSanPham);
		
		String columns[] = { "Mã sản phẩm","Tên sản phẩm","Giá bán","Số lượng bán","Danh mục","Chất liệu","Màu sắc","Kích cỡ","Số lượng tồn", "Nhà cung cấp","Gía nhập"};
		modelTBLSanPham = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblSanPham = new JTable(modelTBLSanPham);
		scrPanel = new JScrollPane(tblSanPham, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblSanPham);
		scrPanel.setBounds(10, 388, 1298, 388);
		add(scrPanel);
		
		utils.Format.setButtonEvent(btnXoaTrang,btnTimKiem);
		
		cbbNhaCungCap = new JComboBox();
		cbbNhaCungCap.setBounds(825, 105, 190, 31);
		pnTimKiemSanPham.add(cbbNhaCungCap);
		
		lblSoLuongBan = new JLabel("Số lượng bán");
		lblSoLuongBan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSoLuongBan.setBounds(33, 160, 123, 24);
		pnTimKiemSanPham.add(lblSoLuongBan);
		
		lblGiaNhap = new JLabel("Giá nhập");
		lblGiaNhap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGiaNhap.setBounds(382, 160, 93, 24);
		pnTimKiemSanPham.add(lblGiaNhap);
		
		cbbGiaNhap = new JComboBox();
		cbbGiaNhap.setBounds(480, 159, 190, 31);
		pnTimKiemSanPham.add(cbbGiaNhap);
		
		cbbSoLuongTon = new JComboBox();
		cbbSoLuongTon.setBounds(825, 63, 190, 31);
		pnTimKiemSanPham.add(cbbSoLuongTon);
		
		cbbSoLuongBan = new JComboBox();
		cbbSoLuongBan.setBounds(166, 159, 190, 31);
		pnTimKiemSanPham.add(cbbSoLuongBan);

		
		lblHinhAnh = new JLabel("");
		lblHinhAnh.setBackground(new Color(255, 255, 255));
		lblHinhAnh.setBounds(1071, 19, 185, 202);
		lblHinhAnh.setBorder(LineBorder.createBlackLineBorder());
		pnTimKiemSanPham.add(lblHinhAnh);
		
		lblGiaNhap.setVisible(true);
		cbbGiaNhap.setVisible(true);
		
//		if(!utils.Contains.getRole().equals("QL")) {
//		lblGiaNhap.setVisible(false);
//		cbbGiaNhap.setVisible(false);
//	    modelTBLSanPham.setColumnIdentifiers(Arrays.copyOf(columns, columns.length - 1));
//	 }	
		
		// nếu không phải là quản lý thì không hiển thị giá nhập
		if(!utils.Contains.getRole().equals("QL")) {
			lblGiaNhap.setVisible(false);
			cbbGiaNhap.setVisible(false);
		    modelTBLSanPham.setColumnIdentifiers(Arrays.copyOf(columns, columns.length - 1));
		 }	
		
		
		docuLieuDatabase(dssp.getDanhSachSanPham()); // đọc dữ liệu và hiển thị lên table
		updateAllCbb(); // thêm item vào combobox
		docDuLieuTuComboBox(); // đọc dữ liệu từ database và add item vào combobox
		
		//thêm sự kiện cho các nút và table
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		tblSanPham.addMouseListener(this);
	}
	
	// thêm các dữ liệu vào combobox
	private void updateAllCbb() {
		cbbMaSanPham.addItem("Tất cả");
		cbbGiaBan.addItem("Tất cả");
		cbbSoLuongBan.addItem("Tất cả");
		cbbSoLuongTon.addItem("Tất cả");
		cbbDanhMuc.addItem("Tất cả");
		cbbChatLieu.addItem("Tất cả");
		cbbMauSac.addItem("Tất cả");
		cbbGiaNhap.addItem("Tất cả");
		cbbKichCo.addItem("Tất cả");
		cbbNhaCungCap.addItem("Tất cả");
		
		cbbGiaBan.addItem("Dưới 100000 VND");
		cbbGiaBan.addItem("Dưới 200000 VND");
		cbbGiaBan.addItem("Dưới 500000 VND");
		cbbGiaBan.addItem("Trên 500000 VND");
		
		cbbGiaNhap.addItem("Dưới 100000 VND");
		cbbGiaNhap.addItem("Dưới 200000 VND");
		cbbGiaNhap.addItem("Dưới 500000 VND");
		cbbGiaNhap.addItem("Trên 500000 VND");
		
		cbbSoLuongTon.addItem("Dưới 20");
		cbbSoLuongTon.addItem("Dưới 50");
		cbbSoLuongTon.addItem("Dưới 100");
		cbbSoLuongTon.addItem("Trên 100");
		
		cbbSoLuongBan.addItem("Dưới 20");
		cbbSoLuongBan.addItem("Dưới 50");
		cbbSoLuongBan.addItem("Dưới 100");
		cbbSoLuongBan.addItem("Trên 100");
	}
	
	// xóa trắng các textField và set item đầu tiên cho combobox
	private void xoaTrang() {
		cbbMaSanPham.setSelectedIndex(0);
		cbbMaSanPham.setSelectedIndex(0);
		cbbGiaBan.setSelectedIndex(0);
		cbbSoLuongBan.setSelectedIndex(0);
		cbbSoLuongTon.setSelectedIndex(0);
		cbbDanhMuc.setSelectedIndex(0);
		cbbChatLieu.setSelectedIndex(0);
		cbbMauSac.setSelectedIndex(0);
		cbbGiaNhap.setSelectedIndex(0);
		cbbKichCo.setSelectedIndex(0);
		cbbNhaCungCap.setSelectedIndex(0);
		txtTenSanPham.setText("");
		tblSanPham.clearSelection();
		lblHinhAnh.setIcon(null);
	}
	
	// đọc dữ liệu từ database sau đó thêm vào table
	public void docuLieuDatabase(List<SanPham> ds) {
		for(SanPham sanPham : ds) {
			Object[] row= {
					sanPham.getMaSanPham(),
					sanPham.getTenSanPham(),
					utils.Format.formatAmout(sanPham.getGiaBan()),
					sanPham.getSoLuongBan(),
					sanPham.getDanhMuc(),
					sanPham.getChatLieu(),
					sanPham.getMauSac(),
					sanPham.getKichCo(),
					sanPham.getSoLuongTon(),
					sanPham.getNhaCungCap().getTenNhaCungCap(),
					utils.Format.formatAmout(sanPham.getGiaNhap()),
			};
			modelTBLSanPham.addRow(row);
		}
	}
	
	// đọc dữ liệu từ database và thêm vào combobox
	private void docDuLieuTuComboBox() {
		for(SanPham sanPham: dssp.getDanhSachSanPham()) {
			cbbMaSanPham.addItem(sanPham.getMaSanPham()+"");
		}
		for(String danhMuc : dssp.getDanhSachDanhMuc()) {
			cbbDanhMuc.addItem(danhMuc);
		}
		for(String chatLieu : dssp.getDanhSachChatLieu()) {
			cbbChatLieu.addItem(chatLieu);
		}
		for(String mauSac : dssp.getDanhSachMauSac()) {
			cbbMauSac.addItem(mauSac);
		}
		for(String kichCo: dssp.getDanhSachKichCo()) {
			cbbKichCo.addItem(kichCo);
		}
		for(NhaCungCap ncc: dsncc.getListNhaCungCap()) {
			cbbNhaCungCap.addItem(ncc.getTenNhaCungCap());
		}
	}


	//tạo sự kiện cho các button
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTimKiem)) {
			timKiem(); // gọi hàm tìm kiếm nếu nhấn tìm kiếm
		} else if(o.equals(btnXoaTrang)) {
			xoaTrang(); // gọi hàm xóa trắng nếu nhấn xóa trắng
		}
	}

	//hàm set hình ảnh cho vừa với khung ảnh
	private Image scaleImage(Image image, int w, int h) {
        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return scaled;
 }

	@Override
	// hiển thị các dữ liệu của hàng được chọn lên các combobox và textField khi nhấn vào
	public void mouseClicked(MouseEvent e) {
			int row=tblSanPham.getSelectedRow();
			
			cbbMaSanPham.setSelectedItem((String) tblSanPham.getValueAt(row, 0) + "");
			txtTenSanPham.setText((String) tblSanPham.getValueAt(row, 1).toString()+"");
			cbbDanhMuc.setSelectedItem((String) tblSanPham.getValueAt(row, 4) + "");
			cbbChatLieu.setSelectedItem((String) tblSanPham.getValueAt(row, 5) + "");
			cbbMauSac.setSelectedItem((String) tblSanPham.getValueAt(row, 6) + "");
			cbbKichCo.setSelectedItem((String) tblSanPham.getValueAt(row, 7) + "");
			cbbNhaCungCap.setSelectedItem((String) tblSanPham.getValueAt(row, 9) + "");
			
			String pathImage=dssp.getAnhSanPham(tblSanPham.getValueAt(row, 0).toString());
			 try {
			    	ImageIcon image_avt = new ImageIcon(getClass().getResource(pathImage));
					Image scaled = scaleImage(image_avt.getImage(), 185, 202);
					image_avt.setImage(scaled);
					lblHinhAnh.setIcon(image_avt);

				} catch (Exception e2) {
					// TODO: handle exception
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

	// lấy dữ liệu từ các combobox và textfield sau đó chuyển thành dữ liệu hợp lý để thực hiện truy vấn
	private void timKiem() {
		String maSanPham =cbbMaSanPham.getSelectedItem().toString()+"";
		String tenSanPham=txtTenSanPham.getText().trim().toString()+"";
		String giaBan=cbbGiaBan.getSelectedItem().toString().replaceAll("[,.]", "");
		String soLuongBan=cbbSoLuongBan.getSelectedItem().toString()+"";
		String danhMuc=cbbDanhMuc.getSelectedItem().toString()+"";
		String chatLieu=cbbChatLieu.getSelectedItem().toString()+"";
		String mauSac=cbbMauSac.getSelectedItem().toString()+"";
		String kichCo=cbbKichCo.getSelectedItem().toString()+"";
		String soLuongTon=cbbSoLuongTon.getSelectedItem().toString()+"";
		String tenNCC=cbbNhaCungCap.getSelectedItem().toString()+"";
		String giaNhap=cbbGiaNhap.getSelectedItem().toString()+"".replaceAll("[,.]", "");
		

		
		if(maSanPham.equals("Tất cả"))
			maSanPham="";
		if(danhMuc.equals("Tất cả"))
			danhMuc="";
		if(chatLieu.equals("Tất cả"))
			chatLieu="";
		if(mauSac.equals("Tất cả"))
			mauSac="";	
		if(kichCo.equals("Tất cả"))
			kichCo="";
		if(tenNCC.equals("Tất cả"))
			tenNCC="";
		
		
		if(giaBan.equals("Tất cả"))
			giaBan=" like '%'";
		else if(giaBan.equals("Dưới 100000 VND"))
			giaBan=" <= 100000";
		else if(giaBan.equals("Dưới 200000 VND"))
			giaBan=" <= 200000";
		else if(giaBan.equals("Dưới 500000 VND"))
			giaBan=" <= 500000";
		else if(giaBan.equals("Trên 500000 VND"))
			giaBan=" > 500000";
		
		if(giaNhap.equals("Tất cả"))
			giaNhap=" like '%'";
		else if(giaNhap.equals("Dưới 100000 VND"))
			giaNhap=" <= 100000";
		else if(giaNhap.equals("Dưới 200000 VND"))
			giaNhap=" <= 200000";
		else if(giaNhap.equals("Dưới 500000 VND"))
			giaNhap=" <= 500000";
		else if(giaNhap.equals("Trên 500000 VND"))
			giaNhap=" > 500000";
		
		if(soLuongTon.equals("Tất cả"))
			soLuongTon=" like '%'";
		else if(soLuongTon.equals("Dưới 20"))
			soLuongTon = " <= 20";
		else if(soLuongTon.equals("Dưới 50")) 
			soLuongTon = " <= 50";
		else if(soLuongTon.equals("Dưới 100")) 
			soLuongTon = " <= 100";
		else if(soLuongTon.equals("Trên 100"))
			soLuongTon = " > 100";
		
		if(soLuongBan.equals("Tất cả"))
			soLuongBan=" like '%'";
		else if(soLuongBan.equals("Dưới 20"))
			soLuongBan = " <= 20";
		else if(soLuongBan.equals("Dưới 50")) 
			soLuongBan = " <= 50";
		else if(soLuongBan.equals("Dưới 100")) 
			soLuongBan = " <= 100";
		else if(soLuongBan.equals("Trên 100"))
			soLuongBan = " > 100";
		
		// thực hiện tìm kiếm theo các tiêu chí được chọn
		List<SanPham> listSanPham=dssp.getlistSanPhamTheoDK(maSanPham, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, danhMuc, mauSac, chatLieu, kichCo, tenNCC);
		if(listSanPham.size()>0) { 
			xoaHetDuLieuTrenTable();
			docuLieuDatabase(listSanPham);	// nếu tìm thấy thì xóa dữ liệu trên table sau đó add các dữ liệu tìm thấy được vào table
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy");
		}	
		
	}	
	
	
	// xóa dữ liệu trên table 
	public void xoaHetDuLieuTrenTable() {
		 DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
		    int rowCount = model.getRowCount();
		    for (int i = rowCount - 1; i >= 0; i--) {
		        model.removeRow(i);
		    }

	}
	

	

}

