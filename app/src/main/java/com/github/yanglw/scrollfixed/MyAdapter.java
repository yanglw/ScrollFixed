package com.github.yanglw.scrollfixed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yanglw on 2014/4/11 011.
 */
public class MyAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private int[] colors;
    private String[] texts;

    public MyAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);

        colors = new int[5];
        colors[0] = R.color.black;
        colors[1] = R.color.b;
        colors[2] = R.color.r;
        colors[3] = R.color.g;
        colors[4] = R.color.p;

        texts = context.getResources().getStringArray(R.array.texts);
    }

    @Override
    public int getCount()
    {
        return texts.length * 5;
    }

    @Override
    public Object getItem(int position)
    {
        return texts[position % texts.length];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.i_item, parent, false);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_list_iv_img);
            holder.textView = (TextView) convertView.findViewById(R.id.item_list_tv_text);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        holder.imageView.setImageResource(colors[position % colors.length]);
        holder.textView.setText(texts[position % texts.length]);

        return convertView;
    }

    class Holder
    {
        public ImageView imageView;
        public TextView textView;
    }
}
