package com.vogella.android.retrofitgithub.services.servicesList;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerAnchor;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.SingleLayoutBalloonViewAdapter;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResult;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;
import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;
import com.vogella.android.retrofitgithub.common.BackToolbar;

import android.support.v7.widget.Toolbar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ServiceListMain extends AppCompatActivity implements OnMapReadyCallback,
        TomtomMapCallback.OnMapLongClickListener  {

    //fields should be private
    private Toolbar my_toolbar;
    private BackToolbar backToolbar;
    private Button register,menu_list,menu_map;
    private Intent intent,intent1;
    private boolean accpet;
    private ImageView status;
    private TomtomMap tomtomMap;
    private SearchApi searchApi;
    private RoutingApi routingApi;
    private Route route;
    private LatLng departurePosition;
    private LatLng destinationPosition;
    private LatLng wayPointPosition;
    private Icon departureIcon;
    private Icon destinationIcon;
    private Icon marker;
    private ImageButton btnSearch;
    private EditText editTextPois;


    LatLng latLng;




    private final OnMapReadyCallback onMapReadyCallback =
            new OnMapReadyCallback() {
                @Override
                public void onMapReady(TomtomMap map) {
                    //Map is ready here
                    tomtomMap = map;
                    tomtomMap.setMyLocationEnabled(true);
                }
            };





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list_main);
        initTomTomServices();
        initUIViews();
        setupUIViewListeners();
        menu_list = (Button)findViewById(R.id.menu_list);
        menu_map = (Button)findViewById(R.id.menu_map);
        my_toolbar = (Toolbar)findViewById(R.id.my_toolbar);


        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Back");

        my_toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_white_24dp));
        my_toolbar.setNavigationOnClickListener(v -> getApplicationContext().startActivity(new Intent(ServiceListMain.this, ServiceService.class)));

        ListViewAdapter adapter = new ListViewAdapter();
        latLng  = new LatLng(52.064,4.31402);

        departureIcon = Icon.Factory.fromResources(ServiceListMain.this, R.drawable.ic_map_route_departure);
        destinationIcon = Icon.Factory.fromResources(ServiceListMain.this, R.drawable.ic_map_route_destination);
        marker = Icon.Factory.fromResources(ServiceListMain.this, R.drawable.ic_map_route_departure);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.listbar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_list:
                Intent intent = new Intent(ServiceListMain.this, ServiceListview.class);
                startActivity(intent);
                return true;

            case R.id.menu_map:
                Intent intent2 = new Intent(ServiceListMain.this, ServiceListMain.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



   @Override
    //from now about map
   public void onMapReady(@NonNull TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;

        this.tomtomMap.setMyLocationEnabled(true);
        this.tomtomMap.addOnMapLongClickListener(this);
        this.tomtomMap.getMarkerSettings().setMarkersClustering(true);



       MarkerBuilder(latLng,"help","y");

       MarkerBuilder(new LatLng(52.164,4.41402),"hellp","yy");

       MarkerBuilder(new LatLng(52.264,4.51402),"hellplllll","yttttty");

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





    public void OnMapLongClick(@NonNull LatLng latLng){
        if (isDeparturePositionSet() && isDestinationPositionSet()) {
            clearMap();
        } else {
            handleLongClick(latLng);
        }
    }

    private boolean isDestinationPositionSet() {
        return destinationPosition != null;
    }

    private boolean isDeparturePositionSet() {
        return departurePosition != null;
    }


    private void handleApiError(Throwable e) {
        Toast.makeText(ServiceListMain.this, getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
    }


    private void handleLongClick(@NonNull LatLng latLng) {
        searchApi.reverseGeocoding(new ReverseGeocoderSearchQueryBuilder(latLng.getLatitude(), latLng.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ReverseGeocoderSearchResponse>() {
                    @Override
                    public void onSuccess(ReverseGeocoderSearchResponse response) {
                        processResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                    }

                    private void processResponse(ReverseGeocoderSearchResponse response) {
                        if (response.hasResults()) {
                            processFirstResult(response.getAddresses().get(0).getPosition());
                        }
                        else {
                            Toast.makeText(ServiceListMain.this, getString(R.string.geocode_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void processFirstResult(LatLng geocodedPosition) {
                        if (!isDeparturePositionSet()) {
                            setAndDisplayDeparturePosition(geocodedPosition);
                        } else {
                            destinationPosition = geocodedPosition;
                            tomtomMap.removeMarkers();
                            drawRoute(departurePosition, destinationPosition);
                        }
                    }

                    private void setAndDisplayDeparturePosition(LatLng geocodedPosition) {
                        departurePosition = geocodedPosition;
                        createMarkerIfNotPresent(departurePosition, departureIcon);
                    }
                });
    }



    private void clearMap() {
        tomtomMap.clear();
        departurePosition = null;
        destinationPosition = null;
        route = null;
    }
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    private void initTomTomServices(){
        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getAsyncMap(this);
        searchApi = OnlineSearchApi.create(this);
        routingApi = OnlineRoutingApi.create(this);
    }

    private void initUIViews() {



    }
    private void setupUIViewListeners() {


    }
    private RouteQuery createRouteQuery(LatLng start, LatLng stop, LatLng[] wayPoints) {
        return (wayPoints != null) ?
                new RouteQueryBuilder(start, stop).withWayPoints(wayPoints).withRouteType(RouteType.FASTEST) :
                new RouteQueryBuilder(start, stop).withRouteType(RouteType.FASTEST);
    }

    private void drawRoute(LatLng start, LatLng stop) {
        wayPointPosition = null;
        drawRouteWithWayPoints(start, stop, null);
    }

    private void drawRouteWithWayPoints(LatLng start, LatLng stop, LatLng[] wayPoints) {
        RouteQuery routeQuery = createRouteQuery(start, stop, wayPoints);
        routingApi.planRoute(routeQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<RouteResult>() {

                    @Override
                    public void onSuccess(RouteResult routeResult) {
                        displayRoutes(routeResult.getRoutes());
                        tomtomMap.displayRoutesOverview();
                    }
                    private void displayRoutes(List<FullRoute> routes) {
                        for (FullRoute fullRoute : routes) {
                            route = tomtomMap.addRoute(new RouteBuilder(
                                    fullRoute.getCoordinates()).startIcon(departureIcon).endIcon(destinationIcon).isActive(true));
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                       e.printStackTrace();
                    }
                });
    }

    private void createMarkerIfNotPresent(LatLng position, Icon icon) {
        Optional optionalMarker = tomtomMap.findMarkerByPosition(position);

        if (!optionalMarker.isPresent()) {
            tomtomMap.addMarker(new MarkerBuilder(position)
                    .icon(icon));
        }
    }


    private SingleLayoutBalloonViewAdapter createCustomViewAdapter(String title, String des) {
        return new SingleLayoutBalloonViewAdapter(R.layout.marker_custom_balloon) {


            @Override
            public void onBindView(View view, final Marker marker, BaseMarkerBalloon baseMarkerBalloon) {
                Button btnAddWayPoint = view.findViewById(R.id.btn_balloon_waypoint);
                TextView textViewPoiName = view.findViewById(R.id.textview_balloon_poiname);
                TextView textViewPoiAddress = view.findViewById(R.id.textview_balloon_poiaddress);

                //textViewPoiName.setText(title);
                // textViewPoiAddress.setText(des);

                btnAddWayPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                  //  Intent intent = new Intent(ServiceListMain.this,Request_Detail.class);
               //     startActivity(intent);

                    }

                });
            }
        };
    }



    public void MarkerBuilder(LatLng position, String title,String des){

        this.tomtomMap.getMarkerSettings().setMarkerBalloonViewAdapter(createCustomViewAdapter(title,des));
        SimpleMarkerBalloon balloon = new SimpleMarkerBalloon(title);
        MarkerBuilder markerBuilder = new MarkerBuilder(position)
                .icon(Icon.Factory.fromResources(ServiceListMain.this, R.drawable.ic_favourites)).markerBalloon(balloon)
                .tag("more information in tag").iconAnchor(MarkerAnchor.Top)
                .decal(true);

        tomtomMap.addMarker(markerBuilder);

    }

}
