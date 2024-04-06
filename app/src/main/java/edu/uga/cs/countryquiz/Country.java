package edu.uga.cs.countryquiz;

public class Country {
    public final String TAG = "Country";
    private long id;
    private String country;
    private String continent;

    public Country(){
        this.id = -1;
        this.country = null;
        this.continent = null;
    }

    public Country(String country, String continent){
        this.id = -1;
        this.country = country;
        this.continent = continent;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }


}
