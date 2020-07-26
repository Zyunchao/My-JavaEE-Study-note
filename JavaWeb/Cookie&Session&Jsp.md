# Cookie&SessionJsp

# 1 会话技术

## 1.1 会话管理概述

### 1.1.1 什么是会话

这里的会话，指的是 web 开发中的一次通话过程，当打开浏览器，访问网站地址后，会话开始，当关闭浏览器（或者到了过期时间），会话结束。

举个例子：

 例如，你在给家人打电话，这时突然有送快递的配送员敲门，你放下电话去开门，收完快递回来后，通话还在保持中，继续说话就行了。

### 1.1.2 会话管理作用

什么时候会用到会话管理呢？最常见的就是购物车，当我们登录成功后，把商品加入到购物车之中，此时我们无论再浏览什么商品，当点击购物车时，那些加入的商品都仍在购物车中。

在我们的实际开发中，还有很多地方都离不开会话管理技术。比如，我们在论坛发帖，没有登录的游客身份是不允许发帖的。所以当我们登录成功后，无论我们进入哪个版块发帖，只要权限允许的情况下，服务器都会认识我们，从而让我们发帖，因为登录成功的信息一直保留在服务器端的会话中。

通过上面的两个例子，我们可以看出，它是为我们共享数据用的，并且是在不同请求间实现数据共享。也就是说，如果我们需要在多次请求间实现数据共享，就可以考虑使用会话管理技术了。

### 1.1.3 会话管理分类

在 JavaEE 的项目中，会话管理分为两类。分别是：客户端会话管理技术和服务端会话管理技术。

**客户端会话管理技术**

 它是把要共享的数据保存到了客户端（也就是浏览器端）。每次请求时，把会话信息带到服务器，从而实现多次请求的数据共享。

**服务端会话管理技术**

 它本质仍是采用客户端会话管理技术，只不过保存到客户端的是一个特殊的标识，并且把要共享的数据保存到了服务端的内存对象中。每次请求时，把这个标识带到服务器端，然后使用这个标识，找到对应的内存空间，从而实现数据共享。

## 1.2 客户端会话管理技术

### 1.2.1 Cookie 概述

#### 1）什么是 Cookie

它是客户端浏览器的缓存文件，里面记录了客户浏览器访问网站的一些内容。同时，也是 HTTP 协议请求和响应消息头的一部分（在 HTTP 协议课程中，我们备注了它很重要）。

#### 2）Cookie 的 API 详解

**作用**

它可以保存客户浏览器访问网站的相关内容（需要客户端不禁用 Cookie）。从而在每次访问需要同一个内容时，先从本地缓存获取，使资源共享，提高效率。

**Cookie 的属性**

| 属性名称 | 属性作用                  | 是否重要 |
| -------- | ------------------------- | -------- |
| name     | cookie 的名称             | 必要属性 |
| value    | cookie 的值（不能是中文） | 必要属性 |
| path     | cookie 的路径             | 重要     |
| domain   | cookie 的域名             | 重要     |
| maxAge   | cookie 的生存时间。       | 重要     |
| version  | cookie 的版本号。         | 不重要   |
| comment  | cookie 的说明。           | 不重要   |

**细节**

Cookie 有大小，个数限制。每个网站最多只能存 20 个 cookie，且大小不能超过 4kb。同时，所有网站的 cookie 总数不超过 300 个。

当删除 Cookie 时，设置 maxAge 值为 0。当不设置 maxAge 时，使用的是浏览器的内存，当关闭浏览器之后，cookie 将丢失。设置了此值，就会保存成缓存文件（值必须是大于 0 的,以秒为单位）。

#### 3）Cookie 涉及的常用方法

**创建 Cookie**

![Cookie的方法](assets/Cookie的方法.png)

```java
/**
 * 通过指定的名称和值构造一个Cookie
 *
 * Cookie的名称必须遵循RFC 2109规范。这就意味着，它只能包含ASCII字母数字字符，
 * 不能包含逗号、分号或空格或以$字符开头。
 * 创建后无法更改cookie的名称。
 *
 * 该值可以是服务器选择发送的任何内容。
 * 它的价值可能只有服务器才感兴趣。
 * 创建之后，可以使用setValue方法更改cookie的值。
 */
public Cookie(String name, String value) {
	validation.validate(name);
	this.name = name;
	this.value = value;
}
```

**向浏览器添加 Cookie**

![添加Cookie的方法](assets/添加Cookie的方法.png)

```java
/**
 * 添加Cookie到响应中。此方法可以多次调用，用以添加多个Cookie。
 */
public void addCookie(Cookie cookie);
```

**从服务器端获取 Cookie**

![获取Cookie的方法](assets/获取Cookie的方法.png)

```java
/**
 * 这是HttpServletRequest中的方法。
 * 它返回一个Cookie的数组，包含客户端随此请求发送的所有Cookie对象。
 * 如果没有符合规则的cookie，则此方法返回null。
 */
 public Cookie[] getCookies();
```

### 1.2.2 Cookie 的 Path 细节：浏览器什么时候带给服务器，什么时候不带

#### 1）需求说明

创建一个 Cookie，设置 Cookie 的 path，通过不同的路径访问，从而查看请求携带 Cookie 的情况。

#### 2）案例目的

通过此案例的讲解，同学们可以清晰的描述出，客户浏览器何时带 cookie 到服务器端，何时不带。

#### 3）案例步骤

**第一步：创建 JavaWeb 工程**

沿用第一个案例中的工程即可。

**第二步：编写 Servlet**

```JAVA
/**
 * Cookie的路径问题
 * 前期准备：
 * 	1.在demo1中写一个cookie到客户端
 *  2.在demo2和demo3中分别去获取cookie
 *  	demo1的Servlet映射是   /servlet/PathQuestionDemo1
 *  	demo2的Servlet映射是   /servlet/PathQuestionDemo2
 *  	demo3的Servlet映射是   /PathQuestionDemo3
 *
 * @author 黑马程序员
 * @Company http://www.itheima.com
 *
 */
public class PathQuestionDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.创建一个Cookie
		Cookie cookie = new Cookie("pathquestion","CookiePathQuestion");
		//2.设置cookie的最大存活时间
		cookie.setMaxAge(Integer.MAX_VALUE);
		//3.把cookie发送到客户端
		response.addCookie(cookie);//setHeader("Set-Cookie","cookie的值")
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

```

```java
/**
 * 获取Cookie，名称是pathquestion
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class PathQuestionDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取所有的cookie
		Cookie[] cs = request.getCookies();
		//2.遍历cookie的数组
		for(int i=0;cs!=null && i<cs.length;i++){
			if("pathquestion".equals(cs[i].getName())){
				//找到了我们想要的cookie，输出cookie的值
				response.getWriter().write(cs[i].getValue());
				return;
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
```

```java
/**
 * 获取Cookie，名称是pathquestion
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class PathQuestionDemo3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取所有的cookie
		Cookie[] cs = request.getCookies();
		//2.遍历cookie的数组
		for(int i=0;cs!=null && i<cs.length;i++){
			if("pathquestion".equals(cs[i].getName())){
				//找到了我们想要的cookie，输出cookie的值
				response.getWriter().write(cs[i].getValue());
				return;
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
```

**第三步：配置 Servlet**

```xml
<!--配置Cookie路径问题案例的Servlet-->
<servlet>
    <servlet-name>PathQuestionDemo1</servlet-name>
    <servlet-class>com.itheima.web.servlet.pathquestion.PathQuestionDemo1</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>PathQuestionDemo1</servlet-name>
    <url-pattern>/servlet/PathQuestionDemo1</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>PathQuestionDemo2</servlet-name>
    <servlet-class>com.itheima.web.servlet.pathquestion.PathQuestionDemo2</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>PathQuestionDemo2</servlet-name>
    <url-pattern>/servlet/PathQuestionDemo2</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>PathQuestionDemo3</servlet-name>
    <servlet-class>com.itheima.web.servlet.pathquestion.PathQuestionDemo3</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>PathQuestionDemo3</servlet-name>
    <url-pattern>/PathQuestionDemo3</url-pattern>
</servlet-mapping>
```

**第四步：部署工程**

沿用第一个案例中的工程部署即可。

#### 4）测试结果

通过分别运行 PathQuestionDemo1，2 和 3 这 3 个 Servlet，我们发现由 demo1 写 Cookie，在 demo2 中可以取到，但是到了 demo3 中就无法获取了，如下图所示：

![案例2-1](assets/案例2-1.png)

![案例2-2](assets/案例2-2.png)

![案例2-3](assets/案例2-3.png)

#### 5）路径问题的分析及总结

**问题：**
demo2 和 demo3 谁能取到 cookie？
**答案：**
demo2 能取到，demo3 取不到
**分析：**
首先，我们要知道如何确定一个 cookie？
那就是使用 cookie 的三个属性组合：<font color='red'><b>domain+path+name</b></font>
这里面，同一个应用的 domain 是一样的，在我们的案例中都是 localhost。
​ 并且，我们取的都是同一个 cookie，所以 name 也是一样的，都是 pathquestion。
​ 那么，不一样的只能是 path 了。但是我们没有设置过 cookie 的 path 属性，这就表明 path 是有默认值的。
接下来，我们打开这个 cookie 来看一看，在 ie 浏览器访问一次 PathQuestionDemo1 这个 Servlet：

Cookie 中的内容：
![Cookie文件介绍](assets/Cookie文件介绍.png)
我们是通过 demo1 写的 cookie，demo1 的访问路径是： http://localhost:9090/servlet/PathQuestionDemo1
通过比较两个路径：请求资源地址和 cookie 的 path，可以看出：cookie 的 path 默认值是：请求资源 URI，没有资源的部分（在我们的案例中，就是没有 PathQuestionDemo1）。

**客户端什么时候带 cookie 到服务器，什么时候不带？**
​ 就是看请求资源 URI 和 cookie 的 path 比较。

 <font color='red'>请求资源 URI.startWith(cookie 的 path) </font> 如果返回的是 true 就带，如果返回的是 false 就不带。

 简单的说： 就是看谁的地址更精细

 比如：Cookie 的 path： /国家 /省份 /城市

请求资源 URI : /国家 /省份 不带
请求资源 URI ： /国家 /省份 /城市 /区县 带

![案例2-4](assets/案例2-4.png)

在我们的案例中：

| 访问 URL                                                             | URI 部分                   | Cookie 的 Path | 是否携带 Cookie | 能否取到 Cookie |
| -------------------------------------------------------------------- | -------------------------- | -------------- | --------------- | --------------- |
| [PathQuestionDemo2](http://localhost:9090/servlet/PathQuestionDemo2) | /servlet/PathQuestionDemo2 | /servlet/      | 带              | 能取到          |
| [PathQuestionDemo3](http://localhost:9090/PathQuestionDemo3)         | /PathQuestionDemo3         | /servlet/      | 不带            | 不能取到        |

## 1.3 服务端会话管理概述

### 1.3.1 HttpSession 概述

#### 1）HttpSession 对象介绍

它是 Servlet 规范中提供的一个接口。该接口的实现由 Servlet 规范的实现提供商提供。我们使用的是 Tomcat 服务器，它对 Servlet 规范进行了实现，所以 HttpSession 接口的实现由 Tomcat 提供。该对象用于提供一种通过多个页面请求或访问网站来标识用户并存储有关该用户的信息的方法。简单说它就是一个服务端会话对象，用于存储用户的会话数据。

同时，它也是 Servlet 规范中四大域对象之一的会话域对象。并且它也是用于实现数据共享的。但它与我们之前讲解的应用域和请求域是有区别的。

| 域对象         | 作用范围     | 使用场景                                                       |
| -------------- | ------------ | -------------------------------------------------------------- |
| ServletContext | 整个应用范围 | 当前项目中需要数据共享时，可以使用此域对象。                   |
| ServletRequest | 当前请求范围 | 在请求或者当前请求转发时需要数据共享可以使用此域对象。         |
| HttpSession    | 会话返回     | 在当前会话范围中实现数据共享。它可以在多次请求中实现数据共享。 |

#### 2）HttpSession 的获取

获取 HttpSession 是通过 HttpServletRequest 接口中的两个方法获取的，如下图所示：

![获取HttpSession的两个方法](assets/获取HttpSession的两个方法.png)

这两个方法的区别：

![获取Session的两个方法](assets/获取Session的两个方法.png)

#### 3）HttpSession 的常用方法

![HttpSession方法介绍](assets/HttpSession方法介绍.png)

### 1.3.2 HttpSession 的入门案例

#### 1）需求说明

在请求 HttpSessionDemo1 这个 Servlet 时，携带用户名信息，并且把信息保存到会话域中，然后从 HttpSessionDemo2 这个 Servlet 中获取登录信息。

#### 2）案例目的

通过本案例的讲解，同学们可以清楚的认识到会话域的作用，即多次请求间的数据共享。因为是两次请求，请求域肯定不一样了，所以不能用请求域实现。

最终掌握 HttpSession 对象的获取和使用。

#### 3）原理分析

HttpSession，它虽然是服务端会话管理技术的对象，但它本质仍是一个 Cookie。是一个由服务器自动创建的特殊的 Cookie，Cookie 的名称就是 JSESSIONID，Cookie 的值是服务器分配的一个唯一的标识。

当我们使用 HttpSession 时，浏览器在没有禁用 Cookie 的情况下，都会把这个 Cookie 带到服务器端，然后根据唯一标识去查找对应的 HttpSession 对象，找到了，我们就可以直接使用了。下图就是我们入门案例中，HttpSession 分配的唯一标识，同学们可以看到两次请求的 JSESSIONID 的值是一样的：

![案例3-5](assets/案例3-5.png)

### 1.3.3 HttpSession 的钝化和活化

**什么是持久态**

 把长时间不用，但还不到过期时间的 HttpSession 进行序列化，写到磁盘上。

 我们把 HttpSession 持久态也叫做钝化。（与钝化相反的，我们叫活化。）

**什么时候使用持久化**

 第一种情况：当访问量很大时，服务器会根据 getLastAccessTime 来进行排序，对长时间不用，但是还没到过期时间的 HttpSession 进行持久化。

 第二种情况：当服务器进行重启的时候，为了保持客户 HttpSession 中的数据，也要对 HttpSession 进行持久化

**注意**

 HttpSession 的持久化由服务器来负责管理，我们不用关心。

 只有实现了序列化接口的类才能被序列化，否则不行。

# 2 页面技术

## 2.1 JSP 基础

### 2.1.1 JSP 简介

JSP 全称是 Java Server Page，它和 Servlet 一样，也是 sun 公司推出的一套开发动态 web 资源的技术，称为 JSP/Servlet 规范。JSP 的本质其实就是一个 Servlet。

### 2.1.2 JSP 和 HTML 以及 Servlet 的适用场景

| 类别    | 适用场景                                                                                  |
| ------- | ----------------------------------------------------------------------------------------- |
| HTML    | 只能开发静态资源，不能包含 java 代码，无法添加动态数据。                                  |
| Servlet | 写 java 代码，可以输出页面内容，但是很不方便，开发效率极低。                              |
| JSP     | 它包括了 HTML 的展示技术，同时具备 Servlet 输出动态资源的能力。但是不适合作为控制器来用。 |

### 2.1.3 JSP 简单入门

**创建 JavaWeb 工程**

![案例jsp1](assets/案例jsp1.png)

**在 index.jsp 中填写内容**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>JSP的入门</title>
  </head>
  <body>
      这是第一个JSP页面
  </body>
</html>
```

**部署项目**

沿用会话管理工程的部署方式即可。

**测试运行**

![案例jsp2](assets/案例jsp2.png)

### 2.1.4 JSP 说明

写在之前： 明确 JSP 就是一个 Servlet。是一个特殊的 Servlet。

JSP 的原理：

 客户端提交请求

 ——Tomcat 服务器解析请求地址

 ——找到 JSP 页面

 ——Tomcat 将 JSP 页面翻译成 Servlet 的 java 文件

 ——将翻译好的.java 文件编译成.class 文件

 ——返回到客户浏览器上。

#### 1）执行过程分析图

![Tomcat执行过程](assets/Tomcat执行过程.png)

#### 2）JSP 的.java 文件内容分析

当我们打开 index.jsp 翻译的 java 文件看到的就是`public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase`类的声明，然后我们在 Tomcat 的源码中找到类的声明，如下图：

![Tomcat中的HttpJspBase类声明](assets/Tomcat中的HttpJspBase类声明.png)

这张图一出场，就表明我们写的 JSP 它本质就是一个 HttpServlet 了。

![jsp的本质说明](assets/jsp的本质说明.png)

同时，我们在 index_jsp.java 文件中找到了输出页面的代码，并且在浏览器端查看源文件，看到的内容是一样的。这也就是说明，我们的浏览器上的内容，在通过 jsp 展示时，本质都是用 out.write()输出出来的。

讲到这里，我们应该清楚的认识到，JSP 它是一个特殊的 Servlet，主要是用于展示动态数据。它展示的方式是用流把数据输出出来，而我们在使用 JSP 时，涉及 HTML 的部分，都与 HTML 的用法一致，这部分称为 jsp 中的模板元素，在开发过程中，先写好这些模板元素，因为它们决定了页面的外观。

## 2.2 JSP 应用

### 2.2.1 JSP 语法

#### 1）Java 代码块

在 jsp 中，可以使用 java 脚本代码。形式为：<font color='red'><b><% 此处写 java 代码 %></b></font>

但是，在实际开发中，极少使用此种形式编写 java 代码。同时需要注意的是：

```jsp
<%
	在里面写java程序脚本需要注意：这里面的内容由tomcat负责翻译，翻译之后是service方法的成员变量
%>
```

**示例：**

```jsp
<!--Java代码块-->
<% out.println("这是Java代码块");%>
<hr/>
```

#### 2）JSP 表达式

在 jsp 中，可以使用特定表达式语法，形式为：<font color='red'><b><%=表达式%></b></font>

jsp 在翻译完后是 out.print(表达式内容);

所以：<%out.print("当前时间);%>和<%="当前时间"%>是一样的。

在实际开发中，这种表达式语法用的也很少使用。

**示例：**

```jsp
<!--JSP表达式-->
<%="这是JSP表达式"%><br/>
就相当于<br/>
<%out.println("这是没有JSP表达式输出的");%>
```

#### 3）JSP 声明

在 JSP 中也可以声明一些变量，方法，静态方法，形式为：<font color='red'><b><%! 声明的内容 %></b></font>

使用 JSP 声明需要注意：

```jsp
<%!
	需要注意的是： 写在里面的内容将会被tomcat翻译成全局的属性或者类方法。
%>
```

**示例：**

```jsp
<!--JSP声明-->
<%! String str = "声明语法格式";%>
<%=str%>
```

#### 4）JSP 注释

在使用 JSP 时，它有自己的注释，形式为：<font color='red'><b><%--注释--%></b></font>

需要注意的是：

 在 Jsp 中可以使用 html 的注释，但是只能注释 html 元素，不能注释 java 程序片段和表达式。同时，被 html 注释部分会参与翻译，并且会在浏览器上显示

 jsp 的注释不仅可以注释 java 程序片段，也可以注释 html 元素，并且被 jsp 注释的部分不会参与翻译成.java 文件，也不会在浏览器上显示。

**示例：**

```jsp
<%--JSP注释--%>
<!--HTML注释-->
```

#### 5）语法的示例

**JSP 语法完整示例代码**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP语法</title>
</head>
<body>

<!--Java代码块-->
<% out.println("这是Java代码块");%>
<hr/>

<!--JSP表达式-->
<%="这是JSP表达式"%><br/>
就相当于<br/>
<%out.println("这是没有JSP表达式输出的");%>

<hr/>
<!--JSP声明-->
<%! String str = "声明语法格式";%>
<%=str%>

<hr/>

<%--JSP注释--%>
<!--HTML注释-->

</body>
</html>
```

**JSP 语法运行结果**

![案例jsp3](assets/案例jsp3.png)

### 2.2.2 JSP 指令

#### 1）page 指令

**language:**告知引擎，脚本使用的是 java，默认是 java，支持 java。不写也行。

**extends**：告知引擎，JSP 对应的 Servlet 的父类是哪个，不需要写，也不需要改。

**import**：告知引擎，导入哪些包（类）。

 **注意：引擎会自动导入：java.lang.\*,javax.servlet.\*,javax.servlet.http.\*,javax.servlet.jsp.\***

 **导入的形式：**

 **<%@page import=”java.util.Date,java.util.UUID”%>或者：**

 **<%@page import=”java.util.Date”%>**

 **<%@page import=”java.util.UUID”%>** **用 Eclipse：Alt+/ 自动导入**

**session**：告知引擎是否产生 HttpSession 对象，即是否在代码中调用 request.getSession()。默认是 true。

**buffer**：JspWriter 用于输出 JSP 内容到页面上。告知引擎，设定他的缓存大小。默认 8kb。

**errorPage**：告知引擎，当前页面出现异常后，应该转发到哪个页面上（路径写法：/代表当前应用）

 **小贴士：当在 errorpage 上使用了 isErrorPage=true 之后，ie8 有时候不能正常显示**

 **配置全局错误页面：web.xml**

```xml
<error-page>
    <exception-type>java.lang.Exception</exception-type>
    <location>/error.jsp</location>
</error-page>
<error-page>
    <error-code>404</error-code>
    <location>/404.html</location>
</error-page>
```

 **当使用了全局错误页面，就无须再写 errorPage 来实现转到错误页面，而是由服务器负责跳转到错误页面。**

**isErrorPage**：告知引擎，是否抓住异常。如果该属性为 true，页面中就可以使用 exception 对象，打印异常的详细信息。默认值是 false。

**contentType**：告知引擎，响应正文的 MIME 类型。contentType="text/html;charset=UTF-8"

 相当于 response.setContentType("text/html;charset=UTF-8");

**pageEncoding**：告知引擎，翻译 jsp 时（从磁盘上读取 jsp 文件）所用的码表。pageEncoding="UTF-8"相当于告知引擎用 UTF-8 读取 JSP

**isELIgnored\***：告知引擎，是否忽略 EL 表达式，默认值是 false，不忽略。

#### 2）include 指令

语法格式：<%@include file="" %>该指令是包含外部页面。

属性：file，以/开头，就代表当前应用。

**使用示例**

![静态包含1](assets/静态包含1.png)

**静态包含的特点**

![静态包含2](assets/静态包含2.png)

#### 3）taglib 指令

语法格式：<%taglib uri="" prefix=""%>

作用：该指令用于引入外部标签库。html 标签和 jsp 标签不用引入。

属性：

 uri：外部标签的 URI 地址。

 prefix：使用标签时的前缀。

### 2.2.3 JSP 细节

#### 1）九大隐式对象

什么是隐式对象呢？它指的是在 jsp 中，可以不声明就直接使用的对象。它只存在于 jsp 中，因为 java 类中的变量必须要先声明再使用。其实 jsp 中的隐式对象也并非是未声明，只是它是在翻译成.java 文件时声明的。所以我们在 jsp 中可以直接使用。

| 隐式对象名称 | 类型                                   | 备注                             |
| ------------ | -------------------------------------- | -------------------------------- |
| request      | javax.servlet.http.HttpServletRequest  |                                  |
| response     | javax.servlet.http.HttpServletResponse |                                  |
| session      | javax.servlet.http.HttpSession         | Page 指令可以控制开关            |
| application  | javax.servlet.ServletContext           |                                  |
| page         | Java.lang.Object                       | 当前 jsp 对应的 servlet 引用实例 |
| config       | javax.servlet.ServletConfig            |                                  |
| exception    | java.lang.Throwable                    | page 指令有开关                  |
| out          | javax.servlet.jsp.JspWriter            | 字符输出流，相当于 printwriter   |
| pageContext  | javax.servlet.jsp.PageContext          | 很重要                           |

#### 2）PageContext 对象

**简介**

它是 JSP 独有的对象，Servlet 中没有这个对象。本身也是一个域（作用范围）对象，但是它可以操作其他 3 个域对象中的属性。而且还可以获取其他 8 个隐式对象。

**生命周期**

它是一个局部变量，所以它的生命周期随着 JSP 的创建而诞生，随着 JSP 的结束而消失。每个 JSP 页面都有一个独立的 PageContext。

**常用方法**

![PageContext方法详解](assets/PageContext方法详解.png)

在上图中，同学们发现没有页面域操作的方法，其实是定义在了 PageContext 的父类 JspContext 中，如下图所示：

![JspContext](assets/JspContext.png)

#### 3）四大域对象

| 域对象名称     | 范围     | 级别                     | 备注                                     |
| -------------- | -------- | ------------------------ | ---------------------------------------- |
| PageContext    | 页面范围 | 最小，只能在当前页面用   | 因范围太小，开发中用的很少               |
| ServletRequest | 请求范围 | 一次请求或当期请求转发用 | 当请求转发之后，再次转发时请求域丢失     |
| HttpSession    | 会话范围 | 多次请求数据共享时使用   | 多次请求共享数据，但不同的客户端不能共享 |
| ServletContext | 应用范围 | 最大，整个应用都可以使用 | 尽量少用，如果对数据有修改需要做同步处理 |

### 2.2.4 JSP 最佳实战-MVC 模型

**Servlet：**擅长处理业务逻辑，不擅长输出显示界面。在 web 开发中多用于控制程序逻辑（流程）。所以我们称之为：控制器。

**JSP：**擅长显示界面，不擅长处理程序逻辑。在 web 开发中多用于展示动态界面。所以我们称之为：视图。

例如: ![1577355748295](assets/1577355748295.png)

M：model ，通常用于封装数据，封装的是数据模型。

V：view ，通常用于展示数据。动态展示用 jsp 页面，静态数据展示用 html。

C：controller ，通常用于处理请求和响应。一般指的是 Servlet。

# 3 综合案例-学生管理系统升级

## 3.1 案例需求介绍

### 3.1.1 需求 1：页面改造

在讲解 jsp 课程之前，我们的页面都是用 HTML 展示的，HTML 它不能展示动态资源。今天我们将把 HTML 改造为 JSP 展示。

### 3.1.2 需求 2：登录成功后记录用户名密码

当我们首次访问 index.jsp 时，它会让我们登录。当我们登录成功之后，再访问 index.jsp 时，就可以使用新增学生和学生列表的功能了。

## 3.2 案例环境准备

今日案例仍然采用上次课程中的《学生管理系统》案例的代码
