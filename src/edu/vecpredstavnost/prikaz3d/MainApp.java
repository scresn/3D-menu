package edu.vecpredstavnost.prikaz3d;

import javax.media.opengl.GL;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;



public class MainApp implements ApplicationListener
{
	// objekt
	int lastX, lastY;
	//private Mesh triangle;
	Mesh[] cube;
	Mesh obj;
	Texture tex;
	
	/*private float[] tocke = new float[] {
		-0.5f, -0.5f, 0, Color.RED.toFloatBits(),
		 0.5f, -0.5f, 0, Color.GREEN.toFloatBits(),
		-0.2f,  0.5f, 0, Color.BLUE.toFloatBits()
	};*/
	
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
	public void create()
	{
		/*triangle = new Mesh(true, 3, 3,
				new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		triangle.setVertices(new float[] {
				-0.5f, -0.5f, 0, Color.RED.toFloatBits(),
				 0.5f, -0.5f, 0, Color.GREEN.toFloatBits(),
				-0.2f,  0.5f, 0, Color.BLUE.toFloatBits()
		});
		triangle.setIndices(new short[] {0, 1, 2});*/
		
		// doloèi kocko
		/*cube = new Mesh[6];
		
		for (int i = 0; i < cube.length; i++) {
			cube[i] = new Mesh (true, 4, 4,
					new VertexAttribute(Usage.Position, 3, "a_position"),
					new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		}
		cube[0].setVertices(new float[] {  // top
				 0.5f,  0.5f,  0.5f, Color.WHITE.toFloatBits(),
				 0.5f,  0.5f, -0.5f, Color.WHITE.toFloatBits(),
				-0.5f,  0.5f,  0.5f, Color.WHITE.toFloatBits(),
				-0.5f,  0.5f, -0.5f, Color.WHITE.toFloatBits(),
		});
		cube[1].setVertices(new float[] {  // front
				 0.5f,  0.5f,  0.5f, Color.RED.toFloatBits(),
				 0.5f, -0.5f,  0.5f, Color.RED.toFloatBits(),
				-0.5f,  0.5f,  0.5f, Color.RED.toFloatBits(),
				-0.5f, -0.5f,  0.5f, Color.RED.toFloatBits(),
		});
		cube[2].setVertices(new float[] {  // left
				 0.5f,  0.5f,  0.5f, Color.BLUE.toFloatBits(),
				 0.5f,  0.5f, -0.5f, Color.BLUE.toFloatBits(),
				 0.5f, -0.5f,  0.5f, Color.BLUE.toFloatBits(),
				 0.5f, -0.5f, -0.5f, Color.BLUE.toFloatBits(),
		});
		cube[3].setVertices(new float[] {  // back
				 0.5f,  0.5f, -0.5f, Color.GREEN.toFloatBits(),
				 0.5f, -0.5f, -0.5f, Color.GREEN.toFloatBits(),
				-0.5f,  0.5f, -0.5f, Color.GREEN.toFloatBits(),
				-0.5f, -0.5f, -0.5f, Color.GREEN.toFloatBits(),
		});
		cube[4].setVertices(new float[] {  // right
				-0.5f,  0.5f,  0.5f, Color.RED.toFloatBits(),
				-0.5f,  0.5f, -0.5f, Color.GREEN.toFloatBits(),
				-0.5f, -0.5f,  0.5f, Color.BLUE.toFloatBits(),
				-0.5f, -0.5f, -0.5f, Color.RED.toFloatBits(),
		});
		cube[5].setVertices(new float[] {
				 0.5f, -0.5f,  0.5f, Color.BLACK.toFloatBits(),
				 0.5f, -0.5f, -0.5f, Color.BLACK.toFloatBits(),
				-0.5f, -0.5f,  0.5f, Color.BLACK.toFloatBits(),
				-0.5f, -0.5f, -0.5f, Color.BLACK.toFloatBits(),
		});*/
		
		// obj model
		obj = ObjLoader.loadObj(Gdx.files.internal("data/Butterfly.obj").read());
		//obj = ObjLoader.loadObj(Gdx.files.internal("data/monkey.obj").read());
		//obj = ObjLoader.loadObj(Gdx.files.internal("data/chest.obj").read());
		tex = new Texture(Gdx.files.internal("data/Butterfly_g.jpg"));
		
		
		// naloži zvok
		sound = Gdx.audio.newSound(Gdx.files.internal("data/sound.ogg"));
		
		// spremenljivke za kamero
		isPerspective = true;
		angleX = angleY = 0;
		zoom = 2;
		camX = camY = 0;
		
		// pravilno izriši mreže glede na globino
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		// osvetlitev
		Gdx.gl.glEnable(GL10.GL_LIGHTING);
		Gdx.gl.glEnable(GL10.GL_LIGHT0);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render()
	{
		Gdx.gl.glEnable( GL10.GL_COLOR_MATERIAL );
		Gdx.gl.glEnable( GL10.GL_TEXTURE_2D );
		
		// nadzor kamere
		if(Gdx.input.justTouched() && Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			isPerspective = !isPerspective;
			setCamera();
			sound.play();
		}
		if(Gdx.input.isKeyPressed(Keys.PLUS)) {
			zoom -= 0.1;
			updateCamera();
		}
		if(Gdx.input.isKeyPressed(Keys.MINUS)) {
			zoom += 0.1;
			updateCamera();
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			angleY += 3;
			if(angleY > 89) angleY = 89;
			updateCamera();
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			angleY -= 3;
			if(angleY < -89) angleY = -89;
			updateCamera();
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			angleX -= 3;
			updateCamera();
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			angleX += 3;
			updateCamera();
		}
		
		// premikanje objekta
		if(Gdx.input.justTouched()) {
			lastX = Gdx.input.getX();
			lastY = Gdx.input.getY();
		}
		else if(Gdx.input.isTouched() && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			//camX += (int)((lastX - Gdx.input.getX()) * 0.01f);
			//camY += (int)((lastY - Gdx.input.getY()) * 0.01f);
			/*float x = (lastX - Gdx.input.getX()) * 0.01f;
			float y = (lastY - Gdx.input.getY()) * 0.01f;
			
			tocke[0] -= x; tocke[1] += y;
			tocke[4] -= x; tocke[5] += y;
			tocke[8] -= x; tocke[9] += y;
			
			triangle.setVertices(tocke);*/
			
			lastX = Gdx.input.getX();
			lastY = Gdx.input.getY();
		}
		
		camera.update();
		camera.apply(Gdx.gl10);

		// izris
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[]{0, 0, 1, 1}, 0);
		
		//triangle.render(GL10.GL_TRIANGLES, 0, 3);
		
		//for (Mesh face : cube)
		//	face.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		obj.render(GL.GL_TRIANGLES);
	}
	
	private void updateCamera()
	{
		double x = angleX * 2 * Math.PI/360;
		double y = angleY * 2 * Math.PI/360;
		camera.position.x = (float)Math.sin(x) * zoom * (float)Math.cos(y) + camX;
		camera.position.z = (float)Math.cos(x) * zoom * (float)Math.cos(y) ;
		camera.position.y = (float)Math.sin(y) * zoom + camY;
		camera.lookAt(0, 0, 0);
	}
	
	private void setCamera()
	{
		float aspectRatio = (float) width / (float) height;
		camera = isPerspective
				? new PerspectiveCamera(67, 2f * aspectRatio, 2f)
				: new OrthographicCamera(2f * aspectRatio, 2f);
		
		updateCamera();
	}

	@Override
	public void resize(int w, int h)
	{
		width = w;
		height = h;
		
		setCamera();
	}

	@Override
	public void resume() {
	}

}
