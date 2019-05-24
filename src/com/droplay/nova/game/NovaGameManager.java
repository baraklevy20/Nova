package com.droplay.nova.game;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaApplication;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaManager;
import com.droplay.nova.R;
import com.droplay.nova.objects.Crown;
import com.droplay.nova.objects.Crown.ClickState;
import com.droplay.nova.objects.King;
import com.droplay.nova.objects.NovaObject;

public class NovaGameManager extends NovaManager implements NovaConstants {
	private boolean isGameRunning;
	private boolean isGameEnded;
	private boolean isNewBestScore;
	private int score, bestScore;
	private long lastClickTime;
	private float lastX;
	private Crown[] crowns;
	private ButtonState buttonState;
	private King.Crown selectedCrown, lastSelectedCrown;
	private NovaObject pause;
	
	public enum ButtonState {
		NON,
		NEW_GAME,
		SHARE,
		SCORES
	}
	
	public NovaGameManager(NovaContext context) {
		super(context);
	}
	
	public ButtonState getButtonState() {
		return buttonState;
	}
	public boolean isNewBestScore() {
		return isNewBestScore;
	}
	
	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
	
	@Override
	public void initialize() {
		super.initialize();
//		context.getActivity().readParameters();
		dynamicScreen = new GameDynamicScreen(context);
		int size = 105 * context.getGraphics().getWidth() / 480;
		crowns = new Crown[5];
		crowns[King.Crown.BLUE.ordinal()] = new Crown(context.getResources().BLUE_CROWN, context.getGraphics().getWidth() * 55 / 480, context.getGraphics().getHeight() * 648 / 800);
		crowns[King.Crown.ORANGE.ordinal()] = new Crown(context.getResources().ORANGE_CROWN, context.getGraphics().getWidth() * 55 / 480 + size, context.getGraphics().getHeight() * 648 / 800);
		crowns[King.Crown.GREEN.ordinal()] = new Crown(context.getResources().GREEN_CROWN, context.getGraphics().getWidth() * 55 / 480 + size * 2, context.getGraphics().getHeight() * 648 / 800);
		crowns[King.Crown.PURPLE.ordinal()] = new Crown(context.getResources().PURPLE_CROWN, context.getGraphics().getWidth() * 55 / 480 + size * 3, context.getGraphics().getHeight() * 648 / 800);
		pause = new NovaObject(new AnimationSprite(context.getResources().PAUSE));
		initiateNewRun();
	}
	
	public void initiateNewRun() {
		score = 0;
		selectedCrown = King.Crown.NORMAL;
		lastSelectedCrown = King.Crown.NORMAL;
		buttonState = ButtonState.NON;
		isNewBestScore = false;
		((AnimationSprite)crowns[King.Crown.BLUE.ordinal()].getSprite()).setFrameIndex(0);
		crowns[King.Crown.BLUE.ordinal()].setClickState(ClickState.NON_CLICKED);
		((AnimationSprite)crowns[King.Crown.ORANGE.ordinal()].getSprite()).setFrameIndex(0);
		crowns[King.Crown.ORANGE.ordinal()].setClickState(ClickState.NON_CLICKED);
		((AnimationSprite)crowns[King.Crown.GREEN.ordinal()].getSprite()).setFrameIndex(0);
		crowns[King.Crown.GREEN.ordinal()].setClickState(ClickState.NON_CLICKED);
		((AnimationSprite)crowns[King.Crown.PURPLE.ordinal()].getSprite()).setFrameIndex(0);
		crowns[King.Crown.PURPLE.ordinal()].setClickState(ClickState.NON_CLICKED);
		pause.setX(((GameDynamicScreen)dynamicScreen).getKing().getX() + 51 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96);
		pause.setY(((GameDynamicScreen)dynamicScreen).getKing().getY() - 73 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137);
		isGameEnded = false;
	}

	@Override
	public void onPause() {
		isGameRunning = false;
		
		if (score > bestScore) {
			bestScore = score;
			context.getActivity().writeParameters();
		}
	}

	@Override
	public void onResume() {
		isGameRunning = false;
		buttonState = ButtonState.NON;
		if (pause != null) {
			((AnimationSprite)pause.getSprite()).resetAnimation();
			((AnimationSprite)pause.getSprite()).setReverse(false);
			pause.setX(((GameDynamicScreen)dynamicScreen).getKing().getX() + 51 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96);
			pause.setY(((GameDynamicScreen)dynamicScreen).getKing().getY() - 73 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137);
		}
	}

	@Override
	public void progressGame() {
		dynamicScreen.onGameProgress();
		staticScreen.onGameProgress();
		
		for (Crown c : crowns)
			if (c != null) // For normal
				c.onGameProgress();
	}

	public Crown[] getCrowns() {
		return crowns;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public void handleTouch(MotionEvent event) {
		super.handleTouch(event);
		float x = event.getX();
		float y = event.getY();
		int width = context.getGraphics().getWidth();
		int height = context.getGraphics().getHeight();
		King king = ((GameDynamicScreen)dynamicScreen).getKing();
		int size = 101 * width / 480; // Size of each crown + spacing
		int spacingSize = 17 * width / 480;
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!isGameRunning) {
				if (isGameEnded && buttonState == ButtonState.NON) {
					if (y >= height * 453 / 800 && y < height * 453 / 800 + context.getResources().SHARE.getHeight()) {
						if (x >= width / 2 - context.getResources().SHARE.getWidth() / 2 - width * 26 / 480 - context.getResources().PLAY_AGAIN.getWidth() &&
								x < width / 2 - context.getResources().SHARE.getWidth() / 2 - width * 26 / 480) {
							buttonState = ButtonState.NEW_GAME;
							if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
								((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
						}
						else if (x >= width / 2 - context.getResources().SHARE.getWidth() / 2 &&
								x < width / 2 + context.getResources().SHARE.getWidth() / 2) {
							buttonState = ButtonState.SHARE;
							if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
								((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
						}
						else if (x >= width / 2 - context.getResources().SHARE.getWidth() / 2 + width * 26 / 480 + context.getResources().PLAY_AGAIN.getWidth() &&
								x < width / 2 - context.getResources().SHARE.getWidth() / 2 + width * 26 / 480 + 2 * context.getResources().PLAY_AGAIN.getWidth()) {
							buttonState = ButtonState.SCORES;
							if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
								((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
						}
					}
				}
				else {
					((AnimationSprite)pause.getSprite()).setReverse(true);
				}
			}
		}
		// Crowns
		if (isGameRunning && !king.isDead() && (event.getAction() == MotionEvent.ACTION_DOWN || (event.getAction() == MotionEvent.ACTION_MOVE && Math.abs(lastX - x) * 1080 / height / (System.currentTimeMillis() - lastClickTime) <= 2.8))) {
			if (y >= height * 648 / 800 - spacingSize && y < height * 648 / 800 + context.getResources().BLUE_CROWN.getHeight() + spacingSize) {
				if (x >= width * 55 / 480 - spacingSize && x < width * 55 / 480 + context.getResources().BLUE_CROWN.getHeight() + spacingSize) {
					lastSelectedCrown = selectedCrown;
					selectedCrown = King.Crown.BLUE;
				}
				else if (x >= width * 55 / 480 + size - spacingSize && x < width * 55 / 480 + size + context.getResources().BLUE_CROWN.getHeight() + spacingSize) {
					lastSelectedCrown = selectedCrown;
					selectedCrown = King.Crown.ORANGE;
				}
				else if (x >= width * 55 / 480 + size * 2 - spacingSize && x < width * 55 / 480 + size * 2 + context.getResources().BLUE_CROWN.getHeight() + spacingSize) {
					lastSelectedCrown = selectedCrown;
					selectedCrown = King.Crown.GREEN;
				}
				else if (x >= width * 55 / 480 + size * 3 - spacingSize && x < width * 55 / 480 + size * 3 + context.getResources().BLUE_CROWN.getHeight() + spacingSize) {
					lastSelectedCrown = selectedCrown;
					selectedCrown = King.Crown.PURPLE;
				}
			}
			
			if (selectedCrown != King.Crown.NORMAL && lastSelectedCrown != selectedCrown) {
				if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
					((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.turn);
				king.setCrown(selectedCrown);
				crowns[selectedCrown.ordinal()].setClickState(ClickState.DOWN);
			}
			
			if (lastSelectedCrown != King.Crown.NORMAL && lastSelectedCrown != selectedCrown)
				crowns[lastSelectedCrown.ordinal()].setClickState(ClickState.UP);
			
			lastX = x;
			lastClickTime = System.currentTimeMillis();
		}
		else if (isGameRunning && !king.isDead() && event.getAction() == MotionEvent.ACTION_UP) {
			if (selectedCrown != King.Crown.NORMAL) {
				crowns[selectedCrown.ordinal()].setClickState(ClickState.UP);
				selectedCrown = King.Crown.NORMAL;
				lastSelectedCrown = King.Crown.NORMAL;
				king.setCrown(selectedCrown);
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP && isGameEnded) {
			switch (buttonState) {
			case NEW_GAME:
				((GameDynamicScreen)dynamicScreen).initiate();
				initiateNewRun();
				context.getGraphics().initialize();
				buttonState = ButtonState.NON;
				break;
			case SHARE:
				Bitmap bitmap = Bitmap.createBitmap(context.getResources().SHARE_PIC.getWidth(),
						context.getResources().SHARE_PIC.getHeight(), Config.ARGB_8888);
			    Canvas canvas = new Canvas(bitmap);
			    canvas.drawBitmap(context.getResources().SHARE_PIC, 0, 0, null);
			    String scoresString = String.valueOf(score);
			    Rect bounds = new Rect();
			    context.getResources().COMMON_PAINT.setTextSize(context.getGraphics().getResources().getDimension(R.dimen.score));
			    context.getResources().COMMON_PAINT.getTextBounds(scoresString, 0, scoresString.length(), bounds);
			    canvas.drawText(scoresString, bitmap.getWidth() / 2 - bounds.right / 2, 121 * bitmap.getHeight() / 180, context.getResources().COMMON_PAINT);

				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				File f = new File(context.getActivity().getFilesDir() + File.separator + "temporary_file.jpeg");
				try {
				    f.createNewFile();
				    FileOutputStream fo = new FileOutputStream(f);
				    fo.write(bytes.toByteArray());
				    fo.close();
				} catch (IOException e) {                       
				        e.printStackTrace();
				}
				
				
//				Uri uri = Uri.parse("file://" + f.getAbsolutePath());
				
				// Share intent
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("image/jpeg");
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				shareIntent.putExtra(Intent.EXTRA_STREAM, CachedFileProvider.CONTENT_URI);
//				shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://" + CachedFileProvider.AUTHORITY + File.separator + "img" + File.separator + "temporary_file.jpeg"));
//				shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Nova!");
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi look at my score at Nova!");
			    ((NovaGameActivity)context.getActivity()).startActivityForResult(Intent.createChooser(shareIntent, "Share via"), REQUEST_CODE_SHARE);
				break;
			case SCORES:
				context.getActivity().showLeaderboard();
				break;
			case NON:
			}
		}
	}

	public void onPassed() {
		score++;
		if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
			((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.portal_pass);
		((NovaGameGraphics)context.getGraphics()).setScore(score);
	}

	public void onCollision() {
		if (score > bestScore) {
			isNewBestScore = true;
			bestScore = score;
			context.getActivity().writeParameters();
		}
		
		((AnimationSprite)pause.getSprite()).resetAnimation();
		((AnimationSprite)pause.getSprite()).setReverse(false);
		isGameRunning = false;
		isGameEnded = true;
		((NovaGameGraphics)context.getGraphics()).setGameEnded(isGameEnded);
	}

	public boolean isGameRunning() {
		return isGameRunning;
	}

	public void setButtonState(ButtonState buttonState) {
		this.buttonState = buttonState;
	}

	public int getBestScore() {
		return bestScore;
	}

	public NovaObject getPause() {
		return pause;
	}

	public void setGameRunning(boolean isGameRunning) {
		this.isGameRunning = isGameRunning;
	}
}
