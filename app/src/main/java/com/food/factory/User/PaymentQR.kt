package com.food.factory.User

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.food.factory.R
import com.food.factory.Variable

class PaymentQR : AppCompatActivity() {
    lateinit var qrIV: ImageView
    lateinit var msgEdt: EditText
    var thiscontext: Context? = null

    // on below line we are creating
    // a variable for bitmap
    lateinit var bitmap: Bitmap

    // on below line we are creating
    // a variable for qr encoder.
    lateinit var qrEncoder: QRGEncoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_qr)

        qrIV = findViewById(R.id.imageQR)
        thiscontext = this

        if (Variable.paymentID?.isEmpty() == true) {

            // on below line we are displaying toast message to display enter some text
            Toast.makeText(thiscontext, "Enter your message", Toast.LENGTH_SHORT).show()
        }
        else {
            // on below line we are getting service for window manager
            val windowManager: WindowManager = thiscontext?.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager

            // on below line we are initializing a
            // variable for our default display
            val display: Display = windowManager.defaultDisplay

            // on below line we are creating a variable
            // for point which is use to display in qr code
            val point: Point = Point()
            display.getSize(point)

            // on below line we are getting
            // height and width of our point
            val width = point.x
            val height = point.y

            // on below line we are generating
            // dimensions for width and height
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            // on below line we are initializing our qr encoder
            qrEncoder = QRGEncoder(Variable.paymentID, null, QRGContents.Type.TEXT, dimen)

            // on below line we are running a try
            // and catch block for initializing our bitmap
            try {
                // on below line we are
                // initializing our bitmap
//                    bitmap = qrEncoder.encodeAsBitmap()


                bitmap = qrEncoder.getBitmap();

                // on below line we are setting
                // this bitmap to our image view
                qrIV.setImageBitmap(bitmap)
            } catch (e: Exception) {
                // on below line we
                // are handling exception
                e.printStackTrace()
            }
        }



    }
}