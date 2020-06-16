// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;
 
// The interface class is a collection of moves or operations that the player can perform 
// on their active turn, each with different applications and purposes spread out across the 
// game;therefore they are collected in this class to ease the flow of the game.
public class Interface{
   
   // Main method for the purpose of testing each respective class function.
   public static void main (String args[]){
   
   }
   
   
   // A method to show the current player all relevant information related to the game 
   public void checkStats(List<Player> players, Player player, int totalDays, int currentDay){
      System.out.println("***********STATS******************");
      System.out.println("Current player: " + player.getName() + " | Day " + currentDay + " of " + totalDays + " Days");
      System.out.println("Current location: " + player.getLocation());
      System.out.println("Current Rank: " + player.getRank());
      System.out.println("Current Role: " + player.getRole());
      System.out.println(" Cash  |  Credits ");
      System.out.println("  " +player.getCash() + "    |     " + player.getCredits());
      System.out.println("Rehearse Tokens: " + player.getRehearsals());
      System.out.println("Current score: " + (player.getRank()*5 + player.getCash() + player.getCredits()) );
      System.out.println("Player locations: ");
      for(int i = 0; i < players.size(); i++){
         System.out.println(players.get(i).getName() + " is in " + players.get(i).getLocation() + " with the role: " + players.get(i).getRole() + " |");
      }
      System.out.println("**********************************");
   }
   
   public String showStats(Player player, int totalDays, int currentDay){
      String ret = ("Current player: " + player.getName() + "\n Day " + currentDay + " of " + totalDays + " Days\n" + "Current location: " + player.getLocation() + "\n" + "Current Rank: " + player.getRank() + 
         "\nCurrent Role: " + player.getRole() + "\n" + " Cash  |  Credits \n" + "     " +player.getCash() + "    |     " + player.getCredits() + " \n" + "Rehearse Tokens: " + player.getRehearsals() + " \n" + 
            "Current score: " + (player.getRank()*5 + player.getCash() + player.getCredits()) + " \n");
      return ret;
   }
   
   
   // A simple method that prints out the map for the players to see all named locations
   // and their relevant connections 
   public void map(){      
      System.out.println("________________");
      System.out.println("| Deadwood Map |");
      System.out.println("___________________________________________________");
      System.out.println("|Train Station  |  Jail    |       Main Street    |");
      System.out.println("|                                                 |");
      System.out.println("|     |---  ----|----  ----|--  ------|-----  ----|");
      System.out.println("|     |    General Store      Saloon  |  Trailer  |");
      System.out.println("|                          |                      |");
      System.out.println("|-  --|--|-----------  ----+----  ----|-----  ----|");
      System.out.println("|        |                 |          |           |");
      System.out.println("|Casting |     Ranch       |   Bank   |   Hotel   |");
      System.out.println("|Office                                           |");
      System.out.println("|--  ----|----  -----------|----  ----|           |");
      System.out.println("|                   |                 |           |");
      System.out.println("|  Secret Hideout         Church                  |");
      System.out.println("|___________________|_________________|___________|");
  
   }
   
   // Method that takes the current active player and the board object, it presents them 
   // with what the possible moves are, and will prompt them until they make a valid move
   // updating their current location
   public void move(Player currentPlayer, Board board){
      String currLoc = currentPlayer.getLocation();
      System.out.println("Current location: " + currLoc);
      
      List<String> choices = new ArrayList<String>();
      choices = board.getAdjacent(currLoc);
 
      for(int i = 0; i < choices.size(); i++){
         System.out.println((i+1) + ": " + choices.get(i));// print all of their possible moves
      }
      System.out.print("Please enter the number of which location you would like to move to\n:");
      
      int input = getInput();
      while(input < 1 || input > choices.size()){// if they enter the wrong number
         System.out.println("Please enter the number of where you would like to move (from 1 to " + choices.size() + ")");
         input = getInput();
      }
      for(int i = 0; i < choices.size(); i++){
         if(i == (input - 1)){  
            currentPlayer.setLocation(choices.get(i));// set their current location NOW to the new location
            currentPlayer.setMoved(true);
            System.out.println("Current location now: " + currentPlayer.getLocation());
            if(!currentPlayer.getLocation().equals("office") && !currentPlayer.getLocation().equals("trailer")){
               board.checkFlipped(currentPlayer);// check if this location's set card has been flipped or not 
            }
            break;
         }
      }   
   }
   
   public Object[] getMoveOptions(Player currentPlayer, Board board){
      String currLoc = currentPlayer.getLocation();
      
      List<String> choices = new ArrayList<String>();
      choices = board.getAdjacent(currLoc);
      
      
      Object[] options = new Object[choices.size()];
      for(int i = 0; i < choices.size(); i++){
         options[i] = choices.get(i);
      }
      return options;
   }
   
      
   public String act(Board board, Player player){
      Set set = board.getCurrentSet(player.getLocation());// get the set the player is currently acting on
      Scene scene = set.getScene();
      
      String ret = "";
      
      int budget = scene.getBudget();
    
      int roll = rollDice();
      System.out.println("Die roll = " + roll + " + Rehearsal Tokens: " + player.getRehearsals() + " VS Budget: " + budget);
      ret += ("Die roll = " + roll + " + Rehearsal Tokens: " + player.getRehearsals() + " VS Budget: " + budget + "\n");
      if( (roll + player.getRehearsals()) >= budget){
         System.out.println("Act successful! Good Acting!");
         ret += "Act successful! Good Acting!\n";
         
         // pay them 
         if(player.getOnCardRole()){// if they are an on card actor, pay them 2 credits 
            System.out.println("You earned 2 credits!");
            ret += "You earned 2 credits!\n";
            player.addCredit(2);
         } else {// an off card actor 
            System.out.println("You earned 1 credit and $1!");
            ret += "You earned 1 credit and $1!\n";
            player.addCredit(1);
            player.addMoney(1);
         }
         
         set.removeShot();// decrease shot counter for the scene they are in 
         System.out.println("Shots left: " + set.getShots());
         ret += ("Shots left: " + set.getShots() + "\n");
         if(set.getShots() == 0){
            ret+= ("Scene is done filming!\n");
            if(set.endScene(board, player)){// if the end scene method returns true, then there was at least 1 actor on the card at the end 
               //List<OnCardRole> onRoles = board.getOnCardRoles(player.getLocation());
              List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
              onRoles = scene.getTakenRoles();
              
              List<OnCardRole> notTaken = board.getOnCardRoles(player.getLocation());
              for(int i = 0; i < notTaken.size(); i++){
                  onRoles.add(notTaken.get(i));
              }               
               ret += payoutRoll(onRoles, budget);// NOW payout all of the on card actors a bonus 
            }
         }
      } else {// else they failed the roll 
         if(player.getOnCardRole()){// if they failed the role as a lead actor, get nothing 
            System.out.println("Act unsuccessful, try again later!");
            ret += "Act unsuccessful, try again later!\n";
         } else {// an extra failing still earns money 
            System.out.println("Act unsuccessful, as an extra you earn $1");
            ret += "Act unsuccessful, as an extra you earn $1\n";
            player.addMoney(1);
         }
      }
      player.setActed(true);// set the has acted boolean to true
      return ret;
   }
   
   
   public boolean rehearse(Player player){
         // player.addRehearsal (if they are not at the limit)
         player.setRehearsed(true);// set the rehearsal boolean to true 
         int rehearse = player.getRehearsals();
         rehearse++;
         System.out.println("Current rehearsal tokens: " + rehearse);
         player.setRehearsals(rehearse);
      
         return true;
   }
   
   public String guiRaiseRank(Player player){
      String ret = "";
      ret+= ("Rank |  Dollars | Credits\n");
      ret+= (" 2   |     4    |    5\n");
      ret+= (" 3   |     10   |    10\n");
      ret+= (" 4   |     18   |    15\n");
      ret+= (" 5   |     28   |    20\n");
      ret+= (" 6   |     40   |    25\n");
      ret+= ("Which rank would you like to purchase?\n");
      ret+= ("Your Stats: Rank " + player.getRank() + " | $" + player.getCash() + " | Credits " + player.getCredits() + "\n");  
      return ret; 
   }
   public String checkCashRank(String s, Player player){
      String ret = "";
      int[] prices = {4,10,18,28,40};// upgrade costs for dollars, credits
      for(int i = 2; i < prices.length; i++){
         if(s.contains(Integer.toString(i))){
            System.out.println(i + " | " + prices[i-2]);
            if((i+2) <= player.getRank()){
               ret+=("You cannot lower your rank, try a different rank.");
            } else if(player.getCash() < prices[i-2]){
               ret+=("Not enough cash for this upgrade");
            } else {
               ret+=("Successfully upgraded to rank " + i + "!");
               player.setCash(player.getCash() - prices[i-2]);
               player.setRank(i);

            }
            
         }
      }
      return ret;
   }
   public String checkCreditRank(String s, Player player){
      int[] prices = {5,10,15,20,25};// upgrade costs for dollars, credits
      String ret = "";
      for(int i = 2; i < prices.length; i++){
         if(s.contains(Integer.toString(i))){
            System.out.println(i + " | " + prices[i-2]);
            if((i+2) <= player.getRank()){
               ret+=("You cannot lower your rank, try a different rank.");
            } else if(player.getCredits() < prices[i-2]){
               ret+=("Not enough credits for this upgrade");
            } else {
               ret+=("Successfully upgraded to rank " + i + "!");
               player.setCredits(player.getCredits() - prices[i-2]);
               player.setRank(i);

            }
         }
      }
      return ret;
   
   }
   


   // A method that will take in the player's desired rank and then will check if they 
   // have enough credits or cash to aqcuire said rank upgrade.
   public boolean raiseRank( Player player){
      int[][] prices = { {4,5},{10,10},{18,15},{28,20},{40,25}};// upgrade costs for dollars, credits

      System.out.println("Rank |  Dollars | Credits");
      System.out.println(" 2   |     4    |    5");
      System.out.println(" 3   |     10   |    10");
      System.out.println(" 4   |     18   |    15");
      System.out.println(" 5   |     28   |    20");
      System.out.println(" 6   |     40   |    25");
      System.out.print("Which rank would you like to purchase?");
      System.out.println("Your Stats: Rank " + player.getRank() + " | $" + player.getCash() + " | Credits " + player.getCredits());
      System.out.print("Enter 0. to exit.\n:");
      int desiredRank = getInput();
      if(desiredRank == 0){// let them leave if they can't afford any upgrades
         return false;
      }
      while(desiredRank < 2 || desiredRank > 6){// IF they don't enter a valid rank 
         System.out.print("Please enter a valid rank between 2 and 6\n:");
         desiredRank = getInput();
         if(desiredRank == 0){
            return false;   
         }
      }
      if(desiredRank <= player.getRank()){
         System.out.println("You cannot lower your rank, try a different rank.");
         raiseRank(player);   
      }
      
      System.out.println("How would you like to purchase this rank?");
      System.out.print("1. Dollars | 2. Credits\n:");
      int payment = getInput();
      while(payment < 1 || payment > 2){
         System.out.print("please enter a form of payment either 1 or 2\n:");
         payment = getInput();
      }
      
      if(payment == 1){
         if(player.getCash() >= prices[desiredRank-2][payment-1]){// Check to see if they have enough dollars to buy said upgrade 
            System.out.println("Successfully upgraded to rank " + desiredRank + "!");
            player.setCash(player.getCash() - prices[desiredRank-2][payment-1]);
            player.setRank(desiredRank);

         } else {
            System.out.println("Not enough cash for this upgrade");
         }
      } else if(payment == 2) {
         if(player.getCredits() >= prices[desiredRank-2][payment-1]){
            System.out.println("Successfully upgraded to rank " + desiredRank + "!");
            player.setCredits(player.getCredits() - prices[desiredRank-2][payment-1]);
            player.setRank(desiredRank);

         } else {
            System.out.println("Not enough credits for this upgrade");
         }
      }

      return true;
   }

   // A helper method that will get input from the user in the form of an integer, if 
   // the user enters anything but an integer they will be prompted to enter again. 
   public int getInput(){
      Scanner scan = new Scanner(System.in);
      
      try{// Try to get input from the user, if its not an int catch the error
         String input = scan.nextLine();
         int num = Integer.parseInt(input);
         return num;
      
      } catch (NumberFormatException e){
         System.out.print("Please enter a number...\n:");
         getInput();// If they didn't enter a number, ask them to try again.
      }
      return -1;
   }
   
   
   public Object[] getRoleObject(Player currentPlayer, Board board){
      String currLoc = currentPlayer.getLocation();
      List<String> choices = new ArrayList<String>();
      
      List<OffCardRole> offRoles = new ArrayList<OffCardRole>();// get all off card roles for the current set the player is on 
      offRoles = board.getOffCardRoles(currLoc);
      
      
      List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
      onRoles = board.getOnCardRoles(currLoc);
      
      Object [] options = new Object[offRoles.size() + onRoles.size()];
      int i;
      for(i = 0; i < offRoles.size(); i++){
         options[i] = (offRoles.get(i).getName() + " | Rank: " +  offRoles.get(i).getRank() + " (Off Card)");
      }
      for(int j = 0; j < onRoles.size(); j++){
         options[i] = (onRoles.get(j).getName() + " | Rank: " +  onRoles.get(j).getLevel() + " (On Card)");
         i++;
      }
      return options;
   }
   
   public boolean checkRoleRank(String s, Player currentPlayer, Board board){
      String currLoc = currentPlayer.getLocation();
      List<String> choices = new ArrayList<String>();
      
      List<OffCardRole> offRoles = new ArrayList<OffCardRole>();// get all off card roles for the current set the player is on 
      offRoles = board.getOffCardRoles(currLoc);
      
      List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
      onRoles = board.getOnCardRoles(currLoc);
      

      for(int i = 0; i < offRoles.size(); i++){
      
         System.out.println(offRoles.get(i).getName() + " " + i);
      
        if(s.contains(offRoles.get(i).getName())){
            if(currentPlayer.getRank() < offRoles.get(i).getRank()){
               return false;
            } else {
               System.out.println("taking off role");
               OffCardRole temp = offRoles.get(i);
               getOffRole(temp, currentPlayer, board);
               //takeOffCard(offRoles, currentPlayer, (i+1), board);
               return true;
            }
        }
      }
      for(int j = 0; j < onRoles.size(); j++){
      
         System.out.println(onRoles.get(j).getName());
      
         if(s.contains(onRoles.get(j).getName())){
            if(currentPlayer.getRank() < onRoles.get(j).getLevel()){
               return false;
            } else {
               System.out.println("taking on role");
               OnCardRole temp = onRoles.get(j);
               //takeOnCard(onRoles, currentPlayer, (j+1), board);
               getOnRole(temp, currentPlayer,board);
               return true;
            }
        }
      }
      return false;
   }
    
   // Presents the user with all off card roles and on card roles they can choose, then asking
   // them which they would like to try and take.
   public void takeRole(Board board, Player player){
      if(player.getLocation().equals("trailer") || player.getLocation().equals("office")){
         System.out.println("Must be on a set, not in the office / trailer to take a role...");
         return;
      }
      List<OffCardRole> offRoles = new ArrayList<OffCardRole>();// get all off card roles for the current set the player is on 
      offRoles = board.getOffCardRoles(player.getLocation());
      
      System.out.println("Off Card Roles:\n0. Exit Take a Role");
      for(int i = 0; i < offRoles.size(); i++){
         System.out.println((i+1) + ". " + offRoles.get(i).getName() + " | Line: '" + offRoles.get(i).getQuote() +  "' | Rank: " +  offRoles.get(i).getRank());
      }  

      System.out.println("On Card Roles:");
      List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
      onRoles = board.getOnCardRoles(player.getLocation());
      for(int i = offRoles.size(); i < onRoles.size() + offRoles.size(); i++){
         System.out.println((i+1) + ". " + onRoles.get(i -offRoles.size()).getName() +  " | Line: '" + onRoles.get(i-offRoles.size()).getLine() + "' | Rank: " + onRoles.get(i-offRoles.size()).getLevel());
      }
      
      System.out.print("Please enter the number of the role you would like to choose\n:");
      int input = getInput();
      while(input < 0 || input > (offRoles.size() + onRoles.size())){// if they enter the wrong number
         System.out.println("Please enter the number of the role  (from 0 to " + (offRoles.size() + onRoles.size()) + ")");
         input = getInput();
      }
      if(input == 0){// if they cannot afford any roles / change their mind, allow them to leave this action
         System.out.println("Exiting the take a role menu.");
         return;   
      }
      if(input <= offRoles.size()){// off card role 
         takeOffCard(offRoles, player, input, board);
      }else {// an on card role
         input -= offRoles.size();
         takeOnCard(onRoles, player, input, board);
      }
   }
 
   public void getOffRole(OffCardRole role, Player player, Board board){
      player.setRole(role.getName());
      player.setOffCRoleInfo(role);
      Set currentSet = board.getCurrentSet(player.getLocation());// get the set object the player is currently on 
      currentSet.roleIsTaken(player.getRole());// put the role that player has taken into the taken roles list for this set 
      
 
      role.setPlayer(player);
      
      System.out.println("Current role now: " + player.getRole());
      player.setHasRole(true);

   }
   
   
   public void getOnRole(OnCardRole role, Player player, Board board){
      player.setRole(role.getName());
      player.setOnCRoleInfo(role);
      Set currentSet = board.getCurrentSet(player.getLocation());// get the set object the player is currently on 
      Scene temp = currentSet.getScene();
      temp.roleIsTaken(player.getRole());// put the role that player has taken into the taken roles list for this set 
      
      
      role.setPlayer(player);
      
      System.out.println("Current role now: " + player.getRole());
      player.setHasRole(true);
      player.setOnCardRole(true);

   }
 
 
   public void takeOffCard(List<OffCardRole> offRoles, Player player, int input, Board board){
      
      System.out.println("input = " + input + " offRoles size = " + offRoles.size());
      
      for(int i = 0; i < offRoles.size(); i++){
         if(i == (input - 1)){
         
            System.out.println(offRoles.get(i).getName() + " " + i);
         
            if(player.getRank() < offRoles.get(i).getRank()){
               System.out.println("Not high enough to get this role. Try choosing a different role");
               //takeRole(board, player);
            } else {
               player.setRole(offRoles.get(i).getName());
               player.setOffCRoleInfo(offRoles.get(i));
               Set currentSet = board.getCurrentSet(player.getLocation());// get the set object the player is currently on 
               currentSet.roleIsTaken(player.getRole());// put the role that player has taken into the taken roles list for this set 
               
               System.out.println("getting here?");
               
               System.out.println("off roles.get(" + i + ") = " + offRoles.get(i).getName());
               offRoles.get(i).setPlayer(player);
               
               System.out.println("Current role now: " + player.getRole());
               player.setHasRole(true);

               break;
            }
         }
      }  
   }
   
   public void takeOnCard(List<OnCardRole> onRoles, Player player, int input, Board board){
      for(int i = 0; i < onRoles.size(); i++){
         if(i == (input - 1)){
            if(player.getRank() < onRoles.get(i).getLevel()){
               System.out.println("Not high enough to get this role. Try choosing a different role");
               takeRole(board, player);
            } else {
               player.setRole(onRoles.get(i).getName());
               player.setOnCRoleInfo(onRoles.get(i));
               Set currentSet = board.getCurrentSet(player.getLocation());// get the set object the player is currently on 
               Scene temp = currentSet.getScene();
               temp.roleIsTaken(player.getRole());// put the role that player has taken into the taken roles list for this set 
               onRoles.get(i).setPlayer(player);
               
               System.out.println("Current role now: " + player.getRole());
               player.setHasRole(true);
               player.setOnCardRole(true);

               break;
            }
         }
      }  
   }
 
 
   // A method that will "roll" one six sided die, and return an integer between 1 - 6
   public int rollDice(){
      int dieNum = 0;
      
      dieNum = (int)(Math.random()*6) + 1;
      
      return dieNum;
   }

   // Given the budget of a film, that amount of dice are rolled and distributed among the main actors
   // who completed the scene, according to the distribution rules. 
   public String payoutRoll(List<OnCardRole> onRoles, int budget){
      Integer[] dieRolls = new Integer[budget];// we roll budget amount of dice
      String ret = "";
      for(int i = 0; i < budget; i++){
         dieRolls[i] = rollDice();
      }
      int dieIndex = 0;
      Arrays.sort(dieRolls, Collections.reverseOrder());// sort the dice from largest to smallest 
      for(int i = 0; i < budget; i++){
         ret+=("Dice rolls = " + dieRolls[i] + "\n");
      }
      int max = 0; 
      int count = 0;
      int index = 0;
      List<OnCardRole> temp = new ArrayList<OnCardRole>();
      
      while(count < onRoles.size()){// order the roles from greatest to lowest ranks
             if(onRoles.get(count).getLevel() > max){
               max = onRoles.get(count).getLevel();
               index = count;
            }
            count++;
            if(count == onRoles.size()){
               temp.add(onRoles.remove(index));
               count = 0;
               max = 0;
               index = 0;
               if(temp.size() == onRoles.size()){
                  break;
               }
            }
      }
      while(dieIndex < budget){// until all of the dice have been distributed
         for(int i = 0; i < temp.size(); i++){// loop from highest ranked role to lowest ranked role 
            if(temp.get(i).getPlayer() != null){// if the current role has a player attached to it 
               temp.get(i).getPlayer().addMoney(dieRolls[dieIndex]);// pay them the number of that die
               System.out.println(temp.get(i).getPlayer().getName() + " gets $" + dieRolls[dieIndex] + " bonus money");
               ret += (temp.get(i).getPlayer().getName() + " gets $" + dieRolls[dieIndex] + " bonus money\n");
               dieIndex++;
               if(dieIndex == budget){// when we have distributed all of the dice, exit 
                  return ret;
               }
            } else {// if nobody is on this role, skip it (nobody gets that die)
               dieIndex++;
               if(dieIndex == budget){// when we have distributed all of the dice, exit 
                  return ret;
               }
            }
            
         }
      }
      return ret;
   }

}
