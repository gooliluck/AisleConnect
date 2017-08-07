package com.aisleconnect;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    String LoginURL = "https://apistage2.aisleconnect.us/ac.api/rest/v2.0/checklist";
    String accounttext="paul.lin@lineagenetworks.com";
    String passwordtext = "welcome1";
    EditText account,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(mutiThread);
                thread.start();
            }
        });

    }
    private JSONObject sendPostDataToInternet(String strTxt){
        Log.e("MainActivity","startconnect");
        HttpURLConnection c=null;
        final String accountstring = account.getText().toString();
        final String passwordstring =password.getText().toString();
        trustAllCertificates();
        if (accountstring.length() == 0||passwordstring.length() == 0){
            Log.e("MainActivity","account or password empty");
            return null;
        }
        Log.d("MainActivity","show account "+accountstring+"    and password "+passwordstring);
        try {
            Authenticator.setDefault(new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(accountstring,passwordstring.toCharArray());
                }});
            c = (HttpURLConnection) new URL(strTxt).openConnection();
            c.setConnectTimeout(5000);
            c.setUseCaches(false);
            c.connect();
            if(c.getResponseCode() == HttpsURLConnection.HTTP_OK){
                // 讀取網頁內容
                InputStream inputStream     = c.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader( new InputStreamReader(inputStream) );

                String tempStr;
                StringBuffer stringBuffer = new StringBuffer();

                while( ( tempStr = bufferedReader.readLine() ) != null ) {
                    stringBuffer.append( tempStr );
                }

                bufferedReader.close();
                inputStream.close();

                // 取得網頁內容類型
                String  mime = c.getContentType();
                boolean isMediaStream = false;

                // 判斷是否為串流檔案
                if( mime.indexOf("audio") == 0 ||  mime.indexOf("video") == 0 ){
                    isMediaStream = true;
                }

                // 網頁內容字串
                String responseString = stringBuffer.toString();
//                Log.e("response",""+responseString);
                return  new JSONObject(responseString);
            }else
            {
                Log.e("response","fail");
            }
        } catch (Exception e) {
            Log.e("response",e.toString());
        }finally {
            // 中斷連線
            if( c != null ) {
                c.disconnect();
            }
        }
        return null;
		/* END Test/Play with beanstalk API */
    }
    private Runnable mutiThread = new Runnable(){
        public void run(){
            // 運行網路連線的程式
            JSONObject r = sendPostDataToInternet(LoginURL);
            if (r != null) {
                ToOwnerList(r);
            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                        ad.setTitle("錯誤");
                        ad.setMessage("帳號或密碼輸入錯誤");
                        ad.setNeutralButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        ad.show();
                    }
                });
            }

        }
    };
    private void ToOwnerList(JSONObject owner){

        try{
            JSONArray dataarray = owner.getJSONArray("data");
            for (int cn =0;cn<dataarray.length();cn++){
                JSONObject childobj =  dataarray.getJSONObject(cn);
                Log.e("ownerlist","count "+cn +"    "+childobj.toString());
                String childname = childobj.getString("name");
                int childindex = childobj.getInt("id");
                String imgUrl = childobj.getString("imageUrl");
                OwnerListData childdata = new OwnerListData();
                childdata.imgURL = imgUrl;
                childdata.Name = childname;
                childdata.Number = childindex;
                GlobalData.getInstance().ownerlist.add(childdata);
            }
        }catch (Exception e){

        }
    }
    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}
