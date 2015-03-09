package com.ranholmes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCAFragment;

public class FragmentPage2_search extends Fragment implements BMPCAFragment {
	private View v;
	private RelativeLayout mLock;
	private TextView account_name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (v == null) {
			v = inflater.inflate(R.layout.fragment_2_search, container, false);
			initView(v);
		}
		// 缓存的v需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) v.getParent();
		if (parent != null) {
			parent.removeView(v);
		}
		return v;
	}
	
	
	@Override
	public void refresh(Object... params) {
	}

	@Override
	public void initView(View v) {
		mLock = (RelativeLayout)v.findViewById(R.id.fragment3_myBinaCode);
		account_name = (TextView)v.findViewById(R.id.fragment2_account_name);
		account_name.setText(Storage.getString(getActivity(), "account_name"));
		mLock.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),SetPasswordActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
}
