package brusd.anidub.com.DawnloadAndParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import brusd.anidub.com.AnidabFragment.UpdataListFragment;
import brusd.anidub.com.DBAniDubPackage.AnimeController;
import brusd.anidub.com.DBAniDubPackage.DatabaseAniDubOpenHelper;
import brusd.anidub.com.DataClasses.AnimeItem;
import brusd.anidub.com.DataClasses.DataStorage;
import brusd.anidub.com.MainActivity;
import brusd.anidub.com.R;
import brusd.anidub.com.SplashAniDubScreen;

/**
 * Created by BruSD on 20.07.13.
 */


public class DawnloadRssAsyncTask extends AsyncTask<Void, Void, Integer> {
    TagNode rootNode;
    private ProgressDialog dialog;
    private Activity activity = null;
    private  URL xmlUrl;
    private Fragment fragment;

    private static final String TAG = AnimeController.class.getSimpleName();

    public DawnloadRssAsyncTask(Activity activity, Fragment fragment){
        this.activity= activity;
        this.fragment = fragment;
        try {
            xmlUrl = new URL("http://online.anidub.com/rss.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    /**
     * <p>Start ProgressDialog </p>
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Integer result) {


        if (activity != null && activity instanceof SplashAniDubScreen){
            ((SplashAniDubScreen)activity).startMainActivity();

        }
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try
        {
            HttpURLConnection conn = (HttpURLConnection)xmlUrl.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();

                //DocumentBuilderFactory, DocumentBuilder are used for xml parsing
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                DocumentBuilder db;
                try { db = dbf.newDocumentBuilder();
                } catch (ParserConfigurationException ex) {
                    //  This can't be!!!
                    return null;
                }

                //using db (Document Builder) parse xml data and assign it to Element
                Document document;
                try {
                    document = db.parse(stream);
                } catch (SAXException ex) {
                    //  This can't be!!!
                    return  null;
                }
                Element element = document.getDocumentElement();

                //take rss nodes to NodeList
                NodeList nodeList = element.getElementsByTagName("item");
                ArrayList<AnimeItem> animeItemArrayList = new ArrayList<AnimeItem>();

                if (nodeList.getLength() > 0)
                    try {
                        //создали нашу базу и открыли для записи
                        DatabaseAniDubOpenHelper dbhelper = new DatabaseAniDubOpenHelper(activity);
                        SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();

                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Element entry = (Element) nodeList.item(i);

                            Element _guidE = (Element) entry.getElementsByTagName("guid").item(0);
                            Element _titleE = (Element) entry.getElementsByTagName("title").item(0);
                            Element _linkE = (Element) entry.getElementsByTagName("link").item(0);
                            Element _descriptionE = (Element) entry.getElementsByTagName("description").item(0);
                            Element _pubDateE = (Element) entry.getElementsByTagName("pubDate").item(0);


                            String _guid = _guidE.getTextContent();//getFirstChild().getNodeValue();
                            String _title = _titleE.getTextContent();//getFirstChild().getNodeValue();

                            URL _link = new URL(_linkE.getTextContent());//getFirstChild().getNodeValue());
                            String _description = _descriptionE.getTextContent();//getFirstChild().getNodeValue();

                            Date _pubDateT =  new Date(_pubDateE.getTextContent());

                            SimpleDateFormat df = new SimpleDateFormat("E dd/MM/yy HH:mm");
                            String _pubDate = df.format(_pubDateT);


                            if (!AnimeController.isAnimeInBase(activity, sqliteDB, _guid)){
                                String _imageLink = getAnimeImageURL(_link);

                                AnimeController.write(activity, sqliteDB, _guid, _title, _description, _link.toString(), _pubDate, _imageLink);
                            }else {
                                AnimeController.update(activity, sqliteDB, _title, _pubDate, _guid);
                            }


                            /*
                            AnimeItem newAnimeItem = new AnimeItem(_guid,_title, _link, _description, _pubDate);
                            animeItemArrayList.add(newAnimeItem);

                             */

                        } // end of item array

                        sqliteDB.close();
                        dbhelper.close();
                    } catch (SQLiteException e) {
                        Log.e(TAG, "Failed open rimes database. ", e);
                    } catch (SQLException e) {
                        Log.e(TAG, "Failed to insert Names. ", e);
                    }
                DataStorage.setAnimeList(animeItemArrayList);
            }
            else {
                // Bad Connection
            }
        }
        catch (IOException ex){
            // Bad Connection
        }


        return null;
    }

    public String getAnimeImageURL(URL linkToAnimaPage){

        //Создаём объект HtmlCleaner
        HtmlCleaner cleaner = new HtmlCleaner();
        //Загружаем html код сайта
        try {
            rootNode = cleaner.clean(linkToAnimaPage);
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Выбираем все ссылки
        TagNode divElements[] = rootNode.getElementsByName("div", true);

        String tempUrl = null;
        for (int i = 0; divElements != null && i < divElements.length; i++) {
            //получаем атрибут по имени
            String classType = divElements[i].getAttributeByName("class");
            //если атрибут есть и он эквивалентен искомому, то добавляем в список
            if (classType != null && classType.equals("poster_img")) {
                List<TagNode> temp = divElements[i].getChildTagList();
                tempUrl = temp.get(temp.size()-1).getAttributeByName("src").toString();

            }

        }
        return tempUrl;
    }
}
