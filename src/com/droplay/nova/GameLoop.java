package com.droplay.nova;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop implements Runnable {
	private Thread gameLoopThread;
	private boolean isRunning;
	private NovaContext context;
	private SurfaceHolder holder;
	
	public static final int FPS = 25;
	// If the game gets slow, it can skip a few frames, but only 5, so it would still look well
	public static final int MAX_FRAMES_SKIPPED = 5;
	
	public GameLoop(NovaContext context) {
		this.context = context;
	}
	
	public void startGameLoop(SurfaceHolder holder) {
		this.holder = holder;
		isRunning = true;
		gameLoopThread = new Thread(this);
		gameLoopThread.start();
	}

	public void stopGameLoop() {
		if (isRunning) {
			isRunning = false;
		}
	}
	
	public Thread getThread() {
		return gameLoopThread;
	}
	
	public boolean isAlive() {
		return gameLoopThread.isAlive();
	}

	public void run() {
		long startTime, sleepTime;
		final NovaManager game = context.getManager();
		final NovaGraphics graphics = context.getGraphics();
		Canvas canvas;
		int framesSkipped;
		
		try {
			while (isRunning) {
				startTime = System.currentTimeMillis();
				game.progressGame();
				
				// Lock the canvas so we can paint on it
				canvas = holder.lockCanvas();
				
				if (canvas != null) {
					// Paint on the canvas
					graphics.doDraw(canvas);
					// Post update
					holder.unlockCanvasAndPost(canvas);
				}
				
				// Calculate the sleep time using the time passed before drawing
				sleepTime = 1000 / FPS - (System.currentTimeMillis() - startTime);
				
				// If the game is running fast, keep sleeping
				if (sleepTime > 0)
					Thread.sleep(sleepTime);
				// Otherwise, only progress the game without drawing
				else {
					framesSkipped = 0;
					
					// But only wait 5 frames! NO MORE! :@
					while (sleepTime < 0 && framesSkipped++ < MAX_FRAMES_SKIPPED) {
						game.progressGame();
						sleepTime += 1000 / FPS;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}