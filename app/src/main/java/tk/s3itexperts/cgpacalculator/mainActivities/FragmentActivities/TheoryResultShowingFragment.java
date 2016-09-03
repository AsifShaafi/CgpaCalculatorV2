package tk.s3itexperts.cgpacalculator.mainActivities.FragmentActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import tk.s3itexperts.cgpacalculator.mainActivities.TabActivity;

public class TheoryResultShowingFragment extends Fragment {

    @BindView(R.id.webViewForTheoryResult)
    WebView webViewForTheory;

    public TheoryResultShowingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_theory_result_showing, container, false);
        ButterKnife.bind(this, v);

        loadResult(TabActivity.THEORY_RESULT_BASE_URL + "2_1" + ".php");

        return v;
    }

    public void loadResult(final String url) {
        Log.i("lab", "loadResult: " + url);

        webViewForTheory.postDelayed(new Runnable() {

            @Override
            public void run() {
                webViewForTheory.loadUrl(url);
            }
        }, 500);
        WebSettings webSettings = webViewForTheory.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDisplayZoomControls(true);

        webViewForTheory.setFitsSystemWindows(true);
        webViewForTheory.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webViewForTheory.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                webViewForTheory.loadUrl("file:///android_asset/Error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return true;
            }
        });
    }
}
