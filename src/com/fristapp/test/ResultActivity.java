
package com.fristapp.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
public class ResultActivity extends Activity {

	double qmax,mmax;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
		P p = intent.getParcelableExtra("p");
		Q q = intent.getParcelableExtra("q");
		M m = intent.getParcelableExtra("m");		
		
		double length = intent.getDoubleExtra("length",12);
		
		Log.d("length",Double.toString(length));	
		
		List<Point> qpoints_q = new ArrayList<Point>();
		List<Point> mpoints_q = new ArrayList<Point>();
		List<Point> qpoints_m = new ArrayList<Point>();
		List<Point> mpoints_m = new ArrayList<Point>();
		List<Point> qpoints_p = new ArrayList<Point>();
		List<Point> mpoints_p = new ArrayList<Point>();
		List<Point> qpoints = new ArrayList<Point>();
		List<Point> mpoints = new ArrayList<Point>();
		
		
		
		// а и б должны пересчитывать в менактивити, чтоюбы подходили в формулы, щас тут координаты
		qpoints.add(new Point(0,length));
		qpoints.add(new Point(0,-length));
		qpoints.add(new Point(0,0));
		qpoints.add(new Point(length,0));
		qpoints.add(new Point(0,0));//5
		mpoints.add(new Point(0,length));
		mpoints.add(new Point(0,-length));
		mpoints.add(new Point(0,0));
		mpoints.add(new Point(length,0));
		mpoints.add(new Point(0,0));
		
		Log.d("debug","1");
		Log.d("debug","2");
		//расчет точек графиков момента и силы от распределенной нагрузки
		//создать по 2 массива точек для каждого типа нагрузок и складывать в конце
		
			for(double j=0.0;j<=length;j+=0.01)
			{
				//[0,a-b/2]
				if(j <= q.a - q.b/2.0)
				{
					qpoints_q.add(new Point((double)Math.round(j*100)/100,q.amount*q.b*(1-q.a/length)));
					mpoints_q.add(new Point((double)Math.round(j*100)/100,q.amount*q.b*(1-q.a/length)*j));
					
				}
				//[a-b/2,a+b/2]
				if(j >= q.a - q.b/2.0 && j <= q.a + q.b/2.0)
				{
					qpoints_q.add(new Point((double)Math.round(j*100)/100,q.amount*(q.b*(1-q.a/length) - j + q.a - q.b/2.0)));
					mpoints_q.add(new Point((double)Math.round(j*100)/100,q.amount*q.b*(1-q.a/length)*j - (q.amount/2.0)*(j - (q.a - q.b/2.0))*(j - (q.a - q.b/2.0))));
					
				}
				//[a+b/2,l]
				if(j >= q.a + q.b/2.0 && j <= length)
				{
					qpoints_q.add(new Point((double)Math.round(j*100)/100,(-1)*(q.amount*q.b*q.a)/length));
					mpoints_q.add(new Point((double)Math.round(j*100)/100,q.amount*q.b*(1-q.a/length)*j - q.amount*q.b*(j-q.a)));
					
				}				
				
			}
		
		Log.d("debug","3");
		//расчет точек графиков момента и силы от момента
		
			for(double j=0.0;j<=length;j+=0.01)
			{
				
				//[0,a]
				if(j <= m.a)
				{	
					qpoints_m.add(new Point((double)Math.round(j*100)/100,m.amount/length));
					mpoints_m.add(new Point((double)Math.round(j*100)/100,(m.amount/length)*j));					
				}
				//[a,l]
				if(j >= m.a)
				{	
					qpoints_m.add(new Point((double)Math.round(j*100)/100,m.amount/length));
					mpoints_m.add(new Point((double)Math.round(j*100)/100,m.amount*(j/length - 1)));					
				}							
				
			}
		
		Log.d("debug","4");
		//расчет точек графиков момента и силы от сосредоточенной силы		
			
			for(double j=0.0;j<=length;j+=0.01)
			{				
				//[0,a]
				if(j <= p.a)
				{	
					qpoints_p.add(new Point((double)Math.round(j*100)/100,p.amount*(1-p.a/length)));
					mpoints_p.add(new Point((double)Math.round(j*100)/100,p.amount*(1-p.a/length)*j));					
				}
				//[a,l]
				if(j >= p.a)
				{	
					qpoints_p.add(new Point((double)Math.round(j*100)/100,(-1)*(p.amount*p.a)/length));
					mpoints_p.add(new Point((double)Math.round(j*100)/100,p.amount*(1-p.a/length)*j - p.amount*(j - p.a)));					
				}										
			}
		
		Log.d("debug","5");
		Log.d("qpoints_p length",Integer.toString(qpoints_p.size()));
		Log.d("qpoints_m length",Integer.toString(qpoints_m.size()));
		Log.d("qpoints_q length",Integer.toString(qpoints_q.size()));
		Log.d("mpoints_p length",Integer.toString(mpoints_p.size()));
		Log.d("mpoints_m length",Integer.toString(mpoints_m.size()));
		Log.d("mpoints_q length",Integer.toString(mpoints_q.size()));
		
		int indexer=0;
		for(double j=0.0;j<=length;j+=0.01)
		{
			qpoints.add(new Point((double)Math.round(j*100)/100,qpoints_q.get(indexer).y+qpoints_m.get(indexer).y+qpoints_p.get(indexer).y));
			mpoints.add(new Point((double)Math.round(j*100)/100,mpoints_q.get(indexer).y+mpoints_m.get(indexer).y+mpoints_p.get(indexer).y));
			indexer++;
		}
		
		Log.d("qpoints length",Integer.toString(qpoints.size()));
		Log.d("mpoints length",Integer.toString(mpoints.size()));
		Log.d("pointlist","");
		qmax=qpoints.get(5).y;
		double qmin=qmax;
		double x_q=0,x_m = 0,x_q_min=0,x_m_min=0;
		for (int i=5;i<qpoints.size();i++)
		{
			if(qmax<qpoints.get(i).y)
			{
				qmax=qpoints.get(i).y;
				x_q=qpoints.get(i).x;
			}
			if(qmin>qpoints.get(i).y)
			{
				qmin=qpoints.get(i).y;
				x_q_min=qpoints.get(i).x;
			}
			
		}
		mmax=mpoints.get(5).y;
		double mmin=mmax;
		for (int i=5;i<mpoints.size();i++)
		{
			if(mmax<mpoints.get(i).y)
			{
				x_m=qpoints.get(i).x;
				mmax=mpoints.get(i).y;
			}
			if(mmax>mpoints.get(i).y)
			{
				x_m_min=qpoints.get(i).x;
				mmin=mpoints.get(i).y;
			}
			
		}
		
		
		GraphViewData[] qpoints_g=new GraphViewData[qpoints.size()];		
		int index=0;
		for(int i=0;i<qpoints.size();i++)
		{
			qpoints_g[index]=new GraphViewData(qpoints.get(i).x,qpoints.get(i).y);
			index++;
		}
		index=0;


		GraphViewData[] mpoints_g=new GraphViewData[mpoints.size()];
		for(int i=0;i<mpoints.size();i++)
		{
			mpoints_g[index]=new GraphViewData(mpoints.get(i).x,mpoints.get(i).y);
			index++;
		}
		
		
		
		LinearLayout layout;
		
	
		LineGraphView qplot = new LineGraphView(this, "q");
		qplot.addSeries(new GraphViewSeries(qpoints_g));
		
		layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(qplot);
		layout.setBackgroundColor(Color.BLACK);
		LineGraphView mplot = new LineGraphView(this, "m");
		mplot.addSeries(new GraphViewSeries(mpoints_g));
		
		layout = (LinearLayout) findViewById(R.id.graph2);
		layout.addView(mplot);
		layout.setBackgroundColor(Color.BLACK);
		TextView max = (TextView) findViewById(R.id.textView1);
		max.setText("Max M("+x_m+") = "+mmax+"\n Max Q("+x_q+") = "+qmax);//\nMin M("+x_m_min+") = "+mmin+"\n Min Q("+x_q_min+") = "+qmin+"\n");
		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return super.onCreateOptionsMenu(menu);
      }
      
      // обновление меню
      @Override
      public boolean onPrepareOptionsMenu(Menu menu) {
        
        return super.onPrepareOptionsMenu(menu);
      }

      // обработка нажатий
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        
    	  switch (item.getItemId()) {    	    
    	    case R.id.exit_result:
    	      //exit
    	    	finish();
    	      break;
    	    }
        
        
        return super.onOptionsItemSelected(item);
      }
    

}
