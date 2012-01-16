package edu.SimonCresnjovnjak.meni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class podatki implements Stanje {

	Camera cam;
	Texture texture;
	Mesh mesh;

	@Override
	public void onCreate() {

		texture = new Texture(Gdx.files.internal("podatki_pd.png"));

		mesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3,
				"a_position"), new VertexAttribute(Usage.TextureCoordinates, 2,
				"a_texCoords"));
		mesh.setVertices(new float[] { -1, -1.0f, 1, 0, 1.0f, 1, -1.0f, 1, 1,
				1.0f, 1, 1.0f, 1, 1, 0, -1, 1.0f, 1, 0, 0 });
		mesh.setIndices(new short[] { 0, 1, 2, 3 });
	}

	@Override
	public void onRender() {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

		cam.update();
		cam.apply(Gdx.gl10);

		texture.bind();
		mesh.render(GL10.GL_TRIANGLE_FAN);
	}

	@Override
	public void onResize(int sirina, int visina) {

		float aspectRatio = (float) sirina / (float) visina;
		cam = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
		cam.position.x = 0;
		cam.position.y = 0;
		cam.position.z = 2.5f;
		cam.lookAt(0, 0, 0);
	}

	@Override
	public void onDestroy() {

		mesh.dispose();
		texture.dispose();
	}

}
