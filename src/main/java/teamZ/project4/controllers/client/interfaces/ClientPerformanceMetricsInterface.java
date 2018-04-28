package teamZ.project4.controllers.client.interfaces;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import teamZ.project4.model.Emotion;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public interface ClientPerformanceMetricsInterface {
    /**
     * Handles a click for changing the color
     */
    void handleChangeColorClick(JButton button, Emotion emotion, HashMap<String, Color> availableColors,
                                HashMap<Emotion, Color> emotionColors, JFreeChart chart, ChartPanel panelChart);

    /**
     * Updates the graph line colors to correspond with the set emotion color
     */
    void updateGraphColors(JFreeChart chart, ChartPanel panelChart, HashMap<Emotion, Color> emotionColors);
}
