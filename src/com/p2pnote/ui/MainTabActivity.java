package com.p2pnote.ui;

import com.p2pnote.ui.R;
import com.p2pnote.ui.R.id;
import com.p2pnote.ui.R.layout;
import com.p2pnote.ui.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainTabActivity extends ActivityGroup {
	private TabHost tabHost;
	private TextView main_tab_new_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		Intent stateService =  new Intent (this,com.p2pnote.service.LogService.class);
		startService( stateService );

		//tab host        
		tabHost = (TabHost)findViewById(R.id.tabhost); 
		tabHost.setup(this.getLocalActivityManager());
		
        TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, HomePageActivity.class);
        spec=tabHost.newTabSpec("主页").setIndicator("主页").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,DetailPageActivity.class);
        spec=tabHost.newTabSpec("流水").setIndicator("流水").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, ReportActivity.class);
        spec=tabHost.newTabSpec("分析").setIndicator("分析").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, ChannelListActivity.class);
        spec=tabHost.newTabSpec("平台").setIndicator("平台").setContent(intent);
        tabHost.addTab(spec);
  
        intent=new Intent().setClass(this, HomePageActivity.class);
        spec=tabHost.newTabSpec("更多").setIndicator("更多").setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_homepage://主页
					tabHost.setCurrentTabByTag("主页");
					break;
				case R.id.main_tab_detail://流水
					tabHost.setCurrentTabByTag("流水");
					break;
				case R.id.main_tab_analysis:
					tabHost.setCurrentTabByTag("分析");
					break;
				case R.id.main_tab_channel:
					tabHost.setCurrentTabByTag("平台");
					break;	
				case R.id.main_tab_more:
					tabHost.setCurrentTabByTag("更多");
					break;
				default:
					tabHost.setCurrentTabByTag("主页");
					break;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
