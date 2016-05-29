package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 27.05.16.
 */
public class BorderActor extends Actor {
    private ShapeRenderer renderer;
    private final EdgeShape shape;
    private Color color = Color.BLACK;
    private final Body body;
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;


    public BorderActor(float x1, float y1, float x2, float y2, ShapeRenderer renderer, World world) {
        this.renderer = renderer;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        shape = new EdgeShape();
        shape.set(x1, y1, x2, y2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1f; // Make it bounce a little bit

        body.createFixture(fixtureDef);
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
        renderer.line(x1, y1, x2, y2);
    }

    public void dispose() {
        shape.dispose();
    }
}
