package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

var TAG: String = "NotedApp"

class FolderListFragment : Fragment() {
    interface Callbacks{
        fun onFolderSelected(folderName: String)
    }
    private var callbacks: Callbacks? = null
    private var adapter: FolderAdapter? = FolderAdapter(emptyList())

    private val folderListViewModel: FolderListViewModel by lazy {
        ViewModelProviders.of(this).get(FolderListViewModel::class.java)
    }
    private lateinit var folderListRecyclerView: RecyclerView
    private lateinit var addFolderButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total folders: ${folderListViewModel.folders.size}")

        addFolderButton.setOnClickListener{ view ->
            // add folder

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder_list, container, false)
    }

    private inner class FolderHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val folderLabelView: TextView = itemView.findViewById(R.id.folderLabel)
        val folderMenuView: Button = itemView.findViewById(R.id.folderMenu)
    }

    private inner class FolderAdapter(var folders: List<Folder>)
        : RecyclerView.Adapter<FolderHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : FolderHolder {
            val view = layoutInflater.inflate(R.layout.fragment_folder_list, parent, false)
            return FolderHolder(view)
        }
        override fun getItemCount() = folders.size
        override fun onBindViewHolder(holder: FolderHolder, position: Int) {
            val folder = folders[position]
            holder.apply {
                folderLabelView.text = folder.title
            }
        }
    }

    companion object {
        fun newInstance(): FolderListFragment{
            return FolderListFragment()
        }
    }
}