package bp.misk.osmosis.desktop;

import com.badlogic.gdx.Gdx;

/**
 * Created by bp on 03.06.16.
 */
public class FileSaveDataVisualizer implements bp.misk.osmosis.DataVisualizer {

    @Override
    public void initialize() {

    }

    @Override
    public void updateMembranePosition(float time, float position) {

    }


    @Override
    public void updateConcentration(float time, float left, float right) {
        left = 1 - left;
        right = 1 - right;
        float difference = left - right;
        float meanDifference = getMeanDifference(difference);
        if (achievedZeroMean(meanDifference) && !achivedZeroFirstTime) {
            System.out.println(" " + time);
            achivedZeroFirstTime = true;
            Gdx.app.exit();
        }
    }

    private boolean achivedZeroFirstTime = false;
    private float[] differences = new float[10];
    private int diffIndex = 0;
    private boolean firstData = true;

    private boolean achievedZeroMean(float meanDifference) {
        if (meanDifference > -0.01 && meanDifference < 0.01) {
            return true;
        }
        return false;
    }

    private float getMeanDifference(float newDifference) {
        if (firstData) {
            for (int i = 0; i < differences.length; i++)
                differences[i] = newDifference;
            firstData = false;
            return newDifference;
        }
        else {
            differences[diffIndex] = newDifference;
            diffIndex = (diffIndex + 1) % differences.length;
            float diffSum = 0;
            for (float difference : differences)
                diffSum += difference;

            return diffSum / differences.length;
        }
    }
}
