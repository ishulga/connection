package com.sh.connection.persistence.jpa.pl;

import com.sh.connection.persistence.model.HasId;
import com.sh.connection.util.InterfaceUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GenericPL<T extends HasId & Serializable> {
    protected final Class<T> type;

    @PersistenceContext
    protected EntityManager em;

    public GenericPL(Class<T> type) {
        this.type = type;
    }

    public T create(T newObject) {
        em.persist(newObject);
        return newObject;
    }

    public void delete(Long id) {
        em.remove(InterfaceUtils.getReferenceWithId(id, type));
    }

    public T getById(Long id) {
        return em.find(type, id);
    }

    @SuppressWarnings("unchecked")
//    public List<T> getListByQBE(T qbe) {
//        return getCriteria(qbe).list();
//    }

//    protected Criteria getCriteria(T qbe, String... excludeProperties) {
//        Example example = Example.create(qbe).excludeZeroes();
//        for (String f : excludeProperties) {
//            example.excludeProperty(f);
//        }
//        return getSession().createCriteria(qbe.getClass()).add(example)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//    }

    public void merge(T transientObject) {
        em.merge(transientObject);
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

    @SuppressWarnings("unchecked")
    public Set<T> mergeObjectCollectionsAndDeleteOrphan(Set<? extends T> oldSet, Set<? extends T> newSet) {
        Set<T> result = new HashSet<T>();
        Set<Long> oldObjectsIds = InterfaceUtils.getIdSet(newSet);
        for (T newOne : newSet) {
            if (newOne.getId() == null) {
                T id = create(newOne);
                result.add((T) InterfaceUtils.getReferenceWithId(id.getId(), newOne.getClass()));
                continue;
            }
            merge(newOne);
            result.add(newOne);
        }
        for (T oldOne : oldSet) {
            if (!oldObjectsIds.contains(oldOne.getId())) {
                delete(oldOne.getId());
            }
        }
        return result;
    }

}
