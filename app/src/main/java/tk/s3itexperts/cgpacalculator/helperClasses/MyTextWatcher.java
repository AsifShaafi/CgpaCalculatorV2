package tk.s3itexperts.cgpacalculator.helperClasses;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Asif Imtiaz Shaafi, on 9/3/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class MyTextWatcher implements TextWatcher {

    private TextInputLayout mTextInputLayout;
    private EditText currentView;
    private View nextView;
    private int size;

    public MyTextWatcher(EditText currentView, View nextView, int size) {
        this.currentView = currentView;
        this.nextView = nextView;
        this.size = size;
    }

    public MyTextWatcher(TextInputLayout mTextInputLayout, EditText currentView, View nextView, int size) {
        this.mTextInputLayout = mTextInputLayout;
        this.currentView = currentView;
        this.nextView = nextView;
        this.size = size;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mTextInputLayout != null && mTextInputLayout.isErrorEnabled())
        {
            mTextInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (currentView.getText().toString().length() == size)
        {
                nextView.requestFocus();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
