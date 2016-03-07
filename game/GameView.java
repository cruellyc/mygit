package game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
 
import javax.swing.JOptionPane;
import javax.swing.Timer;
 
public class GameView implements Runnable{
 
    public static int one_px = 10;
    public static int num_px = 20;
    public static int num_py = 30;
    public static int view_type;
    private static final int type=16;
    private int sy;
    private int x = 10;
    private int y;
    public static int score;
    private int color = 255;
    private final int allNum = 10;
    private int next;
    private int nextOne=1;
    private boolean isOver;
    private Random r = new Random();
    private int move;
    public static String s="成名太难了！";
     
    private ArrayList<Point> view;
    // 把落下的view放入all
    private ArrayList<Point> all;
    // 一个view消失前放入的集合
    private ArrayList<Point> three;
    private ArrayList<Integer> allView;
    private ArrayList<Point> nextView;
    public GameView() {
        init();
    }
 
    // 初始化
    private void init() {
        view=new ArrayList<Point>();
        all=new ArrayList<Point>();
        three=new ArrayList<Point>();
        nextView=new ArrayList<Point>();
        allView = new ArrayList<Integer>();
    }
    //颜色变化
    Timer back = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            move++;
            color = color --;
            if (color <= 0) {
                color = 255;
            }
            if(move==3){
                three.clear();
                move=0;
                back.stop();
            }
        }
    });
    //------------------------------------------------------------
    Timer timer = new Timer(300, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addView(view_type);
        }
    });
    Timer add = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addView(view_type);
        }
    });
 
    public void timerStart() {
        timer.start();
        view(allView.get(nextOne),nextView);
        isOver = false;
    }
 
    public void reStart() {
        all.clear();
        view.clear();
        allView.clear();
        allView();
        next=0;
        nextOne=1;
        view_type=allView.get(next++);
        int view=allView.get(nextOne++);
        view(view,nextView);
        timer.start();
        y=0;
        isOver = false;
        System.out.println(allView);
    }
 
     
 
    public void addView(int view_type) {
        view(view_type,view);
        addList();
    }
 
    public void gameOver() {
        isOver = true;
        timer.stop();
    }
     
    public int getType() {
        int n = r.nextInt(type);
        return n;
    }
 
    // 判断是否结束
    public boolean isGameOver() {
        int x = 0;
        while (x < num_px) {
            if (all.contains(new Point(x, 0))) {
                return true;
            }
            x++;
        }
        return false;
    }
 
    // 中间使用的集合
    private ArrayList<Point> one = new ArrayList<Point>();
    // 移动的方向,有传进的参数 n决定
    public void moving(int n) {
        int xx = 0;
        int yy = 0;
 
        for (Point p : view) {
            switch (n) {
            case 0:
                break;
            case 1:
                xx = p.x - 1;
                break;
            case 2:
                xx = p.x + 1;
                break;
            }
            yy = p.y;
            one.add(new Point(xx, yy));
        }
 
        if (n == 1)
            x--;
        if (n == 2)
            x++;
        view.clear();
        view.addAll(one);
        one.clear();
    }
 
    // 把一行中全部有子的,放入two中
    private ArrayList<Point> two = new ArrayList<Point>();
    // 移动一下判断一次
    public void remove() {
        int i = num_py - 1;
        Point p;
        while (i > sy) {
            for (int j = 0; j < num_px; j++) {
                p = new Point(j, i);
                if (all.contains(p)) {
                    two.add(p);
                } else {
                    two.clear();
                    break ;
                }
            }
            if (two.size() == num_px) {
                all.removeAll(two);
                three.addAll(two);
                score = score + 100;
                back.start();
                two.clear();
                down(i);
                i++;
            }
            i--;
        }
    }
 
    // 把要下降的行放入集合
    private ArrayList<Point> down = new ArrayList<Point>();
    // 把要下降的 行放入集合
    private ArrayList<Point> downs = new ArrayList<Point>();
    //向下降一行
    public void down(int idx) {
        int i=idx;
        while ( i > 0  ) {
            for (int j = 0; j < num_px; j++) {
                Point p = new Point(j, i);
                if (all.contains(p)) {
                    down.add(p);
                    downs.add(new Point(p.x, p.y + 1));
                }
            }
            i--;
        }
        all.removeAll(down);
        all.addAll(downs);
        downs.clear();
        down.clear();
    }
 
    // 判断左边有没有
    public boolean leftHas() {
        for (Point p : view) {
            if (all.contains(new Point(p.x - 1, p.y))) {
                return false;
            }
        }
        return true;
    }
 
    // 判断右边有没有
    public boolean rightHas() {
        for (Point p : view) {
            if (all.contains(new Point(p.x + 1, p.y))) {
                return false;
            }
        }
        return true;
    }
 
    // 下一个类型
    public void next() {
        all.addAll(view);
        view.clear();
        nextView.clear();
        sy = y - 5;// sy
        x = num_px / 2;
        y = 0;
        remove();
        timer.start();
        add.stop();
        if (isGameOver()) {
            gameOver();
        }
        nextView();
    }
    //下一个
    public void nextView(){
 
        if(nextOne==allNum){
            nextOne=0;
        }
        int view=allView.get(nextOne);
        view_type = allView.get(next++);
        if(next==allNum){
            next=0;
        }
        if(nextOne==0){
            allView.clear();
            allView();
        }
        view(view,nextView);
        nextOne++;
    }
    // 一个类型中的数量有几个,多了就移除
    public void addList() {
        if (view.size() > 4) {
            while (view.size() > 4) {
                view.remove(0);
            }
        }
        if (y > num_py - 2) {
            next();
        }
        Iterator<Point> ite = view.iterator();
        while (ite.hasNext()) {
            Point p = ite.next();
            if (all.contains(new Point(p.x, p.y + 1))) {
                next();
                break;
            }
        }
        y++;
    }
 
    // 变化过程
    public int setType(int type) {
        if (type == 0) {
            return 1;
        }
        else if (type == 1) {
            return 0;
        }
        else if (type == 5) {
            return 2;
        }
        else if (type == 9) {
            return 6;
        }
        else if (type == 13) {
            return 10;
        }else if(type == 14 || type == 15){
            return type;
        }
        else {
            return type+1;
        }
         
    }
 
    // 有几种类型变化
    public void view(int view_type,ArrayList<Point> view) {
        switch (view_type) {
        case 0:
            view.add(new Point(x, y));
            view.add(new Point(x, y-1));
            view.add(new Point(x, y-2));
            view.add(new Point(x, y-3));
            break;
        case 1:
            view.add(new Point(x, y));
            view.add(new Point(x + 1, y));
            view.add(new Point(x - 1, y));
            view.add(new Point(x + 2, y));
            break;
        // -----------------------------------------------------------------------
        case 2:
            view.add(new Point(x, y));     //   **
            view.add(new Point(x, y-2));   //   ****
            view.add(new Point(x, y - 1)); //   **
            view.add(new Point(x + 1, y - 1));
            break;
        case 3:
            view.add(new Point(x, y)); //
            view.add(new Point(x-1, y - 1)); //  **
            view.add(new Point(x-2, y));    // ******
            view.add(new Point(x - 1, y));
            break;
        case 4:
            view.add(new Point(x, y));      // **
            view.add(new Point(x, y - 1));  // **
            view.add(new Point(x, y - 2));  // **
            view.add(new Point(x - 1, y-1));
            break;
        case 5:
            view.add(new Point(x, y)); //
            view.add(new Point(x, y - 1)); //
            view.add(new Point(x - 1, y - 1)); //
            view.add(new Point(x + 1, y - 1));
            break;
        // -----------------------------------------------------------------
        case 6:
            view.add(new Point(x, y));      //    **
            view.add(new Point(x - 1, y)); // ******
            view.add(new Point(x - 2, y));
            view.add(new Point(x, y - 1));
            break;
        case 7:
            view.add(new Point(x, y));      // ****
            view.add(new Point(x, y - 1)); //    **
            view.add(new Point(x, y - 2)); //    **
            view.add(new Point(x - 1, y - 2));
            break;
        case 8:
            view.add(new Point(x, y)); //
            view.add(new Point(x, y - 1));      // ******
            view.add(new Point(x + 1, y - 1));  // **
            view.add(new Point(x + 2, y - 1));
            break;
        case 9:
            view.add(new Point(x, y));     //   **
            view.add(new Point(x-1, y));    //  **
            view.add(new Point(x-1, y - 1)); // ****
            view.add(new Point(x - 1, y - 2));
            break;
        // --------------------------------------------------------------------
        case 10:
            view.add(new Point(x, y));       //   **
            view.add(new Point(x+1, y - 1)); // ****
            view.add(new Point(x, y - 1));   // **
            view.add(new Point(x + 1, y-2));
            break;
        case 11:
            view.add(new Point(x, y));     // ****
            view.add(new Point(x-1, y )); //    ****
            view.add(new Point(x-1, y - 1)); // 
            view.add(new Point(x - 2, y - 1));
            break;
            //--------------------------------------
        case 12:
            view.add(new Point(x, y));          // **
            view.add(new Point(x, y - 1));      // ****
            view.add(new Point(x - 1, y - 1));  //   **
            view.add(new Point(x - 1, y - 2));
            break;
        case 13:
            view.add(new Point(x, y));      //   ****
            view.add(new Point(x, y - 1));  // ****
            view.add(new Point(x-1, y));
            view.add(new Point(x + 1, y - 1));
            break;
            //----------------------------------------------
        case 14:
            view.add(new Point(x, y));
            view.add(new Point(x - 1, y));
            break;
        case 15:
            view.add(new Point(x, y));
            view.add(new Point(x, y-2));
            view.add(new Point(x-1, y-1));
            view.add(new Point(x-1, y-2));
            break;
        }
 
    }
 
    public int getOne_px() {
        return one_px;
    }
 
    public void down() {
        timer.stop();
        add.start();
    }
 
    public int getColor() {
        return color;
    }
 
    public int getMax() {
        int max = 0;
        for (Point p : view) {
            if (p.x > max) {
                max = p.x;
            }
        }
        return max;
    }
 
    public int getMin() {
        int min = num_px - 1;
        for (Point p : view) {
            if (p.x < min) {
                min = p.x;
            }
        }
        return min;
    }
 
    public boolean isOver() {
        return isOver;
    }
    public void run() {
        System.out.println(s);
    }
     
    public void allView() {
        int n = 0;
        for (int i = 0; i < allNum; i++) {
            n = getType();
            allView.add(n);
        }
    }
 
    public ArrayList<Point> getNextView() {
        return nextView;
    }
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }
 
    public int getScore() {
        return score;
    }
 
    public ArrayList<Point> getView() {
        return view;
    }
 
    public ArrayList<Point> getAll() {
        return all;
    }
 
    public ArrayList<Point> getThree() {
        return three;
    }
}