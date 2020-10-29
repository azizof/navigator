package de.azizothman.navigator.entities;

public enum Product {

	NATIONAL_EXPRESS_ICE("nationalExpress"), NATIONAL_IC_EC("national"), REGIONAL_EXPRESS_RE("regionalExp"), REGIONAL_RB("regional"),
	SUBURBAHN_SBAHN("suburban"), BUS("bus"), SUBWAY_UBAHN ("subway"), TRAM ("tram");


	private String value;
	 
	Product(String value) {
        this.value = value;
    }
 
    public String getStringValue() {
        return value;
    }
}
