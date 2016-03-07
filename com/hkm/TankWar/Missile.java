package com.hkm.TankWar;
import java.awt.*;
import java.awt.Event.*;
import java.awt.event.KeyEvent;
import java.util.List;
/**
 * 子弹类
 * @author Hekangmin
 *
 */
  public class Missile {
        private int x,y;//子弹的位置
        private Tank.Direction dir;//坦克方向
         
        private static final int XSPEED=10;//坦克x方向的移动速度，
        private static final int YSPEED=10;//坦克y方向的移动速度，
         
        public static final int WIDTH=10;
        public static final int HEIGHT=10;
         
        private boolean Live=true;//判断子弹是否活着
         
        private boolean good;//区分敌军子弹和我军子弹
         
        private TankWarClient tc;
         
    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
     
    public Missile(int x,int y,boolean good,Tank.Direction dir,TankWarClient tc)
    {
        this(x,y,dir);
        this.good=good;//将坦克好坏的属性与子弹还坏属性设为相同；
        this.tc=tc;
    }
     
    /**
     * 画出子弹
     * @param g为画笔
     */
    public void draw(Graphics g)
    {
        if(!Live)
            {
            tc.missiles.remove(this);
            return;
            }
        Color c=g.getColor();
        if(good)
        {
            g.setColor(Color.BLUE);
        }
        else g.setColor(Color.ORANGE);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
         
        move();
    }
     
    /**
     * 根据坦克的方向让子弹移动
     */
    private void move() {
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
        }
         
        if(x<0||y<0||x>TankWarClient.GAME_WIDTH||y>TankWarClient.GAME_HEIGHT)//子弹越界则让其死亡；
        {
            Live=false;
        }
    }
     
     
    public boolean isLive()
    {
        return Live;
    }
 
    public Rectangle getRect()//获取子弹的矩形区域;
    {
        return new Rectangle(this.x,this.y,this.WIDTH,this.HEIGHT);
    }
     
    /**
     * 判断子弹与坦克碰撞；
     * @param t为坦克
     * @return返回true则表示发生碰撞，否则没有碰撞；
     */
    public boolean hitTank(Tank t)
    {
        if(this.Live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood())
        {
            if(t.isGood())
            {
                t.setLife(t.getLife()-10);
                if(t.getLife()<=0) t.setLive(false); 
            }else{
                t.setLive(false);
            }
            this.Live=false;///将子弹设为死亡;
            Explode e=new Explode(x,y,tc);//发生爆炸；
            tc.explodes.add(e);
            return true;
             
        }
        return false;
    }
    /**
     * 判断子弹与敌军坦克相撞；
     * @param tanks敌军坦克
     * @returntrue表示相撞，false没有相撞；
     */
    public boolean hitTanks(List<Tank> tanks)
    {
        for(int i=0;i<tanks.size();i++)
        {
            if(hitTank(tc.tanks.get(i)))
            {
                return true;
            }
        }
         
        return false;
    }
     
    /**
     * 判断子弹是否撞墙
     * @param w墙
     * @returntrue，撞上，false，未撞上；
     */
    public boolean hitsWall(Wall w)
    {
        if(this.Live&&this.getRect().intersects(w.getRect()))
        {
            Live=false;
            return true;
        }
        return false;
    }
     
     
}