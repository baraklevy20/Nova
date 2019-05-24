package com.droplay.nova.opening;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaGraphics;
import com.droplay.nova.R;
import com.droplay.nova.objects.NovaObject;

public class NovaOpeningGraphics extends NovaGraphics {
	private NovaObject logo;
	private Rect bounds, bounds2;
	private String currentString;
	
	public NovaOpeningGraphics(NovaContext context) {
		super(context);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		canvas.drawBitmap(logo.getSprite().getFrame(), logo.getX(), logo.getY(), null);
		
		currentString = "DROPLAY";
		resources.SMALL_PAINT.setTextSize(getResources().getDimension(R.dimen.logo_droplay));
		resources.SMALL_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds);
		resources.SMALL_PAINT.setColor(0xFFFFFFFF);
		canvas.drawText(currentString,
				width / 2 - bounds.right / 2,
				logo.getY() - bounds.top + ((AnimationSprite)logo.getSprite()).getFrames().getHeight() + height * 50 / 840,
				resources.SMALL_PAINT);
		
		currentString = "ARCADE.";
		resources.SMALL_PAINT.setTextSize(getResources().getDimension(R.dimen.logo_arcade));
		resources.SMALL_PAINT.getTextBounds(currentString, 0, currentString.length(), bounds2);
		resources.SMALL_PAINT.setColor(0xFF1C9BFF);
		canvas.drawText(currentString,
				width / 2 - bounds.right / 2,
				logo.getY() - bounds.top - bounds2.top + ((AnimationSprite)logo.getSprite()).getFrames().getHeight() + height * 50 / 840 + height * 10 / 840,
				resources.SMALL_PAINT);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		logo = ((NovaOpeningManager)context.getManager()).getLogo();
		bounds = new Rect();
		bounds2 = new Rect();
	}
}