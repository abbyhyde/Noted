package com.example.noted

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private var TAG: String = "NotedApp"

class FolderListFragment : Fragment() {
    interface Callbacks{
        fun onFolderSelected(folderName: String)
    }
    private var callbacks: Callbacks? = null
    private var adapter: FolderAdapter? = null

    private val folderListViewModel: FolderListViewModel by lazy {
        ViewModelProviders.of(this).get(FolderListViewModel::class.java)
    }
    private lateinit var folderListRecyclerView: RecyclerView
    private lateinit var addFolderButton: ImageButton

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "FolderListFragment onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "FolderListFragment onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
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
        Log.d(TAG, "inside onCreate of folderListFragment, Total folders: ${folderListViewModel.folders.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.d(TAG, "inside onCreateView of folderListFragment")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_folder_list, container, false)
        folderListRecyclerView =
            view.findViewById(R.id.folderListRecyclerView) as RecyclerView
        folderListRecyclerView.layoutManager = LinearLayoutManager(context)
//        addFolderButton = view.findViewById(R.id.addFolderButton) as ImageButton
//
//        addFolderButton.setOnClickListener{ view ->
//            // add folder
//
//        }

        updateUI()
        return view
    }

    private fun updateUI() {
        val folders = folderListViewModel.folders
        adapter = FolderAdapter(folders)
        folderListRecyclerView.adapter = adapter
    }

    private inner class FolderHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var folder: Folder
        private val titleTextView: TextView = itemView.findViewById(R.id.folderLabel)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(folder: Folder) {
            this.folder = folder
            titleTextView.text = this.folder.title
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