package org.example.nai07.repository;

import org.example.nai07.model.DataSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RawDataSetRepository {
    private final List<DataSet> dataSetList;

    public RawDataSetRepository(List<DataSet> dataSetList) {
        this.dataSetList = dataSetList;
    }

    public void addDataSet(DataSet dataSet){
        dataSetList.add(dataSet);
    }

    public List<DataSet> getDataSetList() {
        return dataSetList;
    }
}
