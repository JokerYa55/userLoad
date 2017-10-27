/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.userloader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import rtk.bean.TUsers;
import java.io.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Query;
import rtk.bean.BrokerLink;
import rtk.bean.BrokerLinkPK;
import rtk.bean.TUserAttribute;

/**
 *
 * @author vasil
 */
public class loader {

    /**
     * @param args the command line arguments
     */
    private static final Logger log = Logger.getLogger(loader.class.getName());
    private static String identityProvider;
    private static String storageProviderID;
    private static String realmID;
    private static long commit_count;
    private static String filename_in;
    private static String filename_out;

    public static void main(String[] args) {
        // TODO code application logic here
        log.info(Arrays.toString(args));
        if (args.length > 0) {
            filename_in = args[0];
            filename_out = args[1];
            log.info("filename_in => " + filename_in);
            log.info("filename_out => " + filename_out);

            try {

                commit_count = Long.parseLong(args[2]);
                identityProvider = args[3];
                storageProviderID = args[4];
                realmID = args[5];

                log.info("commit_count => " + commit_count);
                log.info("identityProvider => " + identityProvider);
                log.info("storageProviderID => " + storageProviderID);
                log.info("realmID => " + realmID);

                loadUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadUsers() {
        try {
            String nextString;
            long i = 0;
            long err_line = 0;
            long rez = 0;
            String[] arr;
            TUsers user;
            EntityManager em = Persistence.createEntityManagerFactory("rti_userLoader_JPA").createEntityManager();
            EntityManager em1 = Persistence.createEntityManagerFactory("rti_userLoader_KK_JPA").createEntityManager();
            StringBuilder temp = new StringBuilder();

            Query query_user = em.createNativeQuery("INSERT INTO public.t_users(\n"
                    + "             id, "
                    + "             description, "
                    + "             email, "
                    + "             enabled, "
                    + "             firstname, "
                    + "             hash_type, \n"
                    + "             lastname, "
                    + "             password, "
                    + "             phone, "
                    + "             salt, "
                    + "             thirdname,  "
                    + "             user_region, \n"
                    + "             user_status, "
                    + "             username)\n"
                    + "    VALUES (nextval('t_users_id_seq'), ?, ?, ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, ?, ?, ?) returning id");

            Query query_attr = em.createNativeQuery("INSERT INTO public.t_user_attribute(\n"
                    + "             id, "
                    + "             name, "
                    + "             value, "
                    + "             visible_flag, "
                    + "             user_id)\n"
                    + "    VALUES (nextval('t_user_attribute_id_seq'), ?, ?, ?, ?) returning id");
            Query query_broker = em1.createNativeQuery("INSERT INTO public.broker_link(\n"
                    + "             identity_provider, "
                    + "             storage_provider_id, "
                    + "             realm_id, "
                    + "             broker_user_id, \n"
                    + "             broker_username, "
                    + "             user_id)\n"
                    + "    VALUES (?, ?, ?, \n"
                    + "            ?, ?, ?) returning identity_provider");

            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)))) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));
                while ((nextString = bReader.readLine()) != null) {
                    try {
                        rez = (long) i % commit_count;
                        err_line++;
                        //rez = (long) Math.floor(i / 5);
                        if (rez == 0) {
                            log.info("commit transaction i=>" + i);
                            if (em.getTransaction().isActive()) {
                                try {
                                    em.getTransaction().commit();
                                    if (em1.getTransaction().isActive()) {
                                        em1.getTransaction().commit();
                                    }

                                } catch (Exception e3) {
                                    log.log(Priority.ERROR, e3);
                                    temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
                                    bWriter.write(temp.toString() + "\n");
                                    bWriter.flush();
                                    temp = new StringBuilder();
                                    err_line = 0;
                                    if (em.getTransaction().isActive()) {
                                        em.getTransaction().rollback();
                                        if (em1.getTransaction().isActive()) {
                                            em1.getTransaction().rollback();
                                        }
                                        em.getTransaction().begin();
                                        em1.getTransaction().begin();
                                    }
                                    i++;
                                }
                            }
                            err_line = 0;
                            temp.delete(0, Integer.MAX_VALUE);
                            em.getTransaction().begin();
                            em1.getTransaction().begin();
                        }
                        temp.append(nextString).append("\n");
                        nextString = nextString.replaceAll("\"", "");
                        arr = nextString.split(";", -1);
                        // log.info(Arrays.toString(arr));

                        try {
                            //em.persist(user);

                            query_user.setParameter(1, "<ELK_ADD>");
                            query_user.setParameter(2, arr[1]);
                            query_user.setParameter(3, true);
                            query_user.setParameter(4, arr[4]);
                            query_user.setParameter(5, "sha1");
                            query_user.setParameter(6, arr[5]);
                            query_user.setParameter(7, arr[7]);
                            query_user.setParameter(8, formatPhone(arr[2]));
                            query_user.setParameter(9, arr[8]);
                            query_user.setParameter(10, arr[6]);
                            query_user.setParameter(11, Integer.parseInt(arr[9]));
                            query_user.setParameter(12, 0);
                            query_user.setParameter(13, arr[3]);
                            Long user_id = (Long) query_user.getSingleResult();

                            query_attr.setParameter(1, "id_app_1");
                            query_attr.setParameter(2, arr[0]);
                            query_attr.setParameter(3, true);
                            query_attr.setParameter(4, user_id);

                            Long attr_id = (Long) query_attr.getSingleResult();
                            /*
                            identity_provider, "
                    + "             storage_provider_id, "
                    + "             realm_id, "
                    + "             broker_user_id, \n"
                    + "             broker_username, "
                    + "             user_id
                            */
                            
                            query_broker.setParameter(1, identityProvider);
                            query_broker.setParameter(2, storageProviderID);
                            query_broker.setParameter(3, realmID);
                            query_broker.setParameter(4, arr[0]);
                            query_broker.setParameter(5, arr[3]);
                            query_broker.setParameter(6, "f:" + storageProviderID + ":" + user_id);
                            
                            String broker_id = (String) query_broker.getSingleResult();
                            
                            //Long user_id = biid.longValue();
                            // Добавляем ссылку на провайдер
                            /*BrokerLinkPK pk = new BrokerLinkPK(identityProvider, "f:" + storageProviderID + ":" + user.getId().toString());
                            BrokerLink link = new BrokerLink();
                            link.setBrokerUsername(user.getUsername());
                            link.setStorageProviderId(storageProviderID);
                            link.setBrokerLinkPK(pk);
                            link.setRealmId(realmID);
                            link.setBrokerUserId(arr[0]);

                            //log.info("link => " + link);
                            em1.persist(link);
                             */
                            if (em1.getTransaction().isActive()) {
                                em1.getTransaction().commit();
                            }

                        } catch (Exception e2) {
                            log.log(Priority.ERROR, e2);
                            temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
                            bWriter.write(temp.toString() + "\n");
                            bWriter.flush();
                            temp = new StringBuilder();
                            err_line = 0;
                            if (em.getTransaction().isActive()) {
                                em.getTransaction().rollback();
                                if (em1.getTransaction().isActive()) {
                                    em1.getTransaction().rollback();
                                }
                                em.getTransaction().begin();
                                em1.getTransaction().begin();
                            }
                            i++;
                        }
                        i++;
                    } catch (IOException e1) {
                        log.log(Priority.ERROR, e1);
                        log.error("Ошибка => " + nextString);
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {
                log.log(Priority.ERROR, e);
            }
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }

            if (em1.getTransaction().isActive()) {
                em1.getTransaction().commit();
            }
            em.close();
            em1.close();
        } catch (Exception e11) {
            log.log(Priority.ERROR, e11);
        }
    }

    private static void loadAttr() {
//        try {
//            String nextString;
//            long i = 0;
//            long err_line = 0;
//            TUsers user;
//            EntityManager em = Persistence.createEntityManagerFactory("rti_userLoader_JPA").createEntityManager();
//            StringBuilder temp = new StringBuilder();
//
//            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)))) {
//                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));
//
//                while ((nextString = bReader.readLine()) != null) {
//                    long rez = (long) i % commit_count;
//                    err_line++;
//                    //rez = (long) Math.floor(i / 5);
//                    if (rez == 0) {
//                        log.info("commit transaction i=>" + i);
//                        if (em.getTransaction().isActive()) {
//                            try {
//                                em.getTransaction().commit();
//                            } catch (Exception e3) {
//                                log.log(Priority.ERROR, e3);
//                                temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
//                                bWriter.write(temp.toString() + "\n");
//                                bWriter.flush();
//                                temp = new StringBuilder();
//                                err_line = 0;
//                                if (em.getTransaction().isActive()) {
//                                    em.getTransaction().rollback();
//                                    em.getTransaction().begin();
//                                }
//                                i++;
//                            }
//                        }
//                        err_line = 0;
//                        temp.delete(0, Integer.MAX_VALUE);
//                        em.getTransaction().begin();
//                    }
//                    temp.append(nextString).append("\n");
//                    String[] arr = nextString.split(";", -1);
//                    TUsersDAO userDAO = new TUsersDAO(em);
//                    user = userDAO.getItemByName(arr[3], "TUsers.findByUsername");
//                    // Добавляем аттрибуты
//                    TUserAttribute attr = new TUserAttribute();
//                    attr.setUserId(user);
//                    attr.setName("id_app_1");
//                    attr.setValue(arr[0]);
//                    attr.setVisibleFlag(true);
//                    try {
//                        em.merge(attr);
//                    } catch (Exception e199) {
//                        log.log(Priority.ERROR, e199);
//                        temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
//                        bWriter.write(temp.toString() + "\n");
//                        bWriter.flush();
//                        temp = new StringBuilder();
//                        err_line = 0;
//                        if (em.getTransaction().isActive()) {
//                            em.getTransaction().rollback();
//                            em.getTransaction().begin();
//                        }
//                        i++;
//                    }
//                    i++;
//                }
//            } catch (Exception e) {
//                log.log(Priority.ERROR, e);
//            }
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().commit();
//            }
//            em.close();
//        } catch (Exception e99) {
//            log.log(Priority.ERROR, e99);
//        }
    }

    private static void loadIdentity() {
        try {

        } catch (Exception e) {
            log.log(Priority.ERROR, e);
        }
    }

    private static String formatPhone(String phone) {
        String[] s2 = phone.split("\\D+");
        StringBuilder phone_temp = new StringBuilder();
        for (String str : s2) {
            phone_temp.append(str);
        }
        return phone_temp.toString();
    }
}
