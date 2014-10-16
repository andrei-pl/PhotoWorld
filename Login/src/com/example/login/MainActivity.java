package com.example.login;

import com.example.db.PhotoApp;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener
{
	private Intent imagesScreen;
	private SharedPreferences mPrefs;
	private Editor mEditor;
	private EditText editUser, editPass;
	private Button btnLogin, btnRegister;
	private UserManager mManager;
	private CheckBox remember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imagesScreen = new Intent(MainActivity.this, ImagesPageActivity.class);
		
		mManager = new UserManager(this.getApplicationContext());
		if(mManager.isLogged()) {
			Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
			startActivity(imagesScreen);
		}
		editUser = (EditText) findViewById(R.id.txtLogUser);
		editPass = (EditText) findViewById(R.id.txtLogPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		remember = (CheckBox) findViewById(R.id.chkRemember);
		btnRegister.setOnClickListener(this);		
		btnLogin.setOnClickListener(this);
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnLogin) {
			String user = editUser.getText().toString();
			String pass = editPass.getText().toString();
			
			try {
				boolean isExist = ((PhotoApp) getApplication()).getUserDB().loginUser(user, pass);
				
				if(isExist){
					mManager.registerUser(user, pass);
					mManager.logIn(user, pass ,remember.isChecked());
					if(mManager.isLogged()) {
						Toast.makeText(this, "Loged in", Toast.LENGTH_SHORT).show();
						startActivity(imagesScreen);
						
						if(remember.isChecked()) {
							
						}
					} else {
						Toast.makeText(this, "Username or password is invalid", Toast.LENGTH_SHORT).show();
					}
				}
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
			}
			
		} else if (v.getId() == R.id.btnRegister) {
			Intent screenRegister = new Intent(MainActivity.this, RegisterActivity.class);
			startActivity(screenRegister);
			
		}
	}
}
