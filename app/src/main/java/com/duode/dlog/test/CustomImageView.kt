package com.duode.dlog.test

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * @author hekang
 * @des
 * @date 2020/12/22 15:03
 */

@BindingMethods(value = [
    BindingMethod(type = CustomImageView::class, attribute =
    "loadImage",
        method = "setImage")]
)
class CustomImageView(ctx: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(ctx, attrs) {

    fun setImage(res: Int) {
        setImageResource(res)
    }
}
