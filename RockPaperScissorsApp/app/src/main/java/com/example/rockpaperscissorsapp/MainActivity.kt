package com.example.rockpaperscissorsapp

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissorsapp.ml.RpsFineTunedModel
import org.tensorflow.lite.support.image.TensorImage
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var loadPictureButton: Button
    lateinit var takePictureButton: Button
    lateinit var resultText: TextView
    val REQUEST_CODE_GALLERY = 100
    val REQUEST_CODE_CAMERA = 200
    lateinit var bitmap: Bitmap

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            resultText.text = ""
            if(permissions.entries.first().value) {
                takePictureButton.setOnClickListener {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    try {
                        startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                    } catch (e: ActivityNotFoundException) {
                        // display error state to the user
                    }
                }
                resultText.append("You can use your camera to take a picture\n")
            } else {
                resultText.append("You need to allow the app to use the camera to take picture.\n")
            }
            if (permissions.entries.last().value) {
                loadPictureButton.setOnClickListener {
                    val gallery = Intent(Intent.ACTION_PICK)
                    gallery.type = "image/*"
                    startActivityForResult(gallery, REQUEST_CODE_GALLERY)
                }
                resultText.append("You can access your gallery to load a picture")
            } else {
                resultText.append("You need to allow the app to access your gallery to load it")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Rock Paper Scissor Classifier"
        imageView = findViewById(R.id.imageView)
        loadPictureButton = findViewById(R.id.loadPictureButton)
        takePictureButton = findViewById(R.id.takePictureButton)
        resultText = findViewById(R.id.resultText)
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GALLERY) {
            val imageUri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CAMERA){
            bitmap = data?.extras?.get("data") as Bitmap
        }
        if (resultCode == Activity.RESULT_OK && (requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_CODE_CAMERA)) {
            bitmap.toSquare()?.let {
                val model = RpsFineTunedModel.newInstance(applicationContext())
                bitmap = it.copy(Bitmap.Config.ARGB_8888, true)
                // Creates inputs for reference.
                val image = TensorImage.fromBitmap(bitmap)

                // Runs model inference and gets result.
                val outputs = model.process(image)
                val probability = outputs.probabilityAsCategoryList

                resultText.text = ""
                resultText.append(probability[0].label + ": " + probability[0].score + "\n")
                resultText.append(probability[1].label + ": " + probability[1].score + "\n")
                resultText.append(probability[2].label + ": " + probability[2].score + "\n")

                // Releases model resources if no longer used.
                model.close()
                imageView.setImageBitmap(it)

            }
        }

    }

    fun Bitmap.toSquare():Bitmap?{
        // get the small side of bitmap
        val side = min(width, height)

        // calculate the x and y offset
        val xOffset = (width - side) /2
        val yOffset = (height - side)/2

        // create a square bitmap
        // a square is closed, two dimensional shape with 4 equal sides
        var croppedBitmap = Bitmap.createBitmap(
            this, // source bitmap
            xOffset, // x coordinate of the first pixel in source
            yOffset, // y coordinate of the first pixel in source
            side, // width
            side // height
        )
        return Bitmap.createScaledBitmap(croppedBitmap, 224, 224, false)
    }

}