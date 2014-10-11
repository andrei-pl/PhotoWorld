package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener
{
	
	private EditText editUser, editPass, editConfirmPass;
	private Button btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		editUser = (EditText) findViewById(R.id.txtRegUser);
		editPass = (EditText) findViewById(R.id.txtRegPass);
		editConfirmPass = (EditText) findViewById(R.id.txtRegConfirmPass);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btnRegister) {
			UserManager manager = new UserManager(this.getApplicationContext());
			manager.registerUser(editUser.getText().toString(), editPass.getText().toString());
			if(manager.ApplyAllSettings()) {
				Toast.makeText(this, "Register at 100%", Toast.LENGTH_SHORT).show();
				Intent in = new Intent(RegisterActivity.this, ImagesPageActivity.class);  //TODO Save in Everlive: gIMQgGG9sI53ZQjD
				startActivity(in);
			}
		}
	}
}