package com.example.vxplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vxplayer.databinding.FragmentFoldersBinding

class FoldersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        val binding = FragmentFoldersBinding.bind(view)
        binding.rvFolderView.setHasFixedSize(true)
        binding.rvFolderView.setItemViewCacheSize(10)
        binding.rvFolderView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFolderView.adapter = FolderAdapter(requireContext(), MainActivity.folderList)
        binding.tvTotalFolders.text = "Total Folders: ${MainActivity.folderList.size}"
        return view
    }

}