package bp.misk.osmosis.desktop;

import bp.misk.osmosis.DataVisualizer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bp on 03.06.16.
 */
public class JFreeGraphDataVisualizer extends ApplicationFrame implements DataVisualizer {

    private TimeSeries membranePositionSeries;
    private TimeSeries concentrationDiffSeries;
    private double lastValue = 100.0;


    public JFreeGraphDataVisualizer() {

        super("Wykresy");
        this.membranePositionSeries = new TimeSeries("Pozycja membrany", Millisecond.class);
        this.concentrationDiffSeries = new TimeSeries("Różnica stężeń", Millisecond.class);
        final TimeSeriesCollection membranePositionDataset = new TimeSeriesCollection(this.membranePositionSeries);
        final TimeSeriesCollection concentrationDiffDataset = new TimeSeriesCollection(this.concentrationDiffSeries);
        final JFreeChart membranePositionChart = createMembranePositionChart(membranePositionDataset);
        final ChartPanel membranePositionChartPanel = new ChartPanel(membranePositionChart);
        final JFreeChart concentrationDiffChart = createConcentrationDiffChart(concentrationDiffDataset);
        final ChartPanel concentrationDiffChartPanel = new ChartPanel(concentrationDiffChart);

        final JPanel content = new JPanel(new GridLayout());
        content.add(concentrationDiffChartPanel);
        concentrationDiffChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        content.add(membranePositionChartPanel);
        membranePositionChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

    }

    private JFreeChart createConcentrationDiffChart(TimeSeriesCollection dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Różnica stężeń",
                "Czas",
                "Różnica",
                dataset,
                true,
                true,
                false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-1.0, 1.0);
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
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 100.0);
        return result;
    }

    @Override
    public void updateMembranePosition(float time, float position) {
        int second = (int) time;
        int millisecond = (int) ((time - second) * 1000);
        this.membranePositionSeries.addOrUpdate(new Millisecond(millisecond, second, 0, 0, 1, 1, 1900), position);
    }

    @Override
    public void updateConcentration(float time, float left, float right) {
        float difference = left - right;
        int second = (int) time;
        int millisecond = (int) ((time - second) * 1000);
        this.concentrationDiffSeries.addOrUpdate(new Millisecond(millisecond, second, 0, 0, 1, 1, 1900), difference);
    }

    @Override
    public void initialize() {
        this.pack();
//        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
