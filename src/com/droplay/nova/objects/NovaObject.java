package com.droplay.nova.objects;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaSprite;
import com.droplay.nova.ProcessGameListener;

public class NovaObject implements ProcessGameListener {
	protected NovaSprite sprite;
	protected float x, y;
	
	public NovaSprite getSprite() {
		return sprite;
	}
	
	public NovaObject(NovaSprite sprite, float x, float y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	public NovaObject(AnimationSprite sprite) {
		this.sprite = sprite;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setNovaSprite(NovaSprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void onGameProgress() {
		if (sprite != null && sprite instanceof AnimationSprite && ((AnimationSprite)sprite).getFrames() != null)
			((AnimationSprite)sprite).normalUpdate();
	}
}
