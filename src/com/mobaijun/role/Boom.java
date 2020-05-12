package com.mobaijun.role;
/**
  子弹类
 */
public class Boom {
	//子弹的坐标，大小，速度
	public int x,y;
	public int width;
	public int speed=1;
	
	public Boom(int x, int y, int width) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
	}

}