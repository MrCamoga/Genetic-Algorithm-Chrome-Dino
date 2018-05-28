package com.camoga.ga;

import java.awt.Color;
import java.awt.Graphics;

public class Cactus extends Entity {
	
	public static final Vec2 shortCactus = new Vec2(16,16);
	public static final Vec2 smallCactus = new Vec2(16,35);
	public static final Vec2 mediumCactus = new Vec2(33,35);
	public static final Vec2 largeCactus = new Vec2(40,35);
	
	public Cactus(Vec2 size, Vec2 pos) {
		this.width = size.getX();
		this.height = size.getY();
		this.pos = pos;
	}
	
	public void render(Graphics g, Vec2 offset) {
		g.setColor(Color.GREEN);
		g.fillRect(getXScreen(offset), getYScreen(offset), width, height);
	}

	public void tick() {
		
	}
}