package com.nettyold.test.sort;

import java.util.Arrays;

/**
 * @author 周雨农
 * @date 2020-05-28 22:23
 * @description 快速排序    分治法
 */
public class quickSort {
    public static void quickSort(int[] arr,int startIndex,int endIndex) {
        //递归条件结束：当startIndex大于等于endIndex时
        if (startIndex >= endIndex) {
            return;
        }
        //利用基准元素位置
        int pivotIndex = partition(arr,startIndex,endIndex);
        //根据基准元素，拆分成两部分进行递归排序
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1,startIndex);
    }

    private static int partition(int[] arr,int startIndex,int endIndex){
        //获取第一个位置(也可以随机选择位置)的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;

        while (left != right) {
            //控制right指针比较并且向右移动
            while (left < right && arr[right] > pivot) {
                right--;
            }
            //控制left指针比较并且向左移动
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指针所指向的元素
            if (left < right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }

        }
        //pivot和指针重合点进行交换
        arr[startIndex] = arr[left];
        arr[right] = pivot;
        return left;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4,4,6,5,3,2,8,1};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

}
