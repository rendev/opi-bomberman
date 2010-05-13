package com.opisoft.engine.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.SpriteAnimationParams;
import com.opisoft.engine.commands.AddTextureCommand;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;

public class Render extends Component {
	private Sprite _sprite;
	private SpriteAnimationParams _animParams;
	private int _zIndex;

	public Render() {
	}
	
	public Render(Render other) {
		super(other);
		_sprite = new Sprite(other._sprite.getTexture());
		_animParams = other._animParams;
		_zIndex = other._zIndex;
	}

	public void setSprite(Sprite sprite) {
		this._sprite = sprite;
	}

	public Sprite getSprite() {
		return _sprite;
	}

	public void setZIndex(Integer zIndex) {
		this._zIndex = zIndex;
	}

	public int getZIndex() {
		return _zIndex;
	}

	public boolean initFromJson(JsonObject object) {
		JsonObject tex = object.get("texture").getAsJsonObject();
		String texName = tex.get("src").getAsString();
		Texture texture = new Texture(getAssetsPath()+"/"+texName);
		GameEngine.self.process(new AddTextureCommand(texture));
		
		Integer tileRows = 1, tileCols = 1;
		JsonElement currElem = tex.get("tileRows");
		
		if (currElem != null) {
			tileRows = currElem.getAsInt();
			
			currElem = tex.get("tileCols");
			
			if (currElem != null) {
				tileCols = currElem.getAsInt();
			}
		}
				
		if (tileCols > 1 || tileRows > 1) {
			texture.setTileCount(tileCols, tileRows);
		}				
		_sprite = new Sprite(texture);
		//TODO: animation params
		return true;
	}

	@Override
	public void release() {
		Rokon.getRokon().removeSprite(_sprite);
	}

	@Override
	public Component clone() {
		return new Render(this);
	}
}
