参考这里：http://sites.google.com/site/io/inside-the-android-application-framework


# Android App #

  * An Android app is a combination of A/B/C/S.
  * Activity/Broadcast Intent Receiver/Content Provider/Service

# Android App lifecycle #

  * 完全由系统决定，如果没有内存了，任何App都可能被kill，包括Service
  * kill实际上是指app所处的process被kill
  * 系统会自动选择，例如background app比foreground更可能被kill
  * 这里体现出android是为内存有限的嵌入式设备，尤其是手机设计的。

  * Note:kill之前，Activity.onPause肯定被调用，Activity.onStop可能被调用

# Activity #

  * Activity代表一件事，并且此事务包含一个特定的用户界面。例如撰写一封新邮件，是一个典型的Activity。
  * 一个应用程序可以处理很多件事务，所以一个程序（通常是APK, see below）可以由多个Activity组成
  * Activity可以注册n个Intent
  * 一个Activity触发另一个Activity用startActivity(Intent)来实现，系统可以根据Intent选择最合适的Activity，编程感受和用户感受都会流畅自然
  * 每个Activity大多都会用到UI，setContentView(View)可以绑定一个View到Activity

  * Note: Activity只能在foreground运行，一旦被遮盖，onPause, onStop就会被调用
Activity 可能不太适合实现IM client后台监听消息的功能

# Broadcast Intent Receiver #

  * 不一定有UI，也不必已经在运行，在你注册的事件发生的时候，系统会自动启动你的Broadcast Intent Receiver
  * 很适合实现MIDP Push功能
  * 可能适合实现不繁忙的IM client的监听功能

# Service #

  * 没有UI，而且长时间运行
  * 为了能背景运行，防止被kill，可以使用Service
  * 可能适合实现繁忙的IM client的监听功能

# APK #

  * 一个APK可以包含多个Activity
  * 注册了android.intent.action.MAIN android.intent.category.LAUNCHER的Activity是程序（APK）的入口
  * 这种main activity（有时也叫front door）可能有多个。
  * 从main activity启动的其他Activity，都同属于main activity所在的Task(Task, see below)


# Task #

  * A new task is created by starting an activity Intent with the Intent.FLAG\_ACTIVITY\_NEW\_TASK flag; this Intent will be used as the root Intent of the task, defining what task it is.
系统中并没有Task这个类。Task是一个activity stack, shown as below:

----------------OTHER Intent-----------------
> Activity
----------------OTHER Intent-----------------
> Activity
----------------OTHER Intent-----------------
> Activity
----------------OTHER Intent-----------------
> Activity (root)
-------Intent.FLAG\_ACTIVITY\_NEW\_TASK---------

  * 对于从notification manager或桌面图标启动的app，多采用NEW\_TASK intent。


# Task Affinities #
  * 还没完全搞明白
  * 每个Task都有Affinity?每个Activity都有Affinity?
  * NEW\_TASK模式启动Activity的时候，要查看Activity with same Affinity是否正在运行
如果是，把这个Task的当前状态拿到foreground；如不是，启动新的Task。


＝process＝

  * 指linux的进程。如不特别指定，每个APK也运行在一个process中。
一个APK也可以指定将其内部的A/B/C/S放在不同的process里面运行（这些对用户是完全透明的实现细节），
  * 好处：
  1. 性能分配平均，例如MP3 service单独用一个process，不容易卡
  1. 内存低时，系统容易kill某一个，特别是重量级code，例如JPG解压缩进程
  1. 安全性，把敏感code隔离
  * 坏处：
> > 一旦分配到不同的process，AIDL (Android Interface Definition Language)可能要用了

# Threads #

  * Android System避免生成过多的Thread。很多Activity的callback（onXXX）都是在该Activity所处的process的main thread调用的。用户应避免在onXXX里面写long run的code

页顶的url里面对looper的介绍很重要。