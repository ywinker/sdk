package com.wink.sdk.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/20
 * 公司：    winker
 * 作者：    yingzy
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
//        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildPosition(view) == 0)
            outRect.top = space;*/
    }
}
