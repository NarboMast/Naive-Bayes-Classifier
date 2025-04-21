package org.example.nai07.enums;

import org.example.nai07.model.DataType;

public enum Windy implements DataType {
    TRUE,FALSE;

    @Override
    public String getValue() {
        return name();
    }
}
