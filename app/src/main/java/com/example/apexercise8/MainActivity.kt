package com.example.apexercise8

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.apexercise8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherImageView = findViewById(R.id.weatherImageView)


        binding.toggleButton.setOnClickListener {
            if (binding.toggleButton.isChecked) {
                Toast.makeText(applicationContext, "Celsius selected", Toast.LENGTH_SHORT).show()
            } else {
                // Fahrenheit selected
                Toast.makeText(applicationContext, "Fahrenheit selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedID ->
            when (checkedID) {
                R.id.radioButton -> {
                    Toast.makeText(applicationContext, "Option 1", Toast.LENGTH_SHORT).show()
                }

                R.id.radioButton2 -> {
                    Toast.makeText(applicationContext, "Option 2", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.Sunny.setOnClickListener {
            if (binding.Sunny.isChecked) {
                Toast.makeText(applicationContext, "Sunny Checked", Toast.LENGTH_SHORT).show()
//                weatherImageView.setImageResource(R.drawable.sunny)

            } else {
                Toast.makeText(applicationContext, "Sunny Unchecked", Toast.LENGTH_SHORT).show()

            }
            updateWeatherImage()
        }
        binding.Cloudy.setOnClickListener {
            if (binding.Cloudy.isChecked) {
                Toast.makeText(applicationContext, "Cloudy Checked", Toast.LENGTH_SHORT).show()
//                weatherImageView.setImageResource(R.drawable.cloudy)

            } else {
                Toast.makeText(applicationContext, "Cloudy Unchecked", Toast.LENGTH_SHORT).show()

            }
            updateWeatherImage()
        }
        binding.Rainy.setOnClickListener {
            if (binding.Rainy.isChecked) {
                Toast.makeText(applicationContext, "Rainy Checked", Toast.LENGTH_SHORT).show()
//                weatherImageView.setImageResource(R.drawable.rainy)

            } else {
                Toast.makeText(applicationContext, "Rainy Unchecked", Toast.LENGTH_SHORT).show()

            }
            updateWeatherImage()
        }

        val spinnerItems = arrayOf("-Select-","New York", "London", "Tokyo")
        val adapter2 = ArrayAdapter(this, R.layout.customitem, spinnerItems)
        adapter2.setDropDownViewResource(R.layout.customitem)
        binding.spinner2.adapter = adapter2

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinnerItems[position]
                Toast.makeText(applicationContext, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Nothing is Selected", Toast.LENGTH_SHORT).show()
            }


        }
        binding.FrameLayout.setOnClickListener {

            val selectedWeatherConditions = StringBuilder()
            if (binding.Sunny.isChecked) {
                selectedWeatherConditions.append("Sunny, ")
            }
            if (binding.Cloudy.isChecked) {
                selectedWeatherConditions.append("Cloudy, ")
            }
            if (binding.Rainy.isChecked) {
                selectedWeatherConditions.append("Rainy, ")
            }

            val selectedOption = binding.radioGroup.findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId).text
            val selectedLocation = binding.spinner2.selectedItem as String
            val dialogMessage = "Selected Options:\n" +
                    "Temperature Unit: ${if (binding.toggleButton.isChecked) "Celsius" else "Fahrenheit"}\n" +
                    "Weather Conditions: ${selectedWeatherConditions.trimEnd().toString()}\n" +
                    "Option: $selectedOption\n" +
                    "Location: $selectedLocation"
            showAlertDialog(dialogMessage)
        }


    }
    private fun showAlertDialog(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        dialogTitle.text = "Weather Update"
        dialogMessage.text = message

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        dialogButton.setOnClickListener {
            Toast.makeText(applicationContext, "Weather has been updated", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
    }
    private fun updateWeatherImage() {
        val selectedWeatherConditions = mutableListOf<String>()

        if (binding.Sunny.isChecked) {
            selectedWeatherConditions.add("Sunny")
        }

        if (binding.Cloudy.isChecked) {
            selectedWeatherConditions.add("Cloudy")
        }

        if (binding.Rainy.isChecked) {
            selectedWeatherConditions.add("Rainy")
        }

        val imageDrawableId = when (selectedWeatherConditions.size) {
            0 -> R.drawable.cloudyyyy // No weather condition selected
            1 -> {
                val condition = selectedWeatherConditions[0]
                when (condition) {
                    "Sunny" -> R.drawable.sunny
                    "Cloudy" -> R.drawable.cloudy
                    "Rainy" -> R.drawable.rainy
                    else -> R.drawable.cloudyyyy // Handle unknown condition
                }
            }
            2 -> {
                if (selectedWeatherConditions.contains("Sunny") && selectedWeatherConditions.contains("Cloudy")) {
                    R.drawable.sunnycloudy // Show image for both Sunny and Cloudy conditions
                } else if (selectedWeatherConditions.contains("Sunny") && selectedWeatherConditions.contains("Rainy")) {
                    R.drawable.sunnyrainy // Show image for both Sunny and Rainy conditions
                } else if (selectedWeatherConditions.contains("Cloudy") && selectedWeatherConditions.contains("Rainy")) {
                    R.drawable.cloudyrainy // Show image for both Cloudy and Rainy conditions
                } else {
                    R.drawable.cloudyyyy // Handle other combinations
                }
            }
            3 -> {
                if (selectedWeatherConditions.contains("Sunny") && selectedWeatherConditions.contains("Cloudy") && selectedWeatherConditions.contains("Rainy")) {
                    R.drawable.sunnycloudyrainy // Show image for all three conditions selected
                } else {
                    R.drawable.cloudyyyy // Handle other combinations
                }
            }
            else -> R.drawable.cloudyyyy // Handle unexpected conditions
        }

        weatherImageView.setImageResource(imageDrawableId)



    }









}


