package com.opisoft.engine;

import java.util.HashMap;
import java.util.Map;

import com.opisoft.engine.components.Component;
import com.opisoft.engine.components.ComponentType;

public class Entity implements Cloneable {
	private Map<ComponentType, Component> _components;
	private int _id;
	private String _dn;
	
	public Entity() {
		_components = new HashMap<ComponentType,Component>();
	}
	
	public Entity(Entity other) {
		_components = new HashMap<ComponentType,Component>();
		_id = other._id;
		_dn = other._dn;
		
		for (Component cmp : other._components.values()) {
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
		_components.put(component.type(), component);
	}
	
	public boolean hasComponent(ComponentType type) {
		return _components.containsKey(type);
	}
	
//	public boolean hasComponent(Class<? extends Component> type) {
//		return componentIndex(type) >= 0;
//	}
	
//	public Integer componentIndex(Class<? extends Component> type) {
//		for (Integer i=0; i < _components.size(); i++) {
//			if (_components.get(i).getClass() == type) {
//				return i;
//			}
//		}
//		return -1;
//	}
	
	public Component getComponent(ComponentType type) {	
		return _components.get(type);
	}

	public void setDistinguishName(String dn) {
		this._dn = dn;
	}

	public String getDistinguishName() {
		return _dn;
	}
	
	public void releaseComponents() {
		for (Component cmp : _components.values()) {
			cmp.release();
		}
	}
	
	public Entity clone() {
		return new Entity(this);
	}
}
