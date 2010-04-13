package com.opisoft.bomber;

import java.io.IOException;
import java.io.InputStream;

import com.opisoft.gui.MapLoader;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class Bomber extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		InputStream strm;
		try {
			strm = getAssets().open("maps/testmap/index.json");
	        MapLoader loader = new MapLoader(strm);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}