// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;
 
public class OffCardRole{
   private String name;
   private String quote;
   private int[] area;
   private int rank;
   private String scene;
   private Player player;

   // Main method for the purpose of testing each respective class function.
   public static void main (String args[]){
   
   }
   
   OffCardRole(String name, int rank, String quote, int[] area){
      this.name = name;
      this.rank = rank;
      this.quote = quote;
      this.area = area;
      this.player = null;
   }
   
   
   
   // Getters-and-Setters
   
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name = name;
   }
   public String getQuote(){
      return quote;
   }
   public void setQuote(String quote){
      this.quote = quote;
   }
   public int getRank(){
      return rank;
   }
   public void setRank(int rank){
      this.rank = rank;
   }
   public String getScene(){
      return scene;
   }
   public void setScene(String scene){
      this.scene = scene;
   }
   public Player getPlayer(){
      return player;
   }
   public void setPlayer(Player player){
      this.player = player;
   }
   public int[] getArea() {
      return area;
   }


}
