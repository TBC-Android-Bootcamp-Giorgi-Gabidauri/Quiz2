package com.gabo.anagrams

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabo.anagrams.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: MutableList<String>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = mutableListOf()

        with(binding) {
            btnSave.setOnClickListener {
                addToList(etAnagram.text.toString())
                Snackbar.make(binding.cl, "Saved (your word: <${etAnagram.text}> added to list of words)", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.btnOutput).show()
                tvSeeWords.text = "Words you've entered: \n $list"
            }
            btnOutput.setOnClickListener {
                val groupedAnagrams = groupAnagrams(list)
                tvAnagramCount.text = "Anagram count \n ${groupedAnagrams.size}"
                tvSeeAnagrams.text = "grouped Anagrams: \n $groupedAnagrams"
            }
            btnClear.setOnClickListener {
                list = mutableListOf()
                tvSeeWords.text = getString(R.string.words_you_ve_entered)
                tvSeeAnagrams.text = getString(R.string.grouped_anagrams)
                tvAnagramCount.text = getString(R.string.anagram_count)
            }
        }
    }

    private fun addToList(input: String) {
        list.add(input)
    }

    private fun groupAnagrams(strs: MutableList<String>): List<List<String>> {
        val resultingMap = mutableMapOf<Map<Char, Int>, List<String>>()

        strs.forEach { str ->
            val key = mutableMapOf<Char, Int>()
            str.forEach {
                key[it] = (key[it] ?: 0) + 1
            }
            resultingMap[key] = (resultingMap[key] ?: listOf()) + str
        }
        return resultingMap.values.toList()
    }
}


