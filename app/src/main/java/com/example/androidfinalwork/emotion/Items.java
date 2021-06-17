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
public class Items {

    private List<Subitems> subitems;
    private List<String> replies;
    private double prob;
    private String label;
    public void setSubitems(List<Subitems> subitems) {
         this.subitems = subitems;
     }
     public List<Subitems> getSubitems() {
         return subitems;
     }

    public void setReplies(List<String> replies) {
         this.replies = replies;
     }
     public List<String> getReplies() {
         return replies;
     }

    public void setProb(double prob) {
         this.prob = prob;
     }
     public double getProb() {
         return prob;
     }

    public void setLabel(String label) {
         this.label = label;
     }
     public String getLabel() {
         return label;
     }

}