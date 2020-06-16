// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;
 
public class OnCardRole{
   private String name;
   private int level;
   private int[] area;
   private String line;
   private String cardName;
   private Player player;

   // Main method for the purpose of testing each respective class function.
   public static void main (String args[]){
   
   }
   
   OnCardRole(String name, int level, int[] area, String line, String cardName){
      this.name = name;
      this.level = level;
      this.area = area;
      this.line = line;
      this.cardName = cardName;
      this.player = null;
   }
   
   
   // Getters-and-Setters
   
   public String getName(){
      return name;
   }
   public String getLine(){
      return line;
   }
   public int getLevel(){
      return level;
   }
   public String getCardName(){
      return cardName;
   }
   public void setPlayer(Player player){
      this.player = player;
   }
   public Player getPlayer(){
      return player;
   }
   public int[] getArea() {
      return area;
   }


}
