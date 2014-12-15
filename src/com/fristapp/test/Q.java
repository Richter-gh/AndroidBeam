package com.fristapp.test;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Q implements Parcelable {
	  
	  final static String LOG_TAG = "Q class";

	
	  public double a,b,amount;

	  // обычный конструктор
	  public Q(double a_, double b_, double amount_) {
	    Log.d(LOG_TAG, "MyObject(String _s, int _i)");
	    a = a_;
	    b = b_;
	    amount=amount_;
	  }

	  public int describeContents() {
	    return 0;
	  }

	  // упаковываем объект в Parcel
	  public void writeToParcel(Parcel parcel, int flags) {
	    Log.d(LOG_TAG, "writeToParcel");
	    parcel.writeDouble(a);
	    parcel.writeDouble(b);
	    parcel.writeDouble(amount);	    
	  }

	  public static final Parcelable.Creator<Q> CREATOR = new Parcelable.Creator<Q>() {
	    // распаковываем объект из Parcel
	    public Q createFromParcel(Parcel in) {
	      Log.d(LOG_TAG, "createFromParcel");
	      return new Q(in);
	    }

	    public Q[] newArray(int size) {
	      return new Q[size];
	    }
	  };

	  // конструктор, считывающий данные из Parcel
	  private Q(Parcel parcel) {
	    Log.d(LOG_TAG, "MyObject(Parcel parcel)");
	    a = parcel.readDouble();
	    b = parcel.readDouble();
	    amount = parcel.readDouble();
	  }
	

	}