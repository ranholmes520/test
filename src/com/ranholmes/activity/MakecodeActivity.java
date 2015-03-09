package com.ranholmes.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.zxing.encoding.EncodingHandler;

public class MakecodeActivity extends Activity{

	private Button cancelBtn;
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_makecode);
		
		cancelBtn = (Button)findViewById(R.id.back);
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		image=(ImageView) findViewById(R.id.fragment2_searchtype_imagebutton);
//		String contentString="                        "+Integer.parseInt(Storage.getString(MakecodeActivity.this, "u_id"))+"                                                              ";
		String contentString= Integer.parseInt(Storage.getString(MakecodeActivity.this, "account_id"))+"";
		if (contentString != null && contentString.trim().length() > 0) {
			//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
			Bitmap qrCodeBitmap;
			try {
				qrCodeBitmap = EncodingHandler.createQRCode(contentString, 500);
				image.setImageBitmap(qrCodeBitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
//		Bitmap bt_head=Storage.getBitmap(MakecodeActivity.this, "myheadphoto");
//		image2.setImageBitmap(bt_head);
		
		
	}
}
