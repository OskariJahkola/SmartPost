package oliotht;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class Storage {
    
    private Random randomGenerator;
    private ArrayList<Package> packageList = new ArrayList();
    
    public Storage()
    {
        // Initialize Random generator and set the seed as the answer to life, the universe and everything.
        randomGenerator = new Random();
        randomGenerator.setSeed(42);
    }

    // Methods for adding packages per class basis
    public void addFirstClass(Item item, boolean fragile, SmartPost startLoc, SmartPost endLoc)
    {
        packageList.add(new FirstClass(item, fragile, startLoc, endLoc));
    }
    
    public void addSecondClass(Item item, boolean fragile, SmartPost startLoc, SmartPost endLoc)
    {
        packageList.add(new SecondClass(item, fragile, startLoc, endLoc));
    }
    
    public void addThirdClass(Item item, boolean fragile, SmartPost startLoc, SmartPost endLoc)
    {
        packageList.add(new ThirdClass(item, fragile, startLoc, endLoc));
    }
    
    public ArrayList<Package> getPackageList()
    {
        return packageList;
    }
    
    // Returns the amount of packages in the storage
    public int packageCount()
    {
        return packageList.size();
    }
    
    public void deletePackage(Package p)
    {
        for (Package pack : packageList) {
            if (pack == p)
            {
                packageList.remove(p);
                break;
            }
        }
    }
    
    // Calculates distance based on the latitude and longitude of start and end SmartPost locations.
    public float getDistance(GeoLoc startLoc, GeoLoc endLoc)
    {
       float lat1 = startLoc.getLat();
       float lat2 = endLoc.getLat();
       float lon1 = startLoc.getLon(); 
       float lon2 = endLoc.getLon();
       float earthRadius = 6378.137f;
       float dLat, dLon;

       dLat = (float) ((lat2 - lat1) * Math.PI / 180);
       dLon = (float) ((lon2 - lon1) * Math.PI / 180);
       
       float a = (float) (Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2));

       float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
       
       return earthRadius * c * 1.1f; // Multiply the end result by 1.1 (add 10%) since all roads aren't straight lines after all.
    }
    
    // Determines if an item can be packaged based on its physical properties.
    public boolean canBePackged(Integer pClass, float iSize, float iWeight)
    {
        switch (pClass)
        {
            case 1:
                if (iSize <= 75000 && iWeight <= 50)
                    return true;
            case 2:
                if (iSize <= 50000 && iWeight <= 40)
                    return true;
            case 3:
                if (iSize <= 100000 && iWeight <= 60)
                    return true;
            default:
                return false;
        }
    }
    
    // Determines if a package is going to break during transit.
    public boolean randomBreakGenerator(String pClass, boolean fragile, float iSize, float iWeight)
    {
        boolean broke = false;
        float breakFactor = 1.0f;
        float decider, randomInt;
        
        // First class packages always break if the item is fragile (but cannot be sent if package is marked as fragile)
        if (pClass.equals("FirstClass"))
        {
            if (fragile)
            {
                broke = true;
            }
            else 
            {
                broke = false;
            }
        }
        
        // Third class *FRAGILE* packages have a 50/50 chance of breaking, smaller and/or lighter packages have a higher chance of breaking (about 70%)
        if (pClass.equals("ThirdClass"))
        {
            if (fragile)
            {
                if (iWeight < 40)
                {
                    breakFactor -= 0.15f;
                }
                if (iSize < 75000)
                {
                    breakFactor -= 0.15f;
                }

                randomInt = randomGenerator.nextInt(100);
                decider = randomInt * breakFactor;

                if (decider < 50)
                {
                    broke = true;
                }

                else if (decider >= 50)
                {
                    broke = false;
                }
            }
        }
        return broke;
    } 
}
