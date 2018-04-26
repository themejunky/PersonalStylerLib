package com.themejunky.personalstylerlib.customdialogs.hour;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.themejunky.personalstylerlib.utils.SpeedyLinearLayoutManager;

public class ScheduleRecyclerListener extends RecyclerView.OnScrollListener {

    private SpeedyLinearLayoutManager mPlm;

    ScheduleRecyclerListener(SpeedyLinearLayoutManager nPlm) {
        this.mPlm = nPlm;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:

                int position = mPlm.findFirstVisibleItemPosition();
                View mFirstVisible = mPlm.findViewByPosition(mPlm.findFirstVisibleItemPosition());

                if ((-1*mFirstVisible.getY())<40) {
                    mPlm.scrollToPosition(position);
                } else {
                    mPlm.scrollToPositionWithOffset(position+1,0);
                }
                break;
//                case RecyclerView.SCROLL_STATE_DRAGGING:
//                    System.out.println("Scrolling now");
//                    break;
//                case RecyclerView.SCROLL_STATE_SETTLING:
//                    System.out.println("Scroll Settling");
//                    break;
        }
    }
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            if (dx > 0) {
//                System.out.println("Scrolled Right");
//            } else if (dx < 0) {
//                System.out.println("Scrolled Left");
//            } else {
//                System.out.println("No Horizontal Scrolled");
//            }
//
//            if (dy > 0) {
//                System.out.println("Scrolled Downwards");
//            } else if (dy < 0) {
//                System.out.println("Scrolled Upwards");
//            } else {
//                System.out.println("No Vertical Scrolled");
//            }
    }
}