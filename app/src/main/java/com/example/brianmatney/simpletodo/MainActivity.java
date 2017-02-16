package com.example.brianmatney.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Add Here
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("Press and hold me to delete");
//        items.add("Enter your own item below");
        Context context = getApplicationContext();
        CharSequence text = "Press and hold item to delete!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setDuration(Toast.LENGTH_LONG);

        toast.show();

//      Setup remove listener method call
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener(){
               @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                        View item, int pos, long id){
//
//                 Remove the item within array at position
                   items.remove(pos);

//                 Refresh the adapter
                   itemsAdapter.notifyDataSetChanged();
//
                   writeItems();

//                 Return true consumes the long click event (marks it handled)
                   return true;
               }
        });
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//  onCreate method
    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

}
