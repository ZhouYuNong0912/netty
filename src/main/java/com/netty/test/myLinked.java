package com.netty.test;

/**
 * @author 周雨农
 * @date 2020-05-23 15:37
 * @description 手撕链表内部结构
 */
public class myLinked {
    //头节点指针
    private Node head;
    //尾节点指针
    private Node last;
    //链表实际长度
    private int size;

    /**
     * 链表插入元素
     * @param data  插入的元素
     * @param index 插入的位置
     * @throws Exception
     */
    public void insert(int data,int index) throws Exception {
        //位置小于0 或 大于链表长度表示超出节点范围
        if(index <0 || index > size){
            throw new IndexOutOfBoundsException("超出链表节点范围");
        }
        Node insertedNode = new Node(data);
        if (size == 0) {
            //空链表
            head = insertedNode;
            last = insertedNode;
        }else if(index == 0){
            //从链表头部插入元素
            insertedNode.next = head;
            head = insertedNode;
        }else if (size == index){
            //从链表尾部插入元素
            last.next = insertedNode;
            last = insertedNode;
        }else{
            //从链表中间插入元素
            Node prevNode = get(index-1);
            insertedNode.next = prevNode.next;
            prevNode.next = insertedNode;
        }
        size++;

    }

    /**
     * 链表查找元素
     * @param index
     * @return
     * @throws Exception
     */
    public Node get(int index) throws Exception{
        if(index <0 || index>=size){
            throw new IndexOutOfBoundsException("超出了链表的节点范围");
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     *  链表输出
     */
    public void output(){
        Node temp = head;
        while (temp != null){
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    /**
     * 链表删除元素
     * @param index
     * @return
     * @throws Exception
     */
    public Node remove(int index) throws Exception{
        if (index < 0 || index > size) {
            throw  new IndexOutOfBoundsException("超出了链表节点范围");
        }
        Node removeNode = null;
        if(index == 0){
            //删除头节点
            removeNode = head;
            head = head.next;
        }else if (index == size-1){
            //删除尾节点
            Node prevNode = get(index-1);
            removeNode = prevNode.next;
            prevNode.next = null;
            last = prevNode;
        }else{
            //删除中间节点
            Node prevNode = get(index-1);
            Node nextNode = prevNode.next.next;
            removeNode = prevNode.next;
            prevNode.next = nextNode;
        }
        size--;
        return removeNode;
    }

    /**
     * 修改链表中元素的值
     * @param data  需要修改的值
     * @param index 修改元素的下标
     * @throws Exception
     */
    public Node update(int data,int index) throws Exception{
        //先根据下标获取到元素所在的位置
        Node updateNode = get(index);
        //然后将元素的data值替换成需要修改的值
        updateNode.data = data;
        return updateNode;
    }


    public static void main(String[] args) throws Exception{
        myLinked myLinked = new myLinked();
        myLinked.insert(3,0);
        myLinked.insert(7,1);
        myLinked.insert(9,2);
        myLinked.insert(5,3);
        myLinked.insert(6,1);
        //修改链表中的值
        myLinked.update(10,0);
        //删除链表中的值
        myLinked.remove(4);
        myLinked.output();
    }



}
