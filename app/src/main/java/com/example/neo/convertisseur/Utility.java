package com.example.neo.convertisseur;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by Neo on 27/06/2017.
 */

class Utility {
    public static void reinitValues(TextView... args) {
        int i;
        for(i=0; i<args.length; i++)
            args[i].setText("");
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
