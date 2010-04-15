package com.opisoft.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import android.content.Context;
import android.content.res.*;
import android.util.Log;

import com.google.gson.*;

public class MapLoader {
	
	public MapLoader() {
	
	}
	
	public OpiMap loadMap(InputStream mapIndexFileStream) {
		OpiMap map = new OpiMap();
		
		try {		
			InputStreamReader reader = new InputStreamReader(mapIndexFileStream,"utf-8");
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
