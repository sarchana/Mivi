package app.com.mivi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aruna.puppala on 22-06-2018.
 */

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    String msn;
    EditText editText_msn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_msn = (EditText)findViewById(R.id.editText2_msn);
        login_button = (Button)findViewById(R.id.button);
        loadJSONFromAsset();//calling .json file

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_msn.getText().toString().equals(msn)){
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    if(editText_msn.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(),"please enter your msn",Toast.LENGTH_SHORT).show();

                    }if(!(editText_msn.getText().toString().length()==0)){
                        Toast.makeText(getApplicationContext(),"entered wrong credentials",Toast.LENGTH_SHORT).show();

                    }

                       // Toast.makeText(getApplicationContext(),"entered wrong credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });

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
            Log.e("m_jArry", ""+m_jArry);


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);

                String type = jsonObject.getString("type");


                if (type.equals("services")) {

                    Log.e("type services", type);
                    JSONObject obj1 = jsonObject.getJSONObject("attributes");
                    Log.e("services obj1",""+ obj1);

                     msn = obj1.getString("msn");

                    Log.e("msn", msn);

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

