// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;


public class Board {
    private List<Set> sets;// manages all of the sets parsed from the XML file for the board
    private List<Set> activeSets;// all of the sets (cards) that are active
    private List<Set> inactiveSets;// all of the sets (cards) that have been completed and are no longer available

    private List<Scene> activeCards;
    private List<Scene> discardPile;

    Board() {
        sets = new ArrayList<Set>();
        activeSets = new ArrayList<Set>();
        inactiveSets = new ArrayList<Set>();
        activeCards = new ArrayList<Scene>();
        discardPile = new ArrayList<Scene>();
    }

    // Main method for the purpose of testing each respective class function.
    public static void main(String args[]) {

    }

    // Method that adds one of the 50 cards to the active card pile
    public void addSceneCard(Scene scene) {
        activeCards.add(scene);
    }


    public List<Scene> drawSceneCards() {
        List<Scene> drawn = new ArrayList<Scene>();
        int count = 0;
        if (activeCards.size() == 0) {// this shouldnt ever happen, but in case it does
            System.out.println("Active card pile is empty");
            return drawn;
        }
        while (count < 10) {// randomly generate 10 numbers, pulling those indices from the pile of cards
            int randDraw = (int) (Math.random() * activeCards.size());
            //System.out.println(randDraw);
            drawn.add(activeCards.get(randDraw));// get that card
            activeCards.remove(randDraw);// remove that card

            count++;
        }
        return drawn;
    }


    // Simple method to add a set to our board
    public void addToBoard(Set set) {
        sets.add(set);
    }

    public List getAdjacent(String currLoc) {
        List<String> neighbors = new ArrayList<String>();

        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals(currLoc)) {
                neighbors = sets.get(i).getNeighbors();
            }

        }
        return neighbors;
    }

    public List<OffCardRole> getOffCardRoles(String currLoc) {
        List<OffCardRole> offRoles = new ArrayList<OffCardRole>();

        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals(currLoc)) {
                offRoles = sets.get(i).getAvailRoles();
            }

        }
        return offRoles;
    }

    // Method that find the set the current player is on, and then gets the scene card's available roles
    public List<OnCardRole> getOnCardRoles(String currLoc) {
        List<OnCardRole> onRoles = new ArrayList<OnCardRole>();
        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals(currLoc)) {
                Set temp = sets.get(i);
                onRoles = temp.getScene().getAvailRoles();
                break;
            }

        }
        return onRoles;
    }

    public Set getCurrentSet(String currLoc) {
        Set currSet = new Set();
        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals(currLoc)) {
                currSet = sets.get(i);
            }
        }
        return currSet;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void checkFlipped(Player player) {// see if the scene card on a set has been flipped (IE visited once this round)
        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals(player.getLocation())) {
                Set temp = sets.get(i);
                if (!temp.isCardFlipped()) {
                    System.out.println("You are the first to visit this set!\nThe scene being shot here is: ");
                    temp.getScene().printInfo();
                    temp.setCardFlipped(true);
                }
                break;
            }
        }
    }

    // Method to see how many active sets there are
    public boolean checkSets() {
        int activeSetCount = 0;
        for (int i = 0; i < sets.size() - 2; i++) {// minus 2 to ignore the office and trailer
            Set temp = sets.get(i);
            if (temp.getShots() != 0) {// IE if there are still shots left to shoot for a scene
                activeSetCount++;
            }
        }
        if (activeSetCount == 1) {// CHANGE THIS VALUE TO MAKE THE GAME SHORTER =========================================
            return true;// if true, its time to end the day
        }
        return false;// else keep going
    }

}
