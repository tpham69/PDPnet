package thaiph.ph48495.pdpnet.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import thaiph.ph48495.pdpnet.fragments.BietOnFragment;
import thaiph.ph48495.pdpnet.fragments.BietOnHistoryFragment;
import thaiph.ph48495.pdpnet.fragments.RunningFragment;
import thaiph.ph48495.pdpnet.fragments.RunningHistoryFragment;

public class HistoryPagerAdapter extends FragmentStateAdapter {
    public HistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BietOnHistoryFragment();
            case 1:
                return new RunningHistoryFragment();
            default:
                return new BietOnHistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Number of fragments
    }
}
