/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.awt.Image;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class AdminManager extends javax.swing.JFrame {

    private String header[] = {"Student ID", "Fullname", "Email", "Phone Number", "Sex", "Address"};
    private DefaultTableModel tblModel = new DefaultTableModel(header, 0);
    List<Student> liststu = new ArrayList<>();

    /**
     * Creates new form AdminManager
     */
    public AdminManager() {
        initComponents();
        setLocationRelativeTo(null);
        LoadData();
        rdoMale.setSelected(true);
    }

    public void LoadData() {
        liststu.clear();
        System.out.println("----------------------");
        Connection conn = null;
        try {
            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            String sql = "select * from ListStudent";
            // Tạo đối tượng thực thi câu lệnh Select
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            Vector data = null;
            tblModel.setRowCount(0);

            // Thực thi
            // Nếu sách không tồn tại
            while (rs.next()) {

                data = new Vector();
                data.add(rs.getString("studentid"));
                data.add(rs.getString("fullname"));
                data.add(rs.getString("email"));
                data.add(rs.getString("phonenum"));
                data.add(rs.getString("sex"));
                data.add(rs.getString("address"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);

                tblList.setModel(tblModel);

                Student list = new Student();

                list.setStudentID(rs.getString("studentid"));
                list.setFullname(rs.getString("fullname"));
                list.setEmail(rs.getString("email"));
                list.setPhonenum(rs.getString("phonenum"));
                list.setSex(rs.getString("sex"));
                list.setAdd(rs.getString("address"));
                list.setImagepath(rs.getString("imgpath"));
                liststu.add(list);
            }
            conn.close();
            st.close();
            rs.close();
            System.out.println(liststu.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JFileChooser img = new JFileChooser();

    String IU = "";

    void Resize(String pathImg) {
        try {
            ImageIcon imageIcon = new ImageIcon(pathImg);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            lblImg.setIcon(new ImageIcon(newimg));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    void Load() {
        img.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = img.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File imcpick = img.getSelectedFile();
            String pathImg = imcpick.getAbsolutePath();
            Resize(pathImg);
            IU = pathImg;
        }
    }

    void Remove() {
        lblImg.setIcon(null);
        IU = null;
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setRowCount(0);
        for (Student em : liststu) {
            model.addRow(new Object[]{em.getStudentID(), em.getFullname(), em.getEmail(), em.getPhonenum(), em.getSex(), em.getAdd()});
        }
    }

    void ClickTable() {
        int j = tblList.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();

        txtStudentID.setText(model.getValueAt(j, 0).toString());
        txtFullname.setText(model.getValueAt(j, 1).toString());
        txtEmail.setText(model.getValueAt(j, 2).toString());
        txtPhoneNum.setText(model.getValueAt(j, 3).toString());
        if (model.getValueAt(j, 4).toString().equals("Male")) {
            rdoMale.setSelected(true);
        } else {
            rdoFemale.setSelected(true);
        }
        txaAdd.setText(model.getValueAt(j, 5).toString());
        lblImg.setIcon(new ImageIcon(((new ImageIcon(liststu.get(j).getImagepath()).getImage()).
                getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), java.awt.Image.SCALE_SMOOTH))));
    }

    void New() {
        txtStudentID.setText("");
        txtFullname.setText("");
        txtEmail.setText("");
        txtPhoneNum.setText("");
        rdoMale.setSelected(true);
        txaAdd.setText("");
        lblImg.setIcon(null);
    }

    void AddStudent() {
        Connection conn = null;
        String sql1 = "INSERT INTO ListStudent (studentid, fullname, email, phonenum, sex, address, imgpath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            String stuid = txtStudentID.getText();
            String fullname = txtFullname.getText();
            String email = txtEmail.getText();
            String phone = txtPhoneNum.getText();
            String sex = "";
            if (rdoMale.isSelected()) {
                sex = "Male";
            } else {
                sex = "Female";
            }
            String add = txaAdd.getText();

            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            // Kiểm tra trước khi thêm
            java.sql.Statement st = conn.createStatement();
            String sql = "select * from ListStudent";
            ResultSet rs = st.executeQuery(sql);

            // Trong khi chưa hết dữ liệu
            boolean check = true;
            while (rs.next()) {
                if (rs.getString("studentid").equals(txtStudentID.getText())) {
                    System.out.println("Username đã tồn tại");
                    check = false;
                    return;
                }
            }
            // Câu lệnh xem dữ liệu

            if (check) {

                // Tạo đối tượng thực thi câu lệnh Select
                PreparedStatement ps = conn.prepareStatement(sql1);
                ps.setString(1, stuid);
                ps.setString(2, fullname);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.setString(5, sex);
                ps.setString(6, add);
                ps.setString(7, IU);
                System.out.println("Thêm thành công");

                int row = ps.executeUpdate();
                if (row != 0) {
                    LoadData();
                    fillTable();
                    IU = null;
                } else {
                    return;
                }
            }

            // Thực thi
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void DeleteStudent() {
        Connection conn = null;
        try {
            String stuid = txtStudentID.getText();

            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            // Kiểm tra trước khi thêm
            java.sql.Statement st = conn.createStatement();
            String sql = "select * from ListStudent";
            ResultSet rs = st.executeQuery(sql);

            // Trong khi chưa hết dữ liệu
            while (rs.next()) {
                if (rs.getString("studentid").equals(txtStudentID.getText())) {
                    CallableStatement st1 = conn.prepareCall("{CALLL SP_DELETE('" + stuid + "')}");

                    st1.executeUpdate();
                    LoadData();
                    fillTable();
                    IU = null;
                }
            }
            

            // Thực thi
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPhoneNum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAdd = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblImg = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 153));
        jLabel1.setText("Admin Management");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Student ID");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));
        jPanel1.add(txtStudentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 240, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Fullname");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));
        jPanel1.add(txtFullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 240, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Email");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 240, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Phone Number");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        txtPhoneNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNumActionPerformed(evt);
            }
        });
        jPanel1.add(txtPhoneNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 240, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Sex");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Address");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        txaAdd.setColumns(20);
        txaAdd.setRows(5);
        jScrollPane1.setViewportView(txaAdd);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 240, 120));

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Fullname", "Email", "Phone Number", "Sex", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblList);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 760, 290));

        lblImg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(lblImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 150, 150));

        btnNew.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/new.png")); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel1.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 70, 40));

        btnSave.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/add.png")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 300, 70, 40));

        btnDelete.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/delete.png")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, 70, 40));

        btnUpdate.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/edit.png")); // NOI18N
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 300, 80, 40));

        buttonGroup1.add(rdoMale);
        rdoMale.setText("Male");
        jPanel1.add(rdoMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, -1, -1));

        buttonGroup1.add(rdoFemale);
        rdoFemale.setText("Female");
        jPanel1.add(rdoFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/up.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, 70, 30));

        jButton2.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/remove.png")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 80, 30));

        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/bg2.png")); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 0, 820, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNumActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        AddStudent();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Load();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Remove();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        // TODO add your handling code here:
        ClickTable();
    }//GEN-LAST:event_tblListMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        New();
    }//GEN-LAST:event_btnNewActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Login lg = new Login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DeleteStudent();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImg;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTable tblList;
    private javax.swing.JTextArea txaAdd;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtPhoneNum;
    private javax.swing.JTextField txtStudentID;
    // End of variables declaration//GEN-END:variables
}
