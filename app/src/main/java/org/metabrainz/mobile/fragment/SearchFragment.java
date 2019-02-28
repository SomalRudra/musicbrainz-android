package org.metabrainz.mobile.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import org.metabrainz.mobile.R;
import org.metabrainz.mobile.activity.SearchActivity;
import org.metabrainz.mobile.intent.IntentFactory.Extra;
import org.metabrainz.mobile.suggestion.SuggestionHelper;

public class SearchFragment extends ContextFragment implements SearchView.OnQueryTextListener {

    private Spinner searchTypeSpinner;
    private SuggestionHelper suggestionHelper;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dash_search, container);
        searchTypeSpinner = layout.findViewById(R.id.search_spin);
        searchView = layout.findViewById(R.id.search_view);
        setupSearchView();
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSearchTypeSpinner();
        suggestionHelper = new SuggestionHelper(getActivity());
    }

    private void setupSearchTypeSpinner() {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.searchType,
                android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(typeAdapter);
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (App.getUser().isSearchSuggestionsEnabled()) {
            searchField.setAdapter(suggestionHelper.getAdapter());
            searchField.setOnItemClickListener(this);
        } else {
            searchField.setAdapter(suggestionHelper.getEmptyAdapter());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.query_input && actionId == EditorInfo.IME_NULL) {
            startSearch();
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startSearch();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_btn) {
            startSearch();
        }
    }*/

    private void startSearch() {
        String query = searchView.getQuery().toString();

        if (query.length() > 0) {
            Intent searchIntent = new Intent(context, SearchActivity.class);
            searchIntent.putExtra(Extra.TYPE, getSearchTypeFromSpinner());
            searchIntent.putExtra(Extra.QUERY, query);
            startActivity(searchIntent);
        } else {
            Toast.makeText(context, R.string.toast_search_err, Toast.LENGTH_SHORT).show();
        }
    }

    private String getSearchTypeFromSpinner() {
        int spinnerPosition = searchTypeSpinner.getSelectedItemPosition();
        switch (spinnerPosition) {
            case 0:
                return Extra.ARTIST;
            case 1:
                return Extra.RELEASE;
            case 2:
                return Extra.LABEL;
            case 3:
                return Extra.RECORDING;
            case 4:
                return Extra.RELEASE_GROUP;
            case 5:
                return Extra.INSTRUMENT;
            case 6:
                return Extra.EVENT;
            default:
                return Extra.ALL;
        }
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        //SearchSuggestionsAdapter adapter = new SearchSuggestionsAdapter(getActivity(),null,0);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startSearch();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}