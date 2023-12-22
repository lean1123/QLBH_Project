package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import connectDB.ConnectDB;
import dao.TaiKhoan_DAO;
import entity.NhanVien;

public class FrameDangNhap extends JFrame implements ActionListener,MouseListener, FocusListener{
	private JPanel pnlMenu;
	private JPanel pnlInputIF;

	private JButton btnQL;
	private JButton btnNV;
	private JButton btnLogin;

	private JTextField txtTK;
	private JPasswordField jPasswordField;

	private JLabel lblQuyen;
	private String Role = "QL";
	private TaiKhoan_DAO taiKhoan_DAO;
	public static NhanVien nhanVienDangNhap;

	public FrameDangNhap() {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taiKhoan_DAO = new TaiKhoan_DAO();
		anhXa();
		setTitle("Đăng Nhập");
		setSize(1061, 589);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		// create lable
		JLabel lblIconPanelMenu = new JLabel(new ImageIcon(getClass().getResource("/images/icons/image 2.png")));
		JLabel lblIconPanelInputIF = new JLabel(new ImageIcon(getClass().getResource("/images/icons/image 3.png")));
//		lblIconPanelInputIF.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconPanelMenu.setHorizontalAlignment(SwingConstants.CENTER);

		//pnlMenu
		//config jpnl
		this.pnlMenu.setBounds(0, 0, 489, 589);
		this.pnlInputIF.setBounds(489, 0,572,589);
		this.pnlInputIF.setLayout(null);
		this.pnlMenu.setBackground(Color.white);
		this.pnlInputIF.setBackground(Color.white);
		Border bordeRight = BorderFactory.createMatteBorder(0, 0, 0,1, Color.black);

		this.pnlMenu.setBorder(bordeRight);
		//config btn
		this.btnNV.setText("Nhân Viên Bán Hàng");
		this.btnQL.setText("Nhân Viên Quản Lý");

		this.btnNV.setFont(new Font("",Font.BOLD,16));
		this.btnQL.setFont(new Font("",Font.BOLD,16));

		changeColorButton(this.btnNV,"DEFAULT");
		changeColorButton(this.btnQL,"ACTIVE");


		Dimension btnSize = new Dimension(221,48);
		this.btnNV.setMaximumSize(btnSize);
		this.btnQL.setMaximumSize(btnSize);

		this.btnNV.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.btnQL.setAlignmentX(Component.CENTER_ALIGNMENT);

		//group btn

		JPanel pnlGroupButtonLeft = new JPanel();
		pnlGroupButtonLeft.setLayout(new BoxLayout(pnlGroupButtonLeft, BoxLayout.Y_AXIS));

		pnlGroupButtonLeft.setPreferredSize(new Dimension(488,589));
		pnlGroupButtonLeft.add(Box.createRigidArea(new Dimension(0,40)));
		pnlGroupButtonLeft.add(this.btnQL);
		pnlGroupButtonLeft.add(Box.createRigidArea(new Dimension(0,20)));
		pnlGroupButtonLeft.add(this.btnNV);
		pnlGroupButtonLeft.add(Box.createRigidArea(new Dimension(0,20)));
		pnlGroupButtonLeft.setBackground(Color.white);

		//pnlInputIF

		JPanel pnlLeftTop = new JPanel();//nằm ở trên txt nhập tài khoản
		pnlLeftTop.setLayout(null);
		pnlLeftTop.setBounds(144, 60, 285,114);
		pnlLeftTop.setBackground(Color.white);

		JLabel lblMessErrTK = new JLabel("");
		JLabel lblMessErrMK = new JLabel("");
		JLabel lblDangNhap = new JLabel("Đăng Nhập");
		this.lblQuyen = new JLabel("Nhân Viên Quản Lý");

		lblMessErrTK.setForeground(Color.red);
		lblMessErrMK.setForeground(Color.red);
		lblDangNhap.setFont(new Font("",Font.CENTER_BASELINE,20));
		lblDangNhap.setBounds(120, 10, 141, 33);
		lblQuyen.setBounds(120, 43, 141, 33);
		lblMessErrTK.setBounds(141,270, 240,50);
		lblMessErrMK.setBounds(141,340, 240,50);

		lblIconPanelInputIF.setBounds(0, 0, 108,114);
		pnlLeftTop.add(lblIconPanelInputIF);
		pnlLeftTop.add(lblDangNhap);
		pnlLeftTop.add(lblQuyen);

		this.jPasswordField.setMargin(new Insets(0, 10,0, 0));
		this.txtTK.setMargin(new Insets(0, 10,0, 0));
		this.jPasswordField.setBounds(141,310, 240,50);
		this.txtTK.setBounds(141,240, 240,50);
		this.jPasswordField.setText("Nhập Mật Khẩu");
		this.txtTK.setText("Nhập Tên Tài Khoản");
		this.jPasswordField.setEchoChar((char) 0);//hiện mật khẩu

		this.jPasswordField.setForeground(Color.decode("#A8A8B7"));
		this.txtTK.setForeground(Color.decode("#A8A8B7"));
		this.jPasswordField.setFont(new Font("",Font.LAYOUT_LEFT_TO_RIGHT,16));
		this.txtTK.setFont(new Font("",Font.LAYOUT_LEFT_TO_RIGHT,16));

		//btn login
		this.btnLogin.setBounds(165, 400, 200, 41);
		this.btnLogin.setText("Đăng Nhập");
		this.btnLogin.setFont(new Font("", Font.BOLD,16));
		this.btnLogin.setForeground(Color.WHITE);
		this.btnLogin.setBackground(Color.decode("#FF008A"));
		this.btnLogin.setBorder(null);


		//add element to the pnlMenu
		this.pnlMenu.add(lblIconPanelMenu);
		this.pnlMenu.add(pnlGroupButtonLeft);
		//add element to the pnlInputIF

		this.pnlInputIF.add(pnlLeftTop);

		this.pnlInputIF.add(txtTK);
		this.pnlInputIF.add(jPasswordField);
		this.pnlInputIF.add(btnLogin);
		this.pnlInputIF.add(lblMessErrTK);
		this.pnlInputIF.add(lblMessErrMK);


		add(pnlMenu);
		add(pnlInputIF);
		

		//gán sự kiện cho btn
		this.btnNV.addActionListener(this);
		this.btnQL.addActionListener(this);
		this.btnLogin.addActionListener(this);

		//gán sự kiện cho txt
		this.txtTK.addActionListener(this);

		this.txtTK.addMouseListener(this);


		this.jPasswordField.addActionListener(this);

		this.jPasswordField.addFocusListener(this);
		this.jPasswordField.addMouseListener(this);

		this.txtTK.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(txtTK.getText().trim().equals("")) {
					lblMessErrTK.setText("Tên tài Khoản không được để trống");
					lblMessErrTK.setSize(240,50);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(txtTK.getText().trim().matches("[\\w ]{1,}") ) {
					lblMessErrTK.setText("");
					lblMessErrTK.setSize(0,0);
				}else {
					if(txtTK.getText().trim().equals("")) {
						lblMessErrTK.setText("Tên tài Khoản không được để trống");
						lblMessErrTK.setSize(240,50);
					}else {
						lblMessErrTK.setText("Tên tài Khoản không được có ký tự đặc biệt");
						lblMessErrTK.setSize(240,50);
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("đang nhập vào txtTK");
				if(txtTK.getText().trim().matches("[^\\w ]{1,}") ) {
					lblMessErrTK.setText("");
					lblMessErrTK.setSize(0,0);
				}else {
					if(txtTK.getText().trim().equals("")) {
						lblMessErrTK.setText("Tên tài Khoản không được để trống");
						lblMessErrTK.setSize(240,70);
					}else {
						lblMessErrTK.setText("Tên tài Khoản không được có ký tự đặc biệt");
						lblMessErrTK.setSize(240,70);
					}
				}

			}
		});

		this.jPasswordField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				char[] charPassword = jPasswordField.getPassword();
				String password = new String(charPassword);
				if(password.trim().equals("")) {
					lblMessErrMK.setText("Mật khẩu không được để trống");
					lblMessErrMK.setSize(240,50);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

				char[] charPassword = jPasswordField.getPassword();
				String password = new String(charPassword);
				if(password.trim().equals("")) {
					lblMessErrMK.setText("Mật khẩu không được để trống");
					lblMessErrMK.setSize(240,50);
				}else {
					lblMessErrMK.setText("");
					lblMessErrMK.setSize(0,0);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				char[] charPassword = jPasswordField.getPassword();
				String password = new String(charPassword);
				if(password.trim().equals("")) {
					lblMessErrMK.setText("Mật khẩu không được để trống");
					lblMessErrMK.setSize(240,50);
				}else {
					lblMessErrMK.setText("");
					lblMessErrMK.setSize(0,0);
				}

			}
		});

	}
	/*
	 * function changeColorButton được sử dụng thay đổi màu của button khi
	 * nhấn vào
	 * @param btn button cần thay đổi màu
	 * @string string có 2 kiểu là DEFAULT hoặc ACTIVE
	 */
	private void changeColorButton(JButton btn, String string) {
		btn.setBorder(new LineBorder(Color.decode("#919191"), 1));
		btn.setForeground(Color.black);
		if(string.equals("DEFAULT")) {
			btn.setBackground(Color.white);
		}else if(string.equals("ACTIVE")){
			btn.setBackground(Color.decode("#DEDEDE"));

		}

	}
	/*
	 * function anhXa được sử dụng để ánh xạ các biến trong class
	 */
	private void anhXa() {
		this.pnlMenu = new JPanel();
		this.pnlInputIF = new JPanel();

		this.btnNV = new JButton();
		this.btnQL = new JButton();
		this.btnLogin = new JButton();

		this.jPasswordField = new JPasswordField();
		this.txtTK = new JTextField();


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(this.btnQL)) {
			changeColorButton(btnNV,"DEFAULT");
			changeColorButton(btnQL,"ACTIVE");
			this.lblQuyen.setText("Nhân Viên Quản Lý");
			this.Role="QL";
		}else if(o.equals(this.btnNV)) {
			changeColorButton(btnNV,"ACTIVE");
			changeColorButton(btnQL,"DEFAULT");
			this.lblQuyen.setText("Nhân Viên Bán Hàng");
			this.Role="NV";
		}else if(o.equals(btnLogin)) {
			utils.Contains.setRole(Role);
			if(!Role.equals("KH")) {
				String matKhau = new String(this.jPasswordField.getPassword()).trim();
				String taiKhoan = this.txtTK.getText().trim();
				NhanVien resultLogin = taiKhoan_DAO.dangNhap("admin", "admin");
				nhanVienDangNhap = resultLogin;
				if(resultLogin != null) {
					utils.Contains.setMaNV(resultLogin.getMaNhanVien());
					utils.Contains.setTenNV(resultLogin.getTen());
					FrameTrangChu frameTrangChu = new FrameTrangChu();
					FrameDangNhap.this.dispose();
					frameTrangChu.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(FrameDangNhap.this,"Tài Khoảng hoặc mật khẩu không chính xác.", "ERROR",JOptionPane.CLOSED_OPTION);
				}
			}
		}else if(o.equals(txtTK)) {
			jPasswordField.selectAll();
			jPasswordField.requestFocus();
		}else if(o.equals(jPasswordField)) {
			btnLogin.doClick();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(txtTK)) {
			if(txtTK.getText().trim().equals("Nhập Tên Tài Khoản")) {
				txtTK.setText("");
				txtTK.setForeground(Color.black);
			}
		}else if(o.equals(jPasswordField)) {
			char[] charPassword = jPasswordField.getPassword();
			String password = new String(charPassword);

			if(password.trim().equals("Nhập Mật Khẩu")) {
				jPasswordField.setText("");
				jPasswordField.setForeground(Color.black);
				jPasswordField.setEchoChar('\u2022');//ẩn mật khẩu bằng ký tự đặc biệt
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(jPasswordField)) {
			char[] charPassword = jPasswordField.getPassword();
			String password = new String(charPassword);

			if(password.trim().equals("Nhập Mật Khẩu")) {
				jPasswordField.setText("");
				jPasswordField.setForeground(Color.black);
				jPasswordField.setEchoChar('\u2022');//ẩn mật khẩu bằng ký tự đặc biệt
			}
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	public static NhanVien getNhanVienDangNhap() {
		return nhanVienDangNhap;
	}
}
