package com.example.visitsrilankaplases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.visitsrilankaplases.R
import com.example.visitsrilankaplases.models.GuideModel
import com.google.firebase.database.FirebaseDatabase

class GuidedetailActivity : AppCompatActivity() {

    private lateinit var tvGuideId: TextView
    private lateinit var tvGuideName: TextView
    private lateinit var tvGuideAddress: TextView
    private lateinit var tvGuidePhone: TextView
    private lateinit var tvGuideEmail: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guidedetail)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("guideId").toString(),
                intent.getStringExtra("guideName").toString()
            )
        }
        
        btnDelete.setOnClickListener { 
            deleteRecord(
                intent.getStringExtra("guideId").toString()
            )

        }


    }

    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Guide").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Guide data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DisplayGuideActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()

        }
    }

    private fun initView() {
        tvGuideId = findViewById(R.id.tvGuideId)
        tvGuideName = findViewById(R.id.tvGuideName)
        tvGuideAddress = findViewById(R.id.tvGuideAddress)
        tvGuidePhone = findViewById(R.id.tvGuidePhone)
        tvGuideEmail = findViewById(R.id.tvGuideEmail)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews() {
        tvGuideId.text = intent.getStringExtra("guideId")
        tvGuideName.text = intent.getStringExtra("guideName")
        tvGuideAddress.text = intent.getStringExtra("guideAddress")
        tvGuidePhone.text = intent.getStringExtra("guidePhone")
        tvGuideEmail.text = intent.getStringExtra("guideEmail")
    }

    private fun openUpdateDialog(
        guideId: String,
        guideName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etGuideName = mDialogView.findViewById<EditText>(R.id.etGuideName)
        val etGuideAddress = mDialogView.findViewById<EditText>(R.id.etGuideAddress)
        val etGuidePhone = mDialogView.findViewById<EditText>(R.id.etetGuidePhone)
        val etGuideEmail = mDialogView.findViewById<EditText>(R.id.etetGuideEmail)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etGuideName.setText(intent.getStringExtra("guideName").toString())
        etGuideAddress.setText(intent.getStringExtra("guideAddress").toString())
        etGuidePhone.setText(intent.getStringExtra("guidePhone").toString())
        etGuideEmail.setText(intent.getStringExtra("guideEmail").toString())

        mDialog.setTitle("Updating $guideName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateGuideData(
                guideId,
                etGuideName.text.toString(),
                etGuideAddress.text.toString(),
                etGuidePhone.text.toString(),
                etGuideEmail.text.toString()
            )

            Toast.makeText(applicationContext, "Guide Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvGuideName.text = etGuideName.text.toString()
            tvGuideAddress.text = etGuideAddress.text.toString()
            tvGuidePhone.text = etGuidePhone.text.toString()
            tvGuideEmail.text = etGuideEmail.text.toString()

            alertDialog.dismiss()
        }
    }


    private fun updateGuideData(
        id: String,
        name: String,
        address: String,
        phone: String,
        email: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Guide").child(id)
        val empInfo = GuideModel(id, name, address, phone,email)
        dbRef.setValue(empInfo)
    }


}




