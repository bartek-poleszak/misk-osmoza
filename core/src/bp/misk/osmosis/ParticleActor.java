package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 27.05.16.
 */
public class ParticleActor extends Actor {
    private float radius;
    private ShapeRenderer renderer;
    private final CircleShape circleShape;
    private Color color = Color.BLUE;
    private final Body body;

    public ParticleActor(float initialX, float initialY, float radius, ShapeRenderer renderer, World world) {
        this.radius = radius;
        this.renderer = renderer;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(initialX, initialY);
        setPosition(initialX, initialY);

        body = world.createBody(bodyDef);

        circleShape = new CircleShape();
        circleShape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1f; // Make it bounce a little bit

        body.createFixture(fixtureDef);
        setInitialVelocity();
    }

    private void setInitialVelocity() {
        float x = MathUtils.random(-40, 40);
        float y = MathUtils.random(-40, 40);
//        if (Math.abs(x) + Math.abs(y) < 40)
//            setInitialVelocity();
        body.setLinearVelocity(x, y);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void act(float delta) {
        Vector2 pos = body.getWorldCenter();
        setPosition(pos.x, pos.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setColor(color);
        renderer.circle(getX(), getY(), radius);
    }

    public void dispose() {
        circleShape.dispose();
    }
}
