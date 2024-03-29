package com.example.filtergputest

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.view.drawToBitmap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    lateinit var applyFilter: Button
    lateinit var filterImage: ImageFilterView

    val workerThread: ExecutorService = Executors.newCachedThreadPool()
    val workerHandler = Handler(Looper.getMainLooper())

    var testBitmap: Bitmap? = null
    var hsv: FloatArray = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applyFilter = findViewById(R.id.button)
        filterImage = findViewById(R.id.imageFilterView)


        //Color.RGBToHSV(206, 43, 55, hsv)

        val hueValues = hsv[0]
        val satValues = hsv[1]
        val hsvValues = hsv[2]

        // RGB to HSV
        //Color.colorToHSV(currentColor, hsv)

        workerHandler.postDelayed({
            testBitmap = filterImage.drawToBitmap(Bitmap.Config.ARGB_8888)

            //HSL Adjust (hue: 0.02, saturation: -0.31, Lightness : -0.17)
            //Please see the manual for more details.
            // val ruleString = "@adjust hsl 0.02 -0.31 -0.17"
            // val ruleString = "@curve R(0, 0)(207, 240)(255, 255)"
            //val ruleString = "@curve RGB(0, 0)(177, 166)(255, 255)R(0, 0)(52, 96)(224, 255)(255, 255)"
            // val ruleString = "@adjust saturation 0.7 @pixblend screen 1 0.243 0.69 1 30"
            //val ruleString = "@adjust saturation 2.0"
            //val ruleString = "@adjust saturation 0.6"//saturation is the RGB color Value in the Picture pixel
            //val ruleString = MyCustomFilter.EFFECT_CONFIGS[103]

            //val ruleString = "@adjust hsv -1 -1 -1 -1 -1 -1"
            val ruleString = "#unpack @style emboss 1 2 2 "

            //testBitmap = CGENativeLibrary.filterImage_MultipleEffects(testBitmap, ruleString, 1.0f)
            // testBitmap = MyCustomFilter.applyHueFilter(testBitmap,3)
            // testBitmap = MyCustomFilter.applySaturationFilter(testBitmap,3)
            // testBitmap = MyCustomFilter.applyBlackFilter(testBitmap)
            //testBitmap = MyCustomFilter.ImageProcessor.tintImage(testBitmap,50)
            //testBitmap = MyCustomFilter.ImageProcessor2(testBitmap).image
            //testBitmap = MyCustomFilter.flip(testBitmap,2)
            //testBitmap = MyCustomFilter.roundCorner(testBitmap,50f)
            //testBitmap = MyCustomFilter.boost(testBitmap,3,40f)
            //testBitmap = MyCustomFilter.engrave(testBitmap)
            //testBitmap = MyCustomFilter.emboss(testBitmap)
            //testBitmap = MyCustomFilter.smooth(testBitmap,10.0)
            //testBitmap = MyCustomFilter.applyMeanRemoval(testBitmap)
            //testBitmap = MyCustomFilter.sharpen(testBitmap,2.0)
            //testBitmap = MyCustomFilter.applyGaussianBlur(testBitmap)
            //testBitmap = MyCustomFilter.doBrightness(testBitmap,50)
            //testBitmap = MyCustomFilter.rotate(testBitmap,270f)
            //testBitmap = MyCustomFilter.createContrast(testBitmap,50.0)
            //testBitmap = MyCustomFilter.decreaseColorDepth(testBitmap,32)
            //testBitmap = MyCustomFilter.createSepiaToningEffect(testBitmap,128,200.0,200.0,0.0)
            //testBitmap = MyCustomFilter.doColorFilter(testBitmap,50.0,50.0,50.0)
            //testBitmap = MyCustomFilter.doGamma(testBitmap,1.8,1.8,1.8)
            //testBitmap = MyCustomFilter.doGreyscale(testBitmap)
            //testBitmap = MyCustomFilter.doInvert(testBitmap)
            testBitmap = MyCustomFilter.doHighlightImage(testBitmap)

        }, 500)


        //Note ! This Web convert rgb values into hsv..
        // https://www.rapidtables.com/convert/color/rgb-to-hsv.html

        /* Black	#000000	(0,0,0)	(0°,0%,0%)
         White	#FFFFFF	(255,255,255)	(0°,0%,100%)
         Red	#FF0000	(255,0,0)	(0°,100%,100%)
         Lime	#00FF00	(0,255,0)	(120°,100%,100%)
         Blue	#0000FF	(0,0,255)	(240°,100%,100%)
         Yellow	#FFFF00	(255,255,0)	(60°,100%,100%)
         Cyan	#00FFFF	(0,255,255)	(180°,100%,100%)
         Magenta	#FF00FF	(255,0,255)	(300°,100%,100%)
         Silver	#BFBFBF	(191,191,191)	(0°,0%,75%)
         Gray	#808080	(128,128,128)	(0°,0%,50%)
         Maroon	#800000	(128,0,0)	(0°,100%,50%)
         Olive	#808000	(128,128,0)	(60°,100%,50%)
         Green	#008000	(0,128,0)	(120°,100%,50%)
         Purple	#800080	(128,0,128)	(300°,100%,50%)
         Teal	#008080	(0,128,128)	(180°,100%,50%)
         Navy	#000080	(0,0,128)	(240°,100%,50%)*/

        val hsv = floatArrayOf(160f, 1f, 0.62f)
        rgb_to_hsv(255.0, 38.0, 68.0)

        applyFilter.setOnClickListener {
            //applyAdjustment()

            //filterImage.setColorFilter(Color.HSVToColor(hsv), PorterDuff.Mode.OVERLAY)

            filterImage.setImageBitmap(testBitmap)
        }

    }

    fun rgb_to_hsv(r: Double, g: Double, b: Double) {

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        var r = r
        var g = g
        var b = b
        r /= 255.0
        g /= 255.0
        b /= 255.0

        // h, s, v = hue, saturation, value
        val cmax = r.coerceAtLeast(g.coerceAtLeast(b)) // maximum of r, g, b
        val cmin = r.coerceAtMost(g.coerceAtMost(b)) // minimum of r, g, b
        val diff = cmax - cmin // diff of cmax and cmin.
        var h = -1.0
        var s = -1.0

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin) h = 0.0 else if (cmax == r) h =
            (60 * ((g - b) / diff) + 360) % 360 else if (cmax == g) h =
            (60 * ((b - r) / diff) + 120) % 360 else if (cmax == b) h =
            (60 * ((r - g) / diff) + 240) % 360

        // if cmax equal zero
        s = if (cmax == 0.0) 0.0 else diff / cmax * 100

        // compute v
        val v = cmax * 100
        Log.d("SHV_Values", "$h $s $v")
        println("($h $s $v)")
    }

    private fun applyAdjustment() {
        filterImage.brightness = 1.5f
        filterImage.contrast = 1.5f
        filterImage.saturation = 2.0f
        filterImage.warmth = 0.5f
    }
}