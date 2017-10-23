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
    private static Logger log = Logger.getLogger(loader.class.getName());

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

                BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename_in)));
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename_out));

                String nextString;
                while ((nextString = bReader.readLine()) != null) {
                    try {
                        em.getTransaction().begin();
                        String[] arr = nextString.split(";", -1);
                        //log.info(Arrays.toString(arr));
                        user = new TUsers();
                        user.setUsername(arr[3]);
                        user.setFirstname(arr[3]);
                        user.setLastname(arr[3]);
                        user.setThirdname(arr[3]);
                        user.setPassword(arr[2]);
                        user.setSalt(arr[7]);
                        user.setPhone(formatPhone(arr[5]));
                        user.setEmail(arr[4]);
                        user.setCreateDate(new Date());
                        user.setUserRegion(23);
                        user.setHashType("md5");

                        em.merge(user);
                        em.getTransaction().commit();
                    } catch (Exception e1) {
                        log.log(Priority.ERROR, e1);
                        log.error("Ошибка => " + nextString);
                        bWriter.write(nextString+"\n");
                        bWriter.flush();
                    }
                    //bReader.close();                    
                    //bWriter.close();
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
