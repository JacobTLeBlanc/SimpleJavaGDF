package com.jamescho.game.main;

// Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.jamescho.game.state.LoadState;
import com.jamescho.game.state.State;
import com.jamescho.framework.util.InputHandler;

@SuppressWarnings("serial")

public class Game extends JPanel implements Runnable {
	// Instance vars
	private int gameWidth;
	private int gameHeight;
	private Image gameImage;
	private Thread gameThread;
	private volatile boolean running;
	private volatile State currentState;
	private InputHandler inputHandler;
	
	// Constructor
	public Game(int gameWidth, int gameHeight) {
		// Get dimensions
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		
		// Set size/background
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.BLACK);
		
		// Get keyboard/mouse input
		setFocusable(true);
		requestFocus();
	}
	
	public void setCurrentState(State newState) {
		System.gc();
		newState.init();
		currentState = newState;
		// Change state for input class
		inputHandler.setCurrentState(currentState);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		// Get input
		initInput();
		setCurrentState(new LoadState());
		// Start game
		initGame();
	}
	
	private void initGame() {
		running = true;
		
		// Create new thread to allow simultaneous processing
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}
	
	private void initInput() {
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
	}

	@Override
	public void run() {
		// Game loop
		while (running) {
			// Update state
			currentState.update();
			prepareGameImage();
			currentState.render(gameImage.getGraphics());
			repaint();
			try {
				// Delay thread by 14 milliseconds
				Thread.sleep(14);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// End game if not running
		System.exit(0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameImage == null) {
			return;
		}
		g.drawImage(gameImage, 0, 0, null);
	}

	private void prepareGameImage() {
		// Create new image if none exist
		if (gameImage == null) {
			gameImage = createImage(gameWidth, gameHeight);
		}
		
		Graphics g = gameImage.getGraphics();
		g.clearRect(0, 0, gameWidth, gameHeight);
		
	}
	
	public void exit() {
		running = false;
	}
}
