# Taboola JS widgets in 3rd party WebViews (TaboolaJs)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-com/taboola-android-sdk/android-sdk/images/download.svg?version=2.0.8) ](https://bintray.com/taboola-com/taboola-android-sdk/android-sdk/2.0.8/link)
[![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)](https://github.com/taboola/taboola-android/blob/master/LICENSE)

## Table Of Contents
1. [Getting Started](#1-getting-started)
2. [Migrating from Taboola plain JS integration](#2-migrating-from-taboola-plain-js-integration)
3. [Example App](#3-example-app)
4. [SDK Reference](#4-sdk-reference)
5. [Proguard](#5-proguard)
6. [GDPR](#6-gdpr)
7. [License](#7-license)

## 1. Getting Started

`TaboolaJs` SDK integration allows app developers to show Taboola widgets within their own webviews side-to-side with other content from the app.

If you already have a Taboola plain JS widget implemented, you can easily migrate the TaboolaJs and gain the full benefits of using the SDK. the changes required are minimal. Please refer to section [Migrating from Taboola plain JS integration](#2-migrating-from-taboola-plain-js-integration) for more details about how to migrate.

If you are implementing a new Taboola integration in your app, `TaboolaJs` should be fast and easy to implement, and will give you the benefits of both HTML/JS and native.

### 1.1. Minimum requirements

* Android version 2.1  (```android:minSdkVersion="9"```)

### 1.2. Incorporating the SDK

1. Add the library dependency to your project

  ```groovy
   compile 'com.taboola:android-sdk:2.0.8@aar'

   // include to have clicks opened in chrome tabs rather than in a default browser (mandatory)
   compile 'com.android.support:customtabs:26.+'
 ```
> ## Notice
> We encourgae developers to use the latest SDK version. In order to stay up-to-date we suggest subscribing to get github notifications whenever there is a new release. For more information check: [https://help.github.com/articles/managing-notifications-for-pushes-to-a-repository/]()


2. Include this line in your app’s AndroidManifest.xml to allow Internet access
 ```
   <uses-permission android:name="android.permission.INTERNET" />
 ```

### 1.3. Init TaboolaJs

In your `Application` class

```java
   public class MyApplication extends Application {
       @Override
       public void onCreate() {
           super.onCreate();
           TaboolaJs.getInstance().init(getApplicationContext());
       }
   }
```
### 1.4. Register/Unregister WebViews

Before loading the actual content in your webview, you should register any webview that's intented to show Taboola widgets.

Webviews should be registered before their content is actually loaded. If you register after loading, a refresh of the webview is required.

Make sure to unregister your webview before it's destroyed.

in your `Activity` or `Fragment` code:

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // webView must be registered before page is (re)loaded
        TaboolaJs.getInstance().registerWebView(mWebView);

        // setting the listener is optional
        TaboolaJs.getInstance().setOnRenderListener(mWebView,  new OnRenderListener() {
                @Override
                public void onRenderSuccessful(WebView webView, String placementName) {
                        // todo
                }

                @Override
                public void onRenderFailed(WebView webView, String placementName, String errorMessage) {
                        // todo
                }
        });

        // TODO: load webiew content
        // Note: if you are loading using webView.loadDataWithBaseURL(), baseUrl must be set.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaboolaJs.getInstance().unregisterWebView(mWebView);
    }
```

### 1.5. Intercepting recommendation clicks

The default click behavior of TaboolaWidget is as follows:

* On devices where Chrome custom tab is supported - open the recommendation in a Chrome custom tab (in-app)
* Otherwise - open the recommendation in the system default web browser (outside of the app)

`TaboolaJs` allows app developers to intercept recommendation clicks in order to create a click-through or to override the default way of opening the recommended article.

In order to intercept clicks, you should implement the interface `TaboolaOnClickListener` and set it in the `TaboolaJs` object.

```java
   TaboolaJs.getInstance().setOnClickListener(new TaboolaOnClickListener() {
       @Override
       public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic) {
           return false;
       }
   });

```

This method will be called every time a user clicks a recommendation, right before triggering the default behavior. You can block default click handling for organic items by returning `false` in `onItemClick()` method.

* Return **`false`** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser). **(Aborts only for organic items!)**
* Return **`true`** - this will allow the app to implement a click-through and continue to the default behaviour.

`isOrganic` indicates whether the item clicked was an organic content recommendation or not.
**Best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which shows that content.**

### 1.6. Adding HTML/JS widget within the webview
Your HTML page loaded inside the webview should contain the Taboola mobile JS code in order to bind with the TaboolaJs native SDK and actually show the widget.

If you are already familiar with the Taboola web JS code, notice that although the Taboola mobile JS code is mostly identical to the Taboola web JS code, there are a few minor modifications that should be made.

Place this code in the `<head>` tag of any HTML page on which you’d like the Taboola widget to appear:

```javascript
<script type="text/javascript">
     window._taboola = window._taboola || [];
     _taboola.push({page-type:page-type-values-received-from-your-Taboola-account-manager:'auto', url:'pass-url-here'});
     !function (e, f, u, i) {
          if (!document.getElementById(i)){
               e.async = 1;
               e.src = u;
               e.id = i;
               f.parentNode.insertBefore(e, f);
          }
     }(document.createElement('script'),document.getElementsByTagName('script')[0],'//cdn.taboola.com/libtrc/publisher-id/mobile-loader.js','tb_mobile_loader_script');
</script>
```

**'page-type-values-received-from-your-Taboola-account-manager'**: pass the internal app representation of the page as received from Taboola account manager.

**'pass-url-here'**: pass the canonical url (web representation) of the app page - this is needed for us to crawl the page to get contextual and meta data.

**'publisher-id'**: replace it with the publisher ID received from your Taboola account manager.

Place this code where you want the widget to appear:

```html
<div id="container-id"></div>
<script type="text/javascript">
     window._taboola = window._taboola || [];
     _taboola.push({mode: 'mode-name',
     	container: 'container-id',
     	placement: 'Placement Name',
     	target_type: 'mix'});

 // Notice - this part is unique to mobile SDK JS integrations!
_taboola["mobile"] = window._taboola["mobile"] || [];
_taboola["mobile"].push({
   publisher:"publisher-id-goes-here"
});
</script>
```
**'container-id'**: use any id for the actual widget container element

**'mode-name'**: replace it with the mode parameter received from your Taboola account manager

**'Placement Name'**: use the placement name received from your Taboola account manager

**"publisher-id-goes-here"**: replace it with the publisher ID received from your Taboola account manager.

Do not forget to register your webview with the native `TaboolaJs`object!

## 2. Migrating from Taboola plain JS integration
If your app already has a webview which contains the Taboola web JS code in it, you can easily migrate with `TaboolaJS` with a few simple steps:

### 2.1 Javascript changes
* In your page `<head>` section, change the path of taboola `loader.js` to be `mobile-loader.js`
* Add this to your script right before pushing the configuration to `_taboola` (replace **'publisher-id-goes-here'** with your actual publisher id)

```javascript
_taboola["mobile"] = window._taboola["mobile"] || [];
_taboola["mobile"].push({
   publisher:"publisher-id-goes-here"
});
```

### 2.1 Native code changes
follow the instructions on steps 1.1 to 1.5 to configure `TaboolaJs` native side within your app.


## 3. Example App
This repository includes an example Android app which uses the `TaboolaJs`. Review it and see how `TaboolaJs` is integrated in practice.

## 4. SDK Reference
[TaboolaJs Reference](/TaboolaJsReference.md)

## 5. ProGuard
You can find proguard rules for Taboola Widget in [proguard-taboola-js.pro](/Examples/JsSampleTaboola/app/proguard-taboola-js.pro) file.
The file contains instructions on which rules to comment/uncomment depending on which parts of the SDK you are using.

## 6. GDPR
In order to support the The EU General Data Protection Regulation (GDPR - https://www.eugdpr.org/) in Taboola Mobile SDK, application developer should show a pop up asking the user’s permission for storing their personal data in the App. In order to control the user’s personal data (to store in the App or not) there exists a flag `User_opt_out`. It’s mandatory to set this flag when using the Taboola SDK. The way to set this flag depends on the type of SDK you are using. By default we assume no permission from the user on a pop up, so the personal data will not be saved.

### 6.1. How to set the flag in the SDK integration
Below you can find the way how to set the flag on SDK JS we support. It’s recommended to put these lines alongside the other settings, such as publisher name, etc.

In the HTML file that contain the JS with publisher details, you will need to add:

```javascript
_taboola.push(user_opt_out, ‘true’);
```

## 7. License
This program is licensed under the Taboola, Inc. SDK License Agreement (the “License Agreement”).  By copying, using or redistributing this program, you agree to the terms of the License Agreement.  The full text of the license agreement can be found at [https://github.com/taboola/taboola-android/blob/master/LICENSE](https://github.com/taboola/taboola-android/blob/master/LICENSE).
Copyright 2017 Taboola, Inc.  All rights reserved.
