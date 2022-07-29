package com.mgsoftware.flatframelayout.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.mgsoftware.flatframelayout.R
import org.mini2Dx.gdx.math.Rectangle

/**
 * base on https://github.com/hoang8f/android-flat-button
 */
class FlatFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val frontPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bounds = Rectangle()
    private val backBounds = Rectangle()
    private val frontBounds = Rectangle()

    private var cornersRadius: Float = 0f
    private var shadowHeight: Float = 0f

    private var interceptTouchEvent = false

    init {
        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = Color.BLACK
        strokePaint.strokeWidth = 0f

        val a = context.obtainStyledAttributes(attrs, R.styleable.FlatFrameLayout)

        var key = R.styleable.FlatFrameLayout_FlatFrameLayout_color
        if (a.hasValue(key))
            frontPaint.color = a.getColor(key, frontPaint.color)

        key = R.styleable.FlatFrameLayout_FlatFrameLayout_cornersRadius
        if (a.hasValue(key))
            cornersRadius = a.getDimension(key, cornersRadius)

        key = R.styleable.FlatFrameLayout_FlatFrameLayout_shadowColor
        if (a.hasValue(key))
            backPaint.color = a.getColor(key, backPaint.color)

        key = R.styleable.FlatFrameLayout_FlatFrameLayout_shadowHeight
        if (a.hasValue(key))
            shadowHeight = a.getDimension(key, shadowHeight)

        key = R.styleable.FlatFrameLayout_FlatFrameLayout_borderColor
        if (a.hasValue(key))
            strokePaint.color = a.getColor(key, strokePaint.color)

        key = R.styleable.FlatFrameLayout_FlatFrameLayout_borderWidth
        if (a.hasValue(key))
            strokePaint.strokeWidth = a.getDimension(key, strokePaint.strokeWidth)

        a.recycle()

        updatePadding(top = 0, bottom = shadowHeight.toInt())
        invalidate()

        isFocusable = true
        isClickable = true
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(0f, 0f, w.toFloat(), h.toFloat())
        backBounds.set(bounds)
        frontBounds.set(backBounds)
        frontBounds.height -= shadowHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(
            backBounds.x, backBounds.y,
            backBounds.x + backBounds.width, backBounds.y + backBounds.height,
            cornersRadius, cornersRadius,
            backPaint
        )

        canvas.drawRoundRect(
            frontBounds.x, frontBounds.y,
            frontBounds.x + frontBounds.width, frontBounds.y + frontBounds.height,
            cornersRadius, cornersRadius,
            frontPaint
        )

        if (strokePaint.strokeWidth > 0f) {
            canvas.drawRoundRect(
                backBounds.x, backBounds.y,
                backBounds.x + backBounds.width, backBounds.y + backBounds.height,
                cornersRadius, cornersRadius,
                strokePaint
            )
            canvas.drawRoundRect(
                frontBounds.x, frontBounds.y,
                frontBounds.x + frontBounds.width, frontBounds.y + frontBounds.height,
                cornersRadius, cornersRadius,
                strokePaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            interceptTouchEvent = true
        }

        if (interceptTouchEvent) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    backBounds.y += shadowHeight
                    backBounds.height -= shadowHeight
                    frontBounds.y += shadowHeight

                    updatePadding(top = shadowHeight.toInt(), bottom = 0)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!bounds.contains(event.x, event.y)) {
                        event.action = MotionEvent.ACTION_CANCEL
                        interceptTouchEvent = false

                        internalActionUp()
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                    internalActionUp()
                }
            }
        }
        if (event.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_CANCEL) {
            interceptTouchEvent = false
        }

        return super.onTouchEvent(event) && interceptTouchEvent
    }

    private fun internalActionUp() {
        backBounds.y -= shadowHeight
        backBounds.height += shadowHeight
        frontBounds.y -= shadowHeight

        updatePadding(top = 0, bottom = shadowHeight.toInt())
        invalidate()
    }
}