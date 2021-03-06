package net.mobctrl.moveviews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Zheng Haibo
 * @web http://www.mobctrl.net
 *
 */
@SuppressLint({ "ClickableViewAccessibility", "NewApi" })
public class MainActivity extends Activity implements OnTouchListener {

	private ImageView ivMove;
	private RelativeLayout rlRoot;
	private TextView tvTips;
	private int topTitleHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ivMove = (ImageView) findViewById(R.id.iv_move);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
		tvTips = (TextView) findViewById(R.id.tv_tips);
		rlRoot.setOnTouchListener(this);
		tvTips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MainActivity2.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int[] locations = new int[2];
			tvTips.getLocationInWindow(locations);
			topTitleHeight = locations[1];
			break;
		case MotionEvent.ACTION_MOVE:
			moveViewWithFinger(ivMove, event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}

	/**
	 * 设置View的布局属性，使得view随着手指移动 注意：view所在的布局必须使用RelativeLayout 而且不得设置居中等样式
	 * 
	 * @param view
	 * @param rawX
	 * @param rawY
	 */
	private void moveViewWithFinger(View view, float rawX, float rawY) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
				.getLayoutParams();
		params.leftMargin = (int) rawX - ivMove.getWidth() / 2;
		params.topMargin = (int) rawY - topTitleHeight - ivMove.getHeight() / 2;
		view.setLayoutParams(params);
	}

	/**
	 * 通过layout方法，移动view
	 * 
	 * @param view
	 * @param rawX
	 * @param rawY
	 */
	private void moveViewByLayout(View view, int rawX, int rawY, int scale) {
		int left = rawX - ivMove.getWidth() / 2;
		int top = rawY - topTitleHeight - ivMove.getHeight() / 2;
		int width = left + (int) (view.getWidth() + scale);
		int height = top + (int) (view.getHeight() + scale);
		view.layout(left, top, width, height);
	}

	private void moveViewByLayout(View view, int rawX, int rawY) {
		int left = rawX - ivMove.getWidth() / 2;
		int top = rawY - topTitleHeight - ivMove.getHeight() / 2;
		int width = left + view.getWidth();
		int height = top + view.getHeight();
		view.layout(left, top, width, height);
	}

}
