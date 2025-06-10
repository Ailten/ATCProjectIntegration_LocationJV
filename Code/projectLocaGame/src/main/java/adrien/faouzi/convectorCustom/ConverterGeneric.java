//package adrien.faouzi.convectorCustom;
//
//import adrien.faouzi.Interface.IEntity;
//import adrien.faouzi.Interface.IService;
//import adrien.faouzi.entities.Product;
//import adrien.faouzi.projetlocagame.connexion.EMF;
//import adrien.faouzi.services.ProductService;
//
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.persistence.EntityManager;
//
//public class ConverterGeneric<TEntity extends IEntity, TService extends IService> implements Converter {
//
//    //for use in xhtml :
//    //in input
//    //<f:converter converterId="MyEntityConverter"/>
//    //or select (more use full)
//    //<p:selectOneMenu converter="MyEntityConverter"
//
//    @Override
//    public TEntity getAsObject(String value){
//
//        if (value==null || value.equals("0") || value.equals("")) {
//            return null;
//        }
//
//        EntityManager em = EMF.getEM();
//        TService service = new TService();
//        TEntity entity;
//        try
//        {
//            entity = service.selectEntityByIdEntity(Integer.parseInt(value), em);
//        }catch (Exception e)
//        {
//            entity = null;
//        }
//        finally {
//            em.close();
//        }
//        return entity;
//
//    }
//
//
//    @Override
//    public String getAsString(Object value)
//    {
//        if(value==null){
//            return "0";
//        }
//        TEntity entity = (TEntity) value;
//        return String.valueOf(entity.getId());
//    }
//}
