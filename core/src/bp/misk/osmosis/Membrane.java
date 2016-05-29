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
    private ShapeRenderer renderer;

    public Membrane(float x, float bottom, float top, float space, ShapeRenderer renderer, World world) {
        this.renderer = renderer;
        for (float y = bottom; y <= top; y += 2*space) {
            float yy = Math.min(y+space, top);
            BorderActor actor = new BorderActor(x, y, x, yy, renderer, world);
            addActor(actor);
        }
    }

    public void dispose() {
        for (Actor actor : getChildren()) {
            BorderActor borderActor = (BorderActor) actor;
            borderActor.dispose();
        }
    }

    public float getCurrentPosition() {
        return getChildren().get(0).getX();
    }
}
