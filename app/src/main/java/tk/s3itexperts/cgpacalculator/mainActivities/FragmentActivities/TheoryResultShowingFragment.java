package tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities;

import android.os.Bundle;
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

public class TheoryResultShowingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.webViewForTheoryResult)
    WebView webViewForTheory;
    @BindView(R.id.theoryResultRefresh)
    SwipeRefreshLayout mSwipeRefreshLayoutTheory;

    public TheoryResultShowingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_theory_result_showing, container, false);
        ButterKnife.bind(this, v);

        mSwipeRefreshLayoutTheory.setOnRefreshListener(this);

        mSwipeRefreshLayoutTheory.post(new Runnable() {
            @Override
            public void run() {

                /*
                    calling the static method with the url to show the results in the webpage
                 */
                StaticDialogsAndMethods.checkNetworkAndLoadUrl(
                        getContext(),
                        TabActivity.THEORY_RESULT_BASE_URL +
                                TabActivity.mCourseStructure.getWebPageNameExtenction() +
                                ".php",
                        webViewForTheory,
                        mSwipeRefreshLayoutTheory
                );
            }
        });

        return v;
    }

    @Override
    public void onRefresh() {

        StaticDialogsAndMethods.checkNetworkAndLoadUrl(
                getContext(),
                TabActivity.THEORY_RESULT_BASE_URL +
                        TabActivity.mCourseStructure.getWebPageNameExtenction() +
                        ".php",
                webViewForTheory,
                mSwipeRefreshLayoutTheory
        );
    }
}
