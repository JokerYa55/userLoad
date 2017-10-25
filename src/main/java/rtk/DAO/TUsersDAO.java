/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import rtk.bean.TUsers;
import rtk.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class TUsersDAO implements daoInterface<TUsers, Long> {

    private EntityManager em;

    public TUsersDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }

    public TUsers getItemByName(String name, String jpqName) {
        TUsers res = null;
        try {
            //System.out.println("id => " + name);            
            TypedQuery<TUsers> namedQuery = em.createNamedQuery(jpqName, TUsers.class);
            namedQuery.setParameter("username", name);
            res = namedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("NoDataFound");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return res;
    }
    
}
