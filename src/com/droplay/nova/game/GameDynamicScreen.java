package com.droplay.nova.game;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.DynamicScreen;
import com.droplay.nova.NovaApplication;
import com.droplay.nova.NovaContext;
import com.droplay.nova.R;
import com.droplay.nova.objects.King;
import com.droplay.nova.objects.Portal;

public class GameDynamicScreen extends DynamicScreen {
	private Portal portal;
	private NovaContext context;
	private King king;
	private int topBound;
	private int bottomBound;
	private float nextPortalX, nextPortalY, nextPortalVelocity;
	
	public GameDynamicScreen(NovaContext context) {
		super(context);
		this.context = context;
		
		initiate();
	}
	
	public void initiate() {
		nextPortalX = -2 * context.getResources().GREEN_PORTAL.getWidth();
		topBound = context.getGraphics().getHeight() * 15 / 100;
		bottomBound = context.getGraphics().getHeight() * 78 / 100;
		portal = new Portal(context.getResources(), context.getGraphics().getWidth(), context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth(), 
				context.getGraphics().getWidth() + (float)(Math.random() * context.getGraphics().getWidth()),
				topBound + (float)(Math.random() * (bottomBound - topBound - context.getResources().GREEN_PORTAL.getHeight())),
				Portal.Type.values()[(int)(Math.random() * 4)], -PORTAL_SPEED);
		king = new King(context, context.getGraphics().getWidth() / 5,
				context.getGraphics().getHeight() / 2 - context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 2, topBound, bottomBound, portal);
	}
	
	public Portal getCurrentPortal() {
		return portal;
	}
	
	public King getKing() {
		return king;
	}

	@Override
	public void onGameProgress() {
		super.onGameProgress();
		((NovaGameManager)context.getManager()).getPause().onGameProgress();
		
		if (((AnimationSprite)((NovaGameManager)context.getManager()).getPause().getSprite()).isReverse() && ((AnimationSprite)((NovaGameManager)context.getManager()).getPause().getSprite()).isDone())
			((NovaGameManager)context.getManager()).setGameRunning(true);
		
		if (((NovaGameManager)context.getManager()).isGameRunning()) {
			// King
			king.onGameProgress();

			if (king.isDead() && ((AnimationSprite)king.getSprite()).isDone())
				((NovaGameManager)context.getManager()).onCollision();
			
			// Portal
			portal.onGameProgress();
			
			// If touched the portal
			if (king.getX() + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() >= portal.getX() &&
					king.getX() + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() < portal.getX() - portal.getRealXVelocity()) {
				king.notifyPortalTouched();
			}
			else if (king.getX()  + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() >= portal.getX() + context.getResources().BLUE_PORTAL.getWidth() &&
					king.getX()  + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() < portal.getX() + context.getResources().BLUE_PORTAL.getWidth() - portal.getRealXVelocity()) {
				if (king.getCrown() != King.Crown.BLUE && portal.getPortalType() == Portal.Type.BLUE_PORTAL ||
						king.getCrown() != King.Crown.GREEN && portal.getPortalType() == Portal.Type.GREEN_PORTAL ||
						king.getCrown() != King.Crown.ORANGE && portal.getPortalType() == Portal.Type.ORANGE_PORTAL ||
						king.getCrown() != King.Crown.PURPLE && portal.getPortalType() == Portal.Type.PURPLE_PORTAL) {
					if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
						((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.die);
					king.notifyDeath();
				}
				else {
					((NovaGameGraphics)context.getGraphics()).setLastPortalType(portal.getPortalType());
					((NovaGameManager)context.getManager()).onPassed();
					
					if (((NovaGameManager)context.getManager()).getScore() == ((NovaGameManager)context.getManager()).getBestScore())
						king.notifyOh();
					else if (((NovaGameManager)context.getManager()).getScore() == ((NovaGameManager)context.getManager()).getBestScore() + 1)
						king.removeOh();
				}
			}
			// If passed the portal
			else if (!king.isDead() && king.getX() >= portal.getX() + context.getResources().BLUE_PORTAL.getWidth() &&
					king.getX() < portal.getX() + context.getResources().BLUE_PORTAL.getWidth() - portal.getRealXVelocity()) {
				
				nextPortalX = context.getGraphics().getWidth() + (float)(Math.random() * context.getGraphics().getWidth());
				nextPortalY = topBound + (float)(Math.random() * (bottomBound - topBound - context.getResources().GREEN_PORTAL.getHeight()));
				nextPortalVelocity = portal.getxVelocity();
				
				if (-portal.getxVelocity() < MAX_PORTAL_SPEED)
					nextPortalVelocity -= BOOST_SPEED;
				else if (-portal.getxVelocity() < SECOND_MAX_PORTAL_SPEED)
					nextPortalVelocity -= BOOST_SPEED / 3.0f;
				
				king.notifyPortalPassed(nextPortalX, nextPortalY, nextPortalVelocity);
			}
			// If the portal is out of the screen
			else if (portal.getX() + context.getResources().BLUE_PORTAL.getWidth() < 0) {
				portal.setX(nextPortalX);
				portal.setY(nextPortalY);
				portal.setPortalType(Portal.Type.values()[(int)(Math.random() * 4)]);
				portal.setxVelocity(nextPortalVelocity);
				switch (portal.getPortalType()) {
				case BLUE_PORTAL: ((AnimationSprite)portal.getSprite()).setFrames(resources.BLUE_PORTAL); portal.getParticlesAnimation().setFrames(resources.BLUE_PARTICLES); break;
				case GREEN_PORTAL: ((AnimationSprite)portal.getSprite()).setFrames(resources.GREEN_PORTAL); portal.getParticlesAnimation().setFrames(resources.GREEN_PARTICLES); break;
				case PURPLE_PORTAL: ((AnimationSprite)portal.getSprite()).setFrames(resources.PURPLE_PORTAL); portal.getParticlesAnimation().setFrames(resources.PURPLE_PARTICLES); break;
				case ORANGE_PORTAL: ((AnimationSprite)portal.getSprite()).setFrames(resources.ORANGE_PORTAL); portal.getParticlesAnimation().setFrames(resources.ORANGE_PARTICLES); break;
				}
				((AnimationSprite)portal.getSprite()).resetAnimation();
			}
		}
	}
}
