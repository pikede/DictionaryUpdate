package com.example.urbandictionaryn

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryn.databinding.SearchedItemsBinding
import com.example.urbandictionaryn.models.WordDefinitions
import kotlin.random.Random

class DataAdapter(private val listener: WordDetailClickListener) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private var dataset: ArrayList<WordDefinitions> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchedItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position], listener, position)
    }

    fun updateDataSet(newDataset: List<WordDefinitions>) {
        val diffUtil = WordDiffUtil(dataset, newDataset)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        dataset.clear()
        dataset.addAll(newDataset)

        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: SearchedItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            wordDefinitions: WordDefinitions,
            listener: WordDetailClickListener,
            position: Int
        ) {
            with(binding) {
                wordDefinition.text = wordDefinitions.definition
                thumbsUp.text = wordDefinitions.thumbsUp.toString()
                thumbsDown.text = wordDefinitions.thumbsDown.toString()
                author.text = wordDefinitions.author
                writtenOn.text = wordDefinitions.writtenOn

                root.setBackgroundColor(randomColor())
                root.setOnClickListener {
                    listener.wordSelected(position)
                }
            }
        }

        private fun randomColor() = Color.rgb(
            Random.nextInt(100) + 45,
            Random.nextInt(150) + 44,
            Random.nextInt(1650) + 30
        )
    }

    class WordDiffUtil(
        private val oldList: List<WordDefinitions>,
        private val newList: List<WordDefinitions>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].definition == newList[newItemPosition].definition &&
                    oldList[oldItemPosition].author == newList[newItemPosition].author &&
                    oldList[oldItemPosition].permalink == newList[newItemPosition].permalink &&
                    oldList[oldItemPosition].defId == newList[newItemPosition].defId &&
                    oldList[oldItemPosition].currentVote == newList[newItemPosition].currentVote &&
                    oldList[oldItemPosition].writtenOn == newList[newItemPosition].writtenOn
        }
    }

    interface WordDetailClickListener {
        fun wordSelected(position: Int)
    }
}