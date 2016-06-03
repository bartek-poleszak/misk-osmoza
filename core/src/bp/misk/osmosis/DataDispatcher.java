package bp.misk.osmosis;

/**
 * Created by bp on 03.06.16.
 */
public class DataDispatcher {
    private final DataVisualizer visualizer;
    private final ParticleManager particleManager;
    private float time = 0;
    private float dispatchInterval = 1;
    private float timeSinceLastDispatch = dispatchInterval;

    public DataDispatcher(DataVisualizer visualizer, ParticleManager particleManager) {
        this.visualizer = visualizer;
        this.particleManager = particleManager;
        visualizer.initialize();
    }

    public void setDispatchInterval(float dispatchInterval) {
        this.dispatchInterval = dispatchInterval;
        timeSinceLastDispatch = dispatchInterval;
    }

    public void update(float delta) {
        this.time += delta;
        timeSinceLastDispatch += delta;
        if (timeSinceLastDispatch >= dispatchInterval)
            dispatch();
    }

    private void dispatch() {
        timeSinceLastDispatch = 0;
        ParticleManager.AreaData left = particleManager.getLeftAreaData();
        ParticleManager.AreaData right = particleManager.getRightAreaData();

        visualizer.updateMembranePosition(time, particleManager.getRelativeMembranePosition());
        visualizer.updateConcentration(time, left.getWaterConcentration(), right.getWaterConcentration());
    }
}
