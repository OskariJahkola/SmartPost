package oliotht;
/**
 *
 * @author Olio-ohjelmointi Harjoitustyö, LUT 
 * Kaplas & Jahkola
 * 19.12.2014
 * 
 */

// Package itself is an abstract call and cannot be initialized on its own
public abstract class Package
{
    protected Item item;
    protected boolean fragile;
    protected SmartPost startPost;
    protected SmartPost endPost;
    
    @Override
    public String toString() 
    {
        if (fragile)
        {
            return item.getName() + ", Särkyvä";
        }
        else return item.getName();
    }  
}

// Different classes that extend the abstrac package class, these must be called to create a new package.
class FirstClass extends Package 
{
    public FirstClass(Item i, boolean f, SmartPost sp, SmartPost ep)
    {
        item = i; 
        fragile = f;  
        startPost = sp;
        endPost = ep;
    }
}

class SecondClass extends Package
{
    public SecondClass(Item i, boolean f, SmartPost sp, SmartPost ep)
    {
        item = i; 
        fragile = f;  
        startPost = sp;
        endPost = ep;
    }
}

class ThirdClass extends Package
{
    public ThirdClass(Item i, boolean f, SmartPost sp, SmartPost ep)
    {
        item = i; 
        fragile = f;  
        startPost = sp;
        endPost = ep;
    }
     
}

