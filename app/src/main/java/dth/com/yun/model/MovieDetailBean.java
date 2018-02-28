package dth.com.yun.model;

import java.util.List;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class MovieDetailBean {


    /**
     * aka : ["极限特工：终极回归","3X反恐暴族：重火力回归(港)","限制级战警3: 重返极限(台)","极限特工3","Xander Returns"]
     * alt : https://movie.douban.com/subject/3230115/
     * casts : [{"alt":"https://movie.douban.com/celebrity/1041020/","avatars":{"large":"https://img5.doubanio.com/img/celebrity/large/53186.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/53186.jpg","small":"https://img5.doubanio.com/img/celebrity/small/53186.jpg"},"id":"1041020","name":"范·迪塞尔"},{"alt":"https://movie.douban.com/celebrity/1025194/","avatars":{"large":"https://img3.doubanio.com/img/celebrity/large/10695.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/10695.jpg","small":"https://img3.doubanio.com/img/celebrity/small/10695.jpg"},"id":"1025194","name":"甄子丹"},{"alt":"https://movie.douban.com/celebrity/1014002/","avatars":{"large":"https://img5.doubanio.com/img/celebrity/large/4946.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/4946.jpg","small":"https://img5.doubanio.com/img/celebrity/small/4946.jpg"},"id":"1014002","name":"迪皮卡·帕度柯妮"},{"alt":"https://movie.douban.com/celebrity/1337000/","avatars":{"large":"https://img3.doubanio.com/img/celebrity/large/1401722517.74.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1401722517.74.jpg","small":"https://img3.doubanio.com/img/celebrity/small/1401722517.74.jpg"},"id":"1337000","name":"吴亦凡"}]
     * collect_count : 34788
     * comments_count : 18052
     * countries : ["美国"]
     * directors : [{"alt":"https://movie.douban.com/celebrity/1014019/","avatars":{"large":"https://img3.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png","medium":"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png","small":"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png"},"id":"1014019","name":"D·J·卡卢索"}]
     * douban_site : 
     * genres : ["动作","惊悚","冒险"]
     * id : 3230115
     * images : {"large":"https://img5.doubanio.com/view/movie_poster_cover/lpst/public/p2410569976.jpg","medium":"https://img5.doubanio.com/view/movie_poster_cover/spst/public/p2410569976.jpg","small":"https://img5.doubanio.com/view/movie_poster_cover/ipst/public/p2410569976.jpg"}
     * mobile_url : https://movie.douban.com/subject/3230115/mobile
     * original_title : xXx: The Return of Xander Cage
     * rating : {"average":5.8,"max":10,"min":0,"stars":"30"}
     * ratings_count : 32455
     * reviews_count : 496
     * schedule_url : https://movie.douban.com/subject/3230115/cinema/
     * share_url : https://m.douban.com/movie/subject/3230115
     * subtype : movie
     * summary : 故事聚焦在由范·迪塞尔带头的的特工小队和以甄子丹为首的反派组织之间的对决。在这部作品中，迪塞尔饰演的特工凯奇不再是孤胆英雄，他将与一群出色的伙伴共同作战：塞缪尔·杰克逊饰演的国安局特工，印度女星迪皮卡·帕度柯妮饰演的与凯奇颇有渊源的女猎人，凭借《吸血鬼日记》走红的妮娜·杜波夫扮演的技术专家，《女子监狱》女星露比·罗丝饰演的狙击手,中国当红偶像演员吴亦凡饰演的特工Nicks。
     * title : 极限特工3：终极回归
     * wish_count : 14656
     * year : 2017
     */

    private String alt;
    private int    collect_count;
    private int    comments_count;
    private String douban_site;
    private String id;
    /**
     * large : https://img5.doubanio.com/view/movie_poster_cover/lpst/public/p2410569976.jpg
     * medium : https://img5.doubanio.com/view/movie_poster_cover/spst/public/p2410569976.jpg
     * small : https://img5.doubanio.com/view/movie_poster_cover/ipst/public/p2410569976.jpg
     */

    private ImagesEntity images;
    private String mobile_url;
    private String original_title;
    /**
     * average : 5.8
     * max : 10
     * min : 0
     * stars : 30
     */

    private RatingEntity rating;
    private int          ratings_count;
    private int          reviews_count;
    private String       schedule_url;
    private String       share_url;
    private String       subtype;
    private String                summary;
    private String                title;
    private int                   wish_count;
    private String                year;
    private List<String>          aka;
    /**
     * alt : https://movie.douban.com/celebrity/1041020/
     * avatars : {"large":"https://img5.doubanio.com/img/celebrity/large/53186.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/53186.jpg","small":"https://img5.doubanio.com/img/celebrity/small/53186.jpg"}
     * id : 1041020
     * name : 范·迪塞尔
     */

    private List<PersonEntity>    casts; //casts 数据和directors相同  手动解析成同一个bean
    private List<String>          countries;
    /**
     * alt : https://movie.douban.com/celebrity/1014019/
     * avatars : {"large":"https://img3.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png","medium":"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png","small":"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png"}
     * id : 1014019
     * name : D·J·卡卢索
     */

    private List<PersonEntity> directors;
    private List<String> genres;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public List<PersonEntity> getCasts() {
        return casts;
    }

    public void setCasts(List<PersonEntity> casts) {
        this.casts = casts;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<PersonEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<PersonEntity> directors) {
        this.directors = directors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public static class ImagesEntity {
        private String large;
        private String medium;
        private String small;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }
    }

    public static class RatingEntity {
        private double average;
        private int    max;
        private int    min;
        private String stars;

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }
    }

    public static class PersonEntity {
        private String alt;
        /**
         * large : https://img5.doubanio.com/img/celebrity/large/53186.jpg
         * medium : https://img5.doubanio.com/img/celebrity/medium/53186.jpg
         * small : https://img5.doubanio.com/img/celebrity/small/53186.jpg
         */

        private AvatarsEntity avatars;
        private String id;
        private String name;
        private String type;//手动增加一个字段  表示导演 或者演员


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsEntity getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsEntity avatars) {
            this.avatars = avatars;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class AvatarsEntity {
            private String large;
            private String medium;
            private String small;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }
        }
    }

    /*public static class personEntity {
        private String alt;
        *//**
         * large : https://img3.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png
         * medium : https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png
         * small : https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png
         *//*

        private AvatarsEntity avatars;
        private String id;
        private String name;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsEntity getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsEntity avatars) {
            this.avatars = avatars;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class AvatarsEntity {
            private String large;
            private String medium;
            private String small;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }
        }
    }*/
}
