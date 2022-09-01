package com.mobaijun.util;

import com.mobaijun.role.Boom;
import com.mobaijun.ui.GameFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 键盘按下监听类
 */
public class KeyListener extends KeyAdapter {

    /**
     * 接收到了当前主界面：游戏界面
     */
    public GameFrame gf;

    public KeyListener(GameFrame gf) {
        this.gf = gf;
    }

    /**
     * 键盘监听
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            // 向右走
            case 39:
                // 信号位
                gf.mario.right = true;
                break;
            // 向左走
            case 37:
                gf.mario.left = true;
                break;
            case 66:
                addBoom();
                break;
            //向上跳
            case 38:
                gf.mario.up = true;
                break;
            default:
                throw new RuntimeException("Abnormal operation, wrong direction!");
        }
    }

    /**
     * 添加子弹
     */
    public void addBoom() {
        Boom b = new Boom(gf.mario.x, gf.mario.y + 5, 10);
        if (gf.mario.left) {
            b.speed = -2;
        }
        if (gf.mario.right) {
            b.speed = 2;
        }
        gf.boomList.add(b);
    }

    /**
     * 键盘释放监听
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 39) {
            gf.mario.right = false;
            gf.mario.img = new ImageIcon("image/mari1.png").getImage();
        }
        if (code == 37) {
            gf.mario.left = false;
            gf.mario.img = new ImageIcon("image/mari_left1.png").getImage();
        }
        if (code == 38) {
            gf.mario.up = false;
        }
    }
}
