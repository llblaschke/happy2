package com.example.happy2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyShowCategoriesDialogAdapter extends BaseAdapter {

    private ArrayList<String> mData = new ArrayList<String>();
    private ArrayList<Integer> mDisabled = new ArrayList<Integer>();
    private LayoutInflater mInflater;

    public MyShowCategoriesDialogAdapter(CharSequence[] items,
                           ArrayList<Integer> disabledItems, Context context) {
        mInflater = LayoutInflater.from(context);
        mDisabled = disabledItems;
        for( int i = 0; i< items.length; ++i ){
            addItem( items[i].toString());
        }
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if( mDisabled.contains(position))
            return 1; //disabled
        return 0; //enabled as normal
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        switch(type) {
            case 1:
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(mData.get(position));
                convertView.setEnabled(false);
                convertView.setFocusable(false);
                //convertView.setClickable(false);
                break;
            case 0:
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(mData.get(position));
                break;

        }
        return convertView;
    }

}