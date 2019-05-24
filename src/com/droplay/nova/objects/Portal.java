package com.droplay.nova.objects;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaResources;
import com.droplay.nova.ProcessGameListener;

public class Portal extends NovaMovingObject implements ProcessGameListener {
	private boolean isPassed;
	private AnimationSprite particlesAnimation;
	private Type portalType;
	
	public enum Type {
		BLUE_PORTAL,
		GREEN_PORTAL,
		PURPLE_PORTAL,
		ORANGE_PORTAL
	}
	
	public Portal(NovaResources resources, final int SCREEN_WIDTH, final int KING_WIDTH, float x, float y, Type portalType, float xVelocity) {
		super(new AnimationSprite(), x, y, xVelocity);
		
		this.portalType = portalType;
		particlesAnimation = new AnimationSprite();
		switch (portalType) {
		case BLUE_PORTAL: ((AnimationSprite)sprite).setFrames(resources.BLUE_PORTAL); particlesAnimation.setFrames(resources.BLUE_PARTICLES); break;
		case GREEN_PORTAL: ((AnimationSprite)sprite).setFrames(resources.GREEN_PORTAL); particlesAnimation.setFrames(resources.GREEN_PARTICLES); break;
		case PURPLE_PORTAL: ((AnimationSprite)sprite).setFrames(resources.PURPLE_PORTAL); particlesAnimation.setFrames(resources.PURPLE_PARTICLES); break;
		case ORANGE_PORTAL: ((AnimationSprite)sprite).setFrames(resources.ORANGE_PORTAL); particlesAnimation.setFrames(resources.ORANGE_PARTICLES); break;
		}
	}
	
	public Type getPortalType() {
		return portalType;
	}
	
	public void setPortalType(Type portalType) {
		this.portalType = portalType;
	}
	
	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}
	
	public boolean isPassed() {
		return isPassed;
	}

	public AnimationSprite getParticlesAnimation() {
		return particlesAnimation;
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		particlesAnimation.normalUpdate();
	}
}
