package test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Scan_QR_CODE extends javax.swing.JFrame implements Runnable, ThreadFactory {

        List<ThongTinDangNhap> lstThongTinDangNhap = new ArrayList<>();
        public String str = "";
        private WebcamPanel panel = null;
        private Webcam webcam = null;

        private static final long serialVersionUID = 6441489157408381878L;
        private Executor executor = Executors.newSingleThreadExecutor(this);
        public Timer t;
        boolean check = true;
        public String taiKhoan = "";
        public String matKhau = "";

        public Scan_QR_CODE() {
                try {
                        loadToFile();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                initComponents();
                initWebcam();
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                result_field.setVisible(false);
                setLocationRelativeTo(null);
                setResizable(false);
                ActionListener a = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int value = jProgressBar1.getValue() + 1;
                                if (value == 100) {
                                        checkLogin();
                                }
                                jProgressBar1.setValue(value <= 100 ? value : 0);
                        }
                };
                t = new javax.swing.Timer(10, a);

        }
        //------------------------------------------------------------------------------------------------------------------------------------------------

        public Object readObject(String path) throws Exception {
                try (FileInputStream fin = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fin);) {
                        Object obj = ois.readObject();
                        return obj;
                }

        }

        public void writeObject(String path, Object obj) throws Exception {
                try (
                        FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                        oos.writeObject(obj);
                }
        }

        public void saveToFile() throws Exception {
                writeObject("thongTinDangNhap_LAB3.txt", lstThongTinDangNhap);
        }

        public void loadToFile() throws Exception {
                lstThongTinDangNhap = (List<ThongTinDangNhap>) readObject("thongTinDangNhap_LAB3.txt");
        }

        //------------------------------------------------------------------------------------------------------------------------------------------------
        public boolean dangNhapThanhCong = false;

        public void checkLogin() {

                for (int i = 0; i < lstThongTinDangNhap.size(); i++) {
                        if (str.equalsIgnoreCase(lstThongTinDangNhap.get(i).getIdAndPassword())) {
                                dangNhapThanhCong = true;
                                taiKhoan = lstThongTinDangNhap.get(i).getId();
                                matKhau = lstThongTinDangNhap.get(i).getPassword();
                                break;
                        }
                }

                if (dangNhapThanhCong) {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thành công !");
                        Bai2 b2 = new Bai2();
                        b2.setVisible(true);
                        NangCao_Login nc = new NangCao_Login();
                        nc.setVisible(false);
//                        Thread.currentThread().interrupted();
                        t.stop();
                        this.dispose();
                        webcam.close();
                } else {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thất bại !");
//                        Thread.currentThread().interrupted();
                        t.stop();
                        this.dispose();
                        webcam.close();
                }
        }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        result_field = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        result_field.setBorder(null);
        jPanel1.add(result_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 470, 20));

        jSeparator1.setForeground(new java.awt.Color(126, 167, 206));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 470, 10));

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 340));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 380));

        jProgressBar1.setForeground(new java.awt.Color(102, 255, 102));
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 480, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                if ("Windows".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Scan_QR_CODE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(() -> {
                        new Scan_QR_CODE().setVisible(true);
                });
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField result_field;
    // End of variables declaration//GEN-END:variables

        private void initWebcam() {
                Dimension size = WebcamResolution.QVGA.getSize();
                webcam = Webcam.getWebcams().get(0); //0 is default webcam
                webcam.setViewSize(size);

                panel = new WebcamPanel(webcam);
                panel.setPreferredSize(size);
//                panel.setFPSDisplayed(true);

                jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));

                executor.execute(this);
        }

        @Override
        public void run() {
                do {
                        webcam.open();
                        try {
                                Thread.sleep(100);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                        Result result = null;
                        BufferedImage image = null;

                        if (webcam.isOpen()) {
                                if ((image = webcam.getImage()) == null) {
                                        continue;
                                }
                        }

                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        try {
                                result = new MultiFormatReader().decode(bitmap);
                        } catch (NotFoundException e) {
                                //No result...
                        }

                        if (result != null) {
                                check = false;
                                result_field.setText(result.getText());
                                str = result.getText();
                                t.start();
                                break;
                        }
                } while (check);
                Thread.interrupted();
        }

        @Override
        public Thread newThread(Runnable r) {
                Thread t1 = new Thread(r, "My Thread");
                t1.setDaemon(true);
                t1.interrupt();
                return t1;
        }
}
