package oliotht;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class SmartPost
{    
    private String address, zipCode, avail, poffice, city, info, strippedOffice;
    private GeoLoc geo;

    public SmartPost() 
    {
        address = "Ei asetettu";
        zipCode = "00000";
        city = "Ei asetettu";
        poffice = "Ei asetettu";
        avail = "Ei asetettu";
        info = "Ei asetettu";
        strippedOffice = poffice.replace("Pakettiautomaatti, ", "");
        geo = new GeoLoc();
    }

    public SmartPost(String a, String z, String c, String av, String p, GeoLoc location) 
    {
        address = a;
        zipCode = z;
        city = c;
        avail = av;
        poffice = p;
        geo = location;
        strippedOffice = poffice.replace("Pakettiautomaatti, ", "");
        info = strippedOffice + ", Avoinna: " + avail;
    }
    
    
    public String getAddress() 
    {
        return address;
    }

    public String getZipCode() 
    {
        return zipCode;
    }
    
    public String getCity() 
    {
        return city;
    }
    
    public String getInfo() 
    {
        return info;
    }
    
    GeoLoc getGeo() {
        return geo;
    }
    
    public String getStrippedOffice()
    {
        return strippedOffice;
    }

    @Override
    public String toString() 
    {
        return city + " " + zipCode + ", " + strippedOffice;
    }   
}

class GeoLoc
{
    float lat;
    float lon;
    
    GeoLoc()
    {
        lat = 0.0f;
        lon = 0.0f;
    }
    
    GeoLoc(float la, float lo)
    {
        lat = la;
        lon = lo;
    }
    
    public float getLat() 
    {
        return lat;
    }
    
    public float getLon() 
    {
        return lon;
    }  
}
