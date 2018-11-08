###---------- Begin: proguard configuration for Taboola js  ----------
# add if using JS
-keep class com.taboola.android.js.InjectedObject** { *; }


# add if NOT using API SDK
-dontwarn com.taboola.android.api.**

# add if NOT using DFP mediation
-dontwarn com.taboola.android.mediation.DfpCustomEventBanner

# add if NOT using MoPub mediation
-dontwarn com.taboola.android.mediation.MoPubCustomEventBanner

# Always add
-keepnames class com.taboola.android.integration_verifier.testing.tests.proguard.ProguardVerificationStub
###---------- End: proguard configuration for Taboola js  ----------
