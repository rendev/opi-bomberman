package com.opisoft.engine.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Move extends Component {
	private int _speed;
	private int _acceleration;
		
	public Move() {		
		super(ComponentType.Move);
	}
	
	public Move(Move other) {
		super(ComponentType.Move);
		_speed = other._speed;
		_acceleration = other._acceleration;
	}
	
	public boolean initFromJson(JsonObject object) {
		boolean res = false;
		JsonElement el = object.get("speed");
		
		if (el != null) {
			_speed = el.getAsInt();
			
			el = object.get("accel");
			
			if (el != null) {
				_acceleration = el.getAsInt();
			}
			res = true;
		}
		return res;
	}
	
	@Override
	public Component clone() {
		return new Move(this);
	}

	@Override
	public void release() {
		
	}

}
