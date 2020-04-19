package com.imooc.wechatshop.demo;


import lombok.extern.slf4j.Slf4j;

/**
 * 各种排序算法
 */
@Slf4j
public class SortAlgorithm {

    /**
     * 待排序数组
     */
    public int[] sortArray = {9,1,5,8,3,7,4,6,2};

    /**
     * 排序经常用到的两个值交换方法
     */
    private void swap(int[] array,int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 冒泡排序：
     * 冒泡排序是一种交换排序，他的基本思想是,重复地走访过要排序的元素列，依次比较两个相邻的元素如果反序则交换，直到没有反序记录为止；
     */
    public void bubbleSort0(int[] array){

        for(int i=0;i < array.length;i++) {

            for (int j = i + 1; j < array.length; j++) {
                //如果数组中的前一个数值比后面的大 则交换两个数值
                if (array[i] > array[j]) {
                    swap(array, i, j);
                }
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }

    //冒泡优化，这次下面的比较从后往前比较 在数字较多的时候有很大提升
    public void bubbleSort1(int[] array){

        for(int i=0;i < array.length;i++) {

            for (int j = array.length-1; j > i; j--) {
                //如果数组中的前一个数值比后面的大 则交换两个数值
                if (array[j-1] > array[j]) {
                    swap(array,j-1, j);
                }
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }

    //冒泡优化，这次在数字顺序已经很有序的情况下有很大提升
    public void bubbleSort2(int[] array){

        Boolean flag = true;
        for(int i=0;i < array.length && flag;i++) {
            flag = false;
            for (int j = array.length-1; j > i; j--) {
                //如果数组中的前一个数值比后面的大 则交换两个数值
                if (array[j-1] > array[j]) {
                    swap(array,j-1, j);
                    flag = true;
                }
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }


    /**
     * 简单选择排序：
     * 通过n-i次关键字比较,从n-i+1个记录中选出关键字最小的记录，并和第i个记录交换
     * @param array
     */
    public void selectSort(int[] array){

        for (int i = 0; i < array.length; i++) {
            //将当前下标的值定义为最小的值
            int min = i;
            for (int j = i+1; j < array.length; j++) {
                if(array[min] > array[j]){
                    min = j;
                }
            }
            if(i != min){
                swap(array,i,min);
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }

    /**
     * 直接插入排序：(像扑克牌一样)
     * 直接插入排序是将一个记录插入到已经排好序的有序表中从而得到一个新的，记录增加1的有序表
     * @param array
     */
    public int[] insertSort(int[] array){

        //外层循环确定待比较数值,必须i=1，因为开始从第二个数与第一个数进行比较
        for (int i=1; i<array.length; i++) {

            //待比较数值
            int temp = array[i];
            //内层循环为待比较数值确定其最终位置
            int j = i - 1;
            //待比较数值比前一位置小，应插往前插一位
            for (;j>=0 && array[j]>temp; j--) {

                //将大于temp的值整体后移一个单位
                array[j+1] = array[j];
            }
            array[j+1] = temp; //待比较数值比前一位置大，最终位置无误
        }
        return array;
    }

    public static void main(String[] args) {
        SortAlgorithm sortAlgorithm = new SortAlgorithm();

//        sortAlgorithm.bubbleSort2(sortAlgorithm.sortArray);

        sortAlgorithm.selectSort(sortAlgorithm.sortArray);

//        int[] array = sortAlgorithm.insertSort(sortAlgorithm.sortArray);
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i]);
//            if(i == array.length-1){
//                System.out.println();
//            }
//        }
    }
}
