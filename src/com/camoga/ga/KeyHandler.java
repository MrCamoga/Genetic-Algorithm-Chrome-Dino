package com.camoga.ga;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private Main main;
	
	public KeyHandler(Main main) {
		this.main = main;
		main.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) main.level.population.get(0).jump();
		if(e.getKeyCode() == KeyEvent.VK_DOWN) main.level.population.get(0).down();
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) main.level.population.get(0).up();
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
