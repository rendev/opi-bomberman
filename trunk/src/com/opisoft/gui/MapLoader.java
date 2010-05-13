package com.opisoft.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import android.app.Activity;
import android.content.Context;
import android.content.res.*;
import android.util.Log;

import com.google.gson.*;

public class MapLoader {
	private Activity _activity;
	
	public MapLoader() {		
	}
	
	public MapLoader(Activity activity) {
		_activity = activity;
	}
	
	public void setActivity(Activity activity) {
		_activity = activity;
	}
	
	public GameMap loadMap(String mapFolder) {
		GameMap map = new GameMap(mapFolder);
		
		try {		
			InputStream strm = _activity.getAssets().open(mapFolder+"/index.json");
			InputStreamReader reader = new InputStreamReader(strm,"utf-8");
			JsonParser parser = new JsonParser();
			JsonObject rootObj = parser.parse(reader).getAsJsonObject();
			map.initFromJson(rootObj);
		} catch (IOException ex) {			
			Log.e("MapLoader", ex.getMessage());
		} catch (Exception e) {
			Log.e("MapLoader", e.getMessage());
		}
		return map;
	}
}
