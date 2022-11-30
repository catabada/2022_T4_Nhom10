package entity.weather;

import constant.StatusData;

import java.io.Serializable;
import java.util.Objects;

public class Weather implements Serializable {
    private int id;
    private String naturalKey;
    private int dateTimeId;
    private String time;
    private int provinceId;
    private int temp;
    private String cloudDescription;
    private int minTemp;
    private int maxTemp;
    private double humidity;
    private double vision;
    private double windSpd;
    private double uvIndex;
    private String atmosphereQuality;
    private StatusData status;
    private String expiredDate;

    public Weather() {
    }

    public Weather(int id, String naturalKey, int dateTimeId, String time, int provinceId, int temp, String cloudDescription, int minTemp, int maxTemp, double humidity, double vision, double windSpd, double uvIndex, String atmosphereQuality, StatusData status, String expiredDate) {
        this.id = id;
        this.naturalKey = naturalKey;
        this.dateTimeId = dateTimeId;
        this.time = time;
        this.provinceId = provinceId;
        this.temp = temp;
        this.cloudDescription = cloudDescription;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.vision = vision;
        this.windSpd = windSpd;
        this.uvIndex = uvIndex;
        this.atmosphereQuality = atmosphereQuality;
        this.status = status;
        this.expiredDate = expiredDate;
    }

    public Weather(String naturalKey, int dateTimeId, String time, int provinceId, int temp, String cloudDescription, int minTemp, int maxTemp, double humidity, double vision, double windSpd, double uvIndex, String atmosphereQuality) {
        this.naturalKey = naturalKey;
        this.dateTimeId = dateTimeId;
        this.time = time;
        this.provinceId = provinceId;
        this.temp = temp;
        this.cloudDescription = cloudDescription;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.vision = vision;
        this.windSpd = windSpd;
        this.uvIndex = uvIndex;
        this.atmosphereQuality = atmosphereQuality;
    }

    public Weather(int id, String naturalKey, int dateTimeId, String time, int provinceId, int temp, String cloudDescription, int minTemp, int maxTemp, double humidity, double vision, double windSpd, double uvIndex, String atmosphereQuality) {
        this.id = id;
        this.naturalKey = naturalKey;
        this.dateTimeId = dateTimeId;
        this.time = time;
        this.provinceId = provinceId;
        this.temp = temp;
        this.cloudDescription = cloudDescription;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.vision = vision;
        this.windSpd = windSpd;
        this.uvIndex = uvIndex;
        this.atmosphereQuality = atmosphereQuality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaturalKey() {
        return naturalKey;
    }

    public void setNaturalKey(String naturalKey) {
        this.naturalKey = naturalKey;
    }

    public int getDateTimeId() {
        return dateTimeId;
    }

    public void setDateTimeId(int dateTimeId) {
        this.dateTimeId = dateTimeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getCloudDescription() {
        return cloudDescription;
    }

    public void setCloudDescription(String cloudDescription) {
        this.cloudDescription = cloudDescription;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getVision() {
        return vision;
    }

    public void setVision(double vision) {
        this.vision = vision;
    }

    public double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(double windSpd) {
        this.windSpd = windSpd;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getAtmosphereQuality() {
        return atmosphereQuality;
    }

    public void setAtmosphereQuality(String atmosphereQuality) {
        this.atmosphereQuality = atmosphereQuality;
    }

    public StatusData getStatus() {
        return status;
    }

    public void setStatus(StatusData status) {
        this.status = status;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather weather)) return false;
        return provinceId == weather.provinceId
                && temp == weather.temp
                && minTemp == weather.minTemp
                && maxTemp == weather.maxTemp
                && Double.compare(weather.humidity, humidity) == 0
                && Double.compare(weather.vision, vision) == 0
                && Double.compare(weather.windSpd, windSpd) == 0
                && Double.compare(weather.uvIndex, uvIndex) == 0
                && Objects.equals(naturalKey, weather.naturalKey)
                && Objects.equals(cloudDescription, weather.cloudDescription)
                && Objects.equals(atmosphereQuality, weather.atmosphereQuality);
    }

}
