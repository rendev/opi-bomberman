package com.opisoft.bomber;

import java.io.IOException;
import java.io.InputStream;

import com.opisoft.gui.GameEngine;
import com.opisoft.gui.MapLoader;
import com.opisoft.gui.GameMap;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.RokonExamples.Example7;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class Bomber extends RokonActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      	createEngine("pisikak.png", 480, 320, true);
    	rokon.setBackgroundColor(1, 0, 0);
        GameEngine.self().setActivity(this);
    }
    
    @Override
	public void onLoadComplete() { 
		try {
	        GameEngine.self().loadMap("maps/testmap");	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public void onRestart() {
		//The engine will automatically pause time when it is hidden, so if you aren't handling this yourself you need to automatically unpause
		//It seems that some(?) devices are restarting the examples from the beginning, because the OpenGL activities aren't the main Activity
		rokon.unpause();
	}
}