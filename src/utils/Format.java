package utils;

import java.awt.Color;
import java.awt.Font;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;

public class Format {
	/*
	 * function setButtonEvent được sử dụng để thay đổi màu và kích thước chữ của
	 * button xử lý chức năng
	 *
	 *  @param button biến đối số không xác định(varargs)
	 *
	 */
	public static void setButtonEvent(JButton... button) {
		for (JButton jButton : button) {
			jButton.setBackground(Color.decode("#FF008A"));
			jButton.setFont(new Font("", Font.BOLD,14));
			jButton.setForeground(Color.white);
			jButton.setBorder(null);
		}
	}

	/*
	 * function formatDate được sử dụng để định dạng thời gian kiểu giờ:phút
	 *
	 * @param time là đối tượng cần đổi thành chuỗi string
	 * @return trả về 1 đối tượng String đã qua xử lý thành giờ:phút
	 */
	public static String formatDate(Time time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String str = format.format(time);
		return str;
	}

	/*
	 * function formatAmout được sử dụng để định dạng tiền tệ trong ứng dụng
	 *
	 * @param amount số tiền
	 * @return trả về 1 đối tượng String đã qua xử lý
	 */
	public static String formatAmout(float amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
	}
}
