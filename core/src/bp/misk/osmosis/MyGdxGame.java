package bp.misk.osmosis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends ApplicationAdapter {
	private Stage stage;
    private ShapeRenderer shapeRenderer;
    public static Texture sampleTexture;
    private FPSLogger fpsLogger = new FPSLogger();
	
	@Override
	public void create () {

        sampleTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
		stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(stage);
	}

    private void createBorders() {
    }


    @Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

    int frameCount = 0;
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        stage.draw();
        shapeRenderer.end();
        fpsLogger.log();
    }

	@Override
	public void dispose() {
		stage.dispose();
        sampleTexture.dispose();
	}
}
