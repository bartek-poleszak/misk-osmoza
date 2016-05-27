package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 24.05.16.
 */
public class VerticalBorderActor extends Actor implements BorderActor {
    private ShapeRenderer renderer;
    private float bottomX;
    private float bottomY;
    private float topY;
    private float length;

    public VerticalBorderActor(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void setLinePosition(float x, float y, float length) {
        bottomX = x;
        bottomY = y;
        this.length = length;
        topY = bottomY + length;
    }

    @Override
    public void makeCollisionWithParticle(DotActor particle) {
        if (    particle.getY() > bottomY - particle.getRadius() &&
                particle.getY() < topY + particle.getRadius()       )
        {
            float dist = particle.getX() - bottomX;
            if (Math.abs(dist) <= particle.getRadius()) {
                if (particle.getDiff().x < 0)
                    particle.setVelocity(Math.abs(particle.getVelocity().x), particle.getVelocity().y);
                else
                    particle.setVelocity(-Math.abs(particle.getVelocity().x), particle.getVelocity().y);
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setColor(Color.BLACK);
        renderer.rect(bottomX, bottomY, 5, length);
    }
}
