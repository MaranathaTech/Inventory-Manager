package invmgr.Models;

import invmgr.AbstractClasses.Part;

/** This extension of the Part abstract class, represents an Outsourced Part.
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */
public class Outsourced extends Part{

    private String companyName;

    /** Creates an Outsourced Part.
     * @param id The part's ID.
     * @param name The part's name.
     * @param price The part's price.
     * @param stock The part's stock.
     * @param min The part's min.
     * @param max The part's max.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * This method is used to set the companyName.
     * @param companyName the name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method is used to return the companyName.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}
