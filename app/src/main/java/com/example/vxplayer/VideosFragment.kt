package com.example.vxplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vxplayer.databinding.FragmentVideosBinding


class VideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_videos, container, false)
        val binding = FragmentVideosBinding.bind(view)
        binding.rvView.setItemViewCacheSize(10)
        binding.rvView.setHasFixedSize(true)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = VideoAdapter(requireContext(), MainActivity.videoLIst)
        binding.tvTotalVideos.text = "Total Videos: ${MainActivity.videoLIst.size}"
        return view
    }
}