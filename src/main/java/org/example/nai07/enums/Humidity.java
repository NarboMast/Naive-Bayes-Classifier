package org.example.nai07.enums;

import org.example.nai07.model.DataType;

public enum Humidity implements DataType {
    HIGH, NORMAL;

    @Override
    public String getValue() {
        return name();
    }
}
