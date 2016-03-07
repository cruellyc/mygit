package com.hkm.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
 
/**
 * 这个是游戏的运行窗口；
 * @author Hekangmin
 *
 */
public class TankWarClient extends Frame{
/**
 * 游戏窗口的宽度；
 */
    public static final int GAME_WIDTH=800;
     
    /**
     * 游戏窗口的高度；
     */
    public static final int GAME_HEIGHT=600;
     
    Tank MyTank=new Tank(700,400,true,Tank.Direction.STOP,this);
    List<Tank> tanks=new ArrayList<Tank>();
    List<Explode> explodes=new ArrayList<Explode>();
    List<Missile> missiles=new ArrayList<Missile>();
    Wall w1=new Wall(300,200,20,200,this);
    Wall w2=new Wall(600,300,30,150,this);
    Blood b=new Blood();
     
    /**
     * 画一张虚拟图片；
     */
    Image OffScreenImage=null;
     
    public TankWarClient(String name)//设置文字
    {
        super(name);
    }
     
    /**
     * 运行窗口；
     */
     
    public void launchFrame()
    {
        for(int i=0;i<10;i++)//添加十辆敌军坦克
        {
            tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
        }
         
        this.setBounds(200,100,GAME_WIDTH,GAME_HEIGHT);
        this.setBackground(Color.GREEN);
        this.addWindowListener(new WindowAdapter()//匿名类
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyMonitor());//加入键盘监听器；
        this.setResizable(false);//不可改变窗口的大小；
         
        this.setVisible(true);
         
        new Thread(new PaintThread()).start();//新建一个线程；
    }
     
    public void paint(Graphics g)
    {
        g.drawString("Missile count: "+missiles.size(), 10, 50);//显示字符串；
        g.drawString("Explodes count: "+explodes.size(),10,70);
        g.drawString("tanks count: "+tanks.size(),10,90);
        g.drawString("Mytank life: "+MyTank.getLife(),10,110);
        /**
         * 画出墙；
         */
        w1.draw(g);
        w2.draw(g);
         
        /**
         * 检测子弹与各类的事情；
         */
        for(int i=0;i<missiles.size();i++)
        {
            Missile m=missiles.get(i);
            m.hitsWall(w1);
            m.hitsWall(w2);
            m.hitTanks(tanks);
            m.hitTank(MyTank);
            m.draw(g);
             
            //if(!m.isLive())
                //missiles.remove(m);
            //else m.draw(g);
        }
        /**
         * 画出爆炸；
         */
        for(int i=0;i<explodes.size();i++)
        {
            Explode e=explodes.get(i);
            e.draw(g);
        }
         
        for(int i=0;i<tanks.size();i++)
        {
            Tank t=tanks.get(i);
            t.colliedsWithWall(w1);
            t.colliedsWithWall(w2);
            t.colliedsWithTanks(tanks);
            t.draw(g);
        }
         
        b.draw(g);
        MyTank.eat(b);
        MyTank.draw(g);
    }
     
    /**
     * 利用双缓冲技术消除坦克闪烁的现象；
     */
    public void update(Graphics g) //g为画在屏幕上的画笔;
    {
        if(OffScreenImage==null)
            OffScreenImage=this.createImage(GAME_WIDTH, GAME_HEIGHT);
        Graphics gOffScreen=OffScreenImage.getGraphics();//gOffScreen是OffScreenImage的画笔;
        Color c=gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);//画在虚拟图片上;
        g.drawImage(OffScreenImage,0,0,null);//用g画笔将虚拟图片上的东西画在屏幕上
         
    }
     
     
    private class PaintThread implements Runnable{
 
        public void run() {
            while(true)
            {
                repaint();//这里的repaint方法是Frame类的
                try{
                Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
     
    private class KeyMonitor extends KeyAdapter
    {
        public void keyReleased(KeyEvent e) {
            MyTank.keyReleased(e);
        }
 
        public void keyPressed(KeyEvent e) {
            MyTank.KeyPressed(e);
            }
         
         
    }
     
     
     
    public static void main(String[] args) {
        new TankWarClient("My Tank World").launchFrame();
    }
     
     
     
     
}