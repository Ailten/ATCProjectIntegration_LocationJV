package adrien.faouzi.Interface;

import javax.persistence.EntityManager;

public interface IService<TEntity> {

    public TEntity selectEntityByIdEntity(int idEntity, EntityManager em);

}
