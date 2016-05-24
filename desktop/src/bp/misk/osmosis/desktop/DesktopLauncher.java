package bp.misk.osmosis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import bp.misk.osmosis.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 800;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;
		config.vSyncEnabled = false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
