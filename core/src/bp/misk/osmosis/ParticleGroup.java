package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by bp on 09.05.16.
 */
public class ParticleGroup extends Group {

    public void setSpeed(float speed) {
        for (Actor actor : getChildren()) {
            DotActor dot = (DotActor) actor;
            dot.setInitialSpeed(speed);
        }
    }

    public void setDotColor(Color color) {
        for (Actor actor : getChildren()) {
            DotActor dot = (DotActor) actor;
            dot.setDotColor(color);
        }
    }
}
