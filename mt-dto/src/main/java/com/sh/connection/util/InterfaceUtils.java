package com.sh.connection.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sh.connection.persistence.model.HasId;

import org.apache.commons.lang.ArrayUtils;

public class InterfaceUtils
{
    private InterfaceUtils()
    {
    }

    public static <I extends HasId> Set<Long> getIdSet(Collection<I> objectList)
    {
        Set<Long> result = new HashSet<Long>(objectList.size());
        for (I i : objectList)
        {
            if (i.getId() != null)
            {
                result.add(i.getId());
            }
        }
        return result;
    }

    public static <I extends HasId> Map<Long, I> getIdToObjectsMap(Collection<I> objectList)
    {
        Map<Long, I> result = new HashMap<Long, I>(objectList.size());
        for (I i : objectList)
        {
            if (i.getId() != null)
            {
                result.put(i.getId(), i);
            }
        }
        return result;
    }

    public static <I extends HasId> I getReferenceWithId(Long id, Class<I> clazz)
    {
        I result;
        try
        {
            result = clazz.newInstance();
        }
        catch (Exception e)
        {
            // should never happen
            throw new IllegalArgumentException("Can't instantiate object instance.", e);
        }
        result.setId(id);
        return result;
    }

    public static <I extends HasId> I getById(Collection<I> objects, Long id)
    {
        if (id == null)
        {
            return null;
        }
        for (I i : objects)
        {
            if (id.equals(i.getId()))
            {
                return i;
            }
        }
        return null;
    }

    public static Field[] getAllDeclaredFields(Class<?> type)
    {
        if (type != null)
        {
            return (Field[]) ArrayUtils.addAll(type.getDeclaredFields(),
                getAllDeclaredFields(type.getSuperclass()));
        }
        return new Field[0];
    }
}
