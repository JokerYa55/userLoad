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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import rtk.DAO.TUsersDAO;
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
            TUsers user;
            EntityManager em = Persistence.createEntityManagerFactory("rti_userLoader_JPA").createEntityManager();
            EntityManager em1 = Persistence.createEntityManagerFactory("rti_userLoader_KK_JPA").createEntityManager();
            StringBuilder temp = new StringBuilder();

            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)))) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));
                while ((nextString = bReader.readLine()) != null) {
                    try {
                        long rez = (long) i % commit_count;
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
                        }
                        temp.append(nextString).append("\n");
                        String[] arr = nextString.split(";", -1);
                        //log.info(Arrays.toString(arr));
                        user = new TUsers();
                        user.setUsername(arr[3]);
                        user.setFirstname(arr[4]);
                        user.setLastname(arr[5]);
                        user.setThirdname(arr[6]);
                        user.setPassword(arr[7]);
                        user.setSalt(arr[8]);
                        user.setPhone(formatPhone(arr[2]));
                        user.setEmail(arr[1]);
                        user.setCreateDate(new Date());
                        user.setUserRegion(Integer.parseInt(arr[9]));
                        user.setEnabled(true);
                        user.setHashType("sha1");
                        user.setDescription(arr[0]);

                        TUserAttribute attr = new TUserAttribute();
                        attr.setUserId(user);
                        attr.setName("id_app_1");
                        attr.setValue(arr[0]);
                        attr.setVisibleFlag(true);
                        
                        Collection<TUserAttribute> attrList = new LinkedList();
                        attrList.add(attr);
                        
                        user.setTUserAttributeCollection(attrList);

                        try {
                            em.merge(user);
                            // Добавляем ссылку на провайдер
                            
                            BrokerLinkPK pk = new BrokerLinkPK(identityProvider, "f:" + storageProviderID + ":" + user.getId().toString());
                            BrokerLink link = new BrokerLink();
                            link.setBrokerUsername(user.getUsername());
                            link.setStorageProviderId(storageProviderID);
                            link.setBrokerLinkPK(pk);
                            link.setRealmId(realmID);
                            link.setBrokerUserId(arr[0]);

                            em1.merge(link);

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
        try {
            String nextString;
            long i = 0;
            long err_line = 0;
            TUsers user;
            EntityManager em = Persistence.createEntityManagerFactory("rti_userLoader_JPA").createEntityManager();
            StringBuilder temp = new StringBuilder();

            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)))) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));

                while ((nextString = bReader.readLine()) != null) {
                    long rez = (long) i % commit_count;
                    err_line++;
                    //rez = (long) Math.floor(i / 5);
                    if (rez == 0) {
                        log.info("commit transaction i=>" + i);
                        if (em.getTransaction().isActive()) {
                            try {
                                em.getTransaction().commit();
                            } catch (Exception e3) {
                                log.log(Priority.ERROR, e3);
                                temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
                                bWriter.write(temp.toString() + "\n");
                                bWriter.flush();
                                temp = new StringBuilder();
                                err_line = 0;
                                if (em.getTransaction().isActive()) {
                                    em.getTransaction().rollback();
                                    em.getTransaction().begin();
                                }
                                i++;
                            }
                        }
                        err_line = 0;
                        temp.delete(0, Integer.MAX_VALUE);
                        em.getTransaction().begin();
                    }
                    temp.append(nextString).append("\n");
                    String[] arr = nextString.split(";", -1);
                    TUsersDAO userDAO = new TUsersDAO(em);
                    user = userDAO.getItemByName(arr[3], "TUsers.findByUsername");
                    // Добавляем аттрибуты
                    TUserAttribute attr = new TUserAttribute();
                    attr.setUserId(user);
                    attr.setName("id_app_1");
                    attr.setValue(arr[0]);
                    attr.setVisibleFlag(true);
                    try {
                        em.merge(attr);
                    } catch (Exception e199) {
                        log.log(Priority.ERROR, e199);
                        temp.append("-------------------------------- err_line => ").append(err_line).append(" fileLine => ").append(i).append(" ------------------------------------------\n");
                        bWriter.write(temp.toString() + "\n");
                        bWriter.flush();
                        temp = new StringBuilder();
                        err_line = 0;
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            em.getTransaction().begin();
                        }
                        i++;
                    }
                    i++;
                }
            } catch (Exception e) {
                log.log(Priority.ERROR, e);
            }
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        } catch (Exception e99) {
            log.log(Priority.ERROR, e99);
        }
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
