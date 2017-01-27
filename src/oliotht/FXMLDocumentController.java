package oliotht;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import java.lang.Math;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class FXMLDocumentController implements Initializable
{ 
    // Initialize all the needed lists, maps, sets and objects.
    Storage storage = new Storage();
    DataBuilder builder = new DataBuilder();
    Headquarters HQ = new Headquarters();
    ArrayList<Item> premadeItems = new ArrayList();
    
    // Initialize counters for logistics
    int packageCount = storage.packageCount();
    int packagesSent = 0;
    int packagesBroke = 0;
    int smartPostCount = HQ.parsedLocationCount();
    int smartPostsAdded = HQ.addedLocationCount();
    int itemCount = 0;
    int failedPacks = 0;
    int gamePoints = 0;
    
    @FXML
    private WebView webmap;
    @FXML
    private Button createButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button openCreateButton;
    @FXML
    private ComboBox<SmartPost> smartPostsCombo;
    @FXML
    private TextArea messages;
    @FXML
    private ComboBox<Package> packagesCombo;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Item> itemsCombo;
    @FXML
    private Button packageButton;
    @FXML
    private ComboBox<SmartPost> senderCombo;
    @FXML
    private ComboBox<SmartPost> recieverCombo;
    @FXML
    private TextField nameField;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField weightField;
    @FXML
    private RadioButton FirstClassRadio;
    @FXML
    private ToggleGroup classGroup;
    @FXML
    private RadioButton SecondClassRadio;
    @FXML
    private RadioButton ThirdClassRadio;
    @FXML
    private Button sendButton;
    @FXML
    private TabPane tabMenu;
    @FXML
    private Tab mainTab;
    @FXML
    private Tab packageTab;
    @FXML
    private Tab logstatTab;
    @FXML
    private TextArea packagingMessages;
    @FXML
    private CheckBox fragileCheck;
    @FXML
    private ComboBox<String> smartPostCityCombo;
    @FXML
    private ComboBox<String> senderCityCombo;
    @FXML
    private ComboBox<String> recieverCityCombo;
    @FXML
    private CheckBox fragileItemCheck;
    @FXML
    private TextArea packageInfo;
    @FXML
    private TextArea itemInfo;
    @FXML
    private TextArea shippingInfo;
    @FXML
    private TextArea minigameMessage;
    @FXML
    private Button goToMainButton;
    @FXML
    private Button goToPackageButton;
    @FXML
    private TextArea smartPostInfo;
    @FXML
    private TextArea miniGameInfo;
    @FXML
    private TextArea miniGameScore;
    @FXML
    private TextArea packageScore;
    @FXML
    private TextArea itemScore;
    @FXML
    private TextArea shippingScore;
    @FXML
    private TextArea smartPostScore;

    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        webmap.getEngine().load(getClass().getResource("index.html").toExternalForm()); // Loads the google map from index.html
        
        HQ.setParsedLocationsList(builder.getSmartPosts()); // Gets all SmartPost locations from XML file.
        
        // Gets all the different cities that have SmartPosts in them into a list.
        for (SmartPost smp : HQ.getParsedLocationsList()) {
            System.out.println("Lisätään: " + smp.getCity() + ", " + smp.getStrippedOffice());
            HQ.addSmartPostCity(smp.getCity());
        }

        // Sets all the cities we just got to a Combo Box
        for (String city : HQ.getSmartPostCities()) {
            smartPostCityCombo.getItems().add(city);
        }
        
        // Add different premade items to the Item Combo Box.
        itemsCombo.getItems().add(new Item("Uolevi Nikula -Bobblehead", 6000.0f, 0.45f, false));
        itemsCombo.getItems().add(new Item("Programming For Dummies -kirja", 1400.0f, 0.4f, false));
        itemsCombo.getItems().add(new Item("Ydinpommi", 1470000.0f, 1100.0f, true));
        itemsCombo.getItems().add(new Item("Half Life 3- tietokonepeli", 24000.0f, 0.2f, false));
        itemsCombo.getItems().add(new Item("Mona Lisa", 24000.0f, 2.6f, true));
        itemsCombo.getItems().add(new Item("Posliini teekuppi", 150.0f, 0.15f, true));
        itemsCombo.getItems().add(new Item("Sukset", 2000.0f, 3.8f, false));
        itemsCombo.getItems().add(new Item("Hehkulamppu", 250.0f, 0.05f, true));
       
    }  

    @FXML
    private void handleCreateAction(ActionEvent event) // Handles creating new SmartPost locations to the map.
    {
        if (smartPostsCombo.valueProperty().getValue() != null)
        {
            for (SmartPost smp : HQ.getParsedLocationsList()) {

                String loc = "'" + smp.getAddress() + ", " + smp.getZipCode() + " " + smp.getCity() + "'";
                String info = "'" + smp.getInfo() + "'";
                String tag = "'green'";

                if (smp.getAddress().equals(smartPostsCombo.valueProperty().getValue().getAddress()))
                {
                    HQ.addAddedLocation(smp);
                    webmap.getEngine().executeScript("document.goToLocation("+loc+","+info+","+tag+")");
                    messages.setText(smp.getZipCode() + " " + smp.getCity() + ", " + smp.getStrippedOffice() + " Lisätty kartalle!");
                    smartPostInfo.setText(smartPostInfo.getText() + smp.getCity() + ", " + smp.getStrippedOffice() + " Lisätty kartalle!\n");
                }
            }
        }
        else 
        {
            messages.setText("Valitse SmartPost asema ensin!");
            System.out.println("SmartPost asemaa ei valittu.");
        }
        
        /* ********* Package ComboBoxes *********** */
        
        // Gets all the cities that we've added locations to.
        for (SmartPost smp : HQ.getAddedLocationsSet()) {
            System.out.println("Lisätään: " + smp.getCity() + ", " + smp.getStrippedOffice());
            HQ.addPackagingCity(smp.getCity());
        }
        
        // Clears the ComboBoxes to avoid duplicates and sets all the cities we just got.
        senderCityCombo.getItems().clear();
        recieverCityCombo.getItems().clear();
        for (String city : HQ.getPackagingCities()) {
            senderCityCombo.getItems().add(city);
            recieverCityCombo.getItems().add(city);
        }
    }


    @FXML
    private void handleClearAction(ActionEvent event) // Handles removing paths drawn on to the google map.
    {
        System.out.println("Ajetaan 'deletePaths' javascripti, eli poistetaan mahdollisesti piirretyt reitit.");
        messages.setText("Poistetaan reitit.");
        webmap.getEngine().executeScript("document.deletePaths()");
    }


    @FXML
    private void handleOpenCreateAction(ActionEvent event) // Opens packaging tab
    {
        tabMenu.getSelectionModel().select(packageTab);
    }

    @FXML
    private void handleCancelPackageAction(ActionEvent event) // Clears out all fields on packaging tab and goes back to the main tab.
    {
        nameField.clear();
        sizeField.clear();
        weightField.clear();
        FirstClassRadio.setSelected(false);
        SecondClassRadio.setSelected(false);
        ThirdClassRadio.setSelected(false);
        itemsCombo.getSelectionModel().clearSelection();
        senderCombo.getSelectionModel().clearSelection();
        recieverCombo.getSelectionModel().clearSelection();
        packagingMessages.clear();
        tabMenu.getSelectionModel().select(mainTab);
        senderCityCombo.getSelectionModel().clearSelection();
        recieverCityCombo.getSelectionModel().clearSelection();
        senderCombo.getSelectionModel().clearSelection();
        recieverCombo.getSelectionModel().clearSelection();    
    }

    @FXML
    private void handlePackageAction(ActionEvent event) // Handles packaging items, includes checks if an item can be packaged.
    {
        // Initialize all needed variables
        String tempName = "item_name";
        float tempVol = 0.0f;
        float tempWeight = 0.0f;
        boolean tempFragile;
        boolean tempFragileItem;
        boolean itemSet = false;
        boolean geoSet = false;
        boolean packFailed = false;
        boolean newItem = true;
        SmartPost startLocation = new SmartPost();
        SmartPost endLocation = new SmartPost();
        packagingMessages.clear();
            
        // Get Geolocations from selected Smart Posts
        if (senderCombo.getSelectionModel().getSelectedItem() != null 
                && recieverCombo.getSelectionModel().getSelectedItem() != null 
                && senderCombo.getSelectionModel().getSelectedItem() != recieverCombo.getSelectionModel().getSelectedItem())
        { 
            startLocation = senderCombo.getSelectionModel().getSelectedItem();
            endLocation = recieverCombo.getSelectionModel().getSelectedItem();
            geoSet = true;
        }
        else 
        { 
            packFailed = true;
            packagingMessages.setText(packagingMessages.getText() + "Tarkasta paketin lähetystiedot!\n"); 
        }

        // Check and set whether package is fragile
        tempFragile = fragileCheck.isSelected();

        // Set info for new item based on GUI input, if no item selected on ComboBox
        if (itemsCombo.getSelectionModel().getSelectedItem() == null)
        {
            if (!nameField.getText().equals("")) 
            { 
                tempName = nameField.getText().trim(); 
            }
            if (!sizeField.getText().equals("")) 
            { 
                try 
                { 
                    tempVol = Float.parseFloat(sizeField.getText().trim()); 
                } 
                catch(NumberFormatException e) 
                { 
                    packFailed = true;
                    packagingMessages.setText(packagingMessages.getText() + "Tarkasta esineen koko!\n");
                }
                itemSet = true;
            }
            if (!weightField.getText().equals("")) 
            { 
                try 
                { 
                    tempWeight = Float.parseFloat(weightField.getText().trim());
                } 
                catch(NumberFormatException e) 
                { 
                    packagingMessages.setText(packagingMessages.getText() + "Tarkasta esineen paino!\n");
                    packFailed = true;
                }
                itemSet = true;
            }
            tempFragileItem = fragileItemCheck.isSelected();
        }
        else // if Combobox has an item selected, use the existing information.
        {
            tempName = itemsCombo.getSelectionModel().getSelectedItem().getName();
            tempVol = itemsCombo.getSelectionModel().getSelectedItem().getVolume();
            tempWeight = itemsCombo.getSelectionModel().getSelectedItem().getWeight();  
            tempFragileItem = itemsCombo.getSelectionModel().getSelectedItem().getFragileItem();
            itemSet = true;
            newItem = false;
        }
        
        // create the item to be packaged
        Item tempItem = new Item(tempName, tempVol, tempWeight, tempFragileItem);
        
        // Continue if item info has been set correctly and we've gotten the GeoLocations.
        if (itemSet && geoSet)
        {
            // Check which package class is selected
            if (FirstClassRadio.selectedProperty().getValue())
            {
                if (storage.canBePackged(1, tempVol, tempWeight)) // Check whether the item can be packaged.
                {
                    if (storage.getDistance(startLocation.getGeo(), endLocation.getGeo()) < 150) // Check if the distance is too far away to ship.
                    {
                        if (!tempFragile) // Check if the package is marked as fragile
                        {
                        storage.addFirstClass(tempItem, tempFragile, startLocation, endLocation);
                        packagingMessages.setText(packagingMessages.getText() + "Ensimmäisen luokan paketti paketoitu ja siirretty varastoon!\n");
                        
                        itemInfo.setText(itemInfo.getText() + "Pakattu esine: " + tempName + ", Koko: " + tempVol + ", Paino: " + tempWeight + ", Särkyvä: " + tempFragileItem + "\n");
                        
                        packageInfo.setText(packageInfo.getText() 
                                + "1. Luokan Paketti: " + tempItem.getName() 
                                + ", Särkyvä: " + tempFragile  
                                + "\nPaino: " + tempItem.getWeight() 
                                + " (kg), Koko: " + tempItem.getVolume() 
                                + " (cm^3)" + ", Matkan pituus: " + Math.round(storage.getDistance(startLocation.getGeo(), endLocation.getGeo())) + " (km)"
                                + "\nLähtee asemalta: " + startLocation.getCity() + ", " + startLocation.getStrippedOffice() 
                                + ", Asemalle: " + endLocation.getCity() + ", " + endLocation.getStrippedOffice() 
                                + "\n--------------------------------------------------------------------------------\n");
                        
                        gamePoints += 1;
                        miniGameInfo.setText(miniGameInfo.getText() + "+1 (Paketoiminen)\n");
                        }
                        else
                        {
                            packFailed = true;
                            packagingMessages.setText(packagingMessages.getText() + "Särkyvää pakettia ei voi lähettää ykkösluokassa.\n");
                        }
                    }
                    else 
                    {
                    packFailed = true;
                    packagingMessages.setText(packagingMessages.getText() + "Max pituus 150km, et voi lähettää pakettia näin pitkälle!\n");  
                    }
                }
                else
                {
                    packFailed = true;
                    packagingMessages.setText("Pakettia ei voida luoda, esine on liian suuri tai painava!\n");
                }
            }
            else if (SecondClassRadio.selectedProperty().getValue())
            {
                if (storage.canBePackged(2, tempVol, tempWeight))
                {
                    storage.addSecondClass(tempItem, tempFragile, startLocation, endLocation);
                    packagingMessages.setText(packagingMessages.getText() + "Toisen luokan paketti paketoitu ja siirretty varastoon!\n");
                    
                    itemInfo.setText(itemInfo.getText() + "Pakattu esine: " + tempName + ", Koko: " + tempVol + ", Paino: " + tempWeight + ", Särkyvä: " + tempFragileItem + "\n");
                    
                    packageInfo.setText(packageInfo.getText() + "2. Luokan Paketti: " + tempItem.getName() 
                            + ", Särkyvä: " + tempFragile  
                            + "\nPaino: " + tempItem.getWeight() 
                            + " (kg), Koko: " + tempItem.getVolume() + " (cm^3)" 
                            + ", Matkan pituus: " + Math.round(storage.getDistance(startLocation.getGeo(), endLocation.getGeo())) + " (km)"
                            + "\nLähtee asemalta: " + startLocation.getCity() + ", " + startLocation.getStrippedOffice() 
                            + ", Asemalle: " + endLocation.getCity() + ", " + endLocation.getStrippedOffice() 
                            + "\n--------------------------------------------------------------------------------\n");
                    
                    gamePoints += 1;
                    miniGameInfo.setText(miniGameInfo.getText() + "+1 (Paketoiminen)\n");
                }
                else
                {
                    packFailed = true;
                    packagingMessages.setText("Pakettia ei voida luoda, esine on liian suuri tai painava!");
                }
            }
            else if (ThirdClassRadio.selectedProperty().getValue())
            {
                if (storage.canBePackged(3, tempVol, tempWeight))
                {
                    storage.addThirdClass(tempItem, tempFragile, startLocation, endLocation);
                    packagingMessages.setText(packagingMessages.getText() + "Kolmannen luokan paketti paketoitu ja siirretty varastoon!\n");
                    
                    itemInfo.setText(itemInfo.getText() + "Pakattu esine: " + tempName + ", Koko: " + tempVol + ", Paino: " + tempWeight + ", Särkyvä: " + tempFragileItem + "\n");
                    
                    packageInfo.setText(packageInfo.getText() + "3. Luokan Paketti: " 
                            + tempItem.getName() + ", Särkyvä: " + tempFragile  
                            + "\nPaino: " + tempItem.getWeight() 
                            + " (kg), Koko: " + tempItem.getVolume() 
                            + " (cm^3)" + ", Matkan pituus: " + Math.round(storage.getDistance(startLocation.getGeo(), endLocation.getGeo())) + " (km)"
                            + "\nLähtee asemalta: " + startLocation.getCity() + ", " + startLocation.getStrippedOffice() 
                            + ", Asemalle: " + endLocation.getCity() + ", " + endLocation.getStrippedOffice() 
                            + "\n--------------------------------------------------------------------------------\n");
                    
                    gamePoints += 1;
                    miniGameInfo.setText(miniGameInfo.getText() + "+1 (Paketoiminen)\n");
                }
                else
                {
                    packFailed = true;
                    packagingMessages.setText("Pakettia ei voida luoda, esine on liian suuri tai painava!\n");
                }
            }
            else
            {
                System.out.println("Paketti luokkaa ei valittu");
                packFailed = true;
                packagingMessages.setText(packagingMessages.getText() + "Valitse pakettiluokka!\n");
            }   
        }
        else 
        {
            packFailed = true;
            packagingMessages.setText(packagingMessages.getText() + "Tarkasta paketin tiedot!\n");
        }
        
        // See if packaging failed at any point, and remove 1 point from the minigame. If packaging didn't fail and we created a newItem, add 1 to itemCount
        if (packFailed)
        {
            gamePoints -= 1;
            failedPacks += 1;
            miniGameInfo.setText(miniGameInfo.getText() + "-1 (Väärin paketoitu)\n");
        }
        else if (newItem)
        {
            itemCount += 1;
        }
        packFailed = false;
        
        // Clear out package ComboBox and set all items from package list to the CB.
        packagesCombo.getItems().clear();
        for (Package p : storage.getPackageList()) 
        {
            packagesCombo.getItems().add(p);
        }
    }

    @FXML
    private void handleSendAction(ActionEvent event)
    {
        if (packagesCombo.getSelectionModel().getSelectedItem() != null) // Check if a package is selected.
        {
            // Get information from the package
            Package tbSent = packagesCombo.getSelectionModel().getSelectedItem();
            String pClass = tbSent.getClass().getSimpleName();
            boolean fragileItem = tbSent.item.getFragileItem();
            float iSize = tbSent.item.getVolume();
            float iWeight = tbSent.item.getWeight();

            // Check if the package is going to break during transmit.
            if (storage.randomBreakGenerator(pClass, fragileItem, iSize, iWeight))
            {
                packagesBroke += 1;
                gamePoints -= 2;
                miniGameInfo.setText(miniGameInfo.getText() + "-2 (Särkyminen)\n");
                messages.setText("Lähetetään paketti: " + tbSent.item.getName() + "\nKohteesta: " +  tbSent.startPost.getStrippedOffice() 
                        + "\nKohteeseen: " + tbSent.endPost.getStrippedOffice() + "\nValitettavasti paketti meni matkalla rikki.");
                
                shippingInfo.setText(shippingInfo.getText() + "Paketti: " + tbSent.item.getName() + ", Särkyi matkalla\n"
                        + "Asemalta: " + tbSent.startPost.getCity() + ", " + tbSent.startPost.getStrippedOffice() 
                        + ", Asemalle: " + tbSent.endPost.getCity() + ", " + tbSent.endPost.getStrippedOffice() 
                        + "\n--------------------------------------------------------------------------------\n");
                
                storage.deletePackage(packagesCombo.getSelectionModel().getSelectedItem());
                packagesCombo.getItems().remove(packagesCombo.getSelectionModel().getSelectedItem());
            }
            else // If the package doesn't break, add GeoLocation data to an ArrayList
            {
                ArrayList<Float> pathList = new ArrayList<>();
                pathList.add(tbSent.startPost.getGeo().getLat());
                pathList.add(tbSent.startPost.getGeo().getLon());
                pathList.add(tbSent.endPost.getGeo().getLat());
                pathList.add(tbSent.endPost.getGeo().getLon());
                
                // Send package (draw it on the map), with different color/speed depending on package class
                if (pClass.equals("FirstClass")) {
                    webmap.getEngine().executeScript("document.createPath("+pathList+", 'red', 1)");
                }
                else if (pClass.equals("SecondClass")) {
                    webmap.getEngine().executeScript("document.createPath("+pathList+", 'purple', 2)");
                }
                else if (pClass.equals("ThirdClass")) {
                    webmap.getEngine().executeScript("document.createPath("+pathList+", 'blue', 3)");
                    
                }
                else {
                    messages.setText("Luokkaa ei asetettu... Miten tähän päästiin?\n");
                }
                
                if (pClass.equals("FirstClass") || pClass.equals("SecondClass") || pClass.equals("ThirdClass"))
                {
                    packagesSent += 1;
                    gamePoints += 2;
                    miniGameInfo.setText(miniGameInfo.getText() + "+2 (Lähetys)\n");
                    System.out.println("Piirretään reitti pisteestä: " + tbSent.startPost.getCity() + ", " + tbSent.startPost.getStrippedOffice() 
                            + " Pisteeseen: " + tbSent.endPost.getCity() + ", "  + tbSent.endPost.getStrippedOffice());
                    
                    messages.setText("Lähetetään paketti: " + tbSent.item.getName() + "\nKohteesta: " + tbSent.startPost.getCity() + ", " 
                            +  tbSent.startPost.getStrippedOffice()+ "\nKohteeseen: " + tbSent.endPost.getCity() + ", " + tbSent.endPost.getStrippedOffice());
                    
                    shippingInfo.setText(shippingInfo.getText() + "Lähettiin paketti: " + tbSent.item.getName() + ", Luokka: " + pClass
                            + "\nKohteesta: " + tbSent.startPost.getCity() + ", " +  tbSent.startPost.getStrippedOffice() 
                            + ", Kohteeseen: " + tbSent.endPost.getCity() + ", " + tbSent.endPost.getStrippedOffice()
                            + "\n--------------------------------------------------------------------------------\n");
                    
                    storage.deletePackage(packagesCombo.getSelectionModel().getSelectedItem());
                    packagesCombo.getItems().remove(packagesCombo.getSelectionModel().getSelectedItem());
                    
                }

            }
        }
        else
        {
            System.out.println("Pakettia ei ole valittu.");
            messages.setText("Valitse paketti ensin!\n");
        }
    }

    // Print out various information about package classes when you click to choose one.
    @FXML
    private void handleClass1RadioAction(ActionEvent event)
    {
        packagingMessages.setText("1. luokan paketti:\nNopeakuljetus\nMax pituus 150km\nMax Tilavuus 0.75m^3\nMax Paino 50kg\nEi särkyviä paketteja.");
    }

    @FXML
    private void handleClass2RadioAction(ActionEvent event)
    {
        packagingMessages.setText("2. luokan paketti:\nTurvakuljetus\nMax Tilavuus 0.50m^3\nMax Paino 40kg\nEi pituus rajaa\nSärkyvät paketit tänne");
    }

    @FXML
    private void handleClass3RadioAction(ActionEvent event)
    {
        packagingMessages.setText("3. luokan paketti:\nHalpiskuljetus\nMax Tilavuus 1m^3\nMax Paino 60kg\nEi pituus rajaa\nSuositellaan suurille ja painaville paketeille");
    }

    // Clears out new item fields if an existing item is selected
    @FXML
    private void handleItemsComboAction(ActionEvent event)
    {
        nameField.clear();
        sizeField.clear();
        weightField.clear();
        fragileItemCheck.setSelected(false);
    }

    // Clears out existing selected item, if new item fields are typed on.
    @FXML
    private void handleNewItemNameAction(KeyEvent event)
    {
        itemsCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleNewItemSizeAction(KeyEvent event)
    {
        itemsCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleNewItemMassAction(KeyEvent event)
    {
        itemsCombo.getSelectionModel().clearSelection();
    }
    @FXML
    private void handleFragileItemAction(ActionEvent event)
    {
        itemsCombo.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleSmartPostCityAction(ActionEvent event)
    {
        //Add all the SmartPosts from selected city to the Combo Box
        smartPostsCombo.getItems().clear();
        for (SmartPost smp : HQ.getParsedLocationsList())
        {
            if (smartPostCityCombo.getSelectionModel().getSelectedItem().equals(smp.getCity()))
            {
            smartPostsCombo.getItems().add(smp);
            }
        }
    }

    @FXML
    private void handleSenderCityAction(ActionEvent event)
    {  
        // Clear out Combo Box before we add new locations, so that we don't get duplicates.
        senderCombo.getItems().clear();
        for (SmartPost smp : HQ.getAddedLocationsSet()) 
        {
            if (senderCityCombo.getSelectionModel().getSelectedItem().equals(smp.getCity()))
            {
                senderCombo.getItems().add(smp);
            }
        }
    }

    @FXML
    private void handleRecieverCityAction(ActionEvent event)
    {
        // Clear out Combo Box before we add new locations, so that we don't get duplicates.
        recieverCombo.getItems().clear();
        for (SmartPost smp : HQ.getAddedLocationsSet()) 
        {
            if (recieverCityCombo.getSelectionModel().getSelectedItem().equals(smp.getCity()))
            {
                recieverCombo.getItems().add(smp);
            }
        }
    } 

    // Goes to Main tab
    @FXML
    private void handleGoMainButtonAction(ActionEvent event)
    {
        tabMenu.getSelectionModel().select(mainTab);
    }

    // Goes to Package tab
    @FXML
    private void handleGoPackButtonAction(ActionEvent event)
    {
        tabMenu.getSelectionModel().select(packageTab);
    }

    // Update logistics when switching to Logistics tab.
    @FXML
    private void handleTabChangeAction(Event event)
    {
        packageCount = storage.packageCount();
        smartPostCount = HQ.parsedLocationCount();
        smartPostsAdded = HQ.addedLocationCount();

        packageScore.setText("Paketteja varastossa: " + packageCount + "; Väärinpakkaus yrityksiä: " + failedPacks);
        itemScore.setText("Uusia esineitä luotu: " + itemCount + "; Valmiita esineitä: " + itemsCombo.getItems().size());
        shippingScore.setText("Lähetetty: " + packagesSent + "; Rikottu: " + packagesBroke);
        smartPostScore.setText("Asemia löydetty: " + smartPostCount + "; Asemia lisätty: " + smartPostsAdded);
        miniGameScore.setText("Yhteispisteet: " + gamePoints);
        
        if (gamePoints == 0)
        {
            minigameMessage.setText("Pakkaa ja lähetä paketteja saadaksesi pisteitä!");
        }
        else if (gamePoints >= 0 && gamePoints < 5)
        {
            minigameMessage.setText("Hyvä, et ole ainakaan menettänyt pisteitä!");
        }
        else if (gamePoints >= 5 && gamePoints <= 10)
        {
            minigameMessage.setText("Hienoa! Pisteesi alkavat jo kertyä!");
        }
        else if (gamePoints > 10 && gamePoints <= 15)
        {
            minigameMessage.setText("Mahtavaa! Olet jo yli puolenvälin!");
        }
        else if (gamePoints > 15 && gamePoints < 20)
        {
            minigameMessage.setText("Jatka samaan malliin, olet lähellä voittoa!");
        }
        else if (gamePoints >= 20)
        {
            minigameMessage.setText("Onneksi olkoon! Hyvin tehty työ on palkinto itsessään!");
        }
        else if (gamePoints < 0 && gamePoints >= -5)
        {
            minigameMessage.setText("Olet miinuksen puolella, yritä saada pisteitä");
        }
        else if (gamePoints < -5 && gamePoints >= -10)
        {
            minigameMessage.setText("Mitä sinä teet, ei tarkoituksena ole rikkoa paketteja!");
        }
        else if (gamePoints < -10 && gamePoints >= -20)
        {
            minigameMessage.setText("Kuinka tämä on edes mahdollista!");
        }
        else if (gamePoints < -20)
        {
            minigameMessage.setText("Vandalismisi on ilmoitettu poliisille!");
        }
        
    }
}
