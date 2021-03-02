package com.example.anchorbooks.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anchorbooks.R
import com.example.anchorbooks.databinding.FragmentSecondBinding
import com.example.anchorbooks.ui.adapter.AdapterDetail
import com.example.anchorbooks.viewmodel.ViewModel

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: ViewModel by activityViewModels()
    var idM: String = ""
    var title: String = ""

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            idM = requireArguments().getString("id", "")
            title = requireArguments().getString("title", "")
        }
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

        binding.fabBuy.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:ventas@anchorBooks.cl")
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mailSubject, title, idM))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.mailText, title, idM))
            }
            startActivity(intent)
        }
    }
}