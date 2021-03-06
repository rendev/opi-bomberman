package com.opisoft.engine.commands;

import java.util.List;

import com.opisoft.engine.Entity;

public class RenderCommand extends Command {
	public RenderCommand(List<Entity> entities) {
		super(CommandType.Render);
		addEntities(entities);
	}
}
