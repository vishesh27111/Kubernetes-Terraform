package com.example.c2.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Service {

    public static int Total_Amount(String fname, String productname) throws FileNotFoundException, IOException{

        File filename = new File(new File("/home/Vishesh_PV_dir"), fname);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int total = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {

                String product = line.split(",")[0].trim();
                int amount = Integer.parseInt(line.split(",")[1].trim());

                if(product.equals(productname)){
                    total = total + amount;
                }
            }
            return total;
        }
        catch(Exception e){
            throw e;
        }
        
    }
}
