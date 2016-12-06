##android上可文字垂直显示的textview，可以设置文字起始方向,文字大小，文字换行符，文字边线，文字边线的方向等等。

![image](https://github.com/tenny1225/VerticalTextView/raw/master/image/m1.png)
###用法1
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <com.tenny.mystory.TextViewVertical
        android:layout_width="wrap_content"
        android:layout_height="300dp"
        app:v_start="left"
        app:v_text="最开始的时候,你并不熟悉这座陌生的城市，你甚至摸得到内心那种无法融入的恐慌和彷徨，据说这种感觉叫漂泊！！"
        app:v_textColor="#565656"
        app:v_textSize="18dp" />
</LinearLayout>
```

![image](https://github.com/tenny1225/VerticalTextView/raw/master/image/m2.png)
###用法2
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <com.tenny.mystory.TextViewVertical
        android:layout_width="wrap_content"
        android:layout_height="300dp"
        app:v_cutChars=","   <!--多个分隔符用|隔开-->
        app:v_line="right"
        app:v_lineColor="#5555"
        app:v_lineWidth="0.5dp"
        app:v_start="right"
        app:v_text="最开始的时候 你并不熟悉这座陌生的城市,你甚至摸得到内心那种无法融入的恐慌和彷徨,据说,这种感觉叫漂泊"
        app:v_textColor="#565656"
        app:v_textHorizontalMargin="18dp"
        app:v_textSize="18dp" />
</LinearLayout>
```
