package bp.misk.osmosis;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by bp on 24.05.16.
 */
public interface BorderActor {
    void setLinePosition(float x, float y, float length);

    void makeCollisionWithParticle(DotActor particle);

    void draw(Batch batch, float parentAlpha);
}
