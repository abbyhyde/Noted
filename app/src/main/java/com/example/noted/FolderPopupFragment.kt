package com.example.noted

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*


private var TAG: String = "NotedApp"
private const val ARG_CURRENT_TITLE = "currentFolderTitle"
private const val ARG_CURRENT_COLOR = "currentFolderColor"
private const val ARG_FOLDER_ID = "folderId"

class FolderPopupFragment : Fragment() {
    interface Callbacks {
        fun backToFolderList()
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

    private lateinit var folder: Folder
    private lateinit var popupTitle: EditText
    private lateinit var popupSaveButton: Button
    private lateinit var popupDeleteButton: Button
    private lateinit var popupColorButtons: RadioGroup
    private lateinit var popupWhiteFolder: RadioButton
    private lateinit var popupGreyFolder: RadioButton
    private lateinit var popupBlueFolder: RadioButton
    private lateinit var popupPurpleFolder: RadioButton


    private val folderListViewModel: FolderListViewModel by lazy {
        ViewModelProviders.of(this).get(FolderListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val folderId: UUID = arguments?.getSerializable(ARG_FOLDER_ID) as UUID
        folderListViewModel.loadFolder(folderId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_folder_popup, container, false)
        popupTitle = view.findViewById(R.id.folder_rename)
        popupSaveButton = view.findViewById(R.id.popup_folder_savebutton)
        popupColorButtons = view.findViewById(R.id.colorRadioButtons)
        popupWhiteFolder = view.findViewById(R.id.whiteFolderButton)
        popupGreyFolder = view.findViewById(R.id.greyFolderButton)
        popupBlueFolder = view.findViewById(R.id.blueFolderButton)
        popupPurpleFolder = view.findViewById(R.id.purpleFolderButton)

        popupWhiteFolder.setOnClickListener { view: View ->
            popupColorButtons.check(view.id)
        }
        popupGreyFolder.setOnClickListener { view: View ->
            popupColorButtons.check(view.id)
        }
        popupBlueFolder.setOnClickListener { view: View ->
            popupColorButtons.check(view.id)
        }
        popupPurpleFolder.setOnClickListener { view: View ->
            popupColorButtons.check(view.id)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        folderListViewModel.folderLiveData.observe(
            viewLifecycleOwner,
            Observer { folder ->
                folder?.let {
                    this.folder = folder
                    updateUI()
                }
            })

        popupSaveButton.setOnClickListener {

            folder.color = returnColor(popupColorButtons.checkedRadioButtonId)
            folder.title = popupTitle.text.toString()

            folderListViewModel.saveFolder(folder)
            callbacks?.backToFolderList()
        }
    }

    override fun onStop(){
        super.onStop()
        folder.color = returnColor(popupColorButtons.checkedRadioButtonId)
        folder.title = popupTitle.text.toString()

        folderListViewModel.saveFolder(folder)
    }

    private fun updateUI(){
        popupTitle.setText(folder.title)
    }

    private fun returnColor(radioButtonId: Int): String {
        when (radioButtonId) {
            popupWhiteFolder.id -> return "white" // white
            popupBlueFolder.id -> return "blue" // blue
            popupGreyFolder.id -> return "gray" // gray
            popupPurpleFolder.id -> return "purple" // purple
        }

        return "white"
    }

    companion object {
        fun newInstance(
            currentFolderTitle: String,
            currentColor: String,
        ): FolderPopupFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CURRENT_TITLE, currentFolderTitle)
                putSerializable(ARG_CURRENT_COLOR, currentColor)
            }
            return FolderPopupFragment().apply {
                arguments = args
            }
        }

        fun newInstance(folderID: UUID): FolderPopupFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FOLDER_ID, folderID)
            }
            return FolderPopupFragment().apply {
                arguments = args
            }
        }

    }
}