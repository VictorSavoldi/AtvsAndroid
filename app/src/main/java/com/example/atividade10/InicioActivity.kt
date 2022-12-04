package com.example.atividade10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        var btListaPacientes = findViewById<Button>(R.id.btPacientes)
        var btSobreDev = findViewById<Button>(R.id.btDev)


        btListaPacientes.setOnClickListener{

            var i = Intent(this, PacientesActivity::class.java)

            startActivity(i)
        }

        btSobreDev.setOnClickListener{

            var i = Intent(this, DevActivity::class.java)
                .putExtra("Nome", "Victor Hugo Freitas Savoldi")
                .putExtra("Email", "victor.savoldi@aluno.unifafibe.edu.br")
                .putExtra("RA", "2017193549")
                .putExtra("Telefone", "(17) 992067646")

            startActivity(i)
        }
    }
}