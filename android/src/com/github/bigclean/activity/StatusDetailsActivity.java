package com.github.bigclean.activity;

import com.github.bigclean.R;
import com.github.bigclean.fragment.StatusDetailsFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class StatusDetailsActivity extends FragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_details_container);
		
		Long    id   = getIntent().getExtras().getLong("status_id");
		Integer type = getIntent().getExtras().getInt("status_type");
		StatusDetailsFragment statusDetailsFragment = StatusDetailsFragment.newInstance(id, type);
		getSupportFragmentManager().beginTransaction().add(R.id.status_details_fragment_container, statusDetailsFragment).commit();
	}

}