
package test;

import java.text.NumberFormat;
import java.util.Locale;
public class demoNameImage {
	public static void main(String[] args) {
		String str = "1234567.89";
        float number = Float.parseFloat(str);

        // Định dạng số tiền tệ theo Locale của Việt Nam
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);

        String formattedCurrency = currencyFormatter.format(number);
        System.out.println("Số tiền sau khi định dạng: " + formattedCurrency);
    }
}