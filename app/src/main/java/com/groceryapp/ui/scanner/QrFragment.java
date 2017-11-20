package com.groceryapp.ui.scanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.groceryapp.R;
import com.groceryapp.ui.admin.AddItem;
import com.groceryapp.ui.admin.AdminHome;
import com.groceryapp.ui.home.ShellActivity;
import com.groceryapp.ui.shopping_cart.ItemDetail;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class QrFragment extends Fragment
        implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private LinearLayout mainContainer;
    private AdminHome admin;
    private ShellActivity shell;

    public QrFragment() {
        // Required empty public constructor
    }

    public static QrFragment getInstance() {
        QrFragment fragment = new QrFragment();
        return fragment;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getActivity(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        mainContainer = view.findViewById(R.id.container);
        mScannerView = new ZXingScannerView(getActivity());
        mainContainer.addView(mScannerView);
        Activity activity = getActivity();
        if (activity instanceof AdminHome) {
            admin = (AdminHome) activity;

        }

        if (activity instanceof ShellActivity) {
            shell = (ShellActivity) activity;
            shell.setToolbarTitle("Scanner");
        }
        return view;
    }

    @Override
    public void handleResult(Result result) {
        final String result1 = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (admin != null) {
                admin.loadFragment(result1, 0);
            }
            if (shell != null) {
                shell.loadFragment(result1);
            }

        });
        builder.setNeutralButton("CANCEL", (dialog, which) -> {
            mScannerView.resumeCameraPreview(QrFragment.this);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result1));
//                startActivity(browserIntent);
        });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(camId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
        mScannerView = null;
    }
}


