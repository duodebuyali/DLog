package com.duode.dlog

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout.*

/**
 * @author hekang
 * @des
 * @date 2021/3/9 13:47
 */
class LayoutActivity : AppCompatActivity() {

    companion object {
        fun getCallIntent(ctx: Context): Intent {
            return Intent(ctx, LayoutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher)

        println("bitmap:${bitmap.density}--${bitmap.byteCount}--${bitmap.allocationByteCount}--${bitmap.width}")

        iv.post {
            val d = iv.drawable as BitmapDrawable
            val srcBp = d.bitmap
            println("iv:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${iv.width}")
        }

        var hasChange = false

        /**
         * http://blog.itpub.net/69912579/viewspace-2715056/
         * 从 高dpi往低的 迁移，其所占的内存不变；
         * 从低往高的迁移，其所占内存以 本身的dpi和高dpi的比值进行增加
         *
         * ImageView设置自适应大小
         * srcBp.density，如果图片的 所放的 位置高于 设备的dpi，那么会转化为 设备的dpi对图片进行压缩(压缩大小=设备dpi/文件dpi),iv.width= icon.width * 压缩大小
         * 如果小于设备的dpi，那么直接使用图片的dpi，保持图片的大小不变，但是 iv.width= icon.width * 设备dpi/文件dpi
         * */
        iv.setOnClickListener {
            iv.setImageResource(if (hasChange) R.drawable.ic_demo else R.drawable.ic_icon)
            hasChange = !hasChange
            iv.post {
                val d = iv.drawable as BitmapDrawable
                val srcBp = d.bitmap
                println("iv:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${it.width}")
            }
        }

        iv_low.post {
            val d = iv_low.drawable as BitmapDrawable
            val srcBp = d.bitmap
            println("iv_low:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${iv_low.width}")
        }
        iv_low.setOnClickListener {
            iv_low.setImageResource(if (hasChange) R.drawable.ic_low else R.drawable.ic_middle)
            hasChange = !hasChange
            iv_low.post {
                val d = iv_low.drawable as BitmapDrawable
                val srcBp = d.bitmap
                println("iv_low:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${it.width}")
            }
        }

        /**
         * nodpi 的图片，其bitmap的大小一直不变（等于图片的原始大小）
         * */
        iv_none.post {
            val d = iv_none.drawable as BitmapDrawable
            val srcBp = d.bitmap
            println("iv_none:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${iv_none.width}")
        }

        iv_none.setOnClickListener {
            iv_none.layoutParams = iv_none.layoutParams.apply {
                width = 72
                height = 72
            }
            iv_none.post {
                val d = iv_low.drawable as BitmapDrawable
                val srcBp = d.bitmap
                println("iv_none:${srcBp.density}--${srcBp.byteCount}--${srcBp.allocationByteCount}--${srcBp.width}--${it.width}")
            }
        }
    }
}