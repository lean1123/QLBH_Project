package utils;

public class Contains {
	private static String maNV;
	private static String tenNV;
	private static String role;
	public static final boolean NAM = true;
	public static final boolean NU = false;
	public static final String NHAN_VIEN_QUAN_LY = "QL";
	public static final String NHAN_VIEN_BAN_HANG = "NV";
	public static final String KIEU_PHAN_CA_CO_DINH = "Cố Định";
	public static final String KIEU_PHAN_CA_TUY_CHINH = "Tùy Chỉnh";
	public static final int WIDTH_PANEL_CONTENT = 1320;
	public static final int HEIGHT_PANEL_CONTENT = 805;
	// Biểu thức chính quy kiểm tra địa chỉ (dường dẫn)
	public static final String DIA_CHI_REGEX = "^[0-9\\/]*\\s?[\\p{L}À-ỹđĐ0-9\\s,]+";

	// Biểu thức chính quy kiểm tra họ và tên
	public static final String HO_TEN_REGEX = "^(\\p{Lu}\\p{Ll}*)+(\\s\\p{Lu}\\p{Ll}*)*$";

	// Biểu thức chính quy kiểm tra địa chỉ email
	public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

	// Biểu thức chính quy kiểm tra số điện thoại
	public static final String SDT_REGEX = "^[0-9]{1,10}$";

	public static String getTenNV() {
		return tenNV;
	}
	public static void setTenNV(String tenNV) {
		Contains.tenNV = tenNV;
	}
	

	public static String getMaNV() {
		return maNV;
	}
	public static void setMaNV(String maNV) {
		Contains.maNV = maNV;
	}
	public static String getRole() {
		return role;
	}
	public static void setRole(String role) {
		Contains.role = role;
	}

	private static String pathOfReportFiles = "ReportFiles\\";

	public static String getPathOfReportFiles() {
		return pathOfReportFiles;
	}

	public static void setPathOfReportFiles(String pathOfReportFiles) {
		Contains.pathOfReportFiles = pathOfReportFiles;
	}


}
