package com.fristapp.test;

import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener{
	LineGraphView balka_plot;
	GraphViewData[] points_p;
	GraphViewData[] points_gv;
	GraphViewData[] points_q;
	GraphViewData[] points_m;
	TextView lengthView;
	TextView momentView,mStart;
	TextView qView,qStart,qEnd;
	TextView pView,pStart;
	P p = new P(0,0);
	Q q = new Q(0,0,0);
	M m = new M(0,0);
	double totalmoment,totalq,totalp;
	double length;
	ImageView imaga;
	AlertDialog.Builder dialog;
	Dialog myDialog;
	EditText aEditText,bEditText,amountEditText,lengthEditText;
	Button temp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        points_p=new GraphViewData[1];    	  
  	  	points_p[0]=new GraphViewData(0,0);
  	  	points_m=new GraphViewData[1];    	  
	  	points_m[0]=new GraphViewData(0,0);
	  	points_q=new GraphViewData[1];    	  
  	  	points_q[0]=new GraphViewData(0,0);
  	  	
		
        DrawBalka();
        
        totalmoment=0;totalp=0;totalq=0;
        length=0;
        lengthView = (TextView) findViewById(R.id.lengthView);
        momentView = (TextView) findViewById(R.id.momentView);
        mStart = (TextView) findViewById(R.id.mStart);
        qView = (TextView) findViewById(R.id.qView);
        qStart = (TextView) findViewById(R.id.qStart);
        qEnd = (TextView) findViewById(R.id.qEnd);
        pView = (TextView) findViewById(R.id.pView);
        pStart = (TextView) findViewById(R.id.pStart);
       
        
        
        findViewById(R.id.addQ).setOnClickListener((OnClickListener) this);
        findViewById(R.id.addMoment).setOnClickListener((OnClickListener) this);
        findViewById(R.id.addP).setOnClickListener((OnClickListener) this);
        findViewById(R.id.calc).setOnClickListener((OnClickListener) this);
        
        totalmoment=0;totalp=0;totalq=0;
        length=0;
         
    	myDialog = new Dialog(MainActivity.this);
        myDialog.setContentView(R.layout.length_dialog);
        myDialog.setTitle("Введите длину");
        aEditText=(EditText)myDialog.findViewById(R.id.length_a);
                  
        temp=(Button)myDialog.findViewById(R.id.length_ok);
        temp.setOnClickListener(new OnClickListener() {
             @Override
              public void onClick(View v) {
                    
                    length = Double.parseDouble(aEditText.getText().toString());
                    lengthView.setText("Length = "+length);
                    qView.setText("Q = "+totalq);  
                    momentView.setText("M = "+totalmoment); 
                    pView.setText("P = "+totalp);  
                    myDialog.cancel();
                    //DrawEmptyBalka(); 
              }
             
        });
        myDialog.setOnDismissListener(new OnDismissListener(){
        	@Override
        	public void onDismiss(DialogInterface dialog)
        	{
        		drawM(m.a);
            	drawP(p.a);
            	drawQ(q.a,q.b);
        	}
        });
        	
        myDialog.show();      
        
      }

    public void onClick(View v) {
    	
    	Intent intent = new Intent(this, com.fristapp.test.ResultActivity.class);
    	
    	
        switch (v.getId()) {
        
       
        case R.id.addMoment:
        	myDialog = new Dialog(MainActivity.this);
            myDialog.setContentView(R.layout.m_dialog);
            myDialog.setTitle("Момент");
            aEditText=(EditText)myDialog.findViewById(R.id.m_a);    
            amountEditText=(EditText)myDialog.findViewById(R.id.m_amount);             
            temp=(Button)myDialog.findViewById(R.id.m_ok);
            temp.setOnClickListener(new OnClickListener() {
                 @Override
                  public void onClick(View v) {
                        m.a=Double.parseDouble(aEditText.getText().toString());
                        m.amount=Double.parseDouble(amountEditText.getText().toString());
                        totalmoment=Double.parseDouble(amountEditText.getText().toString());
                        momentView.setText("M = "+totalmoment); 
                        mStart.setText("a: "+m.a); 
                        myDialog.cancel();
                  }
            });
            myDialog.setOnDismissListener(new OnDismissListener(){
            	@Override
            	public void onDismiss(DialogInterface dialog)
            	{
            		drawM(m.a);
                	drawP(p.a);
                	drawQ(q.a,q.b);
            	}
            });
            myDialog.show();
        	Toast.makeText(this, "AddMoment", Toast.LENGTH_SHORT).show();        	        	        	
        	break;        
        case R.id.addQ:
        	Toast.makeText(this, "AddQ", Toast.LENGTH_SHORT).show();        	
        	myDialog = new Dialog(MainActivity.this);
            myDialog.setContentView(R.layout.q_dialog);
            myDialog.setTitle("Распределенная нагрузка");
            aEditText=(EditText)myDialog.findViewById(R.id.q_a);
            bEditText=(EditText)myDialog.findViewById(R.id.q_b);
            amountEditText=(EditText)myDialog.findViewById(R.id.q_amount);             
            temp=(Button)myDialog.findViewById(R.id.q_ok);
            temp.setOnClickListener(new OnClickListener() {
                 @Override
                  public void onClick(View v) {
                	 double a=Double.parseDouble(aEditText.getText().toString());
                	 double b=Double.parseDouble(bEditText.getText().toString());
                      q.a=a;q.b=b;
                       q.amount=Double.parseDouble(amountEditText.getText().toString());
                       totalq=Double.parseDouble(amountEditText.getText().toString());
                       qView.setText("Q = "+totalq); 
                       qStart.setText("a = "+q.a);
                       qEnd.setText("b = "+q.b);
                       
                       myDialog.cancel();
                  }
            });
            myDialog.setOnDismissListener(new OnDismissListener(){
            	@Override
            	public void onDismiss(DialogInterface dialog)
            	{
            		drawM(m.a);
                	drawP(p.a);
                	drawQ(q.a,q.b);
            	}
            });
            myDialog.show();      	
        	break;
        case R.id.addP:
        	Toast.makeText(this, "AddQ", Toast.LENGTH_SHORT).show();        	
        	myDialog = new Dialog(MainActivity.this);
            myDialog.setContentView(R.layout.p_dialog);
            myDialog.setTitle("Сосредоточенная сила");
            aEditText=(EditText)myDialog.findViewById(R.id.p_a);    
            amountEditText=(EditText)myDialog.findViewById(R.id.p_amount);             
            temp=(Button)myDialog.findViewById(R.id.p_ok);
            temp.setOnClickListener(new OnClickListener() {
                 @Override
                  public void onClick(View v) {
                        p.a=Double.parseDouble(aEditText.getText().toString());
                        p.amount=Double.parseDouble(amountEditText.getText().toString());
                        totalp=Double.parseDouble(amountEditText.getText().toString());
                        pView.setText("P = "+totalp);
                        pStart.setText("a: "+p.a);
                        myDialog.cancel();
                  }
            });
            myDialog.setOnDismissListener(new OnDismissListener(){
            	@Override
            	public void onDismiss(DialogInterface dialog)
            	{
            		drawM(m.a);
                	drawP(p.a);
                	drawQ(q.a,q.b);
            	}
            });
            myDialog.show();    	
        	break;
        case R.id.calc:
        	Toast.makeText(this, "Calc", Toast.LENGTH_SHORT).show();   
        	intent.putExtra("length", length);
        	double temp=q.a;
        	 q.a=(q.b+temp)/2;
             q.b=(q.b-temp);
        	intent.putExtra("p", p);
        	intent.putExtra("q", q);
        	intent.putExtra("m", m);
            startActivity(intent);        	
        	break;
       
        }
        
        
      }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
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
    	    case R.id.menu_new:
    	    	totalmoment=0;totalp=0;totalq=0;
    	        length=0;
    	        
    	    	myDialog = new Dialog(MainActivity.this);
                myDialog.setContentView(R.layout.length_dialog);
                myDialog.setTitle("Введите длину");
                aEditText=(EditText)myDialog.findViewById(R.id.length_a);
                p = new P(0,0);
            	q = new Q(0,0,0);
            	m = new M(0,0);          
                temp=(Button)myDialog.findViewById(R.id.length_ok);
                temp.setOnClickListener(new OnClickListener() {
                     @Override
                      public void onClick(View v) {
                            
                            length = Double.parseDouble(aEditText.getText().toString());
                            lengthView.setText("Length = "+length);
                            qView.setText("Q = "+totalq);  
                            momentView.setText("M = "+totalmoment); 
                            pView.setText("P = "+totalp);  
                            myDialog.cancel();
                             
                      }
                });
                myDialog.setOnDismissListener(new OnDismissListener(){
                	@Override
                	public void onDismiss(DialogInterface dialog)
                	{
                		drawM(m.a);
                    	drawP(p.a);
                    	drawQ(q.a,q.b);
                	}
                });
                myDialog.show();
                 
    	      break;
    	    case R.id.menu_exit:
    	      //exit
    	    	finish();
    	      break;
    	    }
        
        
        return super.onOptionsItemSelected(item);
      }
      public void drawM(double start)//ok
      {
    	  Log.d("drawM","drawM");
    	  if(m.amount==0)
    		  return;
    	  int heigth=5;
    	  double coeff=10.0/length;
    	  double length1=coeff*start+0.5;
    	  List<Point> points = new ArrayList<Point>();
    	  if(m.amount>0)
    		  points.add(new Point(length1-0.5,heigth));
    	  else
    		  points.add(new Point(length1+0.5,heigth));
    	  points.add(new Point(length1,heigth));
    	  points.add(new Point(length1,0));
    	  points.add(new Point(length1,-heigth));
    	  if(m.amount>0)
    		  points.add(new Point(length1+0.5,-heigth));
    	  else
    		  points.add(new Point(length1-0.5,-heigth));         	        	
    	  points_m=new GraphViewData[points.size()];    	  
    	  int index=0;
    	  for(int i=0;i<points.size();i++)
    	  {
    		  points_m[index]=new GraphViewData(points.get(i).x,points.get(i).y);
    		  Log.d("oiints",Double.toString(points.get(i).x)+" "+Double.toString(points.get(i).y));
    		  index++;
    	  }
    	  LinearLayout layout;   	      	  
    	  layout = (LinearLayout) findViewById(R.id.graph_main);    	  
    	  layout.removeAllViews();    		
    	  balka_plot = new LineGraphView(this, "balka");
    	  balka_plot.addSeries(new GraphViewSeries(points_m));
    	  balka_plot.addSeries(new GraphViewSeries(points_q));
    	  balka_plot.addSeries(new GraphViewSeries(points_p));
    	  balka_plot.addSeries(new GraphViewSeries(points_gv));   	      		
    	  layout.addView(balka_plot);
    	  layout.setBackgroundColor(Color.BLACK);
      }
      public void drawP(double start)
      {
    	  Log.d("drawP","drawP");
    	  if(p.amount==0)
    		  return;
    	  double coeff=10.0/length;
    	  double length1=coeff*start+0.5;
    	  int heigth=6;
    	  List<Point> points = new ArrayList<Point>();
    	  if(p.amount>0)
    	  {
    		  points.add(new Point(length1,heigth+0.5));
        	  points.add(new Point(length1-0.5,heigth));
        	  points.add(new Point(length1+0.5,heigth));
        	  points.add(new Point(length1,heigth+0.5));
    	  }    		 
    	  else
    		  points.add(new Point(length1,heigth));
    	  points.add(new Point(length1,heigth));
    	  points.add(new Point(length1,0));
    	  
    	  if(p.amount>0)
    	  {
    		  points.add(new Point(length1,-heigth));        	  
    	  }    		 
    	  else
    	  {
    		  points.add(new Point(length1,-heigth-0.5));
        	  points.add(new Point(length1-0.5,-heigth));
        	  points.add(new Point(length1+0.5,-heigth));
        	  points.add(new Point(length1,-heigth-0.5));    		  
    	  }
    		          	
    	  points_p=new GraphViewData[points.size()];    	  
    	  int index=0;
    	  for(int i=0;i<points.size();i++)
    	  {
    		  points_p[index]=new GraphViewData(points.get(i).x,points.get(i).y);
    		  Log.d("oiints",Double.toString(points.get(i).x)+" "+Double.toString(points.get(i).y));
    		  index++;
    	  }
    	  LinearLayout layout;   	      	  
    	  layout = (LinearLayout) findViewById(R.id.graph_main);    	  
    	  layout.removeAllViews();    		
    	  balka_plot = new LineGraphView(this, "balka");
    	  balka_plot.addSeries(new GraphViewSeries(points_m));
    	  balka_plot.addSeries(new GraphViewSeries(points_q));
    	  balka_plot.addSeries(new GraphViewSeries(points_p));
    	  balka_plot.addSeries(new GraphViewSeries(points_gv));   	      		
    	  layout.addView(balka_plot);
    	  layout.setBackgroundColor(Color.BLACK);
      	
      }
      public void drawQ(double start, double finish)
      {
    	  Log.d("drawQ","drawQ");
    	  if(q.amount==0)
    		  return;
    	  double coeff=10.0/length;
    	  double length1=coeff*start+0.5;
    	  double length2=coeff*finish+0.5;
    	  int heigth=4;
    	  List<Point> points = new ArrayList<Point>();
    	  if(q.amount>0)
    	  {
    		  points.add(new Point(length1,0));
    		  points.add(new Point(length1,heigth));
        	  points.add(new Point(length2,heigth));
        	  points.add(new Point(length2,0));
    	  }    		  
    	  else
    	  {  
    		  points.add(new Point(length1,0));
    		  points.add(new Point(length1,-heigth));
        	  points.add(new Point(length2,-heigth));
        	  points.add(new Point(length2,0));
    	  }

    	  points_q=new GraphViewData[points.size()];

   	      int index=0;
   	      for(int i=0;i<points.size();i++)
   	      {
   	    	points_q[index]=new GraphViewData(points.get(i).x,points.get(i).y);
   	    	  Log.d("oiints",Double.toString(points.get(i).x)+" "+Double.toString(points.get(i).y));
   	    	  index++;
   	    	  }
   	      LinearLayout layout;  		
   	      layout = (LinearLayout) findViewById(R.id.graph_main);   	      
   	      layout.removeAllViews();  
   	      balka_plot = new LineGraphView(this, "balka");
   	      balka_plot.addSeries(new GraphViewSeries(points_m));
   	      balka_plot.addSeries(new GraphViewSeries(points_q));
   	      balka_plot.addSeries(new GraphViewSeries(points_p));
   	      balka_plot.addSeries(new GraphViewSeries(points_gv)); 		  		
   	      layout.addView(balka_plot);
   	      layout.setBackgroundColor(Color.BLACK);
    	  
      }
      public void DrawBalka()
      {
    	  Log.d("drawbalka","drawbalka");
    	  int length1=10;
    	  List<Point> points = new ArrayList<Point>();
    	  points.add(new Point(0,length1));
    	  points.add(new Point(0,-length1));
    	  points.add(new Point(0,-1));
    	  points.add(new Point(1,-1));
    	  points.add(new Point(0.5,0));
    	  points.add(new Point(0,-1));
    	  points.add(new Point(0.5,0));    	  
    	  points.add(new Point(length1+0.5,0));    	  
    	  points.add(new Point(length1,-1));
    	  points.add(new Point(length1+1,-1));    	  
    	  points.add(new Point(length1+0.5,0));
    	  points.add(new Point(length1+1,-1));
    	        	
    	  points_gv=new GraphViewData[points.size()];    	  
    	  int index=0;
    	  for(int i=0;i<points.size();i++)
    	  {
    		  points_gv[index]=new GraphViewData(points.get(i).x,points.get(i).y);
    		  Log.d("oiints",Double.toString(points.get(i).x)+" "+Double.toString(points.get(i).y));
    		  index++;
    		  }
    	  LinearLayout layout;    	  
    	  layout = (LinearLayout) findViewById(R.id.graph_main);  		
    	  layout.removeAllViews();
    	  balka_plot = new LineGraphView(this, "balka");
    	  balka_plot.addSeries(new GraphViewSeries(points_gv));  		
    	  layout = (LinearLayout) findViewById(R.id.graph_main);
    	  layout.addView(balka_plot);
    	  layout.setBackgroundColor(Color.BLACK);
      }

      
}
