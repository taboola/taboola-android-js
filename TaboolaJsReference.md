# Taboola JS SDK Reference

## public class TaboolaJs
### `public static TaboolaJs getInstance()`

**Returns:** singleton instance of the SDK

### `public TaboolaJs init(Context applicationContext)`

Initializes TaboolaJs. Must be called before any other method of the JS SDK. Typically you would want to do it in you Application class in OnCreate()

 * **Parameters:** `applicationContext` 

### `public void registerWebView(WebView webView)`

Registers `WebView` within Taboola JS SDK. `android.webkit.JavascriptInterface` with the name declared as `TaboolaJs.INJECTED_OBJECT_NAME` will be injected into given `WebView`

Note: When `WebView` is no longer needed it must be unregistered to avoid leaks

 * **Parameters:** `webView` — `WebView` to register
 * **See also:** TaboolaJs.unregisterWebView(WebView)

### `public void registerWebView(WebView webView, OnRenderListener onRenderListener)`

Registers `WebView` within Taboola JS SDK. `android.webkit.JavascriptInterface` with the name declared as `TaboolaJs.INJECTED_OBJECT_NAME` will be injected into given `WebView`

Note: When `WebView` is no longer needed it must be unregistered to avoid leaks

 * **Parameters:**
   * `webView` — WebView to register
   * `onRenderListener` — listener to be called when recommendations have succeeded or failed to render (called separately for each placement)
 * **See also:** TaboolaJs.unregisterWebView(WebView)

### `public void unregisterWebView(WebView webView)`

Unregisters WebView within Taboola JS SDK. Also, if Android version is HONEYCOMB (Api 11) or higher then previously injected `android.webkit.JavascriptInterface` will be removed

 * **Parameters:** `webView` — WebView to unregister
 * **See also:** TaboolaJs.registerWebView(WebView)

### public void setLogLevel(int logLevel)

Sets log level for the SDK. You can find logLevel constants in the `com.taboola.android.utils.Logger` class, for example `Logger.DEBUG`

### `public void setOnRenderListener(WebView webView, @Nullable OnRenderListener onRenderListener)`

Registers a listener to be called when recommendations have succeeded or failed to render (called separately for each placement)

### `public TaboolaJs setOnClickListener(TaboolaOnClickListener onClickListener)`

Sdk allows to intercept recommendation clicks, and block default click handling for organic items. If you wand to block the default behavior, and display the recommendation content on your own, return `false` in `TaboolaOnClickListener.onItemClick(String, String, String, boolean)`

Note: The return value will be ignored for non-organic items (!).

The listener will be called every time a user clicks a recommendation, right before triggering the default behavior

## public interface OnRenderListener
Listener to be called when recommendations have succeeded or failed (called separately for each placement)

### `void onRenderSuccessful(WebView webView, String placementName)`
### `void onRenderFailed(WebView webView, String placementName, String errorMessage)`
