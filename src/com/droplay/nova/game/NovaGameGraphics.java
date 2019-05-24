package com.droplay.nova.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaGraphics;
import com.droplay.nova.R;
import com.droplay.nova.game.NovaGameManager.ButtonState;
import com.droplay.nova.objects.Crown;
import com.droplay.nova.objects.King;
import com.droplay.nova.objects.NovaObject;
import com.droplay.nova.objects.Portal;
import com.droplay.nova.objects.TimedAnimation;

public class NovaGameGraphics extends NovaGraphics implements NovaConstants {
	private Portal portal;
	private Rect bounds = new Rect();
	private String scoresString;
	private boolean isGameEnded;
	private King king;
	private Crown greenCrown, blueCrown, purpleCrown, orangeCrown;
	private TimedAnimation sweat, mouth;
	private NovaObject pause;
	private int extra;
	private Portal.Type lastPortalType;
	
	public NovaGameGraphics(NovaContext context) {
		super(context);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		if (!isGameEnded) {
			if (!king.isDead() &&
					((AnimationSprite)king.getSprite()).getFrames() == null)
				canvas.drawBitmap(resources.NORMAL_TO_NORMAL, king.getX(), king.getY(), null);
			else
				canvas.drawBitmap(king.getSprite().getFrame(), king.getX(), king.getY(), null);
			
			// Draw face animations
			int frameIndex = ((AnimationSprite)king.getSprite()).getFrameIndex();
			if (king.getCrown() != King.Crown.NORMAL && king.getOldCrown() == King.Crown.NORMAL) {
				// Move the animation a bit according to the current frame
				if (frameIndex == 3)
					extra = -1;
				else if (frameIndex == 4)
					extra = -3;
				else if (frameIndex == 5)
					extra = -5;
				else if (frameIndex == 6)
					extra = -4;
				else if (frameIndex == 7)
					extra = -2;
			}
			else if (king.getCrown() != King.Crown.NORMAL && king.getOldCrown() != King.Crown.NORMAL)
				extra = -2;
			else if (king.getCrown() == King.Crown.NORMAL && king.getOldCrown() != King.Crown.NORMAL) {
				if (frameIndex == 1)
					extra = -1;
				else if (frameIndex == 2)
					extra = -2;
				else if (frameIndex == 3)
					extra = -1;
			}
			
			if (sweat.isVisible())
				canvas.drawBitmap(sweat.getSprite().getFrame(), sweat.getX(), sweat.getY() + extra * height / 800, null);
			
			if (mouth.isVisible())
				canvas.drawBitmap(mouth.getSprite().getFrame(), mouth.getX(), mouth.getY() + extra * height / 800, null);
			
			// Portal
			if (portal.isInScreen(resources)) {
				canvas.drawBitmap(portal.getSprite().getFrame(), portal.getX(), portal.getY(), null);
				canvas.drawBitmap(portal.getParticlesAnimation().getFrame(), portal.getX() - resources.BLUE_PORTAL.getWidth(), portal.getY(), null);
			}
			
			// Draw balloons
			if (king.getOh() != null)
				canvas.drawBitmap(king.getOh().getSprite().getFrame(), king.getOh().getX(), king.getOh().getY(), null);
			
			if (!((NovaGameManager)context.getManager()).isGameRunning())
				canvas.drawBitmap(pause.getSprite().getFrame(), pause.getX(), pause.getY(), null);
			// Draw crowns
			canvas.drawBitmap(blueCrown.getSprite().getFrame(), blueCrown.getX(), blueCrown.getY(), null);
			canvas.drawBitmap(orangeCrown.getSprite().getFrame(), orangeCrown.getX(), orangeCrown.getY(), null);
			canvas.drawBitmap(greenCrown.getSprite().getFrame(), greenCrown.getX(), greenCrown.getY(), null);
			canvas.drawBitmap(purpleCrown.getSprite().getFrame(), purpleCrown.getX(), purpleCrown.getY(), null);
			
			// Draw score
			resources.COMMON_PAINT.setTextSize(getResources().getDimension(R.dimen.score));
			resources.COMMON_PAINT.getTextBounds(scoresString, 0, scoresString.length(), bounds);
			
			if (lastPortalType != null) {
				switch (lastPortalType) {
				case BLUE_PORTAL: resources.COMMON_PAINT.setColor(0xffa8caea); break;
				case GREEN_PORTAL: resources.COMMON_PAINT.setColor(0xffb1d49e); break;
				case ORANGE_PORTAL: resources.COMMON_PAINT.setColor(0xffd68e93); break;
				case PURPLE_PORTAL: resources.COMMON_PAINT.setColor(0xffc86fb0); break;
				}
				canvas.drawText(scoresString, width / 2 - bounds.right / 2 + getResources().getDimension(R.dimen.one_dp), height * 67 / 800 - bounds.top, context.getResources().COMMON_PAINT);
			}
			resources.COMMON_PAINT.setColor(0xFFFFFFFF);
			canvas.drawText(scoresString, width / 2 - bounds.right / 2, height * 67 / 800 - bounds.top, context.getResources().COMMON_PAINT);
		}
		// If the game is over
		else {
			ButtonState buttonState = ((NovaGameManager)context.getManager()).getButtonState();
			
			if (buttonState == ButtonState.NEW_GAME)
				canvas.drawBitmap(resources.PLAY_AGAIN_PRESSED, width / 2 - resources.SHARE.getWidth() / 2 - width * 26 / 480 - resources.PLAY_AGAIN.getWidth(), height * 453 / 800, null);
			else 	
				canvas.drawBitmap(resources.PLAY_AGAIN, width / 2 - resources.SHARE.getWidth() / 2 - width * 26 / 480 - resources.PLAY_AGAIN.getWidth(), height * 453 / 800, null);
			
			if (buttonState == ButtonState.SHARE)
				canvas.drawBitmap(resources.SHARE_PRESSED, width / 2 - resources.SHARE.getWidth() / 2, height * 453 / 800, null);
			else
				canvas.drawBitmap(resources.SHARE, width / 2 - resources.SHARE.getWidth() / 2, height * 453 / 800, null);
			if (buttonState == ButtonState.SCORES)
				canvas.drawBitmap(resources.SCORES_PRESSED, width / 2 - resources.SHARE.getWidth() / 2 + width * 26 / 480 + resources.PLAY_AGAIN.getWidth(), height * 453 / 800, null);
			else
				canvas.drawBitmap(resources.SCORES, width / 2 - resources.SHARE.getWidth() / 2 + width * 26 / 480 + resources.PLAY_AGAIN.getWidth(), height * 453 / 800, null);
			
			// Draw big score
			resources.COMMON_PAINT.setTextSize(getResources().getDimension(R.dimen.score));
			resources.COMMON_PAINT.setColor(0xFFFFFFFF);
			resources.COMMON_PAINT.getTextBounds(scoresString, 0, scoresString.length(), bounds);
			canvas.drawText(scoresString, width / 2 - bounds.right / 2, height * 403 / 800, resources.COMMON_PAINT);

			if (((NovaGameManager)context.getManager()).isNewBestScore())
				canvas.drawBitmap(resources.NEW_BEST, width / 2 - resources.NEW_BEST.getWidth() / 2,
						height * 285 / 800, null);
			else {
				canvas.drawBitmap(resources.NO_NEW_BEST, width / 2 - resources.NO_NEW_BEST.getWidth() / 2,
						height * 285 / 800, null);
			
				// Draw small score (best score)
				resources.SMALL_PAINT.setTextSize(getResources().getDimension(R.dimen.best_score));
				resources.SMALL_PAINT.setColor(0xffd7d7d7);
				resources.SMALL_PAINT.getTextBounds(String.valueOf(((NovaGameManager)context.getManager()).getBestScore()), 0, String.valueOf(((NovaGameManager)context.getManager()).getBestScore()).length(), bounds);
				canvas.drawText(String.valueOf(((NovaGameManager)context.getManager()).getBestScore()), width / 2 - bounds.right / 2, height * 331 / 800, resources.SMALL_PAINT);
			}
			// Draw "your score"
			resources.SMALL_PAINT.setTextSize(getResources().getDimension(R.dimen.your_score));
			resources.SMALL_PAINT.setColor(0xFFFFFFFF);
			resources.SMALL_PAINT.getTextBounds("YOUR SCORE", 0, "YOUR SCORE".length(), bounds);
			canvas.drawText("YOUR SCORE", width / 2 - bounds.right / 2, height * 232 / 800, resources.SMALL_PAINT);
		}
	}
	
	public void setLastPortalType(Portal.Type lastPortalType) {
		this.lastPortalType = lastPortalType;
	}
	
	public void setGameEnded(boolean isGameEnded) {
		this.isGameEnded = isGameEnded;
	}
	
	public void setScore(int score) {
		scoresString = String.valueOf(score);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		setScore(0);
		isGameEnded = false;
		lastPortalType = null;
		portal = ((GameDynamicScreen)dynamicScreen).getCurrentPortal();
		king = ((GameDynamicScreen)dynamicScreen).getKing();
		greenCrown = ((NovaGameManager)context.getManager()).getCrowns()[King.Crown.GREEN.ordinal()];
		blueCrown = ((NovaGameManager)context.getManager()).getCrowns()[King.Crown.BLUE.ordinal()];
		purpleCrown = ((NovaGameManager)context.getManager()).getCrowns()[King.Crown.PURPLE.ordinal()];
		orangeCrown = ((NovaGameManager)context.getManager()).getCrowns()[King.Crown.ORANGE.ordinal()];
		sweat = king.getSweat();
		mouth = king.getMouth();
		pause = ((NovaGameManager)context.getManager()).getPause();
	}
}
