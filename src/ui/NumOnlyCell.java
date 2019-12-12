package ui;

import javax.swing.*;
import java.awt.*;

public class NumOnlyCell extends JButton {

    private int i, j;

    public NumOnlyCell(int i, int j){
        this.setSize(50,50);
        Font font = new Font("",2,24);
        this.setFont(font);
        this.setBackground(new Color(255,130,171));
        this.setForeground(Color.WHITE);
        this.i = i;
        this.j = j;
    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
