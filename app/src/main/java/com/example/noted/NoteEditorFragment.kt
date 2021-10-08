package com.example.noted

import android.graphics.Typeface
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.Button

private var TAG: String = "NotedApp"

class NoteEditorFragment : Fragment() {
    lateinit var notesListBackButton: ImageButton
    lateinit var editNoteTitle: EditText
    lateinit var shareButton: ImageButton
    lateinit var editNote: EditText
    lateinit var boldButton: Button
    lateinit var italicsButton: Button
    lateinit var imageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
        imageButton = view.findViewById(R.id.imageButton) as ImageButton
        boldButton = view.findViewById(R.id.boldButton) as Button
        italicsButton = view.findViewById(R.id.italicsButton) as Button

        boldButton.setOnClickListener(){
            bold(it)
        }
        italicsButton.setOnClickListener(){
            italics(it)
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
                // This space intentionally left blank
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
//                note.title = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        editNoteTitle.addTextChangedListener(titleWatcher)
    }

    companion object {
        fun newInstance() =

            NoteEditorFragment().apply {
                arguments = Bundle().apply {
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