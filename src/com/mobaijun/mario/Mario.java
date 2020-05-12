package com.mobaijun.mario;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import com.mobaijun.ui.GameFrame;
import com.mobaijun.role.Enemy;

//自己的角色类
public class Mario extends Thread {
	
	public GameFrame gf;
	
	public boolean jumpFlag=true;
	
	//马里奥的坐标
	public int x=0,y=358;
	//马里奥的速度
	public int xspeed=5 , yspeed=1;
	//马里奥的宽高
	public int width=30,height=32;
	//马里奥的图片
	public Image img = new ImageIcon("image/mari1.png").getImage();
	
	public boolean left=false,right=false,down=false,up=false;
	
	public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";
	
	
	public Mario (GameFrame gf) {
		this.gf = gf;
		this.Gravity();
	}

	// 玛丽飞翔的逻辑 ；移动的逻辑都在这里。
	public void run(){
		while(true){
			//向左走
			if(left){
				//碰撞到了
				if(hit(Dir_Left)){
					this.xspeed=0;
				}
				
				if(this.x>=0){
					this.x-=this.xspeed;
					this.img=new ImageIcon("image/mari_left.gif").getImage();
				}
				
				this.xspeed=5;
			}
			
			//向右走
			if(right){
				// 右边碰撞物检测应该是往右走的时候检测
				// 进行碰撞检测：至少主角（玛丽，碰撞物）
				if(hit(Dir_Right)){
					this.xspeed=0;
				}
				//任人物向右移动
				if(this.x<400){
					this.x += this.xspeed;
					this.img=new ImageIcon("image/mari_right.gif").getImage();
				}
				
				if(this.x>=400){
					//背景向左移动
					gf.bg.x-=this.xspeed;
					//障碍物项左移动
					for (int i = 0; i <gf.eneryList.size(); i++) {
						Enemy enery = gf.eneryList.get(i);
						enery.x-=this.xspeed;
					}
					this.img= new ImageIcon("image/mari_right.gif").getImage();
				}
				this.xspeed=5;
			}
			
			//向上跳
			if(up){

				if(jumpFlag && !isGravity){
					jumpFlag=false;
					new Thread(){
						public void run(){
							jump();
							jumpFlag=true;
						}
					}.start();
				}
			}
			
			try {
				this.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//向上跳的函数
	public void jump(){
		int jumpHeigh=0;
		for (int i = 0; i < 150; i++) {
			gf.mario.y-=this.yspeed;
			jumpHeigh++;
			if(hit(Dir_Up)){
				break;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i <jumpHeigh; i++) {
			gf.mario.y+=this.yspeed;
			if(hit(Dir_Down)){
				this.yspeed=0;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		this.yspeed=1;//还原速度
	}
	
	//检测碰撞
	public boolean hit(String dir){
		// Swing技术中，人家已经提供了！！
		Rectangle myrect = new Rectangle(this.x,this.y,this.width,this.height);

		Rectangle rect =null;
		
		for (int i = 0; i < gf.eneryList.size(); i++) {
			Enemy enery = gf.eneryList.get(i);
			
			if(dir.equals("Left")){
				rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
			}else if(dir.equals("Right")){
				// 右侧碰撞物检测。
				rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
			}
			
			else if(dir.equals("Up")){
				rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
			}else if(dir.equals("Down")){
				rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
			}
			//碰撞检测
			if(myrect.intersects(rect)){
				return true;
			}
		}
		
		return false;
	}
	
	//检查是否贴地
	public boolean isGravity=false;

	// 重力线程！
	public void Gravity(){
			new Thread(){
				public void run(){
					
					while(true){
						try {
							sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						

						while(true){
							if(!jumpFlag){
								break;
							}
							
							if(hit(Dir_Down)){
								break;
							}
							
							if(y>=358){
								isGravity=false;
							}else{
								isGravity=true;
								y+=yspeed;
							}
							
							try {
								sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
				}
				}
			}.start();
	
	}
}
