package com.opisoft.engine.components;

import com.google.gson.JsonObject;

import android.graphics.Point;

public class Position extends Component {
	private Point _posInCells;

	public void setPosInCells(Point posInCells) {
		this._posInCells = posInCells;
	}

	public Point getPosInCells() {
		return _posInCells;
	}

	public boolean initFromJson(JsonObject object) {
		Integer xCells = object.get("x").getAsInt();
		Integer yCells = object.get("y").getAsInt();
		_posInCells = new Point(xCells,yCells);
		return true;
	}
}
