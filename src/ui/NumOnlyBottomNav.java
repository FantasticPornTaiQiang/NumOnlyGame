package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.NumOnlyMainFrame.*;

class NumOnlyBottomNav extends JPanel {
    int advancedCount = 0;
    JRadioButton chooseModeJBtn0;
    JRadioButton chooseModeJBtn1;
    JRadioButton chooseModeJBtn2;
    JRadioButton chooseModeJBtn3;
    JRadioButton chooseModeJBtn4;


    NumOnlyBottomNav(NumOnlyCanvas numOnlyCanvas) {
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel jPanel = new JPanel();
        String[] modes = new String[]{"简单", "普通", "困难", "地狱", "弱智"};
        chooseModeJBtn1 = new JRadioButton(modes[0]);
        chooseModeJBtn2 = new JRadioButton(modes[1]);
        chooseModeJBtn3 = new JRadioButton(modes[2]);
        chooseModeJBtn4 = new JRadioButton(modes[3]);
        chooseModeJBtn0 = new JRadioButton(modes[4]);
        buttonGroup.add(chooseModeJBtn0);
        buttonGroup.add(chooseModeJBtn1);
        buttonGroup.add(chooseModeJBtn2);
        buttonGroup.add(chooseModeJBtn3);
        buttonGroup.add(chooseModeJBtn4);
        jPanel.add(chooseModeJBtn0);
        jPanel.add(chooseModeJBtn1);
        jPanel.add(chooseModeJBtn2);
        jPanel.add(chooseModeJBtn3);
        jPanel.add(chooseModeJBtn4);
        chooseModeJBtn1.setSelected(true);
        this.add(jPanel);


        JPanel tipsPanel = new JPanel();
        JButton tipsJBtn = new JButton("提示");
        tipsJBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isTip && !isAdvancedTip) {
                    numOnlyCanvas.autoDone();
                    NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg2.png");
                    NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
                    isTip = true;
                } else if (isTip && !isAdvancedTip) {
                    numOnlyCanvas.autoUndone();
                    NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg1.png");
                    NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
                    isTip = false;
                }
            }
        });
        tipsPanel.add(tipsJBtn);
        this.add(tipsPanel, BorderLayout.SOUTH);

        JPanel advancedTipsPanel = new JPanel();
        JButton advancedTipsJBtn = new JButton("高级提示");
        advancedTipsJBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                advancedCount++;
                if(advancedCount <= 3){
                    Icon icon = new ImageIcon("icon/zfb.jpg");
                    JOptionPane.showMessageDialog(null, "支付巨款以启用高级提示", "错误！", JOptionPane.WARNING_MESSAGE, icon);
                } else {
                    if(!isAdvancedTip && !isTip) {
                        numOnlyCanvas.advancedAutoDone();
                        if(!numOnlyCanvas.isGetSolution){
                            JOptionPane.showMessageDialog(null, "无解");
                            return;
                        }
                        isAdvancedTip = true;
                    } else if(isAdvancedTip && !isTip) {
                        numOnlyCanvas.autoUndone();
                        isAdvancedTip = false;
                    }


                }
            }
        });
        advancedTipsPanel.add(advancedTipsJBtn);
        this.add(advancedTipsPanel, BorderLayout.SOUTH);

        JPanel backPanel = new JPanel();
        JButton backJBtn = new JButton("清空");
        backJBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0;i<9;i++){
                    for(int j= 0;j<9;j++){
                        if(numOnlyCanvas.cells[i][j].isEnabled()) {
                            numOnlyCanvas.cells[i][j].setText("");
                            isTip = false;
                            isAdvancedTip = false;
                            NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg1.png");
                            NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
                        }

                    }
                }
            }
        });
        backPanel.add(backJBtn);
        this.add(backPanel, BorderLayout.SOUTH);
    }
}
