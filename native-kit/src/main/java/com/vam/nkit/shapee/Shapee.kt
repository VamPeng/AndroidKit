package com.vam.nkit.shapee

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.IntDef

/**
 * created on 2022/3/17
 * @author Yuhui
 * @description 主要是依托于GradientDrawable类。
 * 这个就是一个shape生成
 * 后期可以考虑做一个扩展类，专门写常用的shape生成ob
 */
object Shapee {

    @IntDef(*[GradientDrawable.RECTANGLE, GradientDrawable.OVAL, GradientDrawable.LINE, GradientDrawable.RING])
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Shape

    private val map = mutableMapOf<String, GradientDrawable>()

    fun with(): ShapeBuilder {
        return ShapeBuilder()
    }

    fun with(v: View): ShapeBuilder {
        return ShapeBuilder(v)
    }

    class ShapeBuilder internal constructor(private val v: View? = null) {

        private val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
        }

        /**
         * GradientDrawable.RECTANGLE:矩形
         * GradientDrawable.OVAL:椭圆形
         * GradientDrawable.LINE:一条线
         * GradientDrawable.RING:环形（环形试了好久不知为何画不出来）
         */

        /**
         * 默认是 [GradientDrawable.RECTANGLE]
         */
        fun shape(@Shape shapeType: Int): ShapeBuilder {
            drawable.shape = shapeType
            return this
        }

        /**
         * 后期需要改成可以指定四个圆角
         */
        fun corners(radius: Float): ShapeBuilder {
            drawable.cornerRadius = radius
            return this
        }

        /**
         * 指定矩形的四个圆角，[GradientDrawable.getCornerRadii]是一个长度为8的
         */
        fun corners(radii: FloatArray): ShapeBuilder {
            drawable.cornerRadii = radii
            return this
        }

        /**
         * 这里默认每个角的横竖方向的半径是相同的，
         * 一般来说不同的基本没有，有的话自己传floatArray
         */
        fun corners(
            top_left: Float = 0f,
            top_right: Float = 0f,
            bottom_left: Float = 0f,
            bottom_right: Float = 0f
        ): ShapeBuilder {
            return corners(
                floatArrayOf(
                    top_left,
                    top_left,
                    top_right,
                    top_right,
                    bottom_left,
                    bottom_left,
                    bottom_right,
                    bottom_right
                )
            )
        }

        fun solid(
            solidColor: Int
        ): ShapeBuilder {
            drawable.setColor(solidColor)
            return this
        }

        /**
         * 没有加虚线，后期有需求再加
         */
        fun stroke(
            strokeWidth: Int,
            strokeColor: Int,
        ): ShapeBuilder {
            drawable.setStroke(strokeWidth, strokeColor)
            return this
        }

        fun gradient(colors: IntArray): ShapeBuilder {
            drawable.colors = colors
            drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
            return this
        }

        fun size(w: Int, h: Int): ShapeBuilder {
            drawable.setSize(w, h)
            return this
        }

        fun build(): GradientDrawable {
            if (v != null) v.background = drawable
            return drawable
        }

    }

}