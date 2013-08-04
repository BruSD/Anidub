package brusd.anidub.com.DataClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import brusd.anidub.com.DawnloadAndParser.HtmlHelperAsyncTask;
import brusd.anidub.com.R;

/**
 * Created by BruSD on 22.07.13.
 */
public class AnimeAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends Map<String, ?>> data;
    private Activity activity;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     *                 in the from parameter.
     */

    public AnimeAdapter(Context context,
                        List<? extends Map<String, ?>> data,
                        int resource,
                        String[] from,
                        int[] to, Activity activity) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        this.activity = activity;

    }
    /**
     * <p>Count of items in the data set represented by this Adapter</p>
     * @return How many items are in the data set represented by this Adapter
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * <p>Get the data item associated with the specified position in the data set.</p>
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return The data at the specified position
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * <p>Get the row id associated with the specified position in the list.</p>
     * @param position The position of the item within the adapter's data set whose row id we want
     * @return The id of the item at the specified position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        TextView animeTitle;
        TextView animePubDay;
        ImageButton showDetailAnimeButton;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        AnimeItem animeItem = DataStorage.getAnimeList().get(position);


        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_anime_layout, parent, false);
            holder = new ViewHolder();

            holder.animeTitle = (TextView)convertView.findViewById(R.id.title_anime_item);
            holder.animePubDay = (TextView)convertView.findViewById(R.id.pub_date_anime_item);

            holder.showDetailAnimeButton = (ImageButton)convertView.findViewById(R.id.detail_view_button_item);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.animeTitle.setText(animeItem.getTitle());
        holder.animePubDay.setText(animeItem.getPubDate());

        holder.showDetailAnimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HtmlHelperAsyncTask(activity, DataStorage.getAnimeList().get(position).getLink()).execute();
            }
        });

        return convertView;
    }
}