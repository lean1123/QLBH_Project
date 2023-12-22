package utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.*;
import connectDB.ConnectDB;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Function {

	/*
	 * lấy demension của màng hình
	 */
	public static Dimension getScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
	}

	/*
	 * lấy height của màng hình
	 */
	public static double getScreenHeight() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getHeight();
	}

	/*
	 * lấy width của màng hình
	 */
	public static double getScreenWidth() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getWidth();
	}

	/**
	 * Xuất dữ liệu từ DefaultTableModel ra file Excel.
	 *
	 * @param fileName   Tên file Excel đầu ra.
	 * @param sheetTitle Tiêu đề của sheet trong file Excel.
	 * @param cols       Mảng chứa tên các cột.
	 * @param data       DefaultTableModel chứa dữ liệu cần xuất.
	 * @return True nếu xuất thành công, False nếu có lỗi.
	 */

	public static boolean xuatExcel(String fileName, String sheetTitle, String cols[], DefaultTableModel data) {
		int rowCount = data.getRowCount();
		if (rowCount > 0) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetTitle);
			int colsLength = cols.length;
			int countOfCols = 0;

			// Tạo tiêu đề cho các cột
			XSSFRow headerRow = sheet.createRow(0);
			for (String col : cols) {
				headerRow.createCell(countOfCols).setCellValue(col);
				countOfCols++;
			}
			int rowNum = 1;
			for (int i = 0; i < rowCount; i++) {
				XSSFRow row = sheet.createRow(rowNum++);
				for (int j = 0; j < colsLength; j++) {
					row.createCell(j).setCellValue(data.getValueAt(i, j).toString());
				}
			}

//			 Lấy ngày hiện tại làm tên file khi xuất ra file excel File thống kê được lưu trong thư mục ReportFiles
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); // Định dạng thời gian
			String formattedDate = dateFormat.format(now);

			String filePath = Contains.getPathOfReportFiles() + File.separator + fileName + formattedDate + ".xlsx";

			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return true;
		}
		return false;
	}

	/*
	 * function scaleImage được sử dụng để chỉnh lại kích thước hình ảnh, khi show
	 * ra hình sản phẩm
	 *
	 * @param image là hỉnh ảnh được gửi đến để xử lý
	 *
	 * @param w là width muốn thay đổi
	 *
	 * @param h là height muốn thay đổi
	 *
	 * @return trả về 1 đối tượng Image với kích thức đã được thay đổi theo yêu cầu
	 */
	public static Image scaleImage(Image image, int w, int h) {
		Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return scaled;
	}

	/**
	 * function sendSMSWithTwilio được sử dụng để gửi tin nhắn đến người dùng truy
	 * cập vào website với tên tai khoản và mật khẩu được cung cấp để biết thêm
	 * thông tin https://www.twilio.com/login
	 *
	 * @param toPhone là số điện thoại khách hàng muốn gửi tin nhắn tới
	 *
	 * @param message là tin nhắn muốn gửi tới khách hàng
	 *
	 * @return 1 chuỗi tin nhắn thông báo kết quá gửi tin nhắn
	 */
	public static String sendSMSWithTwilio(String toPhone, String message) {
		final String URL = "https://api.twilio.com/2010-04-01/Accounts/AC7c3327f98229f727c2cf9ac7b6780faf/Messages.json";
		final String fromPhone = "+12562902470";
		if (toPhone.matches("^[0-9]{10}$")) {
			toPhone = toPhone.substring(1);
			try {

				URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				// Set the request method to POST
				con.setRequestMethod("POST");

				// Set the request headers
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Authorization",
						"Basic QUM3YzMzMjdmOTgyMjlmNzI3YzJjZjlhYzdiNjc4MGZhZjo5NjQwMDMyNWFmOTdlN2Q1ODNmNDMxNDk4ZGE0NTYwYg==");

				// Enable input/output streams
				con.setDoOutput(true);

				// Prepare the request data
				String data = "Body=" + message + "&To=+84" + toPhone + "&From=" + fromPhone;
				byte[] postData = data.getBytes(StandardCharsets.UTF_8);

				// Write the request data to the output stream
				try (OutputStream os = con.getOutputStream()) {
					os.write(postData);
				}

				// Get the response code
				int responseCode = con.getResponseCode();
				System.out.println("Response Code: " + responseCode);

				// TODO: Handle the response as needed (read the response body, etc.)
				if (responseCode == 201) {
					return "Gửi tin nhắn thành công";
				} else {
					return "Gửi tin nhắn thất bại, vui lòng kiểm tra đúng thông tin!";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Lỗi khi gửi dữ liệu lên twilio!";
			}
		}
		return "Số điện thoại không đúng định dạng!";

	}

	/**
	 * Xuất hóa đơn dưới dạng file PDF và hiển thị trên JasperViewer.
	 *
	 * @param maHDVar     Tên biến được sử dụng trong báo cáo Jasper để truyền mã
	 *                    hóa đơn.
	 * @param maDHO       Giá trị mã hóa đơn được truyền vào báo cáo Jasper.
	 * @param giaGiamVar  Tên biến được sử dụng trong báo cáo Jasper để truyền số
	 *                    tiền khuyến mãi.
	 * @param giaGiam     Giá trị giảm giá được truyền vào báo cáo Jasper.
	 * @param tongTienVar Tên biến được sử dụng trong báo cáo Jasper để truyền tổng
	 *                    tiền.
	 * @param tongTien    Giá trị tổng tiền được truyền vào báo cáo Jasper.
	 * @param pathFile    Đường dẫn đến file PDF được xuất ra.
	 * @param xuatHoaDon  True nếu muốn xuất hóa đơn ra file PDF, False nếu chỉ muốn
	 *                    hiển thị trên JasperViewer.
	 */

	public static void xuatHoaDonPDF(String maHDVar, Object maDHO, String giaGiamVar, Object giaGiam,
			String tongTienVar, Object tongTien, String pathFile, Boolean xuatHoaDon) {
		String src = "src\\reports\\hoaDon.jrxml";

		try {
			Hashtable<String, Object> map = new Hashtable<>();
			JasperReport report = JasperCompileManager.compileReport(src);
			map.put(maHDVar, maDHO);
			map.put(tongTienVar, tongTien);
			map.put(giaGiamVar, giaGiam);
			JasperPrint print = JasperFillManager.fillReport(report, map, ConnectDB.con);
			JasperViewer.viewReport(print, false);

			if (xuatHoaDon) {
				JasperExportManager.exportReportToPdfFile(print, pathFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Chuyển đổi đối tượng java.util.Date thành java.time.LocalDate.
	 *
	 * @param dateToConvert Đối tượng Date cần chuyển đổi.
	 * @return LocalDate tương ứng với đối tượng Date đầu vào.
	 */

	public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
