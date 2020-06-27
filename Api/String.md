# String

## 字符串的特点

1. Java 程序中所有的双引号字符串，都是 String 类的对象
2. 字符串不可变，它们的值在创建后不能被更改
3. 虽然 String 的值是不可变的，但是它们可以共享
   > 字符串常量池：当使用**双引号创建字符串对象的时候**，系统会检查该字符串是否在字符串常量池中存在
   >
   > - 不存在：创建
   > - 存在：不会重新创建，而是直接使用

## 构造方法

Code

```java
/*
*
*  在 Js 中，String 是一个基本数据类型
*  在 Java 中，它是属于 String 的实例，且存储在 字符串常量池中
*       字符串常量池在堆中
*
* String 属于 java.lang 包
*   lang 包属于核心类，不用我们手动加载，可直接使用下面的类
*
* 字符串最大的特性：不可变，值在创建后不能更改
* */

// 字符串常见构造方法
// 1. 无参构造方法，创建一个空字符串
String str1 = new String();
System.out.println("无参构造 = " + str1);

// 2. 传入一个字符数组
char[] cs = new char[]{'a', 'b', 'c'};
String cStr = new String(cs);
System.out.println("传入字符数组构造 = " + cStr);

// 3. 传入一个字符串
/*
* 这种方式其实是创建了两个字符串对象
*   new 在堆中创建了一个
*   参数 "abc" 又在字符串常量池中创建了一个
*
* 造成内存浪费，不推荐
* */
String strStr = new String("abc");
System.out.println("strStr = " + strStr);

// 4. 字面量字符串
String str = "abc";
System.out.println("str = " + str);

```

## 字面量和 new 创建区别

Code

```java
/*
*
* 使用字面量形式和使用 new 创建字符串的差别
*   使用字面形式的字符串，会保存在字符串常量池中，后续代码再使用这个字符串时，将不会再创建 -- 节省内存
*   使用 new 构造出来的字符串，即使字符串值相同，每次创建都会在堆中开辟空间 -- 浪费内存
*
* */
String s1 = "abc";
String s2 = "abc";

String ns1 = new String("abc");
String ns2 = new String("abc");

System.out.println("s1 == s2 = " + (s1 == s2)); // true -- 字符串常量池中的同一个字符串 -- 地址相同
System.out.println("ns1 == ns2 = " + (ns1 == ns2)); // false -- 堆中的两个对象 -- 地址不同
```

## 字符串拼接

当字符串和变量使用 + 号串联（拼接）的时候，系统底层会自动创建一个**StringBuilder**对象
然后再调用其**append**方法完成拼接
拼接后，再调用其**toString**方法转换为 **String** 类型

```java
String s1 = "abc";
String s2 = "ab";
String s3 = s2 + "c";
System.out.println(s1 == s3); // false

// Java存在常量优化机制，在编译的时候，就会将"a" + "b" + "c" 拼接为 "abc"
String s4 = "a" + "b" + "c";
System.out.println(s1 == s4); // true
```

## 方法

- equals
  - 对比字符串内容是否相等
- equalsIgnoreCase​
  - 对比字符串，忽略大小写
- length
  - 获取字符串长度
- charAt
  - 获取字符串对应索引的字符
- toCharArray
  - 字符串拆分成字符数组
- subString
  - 截取字符串
- replace
  - 替换字符串
- split

  - 切割字符串

# StringBuilder

一个类，一个**序列化**数据的容器，可以将装载任何数据的容器
得到一个 **StringBilder** 类型的字符串
最终数据可以使用 **toString** 方法装换称 **String** 类型的字符串
