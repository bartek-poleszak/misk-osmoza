package bp.misk.osmosis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private final int leftBorder = 1;
    private final int bottomBorder = 1;
    private final int rightBorder = 101;
    private final int topBorder = 61;
    private World world = new World(new Vector2(0, 0), true);
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    public static Texture sampleTexture;
    private FPSLogger fpsLogger = new FPSLogger();
    private ParticleManager particleManager = new ParticleManager(leftBorder, rightBorder);
    private GameConfig config;
    private DataDispatcher dataDispatcher;

    public MyGdxGame(GameConfig config) {
        this.config = config;
    }

    @Override
	public void create () {
        debugRenderer = new Box2DDebugRenderer();
        sampleTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10));
        stage.getCamera().update();
        shapeRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(stage);
        dataDispatcher = new DataDispatcher(config.dataVisualizer, particleManager);
        dataDispatcher.setDispatchInterval(1f);

        createParticles();
        createBorders();
    }

    private void createParticles() {

        ParticleCreator.setMaxWaterParticles(config.maxWaterParticles);
        ParticleCreator.setWaterRadius(config.waterParticleRadius);

        int middle = (rightBorder - leftBorder) / 2;
        ParticleCreator leftParticleCreator = new ParticleCreator(shapeRenderer, world);
        ParticleCreator rightParticleCreator = new ParticleCreator(shapeRenderer, world);

        leftParticleCreator.setSaltiness(config.leftSaltiness);
        leftParticleCreator.setBounds(leftBorder + 2, bottomBorder, middle - 2, topBorder);
        rightParticleCreator.setSaltiness(config.rightSaltiness);
        rightParticleCreator.setBounds(middle + 2, bottomBorder, rightBorder - 2, topBorder);

        ArrayList<ParticleActor> particles = new ArrayList<ParticleActor>();
        particles.addAll(leftParticleCreator.createParticles());
        particles.addAll(rightParticleCreator.createParticles());
        for (ParticleActor particle : particles) {
            stage.addActor(particle);
            particleManager.addParticle(particle);
        }

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

        Membrane.setHoleDistance(config.holeDistance);
        Membrane membrane = new Membrane((rightBorder - leftBorder) / 2, bottomBorder, topBorder, 3f * config.waterParticleRadius, shapeRenderer, world);
        stage.addActor(membrane);
        particleManager.setMembrane(membrane);
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
//        fpsLogger.log();

        for (int i = 0; i < config.frameSkip+1; i++) {
            stage.act();
            particleManager.update();

//        debugRenderer.render(world, stage.getCamera().combined);
            float delta = 0.016f;
            world.step(delta, 6, 2);
            dataDispatcher.update(delta);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(0, 0, stage.getCamera().viewportWidth, stage.getCamera().viewportHeight);
        stage.draw();
        shapeRenderer.end();
    }

    @Override
	public void dispose() {
		stage.dispose();
        sampleTexture.dispose();
	}
}
