package saulmm.test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import saulmm.test.media.MediaFragment;

public class MainActivity extends AppCompatActivity {

	private NavigationView mNavigation;
	private DrawerLayout mDrawerLayout;
	private Toolbar mToolbar;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bindViews();
		initToolbar();
		initNavigation();
	}

	private void bindViews() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
		mNavigation = (NavigationView) findViewById(R.id.main_navigation);
	}

	private void initToolbar() {
		mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
		mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_24dp, getTheme()));
		mToolbar.setTitle(getString(R.string.app_name));
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {

				if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT))
					mDrawerLayout.openDrawer(Gravity.LEFT);
				else mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
	}

	private void initNavigation() {
		mNavigation.setNavigationItemSelectedListener(
			new NavigationView.OnNavigationItemSelectedListener() {

				boolean handled = false;

				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem) {
					if (menuItem.getItemId() == R.id.drawer_item_1) {
						initMediaFragment();
						mToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
						handled = true;
					}

					//if (menuItem.getItemId() == R.id.drawer_item_2) handled = true;

					mDrawerLayout.closeDrawer(Gravity.LEFT);
					return handled;
				}
			});
	}

	private void initMediaFragment() {
		getSupportFragmentManager().beginTransaction()
			.add(R.id.main_content, MediaFragment.newInstance())
			.commit();

	}
}
