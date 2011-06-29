package com.web.login;

import java.io.DataInputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
  
import android.app.Activity;  
import android.app.AlertDialog;  
import android.app.ProgressDialog;  
import android.content.DialogInterface;  
import android.content.Intent;  
import android.content.SharedPreferences;  
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Message;  
import android.util.Log;  
import android.view.Menu;  
import android.view.MenuItem;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.CheckBox;  
import android.widget.CompoundButton;  
import android.widget.EditText;  
import android.widget.Toast;  
import android.widget.CompoundButton.OnCheckedChangeListener;  
  
public class Login extends Activity {  
    private String userName;  
    private String password;  
      
    private EditText view_userName;  
    private EditText view_password;  
    private CheckBox view_rememberMe;  
    private Button view_loginSubmit;  
      
    private static final int MENU_EXIT = Menu.FIRST - 1;  
    private static final int MENU_ABOUT = Menu.FIRST;  
      
    private final String SHARE_LOGIN_TAG = "MAP_SHARE_LOGIN_TAG";  
  
    private String SHARE_LOGIN_USERNAME = "MAP_LOGIN_USERNAME";  
    private String SHARE_LOGIN_PASSWORD = "MAP_LOGIN_PASSWORD";  
      
    private boolean isNetError;  
      
    private ProgressDialog proDialog;  
  
    // 判断网络情况  
    Handler loginHandler = new Handler() {  
        public void handleMessage(Message msg)   
        {  
            isNetError = msg.getData().getBoolean("isNetError");  
            if(proDialog != null)   
            {  
                proDialog.dismiss();  
            }  
            if(isNetError)   
            {  
                Toast.makeText(Login.this, "当前网络不可用",  
                        Toast.LENGTH_SHORT).show();  
            }  
            else   
            {  
                Toast.makeText(Login.this, "错误的用户名或密码",  
                        Toast.LENGTH_SHORT).show();  
                  
                clearSharePassword();  
            }  
        }  
    };  
  
  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        findViewsById();  
        initView(false);  
          
        setListener();  
    }  
  
  
    private void findViewsById() {  
        view_userName = (EditText)findViewById(R.id.loginUserNameEdit);  
        view_password = (EditText)findViewById(R.id.loginPasswordEdit);  
        view_rememberMe = (CheckBox)findViewById(R.id.loginRememberMeCheckBox);  
        view_loginSubmit = (Button)findViewById(R.id.loginSubmit);  
    }  
  
      
    private void initView(boolean isRememberMe) {  
        SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);  
        String userName = share.getString(SHARE_LOGIN_USERNAME, "");  
        String password = share.getString(SHARE_LOGIN_PASSWORD, "");  
        Log.d(this.toString(), "userName=" + userName + " password=" + password);  
        if (!"".equals(userName)) {  
            view_userName.setText(userName);  
        }  
        if (!"".equals(password)) {  
            view_password.setText(password);  
            view_rememberMe.setChecked(true);  
        }  
          
        if (view_password.getText().toString().length() > 0) {  
            // view_loginSubmit.requestFocus();  
            // view_password.requestFocus();  
        }  
        share = null;  
    }  
  
      
    private boolean validateLocalLogin(String userName, String password, String validateUrl) {  
          
        boolean loginState = false;  
        HttpURLConnection conn = null;  
        DataInputStream dis = null;  
        try   
        {  
            URL url = new URL(validateUrl);  
            conn = (HttpURLConnection)url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");     
            conn.connect();  
          
            dis = new DataInputStream(conn.getInputStream());  
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)   
            {  
                Log.d(this.toString(), "HTTP ERROR");  
                isNetError = true;  
                return false;  
            }  
              
            int loginStateInt = dis.read();  
  
            Log.v("loginState", String.valueOf(loginStateInt));  
            if(loginStateInt == 1)   
            {  
                loginState = true;  
            }  
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
            isNetError = true;  
            Log.d(this.toString(), e.getMessage() + "  127 line");  
        }   
        finally   
        {  
            if(conn != null)   
            {  
                conn.disconnect();  
            }  
        }  
          
        if(loginState)   
        {  
            if(isRememberMe())   
            {  
                saveSharePreferences(true, true);  
            }   
            else   
            {  
                saveSharePreferences(true, false);  
            }  
        }   
        else   
        {  
            if(!isNetError)  
            {  
                clearSharePassword();  
            }  
        }  
        if(!view_rememberMe.isChecked())   
        {  
            clearSharePassword();  
        }  
          
        Log.v("loginState", String.valueOf(loginState));  
        return loginState;  
    }  
  
      
    private void saveSharePreferences(boolean saveUserName, boolean savePassword) {  
        SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);  
        if(saveUserName) {  
            Log.d(this.toString(), "saveUserName="  
                    + view_userName.getText().toString());  
            share.edit().putString(SHARE_LOGIN_USERNAME,  
                    view_userName.getText().toString()).commit();  
        }  
        if (savePassword) {  
            share.edit().putString(SHARE_LOGIN_PASSWORD,  
                    view_password.getText().toString()).commit();  
        }  
        share = null;  
    }  
  
      
    private boolean isRememberMe() {  
        if (view_rememberMe.isChecked()) {  
            return true;  
        }  
        return false;  
    }  
  
      
    private OnClickListener submitListener = new OnClickListener() {  
      
        public void onClick(View v) {  
            proDialog = ProgressDialog.show(Login.this, "请稍候",  
                    "", true, true);  
              
            Thread loginThread = new Thread(new LoginFailureHandler());  
            loginThread.start();  
        }  
    };  
  
    // .start();  
    // }  
    // };  
  
      
    private OnCheckedChangeListener rememberMeListener = new OnCheckedChangeListener() {  
  
  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            if (view_rememberMe.isChecked()) {  
                Toast.makeText(Login.this, "ischecked",  
                        Toast.LENGTH_SHORT).show();  
            }  
        }  
    };  
  
  
      
  
      
    private void setListener() {  
        view_loginSubmit.setOnClickListener(submitListener);  
          
        view_rememberMe.setOnCheckedChangeListener(rememberMeListener);  
    }  
  
      
    public boolean onCreateOptionsMenu(Menu menu) {  
        super.onCreateOptionsMenu(menu);  
        menu.add(0, MENU_EXIT, 0, getResources().getText(R.string.MENU_EXIT));  
        menu.add(0, MENU_ABOUT, 0, getResources().getText(R.string.MENU_ABOUT));  
        return true;  
    }  
  
  
    public boolean onMenuItemSelected(int featureId, MenuItem item) {  
        super.onMenuItemSelected(featureId, item);  
        switch (item.getItemId()) {  
        case MENU_EXIT:  
            finish();  
            break;  
        case MENU_ABOUT:  
            alertAbout();  
            break;  
        }  
        return true;  
    }  
  
      
    private void alertAbout() {  
        new AlertDialog.Builder(Login.this).setTitle(R.string.MENU_ABOUT)  
                .setMessage(R.string.aboutInfo).setPositiveButton(  
                        R.string.ok_label,  
                        new DialogInterface.OnClickListener() {  
                            public void onClick(  
                                    DialogInterface dialoginterface, int i) {  
                            }  
                        }).show();  
    }  
  
  
    private void clearSharePassword() {  
        SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);  
        share.edit().putString(SHARE_LOGIN_PASSWORD, "").commit();  
        share = null;  
    }  
  
    class LoginFailureHandler implements Runnable {  
  
        public void run() {  
            userName = view_userName.getText().toString();  
            password = view_password.getText().toString();  
              
            String validateURL="http://10.0.2.2:8080/androidShopServer/loginCheck.action?userName="  
                + userName + "&password=" + password;  
            boolean loginState = validateLocalLogin(userName, password,  
                    validateURL);  
            Log.d(this.toString(), "validateLogin");  
  
            if(loginState)   
            {     
                Intent intent = new Intent();  
                intent.setClass(Login.this, IndexPage.class);  
                Bundle bundle = new Bundle();  
                bundle.putString("MAP_USERNAME", userName);  
                intent.putExtras(bundle);  
                  
                startActivity(intent);  
                proDialog.dismiss();  
            } else {  
                  
                Message message = new Message();  
                Bundle bundle = new Bundle();  
                bundle.putBoolean("isNetError", isNetError);  
                message.setData(bundle);  
                loginHandler.sendMessage(message);  
            }  
        }  
  
    }  
}  