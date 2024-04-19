package com.example.bucketlist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Populate the ranking dropdown
        val rankings = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rankings)
        spinnerRanking.adapter = adapter

        // Add Destination Button Click Listener
        btnAddDestination.setOnClickListener {
            val destinationName = etDestinationName.text.toString()
            val description = etDescription.text.toString()
            val ranking = spinnerRanking.selectedItem.toString()

            if (destinationName.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                addDestination(destinationName, description, ranking)
            }
        }
    }

    private fun addDestination(destinationName: String, description: String, ranking: String) {
        // Create a new document with a generated ID
        val destination = hashMapOf(
            "name" to destinationName,
            "description" to description,
            "ranking" to ranking.toInt() // Convert ranking to Int
        )

        // Add a new document with a generated ID
        firestore.collection("destinations")
            .add(destination)
            .addOnSuccessListener {
                Toast.makeText(this, "Destination added successfully", Toast.LENGTH_SHORT).show()
                finish() // Finish the activity and return to the main activity
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding destination: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
