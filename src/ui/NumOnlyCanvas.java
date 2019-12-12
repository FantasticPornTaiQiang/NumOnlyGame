package ui;

import algorithm.NumOnlyGenerateAlgorithm;
import algorithm.NumOnlySolutionAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ui.NumOnlyMainFrame.*;

public class NumOnlyCanvas extends JPanel implements MouseListener {
    NumOnlyCell[][] cells;
    // 得到数独数组
    public int[][] maps;
    private SelectNumFrame selectNum;
    int solvesCount = 0;
    public String[][] originMaps;
    public boolean isGetSolution;
//    public String[][] originCells;

    /*
     * 默认构造函数
     */
    public NumOnlyCanvas() {
        NumOnlyMainFrame.usedTime = 0;
        maps = NumOnlyGenerateAlgorithm.getMap();
        // 加载数独区
        this.setLayout(null);
        cells = new NumOnlyCell[9][9];
        originMaps = new String[9][9];
//        originCells = new String[9][9];
        isTip = false;
        isAdvancedTip = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // this.remove(cells[i][j]);
                // 创建单元格
                cells[i][j] = new NumOnlyCell(i, j);
                // 设置位置
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                if (passRole(NumOnlyMainFrame.mode)) {
                    originMaps[i][j] = maps[i][j] + "";
                    cells[i][j].setText("" + maps[i][j]);
                    // 设置背景颜色
                    cells[i][j].setBackground(getColor(maps[i][j]));
                    cells[i][j].setEnabled(false);
                    cells[i][j].setForeground(Color.gray);
                } else {
                    originMaps[i][j] = "";
                    cells[i][j].addMouseListener(this);
                }
                this.add(cells[i][j]);
            }
        }
        if(!NumOnlySolutionAlgorithm.checkModeDFS(originMaps, mode)) reLoadCanvas();
//        for(int i = 0;i<9;i++){
//            for(int j= 0;j<9;j++){
//                originCells[i][j] = originMaps[i][j];
//            }
//        }
        checkFinish();
    }

    public void autoDone() {
        for(int i = 0;i<9;i++){
            for(int j= 0;j<9;j++){
                originMaps[i][j] = cells[i][j].getText().trim();
            }
        }
        for(int i = 0;i<9;i++){
            for(int j= 0;j<9;j++){
                cells[i][j].setText(maps[i][j]+"");
            }
        }
    }

    public void autoUndone() {
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                cells[i][j].setText(originMaps[i][j]);
            }
        }
    }

    private void advancedDFS(int i, int j) {
        solvesCount++;
        if (i == 8 && j == 9) {
            //成功
            isGetSolution = true;
            solvesCount++;
            return;
        }
        //已经到了列末尾了，还没到行尾，就换行
        if (j == 9) {
            i++;
            j = 0;
        }
        //如果i行j列是空格，那么才进入给空格填值的逻辑
        if (cells[i][j].getText().trim().equals("")) {
            for (int k = 1; k <= 9; k++) {
                //判断给i行j列放1-9中的任意一个数是否能满足规则
                if (check(i, j, k)) {
                    //将该值赋给该空格，然后进入下一个空格
                    cells[i][j].setText(k + "");
                    advancedDFS(i, j + 1);
                    //初始化该空格
                    if (isGetSolution)
                        return;
                    cells[i][j].setText("");
                }
            }
        } else {
            //如果该位置已经有值了，就进入下一个空格进行计算
            advancedDFS(i, j + 1);
        }
    }

    private boolean check(int row, int line, int number) {
        //判断该行该列是否有重复数字
        for (int i = 0; i < 9; i++) {
            if (cells[row][i].getText().trim().equals(number + "") || cells[i][line].getText().trim().equals(number + "") ) {
                return false;
            }
        }
        //判断小九宫格是否有重复
        int tempRow = row / 3;
        int tempLine = line / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[tempRow * 3 + i][tempLine * 3 + j].getText().trim().equals(number + "")) {
                    return false;
                }
            }
        }

        return true;
    }

    public void advancedAutoDone()
    {
        for(int i = 0;i<9;i++){
            for(int j= 0;j<9;j++){
                originMaps[i][j] = cells[i][j].getText().trim();
            }
        }
        solvesCount = 0;
        isGetSolution = false;
        //进入求解
        advancedDFS(0, 0);
    }

    /*
     * 检查是否完成
     */
    private void checkFinish() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!check(i, j)) {
                    return;
                }
            }
        }

        // 停止用户用时计时器
        NumOnlyMainFrame.userTimeAction.stop();
        // 清除所有cell监听
        clearAllListener();
        // 闯关数加一
        //TODO:通关后
        NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg4.png");
        NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
        JOptionPane.showMessageDialog(this, "恭喜你通过本关！用时："
                + NumOnlyMainFrame.usedTime + "秒\n即将进入下一关！");
        // 更新关卡提示
//        NumOnlyMainFrame.lbPass.setText("" + NumOnlyMainFrame.pass);
        // 开始新的关卡
        reLoadCanvas();
        // 打开用户用时计时器
        NumOnlyMainFrame.userTimeAction.start();
    }

    /*
     * 检查指定坐标处的单元格
     */

    private boolean check(int i, int j) {
        if (cells[i][j].getText().isEmpty()) {
            return false;
        }

        for (int k = 0; k < 9; k++) {
            if (cells[i][j].getText().trim().equals(cells[i][k].getText().trim()) && j!=k) {
                return false;
            }
            if (cells[i][j].getText().trim().equals(cells[k][j].getText().trim()) && i != k) {
                return false;
            }
            int ii = (i / 3) * 3 + k / 3;
            int jj = (j / 3) * 3 + k % 3;
            if (cells[i][j].getText().trim().equals(cells[ii][jj].getText().trim()) &&!(i == ii && j == jj)) {
                return false;
            }

        }
        return true;
    }

    /*
     * 重新加载数独区
     */
    public void reLoadCanvas() {
        NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg1.png");
        NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
        NumOnlyMainFrame.usedTime = 0;
        maps = NumOnlyGenerateAlgorithm.getMap();
        originMaps = new String[9][9];
//        originCells = new String[9][9];
        isTip = false;
        isAdvancedTip = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.remove(cells[i][j]);
                // 创建单元格
                cells[i][j] = new NumOnlyCell(i,j);
                // 设置位置
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                if (passRole(NumOnlyMainFrame.mode)) {
                    cells[i][j].setText("" + maps[i][j]);
                    originMaps[i][j] = maps[i][j] + "";
                    // 设置背景颜色
                    cells[i][j].setBackground(getColor(maps[i][j]));
                    cells[i][j].setEnabled(false);
                    cells[i][j].setForeground(Color.gray);
                } else {
                    originMaps[i][j] = "";
                    cells[i][j].addMouseListener(this);
                }
                this.add(cells[i][j]);
            }
        }
//        for(int i = 0;i<9;i++){
//            for(int j= 0;j<9;j++){
//                originCells[i][j] = originMaps[i][j];
//            }
//        }
        this.repaint();
        if(!NumOnlySolutionAlgorithm.checkModeDFS(originMaps, mode)) reLoadCanvas();
        checkFinish();
    }

    /*
     * 根据关卡随机产生该位置是否显示数字
     */
    private boolean passRole(int mode) {
        switch (mode) {
            case IDIOT:
                return Math.random() < 0.94;
            case EASY:
                return Math.random() < 0.77;
            case NORMAL:
                return Math.random() < 0.59;
            case HARD:
                return Math.random() < 0.39;
            case HELL:
                return Math.random() < 0.27;
            default:
                return Math.random() > 1;
        }
    }

    /*
     * 根据数字获得颜色
     */
    private Color getColor(int i) {
        Color color = Color.pink;
        switch (i) {
            case 1:
                color = new Color(255, 255, 204);
                break;
            case 2:
                color = new Color(204, 255, 255);
                break;
            case 3:
                color = new Color(255, 204, 204);
                break;
            case 4:
                color = new Color(255, 204, 153);
                break;
            case 5:
                color = new Color(127, 255, 212);
                break;
            case 6:
                color = new Color(204, 204, 204);
                break;
            case 7:
                color = new Color(135, 206, 250);
                break;
            case 8:
                color = new Color(255, 255, 255);
                break;
            case 9:
                color = new Color(153, 255, 153);
                break;
            default:
                break;
        }
        return color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int modes = e.getModifiers();
        String originNum = ((NumOnlyCell) e.getSource()).getText().trim();
        if ((modes & InputEvent.BUTTON3_MASK) != 0) {// 点击鼠标右键
            // 清空点击单元格上的内容
            ((NumOnlyCell) e.getSource()).setText("");
        } else if ((modes & InputEvent.BUTTON1_MASK) != 0) {// 点击鼠标左键
            // 如果选择数字窗口存在则销毁
            if (selectNum != null) {
                selectNum.dispose();
            }
            NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg1.png");
            NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
            // 新建一个选择窗口
            selectNum = new SelectNumFrame();
            // 设置成模态窗口
            selectNum.setModal(true);
            // 设置选择窗口在显示器上的位置
            selectNum.setLocation(e.getLocationOnScreen().x,
                    e.getLocationOnScreen().y);
            // 将点击的单元格传递给数字选择窗口
            selectNum.setCell((NumOnlyCell) e.getSource());
            // 显示数字选择窗口
            selectNum.setVisible(true);
            if (!check(((NumOnlyCell) e.getSource()).getI(), ((NumOnlyCell) e.getSource()).getJ())){
                NumOnlyMainFrame.bgIcon = new ImageIcon("icon/bg3.png");
                NumOnlyMainFrame.bgJLabel.setIcon(bgIcon);
                JOptionPane.showMessageDialog(this, "已冲突！");
                ((NumOnlyCell) e.getSource()).setText(originNum);
            }
        }

        checkFinish();
    }

    /*
     * 清除所有cell的点击监听
     */
    private void clearAllListener() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].removeMouseListener(this);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
