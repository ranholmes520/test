package com.ranholmes.aemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulucu.chart.ChartProp;
import com.example.ulucu.chart.ChartPropChangeListener;
import com.example.ulucu.chart.MyButton;
import com.example.ulucu.chart.PieView;

public class MainActivity extends Activity implements OnClickListener {
	private int[] itemColor;// 选项颜色
	private String[] itemText;// 选项文字
	private PieView pieView;
	private float surfacViewWidth = 0;
	private float surfacViewHeight = 0;
	private MyButton myButton;
	private TextView textView;
	private Button btn;
	private EditText edtColor;
	private EditText edtDesp;
	private EditText edtWeight;
	
	private ArrayList<Integer> colors = new ArrayList<Integer>();
	private ArrayList<Float> percents = new ArrayList<Float>();
	private ArrayList<String> namess = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.acitivity_main);
		initView();

	}

	@Override
	protected void onStart() {

		super.onStart();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pieView.rotateEnable();
	}

	protected void onPause() {
		super.onPause();

		if (pieView != null) {
			pieView.rotateDisable();
		}
	}

	private void mySetMargins(View view, int left, int top, int right,
			int bottom) {
		FrameLayout.LayoutParams layoutParam = (FrameLayout.LayoutParams) view
				.getLayoutParams();
		layoutParam.setMargins(left, top, right, bottom);
	}

	private int myGetWidth(View view) {
		FrameLayout.LayoutParams layoutParam = (FrameLayout.LayoutParams) view
				.getLayoutParams();
		return layoutParam.width;
	}

	/**
	 * 
	 * Description:初始化界面元素
	 * 
	 */

	public void initView() {

		pieView = (PieView) this.findViewById(R.id.lotteryView);
		myButton = (MyButton) this.findViewById(R.id.MyBt);
		textView = (TextView) this.findViewById(R.id.MyTV);
		btn 	 = (Button) this.findViewById(R.id.btn);
		edtColor = (EditText)this.findViewById(R.id.color);
		edtDesp  = (EditText)this.findViewById(R.id.item);
		edtWeight= (EditText)this.findViewById(R.id.weight);
		
//		initItem();
		refreshItem(Color.RED, 25f, Color.RED+"");
		
		refreshItem(Color.BLUE, 25f, "lpsf");
		pieView.initPercents();
		Message msg = new Message();
		msg.obj = pieView.getCurrentChartProp();
		handler.sendMessage(msg);

		// textView=(TextView) this.findViewById(R.id.textView);
		pieView.setChartPropChangeListener(new ChartPropChangeListener() {

			@Override
			public void getChartProp(ChartProp chartProp) {
				Message msg = new Message();
				msg.obj = chartProp;
				handler.sendMessage(msg);
			}
		});
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				refreshItem(Integer.parseInt(edtColor.getText()+""), Float.parseFloat(edtWeight.getText()+""), edtDesp.getText()+"");
			}
		});
		pieView.start();

	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ChartProp chartProp = (ChartProp) msg.obj;
			myButton.setBackgroundPaintColor(chartProp.getColor());
			textView.setText(chartProp.getName());
			textView.setTextColor(chartProp.getColor());
		};
	};

	/**
	 * 
	 * Description:初始化转盘的颜色，文字
	 * 
	 */
	public void initItem() {
		int color[] = new int[] { Color.BLUE, Color.GREEN, Color.RED,
				Color.YELLOW, Color.WHITE };
		float percent[] = new float[] { 0.2f, 0.3f, 0.1f, 0.35f, 0.05f };
		String names[] = new String[] { "BLUE", "GREEN", "RED", "YELLOW",
				"WHITE" };

		ArrayList<ChartProp> acps = pieView.createCharts(5);
		int size = acps.size();
		for (int i = 0; i < size; i++) {
			ChartProp chartProp = acps.get(i);
			chartProp.setColor(color[i]);
			chartProp.setPercent(percent[i]);
			chartProp.setName(names[i]);

		}
		pieView.initPercents();
	}
	
	public void refreshItem(Integer color,float percent,String name){
		
		colors.add(color);
		percents.add(percent);
		namess.add(name);
		ArrayList<ChartProp> acps = pieView.createCharts(colors.size());
		int sum = 0;
		for(int i = 0;i < percents.size();i++){
			sum+=percents.get(i);
		}
		for(int i = 0; i< colors.size();i++){
			ChartProp chartProp = acps.get(i);
			chartProp.setColor(colors.get(i));
			chartProp.setPercent(percents.get(i)/sum);
			chartProp.setName(namess.get(i));
		}
//		pieView.initPercents();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}