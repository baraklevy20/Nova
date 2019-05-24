package com.droplay.nova.credits;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.MotionEvent;

import com.droplay.nova.NovaApplication;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaManager;
import com.droplay.nova.R;

public class NovaCreditsManager extends NovaManager {
	private Rect musicByBounds, roleMusicBounds;
	private boolean isCameFromWeb;
	public static final String URL = "http://www.freemusicarchive.org/music/Rolemusic/gigs_n_contest/rolemusic_-_gigs_n_contest_-_07_l3go";
	public NovaCreditsManager(NovaContext context) {
		super(context);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		musicByBounds = new Rect();
		roleMusicBounds = new Rect();
		isCameFromWeb = false;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		if (isCameFromWeb) {
			((NovaApplication)context.getActivity().getApplication()).playMusic(R.raw.menu_music);
			isCameFromWeb = false;
		}
	}

	@Override
	public void progressGame() {
		dynamicScreen.onGameProgress();
		staticScreen.onGameProgress();
	}

	@Override
	public void handleTouch(MotionEvent event) {
		super.handleTouch(event);
		float x = event.getX();
		float y = event.getY();
		int width = context.getGraphics().getWidth();
		int height = context.getGraphics().getHeight();
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			context.getResources().SMALL_PAINT.setTextSize(context.getActivity().getResources().getDimension(R.dimen.credits_normal));
			context.getResources().SMALL_PAINT.getTextBounds("MUSIC BY ", 0, "MUSIC BY ".length(), musicByBounds);
			context.getResources().SMALL_PAINT.getTextBounds("ROLE MUSIC - L3GO", 0, "ROLE MUSIC - L3GO".length(), roleMusicBounds);
			
			// If you click on the artist, it opens its website
			if (x >= 64 * width / 480 + musicByBounds.right && x < 64 * width / 480 + musicByBounds.right + roleMusicBounds.right &&
					y <= height * 319 / 800 && y > height * 319 / 800 + roleMusicBounds.top) {
				isCameFromWeb = true;
				((NovaApplication)context.getActivity().getApplication()).releaseMediaPlayers();
				context.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
			}
		}
	}
}
