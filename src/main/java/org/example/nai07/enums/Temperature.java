package org.example.nai07.enums;

import org.example.nai07.model.DataType;

public enum Temperature implements DataType {
    HOT, MILD, COOL;

    @Override
    public String getValue() {
        return name();
    }
}
