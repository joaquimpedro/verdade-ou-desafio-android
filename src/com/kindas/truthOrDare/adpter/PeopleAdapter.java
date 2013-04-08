package com.kindas.truthOrDare.adpter;

import java.util.List;

import com.kindas.truthOrDare.R;
import com.kindas.truthOrDare.model.People;

import android.content.Context;
import android.inputmethodservice.Keyboard.Row;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PeopleAdapter extends BaseAdapter {

	private Context mContext;
	private List<People> mPeoples;

	public PeopleAdapter(Context context, List<People> peoples) {
		this.mContext = context;
		this.mPeoples = peoples;
	}

	@Override
	public int getCount() {
		return mPeoples.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mPeoples.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		People people = mPeoples.get(position);
		
		View row = inflater.inflate(R.layout.people_item, parent, false);
		TextView name = (TextView) row.findViewById(R.id.txtName);
		
		name.setText(people.getName());
				
		return row;
	}

}
