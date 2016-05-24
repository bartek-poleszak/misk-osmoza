package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 09.05.16.
 */
public class DotActor extends Actor {
    private ShapeRenderer renderer;
    private Color color = Color.BLUE;
    private int radius = 5;
    private Vector2 velocity = new Vector2(0, 0);

    public DotActor(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    public void setDotColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setInitialSpeed(float speed) {
        velocity.set(velocity.x * speed, velocity.y*speed);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setColor(color);
        renderer.circle(getX(), getY(), radius);
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void act(float delta) {
        float newX = getX()+(velocity.x * delta);
        float newY = getY()+(velocity.y * delta);
        setPosition(newX, newY);
    }
}
