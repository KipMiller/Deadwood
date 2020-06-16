// Authors: Erin Howard | Chris Miller 
// 
// CSCI 345 
// Assignment 2 
// Text-Based Deadwood

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class SetUp {
    private List<Scene> sceneCards;
    private List<Scene> discardPile;

    // Main method for the purpose of testing each respective class function.
    public static void main(String args[]) {

    }

    // Returns a document object after loading the file
    public static Document getDocFromFile(String fileName) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;
        try {
            doc = db.parse(fileName);
        } catch (Exception e) {
            System.out.println("XML parse failure");
            e.printStackTrace();
        }
        return doc;
    }

    // Gets the area from the board node
    public static int[] setupArea(Element e) {
        // Get the area info from the element
        NodeList area = e.getElementsByTagName("area");
        String x = (area.item(0)).getAttributes().getNamedItem("x").getNodeValue();// the x coordinate of the area
        String y = (area.item(0)).getAttributes().getNamedItem("y").getNodeValue();// the x coordinate of the area
        String h = (area.item(0)).getAttributes().getNamedItem("h").getNodeValue();// the x coordinate of the area
        String w = (area.item(0)).getAttributes().getNamedItem("w").getNodeValue();// the x coordinate of the area

        // Add the area to array and return
        if (e.getNodeName() == "take") {
            String shot = e.getAttributes().getNamedItem("number").getNodeValue();
            int[] areaArray = {Integer.parseInt(shot), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(h), Integer.parseInt(w)};

            return areaArray;
        } else {
            int[] areaArray = {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(h), Integer.parseInt(w)};

            return areaArray;
        }
    }

    public static List<String> setupNeighbors(Element e) {
        List<String> list = new ArrayList<String>();
        NodeList neighbors = e.getElementsByTagName("neighbors"); // Get neighbors from parent element
        NodeList neighbor = ((Element) neighbors.item(0)).getElementsByTagName("neighbor"); // Get neighbors from neighbor

        // Add each neighboring location to the new set
        for (int j = 0; j < neighbor.getLength(); j++) {
            String neighborName = ((Element) neighbor.item(j)).getAttribute("name");// the name of each neighbor of the set
            //System.out.println(j + " " + neighborName);

            list.add(neighborName);
        }
        return list;
    }

    // Method that open the cards xml file and creates scene objects with all pertinent information such as
    // card, scene and on-card part names, budgets, and tag lines.
    public static void setupCards(Board board) {
        try {
            Document d = getDocFromFile("cards.xml");
            Element root = d.getDocumentElement();

            // Get the specific child elements of the root and store them in NodeList
            NodeList cards = root.getElementsByTagName("card");

            // Pulling out the grandchild elements of Cards
            for (int i = 0; i < cards.getLength(); i++) {
                // Create a new card
                Element card = (Element) cards.item(i); // isolate an individual card
                String cardName = card.getAttributes().getNamedItem("name").getNodeValue();// the name of the card
                String cardImg = card.getAttributes().getNamedItem("img").getNodeValue();// the img of the card
                String cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();// the budget of the card
                Scene newScene = new Scene(cardName, cardImg, Integer.parseInt(cardBudget));// create a new scene
                //System.out.println(i + " " + cardName + " " + cardImg + " " + cardBudget);

                ///// SCENE /////
                NodeList scenes = card.getElementsByTagName("scene"); // Get scenes from card

                // Add a new scene for each card
                for (int j = 0; j < scenes.getLength(); j++) {
                    Element scene = (Element) scenes.item(j);
                    String sceneNumber = scene.getAttribute("number");// the number of each card
                    String sceneDescription = scene.getTextContent();// the description of the scene
                    sceneDescription = sceneDescription.trim();// remove the new lines and tabs
                    //System.out.println(j + " " + sceneNumber + " " + sceneDescription);

                    newScene.setSceneNumber(Integer.parseInt(sceneNumber));// add scene number to new scene
                    newScene.setSceneDescription(sceneDescription);// add scene description to new scene
                }

                ///// PARTS /////
                NodeList parts = card.getElementsByTagName("part"); // Get parts from card

                // Add a new part to each card
                for (int k = 0; k < parts.getLength(); k++) {
                    Element part = (Element) parts.item(k);
                    String partName = part.getAttribute("name");// the name of each part
                    String partLevel = part.getAttribute("level");// the level of each part

                    //Element line = part.items(0);
                    String partLine = part.getElementsByTagName("line").item(0).getTextContent();// the line for the part

                    //System.out.println(k + " " + partName + " " + partLevel + " " + partLine);

                    ///// AREA /////
                    int[] areaArray = setupArea(part); // Get the area from part and store it
                    //System.out.println("Set Area: x " + areaArray[0] + " y " + areaArray[1] + " h " + areaArray[2] + " w " + areaArray[3]);

                    ///// ON-CARD ROLE /////
                    OnCardRole newRole = new OnCardRole(partName, Integer.parseInt(partLevel), areaArray, partLine, cardName);// add new on-card role
                    newScene.addAvailRoles(newRole);// add the role to available roles for the scene
                }

                board.addSceneCard(newScene);// add each of the 50 cards to the active pile in the board object
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method that open the board xml file and creates set object with all pertinent information such as
    // neighbors, off card role names, and take counters. The list of active sets is filled with the new
    // set objects as they are created (for use in the deadwood system class).
    public static void setupLocations(Board board) {
        try {
            Document d = getDocFromFile("board.xml");
            Element root = d.getDocumentElement();

            ////////// SETS //////////
            NodeList sets = root.getElementsByTagName("set"); // Get sets from root

            // Pulling out the grandchild elements of Set
            for (int i = 0; i < sets.getLength(); i++) {

                // Create a new set
                Element set = (Element) sets.item(i); // isolate an individual set
                String setName = set.getAttributes().getNamedItem("name").getNodeValue();// the name of each set on the board
                //System.out.println("Set Name: " + setName);
                Set newSet = new Set(setName);

                ///// NEIGHBORS /////
                List<String> setNeighbors = setupNeighbors(set);// Get a list of neighbors

                // Add neighbors to trailer
                for (int j = 0; j < setNeighbors.size(); j++) {
                    newSet.addNeighbor(setNeighbors.get(j));
                }

                ///// AREA /////
                int[] areaArray = setupArea(set); // Get the area from set and store it
                newSet.setArea(areaArray); // Set the area for the set
                //System.out.println("Set Area: x " + areaArray[0] + " y " + areaArray[1] + " h " + areaArray[2] + " w " + areaArray[3]);

                ///// TAKES /////
                NodeList takes = set.getElementsByTagName("takes"); // Get takes from set
                NodeList take = ((Element) takes.item(0)).getElementsByTagName("take"); // Get take from takes
                List<int[]> takeArea = new ArrayList<int[]>();

                //System.out.println(take.getLength());
                int totalTakes = take.getLength();
                //System.out.println("Total takes: " + totalTakes);
                newSet.setTotalShots(totalTakes);// set the total number of shots per set
                for (int k = 0; k < take.getLength(); k++) {
                    int[] shotAreaArray = setupArea((Element) take.item(k));
                    takeArea.add(shotAreaArray);
                    //System.out.println("Take " + shotAreaArray[0] + " " + Arrays.toString(shotAreaArray));
                }

                // PARTS //
                NodeList parts = set.getElementsByTagName("parts");
                NodeList part = ((Element) parts.item(0)).getElementsByTagName("part");


                for (int j = 0; j < part.getLength(); j++) {// get all off card roles (parts) from the XML file
                    String partName = ((Element) part.item(j)).getAttribute("name");
                    int partRank = (Integer.parseInt(((Element) part.item(j)).getAttribute("level")));
                    String partLine = "";


                    NodeList children = part.item(j).getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node sub = children.item(k);
                        if (sub.getNodeName().equals("line")) {
                            partLine = sub.getTextContent();
                        }
                    }

                    ///// AREA /////
                    int[] roleAreaArray = setupArea((Element) part.item(j)); // Get the area from off-card part and store it

                    OffCardRole offRole = new OffCardRole(partName, partRank, partLine, roleAreaArray);
                    newSet.addAvailRoles(offRole);
                    //System.out.println("Name: " + partName + " | Rank: " + partRank + " | Line: " + partLine);
                }

                newSet.setTakeArea(takeArea);
                board.addToBoard(newSet);// add this set to our board object so we can maintain each location
            }

            ////////// TRAILER //////////
            Element trailer = (Element) root.getElementsByTagName("trailer").item(0);// Get trailer from root
            Set trail = new Set("trailer");// Create new set called trailer
            //System.out.println("Set Name: Trailer");

            ///// NEIGHBORS /////
            List<String> trailNeighbors = setupNeighbors(trailer);// Get a list of neighbors

            // Add neighbors to trailer
            for (int i = 0; i < trailNeighbors.size(); i++) {
                trail.addNeighbor(trailNeighbors.get(i));
            }
            board.addToBoard(trail); // Add trailer to board

            ///// AREA /////
            int[] areaArrayTrail = setupArea(trailer); // Get the area from trailer and store it
            //System.out.println("Set Area: x " + areaArrayTrail[0] + " y " + areaArrayTrail[1] + " h " + areaArrayTrail[2] + " w " + areaArrayTrail[3]);
            trail.setArea(areaArrayTrail); // Set the area for the trailer

            ////////// OFFICE //////////
            Element office = (Element) root.getElementsByTagName("office").item(0);// Get office from root
            Set off = new Set("office");// Create new set called office
            //System.out.println("Set Name: Office");

            ///// NEIGHBORS /////
            List<String> offNeighbors = setupNeighbors(office);// Get a list of neighbors

            // Add neighbors to trailer
            for (int i = 0; i < offNeighbors.size(); i++) {
                off.addNeighbor(offNeighbors.get(i));
            }
            board.addToBoard(off);

            ///// AREA /////
            int[] areaArrayOff = setupArea(office); // Get the area from office and store it
            //System.out.println("Set Area: x " + areaArrayOff[0] + " y " + areaArrayOff[1] + " h " + areaArrayOff[2] + " w " + areaArrayOff[3]);
            off.setArea(areaArrayOff); // Set the area for the office

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // Method for ending a day of the game, all players are returned to the trailer,
    // the last scene is NOT finished, ten new scene cards are dealt face down, and all of the shot counters are replaced.
    public void endDay(List<Player> players, Board board, Visuals gui) {
        System.out.println("###################################################");
        System.out.println("Only one active set remains, the day is ending now.");
        System.out.println("###################################################");
        for (int i = 0; i < players.size(); i++) {// Set all player locations back to the trailer.
            Player temp = players.get(i);
            temp.setActed(false);
            temp.setMoved(false);
            temp.setRehearsed(false);
            temp.setHasRole(false);
            temp.setOnCardRole(false);
            temp.setRole("None");
            temp.setLocation("trailer");
            gui.updatePlayerIcon(temp);
        }

        // take last scene and add to discard pile
        setScenesAndCounters(board);
    }

    public void setScenesAndCounters(Board board) {
        List<Scene> newScenes = board.drawSceneCards();// draw ten new scene cards
        List<Set> sets = board.getSets();// get all 10 sets from the board

        for (int i = 0; i < sets.size() - 2; i++) {// minus 2 to skip the office and the trailer
            Scene scene = newScenes.remove(0);// pull out the top scene on the new pile of scenes
            Set set = sets.get(i);
            sets.get(i).setScene(scene);// assign that scene to the current set
            sets.get(i).resetShotCounter();// replace all of the shot counters
            sets.get(i).setCardFlipped(false);
            sets.get(i).resetRoles();
        }
    }


    // Method that takes in the list of all players and calculates each of their scores, finding the highest
    // among all the players and declaring said player the winner of Deadwood.
    public String endGame(List<Player> players) {
        // calculate everyones scores and determine the winner of the game
        int highest = 0;
        String name = "";
        String ret = "";

        for (int i = 0; i < players.size(); i++) {// TODO: Might have to prepare for a tie in highest score ?
            Player temp = players.get(i);
            String tName = temp.getName();
            temp.calcScore();
            int tScore = temp.getScore();

            System.out.println(tName + "'s score: " + tScore);
            ret += (tName + "'s score: " + tScore + "\n");
            if (tScore > highest) {
                highest = tScore;
                name = tName;
            }
        }
        System.out.println("*****************************\nCongratulations! " + name + " is the winner of Deadwood!");
        ret += ("*****************************\nCongratulations! " + name + " is the winner of Deadwood!");
        return ret;
    }


}
