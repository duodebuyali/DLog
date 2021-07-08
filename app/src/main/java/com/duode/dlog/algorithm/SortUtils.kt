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
     * 插入排序
     *  从左往右取值，依次与自己左边的数据进行对比，如果比自己大，就向后移一位，如果对比到最后一个了，右移一位的同时需要把值赋值给第一位；
     *  反之如果是比自己小，把当前值赋值给对比值的上一位。
     * */
    fun insertSort(array: IntArray) {

        a@ for (x in array.indices) {//(8, 1, 2, 44, 4, 656, 0, 33, 1, 5)
            var y = x - 1
            val v = array[x]
            b@ while (y >= 0) {
                if (array[y] > v) {//比取出来对比的数更大，后移一位
                    array[y + 1] = array[y]
                    if (y == 0) {//如果是最左边一个，表示v是最小的不再需要进行对比，直接赋值给第一位
                        array[y] = v
                    }
                } else {
                    array[y + 1] = v
                    println("insertSort--i:$x;y:$y;array:${Arrays.toString(array)}")
                    break@b//不需要再对左边的进行比较，当前被对比的数的上一位就是他的位置
                }
                println("insertSort--i:$x;y:$y;array:${Arrays.toString(array)}")
                y--
            }
        }
    }


    /**
     * 快速排序
     *
     * */
    fun quickSort(array: IntArray) {

//        val pivotV=array[]


    }
}