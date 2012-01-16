package edu.SimonCresnjovnjak.meni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class meni {

	Texture textura;
	Stanje stanje;

	public meni(String internalTexturePath, Stanje appState) {

		textura = new Texture(Gdx.files.internal(internalTexturePath));
		stanje = appState;
	}

	public Texture getTexture() {

		return textura;
	}

	public Stanje getState() {

		return stanje;
	}

}
