package com.kindas.truthOrDare;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kindas.truthOrDare.adpter.PeopleAdapter;
import com.kindas.truthOrDare.model.People;

public class TruthOrDareActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	final static People people = new People();
	static Integer posInterviewer = null;
	static Integer posVictim;
	static PeopleAdapter adapterPeoples;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truth_or_dare);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			People people = null;
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		// final People people = new People();

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			final View rootView;

			if (getArguments().getInt(ARG_SECTION_NUMBER) % 2 == 0) {
				rootView = inflater.inflate(R.layout.truth_or_dare, container, false);

				Button sort = (Button) rootView.findViewById(R.id.btnSort);

				final TextView interviewer = (TextView) rootView.findViewById(R.id.txtInterviewer);
				final TextView victim = (TextView) rootView.findViewById(R.id.txtVictim);
				final TextView to = (TextView) rootView.findViewById(R.id.txtTo);

				sort.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						sortPeoples(interviewer, victim, to);
					}

				});

				Button ok = (Button) rootView.findViewById(R.id.btnOk);
				Button cancel = (Button) rootView.findViewById(R.id.btnCancel);
				
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						sortPeoples(interviewer, victim, to);
					}
				});
				
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = people.getPeoples().get(posVictim).getName();
						people.getPeoples().remove(people.getPeoples().get(posVictim));
						adapterPeoples.notifyDataSetChanged();
						
						interviewer.setVisibility(View.INVISIBLE);
						victim.setVisibility(View.INVISIBLE);
						to.setVisibility(View.INVISIBLE);
						
						Toast.makeText(rootView.getContext(), name + " Foi removida da lista de participantes por não seguir as regras do jogo!", Toast.LENGTH_SHORT).show();
					}
				});

			} else {
				rootView = inflater.inflate(R.layout.truth_or_dare_peoples, container, false);

				ListView listPeoples = (ListView) rootView.findViewById(R.id.listPeople);
				adapterPeoples = new PeopleAdapter(rootView.getContext(), people.getPeoples());
				listPeoples.setAdapter(adapterPeoples);

				Button addPeople = (Button) rootView.findViewById(R.id.btnAddPeople);
				addPeople.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						EditText name = (EditText) rootView.findViewById(R.id.editTextName);
						people.add(name.getText().toString());
						adapterPeoples.notifyDataSetChanged();
						Toast.makeText(rootView.getContext(), name.getText() + " Foi add a lista de participantes!", Toast.LENGTH_SHORT).show();
						name.setText("");
					}
				});

			}

			return rootView;
		}
	}

	private static int getNext(Integer last, Integer diff) {
		Random random = new Random();
		diff = diff == null ? last : diff;
		Integer next = random.nextInt(people.getPeoples().size());
		while (next == diff) {
			next = random.nextInt(people.getPeoples().size());
		}
		if (last == null)
			return next;
		while (next == last) {
			next = random.nextInt(people.getPeoples().size());
		}
		return next;
	}

	private static void sortPeoples(final TextView interviewer, final TextView victim, final TextView to) {
		if(people.getPeoples().size() == 1) {
			Toast.makeText(interviewer.getContext(), "Para iniciar o game você deve adcionar no mínimo mais 1 pessoa", Toast.LENGTH_SHORT).show();
			return;
		} else if (people.getPeoples().size() == 0) {
			Toast.makeText(interviewer.getContext(), "Para iniciar o game você deve adcionar no mínimo mais 2 pessoa", Toast.LENGTH_SHORT).show();
			return;
		}
		
		posInterviewer = getNext(posInterviewer, posVictim);
		posVictim = getNext(posVictim, posInterviewer);

		interviewer.setText(people.get(posInterviewer).getName());
		victim.setText(people.get(posVictim).getName());

		interviewer.setVisibility(View.VISIBLE);
		victim.setVisibility(View.VISIBLE);
		to.setVisibility(View.VISIBLE);
	}

}
