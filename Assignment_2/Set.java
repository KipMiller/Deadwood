// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;

public class Set {
    private String name;
    private List<String> neighbors;
    private List<Player> players;
    private List<OffCardRole> availableRoles;
    private List<OffCardRole> takenRoles;
    private List<String> setNeighbors;
    private int shotsLeft;// how many shots are left to complete filming the scene on the set
    private int totalShots;// how many total shots there are for a set
    private int[] area;
    private List<int[]> takeArea;
    private Scene scene; // the scene that is occuring on this set, when shots left for this set = 0, that scene is done
    private boolean cardFlipped;// represents if anyone has been on this tile yet (card will be flipped if someone has visited it before)

    // Main method for the purpose of testing each respective class function.
    public static void main(String args[]) {

    }

    Set() {
    }

    Set(String name) {
        this.setNeighbors = new ArrayList<String>();
        this.availableRoles = new ArrayList<OffCardRole>();
        this.players = new ArrayList<Player>();
        this.takenRoles = new ArrayList<OffCardRole>();
        this.takeArea = new ArrayList<int[]>();
        this.name = name;
        cardFlipped = false;
    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isCardFlipped() {
        return cardFlipped;
    }

    public void setCardFlipped(boolean b) {
        cardFlipped = b;
    }

    public void addNeighbor(String neighbor) {
        setNeighbors.add(neighbor);
    }

    public List<String> getNeighbors() {
        return setNeighbors;
    }

    public void removeShot() {
        if (this.shotsLeft != 0) {
            this.shotsLeft--;
        }
    }

    public boolean endScene(Board board, Player player) {
        Set set = board.getCurrentSet(player.getLocation());

        Scene scene = set.getScene();
        List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
        //onRoles = board.getOnCardRoles(player.getLocation());
        onRoles = scene.getTakenRoles();
     
        int onCardCount = 0;

        for (int i = 0; i < onRoles.size(); i++) {
            if (onRoles.get(i).getPlayer() != null) {
                onCardCount++;
                System.out.println(onRoles.get(i).getPlayer().getName());
                Player temp = onRoles.get(i).getPlayer();
                temp.setHasRole(false);// take away their role
                temp.setOnCardRole(false);
                temp.setRole("None");
                temp.setOnCRoleInfo(null);
                temp.setOffCRoleInfo(null);
                temp.setRehearsals(0);// reset their rehearsal tokens
            }
        }


        List<OffCardRole> offRoles = new ArrayList<OffCardRole>();// get all off card roles for the current set the player is on
        //offRoles = board.getOffCardRoles(player.getLocation());
        offRoles = takenRoles; 

        for (int i = 0; i < offRoles.size(); i++) {
            System.out.println(offRoles.get(i).getName());
            if (offRoles.get(i).getPlayer() != null) {
                System.out.println(offRoles.get(i).getPlayer().getName());
                Player temp = offRoles.get(i).getPlayer();
                temp.setHasRole(false);// take away their role
                temp.setOnCardRole(false);
                temp.setRole("None");
                temp.setOnCRoleInfo(null);
                temp.setOffCRoleInfo(null);
                temp.setRehearsals(0);// reset their rehearsal tokens
            }
        }


        if (onCardCount == 0) {// if there are no on-card actors
            System.out.println("No actors on the card when scene finished, there is no bonus payout.");
            return false;
        }

        for (int i = 0; i < offRoles.size(); i++) {// pay all the extras a bonus
            if (offRoles.get(i).getPlayer() != null) {
                System.out.println(offRoles.get(i).getPlayer().getName() + " has earned $" + offRoles.get(i).getRank() + " for being an extra when the scene wrapped");
                offRoles.get(i).getPlayer().addMoney(offRoles.get(i).getRank());// if there was at least 1 actor on the card when the scene finished, extras earn a bonus = to the role's rank
            }
        }

        return true;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots = totalShots;
    }

    public void resetShotCounter() {// reset the shot counter from zero back to its max
        shotsLeft = totalShots;
    }

    public void addAvailRoles(OffCardRole role) {
        availableRoles.add(role);
    }

    public List<OffCardRole> getAvailRoles() {
        return availableRoles;
    }

    public void roleIsTaken(String title) {
        for (int i = 0; i < availableRoles.size(); i++) {
            if (availableRoles.get(i).getName().equals(title)) {
                OffCardRole taken = availableRoles.remove(i);// pull that role from the available roles
                takenRoles.add(taken);// and put it into the taken roles list
            }
        }
    }

    // take all of the roles from the taken pile and put them back into the available list
    // Will be called after a new card has been placed onto the set at the end of a day
    public void resetRoles() {
        for (int i = takenRoles.size() - 1; i >= 0; i--) {
            OffCardRole reset = takenRoles.remove(i);
            availableRoles.add(reset);
        }
    }

    public void addOffCardActor(Player player) {// add the player who has taken a role to the current list of off-card actors
        players.add(player);
    }

    public List<Player> getOffCardActors() {// get all off card actors on this set
        return players;
    }

    // Getters-and-Setters
    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setArea(int[] area) {
        this.area = area;
    }

    public void setTakeArea(List<int[]> takeArea) {
        this.takeArea = takeArea;
    }

    public List<int[]> getTakeArea() {
        return takeArea;
    }

    public int getShots() {
        return shotsLeft;
    }

    public void setShots(int shotsLeft) {
        this.shotsLeft = shotsLeft;
    }

    public int[] getArea() {
        return area;
    }

}
