package com.opisoft.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OpiMap {
	enum BgMode
	{
		Repeat,
		Stretch
	};
	
	private String _name;
	private Integer _width;
	private Integer _height;
	private String _bgImg;
	private BgMode _bgMode;
	private List<MapObject> _objects;
	
	public OpiMap() {
		_objects = new ArrayList<MapObject>();
	}
	
	public boolean initFromJson(JsonObject object) {
		_name = object.get("name").getAsString();
		
		_width = 0;
		_height = 0;
		JsonObject sizeObj = object.getAsJsonObject("size");
		
		if (sizeObj != null) {
			_width = sizeObj.get("width").getAsInt();
			_height = sizeObj.get("height").getAsInt();
		}
		JsonObject bg = object.getAsJsonObject("background");
		
		if (bg != null) {
			_bgImg = bg.get("img").getAsString();
			String bgMode = bg.get("mode").getAsString();
			
			if (bgMode.equals("stretch")) {
				_bgMode = BgMode.Stretch;
			} else {
				_bgMode = BgMode.Repeat;
			}
		}
		
		JsonArray objectsArray = object.getAsJsonArray("objects");
		
		if (objectsArray != null) {
			readObjects(objectsArray);
		}
		return true;
	}
	
	protected boolean readObjects(JsonArray objectsArray) {
		for (JsonElement jsonElement : objectsArray) {
			JsonObject obj = jsonElement.getAsJsonObject();
			
			if (obj != null) {
				MapObject mapObj = MapObjectsFactory.self().createObject(obj.get("name").toString());
				
				if (mapObj != null) {
					mapObj.initFromJson(obj);
					_objects.add(mapObj);	
				}
			}
		}
		return true;
	}
}
