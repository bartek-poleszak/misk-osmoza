package bp.misk.osmosis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private final int leftBorder = 1;
    private final int bottomBorder = 18;
    private final int rightBorder = 100;
    private final int topBorder = 78;
    private World world = new World(new Vector2(0, 0), true);
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    public static Texture sampleTexture;
    private FPSLogger fpsLogger = new FPSLogger();
    private Membrane membrane;

    @Override
	public void create () {
        debugRenderer = new Box2DDebugRenderer();
        sampleTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10));
        stage.getCamera().update();
        shapeRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(stage);

        createParticles();

        createBorders();
	}

    private void createParticles() {
        int middle = (rightBorder - leftBorder) / 2;
        ParticleCreator leftParticleCreator = new ParticleCreator(shapeRenderer, world);
        ParticleCreator rightParticleCreator = new ParticleCreator(shapeRenderer, world);
        ParticleCreator.setMaxWaterParticles(100);

        leftParticleCreator.setSaltiness(0.5f);
        leftParticleCreator.setBounds(leftBorder + 2, bottomBorder, middle - 2, topBorder);
        for (ParticleActor particleActor : leftParticleCreator.createParticles())
            stage.addActor(particleActor);

        rightParticleCreator.setBounds(middle + 2, bottomBorder, rightBorder - 2, topBorder);
        for (ParticleActor particleActor : rightParticleCreator.createParticles())
            stage.addActor(particleActor);

//        for (int i = 0; i < 100; i++) {
//            float x = MathUtils.random(leftBorder + 2, rightBorder - 50);
//            float y = MathUtils.random(bottomBorder + 2, topBorder - 2);
//            ParticleActor particle = new ParticleActor(x, y, 0.5f, shapeRenderer, world);
//            stage.addActor(particle);
//        }
    }

    private void createBorders() {
        BorderActor border = new BorderActor(leftBorder, bottomBorder, leftBorder, topBorder, shapeRenderer, world);
        stage.addActor(border);
        border = new BorderActor(rightBorder, bottomBorder, rightBorder, topBorder, shapeRenderer, world);
        stage.addActor(border);
        border = new BorderActor(leftBorder, topBorder, rightBorder, topBorder, shapeRenderer, world);
        stage.addActor(border);
        border = new BorderActor(leftBorder, bottomBorder, rightBorder, bottomBorder, shapeRenderer, world);
        stage.addActor(border);

        membrane = new Membrane((rightBorder - leftBorder)/2, bottomBorder, topBorder, 1.5f, shapeRenderer, world);
        stage.addActor(membrane);
    }


    @Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(0, 0, stage.getCamera().viewportWidth, stage.getCamera().viewportHeight);
        stage.draw();
        shapeRenderer.end();
        fpsLogger.log();

//        debugRenderer.render(world, stage.getCamera().combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

	@Override
	public void dispose() {
		stage.dispose();
        sampleTexture.dispose();
	}
}
