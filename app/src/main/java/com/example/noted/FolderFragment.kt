package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private var TAG: String = "NotedApp"

class FolderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var FolderLabel: TextView
    private lateinit var FolderMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "inside onCreate of FolderFragment")
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        FolderLabel = view.findViewById(R.id.folderLabel);
        FolderMenu = view.findViewById(R.id.folderMenu);

        FolderMenu.setOnClickListener{ view: View ->
            // pop up menu
        }



        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FolderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FolderFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}