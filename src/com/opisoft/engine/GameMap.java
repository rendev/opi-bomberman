package com.opisoft.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.opisoft.engine.commands.AddTextureCommand;
import com.opisoft.engine.components.Component;
import com.opisoft.engine.components.ComponentFactory;
import com.opisoft.engine.components.ComponentType;
import com.opisoft.engine.components.Position;
import com.opisoft.engine.components.Size;
import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.Backgrounds.CentredBackground;
import com.stickycoding.Rokon.Backgrounds.ScrollingBackground;

public class GameMap {
	enum BgMode {
		Repeat,
		Stretch
	};
	
	enum RepeatType {
		None,
		Horizontal,
		Vertical
	};
	
	private Metrics _metrics;
	private String _name;
	private int _width;
	private int _height;
	private Background _bg;
	private List<Entity> _entities;
	private String _assetsPath;
	
	public GameMap(String assetsPath) {
		_entities = new ArrayList<Entity>();
		_assetsPath = assetsPath;
		_metrics = new Metrics();
	}
	
	public String assetsPath() {
		return _assetsPath;
	}
	
	public Metrics metrics() {
		return _metrics;
	}
	
	public List<Entity> entities() {
		return _entities;
	}
	
	public Entity getEntity(String name) {
		for (Entity entity : _entities) {
			if (entity.getDistinguishName().equals(name)) {
				return entity;
			}
		}
		return null;
	}
	
	public Entity getEntity(Point posInCells) {
		Rect rect = new Rect();
		Position position;
		Size size;
		Point pt;
		
		for (Entity entity : _entities) {
			if (entity.hasComponent(ComponentType.Position) && entity.hasComponent(ComponentType.Size)) {
				position = (Position)entity.getComponent(ComponentType.Position);
				size = (Size)entity.getComponent(ComponentType.Size);
				pt = position.getPosInCells();
				rect.set(pt.x, pt.y, pt.x+size.getWidth(), pt.y+size.getHeight());
				
				if (rect.contains(posInCells.x,posInCells.y)) {
					return entity;
				}
			}
		}
		return null;
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
			GameEngine.self.process(new AddTextureCommand(bgTex));
			
			if (bgMode.equals("static")) {
				_bg = new CentredBackground(bgTex);
			} else if (bgMode.equals("repeat")) {
				_bg = new ScrollingBackground(bgTex);
			}
		}
		
		JsonArray entitiesArray = object.getAsJsonArray("entities");
		
		if (entitiesArray != null) {
			readEntities(entitiesArray);
		}
		return true;
	}
	
	public String resourcesPath() {
		return assetsPath()+"/resources"; 
	}
	
	protected boolean readEntities(JsonArray entitiesArray) {
		for (JsonElement jsonElement : entitiesArray) {
			JsonObject obj = jsonElement.getAsJsonObject();
			
			if (obj != null) {
				Entity entity = new Entity();
				
				JsonElement dn = obj.get("dn");
				
				if (dn != null)
					entity.setDistinguishName(dn.getAsString());
				
				JsonArray components = obj.get("components").getAsJsonArray();
				JsonObject component = null;
				
				for (JsonElement elem : components) {
					component = elem.getAsJsonObject();
					
					Component cmp = ComponentFactory.self.createObject(component.get("type").getAsString());
					cmp.setAssetsPath(resourcesPath());
					
					if (cmp != null && cmp.initFromJson(component)) {
						entity.addComponent(cmp);
					}
				}
				
				if (entity.hasComponent(ComponentType.Position) && entity.hasComponent(ComponentType.Size)) {				
					JsonObject repeatObj = obj.getAsJsonObject("repeat");
					RepeatType repType = RepeatType.None;
					Integer repeatCount = 1;
					
					if (repeatObj != null) {
						String repeatOrientStr = repeatObj.get("orientation").getAsString();
						
						if (repeatOrientStr.equals("horizontal")) {
							repType = RepeatType.Horizontal;
						} else {
							repType = RepeatType.Vertical;
						}
						repeatCount = repeatObj.get("count").getAsInt();
					}
					
					Entity currEntity = entity;
					Position currPos = (Position)currEntity.getComponent(ComponentType.Position);
					Point prevPos = new Point(currPos.getPosInCells());
					Size currSize;
					
					for (Integer i=0; i < repeatCount; i++) {
						if (i > 0)
							currEntity = entity.clone();
						
						if (currEntity != null) {
							currSize = (Size)currEntity.getComponent(ComponentType.Size);
							currPos = (Position)currEntity.getComponent(ComponentType.Position);
							
							if (i > 0) {
								if (repType == RepeatType.Horizontal) {
									currPos.setPosInCells(new Point(prevPos.x + currSize.getWidth(),prevPos.y));
								} else if (repType == RepeatType.Vertical) {
									currPos.setPosInCells(new Point(prevPos.x,prevPos.y+currSize.getHeight()));
								}
								prevPos = currPos.getPosInCells();
							}
							_entities.add(currEntity);
						}
					}
				}
			}
		}
		return true;
	}
}
