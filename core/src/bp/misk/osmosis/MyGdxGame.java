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
    private CollisionManager collisionManager = new CollisionManager();
    public static Texture sampleTexture;
    private ConcentrationGrid concentrationGrid = new ConcentrationGrid();
    private FPSLogger fpsLogger = new FPSLogger();
	
	@Override
	public void create () {

        sampleTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
		stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        setBorders();
        ParticleGroupBuilder builder = new ParticleGroupBuilder(shapeRenderer);

        ParticleGroup water = builder.create();
        for (Actor actor : water.getChildren())
            collisionManager.addParticles((DotActor) actor);

        ParticleGroupBuilder dustBuilder = new ParticleGroupBuilder(shapeRenderer);
        dustBuilder.setAmount(10);
        dustBuilder.setRadius(30);
        ParticleGroup salt = dustBuilder.create();
        salt.setDotColor(Color.ORANGE);
        for (Actor actor : salt.getChildren())
            collisionManager.addParticles((DotActor) actor);

        concentrationGrid.setParticles(collisionManager.getParticles());
        concentrationGrid.setCellDensity(2, 2);
        stage.addActor(water);
        stage.addActor(salt);

        BorderActor leftBorder = new BorderActor(shapeRenderer);
        leftBorder.setBorders(10, 10, 20, 750);

        stage.addActor(leftBorder);

		Gdx.input.setInputProcessor(stage);
	}

    private void setBorders() {
        float left = 20;
        float bottom = 30;
        float top = 700;
        float right = 800;
        collisionManager.setBorders(left, bottom, right, top);
        concentrationGrid.setBorders(left, bottom, right, top);
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

        collisionManager.act();
        concentrationGrid.checkConcentration();
        stage.act();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        collisionManager.drawBorders(shapeRenderer);
//        concentrationGrid.draw(shapeRenderer);
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
