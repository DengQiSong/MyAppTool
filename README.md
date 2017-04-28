# CommonStructure
自己用的MVP架构的Android快速开发框架

* App  ——Application Activity Fragment Presenter等的顶级父类
* Config  ——API,常量表等
* Model  ——数据层(数据库操作也放在这一层)=
* Module ——将界面层以功能模块分配包。
* Utils    ——工具类集合
* Widget  ——各个可复用View集合

# 用到的开源库
#### 我们需要牢牢的记住：所有的第三方库能实现的功能，我们使用原生的API只要花时间和精力也能实现，但是可能会出现很多的bug而且会花费较多的时间和精力，而且性能也不一定很好，第三方的库会帮我们封装底层的一些代码，避免我们做重复多余易出错的事情，让我们专注于业务逻辑，所以学习任何一个第三方库都将是简单的，我们不应心生畏惧。
* [retrofit](http://square.github.io/retrofit) 一个针对Android和Java类型安全的http客户端,由Square公司开源的一个高质量高效率的http库
* [glide](https://github.com/bumptech/glide) Android的图像加载和缓存库，专注于平滑滚动,由谷歌在谷歌开发者论坛介绍的图片加载库，作者是bumptech
* [greendao](https://github.com/greenrobot/greenDAO) 是一个将对象映射到SQLite数据库中的轻量且快速的ORM解决方案,关于greenDAO的概念可以看[官网](http://greenrobot.org/greendao/)
* [butterknife](https://github.com/JakeWharton/butterknife) 专注于Android系统的View注入框架,可以减少大量的findViewById以及setOnClickListener代码,
# 编程思想

* 能复制就复制，节约时间避免出错
* 保留原本结构，简单上手容易调试
* 说明随手可得，不用上网或打开文档
* 增加必要注释，说明功能和使用方法
* 命名尽量规范，容易查找一看就懂
* 函数尽量嵌套，减少代码容易修改
* 最先参数判错，任意调用不会崩溃
* 代码模块分区，方便浏览容易查找
* 封装常用代码，方便使用降低耦合
* 回收多余占用，优化内存提高性能
* 分包结构合理，模块清晰浏览方便
* 多用工具和快捷键，增删改查快捷高效