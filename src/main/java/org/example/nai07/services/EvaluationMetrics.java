package org.example.nai07.services;

import org.springframework.stereotype.Service;

@Service
public class EvaluationMetrics {
    public double accuracy(int totalCorrect, int totalObservation){
        return (double) totalCorrect / totalObservation;
    }

//    Take one side of an observations and do the same as accuracy
    public double precision(int totalCorrectB, int totalObservationsB){
        return accuracy(totalCorrectB, totalObservationsB);
    }

    public double recall(int totalCorrectB, int totalActualB){
        return accuracy(totalCorrectB, totalActualB);
    }

    public double fMeasure(double precision, double recall){
        return 2 * (double)
                (precision * recall)
                /
                (precision + recall);
    }
}
