package com.ming.weightlossapp.Domain.PhysicalInformation;

import android.util.Log;

public class physicalInformationController {
    public static double getBMI(double weight,double height){
        double bmi=0;
        bmi=weight/(height*height);
        Log.i("BMI",String.valueOf(bmi));
        return bmi;
    }
}
