package com.duode.log.logconsole.bean

import android.os.Parcel
import android.os.Parcelable
import com.duode.log.logconsole.consts.ConsoleConst

/**
 * @author hekang
 * @des 配置console显示的数据以及样式
 * @date 2020/9/16 13:46
 *
 * 每个条件，是一个item，每个item配置一些默认选择和自定义选项；
 * 自定义都是另外弹出一个界面，选择当前输入的数据是前缀或后缀或包含(前后缀都可以)
 * 每个item的选项，都是当前flag值和全部以及自定义；如果在自定义输入了数据，item的选项会新增这个数据并选中；当关闭预览console之后，清空之前自定义的数据，展示默认选项
 *
 * 增加一个排序的选项，按时间倒序或正序；按插入即id倒序或正序
 */
data class QueryConfigData(
    var queryFlag: Int = ConsoleConst.NONE_MATCH_FLAG,
    var queryGlobalTag: String = ConsoleConst.NONE_MATCH_GLOBAL_TAG,
    var querySelfTag: String = ConsoleConst.NONE_MATCH_SELF_TAG,
    var queryFileName: String = ConsoleConst.NONE_MATCH_FILE_NAME,
    var queryClassName: String = ConsoleConst.NONE_MATCH_CLASS_NAME,
    var queryMethodName: String = ConsoleConst.NONE_MATCH_METHOD_NAME
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString() ?: ConsoleConst.NONE_MATCH_GLOBAL_TAG,
        source.readString() ?: ConsoleConst.NONE_MATCH_SELF_TAG,
        source.readString() ?: ConsoleConst.NONE_MATCH_FILE_NAME,
        source.readString() ?: ConsoleConst.NONE_MATCH_CLASS_NAME,
        source.readString() ?: ConsoleConst.NONE_MATCH_METHOD_NAME
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(queryFlag)
        writeString(queryGlobalTag)
        writeString(querySelfTag)
        writeString(queryFileName)
        writeString(queryClassName)
        writeString(queryMethodName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QueryConfigData> =
            object : Parcelable.Creator<QueryConfigData> {
                override fun createFromParcel(source: Parcel): QueryConfigData =
                    QueryConfigData(source)

                override fun newArray(size: Int): Array<QueryConfigData?> = arrayOfNulls(size)
            }
    }
}