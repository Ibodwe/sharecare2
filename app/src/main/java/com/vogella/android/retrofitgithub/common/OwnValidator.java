package com.vogella.android.retrofitgithub.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;

import java.util.regex.Pattern;

public class OwnValidator {

    public boolean validEmail(Activity activity, EditText email) {
        String inputEmailText = email.getText().toString();

        isNotOnlineOrEmpty(activity, inputEmailText);

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");
        boolean isValid = pattern.matcher(inputEmailText).matches();
        if(!isValid) {
            Toast.makeText(activity, activity.getString(R.string.emailNotMatchingPattern), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validPassword(Activity activity, EditText password) {
        String inputPassword = password.getText().toString();

        if(isNotOnlineOrEmpty(activity, inputPassword)) return false;

        if(isTooShort(activity, inputPassword)) return false;

        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$");
        boolean isValid = pattern.matcher(inputPassword).matches();
        if(!isValid) {
            Toast.makeText(activity, activity.getString(R.string.passwortNotMatchingPattern), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isTooShort(Activity activity, String inputPassword) {
        if(inputPassword.length() < 8){
            Toast.makeText(activity, activity.getString(R.string.passwordShort), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    //remember to add your own toast message!!!
    public boolean validateWhetherTheyAreEqual(Activity activity, String stringOne, String stringTwo) {
        isNotOnlineOrEmpty(activity, stringOne);
        isNotOnlineOrEmpty(activity, stringTwo);
        return stringOne.equals(stringTwo);
    }

    public boolean isNotOnlineOrEmpty(Activity activity, String inputString) {
        if (!isOnline(activity)) {
            Toast.makeText(activity, activity.getString(R.string.noInternet), Toast.LENGTH_LONG).show();
            return true;
        }

        if (TextUtils.isEmpty(inputString)) {
            Toast.makeText(activity, activity.getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public boolean isFieldNotEmpty(Activity activity, EditText inputField){
        String inputString = inputField.getText().toString();
        if (TextUtils.isEmpty(inputString)) {
            Toast.makeText(activity, activity.getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
