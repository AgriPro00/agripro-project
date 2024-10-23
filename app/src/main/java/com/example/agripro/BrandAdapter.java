package com.example.agripro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BrandAdapter extends BaseAdapter {
    private Context context;
    private List<Brand> brandList;


    public BrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @Override
    public int getCount() {
        return  brandList.size();
    }

    @Override
    public Object getItem(int position) {
        return  brandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.brand_item, parent, false);
        }

        Brand currentItem = brandList.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        textView.setText(currentItem.getProductName());
        Picasso.get().load(currentItem.getProductImg()).into(imageView);

        return convertView;
    }
}
