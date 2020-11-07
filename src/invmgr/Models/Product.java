package invmgr.Models;

import invmgr.AbstractClasses.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Represents a Product.
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Creates a Product.
     * @param id The product's ID.
     * @param name The product's name.
     * @param price The product's price.
     * @param stock The product's stock.
     * @param min The product's min.
     * @param max The product's max.
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> parts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = parts;
    }

    /**
     * This method is used to set the id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * This method is used to set the id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is used to get the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to set the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to get the price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method is used to set the price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method is used to get the stock.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method is used to set the stock.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method is used to get the min.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * This method is used to set the min.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method is used to get the max.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * This method is used to set the mac.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method is used to add to the associatedParts Collection.
     * @param part the Part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method is used to delete to the part from the associatedParts Collection.
     * @param part the Part to delete
     */
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }

    /**
     * This method is used to get the associatedParts.
     * @return the associatedParts collection
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
