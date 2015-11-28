package com.alanddev.manwal.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.TransactionDay;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.foound.widget.AmazingAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionAdapter extends AmazingAdapter {

	private List<TransactionDay> datas;
	private LayoutInflater inflate;
	private Context mContext;
	
	public  TransactionAdapter(Context context,LayoutInflater inflate,List<TransactionDay> datas) {
		// TODO Auto-generated constructor stub
		this.datas = datas;
		this.inflate = inflate;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		int res = 0;
		for (int i = 0; i < datas.size(); i++) {
			res += datas.get(i).getItems().size();
		}
		return res;
	}

	@Override
	public Object getItem(int position) {
		int c = 0;
		for (int i = 0; i < datas.size(); i++) {
			if (position >= c && position < c + datas.get(i).getItems().size()) {
				return datas.get(i).getItems().get(position - c);
			}
			c += datas.get(i).getItems().size();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	protected void onNextPageRequested(int page) {
	}

	@Override
	protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
		View header = view.findViewById(R.id.header);
		if (displaySectionHeader) {
			header.setVisibility(View.VISIBLE);
			TransactionDay transactionDay = datas.get(getSectionForPosition(position));
			Date date = transactionDay.getDisplay_date();
			TextView txtDate = (TextView) header.findViewById(R.id.txtdate);
			if(date!=null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				txtDate.setText(cal.get(Calendar.DATE) + "");
				TextView txtDay = (TextView) header.findViewById(R.id.txtday);
				txtDay.setText(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Utils.getLocale()));
				TextView txtyear = (TextView) header.findViewById(R.id.txtyear);
				txtyear.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Utils.getLocale()) + " " + cal.get(Calendar.YEAR));
			}else if(transactionDay.getDisplayStr()!=null){
				txtDate.setText(transactionDay.getDisplayStr());
			}
			TextView txtAmount = (TextView) header.findViewById(R.id.txtheadamout);
			txtAmount.setText(transactionDay.getNetamount() + "");
		} else {
			header.setVisibility(View.GONE);
		}
	}

	@Override
	public View getAmazingView(int position, View convertView, ViewGroup parent) {
		View res = convertView;
		if (res == null){
			res = inflate.inflate(R.layout.item_list_transaction, null);
		}

		TextView txttype = (TextView) res.findViewById(R.id.txttitle);
		TextView txtdes = (TextView) res.findViewById(R.id.txtdes);
		TextView txtamout = (TextView) res.findViewById(R.id.txtamout);
		ImageView imgicon = (ImageView) res.findViewById(R.id.imgicon);
		TransactionDetail composer = (TransactionDetail) getItem(position);
		txttype.setText(composer.getCate_name());
		txtdes.setText(composer.getNote());
		if(composer.getCate_type()== Constant.EXPENSE_TYPE){
			txtamout.setTextColor(mContext.getResources().getColor(R.color.colorOutFlow));
		}else{
			txtamout.setTextColor(mContext.getResources().getColor(R.color.colorInflow));
		}
		txtamout.setText(composer.getAmountt()+"");
		imgicon.setImageResource(mContext.getResources().getIdentifier("ic_category_"+composer.getCate_img(),"mipmap",mContext.getPackageName()));

		return res;
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
	}

	@Override
	public int getPositionForSection(int section) {
		if (section < 0)
			section = 0;
		if (section >= datas.size())
			section = datas.size() - 1;
		int c = 0;
		for (int i = 0; i < datas.size(); i++) {
			if (section == i) {
				return c;
			}
			c += datas.get(i).getItems().size();
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		int c = 0;
		for (int i = 0; i < datas.size(); i++) {
			if (position >= c && position < c + datas.get(i).getItems().size()) {
				return i;
			}
			c += datas.get(i).getItems().size();
		}
		return -1;
	}

	@Override
	public String[] getSections() {
		return null;
	}
}
