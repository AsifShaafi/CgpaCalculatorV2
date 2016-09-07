package tk.s3itexperts.cgpacalculator.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.Data.CourseData;
import tk.s3itexperts.cgpacalculator.Data.CourseStructure;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.StaticDialogsAndMethods;
import tk.s3itexperts.cgpacalculator.helperClasses.ThemeChanger;
import tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities.LabResultShowingFragment;
import tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities.MarksTakingFragment;
import tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities.TheoryResultShowingFragment;

/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class TabActivity extends AppCompatActivity {

    public static final String LAB_RESULT_BASE_URL = "http://www.aust.edu/result/cse_s_";
    public static final String THEORY_RESULT_BASE_URL = "http://www.aust.edu/result/cse_t_";
    public static final String RESULT = "result";
    public static final String CALLING_FROM = "calling_from"; //this constant is for telling the result showing activity from which activity it was called

    private List<CourseStructure> courseStructures = CourseData.getCourseList();
    public static CourseStructure mCourseStructure;
    MarksTakingFragment mMarksTakingFragment = new MarksTakingFragment();

    //binding the views with butter-knife
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            setting the theme which was last selected or default
         */
        ThemeChanger.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_tab_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.i("student", "onCreate: " + MainActivity.STD_YEAR + " " + MainActivity.STD_SEMESTER);

        for (CourseStructure structure: courseStructures)
        {
            if (structure.getYear() == MainActivity.STD_YEAR
                    && structure.getSemester() == MainActivity.STD_SEMESTER)
            {
                mCourseStructure = structure;
                mMarksTakingFragment.setTitle(mCourseStructure.getCourseTitles());
            }
        }

        setViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager); //setting up the viewpager title in the tab-layout
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
            StaticDialogsAndMethods.changeTheme(this, TabActivity.this);
            return true;
        } else //noinspection SimplifiableIfStatement
            if (id == android.R.id.home) {
                finish();
            } else if (id == R.id.action_viewSavedFiles) {
                startActivity(new Intent(TabActivity.this, ManageSaveFiles.class));
            }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

         /*
            defining custom transition when the activity changes
         */
        overridePendingTransition(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
        );
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mMarksTakingFragment, "Calculate");
        adapter.addFragment(new TheoryResultShowingFragment(), "Theory Results");
        adapter.addFragment(new LabResultShowingFragment(), "Lab Results");
        viewPager.setAdapter(adapter);
    }

    /*
        a inner class for creating and managing the view pager
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private static final String TAG = "hi";
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            Log.i(TAG, "getItem: " + position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
