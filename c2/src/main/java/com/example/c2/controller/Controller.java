package com.example.c2.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.c2.model.Input;
import com.example.c2.model.InvalidCSV;
import com.example.c2.model.Output;
import com.example.c2.service.Service;

@RestController
public class Controller {
    
    InvalidCSV csv_error;
    Output output;
    @PostMapping(path="/sum")
    public ResponseEntity<Object> calculateSum(@RequestBody Input input) throws FileNotFoundException, IOException{

        String filename = input.getFile();

        String f = filename;
        try{
            int sum = Service.Total_Amount(filename,input.getProduct());

            if(sum==0){
                csv_error = new InvalidCSV();
                csv_error.setError("Input file not in CSV format.");
                csv_error.setFile(input.getFile());

                return new ResponseEntity<>(csv_error,HttpStatus.OK);
            }
            output = new Output();
            output.setFile(input.getFile());
            output.setSum(sum);

            return new ResponseEntity<>(output,HttpStatus.OK);
        }
        catch(Exception e){
            csv_error = new InvalidCSV();
            csv_error.setError("Input file not in CSV format.");
            csv_error.setFile(input.getFile());

            return new ResponseEntity<>(csv_error,HttpStatus.OK);
        }    

    }
}