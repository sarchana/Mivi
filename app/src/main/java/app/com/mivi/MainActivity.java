package app.com.mivi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import app.com.mivi.connections.ConnectionDetector;

public class MainActivity extends AppCompatActivity  {
    ArrayList<String> typesList = new ArrayList<>();
    private ConnectionDetector cd;

    TextView text_remaining_balance,text_product_name,text_product_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_remaining_balance = (TextView) findViewById(R.id.text_remaining_balance);
        text_product_name = (TextView) findViewById(R.id.text_product_name);
        text_product_price = (TextView) findViewById(R.id.text_product_price);
        loadJSONFromAsset();
    }
    public String loadJSONFromAsset() {
        String json = null;
        Log.e("getting data", "getting data");


        try {
            InputStream is = getAssets().open("collection.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("included");
            //JSONArray m_jArry = obj.getJSONArray(json);
            Log.e("m_jArry", ""+m_jArry);
           // JSONObject jsonObject = m_jArry.getJSONObject();


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(0);
                //type
                // attributes
                String type = jsonObject.getString("type");

                JSONObject jsonObject1 = m_jArry.getJSONObject(1);
                //type
                // attributes
                String type1 = jsonObject1.getString("type");

                Log.e("type1", ""+type1);
                JSONObject jsonObject2 = m_jArry.getJSONObject(2);
                //type
                // attributes
                String type2 = jsonObject2.getString("type");

                Log.e("type2", ""+type2);


               // typesList.add(type);
                typesList.add(type1);
                typesList.add(type2);
                Log.e("typesList", ""+typesList);

                if (type.equals("services")) {

                    Log.e("type services", type);
                    JSONObject obj1 = jsonObject.getJSONObject("attributes");//new JSONObject("attributes");
                    Log.e("services obj1",""+ obj1);

                    String msn = obj1.getString("msn");
                    Log.e("msn", msn);

                }
                if (typesList.get(0).equals("subscriptions")) {
                    Log.e("type subscriptions", jsonObject1.getString("type"));
                    JSONObject obj3 = jsonObject1.getJSONObject("attributes");
                    Log.e("subscriptions obj3",""+ obj3);

                    String data_balance = jsonObject1.getJSONObject("attributes").getString("included-data-balance");
                    Log.e("data_balance", data_balance);
                    text_remaining_balance.setText(data_balance);
                }
                if (typesList.get(1).equals("products")) {
                    Log.e("type products", jsonObject2.getString("type"));
                    JSONObject obj2 = jsonObject2.getJSONObject("attributes");
                    Log.e("products obj2",""+ obj2);

                    String price = obj2.getString("price");
                    String name = obj2.getString("name");

                    text_product_name.setText(name);
                    text_product_price.setText(price);
                    Log.e("price", price);
                    Log.e("name", name);

                }

            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
