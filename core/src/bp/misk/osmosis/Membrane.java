package bp.misk.osmosis;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

/**
 * Created by bp on 27.05.16.
 */
public class Membrane extends Group {
    private static float holeDistance;
    private ShapeRenderer renderer;

    public Membrane(float x, float bottom, float top, float space, ShapeRenderer renderer, World world) {
        this.renderer = renderer;
        setPosition(x, bottom);
        for (float y = bottom; y <= top; y += space + holeDistance) {
            float yy = Math.min(y+ holeDistance, top);
            BorderActor actor = new BorderActor(x, y, x, yy, renderer, world);
            addActor(actor);
        }
    }

    public static void setHoleDistance(float holeDistance) {
        Membrane.holeDistance = holeDistance;
    }

    public void dispose() {
        for (Actor actor : getChildren()) {
            BorderActor borderActor = (BorderActor) actor;
            borderActor.dispose();
        }
    }

    public void setXVelocity(float vel) {
        for (Actor actor : getChildren()) {
            BorderActor borderActor = (BorderActor) actor;
            borderActor.setXVelocity(vel);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getChildren().size > 0) {

            setPosition(getChildren().get(0).getX(), getY());
        }

    }
}
