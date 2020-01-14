# AndroidX MVVM Starter Kit in Java

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)


This is a starter project which implements Model-View-ViewModel Arch Pattern.  


## Libraries Used
```
    //life
    implementation 'androidx.lifecycle:lifecycle-extensions:2.1.0'
    implementation 'androidx.lifecycle:lifecycle-common-java8:2.1.0'

    //Binding
    implementation 'com.jakewharton:butterknife:10.0.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'

    //Paging
    implementation 'androidx.paging:paging-runtime:2.1.1'

    //UI
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.google.android.material:material:1.2.0-alpha03'

    //Networking
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'com.squareup.retrofit2:converter-scalars:2.5.0'

    //Http
    implementation 'com.squareup.okhttp3:logging-interceptor:3.12.0'
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'

    //Rx
    implementation 'io.reactivex.rxjava2:rxjava:2.2.4'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'

    //Dagger
    implementation "com.google.dagger:dagger:2.19"
    annotationProcessor "com.google.dagger:dagger-compiler:2.19"
    implementation "com.google.dagger:dagger-android:2.19"
    implementation "com.google.dagger:dagger-android-support:2.19"
    annotationProcessor "com.google.dagger:dagger-android-processor:2.19"

    //MultiDex
    implementation 'androidx.multidex:multidex:2.0.1'

    //img downloader
    //implementation 'com.squareup.picasso:picasso:2.71828'

    //room
    def room_version = "2.2.3"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"

    // gson
    implementation 'com.google.code.gson:gson:2.8.6'

    // auto
    compileOnly 'javax.annotation:jsr250-api:1.0'
    api 'com.google.auto.value:auto-value-annotations:1.6.3'
    annotationProcessor "com.google.auto.value:auto-value:1.6.3"
    annotationProcessor 'com.ryanharter.auto.value:auto-value-gson:0.8.0'
    implementation 'com.ryanharter.auto.value:auto-value-gson-annotations:0.8.0'
    annotationProcessor 'com.ryanharter.auto.value:auto-value-parcel:0.2.6'

    // Cookie
    implementation 'com.github.franmontiel:PersistentCookieJar:v1.0.1'

    //MAP
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'

    //Rx
    implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.5@aar'
    implementation 'com.patloew.rxlocation:rxlocation:1.0.5'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.4'
    implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.5@aar'
    implementation 'com.jakewharton.rxbinding2:rxbinding-support-v4:2.2.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
```
