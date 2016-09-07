package tk.s3itexperts.cgpacalculator.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.StaticDialogsAndMethods;
import tk.s3itexperts.cgpacalculator.helperClasses.ThemeChanger;


/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class MainActivity extends AppCompatActivity {

    /*
        binding all the views of main activity using the butter knife
     */
    @BindView(R.id.selectedYearShowingTextView)
    TextView mSelectedYear;
    @BindView(R.id.semesterSpinner)
    Spinner mSemesterSelectionSpinner;

    //static variables to store the year and semester of a student
    public static int STD_YEAR = 2, STD_SEMESTER = 1;

    /*
        Lists of the semesters
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            setting the theme which was last selected or default
         */
        ThemeChanger.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
            setting the semester options for spinner
         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_theme) {
            StaticDialogsAndMethods.changeTheme(this, MainActivity.this);
            return true;
        }
        else if (id == R.id.action_viewSavedFiles)
        {
            startActivity(new Intent(MainActivity.this, ManageSaveFiles.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        opens the tab activity after getting the year and semester of a student
     */
    public void openTabActivity(View view) {
        startActivity(new Intent(this, TabActivity.class));

        /*
            defining custom transition when the activity changes
         */
        overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left
        );
    }

    public void getYearOfStudent(View view) {
        StaticDialogsAndMethods.showYearOptions(MainActivity.this, mSelectedYear);
    }
}
