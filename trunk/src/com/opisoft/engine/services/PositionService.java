package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.commands.CheckCollisionCommand;
import com.opisoft.engine.commands.CheckCollisionResult;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.CommandType;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.MoveCommand;
import com.opisoft.engine.commands.PositionCommand;
import com.opisoft.engine.commands.MoveCommand.Direction;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Position;
import com.opisoft.engine.components.Render;

public class PositionService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();
	private ArrayBlockingQueue<Command> _queue = new ArrayBlockingQueue<Command>(100);
	
	public PositionService() {
		_cmds.add(PositionCommand.class);
		_cmds.add(MoveCommand.class);
		_processThread.setPriority(Thread.MAX_PRIORITY);
		_processThread.start();
	}
	
	private Thread _processThread = new Thread() {
		Command _lastCmd;
		
		@Override
		public void run() {
			try {
				while (true) {
					_lastCmd = _queue.poll(1, TimeUnit.MILLISECONDS);
	
					if (_lastCmd != null) {
						doProcessCommand(_lastCmd);
						_lastCmd = null;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		};
	};
	
	public ICommandResult process(Command command) {
		_queue.add(command);
		return null;
	}
	
	protected void doProcessCommand(Command command) {
		if (command.type() == CommandType.Position) {
			for (Entity entity : command.getEntities()) {
				if (entity == null)
					continue;
				
				Position position = (Position)entity.getComponent(ComponentType.Position);
				Render render = (Render)entity.getComponent(ComponentType.Render);
				
				if (position != null && render != null) {
					Point ptInPixels = GameEngine.self.mapMetrics().measure(position.getPosInCells());
					render.getSprite().setXY(ptInPixels.x, ptInPixels.y);
				}
			}
		} else if (command.type() == CommandType.Move) {
			for (Entity entity : command.getEntities()) {
				if (entity == null)
					continue;
				
				Position position = (Position)entity.getComponent(ComponentType.Position);
				Render render = (Render)entity.getComponent(ComponentType.Render);

				MoveCommand cmd = (MoveCommand)command;
				
				if (position != null && render != null) {
					Point oldPos = position.getPosInCells();					
					Direction d = cmd.getDirection();
					
					if (cmd.getDx() == 0) {//vertical movement
						int newY = oldPos.y+cmd.getDy();
						int minY = Math.min(oldPos.y,newY);
						int maxY = Math.max(oldPos.y,newY);
						Point pt = new Point(oldPos);
						
						for (int y = minY; y <= maxY; y++) {
							pt.y = y;
							CheckCollisionResult collisionRes = (CheckCollisionResult)GameEngine.self.process(new CheckCollisionCommand(pt,d));
							
							if (collisionRes != null && !collisionRes.isEmpty()) {//collided
								cmd.setDy((d == Direction.South) ? y-minY : y-maxY);
								break;
							}
						}
					} else {//horizontal
						int newX = oldPos.x+cmd.getDx();
						int minX = Math.min(oldPos.x,newX);
						int maxX = Math.max(oldPos.x,newX);
						Point pt = new Point(oldPos);
						
						for (int x = minX; x <= maxX; x++) {
							pt.x = x;
							CheckCollisionResult collisionRes = (CheckCollisionResult)GameEngine.self.process(new CheckCollisionCommand(pt,d));
							
							if (collisionRes != null  && !collisionRes.isEmpty()) {
								cmd.setDx((d == Direction.East) ? x - minX : x - maxX);
								break;
							}
						}
					}
					
					position.setPosInCells(new Point(oldPos.x+cmd.getDx(),oldPos.y+cmd.getDy()));
					
					if (!position.getPosInCells().equals(oldPos)) {
						Point deltaPixels = GameEngine.self.mapMetrics().measure(new Point(cmd.getDx(),cmd.getDy()));
						render.getSprite().move(deltaPixels.x, deltaPixels.y);
					}
				}
			}
		}
	}
	
	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}

}
