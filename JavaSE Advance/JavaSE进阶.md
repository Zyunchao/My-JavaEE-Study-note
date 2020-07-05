# JavaSE 进阶

## 包

如果将所有的类文件都放在同一个包下，不利于管理和后期维护

对于不同功能的类文件，可以放在不同的包下进行管理

**包**：本质上就是**文件夹**

### 包的定义

- 使用 **package** 关键字定义包
- 格式：**`package 包名;`** 如果是多级包，中间用 "." 进行分割

> 在类文件中需要声明其所在的包
>
> **package** 关键字定义（标识）当前文件是属于哪个包的

### 包的注意事项

- **package** 语句必须是程序的第一条**可执行**的代码
- **package** 语句在一个 java 文件中**只能有一个**
- 如果没有 **package**，默认表示无包名

### 类与类之间的访问

- 同一个包下的访问

  > 不需要导包

- 不同包下的访问

  1. import 导包后访问
  2. 通过**全类名**(包名 + 类名) 访问

     > 使用场景：多个包下，出现了重名的类，可以使用这种方式进行区分

> 类的导入就像 js 中模块的导入。

**Demo：**

![类的访问](./img/package-class.png)

```java
// 项目结构如上图
package com.itheima.test;

import com.itheima.doman.Student;
//import com.itheima.test2.Student; // 报错，同名的类即使包不同，也不允许二次导入

public class TestPackage {
    public static void main(String[] args) {
        /*
         * 不同包下的类，使用的时候，使用 import 进行导入
         * */
        Student stu = new Student();
        System.out.println("stu = " + stu);

        /*
         * 同一包下的类，在使用的时候不需要导包
         * */
        Person Jack = new Person("Jack", 24);
        System.out.println("Jack name = " + Jack.getName());

        /*
         *
         * 同一个名字不同包下的类，只允许导入一个
         * 如果要访问第二个同名字的类，则可以使用 全类名的形式
         *   全类名：包名.类名
         * */
        com.itheima.test2.Student stu2 = new com.itheima.test2.Student();
        System.out.println("stu2 = " + stu2);
    }
}
```

## static 关键字

**static** 关键字是静态的意思，是 Java 中的一个**修饰符**，可以修饰**成员方法、成员变量**

- 被 static 修饰的成员变量，一般叫做静态变量
- 被 static 修饰的成员方法，一般叫做静态方法

**static** 修饰的特点

1. 被 static 修饰的成员，会被该类的所有对象所**共享**
2. 被 static 修饰的成员，会随着类的加载（字节码在方法区的加载）而加载，**优先于对象**存在
3. **多了**一种调用方式，可以通过 **类名.成员** 进行调用

**Person.java**

```java
package staticTest;

public class Person {
    String name;
    int age;
    static String school; // static 修饰为 静态属性

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public void show() {
        System.out.println("name：" + name + ", age：" + age + ", school：" + school);
    }
}
```

**PersonTest.java**

```java
package staticTest;

public class PersonTest {
    public static void main(String[] args) {
        Person Jack = new Person();
        /*
         * 没有使用 private 修饰的成员，可以在类外访问
         * */
        Jack.name = "Jack";
        Jack.age = 18;
        Jack.school = "黑马程序员";
        Jack.show(); // name：Jack, age：18, school：黑马程序员

        Person Rows = new Person();
        Rows.show(); // name：null, age：0, school：黑马程序员

        System.out.println("-----------------------");
        Rows.name = "Rows";
        Rows.age = 20;
        Rows.school = "传智专修学院";

        Jack.show(); // name：Jack, age：18, school：传智专修学院
        Rows.show(); // name：Rows, age：20, school：传智专修学院


        /*
         * 在类中，成员使用 static 修饰后，就变成了静态属性、静态方法
         *   在 Js 中，构造函数的静态属性、方法，只能通过 函数.属性 的方式进行添加
         *   且，实例无法访问静态属性、方法
         *   静态属性、方法只能使用 函数.属性名 的方式进行访问
         *   es6 的 class 的实例、静态属性的定义有所改变
         *
         * Java 中，静态属性使用 static 修饰
         *   静态属性是被所有的实例共享的，也就是说，所有的实例都可以访问静态属性
         *   静态属性除了 实例.属性名 的方式访问外，还可以使用 类.属性名 的方式进行访问
         *
         * */
        System.out.println("静态属性访问：");
        System.out.println("Person.school = " + Person.school); // 传智专修学院
    }
}

```

**Ps**:

> static 修饰的静态属性的一个关键特性是所有的**实例共享**的，且可以通过 实例. 的方式进行访问
>
> 静态属性本质上是**属于类**的，只不过实例可以访问

### static 注意事项

- **静态方法**中，**只能访问**静态成员（静态属性、静态方法）

  > 静态方法会随着类的加载而加载，**优先于**对象存在
  >
  > 非静态需要在**创建对象**后，才可以使用
  >
  > 在静态中使用非静态的话，非静态还不存在，无法使用

- **非静态方法**中，可以使用静态成员，也可以使用**非**静态成员

  > 非静态存在的时候，静态成员**已经存在**了

- **静态方法**中，**没有 this** 关键字
  > this 关键字代表**实例的引用**
  >
  > 静态加载的时候还没有实例，没有 this 指向

**简而言之：**

> **静态成员**无法访问实例成员，因为静态成员存在的时候实例成员**还不存在**
>
> **实例成员**可以访问静态成员，因为实例成员存在的时候静态成员**已经存在**了

## 内部类

在一个 class 内部可以继续定义类，在类中定义的类称之为 **“内部类”**

Ps:

> 相当于 Js 函数内声明函数

内部类的访问特点：

- 内部类可以直接访问外部类的成员，包括私有

  > 作用域

- 外部类要使用内部类的成员，必须创建对象

```java
/*
* 1. 普通内部类的构建方式：
*   外部类名.内部类名 变量名 = new 外部类().new 内部类();
* */
OuterWrapper.InnerClass inner = new OuterWrapper().new InnerClass();
inner.show();

// -------------------------------------------------------------------

class OuterWrapper {
    private String name = "外部类 - OuterWrapper";

    // 在类的内部定义的类 = 内部类
    class InnerClass {
        private String name = "内部类 - InnerClass";

        public void show() {
            System.out.println("当前类属于：" + OuterWrapper.this.name);
            System.out.println(name);
            OuterWrapper.this.show();
        }
    }

    public void show() {
        System.out.println("外部类的 show 方法");
    }
}
```

### 内部类成员访问

作用域规则

**就近原则，遮蔽效应**

如果要指定访问外部类的成员，使用

> `外部类.this.成员`

### 修饰成员内部类

```java
/*
 * 2. 静态内部类、私有内部类
 * 首先，内部类是在类的内部声明的
 *   那么，内部声明的类就是属于 外部类的成员
 * 既然是成员，那就可以使用修饰符进行修饰
 *
 * 使用 static 修饰的内部类也就是：静态内部类
 * 使用 private 修饰的内部类也就是：私有内部类
 *
 * */

class OuterWrapper2 {
    private String name = "外部类：OuterWrapper2";
    static String fun = "包裹内部类";

    // 公开的内部类：public 修饰
    public class PublicInner {
        private String name = "公开内部类：PublicInner";

        public void show() {
            System.out.println(OuterWrapper2.this.name + " 的 " + this.name);
        }
    }

    // 静态的内部类：static 修饰
    static class StaticInner {
        private String name = "静态内部类：StaticInner";
        static String innerFun = "创建内部类";

        public void show() {
            /*
             * 在静态内部类中，无法访问到 外部类的 this
             * 静态内部类,随着外部类的加载而加载，那么外部类的this 在内部类中无法访问
             * */
            // System.out.println(OuterWrapper2.this.name + " 的 " + this.name);
            System.out.println(this.name);
        }

        /*
         * static 修饰的静态成员，都是属于类的
         * 都可以使用 类名. 的方式进行访问
         *
         * 外部类.静态内部类.静态成员（变量/方法）
         * */
        static void showFun() {
            /*
             * 内部类的成员访问遵循作用域链的访问规则
             * 就近原则，遮蔽效应
             *
             * 局部作用域 => 内部类成员 => 外部类成员 => 报错
             * */
            System.out.println("静态内部类 StaticInner 执行：");
            System.out.println("外部类的作用：" + fun);
            System.out.println("StaticInner 内部类的作用：" + innerFun);
        }
    }

    // 私有的内部类：private 修饰
    private class PrivateInner {
        /*
         *
         * 类的私有成员
         * private 修饰的成员将只能在本类中访问
         *
         * 内部类可以访问 外部类的私有成员，内部类还是属于外部类的内部的
         * 可以正常访问
         *
         * */
        private String name = "私有内部类：PrivateInner";

        public void show() {
            System.out.println(OuterWrapper2.this.name + " 的 " + this.name);
        }
    }

    /*
    * 私有内部类，是相对于外部类来说私有的
    * 也就代表，私有内部类只能在当前外部类中（外部类的成员方法、其他非静态内部类）使用
    *
    * */
    public void getPrivateInnerInstance() {
        PrivateInner privateInner = new PrivateInner();
        privateInner.show();
    }
}
```

### 局部内部类

在**成员方法中定义**的内部类，只能在方法内使用，不能在方法外部使用

> 各种内部类 == Js 函数的嵌套
>
> 成员访问遵循**作用域链**，就近原则，遮蔽效应

访问权限遵循权限修饰符

### 匿名内部类

```java
/*
 *
 * 匿名内部类
 * 本质上是一个 局部内部类，在局部存活
 *
 * 匿名内部类的前提：需要存在一个接口或者类
 *
 * 产生原因：
 *   当我们要使用一个接口内的方法时，必须要实现这个接口，并在实现类中对接口中的方法进行重写
 *   我们可以使用 new 关键字对接口进行调用，new 关键字调用的时候，可以在调用后面跟上对接口内方法的重写
 *   这样一来，new 调用完之后，就可以直接得到一个接口的实现类的实例了
 *
 *   new 关键字调用的部分，实际上是接口的实现类，但并没有名字，因此成为 “匿名内部类”
 *
 * 我们就省略掉了 创建实现类，构造实现类实例的步骤，简化了接口的使用
 *   按需使用匿名类
 * */

public class LocalInnerClassDemo {
    public static void main(String[] args) {
        /*
         * 匿名内部类
         * 在 new 匿名内部类的时候，有返回值，可以使用 接口/父类 的类型（多态）进行接收
         * */
        Animal cat = new Animal() {
            @Override
            public void eat() {
                System.out.println("猫吃鱼");
            }

            @Override
            public void drink() {
                System.out.println("猫喝奶");
            }

            @Override
            public void run() {
                System.out.println("猫灵活的跑");
            }
        };
        cat.show();
    }
}

interface Animal {
    void eat();

    void drink();

    void run();

    default void show() {
        eat();
        drink();
        run();
    }
}
```

## Lambda 表达式

在我们使用匿名内部类的情况下，如果匿名内部类的 接口中只有一个要重写的方法

那么就可以使用 Lambda 表达式

Lambda 表达式很局限：

- 只能是接口（匿名内部类可以是接口，也可以是类）
- 接口中有且只能有一个方法

**Lambda 表达式的三要素**

- ()：用来接收参数
- ->：指向方法体（用箭头指向后面要做的事）
- {}：方法体（包含一段代码）

**Lambada 表达式的格式**

> (形式参数) -> { 代码块 }

```java

interface Marriage {
    void who();
}

interface MyMath {
    int getSum(int a, int b);
}

// ----------------------------------

public class LocalInnerClassDemo {
    public static void main(String[] args) {
        /*
         * Lambda 表达式
         * */
        Marriage marriage = () -> {
            System.out.println("刘一鸣");
        };
        marriage.who();

        /*
         * Lambda 简写 和 js 中的箭头函数简写规则一致
         * */
        MyMath myMath = (a, b) -> a + b;
        int sum = myMath.getSum(10, 20);
        System.out.println("sum = " + sum);
    }
}
```

## 包装类

将基本数据类型封装成对象的好处在于可以在对象中定义更多的功能方法操作该数据

| 基本数据类型 |    包装类     |
| :----------: | :-----------: |
|   **byte**   |   **Byte**    |
|  **short**   |   **Short**   |
|   **int**    |  **Integer**  |
|   **long**   |   **Long**    |
|  **float**   |   **Float**   |
|  **double**  |  **Double**   |
|   **char**   | **Character** |
| **boolean**  |  **Boolean**  |

Ps:

> Java 中的包装类 == JS 中的数据类型的构造函数（Numeber、String、Boolean...）

### 自定装箱和自动拆箱

**装箱**：把基本数据类型转换为对应的包装类类型

> Integer i = 100;

**拆箱**：把包装类类型转换为对应的基本数据类型

> int i1 = Integer.valueof("10");
