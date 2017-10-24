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

/**
 *
 * @author vasil
 */
public class loader {

    /**
     * @param args the command line arguments
     */
    private static final Logger log = Logger.getLogger(loader.class.getName());

    public static void main(String[] args) {
        // TODO code application logic here
        log.info(Arrays.toString(args));
        if (args.length > 0) {
            String filename_in = args[0];
            String filename_out = args[1];
            log.info("filename_in => " + filename_in);
            log.info("filename_out => " + filename_out);
            TUsers user;
            try {
                EntityManager em = Persistence.createEntityManagerFactory("rti_userLoader_JPA").createEntityManager();
                EntityManager em1 = Persistence.createEntityManagerFactory("rti_userLoader_KK_JPA").createEntityManager();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)));
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));

                String nextString;
                long i = 0;
                long err_line = 0;
                StringBuilder temp = new StringBuilder();
                while ((nextString = bReader.readLine()) != null) {
                    try {
                        long rez = (long) i % 100;
                        err_line++;
                        //rez = (long) Math.floor(i / 5);
                        if (rez == 0) {
                            log.info("commit transaction i= " + i);
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
                        user.setHashType("sha1");
                        try {
                            //em.merge(user);
                            // Добавляем ссылку на провайдер
                        } catch (Exception e2) {
                            log.log(Priority.ERROR, e2);
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
                    } catch (IOException e1) {
                        log.log(Priority.ERROR, e1);
                        log.error("Ошибка => " + nextString);
                        e1.printStackTrace();
                    }

                }
                if (em.getTransaction().isActive()) {
                    em.getTransaction().commit();
                }
            } catch (Exception e) {
                log.log(Priority.ERROR, e);
            }
        }
    }

    private static String formatPhone(String phone) {
        String[] s2 = phone.split("\\D+");
        StringBuilder phone_temp = new StringBuilder();
//        if (!phone.contains("+")) {
//            phone_temp.append("+7");
//        }else
//        {
//            phone_temp.append("+");
//        }
        for (String str : s2) {
            phone_temp.append(str);
        }
        return phone_temp.toString();
    }
}
