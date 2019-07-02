### 学习日志 每次的题目为日期+姓名

### 2019/7/1 苑齐超
##### python以及一些库的安装
1. python 3.7.3 版本下载安装  https://www.python.org/downloads/windows/ ，注意要下载x86-64版本的，安装过程时勾选下方的  
   1.  [ ] Add Python3.7 to Path 
2. python安装完毕后相关库的安装 在命令提示符输入下列指令
*tip: 如果不想安装在系统盘可以参考网页   https://blog.csdn.net/mukvintt/article/details/80908951* 
<br/>
   1. Pillow（图像处理库）
        ``` bash
        pip install Pillow
        ```
    2. numpy（维度数组与矩阵运算）
        ``` bash
        pip install numpy
        ```
    3. OpenCV（跨平台计算机视觉库）
        ``` bash
        pip install opencv-python
        ```
    4. tensorflow（会有点慢） （因为硬件限制就不要装GPU版本了
        ``` bash
        pip install tensorflow 
        ```
        tip:安装完上述库之后，在import时出现了一些问题
            1. 报错 ```OSError: [WinError 193] %1 不是有效的 Win32 应用程序```
            解决方案：重装python3.7.3，并安装在系统盘.
            2. ```import tensorflow``` 后, 显示 ```Could not find 'cudart64_100.dll'```(yx安装使用无阻，可能是因为他装的是cpu版本, 我装的是tensorflow-gpu版本?)
            解决方案：安装 CUDA 和 cuDNN
            安装教程：https://blog.csdn.net/u010099080/article/details/53418159
            结果：在安装CUDA时又出现了种种问题，还险些把N卡驱动删了...故放弃gpu版本，改用cpu版本。暂定成功
