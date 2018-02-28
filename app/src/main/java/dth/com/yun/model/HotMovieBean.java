package dth.com.yun.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class HotMovieBean {




    private int count;
    private int    start;
    private String title;
    private int    total;
    /**
     * alt : https://movie.douban.com/subject/3230115/
     * casts : [{"alt":"https://movie.douban.com/celebrity/1041020/","avatars":{"large":"https://img5.doubanio.com/img/celebrity/large/53186.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/53186.jpg","small":"https://img5.doubanio.com/img/celebrity/small/53186.jpg"},"id":"1041020","name":"范·迪塞尔"},{"alt":"https://movie.douban.com/celebrity/1025194/","avatars":{"large":"https://img3.doubanio.com/img/celebrity/large/10695.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/10695.jpg","small":"https://img3.doubanio.com/img/celebrity/small/10695.jpg"},"id":"1025194","name":"甄子丹"},{"alt":"https://movie.douban.com/celebrity/1014002/","avatars":{"large":"https://img5.doubanio.com/img/celebrity/large/4946.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/4946.jpg","small":"https://img5.doubanio.com/img/celebrity/small/4946.jpg"},"id":"1014002","name":"迪皮卡·帕度柯妮"}]
     * collect_count : 32328
     * directors : [{"alt":"https://movie.douban.com/celebrity/1014019/","avatars":{"large":"https://img1.doubanio.com/img/celebrity/large/20007.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/20007.jpg","small":"https://img1.doubanio.com/img/celebrity/small/20007.jpg"},"id":"1014019","name":"D·J·卡卢索"}]
     * genres : ["动作","惊悚","冒险"]
     * id : 3230115
     * images : {"large":"https://img5.doubanio.com/view/movie_poster_cover/lpst/public/p2410569976.jpg","medium":"https://img5.doubanio.com/view/movie_poster_cover/spst/public/p2410569976.jpg","small":"https://img5.doubanio.com/view/movie_poster_cover/ipst/public/p2410569976.jpg"}
     * original_title : xXx: The Return of Xander Cage
     * rating : {"average":5.8,"max":10,"min":0,"stars":"30"}
     * subtype : movie
     * title : 极限特工3：终极回归
     * year : 2017
     */

    private List<SubjectsEntity> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SubjectsEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsEntity> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsEntity implements Serializable{
        private String alt;
        private int    collect_count;
        private String id;
        /**
         * large : https://img5.doubanio.com/view/movie_poster_cover/lpst/public/p2410569976.jpg
         * medium : https://img5.doubanio.com/view/movie_poster_cover/spst/public/p2410569976.jpg
         * small : https://img5.doubanio.com/view/movie_poster_cover/ipst/public/p2410569976.jpg
         */

        private ImagesEntity images;
        private String original_title;
        /**
         * average : 5.8
         * max : 10
         * min : 0
         * stars : 30
         */

        private RatingEntity rating;
        private String subtype;
        private String title;
        private String year;
        /**
         * alt : https://movie.douban.com/celebrity/1041020/
         * avatars : {"large":"https://img5.doubanio.com/img/celebrity/large/53186.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/53186.jpg","small":"https://img5.doubanio.com/img/celebrity/small/53186.jpg"}
         * id : 1041020
         * name : 范·迪塞尔
         */

        private List<CastsEntity>     casts;
        /**
         * alt : https://movie.douban.com/celebrity/1014019/
         * avatars : {"large":"https://img1.doubanio.com/img/celebrity/large/20007.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/20007.jpg","small":"https://img1.doubanio.com/img/celebrity/small/20007.jpg"}
         * id : 1014019
         * name : D·J·卡卢索
         */

        private List<DirectorsEntity> directors;
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

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<CastsEntity> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsEntity> casts) {
            this.casts = casts;
        }

        public List<DirectorsEntity> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsEntity> directors) {
            this.directors = directors;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public static class ImagesEntity implements Serializable{
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

        public static class RatingEntity implements Serializable{
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

        public static class CastsEntity implements Serializable {
            private String alt;
            /**
             * large : https://img5.doubanio.com/img/celebrity/large/53186.jpg
             * medium : https://img5.doubanio.com/img/celebrity/medium/53186.jpg
             * small : https://img5.doubanio.com/img/celebrity/small/53186.jpg
             */

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

            public static class AvatarsEntity implements Serializable{
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

        public static class DirectorsEntity implements Serializable{
            private String alt;
            /**
             * large : https://img1.doubanio.com/img/celebrity/large/20007.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/20007.jpg
             * small : https://img1.doubanio.com/img/celebrity/small/20007.jpg
             */

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

            public static class AvatarsEntity implements Serializable{
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
    }
}
