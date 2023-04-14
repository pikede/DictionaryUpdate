package com.example.urbandictionaryn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionaryn.databinding.FragmentWordBinding
import com.example.urbandictionaryn.models.WordDefinitions
import com.example.urbandictionaryn.utils.SortTypeEnum
import com.example.urbandictionaryn.viewmodel.UrbanDictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WordFragment : Fragment(), DataAdapter.WordDetailClickListener {

    private lateinit var binding: FragmentWordBinding
    private lateinit var dataAdapter: DataAdapter
    private val viewModel: UrbanDictionaryViewModel by sharedViewModel()

    companion object {
        @JvmStatic
        fun newInstance() = WordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        with(viewModel) {
            wordDefinitions.observe(viewLifecycleOwner, definitionObserver)
            errorMsg.observe(viewLifecycleOwner, errorMsgObserver)
        }
    }

    private fun initUI() {
        initRecyclerView()
        initRadioGroup()
        binding.btnSearch.setOnClickListener { handleSearch() }
        binding.btnClear.setOnClickListener { clearAll() }
    }

    private fun initRecyclerView() {
        with(binding.recycleview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            dataAdapter = DataAdapter(this@WordFragment)
            adapter = dataAdapter
        }
    }

    private fun initRadioGroup() = binding.radioSort.setOnCheckedChangeListener { radioGroup, _ ->
        run {
            val selectedButton = radioGroup.checkedRadioButtonId
            if (selectedButton != -1) {
                when (selectedButton) {
                    binding.thumbsUp.id -> {
                        viewModel.sortDefinitions(SortTypeEnum.UP)
                    }
                    binding.thumbsDown.id -> {
                        viewModel.sortDefinitions(SortTypeEnum.DOWN)
                    }
                }
            }
        }
    }

    private fun handleSearch() {
        val wordEntered = binding.enteredWord.text.toString().lowercase().trim()
        if (wordEntered.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.searchHint), Toast.LENGTH_LONG)
                .show()
            return
        }
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getWordDefinitions(wordEntered)
    }

    private fun clearAll() {
        viewModel.clearWordDefinition()
        with(binding) {
            enteredWord.text.clear()
            thumbsUp.isChecked = false
            thumbsDown.isChecked = false
        }
    }

    override fun wordSelected(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(android.R.id.content, WordDetailFragment.newInstance(position))
            ?.addToBackStack(null)?.commit()
    }

    private val definitionObserver = Observer<List<WordDefinitions>> {
        binding.progressBar.visibility = View.GONE
        dataAdapter.updateDataSet(it)
    }

    private val errorMsgObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }
}