package tn.esprit.esprithub.entities;

public class DistanceCalculator {
    public double calculateDistance(Location point1, Location point2) {
        double lat1 = Math.toRadians(point1.getLat());
        double lon1 = Math.toRadians(point1.getLng());
        double lat2 = Math.toRadians(point2.getLat());
        double lon2 = Math.toRadians(point2.getLng());

        // Formule de Haversine
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radius = 6371; // Rayon de la Terre en kilomètres
        return radius * c;
    }
}
