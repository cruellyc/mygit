package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
 
import javax.swing.*;
 
public class AcssiMain extends JFrame {
 
    private LiftPane liftPane;
 
    private JMenuBar bar;
 
    private JMenu menu;
    private JMenu player;
    private JMenu help;
 
    private JMenuItem start;
    private JMenuItem restart;
    private JMenuItem end;
    private JMenuItem players;
    private JMenuItem helpItem;
     
     
    private JButton bStart;
    private JButton bEnd;
 
    private int frame_width = 300;
    private int frame_height = 350;
     
     
     
    public AcssiMain() {
        init();
    }
    public  void center(JFrame frame){
        //Toolkit 是当前 绘图系统集合工具包
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screen=toolkit.getScreenSize();//屏幕的宽高
        int frameWidth=frame.getWidth();
        int frameHigh=frame.getHeight();
        int x=(screen.width-frameWidth)/2;
        int y=(screen.height-frameHigh)/2;
        frame.setLocation(x, y);
    }
    private void init() {
        bar = new JMenuBar();
        menu = new JMenu("选项");
        player=new JMenu("玩家");
        help = new JMenu("help");
        start = new JMenuItem("开始");
        restart = new JMenuItem("重新开始");
        end = new JMenuItem("退出");
        players=new JMenuItem("点我");
        helpItem=new JMenuItem("help");
         
         
        setJMenuBar(bar);
        setTitle("俄罗斯方块");
        setSize(frame_width, frame_height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        center(this);
        setVisible(true);
        setResizable(false);
 
        bar.add(menu);
        bar.add(player);
        bar.add(help);
        menu.add(start);
        menu.add(restart);
        menu.add(end);
         
        player.add(players);
         
        help.add(helpItem);
        liftPane = new LiftPane();
        liftPane.setBounds(0, 0, 300, 350);
        bStart = new JButton("start");
        bEnd = new JButton("end");
        add(liftPane);
        validate();
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                liftPane.timerStart();
                new Log().writeLog("开始了游戏");
            }
        });
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                liftPane.reStart();
                new Log().writeLog("重新开始了游戏");
            }
        });
        end.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(liftPane.isOver()==false)
                    liftPane.user();
                new Log().writeLog("退出了游戏");
                System.exit(0);
            }
        });
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "up 转向 down 加速 lift 左移 right 右移！");
            }
        });
        players.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "在unix上可以运行 window 上可能有Bug！");
            }
        });
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                Log log=new Log();
                log.writeLog("退出了游戏");
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        new AcssiMain();
    }
}