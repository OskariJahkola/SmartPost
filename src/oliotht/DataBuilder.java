package oliotht;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class DataBuilder
{
    private ArrayList<SmartPost> smpList;
    private Document doc;

    public ArrayList<SmartPost> getSmartPosts() 
    {
        // Gets the entire XML files contents
        try 
        {
            URL url = new URL("http://smartpost.ee/fi_apt.xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String content = "";
            String line;

            while ((line = br.readLine()) != null) 
            {
                content += line + "\n";
            }
            
            // Gets wanted data from the XML files contents
            smpList = getData(content);

        } 
        catch (MalformedURLException ex) 
        {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return smpList;
    }
     

    public ArrayList<SmartPost> getData(String content) 
    {
        // Creates a new document builder and parses the data.
        try 
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            doc = dBuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();

            // New SmartPost list to save Smart Post data from XML.
            smpList = new ArrayList();
            parseCurrentData();
        } 
        catch (ParserConfigurationException | SAXException | IOException ex) 
        {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return smpList;
    }

    private void parseCurrentData() 
    {
        // Gets all nodes with the tag 'place' and seperates required sub elements, which then get created into a SmartPost object and added to the SmartPost list.
        NodeList nodes = doc.getElementsByTagName("place");
        for (int i = 1; i < nodes.getLength(); i++) 
        {
            Node node = nodes.item(i);
            Element e = (Element) node;

            String zipCode = e.getElementsByTagName("code").item(0).getFirstChild().getNodeValue();
            String city = e.getElementsByTagName("city").item(0).getFirstChild().getNodeValue();
            String address = e.getElementsByTagName("address").item(0).getFirstChild().getNodeValue();
            String avail = e.getElementsByTagName("availability").item(0).getFirstChild().getNodeValue();
            String poffice = e.getElementsByTagName("postoffice").item(0).getFirstChild().getNodeValue();
            Float latitude = Float.parseFloat(e.getElementsByTagName("lat").item(0).getFirstChild().getNodeValue());
            Float longitude = Float.parseFloat(e.getElementsByTagName("lng").item(0).getFirstChild().getNodeValue());
            GeoLoc location = new GeoLoc(latitude, longitude);
            //System.out.println(zipCode + " " + city + ", " + address + ", " + zipCode);
            smpList.add(new SmartPost(address, zipCode, city, avail, poffice, location));
        }
    }
}
