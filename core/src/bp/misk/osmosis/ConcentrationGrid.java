package bp.misk.osmosis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Comparator;
import java.util.List;

/**
 * Created by bp on 10.05.16.
 */
public class ConcentrationGrid {
    private float leftBorder;
    private float rightBorder;
    private float topBorder;
    private float bottomBorder;

    private int xCells;
    private int yCells;

    private float cellWidth;
    private float cellHeight;
    private int[][] buckets;

    private List<DotActor> particles;

    public void setBorders(float left, float bottom, float right, float top) {
        leftBorder = left;
        rightBorder = right;
        topBorder = top;
        bottomBorder = bottom;
        calculateCellDimensions();
    }

    public void setCellDensity(int xCells, int yCells) {
        this.xCells = xCells;
        this.yCells = yCells;
        buckets = new int[xCells][yCells];
        calculateCellDimensions();
    }

    public void setParticles(List<DotActor> particles) {
        this.particles = particles;
    }

    private void calculateCellDimensions() {
        cellWidth = (rightBorder - leftBorder) / xCells;
        cellHeight = (topBorder - bottomBorder) / yCells;
    }

    public void draw(ShapeRenderer renderer) {
        float currentX = leftBorder;
        float currentY = bottomBorder;
        for (int y = 0; y < yCells; y++) {
            for (int x = 0; x < xCells; x++) {
                float concentration = (float)buckets[x][y] / particles.size();
                renderer.setColor(0, concentration + 0.2f, 0, 1);
                renderer.rect(currentX, currentY, cellWidth, cellHeight);
                currentX += cellWidth;
            }
            currentY += cellHeight;
            currentX = leftBorder;
        }
    }

    public void checkConcentration() {
        clearBuckets();
        for (DotActor particle : particles) {
            float normalizedX = particle.getX() - leftBorder;
            float normalizedY = particle.getY() - bottomBorder;
            int xBucket = (int) (normalizedX / cellWidth);
            int yBucket = (int) (normalizedY / cellHeight);
            if (xBucket >= xCells) xBucket = xCells - 1;
            if (yBucket >= yCells) yBucket = yCells - 1;
            buckets[xBucket][yBucket]++;
        }
    }

    private void clearBuckets() {
        for (int[] bucket : buckets) {
            for (int i = 0; i < bucket.length; i++) {
                bucket[i] = 0;
            }
        }
    }

}
