# 工程项目简绍-手势识别控制
****
## 项目主要实现功能：
&ensp;&ensp;&ensp;&ensp;使用Java、openCV、JavaFX、JNA、Windows API实现通过电脑摄像头捕捉识别手势来控制电脑执行相应的动作：比如电脑窗口的切换、浏览器的上下滑动和翻页、浏览器的标签切换，还有一些比较高级的功能构想，但目前对Windows API不够了解，初步只想实现一些简单的功能试试，后期如果有可能，便会增加一些更加实用性的功能

## 项目实现构想
### 1、相应的图形界面
&ensp;&ensp;&ensp;&ensp;本次实用JavaFX来实现这个项目，相比较Java Swing，这个比较好看，这个还有可定制行，后期可以自己进行美化操作，好像是用CSS的，有相应的教程（这个完全没有难度，这个是可视化操作的），下面是一个简单的入门使用教程（很棒的一个小应用实战教程）

[http://code.makery.ch/library/javafx-8-tutorial/zh-cn/](http://code.makery.ch/library/javafx-8-tutorial/zh-cn/)

### 2、手在视频中的识别
&ensp;&ensp;&ensp;&ensp;这个使用openCV来识别，感觉这个项目应该是一个后台程序，就不使用机器学习方面的东西，而且用了效果也不一定好（后面不行也可以试试）。这个项目里打算使用的是背景差分和颜色匹配来识别视频中的手，感觉应该是够用的

### 3、手势的识别
&ensp;&ensp;&ensp;&ensp;对手势做出相应的识别，这个是要在上一步准确的识别出手的基础上才能更好的实现，这部分的代码还没试过，应该是有些复杂的

### 4、对Windows的相应控制
&ensp;&ensp;&ensp;&ensp;这个资料就比较少了，现在只是知道一些简单的使用，这步需要用到Windows API和JNA，可能这个需要大量的尝试吧，但实现一些简单的功能应该不是问题。

### 5、相应的参考资料
[javaFX 入门教程](http://code.makery.ch/library/javafx-8-tutorial/zh-cn/)

[opencv视觉编程基础  访问密码 16fc](https://yunpan.cn/cvVg76gyqcRDn)

[jna操作手册.pdf  访问密码 b11e](https://yunpan.cn/cvVsRVm7g5JGx)

[Windows API 函数参考手册  访问密码 abea](https://yunpan.cn/cvVsqwS7EJv4v)

[opencv-java-tutorials  访问密码 e572](https://yunpan.cn/cvVs2UD2ccEM4)

[一个手势玩愤怒的小鸟的程序](https://github.com/jaysnanavati/HandGestureRecognition-OpenCV)

[一个简单手识别的参考代码](https://github.com/jaysnanavati/HandGestureRecognition-OpenCV)

[一个手代替鼠标的参考代码](https://github.com/siam1251/HandGestureRecognition)

[一个比较吊的手势识别代码](https://github.com/Xonxt/htw-forschungsproject)

[前面的比较吊的手势识别YouTube视频](https://www.youtube.com/watch?v=5VT2HLc7zVg&index=26&list=PLijfWBfFEZLgF9eCl1HeudsCi2_e7D5Yp)

[Java中调用Windows API的方法](http://qujianfeng.iteye.com/blog/1258363)

[在IntelliJ IDEA 13中配置OpenCV的Java开发环境](http://www.cnblogs.com/yezhang/p/OpenCV_Java_IDEA.html)


### 6、开发工具
[ideaIC-2016.2.4  访问密码 62f2](https://yunpan.cn/cvVsaB7VbCz9d)

[opencv-3.0.0  访问密码 5e40](https://yunpan.cn/cvVAyqEZEDkRc)

[翻墙教程](https://github.com/lw1243925457/Deity_Way/blob/master/profession/common/XX-Net%E8%AF%A6%E7%BB%86%E5%9B%BE%E6%96%87%E6%95%99%E7%A8%8B.md)
