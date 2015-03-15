# the state of art #
  * JME is widely supported by existing cell phones.
  * There are thousands of MIDlets in the market, most of them are games.
  * Google established OHA and bypassed JME in Android software stack.
  * Sun didn't join OHA.
  * The first Android phone, HTC Dream, will NOT support JME and MIDlet applications.
  * Google organized a coding challenge program for Android, top 50 winners shared the $10,000,000 awards.

> However, I didn't see too many good games among the top 50. So we believe MIDlet will not die on cell phones, and it will be a good supplement for Android.

# cuby overview #
  * cuby is an Android implementation of MIDP, it enables MIDlets for Android.


# cuby killer features #

## fully open source with high quality ##
  * cuby is specially suitable for the customers who need full source code （rumor said that 中兴、华为等厂商很有这种偏好）
  * by 20080917, the cuby development team have more than 16 man years JME development experiences
  * we expect that we will increase to one hundred man years in near future.
  * people may access their blog for more information.

## Pure Android look & feel ##
  * cuby will use Android widget to implement midp high level ui, e.g. use android check box to implement midp check box. So cuby will have 100% Android look & feel. Advanced features, such as theme, will all be supported easily.

## low ROM requirement ##
  * No native code, native code are usually 10 times bigger than dalvik dex or JVM byte code
  * No cldc, no duplicated java.lang package. Cuby will sit on Dalvik VM, which is JSE alike.

## low RAM requirement ##
  * being a pure Android implement, cuby supports Android memory management model.
  * background cuby process will be safely killed when the free memory is low, and recover when cuby goes back to foreground.
  * single vm, single javaheap, single GC, using cuby solution, the phone RAM management is much more efficient, safer and easier than "Dual JVM" solutions.

## Seamless system integration with android ##
  * cuby is specially designed for Android, it supports:
  * seamless integration with Android browser
> > e.g. easy midlet OTA download MIDlet through browser
> > e.g. receive browser's "Open\_Jad\_Intent"
  * seamless integration with Android "Application Management System"
> > e.g., midlet apps can have an icon/shortcut on android desktop, as other applications/widgets did.

## low license cost, low development risk, low porting cost, low maintain cost ##
  * At this moment, cuby is free.
  * android does not officially support native app development so far, so pure java cuby implementation reduced the risk on system design
  * cuby is 100% implemented on Android API, we didn't see any porting effort for using cuby so far
  * it is possible to provide cuby as a binary emulator product (like neogeo).
  * pure java solution is easy to be upgraded after manufacture, in order to add new features or apply bug fixes.
