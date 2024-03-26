package com.example.boshqaruv

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.boshqaruv.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var database :FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        database = FirebaseDatabase.getInstance()
        reference = database.getReference("message")


        binding.swichFlash.setOnCheckedChangeListener { buttonView, isChecked ->
            reference.setValue(isChecked)

        }
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val isChecked:Boolean = snapshot.value as Boolean
                val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
                val cameraId = cameraManager.cameraIdList[0]
                if (isChecked){
                    try {
                        cameraManager.setTorchMode(cameraId, true)

                    } catch (e: CameraAccessException) {
                    }
                }else{
                    try {
                        cameraManager.setTorchMode(cameraId, false)
                    } catch (e: CameraAccessException) {
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}