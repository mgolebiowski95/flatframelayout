package com.example.app.utility

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.inSpans


/**
 * Wrap appended text in [builderAction] in a bold [StyleSpan].
 *
 * @see SpannableStringBuilder.inSpans
 */
inline fun SpannableStringBuilder.typeface(
    typeface: Typeface,
    builderAction: SpannableStringBuilder.() -> Unit
) =
    inSpans(CustomTypefaceSpan(typeface), builderAction = builderAction)