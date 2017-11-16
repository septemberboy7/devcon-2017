package me.myatminsoe.devcon2017.mdetect

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView

object MDetect {

    private val TYPE_DEFAULT = 0
    private val TYPE_UNICODE = 1
    private val TYPE_ZAWGYI = 2

    private var isUnicode = TYPE_DEFAULT

    fun init(context: Context) {
        if (isUnicode != TYPE_DEFAULT) {
            Log.i("MDetect", "MDetect was already initialized.")
            return
        }
        val textView = TextView(context, null)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        textView.text = "\u1000"
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val length1 = textView.measuredWidth

        textView.text = "\u1000\u1039\u1000"
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val length2 = textView.measuredWidth

        Log.i("MDetect", length1.toString() + ", " + length2)

        isUnicode = if (length1 == length2) TYPE_UNICODE else TYPE_ZAWGYI
    }

    /**
     * method for getting user's encoding

     * @return whether the device follows myanmar unicode standard
     */
    fun isUnicode(): Boolean {
        if (isUnicode == TYPE_DEFAULT)
            throw UnsupportedOperationException("MDetect was not initialized.")
        return isUnicode == TYPE_UNICODE
    }
}


fun String.toZawgyi(): String = Rabbit.uni2zg(this)
fun String.toUnicode(): String = Rabbit.zg2uni(this)
fun String.getText(): String = if (MDetect.isUnicode()) this else this.toZawgyi()