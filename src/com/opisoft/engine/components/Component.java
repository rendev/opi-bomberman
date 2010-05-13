package com.opisoft.engine.components;

import com.google.gson.JsonObject;

public abstract class Component implements Cloneable {
	private String _assetsPath;
	
	public Component() {		
	}
	
	public Component(Component other) {
		_assetsPath = other._assetsPath;
	}
	
	public boolean initFromJson(JsonObject object) {
		return false;
	}

	public void setAssetsPath(String assetsPath) {
		this._assetsPath = assetsPath;
	}

	public String getAssetsPath() {
		return _assetsPath;
	}
	
	public abstract void release();	
	public abstract Component clone();
}
