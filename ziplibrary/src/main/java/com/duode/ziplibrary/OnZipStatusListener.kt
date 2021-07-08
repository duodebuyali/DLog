package com.duode.ziplibrary

/**
 * @author hekang
 * @des
 * @date 2021/6/23 18:32
 */
interface OnZipStatusListener {

    fun onStart()

    fun onProgress(progress: Int)

    /**
     * @param path 如果是解压，返回的解压之后的文件的根目录；如果是压缩，返回的是.zip文件的地址
     * */
    fun onCompleted(path: String)

    fun onError(exception: Exception)
}