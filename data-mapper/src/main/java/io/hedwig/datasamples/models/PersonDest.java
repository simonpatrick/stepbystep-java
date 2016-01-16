package io.hedwig.datasamples.models;

public class PersonDest {

    private String sirName;
    private String givenName;

    public String getSirName() {
        return sirName;
    }

    public void setSirName(String sirName) {
        this.sirName = sirName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @Override
    public String toString() {
        return "PersonDest{" +
                "givenName='" + givenName + '\'' +
                ", sirName='" + sirName + '\'' +
                '}';
    }
}