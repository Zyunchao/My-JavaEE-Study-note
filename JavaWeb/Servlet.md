# Servlet

# 1 Servlet

## 1.1 Servlet 概述

Servlet 是 SUN 公司提供的一套规范，名称就叫 Servlet 规范，它也是 JavaEE 规范之一。我们可以像学习 Java 基础一样，通过 API 来学习 Servlet。这里需要注意的是，在我们之前 JDK 的 API 中是没有 Servlet 规范的相关内容，需要使用 JavaEE 的 API。目前在 Oracle 官网中的最新版本是[JavaEE8](https://www.oracle.com/technetwork/java/javaee/documentation/ee8-release-notes-3894362.html)，该网址中介绍了 JavaEE8 的一些新特性。当然，我们可以通过访问[官方 API](https://javaee.github.io/javaee-spec/javadocs/)，学习和查阅里面的内容。

打开官方 API 网址，在左上部分找到 javax.servlet 包，在左下部分找到 Servlet，如下图显示：

![Servlet的API官网](assets/Servlet的API官网.png)

通过阅读 API，我们得到如下信息：

第一：Servlet 是一个运行在 web 服务端的 java 小程序

第二：它可以用于接收和响应客户端的请求

第三：要想实现 Servlet 功能，可以实现 Servlet 接口，继承 GenericServlet 或者 HttpServlet

第四：每次请求都会执行 service 方法

第五：Servlet 还支持配置

具体请看下图：

![ServletAPI详解](assets/ServletAPI详解.png)

## 1.2 Servlet 入门

### 1.2.1 Servlet 编码步骤

#### 1）编码步骤

**第一步：前期准备-创建 JavaWeb 工程**

**第二步：编写一个普通类继承 GenericServlet 并重写 service 方法**

**第三步：在 web.xml 配置 Servlet**

#### 2）测试

**在 Tomcat 中部署项目**

**在浏览器访问 Servlet**

![测试入门案例执行](assets/测试入门案例执行.png)

### 1.2.2 Servlet 执行过程分析

我们通过浏览器发送请求，请求首先到达 Tomcat 服务器，由服务器解析请求 URL，然后在部署的应用列表中找到我们的应用。接下来，在我们的应用中找应用里的 web.xml 配置文件，在 web.xml 中找到 FirstServlet 的配置，找到后执行 service 方法，最后由 FirstServlet 响应客户浏览器。整个过程如下图所示：

![Servlet执行过程图](assets/Servlet执行过程图.jpg)

一句话总结执行过程：

浏览器——>Tomcat 服务器——>我们的应用——>应用中的 web.xml——>FirstServlet——>响应浏览器

### 1.2.3 Servlet 类视图

在《Tomcat 和 Http 协议》这天课程和刚才的入门案例中，我们都定义了自己的 Servlet，实现的方式都是选择继承 GenericServlet，在 Servlet 的 API 介绍中，它提出了我们除了继承 GenericServlet 外还可以继承 HttpServlet，通过查阅 servlet 的类视图，我们看到 GenericServlet 还有一个子类 HttpServlet。同时，在 service 方法中还有参数 ServletRequest 和 ServletResponse，它们的关系如下图所示：

![Servlet类视图](assets/Servlet类视图.png)

### 1.2.4 Servlet 编写方式

#### 1）编写方式说明

我们在实现 Servlet 功能时，可以选择以下三种方式：

第一种：实现 Servlet 接口，接口中的方法必须全部实现。

 使用此种方式，表示接口中的所有方法在需求方面都有重写的必要。此种方式支持最大程度的自定义。

第二种：继承 GenericServlet，service 方法必须重写，其他方可根据需求，选择性重写。

 使用此种方式，表示只在接收和响应客户端请求这方面有重写的需求，而其他方法可根据实际需求选择性重写，使我们的开发 Servlet 变得简单。但是，此种方式是和 HTTP 协议无关的。

第三种：继承 HttpServlet，它是 javax.servlet.http 包下的一个抽象类，是 GenericServlet 的子类。<b><font color='red'>如果我们选择继承 HttpServlet 时，只需要重写 doGet 和 doPost 方法，不要覆盖 service 方法。</font></b>

 使用此种方式，表示我们的请求和响应需要和 HTTP 协议相关。也就是说，我们是通过 HTTP 协议来访问的。那么每次请求和响应都符合 HTTP 协议的规范。请求的方式就是 HTTP 协议所支持的方式（目前我们只知道 GET 和 POST，而实际 HTTP 协议支持 7 种请求方式，GET POST PUT DELETE TRACE OPTIONS HEAD )。

#### 2）HttpServlet 的使用细节

**第一步：在入门案例的工程中创建一个 Servlet 继承 HttpServlet**

<font color='red'>注意：不要重写任何方法</font>，如下图所示：

![HttpServlet的使用1](assets/HttpServlet的使用1.png)

![HttpServlet的使用2](assets/HttpServlet的使用2.png)

**第二步：部署项目并测试访问**

当我们在地址栏输入 ServletDemo2 的访问 URL 时，出现了访问错误，状态码是 405。提示信息是：方法不允许。

**第三步：分析原因**

得出 HttpServlet 的使用结论：

 <b><font color='red'>我们继承了 HttpServlet，需要重写里面的 doGet 和 doPost 方法来接收 get 方式和 post 方式的请求。</font></b>

为了实现代码的可重用性，我们只需要在 doGet 或者 doPost 方法中一个里面提供具体功能即可，而另外的那个方法只需要调用提供了功能的方法。

## 1.3 Servlet 使用细节

### 1.3.1 Servlet 的生命周期

对象的生命周期，就是对象从生到死的过程，即：出生——活着——死亡。用更偏向 于开发的官方说法就是对象创建到销毁的过程。

出生：请求第一次到达 Servlet 时，对象就创建出来，并且初始化成功。只出生一次，就放到内存中。

活着：服务器提供服务的整个过程中，该对象一直存在，每次只是执行 service 方法。

死亡：当服务停止时，或者服务器宕机时，对象消亡。

通过分析 Servlet 的生命周期我们发现，它的实例化和初始化只会在请求第一次到达 Servlet 时执行，而销毁只会在 Tomcat 服务器停止时执行，由此我们得出一个结论，Servlet 对象只会创建一次，销毁一次。所以，Servlet 对象只有一个实例。如果一个对象实例在应用中是唯一的存在，那么我们就说它是单实例的，即运用了单例模式。

### 1.3.2 Servlet 的线程安全

由于 Servlet 运用了单例模式，即整个应用中只有一个实例对象，所以我们需要分析这个唯一的实例中的类成员是否线程安全。接下来，我们来看下面的的示例：

```java
/**
 * 演示Servlet的线程安全问题：
 *    示例需求：
 *       模拟网上看书的翻页功能。
 *       （类似的有浏览商品的翻页，浏览论坛帖子的翻页）
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo4 extends HttpServlet {

    /**
     * 我们讨论的是类成员的线程安全问题，所以要定义一个类成员
     */
    //定义浏览书籍的页码，都是从第一页开始的
    private int currentPage = 1;

    /**
     * 真正翻页看书的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取当前要看的书名（此处我们今天先来用以下，明天来着重讲解请求和响应对象）
        String bookName = req.getParameter("bookName");
        //2.输出书名和当前页码
        System.out.println("您看的是："+bookName+"，当前页码是："+currentPage);
        //3.执行翻页
        currentPage++;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

这是一个模拟在网上看书的示例，我们在 Servlet 中记录了当前要看的页码，理想状态下，用户每次请求都来看自己该看的页码。启动服务，测试一下：

![Servlet的线程安全问题](assets/Servlet的线程安全问题.png)

通过上面的测试我们发现，在 Servlet 中定义了类成员之后，多个浏览器都会共享类成员的数据。其实每一个浏览器端发送请求，就代表是一个线程，那么多个浏览器就是多个线程，所以测试的结果说明了多个线程会共享 Servlet 类成员中的数据，其中任何一个线程修改了数据，都会影响其他线程。因此，我们可以认为 Servlet 它不是线程安全的。

分析产生这个问题的根本原因，其实就是因为 Servlet 是单例，单例对象的类成员只会随类实例化时初始化一次，之后的操作都是改变，而不会重新初始化。

解决这个问题也非常简单，就是在 Servlet 中定义类成员要慎重。如果类成员是共用的，并且只会在初始化时赋值，其余时间都是获取的话，那么是没问题。如果类成员并非共用，或者每次使用都有可能对其赋值，那么就要考虑线程安全问题了，把它定义到 doGet 或者 doPost 方法里面去就可以了。

### 1.3.3 Servlet 的注意事项

#### 1）映射 Servlet 的细节

Servlet 支持三种映射方式，以达到灵活配置的目的。

首先编写一个 Servlet，代码如下：

```java
/**
 * 演示Servlet的映射方式
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo5 extends HttpServlet {

    /**
     * doGet方法输出一句话
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletDemo5接收到了请求");
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

**第一种：指名道姓的方式**

 此种方式，只有和映射配置一模一样时，Servlet 才会接收和响应来自客户端的请求。

 例如：映射为：/servletDemo5

 访问 URL：http://localhost:8585/servlet_demo/servletDemo5

![Servlet映射1](assets/Servlet映射1.png)

**第二种：/开头+通配符的方式**

 此种方式，只要符合目录结构即可，不用考虑结尾是什么。

 例如：映射为：/servlet/\*

 访问 URL：http://localhost:8585/servlet/itheima

 http://localhost:8585/servlet/itcast.do

 这两个 URL 都可以。因为用的\*，表示/servlet/后面的内容是什么都可以。

![Servlet映射2](assets/Servlet映射2.png)

**第三种：通配符+固定格式结尾**

 此种方式，只要符合固定结尾格式即可，其前面的访问 URI 无须关心（注意协议，主机和端口必须正确）

 例如：映射为：\*.do

 访问 URL：http://localhost:8585/servlet/itcast.do

 http://localhost:8585/itheima.do

 这两个 URL 都可以方法。因为都是以.do 作为结尾，而前面用\*号通配符配置的映射，所有无须关心。

![Servlet映射3](assets/Servlet映射3.png)

通过测试我们发现，Servlet 支持多种配置方式，但是由此也引出了一个问题，当有两个及以上的 Servlet 映射都符合请求 URL 时，由谁来响应呢？注意：HTTP 协议的特征是一请求一响应的规则。那么有一个请求，必然有且只有一个响应。所以，我们接下来明确一下，多种映射规则的优先级。

先说结论：指名道姓的方式优先级最高，带有通配符的映射方式，有/的比没/的优先级高

所以，我们前面讲解的三种映射方式的优先级为：第一种>第二种>第三种。

演示代码如下：

```java
/**
 * 它和ServletDemo5组合演示Servlet的访问优先级问题
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo6 extends HttpServlet {

    /**
     * doGet方法输出一句话
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletDemo6接收到了请求");
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

```xml
<!--配置ServletDemo6-->
<servlet>
    <servlet-name>servletDemo6</servlet-name>
    <servlet-class>com.itheima.web.servlet.ServletDemo6</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo6</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

运行结果如下：

![Servlet的优先级](assets/Servlet的优先级.png)

#### 2）多路径映射 Servlet

上一小节我们讲解了 Servlet 的多种映射方式，这一小节我们来介绍一下，一个 Servlet 的多种路径配置的支持。

它其实就是给一个 Servlet 配置多个访问映射，从而可以根据不同请求 URL 实现不同的功能。

首先，创建一个 Servlet：

```java
/**
 * 演示Servlet的多路径映射
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo7 extends HttpServlet {

    /**
     * 根据不同的请求URL，做不同的处理规则
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取当前请求的URI
        String uri = req.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/"),uri.length());
        //2.判断是1号请求还是2号请求
        if("/servletDemo7".equals(uri)){
            System.out.println("ServletDemo7执行1号请求的业务逻辑：商品单价7折显示");
        }else if("/demo7".equals(uri)){
            System.out.println("ServletDemo7执行2号请求的业务逻辑：商品单价8折显示");
        }else {
            System.out.println("ServletDemo7执行基本业务逻辑：商品单价原价显示");
        }
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

接下来，在 web.xml 配置 Servlet：

```xml
<!--配置ServletDemo7-->
<servlet>
    <servlet-name>servletDemo7</servlet-name>
    <servlet-class>com.itheima.web.servlet.ServletDemo7</servlet-class>
</servlet>
<!--映射路径1-->
<servlet-mapping>
    <servlet-name>servletDemo7</servlet-name>
    <url-pattern>/demo7</url-pattern>
</servlet-mapping>
<!--映射路径2-->
<servlet-mapping>
    <servlet-name>servletDemo7</servlet-name>
    <url-pattern>/servletDemo7</url-pattern>
</servlet-mapping>
<!--映射路径3-->
<servlet-mapping>
    <servlet-name>servletDemo7</servlet-name>
    <url-pattern>/servlet/*</url-pattern>
</servlet-mapping>
```

最后，启动服务测试运行结果：

![多路径URL映射](assets/多路径URL映射.png)

#### 3）启动时创建 Servlet

我们前面讲解了 Servlet 的生命周期，Servlet 的创建默认情况下是请求第一次到达 Servlet 时创建的。但是我们都知道，Servlet 是单例的，也就是说在应用中只有唯一的一个实例，所以在 Tomcat 启动加载应用的时候就创建也是一个很好的选择。那么两者有什么区别呢？

- 第一种：应用加载时创建 Servlet，它的优势是在服务器启动时，就把需要的对象都创建完成了，从而在使用的时候减少了创建对象的时间，提高了首次执行的效率。它的弊端也同样明显，因为在应用加载时就创建了 Servlet 对象，因此，导致内存中充斥着大量用不上的 Servlet 对象，造成了内存的浪费。
- 第二种：请求第一次访问是创建 Servlet，它的优势就是减少了对服务器内存的浪费，因为那些一直没有被访问过的 Servlet 对象都没有创建，因此也提高了服务器的启动时间。而它的弊端就是，如果有一些要在应用加载时就做的初始化操作，它都没法完成，从而要考虑其他技术实现。

通过上面的描述，相信同学们都能分析得出何时采用第一种方式，何时采用第二种方式。就是当需要在应用加载就要完成一些工作时，就需要选择第一种方式。当有很多 Servlet 的使用时机并不确定是，就选择第二种方式。

在 web.xml 中是支持对 Servlet 的创建时机进行配置的，配置的方式如下：我们就以 ServletDemo3 为例。

```xml
<!--配置ServletDemo3-->
<servlet>
    <servlet-name>servletDemo3</servlet-name>
    <servlet-class>com.itheima.web.servlet.ServletDemo3</servlet-class>
    <!--配置Servlet的创建顺序，当配置此标签时，Servlet就会改为应用加载时创建
        配置项的取值只能是正整数（包括0），数值越小，表明创建的优先级越高
    -->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo3</servlet-name>
    <url-pattern>/servletDemo3</url-pattern>
</servlet-mapping>
```

![Servlet的启动顺序](assets/Servlet的启动顺序.png)

#### 4）默认 Servlet

默认 Servlet 是由服务器提供的一个 Servlet，它配置在 Tomcat 的 conf 目录下的 web.xml 中。如下图所示：

![默认Servlet](assets/默认Servlet.png)

它的映射路径是<b><font color='red'>`<url-pattern>/<url-pattern>`</font></b>，我们在发送请求时，首先会在我们应用中的 web.xml 中查找映射配置，找到就执行，这块没有问题。但是当找不到对应的 Servlet 路径时，就去找默认的 Servlet，由默认 Servlet 处理。所以，一切都是 Servlet。

### 1.4 Servlet 关系总图

![Servlet类关系总视图](assets/Servlet类关系总视图.png)

# 2 ServletConfig

## 2.1 ServletConfig 概述

### 2.1.1 基本概念

它是 Servlet 的配置参数对象，在 Servlet 规范中，允许为每个 Servlet 都提供一些初始化配置。所以，每个 Servlet 都一个自己的 ServletConfig。它的作用是在 Servlet 初始化期间，把一些配置信息传递给 Servlet。

### 2.1.2 生命周期

由于它是在初始化阶段读取了 web.xml 中为 Servlet 准备的初始化配置，并把配置信息传递给 Servlet，所以生命周期与 Servlet 相同。这里需要注意的是，如果 Servlet 配置了`<load-on-startup>1</load-on-startup>`，那么 ServletConfig 也会在应用加载时创建。

## 2.2 ServletConfig 的使用

### 2.2.1 如何获取

首先，我们要清楚的认识到，它可以为每个 Servlet 都提供初始化参数，所以肯定可以在每个 Servlet 中都配置。那是配置在 Servlet 的声明部分，还是映射部分呢？我们接下来先准备一个 Servlet，然后给同学们揭秘。

```java
/**
 * 演示Servlet的初始化参数对象
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo8 extends HttpServlet {

    //定义Servlet配置对象ServletConfig
    private ServletConfig servletConfig;

    /**
     * 在初始化时为ServletConfig赋值
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
    }

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //输出ServletConfig
        System.out.println(servletConfig);
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

```xml
<!--配置ServletDemo8-->
<servlet>
    <servlet-name>servletDemo8</servlet-name>
    <servlet-class>com.itheima.web.servlet.ServletDemo8</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo8</servlet-name>
    <url-pattern>/servletDemo8</url-pattern>
</servlet-mapping>
```

### 2.2.2 如何配置

在上一小节中，我们已经准备好了 Servlet，同时也获取到了它的 ServletConfig 对象，在本小节中我们将告诉同学们如何配置初始化参数，它需要使用`<servlet>`标签中的`<init-param>`标签来配置。这也就揭秘上一小节的悬念，Servlet 的初始化参数都是配置在 Servlet 的声明部分的。并且每个 Servlet 都支持有多个初始化参数，并且初始化参数都是以键值对的形式存在的。接下来，我们看配置示例：

```xml
<!--配置ServletDemo8-->
<servlet>
    <servlet-name>servletDemo8</servlet-name>
    <servlet-class>com.itheima.web.servlet.ServletDemo8</servlet-class>
    <!--配置初始化参数-->
    <init-param>
        <!--用于获取初始化参数的key-->
        <param-name>encoding</param-name>
        <!--初始化参数的值-->
        <param-value>UTF-8</param-value>
    </init-param>
    <!--每个初始化参数都需要用到init-param标签-->
    <init-param>
        <param-name>servletInfo</param-name>
        <param-value>This is Demo8</param-value>
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo8</servlet-name>
    <url-pattern>/servletDemo8</url-pattern>
</servlet-mapping>
```

### 2.2.3 常用方法

![ServletConfig对象](assets/ServletConfig对象.png)

```java
/**
 * 演示Servlet的初始化参数对象
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo8 extends HttpServlet {

    //定义Servlet配置对象ServletConfig
    private ServletConfig servletConfig;

    /**
     * 在初始化时为ServletConfig赋值
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
    }

    /**
     * doGet方法输出一句话
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.输出ServletConfig
        System.out.println(servletConfig);
        //2.获取Servlet的名称
        String servletName= servletConfig.getServletName();
        System.out.println(servletName);
        //3.获取字符集编码
        String encoding = servletConfig.getInitParameter("encoding");
        System.out.println(encoding);
        //4.获取所有初始化参数名称的枚举
        Enumeration<String> names = servletConfig.getInitParameterNames();
        //遍历names
        while(names.hasMoreElements()){
            //取出每个name
            String name = names.nextElement();
            //根据key获取value
            String value = servletConfig.getInitParameter(name);
            System.out.println("name:"+name+",value:"+value);
        }
        //5.获取ServletContext对象
        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println(servletContext);
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

![ServletConfig演示](assets/ServletConfig演示.png)

# 3 ServletContext

## 3.1 ServletContext 概述

### 3.1.1 基本介绍

ServletContext 对象，它是应用上下文对象。每一个应用有且只有一个 ServletContext 对象。它可以实现让应用中所有 Servlet 间的数据共享。

### 3.1.2 生命周期

出生——活着——死亡

出生： 应用一加载，该对象就被创建出来了。一个应用只有一个实例对象。(Servlet 和 ServletContext 都是单例的)

活着：只要应用一直提供服务，该对象就一直存在。

死亡：应用被卸载（或者服务器挂了），该对象消亡。

### 3.1.3 域对象概念

域对象的概念，它指的是对象有作用域，即有作用范围。

域对象的作用，域对象可以实现数据共享。不同作用范围的域对象，共享数据的能力不一样。

在 Servlet 规范中，一共有 4 个域对象。今天我们讲解的 ServletContext 就是其中一个。它也是我们接触的第一个域对象。它是 web 应用中最大的作用域，叫 application 域。每个应用只有一个 application 域。它可以实现整个应用间的数据共享功能。

## 3.2 ServletContext 的使用

### 3.2.1 如何获取

在讲解 ServletConfig 对象时，我们已经看到了获取 ServletContext 对象的方式，它只需要调用 ServletConfig 对象的`getServletContext()`方法就可以了。具体代码如下：我们创建一个新的 Servlet 用于演示 ServletContext。

```java
/**
 * 用于演示ServletContext对象的使用
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo9 extends HttpServlet {

    //定义Servlet配置对象ServletConfig
    private ServletConfig servletConfig;

    /**
     * 在初始化时为ServletConfig赋值
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;

    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取ServletContext对象
        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println(servletContext);
    }

    /**
     * 调用doGet方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

```xml
<!--配置ServletDemo9-->
    <servlet>
        <servlet-name>servletDemo9</servlet-name>
        <servlet-class>com.itheima.web.servlet.ServletDemo9</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>servletDemo9</servlet-name>
        <url-pattern>/servletDemo9</url-pattern>
    </servlet-mapping>
```

在实际开发中，如果我们每个 Servlet 对 ServletContext 都使用频繁的话，那么每个 Servlet 里定义 ServletConfig，再获取 ServletContext 的代码将非常多，造成大量的重复代码。Servlet 规范的定义中也为我们想到了这一点，所以它在 GenericServlet 中，已经为我们声明好了 ServletContext 获取的方法，如下图所示：

![GenericServlet的getServletContext](assets/GenericServlet的getServletContext.png)

我们的 Servlet 都是继承自 HttpServlet，而 HttpServlet 又是 GenericServlet 的子类，所以我们在获取 ServletContext 时，如果当前 Servlet 没有用到它自己的初始化参数时，就可以不用再定义初始化参数了，而是直接改成下图所示的代码即可：

![获取ServletContext](assets/获取ServletContext.png)

### 3.2.2 如何配置

ServletContext 既然被称之为应用上下文对象，所以它的配置是针对整个应用的配置，而非某个特定 Servlet 的配置。它的配置被称为应用的初始化参数配置。

配置的方式，需要在`<web-app>`标签中使用`<context-param>`来配置初始化参数。具体代码如下：

```xml
<!--配置应用初始化参数-->
<context-param>
    <!--用于获取初始化参数的key-->
    <param-name>servletContextInfo</param-name>
    <!--初始化参数的值-->
    <param-value>This is application scope</param-value>
</context-param>
<!--每个应用初始化参数都需要用到context-param标签-->
<context-param>
    <param-name>globalEncoding</param-name>
    <param-value>UTF-8</param-value>
</context-param>
```

# 4 注解开发 Servlet

## 4.1 Servlet3.0 规范

首先，我们要先跟同学们明确一件事情，我们在《Tomcat 和 HTTP 协议》课程中已经介绍了，我们使用的是 Tomcat9，JavaEE 规范要求是 8，对应的 Servlet 规范规范应该是 JavaEE8 包含的 4.x 版本。

但是，同学们要知道，在企业级应用的开发中，稳定远比追新版本重要的多。所以，我们虽然用到了 Tomcat9 和对应的 JavaEE8，但是涉及的 Servlet 规范我们降板使用，用的是 Servlet3.1 版本。关于兼容性问题，同学们也无须担心，向下兼容的特性，在这里也依然适用。

接下来，同学还有可能疑惑的地方就是，我们课程中明明使用的是 Servlet3.1 版本的规范，但是却总听老师提 Servlet3.0 规范，这两个到底有怎样的联系呢？

现在就给同学们解惑，在大概十多年前，那会还是 Servlet2.5 的版本的天下，它最明显的特征就是 Servlet 的配置要求配在 web.xml 中，<b><font color='red'>我们今天课程中在第 4 章节《注解开发 Servlet》之前，全都是基于 Servlet2.5 规范编写的。</font></b>从 2007 年开始到 2009 年底，在这个时间段，软件开发开始逐步的演变，基于注解的配置理念开始逐渐出现，大量注解配置思想开始用于各种框架的设计中，例如：Spring3.0 版本的 Java Based Configuration，JPA 规范，apache 旗下的 struts2 和 mybatis 的注解配置开发等等。

JavaEE6 规范也是在这个期间设计并推出的，与之对应就是它里面包含了新的 Servlet 规范：<b><font color='red'>Servlet3.0 版本！</font></b>

## 4.2 注解开发入门案例

### 4.2.1 自动注解配置

#### 1）配置步骤

**第一步：创建 JavaWeb 工程，并移除 web.xml**

![Servlet注解开发1](assets/Servlet注解开发1.png)

![Servlet注解开发2](assets/Servlet注解开发2.png)

![注解开发Servlet移除web.xml](assets/注解开发Servlet移除web.xml.png)

**第二步：编写 Servlet**

```java
/**
 * 注解开发Servlet
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet Demo1 Annotation");
    }
}
```

**第三步：使用注解配置 Servlet**

![注解配置Servlet映射](assets/注解配置Servlet映射.png)

**第四步：测试**

![注解配置Servlet测试](assets/注解配置Servlet测试.png)

#### 2）注解详解

```java
/**
 * WebServlet注解
 * @since Servlet 3.0 (Section 8.1.1)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {

    /**
     * 指定Servlet的名称。
     * 相当于xml配置中<servlet>标签下的<servlet-name>
     */
    String name() default "";

    /**
     * 用于映射Servlet访问的url映射
     * 相当于xml配置时的<url-pattern>
     */
    String[] value() default {};

    /**
     * 相当于xml配置时的<url-pattern>
     */
    String[] urlPatterns() default {};

    /**
     * 用于配置Servlet的启动时机
     * 相当于xml配置的<load-on-startup>
     */
    int loadOnStartup() default -1;

    /**
     * 用于配置Servlet的初始化参数
     * 相当于xml配置的<init-param>
     */
    WebInitParam[] initParams() default {};

    /**
     * 用于配置Servlet是否支持异步
     * 相当于xml配置的<async-supported>
     */
    boolean asyncSupported() default false;

    /**
     * 用于指定Servlet的小图标
     */
    String smallIcon() default "";

    /**
     * 用于指定Servlet的大图标
     */
    String largeIcon() default "";

    /**
     * 用于指定Servlet的描述信息
     */
    String description() default "";

    /**
     * 用于指定Servlet的显示名称
     */
    String displayName() default "";
}
```

### 4.2.2 手动创建容器

#### 1）前置说明

在使用 Servlet3.1 版本的规范时，脱离了 web.xml 进行注解开发，它除了支持使用注解的配置方式外，还支持纯手动创建 Servlet 容器的方式。要想使用的话，必须遵循它的编写规范。它是从 Servlet3.0 规范才开始引入的，加入了一个新的接口：

```java
package javax.servlet;

import java.util.Set;

/**
 * 初始化Servlet容器必须实现此接口
 * 它是Servlet3.0规范提供的标准接口
 * @since Servlet 3.0
 */
public interface ServletContainerInitializer {
     /**
     * 启动容器时做一些初始化操作，例如注册Servlet,Filter,Listener等等。
 	 * @since Servlet 3.0
     */
    void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException;
}
```

同时可以利用@HandlesTypes 注解，把要加载到 onStartup 方法中的类字节码传入进来，@HandlesTypes 源码如下：

```java
/**
 * 用于指定要加载到ServletContainerInitializer接口实现了中的字节码
 * @see javax.servlet.ServletContainerInitializer
 * @since Servlet 3.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesTypes {

    /**
     * 指定要加载到ServletContainerInitializer实现类的onStartUp方法中类的字节码。
     * 字节码可以是接口，抽象类或者普通类。
     */
    Class[] value();
}
```

#### 2）编写步骤

**第一步：创建工程，并移除 web.xml**

![手动创建容器之创建工程](assets/手动创建容器之创建工程.png)

![手动创建容器之移除web.xml](assets/手动创建容器之移除web.xml.png)

**第二步：编写 Servlet**

```java
/**
 * 注解开发Servlet 之 手动初始化容器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletDemo1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet Demo1 Annotation manual");
    }
}
```

**第三步：创建初始化容器的类，并按照要求配置**

```java
/**
 * 初始化容器操作
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class MyServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

    }
}
```

在脱离 web.xml 时，要求在 src 目录下包含一个 META-INF 目录，位置和及字母都不能改变，且严格区分大小写。在目录中创建一个名称为`javax.servlet.ServletContainerInitializer`的文件，里面写实现了`ServletContainerInitializer`接口的全限定类名。如下图所示：

![手动创建容器之初始化容器的配置](assets/手动创建容器之初始化容器的配置.png)

**第四步：编写注册 Servlet 的代码**

![手动创建容器之编写注册Servlet的代码](assets/手动创建容器之编写注册Servlet的代码.png)

**第五步：测试**

![手动创建容器之测试](assets/手动创建容器之测试.png)

# 5 Servlet 应用案例-学生管理系统

## 5.1 案例介绍

### 5.1.1 案例需求

在昨天的课程讲解中，我们用 Tomcat 服务器替代了 SE 阶段的学生管理系统中自己写的服务器。今后我们进入企业肯定也会使用成型的产品，而不会自己去写服务器（除非是专门做应用服务器的公司）。

从今天开始案例正式进入了编码阶段，它是延续了 JavaSE 阶段课程的学生管理系统。并且分析了 SE 中系统的各类问题，在 JavaWeb 阶段学习，就是要通过每天的学习，逐步解决 SE 阶段学生管理系统中的遗留问题。

今天，我们将会去解决下面这个问题：<b><font color='red'>保存学生</font></b>。也就是让数据真正的动起来，本质就是通过 html 发送一个请求，把表单中填写的数据带到服务器端。因为每个使用者在表单填写的内容不一样，所有最终存起来的也就不一样了。

### 5.1.2 技术选型

这是一个全新的案例，而不是在 SE 阶段的案例上进行改造。所以我们用项目的方式来去约束这个案例。

任何一个项目，在立项之初都会有技术选型，也就是定义使用的技术集，这里面包含很多。例如：表现层技术，持久层技术，数据库技术等等。

我们今天只针对表现层进行编码，所以就先来定义表现层技术。表现层技术的选型就是 Servlet+HTML 的组合。

由 HTML 中编写表单，Servlet 中定义接收请求的方法，最终把表单数据输出到控制台即可。<b>我们 Servlet 的配置方式仍然选择基于 web.xml 的配置方式。</b>
