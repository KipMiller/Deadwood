// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;


public class Deadwood {
    private int totalDays;// how many days until the game is over
    private int playerTotal; // total number of players in the game
    private int currPlayNum;// the number of which player we are currently on (for deciding who is next)
    private int currentDay;// the current day, so other methods can see whether or not the game is nearly done

    private Visuals gui;
    private SetUp setup;
    private List<Player> playerOrder;
    private Player currentPlayer;

    // Main method for running the actual game of Deadwood from start to finish.
    public static void main(String args[]) {
        //System.out.print("Welcome to DEADWOOD: The Cheapass Game of Acting Badly!\nFirst off, how many players are there? (2 - 8 possible) \n:");
        Deadwood system = new Deadwood();
        system.gui = new Visuals(system);// The visual aspect of the game (BOARD, BUTTONS, ETC)
        Board board = new Board();// create a board object that will be storing all of the sets
        system.setup = new SetUp();// setup the board with everything it needs

        system.setPlayerNum(system.gui);// get the number of total players

        system.playerOrder = new ArrayList<Player>();// the order the players will go in
        system.setPlayers(system.gui);// get the player's names

        SetUp.setupLocations(board);// pass the setup locations
        SetUp.setupCards(board);
        system.setup.setScenesAndCounters(board);
        system.placeCardsAndShots(system.gui, board);

        system.currentDay = 1;// the game starts at day 1
        system.currPlayNum = 0;// starting at player 0 (the 1st player in order)


        system.gui.setCurrPlayer(system.currentPlayer);
        system.gui.updateBoardVar(board, system.currentDay, system.totalDays);
        system.gui.showStats();// update the stats portion of the game window
      
    }// end of the main method

    public String guiEndDay(Board board, int currentDay) {


        String ret = "";
        if (currentDay <= totalDays) {

            if (board.checkSets()) {// if there is only one active set, time to end the day
                setup.endDay(playerOrder, board, gui);

            }

        } else {
            System.out.println("Ending the game");
            ret += setup.endGame(playerOrder);
        }

        return ret;
    }


    // Present the active player with all of the actions they can perform on their turn
    public void getPlayerAction(Board board) {
        Scanner scan = new Scanner(System.in);

        System.out.println("It is currently " + currentPlayer.getName() + "'s turn.");
        System.out.println("What would you like to do?");
        System.out.println("1. Move");
        System.out.println("2. Take a Role");
        System.out.println("3. Act");
        System.out.println("4. Rehearse");
        System.out.println("5. Upgrade Rank");
        System.out.println("6. Check Stats");
        System.out.println("7. End Turn");
        System.out.print("Enter the number of which action you would like to perform\n:");

        try {// Try to get input from the user, if its not an int catch the error
            String input = scan.nextLine();
            int action = Integer.parseInt(input);

            while (action < 1 || action > 7) {// keep looping until they enter a correct number of action
                System.out.println(" * Please enter an action from 1 - 7 *");
                input = scan.nextLine();
                action = Integer.parseInt(input);
            }
            performAction(action, currentPlayer, board);// pass their action off to the method that will perform it if available

        } catch (NumberFormatException e) {
            System.out.print(" * Please enter a number... *\n:");
            getPlayerAction(board);// If they didn't enter a number, ask them to try again.
        }

    }

    // Method that takes in the action the current player wishes to perform, and will determine if they
    // are able to do so, if true the appropriate interface will be called on the player object.
    public void performAction(int action, Player currentPlayer, Board board) {
        Interface in = new Interface();

        if (action == 1) {// move -------------------------------
            if (currentPlayer.hasMoved() == true) {
                System.out.println(" * Cannot move again, have already moved this turn... *");
            } else if (currentPlayer.hasActed() == true) {
                System.out.println(" * Cannot act and move one the same turn... *");
            } else if (currentPlayer.hasRole() == true) {
                System.out.println(" * Cannot move if you are acting in a role... *");
            } else {// attempt to move--------------------------
                in.map();// show the player the map
                in.move(currentPlayer, board);// get their input for where they want to move and update accordingly
            }
        } else if (action == 2) {// take role ------------------
            Set temp = board.getCurrentSet(currentPlayer.getLocation());
            if (temp.getName().equals("office") || temp.getName().equals("trailer")) {
                System.out.println(" * There are no roles to take in the office / trailer... *");
            } else if (temp.getShots() == 0) {
                System.out.println(" * This set is done filming for the day, come back tomorrow... * ");
            } else if (currentPlayer.hasRole() == true) {
                System.out.println(" * Already acting in a role, cannot choose a new role... * ");
            } else {
                in.takeRole(board, currentPlayer);
            }
        } else if (action == 3) {// act -----------------------
            if (currentPlayer.hasActed() || currentPlayer.hasRehearsed()) {// if they have already acted / rehearsed
                System.out.println(" * Already have acted this turn! * ");
            } else if (currentPlayer.hasMoved()) { // if they have moved this turn they cannot act
                System.out.println(" * Cannot move and act in the same turn. *");
            } else if (currentPlayer.hasRole()) {// else allow them to act if they are in a role
                in.act(board, currentPlayer);
            } else {// else they are not in a role
                System.out.println(" * You need to be in a role to act. *");
            }
        } else if (action == 4) {// rehearse ---------------------
            Set temp = board.getCurrentSet(currentPlayer.getLocation());
            if (currentPlayer.hasRehearsed() || currentPlayer.hasActed()) {
                System.out.println(" * Already have rehearsed this turn! *");
            } else if (currentPlayer.hasMoved()) {// if they have moved this turn
                System.out.println(" * Cannot move and rehearse in the same turn *");
            } else if (currentPlayer.hasRole()) {// if they have a role, allow them to rehearse
                if (currentPlayer.getRehearsals() >= temp.getScene().getBudget()) {// if they have enough rehearse tokens to guarantee a win, dont let them
                    System.out.println(" * Cannot rehearse anymore, guaranteed win... *");
                } else {// let them rehearse
                    in.rehearse(currentPlayer);
                }
            } else {
                System.out.println(" * Must be in a role to rehearse. *");
            }
        } else if (action == 5) {// upgrade rank -----------------
            if (currentPlayer.getLocation().equals("office")) {
                in.raiseRank(currentPlayer);
            } else {
                System.out.println(" * Must be in the Casting Office to upgrade your rank... *");
            }
        } else if (action == 6) {// check stats---------------------
            in.checkStats(playerOrder, currentPlayer, totalDays, currentDay);
        } else if (action == 7) {// end turn ---------------------
            nextPlayer();// go to the next player in line
        }
    }

    // method that increments the current player number, restting it to 0 if we have
    // reached the end of the player order, and sets the active player to the player
    // located next in the order list.
    public void nextPlayer() {
        // Reset their flags for their next turn
        currentPlayer.setMoved(false);
        currentPlayer.setActed(false);
        currentPlayer.setRehearsed(false);

        currPlayNum++;
        if (currPlayNum == (playerTotal)) {// if we have reached the end of the player list, loop back to the start
            currPlayNum = 0;
        }
        currentPlayer = playerOrder.get(currPlayNum);// set the current player to the next in line
    }


    public static void guiNextPlayer(Deadwood game) {
        game.nextPlayer();
        game.gui.setCurrPlayer(game.currentPlayer);
    }

    // Method that asks the user how many players will be participating in the game,
    // and sets the total number of days accordingly.
    public void setPlayerNum(Visuals gui) {

        int players = gui.getPlayerNum();


        playerTotal = players;// if what they entered was a valid number, set the game's total players.

        if (players > 1 && players < 4) {// 2 - 3 players
            totalDays = 3;
        } else if (players >= 4) {// 4 +
            totalDays = 4;
        }

        System.out.println("Excellent! You will have " + totalDays + " days to earn as many points as you can!");
    }

    // A method that will ask for the names of each player in the order they would like to
    // play, adding them to the player List in said order.
    public void setPlayers(Visuals gui) {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < playerTotal; i++) {// Get every player's name in the order they will be playing in

            String playerName = gui.getPlayerName(i + 1);// pull up the dialog for player names
            Player playeri = new Player(playerName);

            if (playerTotal == 5) {// 5 players
                playeri.addCredit(2);
            } else if (playerTotal == 6) {// 6 players
                playeri.addCredit(4);
            } else if (playerTotal >= 7 && playerTotal <= 8) { // 7 or 8
                playeri.setRank(2);
            }

            playerOrder.add(playeri);
            gui.updatePlayerIcon(playeri);
        }
        // Display the order of players (to make sure it is correct)
        System.out.println("Perfect! Now we can get started with this order:");
        for (Player p : playerOrder) {// for each player in the list, show their name
            System.out.print(p.getName() + " ");
        }
        System.out.println();
        currentPlayer = playerOrder.get(0);
    }

    public void placeCardsAndShots(Visuals gui, Board board) {
        List<Set> sets = board.getSets();// get all 10 sets from the board

        for (int i = 0; i < sets.size() - 2; i++) {// minus 3 to skip the office and the trailer
            gui.updateSceneCard(sets.get(i));
            gui.updateShotMarker(sets.get(i));
        }
    }

}
