package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by bp on 29.05.16.
 */
public class ParticleCreator {
    private static final float WATER_RADIUS = 0.5f;
    private static final float SALT_RADIUS = 1f;
    private final World world;
    private final ShapeRenderer renderer;

    private float leftBound;
    private float rightBound;
    private float topBound;
    private float bottomBound;

    private float saltiness;
    private static int maxWaterParticles;
    private ArrayList<ParticleActor> result;

    public ParticleCreator(ShapeRenderer renderer, World world) {
        this.renderer = renderer;
        this.world = world;
    }

    public void setBounds(float left, float bottom, float right, float top) {
        leftBound = left;
        rightBound = right;
        bottomBound = bottom;
        topBound = top;
    }

    public void setSaltiness(float saltiness) {
        this.saltiness = saltiness;
    }

    public static void setMaxWaterParticles(int maxWaterParticles) {
        ParticleCreator.maxWaterParticles = maxWaterParticles;
    }

    public ArrayList<ParticleActor> createParticles() {
        result = new ArrayList<ParticleActor>();
        int waterParticles = (int) ((1 - saltiness) * maxWaterParticles);
        int saltParticles = (int) ((saltiness * maxWaterParticles) / 4);
        addParticlesToResult(waterParticles, Color.BLUE, WATER_RADIUS);
        addParticlesToResult(saltParticles, Color.ORANGE, SALT_RADIUS);
        return result;
    }

    public void addParticlesToResult(int count, Color color, float radius) {
        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(leftBound, rightBound);
            float y = MathUtils.random(bottomBound, topBound);
            ParticleActor particle = new ParticleActor(x, y, radius, renderer, world);
            particle.setColor(color);
            result.add(particle);
        }
    }
}
