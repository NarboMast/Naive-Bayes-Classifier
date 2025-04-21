package org.example.nai07.enums;

import org.example.nai07.model.DataType;

public enum Outlook implements DataType {
    SUNNY, OVERCAST, RAINY;

    @Override
    public String getValue() {
        return name();
    }
}
