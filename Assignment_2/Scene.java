// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;
 

public class Scene{
   private String name;
   private String img;
   private int budget;
   private int sceneNumber;
   private String sceneDescription;
   private List<Player> players;
   private List<OnCardRole> availableRoles;
   private List<OnCardRole> takenRoles;
   private boolean active;
   
   Scene(){}

   Scene(String name, String img, int budget){
      this.name = name;
      this.img = img;
      this.budget = budget;
      this.players = new ArrayList<Player>();
      this.availableRoles = new ArrayList<OnCardRole>();
      this.takenRoles = new ArrayList<OnCardRole>();
   }

   // Main method for the purpose of testing each respective class function.
   public static void main (String args[]){
   
   }
   
   public String getName(){
      return name;
   }
   
   public void printInfo(){
      System.out.println("/////////////////////////////////////////////////////////////////////////////////////");
      System.out.println(name + "\nScene " + sceneNumber +": " + sceneDescription + " | Budget: " + budget);
      System.out.println("/////////////////////////////////////////////////////////////////////////////////////");
   }
   
   public void roleIsTaken(String title){
      for(int i = 0; i <availableRoles.size(); i++){
         if(availableRoles.get(i).getName().equals(title)){
            OnCardRole taken = availableRoles.remove(i);// pull that role from the available roles
            takenRoles.add(taken);// and put it into the taken roles list
         }
      } 
   }
   
   private boolean isActive(){
      active = false;
      return active;
   }

   public void addAvailRoles(OnCardRole role){
      availableRoles.add(role);
   }
   public List<OnCardRole> getAvailRoles(){
      return availableRoles;
   }
   public List<OnCardRole> getTakenRoles(){
      return takenRoles;
   }

   public void addOnCardActor(Player player){
      players.add(player);
   }
   public List<Player> getOnCardActors(){
      return players;
   }
   public void setSceneNumber(int sceneNumber){
      this.sceneNumber = sceneNumber;
   }
   public void setSceneDescription(String sceneDescription){
      this.sceneDescription = sceneDescription;
   }
   public int getBudget(){
      return budget;
   }
   public String getImg(){
      return img;
   }
}
