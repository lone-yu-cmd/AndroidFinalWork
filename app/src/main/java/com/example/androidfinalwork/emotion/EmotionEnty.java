/**
  * Copyright 2021 bejson.com 
  */
package com.example.androidfinalwork.emotion;
import java.util.List;

/**
 * Auto-generated: 2021-06-17 13:33:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class EmotionEnty {

    private long log_id;
    private String text;
    private List<Items> items;
    public void setLog_id(long log_id) {
         this.log_id = log_id;
     }
     public long getLog_id() {
         return log_id;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setItems(List<Items> items) {
         this.items = items;
     }
     public List<Items> getItems() {
         return items;
     }

}