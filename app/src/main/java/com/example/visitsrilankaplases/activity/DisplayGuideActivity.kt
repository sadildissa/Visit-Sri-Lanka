package com.example.visitsrilankaplases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visitsrilankaplases.R
import com.example.visitsrilankaplases.adapters.guideAdapter
import com.example.visitsrilankaplases.models.GuideModel
import com.google.firebase.database.*

class DisplayGuideActivity : AppCompatActivity() {

    private lateinit var guideRecyclerView: RecyclerView
    private lateinit var tvLoadingdata: TextView
    private lateinit var guideList : ArrayList<GuideModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dispalyguidedata)

        guideRecyclerView = findViewById(R.id.rvguide)
        guideRecyclerView.layoutManager = LinearLayoutManager(this)
        guideRecyclerView.setHasFixedSize(true)
        tvLoadingdata = findViewById(R.id.tvLoadingData)

        guideList = arrayListOf<GuideModel>()

        getGuideData()
    }
    private fun getGuideData(){
        guideRecyclerView.visibility = View.GONE
        tvLoadingdata.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Guide")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                guideList.clear()
                if (snapshot.exists()){
                    for (guideSnap in snapshot.children){
                        val guideData = guideSnap.getValue(GuideModel::class.java)
                        guideList.add(guideData!!)
                    }
                    val mAdapter = guideAdapter(guideList)
                    guideRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : guideAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@DisplayGuideActivity, GuidedetailActivity::class.java)

                            //put extras
                            intent.putExtra("guideId",guideList[position].guideId)
                            intent.putExtra("guideName", guideList[position].guideName)
                            intent.putExtra("guideAddress", guideList[position].guideAddress)
                            intent.putExtra("guidePhone",guideList[position].guidephone)
                            intent.putExtra("guideEmail", guideList[position].guideemail)
                            startActivity(intent)
                        }

                    })

                    guideRecyclerView.visibility = View.VISIBLE
                    tvLoadingdata.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}