package invmgr.Models;

import invmgr.AbstractClasses.Part;

/** This extension of the Part abstract class, represents an In-House Part.
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */
public class InHouse extends Part {

    private Integer machineId;

    /** Creates an In-House Part.
     * @param id The part's ID.
     * @param name The part's name.
     * @param price The part's price.
     * @param stock The part's stock.
     * @param min The part's min.
     * @param max The part's max.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * This method is used to set the machineId.
     * @param machineId the id to set
     */
    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    /**
     * This method is used to return the machineId.
     * @return the machineId
     */
    public Integer getMachineId() {
        return machineId;
    }

}
