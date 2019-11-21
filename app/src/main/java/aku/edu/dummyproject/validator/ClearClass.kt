package aku.edu.dummyproject.validator

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner

import androidx.cardview.widget.CardView

/**
 * Created by ali.azaz on 03/19/18.
 */

object ClearClass {

    fun ClearRadioButton(container: LinearLayout, rdGrp: RadioGroup) {
        if (rdGrp.checkedRadioButtonId == -1) {

            rdGrp.clearCheck()
            for (i in 0 until container.childCount) {
                val v = container.getChildAt(i)
                if (v is RadioButton) {
                    v.setEnabled(false)
                }
            }
        }
    }

    fun ClearRadioButton(container: LinearLayout, rdGrp: RadioGroup, othertxt: EditText) {
        if (rdGrp.checkedRadioButtonId == -1) {
            rdGrp.clearCheck()
            othertxt.text = null

            for (i in 0 until container.childCount) {
                val v = container.getChildAt(i)
                if (v is RadioButton) {
                    v.setEnabled(false)
                }
            }
        }
    }

    fun ClearCheckBoxes(container: LinearLayout) {
        for (i in 0 until container.childCount) {
            val v = container.getChildAt(i)
            if (v is CheckBox) {
                v.isChecked = false
                v.setEnabled(false)
            }
        }
    }

    fun ClearCheckBoxes(container: LinearLayout, othertxt: EditText) {

        othertxt.text = null

        for (i in 0 until container.childCount) {
            val v = container.getChildAt(i)
            if (v is CheckBox) {
                v.isChecked = false
                v.setEnabled(false)
            }
        }
    }

    fun ClearAllFields(container: View, flag: Boolean?) {
        for (i in 0 until (container as ViewGroup).childCount) {
            val v = container.getChildAt(i)
            if (v is CheckBox) {
                v.isChecked = false
                v.error = null
                if (flag != null)
                    v.setEnabled(flag)

            } else if (v is RadioGroup) {
                v.clearCheck()
                if (flag != null) {
                    for (j in 0 until v.childCount) {
                        v.getChildAt(j).isEnabled = flag
                    }
                }

            } else if (v is EditText) {
                v.text = null
                v.error = null
                v.clearFocus()

                if (flag != null)
                    v.setEnabled(flag)

            } else if (v is RadioButton) {
                if (flag != null)
                    v.setEnabled(flag)
            } else if (v is Spinner) {
                v.setSelection(0)
                if (flag != null)
                    v.setEnabled(flag)
            } else if (v is CardView) {
                ClearAllFields(v, flag)
            } else if (v is LinearLayout) {
                ClearAllFields(v, flag)
            }

        }
    }


}
