# CommonStructure
自己用的项目公共结构块
默认采用MVP模式

App  ——Application Activity Fragment Presenter等的顶级父类

Config  ——API,常量表等

Model  ——数据层(数据库操作也放在这一层)

Module ——将界面层以功能模块分配包。

Utils    ——工具类集合

Widget  ——各个可复用View集合

我们需要牢牢的记住：所有的第三方库能实现的功能，我们使用原生的API只要花时间和精力也能实现，但是可能会出现很多的bug而且会花费较多的时间和精力，而且性能也不一定很好，第三方的库会帮我们封装底层的一些代码，避免我们做重复多余易出错的事情，让我们专注于业务逻辑，所以学习任何一个第三方库都将是简单的，我们不应心生畏惧。

#2017-3-6
greendao数据库操作的例子

图片加载框架glide的封装

网络请求retrofit的封装

convenientbanner实现benner效果

ImageSelectView图片选择

调用系统DownloadManager实现后台下载APK

6.0以上动态权限获取的封装
