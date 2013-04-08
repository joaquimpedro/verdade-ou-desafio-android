package com.kindas.truthOrDare.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kindas.truthOrDare.R;
import com.kindas.truthOrDare.model.People;

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
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		final People people = mPeoples.get(position);
		final PeopleAdapter adapter = this;
		
		final View row = inflater.inflate(R.layout.people_item, parent, false);
		TextView name = (TextView) row.findViewById(R.id.txtName);
		
		name.setText(people.getName());
		
		Button but = (Button) row.findViewById(R.id.btnDelete);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = mPeoples.get(position).getName();
				mPeoples.remove(position);
				adapter.notifyDataSetChanged();
				Toast.makeText(row.getContext(), name + " Foi Removido!", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		return row;
	}

}
