package edu.SimonCresnjovnjak.meni;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class main implements ApplicationListener {

	Stanje stanje;

	meni[] menu;
	int izbira;
	boolean maxmenu;

	Camera cam;

	int sirina, visina;

	public main() {
	}

	@Override
	public void create() {

		menu = new meni[] {
			
				new meni("podatki.png", new podatki()),
				new meni("avtr.png", new avtor()),
				new meni("izhod.png", new izhod()) };

		stanje = null;

		izbira = 0;
		maxmenu = false;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {

		if (stanje != null) {
			// render state
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
				stanje.onDestroy();
				stanje = null;
			} else
				stanje.onRender();
		} else {
			// render menu
			if (maxmenu) {
				if (!(Gdx.input.isKeyPressed(Keys.DPAD_UP) || Gdx.input
						.isKeyPressed(Keys.DPAD_DOWN)))
					maxmenu = false;
			} else {
				if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
					izbira = (izbira - 1);
					if (izbira < 0)
						izbira = 0;
					maxmenu = true;
				}
				if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
					izbira = (izbira + 1);
					if (izbira >= menu.length)
						izbira = menu.length - 1;
					maxmenu = true;
				}
			}

			if (Gdx.input.isKeyPressed(Keys.ENTER)) {
				stanje = menu[izbira].getState();
				stanje.onCreate();
				stanje.onResize(sirina, visina);
			}

			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

			cam.update();
			cam.apply(Gdx.gl10);

			for (int i = 0; i < menu.length; i++) {

				float odmik = (float) ((i - izbira) * 0.6);
				float povecava = i - izbira;
				if (povecava < 0)
					povecava = -povecava;
				povecava = povecava / 6;

				Mesh mesh = new Mesh(true, 4, 4, new VertexAttribute(
						Usage.Position, 3, "a_position"), new VertexAttribute(
						Usage.TextureCoordinates, 2, "a_texCoords"));

				mesh.setVertices(new float[] { -0.8f, -0.2f - odmik, 1, 0, 1,
						0.8f, -0.2f - odmik, 1, 1, 1, 0.8f, 0.2f - odmik, 1,
						1, 0, -0.8f, 0.2f - odmik, 1, 0, 0 });

				mesh.setIndices(new short[] { 0, 1, 2, 3 });
				mesh.scale(1 - povecava, 1 - povecava, 1);

				menu[i].getTexture().bind();
				mesh.render(GL10.GL_TRIANGLE_FAN);
			}
		}

	}

	@Override
	public void resize(int sirina, int visina) {

		if (stanje != null)
			stanje.onResize(sirina, visina);
		else {
			float aspectRatio = (float) sirina / (float) visina;
			cam = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
			cam.position.x = 0;
			cam.position.y = 0;
			cam.position.z = 2.15f;
			cam.lookAt(0, 0, 0);

			this.sirina = sirina;
			this.visina = visina;
		}
	}

	@Override
	public void resume() {
	}

}
