package edu.vecpredstavnost.prikaz3d;

import com.badlogic.gdx.Gdx;

public class QuitState implements State {

	@Override
	public void onCreate() {

		Gdx.app.exit();
	}

	@Override
	public void onRender() {
	}

	@Override
	public void onResize(int w, int h) {
	}

	@Override
	public void onDestroy() {
	}

}
