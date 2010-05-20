package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.ResizeCommand;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Render;
import com.opisoft.engine.components.Size;
import com.stickycoding.Rokon.Sprite;

public class SizeService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();

	public SizeService() {
		_cmds.add(ResizeCommand.class);
	}
	
	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}

	public ICommandResult process(Command command) {
		if (command.getClass() == ResizeCommand.class) {
			for (Entity entity : command.getEntities()) {
				if (entity != null && entity.hasComponent(ComponentType.Size) && entity.hasComponent(ComponentType.Render)) {
					Size size = (Size)entity.getComponent(ComponentType.Size);
					Point sizeInPixels = GameEngine.self.mapMetrics().measure(new Point(size.getWidth(),size.getHeight()));
					Render render = (Render)entity.getComponent(ComponentType.Render);
					Sprite sprite = render.getSprite();
					sprite.setWidth(sizeInPixels.x);
					sprite.setHeight(sizeInPixels.y);
				}
			}
		}
		return null;
	}

}
