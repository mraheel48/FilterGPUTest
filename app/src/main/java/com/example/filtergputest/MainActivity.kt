package com.example.filtergputest

import android.R.attr
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.view.drawToBitmap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.R.attr.src

import org.wysaid.nativePort.CGENativeLibrary




class MainActivity : AppCompatActivity() {

    lateinit var applyFilter: Button
    lateinit var filterImage: ImageFilterView

    val workerThread: ExecutorService = Executors.newCachedThreadPool()
    val workerHandler = Handler(Looper.getMainLooper())

    var testBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applyFilter = findViewById(R.id.button)
        filterImage = findViewById(R.id.imageFilterView)

        workerHandler.postDelayed({
            testBitmap = filterImage.drawToBitmap(Bitmap.Config.ARGB_8888)

            //HSL Adjust (hue: 0.02, saturation: -0.31, Lightness : -0.17)
            //Please see the manual for more details.
            val ruleString = "@adjust hsl 0.0 -0.31 -0.5"

            testBitmap = CGENativeLibrary.filterImage_MultipleEffects(testBitmap, ruleString, 1.0f)

        },1000)



        applyFilter.setOnClickListener {
            applyAdjustment()

           // filterImage.setImageBitmap(testBitmap)
        }

    }

    private fun applyAdjustment() {
        filterImage.brightness = 1.5f
        filterImage.contrast = 1.5f
        filterImage.saturation = 2.0f
        filterImage.warmth = 0.5f
    }
}