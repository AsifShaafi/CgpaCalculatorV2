package tk.s3itexperts.cgpacalculator.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.R;

public class ManageSaveFiles extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private File dir;
    private List<String> fileNames = new ArrayList<>();

    @BindView(R.id.fileNameListView)
    ListView mFileList;
    @BindView(R.id.emptyListTextView)
    TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_save_files);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSavedFilesFromFolder();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpTheList() {
        Log.i("filepath", "list is: " + fileNames.size());
        if (fileNames.size() > 0) {
            mEmptyText.setVisibility(View.GONE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    fileNames);

            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            mFileList.setAdapter(adapter);
            mFileList.setItemsCanFocus(true);
            mFileList.setDividerHeight(1);
            mFileList.setOnItemClickListener(this);
        } else {

            Log.i("filepath", "list is null");
            mFileList.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSavedFilesFromFolder() {
        //checking if the external storage is available or not
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            Log.i("filepath", "state found" );
            File root = Environment.getExternalStorageDirectory();
            dir = new File(root.getAbsolutePath() + File.separator + ResultShowingPage.FOLDER_NAME);

            boolean dirExists = dir.exists();

            if (!dirExists)
            {
                dirExists = dir.mkdir();
            }


            Log.i("filepath", "disexists: " + dirExists);

            if (dirExists) {
                for (File f : dir.listFiles()) {
                    if (f.getName().endsWith(ResultShowingPage.FILE_EXTENTION)) {
                        String name = f.getName();
                        fileNames.add(name);
                        Log.i("filepath", "adding: " + name);
                    }
                }
            }
        }
        setUpTheList();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        readFromTheFile(mFileList.getItemAtPosition(i).toString());
    }

    /*
        reading the results of the student from the selected file
     */
    private void readFromTheFile(String fileName) {
        String filePath = File.separator + fileName;

        File file = new File(dir, filePath);

        try {

            FileInputStream inputStream = new FileInputStream(file);

            InputStreamReader reader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuffer = new StringBuilder();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }

            String result = stringBuffer.toString();
            inputStream.close();

            Intent intent = new Intent(ManageSaveFiles.this, ResultShowingPage.class);
            intent.putExtra(TabActivity.RESULT, result);
            intent.putExtra(TabActivity.CALLING_FROM, false);
            startActivity(intent);

            /*
                defining custom transition when the activity changes
            */
            overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
