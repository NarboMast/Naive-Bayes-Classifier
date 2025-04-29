package org.example.nai07;

import org.example.nai07.controller.NaiveBayesClassifier;
import org.example.nai07.enums.Humidity;
import org.example.nai07.enums.Outlook;
import org.example.nai07.enums.Temperature;
import org.example.nai07.enums.Windy;
import org.example.nai07.model.DataSet;
import org.example.nai07.services.EvaluationMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.xml.crypto.Data;
import java.util.List;

@SpringBootApplication
public class Nai07Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Nai07Application.class, args);

        NaiveBayesClassifier naiveBayesClassifier = context.getBean(NaiveBayesClassifier.class);
        naiveBayesClassifier.getFileService().readFile();
        naiveBayesClassifier.calculateProbabilities();

        //False
        DataSet dt = new DataSet(Outlook.RAINY, Temperature.MILD, Humidity.HIGH, Windy.TRUE);
        //True
        DataSet dt1 = new DataSet(Outlook.OVERCAST, Temperature.HOT, Humidity.NORMAL, Windy.FALSE);
        //False
        DataSet dt2 = new DataSet(Outlook.SUNNY,Temperature.HOT ,Humidity.HIGH ,Windy.FALSE );

//        naiveBayesClassifier.setApplySmoothingAll(true);

        List<DataSet> testList = List.of(
                dt,
                dt1,
                dt2
        );

        int countTrue = 0;
        for(DataSet d : testList){
            boolean temp = naiveBayesClassifier.predict(d);
            if (temp) countTrue++;
            System.out.println(temp);
        }

        EvaluationMetrics evaluationMetrics = context.getBean(EvaluationMetrics.class);

        double accuracy = evaluationMetrics.accuracy(countTrue,testList.size());
        double precision = evaluationMetrics.precision(countTrue, 1);
        double recall = evaluationMetrics.recall(1,1);

        System.out.println("Accuracy: " + accuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);

        System.out.println("F measure: " + evaluationMetrics.fMeasure(precision, recall));

    }

}
