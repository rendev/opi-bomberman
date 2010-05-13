package com.opisoft.engine.commands;

import java.util.List;

import com.opisoft.engine.Entity;

public class PositionCommand extends Command {
	public PositionCommand(List<Entity> entities) {
		addEntities(entities);
	}
}
