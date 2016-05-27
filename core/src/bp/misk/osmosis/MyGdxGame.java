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
        ParticleGroupBuilder builder = new ParticleGroupBuilder(shapeRenderer);
        builder.setRadius(5);
        ParticleGroup water = builder.create();
        for (Actor actor : water.getChildren())
            collisionManager.addParticles((DotActor) actor);

        ParticleGroupBuilder dustBuilder = new ParticleGroupBuilder(shapeRenderer);
        dustBuilder.setAmount(10);
        dustBuilder.setRadius(10);
        dustBuilder.setBorders(20, 20, 200, 200);
        ParticleGroup salt = dustBuilder.create();
        salt.setDotColor(Color.ORANGE);
        for (Actor actor : salt.getChildren())
            collisionManager.addParticles((DotActor) actor);

        concentrationGrid.setParticles(collisionManager.getParticles());
        concentrationGrid.setCellDensity(2, 2);
        stage.addActor(water);
        stage.addActor(salt);

        createBorders();

		Gdx.input.setInputProcessor(stage);
	}

    private void createBorders() {
        VerticalBorderActor leftBorder = new VerticalBorderActor(shapeRenderer);
        int bottom = 10;
        int left = 10;
        int width = 800;
        int height = 750;
        leftBorder.setLinePosition(bottom, left, height);
        VerticalBorderActor rightBorder = new VerticalBorderActor(shapeRenderer);
        rightBorder.setLinePosition(width, left, height);
        HorizontalBorderActor bottomBorder = new HorizontalBorderActor(shapeRenderer);
        bottomBorder.setLinePosition(bottom, left, width);

        HorizontalBorderActor topLeft = new HorizontalBorderActor(shapeRenderer);
        topLeft.setLinePosition(bottom, height - 300, width / 2);
        HorizontalBorderActor topRight = new HorizontalBorderActor(shapeRenderer);
        topRight.setLinePosition(bottom + width / 2, height - 400, width / 2);

        collisionManager.addBorder(leftBorder);
        collisionManager.addBorder(rightBorder);
        collisionManager.addBorder(bottomBorder);
        collisionManager.addBorder(topLeft);
        collisionManager.addBorder(topRight);
        stage.addActor(leftBorder);
        stage.addActor(rightBorder);
        stage.addActor(bottomBorder);
        stage.addActor(topLeft);
        stage.addActor(topRight);

        Membrane membrane = new Membrane(width / 2, bottom, height, 15, shapeRenderer);
        collisionManager.setMembrane(membrane);
        stage.addActor(membrane);

        concentrationGrid.setBorders(left, bottom, left + width, bottom + height);
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
        stage.act();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        concentrationGrid.checkConcentration();
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
