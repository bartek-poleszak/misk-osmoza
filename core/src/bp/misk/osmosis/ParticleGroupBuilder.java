package bp.misk.osmosis;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Created by bp on 09.05.16.
 */
public class ParticleGroupBuilder {
    private ParticleGroup group = new ParticleGroup();
    private ShapeRenderer renderer;
    private int amount = 100;
    private Vector2 bottomLeft = new Vector2(30, 40);
    private Vector2 topRight = new Vector2(790, 690);
    private ArrayList<DotActor> xSorted = new ArrayList<DotActor>();
    private ActorXComparator actorXComparator = new ActorXComparator();
    private int radius = 10;

    public ParticleGroupBuilder(ShapeRenderer renderer) {
        this.renderer = renderer;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ParticleGroup create() {
        for (int i = 0; i < amount; i++) {
            DotActor dot = new DotActor(renderer);
            dot.setRadius(radius);
            randomizePosition(dot);
            randomizeDirection(dot);
            group.addActor(dot);
        }
        group.setSpeed(300f);
        return group;
    }

    private void randomizeDirection(DotActor dot) {
        int randx = MathUtils.random(-1, 1);
        int randy = MathUtils.random(-1, 1);
        if (Math.abs(randx) + Math.abs(randy) < 1)
            randomizeDirection(dot);
        else
            dot.setVelocity(new Vector2(randx, randy));
    }

    private void randomizePosition(DotActor dot) {
        float xPosition = MathUtils.random(bottomLeft.x, topRight.x);
        float yPosition = MathUtils.random(bottomLeft.y, topRight.y);
        if (newParticleDoesntIntersect(xPosition, yPosition, dot.getRadius())) {
            dot.setPosition(xPosition, yPosition);
            xSorted.add(dot);
            xSorted.sort(actorXComparator);
        }
        else
            randomizePosition(dot);
    }

    private boolean newParticleDoesntIntersect(float xPosition, float yPosition, int radius) {
        float borderDelta = radius*2;
        float leftBorder = xPosition - borderDelta;
        float rightBorder = xPosition + borderDelta;
        float bottomBorder = yPosition - borderDelta;
        float topBorder = yPosition + borderDelta;
        int i;
        for (i = xSorted.size() - 1; i >= 0; i--)
            if (xSorted.get(i).getX() < leftBorder)
                break;
        if (i < 0)
            i = 0;
        for ( ; i < xSorted.size(); i++) {
            if (xSorted.get(i).getX() > rightBorder)
                break;
            float y = xSorted.get(i).getY();
            if (y > bottomBorder && y < topBorder)
                return false;
        }

        return true;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setBorders(float left, float bottom, float right, float top) {
        bottomLeft.set(left, bottom);
        topRight.set(right, top);
    }

    private static class ActorXComparator implements Comparator<Actor> {

        @Override
        public int compare(Actor o1, Actor o2) {
            return (int) Math.signum(o1.getX() - o2.getX());
        }
    }
}
