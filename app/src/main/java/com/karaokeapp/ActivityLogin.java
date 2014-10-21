package com.karaokeapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;


public class ActivityLogin extends Activity implements View.OnClickListener {
	
	
	private EditText mName;
	private EditText mEmail;
	private EditText mPassword;
	private Button mLoginBtn;
	private Button mFbLoginBtn;
	private Button mRegistrationBtn;
	
	private final String PREF_EMAIL = "user@karaoke.com";
	private final String PREF_PASSWORD = "123456";
	private final String PREFS_FILENAME = "karaokeApp";
	
	private SharedPreferences mPref;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "dTaJAN6k8fHcjfy6ETBPYvPrABgVf1L40EVQLME0",
                "Nmjj8eCjcxFFYfVdlLPXJygxM4AXM32wUNQUM1Oo");

        final ActionBar actionBar;
        actionBar = getActionBar();
//        actionBar.setBackgroundDrawable();
		setContentView(R.layout.activity_login);
        mName = (EditText) findViewById(R.id.edt_name);
        mEmail = (EditText) findViewById(R.id.edt_email);
        mPassword = (EditText) findViewById(R.id.edt_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mFbLoginBtn = (Button) findViewById(R.id.btn_fb_Login);
        mRegistrationBtn = (Button) findViewById(R.id.btn_registration);
        
        mLoginBtn.setOnClickListener(this);
        mFbLoginBtn.setOnClickListener(this);
        mRegistrationBtn.setOnClickListener(this);

        
        mPref = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
        String prefEmail = mPref.getString(PREF_EMAIL, "");
        String prefPassword = mPref.getString(PREF_PASSWORD, "");
        
        if (!TextUtils.isEmpty(prefEmail) && !TextUtils.isEmpty(prefPassword)){
        	if (prefEmail.equalsIgnoreCase(prefEmail) && prefPassword.equalsIgnoreCase(prefPassword)){
        		Intent mIntent = new Intent(ActivityLogin.this, ActivityMain.class);
        		mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        		startActivity(mIntent);
				Log.i("LOG IN", "User is loged in");
        	} else {
        		Toast.makeText(getApplicationContext(), "e-mail or pasword are wrong!", Toast.LENGTH_SHORT).show();
        		Editor editor = mPref.edit();
				editor.putString(PREF_EMAIL, "");
				editor.putString(PREF_PASSWORD, "");
				editor.commit();
        	}
        }
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_login:
			Log.i("LOGIN", "Login button was pressed");
			Editor mEditor = mPref.edit();
			
			mEditor.putString(PREF_EMAIL, mEmail.getText().toString());
			mEditor.putString(PREF_PASSWORD, mPassword.getText().toString());
			mEditor.commit();
			
			if (mEmail.getText().toString().equals(PREF_EMAIL) & mPassword.getText().toString().equals(PREF_PASSWORD)){
				Intent mIntent = new Intent(ActivityLogin.this, ActivityMain.class);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(mIntent);
				Log.i("LOG IN", "User is loged in");
			} else{
				Toast.makeText(getApplicationContext(), "e-mail or pasword are wrong!", Toast.LENGTH_SHORT).show();
				mEditor.putString(PREF_EMAIL, "");
				mEditor.putString(PREF_PASSWORD, "");
				mEditor.commit();
			}
			break;
		case R.id.btn_fb_Login:
			Log.i("FB-LOGIN", "FB Login button was pressed");
			break;
		case R.id.btn_registration:
            mRegistrationBtn.setTextColor(Color.WHITE);
            mRegistrationBtn.setBackgroundResource(R.drawable.selector_btn_bgnd);
			Log.i("REGISTRATION", "Registration button was pressed");
			mName.setVisibility(View.VISIBLE);
			mLoginBtn.setVisibility(View.GONE);
			mFbLoginBtn.setVisibility(View.GONE);
			
			break;
		}		
	}
    
}
