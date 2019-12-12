package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectNumFrame extends JDialog implements MouseListener {
    private NumOnlyCell cell;

    public void setCell(NumOnlyCell cell) {
        this.cell = cell;
    }

    public SelectNumFrame(){
        //隐藏界面上面的工具栏
        this.setUndecorated(true);
        this.setSize(150, 150);
        this.setBackground(new Color(238,99,99, 200));
        this.setLayout(null);
        addNum();
    }
    //添加数字1~9
    private void addNum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton();
                Font font = new Font("",2,20);
                btn.setFont(font);
                btn.setSize(45, 45);
                btn.setLocation(i*52,j*52);
                btn.setText(""+(j*3+i+1));
                btn.setBackground(new Color(255, 228,196));
                btn.addMouseListener(this);
                this.add(btn);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON1_MASK) != 0) {
            JButton btn = (JButton) e.getSource();
            cell.setText(btn.getText());
        }
        this.dispose();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
