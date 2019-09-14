package com.nike.urbandictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.nike.urbandictionary.adapters.UrbanDictionaryAdapter;
import com.nike.urbandictionary.models.UrbanDictionaryDefinition;
import com.nike.urbandictionary.viewmodels.UrbanDictionaryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URBAN_DICTIONARY_LIST = "URBAN_DICTIONARY_LIST";

    private RecyclerView recyclerView;
    private EditText editText;
    private ProgressBar progressBar;
    private UrbanDictionaryAdapter urbanDictionaryAdapter;
    private ArrayList<UrbanDictionaryDefinition> urbanDictionaryDefinitions = new ArrayList<>();
    private String[] dropDownSelection = {"None", "filterByThumbsUp", "filterByThumbsDown"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.searchEditText);
        editText.addTextChangedListener(textWatcher);
        progressBar = findViewById(R.id.progressBar);
        Spinner spinner = findViewById(R.id.listFilter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dropDownSelection);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
        if (savedInstanceState != null && savedInstanceState.containsKey(URBAN_DICTIONARY_LIST)) {
            urbanDictionaryDefinitions.clear();
            urbanDictionaryDefinitions = savedInstanceState.getParcelableArrayList(URBAN_DICTIONARY_LIST);
        }
        setupRecyclerView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList(URBAN_DICTIONARY_LIST, urbanDictionaryDefinitions);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(URBAN_DICTIONARY_LIST))
            urbanDictionaryDefinitions = savedInstanceState.getParcelableArrayList(URBAN_DICTIONARY_LIST);
    }

    private void setupRecyclerView() {
        if (urbanDictionaryAdapter == null) {
            urbanDictionaryAdapter = new UrbanDictionaryAdapter(MainActivity.this, urbanDictionaryDefinitions);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(urbanDictionaryAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            urbanDictionaryAdapter.notifyDataSetChanged();
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            hideKeyboard(editText.getRootView());
            progressBar.setVisibility(View.VISIBLE);
            UrbanDictionaryViewModel urbanDictionaryViewModel = ViewModelProviders.of(MainActivity.this).get(UrbanDictionaryViewModel.class);
            urbanDictionaryViewModel.getUrbanDictionaryRepository(editText.getText().toString()).observe(MainActivity.this, new Observer<List<UrbanDictionaryDefinition>>() {
                @Override
                public void onChanged(List<UrbanDictionaryDefinition> definitions) {
                    if (definitions != null && !definitions.isEmpty()) {
                        urbanDictionaryDefinitions.clear();
                        urbanDictionaryDefinitions.addAll(definitions);
                        urbanDictionaryAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 1) {
                Collections.sort(urbanDictionaryDefinitions, UrbanDictionaryDefinition.COMPARE_BY_THUMBSUP);
            } else if (i == 2) {
                Collections.sort(urbanDictionaryDefinitions, UrbanDictionaryDefinition.COMPARE_BY_THUMBSDOWN);
            }
            urbanDictionaryAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
}
