package brusd.anidub.com.DataClasses;

import java.util.ArrayList;

import brusd.anidub.com.DataClasses.AnimeItem;

/**
 * Created by BruSD on 21.07.13.
 */
public class DataStorage {

    private static ArrayList<AnimeItem> animeList = new ArrayList<AnimeItem>();

    /**
     * <p>Заполняем временное хранилише списком Ново вышедших аниме</p>
     * @param AnimeList - Лист из новых Анимешек
     */
    public static void  setAnimeList(ArrayList<AnimeItem> AnimeList){
        animeList = AnimeList;
    }
    public static ArrayList<AnimeItem> getAnimeList(){
        return animeList;
    }
}
