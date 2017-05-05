# ScutKicking

## this is written by user0308

### bug 清单

* bug 1: 运行时突然按返回键或home键,短时间内切换回游戏界面时,球的数目会增多

    bug 分析: 游戏运行过程中,除了主线程外,继承自SurfaceView的 MainView 中有一线程mThread在运行,任务是更新游戏界面,画出小球,建筑,人物,用户按下Back键时,MainView经历过程:surfaceDestroyed() -- MainView构造函数 -- surfaceCreated() -- surfaceChanged() ;用户按下Home键时,MainView经历过程:surfaceDestroyed() -- surfaceCreate() -- surfaceChanged(),目前暂时认为在destroyed中将mIsRunning设置为false,此时在run函数中,死循环停止,界面上的球静止不动,接着用户短时间内重开App,进入surfaceCreated(),又创建了一个新线程,将mThread指向该线程,而原来线程则为匿名线程,此时可能有三条线程:主线程,匿名线程,mThread线程,匿名线程执行完循环中从上次停止的地方到循环结束之后就不再执行了(猜测是这样的,因为下次发球的时候还是发两个球,如果匿名线程和mThread都在运行的话,应该发四个球),匿名线程结束,程序又恢复两条线程运行.

  bug 验证方法:在running方法中Log一下当前线程pid(未验证)

  bug 解决办法:暂无
