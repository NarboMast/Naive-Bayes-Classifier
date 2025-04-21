package org.example.nai07;

import org.example.nai07.controller.NaiveBayesClassifier;
import org.example.nai07.enums.*;
import org.example.nai07.model.*;
import org.example.nai07.services.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Nai07ApplicationTests {
    @Autowired
    private FileService fileService;

    @Autowired
    private NaiveBayesClassifier naiveBayesClassifier;

    @Test
    void testReadFile() {
        fileService.readFile();
        fileService.getRawDataSetRepository().getDataSetList().forEach(
                System.out::println
        );
    }

    @Test
    void testCalculateProbabilities(){
        fileService.readFile();
        naiveBayesClassifier.calculateProbabilities();
        System.out.println(naiveBayesClassifier.getProbabilitiesYes().hashCode());
        System.out.println(naiveBayesClassifier.getProbabilitiesNo().hashCode());
        for(Map.Entry<DataType, Integer> entry : naiveBayesClassifier.getProbabilitiesYes().entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println();
        for(Map.Entry<DataType, Integer> entry : naiveBayesClassifier.getProbabilitiesNo().entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    void testPredict(){
//        rainy,mild,high,TRUE,no
//        overcast,hot,normal,FALSE,yes

        DataSet dt = new DataSet(Outlook.RAINY, Temperature.MILD, Humidity.HIGH, Windy.TRUE);
        DataSet dt1 = new DataSet(Outlook.OVERCAST, Temperature.HOT, Humidity.NORMAL, Windy.FALSE);
        DataSet dt2 = new DataSet(Outlook.SUNNY,Temperature.HOT ,Humidity.HIGH ,Windy.FALSE );

        fileService.readFile();
        naiveBayesClassifier.calculateProbabilities();

        assertFalse(naiveBayesClassifier.predict(dt));
        assertTrue(naiveBayesClassifier.predict(dt1));
        assertFalse(naiveBayesClassifier.predict(dt2));
    }

    @Test
    void testMultiplier(){
        double outlook = (double)4/9;
        double temperature = (double)2/9;
        double humidity = (double)6/9;
        double windy = (double)6/9;
        double play = (double)9/14;

        double res = naiveBayesClassifier.multiplier(
                outlook,
                temperature,
                humidity,
                windy,
                play
        );

        System.out.println(res);

        outlook = (double)1/8;
        temperature = (double)2/5;
        humidity = (double)1/5;
        windy = (double)2/5;
        play = (double)5/14;

        res = naiveBayesClassifier.multiplier(
                outlook,
                temperature,
                humidity,
                windy,
                play
        );

        System.out.println(res);
    }

}
