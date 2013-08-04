package brusd.anidub.com.AnidabFragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import brusd.anidub.com.DataClasses.AnimeAdapter;
import brusd.anidub.com.DataClasses.AnimeItem;
import brusd.anidub.com.DataClasses.DataStorage;
import brusd.anidub.com.DawnloadAndParser.DawnloadRssAsyncTask;
import brusd.anidub.com.DawnloadAndParser.HtmlHelperAsyncTask;
import brusd.anidub.com.R;

/**
 * Created by BruSD on 20.07.13.
 */
public class UpdataListFragment extends SherlockFragment{

    private ListView animeListiew;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list_layout, null);
        animeListiew = (ListView)view.findViewById(R.id.list_anime_fragment_list_layout);



        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        new DawnloadRssAsyncTask(getActivity(), UpdataListFragment.this).execute();
    }

    public void loadAnimeRssList(){
        AnimeAdapter adapter = new AnimeAdapter(getActivity(),
                createAnimeList(), R.layout.item_anime_layout,
                new String[] { "animetitle", "animedate"},
                new int [] { R.id.title_anime_item, R.id.pub_date_anime_item}, getActivity() );
        animeListiew.setAdapter(adapter);



    }
    private List<Map<String, ?>> createAnimeList() {
        List<Map<String, ?>> items = new ArrayList<Map<String, ?>>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(" MMMM dd,HH:mm");
            ArrayList<AnimeItem> articleList = DataStorage.getAnimeList();
            for (AnimeItem animeItem : articleList)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("animetitle", animeItem.getTitle());
                map.put("animedate", "Обновлено: " + animeItem.getPubDate());

                items.add(map);
            }
        }catch (Exception e){

        }
        return items;
    }


}
