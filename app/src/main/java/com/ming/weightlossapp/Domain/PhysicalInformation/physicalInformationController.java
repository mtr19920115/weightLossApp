package com.ming.weightlossapp.Domain.PhysicalInformation;

import android.util.Log;

import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;

public class physicalInformationController {
    public static double getBMI(double weight,double height){
        double bmi=0;
        bmi=weight/(height*height);
        Log.i("BMI",String.valueOf(bmi));
        return bmi;
    }

    public static User updateWeight(double weight,double BMI,int uid){

        User user=new User();
        user.setBMI(BMI);
        user.setWeight(weight);
        user.setUid(uid);
        UserDAO dao=new UserDAO();
        int ok=dao.updateUserWeight(user);
        if(ok!=0){
            return user;
        }else{
            return null;
        }

    }
}
