package com.droplay.nova;

// Every object that wants to progress itself in the game loop will implement this interface
public interface ProcessGameListener {
	public void onGameProgress();
}
