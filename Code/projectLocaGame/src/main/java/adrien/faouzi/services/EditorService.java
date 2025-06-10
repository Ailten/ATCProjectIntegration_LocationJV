package adrien.faouzi.services;

import adrien.faouzi.entities.Editor;
import adrien.faouzi.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class EditorService {

    /**
     * select all editor from db.
     * @param em entity manager for aces to db.
     * @return list of all editor from db.
     */
    public List<Editor> selectEditorAll(EntityManager em)
    {
        return em.createNamedQuery("Editor.SelectEditorAll", Editor.class)
                .getResultList();
    }



    /**
     * select one editor from db, selected by id.
     * @param idEditor id of editor research in db.
     * @param em entity manager for aces to db.
     * @return one instance of editor.
     */
    public Editor selectEditorByIdEditor(int idEditor, EntityManager em)
    {
        return em.createNamedQuery("Editor.SelectEditorByIdEditor", Editor.class)
                .setParameter("idEditor", idEditor)
                .getSingleResult();
    }



    /**
     * insert an entity in db.
     * @param editor entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Editor insertEditor(Editor editor, EntityManager em)
    {
        em.persist(editor);
        em.flush();
        return editor;
    }



    /**
     * update an entity in db.
     * @param editor entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Editor updateEditor(Editor editor, EntityManager em)
    {
        em.merge(editor);
        em.flush();
        return editor;
    }



    /**
     * research entity matching with a filter.
     * @param researchWord word using for research.
     * @param orderBy word using for order.
     * @param asc is order ascending.
     * @param em entity manager.
     * @return list entity matching.
     */
    public List<Editor> findEditorByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        return em.createNamedQuery("Editor.SelectEditorByFilter"+
                ((orderBy.equals("id"))? "OrderByNum": "OrderByStr")+
                ((asc)? "Asc": "Desc"),
                Editor.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * count join of an entity before delete.
     * @param idEditor id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoin(int idEditor, EntityManager em){
        return em.createNamedQuery("Editor.SelectJoin", Product.class)
                .setParameter("idEditor", idEditor)
                .getResultList().size();
    }



    /**
     * delete entity from db.
     * @param editor entity ask delete.
     * @param em entity manager.
     */
    public void delete(Editor editor, EntityManager em){
        if(!em.contains(editor))
            editor = em.merge(editor);
        em.remove(editor);
        em.flush();
    }

}
