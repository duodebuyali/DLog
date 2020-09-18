package com.duode.log.logconsole.bean

import android.os.Parcel
import android.os.Parcelable
import com.duode.log.logconsole.consts.ConsoleConst

/**
 * @author hekang
 * @des 配置console显示的数据以及样式
 * @date 2020/9/16 13:46
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