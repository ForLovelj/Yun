package dth.com.yun.model;

import java.io.Serializable;

public class DoubanMeizi implements Serializable{

  private String url;

  private String title;

  private int type;


  public int getType() {

    return type;
  }


  public void setType(int type) {

    this.type = type;
  }


  public String getUrl() {

    return url;
  }


  public void setUrl(String url) {

    this.url = url;
  }


  public String getTitle() {

    return title;
  }


  public void setTitle(String title) {

    this.title = title;
  }
}
