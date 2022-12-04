public class City {
    String city;
    String lat;
    String lng;
    String country;
    String iso2;
    String admin_name;
    String capital;
    String population;
    String population_proper;

    /**
     * Retorna distância do vertice a outro
     * @param b Vértice de destino
     */
    public double distancia(City b) {
        return  Math.sqrt((Math.pow(Double.parseDouble(lat) - Double.parseDouble(b.lat), 2)) +
                (Math.pow(Double.parseDouble(lng) - Double.parseDouble(b.lng), 2)));
    }
}