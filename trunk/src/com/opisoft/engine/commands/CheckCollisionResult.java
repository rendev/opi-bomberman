package com.opisoft.engine.commands;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Position;

public class CheckCollisionResult implements ICommandResult {
	private List<Entity> _surroundEntities;
	
	public CheckCollisionResult(List<Entity> surroundEntities) {
		setSurroundEntities(surroundEntities);
	}

	public boolean isEmpty() {
		return _surroundEntities.isEmpty();
	}
	
	public void setSurroundEntities(List<Entity> surroundEntities) {
		this._surroundEntities = surroundEntities;
	}

	public List<Entity> getSurroundEntities() {
		return _surroundEntities;
	}
	
	public boolean isEntityOnLeft(Point ptInCells) {
		for (Entity e : _surroundEntities) {
			if ( ((Position)e.getComponent(ComponentType.Position)).getPosInCells().x == ptInCells.x-1) {
				return true;
			}
		}
		return false;
	}

	public boolean isEntityOnTop(Point ptInCells) {
		for (Entity e : _surroundEntities) {
			if ( ((Position)e.getComponent(ComponentType.Position)).getPosInCells().y == ptInCells.y-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEntityOnRight(Point ptInCells) {
		for (Entity e : _surroundEntities) {
			if ( ((Position)e.getComponent(ComponentType.Position)).getPosInCells().x == ptInCells.x+1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEntityOnBottom(Point ptInCells) {
		for (Entity e : _surroundEntities) {
			if ( ((Position)e.getComponent(ComponentType.Position)).getPosInCells().y == ptInCells.y+1) {
				return true;
			}
		}
		return false;
	}
}
