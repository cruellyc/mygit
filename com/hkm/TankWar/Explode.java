package com.hkm.TankWar;
import java.awt.*;
/**
 * 爆炸类
 * @author Hekangmin
 *
 */
public class Explode {
    private int x,y;//爆炸发生的位置
     
    private boolean Live=true;
     
    int dia[]={4,8,12,16,32,40,20,14,4};//用园模拟，代表圆的直径；
     
    int step=0;//区别移到第几个直径
     
    private TankWarClient tc;//持有引用
     
    public Explode(int x,int y,TankWarClient tc)
    {
        this.x=x;
        this.y=y;
        this.tc=tc;
    }
    public void draw(Graphics g)
    {
        if(!Live)
        {
            tc.explodes.remove(this);
            return;
        }
        if(step==dia.length)//如果到了最后一个直径爆炸死亡；
        {
            Live=false;
            step=0;
            return;
        }
        Color c=g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, dia[step], dia[step]);
        g.setColor(c);
        step++;
    }
     
}