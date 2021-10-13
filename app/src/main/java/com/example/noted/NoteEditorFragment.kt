package com.example.noted


import OpenWeatherFetchr
import android.Manifest
import android.annotation.SuppressLint
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
import android.os.Build
import android.text.*
import android.text.Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import java.util.*


private const val ARG_FOLDER_TITLE = "folder_title"
private const val ARG_ID = "note_id"

private var TAG: String = "NotedApp"
val locationPermissionCode = 1


class NoteEditorFragment : Fragment() {
    @SuppressLint("RestrictedApi")


    lateinit var note: Note
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
    private val noteEditorViewModel: NoteEditorViewModel by lazy {
        ViewModelProviders.of(this).get(NoteEditorViewModel::class.java)
    }


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

        val noteId: UUID = arguments?.getSerializable(ARG_ID) as UUID
        noteEditorViewModel.loadNote(noteId)
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
            if (l != null && location.text != null) {
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
        italicsButton.setOnClickListener() {
            Log.d(TAG, "italics button clicked")
            italics(it)
        }
        val folderTitle: String = arguments?.getSerializable(ARG_FOLDER_TITLE) as String

        notesListBackButton.setOnClickListener{ view: View ->
            Log.d(TAG, "note back button pressed")
            callbacks?.backToNoteList(folderTitle)
            Log.d(TAG, "backToNoteList finished")

        }
        shareButton.apply{

            setOnClickListener{
                Log.d(TAG, "share button clicked")
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                var body = editNote.text
                var text = (editNoteTitle.text.toString()) + "\n " + location.text.toString() + "\n" + body
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Sharing Note"));
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteEditorViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note
                    updateUI()
                }
            })
    }

    private fun updateUI() {
        editNoteTitle.setText(note.title)
        location.text = note.location   // TODO: make location not update

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            editNote.setText(Html.fromHtml(note.body, TO_HTML_PARAGRAPH_LINES_INDIVIDUAL))
        }else{
            editNote.setText(Html.fromHtml(note.body))
        }
    }

    override fun onStop(){
        super.onStop()
        note.title = editNoteTitle.text.toString()
        note.location = location.text.toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            note.body = Html.toHtml(editNote.text, TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
        }else{
            note.body = Html.toHtml(editNote.text)
        }
        noteEditorViewModel.saveNote(note)
    }

    companion object {
        fun newInstance(id: UUID): NoteEditorFragment {
            val args = Bundle().apply {
                putSerializable(ARG_ID, id)
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