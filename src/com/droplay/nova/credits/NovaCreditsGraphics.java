package com.droplay.nova.credits;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaGraphics;
import com.droplay.nova.R;

public class NovaCreditsGraphics extends NovaGraphics {
	private Rect bounds;
	private final String CREDITS = "CREDITS";
	private final String DOTS = ". . . . . . . .";
	private final String MUSIC_BY = "MUSIC BY";
	private final String ROLE = " ROLE MUSIC - L3GO";
	private final String THANK1 = "THANK YOU VERY MUCH FOR LETTING US USE";
	private final String THANK2 = "AND EDIT YOUR BEAUTIFUL WORK";
	private final String DEVELOP_BY = "DEVELOPED BY:";
	private final String BARACK = "BARACK LEVY";
	private final String DESIGN_BY = "DESIGN AND SOUND EFFECTS BY:";
	private final String STAV = "STAV VIIRAX";
	private final String ALPHA_TESTERS = "ALPHA TESTERS:";
	private final String NOY = "NOY LEVY";
	private final String OHAD = "OHAD COHEN";
	private final String ROMAN = "ROMAN BARSHAY";
	private final String OBAMA = "OBAMA";
	private final String KOREAN_GUY = "A KOREAN GUY";
	private final String GOLDEN_FISH = "STAV'S GOLDEN FISH";
	
	private int logoX;
	private int logoY;
	private int binaryX;
	private int binaryY;
	private int musicByX;
	private int musicByY;
	
	// Y
	private int credits;
	private int dots;
	private int thank1;
	private int thank2;
	private int develop;
	private int barack;
	private int design;
	private int stav;
	private int alpha;
	private int noy;
	private int ohad;
	private int roman;
	private int obama;
	private int korean;
	private int golden;
	
	public NovaCreditsGraphics(NovaContext context) {
		super(context);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		canvas.drawBitmap(resources.NOVA_LOGO, logoX, logoY, null);
		canvas.drawBitmap(resources.BINARY, binaryX, binaryY, null);
		drawTextInMiddle(canvas, CREDITS, credits, R.dimen.credits_big, 0xFFFFFFFF);
		drawTextInMiddle(canvas, DOTS, dots, R.dimen.credits_big, 0xFFFFFFFF);
		resources.SMALL_PAINT.setTextSize(getResources().getDimension(R.dimen.credits_normal));
		canvas.drawText(MUSIC_BY, musicByX, musicByY, resources.SMALL_PAINT);
		resources.SMALL_PAINT.getTextBounds(MUSIC_BY, 0, MUSIC_BY.length(), bounds);
		resources.SMALL_PAINT.setColor(0xFFA276D9);
		canvas.drawText(ROLE, musicByX + bounds.right, musicByY, resources.SMALL_PAINT);
		drawTextInMiddle(canvas, THANK1, thank1, R.dimen.credits_small, 0xFFA6A5A8);
		drawTextInMiddle(canvas, THANK2, thank2, R.dimen.credits_small, 0xFFA6A5A8);
		drawTextInMiddle(canvas, DEVELOP_BY, develop, R.dimen.credits_normal, 0xFFFFFFFF);
		drawTextInMiddle(canvas, BARACK, barack, R.dimen.credits_normal, 0xFF6FAD79);
		drawTextInMiddle(canvas, DESIGN_BY, design, R.dimen.credits_normal, 0xFFFFFFFF);
		drawTextInMiddle(canvas, STAV, stav, R.dimen.credits_normal, 0xFFCB816D);
		drawTextInMiddle(canvas, ALPHA_TESTERS, alpha, R.dimen.credits_normal, 0xFFFFFFFF);
		drawTextInMiddle(canvas, NOY, noy, R.dimen.credits_small, 0xFFFFFFFF);
		drawTextInMiddle(canvas, OHAD, ohad, R.dimen.credits_small, 0xFFFFFFFF);
		drawTextInMiddle(canvas, ROMAN, roman, R.dimen.credits_small, 0xFFFFFFFF);
		drawTextInMiddle(canvas, OBAMA, obama, R.dimen.credits_small, 0xFFFFFFFF);
		drawTextInMiddle(canvas, KOREAN_GUY, korean, R.dimen.credits_small, 0xFFFFFFFF);
		drawTextInMiddle(canvas, GOLDEN_FISH, golden, R.dimen.credits_small, 0xFFFFFFFF);
	}
	
	private void drawTextInMiddle(Canvas canvas, String str, int y, int dimenId, int color) {
		resources.SMALL_PAINT.setTextSize(getResources().getDimension(dimenId));
		resources.SMALL_PAINT.setColor(color);
		resources.SMALL_PAINT.getTextBounds(str, 0, str.length(), bounds);
		canvas.drawText(str, width / 2 - bounds.right / 2, y, resources.SMALL_PAINT);
	}

	@Override
	public void initialize() {
		super.initialize();
		bounds = new Rect();
		
		logoX = width / 2 - resources.NOVA_LOGO.getWidth() / 2;
		logoY = height * 82 / 800;
		binaryX = width / 2 - resources.BINARY.getWidth() / 2;
		musicByX = width * 64 / 480;
		binaryY = height * 566 / 800;
		credits = height * 221 / 800;
		dots = height * 247 / 800;
		musicByY = height * 319 / 800;
		thank1 = height * 343 / 800;
		thank2 = height * 360 / 800;
		develop = height * 418 / 800;
		barack = height * 447 / 800;
		design = height * 495 / 800;
		stav = height * 524 / 800;
		alpha = height * 596 / 800;
		noy = height * 687 / 800;
		ohad = height * 704 / 800;
		roman = height * 721 / 800;
		obama = height * 738 / 800;
		korean = height * 755 / 800;
		golden = height * 772 / 800;
	}
}