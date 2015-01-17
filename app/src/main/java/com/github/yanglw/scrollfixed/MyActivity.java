package com.github.yanglw.scrollfixed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MyActivity extends Activity
{
    private ImageView mImageView;
    private int topMargin;
    private RelativeLayout.LayoutParams layoutParams;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        mImageView = (ImageView) findViewById(R.id.image);
        layoutParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();

        if (layoutParams == null)
        {
            topMargin = 0;
        }
        else
        {
            topMargin = layoutParams.topMargin;
        }

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new MyAdapter(this));

        listView.setOnScrollListener(new MyOnScrollListener());
    }

    class MyOnScrollListener implements AbsListView.OnScrollListener
    {
        private ImageView mFirstView;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState)
        {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            ViewGroup child1 = (ViewGroup) view.getChildAt(0);
            ViewGroup child2 = (ViewGroup) view.getChildAt(1);
            if (child1 != null)
            {
                ImageView view1 = (ImageView) child1.getChildAt(0);
                if (view1 == null)
                {
                    return;
                }
                if (mFirstView != view1)
                {
                    if (mFirstView != null)
                    {
                        mFirstView.setVisibility(View.VISIBLE);
                    }
                    mFirstView = view1;
                }
                mImageView.setImageDrawable(mFirstView.getDrawable());
                mFirstView.setVisibility(View.INVISIBLE);

                if (child2 != null)
                {
                    View view2 = child2.getChildAt(0);
                    if (view2 == null)
                    {
                        return;
                    }
                    view2.setVisibility(View.VISIBLE);

                    /** 判断ListView的第二个子View的图片是否到达了可以让mFirstView移动的位置。
                     * 即当先显示的第二个子View的顶部位置的X轴坐标值是否小于[mFirstView的高度 + child1.getPaddingBottom]
                     * 之所以不计算child1.getPaddingTop是因为child1.getPaddingTop这部分通过移动消失之后，mFirstView才逐渐消失的
                     * 在这里之所以不使用mFirstView的getBottom是因为通过setMargins导致mFirstView的getBottom的数值是变化的
                     * getBottom获取的相对于父控件的底部位置的X轴坐标值
                     */
                    int top = child2.getTop();
                    int bottom = view1.getBottom() + child1.getPaddingBottom();

                    if (bottom >= top)
                    {
                        layoutParams.setMargins(layoutParams.leftMargin,
                                                top - mImageView.getHeight() - child1.getPaddingBottom(),
                                                layoutParams.rightMargin,
                                                layoutParams.bottomMargin);
                    }
                    else
                    {
                        restore();
                    }
                }
                else
                {
                    restore();
                }

                mImageView.setLayoutParams(layoutParams);
            }
        }

        public void restore()
        {
            layoutParams.setMargins(layoutParams.leftMargin,
                                    topMargin,
                                    layoutParams.rightMargin,
                                    layoutParams.bottomMargin);
        }
    }

}
