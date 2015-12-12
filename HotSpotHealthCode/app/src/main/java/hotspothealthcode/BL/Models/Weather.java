package hotspothealthcode.BL.Models;

/**
 * Created by Giladl on 08/12/2015.
 */
public class Weather
{
    private double temperature;
    private double windDirection;
    private double windSpeed;
    private String type;
    private String typeDescription;

    public Weather(double temperature,
                   double windDirection,
                   double windSpeed,
                   String type,
                   String typeDescription)
    {
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
