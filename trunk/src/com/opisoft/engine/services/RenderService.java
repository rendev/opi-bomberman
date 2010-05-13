package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.List;

import com.opisoft.engine.Entity;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.RenderCommand;
import com.opisoft.engine.commands.RenderCommandResult;
import com.opisoft.engine.components.Render;
import com.opisoft.gui.GameEngine;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;

public class RenderService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();

	public RenderService() {
		_cmds.add(RenderCommand.class);
	}
	
	public ICommandResult process(Command command) {
		if (command.getClass() == RenderCommand.class) {
			Entity entity = command.getEntity();
			
			if (entity != null && entity.hasComponent(Render.class)) {
				Rokon rokon = GameEngine.self().rokon();
				Render render = (Render)entity.getComponent(Render.class);
				Sprite sprite = render.getSprite();
				
				if (!sprite.isVisible()) {
					rokon.addSprite(sprite,render.getZIndex());
				}
			}
		}
		return new RenderCommandResult();
	}

	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}

}
