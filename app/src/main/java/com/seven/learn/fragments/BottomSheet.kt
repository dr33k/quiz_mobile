package com.seven.learn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seven.learn.R
import com.seven.learn.util.Constants
import kotlin.random.Random

class BottomSheet(
    val isCorrectAnswer: Boolean = false,
    val callback: () -> Unit
): BottomSheetDialogFragment() {
    private lateinit var bottomSheetTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetTitle = view.findViewById<TextView>(R.id.bottomSheetTitle)

        val remarksArray= Constants.REMARKS[isCorrectAnswer]
        bottomSheetTitle.text = remarksArray?.get(Random.nextInt(remarksArray.size))

        view.findViewById<View>(R.id.bottomSheetButton).setOnClickListener {
            dismiss()
            callback()
        }
    }

}