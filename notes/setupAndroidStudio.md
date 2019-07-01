# Android Studio 开发环境

## 安装

在[官网](https://developer.android.google.cn/studio)下载安装包并安装

会遇到一个问题：因为没有proxy，install SDK的时候会报错 'unable to access android sdk add-on list'

一个解决办法是在安装的时候先选择'Cancel',安装完成后新建project，这个时候会有另一个错误 'SDK tool Directory is missing'。然后手动下载并安装了[SDK Tool](http://tools.android-studio.org/index.php/sdk)。安装完成后就可以正常新建project了。

## 创建project

根据[官网教程](https://developer.android.google.cn/training/basics/firstapp)新建项目并运行

### 运行

App可以在真实的设备（手机）上运行，也可以在模拟器上运行。

在模拟器上运行

1. 在 Android Studio 中，点击 Project 窗口中的 app 模块，然后选择 Run > Run（或点击工具栏中的 Run ）。
2. 在 Select Deployment Target 窗口中，点击 Create New Virtual Device。
3. 在 Select Hardware 屏幕中，选择手机设备（如 Pixel），然后点击 Next。
4. 在 System Image 屏幕中，选择具有最高 API 级别的版本。如果您未安装该版本，屏幕上将显示 Download 链接，因此，请点击该链接并完成下载。
5. 点击 Next。
6. 在 Android Virtual Device (AVD) 屏幕上，保留所有设置不变，然后点击 Finish。
7. 返回到 Select Deployment Target 对话框中，选择您刚刚创建的设备，然后点击 OK。

在点击运行时提示没有安装HAXM，根据提示可以自动安装

> Intel HAXM (Hardware Accelerated Execution Manager) 使用基于 Intel(R) Virtualization Technology (VT) 的硬件加速， 因此需要 CPU 支持 VT ， 而且仅限于 Intel CPU
> Intel HAXM 技术为 Android 模拟器加速

之后就可以用模拟器运行了，不过启动的速度还是很慢。

需要下载安装很多SDK的内容，需要磁盘有足够的空间。目前进行尝试时SDK的安装目录在C:盘，但是C:盘容量不是很大，可能接下来会把路径换到D:盘重新安装一遍。

## 简单的尝试

参考[教程](https://blog.csdn.net/herr_kun/article/details/84146462)学习使用Android Studio，完成很简单的输入、按键和显示的功能

## 一些好看的UI框架

UI界面开发时可以使用一些框架使界面更好看

具体使用方法点击[网页链接](https://www.runoob.com/w3cnote/android-ui-framework.html)

+ 导航栏可以参考Yalantis的一些框架[github](https://github.com/Yalantis/Context-Menu.Android)

但是需要注意整体风格一致，感觉可以参考INSTAGRAM的风格[github](https://github.com/frogermcs/InstaMaterial/tree/Post-8)

## [应用架构指南](https://developer.android.google.cn/jetpack/docs/guide)学习

简单记录一些需要注意的点

+ 分离关注点
+ 通过模型驱动界面
  
  数据持久化。因为 1. 如果 Android 操作系统销毁应用以释放资源，用户不会丢失数据。 2. 当网络连接不稳定或不可用时，应用会继续工作。

  ViewModel, LiveData架构组件可以用来获取数据，通知界面更新数据

+ 用依赖注入的方式管理组件之间的依赖关系 [Dagger 2](https://dagger.dev/)
+ 分层
+ [测试](https://developer.android.google.cn/training/testing/unit-testing/instrumented-unit-tests.html)

  + 界面和交互

    创建此测试的最佳方法是使用 [Espresso](https://developer.android.google.cn/training/testing/ui-testing/espresso-testing.html) 库

  + 其他功能用JUnit测试
