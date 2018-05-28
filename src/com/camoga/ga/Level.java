package com.camoga.ga;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class Level {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Dinosaur> population = new ArrayList<Dinosaur>();
	public ArrayList<Dinosaur> nextpopulation = new ArrayList<Dinosaur>();
	
	private Random rand = new Random();
	
	private Vec2 offset;
	private static Vec2 stVel = new Vec2(5, 0);
	
	private int bestFitness = 0;
	private int generation = 1;
	protected boolean bestDino = false;
	
	public Level() {
		//TODO crear poblacion y generar cactus en tick();
		for(int i = 0; i < 2000; i++) {
			population.add(new Dinosaur());			
		}
		
		offset = new Vec2(population.get(0).getPos().getX(), 0);
	}
	
	int entitytimer = 0;
	int timer = 0;
	public void tick() {
		if(population.size()==0) repopulate();
		entitytimer++;
		for(Dinosaur d : population) {
			d.moveForward(stVel, timer);
			
			double distance = 0;
			double height = 0;
			for(Entity c : entities) {
				if(c.getPos().getX() > d.getPos().getX()) {
					distance = c.getPos().x - d.getPos().x;
					height = c.getPos().y;
					break;
				}
			}
			double speed = stVel.x;
			
			d.tick(distance, speed, d.getVel().y, -height, -d.getPos().y, d.jumping ? 1:0);
		}
		
		for(Entity c : entities) {
			c.tick();
		}
		
		timer += stVel.getX();
		if(timer % 1000 == 0) stVel.add(new Vec2(1,0));
		
		collision();
		
		removeEntities();
		spawnEntities();
		
		updateOffset();
	}
	
	public void collision() {
		Iterator<Dinosaur> dinoIt = population.iterator();
		while(dinoIt.hasNext()) {
			Dinosaur d = dinoIt.next();
			for(Entity c : entities) {
				
				if(hasCollided(d, c)) {
					nextpopulation.add(d);
					dinoIt.remove();
					break;
				}
			}
		}
	}
	
	public void removeEntities() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity c = it.next();
			if(c.getXScreen(offset) < -c.width) it.remove();
		}
	}
	
	public void updateOffset() {
		if(population.size() > 0)
			offset = new Vec2(population.get(0).getPos().getX(), 0);
	}
	
	private double birdRate = 0.2;
	private int minLength = 50, maxLength = 130;
	private int birdSpawn = 5000;
	public void spawnEntities() {
		if(entitytimer > (rand.nextInt(maxLength-minLength)+minLength) && rand.nextDouble() > 0.8) {
			if(timer < birdSpawn) addCactus();
			else {
				if(rand.nextDouble() < birdRate) addBird();
				else addCactus();
			}
			entitytimer = 0;
		}
	}

	private void repopulate() {
		generation++;
		
		nextpopulation.sort(new Comparator<Dinosaur>() {
			public int compare(Dinosaur d1, Dinosaur d2) {
				return d2.fitness-d1.fitness;
			}
		});

		for(Dinosaur d : nextpopulation) {
			if(d.fitness > bestFitness) bestFitness = d.fitness;
		}
		
		
		for(Dinosaur d : nextpopulation) {
			population.add(new Dinosaur(d.brain.clone()));
//			population.add(new Dinosaur(d.brain.clone().mutate(0.05, 0.8)));
			population.add(new Dinosaur());
			if(population.size()==nextpopulation.size()) break;
		}
		
		entities.clear();
		stVel.set(5, 0);
		nextpopulation.clear();
		timer = 0;
		entitytimer = 0;
	}
	
	private void addBird() {
		int birdHeight = rand.nextInt(3);
		switch (birdHeight) {
			case 0:
				entities.add(new Bird(new Vec2(timer+Main.WIDTH/2, 0)));
				break;
			case 1:
				entities.add(new Bird(new Vec2(timer+Main.WIDTH/2, -25)));
				break;
			case 2:
				entities.add(new Bird(new Vec2(timer+Main.WIDTH/2, -65)));
				break;
		}
		
	}

	public void addCactus() {
		int cactus = rand.nextInt(4);
		switch(cactus) {
			case 0:
				entities.add(new Cactus(Cactus.shortCactus, new Vec2(timer+Main.WIDTH/2, 0)));
				break;
			case 1:
				entities.add(new Cactus(Cactus.smallCactus, new Vec2(timer+Main.WIDTH/2,0)));
				break;
			case 2:
				entities.add(new Cactus(Cactus.largeCactus, new Vec2(timer+Main.WIDTH/2,0)));
				break;
			case 3:
				entities.add(new Cactus(Cactus.mediumCactus, new Vec2(timer+Main.WIDTH/2,0)));
				break;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.GRAY);
		g.drawLine(0, Main.HEIGHT/2, Main.WIDTH, Main.HEIGHT/2);
		for(Entity c : entities) {
			c.render(g, offset);
		}
		if(!bestDino)
		for(Dinosaur d : population) {
			d.render(g, offset);
		}
		else if(population.size()>0)population.get(0).render(g, offset);
		g.setColor(Color.red);
		g.drawString("best fitness: " + bestFitness, 600, 100);
		g.drawString("Dinosaurs left: " + population.size(), 600, 120);
		g.drawString("Gen: " + generation, 600, 140);
		g.drawString("Score: " + timer, 600, 160);
		
		if(population.size()>0)
		population.get(0).brain.render(g, 50,50,250, 250);
	}
	
	//FIXME
	public boolean hasCollided(Dinosaur d, Entity c) {
		if(d.getPos().getX()+d.getSize().getX() >= c.getPos().getX() && (c.getPos().getX()+c.width) >= d.getPos().getX() &&
		   d.getPos().getY() <= c.getPos().getY() && (c.getPos().getY()-c.height) <= d.getPos().getY())
			return true;
		return false;
	}
}
