package com.opisoft.engine.commands;

import android.graphics.Path;

public class MoveCommand extends Command {
	public enum Direction { None, West, North, East, South };
	private Direction _direction;
	private int _dx;
	private int _dy;
	private int _time;
	
	public MoveCommand(int dx, int dy, int time) {
		super(CommandType.Move);
		setDx(dx);
		setDy(dy);
		setTime(time);
	}

	public void setDx(int dx) {
		this._dx = dx;
		updateDirection();
	}

	public int getDx() {
		return _dx;
	}

	public void setDy(int dy) {
		this._dy = dy;
		updateDirection();
	}

	public int getDy() {
		return _dy;
	}

	public void setTime(int time) {
		this._time = time;
	}

	public int getTime() {
		return _time;
	}
	
	protected void updateDirection() {
		if (_dx == 0) {
			if (_dy == 0) setDirection(Direction.None);
			else if (_dy > 0) setDirection(Direction.South);
			else setDirection(Direction.North);
		} else if (_dx > 0)	setDirection(Direction.East);
		else setDirection(Direction.West);
	}

	protected void setDirection(Direction direction) {
		this._direction = direction;
	}

	public Direction getDirection() {
		return _direction;
	}
}
