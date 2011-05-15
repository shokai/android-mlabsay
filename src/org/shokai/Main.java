package org.shokai;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.lang.reflect.*;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Main extends Activity implements OnClickListener{
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        for(Field f : R.id.class.getFields()){
            try{
                View v = this.findViewById((Integer)f.get(null));
                if(v.getClass().getName().equals("android.widget.Button")){
                    Button btn = (Button)v;
                    btn.setOnClickListener(this);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onClick(View v) {
        if(v.getClass().getName().equals("android.widget.Button")){            
            Button btn = (Button)v;
            trace(btn.getText());
            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://masui.sfc.keio.ac.jp/say/say");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("message", btn.getText().toString()));
            try{
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse res = client.execute(httppost);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                res.getEntity().writeTo(os);
                trace(os.toString());
                trace("status : " + res.getStatusLine().getStatusCode());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void trace(Object message){
        Log.v("HttpTest", message.toString());
    }
}