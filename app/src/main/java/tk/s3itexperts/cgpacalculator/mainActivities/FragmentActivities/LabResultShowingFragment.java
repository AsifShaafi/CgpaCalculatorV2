package tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.s3itexperts.cgpacalculator.R;
import tk.s3itexperts.cgpacalculator.helperClasses.StaticDialogsAndMethods;
import tk.s3itexperts.cgpacalculator.mainActivities.TabActivity;


public class LabResultShowingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.webViewForLabResult)
    WebView webViewForLAb;
    @BindView(R.id.labResultRefresh)
    SwipeRefreshLayout mSwipeRefreshLayoutLab;

    public LabResultShowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_lab_result_showing_framgment, container, false);

        ButterKnife.bind(this, v);

        mSwipeRefreshLayoutLab.setOnRefreshListener(this);

        mSwipeRefreshLayoutLab.post(new Runnable() {
            @Override
            public void run() {

                /*
                    calling the static method with the url to show the results in the webpage
                 */
                StaticDialogsAndMethods.checkNetworkAndLoadUrl(
                        getContext(),
                        TabActivity.LAB_RESULT_BASE_URL +
                        TabActivity.mCourseStructure.getWebPageNameExtenction() +
                        ".php",
                        webViewForLAb,
                        mSwipeRefreshLayoutLab
                );
            }
        });

        return v;
    }

    @Override
    public void onRefresh() {
        StaticDialogsAndMethods.checkNetworkAndLoadUrl(
                getContext(),
//                TabActivity.LAB_RESULT_BASE_URL +
//                        TabActivity.mCourseStructure.getWebPageNameExtenction() +
//                        ".php",
                "https://www.google.com",
                webViewForLAb,
                mSwipeRefreshLayoutLab
        );
    }

}
