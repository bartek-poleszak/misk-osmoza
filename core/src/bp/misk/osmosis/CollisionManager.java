package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by bp on 10.05.16.
 */
public class CollisionManager {
    private ArrayList<DotActor> particles = new ArrayList<DotActor>();
    private ArrayList<BorderActor> borders = new ArrayList<BorderActor>();
    private float leftBorder;
    private float rightBorder;
    private float topBorder;
    private float bottomBorder;

    public void setBorders(float left, float bottom, float right, float top) {
        leftBorder = left;
        rightBorder = right;
        topBorder = top;
        bottomBorder = bottom;
    }

    public void addParticles(DotActor particle) {
        particles.add(particle);
    }

    public void act() {
        checkBorderCollisions();
        checkCollisionsWithEachOther();
    }

    private void checkCollisionsWithEachOther() {
        for (int i = 0; i < particles.size(); i++) {
            DotActor pi = particles.get(i);
//            pi.setDotColor(Color.BLUE);
            for (int j = i+1; j < particles.size(); j++) {
                DotActor pj = particles.get(j);
                float largerRadius = pi.getRadius() < pj.getRadius() ? pj.getRadius() : pi.getRadius();
                float xdiff = pi.getX() - pj.getX();
                float ydiff = pi.getY() - pj.getY();
                double distance = Math.sqrt(xdiff*xdiff + ydiff*ydiff);
                if (distance <= largerRadius)
                    executeCollision(pi, pj);
            }
        }
    }

    private void executeCollision(DotActor pi, DotActor pj) {
//        pi.setDotColor(Color.GREEN);
//        float collisionPointX =
//                ((pi.getX() * pj.getRadius()) + (pj.getX() * pi.getRadius()))
//                        / (pi.getRadius() + pj.getRadius());
//
//        float collisionPointY =
//                ((pi.getY() * pj.getRadius()) + (pj.getY() * pi.getRadius()))
//                        / (pi.getRadius() + pj.getRadius());

        float velX1 = pi.getVelocity().x;
        float velY1 = pi.getVelocity().y;
        float velX2 = pj.getVelocity().x;
        float velY2 = pj.getVelocity().y;
        float newVelX1 = (velX1 * (pi.getRadius() - pj.getRadius()) + (2 * pj.getRadius() * velX2)) / (pi.getRadius() + pj.getRadius());
        float newVelX2 = (velX2 * (pj.getRadius() - pi.getRadius()) + (2 * pi.getRadius() * velX1)) / (pi.getRadius() + pj.getRadius());
        float newVelY1 = (velY1 * (pi.getRadius() - pj.getRadius()) + (2 * pj.getRadius() * velY2)) / (pi.getRadius() + pj.getRadius());
        float newVelY2 = (velY2 * (pj.getRadius() - pi.getRadius()) + (2 * pi.getRadius() * velY1)) / (pi.getRadius() + pj.getRadius());
        pi.setVelocity(newVelX1, newVelY1);
        pj.setVelocity(newVelX2, newVelY2);
    }

    private void checkBorderCollisions() {
        for (DotActor particle : particles) {
            if (particle.getX() - particle.getRadius() <= leftBorder) {
                particle.setVelocity(Math.abs(particle.getVelocity().x), particle.getVelocity().y);
            } else if (particle.getX() + particle.getRadius() >= rightBorder) {
                particle.setVelocity(-Math.abs(particle.getVelocity().x), particle.getVelocity().y);
            }
            if (particle.getY() - particle.getRadius() <= bottomBorder) {
                particle.setVelocity(particle.getVelocity().x, Math.abs(particle.getVelocity().y));
            } else if (particle.getY() + particle.getRadius() >= topBorder) {
                particle.setVelocity(particle.getVelocity().x, -Math.abs(particle.getVelocity().y));
            }
        }
    }

    public void drawBorders(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(leftBorder, bottomBorder, rightBorder - leftBorder, topBorder - bottomBorder);
    }

    public ArrayList<DotActor> getParticles() {
        return particles;
    }
}
