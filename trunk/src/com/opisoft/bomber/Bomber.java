package com.opisoft.bomber;

import java.io.IOException;
import java.io.InputStream;

import com.opisoft.gui.MapLoader;
import com.opisoft.gui.OpiMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class Bomber extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		InputStream strm;
		try {
			strm = getAssets().open("maps/testmap/index.json");
	        MapLoader loader = new MapLoader();
	        OpiMap testMap = loader.loadMap(strm);
	        Log.d("Bomber", testMap.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}