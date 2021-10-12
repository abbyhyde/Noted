package com.example.noted

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import android.widget.*
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.ViewModelProviders

private var TAG: String = "NotedApp"
const val EXTRA_CURRENT_TITLE =
    "com.bignerdranch.android.geoquiz.currentFolderTitle"
const val EXTRA_CURRENT_COLOR =
    "com.bignerdranch.android.geoquiz.currentFolderColor"
const val EXTRA_POSITION =
    "com.bignerdranch.android.geoquiz.currentFolderPosition"
private const val REQUEST_CODE = 0

class FolderPopup : AppCompatActivity() {
    private lateinit var popupTitle: EditText
    private lateinit var popupSaveButton: Button
    private lateinit var popupDeleteButton: Button
    private lateinit var popupColorButtons: RadioGroup
    private lateinit var popupWhiteFolder: RadioButton
    private lateinit var popupGreyFolder: RadioButton
    private lateinit var popupBlueFolder: RadioButton
    private lateinit var popupPurpleFolder: RadioButton

    private var folderPosition: Int = 0
    private lateinit var newFolderName: String
    private lateinit var newFolderColor: String

    private val folderListViewModel: FolderListViewModel by lazy {
        ViewModelProviders.of(this).get(FolderListViewModel::class.java)
    }

    private var darkStatusBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_folder_popup)

        popupTitle = findViewById(R.id.folder_rename)
        popupSaveButton = findViewById(R.id.popup_folder_savebutton)
        popupDeleteButton = findViewById(R.id.popup_folder_deletebutton)
        popupColorButtons = findViewById(R.id.colorRadioButtons)
        popupWhiteFolder = findViewById(R.id.whiteFolderButton)
        popupGreyFolder = findViewById(R.id.greyFolderButton)
        popupBlueFolder = findViewById(R.id.blueFolderButton)
        popupPurpleFolder = findViewById(R.id.purpleFolderButton)

        popupTitle.setText(intent.getStringExtra(EXTRA_CURRENT_TITLE))
        folderPosition = intent.getIntExtra(EXTRA_POSITION, 0)

        popupSaveButton.setOnClickListener{
            // actually set all the things now
//            Log.d(TAG, "inside save onclick")
            newFolderColor = returnColor(popupColorButtons.checkedRadioButtonId)
            newFolderName = popupTitle.text.toString()
            Log.d(TAG, newFolderColor.toString() + "  " + newFolderName)

            onBackPressed()
            Log.d(TAG, "urgh")
        }
        popupDeleteButton.setOnClickListener{
            // delete folder
        }
        popupWhiteFolder.setOnClickListener{ view: View ->
            popupColorButtons.clearCheck()
            popupColorButtons.check(view.id)
        }
        popupGreyFolder.setOnClickListener{ view: View ->
            popupColorButtons.clearCheck()
            popupColorButtons.check(view.id)
        }
        popupBlueFolder.setOnClickListener{ view: View ->
            popupColorButtons.clearCheck()
            popupColorButtons.check(view.id)
        }
        popupPurpleFolder.setOnClickListener{ view: View ->
            popupColorButtons.clearCheck()
            popupColorButtons.check(view.id)
        }

        // Set the Status bar appearance for different API levels
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If you want dark status bar, set darkStatusBar to true
                if (darkStatusBar) {
                    this.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                this.window.statusBarColor = Color.TRANSPARENT
                setWindowFlag(this, false)
            }
        }
    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        Log.d(TAG, "inside onBackPressed")
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
//                intent.putExtra(EXTRA_CURRENT_TITLE, newFolderName)
//                intent.putExtra(EXTRA_CURRENT_COLOR, newFolderColor)
//                intent.putExtra(EXTRA_POSITION, position)
//                setResult(REQUEST_CODE, intent)
//                finish()
                val data = Intent().apply {
                    putExtra(EXTRA_CURRENT_TITLE, newFolderName)
                    putExtra(EXTRA_CURRENT_COLOR, newFolderColor)
                }
                setResult(Activity.RESULT_OK, data)
                Log.d(TAG, "after setResult")
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

    private fun returnColor(radioButtonId: Int): String {
        when(radioButtonId) {
            2131231235 -> return "white" // white
            2131231226 -> return "blue" // blue
            2131231228 -> return "gray" // gray
            2131231233 -> return "purple" // purple
        }

        return "white"
    }

    companion object {
        fun newIntent(packageContext: Context, currentFolderTitle: String, currentColor: String, position: Int): Intent {
            return Intent(packageContext, FolderPopup::class.java).apply {
                putExtra(EXTRA_CURRENT_TITLE, currentFolderTitle)
                putExtra(EXTRA_CURRENT_COLOR, currentColor)
                putExtra(EXTRA_POSITION, position)
            }
        }
    }

}