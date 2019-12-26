package ddwucom.mobile.final_project.ma02_20170969;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Arrays;
import java.util.List;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class SearchMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG = "SearchMapActivity";
    final static int PERMISSION_REQ_CODE = 100;
    final static int MAP_LINK_CODE = 200;

    /*UI*/
    private GoogleMap mGoogleMap;
    private MarkerOptions markerOptions;
    private double latitude;
    private double lontitude;

    /*DATA*/
    // TODO: Place 클라이언트 객체 선언
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        // 권한 확인 후 권한이 있을 경우 맵 로딩 실행
        if (checkPermission()) {
            mapLoad();
        }
        // TODO: Places 초기화 및 클라이언트 생성
        Places.initialize(getApplicationContext(), getString(R.string.api_key));
        placesClient = Places.createClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Log.d(TAG, "Map ready");

        // 맵 로딩 후 내 위치 표시 버튼 관련 설정
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(locationButtonClickListener);
        mGoogleMap.setOnMyLocationClickListener(locationClickListener);

        markerOptions = new MarkerOptions();
//        mGeoDataClient = Places.getGeoDataClient(MainActivity.this);

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                // TODO: 마커의 InfoWindow 클릭 시 이벤트 처리
                String placeId = marker.getTag().toString(); // 마커의 setTag()로 저장한 PlaceID 확인
                List<Place.Field> placeFields // 상세정보로 요청할 정보의 유형 지정
                        = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHONE_NUMBER, Place.Field.ADDRESS);
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build(); // 요청 생성

                // 요청 처리 및 요청 성공 / 실패 리스너 지정
                placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Intent intent = new Intent(SearchMapActivity.this, MapDetailActivity.class);
                        intent.putExtra("phone", place.getPhoneNumber());
                        intent.putExtra("name", place.getName());
                        intent.putExtra("address", place.getAddress());

                        Log.i(TAG, "Place found: " + place.getName());
                        Log.i(TAG, "Phone: " + place.getPhoneNumber());
                        Log.i(TAG, "Address: " + place.getAddress());

                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        if(exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            int statusCode = apiException.getStatusCode();
                            Log.e(TAG, "Place not found: " + exception.getMessage());
                        }
                    }
                });
            }
        });
    }

    GoogleMap.OnMyLocationButtonClickListener locationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    Toast.makeText(SearchMapActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            };

    GoogleMap.OnMyLocationClickListener locationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    latitude = location.getLatitude();
                    lontitude = location.getLongitude();
                    String mag = String.format("현재 위치: (%f, %f)", latitude, lontitude);
                    Toast.makeText(SearchMapActivity.this, mag, Toast.LENGTH_LONG).show();
                }
            };



    public void onClick(View v) {
        mGoogleMap.clear();
        switch(v.getId()) {
            case R.id.btnSearch:
                // TODO: 장소 정보 요청;
                EditText editText = findViewById(R.id.etKeyword);
                String keyword = editText.getText().toString();
                if(keyword.equals("cafe")) {
                    new NRPlaces.Builder().listener(placesListener)
                            .key(getString(R.string.api_key))
                            .latlng(latitude, lontitude)
                            .radius(100)
                            .type(PlaceType.CAFE)
                            .build()
                            .execute();
                } else if(keyword.equals("restaurant")) {
                    new NRPlaces.Builder().listener(placesListener)
                            .key(getString(R.string.api_key))
                            .latlng(latitude, lontitude)
                            .radius(100)
                            .type(PlaceType.RESTAURANT)
                            .build()
                            .execute();
                }
                break;
        }
    }

    // TODO: PlaceListener 구현
    PlacesListener placesListener = new PlacesListener() {
        @Override
        public void onPlacesFailure(PlacesException e) {

        }

        @Override
        public void onPlacesStart() {

        }

        @Override
        public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (noman.googleplaces.Place place : places) {
                        markerOptions.title(place.getName());
                        markerOptions.position(new LatLng(place.getLatitude(), place.getLongitude()));
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        // 상수 값 (HUE_AZURE 부분) 을 변경하여 Marker 모양 변경 가능
                        Marker newMarker = mGoogleMap.addMarker(markerOptions);
                        newMarker.setTag(place.getPlaceId());
                        Log.d(TAG, "ID: " + place.getPlaceId());
                    }
                }
            });
        }

        @Override
        public void onPlacesFinished() {
        }
    };

    /*구글맵을 멤버변수로 로딩*/
    private void mapLoad() {
        // SupportMapFragment 는 하위 호환 고려 시 사용, activity_main 의 MapFragment 도 동일한 타입으로 선언
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);      // 매배변수 this: MainActivity 가 OnMapReadyCallback 을 구현하므로
    }


    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQ_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
                mapLoad();
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
