package teamZ.project4.ui.client;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import teamZ.project4.constants.ColorConstants;
import teamZ.project4.listeners.ClientListener;
import teamZ.project4.model.Expression;
import teamZ.project4.model.client.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ClientExpressionCountGraphView extends JPanel {

    private JFreeChart expressionCountGraph;
    private DefaultCategoryDataset dataset;

    public ClientExpressionCountGraphView() {

        JPanel panelGraph = new JPanel(new BorderLayout());
        panelGraph.setBackground(ColorConstants.BACKGROUND_PINK);
        panelGraph.setBorder(BorderFactory.createLineBorder(Color.black));

        ClientModel.get().addListener(new ClientListener() {
                                          @Override
                                          public void valuesChanged() {
                                              updateGraph();
                                          }

                                          @Override
                                          public void valuesReset() {

                                          }

                                          @Override
                                          public void valuesAdded() {

                                          }

                                          @Override
                                          public void started() {

                                          }

                                          @Override
                                          public void shutdown() {

                                          }
                                      });

        expressionCountGraph = ChartFactory.createBarChart("Expression Count", "Expression", "Count", createDataset(), PlotOrientation.VERTICAL,
                    true, true, false);
        expressionCountGraph.setBackgroundPaint(Color.WHITE);
        expressionCountGraph.getPlot().setOutlineVisible(false);
        expressionCountGraph.getPlot().setBackgroundAlpha(0);
        ChartPanel graphPanel = new ChartPanel(expressionCountGraph);
        graphPanel.setDomainZoomable(false);
        graphPanel.setRangeZoomable(false);
//        graphPanel.setPreferredSize(new java.awt.Dimension( 1000 , 500 ) );
        panelGraph.add(graphPanel, BorderLayout.CENTER);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(panelGraph);
    }

    private void updateGraph(){
        HashMap<Expression, Integer> expressionPacketsCount = ClientModel.get().getExpressionPacketsCount();
        for(Expression expression : expressionPacketsCount.keySet()){
            dataset.addValue(expressionPacketsCount.get(expression),"Count",expression.NAME);
        }
    }

    private CategoryDataset createDataset() {
        dataset = new DefaultCategoryDataset();
        updateGraph();
        return dataset;
    }
}
