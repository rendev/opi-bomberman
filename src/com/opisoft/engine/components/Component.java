package com.opisoft.engine.components;

import com.google.gson.JsonObject;

public class Component {
	private String _assetsPath;
	
	boolean initFromJson(JsonObject object) {
		return false;
	}

	public void setAssetsPath(String assetsPath) {
		this._assetsPath = assetsPath;
	}

	public String getAssetsPath() {
		return _assetsPath;
	}
	
	
}
