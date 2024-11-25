package com.example.mobiledevproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class DeliveryTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView statusText, etaText, totalPaidText;
    private ImageView qrCodeImageView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Custom driver's location (latitude, longitude)
    private double driverLat = 37.7749;  // Example: San Francisco latitude
    private double driverLng = -122.4194; // Example: San Francisco longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_tracking);

        statusText = findViewById(R.id.status_text);
        etaText = findViewById(R.id.eta_text);
        totalPaidText = findViewById(R.id.total_paid_text);
        qrCodeImageView = findViewById(R.id.qr_code_image);

        mapView = findViewById(R.id.map_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get total paid from the Intent that started this activity
        double totalPaid = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);

        // Set initial text
        statusText.setText("Your Driver Will Be Arriving Soon");
        etaText.setText("Your Estimated Arrival is 13 minutes");
        totalPaidText.setText("Total Paid: $" + String.format("%.2f", totalPaid));

        // Generate and display the QR code for the order
        String qrCodeData = "OrderID:123456789;TotalPaid:$" + String.format("%.2f", totalPaid) + ";ETA:13 minutes";
        generateQRCode(qrCodeData);

        // Initialize map
        Bundle mapViewBundle = savedInstanceState != null ? savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY) : null;
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableLocationTracking();
        }
    }

    @SuppressLint("MissingPermission")
    private void enableLocationTracking() {
        if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // User's current location
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));

                        // Move the camera to the user's location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                        // Driver's location (editable)
                        LatLng driverLocation = new LatLng(driverLat, driverLng);  // Custom editable location
                        googleMap.addMarker(new MarkerOptions().position(driverLocation).title("Your Driver's Location"));
                    }
                }
            });
        }
    }

    private void generateQRCode(String data) {
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 400, 400, hintMap);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }

            android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = new Bundle();
        mapView.onSaveInstanceState(mapViewBundle);
        outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableLocationTracking();
        }
    }
}
