package com.droplay.nova;

import com.droplay.nova.objects.Meteor;
import com.droplay.nova.objects.Meteor.State;
import com.droplay.nova.objects.NovaMovingObject;

// A moving screen (Stars and meteors)
public class DynamicScreen extends NovaScreen implements NovaConstants, ProcessGameListener {
	protected NovaMovingObject[] farStars;
	protected NovaMovingObject[] closerStars;
	protected NovaMovingObject[] nearStars;
	protected Meteor meteor;
	protected NovaResources resources;
	protected NovaContext context;
	
	public DynamicScreen(NovaContext context) {
		super(context);
		this.context = context;
		resources = context.getResources();
		// Get the references for faster use
		farStars = ((NovaApplication)context.getActivity().getApplication()).getFarStars();
		closerStars = ((NovaApplication)context.getActivity().getApplication()).getCloserStars();
		nearStars = ((NovaApplication)context.getActivity().getApplication()).getNearStars();
		meteor = ((NovaApplication)context.getActivity().getApplication()).getMeteor();
	}
	
	@Override
	public void onGameProgress() {
		// Move all of the stars
		
		for (NovaMovingObject o : farStars) {
			o.onGameProgress();
			
			if (o.isOutOfScreen(resources))
				o.setX(o.getStartX() + context.getGraphics().getWidth());
		}
		
		for (NovaMovingObject o : closerStars) {
			o.onGameProgress();
			
			if (o.isOutOfScreen(resources))
				o.setX(o.getStartX() + context.getGraphics().getWidth());
		}
		
		for (NovaMovingObject o : nearStars) {
			o.onGameProgress();
			
			if (o.isOutOfScreen(resources))
				o.setX(o.getStartX() + context.getGraphics().getWidth());
		}
		
		meteor.onGameProgress();
		
		if (meteor.isOutOfScreen(resources)) {
			((AnimationSprite)meteor.getSprite()).resetAnimation();
			meteor.setOldTime(System.currentTimeMillis());
			meteor.setState(State.NOT_VISIBLE);
			meteor.setX((float)(Math.random() * context.getGraphics().getWidth()));
			meteor.setY((float)(Math.random() * context.getGraphics().getHeight()));
		}
	}
}
