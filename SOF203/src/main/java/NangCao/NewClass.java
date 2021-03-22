/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NangCao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jason
 */
public class NewClass {
    public static void main(String[] args) {
        List<Choice> ch = new ArrayList<>();
        Choice c = new Choice();
        c.setQuestion("1+1");
        c.setChoice("2");
        
        ch.add(c);
        
        Choice c1 = new Choice();
        c1.setQuestion("1+3");
        c1.setChoice("4");
        
        ch.add(c1);
        
        Choice c3 = new Choice();
        c3.setQuestion("1+4");
        c3.setChoice("6");
        
        ch.add(c3);
        
        for (Choice choice : ch) {
            System.out.println(choice.getQuestion() + " " + choice.getChoice());
            
        }
        int index = 0;
        boolean checked = false;
        for (int i = 0; i < ch.size() -1; i++) {
            index = i;
            checked = true;
            break;        
        }
        if (checked) {
            System.out.println("--------");
            System.out.println(ch.get(index).getQuestion());
            System.out.println("--------");
            if (ch.get(index).getQuestion().equalsIgnoreCase("1+4")) {
                ch.remove(index);
                System.out.println("--------");
                System.out.println(ch.get(index).getQuestion());
                System.out.println("--------");
                
            }
        }
        System.out.println("---------------");
        for (Choice choice : ch) {
            System.out.println(choice.getQuestion() + " " + choice.getChoice());
            
        }
    }
}
