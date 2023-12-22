package gui_CaLam;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dao.CaLam_DAO;
import entity.CaLam;

public class CapNhatCaLam extends JPanel implements ActionListener , MouseListener{

	private static final long serialVersionUID = 1L;
	private JLabel lblTitle,lblMaCa,lblGioBatDau,lblGioKetThuc,lblTitleBangCaLam;
	private JTextField txtMaCa;
	private JPanel pnCapNhatCalam;
	private JComboBox cmbGioBatDau,cmbGioKetThuc;
	private JButton btnXoaTrang,btnThem;
	private JTable tblCaLam;
	private DefaultTableModel modelTBLCaLam;
	private JScrollPane scPanel;
	private List<CaLam> listCaLam;
	private CaLam_DAO caLam_DAO;
	/**
	 * Create the panel.
	 */
	public CapNhatCaLam() {
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, utils.Contains.WIDTH_PANEL_CONTENT, utils.Contains.HEIGHT_PANEL_CONTENT);

		lblTitle = new JLabel("CẬP NHẬT CA LÀM");
		lblTitle.setForeground(new Color(0, 0, 255));
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(566, 37, 250, 36);
		add(lblTitle);

		pnCapNhatCalam = new JPanel();
		pnCapNhatCalam.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnCapNhatCalam.setBackground(new Color(255, 255, 255));
		pnCapNhatCalam.setBounds(214, 105, 891, 164);
		add(pnCapNhatCalam);
		pnCapNhatCalam.setLayout(null);

		lblMaCa = new JLabel("Mã ca");
		lblMaCa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMaCa.setBounds(42, 46, 66, 30);
		pnCapNhatCalam.add(lblMaCa);

		txtMaCa = new JTextField();
		txtMaCa.setBounds(101, 49, 113, 30);
		pnCapNhatCalam.add(txtMaCa);
		txtMaCa.setColumns(10);
		txtMaCa.setMargin(new Insets(0, 10, 0, 0));
		txtMaCa.setEnabled(false);

		lblGioBatDau = new JLabel("Giờ bắt đầu");
		lblGioBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGioBatDau.setBounds(347, 46, 100, 30);
		pnCapNhatCalam.add(lblGioBatDau);

		cmbGioBatDau = new JComboBox();
		cmbGioBatDau.setBounds(452, 49, 106, 28);
		pnCapNhatCalam.add(cmbGioBatDau);

		lblGioKetThuc = new JLabel("Giờ kết thúc");
		lblGioKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGioKetThuc.setBounds(633, 46, 106, 30);
		pnCapNhatCalam.add(lblGioKetThuc);

		cmbGioKetThuc = new JComboBox();
		cmbGioKetThuc.setBounds(745, 48, 106, 28);
		pnCapNhatCalam.add(cmbGioKetThuc);


		btnThem = new JButton("Thêm");
		btnThem.setBounds(569, 111, 130, 33);
		pnCapNhatCalam.add(btnThem);


		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBounds(415, 111, 130, 33);
		pnCapNhatCalam.add(btnXoaTrang);

		String columns[] = { "Mã ca làm", "Giờ bắt đầu", "Giờ kết thức"};
		modelTBLCaLam = new DefaultTableModel(columns, 0);

		scPanel = new JScrollPane();

		tblCaLam = new JTable(modelTBLCaLam);
		scPanel = new JScrollPane(tblCaLam, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPanel.setViewportView(tblCaLam);
		scPanel.setBounds(148, 427, 1053, 292);
		add(scPanel);

		lblTitleBangCaLam = new JLabel("Danh sách ca làm");
		lblTitleBangCaLam.setForeground(new Color(0, 0, 0));
		lblTitleBangCaLam.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitleBangCaLam.setBounds(566, 387, 250, 36);
		add(lblTitleBangCaLam);

		utils.Format.setButtonEvent(btnThem,btnXoaTrang);
		loadCombobox();

		btnThem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		tblCaLam.addMouseListener(this);

		initial();

	}
	/**
	 * function initial là hàm khởi tạo khi giao diện được chạy hoàn thành
	 */
	private void initial() {
		listCaLam = new ArrayList<>();
		caLam_DAO = new CaLam_DAO();

		listCaLam = caLam_DAO.getAllCaLam();
		loadTableShift(listCaLam);
	}

	private void loadTableShift(List<CaLam> listCaLam2) {
		modelTBLCaLam.setRowCount(0);
		for (CaLam caLam : listCaLam2) {
			String gioBatDau = "";
			String gioKetThuc = "";

			if (caLam.getGioBatDau().getHours() < 13) {
				gioBatDau = caLam.getGioBatDau().toString().substring(0,5) +" AM";
			} else {
				gioBatDau = caLam.getGioBatDau().toString().substring(0,5) +" PM";
			}

			if (caLam.getGioKetThuc().getHours() < 13) {
				gioKetThuc = caLam.getGioKetThuc().toString().substring(0,5) +" AM";
			} else {
				gioKetThuc = caLam.getGioKetThuc().toString().substring(0,5) +" PM";
			}

			String row[] = {caLam.getMaCaLam(),gioBatDau,gioKetThuc};
			modelTBLCaLam.addRow(row);
		}
	}
	/**
	 * function loadCombobox được sử dụng để khởi tạo các giá trị mặc định
	 * và load lên giao diện người dùng
	 */
	public void loadCombobox() {
		for (int i = 1; i < 25; i++) {
			if (i < 13) {
				if(i < 10) {
					cmbGioBatDau.addItem("0"+i + ":00 AM");
					cmbGioBatDau.addItem("0"+i + ":30 AM");
					cmbGioKetThuc.addItem("0"+i + ":00 AM");
					cmbGioKetThuc.addItem("0"+i + ":30 AM");
				}else {
					cmbGioBatDau.addItem(i + ":00 AM");
					cmbGioBatDau.addItem(i + ":30 AM");
					cmbGioKetThuc.addItem(i + ":00 AM");
					cmbGioKetThuc.addItem(i + ":30 AM");
				}
			} else {
				cmbGioBatDau.addItem(i + ":00 PM");
				cmbGioBatDau.addItem(i + ":30 PM");
				cmbGioKetThuc.addItem(i + ":00 PM");
				cmbGioKetThuc.addItem(i + ":30 PM");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			//kiểm tra có trùng giờ bắt đầu và giờ kết thúc hay không
			boolean isCheck = false;
			Time gioBatDau = Time.valueOf(cmbGioBatDau.getSelectedItem().toString().substring(0, 5)+":00");
			Time gioKetThuc = Time.valueOf(cmbGioKetThuc.getSelectedItem().toString().substring(0, 5)+":00");
			for (CaLam caLam : listCaLam) {
				if(caLam.getGioBatDau().compareTo(gioBatDau) == 0 && caLam.getGioKetThuc().compareTo(gioKetThuc) == 0) {
					isCheck = true;
					break;
				}
			}
			// thêm ca làm vào database
			if(!isCheck) {
				CaLam caLam = new CaLam(null, gioBatDau, gioKetThuc);
				caLam_DAO.themCaLam(caLam);
				JOptionPane.showMessageDialog(this, "Đã thêm ca làm thành công", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				listCaLam.clear();
				listCaLam = caLam_DAO.getAllCaLam();
				loadTableShift(listCaLam);
			}else {
				JOptionPane.showMessageDialog(this, "Ca làm đã tồn tại!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}else if(o.equals(btnXoaTrang)) {
			txtMaCa.setText("");
			cmbGioBatDau.setSelectedIndex(0);
			cmbGioKetThuc.setSelectedIndex(0);
		}
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblCaLam)) {
			int row = tblCaLam.getSelectedRow();
			if(row > -1) {
				txtMaCa.setText(tblCaLam.getValueAt(row, 0).toString());
				String gioBatDau = tblCaLam.getValueAt(row, 1).toString();
				String gioKetThuc = tblCaLam.getValueAt(row, 2).toString();
				System.out.println(gioBatDau);
				cmbGioBatDau.setSelectedItem(gioBatDau);
				cmbGioKetThuc.setSelectedItem(gioKetThuc);
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
