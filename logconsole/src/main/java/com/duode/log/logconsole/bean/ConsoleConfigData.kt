package com.duode.log.logconsole.bean

import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import androidx.annotation.FloatRange
import com.duode.log.logconsole.annotation.ConsoleGravity

/**
 * @author hekang
 * @des
 * @date 2020/9/16 14:01
 */
data class ConsoleConfigData(
    @ConsoleGravity
    var gravity: Int = Gravity.BOTTOM,
    @FloatRange(from = 0.3, to = 1.0)
    var widthRadio: Float = 1f,
    @FloatRange(from = 0.3, to = 1.0)
    var heightRadio: Float = 0.5f,
    var queryConfigData: QueryConfigData
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readFloat(),
        source.readFloat(),
        source.readParcelable<QueryConfigData>(QueryConfigData::class.java.classLoader)
            ?: QueryConfigData()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(gravity)
        writeFloat(widthRadio)
        writeFloat(heightRadio)
        writeParcelable(queryConfigData, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ConsoleConfigData> =
            object : Parcelable.Creator<ConsoleConfigData> {
                override fun createFromParcel(source: Parcel): ConsoleConfigData =
                    ConsoleConfigData(source)

                override fun newArray(size: Int): Array<ConsoleConfigData?> = arrayOfNulls(size)
            }
    }
}