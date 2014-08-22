package com.joshdonlan.actionbardemo.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joshdonlan.actionbardemo.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PeopleFragment extends Fragment{

    private final String TAG = "PEOPLEFRAGMENT";

    private PeopleFragmentListener mListener;
    private ArrayAdapter<String> mPeopleAdapter;
    private ActionMode mActionMode;
    private int mPersonSelected;

    public interface PeopleFragmentListener {

    }

    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        return fragment;
    }
    public PeopleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> people = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.people)));
        mPeopleAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,people);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView peopleList = (ListView) rootView.findViewById(R.id.peopleList);
        peopleList.setAdapter(mPeopleAdapter);
        peopleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                mPersonSelected = position;
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PeopleFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement PeopleFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //INTERFACE METHODS
    public void deleteCharacter(){
        mPeopleAdapter.remove(mPeopleAdapter.getItem(mPersonSelected));
        mPeopleAdapter.notifyDataSetChanged();
    }

    public String getToDelete(){
        return mPeopleAdapter.getItem(mPersonSelected);
    }

    //CONTEXTUAL ACTION BAR CALLBACK

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.peoplemenu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.peopleDelete:
                    Log.i(TAG, "Deleting " + mPeopleAdapter.getItem(mPersonSelected));
                    DialogFragment dialog = new DeleteDialogueFragment();
                    Bundle args = new Bundle();
                    args.putString("name", mPeopleAdapter.getItem(mPersonSelected));
                    dialog.setArguments(args);
                    dialog.show(getActivity().getFragmentManager(), "DeleteDialogueFragment");
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

}