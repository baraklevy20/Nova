package com.droplay.nova.credits;

import android.os.Bundle;

import com.droplay.nova.NovaActivity;

public class NovaCreditsActivity extends NovaActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		isShowAds = false;
		super.onCreate(savedInstanceState);
		
		// Wait until all of the resources are loaded
		while (!context.getResources().isOpeningDone())
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void setManagerAndGraphics() {
		this.graphicsView = new NovaCreditsGraphics(context);
		this.manager = new NovaCreditsManager(context);
	}
}
