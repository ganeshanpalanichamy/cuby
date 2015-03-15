在Android中, 每个应用程序都可以有自己的进程. 在写UI应用的时候, 经常要用到Service. 在不同的进程中, 怎样传递对象呢?  显然, Java中不允许跨进程内存共享. 因此传递对象, 只能把对象拆分成操作系统能理解的简单形式, 以达到跨界对象访问的目的. 在J2EE中,采用RMI的方式, 可以通过序列化传递对象. 在Android中, 则采用AIDL的方式. 理论上AIDL可以传递Bundle,实际上做起来却比较麻烦.

AIDL(AndRoid接口描述语言)是一种借口描述语言; 编译器可以通过aidl文件生成一段代码，通过预先定义的接口达到两个进程内部通信进程的目的. 如果需要在一个Activity中, 访问另一个Service中的某个对象, 需要先将对象转化成AIDL可识别的参数(可能是多个参数), 然后使用AIDL来传递这些参数, 在消息的接收端, 使用这些参数组装成自己需要的对象.

AIDL的IPC的机制和COM或CORBA类似, 是基于接口的，但它是轻量级的。它使用代理类在客户端和实现层间传递值. 如果要使用AIDL, 需要完成2件事情: 1. 引入AIDL的相关类.; 2. 调用aidl产生的class.

具体实现步骤如下:

1、创建AIDL文件, 在这个文件里面定义接口, 该接口定义了可供客户端访问的方法和属性。 如: ITaskBinder.adil

package com.cmcc.demo;



import com.cmcc.demo.ITaskCallback;



interface ITaskBinder {



> boolean isTaskRunning();



> void stopRunningTask();



> void registerCallback(ITaskCallback cb);



> void unregisterCallback(ITaskCallback cb);

}

其中: ITaskCallback在文件ITaskCallback.aidl中定义:

package com.cmcc.demo;



interface ITaskCallback {

> void actionPerformed(int actionId);

}

注意: 理论上, 参数可以传递基本数据类型和String, 还有就是Bundle的派生类, 不过在Eclipse中,目前的ADT不支持Bundle做为参数, 据说用Ant编译可以, 我没做尝试.

2、编译AIDL文件, 用Ant的话, 可能需要手动, 使用Eclipse plugin的话,可以根据adil文件自动生产java文件并编译, 不需要人为介入.

3、在Java文件中, 实现AIDL中定义的接口. 编译器会根据AIDL接口, 产生一个JAVA接口。这个接口有一个名为Stub的内部抽象类，它继承扩展了接口并实现了远程调用需要的几个方法。接下来就需要自己去实现自定义的几个接口了.

ITaskBinder.aidl中接口的实现, 在MyService.java中接口以内嵌类的方式实现:

private final ITaskBinder.Stub mBinder = new ITaskBinder.Stub() {

> public void stopRunningTask() {

> //@TODO

> }



> public boolean isTaskRunning() {

> //@TODO

> return false;

> }



> public void registerCallback(ITaskCallback cb) {

> if (cb != null) mCallbacks.register(cb);

> }

> public void unregisterCallback(ITaskCallback cb) {

> if (cb != null) mCallbacks.unregister(cb);

> }

};

在MyActivity.java中ITaskCallback.aidl接口实现:

private ITaskCallback mCallback = new ITaskCallback.Stub() {

> public void actionPerformed(int id) {

> //TODO

> printf("callback id=" + id);

> }

};

4、向客户端提供接口ITaskBinder, 如果写的是service，扩展该Service并重载onBind ()方法来返回一个实现上述接口的类的实例。这个地方返回的mBinder,就是上面通过内嵌了定义的那个. (MyService.java)

> public IBinder onBind(Intent t) {

> printf("service on bind");

> return mBinder;

}

在Activity中, 可以通过Binder定义的接口, 来进行远程调用.

5、在服务器端回调客户端的函数. 前提是当客户端获取的IBinder接口的时候,要去注册回调函数, 只有这样, 服务器端才知道该调用那些函数在:MyService.java中:

> void callback(int val) {

> final int N = mCallbacks.beginBroadcast();

> for (int i=0; i<N; i++) {

> try {

> mCallbacks.getBroadcastItem(i).actionPerformed(val);

> } catch (RemoteException e) {

> // The RemoteCallbackList will take care of removing

> // the dead object for us.

> }

> }

> mCallbacks.finishBroadcast();

}

AIDL的创建方法:

AIDL语法很简单,可以用来声明一个带一个或多个方法的接口，也可以传递参数和返回值。由于远程调用的需要, 这些参数和返回值并不是任何类型.下面是些AIDL支持的数据类型:

1. 不需要import声明的简单Java编程语言类型(int,boolean等)

2. String, CharSequence不需要特殊声明



3. List, Map和Parcelables类型, 这些类型内所包含的数据成员也只能是简单数据类型, String等其他比支持的类型.

(

(另外: 我没尝试Parcelables, 在Eclipse+ADT下编译不过, 或许以后会有所支持).

下面是AIDL语法:

> // 文件名: SomeClass.aidl

> // 文件可以有注释, 跟java的一样

> // 在package以前的注释, 将会被忽略.

> // 函数和变量以前的注释, 都会被加入到生产java代码中.

package com.cmcc.demo;

> // import 引入语句

import com.cmcc.demo.ITaskCallback;



interface ITaskBinder {

> //函数跟java一样, 可以有0到多个参数 ,可以有一个返回值

> boolean isTaskRunning();



> void stopRunningTask();

> //参数可以是另外的一个aidl定义的接口

> void registerCallback(ITaskCallback cb);



void unregisterCallback(ITaskCallback cb);

//参数可以是String, 可以用in表入输入类型, out表示输出类型.

int getCustomerList(in String branch, out String[.md](.md) customerList);



}

实现接口时有几个原则:

.抛出的异常不要返回给调用者. 跨进程抛异常处理是不可取的.

.IPC调用是同步的。如果你知道一个IPC服务需要超过几毫秒的时间才能完成地话，你应该避免在Activity的主线程中调用。 也就是IPC调用会挂起应用程序导致界面失去响应. 这种情况应该考虑单起一个线程来处理.

.不能在AIDL接口中声明静态属性。

IPC的调用步骤:

  1. 声明一个接口类型的变量，该接口类型在.aidl文件中定义。

> 2. 实现ServiceConnection。

> 3. 调用ApplicationContext.bindService(),并在ServiceConnection实现中进行传递.

> 4. 在ServiceConnection.onServiceConnected()实现中，你会接收一个IBinder实例(被调用的Service). 调用

> YourInterfaceName.Stub.asInterface((IBinder)service)将参数转换为YourInterface类型。

> 5. 调用接口中定义的方法。 你总要检测到DeadObjectException异常，该异常在连接断开时被抛出。它只会被远程方法抛出。

> 6. 断开连接，调用接口实例中的ApplicationContext.unbindService()



下面是整个程序:

1. ITaskCallback.aidl



package com.cmcc.demo;



interface ITaskCallback {

> void actionPerformed(int actionId);

}



2. ITaskBinder.aidl

package com.cmcc.demo;



import com.cmcc.demo.ITaskCallback;



interface ITaskBinder {



> boolean isTaskRunning();



> void stopRunningTask();



> void registerCallback(ITaskCallback cb);



> void unregisterCallback(ITaskCallback cb);

}



3.  MyService.java

package com.cmcc.demo;



import android.app.Service;

import android.content.Intent;

import android.os.IBinder;

import android.os.RemoteCallbackList;

import android.os.RemoteException;

import android.util.Log;



public class MyService extends Service {



> @Override

> public void onCreate() {

> printf("service create");

> }



> @Override

> public void onStart(Intent intent, int startId) {

> printf("service start id=" + startId);

> callback(startId);

> }



> @Override

> public IBinder onBind(Intent t) {

> printf("service on bind");

> return mBinder;

> }



> @Override

> public void onDestroy() {

> printf("service on destroy");

> super.onDestroy();

> }



> @Override

> public boolean onUnbind(Intent intent) {

> printf("service on unbind");

> return super.onUnbind(intent);

> }



> public void onRebind(Intent intent) {

> printf("service on rebind");

> super.onRebind(intent);

> }



> private void printf(String str) {

> Log.e("TAG", "###################------ " + str + "------");

> }



> void callback(int val) {

> final int N = mCallbacks.beginBroadcast();

> for (int i=0; i<N; i++) {

> try {

> mCallbacks.getBroadcastItem(i).actionPerformed(val);

> } catch (RemoteException e) {

> // The RemoteCallbackList will take care of removing

> // the dead object for us.

> }

> }

> mCallbacks.finishBroadcast();

> }



> private final ITaskBinder.Stub mBinder = new ITaskBinder.Stub() {

> public void stopRunningTask() {



> }



> public boolean isTaskRunning() {

> return false;

> }



> public void registerCallback(ITaskCallback cb) {

> if (cb != null) mCallbacks.register(cb);

> }

> public void unregisterCallback(ITaskCallback cb) {

> if (cb != null) mCallbacks.unregister(cb);

> }

> };



> final RemoteCallbackList

&lt;ITaskCallback&gt;

 mCallbacks

> = new RemoteCallbackList

&lt;ITaskCallback&gt;

();

}



4. MyActivity.java

package com.cmcc.demo;



import android.app.Activity;

import android.content.ComponentName;

import android.content.Context;

import android.content.Intent;

import android.content.ServiceConnection;

import android.graphics.Color;

import android.os.Bundle;

import android.os.IBinder;

import android.os.RemoteException;

import android.util.Log;

import android.view.View;

import android.view.ViewGroup;

import android.view.View.OnClickListener;

import android.widget.AbsoluteLayout;

import android.widget.Button;

import android.widget.LinearLayout;

import android.widget.RelativeLayout;

import android.widget.TextView;



import java.io.BufferedReader;

import java.io.File;

import java.io.FileOutputStream;

import java.io.FileReader;

import java.io.PrintWriter;



public class MyActivity extends Activity {



> private Button btnOk;

> private Button btnCancel;



> @Override

> public void onCreate(Bundle icicle) {

> super.onCreate(icicle);



> setContentView(R.layout.test\_service);



> btnOk = (Button)findViewById(R.id.btn\_ok);


> btnCancel = (Button)findViewById(R.id.btn\_cancel);



> btnOk.setText("Start Service");

> btnCancel.setTag("Stop Service");



> btnOk.setOnClickListener(new OnClickListener() {

> public void onClick(View v) {

> onOkClick();

> }

> });



> btnCancel.setOnClickListener(new OnClickListener() {

> public void onClick(View v) {

> onCancelClick();

> }

> });

> }



> void onOkClick() {

> Bundle args = new Bundle();



> Intent intent = new Intent(this, MyService.class);

> intent.putExtras(args);



> //printf("send intent to start");



> //startService(intent);

> bindService(intent, mConnection, Context.BIND\_AUTO\_CREATE);

> startService(intent);

> }



> void onCancelClick() {

> Intent intent = new Intent(this, MyService.class);

> //printf("send intent to stop");



> unbindService(mConnection);

> //stopService(intent);

> }



> private void printf(String str) {

> Log.e("TAG", "###################------ " + str + "------");

> }



> ITaskBinder mService;



> private ServiceConnection mConnection = new ServiceConnection() {

> public void onServiceConnected(ComponentName className,

> IBinder service) {

> mService = ITaskBinder.Stub.asInterface(service);

> try {

> mService.registerCallback(mCallback);

> } catch (RemoteException e) {

> }



> }



> public void onServiceDisconnected(ComponentName className) {

> mService = null;

> }

> };



> private ITaskCallback mCallback = new ITaskCallback.Stub() {

> public void actionPerformed(int id) {

> printf("callback id=" + id);

> }

> };

}



5. xml文件(略)