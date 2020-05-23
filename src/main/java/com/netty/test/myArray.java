package com.netty.test;

/**
 * @author 周雨农
 * @date 2020-05-23 9:19
 * @description 手撕数组内部结构
 */
public class myArray{
    //定义一个数组
    private int[] array;
    //定义数组的大小
    private int size;

    public myArray(int capacity){
        //获取传递进来的值，用于规定数组的大小，时间复杂度为O(1)
        this.array = new int[capacity];
        size = 0;
    }

    /**
     * 数组的增加操作
     * @param index    数组的下标
     * @param element  存储的元素
     */
    public void insert(int index,int element) throws  Exception{
        //如果下标是小于0或者大于等于数组的长度,表示数组越界
        if (index < 0 || index > size) {
            throw  new IndexOutOfBoundsException("超出数组实际范围了");
        }
        //超范围插入：数组扩容
        if(size >= array.length){
            resize();
        }

        //如果在数组中间插入元素时,元素向右挪一位
        // 0------3 i = -1  -1>=0 false continue
        // 1------7 i = 0   0 >=1 false continue
        // 2------9 i = 1   1 >=2 false continue
        // 3------5 i = 2   2 >=3 false continue
        // 1------6 i = 3   3 >=1 true
        // 2 >=1 true
        // 1 >=1 true
        // 0 >=1 false continue
        // array{3,7,9,5}
        // array{3,6,7,9,5}
        for (int i = size-1; i >= index ; i--) {
            //array[4](6) = array[3](5)
            //array[3](5) = array[2](9)
            //array[2](6) = array[1](7)
            array[i+1] = array[i];
        }
        //将得到的元素放入到新位置
        //array{3}
        //array{3,7}
        //array{3,7,9}
        //array{3,7,9,5}
        array[index] = element;
        //0,1,2,3
        size++;
    }

    private void resize() {
        //新数组为原来数组长度的2倍
        int[] newArray = new int[array.length*2];
        //将原来数组中的值复制到新的数组中
        //array：原数组
        //srcPos：原数组中的起始位置
        //arrayNew：新数组
        //destPos：新数组中的起始位置
        //array.length：复制的数组元素数量
        System.arraycopy(array,0,newArray,0,array.length);
        this.array = newArray;
    }

    public void  output(){
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        System.out.println("数组长度为：" + array.length);
    }

    /**
     * 数组元素删除
     * @param index
     * @return
     * @throws Exception
     */
    private int delete(int index) throws Exception{
        //判断数组是否越界
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("超出数组实际范围");
        }
        int deleteElement = array[index];
        //从左向右循环,元素逐个向左边挪动一位
        for (int i =index; i < size-1; i++) {
            array[i] = array[i+1];
            //同时将i+1的位置中元素的值补位0
            array[i+1] = 0;
        }
        size--;
        return deleteElement;
    }


    public static void main(String[] args) throws Exception{
        myArray array = new myArray(10);
        array.insert(0,3);
        array.insert(1,7);
        array.insert(2,9);
        array.insert(3,5);
        array.insert(1,6);
        array.output();

        System.out.println("-------------元素删除---------------");
        array.delete(0);
        array.output();

    }



}
