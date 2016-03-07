package com.hkm.TankWar;
import java.awt.*;
/**
 * 血块类，我方坦克吃了可回血；
 * @author Hekangmin
 *
 */
public class Blood {
    private int x,y,w,h;//血块的位置和宽度高度；
     
    private TankWarClient tc;
     
    private int step=0;//纪录血块移动的步数；
     
    private boolean live=true;
    public boolean isLive() {
        return live;
    }
 
    public void setLive(boolean live) {
        this.live = live;
    }
        /**
         * 纪录血块的位置；
         */
    private int[][] pos={{400,300},{400,320},{420,320},{440,300},{440,330},{480,400},{520,400},{540,400}};
               
    public Blood()
    {
        x=pos[0][0];
        y=pos[0][1];
        w=h=18;
    }
     
    public void draw(Graphics g)
    {
        if(!live) return;
         
        Color c=g.getColor();
        g.setColor(Color.CYAN);
        g.fillOval(x, y, w, h);
        g.setColor(c);
         
        move();
    }
    /**
     * 移动血块
     */
    public void move()
    {
        step++;
        if(step>=pos.length) step=0;
        else{
        x=pos[step][0];
        y=pos[step][1];
        }
    }
     
     
    public Rectangle getRect()
    {
        return new Rectangle(x,y,w,h);
    }
     
     
}