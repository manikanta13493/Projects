package com.nike.urbandictionary

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner

import com.nike.urbandictionary.adapters.UrbanDictionaryAdapter
import com.nike.urbandictionary.models.UrbanDictionaryDefinition
import com.nike.urbandictionary.viewmodels.UrbanDictionaryViewModel

import java.util.ArrayList

private const val URBAN_DICTIONARY_LIST = "URBAN_DICTIONARY_LIST"

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var editText: EditText? = null
    private var progressBar: ProgressBar? = null
    private var urbanDictionaryAdapter: UrbanDictionaryAdapter? = null
    private var urbanDictionaryDefinitions: ArrayList<UrbanDictionaryDefinition> = arrayListOf()
    private val dropDownSelection = arrayOf("None", "filterByThumbsUp", "filterByThumbsDown")

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            hideKeyboard(editText!!.rootView)
            progressBar!!.visibility = View.VISIBLE
            val urbanDictionaryViewModel = ViewModelProviders.of(this@MainActivity).get(UrbanDictionaryViewModel::class.java)
            urbanDictionaryViewModel.getUrbanDictionaryRepository(editText!!.text.toString()).observe(this@MainActivity, Observer { definitions ->
                if (definitions != null && definitions.isNotEmpty()) {
                    urbanDictionaryDefinitions.clear()
                    urbanDictionaryDefinitions.addAll(definitions)
                    urbanDictionaryAdapter!!.notifyDataSetChanged()
                    progressBar!!.visibility = View.GONE
                }
            })
        }
    }

    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
            if (i == 1) {
                urbanDictionaryDefinitions.sortedWith(thumbsUpComparator)
            } else if (i == 2) {
                urbanDictionaryDefinitions.sortedWith(thumbsDownComparator)
            }
            urbanDictionaryAdapter!!.notifyDataSetChanged()
        }

        override fun onNothingSelected(adapterView: AdapterView<*>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        editText = findViewById(R.id.searchEditText)
        editText!!.addTextChangedListener(textWatcher)
        progressBar = findViewById(R.id.progressBar)
        val spinner = findViewById<Spinner>(R.id.listFilter)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, dropDownSelection)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter
        spinner?.onItemSelectedListener = itemSelectedListener
        if (savedInstanceState != null && savedInstanceState.containsKey(URBAN_DICTIONARY_LIST)) {
            urbanDictionaryDefinitions.clear()
            urbanDictionaryDefinitions = savedInstanceState.getParcelableArrayList(URBAN_DICTIONARY_LIST)
        }
        setupRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putParcelableArrayList(URBAN_DICTIONARY_LIST, urbanDictionaryDefinitions)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(URBAN_DICTIONARY_LIST))
            urbanDictionaryDefinitions = savedInstanceState.getParcelableArrayList(URBAN_DICTIONARY_LIST)
    }

    private fun setupRecyclerView() {
        urbanDictionaryAdapter = UrbanDictionaryAdapter(this@MainActivity, urbanDictionaryDefinitions)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = urbanDictionaryAdapter
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = true
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private val thumbsUpComparator: java.util.Comparator<UrbanDictionaryDefinition> = Comparator { one, other -> Integer.parseInt(other.thumbsUpCount).compareTo(Integer.parseInt(one.thumbsUpCount)) }

    private val thumbsDownComparator: java.util.Comparator<UrbanDictionaryDefinition> = Comparator { one, other -> Integer.parseInt(other.thumbsDownCount).compareTo(Integer.parseInt(one.thumbsDownCount)) }
}
