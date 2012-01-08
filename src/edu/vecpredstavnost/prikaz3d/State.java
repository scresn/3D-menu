package edu.vecpredstavnost.prikaz3d;

public interface State {

	public void onCreate();

	public void onRender();

	public void onResize(int w, int h);

	public void onDestroy();
}
