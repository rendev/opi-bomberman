package com.opisoft.engine.commands;

import com.stickycoding.Rokon.Texture;

public class AddTextureCommand extends Command {
	private Texture _tex;

	public AddTextureCommand(Texture tex) {
		super(CommandType.AddTexture);
		_tex = tex;
	}
	
	public void setTexture(Texture tex) {
		this._tex = tex;
	}

	public Texture getTexture() {
		return _tex;
	}
}
