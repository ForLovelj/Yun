package dth.com.yun.model;

import java.util.List;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class BookDetailBean {


    /**
     * max : 10
     * numRaters : 254457
     * average : 8.8
     * min : 0
     */

    private RatingEntity rating;
    /**
     * rating : {"max":10,"numRaters":254457,"average":"8.8","min":0}
     * subtitle :
     * author : ["[美] 卡勒德·胡赛尼"]
     * pubdate : 2006-5
     * tags : [{"count":56300,"name":"追风筝的人","title":"追风筝的人"},{"count":39236,"name":"阿富汗","title":"阿富汗"},{"count":38468,"name":"人性","title":"人性"},{"count":35813,"name":"救赎","title":"救赎"},{"count":35007,"name":"小说","title":"小说"},{"count":25679,"name":"卡勒德·胡赛尼","title":"卡勒德·胡赛尼"},{"count":24541,"name":"外国文学","title":"外国文学"},{"count":13276,"name":"外国小说","title":"外国小说"}]
     * origin_title : The Kite Runner
     * image : https://img3.doubanio.com/mpic/s1727290.jpg
     * binding : 平装
     * translator : ["李继宏"]
     * catalog : 第一章
     第二章
     第三章
     第四章
     第五章
     第六章
     第七章
     第八章
     第九章
     第十章
     第十一章
     第十二章
     第十三章
     第十四章
     第十五章
     第十六章
     第十七章
     第十八章
     第十九章
     第二十章
     第二十一章
     第二十二章
     第二十三章
     第二十四章
     第二十五章
     译后记
     * ebook_url : https://read.douban.com/ebook/1162265/
     * pages : 362
     * images : {"small":"https://img3.doubanio.com/spic/s1727290.jpg","large":"https://img3.doubanio.com/lpic/s1727290.jpg","medium":"https://img3.doubanio.com/mpic/s1727290.jpg"}
     * alt : https://book.douban.com/subject/1770782/
     * id : 1770782
     * publisher : 上海人民出版社
     * isbn10 : 7208061645
     * isbn13 : 9787208061644
     * title : 追风筝的人
     * url : https://api.douban.com/v2/book/1770782
     * alt_title : The Kite Runner
     * author_intro : 卡勒德·胡赛尼（Khaled Hosseini），1965年生于阿富汗喀布尔市，后随父亲迁往美国。胡赛尼毕业于加州大学圣地亚哥医学系，现居加州。“立志拂去蒙在阿富汗普通民众面孔的尘灰，将背后灵魂的悸动展示给世人。”著有小说《追风筝的人》(The Kite Runner，2003）、《灿烂千阳》(A Thousand Splendid Suns，2007)、《群山回唱》（And the Mountains Echoed,2013）。作品全球销量超过4000万册。2006年，因其作品巨大的国际影响力，胡赛尼获得联合国人道主义奖，并受邀担任联合国难民署亲善大使。
     * summary : 12岁的阿富汗富家少爷阿米尔与仆人哈桑情同手足。然而，在一场风筝比赛后，发生了一件悲惨不堪的事，阿米尔为自己的懦弱感到自责和痛苦，逼走了哈桑，不久，自己也跟随父亲逃往美国。
     成年后的阿米尔始终无法原谅自己当年对哈桑的背叛。为了赎罪，阿米尔再度踏上暌违二十多年的故乡，希望能为不幸的好友尽最后一点心力，却发现一个惊天谎言，儿时的噩梦再度重演，阿米尔该如何抉择？
     故事如此残忍而又美丽，作者以温暖细腻的笔法勾勒人性的本质与救赎，读来令人荡气回肠。
     * ebook_price : 12.99
     * series : {"id":"19760","title":"卡勒德·胡赛尼作品"}
     * price : 29.00元
     */

    private String       subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String ebook_url;
    private String pages;
    /**
     * small : https://img3.doubanio.com/spic/s1727290.jpg
     * large : https://img3.doubanio.com/lpic/s1727290.jpg
     * medium : https://img3.doubanio.com/mpic/s1727290.jpg
     */

    private ImagesEntity images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String ebook_price;
    /**
     * id : 19760
     * title : 卡勒德·胡赛尼作品
     */

    private SeriesEntity series;
    private String       price;
    private List<String> author;
    /**
     * count : 56300
     * name : 追风筝的人
     * title : 追风筝的人
     */

    private List<TagsEntity> tags;
    private List<String> translator;

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public void setSeries(SeriesEntity series) {
        this.series = series;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public String getImage() {
        return image;
    }

    public String getBinding() {
        return binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public String getPages() {
        return pages;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public String getEbook_price() {
        return ebook_price;
    }

    public SeriesEntity getSeries() {
        return series;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public static class RatingEntity {
        private int    max;
        private int    numRaters;
        private String average;
        private int    min;

        public void setMax(int max) {
            this.max = max;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public String getAverage() {
            return average;
        }

        public int getMin() {
            return min;
        }
    }

    public static class ImagesEntity {
        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }

    public static class SeriesEntity {
        private String id;
        private String title;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class TagsEntity {
        private int    count;
        private String name;
        private String title;

        public void setCount(int count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }
    }
}
