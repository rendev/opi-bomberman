package com.opisoft.engine.commands;

import java.util.List;

import com.opisoft.engine.Entity;

public class ResizeCommand extends Command {
	public ResizeCommand(List<Entity> entities) {
		super(CommandType.Resize);
		addEntities(entities);
	}
}
