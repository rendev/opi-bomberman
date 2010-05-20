package com.opisoft.engine.services;

import java.util.ArrayList;
import java.util.List;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.commands.AddTextureCommand;
import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.LoadTexturesCommand;
import com.opisoft.engine.commands.RenderCommand;
import com.opisoft.engine.commands.RenderCommandResult;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Render;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;

public class RenderService implements IService {
	private static ArrayList<Class<? extends Command>> _cmds = new ArrayList<Class<? extends Command>>();
	private TextureAtlas _atlas;
	
	public RenderService() {
		_cmds.add(RenderCommand.class);
		_cmds.add(AddTextureCommand.class);
		_cmds.add(LoadTexturesCommand.class);
		_atlas = new TextureAtlas(1024, 1024);
	}
	
	public ICommandResult process(Command command) {
		if (command.getClass() == RenderCommand.class) {
			for (Entity entity : command.getEntities()) {
				if (entity != null && entity.hasComponent(ComponentType.Render)) {
					Rokon rokon = Rokon.getRokon();
					Render render = (Render)entity.getComponent(ComponentType.Render);
					Sprite sprite = render.getSprite();
					rokon.addSprite(sprite,render.getZIndex());
				}
			}
		} else if (command.getClass() == AddTextureCommand.class) {
			_atlas.insert(((AddTextureCommand)command).getTexture());
		} else if (command.getClass() == LoadTexturesCommand.class) {
			TextureManager.load(_atlas);
		}
		return new RenderCommandResult();
	}

	public List<Class<? extends Command>> handledCommands() {
		return _cmds;
	}
}
