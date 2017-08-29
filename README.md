# Taboola JS widgets in 3rd party WebViews (TaboolaJs)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-com/taboola-android-sdk/android-sdk/images/download.svg) ](https://bintray.com/taboola-com/taboola-android-sdk/android-sdk/_latestVersion)
[![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)](https://github.com/taboola/taboola-android/blob/master/LICENSE)

## Table Of Contents
1. [Getting Started](#1-getting-started)
2. [Example Apps](#2-example-apps)
3. [SDK Reference](#3-sdk-reference)
4. [License](#4-license)

## 1. Getting Started

### 1.1. Minimum requirements

* Android version 2.1  (```android:minSdkVersion="9"```)

### 1.2. Incorporating the SDK

1. Add the library dependency to your project
  
  ```groovy
   compile 'com.taboola:android-sdk:1.+@aar'

   // include to have clicks opened in chrome tabs rather than in a default browser (mandatory)
   compile 'com.android.support:customtabs:25.+'
 ```
> ## Notice
> the + notation in gradle sdk version number is only a suggestion. We encourgae developers to use the latest SDK version. Taboola SDK will remain backward compatible between minor versions.


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

in your `Activity` or `Fragment` code:

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaboolaJs.getInstance().registerWebView(mWebView);
        
        // setting listener is optional
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
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaboolaJs.getInstance().unregisterWebView(mWebView);
    }
```

### 1.5. Intercepting recommendation clicks

The default click behavior of TaboolaWidget is as follows:

* On devices where Chrome custom tab is supported - open the recommendation in a chrome custom tab (in-app)
* Otherwise - open the recommendation in the system default web browser (outside of the app) 

TaboolaApi allows app developers to intercept recommendation clicks in order to create a click-through or to override the default way of opening the recommended article. 

In order to intercept clicks, you should implement the interface `com.taboola.android.api.TaboolaOnClickListener` and set it in the sdk.

```java
   TaboolaJs.getInstance().setOnClickListener(new TaboolaOnClickListener() {
       @Override
       public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic) {
           return false;
       }
   });

```

This method will be called every time a user clicks a recommendation, right before triggering the default behavior. You can block default click handling for organic items by returning `false` in `onItemClick()` method.

* Return **`false`** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser). (Aborts only for organic items!)
* Return **`true`** - this will allow the app to implement a click-through and continue to the default behaviour.

`isOrganic` indicates whether the item clicked was an organic content recommendation or not.
**Best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which shows that piece of content.**

## 2. Example App
This repository includes an example Android app which uses the `TaboolaJs`.

## 4. SDK Reference
[TaboolaJs Reference](/TaboolaJsReference.md)

## 5. License
This program is licensed under the Taboola, Inc. SDK License Agreement (the “License Agreement”).  By copying, using or redistributing this program, you agree to the terms of the License Agreement.  The full text of the license agreement can be found at [https://github.com/taboola/taboola-android/blob/master/LICENSE](https://github.com/taboola/taboola-android/blob/master/LICENSE).
Copyright 2017 Taboola, Inc.  All rights reserved.
