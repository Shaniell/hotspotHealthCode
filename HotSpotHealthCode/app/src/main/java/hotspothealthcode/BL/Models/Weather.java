package hotspothealthcode.BL.Models;

/**
 * Created by Giladl on 08/12/2015.
 */
public class Weather
{
    private float temperature;
    private float windDirection;
    private float windSpeed;
    private String type;
    private String typeDescription;

    public Weather(float temperature,
                   float windDirection,
                   float windSpeed,
                   String type,
                   String typeDescription)
    {
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
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
