package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.Map.Entry;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n";
                                                /*"Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier";*/
    
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    private  String[] splitText = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }
    
    @FXML
    private void aantalAction(ActionEvent event) {
        Collection<String> unique = new HashSet<String>();
        for (String s : getSplitText()) {
            unique.add(s.toLowerCase());
        }
        taOutput.setText(String.format("Totaal aantal woorden: %d \nAantal verschillende woorden: %d",getSplitText().length,  unique.size()));
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        Collection<String> sorted = new TreeSet<String>();
        for (String s : getSplitText()) {
            sorted.add(s.toLowerCase());
        }
        String out = "";
        for (String s : sorted){
            out = s + "\n" + out;
        }
        taOutput.setText(out);
    }

    @FXML
    private void frequentieAction(ActionEvent event) {

        Map<String, Integer> map = new TreeMap<String, Integer>();
        for (String s : getSplitText()) {
            s = s.toLowerCase();
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        String out = "";
        for (Entry<String, Integer> e : sortByValue(map, true)){
            out = out + String.format("%-20s\t%d\n", e.getKey() + ":", e.getValue());
        }
        taOutput.setText(out);
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        TreeMap<String, LinkedList<Integer>> concordance = new TreeMap<String, LinkedList<Integer>>();
        String[] splitLines = taInput.getText().toLowerCase().split("\n");
        for (int i = 0; i < splitLines.length; i++) {
            //Original
            for (String s : splitLines[i].split("[^a-zA-Z]+")) {
            //Without words in that occur multiple times in one line
            //for (String s : new HashSet<String>(Arrays.asList(splitLines[i].split("[^a-zA-Z]+")))) {
                if (!concordance.containsKey(s) || concordance.get(s) == null) {
                    LinkedList ll = new LinkedList<Integer>();
                    ll.add(i + 1);
                    concordance.put(s, ll);
                } else {
                    LinkedList ll = concordance.get(s);
                    ll.add(i + 1);
                }
            }
        }
        String out = "";
        for (Entry<String, LinkedList<Integer>> e : concordance.entrySet()){
            out = out + String.format("%-20s\t%s\n", e.getKey() + ":", e.getValue().toString());
        }
        taOutput.setText(out);
    }
    private String[] getSplitText() {
        if (splitText == null) {
            splitText = taInput.getText().split("[^a-zA-Z]+");
        }
        return splitText;
    }
    private static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        return list;
    }

}



   

