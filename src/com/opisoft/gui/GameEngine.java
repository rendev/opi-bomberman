package com.opisoft.gui;

import com.opisoft.gui.SimpleObject;
import com.opisoft.gui.MapObjectsFactory;
import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.ScrollingBackground;
import com.stickycoding.Rokon.Backgrounds.TiledBackground;

public class GameEngine {
	private static GameEngine _self = new GameEngine();
	private static MapLoader _mapLoader = new MapLoader();
	private RokonActivity _gameActivity;
	private TextureAtlas _texAtlas;
	private GameMap _currMap;

	protected GameEngine() {
		_texAtlas = new TextureAtlas(1024, 1024);
		registerObjects();
	}
	
	public static GameEngine self() {
		return _self;
	}
	
	public void registerObjects() {
		MapObjectsFactory.self.registerObject("simple",new SimpleObject());
	}
	
	public RokonActivity gameActivity() {
		return _gameActivity;
	}
	
	public Rokon rokon() {
		return _gameActivity.rokon;
	}
	
	public void addTexture(Texture tex) {
		_texAtlas.insert(tex);
	}
	
	public void setActivity(RokonActivity activity) {
		_gameActivity = activity;
		_mapLoader.setActivity(_gameActivity);		
	}
	
	public boolean loadMap(String mapPath) {
		boolean res = false;
		
		GameMap map = _mapLoader.loadMap(mapPath);
        
		if (map != null) {
			try {
				_currMap = map;
				initBackground();
				loadMapObjects();
				TextureManager.load(_texAtlas);
				res = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected void initBackground() {
		rokon().setBackground(_currMap.background());		
	}
	
	protected void loadMapObjects() {
		for (MapObject object : _currMap.objects()) {
			rokon().addSprite(object.sprite());
		}
	}
}
