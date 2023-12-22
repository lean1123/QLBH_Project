package test;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.UIManager;

import gui.FrameDangNhap;

public class Test {
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Font fontLable = new  Font("Times New Roman", Font.PLAIN, 16);
					UIManager.put("Label.font", fontLable);
					FrameDangNhap frame = new FrameDangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
