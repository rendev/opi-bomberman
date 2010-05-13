package com.opisoft.gui;

import com.google.gson.JsonObject;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;

public class SimpleObject extends MapObject {
	private String _texture;
	
	protected SimpleObject() {
		super("simple");
		_texture = new String();
	}
	
	public SimpleObject(SimpleObject other) {
		super(other);
		if (other._texture.length() > 0) {
			_texture = other._texture;
			setSprite(initSprite());
			setPos(other.pos());
			setWidthHeight(other.width(), other.height());
		}
	}

	@Override
	public MapObject clone() {
		return new SimpleObject(this);
	}
	
	@Override
	public boolean initFromJson(JsonObject obj) {
		JsonObject spriteObj = obj.get("sprite").getAsJsonObject();
		_texture = spriteObj.get("texture").getAsString();
		super.initFromJson(obj);
		return true;
	}
	
	@Override
	public Sprite initSprite() {		
		Texture tex = new Texture(assetsPath()+"/"+_texture);
		GameEngine.self().addTexture(tex);
		return new Sprite(tex);
	}
}
