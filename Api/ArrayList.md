# ArrayList

code

```java
/*
* 集合
* ArrayList 是Java 中的一个类
*
*  它的底层是 数组实现
*
* 相当于给 array 扩展了方法
*
* Array 只能存储固定数量的元素，一个固定的盒子
*
* ArrayList 可以自动扩容，像一个无限的盒子
* */

/*
* 既然是 Java 中的一个类，就需要构造实例
*
* 构造方法
*  ArrayList() 构造一个初始容量为 十 的空列表
*  ArrayList(8)
*
* Java 中的 ArrayList == JS 中的 Array
*
* 直接打印 ArrayList 能够打印出 集合中的内容
* */

ArrayList al1 = new ArrayList();
System.out.println("al1 = " + al1);
// ArrayList 没有 length 属性，使用 size 获取元素个数
/*
* ArrayList 添加元素使用 add
* */
al1.add("123");
al1.add(456);
al1.add(true);
System.out.println("al1 = " + al1);
```

## 常用方法

容器的基本操作：增删改查

- size：获取元素个数
- remove：移除元素
- set：修改制定位置的元素
- get：获取指定位置的元素

```java
public class ListMethodDemo {
    public static void main(String[] args) {
        myUtil myUtil = new myUtil();
        ArrayList<String> strList = new ArrayList();
        for (int i = 0; i < 8; i++) {
            /*
             * 1. 增加元素
             *   add
             * */
            strList.add(myUtil.getRandomStr(4));
        }
        System.out.println("after add strList = " + strList + ", size = " + strList.size());

        // 注意：ArrayList 中的方法都会直接改变原 list（同 Js 的 Array）

        /*
         * 2. 删除元素
         * remove​(int index)
         *   删除指定位置的元素
         *   返回删除的元素
         *
         * remove​(Object o)
         *   删除指定元素
         *   返回 boolean 是否删除成功
         *
         * removeAll​(Collection<?> c)
         * */
        System.out.println();
        System.out.println("删除元素 ---------------------------");
        String delResEle = strList.remove(0); // 返回删除的元素
        boolean delResFlag = strList.remove("abc"); // 是否删除成功
        System.out.println("delResEle = " + delResEle);
        System.out.println("delResFlag = " + (delResFlag ? "删除成功" : "删除失败"));
        System.out.println("after remove strList = " + strList + ", size = " + strList.size());


        /*
         *
         * 3. 修改元素
         *  set​(int index, E element)
         *  用指定的元素替换指定位置的元素
         *  返回被替换的那个元素
         * */
        System.out.println();
        System.out.println("修改元素 ---------------------------");
        String editE = strList.set(3, "TMD");
        System.out.println("editE = " + editE);
        System.out.println("after edit strList = " + strList + ", size = " + strList.size());

        /*
         * 4. 查元素
         *   get​(int index)
         *   返回指定索引的元素
         * */
        System.out.println();
        System.out.println("查元素 ---------------------------");
        String searchRes = strList.get(5);
        System.out.println("searchRes = " + searchRes);


        /*
         * 遍历集合，使用 集合的 size 方法，获取到集合的元素数量
         * 使用for循环
         * 使用结合的 get 方法 获取对应索引得元素
         * */
        System.out.println();
        System.out.println("遍历集合 ArrayList ----------------");
        for (int i = 0; i < strList.size(); i++) {
            String temp = strList.get(i);
            System.out.println("strList[" + i + "] = " + temp);
        }
    }
}
```
