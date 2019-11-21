package aku.edu.dummyproject.validator

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView

import androidx.cardview.widget.CardView

import com.edittextpicker.aliazaz.EditTextPicker

import java.lang.reflect.Field

import aku.edu.dummyproject.R

object ValidatorClass {

    fun EmptyTextBox(context: Context, txt: EditText, msg: String): Boolean {
        if (TextUtils.isEmpty(txt.text.toString())) {
            txt.error = "This data is Required! "    // Set Error on last radio button
            txt.isFocusableInTouchMode = true
            txt.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(txt.id) + ": This data is Required!"
            )
            return false
        } else {
            txt.error = null
            txt.clearFocus()
            return true
        }

    }

    private fun EmptyEditTextPicker(context: Context, txt: EditText, msg: String): Boolean {
        var messageConv = ""
        var flag = true
        if (!(txt as EditTextPicker).isEmptyTextBox) {
            flag = false
            messageConv = "ERROR(empty)"
        } else if (!txt.isRangeTextValidate) {
            flag = false
            messageConv = "ERROR(range)"
        } else if (!txt.isTextEqualToPattern) {
            flag = false
            messageConv = "ERROR(pattern)"
        }

        if (!flag) {
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(txt.getId()) + ": " + messageConv
            )
            return false
        } else {
            txt.setError(null)
            txt.clearFocus()
            return true
        }

    }

    fun EmptyCardCheckBox(
        context: Context,
        container: CardView,
        cbx: CheckBox,
        msg: String
    ): Boolean {

        var flag: Boolean? = false
        for (i in 0 until container.childCount) {
            val v = container.getChildAt(i)
            if (v is LinearLayout) {
                for (j in 0 until v.childCount) {
                    val view = v.getChildAt(j)
                    if (view is CheckBox) {
                        if (view.isChecked) {
                            flag = true
                            break
                        }
                    }

                }
            }
        }
        if (flag!!) {
            return true
        } else {
            cbx.error = "This data is Required!"    // Set Error on last radio button

            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(cbx.id) + ": This data is Required!"
            )
            return false
        }
    }

    fun EmptyTextBox(context: Context, txt: TextView, msg: String): Boolean {
        if (TextUtils.isEmpty(txt.text.toString())) {
            txt.error = "This data is Required! "    // Set Error on last radio button
            txt.isFocusableInTouchMode = true
            txt.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(txt.id) + ": This data is Required!"
            )
            return false
        } else {
            txt.error = null
            txt.clearFocus()
            return true
        }

    }

    fun RangeTextBox(
        context: Context,
        txt: EditText,
        min: Int,
        max: Int,
        msg: String,
        type: String
    ): Boolean {

        if (Integer.valueOf(txt.text.toString()) < min || Integer.valueOf(txt.text.toString()) > max) {
            txt.error = "Range is $min to $max$type ... "    // Set Error on last radio button
            txt.isFocusableInTouchMode = true
            txt.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(txt.id) + ": Range is " + min + " to " + max + " times...  "
            )
            return false
        } else {
            txt.error = null
            txt.clearFocus()
            return true
        }
    }


    fun RangeTextBox(
        context: Context,
        txt: EditText,
        min: Double,
        max: Double,
        msg: String,
        type: String
    ): Boolean {

        if (java.lang.Double.valueOf(txt.text.toString()) < min || java.lang.Double.valueOf(txt.text.toString()) > max) {
            txt.error = "Range is $min to $max$type ... "    // Set Error on last radio button
            txt.isFocusableInTouchMode = true
            txt.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(txt.id) + ": Range is " + min + " to " + max + " times...  "
            )
            return false
        } else {
            txt.error = null
            txt.clearFocus()
            return true
        }
    }

    fun EmptySpinner(context: Context, spin: Spinner, msg: String): Boolean {
        if (spin.selectedItemPosition == 0) {
            (spin.selectedView as TextView).text = "This Data is Required"
            (spin.selectedView as TextView).setTextColor(Color.RED)
            spin.isFocusableInTouchMode = true
            spin.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(spin.id) + ": This data is Required!"
            )
            return false
        } else {
            (spin.selectedView as TextView).error = null
            return true
        }
    }

    fun EmptyRadioButton(
        context: Context,
        rdGrp: RadioGroup,
        rdBtn: RadioButton,
        msg: String
    ): Boolean {
        if (rdGrp.checkedRadioButtonId == -1) {
            rdBtn.error = "This data is Required!"    // Set Error on last radio button
            rdBtn.isFocusable = true
            rdBtn.isFocusableInTouchMode = true
            rdBtn.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(rdGrp.id) + ": This data is Required!"
            )
            return false
        } else {
            var rdbFlag = true
            for (j in 0 until rdGrp.childCount) {
                val innerV = rdGrp.getChildAt(j)

                if (innerV is RadioButton) {
                    if (!innerV.isChecked) continue
                }

                if (innerV is EditText) {
                    if (getIDComponent(rdGrp.findViewById(rdGrp.checkedRadioButtonId)) == innerV.getTag())
                        if (innerV is EditTextPicker)
                            rdbFlag = EmptyEditTextPicker(
                                context,
                                innerV as EditText,
                                getString(context, getIDComponent(innerV))
                            )
                        else
                            rdbFlag = EmptyTextBox(
                                context,
                                innerV,
                                getString(context, getIDComponent(innerV))
                            )
                }
                if (!rdbFlag) break
            }

            if (rdbFlag) {
                rdBtn.error = null
                rdBtn.clearFocus()
                return rdbFlag
            } else
                return rdbFlag

        }
    }

    fun EmptyRadioButton(
        context: Context,
        rdGrp: RadioGroup,
        rdBtn: RadioButton,
        txt: EditText,
        msg: String
    ): Boolean {
        if (rdGrp.checkedRadioButtonId == -1) {
            rdBtn.error = "This data is Required!"    // Set Error on last radio button
            rdBtn.isFocusable = true
            rdBtn.isFocusableInTouchMode = true
            rdBtn.requestFocus()
            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(rdGrp.id) + ": This data is Required!"
            )
            return false
        } else {
            rdBtn.error = null
            rdBtn.clearFocus()
            if (rdBtn.isChecked) {
                return EmptyTextBox(context, txt, msg)
            } else {
                txt.clearFocus()
                txt.error = null
                return true
            }
        }
    }

    fun EmptyCheckBox(
        context: Context,
        container: LinearLayout,
        cbx: CheckBox,
        msg: String
    ): Boolean {

        var flag: Boolean? = false
        for (i in 0 until container.childCount) {
            val v = container.getChildAt(i)
            if (v is CheckBox) {
                v.error = null

                if (!v.isEnabled) {
                    flag = true
                    continue
                } else {
                    if (!flag!!)
                        flag = false
                }

                if (v.isChecked) {
                    flag = true

                    for (j in 0 until container.childCount) {
                        val innerV = container.getChildAt(j)
                        if (innerV is EditText) {
                            if (getIDComponent(v) == innerV.getTag()) {
                                if (innerV is EditTextPicker)
                                    flag = EmptyEditTextPicker(
                                        context,
                                        innerV as EditText,
                                        getString(context, getIDComponent(innerV))
                                    )
                                else
                                    flag = EmptyTextBox(
                                        context,
                                        innerV,
                                        getString(context, getIDComponent(innerV))
                                    )
                            }
                        }
                    }
                    //                    break;
                }
            }
        }
        if (!flag!!) {
            cbx.error = "This data is Required!"    // Set Error on last radio button

            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(cbx.id) + ": This data is Required!"
            )
            return false
        }
        return true
    }

    fun EmptyCheckBox(
        context: Context,
        container: LinearLayout,
        cbx: CheckBox,
        txt: EditText,
        msg: String
    ): Boolean {

        var flag: Boolean? = false
        for (i in 0 until container.childCount) {
            val v = container.getChildAt(i)
            if (v is CheckBox) {
                if (v.isChecked) {
                    flag = true
                    break
                }
            }
        }
        if (flag!!) {
            cbx.error = null
            //Changed According to J2ME Lint
            return !cbx.isChecked || EmptyTextBox(context, txt, msg)
        } else {
            cbx.error = "This data is Required!"    // Set Error on last radio button

            Log.i(
                context.javaClass.name,
                context.resources.getResourceEntryName(cbx.id) + ": This data is Required!"
            )
            return false
        }
    }

    fun setScrollViewFocus(scrollView: ScrollView) {
        scrollView.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
        scrollView.isFocusable = true
        scrollView.isFocusableInTouchMode = true
        scrollView.setOnTouchListener { v, event ->
            v.requestFocusFromTouch()
            false
        }
    }

    fun EmptyCheckingContainer(context: Context, lv: ViewGroup): Boolean {

        for (i in 0 until lv.childCount) {
            val view = lv.getChildAt(i)

            if (view.visibility == View.GONE || !view.isEnabled)
                continue

            // use tag for some situations
            if (view.tag != null && view.tag == "-1") {
                if (view is EditText)
                    view.error = null
                else if (view is LinearLayout)
                    ClearClass.ClearAllFields(view, null)
                else if (view is CheckBox)
                    view.error = null
                continue
            }

            if (view is CardView) {
                if (!EmptyCheckingContainer(context, view as ViewGroup)) {
                    return false
                }
            } else if (view is RadioGroup) {


                var radioFlag = false
                var v: View? = null
                for (j in 0 until view.childCount) {
                    if (view.getChildAt(j) is RadioButton) {
                        v = view.getChildAt(j)
                        radioFlag = true
                        break
                    }
                }

                if (!radioFlag) continue

                if (v != null) {

                    val asNamed = getString(context, getIDComponent(view))

                    if (!EmptyRadioButton(context, view, v as RadioButton, asNamed)) {
                        return false
                    }
                }
            } else if (view is Spinner) {
                if (!EmptySpinner(context, view, getString(context, getIDComponent(view)))) {
                    return false
                }
            } else if (view is EditText) {
                if (view is EditTextPicker) {
                    if (!EmptyEditTextPicker(
                            context,
                            view as EditText,
                            getString(context, getIDComponent(view))
                        )
                    )
                        return false
                } else {
                    if (!EmptyTextBox(context, view, getString(context, getIDComponent(view)))) {
                        return false
                    }
                }
            } else if (view is CheckBox) {
                if (!view.isChecked) {
                    view.error = getString(context, getIDComponent(view))
                    return false
                }
            } else if (view is LinearLayout) {

                if (view.getTag() != null && view.getTag() == "0") {
                    if (!EmptyCheckBox(
                            context, view,
                            view.getChildAt(0) as CheckBox,
                            getString(context, getIDComponent(view.getChildAt(0)))
                        )
                    ) {
                        return false
                    }
                } else {
                    if (!EmptyCheckingContainer(context, view as ViewGroup)) {
                        return false
                    }
                }

            }
        }
        return true
    }

    fun getIDComponent(view: View): String {
        val idName = view.resources.getResourceName(view.id).split("id/".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()

        return idName[1]
    }

    private fun getString(context: Context, idName: String): String {

        val fields = R.string::class.java.fields
        for (field in fields) {

            if (field.name.split("R\$string.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] == idName) {
                try {
                    val id = field.getInt(R.string::class.java) //id of string

                    return context.getString(id)

                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        }
        return ""
    }

}
