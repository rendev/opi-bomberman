package com.opisoft.gui;

import java.util.HashMap;
import java.util.Map;

public class MapObjectsFactory {
	private Map<String,MapObject> _objectsMap;

	public static final MapObjectsFactory self = new MapObjectsFactory();

	public MapObjectsFactory() {
		_objectsMap = new HashMap<String,MapObject>();
	}
		
	public boolean registerObject(String type, MapObject prototype) {
		return (_objectsMap.put(type, prototype) != null);		
	}
	
	public MapObject createObject(String type) {
		MapObject obj = _objectsMap.get(type.toLowerCase());
		
		if (obj != null) {
			obj = (MapObject)obj.clone();
		}
		return obj;
	}
}
