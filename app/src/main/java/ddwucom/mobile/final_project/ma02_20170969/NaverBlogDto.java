package ddwucom.mobile.final_project.ma02_20170969;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class NaverBlogDto implements Serializable {

    private int _id;
    private String title; // 블로그 게시물 제목
    private String blogLink; // 블로그 링크
    private String description; // 블로그 게시물 내용
//    private String imageLink;
//    private String imageFileName;       // 외부저장소에 저장했을 때의 파일명

    public NaverBlogDto() {
//        this.imageFileName = null;      // 생성 시에는 외부저장소에 파일이 없으므로 null로 초기화
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
//        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  _id + ": " + title + " (" + blogLink + ')';
    }
}
