package com.seven.learn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seven.learn.R
import com.seven.learn.util.Constants
import kotlin.random.Random

class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var bottomSheetTitle: TextView
    private var onNextClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Disable tap-outside-dismiss
        isCancelable = false

        //Disable dragging dialog
        val dialog = dialog as? BottomSheetDialog
        dialog?.behavior?.apply {
            this.isHideable = false
            this.isDraggable = false
            this.state = BottomSheetBehavior.STATE_EXPANDED
        }

        bottomSheetTitle = view.findViewById(R.id.bottomSheetTitle)
        //Set random titles
        val isCorrectAnswer = arguments?.getBoolean(IS_CORRECT_ANSWER_KEY) ?: false
        val remarksArray = Constants.REMARKS[isCorrectAnswer]

        bottomSheetTitle.text = remarksArray?.get(Random.nextInt(remarksArray.size))
        bottomSheetTitle.setTextColor(
            view.context.getColor(
                if (isCorrectAnswer) R.color.correct
                else R.color.danger
            )
        )

        view.findViewById<View>(R.id.bottomSheetButton).setOnClickListener {
            dismiss()
            onNextClicked?.invoke()
        }
    }

    companion object {
        const val IS_CORRECT_ANSWER_KEY = "IS_CORRECT_ANSWER"

        fun newInstance(
            isCorrectAnswer: Boolean,
            callback: () -> Unit
        ): BottomSheet = BottomSheet().apply {
            arguments = Bundle().apply {
                putBoolean(IS_CORRECT_ANSWER_KEY, isCorrectAnswer)
            }
            onNextClicked = callback
        }

    }

}