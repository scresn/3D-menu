package edu.vecpredstavnost.prikaz3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class MainMenu implements ApplicationListener {

	State state;

	MenuItem[] menu;
	int selection;
	boolean keyIsDown;

	Camera cam;

	int w, h;

	public MainMenu() {
	}

	@Override
	public void create() {

		menu = new MenuItem[] {
				new MenuItem("data/menu/start.png", new ModelState()),
				new MenuItem("data/menu/about.png", new AboutState()),
				new MenuItem("data/menu/author.png", new AuthorState()),
				new MenuItem("data/menu/quit.png", new QuitState()) };

		state = null;

		selection = 0;
		keyIsDown = false;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {

		if (state != null) {
			// render state
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				state.onDestroy();
				state = null;
			} else
				state.onRender();
		} else {
			// render menu
			if (keyIsDown) {
				if (!(Gdx.input.isKeyPressed(Keys.DPAD_UP) || Gdx.input
						.isKeyPressed(Keys.DPAD_DOWN)))
					keyIsDown = false;
			} else {
				if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
					selection = (selection - 1);
					if (selection < 0)
						selection = 0;
					keyIsDown = true;
				}
				if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
					selection = (selection + 1);
					if (selection >= menu.length)
						selection = menu.length - 1;
					keyIsDown = true;
				}
			}

			if (Gdx.input.isKeyPressed(Keys.ENTER)) {
				state = menu[selection].getState();
				state.onCreate();
				state.onResize(w, h);
			}

			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

			cam.update();
			cam.apply(Gdx.gl10);

			for (int i = 0; i < menu.length; i++) {

				float offset = (float) ((i - selection) * 0.7);
				float scale = i - selection;
				if (scale < 0)
					scale = -scale;
				scale = scale / 8;

				Mesh mesh = new Mesh(true, 4, 4, new VertexAttribute(
						Usage.Position, 3, "a_position"), new VertexAttribute(
						Usage.TextureCoordinates, 2, "a_texCoords"));

				mesh.setVertices(new float[] { -0.8f, -0.2f - offset, 1, 0, 1,
						0.8f, -0.2f - offset, 1, 1, 1, 0.8f, 0.2f - offset, 1,
						1, 0, -0.8f, 0.2f - offset, 1, 0, 0 });

				mesh.setIndices(new short[] { 0, 1, 2, 3 });
				mesh.scale(1 - scale, 1 - scale, 1);

				menu[i].getTexture().bind();
				mesh.render(GL10.GL_TRIANGLE_FAN);
			}
		}

	}

	@Override
	public void resize(int w, int h) {

		if (state != null)
			state.onResize(w, h);
		else {
			float aspectRatio = (float) w / (float) h;
			cam = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
			cam.position.x = 0;
			cam.position.y = 0;
			cam.position.z = 2.5f;
			cam.lookAt(0, 0, 0);

			this.w = w;
			this.h = h;
		}
	}

	@Override
	public void resume() {
	}

}
