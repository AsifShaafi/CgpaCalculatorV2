package tk.s3itexperts.cgpacalculator.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.StaticDialogsAndMethods;
import tk.s3itexperts.cgpacalculator.helperClasses.ThemeChanger;

public class ResultShowingPage extends AppCompatActivity {

    public static final String FOLDER_NAME = "CGPA_calculator";
    public static final String FILE_EXTENTION = ".txt";
    public static boolean isFileSaved = false;

    /*
        default heading of the file. It is added on top of the saved file before saving
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
    private StringBuilder resultToDisplay =
            new StringBuilder("Date Created: " + sdf.format(Calendar.getInstance().getTime()) + "\n\n");

    @BindView(R.id.saveResultFab)
    FloatingActionButton fabForSave;
    @BindView(R.id.resultShowingTextView)
    TextView mResultShowingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            setting the theme which was last selected or default
         */
        ThemeChanger.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_result_showing_page);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        boolean isSavedFile = getIntent().getBooleanExtra(TabActivity.CALLING_FROM, false);
        //isSavedFile=false means that this activity is being called after the use has given all his marks and calculated the cgpa
        //and true means that this activity is called to show/display the saved files,so they don't need to be saved
        String result = getIntent().getStringExtra(TabActivity.RESULT);

        if (!isSavedFile) {
            isFileSaved = false;
            fabForSave.setVisibility(View.VISIBLE);
            fabForSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StaticDialogsAndMethods.ShowUserSaveOptionAndCallSavingFunction(ResultShowingPage.this,
                            resultToDisplay.toString(), view);
                }
            });
        } else {
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
        } else {
            StaticDialogsAndMethods.showAlertIfFileNotSaved(ResultShowingPage.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isFileSaved) {
                    finish();
                } else {
                    StaticDialogsAndMethods.showAlertIfFileNotSaved(ResultShowingPage.this);
                }
                return true;
            case R.id.action_viewSavedFiles:
                startActivity(new Intent(ResultShowingPage.this, ManageSaveFiles.class));
                break;
            case R.id.action_change_theme:
                StaticDialogsAndMethods.changeTheme(this, ResultShowingPage.this);
                break;

        }
        return true;
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

            boolean dirMade = dir.exists();
            if (!dirMade) {
                dirMade = dir.mkdir();
            }

            if (dirMade) {
                File myFile = new File(dir, fileName + FILE_EXTENTION);
                File myFilePdf = new File(dir, fileName + ".pdf");

                FileOutputStream fileOutputStream = null;
                FileOutputStream fileOutputStreamPdf = null;
                Document document = new Document();

                try {
                    fileOutputStream = new FileOutputStream(myFile);
                    fileOutputStream.write(result.getBytes());

                    //for creating pdf
                    Log.i("pdf", "saving pdf started");
                    fileOutputStreamPdf = new FileOutputStream(myFilePdf);

                    PdfWriter.getInstance(document, fileOutputStreamPdf);
                    document.open();
                    Log.i("pdf", "document opened");

                    Paragraph p1 = new Paragraph(result);
                    Font paraFont = new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE);
                    p1.setAlignment(Paragraph.ALIGN_CENTER);
                    p1.setFont(paraFont);
                    Log.i("pdf", "paragraph ready");

                    //add paragraph to document
                    document.add(p1);
                    Log.i("pdf", "paragraph added");


                } catch (Exception e) {
                    Log.e("File error ", e.getMessage());
                    e.printStackTrace();
                } finally {
                    document.close();
                    Log.i("pdf", "document closed");
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        if (fileOutputStreamPdf != null) {
                            fileOutputStreamPdf.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Snackbar.make(view, "File Saved", Snackbar.LENGTH_SHORT)
                        .setAction("OK", null)
                        .show();
                ResultShowingPage.isFileSaved = true;
            } else {
                Log.i("filepath", "saveResult: folder not created");

                Snackbar.make(view, "File Not Saved", Snackbar.LENGTH_SHORT)
                        .setAction("OK", null)
                        .show();
                ResultShowingPage.isFileSaved = false;
            }

        }
    }

}
