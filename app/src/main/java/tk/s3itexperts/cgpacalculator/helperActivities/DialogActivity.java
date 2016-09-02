package tk.s3itexperts.cgpacalculator.helperActivities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.ThemeChanger;
import tk.s3itexperts.cgpacalculator.mainActivities.MainActivity;
import tk.s3itexperts.cgpacalculator.mainActivities.ResultShowingPage;

/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class DialogActivity {

    /*
        dialog to give the user the options to set the year
     */
    public static void showYearOptions(final Context context, final TextView mTextView) {
        final String[] dataList = context.getResources().getStringArray(R.array.years);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("Choose your year:")
                .setItems(dataList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.STD_YEAR = i + 1;
                        mTextView.setText(String.format(Locale.getDefault(),
                                "%s %s",
                                context.getResources().getString(R.string.you_selected_year), dataList[i]));
                    }
                });

        Dialog dialog = builder.create();
        dialog.show();
    }

    /*
        dialog to give the user the options to select and change the theme of the app
     */

    public static void changeTheme(final Context context, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Select theme:")
                .setItems(new String[]{"Dark", "Light"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ThemeChanger.changeToTheme(activity, i);
                    }
                });

        Dialog dialog = builder.create();
        dialog.show();
    }

    /*
        method for displaying a dialog for the user to give their id and save the file with that id
     */
    public static void ShowUserSaveOptionAndCallSavingFunction(final ResultShowingPage activity,
                                                               final String result, final View view) {
        final View v = LayoutInflater.from(activity).inflate(R.layout.saving_dialog, null);
        final EditText text1 = (EditText) v.findViewById(R.id.fileNameEditText1);
        final EditText text2 = (EditText) v.findViewById(R.id.fileNameEditText2);
        final EditText text3 = (EditText) v.findViewById(R.id.fileNameEditText3);
        final EditText text4 = (EditText) v.findViewById(R.id.fileNameEditText4);

        /*
            adding text watcher to go to the next edit text automatically when the use has given the
            required input in the text field
         */
        text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (text1.getText().toString().length() == 2) {
                    text2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (text2.getText().toString().length() == 2) {
                    text3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (text3.getText().toString().length() == 2) {
                    text4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("Enter your id:")
                .setView(v)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!checkEditText(text1) || !checkEditText(text2)
                                || !checkEditText(text3)
                                || !checkEditText(text4)) {

                            activity.saveResult(
                                    result,
                                    text1.getText().toString() + "_" +
                                            text2.getText().toString() + "_" +
                                            text3.getText().toString() + "_" +
                                            text4.getText().toString(),
                                    view
                            );

                        } else {
                            Snackbar.make(view,
                                    "File not saved.Please enter correct id and all fields.", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .show();
                        }
                    }
                });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private static boolean checkEditText(TextView textView) {
        return textView.getText().toString().isEmpty();
    }

    /*
        method to show user the alert if they are leaving the activity without saving the file
     */
    public static void showAlertIfFileNotSaved(final ResultShowingPage resultShowingPageActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(resultShowingPageActivity)
                .setMessage("The result is not saved, would you still want to leave without saving " +
                        "the result?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultShowingPageActivity.finish();
                    }
                })
                .setNegativeButton("Cancel", null);

        Dialog dialog = builder.create();
        dialog.show();
    }
}
