import java.util.*;
import java.io.*;
import java.awt.Image;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.*;
import javax.sound.sampled.*;
import java.net.*;
import javax.sound.sampled.*;


public class Visuals extends JFrame {
    private JFrame mainFrame;

    private Player currentPlayer;
    private Board board;

    private JTextArea playerStats;// the text area that displays the current player's stats
    private int currentDay;
    private int totalDay;
    private boolean endTurn;// keeps track if the user has hit the end turn button or not

    // JLayered Pane
    JLayeredPane bPane;

    private Deadwood game;

    // Buttons
    JButton move;
    JButton takeRole;
    JButton act;
    JButton rehearse;
    JButton upgrade;// only if they are in the casting office
    JButton endTurnButton;
    Interface in;

    // Player labels
    JLabel player1Label;
    JLabel player2Label;
    JLabel player3Label;
    JLabel player4Label;
    JLabel player5Label;
    JLabel player6Label;
    JLabel player7Label;
    JLabel player8Label;

    // Scene card labels
    JLabel set1Label;
    JLabel set2Label;
    JLabel set3Label;
    JLabel set4Label;
    JLabel set5Label;
    JLabel set6Label;
    JLabel set7Label;
    JLabel set8Label;
    JLabel set9Label;
    JLabel set10Label;
    
    // Shot marker labels
    JLabel ts1Label;
    JLabel ts2Label;
    JLabel ts3Label;
    JLabel j1Label;
    JLabel gs1Label;
    JLabel gs2Label;
    JLabel r1Label;
    JLabel r2Label;
    JLabel sh1Label;
    JLabel sh2Label;
    JLabel sh3Label;
    JLabel ms1Label;
    JLabel ms2Label;
    JLabel ms3Label;
    JLabel s1Label;
    JLabel s2Label;
    JLabel b1Label;
    JLabel c1Label;
    JLabel c2Label;
    JLabel h1Label;
    JLabel h2Label;
    JLabel h3Label;

    public JFrame getFrame() {
        return mainFrame;
    }

    public void setCurrPlayer(Player player) {
        currentPlayer = player;
        endTurn = false;
    }

    public boolean getHasEndedTurn() {
        return endTurn;
    }

    // Updates the important variables required to have the GUI display correct information on each turn
    public void updateBoardVar(Board newBoard, int currDay, int totalDays) {
        board = newBoard;
        currentDay = currDay;
        totalDay = totalDays;
    }

    // default constructor renders the GUI
    public Visuals(Deadwood game) {
        drawScreen();
        this.game = game;
    }

    // Main method for testing the GUI
    public static void main(String args[]) {
        Deadwood dw = new Deadwood();
        Visuals V = new Visuals(dw);

        V.drawScreen();
    }

    public void drawScreen() {
        mainFrame = new JFrame("Deadwood");// name of the main window frame

        in = new Interface();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// close when you hit the X button in the top right
        mainFrame.setSize(1350, 1000);// size of the game
        mainFrame.setResizable(false);// make it so you cant drag the window to resize it
        mainFrame.setLocationRelativeTo(null);// make it so the game starts dead center of your monitor

        JPanel layout = new JPanel(new BorderLayout());// make a new panel that will hold all of our other panels and bind it to the game window

        // Bottom Panel (Player Buttons)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Create all of the buttons we will need
        move = new JButton("Move");
        move.addMouseListener(new boardMouseListener());

        takeRole = new JButton("Take a Role");
        takeRole.addMouseListener(new boardMouseListener());

        act = new JButton("Act");
        act.addMouseListener(new boardMouseListener());

        rehearse = new JButton("Rehearse");
        rehearse.addMouseListener(new boardMouseListener());

        upgrade = new JButton("Upgrade Rank");// only if they are in the casting office
        upgrade.addMouseListener(new boardMouseListener());

        endTurnButton = new JButton("End Turn");
        endTurnButton.addMouseListener(new boardMouseListener());

        // Add all of the buttons to the bottom panel
        bottomPanel.add(move);
        bottomPanel.add(takeRole);
        bottomPanel.add(act);
        bottomPanel.add(rehearse);
        bottomPanel.add(upgrade);
        bottomPanel.add(endTurnButton);

        // Right Panel (Player Stats)
        JPanel rightPanel = new JPanel();
        playerStats = new JTextArea(20, 20);// displays the text for the current frame, can be updated with setText
        playerStats.setEditable(false);
        rightPanel.add(playerStats, BorderLayout.CENTER);


        mainFrame.getContentPane().add(bottomPanel, "South");
        mainFrame.add(layout, BorderLayout.PAGE_END);


        layout.add(bottomPanel, BorderLayout.PAGE_END);
        layout.setOpaque(false);

        // Create the JLayeredPane to hold the display, cards, and dice
        bPane = getLayeredPane();

        // Background Image
        JLabel label = new JLabel();
        label.setBounds(1, 1, 1200, 900);
        ImageIcon background = new ImageIcon("board.jpg");
        label.setIcon(background);

        // Layered Panel for board
        bPane = getLayeredPane();
        bPane.setPreferredSize(new Dimension(1200, 900));
        bPane.add(label, new Integer(0));


        // Initiate and set all set cards to invisible
        set1Label = new JLabel();
        set1Label.setVisible(false);
        set2Label = new JLabel();
        set2Label.setVisible(false);
        set3Label = new JLabel();
        set3Label.setVisible(false);
        set4Label = new JLabel();
        set4Label.setVisible(false);
        set5Label = new JLabel();
        set5Label.setVisible(false);
        set6Label = new JLabel();
        set6Label.setVisible(false);
        set7Label = new JLabel();
        set7Label.setVisible(false);
        set8Label = new JLabel();
        set8Label.setVisible(false);
        set9Label = new JLabel();
        set9Label.setVisible(false);
        set10Label = new JLabel();
        set10Label.setVisible(false);

        // Initiate and set all shot trackers
        ts1Label = new JLabel();
        ts1Label.setVisible(false);
        ts2Label = new JLabel();
        ts2Label.setVisible(false);
        ts3Label = new JLabel();
        ts3Label.setVisible(false);
        j1Label = new JLabel();
        j1Label.setVisible(false);
        gs1Label = new JLabel();
        gs1Label.setVisible(false);
        gs2Label = new JLabel();
        gs2Label.setVisible(false);
        r1Label = new JLabel();
        r1Label.setVisible(false);
        r2Label = new JLabel();
        r2Label.setVisible(false);
        sh1Label = new JLabel();
        sh1Label.setVisible(false);
        sh2Label = new JLabel();
        sh2Label.setVisible(false);
        sh3Label = new JLabel();
        sh3Label.setVisible(false);
        ms1Label = new JLabel();
        ms1Label.setVisible(false);
        ms2Label = new JLabel();
        ms2Label.setVisible(false);
        ms3Label = new JLabel();
        ms3Label.setVisible(false);
        s1Label = new JLabel();
        s1Label.setVisible(false);
        s2Label = new JLabel();
        s2Label.setVisible(false);
        b1Label = new JLabel();
        b1Label.setVisible(false);
        c1Label = new JLabel();
        c1Label.setVisible(false);
        c2Label = new JLabel();
        c2Label.setVisible(false);
        h1Label = new JLabel();
        h1Label.setVisible(false);
        h2Label = new JLabel();
        h2Label.setVisible(false);
        h3Label = new JLabel();
        h3Label.setVisible(false);

        // Add set cards to panel
        bPane.add(set1Label, new Integer(2));
        bPane.add(set2Label, new Integer(2));
        bPane.add(set3Label, new Integer(2));
        bPane.add(set4Label, new Integer(2));
        bPane.add(set5Label, new Integer(2));
        bPane.add(set6Label, new Integer(2));
        bPane.add(set7Label, new Integer(2));
        bPane.add(set8Label, new Integer(2));
        bPane.add(set9Label, new Integer(2));
        bPane.add(set10Label, new Integer(2));

        // Add shot markers to panel
        bPane.add(ts1Label, new Integer(2));
        bPane.add(ts2Label, new Integer(2));
        bPane.add(ts3Label, new Integer(2));
        bPane.add(j1Label, new Integer(2));
        bPane.add(gs1Label, new Integer(2));
        bPane.add(gs2Label, new Integer(2));
        bPane.add(r1Label, new Integer(2));
        bPane.add(r2Label, new Integer(2));
        bPane.add(sh1Label, new Integer(2));
        bPane.add(sh2Label, new Integer(2));
        bPane.add(sh3Label, new Integer(2));
        bPane.add(ms1Label, new Integer(2));
        bPane.add(ms2Label, new Integer(2));
        bPane.add(ms3Label, new Integer(2));
        bPane.add(s1Label, new Integer(2));
        bPane.add(s2Label, new Integer(2));
        bPane.add(b1Label, new Integer(2));
        bPane.add(c1Label, new Integer(2));
        bPane.add(c2Label, new Integer(2));
        bPane.add(h1Label, new Integer(2));
        bPane.add(h2Label, new Integer(2));
        bPane.add(h3Label, new Integer(2));

        // Initiate and set all dice to invisible
        player1Label = new JLabel();
        player1Label.setVisible(false);
        player2Label = new JLabel();
        player2Label.setVisible(false);
        player3Label = new JLabel();
        player3Label.setVisible(false);
        player4Label = new JLabel();
        player4Label.setVisible(false);
        player5Label = new JLabel();
        player5Label.setVisible(false);
        player6Label = new JLabel();
        player6Label.setVisible(false);
        player7Label = new JLabel();
        player7Label.setVisible(false);
        player8Label = new JLabel();
        player8Label.setVisible(false);

        // Add dice to panel
        bPane.add(player1Label, new Integer(3));
        bPane.add(player2Label, new Integer(3));
        bPane.add(player3Label, new Integer(3));
        bPane.add(player4Label, new Integer(3));
        bPane.add(player5Label, new Integer(3));
        bPane.add(player6Label, new Integer(3));
        bPane.add(player7Label, new Integer(3));
        bPane.add(player8Label, new Integer(3));

        mainFrame.getContentPane().add(bPane, "Center");
        mainFrame.getContentPane().add(bPane, BorderLayout.CENTER);
        mainFrame.add(rightPanel, BorderLayout.LINE_END);


        mainFrame.setVisible(true);
        mainFrame.pack();

        //bPane.add(mainFrame,new Integer(0));
    }

    // Listeners
    class boardMouseListener implements MouseListener {

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == move) {
                if (currentPlayer.hasMoved() == true) {
                    JOptionPane.showMessageDialog(mainFrame, "Cannot move again, already moved this turn.");
                } else if (currentPlayer.hasActed() == true) {
                    JOptionPane.showMessageDialog(mainFrame, "Cannot act and move in the same turn.");
                } else if (currentPlayer.hasRole() == true) {
                    JOptionPane.showMessageDialog(mainFrame, "Cannot move if you are acting in a role.");
                } else {
                    move();// call the move method on the current player
                }
                showStats();// update the stats portion of the game window
                updatePlayerIcon(currentPlayer);
                updateSceneCard(currentPlayer.getSet());
            } else if (e.getSource() == takeRole) {
                Set temp = board.getCurrentSet(currentPlayer.getLocation());
                if (temp.getName().equals("office") || temp.getName().equals("trailer")) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no roles to take in the office / trailer.");
                } else if (temp.getShots() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "This set is done filming for the day, come back tomorrow.");
                } else if (currentPlayer.hasRole() == true) {
                    JOptionPane.showMessageDialog(mainFrame, "Already acting in a role, cannot choose a new role.");
                } else {
                    takeRole();
                }
                showStats();// update the stats portion of the game window
                updatePlayerIcon(currentPlayer);
            } else if (e.getSource() == act) {
                Set temp = board.getCurrentSet(currentPlayer.getLocation());
                if (temp.getName().equals("office") || temp.getName().equals("trailer")) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no roles to take in the office / trailer.");
                } else if (currentPlayer.hasActed() || currentPlayer.hasRehearsed()) {// if they have already acted / rehearsed
                    JOptionPane.showMessageDialog(mainFrame, "You have already rehearsed/acted this turn!");
                } else if (currentPlayer.hasMoved()) { // if they have moved this turn they cannot act
                    JOptionPane.showMessageDialog(mainFrame, "Cannot move and act in the same turn.");
                } else if (temp.getShots() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "This set is done filming for the day, come back tomorrow.");
                } else if (currentPlayer.hasRole() && temp.getShots() != 0) {// else allow them to act if they are in a role
                    act();
                    updateShotMarker(currentPlayer.getSet());
                    if (board.checkSets()) {// if there is only one active set, time to end the day
                        JOptionPane.showMessageDialog(mainFrame, "Only one active set remains, the day is now ending.\nAll players have been returned to the trailer.");
                        currentDay++;
//                        updateSceneCard(currentPlayer.getSet());
                        currentPlayer.setLocation("trailer");
                        Deadwood.guiNextPlayer(game);
                        showStats();// update the stats portion of the game window
//                        updatePlayerIcon(currentPlayer);
//                        updateSceneCard(currentPlayer.getSet());
                        updateSceneCard();
                        updateShotMarker();
                    }
                    String end = game.guiEndDay(board, currentDay);
                    if (end.length() > 1) {
                        JOptionPane.showMessageDialog(mainFrame, end);
                        System.exit(0);
                    }
                } else {// else they are not in a role
                    JOptionPane.showMessageDialog(mainFrame, "You need to be in a role to act.");
                }
                showStats();// update the stats portion of the game window
                updatePlayerIcon(currentPlayer);
                updateSceneCard(currentPlayer.getSet());
                updateShotMarker(currentPlayer.getSet());
            } else if (e.getSource() == rehearse) {
                Set temp = board.getCurrentSet(currentPlayer.getLocation());
                if (currentPlayer.hasRehearsed() || currentPlayer.hasActed()) {
                    JOptionPane.showMessageDialog(mainFrame, "You have already rehearsed/acted this turn!");
                } else if (currentPlayer.hasMoved()) {// if they have moved this turn
                    JOptionPane.showMessageDialog(mainFrame, "Cannot move and rehearse in the same turn.");
                } else if (currentPlayer.hasRole()) {// if they have a role, allow them to rehearse
                    if (currentPlayer.getRehearsals() >= (temp.getScene().getBudget() - 1)) {// if they have enough rehearse tokens to guarantee a win, dont let them
                        JOptionPane.showMessageDialog(mainFrame, "Cannot rehearse anymore, guaranteed win.");
                    } else {// let them rehearse
                        JOptionPane.showMessageDialog(mainFrame, "Successfully rehearsed!");
                        in.rehearse(currentPlayer);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Must be in a role to rehearse.");
                }
                showStats();// update the stats portion of the game window
            } else if (e.getSource() == upgrade) {
                if (currentPlayer.getLocation().equals("office")) {
                    upgrade(in);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Must be in the Casting Office to upgrade your rank.");
                }
                showStats();// update the stats portion of the game window
                updatePlayerIcon(currentPlayer);
            } else if (e.getSource() == endTurnButton) {
                Deadwood.guiNextPlayer(game);
                showStats();// update the stats portion of the game window
                updatePlayerIcon(currentPlayer);
                if (currentPlayer.getSet() != null) {
                    updateSceneCard(currentPlayer.getSet());
                }
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    // Flipping card, ALL sets. (Ex: for end day.)
    public void updateSceneCard() {
        ImageIcon cardBackFull = new ImageIcon("images\\cards\\CardBack.jpg");
        Image cardSmall = cardBackFull.getImage().getScaledInstance(205, 115, java.awt.Image.SCALE_SMOOTH);
        ImageIcon cardBack = new ImageIcon(cardSmall);

        set1Label.setIcon(cardBack);
        set1Label.setVisible(true);
        set2Label.setIcon(cardBack);
        set2Label.setVisible(true);
        set3Label.setIcon(cardBack);
        set3Label.setVisible(true);
        set4Label.setIcon(cardBack);
        set4Label.setVisible(true);
        set5Label.setIcon(cardBack);
        set5Label.setVisible(true);
        set6Label.setIcon(cardBack);
        set6Label.setVisible(true);
        set7Label.setIcon(cardBack);
        set7Label.setVisible(true);
        set8Label.setIcon(cardBack);
        set8Label.setVisible(true);
        set9Label.setIcon(cardBack);
        set9Label.setVisible(true);
        set10Label.setIcon(cardBack);
        set10Label.setVisible(true);
    }

    // Flipping card, individual sets.
    public void updateSceneCard(Set set) {
        // Trailer and office don't have scenes so skip the updateSceneCard
        if (set.getName().equals("trailer") || set.getName().equals("office")) {
            return;
        }

        Scene scene = set.getScene();

        // Set up card back image
        ImageIcon cardBackFull = new ImageIcon("images\\cards\\CardBack.jpg");
        Image cardSmall = cardBackFull.getImage().getScaledInstance(205, 115, java.awt.Image.SCALE_SMOOTH);
        ImageIcon cardBack = new ImageIcon(cardSmall);

        switch (set.getName()) {
            case "Train Station":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set1Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set1Label.setIcon(set1Icon);

                    // Get the location
                    set1Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set1Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set1Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set1Label.setIcon(cardBack);

                    // Get the location
                    set1Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set1Label.setVisible(true);
                }
                break;

            case "Secret Hideout":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set2Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set2Label.setIcon(set2Icon);

                    // Get the location
                    set2Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set2Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set2Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set2Label.setIcon(cardBack);

                    // Get the location
                    set2Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set2Label.setVisible(true);
                }
                break;

            case "Church":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set3Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set3Label.setIcon(set3Icon);

                    // Get the location
                    set3Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set3Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set3Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set3Label.setIcon(cardBack);

                    // Get the location
                    set3Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set3Label.setVisible(true);
                }
                break;

            case "Hotel":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set4Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set4Label.setIcon(set4Icon);

                    // Get the location
                    set4Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set4Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set4Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set4Label.setIcon(cardBack);

                    // Get the location
                    set4Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set4Label.setVisible(true);
                }
                break;

            case "Main Street":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set5Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set5Label.setIcon(set5Icon);

                    // Get the location
                    set5Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set5Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set5Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set5Label.setIcon(cardBack);

                    // Get the location
                    set5Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set5Label.setVisible(true);
                }
                break;

            case "Jail":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set6Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set6Label.setIcon(set6Icon);

                    // Get the location
                    set6Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set6Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set6Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set6Label.setIcon(cardBack);

                    // Get the location
                    set6Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set6Label.setVisible(true);
                }
                break;

            case "General Store":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set7Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set7Label.setIcon(set7Icon);

                    // Get the location
                    set7Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set7Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set7Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set7Label.setIcon(cardBack);

                    // Get the location
                    set7Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set7Label.setVisible(true);
                }
                break;

            case "Ranch":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set8Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set8Label.setIcon(set8Icon);

                    // Get the location
                    set8Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set8Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set8Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set8Label.setIcon(cardBack);

                    // Get the location
                    set8Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set8Label.setVisible(true);
                }
                break;

            case "Bank":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set9Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set9Label.setIcon(set9Icon);

                    // Get the location
                    set9Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set9Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set9Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set9Label.setIcon(cardBack);

                    // Get the location
                    set9Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set9Label.setVisible(true);
                }
                break;

            case "Saloon":
                if (set.getShots() > 0 && set.isCardFlipped()) {
                    // FACE UP CARD

                    // Get the card image
                    ImageIcon set10Icon = new ImageIcon("images\\cards\\" + scene.getImg());
                    set10Label.setIcon(set10Icon);

                    // Get the location
                    set10Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set10Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // REMOVE THE CARD
                    set10Label.setVisible(false);
                } else {
                    // FACE DOWN CARD

                    // Get the card image
                    set10Label.setIcon(cardBack);

                    // Get the location
                    set10Label.setBounds(set.getArea()[0], set.getArea()[1], set.getArea()[3], set.getArea()[2]);

                    // Display the card image
                    set10Label.setVisible(true);
                }
                break;
        }

    }

    // Updating shot icons. Adding shot marker after every success.
    public void updateShotMarker(Set set) {
        // Set up card back image
        ImageIcon shotMarker = new ImageIcon("images\\shot.png");

        switch (set.getName()) {
            case "Train Station":
                if (set.getShots() == 2) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(2);

                    // Set the icon
                    ts1Label.setIcon(shotMarker);

                    // Set the location
                    ts1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ts1Label.setVisible(true);
                } else if (set.getShots() == 1) {
                    // Two shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    ts2Label.setIcon(shotMarker);

                    // Set the location
                    ts2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ts2Label.setVisible(true);

                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the third take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    ts3Label.setIcon(shotMarker);

                    // Set the location
                    ts3Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ts3Label.setVisible(true);
                } else {
                    // Zero shots completed
                    ts1Label.setVisible(false);
                    ts2Label.setVisible(false);
                    ts3Label.setVisible(false);
                }
                break;

            case "Secret Hideout":
                if (set.getShots() == 2) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(2);

                    // Set the icon
                    sh1Label.setIcon(shotMarker);

                    // Set the location
                    sh1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    sh1Label.setVisible(true);
                } else if (set.getShots() == 1) {
                    // Two shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    sh2Label.setIcon(shotMarker);

                    // Set the location
                    sh2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    sh2Label.setVisible(true);

                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the third take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    sh3Label.setIcon(shotMarker);

                    // Set the location
                    sh3Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    sh3Label.setVisible(true);
                } else {
                    // Zero shots completed
                    sh1Label.setVisible(false);
                    sh2Label.setVisible(false);
                    sh3Label.setVisible(false);
                }
                break;

            case "Church":
                if (set.getShots() == 1) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    c1Label.setIcon(shotMarker);

                    // Set the location
                    c1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    c1Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    c2Label.setIcon(shotMarker);

                    // Set the location
                    c2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    c2Label.setVisible(true);
                } else {
                    // Zero shots completed
                    c1Label.setVisible(false);
                    c2Label.setVisible(false);
                }
                break;

            case "Hotel":
                if (set.getShots() == 2) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(2);

                    // Set the icon
                    h1Label.setIcon(shotMarker);

                    // Set the location
                    h1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    h1Label.setVisible(true);
                } else if (set.getShots() == 1) {
                    // Two shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    h2Label.setIcon(shotMarker);

                    // Set the location
                    h2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    h2Label.setVisible(true);

                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the third take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    h3Label.setIcon(shotMarker);

                    // Set the location
                    h3Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    h3Label.setVisible(true);
                } else {
                    // Zero shots completed
                    h1Label.setVisible(false);
                    h2Label.setVisible(false);
                    h3Label.setVisible(false);
                }
                break;

            case "Main Street":
                if (set.getShots() == 2) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(2);

                    // Set the icon
                    ms1Label.setIcon(shotMarker);

                    // Set the location
                    ms1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ms1Label.setVisible(true);
                } else if (set.getShots() == 1) {
                    // Two shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    ms2Label.setIcon(shotMarker);

                    // Set the location
                    ms2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ms2Label.setVisible(true);

                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the third take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    ms3Label.setIcon(shotMarker);

                    // Set the location
                    ms3Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    ms3Label.setVisible(true);
                } else {
                    // Zero shots completed
                    ms1Label.setVisible(false);
                    ms2Label.setVisible(false);
                    ms3Label.setVisible(false);
                }
                break;

            case "Jail":
                if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the first and last take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    j1Label.setIcon(shotMarker);

                    // Set the location
                    j1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    j1Label.setVisible(true);
                } else {
                    // Zero shots completed
                    j1Label.setVisible(false);
                }
                break;

            case "General Store":
                if (set.getShots() == 1) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    gs1Label.setIcon(shotMarker);

                    // Set the location
                    gs1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    gs1Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    gs2Label.setIcon(shotMarker);

                    // Set the location
                    gs2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    gs2Label.setVisible(true);
                } else {
                    // Zero shots completed
                    gs1Label.setVisible(false);
                    gs2Label.setVisible(false);
                }
                break;

            case "Ranch":
                if (set.getShots() == 1) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    r1Label.setIcon(shotMarker);

                    // Set the location
                    r1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    r1Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    r2Label.setIcon(shotMarker);

                    // Set the location
                    r2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    r2Label.setVisible(true);
                } else {
                    // Zero shots completed
                    r1Label.setVisible(false);
                    r2Label.setVisible(false);
                }
                break;

            case "Bank":
                if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the first and last take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    b1Label.setIcon(shotMarker);

                    // Set the location
                    b1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    b1Label.setVisible(true);
                } else {
                    // Zero shots completed
                    b1Label.setVisible(false);
                }
                break;

            case "Saloon":
                if (set.getShots() == 1) {
                    // One shot completed

                    // Get the area of the first take:
                    int[] takeArea = set.getTakeArea().get(1);

                    // Set the icon
                    s1Label.setIcon(shotMarker);

                    // Set the location
                    s1Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    s1Label.setVisible(true);
                } else if (set.getShots() == 0) {
                    // All shots completed

                    // Get the area of the second take:
                    int[] takeArea = set.getTakeArea().get(0);

                    // Set the icon
                    s2Label.setIcon(shotMarker);

                    // Set the location
                    s2Label.setBounds(takeArea[1], takeArea[2], takeArea[3], takeArea[4]);

                    // Display the card image
                    s2Label.setVisible(true);
                } else {
                    // Zero shots completed
                    s1Label.setVisible(false);
                    s2Label.setVisible(false);
                }
                break;
        }
    }

   // Resetting shot icons after ending day.
    public void updateShotMarker() {
        ts1Label.setVisible(false);
        ts2Label.setVisible(false);
        ts3Label.setVisible(false);
        j1Label.setVisible(false);
        gs1Label.setVisible(false);
        gs2Label.setVisible(false);
        r1Label.setVisible(false);
        r2Label.setVisible(false);
        sh1Label.setVisible(false);
        sh2Label.setVisible(false);
        sh3Label.setVisible(false);
        ms1Label.setVisible(false);
        ms2Label.setVisible(false);
        ms3Label.setVisible(false);
        s1Label.setVisible(false);
        s2Label.setVisible(false);
        b1Label.setVisible(false);
        c1Label.setVisible(false);
        c2Label.setVisible(false);
        h1Label.setVisible(false);
        h2Label.setVisible(false);
        h3Label.setVisible(false);
    }

    // Updating player icon
    public void updatePlayerIcon(Player player) {
        String path = "";
        String location = "";

        // Height and width for the player icons
        int iconSize = 41;
        int thumbSize = 20;

        // Semi-hard-coded locations for the non-role locations.
        int offset = 0;
        int offsetY = 25;
        int xSetOffset = 0;
        int ySetOffset = 0;
        int trailerX = 1000;
        int trailerY = 350;
        int castingX = 15;
        int castingY = 465;
        int ranchX = 270;
        int ranchY = 630;
        int trainX = 10;
        int trainY = 190;
        int hideoutX = 270;
        int hideoutY = 825;
        int mainX = 890;
        int mainY = 165;
        int jailX = 290;
        int jailY = 180;
        int generalX = 300;
        int generalY = 410;
        int churchX = 610;
        int churchY = 860;
        int bankX = 615;
        int bankY = 620;
        int hotelX = 1010;
        int hotelY = 470;
        int saloonX = 615;
        int saloonY = 415;

        if (player.getSet() != null) {
            Set set = player.getSet();
            xSetOffset = set.getArea()[0];
            ySetOffset = set.getArea()[1];
        }

        switch (player.getPlayerNumber()) {
            case 1:
                path = "b" + player.getRank() + ".png";
                ImageIcon player1Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg = player1Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail = new ImageIcon(thumbImg);

                player1Label.setIcon(player1Icon);
                location = player.getLocation();

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player1Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player1Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player1Label.setBounds(trailerX, trailerY, iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player1Label.setBounds(castingX, castingY, iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(ranchX, ranchY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(trainX, trainY, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(hideoutX, hideoutY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(mainX, mainY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(jailX, jailY, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(generalX, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(churchX, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(bankX, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(hotelX, hotelY, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player1Label.setIcon(thumbnail);
                        player1Label.setBounds(saloonX, saloonY, thumbSize, thumbSize);
                    }
                }
                player1Label.setVisible(true);
                break;

            case 2:
                path = "c" + player.getRank() + ".png";
                ImageIcon player2Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg2 = player2Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail2 = new ImageIcon(thumbImg2);
                player2Label.setIcon(player2Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player2Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player2Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player2Label.setBounds((trailerX + 2 * offset), trailerY, iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player2Label.setBounds((castingX + 2 * offset), castingY, iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(ranchX + offset, ranchY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(trainX, trainY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(hideoutX + offset, hideoutY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(mainX + offset, mainY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(jailX + offset, jailY, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(generalX + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(churchX + offset, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(bankX + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(hotelX, hotelY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player2Label.setIcon(thumbnail2);
                        player2Label.setBounds(saloonX + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player2Label.setVisible(true);
                break;

            case 3:
                path = "g" + player.getRank() + ".png";
                ImageIcon player3Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg3 = player3Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail3 = new ImageIcon(thumbImg3);

                player3Label.setIcon(player3Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player3Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player3Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player3Label.setBounds((trailerX + 2 * offset), trailerY, iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player3Label.setBounds((castingX + 2 * offset), castingY, iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(ranchX + offset, ranchY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(trainX, trainY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(hideoutX + offset, hideoutY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(mainX + offset, mainY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(jailX + offset, jailY, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(generalX + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(churchX + offset, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(bankX + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(hotelX, hotelY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player3Label.setIcon(thumbnail3);
                        player3Label.setBounds(saloonX + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player3Label.setVisible(true);
                break;

            case 4:
                path = "o" + player.getRank() + ".png";
                ImageIcon player4Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg4 = player4Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail4 = new ImageIcon(thumbImg4);

                player4Label.setIcon(player4Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player4Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player4Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player4Label.setBounds((trailerX + 2 * offset), trailerY, iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player4Label.setBounds((castingX + 2 * offset), castingY, iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(ranchX + offset, ranchY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(trainX, trainY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(hideoutX + offset, hideoutY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(mainX + offset, mainY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(jailX + offset, jailY, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(generalX + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(churchX + offset, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(bankX + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(hotelX, hotelY + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player4Label.setIcon(thumbnail4);
                        player4Label.setBounds(saloonX + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player4Label.setVisible(true);
                break;

            case 5:
                path = "p" + player.getRank() + ".png";
                ImageIcon player5Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg5 = player5Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail5 = new ImageIcon(thumbImg5);

                player5Label.setIcon(player5Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player5Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player5Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player5Label.setBounds((trailerX + 2 * offset), (trailerY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player5Label.setBounds((castingX + 2 * offset), (castingY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(ranchX + offset, ranchY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(trainX, trainY + offset + 100, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(hideoutX + offset, hideoutY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(mainX + offset, mainY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(jailX + offset + 100, jailY + offsetY + 5, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(generalX + 100 + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(churchX + offset + 100, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(bankX + 100 + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(hotelX, hotelY + 100 + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player5Label.setIcon(thumbnail5);
                        player5Label.setBounds(saloonX + 100 + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player5Label.setVisible(true);
                break;

            case 6:
                path = "r" + player.getRank() + ".png";
                ImageIcon player6Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg6 = player6Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail6 = new ImageIcon(thumbImg6);

                player6Label.setIcon(player6Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player6Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player6Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player6Label.setBounds((trailerX + 2 * offset), (trailerY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player6Label.setBounds((castingX + 2 * offset), (castingY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(ranchX + offset, ranchY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(trainX, trainY + offset + 100, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(hideoutX + offset, hideoutY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(mainX + offset, mainY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(jailX + offset + 100, jailY + offsetY + 5, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(generalX + 100 + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(churchX + offset + 100, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(bankX + 100 + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(hotelX, hotelY + 100 + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player6Label.setIcon(thumbnail6);
                        player6Label.setBounds(saloonX + 100 + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player6Label.setVisible(true);
                break;

            case 7:
                path = "w" + player.getRank() + ".png";
                ImageIcon player7Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg7 = player7Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail7 = new ImageIcon(thumbImg7);
                player7Label.setIcon(player7Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player7Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player7Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player7Label.setBounds((trailerX + 2 * offset), (trailerY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player7Label.setBounds((castingX + 2 * offset), (castingY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(ranchX + offset, ranchY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(trainX, trainY + offset + 100, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(hideoutX + offset, hideoutY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(mainX + offset, mainY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(jailX + offset + 100, jailY + offsetY + 5, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(generalX + 100 + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(churchX + offset + 100, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(bankX + 100 + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(hotelX, hotelY + 100 + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player7Label.setIcon(thumbnail7);
                        player7Label.setBounds(saloonX + 100 + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player7Label.setVisible(true);
                break;

            case 8:
                path = "y" + player.getRank() + ".png";
                ImageIcon player8Icon = new ImageIcon("images\\dice\\" + path);

                // Generate thumbnail for on-set, off-role positions
                Image thumbImg8 = player8Icon.getImage().getScaledInstance(thumbSize, thumbSize, java.awt.Image.SCALE_SMOOTH);
                ImageIcon thumbnail8 = new ImageIcon(thumbImg8);

                player8Label.setIcon(player8Icon);
                location = player.getLocation();

                offset = (player.getPlayerNumber() - 1) % 4 * 25;

                if (player.hasRole()) {
                    if (player.getOnCardRole()) {
                        OnCardRole role = player.getOnCRoleInfo();
                        player8Label.setBounds(xSetOffset + role.getArea()[0], ySetOffset + role.getArea()[1], iconSize, iconSize);
                    } else {
                        OffCardRole role = player.getOffCRoleInfo();
                        player8Label.setBounds(role.getArea()[0] + 4, role.getArea()[1] + 4, iconSize, iconSize);
                    }
                } else {
                    if (location.equals("trailer")) {
                        player8Label.setBounds((trailerX + 2 * offset), (trailerY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("office")) {
                        player8Label.setBounds((castingX + 2 * offset), (castingY + 2 * offsetY), iconSize, iconSize);
                    } else if (location.equals("Ranch")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(ranchX + offset, ranchY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Train Station")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(trainX, trainY + offset + 100, thumbSize, thumbSize);
                    } else if (location.equals("Secret Hideout")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(hideoutX + offset, hideoutY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Main Street")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(mainX + offset, mainY + offsetY, thumbSize, thumbSize);
                    } else if (location.equals("Jail")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(jailX + offset + 100, jailY + offsetY + 5, thumbSize, thumbSize);
                    } else if (location.equals("General Store")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(generalX + 100 + offset, generalY, thumbSize, thumbSize);
                    } else if (location.equals("Church")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(churchX + offset + 100, churchY, thumbSize, thumbSize);
                    } else if (location.equals("Bank")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(bankX + 100 + offset, bankY, thumbSize, thumbSize);
                    } else if (location.equals("Hotel")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(hotelX, hotelY + 100 + offset, thumbSize, thumbSize);
                    } else if (location.equals("Saloon")) {
                        player8Label.setIcon(thumbnail8);
                        player8Label.setBounds(saloonX + 100 + offset, saloonY, thumbSize, thumbSize);
                    }
                }
                player8Label.setVisible(true);
                break;
        }
    }

    // Prompts the user to choose how many players there are
    public int getPlayerNum() {
        Object[] options = {"2", "3", "4", "5", "6", "7", "8"};
        int n = JOptionPane.showOptionDialog(mainFrame, "Welcome to DEADWOOD: The Cheapass Game of Acting Badly!\nFirst off, how many players are there?", "Intro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return (n + 2);
    }

    // Method that gets a player's name
    public String getPlayerName(int number) {
        String s = (String) JOptionPane.showInputDialog(mainFrame, "Player " + number + "'s name:", "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null, null);
        return s;
    }

    // method that updates the stats text area with the most up to date information
    public void showStats() {
        Interface in = new Interface();
        String text = in.showStats(currentPlayer, totalDay, currentDay);

        if (!currentPlayer.getRole().equals("None")) {
            Set set = board.getCurrentSet(currentPlayer.getLocation());// get the set the player is currently acting on
            Scene scene = set.getScene();

            text += ("---------------\nScene: \n" + scene.getName() + "\nBudget: " + scene.getBudget() + "\nShots left: " + set.getShots());
        }

        playerStats.setText(text);

        switch (currentPlayer.getPlayerNumber()) {
            case 1:
                playerStats.setForeground(Color.BLUE);
                playerStats.setBackground(Color.WHITE);
                break;

            case 2:
                playerStats.setForeground(Color.CYAN);
                playerStats.setBackground(Color.GRAY);
                break;

            case 3:
                playerStats.setForeground(Color.GREEN);
                playerStats.setBackground(Color.WHITE);
                break;

            case 4:
                playerStats.setForeground(Color.ORANGE);
                playerStats.setBackground(Color.WHITE);
                break;

            case 5:
                playerStats.setForeground(Color.MAGENTA);
                playerStats.setBackground(Color.WHITE);
                break;

            case 6:
                playerStats.setForeground(Color.RED);
                playerStats.setBackground(Color.WHITE);
                break;

            case 7:
                playerStats.setForeground(Color.BLACK);
                playerStats.setBackground(Color.WHITE);
                break;

            case 8:
                playerStats.setForeground(Color.YELLOW);
                playerStats.setBackground(Color.GRAY);
                break;
        }


    }

    // Method that allows the player to see all of their potential move locations, and allows them to choose one to move to
    public void move() {
        Interface in = new Interface();
        Object[] options = in.getMoveOptions(currentPlayer, board);

        int n = JOptionPane.showOptionDialog(mainFrame, "Where would you like to move to?", "Move",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        currentPlayer.setLocation(options[n].toString());// set their current location NOW to the new location
        currentPlayer.setMoved(true);

        String location = currentPlayer.getLocation();
        System.out.println("Current location now: " + location);
        currentPlayer.setSet(board.getCurrentSet(location));// Update player's set
        if (!currentPlayer.getLocation().equals("office") && !currentPlayer.getLocation().equals("trailer")) {
            board.checkFlipped(currentPlayer);// check if this location's set card has been flipped or not
            updateSceneCard(board.getCurrentSet(location));// update the scene card
        }
    }

    public void takeRole() {
        Interface in = new Interface();
        Object[] options = in.getRoleObject(currentPlayer, board);

        String s = (String) JOptionPane.showInputDialog(mainFrame, "Which role would you like to take?", "Take a Role",
                JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        System.out.println(s);
        if (in.checkRoleRank(s, currentPlayer, board) == false) {
            JOptionPane.showMessageDialog(mainFrame, "Not high enough rank to take this role.");
        }
    }

    public void act() {
        Interface in = new Interface();
        JOptionPane.showMessageDialog(mainFrame, in.act(board, currentPlayer));
    }

    public void upgrade(Interface in) {
        int[][] prices = {{4, 5}, {10, 10}, {18, 15}, {28, 20}, {40, 25}};
        Object[] options2 = {"Rank 2", "Rank 3", "Rank 4", "Rank 5", "Rank 6"};
        Object[] options = {"Cash", "Credits"};
        String msg = in.guiRaiseRank(currentPlayer);


        int n = JOptionPane.showOptionDialog(mainFrame, msg, "Upgrade",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == 0) {// cash
            String s = (String) JOptionPane.showInputDialog(mainFrame, msg, "Upgrade - Cash",
                    JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
            JOptionPane.showMessageDialog(mainFrame, in.checkCashRank(s, currentPlayer));
        } else { // credits
            String s = (String) JOptionPane.showInputDialog(mainFrame, msg, "Upgrade - Credits",
                    JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
            JOptionPane.showMessageDialog(mainFrame, in.checkCreditRank(s, currentPlayer));
        }

    }
}