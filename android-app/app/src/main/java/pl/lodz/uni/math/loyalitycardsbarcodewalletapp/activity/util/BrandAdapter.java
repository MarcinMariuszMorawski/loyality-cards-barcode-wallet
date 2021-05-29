package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;

import java.util.List;

public final class BrandAdapter extends BaseAdapter {
    private Activity context;
    private List<Brand> brandList;
    private static LayoutInflater layoutInflater = null;

    public BrandAdapter(Activity context,List<Brand> brands) {
        this.context = context;
        this.brandList = brands;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Brand getItem(int position) {
        return brandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? layoutInflater.inflate(R.layout.list_item, null) : itemView;
        TextView textViewBrandName = itemView.findViewById(R.id.textViewBrandName);
        Brand selectedBrand = brandList.get(position);
        textViewBrandName.setText(selectedBrand.getName());
        textViewBrandName.setTextColor(Color.parseColor(selectedBrand.getColor()));
        return itemView;
    }
}