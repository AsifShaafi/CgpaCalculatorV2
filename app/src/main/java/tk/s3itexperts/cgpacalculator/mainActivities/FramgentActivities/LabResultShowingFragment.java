package tk.s3itexperts.cgpacalculator.mainActivities.FramgentActivities;


import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class LabResultShowingFragment extends Fragment {

    @BindView(R.id.webViewForLabResult)
    WebView webViewForLAb;

    public LabResultShowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void loadResult(final String url) {
        Log.i("lab", "loadResult: " + url);

        webViewForLAb.postDelayed(new Runnable() {

            @Override
            public void run() {
                webViewForLAb.loadUrl(url);
            }
        }, 500);
        WebSettings webSettings = webViewForLAb.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDisplayZoomControls(true);

        webViewForLAb.setFitsSystemWindows(true);
        webViewForLAb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webViewForLAb.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                webViewForLAb.loadUrl("file:///android_asset/Error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_lab_result_showing_framgment, container, false);

        ButterKnife.bind(this, v);

        loadResult(TabActivity.LAB_RESULT_BASE_URL + "2_1" + ".php");

        return v;
    }

}
