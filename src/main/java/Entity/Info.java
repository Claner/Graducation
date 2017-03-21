package Entity;

/**
 * Created by Clanner on 2017/3/19.
 */
public class Info {
    private String publish;
    private String time;
    private String title;
    private String link;
    private String content;

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Info(){}

    public static class Builder {
        private String publish;
        private String time;
        private String title;
        private String link;
        private String content;

        public Builder setPublish(String publish) {
            this.publish = publish;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Info build() {
            Info info = new Info();
            info.publish = publish;
            info.time = time;
            info.title = title;
            info.link = link;
            info.content = content;
            return info;
        }
    }
}
