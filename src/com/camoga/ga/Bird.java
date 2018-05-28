package com.camoga.ga;

import java.awt.Color;
import java.awt.Graphics;

public class Bird extends Entity {
	
	private Vec2 vel = new Vec2(1.1, 0);
	
	public Bird(Vec2 pos) {
		this.width = 15;
		this.height = 15;
		this.pos = pos; 
	}
	
	public void tick() {
		pos.add(vel);
	}
	
	public void render(Graphics g, Vec2 offset) {
		g.setColor(Color.RED);
		g.fillRect(getXScreen(offset), getYScreen(offset), width, height);
	}
}
