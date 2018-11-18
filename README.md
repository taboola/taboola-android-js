# Taboola JS widgets in 3rd party WebViews (TaboolaJs)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-com/taboola-android-sdk/android-sdk/images/download.svg) ](https://bintray.com/taboola-com/taboola-android-sdk/android-sdk/_latestVersion)[![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)](https://github.com/taboola/taboola-android/blob/master/LICENSE)

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
   implementation 'com.taboola:android-sdk:2.0.27'

   // include to have clicks opened in chrome tabs rather than in a default browser (mandatory)
   implementation 'com.android.support:customtabs:27.+'
 ```
 #### Note: It is advised to use the same version for `Custom Tabs` and your projects `compileSdkVersion`. For example:
For `compileSdkVersion` **27** use 'com.android.support:customtabs:**27**.+'

> ## Notice
> We encourage developers to use the latest SDK version. In order to stay up-to-date we suggest to subscribe in order to get github notifications whenever there is a new release. For more information check: [https://help.github.com/articles/managing-notifications-for-pushes-to-a-repository/]()


2. Include this line in your app’s AndroidManifest.xml to allow Internet access
 ```
   <uses-permission android:name="android.permission.INTERNET" />
 ```

### 1.3. Init TaboolaJs

In your `Application` class:

```java
   public class MyApplication extends Application {
       @Override
       public void onCreate() {
           super.onCreate();
           TaboolaJs.getInstance().init(getApplicationContext());
       }
   }
```

If your project does not have an Application extending class, create one and then init TaboolaJs.

### 1.4. Register and Unregister WebViews
TaboolaJs is a component that works using **your** own Webview instance.

Taboola recommends this order to work with TaboolaJs & your Webview is as follows:
1. Register your Webview with TaboolaJs.
2. Load Taboola Content to your Webview.
3. Unregister your Webview from TaboolaJs before exiting app.
#### Note: Webviews should be registered **before** their content is actually loaded. If you register after loading, a refresh of the webview is required.

#### 1.4.1. Registering 
In your `Activity` or `Fragment` code:
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        
        // The WebView must be registered before page is (re)loaded
        TaboolaJs.getInstance().registerWebView(<YOUR_WEBVIEW>);

        // TODO: Load Webview content.
        
        // Note: if you are loading using <YOUR_WEBVIEW>.loadDataWithBaseURL(), baseUrl must be set.
    }
```

#### 1.4.2 Unregistering
In your `Activity` or `Fragment` code:
```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaboolaJs.getInstance().unregisterWebView(<YOUR_WEBVIEW>);
    }
```


### 1.5 Receiving Webview Render Status
If you wish to receive an event about the render status, register the following listener in TaboolaJs.

#### 1.5.1 Create an OnRenderListener
```java
    OnRenderListener onRenderListener = new  OnRenderListener() {
        @Override
        public void onRenderSuccessful(WebView webView, String placementName, int height) {
                //Code...
        }

        @Override
        public void onRenderFailed(WebView webView, String placementName, String errorMessage) {
                //Code...
        }
    });
```
#### 1.5.2 Register the above listener with TaboolaJs
```java
    TaboolaJs.getInstance().setOnRenderListener(<YOUR_WEBVIEW>,  onRenderListener);
```


### 1.6. Intercepting recommendation clicks

##### 1.6.1. The default click behaviour of TaboolaWidget is as follows:
* On devices where `Chrome Custom Tabs` are supported - Taboola will open the recommendation in a Chrome Custom Tab (in-app)
* Otherwise - Taboola will open the recommendation in the default system web browser (outside of the app)

##### 1.6.2. Overriding default behaviour:
TaboolaApi allows app developers to intercept recommendation clicks in order to create a click-through or to override the default way of opening the recommended article.

In order to intercept clicks, you should implement the interface `com.taboola.android.api.TaboolaOnClickListener` and set it in the sdk.

1. Implement the interface `com.taboola.android.api.TaboolaOnClickListener` 
    1.1 `TaboolaOnClickListener` include the methods:
     ```java
    public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic);
     ```
    1.2 Example implementation:
    In the same Activity/Fragment as `TaboolaWidget` instance:
     ```java
    TaboolaOnClickListener taboolaOnClickListener = new TaboolaOnClickListener() {
      @Override
      public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic) {          
          //Code...
          return false;
      }};
     ```    
2. Connect the event listener to your `TaboolaWidget` instance. 
    ```java
    TaboolaApi.getInstance().setOnClickListener(taboolaOnClickListener);
    ```    
    
##### 1.6.3. Event: onItemClick
`boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic)`
This method will be called every time a user clicks on a Taboola Recommendation, right before it is sent to Android OS for relevant action resolve. The return value of this method allows you to control further system behaviour (after your own code executes).

###### 1.6.3.1 `placementName:`
The name of the placement, in which an Item was clicked.

###### 1.6.3.2 `itemtId:`
The id of the Item clicked.

###### 1.6.3.3 `clickUrl:`
Original click url.

###### 1.6.3.4 `isOrganic:` 
Indicates whether the item clicked was an organic content Taboola Recommendation or not.
(The **best practice** would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which will show that piece of content).

###### 1.6.3.5 `Return value:`
* Returning **`false`** - Aborts the click's default behavior. The app should display the Taboola Recommendation content on its own (for example, using an in-app browser).
* Returning **`true`** - The click will be handled by Taboola's default behavior.
**Note:** Sponsored item clicks (non-organic) are not overridable!
    

### 1.7. Adding HTML/JS widget within the WebView
In order to show Taboola Widget in your WebView instance, load the Taboola mobile JS code in your HTML page, as shown here.

If you are already familiar with the Taboola web JS code, notice that although the Taboola mobile JS code is mostly identical to the Taboola web JS code, there are a few minor modifications that should be made.

Taboola requires multiple javascript snippets to be inserted in your page:
#### 1.7.1. HTML Head Component
Place this code in the `<head>` tag of any HTML page on which you’d like the Taboola widget to appear:

##### 1.7.1.1. Code
```javascript
<script type="text/javascript">
     window._taboola = window._taboola || [];
     _taboola.push({page-type:'auto', url:'pass-url-here'});//Or other values received from your Taboola account manager.
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
##### 1.7.1.2 Parameters Explained
**'page-type-values-received-from-your-Taboola-account-manager'**: pass the internal app representation of the page as received from Taboola account manager.

**'pass-url-here'**: pass the canonical url (web representation) of the app page - this is needed for us to crawl the page to get contextual and meta data.

**'publisher-id'**: replace it with the publisher ID received from your Taboola account manager.

#### 1.7.2 HTML Body
Place this code where you want the widget to appear:

##### 1.7.2.1. Code
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
##### 1.7.2.2. Parameters Explained
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
Follow the instructions on steps 1.1 to 1.5 to configure `TaboolaJs` native side within your app.


## 3. Example App
This repository includes an example Android app which uses the `TaboolaJs`. Feel free to review it and see how `TaboolaJs` is integrated in practice.

To use it:
##### 3.1 Clone this repository
1. Look for the "Clone or Download" button on this page top.
2. Copy the url from the drop box.
3. Clone to your local machine using your favourite Git client.

##### 3.2 Open the project wih your IDE.
1. Open the project as you would any other Android project.
2. Taboola is optimized to working with Android Studio but other IDEs should work as well.

##### 3.3 Example App As Troubleshooting Helper:
In case you encounter some issues while integrating the SDK into your app, try to recreate the scenario within the example app. This might help to isolate the problems. For more help, you would be able to send the example app with your recreated issue to Taboola's support.


## 4. SDK Reference
[TaboolaJs Reference](/TaboolaJsReference.md)

## 5. ProGuard
You can find proguard rules for Taboola Widget in [proguard-taboola-js.pro](/Examples/JsSampleTaboola/app/proguard-taboola-js.pro) file.
The file contains instructions to the rules which you should use depending on which parts of the SDK you are using (you should comment/uncomment which you need).

## 6. GDPR
In order to support the The EU General Data Protection Regulation (GDPR - https://www.eugdpr.org/) in Taboola Mobile SDK, application developer should show a pop up asking the user’s permission for storing their personal data in the App. In order to control the user’s personal data (to store in the App or not) there exists a flag `User_opt_out`. It’s mandatory to set this flag when using the Taboola SDK. The way to set this flag depends on the type of SDK you are using. By default we assume no permission from the user on a pop up, so the personal data will not be saved.

### 6.1. How to set the flag in the SDK integration
Below you can find the way how to set the flag on SDK JS we support. It’s recommended to put these lines alongside the other settings, such as publisher name, etc.

In the HTML file that contain the JS with publisher details, you will need to add:

```javascript
_taboola.push({user_opt_out: 'true'});
```

## 7. License
This program is licensed under the Taboola, Inc. SDK License Agreement (the “License Agreement”).  By copying, using or redistributing this program, you agree with the terms of the License Agreement.  The full text of the license agreement can be found at [https://github.com/taboola/taboola-android/blob/master/LICENSE](https://github.com/taboola/taboola-android/blob/master/LICENSE).
Copyright 2017 Taboola, Inc.  All rights reserved.
