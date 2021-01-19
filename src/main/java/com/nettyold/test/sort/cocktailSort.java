package com.nettyold.test.sort;

import java.util.Arrays;

/**
 * @author 周雨农
 * @date 2020-05-28 21:55
 * @description 鸡尾酒排序
 */
public class cocktailSort {
    public static void sort(int[] arrays) {
        int temp = 0;
        for (int i = 0; i < arrays.length / 2; i++) {
            //有序标记，表示每循环一次都是true
            boolean isSorted = true;
            //奇数论，从左向右比较和交换
            for (int j = 0; j < arrays.length - i - 1; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                    //如果有元素交换,表示不是有序,设置为false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            //偶数轮，从右向左进行比较，重新将isSorted设置为true
            isSorted = true;
            for (int j = arrays.length - i - 1; j > i; j--) {
                if (arrays[j] < arrays[j - 1]) {
                    temp = arrays[j];
                    arrays[j] = arrays[j - 1];
                    arrays[j - 1] = temp;
                    //有元素交换,表示不是为true,设置为false
                    isSorted = false;
                }
            }
            if (isSorted){
                break;
            }
        }
    }

    public static void main(String[] args) {
        int array[] = new int[]{2,3,4,5,6,7,8,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

}
