/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlemaps;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author asimsinanyuksel
 */
public class GoogleMapsViewer extends JFXPanel {

    private final ArrayList<Marker> markers = new ArrayList<>();
    private WebEngine engine = null;
    private WebView webView = null;
    private final Set<Point2D> coordinateSet = new HashSet<>();
    private final Random random = new Random();

    public void loadMap(String mapLocation) {
        Platform.runLater(() -> {
            webView = new WebView();
            engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);
            setScene(new Scene(webView));
            File f = new File(getClass().getClassLoader().getResource(mapLocation).getFile());
            engine.load(f.toURI().toString());
        });

    }

    public void generateRandomCoordinates(double centerX, double centerY, double radius, int count) {
        double radiusInDegrees = radius / 111320f;
        while (getCoordinateSet().size() <= count) {
            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);
            double new_x = x / Math.cos(Math.toRadians(centerY));

            double foundLatitude;
            double foundLongitude;

            foundLongitude = centerY + y;
            foundLatitude = centerX + new_x;
            getCoordinateSet().add(new Point2D.Double(foundLatitude, foundLongitude));
        }
    }

    public void createMarkers() {
        Object[] mapArray = getCoordinateSet().toArray();
        for (int i = 0; i < mapArray.length; i++) {
            Point2D point = (Point2D) mapArray[i];
            Marker marker = new Marker();
            String info = "Hello! I am marker " + String.valueOf(i + 1);
            marker.setInfo(info);
            marker.setLabel(String.valueOf(i + 1));
            marker.setLat(String.valueOf(point.getX()));
            marker.setLng(String.valueOf(point.getY()));
            markers.add(marker);
        }

    }
    public void centerMap(String lat,String lng, String zoomLevel){
         Platform.runLater(() -> {
            engine.executeScript("setCenter(" + lat + "," + lng + "," +zoomLevel+ ")");
        });
    
    }
    public void addMarkersToMap() {
        for (Marker marker : markers) {
            addMarker(marker);
        }
    }

    public void addMarker(Marker marker) {

        Platform.runLater(() -> {
            engine.executeScript("addMarker(" + marker.getLat() + "," + marker.getLng() + ",'" + marker.getLabel() + "','" + marker.getInfo() + "'" + ")");
        });
    }

    public void removeMarker(String markerLabel) {
         for(Marker marker:markers){
             if(marker.getLabel().equals(markerLabel)){
                 markers.remove(marker);
                 break;
             }                
         }
         Platform.runLater(() -> {
            engine.executeScript("deleteMarker("+markerLabel+")");
        });

    }

    public void removeAllMarkers() {
        coordinateSet.clear();
        markers.clear();
        Platform.runLater(() -> {
            engine.executeScript("deleteMarkers()");
        });
    }

    /**
     * @return the coordinateSet
     */
    public Set<Point2D> getCoordinateSet() {
        return coordinateSet;
    }

}
