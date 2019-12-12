package ui;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

public class NumOnlyMainFrame extends JFrame {

    public final static int EASY = 1;
    public final static int NORMAL = 2;
    public final static int HARD = 3;
    public final static int HELL = 4;
    public final static int IDIOT = 0;

    public static long usedTime = 0; //
    private NumOnlyCanvas numOnlyCanvas; // 主游戏区
    private NumOnlyBottomNav numOnlyBottomNav;
    public static Timer userTimeAction;
    private JLabel levelLabel;
    public static int mode = 1;
    public static Icon bgIcon;
    public static JLabel bgJLabel;

    public static boolean isTip = false;
    public static boolean isAdvancedTip = false;

    /*
     * 默认构造函数
     */
    public NumOnlyMainFrame() {
        // 初始化方法
        init();
        // 添加组件
        addComponent();
        // 添加主游戏区
        addCanvas();
        // 添加底部导航
        addBottomNav();

        bgIcon = new ImageIcon("icon/bg1.png");
        bgJLabel = new JLabel(bgIcon);
        this.add(bgJLabel, BorderLayout.EAST);
    }

    private void addBottomNav() {
        numOnlyBottomNav = new NumOnlyBottomNav(numOnlyCanvas);
        numOnlyBottomNav.setBorder(new TitledBorder("底部导航"));
        this.add(numOnlyBottomNav, BorderLayout.SOUTH);
    }

    /*
     * 添加主游戏区
     */
    private void addCanvas() {
        numOnlyCanvas = new NumOnlyCanvas();
        numOnlyCanvas.setBorder(new TitledBorder("小美女，小帅哥，走过路过不要错过，快来玩玩吧"));

        // 将主游戏区添加到窗体中
        this.add(numOnlyCanvas, BorderLayout.CENTER);
    }

    /*
     * 添加组件区
     */
    private void addComponent() {
        JPanel panelComponent = new JPanel();
        // 添加消息区
        addPanelMsg(panelComponent);
        // 添加时间区
        addPanelTime(panelComponent);

        // 将组件添加到窗体顶部
        this.add(panelComponent, BorderLayout.NORTH);


    }

    private void addPanelTime(JPanel panelComponent) {
        JPanel panelTime = new JPanel();
        panelTime.setBorder(new TitledBorder("时间"));
        panelTime.setLayout(new GridLayout(2, 1));

        final JLabel lbSysTime = new JLabel();
        final JLabel lbUserTime = new JLabel();

        panelTime.add(lbSysTime, BorderLayout.NORTH);
        panelTime.add(lbUserTime, BorderLayout.SOUTH);

        // 设置系统时间定时器
        Timer sysTimeAction = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                long timeMillis = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                lbSysTime.setText("    系统时间：  " + df.format(timeMillis));
            }
        });
        sysTimeAction.start();
        userTimeAction = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbUserTime.setText("    已经用了：  " + (++usedTime)+ " 秒");
            }
        });
        userTimeAction.start();

        panelComponent.add(panelTime, BorderLayout.EAST);

    }

    /*
     * 添加消息区
     */
    private void addPanelMsg(JPanel panelComponent) {
        // panelComponent.setBorder(new TitledBorder("消息区"));
        panelComponent.setLayout(new GridLayout(1, 4));
        Font font14 = new Font("", 4, 14);
        Font font28 = new Font("", Font.BOLD, 28);
        Font font20 = new Font("", Font.BOLD, 20);

        JPanel panelMsg = new JPanel();
        panelMsg.setBorder(new TitledBorder("小菜单"));

        JLabel lbPass1 = new JLabel("难度：");
        lbPass1.setFont(font28);
        lbPass1.setForeground(new Color(20,144,255));
        panelMsg.add(lbPass1);

        levelLabel = new JLabel("简单");

        levelLabel.setForeground(new Color(0,191,255));
        levelLabel.setFont(font28);
        panelMsg.add(levelLabel);


		JButton btnReLoad = new JButton("变♂身");
		btnReLoad.setFont(font28);
		btnReLoad.setForeground(new Color(20,144,255));
		// 隐藏按钮背景
		btnReLoad.setContentAreaFilled(false);
		// 取消按钮边框
		btnReLoad.setBorderPainted(false);
		btnReLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    if(numOnlyBottomNav.chooseModeJBtn0.isSelected()) {
			        mode = 0;
			        levelLabel.setText("弱智");
                } else if (numOnlyBottomNav.chooseModeJBtn1.isSelected()) {
                    mode = 1;
                    levelLabel.setText("简单");
                } else if (numOnlyBottomNav.chooseModeJBtn2.isSelected()) {
                    mode = 2;
                    levelLabel.setText("普通");
                } else if (numOnlyBottomNav.chooseModeJBtn3.isSelected()) {
                    mode = 3;
                    levelLabel.setText("困难");
                } else if (numOnlyBottomNav.chooseModeJBtn4.isSelected()) {
                    mode = 4;
                    levelLabel.setText("地狱");
                }

			    numOnlyCanvas.reLoadCanvas();
			}
		});
		// 将按钮添加到消息区
        panelMsg.add(btnReLoad);
        panelComponent.add(panelMsg, BorderLayout.CENTER);
    }

    /*
     * 界面初始化
     */
    private void init() {
        ImageIcon image = new ImageIcon("icon/icon.png");
        this.setIconImage(image.getImage());
        // 设置窗口初始大小
        this.setSize(1020, 670);
        // 设置窗口初始位置
        this.setLocation(500, 50);
        // 设置窗口标题
        this.setTitle("数独大师彭泰强勇闯关 小游戏");
        // 设置窗体不允许改变大小
        this.setResizable(false);
        // 设置默认关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setBackground(new Color(200,100,100));
    }

}
