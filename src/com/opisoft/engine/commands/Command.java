package com.opisoft.engine.commands;

import com.opisoft.engine.Entity;

public class Command {	
	private Entity _entity;

	public void setEntity(Entity entity) {
		this._entity = entity;
	}

	public Entity getEntity() {
		return _entity;
	}
	
}
