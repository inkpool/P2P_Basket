/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.p2pnote.chart;

import org.achartengine.ChartFactory;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class PieChart extends AbstractDemoChart {
	//饼图最多8个
	double[] values;
	String[] titles;
	int[] colors;
	final int[] COLORS= new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
			Color.YELLOW,Color.RED,Color.DKGRAY,Color.CYAN,Color.LTGRAY };
	String reportTitle="report";
	
	public PieChart() {
		values[0] = 1;
		values[1] = 2;
		values[2] = 3;
	}

	public PieChart(double value[],String title[],String report_title) {
		values=value;
		titles=title;
		
		colors=new int[values.length];

		for (int i = 0; i < colors.length; i++) {
			colors[i] = COLORS[i];
		}
		
		if (report_title!=null && !"".equals(report_title) && report_title.length()>0)
			reportTitle=report_title;
		
	}

	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Report chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return reportTitle;
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {

		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		renderer.setChartTitleTextSize(20);
		renderer.setDisplayValues(true);
		renderer.setShowLabels(false);
		SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		r.setGradientEnabled(true);
		r.setGradientStart(0, Color.BLUE);
		r.setGradientStop(0, Color.GREEN);
		r.setHighlighted(true);
		Intent intent = ChartFactory.getPieChartIntent(context,
				buildCategoryDataset(reportTitle,titles, values), renderer,
				reportTitle);
		return intent;
	}

}
