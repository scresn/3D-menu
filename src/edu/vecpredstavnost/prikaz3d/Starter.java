package edu.vecpredstavnost.prikaz3d;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Starter {

	public static void main(String[] args) {

		new JoglApplication(new MainMenu(), "OBJ Model Viewer", 480, 320, false);
	}

}
