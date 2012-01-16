package edu.SimonCresnjovnjak.meni;

import com.badlogic.gdx.Gdx;

public class izhod implements Stanje {

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
