# General
# Models
-keepnames class com.schatzdesigns.covid19stats.ui.stats.country.model.Country
-keepnames class com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats

# For stack traces
-keepattributes SourceFile, LineNumberTable

# Get rid of package names, makes file smaller
-repackageclasses

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keep class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class kotlinx.coroutines.CoroutineExceptionHandler {}
-keep class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# AndroidX
-dontwarn androidx.annotation.**
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }
-keep class * extends androidx.fragment.app.Fragment{}

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Support Old versions
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }

-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }

# Navigation
-if public class ** implements androidx.navigation.NavArgs
-keepclassmembers public class ** implements androidx.navigation.NavArgs {
    ** fromBundle(android.os.Bundle);
}

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Dagger
-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}

-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
-dontwarn com.google.errorprone.annotations.**

# Retrofit & OkHttp
-dontwarn javax.annotation.**
-keepattributes *Annotation*, Signature, Exceptions
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# This optimization conflicts with how Retrofit uses proxy objects without concrete implementations
-optimizations !method/removal/parameter

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# SSL
-dontwarn com.android.org.conscrypt.SSLParametersImpl
-dontwarn dalvik.system.CloseGuard
-dontwarn kotlin.internal.**
-dontwarn kotlin.reflect.jvm.internal.ReflectionFactoryImpl
-dontwarn org.apache.harmony.xnet.provdier.jsse.SSLParametersImpl
-dontwarn org.conscrypt.**
-dontwarn sun.misc.Unsafe
-dontwarn sun.security.ssl.SSLContext.Impl

# Okio
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Firebase
-dontwarn com.google.firebase.platforminfo.KotlinDetector

# Crashlytics
-keep public class * extends java.lang.Exception
# to skip crashylytics uncomment these:
#-keep class com.crashlytics.** { *; }
#-dontwarn com.crashlytics.**

# Lottie
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}