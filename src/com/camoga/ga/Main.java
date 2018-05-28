package com.camoga.ga;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends Canvas {
	
	public static final int WIDTH = 800, HEIGHT  = 800;
	
	public Level level;
	public KeyHandler key;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private Thread thread;
	
	private double ns = 1e9/60D; 
	
	public Main() {
		createWindow();
		
		thread = new Thread(() -> run());
		thread.start();
	}
	
	public void createWindow() {
		JFrame f = new JFrame();
		setSize(WIDTH, HEIGHT);
		f.setLayout(new BorderLayout());
		f.setVisible(true);
		f.setResizable(false);
		
		
		JPanel panel = new JPanel(new GridLayout(10, 1));
		JButton bestDino = new JButton("Show best dino");
		bestDino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
					case "Show best dino":
						level.bestDino = true;
						bestDino.setText("Show all dinos");
						break;
						
					case "Show all dinos":
						level.bestDino = false;
						bestDino.setText("Show best dino");
						break;
				}
			}
		});
		JTextField tickSpeed = new JTextField();
		tickSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ns = 1e9/Integer.parseInt(tickSpeed.getText());
			}
		});
		
		panel.add(bestDino);
		panel.add(tickSpeed);
		
		f.add(panel, BorderLayout.EAST);
		f.add(this, BorderLayout.CENTER);
		
		f.pack();
		
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init() {
		level = new Level();
		key = new KeyHandler(this);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	public void run() {
		init();
		
		double last = System.nanoTime();
		
		double delta = 0;
		
		long timer = System.currentTimeMillis();
		
		int ticks = 0, frames = 0;
		
		while(true) {
			double now = System.nanoTime();
			delta += (now-last)/ns;
			last = now;
			
			while(delta >= 1) {
				ticks++;
				level.tick();
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.out.println("UPS: " + ticks + " FPS: " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void render() {
		BufferStrategy b = getBufferStrategy();
		if(b == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = b.getDrawGraphics();
		
		g.drawImage(image, 0, 0, null);
		level.render(g);
		g.dispose();
		b.show();
	}
}