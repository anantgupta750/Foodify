package com.food.factory.User

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.food.factory.MainActivity5
import com.food.factory.R
import com.food.factory.databinding.ActivityBarCodeScannerBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.color.utilities.Score.score
import java.io.IOException


class BarCodeScanner : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityBarCodeScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBarCodeScannerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


//        setContentView(R.layout.activity_bar_code_scanner)
        if (ContextCompat.checkSelfPermission(
                this@BarCodeScanner, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@BarCodeScanner, R.anim.scanner_animination)
        binding.barcodeLine.startAnimation(aniSlide)
    }


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
//                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
//                    .show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue


                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    runOnUiThread {
                        cameraSource.stop()
//                        Toast.makeText(this@BarCodeScanner, "value- $scannedValue", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@BarCodeScanner, SubList::class.java)
                        //put extras
                        intent.putExtra("scannedValue", scannedValue)
//                        intent.putExtra("empName", empList[position].hotelName)
//                        intent.putExtra("empAge", empList[position].hotelAge)
//                        intent.putExtra("empSalary", empList[position].hotelSalary)
                        startActivity(intent)
                        finish()
                    }
                }else
                {

                    runOnUiThread {
                        val toast = Toast.makeText(this@BarCodeScanner, "GAME OVER!\nScore:", Toast.LENGTH_SHORT)
//                        toast.show()
                    }
                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@BarCodeScanner,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}