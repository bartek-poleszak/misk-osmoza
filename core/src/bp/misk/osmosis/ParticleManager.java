package bp.misk.osmosis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * Created by bp on 29.05.16.
 */
public class ParticleManager {
    private ArrayList<ParticleActor> particles = new ArrayList<ParticleActor>();
    private Membrane membrane;

    private AreaData leftAreaData = new AreaData();
    private AreaData rightAreaData = new AreaData();

    private float leftBorder;
    private float width;

    public ParticleManager(float leftBorder, float rightBorder) {
        this.leftBorder = leftBorder;
        this.width = rightBorder - leftBorder;
    }

    public void addParticle(ParticleActor particle) {
        particles.add(particle);
    }

    public void setMembrane(Membrane membrane) {
        this.membrane = membrane;
    }

    public AreaData getLeftAreaData() {
        return leftAreaData;
    }

    public AreaData getRightAreaData() {
        return rightAreaData;
    }

    public void update() {
        leftAreaData.reset();
        rightAreaData.reset();
        AreaData currentAreaData;
        for (ParticleActor particle : particles) {
            currentAreaData = determineArea(particle);
            if (particle.getColor() == Color.BLUE)
                currentAreaData.waterParticles++;
            else
                currentAreaData.saltParticles++;
        }

        setMembraneVelocity();
    }

    private void setMembraneVelocity() {
        float leftWeight = calculateWeight(leftAreaData);
        int rightWeight = calculateWeight(rightAreaData);

        float desiredPositionFrac = leftWeight / (rightWeight + leftWeight);
        float desiredPosition = leftBorder + desiredPositionFrac*width;
        float velocity = desiredPosition - membrane.getX();

        membrane.setXVelocity(velocity);

    }

    private int calculateWeight(AreaData areaData) {
        return areaData.waterParticles + 4*areaData.saltParticles;
    }

    private AreaData determineArea(ParticleActor particle) {
        if (particle.getX() < membrane.getX())
            return leftAreaData;
        else
            return rightAreaData;
    }

    public static class AreaData {
        public int waterParticles;
        public int saltParticles;

        public void reset() {
            waterParticles = 0;
            saltParticles = 0;
        }

        public float getWaterConcentration() {
            int total = waterParticles + saltParticles;
            if (total == 0)
                return 0;
            return ((float) waterParticles) / (total);
        }
    }

}
