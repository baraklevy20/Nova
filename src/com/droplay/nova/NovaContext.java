package com.droplay.nova;

// Holds everything together.
// The graphics, the manager, the activity and the resources
public class NovaContext {
	private NovaGraphics graphics;
	private NovaManager manager;
	private NovaActivity activity;
	private NovaResources resources;
	
	public void setGraphics(NovaGraphics graphics) {
		this.graphics = graphics;
	}

	public void setManager(NovaManager manager) {
		this.manager = manager;
	}
	
	public NovaResources getResources() {
		return resources;
	}

	public void setResources(NovaResources resources) {
		this.resources = resources;
	}

	public NovaGraphics getGraphics() {
		return graphics;
	}
	
	public NovaManager getManager() {
		return manager;
	}

	public NovaActivity getActivity() {
		return activity;
	}

	public void setActivity(NovaActivity activity) {
		this.activity = activity;
	}
}
