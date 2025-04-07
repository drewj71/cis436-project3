package com.example.cis436_project3

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private val viewModel: TopViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.catSpinner)

        viewModel.catBreeds.observe(viewLifecycleOwner) { breeds ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, breeds)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val catService = CatService(requireContext())
        catService.getBreeds(
            onSuccess = { breeds -> viewModel.setBreeds(breeds) },
            onError = { error -> viewModel.setError(error) }
        )
    }
}