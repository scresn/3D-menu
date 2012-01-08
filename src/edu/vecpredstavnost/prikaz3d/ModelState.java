package edu.vecpredstavnost.prikaz3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;

public class ModelState implements State {

	// objekt
	int lastX, lastY;
	Mesh obj;
	Texture tex;

	// kamera
	Camera camera;
	Boolean isPerspective;
	int angleX, angleY;
	float zoom;
	int camX, camY;

	// okno
	int width, height;

	Sound sound;

	@Override
	public void onCreate() {

		// obj model
		obj = ObjLoader.loadObj(Gdx.files.internal("data/boxes.obj").read());
		tex = new Texture(Gdx.files.internal("data/texture.jpg"));

		// naloži zvok
		sound = Gdx.audio.newSound(Gdx.files.internal("data/sound.ogg"));

		// spremenljivke za kamero
		isPerspective = true;
		angleX = angleY = 0;
		zoom = 4;
		camX = camY = 0;

		// pravilno izriši mreže glede na globino
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);

		// osvetlitev
		Gdx.gl.glEnable(GL10.GL_LIGHTING);
		Gdx.gl.glEnable(GL10.GL_LIGHT0);

	}

	@Override
	public void onRender() {

		Gdx.gl.glEnable(GL10.GL_COLOR_MATERIAL);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

		// nadzor kamere
		if (Gdx.input.justTouched() && Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			isPerspective = !isPerspective;
			setCamera();
			sound.play();
		}
		if (Gdx.input.isKeyPressed(Keys.PLUS)) {
			zoom -= 0.1;
			updateCamera();
		}
		if (Gdx.input.isKeyPressed(Keys.MINUS)) {
			zoom += 0.1;
			updateCamera();
		}
		if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			angleY += 3;
			if (angleY > 89)
				angleY = 89;
			updateCamera();
		}
		if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			angleY -= 3;
			if (angleY < -89)
				angleY = -89;
			updateCamera();
		}
		if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			angleX -= 3;
			updateCamera();
		}
		if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			angleX += 3;
			updateCamera();
		}

		// premikanje objekta
		if (Gdx.input.justTouched()) {
			lastX = Gdx.input.getX();
			lastY = Gdx.input.getY();
		} else if (Gdx.input.isTouched()
				&& Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// camX += (int)((lastX - Gdx.input.getX()) * 0.01f);
			// camY += (int)((lastY - Gdx.input.getY()) * 0.01f);
			lastX = Gdx.input.getX();
			lastY = Gdx.input.getY();
		}

		camera.update();
		camera.apply(Gdx.gl10);

		// izris
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] { 0,
				0, 1, 1 }, 0);

		tex.bind();
		obj.render(GL10.GL_TRIANGLES);

	}

	private void updateCamera() {

		double x = angleX * 2 * Math.PI / 360;
		double y = angleY * 2 * Math.PI / 360;
		camera.position.x = (float) Math.sin(x) * zoom * (float) Math.cos(y)
				+ camX;
		camera.position.z = (float) Math.cos(x) * zoom * (float) Math.cos(y);
		camera.position.y = (float) Math.sin(y) * zoom + camY;
		camera.lookAt(0, 0, 0);
	}

	private void setCamera() {

		float aspectRatio = (float) width / (float) height;
		camera = isPerspective ? new PerspectiveCamera(67, 2f * aspectRatio, 2f)
				: new OrthographicCamera(2f * aspectRatio, 2f);

		updateCamera();
	}

	@Override
	public void onResize(int w, int h) {

		width = w;
		height = h;

		setCamera();
	}

	@Override
	public void onDestroy() {

		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glDisable(GL10.GL_LIGHTING);
		Gdx.gl.glDisable(GL10.GL_LIGHT0);

		sound.dispose();
		obj.dispose();
		tex.dispose();
		camera.normalizeUp();
	}

}
