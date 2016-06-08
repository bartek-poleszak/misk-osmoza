package bp.misk.osmosis.desktop;

import bp.misk.osmosis.DataVisualizer;
import com.badlogic.gdx.math.MathUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by bp on 03.06.16.
 */
public class JFreeGraphDataVisualizer extends ApplicationFrame implements DataVisualizer {

    private TimeSeries membranePositionSeries;
    private TimeSeries concentrationDiffSeries;
    private TimeSeries concentrationLeftSeries;
    private TimeSeries concentrationRightSeries;
    private TimeSeries concentrationDiffMeanSeries;

    public JFreeGraphDataVisualizer() {

        super("Wykresy");
        this.membranePositionSeries = new TimeSeries("Pozycja membrany", Millisecond.class);
        this.concentrationDiffSeries = new TimeSeries("Różnica stężeń", Millisecond.class);
        this.concentrationDiffMeanSeries = new TimeSeries("Różnica stężeń - średnia 10 pomiarów", Millisecond.class);
        this.concentrationLeftSeries = new TimeSeries("Stężenie soli po lewej stronie membrany", Millisecond.class);
        this.concentrationRightSeries = new TimeSeries("Stężenie soli po prawej stronie membrany", Millisecond.class);
        final TimeSeriesCollection membranePositionDataset = new TimeSeriesCollection(this.membranePositionSeries);
        final TimeSeriesCollection concentrationDiffDataset = new TimeSeriesCollection(this.concentrationDiffSeries);
        final TimeSeriesCollection concentrationDiffMeanDataset = new TimeSeriesCollection(this.concentrationDiffMeanSeries);
        final TimeSeriesCollection concentrationLeftDataset = new TimeSeriesCollection(this.concentrationLeftSeries);
        final TimeSeriesCollection concentrationRightDataset = new TimeSeriesCollection(this.concentrationRightSeries);
        final JFreeChart membranePositionChart = createMembranePositionChart(membranePositionDataset);
        final ChartPanel membranePositionChartPanel = new ChartPanel(membranePositionChart);
        final JFreeChart concentrationChart = createConcentrationChart(concentrationDiffDataset, concentrationLeftDataset, concentrationRightDataset, concentrationDiffMeanDataset);
        final ChartPanel concentrationChartPanel = new ChartPanel(concentrationChart);

        final JPanel content = new JPanel(new GridLayout());
        content.add(concentrationChartPanel);
        concentrationChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        content.add(membranePositionChartPanel);
        membranePositionChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

    }

    private JFreeChart createConcentrationChart(TimeSeriesCollection dataset, TimeSeriesCollection leftDataset, TimeSeriesCollection rightDataset, TimeSeriesCollection diffMeanDataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Stężenia",
                "Czas",
                "Różnica",
                dataset,
                true,
                true,
                false
        );
        final XYPlot plot = result.getXYPlot();
        plot.setDataset(1, leftDataset);
        plot.setRenderer(1, new StandardXYItemRenderer());
        plot.setDataset(2, rightDataset);
        plot.setRenderer(2, new StandardXYItemRenderer());
        plot.setDataset(3, diffMeanDataset);
        plot.setRenderer(3, new StandardXYItemRenderer());
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
//        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-1.01, 1.01);
        return result;
    }

    private JFreeChart createMembranePositionChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Pozycja membrany",
                "Czas",
                "Pozycja",
                dataset,
                true,
                true,
                false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
//        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 100.0);
        return result;
    }

    @Override
    public void updateMembranePosition(float time, float position) {
        this.membranePositionSeries.add(new Millisecond(new Date((long) (time * 1000))), position);
    }

    @Override
    public void updateConcentration(float time, float left, float right) {
        left = 1 - left;
        right = 1 - right;
        float difference = left - right;
        Millisecond period = new Millisecond(new Date((long) (time * 1000)));
        float meanDifference = getMeanDifference(difference);
        if (achievedZeroMean(meanDifference))
            System.out.println("Zero: " + time);
        this.concentrationDiffSeries.add(period, difference);
        this.concentrationDiffMeanSeries.add(period, meanDifference);
        this.concentrationLeftSeries.add(period, left);
        this.concentrationRightSeries.add(period, right);
    }

    private float initMeanSign;
    private boolean achievedZeroMeanDiff = false;
    private float[] differences = new float[10];
    private int diffIndex = 0;
    private boolean firstData = true;

    private boolean achievedZeroMean(float meanDifference) {
        if (meanDifference > -0.01 && meanDifference < 0.01) {
            return true;
        }
        return false;
    }

    private boolean achievedZeroMeanFirstTime(float meanDifference) {
        if (initMeanSign != Math.signum(meanDifference) && !achievedZeroMeanDiff) {
            achievedZeroMeanDiff = true;
            return true;
        }
        return false;
    }

    private float getMeanDifference(float newDifference) {
        if (firstData) {
            initMeanSign = Math.signum(newDifference);
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

    @Override
    public void initialize() {
        this.pack();
//        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
