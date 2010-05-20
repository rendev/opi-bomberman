package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.commands.CheckCollisionCommand;
import com.opisoft.engine.commands.CheckCollisionResult;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.MoveCommand.Direction;

public class CollisionService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();
	private static final Map<Direction,int[]> _collisionProbes = new HashMap<Direction,int[]>();

	public CollisionService() {
		_cmds.add(CheckCollisionCommand.class);
		_collisionProbes.put(Direction.North, new int[]{0,-1});
		_collisionProbes.put(Direction.South, new int[]{0,1});
		_collisionProbes.put(Direction.East, new int[]{1,0});
		_collisionProbes.put(Direction.West, new int[]{-1,0});
	}
	
	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}

	public ICommandResult process(Command command) {
		CheckCollisionResult res = new CheckCollisionResult(null);
		CheckCollisionCommand cmd = (CheckCollisionCommand)command;
		Point pt = cmd.getPos();
		Direction dir = cmd.getDirection();
		Entity currEntity;
		ArrayList<Entity> surroundList = new ArrayList<Entity>();
		
		int[] probe = _collisionProbes.get(dir);	
		currEntity = GameEngine.self.currentMap().getEntity(new Point(pt.x+probe[0],pt.y+probe[1]));
		
		if (currEntity != null) {
			surroundList.add(currEntity);
		}
		res.setSurroundEntities(surroundList);			
		return res;
	}
}
