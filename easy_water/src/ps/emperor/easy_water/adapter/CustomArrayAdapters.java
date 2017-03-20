package ps.emperor.easy_water.adapter;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationProject;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.infoList;
import ps.emperor.easy_water.entity.CustomData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapters extends MyBaseAdapter<infoList> {
	private Context context;
  
	public CustomArrayAdapters(Context context) {
    	super(context);
		this.context = context;
	}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {	
            // Inflate the view since it does not exist
            convertView = inflater.inflate(R.layout.custom_data_views, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        infoList infoList = list.get(position);
        holder.textView.setText(infoList.getGrowersName());

        // Set the color

        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public TextView textView;
    }

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
