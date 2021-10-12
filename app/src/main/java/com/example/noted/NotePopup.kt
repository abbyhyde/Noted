package com.example.noted

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.graphics.ColorUtils

private var TAG: String = "NotedApp"

class NotePopup : AppCompatActivity() {
    private lateinit var popupEditNoteTitle: EditText
    private lateinit var popupRenameButton: Button
    private lateinit var popupDeleteButton: Button
    private lateinit var popupColorButton: Button

    private var darkStatusBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_note_popup)

        popupRenameButton = findViewById(R.id.popup_note_renamebutton)
        popupDeleteButton = findViewById(R.id.popup_note_deletebutton)
        popupColorButton = findViewById(R.id.popup_note_colorbutton)
        popupEditNoteTitle = findViewById(R.id.note_rename)

        popupRenameButton.setOnClickListener{
            // how to do rename?
        }
        popupDeleteButton.setOnClickListener{
            // delete folder
        }
        popupColorButton.setOnClickListener{
            // recolor folder -> new menu
            Log.d(TAG, "Color button pressed")
            onBackPressed()
        }
        // figure out rename and possible color?

    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }

}