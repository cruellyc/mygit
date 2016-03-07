package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
 
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class LiftPane extends JPanel implements KeyListener {
 
    private GameView gameView;
    private ArrayList<Point> view;
    private ArrayList<Point> all;
    private ArrayList<Point> three;
    private ArrayList<Point> nextView;
    private int gametime;
    private int gamemini;
    private int hour;
     
    public LiftPane() {
        init();
    }
 
    public void init() {
        gameView = new GameView();
        gameView.allView();
        view = gameView.getView();
        all = gameView.getAll();
        three = gameView.getThree();
        nextView=gameView.getNextView();
        addKeyListener(this);
        setFocusable(true);
    }
     
    Timer timer = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            repaint();
            timeLimit();
        }
    });
 
    public void timeLimit() {
        gamemini++;
        if (gamemini == 10) {
            gametime++;
            gamemini = 0;
        }
        if (gametime == 60) {
            hour++;
            gametime = 0;
        }
        if (gameView.isOver()) {
            timer.stop();
        }
    }
 
    public void timerStart() {
        gameView.timerStart();
        timer.start();
        new Thread(gameView).start();
        System.out.println("线程开启");
    }
 
    public void reStart() {
        gameView.reStart();
        timer.start();
        gametime = 0;
        gamemini = 0;
        hour = 0;
    }
    public void user(){
        String file="src/gyb/user.dat";
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(all);
            oos.writeObject(view);
            oos.writeObject(three);
            oos.write(gameView.getScore());
            oos.writeBoolean(gameView.isOver());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        int one_px = GameView.one_px;
        g.setColor(new Color(188,238,104));
        g.fill3DRect(0, 0, 200, 300, false);
        g.setColor(new Color(255,153,51));
        for (Point p : view) {
            g.fill3DRect(one_px * p.x, one_px * p.y, one_px, one_px, true);
        }
        for (Point p : all) {
            g.fill3DRect(one_px * p.x, one_px * p.y, one_px, one_px, true);
        }
        g.setColor(new Color(gameView.getColor(), gameView.getColor() / 2,
                gameView.getColor() / 3));
        for (Point p : three) {
            g.fill3DRect(one_px * p.x, one_px * p.y, one_px, one_px, true);
        }
         
        g.setColor(Color.BLACK);
        g.fill3DRect(200, 0, 100, 200, true);
        g.setColor(Color.GREEN);
        g.setFont(new Font("Utopia", Font.BOLD, 30));
        g.drawString("" + gameView.getScore(), 240, 50);
         
        g.setColor(new Color(51,102,0));
        g.setFont(new Font("Utopia", Font.BOLD, 20));
        g.drawString(hour + " : " + gametime + " : " + gamemini, 210, 100);
        for (Point p : nextView) {
            g.fill3DRect(p.x*one_px+140, (p.y+3)*one_px+120, one_px, one_px, true);
        }
        if (gameView.isOver()) {
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Utopia", Font.BOLD, 45));
            g.drawString("Game Over !", 25, 140);
        }
         
    }
 
    public void keyTyped(KeyEvent e) {
    }
 
    public void keyPressed(KeyEvent e) {
        int kv = e.getKeyCode();
        if (kv == KeyEvent.VK_LEFT) {
            if (gameView.getMin() > 0 && gameView.leftHas())
                gameView.moving(1);
        } else if (kv == KeyEvent.VK_RIGHT) {
            if (gameView.getMax() < GameView.num_px - 1 && gameView.rightHas())
                gameView.moving(2);
        } else if (kv == KeyEvent.VK_DOWN) {
            gameView.down();
        } else if (kv == KeyEvent.VK_UP) {
            GameView.view_type = gameView.setType(GameView.view_type);
        }else if(kv == KeyEvent.VK_ENTER){
            System.out.println("Enter");
        }
    }
 
    public void keyReleased(KeyEvent e) {
    }
 
    public boolean isOver() {
        return gameView.isOver();
    }
}