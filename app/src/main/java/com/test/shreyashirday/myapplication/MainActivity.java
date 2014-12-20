package com.test.shreyashirday.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.shreyashirday.utils.BusFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import shreyashirday.Bus;

//GreenDao Test App

public class MainActivity extends Activity {
    Button getData;
    Button listBuses;
    Button goToEdit;
    TextView viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = (Button)findViewById(R.id.button);
        listBuses = (Button)findViewById(R.id.button2);
        viewData = (TextView)findViewById(R.id.textView);
        goToEdit = (Button)findViewById(R.id.button3);

        goToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditBusActivity.class));
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getSugaBusData().execute();

            }
        });
        listBuses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<Bus> myBuses = BusFactory.getAllBuses(getApplicationContext());
                String todaysBuses = "Todays Buses are: ";
                for(int i = 0; i < myBuses.size(); i++){
                    todaysBuses += " " + myBuses.get(i).getRoute() + " and ";
                }

                viewData.setText(todaysBuses);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    private class getSugaBusData extends AsyncTask<Object,Object,Object>{

        @Override
        protected Object doInBackground(Object... params) {

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://runextbus.heroku.com/active");
            try {
                HttpResponse response = client.execute(get);

                StatusLine sl = response.getStatusLine();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                response.getEntity().writeTo(bos);
                bos.close();
                if(sl.getStatusCode() == HttpStatus.SC_OK){


                   return bos.toString();
                }
                else{
                    return null;

                }
            }
            catch(ClientProtocolException c){
                Log.d("ERROR",c.getMessage());
                return  null;

            }
            catch (IOException e){
                Log.d("ERROR",e.getMessage());
                return null;
            }


        }


        @Override
        protected  void onPostExecute(Object result){
            if(result!=null){
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray routesArray = jsonObject.getJSONArray("routes");
                    for(int i = 0;i < routesArray.length();i++){
                        JSONObject route = routesArray.getJSONObject(i);
                        String routeName = route.getString("title");
                        Bus aBus = new Bus();
                        aBus.setRoute(routeName);
                        BusFactory.insertOrUpdate(getApplicationContext(),aBus);
                        try{
                            Thread.sleep(1000);
                            viewData.setText("Bus " + i + " created");
                        }
                        catch(InterruptedException e){
                            Toast.makeText(getApplicationContext(),"Oops" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }





                }
                catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Log.d("RESPONSE",result.toString());

            }
            else{
                viewData.setText("Couldn't get Data");
            }
        }
    }
}
