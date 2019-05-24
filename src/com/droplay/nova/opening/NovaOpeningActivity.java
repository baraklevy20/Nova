package com.droplay.nova.opening;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.droplay.nova.NovaActivity;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.menu.NovaMenuActivity;

public class NovaOpeningActivity extends NovaActivity implements NovaConstants {

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
	protected void onStart() {
		super.onStart();
		
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
	                startActivity(new Intent(NovaOpeningActivity.this, NovaMenuActivity.class));
	                finish();
            }
        }, 2500);
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void setManagerAndGraphics() {
		this.graphicsView = new NovaOpeningGraphics(context);
		this.manager = new NovaOpeningManager(context);
	}
}
