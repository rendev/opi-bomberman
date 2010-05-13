package com.opisoft.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.PointF;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.opisoft.gui.MapObject.RepeatType;
import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.CentredBackground;
import com.stickycoding.Rokon.Backgrounds.ScrollingBackground;

public class GameMap {
	enum BgMode
	{
		Repeat,
		Stretch
	};
	
	private String _name;
	private Integer _width;
	private Integer _height;
	private Background _bg;
	private List<MapObject> _objects;
	private String _assetsPath;
	
	public GameMap(String assetsPath) {
		_objects = new ArrayList<MapObject>();
		_assetsPath = assetsPath;
	}
	
	public String assetsPath() {
		return _assetsPath;
	}
	
	public List<MapObject> objects() {
		return _objects;
	}
	
	public Background background() {
		return _bg;
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
			String bgImg = bg.get("img").getAsString();
			String bgMode = bg.get("mode").getAsString();
			Texture bgTex = new Texture(resourcesPath()+"/"+bgImg);
			GameEngine.self().addTexture(bgTex);
			
			if (bgMode.equals("static")) {
				_bg = new CentredBackground(bgTex);
			} else if (bgMode.equals("repeat")) {
				_bg = new ScrollingBackground(bgTex);
			}
		}
		
		JsonArray objectsArray = object.getAsJsonArray("objects");
		
		if (objectsArray != null) {
			readObjects(objectsArray);
		}
		return true;
	}
	
	public String resourcesPath() {
		return assetsPath()+"/resources"; 
	}
	
	protected boolean readObjects(JsonArray objectsArray) {
		for (JsonElement jsonElement : objectsArray) {
			JsonObject obj = jsonElement.getAsJsonObject();
			
			if (obj != null) {
				JsonObject repeatObj = obj.getAsJsonObject("repeat");
				MapObject.RepeatType repType = MapObject.RepeatType.None;
				Integer repeatCount = 1;
				
				if (repeatObj != null) {
					String repeatOrientStr = repeatObj.get("orientation").getAsString();
					
					if (repeatOrientStr.equals("horizontal")) {
						repType = MapObject.RepeatType.Horizontal;
					} else {
						repType = MapObject.RepeatType.Vertical;
					}
					repeatCount = repeatObj.get("count").getAsInt();
				}
				
				String objName = obj.get("name").getAsString();
				MapObject mapObj = MapObjectsFactory.self.createObject(objName);
				mapObj.setAssetsPath(resourcesPath());
				mapObj.initFromJson(obj);

				MapObject currObj = mapObj;
				PointF pos = new PointF();
				pos.set(currObj.pos());
				PointF prevPos = new PointF();
				prevPos.set(pos);
				
				for (Integer i=0; i < repeatCount; i++) {
					if (i > 0)
						currObj = mapObj.clone();
					
					if (currObj != null) {
						if (i > 0) {
							if (repType == MapObject.RepeatType.Horizontal) {
								pos.set(prevPos.x + currObj.width(),prevPos.y);
							} else if (repType == MapObject.RepeatType.Vertical) {
								pos.set(prevPos.x,prevPos.y+currObj.height());
							}
							currObj.setPos(pos);
							prevPos = pos;
						}
						_objects.add(currObj);
					}
				}
			}
		}
		return true;
	}
}
