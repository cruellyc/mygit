package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
 
public class GuessGame extends Frame{
    TextField tf1=new TextField();
    TextField tf2=new TextField();
    TextField tf3=new TextField();
    TextField tf4=new TextField();
    TextField tf5=new TextField();
    TextField tf6=new TextField();
    TextField tf7=new TextField();
    TextField tf8=new TextField();
    //以上定义8个文本显示框
    int i=1;//标识，控制点击数字显示在相应的TextField
    int f=0;//标识，控制游戏产生4个随机数
    int f1=1;//标识，控制A,B两个结果的显示
    int Enter[]=new int[4];//存储玩家输入的4个数字
    int Arr[]=new int[4];//存储游戏产生的4个随机数字
    int A;//计数，随机数字和输入数字比较，数值和位置都相同的数字个数
    int B;//计数，随机数字和输入数字比较，数值相同，位置不同的数字个数
    int times=0;
    public GuessGame(){
        setTitle("GuessGame");
        setLocation(300,300);
        setSize(300,300);
        setVisible(true);
        Panel p1=new Panel(new GridLayout(1,4,5,5));//构建一个面板，1行4列，用于放置组件TextField1-4
        Panel p2=new Panel(new GridLayout(0,1));
        Panel p3=new Panel(new GridLayout(4,3,6,6));
        Panel p4=new Panel(new GridLayout(0,1));
        Panel p5=new Panel(new GridLayout(1,0));
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);
        tf4.setEditable(false);
        tf5.setEditable(false);
        tf6.setEditable(false);
        tf7.setEditable(false);
        tf8.setEditable(false);
        tf1.setFocusable(false);
        tf2.setFocusable(false);
        tf3.setFocusable(false);
        tf4.setFocusable(false);
        tf5.setFocusable(false);
        tf6.setFocusable(false);
        tf7.setFocusable(false);
        tf8.setFocusable(false);
        //使6个TextField不能编辑
        tf1.setForeground(Color.black);
        tf2.setForeground(Color.black);
        tf3.setForeground(Color.black);
        tf4.setForeground(Color.black);
        tf5.setForeground(Color.black);
        tf6.setForeground(Color.black);
        //设置TextField字体显示的颜色
        NumberListener n1=new NumberListener();
        //
        OperatorListener o1=new OperatorListener();
        //
        Button b0=new Button("0");
        Button b1=new Button("1");
        Button b2=new Button("2");
        Button b3=new Button("3");
        Button b4=new Button("4");
        Button b5=new Button("5");
        Button b6=new Button("6");
        Button b7=new Button("7");
        Button b8=new Button("8");
        Button b9=new Button("9");
        //添加9个数字按钮
        b0.addActionListener(n1);
        b1.addActionListener(n1);
        b2.addActionListener(n1);
        b3.addActionListener(n1);
        b4.addActionListener(n1);
        b5.addActionListener(n1);
        b6.addActionListener(n1);
        b7.addActionListener(n1);
        b8.addActionListener(n1);
        b9.addActionListener(n1);
        //
        Button b10=new Button("重猜");
        Button b11=new Button("新局");
        Button b12=new Button("开始");
        //添加3个命令按钮
        b10.addActionListener(o1);
        b11.addActionListener(o1);
        b12.addActionListener(o1);
        //
        b0.setForeground(Color.black);
        b1.setForeground(Color.black);
        b2.setForeground(Color.black);
        b3.setForeground(Color.black);
        b4.setForeground(Color.black);
        b5.setForeground(Color.black);
        b6.setForeground(Color.black);
        b7.setForeground(Color.black);
        b8.setForeground(Color.black);
        b9.setForeground(Color.black);
        b10.setForeground(Color.black);
        b11.setForeground(Color.black);
        b12.setForeground(Color.black);
        //设置按钮字体的颜色
        Label l1=new Label("A",Label.CENTER);
        Label l2=new Label("B",Label.CENTER);
        Label l3=new Label("次数",Label.CENTER);
        Label l4=new Label("结果",Label.CENTER);
        l1.setForeground(Color.black);
        l2.setForeground(Color.black);
        p1.add(tf1);p1.add(tf2);p1.add(tf3);p1.add(tf4);
        //面板p1加入4个TextField，用于显示玩家输入的数字
        p2.add(b11);p2.add(l3);p2.add(tf7);
        //面板p2加入一个按钮“新局”，用来开局
        p3.add(b7);p3.add(b8);p3.add(b9);
        p3.add(b4);p3.add(b5);p3.add(b6);
        p3.add(b1);p3.add(b2);p3.add(b3);
        p3.add(b0);p3.add(b12);p3.add(b10);
        //面板p3加入12个按钮，数字0-9，开始，以及重猜
        p4.add(tf5);p4.add(l1);p4.add(tf6);p4.add(l2);
        //面板p4加入两个TextField和两个标签，显示猜的结果
        p5.add(l4);p5.add(tf8);
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.WEST);
        add(p3,BorderLayout.CENTER);
        add(p4,BorderLayout.EAST);
        add(p5,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e1){
                System.exit(0);
            }
        });
    }
    class NumberListener implements ActionListener{
        public void actionPerformed(ActionEvent e2){
            NumStore(e2);
        }
    }
    class OperatorListener implements ActionListener{
        public void actionPerformed(ActionEvent e3){
            EventClick(e3);
        }
    }
    public void NumStore(ActionEvent e2){
        //储存4个玩家输入的数字
        if(i<5){
            if(i==1){
                tf1.setText(e2.getActionCommand());
            }
            if(i==2){
                tf2.setText(e2.getActionCommand());
            }
            if(i==3){
                tf3.setText(e2.getActionCommand());
            }
            if(i==4){
                tf4.setText(e2.getActionCommand());
            }
            Enter[i-1]=Integer.parseInt(e2.getActionCommand());
            i++;
        }
    }
    public void EventClick(ActionEvent e3){
        if(e3.getActionCommand()=="新局"){
            //游戏产生4个随机数
            for(int m=0;m<4;m++){
                f=0;f1=0;
                while(f==0){
                    int n=(int)(Math.random()*10);
                    if(m==0){
                        Arr[m]=n;f=1;System.out.println(n);
                    }
                    if(m>0){
                        for(int p=0;p<m;p++){
                            if(Arr[p]==n){
                                f=0;break;
                            }
                            else f=1;
                        }
                        if(f==1){
                            Arr[m]=n;System.out.println(n);
                        }
                    }
                }
                tf1.setText("");tf2.setText("");
                tf3.setText("");tf4.setText("");
                tf7.setText("");times=0;
                i=1;A=0;B=0;
            }
        }
        if(e3.getActionCommand()=="重猜"){
            //清除4个玩家输入的数字
            tf1.setText("");tf2.setText("");
            tf3.setText("");tf4.setText("");
            i=1;A=0;B=0;f1=0;
            times++;
            tf7.setText(String.valueOf(times));
        }
        if(e3.getActionCommand()=="开始"){
            //比较玩家输入的数字与游戏产生的随机数，显示猜测的结果，几A几B
            if(f1==0&&i==5){
                for(int j=0;j<4;j++){
                    for(int k=0;k<4;k++){
                        if(Enter[j]==Arr[k]&&j==k){
                            A++;
                        }
                        else if(Enter[j]==Arr[k]&&i!=k){
                            B++;
                        }
                    }
                }
                tf5.setText(String.valueOf(A));
                tf6.setText(String.valueOf(B));
                f1=1;
                while(A==4){
                    tf8.setText(Arrays.toString(Arr));
                }
            }
        }
    }
    public static void main(String[] args){
        new GuessGame();
    }
}