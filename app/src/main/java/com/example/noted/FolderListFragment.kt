package com.example.noted

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private var TAG: String = "NotedApp"
private const val ARG_FOLDER_ID = "folderId"
private const val REQUEST_CODE = 0

class FolderListFragment : Fragment() {
    interface Callbacks{
        fun onFolderSelected(folderName: String)
        fun onFolderPopup(folderId: UUID)
    }
    private var callbacks: Callbacks? = null
    private var adapter: FolderAdapter? = FolderAdapter(emptyList())

    private val folderListViewModel: FolderListViewModel by lazy {
        ViewModelProviders.of(this).get(FolderListViewModel::class.java)
    }
    private lateinit var folderListRecyclerView: RecyclerView
    private lateinit var addFolderButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    val view = inflater.inflate(R.layout.fragment_folder_list, container, false)
        folderListRecyclerView =
            view.findViewById(R.id.folderListRecyclerView) as RecyclerView
        folderListRecyclerView.layoutManager = LinearLayoutManager(context)
        folderListRecyclerView.adapter = adapter
        addFolderButton = view.findViewById(R.id.addFolderButton) as ImageButton

        addFolderButton.setOnClickListener{ view ->
            folderListViewModel.addFolder(Folder(UUID.randomUUID(), "Folder", "white"))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        folderListViewModel.folderListLiveData.observe(
            viewLifecycleOwner,
            Observer { folders ->
                folders?.let {
                    updateUI(folders)
                }
            })
    }


    private fun updateUI(folders: List<Folder>) {
        adapter = FolderAdapter(folders)
        folderListRecyclerView.adapter = adapter
    }

    private inner class FolderHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var folder: Folder
        private val titleTextView: TextView = itemView.findViewById(R.id.folderLabel)
        private val menuButton: ImageButton = itemView.findViewById(R.id.folderMenu)
        private val folderLayout: ConstraintLayout = itemView.findViewById(R.id.folderConstraintLayout)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        init{
            itemView.setOnClickListener(this)
        }
        fun bind(folder: Folder) {
            this.folder = folder
            titleTextView.text = this.folder.title

            menuButton.setOnClickListener() {
                callbacks?.onFolderPopup(folder.id)
            }
            deleteButton.setOnClickListener(){
                folderListViewModel.deleteFolder(folder)
            }

            Log.d(TAG, folder.color)
            when(folder.color) {
                "white"-> {
                    folderLayout.setBackgroundColor(Color.WHITE)
                    menuButton.setBackgroundColor(Color.WHITE)
                }
                "gray"-> {
                    folderLayout.setBackgroundColor(Color.GRAY)
                    menuButton.setBackgroundColor(Color.GRAY)
                }
                "blue"-> {
                    folderLayout.setBackgroundColor(Color.rgb(189,207,237))
                    menuButton.setBackgroundColor(Color.rgb(189,207,237))
                }
                "purple"-> {
                    folderLayout.setBackgroundColor(Color.rgb(209, 189, 237))
                    menuButton.setBackgroundColor(Color.rgb(209,189,237))
                }
            }
        }
        override fun onClick(v: View?) {
            callbacks?.onFolderSelected(folder.title)
        }
    }

    private inner class FolderAdapter(var folders: List<Folder>)
        : RecyclerView.Adapter<FolderHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : FolderHolder {
            val view = layoutInflater.inflate(R.layout.fragment_folder, parent, false)
            return FolderHolder(view)
        }
        override fun getItemCount() = folders.size
        override fun onBindViewHolder(holder: FolderHolder, position: Int) {
            val folder = folders[position]
            holder.bind(folder)
        }
    }

    companion object {
        fun newInstance(): FolderListFragment{
            return FolderListFragment()
        }
    }
}