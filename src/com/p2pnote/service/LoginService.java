package com.p2pnote.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.Context;
import android.widget.Toast;

public class LoginService {

	public static boolean saveInfo(Context context, String username,
			String password) {
		//getFileDir : /data/data/����/files
		//getCacheDir : /data/data/����/cache
		File file = new File(context.getFilesDir(), "info.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write((username + "=" + password).getBytes());
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static HashMap<String, String> getInfo(Context context) {
		File file = new File(context.getFilesDir(), "info.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String[] result = br.readLine().split("=");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username", result[0]);
			map.put("password", result[1]);
			br.close();
			return map;

		} catch (Exception e) {
			Toast.makeText(context, "没有保存过用户名、密码", 0).show();

		}
		return null;
	}

}
