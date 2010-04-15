package com.opisoft.gui;

import com.google.gson.JsonObject;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class MapObject implements Cloneable {
	private String _name;
	private Point _pos;
	private Integer _width;
	private Integer _height;
	private String _type;
	
	protected MapObject(String name) {
		_name = name.toLowerCase();
	}	
	
	public void setPos(Point pos) {
		_pos = pos;
	}
	
	public Point pos() {
		return _pos;
	}
	
	protected MapObject(MapObject other) {
		_name = other._name;
		_pos = other._pos;
		_width = other._width;
		_height = other._height;
	}
	
	public boolean initFromJson(JsonObject obj) {
		Point position = new Point();
		JsonObject posObj = obj.get("position").getAsJsonObject();

		if (posObj != null) {
			position.x = posObj.get("x").getAsInt();
			position.y = posObj.get("y").getAsInt();
		}
		setPos(position);		
		
		_width = 0;
		_height = 0;
		JsonObject sizeObj = obj.get("size").getAsJsonObject();
		
		if (sizeObj != null) {
			_width = sizeObj.get("width").getAsInt();
			_height = sizeObj.get("height").getAsInt();
		}
		return true;
	}
	
	abstract public MapObject clone();
}
