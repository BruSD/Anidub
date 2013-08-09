package brusd.anidub.com.DBAniDubPackage;

import android.provider.BaseColumns;

/**
 * Created by BruSD on 04.08.13.
 */

    /** Describes History Table and model. */
    public class Names {


        /** Default "ORDER BY" clause. */
        //сортируем по фамилии в убывающем порядке
        //public static final String DEFAULT_SORT = NamesColumns.FNAME + " DESC";


        //имя таблицы
        public static final String TABLE_NAME = "Anime";

        //наш айдишник
        private String guid_anime;
        //поле заголовок Анимешки
        private String title_anime;
        //Описание Аниме
        private String description_anime;
        //Ссылка на Аниме
        private String link_anime;
        //Дата обновления Аниме
        private String up_date_anime;
        //Ссылка на картинку
        private String image_link_anime;
        //
        // Ниже идут сетеры и гетеры для захвата данных из базы
        //
        public String getGuid_anime(){
            return guid_anime;
        }
        public void setGuid_anime(String guidAnime){
            this.guid_anime = guidAnime;
        }

        public String getTitle_anime(){
            return title_anime;
        }
        public void setTitle_anime(String titleAnime){
            this.title_anime = titleAnime;
        }

        public String getDescription_anime(){
            return  description_anime;
        }
        public void setDescription_anime(String descriptionAnime){
            this.description_anime = descriptionAnime;
        }

        public String getLink_anime(){
            return link_anime;
        }
        public void setLink_anime(String linkAnime){
            this.link_anime = linkAnime;
        }

        public String getUp_date_anime(){
            return up_date_anime;
        }
        public void setUp_date_anime(String upDateAnime){
            this.up_date_anime = upDateAnime;
        }

        public String getImage_link_anime (){
            return image_link_anime;
        }
        public void setImage_link_anime(String imageLinkAnime){
            this.image_link_anime = imageLinkAnime;
        }




/*
                 * (non-Javadoc)
                 *
                 * @see java.lang.Object#toString()
                 */
        @Override
        public String toString() {

            /*StringBuilder builder = new StringBuilder();
            builder.append(fname);
            */
            return null;//builder.toString();

        }

        //Класс с именами наших полей в базе
        public class NamesColumns implements BaseColumns {

            /** Strings */
            public static final String GUID_ANIME = "guid_anime";
            /** String */
            public static final String TITLE_ANIME = "title_anime";
            /** String */
            public static final String DESCRIPTION_ANIME = "description_anime";
            /** String */
            public static final String LINK_ANIME = "link_anime";
            /** String */
            public static final String UP_DATE_ANIME = "up_date_anime";
            /** String */
            public static final String IMAGE_LINK_ANIME = "image_link_anime";

        }
    }

