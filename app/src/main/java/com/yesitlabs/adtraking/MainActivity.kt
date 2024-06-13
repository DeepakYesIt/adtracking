package com.yesitlabs.adtraking

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var textView:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.tv_session)

        Adtraking.startSession()

        textView.setOnClickListener{
            Adtraking.froyoUploadData(this,"Male","34FcLvWWlqhBlEQx","1996/05/07","yesitlabs@gmail.com")
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==101){
            Adtraking.onRequestPermissionsResult(requestCode, grantResults)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 || requestCode==200){
            Adtraking.onActivityResult(requestCode,resultCode)
        }
    }

}