
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alsetfuelcalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.opencsv.*; 
import java.util.Arrays;


/**
 *
 * @author Karishma Sukhwani
 */
public class AlsetFuelCalc extends Application {
    
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        
        BorderPane border = new BorderPane();   //will be attached to scene
        
        
        GridPane grid = new GridPane();     //for putting different nodes to grid, which whill later...
        grid.setAlignment(Pos.CENTER);      //...be put into center of border pane
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);
        border.setCenter(grid);         
        //grid.setBackground();
        /*
        FileReader filereader = new FileReader("resources/fuelPrice.csv");
        CSVReader csvReader = new CSVReader(filereader);
        String[] nextRecord; 
        ArrayList<String> fuelData = new ArrayList<>(); //for storing values of files
        while ((nextRecord = csvReader.readNext()) != null) { 
            fuelData.addAll(Arrays.asList(nextRecord)); 
            System.out.println("here"); 
        }
        final double petrolCost = Double.valueOf(fuelData.get(0));  //fuel price will be set once by a file...
        final double dieselCost = Double.valueOf(fuelData.get(1));  //...will be final and can't be changed inside code.
        */
        //temporary reading from text file
        String line;        //for storing data to be read by BufferedReader
        BufferedReader buffR = new BufferedReader(new
                                    FileReader("resources/fuelPrice.txt")); //will read data from this path
        ArrayList<String> fuelData = new ArrayList<>(); //for storing values of files
        while ((line = buffR.readLine()) != null) {     //read line of file until line becomes null(go line by line in to file)
                fuelData.add(line); //add read single value to arraylist of fuelData
        }
        final double petrolCost = Double.valueOf(fuelData.get(0));  //fuel price will be set once by a file...
        final double dieselCost = Double.valueOf(fuelData.get(1));  //...will be final and can't be changed inside code.
        
        Reflection reflection = new Reflection(); //Creating Reflection Effect on title
        reflection.setFraction(.6);
        reflection.setTopOffset(-10);
        Label alsetLabel = new Label();     //Title to be put in to of borderPane
        alsetLabel.setText("ALSET Fuel-Cost Calculator");
        alsetLabel.getStyleClass().add("headingLabel");
        alsetLabel.setEffect(reflection);   //Applying reflection effect on heading
        border.setTop(alsetLabel);
        BorderPane.setAlignment(alsetLabel, Pos.CENTER);
        
        Label distanceLabel = new Label();
        distanceLabel.setText("Trip Distance: ");
        GridPane.setConstraints(distanceLabel, 0, 0);
        
        TextField distanceTF = new TextField();
        GridPane.setConstraints(distanceTF, 0, 1);
        
        ChoiceBox<String> distanceUnit = new ChoiceBox<>();         //for dropdown box of distance units
        distanceUnit.getItems().addAll("Miles", "KiloMeters");
        distanceUnit.setValue("Miles");
        GridPane.setConstraints(distanceUnit, 1, 1);
        
        Label efficiencyLabel = new Label();
        efficiencyLabel.setText("Car's Efficiency: ");
        GridPane.setConstraints(efficiencyLabel, 0, 2);
        
        TextField efficiencyTF = new TextField();
        GridPane.setConstraints(efficiencyTF, 0, 3);
        
        ChoiceBox<String> mileageUnit = new ChoiceBox<>();          //for dropdown box of efficiency units
        mileageUnit.getItems().addAll("Miles Per Gallon", "KiloMeters Per Litre");
        mileageUnit.setValue("Miles Per Gallon");
        GridPane.setConstraints(mileageUnit, 1, 3);
        
        Label fuelLabel = new Label();
        fuelLabel.setText("Select Fuel: ");
        GridPane.setConstraints(fuelLabel, 0, 4);
        
        Label unitCostLabel = new Label();
        unitCostLabel.setText("Fuel Cost: ");
        GridPane.setConstraints(unitCostLabel, 0, 6);
        
        Label unitCostTF = new Label(petrolCost + " £/litre");   //for showing set price to customers
        GridPane.setConstraints(unitCostTF, 0, 7);
        
        HBox fuelRB = new HBox();       //for setting RadioButtons in horizontal layout
        fuelRB.setSpacing(5);
        GridPane.setConstraints(fuelRB, 0, 5);
        
        final ToggleGroup fuel = new ToggleGroup();         //for toggling through radioButton's belonging to one group
        RadioButton rb1Fuel = new RadioButton("Petrol");
        rb1Fuel.setToggleGroup(fuel);               //setting group to toogle through
        rb1Fuel.setSelected(true);
        fuelRB.getChildren().add(rb1Fuel);
        
        
        rb1Fuel.setOnAction((ActionEvent event) -> {
            unitCostTF.setText(petrolCost + " £/litre");    //changing value to show to customer if he/she changes fuel
        });
        
        RadioButton rb2Fuel = new RadioButton("Diesel");    //setting group to toogle through
        rb2Fuel.setToggleGroup(fuel);
        fuelRB.getChildren().add(rb2Fuel);
        
        rb2Fuel.setOnAction((ActionEvent event) -> {
            unitCostTF.setText(dieselCost + " £/litre");    //changing value to show to customer if he/she changes fuel
        });
        
        VBox bottomBP = new VBox();     //to put nodes in a vertical layout which will further...
        border.setBottom(bottomBP);     //...be set to buttom of borderPane 
        
        Label resultLabel = new Label("Result:");
        bottomBP.getChildren().add(resultLabel);
        
        TextArea resultArea = new TextArea();   //for showing results to users
        resultArea.setScaleX(1);
        resultArea.setScaleY(1);
        resultArea.setEditable(false);      //to disable user to edit this feild
        bottomBP.getChildren().add(resultArea);
        
        Button calc = new Button();         //Calculate button
        calc.setText("Calculate cost");
        GridPane.setConstraints(calc, 0, 8);
        
        Button resetB = new Button("Reset");    //Reset Button
        GridPane.setConstraints(resetB, 1, 8);
        
        
        Tooltip tt = new Tooltip(); //to give description of error
        
        //CSV file writing.....
        //File csvFile = new File();   //path of file
        FileWriter outputfile1 = new FileWriter("result.csv", false); //fileWriter to write file
        CSVWriter csvWriter1 = new CSVWriter(outputfile1); //for writing csv file

        String[] header = {"Trip Distance", "Efficiency", "Fuel", "Cost"};  //csvFile Header
        csvWriter1.writeNext(header);
        csvWriter1.flush();
        csvWriter1.close();
        calc.setOnAction((ActionEvent event) -> {   //event to happen by pressing calculate
            /*Here I'm changing values entered by user to one standard unit considered by me as miles,
            so even if user enteres other than miles and MPL it will be converted to miles and
            MPL. here I'm keeping MPL because fuel price is in £/litre, so that it will be
            easier for me to put logic to calculate actual cost.
            */ 
            try{
                double distance=0;
                try{
                    if (distanceTF.getText().equals(""))
                        throw new NullPointerException("Null Value Found!!!");   //Value not entered
                    
                    if (Double.valueOf(distanceTF.getText())<0)   //forcing to throw exception when negative value
                        throw new Exception();
                    
                    distanceTF.setId("");       //for removing error symboll
                    Tooltip.uninstall(distanceTF, tt);  //for removing tooltip
                    
                    
                    distance = Double.valueOf(distanceTF.getText()); //Consider Miles
                    if(distanceUnit.getValue().equals("KiloMeters")){
                        distance = distance*0.621; //converting to Miles
                    }
                }catch(NullPointerException n){
                    tt.setText("Enter a value.feild can't be null");
                    distanceTF.setId("textFieldErr");       //to show error effect and picture set by css file
                    Tooltip.install(distanceTF, tt);        //to show a tooltip for info about error
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                }catch(NumberFormatException n){
                    tt.setText("Enter a numeric value");
                    Tooltip.install(distanceTF, tt);
                    distanceTF.setId("textFieldErr");
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                    //efficiencyTF.setTooltip(tt1);
                }catch (Exception ex) {
                    if (Double.valueOf(distanceTF.getText())<0){  //double checking whether exception
                        tt.setText("Negative value not allowed!!!");    //is occured due to -ve value
                        Tooltip.install(distanceTF, tt);
                        distanceTF.setId("textFieldErr");
                    }
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                }
                
                double efficiency = 0;
                try{
                    efficiencyTF.setId("");     //for removing error symboll
                    Tooltip.uninstall(efficiencyTF, tt);  //for removing tooltip
                    if (efficiencyTF.getText().equals(""))
                        throw new NullPointerException("Null Value Found!!!");   //Value not entered
                    if (Double.valueOf(efficiencyTF.getText())<0)   //forcing to throw exception when negative value
                        throw new Exception();
                    efficiency = Double.valueOf(efficiencyTF.getText())/3.785; //Consider Miles per Litre
                    if(mileageUnit.getValue().equals("KiloMeters Per Litre")){
                        efficiency = Double.valueOf(efficiencyTF.getText())*0.621; //converting to MPL
                    }
                }catch(NumberFormatException n){
                    tt.setText("Enter a numeric value");
                    Tooltip.install(efficiencyTF, tt);
                    efficiencyTF.setId("textFieldErr");
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                }catch(NullPointerException nE){
                    tt.setText("Enter a value.feild can't be null");
                    efficiencyTF.setId("textFieldErr");
                    Tooltip.install(efficiencyTF, tt);
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                } catch (Exception ex) {
                    if (Double.valueOf(efficiencyTF.getText())<0){  //double checking whether exception
                        tt.setText("Negative value not allowed!!!");    //is occured due to -ve value
                        Tooltip.install(efficiencyTF, tt);
                        efficiencyTF.setId("textFieldErr");
                    }
                    resultArea.setText("Exception Ocuured!!!\nFor more info put mouse over error");
                    throw new Exception("for removing textArea");
                }
                double fuelCost;
                if ((((RadioButton)fuel.getSelectedToggle()).getText()).equals("Petrol"))   //for deciding cost acc. to fuel selected
                    fuelCost = petrolCost;
                else
                    fuelCost = dieselCost;
                 
                double totalCost;               //final cost storing variable
                totalCost = calcCost(distance, efficiency, fuelCost);
                resultArea.setText("Distance: " + distanceTF.getText()
                        + " " + distanceUnit.getValue() +"\nEfficiency: " + efficiencyTF.getText()
                        + " " + mileageUnit.getValue() + "\nFuel: " + (((RadioButton)fuel.getSelectedToggle()).getText())
                        + "\nTotal Cost: " + totalCost + " £");
                
                FileWriter outputfile2 = new FileWriter("result.csv", true); //fileWriter to write file
                
                try (CSVWriter csvWriter2 = new CSVWriter(outputfile2)) {
                    ArrayList<String[]> data = new ArrayList<>();
                    data.add(new String[] { distanceTF.getText() + " " + distanceUnit.getValue(),
                            efficiencyTF.getText() + " " + mileageUnit.getValue(),
                            (((RadioButton)fuel.getSelectedToggle()).getText()),
                            String.valueOf(totalCost) + " £"});
                    csvWriter2.writeAll(data);
                    csvWriter2.flush();
                    csvWriter2.close();
                }
                
            }catch(Exception ex){
                //System.out.print(ex);
            } 
        });
        
        resetB.setOnAction((ActionEvent event) -> {
            resultArea.setText("");
            distanceTF.setId("");
            efficiencyTF.setId("");
            distanceTF.setText("");
            efficiencyTF.setText("");
        });
        
        
        
        
        grid.getChildren().addAll(distanceLabel, distanceTF, efficiencyLabel,
                efficiencyTF, distanceUnit, unitCostLabel, unitCostTF,
                calc,resetB, mileageUnit, fuelRB);
        
        
        Scene scene = new Scene(border, 300, 350);
        
        scene.getStylesheets().add("file:///G:/kDoc/mdx/CCE1010_java/v2/alsetFuelCalc/src/alsetfuelcalc/myStyle.css");   //for adding css effects
        
        primaryStage.setTitle("ALSET FUEL COST CALCULATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static double calcCost(double distance, double efficiency, double fuelCost){
        double fuelUsed, totalCost;
        fuelUsed = distance/efficiency;
        totalCost = (fuelUsed*fuelCost);
        return totalCost;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}


