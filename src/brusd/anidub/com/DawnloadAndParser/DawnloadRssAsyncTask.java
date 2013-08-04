package brusd.anidub.com.DawnloadAndParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

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
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import brusd.anidub.com.AnidabFragment.UpdataListFragment;
import brusd.anidub.com.DataClasses.AnimeItem;
import brusd.anidub.com.DataClasses.DataStorage;
import brusd.anidub.com.MainActivity;
import brusd.anidub.com.R;

/**
 * Created by BruSD on 20.07.13.
 */


public class DawnloadRssAsyncTask extends AsyncTask<Void, Void, Integer> {

    private ProgressDialog dialog;
    private Activity activity = null;
    private  URL xmlUrl;
    private Fragment fragment;

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
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Загрузка...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(R.layout.custom_progress_dialog_layout_anidub);
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        if (activity != null && activity instanceof MainActivity) {
            if(fragment != null && fragment instanceof UpdataListFragment){
                ((UpdataListFragment)fragment).loadAnimeRssList();

            }
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
                        String _pubDate = _pubDateE.getTextContent();

                        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy KK:mm:ss ", Locale.getDefault());
                        String newPubDate =new String();
                        try {
                            newPubDate = format.parse(_pubDate).toString();
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        AnimeItem newAnimeItem = new AnimeItem(_guid,_title, _link, _description, _pubDate);

                        animeItemArrayList.add(newAnimeItem);


                    } // end of item array
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
}
