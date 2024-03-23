package com.example.visitsrilankaplases.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.visitsrilankaplases.R
import com.example.visitsrilankaplases.models.GuideModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertActivity : AppCompatActivity() {


    private lateinit var guidename: EditText
    private lateinit var guideaddress: EditText
    private lateinit var guidePhone: EditText
    private lateinit var guideEmail: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertguidedata)


        guidename = findViewById(R.id.guidename)
        guideaddress = findViewById(R.id.guideaddress)
        guidePhone = findViewById(R.id.guidePhone)
        guideEmail = findViewById(R.id.guideEmail)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Guide")

        btnSaveData.setOnClickListener {
            saveGuideData()
        }
    }

    private fun saveGuideData(){
        //geting values

        val guideName = guidename.text.toString()
        val guideAddress = guideaddress.text.toString()
        val guidephone = guidePhone.text.toString()
        val guideemail = guideEmail.text.toString()


        if (guideName.isEmpty()) {
            guidename.error = "Please enter name"
        }
        if (guideAddress.isEmpty()){
            guideaddress.error = "Please Enter guide address"
        }
        if (guidephone.isEmpty()) {
            guidePhone.error = "Please enter Phone number"
        }
        if (guideemail.isEmpty()) {
            guideEmail.error = "Please enter email"
        }

        val guideId = dbRef.push().key!!

        val guide = GuideModel(guideId, guideName, guideAddress,guidephone,guideemail)

        dbRef.child(guideId).setValue(guide)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted Successfully",Toast.LENGTH_LONG).show()

                guidename.text.clear()
                guideaddress.text.clear()
                guidePhone.text.clear()
                guideEmail.text.clear()


            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }
    }
}