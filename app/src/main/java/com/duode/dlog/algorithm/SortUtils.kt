package com.duode.dlog.algorithm

import java.util.*

/**
 * @author hekang
 * @des
 * @date 2021/3/11 11:46
 */
object SortUtils {

    /**
     * 希尔排序
     *
     * @param array
     * @return
     */
    fun shellSort(array: IntArray): IntArray {
        val len = array.size
        var temp: Int
        var gap = len / 2
        while (gap > 0) {
            for (i in gap until len) {
                temp = array[i]
                var preIndex = i - gap
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex]
                    preIndex -= gap
                }
                array[preIndex + gap] = temp

                println("shellSort--i:$i;temp:$temp;array:${Arrays.toString(array)}")
            }
            gap /= 2
        }
        return array
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    fun mergeSort(array: IntArray): IntArray {
        if (array.size < 2) return array
        val mid = array.size / 2
        val left = Arrays.copyOfRange(array, 0, mid)
        val right = Arrays.copyOfRange(array, mid, array.size)
        return merge(mergeSort(left), mergeSort(right))
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    private fun merge(left: IntArray, right: IntArray): IntArray {
        val result = IntArray(left.size + right.size)
        var index = 0
        var i = 0
        var j = 0
        while (index < result.size) {
            if (i >= left.size) result[index] = right[j++] else if (j >= right.size) result[index] =
                left[i++] else if (left[i] > right[j]) result[index] =
                right[j++] else result[index] =
                left[i++]
            index++
        }
        return result
    }

    /**
     * 快速排序
     *
     * */
    fun quickSort(array: IntArray){

//        val pivotV=array[]




    }
}