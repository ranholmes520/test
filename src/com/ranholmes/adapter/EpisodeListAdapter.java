package com.ranholmes.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranholmes.aemanager.R;
import com.ranholmes.dto.RecordDetail;
import com.ranholmes.view.MyListViewPullDownAndUp;
import com.ranholmes.view.RoundImageView;

@SuppressLint("NewApi")
public class EpisodeListAdapter extends BaseAdapter {
	private Context context;
	public List<RecordDetail> listItems;
	private LayoutInflater listContainer;
	private MyListViewPullDownAndUp listView;


	static class ListItemView {
		public RoundImageView typeImage;
		public TextView summary;
	}

	public EpisodeListAdapter(Context context,
			List<RecordDetail> listItems,
			MyListViewPullDownAndUp mbListView) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItems = listItems;
		this.listView = mbListView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 图片
		Drawable cachedImage = null;
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.item_episode, null);
			listItemView = new ListItemView();

			listItemView.typeImage = (RoundImageView) convertView
					.findViewById(R.id.item_record_type_photo);

			listItemView.summary = (TextView) convertView
					.findViewById(R.id.item_record_summary);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		final RecordDetail mb = listItems.get(position);
		String summary_content = mb.getRecord_summary();
		listItemView.summary.setText(summary_content);

//		if (mb.isUser_vip()) {
//			listItemView.userVip.setVisibility(View.VISIBLE);
//		} else {
//			listItemView.userVip.setVisibility(View.GONE);
//		}
		
		if(mb.getRecord_income()==0){
			if(mb.getRecord_o_account_id()==0){
				listItemView.typeImage.setImageResource(R.drawable.image_draw);
			}else{
				listItemView.typeImage.setImageResource(R.drawable.image_trans_out);
				if(mb.getRecord_o_account_id()==10000009){
					listItemView.summary.setText(mb.getRecord_time()+"您购买了2元的福利彩票");
					listItemView.typeImage.setImageResource(R.drawable.image_lottery);
				}
				if(mb.getRecord_o_account_id()==10000010){
					listItemView.summary.setText("感谢您在"+mb.getRecord_time()+"向红十字会捐款"+mb.getRecord_outcome()+"元人民币"+"\n中国红十字会是中华人民共和国统一的红十字组织，中国红十字会总会是从事人道主义工作的社会救助团体，是国际红十字运动的成员。中国红十字会以发扬人道、博爱、奉献精神，保护人的生命和健康，促进人类和平进步事业为宗旨");
					listItemView.typeImage.setImageResource(R.drawable.image_hong);
				}
				if(mb.getRecord_o_account_id()==10000011){
					listItemView.summary.setText("感谢您在"+mb.getRecord_time()+"向壹基金捐款"+mb.getRecord_outcome()+"元人民币"+"\n壹基金是由李连杰先生于2007年发起成立的公益组织，是国内第一家民间公募基金会。");
					listItemView.typeImage.setImageResource(R.drawable.image_yi);
				}
			}
		}else{
			if(mb.getRecord_o_account_id()==0){
				listItemView.typeImage.setImageResource(R.drawable.image_save);
			}else{
				listItemView.typeImage.setImageResource(R.drawable.image_trans_in);
				if(mb.getRecord_o_account_id()==10000009){
					listItemView.summary.setText(mb.getRecord_time()+"您购买的福利彩票中奖了，中奖金额为"+mb.getRecord_income()+"元");
					listItemView.typeImage.setImageResource(R.drawable.image_lottery_ward);
				}
			}
		}
		
		return convertView;
	}

	public void refreshData(List<RecordDetail> listItems) {
		this.listItems = listItems;
		notifyDataSetChanged();
	}

}
