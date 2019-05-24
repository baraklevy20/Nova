package com.droplay.nova;

import com.droplay.nova.objects.NovaObject;
import com.droplay.nova.objects.HiddenStar;

// The static screen contains non moving objects
public class StaticScreen extends NovaScreen implements ProcessGameListener {
	private HiddenStar[] hiddenStars;
	private NovaObject[] shinyStars;
	
	public StaticScreen(NovaContext context) {
		super(context);
		hiddenStars = ((NovaApplication)context.getActivity().getApplication()).getHiddenStars();
		shinyStars = ((NovaApplication)context.getActivity().getApplication()).getShinyStars();
	}
	
	@Override
	public void onGameProgress() {
		for (NovaObject o : shinyStars)
			o.onGameProgress();
		
		for (HiddenStar h : hiddenStars)
			h.onGameProgress();
	}
}
