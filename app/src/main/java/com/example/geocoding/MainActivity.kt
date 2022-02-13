//this code is event handler to a button
// when the button is clicked by the user
// it reads texts from the plain text and validate that there is some text entered
// create a geocode object, request it converts the text into ans address
// this is the part whe every possible errors is fixed like if there is no internet connection or the geo coder doesnt know whee the location is
//Launch an application that can display maps with the first location found
//do some logging just in case of some errors
// show user Toast for feedback

package com.example.geocoding

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.IOException
import kotlin.math.log

private const val TAG = "GEOCODE_PLACE_ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var  placeName: EditText
    private lateinit var mapButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeName = findViewById(R.id.place_name_input)
        mapButton = findViewById(R.id.map_button)
        mapButton.setOnClickListener {
          val placeName = placeName.text.toString()
          if(placeName.isBlank()){
              Toast.makeText(this, getString(R.string.No_text_error), Toast.LENGTH_LONG).show()
          }else{
              Log.d(TAG, "About to geocode $placeName")
              showMapForPlace(placeName)
          }
      }
    }
    //usa and intent to launch map app, for the first location, if a location is found
    private fun showMapForPlace(placeName:String){
        //geocode place name to get list of location
        val geocoder = Geocoder(this)
       try{
           val addresses = geocoder.getFromLocationName(placeName, 1)


            if (addresses.isNotEmpty()) {
                val address = addresses.first()
                Log.d(TAG, "First address is $address")
                val geoUriString = "geo:${address.latitude}, ${address.longitude}"
                Log.d(TAG, "using geo uri $geoUriString")
                val geoUri = Uri.parse(geoUriString)
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                Log.d(TAG, "lAUNCHING MAP ACTIVITY ")
                startActivity(mapIntent)
            }else{
                Log.d(TAG, "No places found for string $placeName")
                Toast.makeText(this, getString(R.string.no_place_found_error) , Toast.LENGTH_SHORT).show()
        }

    }
       catch (e : IOException){
           Log.e(TAG, "Unable to geocode place $placeName", e)
           Toast.makeText(this, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show()

       }       }
}