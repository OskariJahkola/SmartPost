package oliotht;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */
public class Item 
{
    private String name;
    private float volume;
    private float weight;
    private boolean fragileItem;

    public Item(String n, float v, float w, boolean f)
    {
        name = n;
        volume = v;
        weight = w;
        fragileItem = f;
    }

    public String getName()
    {
        return name;
    }
    
    public float getVolume()
    {
        return volume;
    }
    
    public float getWeight()
    {
        return weight;
    }
    
    public boolean getFragileItem()
    {
        return fragileItem;
    }
    
    @Override
    public String toString() {
        if (!fragileItem)
        {
            return name;
        }
        else 
        {
            return name + " (Särkyvä)";
        }
    }
}
