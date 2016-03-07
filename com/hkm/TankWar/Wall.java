package com.hkm.TankWar;
import java.awt.*;
/**
 * 生成阻碍物墙这个类；
 * @author Hekangmin
 *
 */
 
public class Wall {
    /**
     * x，y为墙的位置，w，h为宽度高度；
     */
    int x,y,w,h;
    /**
     * 持有引用
     */
    TankWarClient tc;
     
    public Wall(int x, int y, int w, int h, TankWarClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }
     
    public void draw(Graphics g)
    {
        Color c=g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x,y,w,h);
        g.setColor(c);
    }
     
    /**
     * 得到墙的矩形区域；
     * @return
     */
    public Rectangle getRect()
    {
        return new Rectangle(x,y,w,h);
    }
     
}