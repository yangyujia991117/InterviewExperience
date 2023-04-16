# JAVA

## 并发编程

### 基础知识

- 进程：系统运行程序的基本单位
- 主线程：main函数所在的线程
- 通过ThreadMXBean类可以查看一个 Java 程序有哪些线程
- **一个 Java 程序的运行是 main 线程和多个其他线程同时运行**

- **并发和并行的区别**
  - 并发：两个及两个以上的作业在<font color="red">同一时间段内</font>执行
  - 并行：两个及两个以上的作业在<font color="red">同一时刻</font>执行
  - (关键点就在于是否<font color="red">同时</font>)

- **同步和异步的区别**
  - 同步：发出调用之后<font color="red">一定要得到结果了才能返回</font>，否则要一直等
  - 异步：发出调用后<font color="red">可以直接返回</font>，不用等待结果

### 线程的生命周期和状态

![](https://oss.javaguide.cn/github/javaguide/java/concurrent/640.png)

上图需要注意的：Thread.xxx()表示的可能是调用那个线程的xxx方法，例如threadA.start()就是调用threadA的start()方法，也可能是调用静态方法，例如Thread.sleep()，Object.xxx()表示调用某个资源对象的xxx方法，如lock.wait()表示此时让获取对象lock的那个线程释放锁和CPU，进入等待状态。

- **NEW**: 初始状态，线程被创建出来但没有被调用 `start()` 。

- **RUNNABLE**: 运行状态，线程被调用了 `start()`等待运行的状态。

- **BLOCKED** ：阻塞状态，需要等待锁释放。<font color="red">此时因拿不到锁而阻塞</font>

- **WAITING**：等待状态，表示该线程需要等待其他线程做出一些特定动作（通知或中断）。<font color="red">一般是拿到锁了但因为资源不足等原因进入等待（如果是通过wait进入等待则释放了锁，通过sleep进入则没有释放锁）</font>

- **TIME_WAITING**：超时等待状态，可以在指定的时间后自行返回而不是像 WAITING 那样一直等待。

- **TERMINATED**：终止状态，表示该线程已经运行完毕。

### 线程上下文切换

线程让出CPU的情况：

- 主动让出 CPU，比如调用了 `sleep()`, `wait()` 等。

- <font color="red">时间片用完</font>，因为操作系统要防止一个线程或者进程长时间占用 CPU 导致其他线程或者进程饿死。

- <font color="red">调用了阻塞类型的系统中断</font>，比如请求 IO，线程被阻塞。

- 被终止或结束运行

其中前三种会进行线程切换，需要保存当前线程的上下文，留给线程下次占用CPU时恢复现场，并加载下一个将要占用 CPU 的线程上下文。这就是所谓的 **上下文切换**。

### 线程死锁

**产生死锁的四个必要条件（只要发生死锁，这些条件必然成立，只要一个条件不成立那就不会发生死锁）：**

- **互斥条件：**该资源任意一个时刻只由一个线程占用。

- **请求与保持条件**：一个线程因请求资源而阻塞时，对已获得的资源保持不放。

- **不剥夺条件**：线程已获得的资源在未使用完之前不能被其他线程强行剥夺，只有自己使用完毕后才释放资源。

- **循环等待条件**：若干线程之间形成一种头尾相接的循环等待资源关系。<font color="red">每一个线程所需要的资源被上一个线程占用着</font>

**预防死锁——破坏死锁产生的必要条件：**

- **破坏互斥条件：**将资源改造为可共享的资源（如SPOOLing技术）
- **破坏请求与保持条件** ：<font color="red">静态分配策略：</font>一次性申请完所有的资源再开始执行，这样就不会有等待资源且自己还持有其他资源的情况了。

- **破坏不剥夺条件** ：占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源。

- **破坏循环等待条件** ：<font color="red">层次分配策略：</font>给系统中所有资源统一编号，规定线程必须按照从小到大的顺序申请资源，当<font color="red">拥有编号小的资源时才能申请编号大的资源，在释放了编号大的资源时才能释放编号小的资源</font>，且同类资源（即同一编号的资源）要一次性申请完。

**避免死锁：**

可以借助银行家算法等算法，得到申请资源的**安全序列**，每个线程都按这个安全序列申请资源，就可以进入**安全状态**

**检测死锁：**

操作系统中的每一刻时刻的**系统状态**都可以用**进程-资源分配图**来表示，用于**检测系统是否处于死锁状态**。

1. 如果进程-资源分配图中<font color="red">无环路</font>，则此时系统没有发生死锁

2. 如果进程-资源分配图中<font color="red">有环路</font><font color="red">，且每个资源类仅有一个资源</font>，则系统中已经发生了死锁。

3. 如果进程-资源分配图中<font color="red">有环路，且涉及到的资源类有多个资源</font>，此时系统未必会发生死锁。如果能在进程-资源分配图中找出一个 **既不阻塞又非独立的进程** ，<font color="red">该进程能够在有限的时间内归还占有的资源</font>，也就是把边给消除掉了，重复此过程，直到能在有限的时间内 **消除所有的边** ，则不会发生死锁，否则会发生死锁。(消除边的过程类似于 **拓扑排序**)

**死锁的解除方法：**

- 结束所有进程，重启操作系统
- 一次性撤销涉及死锁的所有进程，解除死锁后再重新运行这些进程
- 逐个撤销涉及死锁的进程，回收其资源直至死锁解除
- 抢占资源：从涉及死锁的一个或几个进程中抢占资源，把夺得的资源再分配给涉及死锁的进程直至死锁解除

### wait()和sleep()的区别

- wait()是Object类的本地方法，调用时用一个对象.wait()（这个对象是该线程占有的那个资源对象，释放的就是这个对象的对象锁）；sleep()是Thread类的静态本地方法，调用时直接Thread.sleep()
- <font color="red">wait()释放锁了，sleep()没有释放</font>
- `wait()` 方法被调用后，线程不会自动苏醒，需要别的线程调用同一个对象上的 `notify()`或者 `notifyAll()` 方法。`sleep()`方法执行完成后，线程会自动苏醒，或者也可以使用 `wait(long timeout)` 超时后线程会自动苏醒。
- `wait()` 通常被用于线程间交互/通信，`sleep()`通常被用于暂停执行。

### 可以直接调用thread.run()吗

不能。

new一个Thread然后调用该对象的start()，就会启动该线程并使线程进入了就绪状态，当分配到时间片后就可以开始运行了。 `start()` 会执行线程的相应准备工作，然后自动执行 `run()` 方法的内容，这是真正的多线程工作。 但是，<font color="red">直接执行 `run()` 方法，会把 `run()` 方法当成一个 main 线程下的普通方法去执行，并不会在某个线程中执行它，所以这并不是多线程工作</font>。

**总结： 调用 `start()` 方法方可启动线程并使线程进入就绪状态，直接执行 `run()` 方法的话不会以多线程的方式执行。**

### volatile关键字

- 保证可见性：通过内存屏障保证可见性。对volatile变量执行读操作时，会在读操作前加入一条load屏障指令，保证volatile变量每次读取数据的时候都强制从主内存读取；对volatile变量执行写操作时，会在写操作后加入一条store屏障指令，保证每次volatile修改之后强制将数据刷新会主内存。
- 保证有序性：对volatile变量进行操作时使用的内存屏障指令（LoadLoad屏障、StoreStore屏障、LoadStore屏障、StoreLoad屏障）能够禁止重排序。
- 不能保障原子性，可以用synchronized和ReentrantLock

### synchronized关键字

可保证可见性、有序性（前两个都是因为synchronized在获取锁时会使用monitorEnter指令，释放锁时使用monitorExit指令，这两个指令作用类似于volatile的内存屏障）、原子性

**使用**

- 修饰类中的某个方法——锁当前对象实例

  ```java
  synchronized void method() {
      //业务代码
  }
  ```

- 修饰类中的静态方法——锁这个类

  ```java
  synchronized static void method() {
      //业务代码
  }
  ```

- 修饰代码块——锁指定对象/类

  ```
  synchronized(object) 表示进入同步代码库前要获得 给定对象的锁。
  synchronized(类.class) 表示进入同步代码前要获得 给定 Class 的锁
  ```

**实现机制**

- 修饰语句块的时候：通过monitorEnter、monitorExit、锁计数器
- 修饰方法的时候：并没有 monitorenter 指令和 monitorexit指令，取得代之的确实是 ACC_SYNCHRONIZED标识，该标识指明了该方法是一个同步方法。JVM 通过该 `ACC_SYNCHRONIZED` 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用

但以上两种的本质都是<font color="red">对象监视器monitor的获取</font>

### ReentrantLock类

**和synchronized的联系区别：**

- 联系：两个都是可重入锁，线程可以再次获取自己的内部锁

- 区别：
  - synchronized 依赖于 JVM 而 ReentrantLock 依赖于 API
  - synchronized是非公平锁，ReentrantLock默认非公平，可以公平
  - ReentrantLock 比 synchronized 增加了一些高级功能：如等待可中断、可实现公平锁、可实现选择性通知

### ReentrantReadWriteLock类

可重入的读写锁，包括两把锁——一把是 `WriteLock` (写锁)，一把是 `ReadLock`（读锁） 。读锁是共享锁，写锁是独占锁。读锁可以被同时读，可以同时被多个线程持有，而写锁最多只能同时被一个线程持有。

可以实现读时不互斥，只要有写就互斥

### StampedLock类

是 JDK 1.8 引入的<font color="red">**性能更好的读写锁**</font>不可重入且不支持条件变量 `Conditon`。

### ThreadLocal类



# 操作系统

操作系统本质上是一个运行在计算机上的软件程序，用于管理计算机硬件和软件资源，屏蔽了硬件层的复杂性。

操作系统的内核是操作系统的核心部分，负责系统的内存管理、硬件设备的管理、文件系统的管理和应用程序的管理。

### 系统调用

- 用户态：用户运行的程序
- 系统态：可以访问计算机的任何资源，不受限制
- 在运行的用户程序中，凡是与系统态级别的资源有关的操作（文件管理、进程控制、内存管理等），必须通过系统调用方式向操作系统提出服务请求，并由操作系统代为完成。

### 进程和线程的区别

- 进程：资源分配的基本单位
- 线程：CPU调度的基本单位

一个进程中有多个线程，各进程是独立的，但同一进程中的线程极有可能会相互影响

线程共享区域：堆（存几乎所有对象实例及数组）、方法区（存已被虚拟机加载的类信息、字段信息、方法信息、常量、静态变量、即时编译器编译后的代码缓存等数据）

线程私有区域：程序计数器（存下一条要执行的指令的地址）、虚拟机栈（存一系列方法调用信息，由一个个栈帧组成，每个栈帧都有局部变量表、操作数栈、动态链接、返回地址）、本地方法栈（和虚拟机栈类似，但存的是native方法调用信息）

### 进程状态

和线程很像，但分为5种状态：

· **创建状态(new)** ：进程正在被创建，尚未到就绪状态。

· **就绪状态(ready)** ：进程已处于准备运行状态，即进程<font color="red"获得了除了处理器之外的一切所需资源，一旦得到处理器资源(处理器分配的时间片)即可运行。

· **运行状态(running)** ：进程正在处理器上上运行(单核 CPU 下任意时刻只有一个进程处于运行状态)。

· **阻塞状态(waiting)** ：又称为***\*等待状态\****，进程正在<font color="red">等待某一事件而暂停运行</font>，如等待某资源，或等待 IO 操作完成。<font color="red">即使处理器空闲，该进程也不能运行</font>。

· **结束状态(terminated)** ：进程正在从系统中消失。可能是进程正常结束或其他原因中断退出运行。

![img](https://yangyujia.oss-cn-beijing.aliyuncs.com/meituan4.png) 

 

### 进程间的通信方式

- **管道/匿名管道(Pipes)：**用于具有亲缘关系的父子进程或兄弟进程之间的通信。

- **有名管道：**可以用于本机任两个进程间通信，严格遵循<font color="red">先进先出</font>。

- **消息队列：**一个消息的链表，是一系列保存在<font color="red">内核</font>中消息的列表，<font color="red">可以实现消息的随机查询,消息不一定要以先进先出的次序读取,也可以按消息的类型读取</font>

  消息队列克服了信号承载信息量少，管道只能承载无格式字 节流以及缓冲区大小受限等缺点。

- **信号（signal)：**用于<font color="red">通知接收进程某个事件已经发生</font>

- **信号量(semaphores)**：常作为一种<font color="red">锁机制</font>，用于实现进程间的互斥与同步，而不是用于存储进程间通信数据

- **共享内存(shared memory)：**

- **套接字(sockets)：**主要用于在客户端和服务器之间通过<font color="red">网络</font>进行通信

### 线程间的同步方式

线程同步是指当多个共享关键资源的线程并发执行时，应该同步线程以避免关键资源的使用冲突。

- **互斥量(mutex)：**采用互斥对象机制，只有拥有互斥对象的线程才有访问公共资源的权限。因为<font color="red">互斥对象只有一个，所以可以保证公共资源不会被多个线程同时访问</font>。比如 Java 中的 synchronized 关键词和各种 Lock 都是这种机制。
- **信号量(Semaphore)** ：它<font color="red">允许同一时刻多个线程访问同一资源，但是需要控制同一时刻访问此资源的最大线程数量</font>。
- **事件(Event)** :Wait/Notify：通过<font color="red">通知操作</font>的方式来保持多线程同步，还可以方便的实现多线程优先级的比较操作。

### 进程的调度算法

- **先来先服务(FCFS)：**选择队列中最先到来的
- **短作业优先(SJF)：**选择队列中估计运行时间最短的进程分配CPU
- **时间片轮转调度：**每个进程被分配一个时间段，在对应时间段分配CPU
- **多级反馈队列调度：**设置若干个就绪队列，给每个队列赋予不同的优先级和时间片，且优先级越高赋予的时间片越小，进程进入队列等待时，首先进入优先级最高的队列末尾，队列内部按FCFS原则等待调度，如果该进程得到CPU后在时间片内没执行完那会进优先级低一级的队列，调度程序每次选择进程都是先到优先级最高的队列看，如果没有才到下一个队列……如果此时某个位于非最高优先级队列的进程正在运行，但来了一个新进程被放到了优先级较高的队列，那么须立即把正在运行的进程放回到它所在的队列的末尾，而把处理机分配给新到的高优先级进程。
- **优先级调度：**每个进程分配优先级，优先级高的先运行，如果有优先级相同则按FCFS

### 内存管理机制

- 连续分配管理方式：为一个程序分配一个连续的内存空间

  - **块式管理：**将内存分为几个固定大小的块，每个块只能包含一个进程，<font color="red">如果程序需要就给它分配一个块。</font>缺点：程序需要的内存空间可能很小，分配的块可能远大于它所需要的空间，会产生很多内存碎片。

- 非连续分配管理方式：允许一个程序使用的内存不连续

  - **页式管理：**把程序的逻辑空间划分为若干个大小相同的<font color="red">页</font>，把物理空间也划分为与逻辑空间中的页大小相同的若干个<font color="red">块</font>，都是从0开始编号，每个逻辑空间的页对应于一个物理的块，逻辑上连续的页可以对应物理上不连续的块，每个页的逻辑地址都由<font color="red">页号+（连接）页内位移量</font>组成，物理地址由<font color="red">块号+（连接）块内位移量</font>组成，两者的位移量是对应相等的，通过页表建立<font color="red">页号->物理块号</font>的映射，因此要想实现逻辑地址->物理地址即<font color="red">直接把逻辑地址前面的页号替换为页表中对应的块号即可</font>。

    好处：产生的碎片较小，不会超过一页的大小

    逻辑地址结构：

    ![](https://img-blog.csdn.net/20131031074824890?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

    逻辑地址到物理地址的映射：

    ![](https://pic3.zhimg.com/80/v2-b6b5d208aa77eac16e298aa5678e1f9e_720w.webp)

    ![](https://img-blog.csdn.net/20131031074902312?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

  - **段式管理：**页式管理虽然提高了内存利用率，但是页式管理其中的页并无任何实际意义。 段式管理把主存分为一段段的，段是**有实际意义**的，每个段定义了一组逻辑信息，例如,有主程序段 MAIN、子程序段 X、数据段 D 及栈段 S 等。<font color="red">内存空间为每个段分配一个连续的空间， 这个空间的大小**由该段大小决定**</font>。逻辑地址是<font color="red">段号+（连接）段内地址</font>，物理地址是<font color="red">基址+（加号，不是连接）段内地址</font>。通过<font color="red">段表</font>查询段号的对应物理段基址。

    逻辑地址结构：

    ![](https://img-blog.csdn.net/20131031091106312?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

    逻辑地址到物理地址的映射：

    ![](https://pic1.zhimg.com/80/v2-302603ba160ebe313e1fa05d1433d828_720w.webp)

    ![](https://img-blog.csdn.net/20131031091138406?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

  - **段页式管理：**<font color="red">程序先分段，每个段内部再分页</font>，通过段表+页表共同实现逻辑地址->物理地址的映射

    逻辑地址结构：

    ![](https://img-blog.csdn.net/20131031091956234?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

    逻辑地址到物理地址的映射：

    ![](https://img-blog.csdn.net/20131031092005921?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

    ![](https://img-blog.csdn.net/20131031092012156?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZzM3OTI3NTYxNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

    

### 快表

为了提高逻辑地址到物理地址的转换速度，在页表方案基础之上引入了快表，快表中存了页表的一部分货全部内容，页表位于内存中，快表位于<font color="red">**cache**</font>中，可加速地址转换速率。

使用快表之后的地址转换流程是这样的：

1. 根据虚拟地址中的页号查快表；
2. 如果该页在快表中，直接从<font color="red">快表</font>中读取相应的物理地址；
3. 如果该页不在快表中，就访问内存中的<font color="red">页表</font>，再从页表中得到物理地址，同时将页表中的该映射表项<font color="red">添加到快表中</font>；
4. 当快表填满后，又要登记新页时，就<font color="red">按照一定的淘汰策略淘汰掉快表中的一个页</font>。

### 多级页表

当页很多时，页表会很大，因此可以采取一种时间换空间的策略——建立<font color="red">多级页表</font>

把逻辑地址中的页号部分拆分成四段，分别是4级、3级……1级索引，根据4级索引找到4级页表中的条目（存对应3级页表的地址，<font color="orange">每一级页表都可能有很多个</font>），然后根据这一条目找到对应的3级页表，再根据3级索引找到3级页表中对应的条目，找到对应的2级页表……一直到找到最终的1级页表中的条目，也就是需要的块号

![](https://img-blog.csdnimg.cn/ff6881282f2b4e409f1b611c6542ddfb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAT2NlYW4mJlN0YXI=,size_20,color_FFFFFF,t_70,g_se,x_16)

通过这种策略，页表总体积减少了，但查询的次数增多——<font color="red">时间换空间</font>

### CPU寻址、虚拟地址

现代处理器使用的是一种称为 **虚拟寻址(Virtual Addressing)** 的寻址方式。**使用虚拟寻址，CPU 需要将虚拟地址翻译成物理地址，这样才能访问到真实的物理内存。** 实际上完成虚拟地址转换为物理地址的硬件是 CPU 中含有一个被称为 **内存管理单元（Memory Management Unit, MMU）** 的硬件。

虚拟地址空间的意义：

- 直接暴露物理地址不安全，
- 直接暴露物理地址不利于同时运行多个程序（例如：微信在运行的时候给内存地址 1xxx 赋值后，QQ 音乐也同样给内存地址 1xxx 赋值，那么 QQ 音乐对内存的赋值就会覆盖微信之前所赋的值，这就造成微信这个程序会崩溃。）
- 使用虚拟地址使得程序可以使用一系列相邻的虚拟地址来访问物理内存中不相邻的大内存缓冲区
- <font color="red">不同进程的虚拟地址彼此隔离</font>，虽然两个进执行到某一步时使用的虚拟地址相同，但物理地址不同，进程不需要理会其他进程使用的地址。

### 虚拟内存（和虚拟存储器是同一个概念）

- 可以让程序拥有超过系统物理内存大小的可用内存空间（实际上有一部分是放在磁盘中，不在实际内存里）
- 为每个进程提供了一个一致的、私有的地址空间，它<font color="red">让每个进程产生了一种自己在独享主存的错觉</font>

#### 虚拟内存的局部性原理

局部性原理是虚拟内存技术的基础，<font color="red">正是因为程序运行具有局部性原理，才可以只装入部分程序到内存就开始运行。</font>

- **时间局部性：**如果程序中的某条指令一旦执行，不久以后该指令可能再次执行；如果某数据被访问过，不久以后该数据可能再次被访问。
- **空间局部性：**一旦程序访问了某个存储单元，在不久之后，其附近的存储单元也将被访问

**利用时间局部性：**将近来使用的指令和数据保存到cache中

**利用空间局部性**：使用较大的cache，将近来使用的存储单元附近的存储单元也保存在cache中

#### 虚拟内存的技术实现

- **请求分页存储管理：**<font color="red">虚拟内存+页式管理：</font>在作业开始运行之前，仅装入当前要执行的部分段即可运行。假如在作业运行的过程中发现要访问的页面不在内存，则会有<font color="red">缺页中断</font>，由处理器通知操作系统按照对应的<font color="red">页面置换算法</font>将相应的页面调入到主存，同时操作系统也可以将暂时不用的页面置换到外存中。
- **请求分段存储管理：**<font color="red">虚拟内存+段式管理：</font>在作业开始运行之前，仅装入当前要执行的部分段即可运行；在执行过程中，可使用请求调入中断<font color="red">动态装入要访问但又不在内存的程序段</font>；当内存空间已满，而又需要装入新的段时，根据<font color="red">段置换功能</font>适当调出某个段，以便腾出空间而装入新的段。
- **请求段页式存储管理：**

#### 页面置换算法

当发生缺页中断时，需要把页面里的一个页置换出来放进新的

- **先进先出算法（FIFO）：**淘汰<font color="red">最先进入内存</font>的页面

- **最近最久未使用算法（LRU）：**淘汰<font color="red">最久没有被访问</font>的页面

- **最少使用算法（LFU）：**淘汰<font color="red">使用次数最少</font>的页面

- **最佳页面置换算法（OPT）：**理想是替换掉永不再使用的或最长时间内不再被访问的页面，但由于无法预估所以无法实现，只是一个理想中的算法，实际上使用上面三个。

  

# Spring

### Spring中的设计模式

### Spring、 Spring Boot和Spring MVC的联系和区别

区别：

- **Spring**是一个框架，包含多个功能模块，如Spring-Core（主要提供 IoC 依赖注入功能的支持，spring中其他模块的功能实现基本都需要依赖该模块） 

- **Spring MVC**是Spring中的一个模块，主要赋予Spring快速构建MVC架构的Web程序的能力，MVC包括(model)、视图(view)、控制器(controller)，核心思想是通过将显示、业务逻辑、数据分离来组织代码。

- **Spring Boot**是spring的完善和扩展，目的是简化配置，例如因为传统spring需要写大量xml等来配置依赖，比较麻烦，而springboot只需要通过一些注解；又如在测试的时候，在传统spring中，需要在maven依赖里添加Spring Test、JUnit 、Mockito、Hamcrest依赖，而在spring boot中只需要添加 spring-boot-starter-test 依赖项来自动包含这些库。

  使用传统Spring创建web应用程序所需的最小依赖项：

  ```xml
  <dependency> 
      <groupId>org.springframework</groupId> 
      <artifactId>spring-web</artifactId> 
      <version>5.1.0.RELEASE</version> 
  </dependency> 
  <dependency> 
      <groupId>org.springframework</groupId> 
      <artifactId>spring-webmvc</artifactId> 
      <version>5.1.0.RELEASE</version> 
  </dependency>
  ```

  使用Springboot只需一个依赖：

  ```xml
  <dependency> 
      <groupId>org.springframework.boot</groupId> 
      <artifactId>spring-boot-starter-web</artifactId> 
      <version>2.0.6.RELEASE</version> 
  </dependency>
  ```

联系：

在构建一个web应用程序时，可以使用spring MVC提供的分层框架，然后通过springboot来简化配置



# 设计模式

### 工厂模式（类创建型）

原理：定义一个工厂类Factory，以及要创建的物品的接口，还有所有物品类型的实现类，当要创建具体某个类的实例对象时，统一使用工厂类进行创建，通过传递一个参数的形式表明要创建的是哪一个类的对象

好处：不用为每个具体类都弄一个工厂，而是通过同一个工厂类进行对象的创建。

![](https://www.runoob.com/wp-content/uploads/2014/08/AB6B814A-0B09-4863-93D6-1E22D6B07FF8.jpg)

工厂类代码：

```java
public class ShapeFactory {
    
   //根据传入的参数决定实例化哪个类的对象，也可以将该方法弄成static静态方法，这样不用创建工厂类的实例就能调用了
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }        
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      return null;
   }
}
```

客户端代码：

```java
public class FactoryPatternDemo {
 
   public static void main(String[] args) {
      ShapeFactory shapeFactory = new ShapeFactory();
      //创建Circle对象，并调用它的 draw 方法
      Shape shape1 = shapeFactory.getShape("CIRCLE");
      shape1.draw();
      //创建Rectangle对象，并调用它的 draw 方法
      Shape shape2 = shapeFactory.getShape("RECTANGLE");
      shape2.draw();
      //创建Square对象，并调用它的 draw 方法
      Shape shape3 = shapeFactory.getShape("SQUARE");
      //调用 Square 的 draw 方法
      shape3.draw();
   }
}
```

### 抽象工厂模式（对象创建型）

原理：工厂模式通常只有一类产品族，所以一个工厂就可以了，抽象工厂模式通常有多类产品族，每个产品族都有一个对应的工厂，然后有一个大的抽象工厂来创建所有产品族的工厂，从而创建所有产品族下面的产品。

适用情况：当需要创建一个产品族时

![image-20230409164759793](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409164759793.png)

如上，海尔具体工厂和TCL具体工厂都继承于EFactory这个抽象工厂类，这两个具体工厂可以生产对应产品族的产品，例如海尔具体工厂可以生产海尔电视和海尔空调，TCL抽象工厂可以生产TCL电视和TCL空调，客户端代码如下：

```java
//创建海尔具体工厂并通过该工厂创建海尔空调和电视：
EFactory haierFactory=new HaierFactory();
Television haierTelevision=haierFactory.produceTelevision();
AirConditioner haierAirConditioner=haierFactory.produceAirConditioner();
//创建TCL具体工厂并通过该工厂创建海尔空调和电视：
EFactory tclFactory=new TCLFactory();
Television tclTelevision=tclFactory.produceTelevision();
AirConditioner tclAirConditioner=tclFactory.produceAirConditioner();
```

### 单例模式（对象创建型）

原理：保证一个类仅有一个实例，并提供一个访问它的全局访问点。

适用情况：当需要某个类在全局只有一个实例时

<font color="red">一般来说单例类中的方法都弄成static静态方法，这样不需要实例化对象</font>

![](https://www.runoob.com/wp-content/uploads/2014/08/62576915-36E0-4B67-B078-704699CA980A.jpg)

单例类SingObject代码：

```java
public class SingleObject {
 
   //创建 SingleObject 的一个对象
   private static SingleObject instance = new SingleObject();
 
   //让构造函数为 private，这样该类就不会被实例化
   private SingleObject(){}
 
   //获取唯一可用的对象
   public static SingleObject getInstance(){
      return instance;
   }
 
   public void showMessage(){
      System.out.println("Hello World!");
   }
}
```

客户端代码：

```java
public class SingletonPatternDemo {
   public static void main(String[] args) {
 	//获取唯一可用的对象
      SingleObject object = SingleObject.getInstance();
      //显示消息
      object.showMessage();
   }
}
```

### 模板方法模式（类行为型）

原理：<font color="red">其实就是最基本的继承结构</font>。定义一个抽象父类和一个具体子类，抽象父类中定义一个<font color="red">模板方法</font>，这个模板方法有若干个<font color="red">步骤</font>，这些步骤已确定实现的可写成具体方法，未确定的可写成抽象方法，交给子类实现，也可以是钩子方法（两种钩子方法——第一种是父类定义的是空方法或某个默认的实现，看子类是否覆盖，若覆盖就按子类的执行，否则按父类默认的执行；第二种是在某个方法执行之前先调用一下钩子方法看是否返回true，返回true再执行下面的，否则不执行，父类中可以默认返回true或fals，子类也可以覆盖）

![image-20230409212637010](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409212637010.png)



### 观察者模式（对象行为型）

原理：定义对象间的一种一对多依赖关系，每当被观察目标的状态（属性或数据）发生改变时，通知所有观察者。

![image-20230409171838118](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409171838118.png)

注意：

- 被观察者抽象类持有观察者接口，因为要通过notify通知所有观察者
- 观察者具体类持有被观察者具体类，因为要通过getState知道该被观察者现在的状态

### 适配器模式（类结构型/对象结构型）

原理：定义一个适配器类(Adapter)，这个适配器类可以提供所有客户端所需要的接口，当客户端调用适配器类的方法时，适配器类可以调用合适的适配者类(Adaptee)的方法，而这个过程对客户端透明。

适用情况：通过适配器，可以调用适配者里很多用户原本不能调用的接口

分为对象适配器、类适配器两种

- 对象适配器：可以适配很多个adaptee

![image-20230409203839792](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409203839792.png)

对象适配器代码：

```java
public class Adapter extends Target{
    private Adaptee adaptee;
    public Adapter(Adaptee adaptee){
        this.adaptee=adaptee;
    }
    public void request(){
        adaptee.specificRequest();
    }
}
```

- 类适配器：Adapter类继承Adaptee类，同时在Adapter中可以改变specificRequest()的具体代码，做更大的变化

![image-20230409204808102](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409204808102.png)

类适配器代码：

```java
public class Adapter extends Adaptee implements Target{
    public void request(){
        specificRequest();
    }
}
```

### 装饰模式（对象结构型）

原理：可以不更改原有类，就能够动态地给原有类的对象添加一些额外的属性和行为，方法是创建一个装饰类来包装原有类，该装饰类继承于原有类，但添加了新的属性或行为

适用情况：不想改变原有类但想为其添加新的属性或行为时

![image-20230409210639145](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409210639145.png)

### 代理模式（对象结构型）

原理：引入一个代理对象来实现对真实对象的操作，或者将代理对象作为真实对象的替身使用。代理类和真实对象类都继承于同一个类，因此有部分相同的方法，代理类中的方法可以比真实对象类多一些或少一些，但新增的方法客户端是不能调用的

适用情况：当无法访问真实对象时（例如真实对象在远程主机上）

![image-20230409211459042](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230409211459042.png)



