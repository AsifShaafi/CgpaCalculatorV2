package tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.mainActivities.ResultShowingPage;

/**
 * Created by Asif Imtiaz Shaafi, on 8/28/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class MarksTakingFragment extends Fragment {


    public MarksTakingFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.cgpaCalculateButton)
    Button mCalculateCgpaButton;

    @OnClick(R.id.cgpaCalculateButton)
    public void calculateAndShowResult(View view) {
        startActivity(new Intent(getContext(), ResultShowingPage.class));
        getActivity().overridePendingTransition(
                android.R.anim.fade_in, android.R.anim.fade_out
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_marks_taking, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

}
