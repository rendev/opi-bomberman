package com.opisoft.engine.components;

import com.google.gson.JsonObject;

public abstract class Component implements Cloneable {
	private String _assetsPath;
	private ComponentType _type;
	
	public Component(ComponentType type) {
		_type = type;
	}
	
	public Component(Component other) {
		_assetsPath = other._assetsPath;
		_type = other._type;
	}
	
	public ComponentType type() {
		return _type;
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
