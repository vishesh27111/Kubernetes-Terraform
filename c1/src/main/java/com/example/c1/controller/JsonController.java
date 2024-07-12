package com.example.c1.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.beans.factory.annotation.Value;
import com.example.c1.model.JsonModel;
import com.example.c1.model.Error;
import com.example.c1.model.Input;
import com.example.c1.model.InvalidCSV;
import com.example.c1.model.Success;
// import lombok.Value;

@RestController
@CrossOrigin(origins = "*")
public class JsonController {

    Error error;
    Success success;
    InvalidCSV csv_error;

    // @Value("${app2.base-url}")
    // private String app2BaseUrl;

    @Autowired
    RestTemplate restTemplate;

    

    @PostMapping(path="/store-file")
    public ResponseEntity<Object> storeFile(@RequestBody JsonModel input) throws IOException{

        error = new Error();
        success = new Success();
        String filename = input.getFile();

        if(filename==null || filename.length()==0 || filename.isEmpty()){
            error.setError("Invalid JSON input.");
            error.setFile(filename);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        else{
            File data = new File(new File("/home/Vishesh_PV_dir"), filename);

            try(FileWriter writer = new FileWriter(data)){
                String[] lines = input.getData().split("\n");
                for (String line : lines) {
                    writer.write(line + "\n");
                }
                writer.close();
                success.setFile(filename);
                success.setMessage("Success.");
                return new ResponseEntity<>(success, HttpStatus.OK);
            }catch (IOException e) {
                System.out.println(e.getMessage());
                error.setError("Error while storing the file to the storage.");
                error.setFile(filename);
                return new ResponseEntity<>(error, HttpStatus.OK);
            }
       }
    }   

    @PostMapping(path="/calculate")
    public ResponseEntity<Object> validateJson(@RequestBody Input input){

        error = new Error();
        success = new Success();
        String filename = input.getFile();

        if(filename==null || filename.length()==0 || filename.isEmpty()){
        error.setError("Invalid JSON input.");
        error.setFile(filename);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
       }
       else{
        File data = new File(new File("/home/Vishesh_PV_dir"), filename);

        if(!data.exists()){
            error.setError("File not found.");
            error.setFile(filename);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        else{  
            final String apiUrl = "http://35.232.64.210/sum";
            // final String apiUrl = "http://localhost:8080/sum";

            String abc = apiUrl;
            
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Input> requestEntity = new HttpEntity<>(input, headers);
            ResponseEntity<Object> response ;
            try
            {
                response = restTemplate.postForEntity(
                apiUrl,
                requestEntity,
                Object.class
            );

            }catch(InternalServerError e){
                csv_error = new InvalidCSV();
                csv_error.setError("Input file not in CSV format.");
                csv_error.setFile(input.getFile());

                return new ResponseEntity<>(csv_error,HttpStatus.OK);

            }

        return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
        }
       }

    }
}