// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.Observable;
import java.util.*;
import java.io.*;


// The player class is how a user interracts with the Deadwood game itself,
// taking their input to determine what each player wants to accomplish on their
// turn. Their input is taken to elsewhere (not sure yet) where it is parsed to determine
// if their action is acceptable.
public class Player extends Observable {

    private String name;
    private static int playerCount = 0; // for making visuals easier to handle
    private int playerNumber = 0;
    private int rank;// 1 - 6
    private String role;// the name of the role the player is currently acting in
    private Set set;// the set the player is currently on
    private OnCardRole onCRoleInfo; // the OnCardRole information if the player is currently acting in one
    private OffCardRole offCRoleInfo; // the OffCardRole information if the player is currently acting in one
    private String location;// the name of their current location
    private int credits;
    private int cash;
    private int score;
    private int rehearsals;// how many rehearsal tokens the player has (helps them roll to act)
    private boolean hasMoved;// an indicator for if the player has moved this turn or not
    private boolean hasActed;
    private boolean hasRehearsed;
    private boolean hasRole;
    private boolean onCardRole;

    // Main method for the purpose of testing each respective class function.
    public static void main(String args[]) {

    }

    // Constructor for the player class with only the name given
    Player(String name) {
        playerCount++;
        this.location = "trailer";// something like this
        this.playerNumber = playerCount;
        this.name = name;
        this.hasMoved = false;
        this.rank = 1;
        this.role = "None";
        this.onCRoleInfo = null;
        this.offCRoleInfo = null;
        this.onCardRole = false;
        this.hasActed = false;
        this.hasRole = false;
        this.hasMoved = false;
        this.score = 0;
        this.cash = 0;
        this.credits = 0;
    }


    // A method to return the current player's score, as detailed in the rules
    public void calcScore() {
        score += cash;// one point for every dollar
        score += credits;// one point for every credit
        score += (rank * 5);
    }

    public void addMoney(int cash) {
        this.cash += cash;
    }

    public void addCredit(int credits) {
        this.credits += credits;
    }


    // Getters-and-Setters
    public boolean hasRole() {
        return hasRole;
    }

    public void setHasRole(boolean b) {
        hasRole = b;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean b) {
        hasMoved = b;
    }

    public boolean hasActed() {
        return hasActed;
    }

    public void setActed(boolean b) {
        hasActed = b;
    }

    public boolean hasRehearsed() {
        return hasRehearsed;
    }

    public void setRehearsed(boolean b) {
        hasRehearsed = b;
    }

    public void setOnCardRole(boolean b) {
        onCardRole = b;
    }

    public boolean getOnCardRole() {
        return onCardRole;
    }

    public void setOnCRoleInfo(OnCardRole onCRoleInfo) {
        this.onCRoleInfo = onCRoleInfo;
    }

    public OnCardRole getOnCRoleInfo() {
        return onCRoleInfo;
    }

    public void setOffCRoleInfo(OffCardRole offCRoleInfo) {
        this.offCRoleInfo = offCRoleInfo;
    }

    public OffCardRole getOffCRoleInfo() {
        return offCRoleInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRehearsals() {
        return rehearsals;
    }

    public void setRehearsals(int rehearsals) {
        this.rehearsals = rehearsals;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

}// end of Player class
