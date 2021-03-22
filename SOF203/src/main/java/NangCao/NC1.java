/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NangCao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class NC1 extends javax.swing.JFrame implements Runnable {

    private static int min = 29;
    private static int sec = 60;
    private static boolean RUN_TIMER = false;
    private static Thread runTimer;

    static List<Question> list = new ArrayList<>();
    static List<Question> list_10 = new ArrayList<>();
    static List<Integer> lstThuTuCauHoi = new ArrayList<>();
    static List<Choice> list_choice = new ArrayList();

    /**
     * Creates new form NC1
     */
    public NC1() {
        initComponents();
        setLocationRelativeTo(null);
        Run();
        First();
        fillForm();

    }

    @Override
    public void run() {
        while (RUN_TIMER) {
            try {
                sec--;
                if (sec == 0) {
                    min -= 1;
                    sec = 60;
                }

                if (min < 0) {
                    RUN_TIMER = false;
                    lblMin.setText("0");
                    lblSec.setText("0");
                    Finish();
                } else {
                    lblMin.setText(String.valueOf(min));
                    lblSec.setText(String.valueOf(sec));
                }

                Thread.sleep(1);

            } catch (Exception e) {

            }
        }
    }

    public void fillForm() {
        for (Question q : list_10) {
            txaQuestion.setText(q.getQuestion());
            chkAnswer1.setText(q.getAnswer1());
            chkAnswer2.setText(q.getAnswer2());
            chkAnswer3.setText(q.getAnswer3());
            chkAnswer4.setText(q.getAnswer4());
        }
    }

    public Object Open(String path) throws Exception {
        try (
                FileInputStream fin = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fin);) {
            Object obj = ois.readObject();
            return obj;
        }

    }

    public void Save(String path, Object obj) throws Exception {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(obj);
        }
    }

    public void Save() throws Exception {
        Save("question1.txt", list);
        JOptionPane.showMessageDialog(this, "Save completed!!");
    }

    public void Open() throws Exception {
        list = (List<Question>) Open("question1.txt");

    }

    public void Run() {
        try {
            Open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            lstThuTuCauHoi.add(i);
        }
        // xáo trộn danh sách
        Collections.shuffle(lstThuTuCauHoi);

        // lấy 10 vị trí đầu tiên  lstNangCao_CauHoi gán cho lst_10Q
        for (int i = 0; i < 3; i++) {
            Question list1 = new Question();
            list1.setQuestion(list.get(lstThuTuCauHoi.get(i)).getQuestion());
            list1.setAnswer1(list.get(lstThuTuCauHoi.get(i)).getAnswer1());
            list1.setAnswer2(list.get(lstThuTuCauHoi.get(i)).getAnswer2());
            list1.setAnswer3(list.get(lstThuTuCauHoi.get(i)).getAnswer3());
            list1.setAnswer4(list.get(lstThuTuCauHoi.get(i)).getAnswer4());
            list1.setAnswer4(list.get(lstThuTuCauHoi.get(i)).getAnswer4());
            list1.setRight(list.get(lstThuTuCauHoi.get(i)).getRight());
            list_10.add(list1);
        }
        fillForm();
    }
    
    boolean check = false;
    void Mark() {
        
        String choice = "";
        String q = "";

        if (chkAnswer1.isSelected()) {
            choice = chkAnswer1.getText();
            q = txaQuestion.getText();
            check = true;
        }
        if (chkAnswer2.isSelected()) {
            choice = chkAnswer2.getText();
            q = txaQuestion.getText();
            check = true;
        }
        if (chkAnswer3.isSelected()) {
            choice = chkAnswer3.getText();
            q = txaQuestion.getText();
            check = true;
        }
        if (chkAnswer4.isSelected()) {
            choice = chkAnswer4.getText();
            q = txaQuestion.getText();
            check = true;
        }

        if (check) {
            Choice choice1 = new Choice();

            choice1.setQuestion(q);
            choice1.setChoice(choice);
            list_choice.add(choice1);

            buttonGroup1.clearSelection();

        }
    }

    void Finish() {
        Mark();
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to finish the test?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            int score = 0;
            for (Question question : list) {
                for (Choice choice1 : list_choice) {
                    if (question.getQuestion().equalsIgnoreCase(choice1.getQuestion())) {
                        if (question.getRight().equals(choice1.getChoice())) {
                            score += 1;
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Your score is: " + score); 
        }

    }

    public void First() {
        txaQuestion.setText(list_10.get(0).getQuestion());
        chkAnswer1.setText(list_10.get(0).getAnswer1());
        chkAnswer2.setText(list_10.get(0).getAnswer2());
        chkAnswer3.setText(list_10.get(0).getAnswer3());
        chkAnswer4.setText(list_10.get(0).getAnswer4());
    }

    public void Next() {
        Mark();

        if (txaQuestion.getText().isEmpty()) {
            txaQuestion.setText(list_10.get(1).getQuestion());
            chkAnswer1.setText(list_10.get(1).getAnswer1());
            chkAnswer2.setText(list_10.get(1).getAnswer2());
            chkAnswer3.setText(list_10.get(1).getAnswer3());
            chkAnswer4.setText(list_10.get(1).getAnswer4());
        } else {
            int j = 0;
            for (int k = 0; k < list_10.size(); k++) {
                if (txaQuestion.getText().equalsIgnoreCase(list_10.get(k).getQuestion())) {
                    j = k + 1;
                    break;
                }

            }
            if (j > list_10.size() - 1) {

                j = 0;
            }
            txaQuestion.setText(list_10.get(j).getQuestion());
            chkAnswer1.setText(list_10.get(j).getAnswer1());
            chkAnswer2.setText(list_10.get(j).getAnswer2());
            chkAnswer3.setText(list_10.get(j).getAnswer3());
            chkAnswer4.setText(list_10.get(j).getAnswer4());
        }

        check = false;
        System.out.println("----------");

    }

    public void Prev() {
        if (txaQuestion.getText().isEmpty()) {
            txaQuestion.setText(list_10.get(list_10.size() - 1).getQuestion());
            chkAnswer1.setText(list_10.get(list_10.size() - 1).getAnswer1());
            chkAnswer2.setText(list_10.get(list_10.size() - 1).getAnswer2());
            chkAnswer3.setText(list_10.get(list_10.size() - 1).getAnswer3());
            chkAnswer4.setText(list_10.get(list_10.size() - 1).getAnswer4());
        } else {
            int j = 0;
            for (int k = 0; k < list_10.size(); k++) {
                if (txaQuestion.getText().equalsIgnoreCase(list_10.get(k).getQuestion())) {
                    j = k - 1;
                    break;
                }

            }
            if (txaQuestion.getText().equalsIgnoreCase(list_10.get(0).getQuestion())) {
                j = list_10.size() - 1;
            }
            txaQuestion.setText(list_10.get(j).getQuestion());
            chkAnswer1.setText(list_10.get(j).getAnswer1());
            chkAnswer2.setText(list_10.get(j).getAnswer2());
            chkAnswer3.setText(list_10.get(j).getAnswer3());
            chkAnswer4.setText(list_10.get(j).getAnswer4());
        }

        boolean check = false;
        String choice = "";
        if (chkAnswer1.isSelected()) {
            choice = chkAnswer1.getText();
            check = true;
            System.out.println(choice);
        }

        if (chkAnswer2.isSelected()) {
            choice = chkAnswer2.getText();
            check = true;
            System.out.println(choice);
        }

        if (chkAnswer3.isSelected()) {
            choice = chkAnswer3.getText();
            check = true;
            System.out.println(choice);
        }

        if (chkAnswer4.isSelected()) {
            choice = chkAnswer4.getText();
            check = true;
            System.out.println(choice);
        }
    }

    public void Last() {
        int last = list_10.size() - 1;
        txaQuestion.setText(list_10.get(last).getQuestion());
        chkAnswer1.setText(list_10.get(last).getAnswer1());
        chkAnswer2.setText(list_10.get(last).getAnswer2());
        chkAnswer3.setText(list_10.get(last).getAnswer3());
        chkAnswer4.setText(list_10.get(last).getAnswer4());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaQuestion = new javax.swing.JTextArea();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFinish = new javax.swing.JButton();
        Timer = new javax.swing.JPanel();
        lblMin = new javax.swing.JLabel();
        lblSec = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        chkAnswer1 = new javax.swing.JCheckBox();
        chkAnswer2 = new javax.swing.JCheckBox();
        chkAnswer3 = new javax.swing.JCheckBox();
        chkAnswer4 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        txaQuestion.setBackground(new java.awt.Color(204, 255, 204));
        txaQuestion.setColumns(20);
        txaQuestion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txaQuestion.setRows(5);
        jScrollPane1.setViewportView(txaQuestion);

        btnFirst.setBackground(new java.awt.Color(255, 204, 255));
        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 153, 102));
        btnFirst.setText("First");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 204, 255));
        btnNext.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 153, 102));
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(255, 204, 255));
        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 153, 102));
        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(255, 204, 255));
        btnLast.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 153, 102));
        btnLast.setText("Last");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnFinish.setBackground(new java.awt.Color(255, 204, 255));
        btnFinish.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnFinish.setForeground(new java.awt.Color(255, 153, 102));
        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishActionPerformed(evt);
            }
        });

        Timer.setBackground(new java.awt.Color(255, 153, 153));
        Timer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblMin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMin.setText("30");

        lblSec.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSec.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSec.setText("00");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Time left:");

        javax.swing.GroupLayout TimerLayout = new javax.swing.GroupLayout(Timer);
        Timer.setLayout(TimerLayout);
        TimerLayout.setHorizontalGroup(
            TimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblMin, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSec, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        TimerLayout.setVerticalGroup(
            TimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TimerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMin)
                    .addComponent(lblSec)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnStart.setBackground(new java.awt.Color(255, 255, 102));
        btnStart.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnStart.setForeground(new java.awt.Color(153, 0, 153));
        btnStart.setText("START THE TEST");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        buttonGroup1.add(chkAnswer1);
        chkAnswer1.setText("Answer 1");

        buttonGroup1.add(chkAnswer2);
        chkAnswer2.setText("Answer 2");

        buttonGroup1.add(chkAnswer3);
        chkAnswer3.setText("Answer 3");

        buttonGroup1.add(chkAnswer4);
        chkAnswer4.setText("Answer 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Timer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkAnswer3)
                    .addComponent(chkAnswer2)
                    .addComponent(chkAnswer1)
                    .addComponent(chkAnswer4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnNext)
                        .addGap(63, 63, 63)
                        .addComponent(btnPrev)
                        .addGap(62, 62, 62)
                        .addComponent(btnLast)
                        .addGap(65, 65, 65)
                        .addComponent(btnFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Timer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkAnswer1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAnswer2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAnswer3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAnswer4)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnLast)
                    .addComponent(btnFinish))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        RUN_TIMER = true;
        runTimer = new Thread(this);
        runTimer.start();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        // TODO add your handling code here:
        Finish();
    }//GEN-LAST:event_btnFinishActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        First();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        Last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        Prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        Next();
    }//GEN-LAST:event_btnNextActionPerformed

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
            java.util.logging.Logger.getLogger(NC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NC1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Timer;
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnStart;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkAnswer1;
    private javax.swing.JCheckBox chkAnswer2;
    private javax.swing.JCheckBox chkAnswer3;
    private javax.swing.JCheckBox chkAnswer4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMin;
    private javax.swing.JLabel lblSec;
    private javax.swing.JTextArea txaQuestion;
    // End of variables declaration//GEN-END:variables

}
