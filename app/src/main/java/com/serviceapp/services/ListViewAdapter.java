package com.serviceapp.services;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	
	private List<GetListDetails> getdetails = null;
	private ArrayList<GetListDetails> arraylist;
	
	public ListViewAdapter(Context context,
			List<GetListDetails> listdata) {
		this.context = context;
		this.getdetails = listdata;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<GetListDetails>();
		this.arraylist.addAll(listdata);
		imageLoader = new ImageLoader(context);
	}
	public class ViewHolder {
		TextView service;
		ImageView imgLogo;
		ImageView imgStatus;
		TextView des;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getdetails.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return getdetails.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
//	public void notifyItemChanged(int i) {
//	}
//
//	public void notifyDataSetChanged_fix() {
//		// unfortunately notifyDataSetChange is declared final, so cannot be overridden.
//		super.notifyDataSetChanged();
//		for (int i = getCount()-1; i>=0; i--)
//			notifyItemChanged(i);
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.request_items, null);
			holder.service = (TextView) convertView.findViewById(R.id.requestTitle);
			holder.imgLogo = (ImageView) convertView.findViewById(R.id.logo);
			holder.imgStatus = (ImageView) convertView.findViewById(R.id.status);
			holder.des=(TextView) convertView.findViewById(R.id.description);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.service.setText(getdetails.get(position).getService());
        holder.imgLogo.setImageResource(getdetails.get(position).getLogo());
		holder.imgStatus.setImageResource(getdetails.get(position).getStatus());
		holder.des.setText(getdetails.get(position).getDes());

		return convertView;
	}

}
