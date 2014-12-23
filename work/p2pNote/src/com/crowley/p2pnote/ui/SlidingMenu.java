package com.crowley.p2pnote.ui;

import com.crowley.p2pnote.R;
import com.nineoldandroids.view.ViewHelper;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWrapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	
	private int mMenuWidth;
	//dp
	private int mMenuRightPadding = 50;
	
	private boolean once = false;
	
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();		
		for(int i=0;i<n;i++){
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
				break;
			default:
				break;
			}
		}
		a.recycle();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		mScreenWidth=outMetrics.widthPixels;		
	}


	public SlidingMenu(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}


	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
		
	}
	
	
	//ÉèÖÃ¿í¸ß
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!once){
			mWrapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWrapper.getChildAt(0);
			mContent = (ViewGroup) mWrapper.getChildAt(1);
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			
			once = true;
		}
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	//Òþ²Ømenu
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub		
		super.onLayout(changed, l, t, r, b);
		
		if(changed){
			this.scrollTo(mMenuWidth, 0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			int scrollX=getScrollX();
			
			if(scrollX>mMenuWidth/2){
				this.smoothScrollTo(mMenuWidth, 0);
			}else{
				this.smoothScrollTo(0, 0);
			}
			return true;
		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		/*float scale = l* 1.0f/mMenuWidth; //1~0
		
		float rightScale = 0.7f+ 0.3f*scale;
		float leftScale = 1.0f -scale * 0.3f;
		float leftAlpha = 0.6f+ 0.4f * (1-scale);
		
		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale * 0.7f);
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);
		
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);*/
	}
}
