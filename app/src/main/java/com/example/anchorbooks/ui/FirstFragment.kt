package com.example.anchorbooks.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anchorbooks.R
import com.example.anchorbooks.databinding.FragmentFirstBinding
import com.example.anchorbooks.ui.adapter.AdapterBooks
import com.example.anchorbooks.viewmodel.ViewModel

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = AdapterBooks()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(context, 1)

        viewModel.listAllBooks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.update(it)
            }
        })

        adapter.selectedBooks().observe(viewLifecycleOwner, Observer {
            it?.let {
                val bundle = Bundle()
                bundle.putString("id", it.id.toString())
                bundle.putString("title", it.title)
                viewModel.selectedBookDetail(it.id)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        })
    }
}