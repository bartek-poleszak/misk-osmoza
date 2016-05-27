package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 24.05.16.
 */
public class HorizontalBorderActor extends Actor implements BorderActor {
    private ShapeRenderer renderer;
    private float leftX;
    private float leftY;
    private float rightX;
    private float length;

    public HorizontalBorderActor(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void setLinePosition(float x, float y, float length) {
        leftX = x;
        leftY = y;
        this.length = length;
        rightX = leftX + length;
    }

    @Override
    public void makeCollisionWithParticle(DotActor particle) {
        if (    particle.getX() > leftX - particle.getRadius() &&
                particle.getX() < rightX + particle.getRadius()       )
        {
            float dist = particle.getY() - leftY;
            if (Math.abs(dist) <= particle.getRadius()) {
                if (particle.getDiff().y < 0)
                    particle.setVelocity(particle.getVelocity().x, Math.abs(particle.getVelocity().y));
                else
                    particle.setVelocity(particle.getVelocity().x, -Math.abs(particle.getVelocity().y));
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setColor(Color.BLACK);
        renderer.rect(leftX, leftY, length, 5);
    }
}
