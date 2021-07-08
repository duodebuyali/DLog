package com.duode.ziplibrary

import android.os.Build
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.CompressionMethod
import net.lingala.zip4j.model.enums.EncryptionMethod
import net.lingala.zip4j.progress.ProgressMonitor
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author hekang
 * @des 使用zip4j处理文件的压缩和解压
 * 参考: https://github.com/srikanth-lingala/zip4j
 * @date 2020/12/14 11:38
 */
object ZipUtils {

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     *
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zipFile 指定的ZIP压缩文件
     * @param destPath 解压目录,如果为空将和zipFile同级解压
     * @param psw ZIP文件的密码,传递空表示没有密码
     * @param listener 监听状态
     * @return  解压后文件集合，返回为空，表示解压失败
     */
    fun unzip(
        zipFile: File,
        destPath: String = "",
        psw: String = "",
        listener: OnZipStatusListener? = null
    ): String {
        val zFile = ZipFile(zipFile)
        zFile.charset =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) StandardCharsets.UTF_8 else Charset.defaultCharset()
        if (!zFile.isValidZipFile) {
            listener?.onError(ZipException("压缩文件不合法,可能被损坏."))
            return ""
        }
        val destDir = if (destPath.isEmpty()) {
            val parentDir = zipFile.parentFile
            File(parentDir?.absolutePath ?: "")
        } else
            File(destPath)
        if (destDir.isDirectory && !destDir.exists()) {
            destDir.mkdir()
        }
        if (psw.isNotEmpty() && zFile.isEncrypted) {
            zFile.setPassword(psw.toCharArray())
        }

        if (listener != null) {
            fetchProgressMonitor(zFile, destDir.absolutePath, listener = listener)
        }

        zFile.isRunInThread = false
        zFile.extractAll(destDir.absolutePath)

        return destDir.absolutePath
    }

    /**
     * 获取当前压缩或解压的进度；如果完成回调 压缩之后压缩文件或解压之后的解压文件夹 的地址
     *
     * @param zFile 用来操作解压或压缩的对象
     * @param destPath 压缩之后压缩文件或解压之后的解压文件夹 的地址
     * @param period 获取进度的频率，默认500ms获取一次
     * @param listener 回调解压状态
     * */
    private fun fetchProgressMonitor(
        zFile: ZipFile,
        destPath: String,
        period: Long = 500L,
        listener: OnZipStatusListener
    ) {
        val progressMonitor = zFile.progressMonitor
        listener.onStart()
        Thread {
            var curProgress: Int
            while (true) {
                curProgress = progressMonitor.percentDone
                listener.onProgress(curProgress)

                if (progressMonitor.result == ProgressMonitor.Result.ERROR) {
                    listener.onError(progressMonitor.exception ?: ZipException("解压失败"))
                    break
                }
                if (progressMonitor.result == ProgressMonitor.Result.CANCELLED) {
                    listener.onError(progressMonitor.exception ?: ZipException("解压已取消"))
                    break
                }
                if (progressMonitor.result == ProgressMonitor.Result.SUCCESS) {
                    listener.onCompleted(destPath)
                    break
                }
                Thread.sleep(period)
            }
        }.start()

    }


    /**
     * 使用给定密码压缩指定文件或文件夹到指定位置.
     *
     * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br></br>
     * 如果传空则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br></br>
     * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
     * @param srcPath 要压缩的文件或文件夹路径
     * @param destPath 压缩文件存放路径，传递空表示不指定，使用srcPath的来命名
     * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.如果为false,将直接压缩目录下文件到压缩文件.
     * @param psw 压缩使用的密码
     * @param listener 监听状态
     *
     * @return 最终的压缩文件存放的绝对路径,如果为空则说明压缩失败.
     */
    fun zip(
        srcPath: String,
        destPath: String = "",
        isCreateDir: Boolean = true,
        psw: String = "",
        listener: OnZipStatusListener? = null
    ): String {
        var dest = destPath
        val srcFile = File(srcPath)
        dest = buildDestinationZipFilePath(srcFile, dest)
        val parameters = ZipParameters()
        parameters.compressionMethod = CompressionMethod.DEFLATE // 压缩方式
        parameters.compressionLevel = CompressionLevel.NORMAL // 压缩级别
        if (psw.isNotEmpty()) {
            parameters.isEncryptFiles = true
            parameters.encryptionMethod = EncryptionMethod.ZIP_STANDARD // 加密方式
        }
        try {
            val zipFile = if (psw.isNotEmpty()) ZipFile(dest, psw.toCharArray()) else ZipFile(dest)
            if (srcFile.isDirectory) {
                // 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
                if (!isCreateDir) {
                    val subFiles = srcFile.listFiles() ?: arrayOf()
                    val temp = ArrayList<File>()
                    Collections.addAll(temp, *subFiles)

                    if (listener != null) {
                        fetchProgressMonitor(zipFile, dest, listener = listener)
                    }
                    zipFile.addFiles(temp, parameters)

                    return dest
                }

                if (listener != null) {
                    fetchProgressMonitor(zipFile, dest, listener = listener)
                }
                zipFile.addFolder(srcFile, parameters)
            } else {
                if (listener != null) {
                    fetchProgressMonitor(zipFile, dest, listener = listener)
                }
                zipFile.addFile(srcFile, parameters)
            }
            return dest
        } catch (e: ZipException) {
            listener?.onError(e)
        }
        return ""
    }

    /**
     * 构建压缩文件存放路径,如果不存在将会创建
     * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
     * @param srcFile 源文件
     * @param destPath 压缩目标路径
     * @return 正确的压缩文件存放路径
     */
    private fun buildDestinationZipFilePath(srcFile: File, destPath: String): String {
        var destParam = destPath
        if (destParam.isEmpty()) {
            val prefix =
                if (srcFile.parent == null || srcFile.parent!!.isEmpty()) "" else {
                    srcFile.parent!! + File.separator
                }
            destParam = if (srcFile.isDirectory) {
                prefix + srcFile.name + ".zip"
            } else {
                val fileName = srcFile.name.substring(0, srcFile.name.lastIndexOf("."))
                "$prefix$fileName.zip"
            }
        } else {
            createDestDirectoryIfNecessary(destParam) // 在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                val fileName: String = if (srcFile.isDirectory) {
                    srcFile.name
                } else {
                    srcFile.name.substring(0, srcFile.name.lastIndexOf("."))
                }
                destParam += "$fileName.zip"
            }
        }
        return destParam
    }

    /**
     * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
     * @param destPath 指定的存放路径,有可能该路径并没有被创建
     */
    private fun createDestDirectoryIfNecessary(destPath: String) {
        val destDir: File = if (destPath.endsWith(File.separator)) {
            File(destPath)
        } else {
            File(destPath.substring(0, destPath.lastIndexOf(File.separator)))
        }
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
    }

}