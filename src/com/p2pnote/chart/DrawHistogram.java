package com.p2pnote.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;  
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;  
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;  
import org.achartengine.renderer.XYSeriesRenderer;  

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.ui.SplashScreenActivity;
  
import android.content.Context;  
import android.content.Intent;  
import android.database.Cursor;
import android.graphics.Color;  
import android.graphics.Paint.Align; 
import android.util.Log;
import android.view.View;

//画柱状图
public class DrawHistogram
{
		private String sqlString;
		private MyDbHelper db = SplashScreenActivity.db;
        private double  mExpenseValue[]={0,0,0,0,0,0,0,0,0,0,0,0}; 
        private double  interest[]={0,0,0,0,0,0,0,0,0,0,0,0};
        private double  highMax=0;
        private double  lowMin=0;
        
        private String  month[];
        
        private void DataPrepare(Context mContent){ //收入查询
            
        int i=0;  //每月消费统计
        Log.d("p2pnote",  "Histogram sql: "+sqlString);
        Cursor cursor = db.rawQuery(sqlString,null);
        
        month = new String[cursor.getCount()];  //字符串数组必须初始化
      
        while (cursor.moveToNext()) {
            if(cursor.getString(0) != null)
            { //
                Log.d("p2pnote",  "cursor get "+cursor.getCount());
                
                month[i]=cursor.getString(cursor.getColumnIndex("months"));
                mExpenseValue[Integer.parseInt(month[i])-1] = cursor.getDouble(cursor.getColumnIndex("amount"));
                
                Log.d("p2pnote",  month[i]+"月 投资:"+mExpenseValue[Integer.parseInt(month[i])-1]);
            }
            i++;
        }
        cursor.close();
        //db.close();
        
    }
    
        
    	public DrawHistogram(String sqlString, MyDbHelper db) {
		super();
		this.sqlString = sqlString;
		this.db = db;
    	}
       
       public  void getMaxAndMin(double[] arr)  
        {  
            int max = 0;  
            int min = 0; 
      
            for(int x=1; x<arr.length; x++)  
            {  
                if(arr[x]>arr[max])   max = x;  
                if(arr[x]<arr[min])   min = x; 
            }  
            if(arr[max]>highMax) highMax= arr[max];  
            if(arr[min]<lowMin)  lowMin =arr[min]; 
        }  
        
      public GraphicalView execute(Context context) {  

          String[] titles = new String[] { "投资", "利息" };
          List<double[]> x = new ArrayList<double[]>();
          for (int i = 0; i < titles.length; i++) {
              x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
          }
          
          DataPrepare(context);
          
          List<double[]> values = new ArrayList<double[]>();
          values.add(mExpenseValue);
          
          for (int i=0;i<mExpenseValue.length;i++){
              
        	  interest[i]=0;
              Log.d("p2pnote",  "利息"+interest[i]);
              
          }
          
          values.add(interest);
          
          getMaxAndMin(mExpenseValue);
          //getMaxAndMin(interest);
             
          int[] colors = new int[] { Color.BLUE, Color.RED };
          
          PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
          XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
          int length = renderer.getSeriesRendererCount();
          
          for (int i = 0; i < length; i++) {
              ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
          }
          setChartSettings(renderer, "全年投资图", "月份", "金额", 0, 12, lowMin, highMax, Color.WHITE, Color.WHITE);//LTGRAY
          renderer.setXLabels(12);
          renderer.setYLabels(12);
          renderer.setShowGrid(true);
          renderer.setXLabelsAlign(Align.RIGHT);
          renderer.setYLabelsAlign(Align.LEFT);
          renderer.setZoomButtonsVisible(true);
          
          renderer.setApplyBackgroundColor(true);
          renderer.setBackgroundColor(Color.argb(0, 220, 228, 234));//
          renderer.setMarginsColor(Color.argb(0, 220, 228, 234));//
          
          renderer.setPanLimits(new double[] { 0, 12, lowMin, highMax });
          renderer.setZoomLimits(new double[] { 0, 12, lowMin, highMax });
          
          return ChartFactory.getLineChartView(context, buildDataset(titles, x, values), renderer); 
      }  
      

      private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
          XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
          setRenderer(renderer, colors, styles);
          return renderer;
      }

      private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
          renderer.setAxisTitleTextSize(15);
          renderer.setChartTitleTextSize(16);
          renderer.setLabelsTextSize(14);
          renderer.setLegendTextSize(14);
          renderer.setPointSize(5f);
          renderer.setMargins(new int[] { 20, 30, 15, 20 });
          int length = colors.length;
          for (int i = 0; i < length; i++) {
              XYSeriesRenderer r = new XYSeriesRenderer();
              r.setColor(colors[i]);
              r.setPointStyle(styles[i]);
              renderer.addSeriesRenderer(r);
          }
      }

      private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
          renderer.setChartTitle(title);
          renderer.setXTitle(xTitle);
          renderer.setYTitle(yTitle);
          renderer.setXAxisMin(xMin);
          renderer.setXAxisMax(xMax);
          renderer.setYAxisMin(yMin);
          renderer.setYAxisMax(yMax);
          renderer.setAxesColor(axesColor);
          renderer.setLabelsColor(labelsColor);
      }

      private XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues, List<double[]> yValues) {
          XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
          addXYSeries(dataset, titles, xValues, yValues, 0);
          return dataset;
      }

      private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues, List<double[]> yValues, int scale) {
          int length = titles.length;
          for (int i = 0; i < length; i++) {
              XYSeries series = new XYSeries(titles[i], scale);
              double[] xV = xValues.get(i);
              double[] yV = yValues.get(i);
              int seriesLength = xV.length;
              for (int k = 0; k < seriesLength; k++) {
                  series.add(xV[k], yV[k]);
              }
              dataset.addSeries(series);
          }
      }
  }
