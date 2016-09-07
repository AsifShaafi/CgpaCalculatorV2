package tk.s3itexperts.cgpacalculator.helperClasses;

import android.app.Activity;
import android.content.Intent;

import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.mainActivities.MainActivity;

/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class ThemeChanger {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;


    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_WHITE:
                activity.setTheme(R.style.AppTheme_Light);
                break;
        }
    }

}
