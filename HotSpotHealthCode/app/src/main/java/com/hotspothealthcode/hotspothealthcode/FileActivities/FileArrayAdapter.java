package com.hotspothealthcode.hotspothealthcode.FileActivities;

import java.util.List;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.Models.ExplorerItem;

public class FileArrayAdapter extends ArrayAdapter<ExplorerItem>{

    private Context context;
    private int id;
    private List<ExplorerItem>items;

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<ExplorerItem> objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        id = textViewResourceId;
        items = objects;
    }

    public ExplorerItem getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }

               /* create a new view of my layout and inflate it in the row */
        //convertView = ( RelativeLayout ) inflater.inflate( resource, null );

        final ExplorerItem explorerItem = items.get(position);

        if (explorerItem != null) {
            TextView name = (TextView) v.findViewById(R.id.tvFileName);
            TextView size = (TextView) v.findViewById(R.id.tvFileSize);

            /* Take the ImageView from layout and set the city's image */
            ImageView imageCity = (ImageView) v.findViewById(R.id.imgItem);

            String uri = "drawable/" + explorerItem.getImage();

            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

            Drawable image = context.getResources().getDrawable(imageResource);

            imageCity.setImageDrawable(image);

            if(name != null)
                name.setText(explorerItem.getName());

            if(size != null)
                size.setText(explorerItem.getData());
        }

        return v;
    }
}
