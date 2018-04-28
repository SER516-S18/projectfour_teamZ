package teamZ.project4.controllers.client;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import teamZ.project4.controllers.client.interfaces.ClientPerformanceMetricsInterface;
import teamZ.project4.model.Emotion;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ClientPerformanceMetricsController implements ClientPerformanceMetricsInterface {

    private Component parentComponent;

    public ClientPerformanceMetricsController(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void handleChangeColorClick(JButton button, Emotion emotion, HashMap<String, Color> availableColors,
                                       HashMap<Emotion, Color> emotionColors, JFreeChart chart, ChartPanel panelChart) {
        ArrayList<String> colors = new ArrayList<>();
        Set<Map.Entry<String, Color>> set = availableColors.entrySet();

        colors.add("Invisible");
        String current = "Invisible";
        Iterator<Map.Entry<String,Color>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Color> entry = it.next();
            if(entry.getValue().equals(emotionColors.get(emotion)))
                current = entry.getKey();

            colors.add(entry.getKey());
        }

        String input = (String) JOptionPane.showInputDialog(
                parentComponent,
                "Choose color",
                emotion.NAME + " color", JOptionPane.QUESTION_MESSAGE,
                null,
                colors.toArray(new String[colors.size()]),
                current
        );

        if(input == null || input.length() == 0)
            return;

        if(input.equals("Invisible")) {
            emotionColors.put(emotion, null);
            button.setVisible(false);
        } else {
            emotionColors.put(emotion, availableColors.get(input));
            button.setForeground(emotionColors.get(emotion));
            button.setVisible(true);
        }

        updateGraphColors(chart, panelChart, emotionColors);
    }

    @Override
    public void updateGraphColors(JFreeChart chart, ChartPanel panelChart, HashMap<Emotion, Color> emotionColors) {
        for(int i = 0; i < Emotion.values().length; i++) {
            Emotion emotion = Emotion.values()[i];
            Color color = emotionColors.get(emotion);
            if(color == null)
                chart.getXYPlot().getRenderer().setSeriesVisible(i, false);
            else {
                chart.getXYPlot().getRenderer().setSeriesPaint(i, color);
                chart.getXYPlot().getRenderer().setSeriesVisible(i, true);
            }
        }
        panelChart.repaint();
    }
}
