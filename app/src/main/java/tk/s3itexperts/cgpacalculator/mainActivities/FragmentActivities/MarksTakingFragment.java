package tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tk.s3itexperts.cgpacalculator.Data.CGPACalculation;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.MyTextWatcher;
import tk.s3itexperts.cgpacalculator.mainActivities.ResultShowingPage;
import tk.s3itexperts.cgpacalculator.mainActivities.TabActivity;

/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class MarksTakingFragment extends Fragment {

    private List<String> titles;
    private List<TextInputLayout> inputLayouts = new ArrayList<>();
    private List<Double> marks = new ArrayList<>();
    private boolean sizeGraterThanNine;


    public MarksTakingFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.cgpaCalculateButton)
    Button mCalculateCgpaButton;

    //binding the subject edit texts
    @BindView(R.id.subject_1)
    TextInputEditText mSubject_1;

    @BindView(R.id.subject_2)
    TextInputEditText mSubject_2;

    @BindView(R.id.subject_3)
    TextInputEditText mSubject_3;

    @BindView(R.id.subject_4)
    TextInputEditText mSubject_4;

    @BindView(R.id.subject_5)
    TextInputEditText mSubject_5;

    @BindView(R.id.subject_6)
    TextInputEditText mSubject_6;

    @BindView(R.id.subject_7)
    TextInputEditText mSubject_7;

    @BindView(R.id.subject_8)
    TextInputEditText mSubject_8;

    @BindView(R.id.subject_9)
    TextInputEditText mSubject_9;

    @BindView(R.id.subject_10)
    TextInputEditText mSubject_10;

    @BindView(R.id.textLayout_1)
    TextInputLayout mTextInputLayout_1;

    @BindView(R.id.textLayout_2)
    TextInputLayout mTextInputLayout_2;

    @BindView(R.id.textLayout_3)
    TextInputLayout mTextInputLayout_3;

    @BindView(R.id.textLayout_4)
    TextInputLayout mTextInputLayout_4;

    @BindView(R.id.textLayout_5)
    TextInputLayout mTextInputLayout_5;

    @BindView(R.id.textLayout_6)
    TextInputLayout mTextInputLayout_6;

    @BindView(R.id.textLayout_7)
    TextInputLayout mTextInputLayout_7;

    @BindView(R.id.textLayout_8)
    TextInputLayout mTextInputLayout_8;

    @BindView(R.id.textLayout_9)
    TextInputLayout mTextInputLayout_9;

    @BindView(R.id.textLayout_10)
    TextInputLayout mTextInputLayout_10;

    @BindView(R.id.calculationProgressBar)
    ProgressBar mProgressBar;

    @OnClick(R.id.cgpaCalculateButton)
    public void calculateAndShowResult(View view) {

        if (checkAllMarks()) {

            mProgressBar.setVisibility(View.VISIBLE);

            collectAllMarks();

            CGPACalculation cgpaCalculation = new CGPACalculation(marks, TabActivity.mCourseStructure.getCourseCredits());

            String result = cgpaCalculation.getResultInFormat();

            if (!result.isEmpty()) {

                mProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), ResultShowingPage.class);
                intent.putExtra(TabActivity.RESULT, result);
                intent.putExtra(TabActivity.CALLING_FROM, false);
                startActivity(intent);

                getActivity().overridePendingTransition(
                        android.R.anim.fade_in, android.R.anim.fade_out
                );
            } else {

                Log.i("calculator", "calculateAndShowResult: " + result);
                mProgressBar.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_marks_taking, container, false);

        ButterKnife.bind(this, v);

        setTitlesInEditText(titles);

        addViewsToList();

        setTextWatcher();

        return v;
    }

    private void addViewsToList() {
        inputLayouts.add(mTextInputLayout_1);
        inputLayouts.add(mTextInputLayout_2);
        inputLayouts.add(mTextInputLayout_3);
        inputLayouts.add(mTextInputLayout_4);
        inputLayouts.add(mTextInputLayout_5);
        inputLayouts.add(mTextInputLayout_6);
        inputLayouts.add(mTextInputLayout_7);
        inputLayouts.add(mTextInputLayout_8);
        inputLayouts.add(mTextInputLayout_9);
        if (mTextInputLayout_10.getVisibility() == View.VISIBLE) {
            inputLayouts.add(mTextInputLayout_10);
        }
    }

    /*
        method to set the text watcher for the edit texts
     */
    protected void setTextWatcher() {
        mSubject_1.addTextChangedListener(new MyTextWatcher(mTextInputLayout_1, mSubject_1, mSubject_2, 2));
        mSubject_2.addTextChangedListener(new MyTextWatcher(mTextInputLayout_2, mSubject_2, mSubject_3, 2));
        mSubject_3.addTextChangedListener(new MyTextWatcher(mTextInputLayout_3, mSubject_3, mSubject_4, 2));
        mSubject_4.addTextChangedListener(new MyTextWatcher(mTextInputLayout_4, mSubject_4, mSubject_5, 2));
        mSubject_5.addTextChangedListener(new MyTextWatcher(mTextInputLayout_5, mSubject_5, mSubject_6, 2));
        mSubject_6.addTextChangedListener(new MyTextWatcher(mTextInputLayout_6, mSubject_6, mSubject_7, 2));
        mSubject_7.addTextChangedListener(new MyTextWatcher(mTextInputLayout_7, mSubject_7, mSubject_8, 2));
        mSubject_8.addTextChangedListener(new MyTextWatcher(mTextInputLayout_8, mSubject_8, mSubject_9, 2));

//        Toast.makeText(getContext(), "gone! finished: " + sizeGraterThanNine, Toast.LENGTH_SHORT).show();

        if (!sizeGraterThanNine) {
//            Toast.makeText(getContext(), " " + sizeGraterThanNine, Toast.LENGTH_SHORT).show();
            mSubject_9.addTextChangedListener(new MyTextWatcher(mTextInputLayout_9, mSubject_9, mCalculateCgpaButton, 2));

        } else {
            mSubject_9.addTextChangedListener(new MyTextWatcher(mTextInputLayout_9, mSubject_9, mSubject_10, 2));

            mSubject_10.addTextChangedListener(new MyTextWatcher(mTextInputLayout_10, mSubject_10, mCalculateCgpaButton, 2));
        }
    }

    /*
        setting the titles of the edit texts
     */
    public void setTitlesInEditText(List<String> titles) {
        int i = titles.size();
        mTextInputLayout_1.setHint(titles.get(0));
        mTextInputLayout_2.setHint(titles.get(1));
        mTextInputLayout_3.setHint(titles.get(2));
        mTextInputLayout_4.setHint(titles.get(3));
        mTextInputLayout_5.setHint(titles.get(4));
        mTextInputLayout_6.setHint(titles.get(5));
        mTextInputLayout_7.setHint(titles.get(6));
        mTextInputLayout_8.setHint(titles.get(7));
        mTextInputLayout_9.setHint(titles.get(8));

        if (!sizeGraterThanNine) {
            mTextInputLayout_10.setVisibility(View.GONE);
        } else {
            mTextInputLayout_10.setHint(titles.get(9));
        }

    }

    public void setTitle(List<String> titles) {
        this.titles = titles;
        sizeGraterThanNine = (titles.size() == 10);
    }

    public boolean checkAllMarks() {
        boolean error = false;
        int errorLayoutCount = 1;
        for (TextInputLayout layout :
                inputLayouts) {
            if (layout.getEditText() != null) {
                String mark = layout.getEditText().getText().toString();
                if (TextUtils.isEmpty(mark)) {
                    error = true;
                    layout.setErrorEnabled(true);
                    layout.setError("This field must be filled");

                    if (errorLayoutCount == 1) {
                        layout.requestFocus();
                        errorLayoutCount++;
                    }
                }
            }
        }
        return !error; //if error found then here error = true and will return false or otherwise
    }

    public void collectAllMarks() {
        for (TextInputLayout layout : inputLayouts) {
//            Toast.makeText(getContext(), layout.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();

            if (layout.getEditText() != null) {
                marks.add(Double.parseDouble(layout.getEditText().getText().toString()));
            }

        }
    }

}
