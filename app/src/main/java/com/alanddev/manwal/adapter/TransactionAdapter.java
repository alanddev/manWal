package com.alanddev.manwal.adapter;

import java.util.List;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Transaction;
import com.alanddev.manwal.model.TransactionDetail;
import com.foound.widget.AmazingAdapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TransactionAdapter extends AmazingAdapter {

	private List<Transaction> datas;
	private LayoutInflater inflate;
	
	public  TransactionAdapter(LayoutInflater inflate,List<Transaction> datas) {
		// TODO Auto-generated constructor stub
		this.datas = datas;
		this.inflate = inflate;
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
			Transaction itemDt = datas.get(getSectionForPosition(position));
			TextView txtDate = (TextView) header.findViewById(R.id.txtdate);
			txtDate.setText(itemDt.getDate());
			TextView txtDay = (TextView) header.findViewById(R.id.txtday);
			txtDay.setText(itemDt.getDay());
			TextView txtAmount = (TextView) header.findViewById(R.id.txtheadamout);
			txtAmount.setText(itemDt.getAmount());
			TextView txtyear = (TextView) header.findViewById(R.id.txtyear);
			txtyear.setText(itemDt.getMonth() + " " + itemDt.getYear());
		} else {
			header.setVisibility(View.GONE);
		}
	}

	@Override
	public View getAmazingView(int position, View convertView, ViewGroup parent) {
		View res = convertView;
		if (res == null){
			res = inflate.inflate(R.layout.list_transaction, null);
		}

		TextView txttype = (TextView) res.findViewById(R.id.txttitle);
		TextView txtdes = (TextView) res.findViewById(R.id.txtdes);
		TextView txtamout = (TextView) res.findViewById(R.id.txtamout);

		TransactionDetail composer = (TransactionDetail) getItem(position);
		txttype.setText(composer.getType());
		txtdes.setText(composer.getDes());
		txtamout.setText(composer.getAmount());

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
