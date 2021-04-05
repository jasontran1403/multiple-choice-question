/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class StudentManager extends javax.swing.JFrame {
    List<Result> listrs = new ArrayList<>();
    private String header[] = {"Student ID", "Fullname", "Java", "JavaScript", "HTML/CSS", "AVERAGE"};
    private DefaultTableModel tblModel = new DefaultTableModel(header, 0);
    /**
     * Creates new form StudentManager
     */
    public StudentManager() {
        initComponents();
        setLocationRelativeTo(null);
        LoadData();
    }
    
    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setRowCount(0);
        for (Result rs : listrs) {
            model.addRow(new Object[]{rs.getStuid(), rs.getFullname(), rs.getJava(), rs.getJavascript(), rs.getHtmlcss(), rs.getAverage()});
        }
    }
    
    public void LoadData() {
        listrs.clear();
        System.out.println("----------------------");
        Connection conn = null;
        try {
            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            String sql = "select * from StudentResult";
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
                data.add(rs.getDouble("java"));
                data.add(rs.getDouble("javascript"));
                data.add(rs.getDouble("htmlcss"));
                data.add(rs.getDouble("average"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);

                tblList.setModel(tblModel);

                Result list = new Result();

                list.setStuid(rs.getString("studentid"));
                list.setFullname(rs.getString("fullname"));
                list.setJava(rs.getDouble("java"));
                list.setJavascript(rs.getDouble("javascript"));
                list.setHtmlcss(rs.getDouble("htmlcss"));
                list.setAverage(rs.getDouble("average"));

                listrs.add(list);
            }
            conn.close();
            st.close();
            rs.close();
            System.out.println(listrs.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        fillTable();
    }
    
    void AddResult() {
        Connection conn = null;
        String sql1 = "INSERT INTO StudentResult (id, studentid, fullname, java, javascript, htmlcss, average) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            String stuid = txtStudentID.getText();
            String fullname = txtFullname.getText();
            String java = txtJava.getText();
            String javascript = txtJavaScript.getText();
            String htmlcss = txtHTMLCSS.getText();
            double avg = (9.5 + 9.6 + 9.9)/3;
            System.out.println(avg);
            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            // Kiểm tra trước khi thêm
            java.sql.Statement st = conn.createStatement();
            String sql = "select * from StudentResult";
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
                ps.setString(1, null);
                ps.setString(2, stuid);
                ps.setString(3, fullname);
                ps.setString(4, java);
                ps.setString(5, javascript);
                ps.setString(6, htmlcss);
                ps.setString(7, String.valueOf(avg));

                System.out.println("Thêm thành công");

                int row = ps.executeUpdate();
                if (row != 0) {
                    LoadData();
                    fillTable();
                } else {
                    return;
                }
            }

            // Thực thi
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    void DeleteResult() {
        Connection conn = null;
        try {
            String stuid = txtStudentID.getText();

            String dbURL = "jdbc:mysql://localhost:3306/Account";
            String username = "root";
            String password = "Hai14031993";
            conn = DriverManager.getConnection(dbURL, username, password);

            // Kiểm tra trước khi thêm
            java.sql.Statement st = conn.createStatement();
            String sql = "select * from StudentResult";
            ResultSet rs = st.executeQuery(sql);

            // Trong khi chưa hết dữ liệu
            while (rs.next()) {
                if (rs.getString("studentid").equals(txtStudentID.getText())) {
                    PreparedStatement st1 = conn.prepareStatement("DELETE FROM StudentResult WHERE studentid=" + "'" + stuid + "'");

                    st1.executeUpdate();
                    LoadData();
                    fillTable();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtStuID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtJava = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtJavaScript = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHTMLCSS = new javax.swing.JTextField();
        lblAverage = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("Student Management");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 153));
        jLabel2.setText("Student ID");

        btnSearch.setBackground(new java.awt.Color(0, 255, 204));
        btnSearch.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/search.png")); // NOI18N
        btnSearch.setText("Search");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(txtStuID, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtStuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 510, 80));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(null);

        btnNew.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/new.png")); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel4.add(btnNew);
        btnNew.setBounds(20, 30, 48, 40);

        btnSave.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/add.png")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel4.add(btnSave);
        btnSave.setBounds(20, 80, 48, 40);

        btnDelete.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/delete.png")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btnDelete);
        btnDelete.setBounds(20, 130, 48, 40);

        btnUpdate.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/edit.png")); // NOI18N
        jPanel4.add(btnUpdate);
        btnUpdate.setBounds(20, 180, 48, 40);

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 110, 260));

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 153));
        jLabel3.setText("Fullname");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 153));
        jLabel4.setText("Student ID");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 153));
        jLabel5.setText("Java");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 153));
        jLabel6.setText("JavaScript");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 153));
        jLabel7.setText("HTML/CSS");

        lblAverage.setBackground(new java.awt.Color(255, 204, 204));
        lblAverage.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblAverage.setForeground(new java.awt.Color(255, 102, 0));
        lblAverage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 153, 153));
        jLabel9.setText("Average");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/first.png"))); // NOI18N

        jButton5.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/next.png")); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/previous.png")); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/last.png")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJava, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtStudentID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                        .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(39, 39, 39)
                                        .addComponent(txtJavaScript, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(40, 40, 40)
                                        .addComponent(txtHTMLCSS, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(lblAverage, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtJavaScript, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtHTMLCSS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAverage, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 580, 260));

        tblList.setBackground(new java.awt.Color(255, 255, 153));
        tblList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "S.ID", "Fullname", "Java", "JavaScript", "HTML/CSS", "AVERAGE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.setEnabled(false);
        jScrollPane1.setViewportView(tblList);
        if (tblList.getColumnModel().getColumnCount() > 0) {
            tblList.getColumnModel().getColumn(0).setResizable(false);
            tblList.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblList.getColumnModel().getColumn(1).setResizable(false);
            tblList.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblList.getColumnModel().getColumn(2).setResizable(false);
            tblList.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblList.getColumnModel().getColumn(3).setResizable(false);
            tblList.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblList.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 800, 80));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 102, 102));
        jLabel8.setText("TOP 3 HIGHEST AVERAGE SCORE");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 320, -1));

        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon("/Users/jason/Desktop/multiple-choice-question/SOF203/src/main/resources/bg.png")); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 600));

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Login lg = new Login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DeleteResult();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        AddResult();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewActionPerformed

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
            java.util.logging.Logger.getLogger(StudentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAverage;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtHTMLCSS;
    private javax.swing.JTextField txtJava;
    private javax.swing.JTextField txtJavaScript;
    private javax.swing.JTextField txtStuID;
    private javax.swing.JTextField txtStudentID;
    // End of variables declaration//GEN-END:variables
}
