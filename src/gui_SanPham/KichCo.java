package gui_SanPham;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.SanPham_DAO;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class KichCo extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnQuanLyKichCo;
	private JLabel lblTieuDe,lblTenKichCo,lblDanhSachKichCo;
//	private JButton btnXoa,btnXoaTrang;
	private JButton btnXoaTrang,btnLuu,btnThem;
	private JTextField txtTenKichCo;
	private JTable tblKichCo;
	private DefaultTableModel modelTBLKichCo;
	private JScrollPane scrPanel;
	
	private SanPham_DAO dssp;
	/**
	 * Create the panel.
	 */
	public KichCo() {
		// kết nối
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dssp=new SanPham_DAO();
		
		// tạo GUI
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, 1332, 797);
		
		lblTieuDe = new JLabel("QUẢN LÝ KÍCH CỠ");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(Color.WHITE);
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.BLUE);
		lblTieuDe.setBounds(468, 10, 338, 44);
		add(lblTieuDe);
		
		pnQuanLyKichCo = new JPanel();
		pnQuanLyKichCo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnQuanLyKichCo.setBackground(new Color(255, 255, 255));
		pnQuanLyKichCo.setBounds(269, 82, 696, 137);
		add(pnQuanLyKichCo);
		pnQuanLyKichCo.setLayout(null);

		lblTenKichCo = new JLabel("Tên kích cỡ ");
		lblTenKichCo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTenKichCo.setBounds(210, 20, 117, 24);
		pnQuanLyKichCo.add(lblTenKichCo);
		
		txtTenKichCo = new JTextField();
		txtTenKichCo.setColumns(10);
		txtTenKichCo.setBounds(321, 19, 196, 31);
		pnQuanLyKichCo.add(txtTenKichCo);
		
		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(445, 78, 123, 31);
		pnQuanLyKichCo.add(btnXoaTrang);
		
		btnLuu = new JButton("Sửa");
		btnLuu.setBounds(312, 78, 123, 31);
		pnQuanLyKichCo.add(btnLuu);
		
		lblDanhSachKichCo = new JLabel("Danh sách kích cỡ");
		lblDanhSachKichCo.setOpaque(true);
		lblDanhSachKichCo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachKichCo.setForeground(new Color(0, 0, 0));
		lblDanhSachKichCo.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblDanhSachKichCo.setBackground(Color.WHITE);
		lblDanhSachKichCo.setBounds(468, 334, 338, 44);
		add(lblDanhSachKichCo);
		
		String columns[] = { "Tên kích cỡ" };
		modelTBLKichCo = new DefaultTableModel(columns, 0);

		scrPanel = new JScrollPane();

		tblKichCo = new JTable(modelTBLKichCo);
		scrPanel = new JScrollPane(tblKichCo, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPanel.setViewportView(tblKichCo);
		scrPanel.setBounds(106, 388, 1075, 292);
		add(scrPanel);
		
		int fontSize = 16;
		Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
		tblKichCo.setFont(newFont);
		
		btnThem = new JButton("Thêm");
		btnThem.setBounds(179, 78, 123, 31);
		pnQuanLyKichCo.add(btnThem);
		
		utils.Format.setButtonEvent(btnLuu,btnXoaTrang,btnThem);
		
		btnXoaTrang.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		tblKichCo.addMouseListener(this);
		
		docDuLieuTuDatabase();
	}
	
	// đọc dữ liệu từ database sau đó add vào table
	public void docDuLieuTuDatabase() {
		for(String kichCo : dssp.getDanhSachKichCo()) {
			Object[] row= {kichCo};			
			modelTBLKichCo.addRow(row);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnXoaTrang)) {
			xoaTrang();
		}else if(o.equals(btnThem)) {
			if(btnThem.getText().trim().equals("Hủy")) {
				btnLuu.setText("Sửa");
				btnThem.setText("Thêm");
				xoaTrang();
				return;
			}
			else {
				btnLuu.setText("Lưu");
				btnThem.setText("Hủy");
			}
		} else if (o.equals(btnLuu)) {
			if(btnLuu.getText().toString().equals("Lưu")) {
	            luuAction(); // Thực hiện lưu mới
	            btnLuu.setText("Sửa");
				btnThem.setText("Thêm");
			}else {
				capNhatDuLieu(); // Nếu có, thực hiện cập nhật dữ liệu 
			}	
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int row= tblKichCo.getSelectedRow();
		txtTenKichCo.setText(tblKichCo.getValueAt(row, 0).toString());
		btnThem.setEnabled(false);
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
	
	// kiểm tra dữ liệu nhập vào textField
	private boolean kiemTraDuLieu() {
		String tenKC = txtTenKichCo.getText().trim();
		
		//kiểm tra để trống
		if (tenKC.length() == 0){
			JOptionPane.showMessageDialog(null, "Tên không được bỏ trống");
			txtTenKichCo.requestFocus();
			return false;
		// kiểm tra nhập vào xem có phải chữ cái viết hoa hoặc số không	
		} else if (!(tenKC.length() > 0 && tenKC.matches("^[A-Z0-9]+$"))) {
			
			JOptionPane.showMessageDialog(null, "Tên kích cỡ phải là chữ viết hoa hết hoặc số ");
			txtTenKichCo.requestFocus();
			return false;
		}
		return true;
	}
	
	// xóa trắng và bỏ chọn hàng trên table
	private void xoaTrang() {
		txtTenKichCo.setText("");
		txtTenKichCo.requestFocus();
		tblKichCo.clearSelection();
		btnThem.setEnabled(true);
	}
	
	// lưu dữ liệu mới
	private void luuAction() {
	    String tenKC = txtTenKichCo.getText().trim().toString();
	    if (kiemTraDuLieu()) {  // kiểm tra dữ liệu đúng không 
	        if(dssp.themKichCo(tenKC)) {  // đúng thì thêm mới
	    	    String ketQua = dssp.layDuLieuCotKichCo();
	                Object[] row = { ketQua };
	                modelTBLKichCo.addRow(row);// lấy tên dữ liệu mới thêm vào và add vào table
	                xoaTrang();
	                JOptionPane.showMessageDialog(null, "Thêm thành công");
	        }
	        else {
				JOptionPane.showMessageDialog(null, "Tên kích cỡ bị trùng");
			}
	    }
	    
	 }
	// cập nhật dữ liệu
	private void capNhatDuLieu() {
	    String tenKCCu = tblKichCo.getValueAt(tblKichCo.getSelectedRow(),0).toString(); // lấy từ table
	    String tenKCMoi = txtTenKichCo.getText().trim().toString(); // lấy từ textField
	    if (kiemTraDuLieu()) {  // kiểm tra dữ liệu đúng không 
	        if (dssp.capNhatKichCo(tenKCCu,tenKCMoi)) { // thực hiện cập nhật bằng hàm cập nhật
	            int row = tblKichCo.getSelectedRow();
	            modelTBLKichCo.setValueAt(tenKCMoi, row, 0); // sửa lại dữ liệu hiển thị trên table
	            xoaTrang();
	            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
	        }else {
				JOptionPane.showMessageDialog(null, "Tên kích cỡ bị trùng");
			}
	    }
	}
}
