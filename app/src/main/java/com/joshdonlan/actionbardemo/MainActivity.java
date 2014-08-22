package com.joshdonlan.actionbardemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.joshdonlan.actionbardemo.fragments.DeleteDialogueFragment;
import com.joshdonlan.actionbardemo.fragments.PeopleFragment;

public class MainActivity extends Activity implements PeopleFragment.PeopleFragmentListener, DeleteDialogueFragment.DeleteDialogueListener {

    private final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PeopleFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //INTERFACE METHODS
    public void deleteCharacter(){
        PeopleFragment peopleFragment = (PeopleFragment) getFragmentManager().findFragmentById(R.id.container);
        peopleFragment.deleteCharacter();
    }

}
