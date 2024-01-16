package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FirebaseUtility {

    private static String phonenumber;
    public static String getCurrentUserId()
    {
        return FirebaseAuth.getInstance().getUid();
    }
    public static boolean isUserLoggedIn()
    {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public static String getCurrentDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a format for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time using the defined format
        return currentDateTime.format(formatter);
    }
    public static String getCurrentUserPhonenumber(){
        return phonenumber;
    }
    public static void setCurrentUserPhonenumber(String number)
    {
        number.replace(" ","");
        phonenumber=number;
    }

    public static boolean checkUserSignedIn() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            return true;
        }
        return false;
    }
}
