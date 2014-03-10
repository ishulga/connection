package com.sh.connection.persistence.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sh.connection.persistence.model.HasId;
import com.sh.connection.util.InterfaceUtils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericPL<T extends HasId & Serializable> extends HibernateDaoSupport
{
    protected final Class<T> type;

    public GenericPL(Class<T> type)
    {
        this.type = type;
    }

    public Long create(T newObject)
    {
        getHibernateTemplate().persist(newObject);
        return newObject.getId();
    }

    public void delete(Long id)
    {
        getHibernateTemplate().delete(InterfaceUtils.getReferenceWithId(id, type));
    }

    public T getById(Long id)
    {
        return getHibernateTemplate().get(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByQBE(T qbe)
    {
        return getCriteria(qbe).list();
    }

    protected Criteria getCriteria(T qbe, String... excludeProperties)
    {
        Example example = Example.create(qbe).excludeZeroes();
        for (String f : excludeProperties)
        {
            example.excludeProperty(f);
        }
        return getSession().createCriteria(qbe.getClass()).add(example)
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    public void merge(T transientObject)
    {
        getHibernateTemplate().merge(transientObject);
    }

    public void flushAndClear()
    {
        getHibernateTemplate().flush();
        getHibernateTemplate().clear();
    }

    @SuppressWarnings("unchecked")
    public Set<T> mergeObjectCollectionsAndDeleteOrphan(Set<? extends T> oldSet, Set<? extends T> newSet)
    {
        Set<T> result = new HashSet<T>();
        Set<Long> oldObjectsIds = InterfaceUtils.getIdSet(newSet);
        for (T newOne : newSet)
        {
            if (newOne.getId() == null)
            {
                Long id = create(newOne);
                result.add((T) InterfaceUtils.getReferenceWithId(id, newOne.getClass()));
                continue;
            }
            merge(newOne);
            result.add(newOne);
        }
        for (T oldOne : oldSet)
        {
            if (!oldObjectsIds.contains(oldOne.getId()))
            {
                delete(oldOne.getId());
            }
        }
        return result;
    }

}
