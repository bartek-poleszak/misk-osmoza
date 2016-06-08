package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by bp on 29.05.16.
 */
public class ParticleCreator {
    private static float waterRadius;
    private static float saltRadius;
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

    public static void setWaterRadius(float waterRadius) {
        ParticleCreator.waterRadius = waterRadius;
        ParticleCreator.saltRadius = 2*waterRadius;
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
        addParticlesToResult(waterParticles, Color.BLUE, waterRadius);
        addParticlesToResult(saltParticles, Color.ORANGE, saltRadius);
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
