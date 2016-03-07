package com.hkm.TankWar;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
/**
 * 坦克类
 * @author Hekangmin
 *
 */
public class Tank {
    public static final int XSPEED=5;//坦克x方向速度
    public static final int YSPEED=5;
     
    public static final int WIDTH=30;
    public static final int HEIGHT=30;
     
    private BloodBar bb=new BloodBar();//血条
     
    private int life=100;
     
    public int getLife() {
        return life;
    }
 
    public void setLife(int life) {
        this.life = life;
    }
 
 
    private static Random r=new Random();
     
    private static int step=r.nextInt(12)+3;//定义一个数表示敌军坦克随机东的步数；
     
    private boolean bL=false,bU=false,bR=false,bD=false;
     
    enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};//利用枚举类型定义坦克方向；
    private int x,y;
     
    private int oldX,oldY;//纪录上一步坦克的位置；
     
    private boolean live=true;//判断是否活着
     
    public boolean isLive() {
        return live;
    }
 
    public void setLive(boolean live) {
        this.live = live;
    }
 
 
    private boolean good;//坦克是好是坏
     
    public boolean isGood() {
        return good;
    }
 
 
    private Direction ptDir=Direction.D;//新增炮筒的方向;
     
    TankWarClient tc;//为了持有对方的引用以可以方便访问其成员变量;
     
    Direction dir=Direction.STOP;//一开始将坦克方向设为stop；
     
     
     
    public Tank(int x,int y,boolean good,Direction dir,TankWarClient tc)
    {
        this.x=x;
        this.y=y;
        this.oldX=x;
        this.oldY=y;
        this.good=good;
        this.dir=dir;
        this.tc=tc;//持有对方的引用;
    }
     
     
    public void draw(Graphics g)
    {
        if(!live)//如果死亡则不再draw；
        {   
            if(!good)
            {
                tc.tanks.remove(this);
                if(tc.tanks.size()<5)//少于5辆坦克时添加坦克；
                {
                    for(int i=0;i<10;i++)
                    {
                        int posX=r.nextInt(800);
                        int posY=r.nextInt(600);
                        tc.tanks.add(new Tank(posX,posY,false,Direction.D,tc));//使得坦克出现的位置随机
                    }
                }
            }
             
            return;
        }
         
        Color c=g.getColor();
        if(good) 
        {
            g.setColor(Color.RED);
            bb.draw(g);
        }
        else g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
         
        switch(ptDir)//画出炮筒的方向;
        {
        case L:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-10, y+Tank.HEIGHT/2);//画出炮筒，画一条直线代替；
            break;
        case LU:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-7, y-7);
            break;
        case U:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y-10);
            break;
        case RU:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+7, y-7);
            break;
        case R:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+10, y+Tank.HEIGHT/2);
            break;
        case RD:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+7, y+Tank.HEIGHT+7);
            break;
        case D:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT+10);
            break;
        case LD:
             g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-7, y+HEIGHT+7);
            break;
        }
        move();
    }
     
     
    public void move()
    {
        oldX=x;//纪录坦克上一步的位置
        oldY=y;
         
        switch(dir)
        {
        case L:
            x-=XSPEED;
            break;
        case LU:
            x-=XSPEED;
            y-=YSPEED;
            break;
        case U:
            y-=YSPEED;
            break;
        case RU:
            x+=XSPEED;
            y-=YSPEED;
            break;
        case R:
            x+=XSPEED;
            break;
        case RD:
            x+=XSPEED;
            y+=YSPEED;
            break;
        case D:
            y+=YSPEED;
            break;
        case LD:
            x-=XSPEED;
            y+=YSPEED;
            break;
        case STOP:
            break;
        }
        if(this.dir!=Direction.STOP)
            this.ptDir=this.dir;
         
        /**
         * 防止坦克越界；
         */
        if(x<0) x=0;
        if(y<25) y=25;
        if(x+Tank.WIDTH>TankWarClient.GAME_WIDTH) x=TankWarClient.GAME_WIDTH-30;
        if(y+Tank.HEIGHT>TankWarClient.GAME_HEIGHT) y=TankWarClient.GAME_HEIGHT-30;
         
        if(!good)
        {
            Direction[] dirs=Direction.values();//将枚举类型转化成数组；
             
            if(step==0)
            {
                step=r.nextInt(12)+3;
                int rn=r.nextInt(dirs.length);//产生length以内随机的整数;
                dir=dirs[rn];
            }
            step--;
            if(r.nextInt(40)>20) this.fire();//使敌军坦克发射子弹；
        }
         
    }
     
    /**
     * 处理按键
     * @param e键盘事件；
     */
    public void KeyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();
        switch(key)
        {
         
        case KeyEvent.VK_LEFT:
            bL=true;
            break;
        case KeyEvent.VK_RIGHT:
            bR=true;
            break;
        case KeyEvent.VK_UP:
            bU=true;
             break;
        case KeyEvent.VK_DOWN:
            bD=true;
            break;
       }
        locationDir();
    }
     
     
    public void keyReleased(KeyEvent e) {
        int key=e.getKeyCode();
        switch(key)
        {
        case KeyEvent.VK_CONTROL:
            fire();
            break;
        case KeyEvent.VK_LEFT:
            bL=false;
            break;
        case KeyEvent.VK_RIGHT:
            bR=false;
            break;
        case KeyEvent.VK_UP:
            bU=false;
             break;
        case KeyEvent.VK_DOWN:
            bD=false;
            break;
        case KeyEvent.VK_A:
            superFire();
            break;
        case KeyEvent.VK_F2:
            reBorn();
            break;
       }
        locationDir();
    }
     
    /**
     * 发射子弹
     * @return返回子弹类型
     */
    public Missile fire() {
        if(!live)
            return null;
        int mx=this.x+Tank.WIDTH/2-Missile.WIDTH/2;//计算子弹发射的位置；
        int my=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
        Missile m=new Missile(mx,my,good,ptDir,this.tc);////根据炮筒方向发射子弹
        tc.missiles.add(m);
        return m;
    }
     
     
    public Missile fire(Direction dir)
    {
        if(!live)
            return null;
        int mx=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
        int my=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
        Missile m=new Missile(mx,my,good,dir,this.tc);//根据坦克的方向发射子弹；
        tc.missiles.add(m);
        return m;
    }
     
    public void superFire()
    {
        Direction[] dirs=Direction.values();
        for(int i=0;i<8;i++)
        {
            fire(dirs[i]);
        }
    }
     
     
    public void locationDir()
    {
        if(bL&&!bU&&!bR&&!bD)
            dir=Direction.L;
        else if(bL&&bU&&!bR&&!bD)
            dir=Direction.LU;
        else if(!bL&&bU&&!bR&&!bD)
            dir=Direction.U;
        else if(!bL&&bU&&bR&&!bD)
            dir=Direction.RU;
        else if(!bL&&!bU&&bR&&!bD)
            dir=Direction.R;
        else if(!bL&&!bU&&bR&&bD)
            dir=Direction.RD;
        else if(!bL&&!bU&&!bR&&bD)
            dir=Direction.D;
        else if(bL&&!bU&&!bR&&bD)
            dir=Direction.LD;
        else if(!bL&&!bU&&!bR&&!bD)
            dir=Direction.STOP;
    }
 
     
     
    public Rectangle getRect()//获取tank的矩形区域
    {
        return new Rectangle(this.x,this.y,this.WIDTH,this.HEIGHT);
    }
     
    /**
     * 坦克撞墙
     * @param w墙
     * @returntrue撞上，false未撞上；
     */
    public boolean colliedsWithWall(Wall w)
    {
        if(this.live&&this.getRect().intersects(w.getRect()))
        {
            this.stay();
            return true;
        }
        return false;
    }
     
    /**
     * 处理坦克与坦克相撞，防止其互相穿越；
     * @param tanks敌军坦克；
     * @return true撞上，false未撞上；
     */
    public boolean colliedsWithTanks(java.util.List<Tank> tanks)
    {
        for(int i=0;i<tanks.size();i++)
        {
            Tank t=tanks.get(i);
            if(this!=t)
            {
                if(this.live&&this.isLive()&&this.getRect().intersects(t.getRect()))
                {
                    this.stay();//返回上一步的位置；
                    t.stay();////返回上一步的位置
                    return true;
                }
                 
            }   
        }
        return false;
    }
     
 
    private void stay()
    {
        x=oldX;
        y=oldY;
    }
     
    /**
     * 为Tank的内部类；血条，显示在我方坦克的头顶上；
     * @author Hekangmin
     *
     */
    private class BloodBar
    {
        public void draw(Graphics g)
        {
            Color c=g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x,y-10,WIDTH,10);
            int w=WIDTH*life/100;
            g.fillRect(x,y-10,w,10);
        }
    }
     
    /**
     * 吃到血块加血；
     * @param b血块
     * @returntrue吃到，false未吃到；
     */
    public boolean eat(Blood b)
    {
        if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect()))
        {
            this.life=100;
            b.setLive(false);
            return true;
        }
        return false;
    }
     
    /**
     * 我军坦克死后复活；
     */
    public void reBorn()
    {
        if(this.isGood()&&!this.isLive())
        {
            this.setLive(true);
            this.setLife(100);
        }
    }
}