package com.example.project;

public class AppSettings {

    private int volume;
    private int brightness;
    private boolean eula;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isEula() {
        return eula;
    }

    public void setEula(boolean eula) {
        this.eula = eula;
    }

}
