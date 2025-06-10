package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Product;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.ProductService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("ProductConverter")
public class ProductConverter implements Converter {

    //for use in xhtml :
    //<f:converter converterId="ProductConverter"/>

    @Override
    public Product getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //---> static methode for call without instantiate in bean.

    public static Product getAsObjectStatic(String value){

        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        ProductService productService = new ProductService();
        Product product;
        try
        {
            product = productService.selectProductByIdProduct(Integer.parseInt(value), em);
        }catch (Exception e)
        {
            product = null;
        }
        finally {
            em.close();
        }
        return product;

    }


    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Product product = (Product) value;
        return String.valueOf(product.getId());
    }
}
