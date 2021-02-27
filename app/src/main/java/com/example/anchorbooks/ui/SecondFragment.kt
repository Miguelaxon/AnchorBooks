package com.example.anchorbooks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anchorbooks.databinding.FragmentSecondBinding
import com.example.anchorbooks.ui.adapter.AdapterDetail
import com.example.anchorbooks.viewmodel.ViewModel

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterDetail()
        binding.rv2.adapter = adapter
        binding.rv2.layoutManager = GridLayoutManager(context, 1)

        viewModel.returnBookDetail().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.update(it)
            }
        })

        adapter.selectedBookDetail().observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.selectedBookDetail(it.id)
            }
        })
    }
}