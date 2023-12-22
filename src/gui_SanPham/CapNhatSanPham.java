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

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhaCungCap_DAO;
import dao.SanPham_DAO;
import entity.NhaCungCap;
import entity.SanPham;
import utils.Format;
import utils.Function;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class CapNhatSanPham extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnQuanLySanPham;
	private JLabel lblTieuDe,lblMaSanPham,lblDanhMuc,lblTenSanPham,lblNhaCungCap,lblChatLieu,lblMauSac,
	lblKichCo,lblSoLuongTon,lblGiaBan,lblDanhSachSanPham,lblGiaNhap,lblSoLuongBan,lblHinhAnh;
	private JButton btnChonAnh,btnXoa,btnXoaTrang,btnLuu, btnThem;
	private JTextField txtTenSanPham,txtMaSanPham,txtGiaBan,txtSoLuongBan,txtGiaNhap;
	private JTable tblSanPham;
	private DefaultTableModel modelTBLSanPham;
	private JScrollPane scrPanel;
	private JComboBox cbbChatLieu,cbbDanhMuc,cbbMauSac,cbbKichCo,cbbNhaCungCap;
	private JSpinner spinnerSoLuongTon;
	private String pathImage;

	
	private SanPham_DAO dssp;

	private NhaCungCap_DAO dsncc;
	/**
	 * Create the panel.
	 */
	public CapNhatSanPham() {
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
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);
		
		lblTieuDe = new JLabel("QUẢN LÝ SẢN PHẨM");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);
		
		pnQuanLySanPham = new JPanel();
		pnQuanLySanPham.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnQuanLySanPham.setBackground(new Color(255, 255, 255));
		pnQuanLySanPham.setBounds(10, 64, 1298, 283);
		add(pnQuanLySanPham);
		pnQuanLySanPham.setLayout(null);
		
		lblMaSanPham = new JLabel("Mã sản phẩm ");
		lblMaSanPham.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaSanPham.setBounds(20, 20, 123, 24);
		pnQuanLySanPham.add(lblMaSanPham);
		
		pathImage="";
		
		txtTenSanPham = new JTextField();
		txtTenSanPham.setBounds(141, 77, 190, 31);
		pnQuanLySanPham.add(txtTenSanPham);
		txtTenSanPham.setColumns(10);
		
		lblDanhMuc = new JLabel("Danh mục");
		lblDanhMuc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDanhMuc.setBounds(413, 20, 93, 24);
		pnQuanLySanPham.add(lblDanhMuc);
		
		lblTenSanPham = new JLabel("Tên sản phẩm ");
		lblTenSanPham.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenSanPham.setBounds(20, 78, 123, 24);
		pnQuanLySanPham.add(lblTenSanPham);
		
		lblNhaCungCap = new JLabel("Nhà cung cấp ");
		lblNhaCungCap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNhaCungCap.setBounds(768, 138, 123, 24);
		pnQuanLySanPham.add(lblNhaCungCap);
		
		lblChatLieu = new JLabel("Chất liệu ");
		lblChatLieu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblChatLieu.setBounds(413, 78, 83, 24);
		pnQuanLySanPham.add(lblChatLieu);
		
		lblMauSac = new JLabel("Màu sắc");
		lblMauSac.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMauSac.setBounds(413, 138, 83, 24);
		pnQuanLySanPham.add(lblMauSac);
		
		lblKichCo = new JLabel("Kích cỡ");
		lblKichCo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblKichCo.setBounds(768, 20, 93, 24);
		pnQuanLySanPham.add(lblKichCo);
		
		lblSoLuongTon = new JLabel("Số lượng tồn");
		lblSoLuongTon.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSoLuongTon.setBounds(768, 78, 114, 24);
		pnQuanLySanPham.add(lblSoLuongTon);
		
		lblGiaBan = new JLabel("Giá bán");
		lblGiaBan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGiaBan.setBounds(20, 138, 93, 24);
		pnQuanLySanPham.add(lblGiaBan);
		
		btnChonAnh = new JButton("Chọn ảnh");
		btnChonAnh.setBounds(1130, 240, 123, 31);
		pnQuanLySanPham.add(btnChonAnh);
		
		txtMaSanPham = new JTextField();
		txtMaSanPham.setColumns(10);
		txtMaSanPham.setBounds(141, 19, 190, 31);
		pnQuanLySanPham.add(txtMaSanPham);
		
		txtGiaBan = new JTextField();
		txtGiaBan.setColumns(10);
		txtGiaBan.setBounds(141, 131, 190, 31);
		pnQuanLySanPham.add(txtGiaBan);
		
//		btnXoa = new JButton("Xóa");
//		btnXoa.setBounds(345, 242, 123, 31);
//		pnQuanLySanPham.add(btnXoa);
		
		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(717, 242, 123, 31);
		pnQuanLySanPham.add(btnXoaTrang);
		
		btnLuu = new JButton("Sửa");
		btnLuu.setBounds(572, 242, 123, 31);
		pnQuanLySanPham.add(btnLuu);
		
		
		lblDanhSachSanPham = new JLabel("Danh sách sản phẩm");
		lblDanhSachSanPham.setOpaque(true);
		lblDanhSachSanPham.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachSanPham.setForeground(new Color(0, 0, 0));
		lblDanhSachSanPham.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachSanPham.setBackground(Color.WHITE);
		lblDanhSachSanPham.setBounds(468, 357, 338, 44);
		add(lblDanhSachSanPham);
		
		String columns[] = { "Mã sản phẩm","Tên sản phẩm","Giá bán","Danh mục","Chất liệu","Màu sắc","Kích cỡ","Số lượng tồn","Số lượng bán", "Nhà cung cấp","Gía nhập"};
		modelTBLSanPham = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblSanPham = new JTable(modelTBLSanPham);
		scrPanel = new JScrollPane(tblSanPham, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblSanPham);
		scrPanel.setBounds(10, 411, 1298, 365);
		add(scrPanel);
		
		cbbDanhMuc = new JComboBox();
		cbbDanhMuc.setBounds(509, 18, 190, 32);
		pnQuanLySanPham.add(cbbDanhMuc);
		
		cbbChatLieu = new JComboBox();
		cbbChatLieu.setBounds(509, 76, 190, 32);
		pnQuanLySanPham.add(cbbChatLieu);
		
		cbbMauSac = new JComboBox();
		cbbMauSac.setBounds(509, 130, 190, 32);
		pnQuanLySanPham.add(cbbMauSac);
		
		cbbKichCo = new JComboBox();
		cbbKichCo.setBounds(884, 18, 190, 32);
		pnQuanLySanPham.add(cbbKichCo);
		
		cbbNhaCungCap = new JComboBox();
		cbbNhaCungCap.setBounds(884, 130, 190, 32);
		pnQuanLySanPham.add(cbbNhaCungCap);
		
		lblGiaNhap = new JLabel("Giá nhập");
		lblGiaNhap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGiaNhap.setBounds(413, 187, 93, 24);
		pnQuanLySanPham.add(lblGiaNhap);
		
		lblSoLuongBan = new JLabel("Số lượng bán");
		lblSoLuongBan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSoLuongBan.setBounds(20, 187, 114, 24);
		pnQuanLySanPham.add(lblSoLuongBan);
		
		txtSoLuongBan = new JTextField();
		txtSoLuongBan.setColumns(10);
		txtSoLuongBan.setBounds(141, 187, 190, 31);
		pnQuanLySanPham.add(txtSoLuongBan);
		
		txtGiaNhap = new JTextField();
		txtGiaNhap.setColumns(10);
		txtGiaNhap.setBounds(509, 187, 190, 31);
		pnQuanLySanPham.add(txtGiaNhap);
		
		utils.Format.setButtonEvent(btnXoaTrang,btnLuu,btnChonAnh);
		
		txtMaSanPham.setEditable(false);
		txtSoLuongBan.setEditable(false);
		
		lblHinhAnh = new JLabel("");
		lblHinhAnh.setBackground(new Color(255, 255, 255));
		lblHinhAnh.setBounds(1103, 10, 185, 202);
		lblHinhAnh.setBorder(LineBorder.createBlackLineBorder());
		pnQuanLySanPham.add(lblHinhAnh);
		
		spinnerSoLuongTon = new JSpinner();
		spinnerSoLuongTon.setBounds(884, 77, 190, 31);
		spinnerSoLuongTon.setModel(new SpinnerNumberModel(new Integer(0), 0, null, new Integer(1)));
		pnQuanLySanPham.add(spinnerSoLuongTon);
		
		btnThem = new JButton("Thêm");
		btnThem.setBounds(426, 242, 123, 31);
		Format.setButtonEvent(btnThem);
		pnQuanLySanPham.add(btnThem);
		
		btnThem.addActionListener(this);
		
		// nếu không phải là quản lý thì không hiển thị giá nhập
//		if(!utils.Contains.getRole().equals("QL")) {
//			lblGiaNhap.setVisible(false);
//			txtGiaNhap.setVisible(false);
//		    modelTBLSanPham.setColumnIdentifiers(Arrays.copyOf(columns, columns.length - 1));
//		 }	
		

		docDuLieuTuComboBox();
		docDuLieuDatabase();
	//	btnXoa.addActionListener(this);
		btnChonAnh.addActionListener(this);
		btnLuu.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		tblSanPham.addMouseListener(this);
	}
	
	// đọc dữ liệu từ database và thêm vào table
	public void docDuLieuDatabase() {
		DecimalFormat formatter=new DecimalFormat("###,###.###");

		for(SanPham sanPham : dssp.getDanhSachSanPham()) {
			Object[] row= {
					sanPham.getMaSanPham(),
					sanPham.getTenSanPham(),
					formatter.format(sanPham.getGiaBan()),
				//	sanPham.getGiaBan(),
					sanPham.getDanhMuc(),
					sanPham.getChatLieu(),
					sanPham.getMauSac(),
					sanPham.getKichCo(),
					sanPham.getSoLuongTon(),
					sanPham.getSoLuongBan(),
					sanPham.getNhaCungCap().getTenNhaCungCap(),
					formatter.format(sanPham.getGiaNhap()),
				//	sanPham.getGiaNhap(),
			};
			modelTBLSanPham.addRow(row);
		}
	}
	
	//đọc dữ liệu từ database và add item vào combobox
	private void docDuLieuTuComboBox() {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnXoaTrang)) {
			xoaTrang();	
//		} else if(o.equals(btnXoa)) {
//			xoaAction();
//			xoaTrang();			
		} else if(o.equals(btnThem)) {
			if(btnThem.getText().trim().equals("Hủy")) {
				btnLuu.setText("Sửa");
				btnThem.setText("Thêm");
				xoaTrang();
				return;
			}
			btnLuu.setText("Lưu");
			btnThem.setText("Hủy");
			txtMaSanPham.setText("");
		} else if(o.equals(btnLuu)) {
			if(btnLuu.getText().toString().equals("Lưu")) {
		            luu(); // Thực hiện lưu mới
		            btnLuu.setText("Sửa");
					btnThem.setText("Thêm");
			}else {
				capNhatSanPham(); // Nếu có, thực hiện cập nhật dữ liệu 
			}
		} else if(o.equals(btnChonAnh)) { // thực hiện chọn ảnh
			
			chonAnhFinal();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row=tblSanPham.getSelectedRow();
		
		String soLuongTon = tblSanPham.getValueAt(row, 7).toString(); // Lấy giá trị từ cột giá trị tồn và chuyển nó về INTERGER
		int parsedValueSoLuongTon = Integer.parseInt(soLuongTon);
		
		// cập nhật combobox và textField theo dòng đã chọn
		txtMaSanPham.setText((String) tblSanPham.getValueAt(row, 0).toString()+"");
		txtTenSanPham.setText((String) tblSanPham.getValueAt(row, 1).toString()+"");
		cbbDanhMuc.setSelectedItem((String) tblSanPham.getValueAt(row, 3) + "");
		cbbChatLieu.setSelectedItem((String) tblSanPham.getValueAt(row, 4) + "");
		cbbMauSac.setSelectedItem((String) tblSanPham.getValueAt(row, 5) + "");
		cbbKichCo.setSelectedItem((String) tblSanPham.getValueAt(row, 6) + "");
		cbbNhaCungCap.setSelectedItem((String) tblSanPham.getValueAt(row, 9) + "");
		txtGiaBan.setText((String) tblSanPham.getValueAt(	row,2).toString()+"");
		txtSoLuongBan.setText((String) tblSanPham.getValueAt(row, 8).toString()+"");
		txtGiaNhap.setText((String) tblSanPham.getValueAt(row, 10).toString()+"");
		spinnerSoLuongTon.setValue(parsedValueSoLuongTon);
		btnThem.setEnabled(false);

		String maSanPham = tblSanPham.getValueAt(row, 0).toString(); //  mã sản phẩm được lấy từ cột đầu tiên

	    // Gọi hàm DAO để lấy địa chỉ hình ảnh dựa trên mã sản phẩm
	    String pathImage = dssp.getAnhSanPham(maSanPham);
	    
	    // Xóa hình ảnh trước đó (nếu có)
	    lblHinhAnh.setIcon(null);
	    
	    // hiển thị hình ảnh theo địa chỉ tìm được và set nó phù hợp với khung hình
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
	
	//xóa trắng textField, combobox và bỏ chọn trong table
	public void xoaTrang() {
		txtMaSanPham.setText("");
		txtTenSanPham.setText("");
		txtGiaBan.setText("");
		txtGiaNhap.setText("");
		txtSoLuongBan.setText("");
		cbbChatLieu.setSelectedIndex(0);
		cbbDanhMuc.setSelectedIndex(0);
		cbbKichCo.setSelectedIndex(0);
		cbbMauSac.setSelectedIndex(0);
		cbbNhaCungCap.setSelectedIndex(0);
		spinnerSoLuongTon.setValue(0);
		lblHinhAnh.setIcon(null);
		tblSanPham.clearSelection();
		btnThem.setEnabled(true);
		
	}

	// kiểm tra đầu vào dữ liệu
//	public boolean kiemTraDuLieu() {
//		String tenSanPham= txtTenSanPham.getText().toString().trim();
//		
//		try {
//			float giaBan = Float.parseFloat(txtGiaBan.getText().toString().trim().replaceAll("[.,]", ""));
//			float giaNhap = Float.parseFloat(txtGiaNhap.getText().toString().trim().replaceAll("[.,]", ""));
//		} catch (Exception e) {
//			// TODO: handle exception
//			JOptionPane.showMessageDialog(null, "Giá bán không được để trống hoặc chứa ký tự đặc biệt");
//			return false;
//		}
//		
//		// kiểm tra có để trống không
//		if(tenSanPham.length()==0) {
//			JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống");
//			txtTenSanPham.requestFocus();
//			return false;
//		} else if(!(tenSanPham.length()>0 && tenSanPham.matches("^[\\p{L}À-ỹđĐ ]*\\b$"))) {
//			JOptionPane.showMessageDialog(null, "Tên sản phẩm phải là chữ và không chứa ký tự đặc biệt");
//			txtTenSanPham.requestFocus();
//			return false;
//		}
//		return true;
//	}
	
	public boolean kiemTraDuLieu2() {
		String tenSanPham=txtTenSanPham.getText().toString().trim();
		String giaBanStr=txtGiaBan.getText().toString().trim().replaceAll("[,.]", "");
		String giaNhapStr=txtGiaNhap.getText().toString().trim().replaceAll("[,.]", "");
		if(tenSanPham.length()==0) {
			JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống");
			txtMaSanPham.requestFocus();
			return false;
		} else if(giaBanStr.length()==0) {
			JOptionPane.showMessageDialog(null, "Giá bán không được để trống");
			txtGiaBan.requestFocus();
			return false;
		} else if(giaNhapStr.length()==0) {
			JOptionPane.showMessageDialog(null, "Giá nhập không được để trống");
			return false;
		}  else if(!(giaBanStr.length() > 0 && giaBanStr.matches("^[1-9][0-9]{4,}$"))) {
			JOptionPane.showMessageDialog(null, "Chỉ nhập số, ít nhất 5 chữ số, không được nhập số 0 ở đầu");
			txtGiaBan.requestFocus();
			return false;
		} else if(!(giaNhapStr.length() > 0 && giaNhapStr.matches("^[1-9][0-9]{4,}$"))) {
			JOptionPane.showMessageDialog(null, "Nhập ít nhất 5 chữ số, không được nhập số 0 ở đầu");
			txtGiaNhap.requestFocus();
			return false;
		} else if(!(tenSanPham.length()>0 && tenSanPham.matches("^[\\p{L}À-ỹđĐ ]*\\b$"))){
			JOptionPane.showMessageDialog(null, "Không chứa số và ký tự đặc biệt");
			return false;
		}
		
		return true;
	}

	
	public void chonAnhFinal() {
    JFileChooser chooser = new JFileChooser("src/images/"); // Không cần truyền đường dẫn mặc định nếu bạn muốn chọn từ bất kỳ đâu
    int returnValue = chooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = chooser.getSelectedFile();
        String originalPath = selectedFile.getPath(); // Lấy đường dẫn tuyệt đối của file đã chọn
        String fileName = selectedFile.getName(); // Lấy tên file

        // Kiểm tra xem file đã chọn có trong thư mục /images/products/ chưa
        File destinationFolder = new File("src/images/products/");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        File destinationFile = new File(destinationFolder, fileName);

        try {
            // Copy file đến thư mục /images/products/
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            pathImage = "/images/products/" + fileName; // Cập nhật đường dẫn cho ảnh

            
           // System.out.println(pathImage);
            // Set ảnh ra lblHinhAnh
            ImageIcon image_avt = new ImageIcon(selectedFile.getAbsolutePath()); // Sử dụng đường dẫn tuyệt đối của file đã chọn
            Image scaled = scaleImage(image_avt.getImage(), 185, 202);
            image_avt.setImage(scaled);
            lblHinhAnh.setIcon(image_avt);
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    ImageIcon image_avt = new ImageIcon(getClass().getResource(pathImage));
//    Image scaled = scaleImage(image_avt.getImage(), 185, 202);
//    image_avt.setImage(scaled);
//    lblHinhAnh.setIcon(image_avt);
    
    
}

//	public void chonAnhFinal2() {
//	    JFileChooser chooser = new JFileChooser(); // Không cần truyền đường dẫn mặc định nếu bạn muốn chọn từ bất kỳ đâu
//	    int returnValue = chooser.showOpenDialog(null);
//	    if (returnValue == JFileChooser.APPROVE_OPTION) {
//	        File selectedFile = chooser.getSelectedFile();
//	        String originalPath = selectedFile.getPath(); // Lấy đường dẫn tuyệt đối của file đã chọn
//	        String fileName = selectedFile.getName(); // Lấy tên file
//
//	        // Đường dẫn tuyệt đối tới thư mục chứa ảnh trong package
//	        String packagePath = getClass().getResource("/images/products/").getPath();
//	        
//	        File destinationFolder = new File(packagePath);
//	        if (!destinationFolder.exists()) {
//	            destinationFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
//	        }
//
//	        File destinationFile = new File(destinationFolder, fileName);
//
//	        try {
//	            // Copy file đến thư mục trong package
//	            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//	            pathImage = "/images/products/" + fileName; // Cập nhật đường dẫn cho ảnh
//	            
//	            System.out.println(pathImage);
//	            
//	            // Set ảnh ra lblHinhAnh
//	            ImageIcon image_avt = new ImageIcon(selectedFile.getAbsolutePath()); // Sử dụng đường dẫn tuyệt đối của file đã chọn
//	            Image scaled = scaleImage(image_avt.getImage(), 185, 202);
//	            image_avt.setImage(scaled);
//	            lblHinhAnh.setIcon(image_avt);
//		        
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}

	// chỉnh chiều dài chiều rộng của ảnh
	private Image scaleImage(Image image, int w, int h) {
        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return scaled;
 }

	// lưu sản phẩm mới
	public void luu() {
		if(kiemTraDuLieu2()) { // kiểm tra dữ liệu phù hợp chưa
			//lấy dữ liệu từ các textField và combobox ra ngoài theo định dạng kiểu dữ liệu tương ứng
			String tenSanPham=txtTenSanPham.getText().trim().toString();
			float giaBan=Float.parseFloat(txtGiaBan.getText().toString().trim().replaceAll("[,.]", ""));
			float giaNhap= Float.parseFloat(txtGiaNhap.getText().toString().trim().replaceAll("[,.]", ""));
			int soLuongTon = (int) spinnerSoLuongTon.getValue();
			int soLuongBan=0;
			
			String tenDM= cbbDanhMuc.getSelectedItem().toString().trim();
			String tenMS = cbbMauSac.getSelectedItem().toString().trim();
			String tenCL= cbbChatLieu.getSelectedItem().toString().trim();
			String tenKC = cbbKichCo.getSelectedItem().toString().trim();
			
			String tenNhaCungCap = cbbNhaCungCap.getSelectedItem().toString().trim();
			List<NhaCungCap> lncc = dsncc.getListNhaCungCapTheoTenNhaCungCap(tenNhaCungCap);
			NhaCungCap nhaCungCap = new NhaCungCap(lncc.get(0).getMaNhaCungCap());

			// tạo ra sản phẩm với các dữ liệu trên
			SanPham sanPham=new SanPham(tenNhaCungCap, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, pathImage, tenDM, tenMS, tenCL, tenKC, nhaCungCap);
			if(dssp.themSanPham(sanPham)) { // thêm sản phẩm vào database
				String ketQua=dssp.layDuLieuCotSanPham(); // lấy mã sản phẩm vừa mới tạo
				Object[] row= {ketQua, sanPham.getTenSanPham(),utils.Format.formatAmout(sanPham.getGiaBan()),tenDM, tenCL, tenMS, tenKC, sanPham.getSoLuongTon(), sanPham.getSoLuongBan(), tenNhaCungCap, utils.Format.formatAmout(sanPham.getGiaNhap())};
				modelTBLSanPham.addRow(row);
				pathImage="";
				System.out.println(pathImage);
			}
			modelTBLSanPham.fireTableDataChanged();
			xoaTrang();
			JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công");
		}
	}
	
	// cập nhật thông tin sản phẩm
	public void capNhatSanPham() {
		if(kiemTraDuLieu2()) {
			// lấy các thông tin trên textField và combobox
			String maSanPham=txtMaSanPham.getText().trim();	   

		    String tenSanPham=txtTenSanPham.getText().trim().toString();
			float giaBan=Float.parseFloat(txtGiaBan.getText().toString().replaceAll("[,.]", ""));
			float giaNhap= Float.parseFloat(txtGiaNhap.getText().toString().replaceAll("[,.]", "")	);
			int soLuongTon = (int) spinnerSoLuongTon.getValue();
			int soLuongBan=0;
			
			String tenDM= cbbDanhMuc.getSelectedItem().toString().trim();
			String tenMS = cbbMauSac.getSelectedItem().toString().trim();
			String tenCL= cbbChatLieu.getSelectedItem().toString().trim();
			String tenKC = cbbKichCo.getSelectedItem().toString().trim();
			String pathImageCu = dssp.getAnhSanPham(maSanPham);
			if(pathImage.equals("")) {
				pathImage=pathImageCu;
			}
			
			
			String tenNhaCungCap = cbbNhaCungCap.getSelectedItem().toString().trim();
			List<NhaCungCap> lncc = dsncc.getListNhaCungCapTheoTenNhaCungCap(tenNhaCungCap);
			NhaCungCap nhaCungCap = new NhaCungCap(lncc.get(0).getMaNhaCungCap());
			
		//	SanPham sanPham=null;

			SanPham	sanPham=new SanPham(maSanPham, tenSanPham, giaNhap, giaBan, soLuongTon, soLuongBan, pathImage, tenDM, tenMS, tenCL, tenKC, nhaCungCap);
			sanPham.setUrlAvt(pathImage);
			if(dssp.capNhatSanPham(sanPham)) { // cập nhật sản phẩm với các thông tin như trên sau đó sửa lại thông tin trên table
				int row=tblSanPham.getSelectedRow();
				modelTBLSanPham.setValueAt(sanPham.getTenSanPham(),row,1);
				modelTBLSanPham.setValueAt(utils.Format.formatAmout(sanPham.getGiaBan()),row,2);
				modelTBLSanPham.setValueAt(sanPham.getDanhMuc(),row,3);
				modelTBLSanPham.setValueAt(sanPham.getChatLieu(),row,4);
				modelTBLSanPham.setValueAt(sanPham.getMauSac(),row,5);
				modelTBLSanPham.setValueAt(sanPham.getKichCo(),row,6);
				modelTBLSanPham.setValueAt(sanPham.getSoLuongTon(),row,7);
				modelTBLSanPham.setValueAt(sanPham.getSoLuongBan(),row,8);
				modelTBLSanPham.setValueAt(tenNhaCungCap,row,9);
//				modelTBLSanPham.setValueAt(sanPham.getNhaCungCap().getTenNhaCungCap(),row,9);
				modelTBLSanPham.setValueAt(utils.Format.formatAmout(sanPham.getGiaNhap()),row,10);
				xoaTrang();
				pathImage="";
			}
			JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công");
		}
	}
}
