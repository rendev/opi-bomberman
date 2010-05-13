package com.opisoft.engine;

import java.util.ArrayList;
import java.util.List;
import com.opisoft.engine.components.Component;

public class Entity implements Cloneable {
	private List<Component> _components;
	private int _id;
	private String _dn;
	
	public Entity() {
		_components = new ArrayList<Component>();
	}
	
	public Entity(Entity other) {
		_components = new ArrayList<Component>();
		_id = other._id;
		_dn = other._dn;
		
		for (Component cmp : other._components) {
			addComponent(cmp.clone());
		}
	}
	
	public void setId(int id) {
		_id = id;
	}
	
	public int getId() {
		return _id;
	}		
	
	public void addComponent(Component component) {
		if (!hasComponent(component.getClass())) {
			_components.add(component);
		}
	}
	
	public boolean hasComponent(Class<? extends Component> type) {
		return componentIndex(type) >= 0;
	}
	
	public Integer componentIndex(Class<? extends Component> type) {
		for (Integer i=0; i < _components.size(); i++) {
			if (_components.get(i).getClass() == type) {
				return i;
			}
		}
		return -1;
	}
	
	public Component getComponent(Class<? extends Component> type) {
		Integer index = componentIndex(type);		
		return (index >= 0) ? _components.get(index) : null;
	}

	public void setDistinguishName(String dn) {
		this._dn = dn;
	}

	public String getDistinguishName() {
		return _dn;
	}
	
	public void releaseComponents() {
		for (Component component : _components) {
			component.release();
		}
	}
	
	public Entity clone() {
		return new Entity(this);
	}
}
