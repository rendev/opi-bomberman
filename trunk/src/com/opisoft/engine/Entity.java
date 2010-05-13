package com.opisoft.engine;

import java.util.List;
import com.opisoft.engine.components.Component;

public class Entity {
	private List<Component> _components;
	private Integer _id;
	private String _dn;
	
	public Entity(Integer id) {
		_id = id;
	}
	
	public Integer getId() {
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
}
