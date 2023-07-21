package com.example.student.aboutAlgorithm;

import java.util.HashMap;
import java.util.Map;

//3.算法题，解题如下
public class Test {
    public static void main(String[] args) {
        //第一题测试
//        int[] arr = {1,4,5,7};
//        int target = 11;
//        int[] temp = twoSum(arr,target);
//        System.out.println(printArray(temp));

        //第二题测试
//        int[] num1 = {1,3};
//        int[] num2 = {2,5,9};
//        double temp = findMedian(num1,num2);
//        System.out.println(temp);
    }

    /*
    *  1. Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
    *       You may assume that each input would have exactly one solution,
    *       and you may not use the same element twice.You can return the answer in any order.
    * */
    public static int[] twoSum(int[] nums, int target){
        Map<Integer,Integer> hashmap = new HashMap<>();
        for (int a=0;a<nums.length;a++){
            int temp = target - nums[a];
            if (hashmap.containsKey(temp)){
                return new int[]{hashmap.get(temp),a};
            }else{
                hashmap.put(nums[a],a);
            }
        }
        return null;
    }
    /*
    *    2. Given two sorted arrays nums1 and nums2 of size m and n respectively,
    *       return the median of the two sorted arrays.
    *       The overall run time complexity should be O(log (m+n)).
    * */
    public static double findMedian(int[] num1,int[] num2){
        //当数组1长度大于数组2的时候，交换一下，将小长度的给数组1
        if (num1.length>num2.length){
            int[] temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int m = num1.length;
        int n = num2.length;
        //分割线左边的所有元素需要满足的个数 m+(n-m+1)/2
        int leftNum = (m+n+1)/2;
        //在num1的区间[0, m]里查找恰当的分割线
        //分割线左边的元素要小于右边的元素
        //使得num1[i-1] <= num2[j] && num2[j-1] <= num1[i]
        int left = 0;
        int right = m;

        while (left<right){
            int i = left + (right - left +1) /2;
            int j = leftNum - i;
            if (num1[i-1] > num2[j]){
                //下一轮搜索区间为[left,i-1]
                right = i-1;
            }else {
                //下一轮搜索区间为[i,right]
                //
                left = i;
            }
        }

        int i = left;
        int j = leftNum - i;
        int num1leftMax = i == 0 ? Integer.MIN_VALUE :num1[i-1];
        int num1rightMin = i == m ? Integer.MAX_VALUE :num1[i];
        int num2leftmax = j ==0? Integer.MIN_VALUE :num2[j-1];
        int num2rightmin = j == n ? Integer.MAX_VALUE :num2[j];

        if (((m+n) % 2) == 1 ){
            return Math.max(num1leftMax,num2leftmax);
        }else {
            return (double)((Math.max(num1leftMax,num2leftmax) + Math.min(num1rightMin,num2rightmin)))/2;
        }
    }


    //遍历数组转成字符串方便观看结果
    public static String printArray(int[] nums){
        StringBuffer str = new StringBuffer();
        str.append("[");
        for (int a=0;a<nums.length;a++){
            if (a == nums.length-1){
                str.append(nums[a]);
                str.append("]");
            }else{
                str.append(nums[a]);
                str.append(",");
            }
        }
        return String.valueOf(str);
    }

}
