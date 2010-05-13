package com.opisoft.engine.commands;

import java.util.ArrayList;
import java.util.List;

import com.opisoft.engine.Entity;

public class Command {	
	private List<Entity> _entities;
	
	public Command() {
		_entities = new ArrayList<Entity>();
	}
	
	public void addEntities(List<Entity> entities) {
		_entities.addAll(entities);
	}

	public void addEntity(Entity entity) {
		_entities.add(entity);
	}
	
	public void setEntity(Entity entity) {
		_entities.clear();
		_entities.add(entity);
	}

	public Entity getEntity() {
		return _entities.size() > 0 ? _entities.get(0) : null;
	}
	
	public List<Entity> getEntities() {
		return _entities;
	}
	
	public boolean isMultiEntity() {
		return _entities.size() > 1;
	}
}
