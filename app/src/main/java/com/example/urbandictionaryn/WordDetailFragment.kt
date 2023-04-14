package com.example.urbandictionaryn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.urbandictionaryn.databinding.FragmentWordDetailBinding
import com.example.urbandictionaryn.models.WordDefinitions
import com.example.urbandictionaryn.viewmodel.UrbanDictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WordDetailFragment : Fragment() {

    private var position: Int? = null
    private val viewModel: UrbanDictionaryViewModel by sharedViewModel()
    private lateinit var binding: FragmentWordDetailBinding

    companion object {
        const val WORD_POSITION = "word_position"

        @JvmStatic
        fun newInstance(position: Int) =
            WordDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(WORD_POSITION, position)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(WORD_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.getWordDetails(it.getInt(WORD_POSITION))
        }
        viewModel.wordDetail.observe(viewLifecycleOwner, wordDetailObserver)
    }

    private val wordDetailObserver = Observer<WordDefinitions> {
        Log.d("**logged", "word name ${it.word}")
        with(binding) {
            wordDefinition.text = it.definition
            thumbsUp.text = it.thumbsUp.toString()
            thumbsDown.text = it.thumbsDown.toString()
            author.text = it.author
            writtenOn.text = it.writtenOn
            permalink.text = it.permalink
        }
    }
}