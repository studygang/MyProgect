package com.gangzi.myprogect.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class Movie {

    /**
     * reason : 查询成功
     * result : {"title":"心花路放","tag":"喜剧 / 爱情","act":"黄渤 徐峥 袁泉 周冬雨 熊乃瑾 陶慧 马苏 岳小军 沈腾 张俪 刘美含 焦俊艳 李晨 郭涛 夏雨","year":"2014","rating":7,"area":"大陆","dir":"宁浩","desc":"耿浩（黄渤 饰）在偶遇\u201c小三\u201d危机，陷入情感困境。面对背叛，耿浩陷入了难以自拔的痛苦之中，好基友郝义（徐峥 饰）为了帮他摆脱痛苦，决定带他南下\u201c猎艳\u201d，遍访\u201c百花\u201d。于是两个\u201c暴走兄弟\u201d带上一只狗，开始了一段疯狂而搞笑的放浪旅途。一路上他们结识了各式女伴，并经历了一连串奇葩的遭遇。最后，两人最终明白了爱的真谛，并收获了彼此的幸福。","cover":"http://p5.qhimg.com/t0171e200d2546740aa.jpg","vdo_status":"play","playlinks":{"qq":"http://v.qq.com/cover/r/relqwr6c0in3s5b.html?ptag=360kan.movie","pptv":"http://v.pptv.com/show/lyibYF3iclVZP2dHg.html","imgo":"http://www.mgtv.com/b/102839/1103088.html?cxid=90f0zbamf","tudou":"http://www.tudou.com/albumplay/KE2zJh9AWII/2Otbf25W5h4.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwMV8wMV8wMQ","youku":"http://v.youku.com/v_show/id_XODMzOTcyNjg0.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwN18wMV8wMQ"},"video_rec":[{"cover":"http://p4.qhimg.com/t01b2c8f106c3a06fc8.jpg","detail_url":"http://www.360kan.com/m/gKPkYUH6SHnAUR.html","title":"人再囧途之泰囧"},{"cover":"http://p9.qhimg.com/d/dy_cd647df6fd13a41877b4cf257946c8d9.jpg","detail_url":"http://www.360kan.com/m/gaPnZkYndXb2Th.html","title":"人在囧途"},{"cover":"http://p2.qhimg.com/d/dy_70dcd0ab72b788a5f8fe4f68d5680312.jpg","detail_url":"http://www.360kan.com/m/faPrZRH3S0HAUB.html","title":"人再囧途之泰囧（纪录片）"},{"cover":"http://p1.qhimg.com/t0180f69b25dd6ee524.jpg","detail_url":"http://www.360kan.com/m/hqXiZBH1Q0P5UB.html","title":"后会无期"}],"act_s":[{"name":"黄渤","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t0175e2bf9bc9d6559e.jpg"},{"name":"徐峥","url":null,"image":"http://p3.qhmsg.com/dmsmty/120_110_100/t0185ffffc651394fac.jpg"},{"name":"袁泉","url":null,"image":"http://p3.qhmsg.com/dmsmty/120_110_100/t017f7ecd3855dce35d.jpg"},{"name":"周冬雨","url":null,"image":"http://p4.qhmsg.com/dmsmty/120_110_100/t01ab74a07f7a8f9512.jpg"},{"name":"熊乃瑾","url":null,"image":"http://p2.qhmsg.com/dmsmty/120_110_100/t01d823b15833217c5c.jpg"},{"name":"陶慧","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t010252027bded35ada.jpg"},{"name":"马苏","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t01a6a6448c71c15fd1.jpg"},{"name":"岳小军","url":null,"image":"http://p8.qhmsg.com/dmsmty/120_110_100/t01b71ebbafcec36355.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * title : 心花路放
         * tag : 喜剧 / 爱情
         * act : 黄渤 徐峥 袁泉 周冬雨 熊乃瑾 陶慧 马苏 岳小军 沈腾 张俪 刘美含 焦俊艳 李晨 郭涛 夏雨
         * year : 2014
         * rating : 7
         * area : 大陆
         * dir : 宁浩
         * desc : 耿浩（黄渤 饰）在偶遇“小三”危机，陷入情感困境。面对背叛，耿浩陷入了难以自拔的痛苦之中，好基友郝义（徐峥 饰）为了帮他摆脱痛苦，决定带他南下“猎艳”，遍访“百花”。于是两个“暴走兄弟”带上一只狗，开始了一段疯狂而搞笑的放浪旅途。一路上他们结识了各式女伴，并经历了一连串奇葩的遭遇。最后，两人最终明白了爱的真谛，并收获了彼此的幸福。
         * cover : http://p5.qhimg.com/t0171e200d2546740aa.jpg
         * vdo_status : play
         * playlinks : {"qq":"http://v.qq.com/cover/r/relqwr6c0in3s5b.html?ptag=360kan.movie","pptv":"http://v.pptv.com/show/lyibYF3iclVZP2dHg.html","imgo":"http://www.mgtv.com/b/102839/1103088.html?cxid=90f0zbamf","tudou":"http://www.tudou.com/albumplay/KE2zJh9AWII/2Otbf25W5h4.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwMV8wMV8wMQ","youku":"http://v.youku.com/v_show/id_XODMzOTcyNjg0.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwN18wMV8wMQ"}
         * video_rec : [{"cover":"http://p4.qhimg.com/t01b2c8f106c3a06fc8.jpg","detail_url":"http://www.360kan.com/m/gKPkYUH6SHnAUR.html","title":"人再囧途之泰囧"},{"cover":"http://p9.qhimg.com/d/dy_cd647df6fd13a41877b4cf257946c8d9.jpg","detail_url":"http://www.360kan.com/m/gaPnZkYndXb2Th.html","title":"人在囧途"},{"cover":"http://p2.qhimg.com/d/dy_70dcd0ab72b788a5f8fe4f68d5680312.jpg","detail_url":"http://www.360kan.com/m/faPrZRH3S0HAUB.html","title":"人再囧途之泰囧（纪录片）"},{"cover":"http://p1.qhimg.com/t0180f69b25dd6ee524.jpg","detail_url":"http://www.360kan.com/m/hqXiZBH1Q0P5UB.html","title":"后会无期"}]
         * act_s : [{"name":"黄渤","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t0175e2bf9bc9d6559e.jpg"},{"name":"徐峥","url":null,"image":"http://p3.qhmsg.com/dmsmty/120_110_100/t0185ffffc651394fac.jpg"},{"name":"袁泉","url":null,"image":"http://p3.qhmsg.com/dmsmty/120_110_100/t017f7ecd3855dce35d.jpg"},{"name":"周冬雨","url":null,"image":"http://p4.qhmsg.com/dmsmty/120_110_100/t01ab74a07f7a8f9512.jpg"},{"name":"熊乃瑾","url":null,"image":"http://p2.qhmsg.com/dmsmty/120_110_100/t01d823b15833217c5c.jpg"},{"name":"陶慧","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t010252027bded35ada.jpg"},{"name":"马苏","url":null,"image":"http://p5.qhmsg.com/dmsmty/120_110_100/t01a6a6448c71c15fd1.jpg"},{"name":"岳小军","url":null,"image":"http://p8.qhmsg.com/dmsmty/120_110_100/t01b71ebbafcec36355.jpg"}]
         */

        private String title;
        private String tag;
        private String act;
        private String year;
        private int rating;
        private String area;
        private String dir;
        private String desc;
        private String cover;
        private String vdo_status;
        private PlaylinksBean playlinks;
        private List<VideoRecBean> video_rec;
        private List<ActSBean> act_s;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getVdo_status() {
            return vdo_status;
        }

        public void setVdo_status(String vdo_status) {
            this.vdo_status = vdo_status;
        }

        public PlaylinksBean getPlaylinks() {
            return playlinks;
        }

        public void setPlaylinks(PlaylinksBean playlinks) {
            this.playlinks = playlinks;
        }

        public List<VideoRecBean> getVideo_rec() {
            return video_rec;
        }

        public void setVideo_rec(List<VideoRecBean> video_rec) {
            this.video_rec = video_rec;
        }

        public List<ActSBean> getAct_s() {
            return act_s;
        }

        public void setAct_s(List<ActSBean> act_s) {
            this.act_s = act_s;
        }

        public static class PlaylinksBean {
            /**
             * qq : http://v.qq.com/cover/r/relqwr6c0in3s5b.html?ptag=360kan.movie
             * pptv : http://v.pptv.com/show/lyibYF3iclVZP2dHg.html
             * imgo : http://www.mgtv.com/b/102839/1103088.html?cxid=90f0zbamf
             * tudou : http://www.tudou.com/albumplay/KE2zJh9AWII/2Otbf25W5h4.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwMV8wMV8wMQ
             * youku : http://v.youku.com/v_show/id_XODMzOTcyNjg0.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwN18wMV8wMQ
             */

            private String qq;
            private String pptv;
            private String imgo;
            private String tudou;
            private String youku;

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getPptv() {
                return pptv;
            }

            public void setPptv(String pptv) {
                this.pptv = pptv;
            }

            public String getImgo() {
                return imgo;
            }

            public void setImgo(String imgo) {
                this.imgo = imgo;
            }

            public String getTudou() {
                return tudou;
            }

            public void setTudou(String tudou) {
                this.tudou = tudou;
            }

            public String getYouku() {
                return youku;
            }

            public void setYouku(String youku) {
                this.youku = youku;
            }
        }

        public static class VideoRecBean {
            /**
             * cover : http://p4.qhimg.com/t01b2c8f106c3a06fc8.jpg
             * detail_url : http://www.360kan.com/m/gKPkYUH6SHnAUR.html
             * title : 人再囧途之泰囧
             */

            private String cover;
            private String detail_url;
            private String title;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ActSBean {
            /**
             * name : 黄渤
             * url : null
             * image : http://p5.qhmsg.com/dmsmty/120_110_100/t0175e2bf9bc9d6559e.jpg
             */

            private String name;
            private Object url;
            private String image;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
