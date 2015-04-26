/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Unlink
 */
public final class XYLineChart {

	private final XYSeries aSeries;
	private final JFreeChart aChart;
	
	public XYLineChart(String paTitle) {
		aSeries = new XYSeries("Series", false);
        aChart = ChartFactory.createXYLineChart(
            paTitle,
            null, 
            null, 
            new XYSeriesCollection(aSeries), 
			PlotOrientation.VERTICAL, 
			false, 
			true, 
			false
        );
		
		setPlotBackground(new Color(236, 240, 241));
		setSeriesColor(new Color(41, 128, 185));
		
		//Autoscale
		NumberAxis xAxis = new NumberAxis();
		xAxis.setAutoRangeIncludesZero(false);
		xAxis.setAutoRange(true);
		xAxis.setLowerMargin(0);
		xAxis.setUpperMargin(0.01);
		xAxis.setNumberFormatOverride(new DecimalFormat("0.0")); 
		aChart.getXYPlot().setDomainAxis(xAxis);
		
		aChart.getXYPlot().getRangeAxis().setAutoRange(true);
		((NumberAxis) aChart.getXYPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("0.0000000"));
		((NumberAxis) aChart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(false);

		if (paTitle != null)
			aChart.setTitle(new TextTitle(paTitle, new Font("SansSerif", Font.BOLD, 12)));
	}
	
	public void setPlotBackground(Color paColor) {
		aChart.getPlot().setBackgroundPaint(paColor);
	}
	
	public void setSeriesColor(Color paColor) {
		aChart.getXYPlot().getRenderer().setSeriesPaint(0, paColor);
	}
	
	public void addPoint(double x, double y) {
		aSeries.add(x, y);
	}
	
	public void clear() {
		aSeries.clear();
	}

	public XYSeries getSeries() {
		return aSeries;
	}

	public JFreeChart getChart() {
		return aChart;
	}

}
