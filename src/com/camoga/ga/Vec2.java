package com.camoga.ga;

public class Vec2 {
	public double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2() {
		this(0, 0);
	}
	
	public Vec2 add(Vec2 v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vec2 sub(Vec2 v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
		public Vec2 mul(double s) {
		this.x *= s;
		this.y *= s;
		return this;
	}
	
	public Vec2 div(double s) {
		this.x /= s;
		this.y /= s;
		return this;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void set(int x, int y) {
		setX(x);
		setY(y);		
	}
}
