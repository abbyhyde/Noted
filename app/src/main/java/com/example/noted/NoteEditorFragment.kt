package com.example.noted


import OpenWeatherFetchr
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.style.StyleSpan;
import android.location.LocationManager
import android.text.*
import android.text.Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView


private const val ARG_NOTE_TITLE = "note_title"
private const val ARG_FOLDER_TITLE = "folder_title"
private var TAG: String = "NotedApp"
val locationPermissionCode = 1

class NoteEditorFragment : Fragment() {
    lateinit var notesListBackButton: ImageButton
    lateinit var editNoteTitle: EditText
    lateinit var shareButton: ImageButton
    lateinit var editNote: EditText
    lateinit var boldButton: Button
    lateinit var italicsButton: Button
    lateinit var location: TextView
    lateinit var locationManager: LocationManager
    lateinit var fusedLocationClient: FusedLocationProviderClient

    interface Callbacks {
        fun backToNoteList(folderTitle: String)
    }
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.context)

        if ((this.context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED)) {
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    locationPermissionCode
                )
            }
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this.activity as Activity) { l: Location ->
            if (l != null) {
                var lat = l.getLatitude().toString()
                var lon = l.getLongitude().toString()
                Log.d(TAG, "lat = " + lat + " lon: " + lon)
                val openWeatherLiveData: LiveData<LocationData> =
                    OpenWeatherFetchr().getLocation(lat, lon)
                openWeatherLiveData.observe(
                    this,
                    Observer { locationData ->
                        Log.d(TAG, "Response received $locationData")
                        var text = "Location: " + locationData.name
                        location.text = text
                    })
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note_editor, container, false)

        notesListBackButton = view.findViewById(R.id.notesListBackButton) as ImageButton
        editNoteTitle = view.findViewById(R.id.editNoteTitle) as EditText
        shareButton = view.findViewById(R.id.shareButton) as ImageButton
        editNote = view.findViewById(R.id.editNote) as EditText
        boldButton = view.findViewById(R.id.boldButton) as Button
        italicsButton = view.findViewById(R.id.italicsButton) as Button
        location = view.findViewById(R.id.location) as TextView

        boldButton.setOnClickListener(){
            Log.d(TAG, "bold button clicked")
            bold(it)
        }
        italicsButton.setOnClickListener(){
            Log.d(TAG, "italics button clicked")
            italics(it)

        val noteTitle: String = arguments?.getSerializable(ARG_NOTE_TITLE) as String
        val folderTitle: String = arguments?.getSerializable(ARG_FOLDER_TITLE) as String
        Log.d(TAG, "args bundle note title: $noteTitle")
        editNoteTitle.setText(noteTitle)

        noteListBackButton.setOnClickListener{ view: View ->
            // go back to notes list
            // need folder title
            Log.d(TAG, "note back button pressed")
            callbacks?.backToNoteList(folderTitle)
            Log.d(TAG, "backToNoteList finished")

        }

        return view
    }

    override fun onStart(){
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        editNoteTitle.addTextChangedListener(titleWatcher)

        shareButton.apply{

            setOnClickListener{
                Log.d(TAG, "share button clicked")
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                //var body = Html.toHtml(SpannableStringBuilder(editNote.text), TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
                var body = editNote.text
                var text = (editNoteTitle.text.toString()) + "\n " + location.text.toString() + "\n" + body
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Sharing Note"));
            }
        }
    }

    private fun updateUI() {
    }

    companion object {
        fun newInstance(noteTitle: String, folderTitle: String): NoteEditorFragment {
            val args = Bundle().apply {
                putSerializable(ARG_NOTE_TITLE, noteTitle)
                putSerializable(ARG_FOLDER_TITLE, folderTitle)
            }
            return NoteEditorFragment().apply {
                arguments = args

            }
        }
    }

    fun bold (view: View){
        var spannableString: Spannable = SpannableStringBuilder(editNote.text)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), editNote.selectionStart, editNote.selectionEnd, 0)
        editNote.setText(spannableString)
    }

    fun italics(view: View){
        var spannableString: Spannable = SpannableStringBuilder(editNote.text)
        spannableString.setSpan(StyleSpan(Typeface.ITALIC), editNote.selectionStart, editNote.selectionEnd, 0)
        editNote.setText(spannableString)
    }




}