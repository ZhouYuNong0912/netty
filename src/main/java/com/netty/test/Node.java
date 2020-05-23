package com.netty.test;

/**
 * @author 周雨农
 * @date 2020-05-23 15:48
 * @description 仿链表的实体类
 */
public class Node {
    Node next = null;//下一个结点
    int data;//结点数据
    public Node(int data){
        this.data = data;
    }
}
