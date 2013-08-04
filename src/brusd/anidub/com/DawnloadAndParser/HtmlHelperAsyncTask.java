package brusd.anidub.com.DawnloadAndParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brusd.anidub.com.R;


/**
 * Created by BruSD on 21.07.13.
 */
public class HtmlHelperAsyncTask extends AsyncTask<Void, Void, String> {
    TagNode rootNode;
    private URL htmlPage;
    private ProgressDialog dialog;
    private Activity activity;
    private Fragment fragment;

    public HtmlHelperAsyncTask(Activity activity, URL htmlPage){
        this.htmlPage = htmlPage;
        this.fragment = fragment;
        this.activity = activity;
    }
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
    protected void onPostExecute(String result) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(activity, result, Toast.LENGTH_LONG).show();

    }

    @Override
    protected String doInBackground(Void... params) {
        //Создаём объект HtmlCleaner
        HtmlCleaner cleaner = new HtmlCleaner();
        //Загружаем html код сайта
        try {
            rootNode = cleaner.clean(htmlPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String, ?>> imageURLtag = null;
        List<TagNode> linkList = new ArrayList<TagNode>();

        //Выбираем все ссылки
        TagNode divElements[] = rootNode.getElementsByName("div", true);
        String resultURL = null;
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
