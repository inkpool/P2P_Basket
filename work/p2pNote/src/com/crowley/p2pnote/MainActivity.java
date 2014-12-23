package com.crowley.p2pnote;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener{
	
	private IndexFragment indexFragment;  
    private WaterFragment waterFragment;  
    private AnalyzeFragment analyzeFragment;  
    private PlatformFragment platformFragment;
    private MoreFragment moreFragment;
    
    private LinearLayout tabIndex;  
    private LinearLayout tabWater;
    private LinearLayout tabAnalyze;
    private LinearLayout tabPlatform;
    private LinearLayout tabMore;
    
    private FragmentManager fragmentManager;
    
    private TextView title;
    
    private Button newItem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();  
        fragmentManager = getFragmentManager();  
        setTabSelection(0);
        
    }

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub 
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        hideFragments(transaction);  
        switch (index)  
        {  
        case 0:   
        	((Button)findViewById(R.id.new_item)).setVisibility(View.VISIBLE);
        	title.setText(R.string.tab_index);
            ((ImageView)tabIndex.findViewById(R.id.tab_index_icon)).setImageResource(R.drawable.index_focus);
            ((TextView)tabIndex.findViewById(R.id.tab_index_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));
            if (indexFragment == null)  
            {   
            	indexFragment = new IndexFragment();  
                transaction.add(R.id.content, indexFragment);  
            } else  
            {   
                transaction.show(indexFragment);  
            }  
            break;  
        case 1:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_water);
        	((ImageView)tabWater.findViewById(R.id.tab_water_icon)).setImageResource(R.drawable.water_focus);
            ((TextView)tabWater.findViewById(R.id.tab_water_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));  
            if (waterFragment == null)  
            {   
            	waterFragment = new WaterFragment();  
                transaction.add(R.id.content, waterFragment);  
            } else  
            {   
                transaction.show(waterFragment);  
            }  
            break;  
        case 2:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_analyze);
        	((ImageView)tabAnalyze.findViewById(R.id.tab_analyze_icon)).setImageResource(R.drawable.analyze_focus);
            ((TextView)tabAnalyze.findViewById(R.id.tab_analyze_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));  
            if (analyzeFragment == null)  
            {   
            	analyzeFragment = new AnalyzeFragment();  
                transaction.add(R.id.content, analyzeFragment);  
            } else  
            {   
                transaction.show(analyzeFragment);  
            }  
            break;  
        case 3: 
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_platform);
        	((ImageView)tabPlatform.findViewById(R.id.tab_platform_icon)).setImageResource(R.drawable.platform_focus); 
            ((TextView)tabPlatform.findViewById(R.id.tab_platform_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen)); 
            if (platformFragment == null)  
            {   
            	platformFragment = new PlatformFragment();  
                transaction.add(R.id.content, platformFragment);  
            } else  
            {   
                transaction.show(platformFragment);  
            }  
            break;
		case 4:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
			title.setText(R.string.tab_more);
	    	((ImageView)tabMore.findViewById(R.id.tab_more_icon)).setImageResource(R.drawable.more_focus);  
            ((TextView)tabMore.findViewById(R.id.tab_more_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));
	        if (moreFragment == null)  
	        {   
	        	moreFragment = new MoreFragment();  
	            transaction.add(R.id.content, moreFragment);  
	        } else  
	        {   
	            transaction.show(moreFragment);  
	        }  
	        break;  
	    }  
        transaction.commit();  
		
	}

	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (indexFragment != null)  
        {  
            transaction.hide(indexFragment);  
        }  
		if (waterFragment != null)  
        {  
            transaction.hide(waterFragment);  
        }  
		if (analyzeFragment != null)  
        {  
            transaction.hide(analyzeFragment);  
        }  
		if (platformFragment != null)  
        {  
            transaction.hide(platformFragment);  
        }  
		if (moreFragment != null)  
        {  
            transaction.hide(moreFragment);  
        }  		
	}

	private void resetBtn() {
		// TODO Auto-generated method stub
        ((ImageView)tabIndex.findViewById(R.id.tab_index_icon)).setImageResource(R.drawable.index); 
    	((ImageView)tabWater.findViewById(R.id.tab_water_icon)).setImageResource(R.drawable.water);
    	((ImageView)tabAnalyze.findViewById(R.id.tab_analyze_icon)).setImageResource(R.drawable.analyze);  
    	((ImageView)tabPlatform.findViewById(R.id.tab_platform_icon)).setImageResource(R.drawable.platform);  
		((ImageView)tabMore.findViewById(R.id.tab_more_icon)).setImageResource(R.drawable.more);
		((TextView)tabIndex.findViewById(R.id.tab_index_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabWater.findViewById(R.id.tab_water_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabAnalyze.findViewById(R.id.tab_analyze_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabPlatform.findViewById(R.id.tab_platform_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabMore.findViewById(R.id.tab_more_text)).setTextColor(getResources().getColor(R.color.tab_text));
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tabIndex = (LinearLayout) findViewById(R.id.tab_index);
		tabWater = (LinearLayout) findViewById(R.id.tab_water);
		tabAnalyze = (LinearLayout) findViewById(R.id.tab_analyze);
		tabPlatform = (LinearLayout) findViewById(R.id.tab_platform);
		tabMore = (LinearLayout) findViewById(R.id.tab_more);
		
		newItem = (Button) findViewById(R.id.new_item);
		
		title = (TextView) findViewById(R.id.main_tab_banner_title);
		
		tabIndex.setOnClickListener(this);
		tabWater.setOnClickListener(this);
		tabAnalyze.setOnClickListener(this);
		tabPlatform.setOnClickListener(this);
		tabMore.setOnClickListener(this);
		newItem.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())  
        {  
        case R.id.tab_index:  
            setTabSelection(0);  
            break;  
        case R.id.tab_water:  
            setTabSelection(1);  
            break;  
        case R.id.tab_analyze:  
            setTabSelection(2);  
            break;  
        case R.id.tab_platform:  
            setTabSelection(3);  
            break; 
        case R.id.tab_more:  
            setTabSelection(4);  
            break;             
        case R.id.new_item:
        	Intent intent=new Intent(this,NewItemActivity.class);
            startActivity(intent);
        default:  
            break;  
        }  
	}
}
