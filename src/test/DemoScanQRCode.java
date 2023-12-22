package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Map;



import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;


public class DemoScanQRCode extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Webcam webcam;
    private JButton btnTurnOff, btnTurnOn;
    private WebcamPanel pnWebCam;
    private Result previousResult = null;
    

    public DemoScanQRCode() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 500));

        initWebcam();
        
        pnWebCam = createWebcamPanel();
        add(pnWebCam, BorderLayout.CENTER);

        btnTurnOff = new JButton("Tắt Cam");
        btnTurnOn = new JButton("Bật Cam");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnTurnOff);
        buttonPanel.add(btnTurnOn);

        btnTurnOff.addActionListener(this);
        btnTurnOn.addActionListener(this);

        add(buttonPanel, BorderLayout.SOUTH);
        
        startQRCodeReader();
    }

    private void initWebcam() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
    }

    private WebcamPanel createWebcamPanel() {
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setFPSLimited(false);
        webcamPanel.setFPSLimit(60);

        return webcamPanel;
    }

    private void startWebcam() {
        if (webcam != null && !webcam.isOpen()) {
            try {
                webcam.open();
                pnWebCam.stop();
                pnWebCam.start();
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý ngoại lệ nếu cần
            }
        }
    }


    private void stopWebcam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
            pnWebCam.stop();
        }
    }
    
    private void startQRCodeReader() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (webcam.isOpen()) {
                        BufferedImage image = webcam.getImage();
                        Result result = readQRCode(image);

                        if (result != null) {
                            if (!result.equals(previousResult)) {
                                // Handle the QR code result (e.g., display, process, etc.)
                                System.out.println("QR Code: " + result.getText());
                                previousResult = result;
                            }
                        }
                    }

                    try {
                        Thread.sleep(100); // Adjust the delay as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Result readQRCode(BufferedImage image) {
        if (image != null) {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Reader reader = new MultiFormatReader();
                return reader.decode(bitmap);
            } catch (NotFoundException e) {
                // QR code not found in the image
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void generateQRCode(String data, String filePath, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("QR Code generated successfully at: " + filePath);
        } catch (WriterException | IOException e) {
            ((Throwable) e).printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnTurnOff)) {
            stopWebcam();
        } else if (source.equals(btnTurnOn)) {
 
            startWebcam();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame containerFrame = new JFrame("Container Frame");
            containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            containerFrame.setSize(800, 800);

            DemoScanQRCode demo = new DemoScanQRCode();
            containerFrame.getContentPane().add(demo);

            // Đảm bảo đặt trạng thái hiển thị của frame thành true
            containerFrame.setVisible(true);
        });
    	
//    	generateQRCode("0987835247", "QRCode\\qrcodeNV000000.png", 200, 200);
    }

}
