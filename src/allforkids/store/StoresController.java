/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.store;

import allforkids.store.models.Store;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class StoresController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }    

    @FXML
    private void goToProducts(ActionEvent event) {
        NavigationService.goTo(event, this, "ProductsList.fxml");
    }

    @Override
    public void mapInitialized() {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            stores = Model.fetch(Store.class).all().where("active", "1").execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Marker>  markers = new ArrayList<>();
        for(Store store: stores) {
            Double lat = (Double)store.getAttr("lat");
            Double lon = (Double)store.getAttr("lon");
            LatLong place = new LatLong(lat, lon);
            
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions.position(place);
            Marker storeMarker = new Marker(makerOptions);
            markers.add(storeMarker);
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content((String)store.getAttr("name"));
            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            infoWindow.open(map, storeMarker);
        }

        MapOptions mapOptions = new MapOptions();
        if(!stores.isEmpty()) {
            Double centerLat = (Double)stores.get(0).getAttr("lat");
            Double centerLon = (Double)stores.get(0).getAttr("lon");
            mapOptions.center(new LatLong(centerLat, centerLon))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);
        }
        
        map = mapView.createMap(mapOptions);
        
        for(Marker marker: markers) {
            map.addMarker( marker );
        }
        
    }
}