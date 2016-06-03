package bp.misk.osmosis;

/**
 * Created by bp on 03.06.16.
 */
public interface DataVisualizer {
    void initialize();
    void updateMembranePosition(float time, float position);
    void updateConcentration(float time, float left, float right);
}
