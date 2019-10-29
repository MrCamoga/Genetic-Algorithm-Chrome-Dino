package com.camoga.ga;

import java.awt.Color;
import java.awt.Graphics;

import com.camoga.nn.NeuralNetwork;

public class Dinosaur {
	
	public static final Vec2 gravity = new Vec2(0,1.2);
	public static final double jumpVel = -16;
	
	private static final Vec2 normalSize = new Vec2(20, 40);
	private static final Vec2 downSize = new Vec2(35, 20);
	
	private Vec2 pos, vel;
	private Vec2 currentSize = normalSize;
	public boolean jumping = false;
	private boolean down = false;
	
	public int fitness;
	
	public NeuralNetwork brain;
	
	public Dinosaur() {
		this.pos = new Vec2();
		this.vel = new Vec2();
		brain = new NeuralNetwork(6,4,2);
		brain.setActivations(new int[2]);			
	}
	
	public Dinosaur(NeuralNetwork brain) {
		this.pos = new Vec2();
		this.vel = new Vec2();
		this.brain = brain;
	}
	
	public void moveForward(Vec2 stVel, int timer) {
		fitness = timer;
		pos.add(stVel);	
	}
	
	public void tick(double distance, double speed, double yvel, double entityheight, double height, double isJumping) {
		double[] output = brain.feed(new double[] {1-distance/Main.WIDTH, speed/10, yvel/10, entityheight/Main.HEIGHT, height/Main.HEIGHT, isJumping});
		if(output[0] > 0.5) jump();
		if(output[1] > 0.5) down();
		
		pos.add(vel);
		
		if(pos.getY() < 0) vel.add(gravity);
		else {
			pos.setY(0);
			if(vel.getY() > 0) vel.setY(0);
			jumping = false;
			down = false;
		}
	}
	
	public void jump() {
		if(jumping) return;
		jumping = true;
		vel.setY(jumpVel);
	}
	
	public void down() {
		if(pos.getY() != 0 && !down) {
			if(!down) vel.add(new Vec2(0, 1));
			down = true;	
		}
		currentSize = downSize;
	}
	
	public void up() {
		currentSize = normalSize;
	}
	
	public void render(Graphics g, Vec2 offset) {
		g.setColor(Color.gray);
		g.fillRect(pos.getX()-offset.getX()+Main.WIDTH/2, pos.getY()-offset.getY()+Main.HEIGHT/2-currentSize.getY(), currentSize.getX(), currentSize.getY());
	}
	
	public Vec2 getPos() {
		return pos;
	}
	
	public Vec2 getVel() {
		return vel;
	}
	
	public Vec2 getSize() {
		return currentSize;
	}
}