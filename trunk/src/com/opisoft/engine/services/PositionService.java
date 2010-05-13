package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.Metrics;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.PositionCommand;
import com.opisoft.engine.components.Position;
import com.opisoft.engine.components.Render;
import com.stickycoding.Rokon.Sprite;

public class PositionService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();

	public PositionService() {
		_cmds.add(PositionCommand.class);
	}
	
	public ICommandResult process(Command command) {
		if (command.getClass() == PositionCommand.class) {
			for (Entity entity : command.getEntities()) {
				if (entity != null && entity.hasComponent(Position.class) && entity.hasComponent(Render.class)) {
					Position position = (Position)entity.getComponent(Position.class);
					Point ptInPixels = GameEngine.self.mapMetrics().measure(position.getPosInCells());
					Render render = (Render)entity.getComponent(Render.class);
					Sprite sprite = render.getSprite();
					sprite.setXY(ptInPixels.x, ptInPixels.y);
				}
			}
		}
		return null;
	}

	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}

}
