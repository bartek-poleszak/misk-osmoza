package bp.misk.osmosis;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

/**
 * Created by bp on 24.05.16.
 */
public class Membrane extends Group {
    private final float bottomX;
    private final float bottomY;
    private final float height;
    private final float space;
    private final ShapeRenderer renderer;
    private ArrayList<VerticalBorderActor> segments = new ArrayList<VerticalBorderActor>();

    public Membrane(float bottomX, float bottomY, float height, float space, ShapeRenderer renderer) {
        this.bottomX = bottomX;
        this.bottomY = bottomY;
        this.height = height;
        this.space = space;
        this.renderer = renderer;

        createLines();
    }

    private void createLines() {
        float topY = bottomY + height;
        for (float y = bottomY; y < topY; y += 2*space) {
            VerticalBorderActor border = new VerticalBorderActor(renderer);
            border.setLinePosition(bottomX, y, space);
            this.addActor(border);
            segments.add(border);
        }
    }

    public void makeCollisionWithParticle(DotActor particle) {
        for (VerticalBorderActor segment : segments) {
            segment.makeCollisionWithParticle(particle);
        }
    }

}
