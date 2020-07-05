import java.util.Arrays;

/*
 *
 * 需求问题：
 *   将一个乱序的 int 数组，按照升序（从小到大）或 降序（从大到下）进行排序
 *
 * 冒泡排序：
 *   核心思想：两两比较，交换位置
 * */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {8, 7, 4, 5, 2, 3, 10, 9, 1, 6};

        // 使用 for 循环实现的 冒泡排序
        bubbleSortUseFor(arr, "DOWN");

        // 使用递归实现的 冒泡排序
        // bubbleSort(arr, "UP", 0);
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
    }

    /*
     * 递归解决问题的思路：
     *      把一个复杂的问题层层转化为一个与原问题相似的规模较小的问题来求解
     *      递归策略只需少量的程序就可描述出解题过程所需要的多次重复计算
     *
     *  简而言之：将复杂的问题，拆分成小的且逻辑相似的问题，然后主要关注小的问题的解决方式
     *           然后重复使用这个解决小问题方式，直至解决完大问题
     */
    private static void bubbleSort(int[] arr, String type, int count) {
        /*
         * 推导：
         *   假设升序下：
         *       当我们第一次排序过后，数组中的最后一个数肯定已经是我们需要的数了，那么就没有再对最后一个数进行比较了
         *           也就是，倒数第二个数没有必要在于最后一个数比较，这样一来，减少一次 两两比较 就可以了，也就是对应的减少一次循环即可
         *           对应的，第二次全数据两两比较，我们只需要将循环的判断语句条件减少 1 即可
         *
         *       当执行第二次排序过后，数组中的最后两个数也就是我们想要的结果了，那么后两个数也就不用在进行比较了
         *           也就是 倒数第三个，已经没有必要倒数第二个数进行比较了
         *           对应的，在即将到来的第三次全数据两两比较中，将循环的判断语句再减少 1 即可
         *       如此往复....
         *
         * 结束循环条件：
         * 在排序最初，都是某个数，和数组中的其他数进行比较
         *   它是不用和自身进行比对的，
         *   所以，究竟要使用多少次全数据两两比较，也就等于，数组的长度 -1 次
         *
         * 比如：
         *   数组中有十个数，1 需要和 2, 3, 4, 5.... 比较，不需要和自身相比
         *   那我们就一共需要比较 9 次
         *   满足 “控制比较次数的循环” 条件就是 arr.length - 1 (( length - 1 ) 本身是数组中的最大索引，但是我们所要满足的索引不应该包含最大的那个索引 )
         *   所以满足循环条件为：
         *       (i) < arr.length - 1;
         *   结束循环的条件为：
         *       (i) >= arr.length - 1;
         * */

        if (count >= arr.length - 1) return;

        for (int i = 0; i < arr.length - 1 - count; i++) {
            /*
             * 要进行两两比较，可以实现的方式为：
             *   当前索引位置的元素 和 下一个索引的元素进行比较
             *       当前索引：i
             *       下一索引：i + 1
             *
             * 两两比较的结束条件：
             *   在进行两两比较的时候，由于要获取到当前索引的下一个 (i + 1)
             *      且当比较到倒数第二个数的时候，倒数第二和倒数第一个数也就和倒数第一的数完成了比较
             *      那么，倒数第一的那个数也就不用在进行比较了
             *
             *      故，循环的结束条件为：i < arr.length - 1;
             *          这样既保证了对比的安全性(索引越界)，又能够减少一次比对
             *
             * 进行两两比较的时候：按照给定的规则（升序/降序）对比较的两个数值进行位置的互换
             *   升序：
             *       如果 (i) > (i + 1) 则
             *           temp = (i)      // temp 存储 (i) 的数据
             *           (i) = (i + 1)   // 将 (i + 1) 赋值给 (i)
             *           (i + 1) = temp  // 将 temp(原先 (i) 的值) 赋值给 (i + 1)
             *       完成互换
             *
             *   降序：
             *       如果 (i) < (i + 1) 则
             *           temp = (i)      // temp 存储 (i) 的数据
             *           (i) = (i + 1)   // 将 (i + 1) 赋值给 (i)
             *           (i + 1) = temp  // 将 temp(原先 (i) 的值) 赋值给 (i + 1)
             *       完成互换
             * */

            int temp = arr[i];
            if (type.equals("UP")) {
                // UP 升序
                if (arr[i] > arr[i + 1]) {
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            } else if (type.equals("DOWN")) {
                // DOWN 升序
                if (arr[i] < arr[i + 1]) {
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }

        bubbleSort(arr, type, ++count);
    }

    public static void bubbleSortUseFor(int[] arr, String type) {
        /*
         * 外层循环控制循环次数
         *   循环次数，数组长度 -1 次
         * */
        for (int i = 0; i < arr.length - 1; i++) {
            /*
             * 内层循环进行两两比较
             * */
            for (int j = 0; j < arr.length - 1 - i; j++) {
                int temp = arr[j];
                /*
                 * 每次循环，当前数据与下一数据比较
                 * 交换位置
                 * */
                if (type.equals("UP") && arr[j] > arr[j + 1]) {
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                } else if (type.equals("DOWN") && arr[j] < arr[j + 1]) {
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
