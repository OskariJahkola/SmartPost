package oliotht;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class Headquarters
{
    private ArrayList<SmartPost> parsedLocationsList = new ArrayList();
    private Set<SmartPost> addedLocationsSet = new HashSet();
    private Set<String> smartPostCities = new TreeSet();
    private Set<String> packagingCities = new TreeSet();
    
    public Headquarters()
    {
    }
    
    // Getters and Setters for XML parsed and added (to the map) SmartPost locations, as well as just cities those SmartPosts are located in.
    public void addParsedLocation(String address, String zipCode, String city, String availablity, String postOffice, GeoLoc location)
    {
        parsedLocationsList.add(new SmartPost(address,  zipCode,  city, availablity, postOffice, location));
    }
    
    public void addParsedLocation(SmartPost smp)
    {
        parsedLocationsList.add(smp);
    }
    
    public void addAddedLocation(String address, String zipCode, String city, String availablity, String postOffice, GeoLoc location)
    {
        addedLocationsSet.add(new SmartPost(address,  zipCode,  city, availablity, postOffice, location));
    }
    
    public void addAddedLocation(SmartPost smp)
    {
        addedLocationsSet.add(smp);
    }
    
    public void addSmartPostCity(String city)
    {
        smartPostCities.add(city);
    }
    
    public void addPackagingCity(String city)
    {
        packagingCities.add(city);
    }
    
    // Gets the amount of Smart Post locations (Total from XML file)
    public int parsedLocationCount()
    {
        return parsedLocationsList.size();
    }
    
    public int addedLocationCount()
    {
        return addedLocationsSet.size();
    }

    public ArrayList<SmartPost> getParsedLocationsList()
    {
        return parsedLocationsList;
    }

    public void setParsedLocationsList(ArrayList<SmartPost> parsedLocationsList)
    {
        this.parsedLocationsList = parsedLocationsList;
    }

    public Set<SmartPost> getAddedLocationsSet()
    {
        return addedLocationsSet;
    }

    public void setAddedLocationsSet(Set<SmartPost> addedLocationsSet)
    {
        this.addedLocationsSet = addedLocationsSet;
    }

    public Set<String> getSmartPostCities()
    {
        return smartPostCities;
    }

    public void setSmartPostCities(Set<String> smartPostCities)
    {
        this.smartPostCities = smartPostCities;
    }

    public Set<String> getPackagingCities()
    {
        return packagingCities;
    }


    public void setPackagingCities(Set<String> packagingCities)
    {
        this.packagingCities = packagingCities;
    }
}

