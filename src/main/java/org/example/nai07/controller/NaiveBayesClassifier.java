package org.example.nai07.controller;

import org.example.nai07.enums.*;
import org.example.nai07.model.DataSet;
import org.example.nai07.model.DataType;
import org.example.nai07.services.FileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class NaiveBayesClassifier {
    private final FileService fileService;
    private final Map<DataType, Integer> probabilitiesYes;
    private final Map<DataType, Integer> probabilitiesNo;
    private int playYesTotal = 0;
    private int playNoTotal = 0;
    private boolean applySmoothingAll = false;

    public NaiveBayesClassifier(FileService fileService,
                                @Qualifier("yesMap") Map<DataType, Integer> probabilitiesYes,
                                @Qualifier("noMap") Map<DataType, Integer> probabilitiesNo){
        this.fileService = fileService;
        this.probabilitiesYes = probabilitiesYes;
        this.probabilitiesNo = probabilitiesNo;
    }

    public void calculateProbabilities(){
        List<DataSet> temp = fileService.getRawDataSetRepository().getDataSetList();
        List<DataType> attributes;

        for(DataSet dataSet : temp){
            attributes = List.of(
                    dataSet.getOutlook(),
                    dataSet.getTemperature(),
                    dataSet.getHumidity(),
                    dataSet.getWindy()
            );
            if (dataSet.isPlay()) {
                playYesTotal++;
                for(DataType dt : attributes){
                    putToMap(probabilitiesYes,dt);
                }
            } else {
                playNoTotal++;
                for(DataType dt : attributes){
                    putToMap(probabilitiesNo,dt);
                }
            }
        }
    }

    public void putToMap(Map<DataType, Integer> map, DataType dataType){
        if(map.containsKey(dataType)) {
            map.put(dataType, map.get(dataType) + 1);
        } else {
            map.put(dataType, 1);
        }
    }

    public boolean predict(DataSet dataSet){
        double resYes = getProbability(probabilitiesYes,dataSet,playYesTotal);
        double resNo = getProbability(probabilitiesNo,dataSet,playNoTotal);

        return resYes > resNo;
    }

    public double getProbability(Map<DataType, Integer> map,
                                 DataSet dataSet,
                                 int total){
        double outlook = simpleSmoothing(
                map.getOrDefault(dataSet.getOutlook(),0),
                total,
                Outlook.values().length
        );

        double temperature = simpleSmoothing(
                map.getOrDefault(dataSet.getTemperature(),0),
                total,
                Temperature.values().length
        );

        double humidity = simpleSmoothing(
                map.getOrDefault(dataSet.getHumidity(),0),
                total,
                Humidity.values().length
        );

        double windy = simpleSmoothing(
                map.getOrDefault(dataSet.getWindy(),0),
                total,
                Windy.values().length
        );

        double play = (double)total/(playYesTotal+playNoTotal);

        return multiplier(
                outlook,
                temperature,
                humidity,
                windy,
                play
        );
    }

    public double simpleSmoothing(int valueFromMap,
                                  int totalDenominator,
                                  int denominatorSmoothing){
        if(applySmoothingAll || valueFromMap == 0){
            System.out.println("Smoothing");
            return (double)
                    (valueFromMap + 1)
                    /
                    (totalDenominator+denominatorSmoothing);
        }

        return (double)
                (valueFromMap)
                /
                (totalDenominator);
    }

    public double multiplier(double outlook,
                             double temperature,
                             double humidity,
                             double windy,
                             double play){
        return outlook*temperature*humidity*windy*play;
    }

//   P(play | X = x)  =
//      P((outlook == overcast) | play) = 4/9
//      P(temperature == hot | play) = 2/9
//      P(humidity == normal | play) = 6/9
//      P(windy == FAlSE | play) = 6/9
//      P(play) = 9/14

//   P(!play | X = x)  =
//      P((outlook == overcast) | !play) = 1/8
//      P(temperature == hot | !play) = 2/5
//      P(humidity == normal | !play) = 1/5
//      P(windy == FAlSE | !play) = 2/5
//      P(!play) = 5/14

    public Map<DataType, Integer> getProbabilitiesYes() {
        return probabilitiesYes;
    }

    public Map<DataType, Integer> getProbabilitiesNo() {
        return probabilitiesNo;
    }

    public FileService getFileService(){
        return fileService;
    }

    public void setApplySmoothingAll(boolean applySmoothingAll){
        this.applySmoothingAll = applySmoothingAll;
    }
}
