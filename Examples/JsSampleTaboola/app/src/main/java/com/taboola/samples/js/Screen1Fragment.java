package com.taboola.samples.js;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.taboola.android.api.TaboolaOnClickListener;
import com.taboola.android.js.TaboolaJs;
import com.taboola.android.utils.Logger;

import static com.taboola.samples.js.Const.BASE_URL;

public class Screen1Fragment extends Fragment {
    private static final String TAG = Screen1Fragment.class.getSimpleName();
    private View mRootView;
    private WebView mWebView;

    public static Screen1Fragment newInstance() {
        return new Screen1Fragment();
    }

    public Screen1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_screen1, container, false);
        mWebView = (WebView) mRootView.findViewById(R.id.web_view);

        initWebView();

        TaboolaJs.getInstance()
                .setLogLevel(Logger.DEBUG)
                .registerWebView(mWebView)
                .setOnClickListener(new TaboolaOnClickListener() {
                    @Override
                    public boolean onItemClick(String placementName, String itemId, String clickUrl,
                                               boolean isOrganic) {
                        // todo handle organic items
                        return false;
                    }
                });

        String contentHtml = FileUtil.getAssetFileContent(getContext(), Const.CONTENT_HTML_FILE_NAME);
        mWebView.loadDataWithBaseURL(BASE_URL, contentHtml, "text/html", "UTF-8", "");

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TaboolaJs.getInstance().unregisterWebView(mWebView);
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result1) {
                return super.onJsAlert(view, url, message, result1);
            }
        });

        if ((BuildConfig.DEBUG) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
}
