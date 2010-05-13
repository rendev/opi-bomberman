package com.opisoft.gui;

import com.google.gson.JsonObject;
import com.stickycoding.Rokon.DynamicObject;
import com.stickycoding.Rokon.Sprite;

import android.graphics.PointF;

public abstract class MapObject implements Cloneable {
	private String _name;
	private String _assetsPath;
	private Sprite _sprite;
	
	enum RepeatType {
		None,
		Horizontal,
		Vertical
	}
	
	protected MapObject(String name) {
		_name = name.toLowerCase();				
	}	
	
	public String assetsPath() {
		return _assetsPath;
	}
	
	public void setAssetsPath(String path) {
		_assetsPath = path;
	}
	
	public void setPos(PointF pos) {
		if (_sprite != null)
			_sprite.setXY(pos.x, pos.y);		
	}
	
	public PointF pos() {
		if (_sprite != null)
			return new PointF(_sprite.getX(),_sprite.getY());
		else return new PointF();
	}
	
	public float width() {
		if (_sprite != null)
			return _sprite.getWidth();
		else return 0;
	}
	
	public float height() {
		if (_sprite != null)
			return _sprite.getHeight();
		else return 0;
	}

	public void setWidthHeight(float width, float height) {
		if (_sprite != null) {
			_sprite.setWidth(width);
			_sprite.setHeight(height);
		}
	}
		
	protected MapObject(MapObject other) {
		_name = other._name;
		_assetsPath = other._assetsPath;
	}
	
	public boolean initFromJson(JsonObject obj) {
		setSprite(initSprite());
		PointF position = new PointF();
		JsonObject posObj = obj.get("position").getAsJsonObject();

		if (posObj != null) {
			position.x = posObj.get("x").getAsInt();
			position.y = posObj.get("y").getAsInt();
		}
		setPos(position);
		
		float width = 0;
		float height = 0;
		JsonObject sizeObj = obj.get("size").getAsJsonObject();
		
		if (sizeObj != null) {
			width = sizeObj.get("width").getAsFloat();
			height = sizeObj.get("height").getAsFloat();
		}
		setWidthHeight(width, height);
		return true;
	}
		
	public Sprite sprite() {
		return _sprite;
	}
	
	protected void setSprite(Sprite sprite) {
		_sprite = sprite;
	}
	
	abstract public MapObject clone();
	abstract public Sprite initSprite();
}
