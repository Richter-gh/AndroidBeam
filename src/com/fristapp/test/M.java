package com.fristapp.test;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class M implements Parcelable {
	  
	  final static String LOG_TAG = "Q class";

	  
	  public double a,amount;

	  // обычный конструктор
	  public M(double a_, double amount_) {
	    Log.d(LOG_TAG, "MyObject(String _s, int _i)");
	    a = a_;
	    
	    amount=amount_;
	  }

	  public int describeContents() {
	    return 0;
	  }

	  // упаковываем объект в Parcel
	  public void writeToParcel(Parcel parcel, int flags) {
	    Log.d(LOG_TAG, "writeToParcel");
	    parcel.writeDouble(a);
	    
	    parcel.writeDouble(amount);	    
	  }

	  public static final Parcelable.Creator<M> CREATOR = new Parcelable.Creator<M>() {
	    // распаковываем объект из Parcel
	    public M createFromParcel(Parcel in) {
	      Log.d(LOG_TAG, "createFromParcel");
	      return new M(in);
	    }

	    public M[] newArray(int size) {
	      return new M[size];
	    }
	  };

	  // конструктор, считывающий данные из Parcel
	  private M(Parcel parcel) {
	    Log.d(LOG_TAG, "MyObject(Parcel parcel)");
	    a = parcel.readDouble();
	   
	    amount = parcel.readDouble();
	  }
	

	}