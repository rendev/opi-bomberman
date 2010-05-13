package com.opisoft.engine.components;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
	private Map<String,Class<? extends Component>> _componentsMap;

	public static final ComponentFactory self = new ComponentFactory();
	
	protected ComponentFactory() {
		_componentsMap = new HashMap<String,Class<? extends Component>>();
	}
	
	public boolean registerObject(String type, Class<? extends Component> classType) {
		return (_componentsMap.put(type, classType) != null);		
	}
	
	public Component createObject(String type) {
		Class<?> classType = _componentsMap.get(type.toLowerCase());
		Component component = null;
		
		if (classType != null) {			
			try {
				component = (Component)classType.newInstance();
			} catch (Exception ex) {}
		}
		return component;
	}
}
