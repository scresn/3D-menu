package edu.vecpredstavnost.prikaz3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MenuItem {

	Texture texture;
	State state;

	public MenuItem(String internalTexturePath, State appState) {

		texture = new Texture(Gdx.files.internal(internalTexturePath));
		state = appState;
	}

	public Texture getTexture() {

		return texture;
	}

	public State getState() {

		return state;
	}

}
