package edu.SimonCresnjovnjak.meni;

public interface Stanje {

	public void onCreate();

	public void onRender();

	public void onResize(int sirina, int visina);

	public void onDestroy();
}
