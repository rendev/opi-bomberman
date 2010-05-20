package com.opisoft.engine.commands;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Position;

public class CheckCollisionCommand extends Command {
	private Point _pos;
	private MoveCommand.Direction _dir;
	
	public CheckCollisionCommand(Point pos, MoveCommand.Direction direction) {
		super(CommandType.CheckCollision);
		setPos(pos);
		_dir = direction;
	}
	
	public CheckCollisionCommand(Entity e) {
		super(CommandType.CheckCollision);
		setPos(((Position)e.getComponent(ComponentType.Position)).getPosInCells());
	}

	public void setPos(Point pos) {
		this._pos = pos;
	}

	public Point getPos() {
		return _pos;
	}
	
	public MoveCommand.Direction getDirection() {
		return _dir;
	}
}
