/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NangCao;

/**
 *
 * @author jason
 */
public class Choice {
    private String question;
    private String choice;

    public Choice() {
    }

    public Choice(String question, String choice) {
        this.question = question;
        this.choice = choice;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
    
    
}
