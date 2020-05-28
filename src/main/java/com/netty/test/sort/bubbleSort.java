package com.netty.test.sort;

import java.util.Arrays;

/**
 * @author 周雨农
 * @date 2020-05-26 22:28
 * @description 冒泡排序
 */
public class bubbleSort {
    public static void sort(int array[]) {
        for (int i = 0; i < array.length - 1; i++) {
            //有序标记,让每一轮所循环的值都为true
            boolean isSorted = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                int temp = 0;
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    //因为有元素交换,所以表示不是有序的,标记为false
                    isSorted = false;
                }
            }
            if (isSorted){
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{5,8,3,2,1,9,7,6};
        sort(array);
        System.out.println(Arrays.toString(array));
        System.out.println(1111);
    }
}
