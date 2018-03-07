# ZAppTheme
Android通过AppTheme实现页面整体切换动画，同时利用代码实现一些特殊界面的特定切换效果实例。
>作者：邹峰立，微博：zrunker，邮箱：zrunker@yahoo.com，微信公众号：书客创作，个人平台：[www.ibooker.cc](http://www.ibooker.cc)。

>本文选自[书客创作](http://www.ibooker.cc)平台第55篇文章。[阅读原文](http://www.ibooker.cc/article/55/detail) 。

![书客创作](http://upload-images.jianshu.io/upload_images/3480018-fc48052e2dfbf1d7..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在很多应用中页面跳转动画可能各不相同，可能是左右滑动，也可能是上下切换或者淡入淡出等。这些页面切换效果是怎么实现的呢？

### AppTheme简介
其实在Android开发中可以通过代码去实现页面切换效果，也可以通过设置Theme主题来实现，但是应用程序主题AppTheme往往会被一些开发者忽略掉，因为在一般开发中默认主题就可以了。其实主题设置可以实现APP很多效果，例如实现窗体透明显示，APP整体切换效果等。

**AppTheme实现窗体透明显示**

采用Eclipse开发APP过程中，可能一些开发者会遇到进入App的时候先会白屏或者黑屏一下之后在进入我们想要的布局，这个原因有多种可能是主题设置问题（默认主题导致/自定义主题不合理导致），也可能是机型问题（不同的手机默认主题不一样）。这时候就可以通过设置AppTheme来解决这一问题。
设置主题需要在res文件夹中找到style.xml，并修改默认主题：
```
<resources>
<!--
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
     -->
    <style name="AppBaseTheme" parent="android:Theme.Light">
    <!--
            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.         -->
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowNoTitle">true</item>
    </style>

<!-- Application theme. -->
    <style name="AppTheme" parent="AppBaseTheme">
  <!-- All customizations that are NOT specific to a particular API-level can go here. -->
        <!--设置窗体透明-->
        <item name="android:windowIsTranslucent">true</item>
        <!--设置窗体无主题-->
        <item name="android:windowNoTitle">true</item>
    </style>
</resources>
```
之后再清单文件AndroidManifest.xml中设置修改后的主题为应用主题：
```
<application
    ......
    android:theme="@style/AppTheme" >
        ......
</application>
```
### AppTheme实现页面整体切换动画-右进左出

有时候为了满足所有机型切换动画统一或者为了实现独特的页面整体切换动画，可以借助于AppTheme进行实现。

如果要实现应用程序右进左出的效果，该怎么实现呢？

**首先要定义页面动画。**

在res/anim文件添加动画文件，这里定义slide_left _in.xml（页面关闭进入），slide_left _out.xml（页面打开退出），slide_right _in.xml（页面打开进入），slide_right _out.xml（页面关闭退出）。

A：slide_left _in.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="300"
        android:fromXDelta="-100.0%p"
        android:toXDelta="0.0" />
</set>
```
B：slide_left _out.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="300"
        android:fromXDelta="0.0"
        android:toXDelta="-100.0%p" />
</set>
```
C：slide_right _in.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="300"
        android:fromXDelta="100.0%p"
        android:toXDelta="0.0" />
</set>
```
D：slide_right _out.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="300"
        android:fromXDelta="0.0"
        android:toXDelta="100.0%p" />
</set>
```
**设置主题动画**

最后只要在style.xml中修改AppTheme主题动画即可。
```
<resources>

  <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>

      <!--设置窗体无主题-->
        <item name="windowNoTitle">true</item>
        <!--设置窗体透明-->
        <item name="android:windowIsTranslucent">true</item>
        <!--设置窗体切换动画-->
        <item name="android:windowAnimationStyle">@style/activityAnimation</item>
    </style>

  <!--Activity进入和退出动画-->
    <!-- animation 样式 -->
    <style name="activityAnimation" parent="@android:style/Animation">
        <item name="android:activityOpenEnterAnimation">@anim/slide_right_in</item>
        <item name="android:activityOpenExitAnimation">@anim/slide_left_out</item>
        <item name="android:activityCloseEnterAnimation">@anim/slide_left_in</item>
        <item name="android:activityCloseExitAnimation">@anim/slide_right_out</item>
    </style>

</resources>
```

### 代码实现页面切换动画

在开发中经常会遇到一些比较特殊的页面要进行特定的页面切换动画，这个时候可以借助于代码实现相应的动画效果。

要想通过代码实现页面切换效果，需要借助于Activity类提供的overridePendingTransition方法来运行页面进入和退出动画。

```
/*
 *  页面切换动画
 *  @param enterAnim 第二个activity进入时的动画
 *  @param exitAnim 第一个activity退出时的动画
 */
overridePendingTransition(int enterAnim, int exitAnim);
```
该方法是Android2.0之后才有的方法，只要紧挨着startActivity()或者finish()函数之后调用，就能实现页面切换动画。

如：从MainActivity到BActivity
```
// 进入BActivity
Intent intent = new Intent(this, BActivity.class);
startActivity(intent);
overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
```
其中R.anim.zoomin和R.anim.zoomout是自定义的动画文件，保存到res/anim。

A：zoomin.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/decelerate_interpolator" >

  <scale
        android:duration="2000"
        android:fromXScale="0.1"
        android:fromYScale="0.1"
        android:pivotX="50%p"
        android:pivotY="50%p"
        android:toXScale="1.0"
        android:toYScale="1.0" />

  <!-- 这里为了看到动画演示效果，把动画持续时间设为3秒 -->
    <alpha
        android:duration="1000"
        android:fromAlpha="0.1"
        android:toAlpha="1.0" />

</set>
```
B：zoomout.xml
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/decelerate_interpolator"
    android:zAdjustment="top" >

  <scale
        android:duration="2000"
        android:fromXScale="1.0"
        android:fromYScale="1.0"
        android:pivotX="50%p"
        android:pivotY="50%p"
        android:toXScale=".5"
        android:toYScale=".5" />

  <!--
           系统内置的动画持续时间
           android:duration="@android:integer/config_mediumAnimTime"
    -->
    <alpha
        android:duration="1000"
        android:fromAlpha="1.0"
        android:toAlpha="0" />

</set>
```
[Github地址](https://github.com/zrunker/ZAppTheme)
[阅读原文](http://www.ibooker.cc/article/55/detail) 

----------
![微信公众号：书客创作](http://upload-images.jianshu.io/upload_images/3480018-0cea13ed2b556759..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
