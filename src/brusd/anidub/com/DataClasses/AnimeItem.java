package brusd.anidub.com.DataClasses;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by BruSD on 21.07.13.
 */
public class AnimeItem {
    private String _guid;
    private String _title;
    private URL _link;
    private String _description;
    private String _pubDate;

    //Не обязательные поля
    private URL _imageLink;
    private String _rateAnime;
    private String _voceCount;
    private ArrayList<URL> _vidosList;

    public AnimeItem(String _guid, String _title, URL _link, String _description, String _pubDate){
        this._guid= _guid;
        this._title = _title;
        this._link = _link;
        this._description = _description;
        this._pubDate = _pubDate;
        //Инициализируем не обязательные поля

        this._imageLink = null;
        this._rateAnime = null;
        this._voceCount = null;
        this._vidosList = null;
    }


    // Гетеры обязательных полей
    public String getGuid(){
        return this._guid;
    }
    public String getTitle(){
        return this._title;
    }
    public URL getLink(){
        return this._link;
    }
    public String getDescription(){
        return this._description;
    }
    public String getPubDate(){
        return this._pubDate;
    }

    //Сетеры не обязательных полей
    public void setImageLink(URL _imageLink){
        this._imageLink= _imageLink;
    }
    public void setRateAnime(String _rateAnime){
        this._rateAnime = _rateAnime;
    }
    public void setVoceCount(String _voceCount){
        this._voceCount = _voceCount;
    }
    public void setVidosList(ArrayList<URL> _vidosList){
        this._vidosList = _vidosList;
    }

    //Гетеры не обязательных полей
    public URL getImageLink(){
        return this._imageLink;
    }
    public String getRateAnime(){
        return this._rateAnime;
    }
    public String getVoceCount(){
        return this._voceCount;
    }
    public ArrayList<URL> getVidosList(){
        return this._vidosList;
    }
}
