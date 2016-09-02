package tk.s3itexperts.cgpacalculator.mainActivities;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperActivities.DialogActivity;

public class ResultShowingPage extends AppCompatActivity {

    public static final String FOLDER_NAME = "CGPA_calculator";
    public static final String FILE_EXTENTION = ".txt";
    public static boolean isFileSaved = false;

    @BindView(R.id.saveResultFab)
    FloatingActionButton fabForSave;
    @BindView(R.id.resultShowingTextView)
    TextView mResultShowingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_showing_page);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        boolean fromCalculate = getIntent().getBooleanExtra(TabActivity.CALLING_FROM, true);
        String result = getIntent().getStringExtra(TabActivity.RESULT);

        if (fromCalculate) {
            isFileSaved = false;
            fabForSave.setVisibility(View.VISIBLE);
            fabForSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogActivity.ShowUserSaveOptionAndCallSavingFunction(ResultShowingPage.this, "fjdl", view);
                }
            });
        }
        else {
            fabForSave.setVisibility(View.GONE);
            isFileSaved = true;
        }

        mResultShowingText.setText(result);

    }

    @Override
    protected void onPause() {
        super.onPause();

         /*
            defining custom transition when the activity changes
         */
        overridePendingTransition(
                android.R.anim.fade_in, android.R.anim.fade_out
        );
    }

    @Override
    public void onBackPressed() {
        if (isFileSaved) {

            super.onBackPressed();
        }
        else{
            DialogActivity.showAlertIfFileNotSaved(ResultShowingPage.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isFileSaved) {
                    finish();
                }
                else{
                    DialogActivity.showAlertIfFileNotSaved(ResultShowingPage.this);
                }

        }
        return super.onOptionsItemSelected(item);
    }

    /*
            method to save the result to the external storage or internal storage
    */
    public void saveResult(String result, String fileName, View view) {

        //checking if the external storage is available or not
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + File.separator + FOLDER_NAME);

            boolean fileMade = true;
            if (!dir.exists()) {
                fileMade = dir.mkdir();
            }

            if (fileMade) {
                File myFile = new File(dir, fileName + FILE_EXTENTION);

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(myFile);
                    fileOutputStream.write(result.getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    Log.e("File error ", e.getMessage());
                    e.printStackTrace();
                }

                Snackbar.make(view, "File Saved", Snackbar.LENGTH_SHORT)
                        .setAction("OK", null)
                        .show();
                ResultShowingPage.isFileSaved = true;
            }
            else{
                Log.i("filepath", "saveResult: folder not created");

                Snackbar.make(view, "File Not Saved", Snackbar.LENGTH_SHORT)
                        .setAction("OK", null)
                        .show();
                ResultShowingPage.isFileSaved = false;
            }

        }
    }

}
