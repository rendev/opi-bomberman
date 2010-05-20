package com.opisoft.engine.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Size extends Component {
	private int _width;
	private int _height;	
	
	public Size() {
		super(ComponentType.Size);
	}
	
	public Size(Size other) {
		super(other);
		_width = other._width;
		_height = other._height;
	}

	public void setWidth(int width) {
		this._width = width;
	}

	public int getWidth() {
		return _width;
	}

	public void setHeight(int height) {
		this._height = height;
	}

	public int getHeight() {
		return _height;
	}

	@Override
	public void release() {		
	}

	@Override
	public Component clone() {
		return new Size(this);
	}
	
	@Override
	public boolean initFromJson(JsonObject object) {
		JsonElement currElem = object.get("width");
		boolean res = false;
		
		if (currElem != null) {
			_width = currElem.getAsInt();			
			currElem = object.get("height");
			
			if (currElem != null) {
				_height = currElem.getAsInt();
				res = true;
			}
		}
		return res;
	}
}
