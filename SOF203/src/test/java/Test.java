/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jason
 */
public class Test {
    public static void main(String[] args) {
       Test1 t = new Test1();
       t.setVisible(true);
       try {
           for (int i = 0; i <= 100; i++) {
               Thread.sleep(10);
               t.lbl.setText(Integer.toString(i) + "%");
               Test2 t2 = new Test2();
               if (i == 100) {
                   t.dispose();
                   t2.show();
               }
           }
       }catch(Exception e) {
           
       }
    }
}
