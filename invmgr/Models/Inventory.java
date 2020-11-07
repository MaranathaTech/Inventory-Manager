package invmgr.Models;

import invmgr.AbstractClasses.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Represents an collection of the application's parts.
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method is used to return this collection.
     * @return the collection
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method is used to return this collection.
     * @return the collection
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method is used to add a part to the collection.
     * @param partToAdd the Part to add to collection
     */
    public static void addPart(Part partToAdd){
        allParts.add(partToAdd);
    }

    /**
     * This method is used to add a part to the collection.
     * @param productToAdd the Part to add to collection
     */
    public static void addProduct(Product productToAdd){
        allProducts.add(productToAdd);
    }

    /**
     * This method is used to check if a part exists in a product.
     * This function is used to correct an error that occurred when trying to delete a part that was contained in a product.
     * @param partToFind the Part to search for.
     * @return a boolean
     */
    public static boolean partExistsInProduct(Part partToFind){
        for (Product product : allProducts) {
            for (Part part : product.getAllAssociatedParts()) {
                if (part.getId() == partToFind.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is used to check if a product exists.
     * @param productToFind the Product to search for.
     * @return a boolean
     */
    public static boolean productExists(Part productToFind){

        for (Product product : allProducts){
            if (product.getId() == productToFind.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to return a collection of results that match the search string.
     * @param partString the string to use to search parts.
     * @return the a list of matching results
     */
    public static ObservableList<Part> lookupPart(String partString){
        ObservableList<Part> parts = FXCollections.observableArrayList();

        for (Part part : allParts){
            String tempID = String.valueOf(part.getId());
            if (part.getName().contains(partString) || tempID.contains(partString)) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * This method is used to return a collection of results that match the search string.
     * @param productString the string to use to search products.
     * @return the a list of matching results
     */
    public static ObservableList<Product> lookupProduct(String productString){
        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product : allProducts){
            String tempID = String.valueOf(product.getId());
            if (product.getName().contains(productString) || tempID.contains(productString)) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * This method is used to return a collection of results that match the search int.
     * @param productId the int to use to search products.
     * @return the a list of matching results
     */
    public static ObservableList<Product> lookupProduct(int productId){
        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product : allProducts){
            if (product.getId() == productId) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * This method is used to return a collection of results that match the search int.
     * @param partId the int to use to search parts.
     * @return the a list of matching results
     */
    public static ObservableList<Part> lookupPart(int partId){
        ObservableList<Part> parts = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getId() == partId) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * This method is used to get a Part from the collection by the supplied index.
     * @param index the item's index that you would like to return.
     * @return the desired Part
     */
    public static Part getPart(int index){
        return allParts.get(index);
    }

    /**
     * This method is used to get a Product from the collection by the supplied index.
     * @param index the item's index that you would like to return.
     * @return the desired Product
     */
    public static Product getProduct(int index){
        return allProducts.get(index);
    }

    /**
     * This method is used to delete a supplied Part object from the collection.
     * @param partToDelete the Part object that should be deleted from the collection.
     * @return boolean
     */
    public static boolean deletePart(Part partToDelete){
        if(allParts.contains(partToDelete)) {
            allParts.remove(partToDelete);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method is used to delete a supplied Product object from the collection.
     * @param productToDelete the Product object that should be deleted from the collection.
     * @return boolean
     */
    public static boolean deleteProduct(Product productToDelete){
        if(allProducts.contains(productToDelete)) {
            allProducts.remove(productToDelete);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method is used to replace a part in the collection.
     * @param partToReplace the Part object that should be replaced in the collection.
     */
    public static void updatePart(Part partToReplace) {
        int count = 0;

        for (Part p : allParts){
            if(partToReplace.getId() == p.getId()){
                allParts.set(count, partToReplace);
            }
            count++;
        }

    }

    /**
     * This method is used to replace a product in the collection.
     * @param productToReplace the Product object that should be replaced in the collection.
     */
    public static void updateProduct(Product productToReplace) {
        int count = 0;

        for (Product p : allProducts){
            if(productToReplace.getId() == p.getId()){
                allProducts.set(count, productToReplace);
            }
            count++;
        }

    }

    /**
     * This method is used to get the index for a supplied Part object.
     * @param part the Part object.
     * @return the index for the supplied part
     */
    public static int getPartIndex(Part part) {
        int count = 0;

        for (Part p : allParts){
            if(part.getId() == p.getId()){
                return count;
            }
            count++;
        }

        return -1;
    }

    /**
     * This method is used to get the index for a supplied Product object.
     * @param product the Product object.
     * @return the index for the supplied part
     */
    public static int getProductIndex(Product product) {
        int count = 0;

        for (Product p : allProducts){
            if(product.getId() == p.getId()){
                return count;
            }
            count++;
        }

        return -1;
    }

    /**
     * This method is used to generate a sequential ID.
     * @return the id for the next part
     */
    public static int getNewPartId() {

        if(allParts.size() == 0){
            return 1;
        }

        return allParts.get(allParts.size() - 1).getId() + 1;

    }

    /**
     * This method is used to generate a sequential ID.
     * @return the id for the next product
     */
    public static int getNewProductId() {

        if(allProducts.size() == 0){
            return 1;
        }

        return allProducts.get(allProducts.size() - 1).getId() + 1;

    }


}
