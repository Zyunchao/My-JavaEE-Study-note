## 1.时间日期类

### 1.1 Date 类（应用）

- 计算机中时间原点

  1970 年 1 月 1 日 00:00:00

- 时间换算单位

  1 秒 = 1000 毫秒

- Date 类概述

  Date 代表了一个特定的时间，精确到毫秒

- Date 类构造方法

  | 方法名                 | 说明                                                               |
  | ---------------------- | ------------------------------------------------------------------ |
  | public Date()          | 分配一个 Date 对象，并初始化，以便它代表它被分配的时间，精确到毫秒 |
  | public Date(long date) | 分配一个 Date 对象，并将其初始化为表示从标准基准时间起指定的毫秒数 |

- 示例代码

  ```java
  public class DateDemo01 {
      public static void main(String[] args) {
          //public Date()：分配一个 Date对象，并初始化，以便它代表它被分配的时间，精确到毫秒
          Date d1 = new Date();
          System.out.println(d1);

          //public Date(long date)：分配一个 Date对象，并将其初始化为表示从标准基准时间起指定的毫秒数
          long date = 1000*60*60;
          Date d2 = new Date(date);
          System.out.println(d2);
      }
  }
  ```

### 1.2 Date 类常用方法（应用）

- 常用方法

  | 方法名                         | 说明                                                         |
  | ------------------------------ | ------------------------------------------------------------ |
  | public long getTime()          | 获取的是日期对象从 1970 年 1 月 1 日 00:00:00 到现在的毫秒值 |
  | public void setTime(long time) | 设置时间，给的是毫秒值                                       |

- 示例代码

  ```java
  public class DateDemo02 {
      public static void main(String[] args) {
          //创建日期对象
          Date d = new Date();

          //public long getTime():获取的是日期对象从1970年1月1日 00:00:00到现在的毫秒值
  //        System.out.println(d.getTime());
  //        System.out.println(d.getTime() * 1.0 / 1000 / 60 / 60 / 24 / 365 + "年");

          //public void setTime(long time):设置时间，给的是毫秒值
  //        long time = 1000*60*60;
          long time = System.currentTimeMillis();
          d.setTime(time);

          System.out.println(d);
      }
  }
  ```

### 1.3 SimpleDateFormat 类（应用）

- SimpleDateFormat 类概述

  ​ SimpleDateFormat 是一个具体的类，用于以区域设置敏感的方式格式化和解析日期。

  ​ 我们重点学习日期格式化和解析

- SimpleDateFormat 类构造方法

  | 方法名                                  | 说明                                                     |
  | --------------------------------------- | -------------------------------------------------------- |
  | public SimpleDateFormat()               | 构造一个 SimpleDateFormat，使用默认模式和日期格式        |
  | public SimpleDateFormat(String pattern) | 构造一个 SimpleDateFormat 使用给定的模式和默认的日期格式 |

- SimpleDateFormat 类的常用方法

  - 格式化(从 Date 到 String)
    - public final String format(Date date)：将日期格式化成日期/时间字符串
  - 解析(从 String 到 Date)
    - public Date parse(String source)：从给定字符串的开始解析文本以生成日期

- 示例代码

  ```java
  public class SimpleDateFormatDemo {
      public static void main(String[] args) throws ParseException {
          //格式化：从 Date 到 String
          Date d = new Date();
  //        SimpleDateFormat sdf = new SimpleDateFormat();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
          String s = sdf.format(d);
          System.out.println(s);
          System.out.println("--------");

          //从 String 到 Date
          String ss = "2048-08-09 11:11:11";
          //ParseException
          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date dd = sdf2.parse(ss);
          System.out.println(dd);
      }
  }
  ```

### 1.4 时间日期类练习 (应用)

- 需求

  秒杀开始时间是 2020 年 11 月 11 日 00:00:00,结束时间是 2020 年 11 月 11 日 00:10:00,用户小贾下单时间是 2020 年 11 月 11 日 00:03:47,用户小皮下单时间是 2020 年 11 月 11 日 00:10:11,判断用户有没有成功参与秒杀活动

- 实现步骤

  1. 判断下单时间是否在开始到结束的范围内
  2. 把字符串形式的时间变成毫秒值

- 代码实现

  ```java
  public class DateDemo5 {
      public static void main(String[] args) throws ParseException {
          //开始时间：2020年11月11日 0:0:0
          //结束时间：2020年11月11日 0:10:0

          //小贾2020年11月11日 0:03:47
          //小皮2020年11月11日 0:10:11

          //1.判断两位同学的下单时间是否在范围之内就可以了。

          //2.要把每一个时间都换算成毫秒值。

          String start = "2020年11月11日 0:0:0";
          String end = "2020年11月11日 0:10:0";

          String jia = "2020年11月11日 0:03:47";
          String pi = "2020年11月11日 0:10:11";

          SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
          long startTime = sdf.parse(start).getTime();
          long endTime = sdf.parse(end).getTime();

  //        System.out.println(startTime);
  //        System.out.println(endTime);
          long jiaTime = sdf.parse(jia).getTime();
          long piTime = sdf.parse(pi).getTime();

          if(jiaTime >= startTime && jiaTime <= endTime){
              System.out.println("小贾同学参加上了秒杀活动");
          }else{
              System.out.println("小贾同学没有参加上秒杀活动");
          }

          System.out.println("------------------------");

          if(piTime >= startTime && piTime <= endTime){
              System.out.println("小皮同学参加上了秒杀活动");
          }else{
              System.out.println("小皮同学没有参加上秒杀活动");
          }

      }

  }
  ```

## 2.JDK8 时间日期类

### 2.1 JDK8 新增日期类 (理解)

- LocalDate 表示日期（年月日）
- LocalTime 表示时间（时分秒）
- LocalDateTime 表示时间+ 日期 （年月日时分秒）

### 2.2 LocalDateTime 创建方法 (应用)

- 方法说明

  | 方法名                                                   | 说明                                                |
  | -------------------------------------------------------- | --------------------------------------------------- |
  | public static LocalDateTime now()                        | 获取当前系统时间                                    |
  | public static LocalDateTime of (年, 月 , 日, 时, 分, 秒) | 使用指定年月日和时分秒初始化一个 LocalDateTime 对象 |

- 示例代码

  ```java
  public class JDK8DateDemo2 {
      public static void main(String[] args) {
          LocalDateTime now = LocalDateTime.now();
          System.out.println(now);

          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 11, 11, 11, 11);
          System.out.println(localDateTime);
      }
  }
  ```

### 2.3 LocalDateTime 获取方法 (应用)

- 方法说明

  | 方法名                          | 说明                        |
  | ------------------------------- | --------------------------- |
  | public int getYear()            | 获取年                      |
  | public int getMonthValue()      | 获取月份（1-12）            |
  | public int getDayOfMonth()      | 获取月份中的第几天（1-31）  |
  | public int getDayOfYear()       | 获取一年中的第几天（1-366） |
  | public DayOfWeek getDayOfWeek() | 获取星期                    |
  | public int getMinute()          | 获取分钟                    |
  | public int getHour()            | 获取小时                    |

- 示例代码

  ```java
  public class JDK8DateDemo3 {
      public static void main(String[] args) {
          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 11, 11, 11, 20);
          //public int getYear()           获取年
          int year = localDateTime.getYear();
          System.out.println("年为" +year);
          //public int getMonthValue()     获取月份（1-12）
          int month = localDateTime.getMonthValue();
          System.out.println("月份为" + month);

          Month month1 = localDateTime.getMonth();
  //        System.out.println(month1);

          //public int getDayOfMonth()     获取月份中的第几天（1-31）
          int day = localDateTime.getDayOfMonth();
          System.out.println("日期为" + day);

          //public int getDayOfYear()      获取一年中的第几天（1-366）
          int dayOfYear = localDateTime.getDayOfYear();
          System.out.println("这是一年中的第" + dayOfYear + "天");

          //public DayOfWeek getDayOfWeek()获取星期
          DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
          System.out.println("星期为" + dayOfWeek);

          //public int getMinute()        获取分钟
          int minute = localDateTime.getMinute();
          System.out.println("分钟为" + minute);
          //public int getHour()           获取小时

          int hour = localDateTime.getHour();
          System.out.println("小时为" + hour);
      }
  }
  ```

### 2.4 LocalDateTime 转换方法 (应用)

- 方法说明

  | 方法名                          | 说明                        |
  | ------------------------------- | --------------------------- |
  | public LocalDate toLocalDate () | 转换成为一个 LocalDate 对象 |
  | public LocalTime toLocalTime () | 转换成为一个 LocalTime 对象 |

- 示例代码

  ```java
  public class JDK8DateDemo4 {
      public static void main(String[] args) {
          LocalDateTime localDateTime = LocalDateTime.of(2020, 12, 12, 8, 10, 12);
          //public LocalDate toLocalDate ()    转换成为一个LocalDate对象
          LocalDate localDate = localDateTime.toLocalDate();
          System.out.println(localDate);

          //public LocalTime toLocalTime ()    转换成为一个LocalTime对象
          LocalTime localTime = localDateTime.toLocalTime();
          System.out.println(localTime);
      }
  }
  ```

### 2.5 LocalDateTime 格式化和解析 (应用)

- 方法说明

  | 方法名                                                    | 说明                                                          |
  | --------------------------------------------------------- | ------------------------------------------------------------- |
  | public String format (指定格式)                           | 把一个 LocalDateTime 格式化成为一个字符串                     |
  | public LocalDateTime parse (准备解析的字符串, 解析格式)   | 把一个日期字符串解析成为一个 LocalDateTime 对象               |
  | public static DateTimeFormatter ofPattern(String pattern) | 使用指定的日期模板获取一个日期格式化器 DateTimeFormatter 对象 |

- 示例代码

  ```java
  public class JDK8DateDemo5 {
      public static void main(String[] args) {
          //method1();
          //method2();
      }

      private static void method2() {
          //public static LocalDateTime parse (准备解析的字符串, 解析格式) 把一个日期字符串解析成为一个LocalDateTime对象
          String s = "2020年11月12日 13:14:15";
          DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
          LocalDateTime parse = LocalDateTime.parse(s, pattern);
          System.out.println(parse);
      }

      private static void method1() {
          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 12, 13, 14, 15);
          System.out.println(localDateTime);
          //public String format (指定格式)   把一个LocalDateTime格式化成为一个字符串
          DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
          String s = localDateTime.format(pattern);
          System.out.println(s);
      }
  }
  ```

### 2.6 LocalDateTime 增加或者减少时间的方法 (应用)

- 方法说明

  | 方法名                                         | 说明           |
  | ---------------------------------------------- | -------------- |
  | public LocalDateTime plusYears (long years)    | 添加或者减去年 |
  | public LocalDateTime plusMonths(long months)   | 添加或者减去月 |
  | public LocalDateTime plusDays(long days)       | 添加或者减去日 |
  | public LocalDateTime plusHours(long hours)     | 添加或者减去时 |
  | public LocalDateTime plusMinutes(long minutes) | 添加或者减去分 |
  | public LocalDateTime plusSeconds(long seconds) | 添加或者减去秒 |
  | public LocalDateTime plusWeeks(long weeks)     | 添加或者减去周 |

- 示例代码

  ```java
  /**
   * JDK8 时间类添加或者减去时间的方法
   */
  public class JDK8DateDemo6 {
      public static void main(String[] args) {
          //public LocalDateTime plusYears (long years)   添加或者减去年

          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 11, 13, 14, 15);
          //LocalDateTime newLocalDateTime = localDateTime.plusYears(1);
          //System.out.println(newLocalDateTime);

          LocalDateTime newLocalDateTime = localDateTime.plusYears(-1);
          System.out.println(newLocalDateTime);
      }
  }
  ```

### 2.7 LocalDateTime 减少或者增加时间的方法 (应用)

- 方法说明

  | 方法名                                          | 说明           |
  | ----------------------------------------------- | -------------- |
  | public LocalDateTime minusYears (long years)    | 减去或者添加年 |
  | public LocalDateTime minusMonths(long months)   | 减去或者添加月 |
  | public LocalDateTime minusDays(long days)       | 减去或者添加日 |
  | public LocalDateTime minusHours(long hours)     | 减去或者添加时 |
  | public LocalDateTime minusMinutes(long minutes) | 减去或者添加分 |
  | public LocalDateTime minusSeconds(long seconds) | 减去或者添加秒 |
  | public LocalDateTime minusWeeks(long weeks)     | 减去或者添加周 |

- 示例代码

  ```java
  /**
   * JDK8 时间类减少或者添加时间的方法
   */
  public class JDK8DateDemo7 {
      public static void main(String[] args) {
          //public LocalDateTime minusYears (long years)  减去或者添加年
          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 11, 13, 14, 15);
          //LocalDateTime newLocalDateTime = localDateTime.minusYears(1);
          //System.out.println(newLocalDateTime);

          LocalDateTime newLocalDateTime = localDateTime.minusYears(-1);
          System.out.println(newLocalDateTime);

      }
  }
  ```

### 2.8 LocalDateTime 修改方法 (应用)

- 方法说明

  | 方法名                                              | 说明                           |
  | --------------------------------------------------- | ------------------------------ |
  | public LocalDateTime withYear(int year)             | 直接修改年                     |
  | public LocalDateTime withMonth(int month)           | 直接修改月                     |
  | public LocalDateTime withDayOfMonth(int dayofmonth) | 直接修改日期(一个月中的第几天) |
  | public LocalDateTime withDayOfYear(int dayOfYear)   | 直接修改日期(一年中的第几天)   |
  | public LocalDateTime withHour(int hour)             | 直接修改小时                   |
  | public LocalDateTime withMinute(int minute)         | 直接修改分钟                   |
  | public LocalDateTime withSecond(int second)         | 直接修改秒                     |

- 示例代码

  ```java
  /**
   * JDK8 时间类修改时间
   */
  public class JDK8DateDemo8 {
      public static void main(String[] args) {
          //public LocalDateTime withYear(int year)   修改年
          LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 11, 13, 14, 15);
         // LocalDateTime newLocalDateTime = localDateTime.withYear(2048);
         // System.out.println(newLocalDateTime);

          LocalDateTime newLocalDateTime = localDateTime.withMonth(20);
          System.out.println(newLocalDateTime);

      }
  }
  ```

### 2.9 Period (应用)

- 方法说明

  | 方法名                                          | 说明                 |
  | ----------------------------------------------- | -------------------- |
  | public static Period between(开始时间,结束时间) | 计算两个“时间"的间隔 |
  | public int getYears()                           | 获得这段时间的年数   |
  | public int getMonths()                          | 获得此期间的总月数   |
  | public int getDays()                            | 获得此期间的天数     |
  | public long toTotalMonths()                     | 获取此期间的总月数   |

- 示例代码

  ```java
  /**
   *  计算两个时间的间隔
   */
  public class JDK8DateDemo9 {
      public static void main(String[] args) {
          //public static Period between(开始时间,结束时间)  计算两个"时间"的间隔

          LocalDate localDate1 = LocalDate.of(2020, 1, 1);
          LocalDate localDate2 = LocalDate.of(2048, 12, 12);
          Period period = Period.between(localDate1, localDate2);
          System.out.println(period);//P28Y11M11D

          //public int getYears()         获得这段时间的年数
          System.out.println(period.getYears());//28
          //public int getMonths()        获得此期间的月数
          System.out.println(period.getMonths());//11
          //public int getDays()          获得此期间的天数
          System.out.println(period.getDays());//11

          //public long toTotalMonths()   获取此期间的总月数
          System.out.println(period.toTotalMonths());//347

      }
  }
  ```

### 2.10 Duration (应用)

- 方法说明

  | 方法名                                           | 说明                 |
  | ------------------------------------------------ | -------------------- |
  | public static Durationbetween(开始时间,结束时间) | 计算两个“时间"的间隔 |
  | public long toSeconds()                          | 获得此时间间隔的秒   |
  | public int toMillis()                            | 获得此时间间隔的毫秒 |
  | public int toNanos()                             | 获得此时间间隔的纳秒 |

- 示例代码

  ```java
  /**
   *  计算两个时间的间隔
   */
  public class JDK8DateDemo10 {
      public static void main(String[] args) {
          //public static Duration between(开始时间,结束时间)  计算两个“时间"的间隔

          LocalDateTime localDateTime1 = LocalDateTime.of(2020, 1, 1, 13, 14, 15);
          LocalDateTime localDateTime2 = LocalDateTime.of(2020, 1, 2, 11, 12, 13);
          Duration duration = Duration.between(localDateTime1, localDateTime2);
          System.out.println(duration);//PT21H57M58S
          //public long toSeconds()	       获得此时间间隔的秒
          System.out.println(duration.toSeconds());//79078
          //public int toMillis()	           获得此时间间隔的毫秒
          System.out.println(duration.toMillis());//79078000
          //public int toNanos()             获得此时间间隔的纳秒
          System.out.println(duration.toNanos());//79078000000000
      }
  }
  ```
