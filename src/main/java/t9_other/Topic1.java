package t9_other;

public class Topic1 {
    /*
        0.了解编码问题
            定长编码
                 ASCII码，Base64

            变长编码
                 utf-8等
                 utf-8：
                    英文字符兼容ASCII码，支持中文字符

            为什么有变长编码？

            分享一个乱码场景

            霍夫曼编码  https://www.cnblogs.com/kubixuesheng/p/4397798.html

        1.网络
            OSI七层网络模型
            TCP/IP四层结构
                链路层：封装帧，解封帧（MTU）；
                网络层：将数据包发送给目的主机
                传输层：解决数据传输中的问题（正确性、有序性、拥塞问题）
                应用层：针对不同类型的应用定义的协议，如面向web的http协议、面向文件传输的ftp协议、面向邮件服务的pop3协议等

            （1）TCP
                是一种面向连接的、可靠的、基于字节流的传输层通信协议。

                https://www.jianshu.com/p/e7f45779008a
                为什么要三次握手？
                    A发起请求到B，建立TCP连接
                    1次：A不知道B是否可以建立连接
                    2次：B不知道A的连接请求是否是有效的（可能是过期的）
                    3次：刚好
                    4次：浪费资源

                为什么要四次挥手？
                    TCP是全双工的通信协议，当A发完数据时，A会要求关闭，B接收到后告知A它知道A关闭了；这时TCP连接还没有完全关闭，
                    B发完数据后，会要求关闭，A接收到后告知B它知道B关闭了；这时这个TCP连接才全部关闭。

                关于TIME_AWAIT：
                    https://blog.csdn.net/u013616945/article/details/77510925
                    为什么要有TIME_WAIT这个状态？
                        1.保证让迟来的TCP报文段有足够的时间被识别并丢弃
                        2.可靠的终止TCP连接
                    大量TIME_WAIT何时出现，怎么解决？
                        大量高并发短连接，会造成大量TIME_WAIT，进而使服务器拒绝一部分客户请求。
                        首先服务器可以设置SO_REUSEADDR套接字选项来通知内核，如果端口忙，但TCP连接位于TIME_WAIT状态时可以重用端口。


                有余力，可以学习下TCP协议的状态图，以及拥塞控制算法。

            （2）UDP
                UDP提供了无连接通信，且不对传送数据包进行可靠性保证，适合于一次传输少量数据，可以支持广播，UDP传输的可靠性由应用层负责（QQ）。


                tcp传输的可靠有序，udp不可靠无序。

            （3）HTTP
                Hyper Text Transfer Protocol（超文本传输协议）
                基于TCP协议，无连接，无状态
                请求（Request）
                    一个HTTP请求报文由
                        请求行（request line）、
                        请求头部（header）、
                        空行、
                        请求数据，
                    4个部分组成。

                响应（Response）
                    HTTP响应也由三个部分组成，分别是：状态行、消息报头、响应正文。

                get和post区别
                    1.GET提交的数据会放在URL之后，以?分割URL和传输数据，参数之间以&相连.
                      POST方法是把提交的数据放在HTTP包的Body中.
                    2.GET提交的数据大小有限制（因为浏览器对URL的长度有限制），而POST方法提交的数据没有限制.
                    3.GET只允许ASCII 字符，而POST允许二进制编码
                    4.GET方式提交数据，会带来安全问题，比如一个登录页面，通过GET方式提交数据时，用户名和密码将出现在URL上，
                      POST相对安全一些。
                    5.GET方法的响应可以被缓存

                session和cookie区别：
                    1、cookie数据存放在客户的浏览器上，session数据放在服务器上。
                    2、cookie不是很安全，别人可以分析存放在本地的COOKIE并进行COOKIE欺骗
                       考虑到安全应当使用session。
                    3、session会在一定时间内保存在服务器上。当访问增多，会比较占用你服务器的性能
                       考虑到减轻服务器性能方面，应当使用COOKIE。
                    4、单个cookie保存的数据不能超过4K，很多浏览器都限制一个站点最多保存20个cookie。

                    建议：
                       将登陆信息等重要信息存放为SESSION
                       其他信息如果需要保留，可以放在COOKIE中
                       Session一般借助cookie机制来实现


                状态码：
                    2XX，200成功
                    301、302，
                    4XX，400客户端请求格式错误，404url+方法不存在
                    5XX，500服务端报错
                https://www.cnblogs.com/ranyonsue/p/5984001.html
                https://www.cnblogs.com/lzq198754/p/5780310.html

           （4）一个HTTP请求的全过程（越详细越好，体现你对整个网络协议栈的理解）

                https://blog.csdn.net/u014600626/article/details/78720763

        2.操作系统向光
            虚拟内存
                https://blog.csdn.net/qq_30137611/article/details/66478426
                https://www.cnblogs.com/shenckicc/p/6884921.html
                http://blog.csdn.net/smilesundream/article/details/70148878（三种方式优缺点）
                涉及知识点：
                    分段、分页、段页式（linux等操作系统采用的）

                    分页：大小固定，没有外部碎片，会有内部碎片，页长与程序的逻辑大小不相关，对内存的使用造成困难；
                    分段：大小按程序需要分配，容易产生大量外部碎片，造成内存利用率不好；
                    段页式：结合前两者，每个程序会分配一个段表，再根据段表找到页，结合了前者的优点，是现在计算机常用的内存管理方式。

                内存管理算法（页面置换算法）
                文件系统（ext2，ext3）

                局部性原理：
                    时间局部性原理、空间局部性原理

            堆&栈（不是数据结构中的那个）
              堆：
                堆内存的使用特点是不连续和无序的，分配和释放成本较高堆则是C/C++函数库提供的（Java则由垃圾回收器控制，其实程序员也是能影响的）。
                堆在进程内是由多个线程共同使用的，堆存储的内容一般也伴随着程序的整个周期。
                例如为了分配一块内存，库函数会按照一定的算法在堆内存中搜索可用的足够大小的空间，使用完后也要手动收回。
                然后进行返回。显然，堆的效率比栈要低得多。

              栈：
                栈是为一个线程配备（栈是线程独享的），为这个线程的函数调用服务的。
                栈是一个连续的空间。
                用于存放返回地址，函数参数，临时变量而用，记录多层调用，是一个有明确的“后入先出”规则的内存 buffer。
                而且是供程序员“隐式”的使用，分配和回收都是“自动”的，程序员对栈内存的使用缺少自由和可控性。
                栈的实现一般是高速缓存或者寄存器（速度快）。

              比较：
                1.用途
                2.与进程线程的关系
                3.连续性
                4.分配回收是否需要程序员干预
                5.速度
                。。。


，          进程&线程
                t3_concurrent_java/Topic1.java:5
                前面提过了

            进程间通信的方式
                t3_concurrent_java/Topic1.java:5
                前面提过了

            IO模型
                https://www.cnblogs.com/findumars/p/6361627.html
                blocking IO（阻塞IO）
                nonblocking IO（非阻塞IO）    ——————————  Java NIO
                IO multiplexing（IO多路复用）
                    进阶问题（可以不看）：selec、poll、epoll（LT、ET）
                signal driven IO（信号驱动IO）
                asynchronous IO（异步IO）    ———————————  Java AIO

            死锁
                概念：多个并发进程因争夺系统资源而产生相互等待的现象。
                死锁的4个必要条件：
                    1、互斥：某种资源一次只允许一个进程访问，即该资源一旦分配给某个进程，其他进程就不能再访问，直到该进程访问结束。
                    2、占有且等待：一个进程本身占有资源（一种或多种），同时还有资源未得到满足，正在等待其他进程释放该资源。
                    3、不可抢占：别人已经占有了某项资源，你不能因为自己也需要该资源，就去把别人的资源抢过来。
                    4、循环等待：存在一个进程链，使得每个进程都占有下一个进程所需的至少一种资源。

                避免死锁：
                    1、一次性获取所有资源
                    2、按顺序获取资源
                    3、当一个已经持有了一些资源的进程在提出新的资源请求没有得到满足时，
                       它必须释放已经保持的所有资源，待以后需要使用的时候再重新申请。

                https://blog.csdn.net/guaiguaihenguai/article/details/80303835

     */
}
