package org.example.nai07.services;

import org.example.nai07.enums.*;
import org.example.nai07.model.DataSet;
import org.example.nai07.repository.RawDataSetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileService {
    private static final String DELIMITER = ",";

    @Value("${outGame}")
    private String filename;
    private final RawDataSetRepository rawDataSetRepository;

    public FileService(RawDataSetRepository rawDataSetRepository){
        this.rawDataSetRepository = rawDataSetRepository;
    }

    public void readFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            String[] values;
            while ((line = br.readLine()) != null) {
                values = line.toUpperCase().split(DELIMITER);
                rawDataSetRepository.addDataSet(
                        new DataSet(
                                Outlook.valueOf(values[0]),
                                Temperature.valueOf(values[1]),
                                Humidity.valueOf(values[2]),
                                Windy.valueOf(values[3]),
                                stringToBool(values[4])
                                )
                );
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    public RawDataSetRepository getRawDataSetRepository() {
        return rawDataSetRepository;
    }

    public boolean stringToBool(String str){
        return str.equals("YES");
    }
}
