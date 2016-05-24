package bp.misk.osmosis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bp on 24.05.16.
 */
public class BorderActor extends Actor {
    private ShapeRenderer renderer;
    private float leftBorder;
    private float rightBorder;
    private float topBorder;
    private float bottomBorder;

    public BorderActor(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    public void setBorders(float left, float bottom, float right, float top) {
        leftBorder = left;
        rightBorder = right;
        topBorder = top;
        bottomBorder = bottom;
    }

    public void makeCollisionWithParticle(DotActor particle) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setColor(Color.BLACK);
        renderer.rect(leftBorder, bottomBorder, rightBorder - leftBorder, topBorder - bottomBorder);
    }
}
