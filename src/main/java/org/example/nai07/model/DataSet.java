package org.example.nai07.model;

import org.example.nai07.enums.*;

public class DataSet {
    private final Outlook outlook;
    private final Temperature temperature;
    private final Humidity humidity;
    private final Windy windy;
    private boolean play;

    public DataSet(Outlook outlook,
                   Temperature temperature,
                   Humidity humidity,
                   Windy windy,
                   boolean play) {
        this.outlook = outlook;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windy = windy;
        this.play = play;
    }

    public DataSet(Outlook outlook,
                   Temperature temperature,
                   Humidity humidity,
                   Windy windy) {
        this.outlook = outlook;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windy = windy;
    }

    public Outlook getOutlook() {
        return outlook;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public Windy getWindy() {
        return windy;
    }

    public boolean isPlay() {
        return play;
    }

    @Override
    public String toString(){
        return String.format(
                "[%s, %s, %s, %s, %s]",
                outlook, temperature, humidity, windy, play
        );
    }
}
