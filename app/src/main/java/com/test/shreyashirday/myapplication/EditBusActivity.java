package com.test.shreyashirday.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.shreyashirday.utils.BusFactory;

import java.util.List;

import shreyashirday.Bus;


public class EditBusActivity extends Activity {

    private Spinner chooseRoute;
    private Button editRoute,deleteRoute;
    private EditText newName;
    private String[] routeArray;
    int id = 0;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bus);
        chooseRoute = (Spinner)findViewById(R.id.spinner);
        editRoute = (Button)findViewById(R.id.editBtn);
        deleteRoute = (Button)findViewById(R.id.deleteBtn);
        newName = (EditText)findViewById(R.id.editText);
        List<Bus> BusList = BusFactory.getAllBuses(getApplicationContext());
        routeArray = new String[BusList.size()];
        for(int i = 0;i < routeArray.length;i++){
            routeArray[i] = BusList.get(i).getRoute();
        }
        ArrayAdapter<String> routeStrings = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,routeArray);

        routeStrings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRoute.setAdapter(routeStrings);



        chooseRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id = position;
                Log.d("POSITION",position+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            editRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newRouteName = newName.getText().toString();
                    List<Bus> matchingBuses = BusFactory.getBusForName(getApplicationContext(), chooseRoute.getSelectedItem().toString());

                    Bus busToBeEdited = matchingBuses.get(0);
                    Log.d("ID:", id + "");
                    if (busToBeEdited != null) {
                        if (newRouteName.length() > 0) {
                            busToBeEdited.setRoute(newRouteName);
                            BusFactory.insertOrUpdate(getApplicationContext(), busToBeEdited);

                            finish();
                            startActivity(getIntent());

                            Toast.makeText(getApplicationContext(), "Route edited!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! You must type in a new name to change the name of this Route", Toast.LENGTH_SHORT).show();


                        }
                    }
                    else{
                        Log.e("ERROR","Bus not found");
                    }
                }

            });

            deleteRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Bus> matchingBuses = BusFactory.getBusForName(getApplicationContext(), chooseRoute.getSelectedItem().toString());
                    Bus busToBeDeleted = matchingBuses.get(0);

                    BusFactory.deleteBusWithId(getApplicationContext(),busToBeDeleted.getId());
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(),"Route Deleted!",Toast.LENGTH_SHORT).show();
                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
