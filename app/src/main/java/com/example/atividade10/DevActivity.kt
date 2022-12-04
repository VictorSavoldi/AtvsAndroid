package com.example.atividade10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DevActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev)

        var viewNome = findViewById<TextView>(R.id.textViewNome)
        var viewEmail = findViewById<TextView>(R.id.textViewEmail)
        var viewRA = findViewById<TextView>(R.id.textViewRA)
        var viewTelefone = findViewById<TextView>(R.id.textViewTelefone)
        var perfilImg = findViewById<ImageView>(R.id.viewImgPerfil)


        viewNome.text = intent.getStringExtra("Nome")
        viewEmail.text = intent.getStringExtra("Email")
        viewRA.text = intent.getStringExtra("RA")
        viewTelefone.text = intent.getStringExtra("Telefone")
        perfilImg.setImageResource(R.drawable.imgperfil)
    }
}