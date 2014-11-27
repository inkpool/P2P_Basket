package com.p2pnote.ui;

import java.util.HashMap;

import com.p2pnote.ui.R;
import com.p2pnote.ui.R.id;
import com.p2pnote.ui.R.layout;
import com.p2pnote.service.LoginService;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUIActivity extends Activity {
	private EditText et_username = null;
	private EditText et_password = null;
	private CheckBox cb_remeber_password = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.et_username = (EditText) this.findViewById(R.id.et_username);
		this.et_password = (EditText) this.findViewById(R.id.et_password);
		this.cb_remeber_password = (CheckBox) this
				.findViewById(R.id.cb_remember_psw);
		HashMap<String, String> info = LoginService.getInfo(this);
		if(info != null) {
			this.et_username.setText(info.get("username"));
			this.et_password.setText(info.get("password"));
		}
	}

	public void login(View view) {
		String username = this.et_username.getText().toString().trim();
		String password = this.et_password.getText().toString().trim();
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "用户名、密码不能为空", 0).show();
		} else {
			if (this.cb_remeber_password.isChecked()) {
				boolean result = LoginService.saveInfo(this, username, password);
				if(result) {
					Toast.makeText(this, "用户名、密码保存成功", 0).show();
				}
				
			}
			if ("weijie".equals(username) && "123".equals(password)) {
				Toast.makeText(this, "登录成功", 0).show();
			} else {
				Toast.makeText(this, "登录失败", 0).show();

			}
		}
	}
}