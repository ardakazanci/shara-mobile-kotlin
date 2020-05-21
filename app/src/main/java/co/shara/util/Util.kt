package co.shara.util

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.core.content.res.ResourcesCompat
import co.shara.R
import java.util.*

object Util {

    fun setUnderlinedSpan(
        context: Context,
        text: String,
        styledText: String
    ): CharSequence? {
        val content = SpannableString(text)
        val start = text.indexOf(styledText)
        val end = start + styledText.length
        val textColor = ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null)
        content.setSpan(UnderlineSpan(), start, end, 0)
        content.setSpan(ForegroundColorSpan(textColor), start, end, 0)
        return content
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}