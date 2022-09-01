package com.mobaijun.ui;

import com.mobaijun.mario.Mario;
import com.mobaijun.role.Boom;
import com.mobaijun.role.Enemy;
import com.mobaijun.role.Pipe;
import com.mobaijun.util.Map;
import com.mobaijun.util.MusicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 主体窗口界面：展示角色。
 */
public class GameFrame extends JFrame {
    /**
     * 超级玛丽:界面需要一个超级玛丽的。
     */
    public Mario mario;
    /**
     * 分别定义:水管，金币和砖块
     */
    public Enemy pipe, cion, brick;
    /**
     * 背景图片
     */
    public BackgroundImage bg;
    /**
     * 定义一个集合容器装敌人对象
     */
    public ArrayList<Enemy> eneryList = new ArrayList<Enemy>();
    /**
     * 定义一个集合容器装子弹
     */
    public ArrayList<Boom> boomList = new ArrayList<Boom>();
    /**
     * 子弹的速度
     */
    public int bspeed = 0;

    /**
     * 地图数据，制定规则，是1画砖头，是2画金币，是3画水管
     */
    public int[][] map = null;

    {
        /**
         * 实例代码块中初始化地图资源的数据
         */
        Map mp = new Map();
        map = mp.readMap();
    }

    /**
     * 构造函数里面初始化背景图片和马里奥对象
     *
     * @throws Exception Exception
     */
    public GameFrame() throws Exception {
        // 初始化窗体相关属性信息数据
        // this代表了当前主界面对象。
        this.setSize(800, 450);
        this.setTitle("超级玛丽");
        this.setResizable(false);
        // 居中展示窗口
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        // 创建玛丽对象
        mario = new Mario(this);

        // 创建背景图片
        bg = new BackgroundImage();

        // 读取地图，并配置地图
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // 读取到的是1，画砖头
                if (map[i][j] == 1) {
                    // x
                    brick = new Pipe(j * 30, i * 30, 30, 30, new ImageIcon("image/brick.png").getImage());
                    eneryList.add(brick);
                }
                // 读到2画金币
                if (map[i][j] == 2) {
                    cion = new Pipe(j * 30, i * 30, 30, 30, new ImageIcon("image/coin_brick.png").getImage());
                    eneryList.add(cion);
                }
                // 读到3画水管
                if (map[i][j] == 3) {
                    pipe = new Pipe(j * 30, i * 30, 60, 120, new ImageIcon("image/pipe.png").getImage());
                    eneryList.add(pipe);
                }

            }
        }

        mario.start();

        // 开启一个线程负责界面的窗体重绘线程
        new Thread(() -> {
            while (true) {
                //重绘窗体
                repaint(); // 自动触发当前窗口中的paint方法
                //检查子弹是否出界
                //checkBoom();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 设置背景音乐
        new Thread(MusicUtil::playBackground).start();
    }

    @Override
    public void paint(Graphics g) {
        // 利用双缓冲画背景图片和马里奥
        BufferedImage bi = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        Graphics big = bi.getGraphics();
        big.drawImage(bg.img, bg.x, bg.y, null);

        // 开始绘制界面上的敌人。
        for (Enemy e : eneryList) {
            big.drawImage(e.img, e.x, e.y, e.width, e.height, null);
        }

        // 画子弹
        for (Boom b : boomList) {
            Color c = big.getColor();
            big.setColor(Color.red);
            big.fillOval(b.x += b.speed, b.y, b.width, b.width);
            big.setColor(c);
        }

        // 画人物 玛丽自己
        big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height, null);
        g.drawImage(bi, 0, 0, null);

    }

    /**
     * 检查子弹是否出界，出界则从容器中移除，不移除的话，内存会泄漏
     */
    public void checkBoom() {
        for (int i = 0; i < boomList.size(); i++) {
            Boom b = boomList.get(i);
            if (b.x < 0 || b.x > 800) {
                boomList.remove(i);
            }
        }
    }

}
