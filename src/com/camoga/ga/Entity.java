package com.camoga.ga;

import java.awt.Graphics;

public abstract class Entity {

	protected int height, width;
	protected Vec2 pos;
	
	public Vec2 getPos() {
		return pos;
	}

	public abstract void tick();
	public abstract void render(Graphics g, Vec2 offset);


	public int getXScreen(Vec2 offset) {
		return pos.getX() - offset.getX() + Main.WIDTH/2;
	}
	
	public int getYScreen(Vec2 offset) {
		return pos.getY() - offset.getY() + Main.HEIGHT/2-height;
	}
}
