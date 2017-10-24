/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.interfaces;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;

/**
 *
 * @author vasil
 * @param <T>
 * @param <V>
 */
public interface daoInterface<T, V> {

    /**
     *
     * @return
     */
    public EntityManager getEM();

    /**
     *
     * @param Item
     * @return
     */
    
    default public T addItem(T Item) {
        T res = null;
        try {
            EntityManager em = getEM();
            em.getTransaction().begin();
            em.merge(Item);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param Item
     * @return
     */

    default public boolean deleteItem(T Item) {
        boolean res = true;
        try {
            EntityManager em = getEM();
            em.getTransaction().begin();
            em.detach(Item);
            em.getTransaction().commit();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param Item
     * @return
     */

    default public boolean updateItem(T Item) {
        System.out.println("updateItem => " + Item);
        boolean res = false;
        try {
            EntityManager em = getEM();
            System.out.println("em = " + em);
            em.getTransaction().begin();
            em.merge(Item);
            res = true;
            em.getTransaction().commit();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param id
     * @param jpqName
     * @param cl
     * @return
     */
    default public T getItem(long id, String jpqName, Class<T> cl) {
        T res = null;
        try {
            System.out.println("id => " + id);
            EntityManager em = getEM();
            TypedQuery<T> namedQuery = em.createNamedQuery(jpqName, cl);
            namedQuery.setParameter("id", id);
            res = namedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("NoDataFound");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return res;
    }
     
    
    /**
     *
     * @param jpqName
     * @param cl
     * @return
     */
    default public List<T> getList(String jpqName, Class<T> cl) {
        System.out.println("getList => " + jpqName + " cl = " + cl.getName());
        List<T> res = null;
        try {
            EntityManager em = getEM();
            TypedQuery<T> namedQuery = em.createNamedQuery(jpqName, cl);
            //namedQuery.setParameter("id", id);
            res = namedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("res => " + res.size());
        return res;
    }

    default public List<T> getList(String jpqName, Class<T> cl, Map<String, Object> params) {
        System.out.println("getList => " + jpqName + " cl = " + cl.getName());
        List<T> res = null;
        try {
            EntityManager em = getEM();
            TypedQuery<T> namedQuery = em.createNamedQuery(jpqName, cl);
            int plimit = 0;
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (!key.equals("limit")) {
                        namedQuery.setParameter(key, value);
                    } else {
                        plimit = (int) value;
                    }
                }
//                params.forEach((key, val) -> {
//                    
//                });
            }
            //namedQuery.setParameter("id", id);
            if (plimit == 0) {
                res = namedQuery.getResultList();
            } else {
                res = namedQuery.setMaxResults(plimit).getResultList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("res => " + res.size());
        return res;
    }

    /**
     *
     * @param startIdx
     * @param countRec
     * @param jpqName
     * @param cl
     * @return
     */
    default public List<T> getList(int startIdx, int countRec, String jpqName, Class<T> cl) {
        List<T> res = null;
        try {
            EntityManager em = getEM();
            TypedQuery<T> namedQuery = em.createNamedQuery(jpqName, cl);
            //namedQuery.setParameter("id", id);            
            res = namedQuery.setFirstResult(startIdx).setMaxResults(countRec).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
