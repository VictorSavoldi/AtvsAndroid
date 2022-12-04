package com.example.atividade10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject

class PacientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        var url = "https://imunizacao-es.saude.gov.br/desc-imunizacao/_search"

        var listaPacientes = findViewById<ListView>(R.id.listaClientes)
        var dados: ArrayList<String> = ArrayList<String>();
        var adapter: ArrayAdapter<String>? = null;

        var nascimentoPaciente: String? = null
        var pacienteIdade: String
        var municipio: String
        var nomeVacina: String
        var vacinaCategoria: String
        var vacinaData: String
        var informacoes: ArrayList<String> = ArrayList<String>();

        listaPacientes.onItemClickListener =
            AdapterView.OnItemClickListener() { _, _, position, id ->
                Toast.makeText(
                    this,
                    ((position + 1).toString() + ") " + nascimentoPaciente),
                    Toast.LENGTH_SHORT
                ).show()

                var i = Intent(this, MapsActivity::class.java)
                    .apply {
                        putExtra("nascimentoPaciente", nascimentoPaciente)
                        putExtra("informacoes", informacoes.get(position))
                    }

                startActivity(i)
            }

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String?>() { response ->

                var resultadoJson = JSONObject(response)

                var objInicial: JSONObject = resultadoJson.getJSONObject("hits");

                var listaObjetos: JSONArray = objInicial.getJSONArray("hits")

                for (i in 0..listaObjetos.length() - 1) {

                    var objFinal: JSONObject = listaObjetos.getJSONObject(i)
                    var objDadosPaciente: JSONObject = objFinal.getJSONObject("_source")

                    dados.add(objDadosPaciente.getString("paciente_dataNascimento"))

                    nascimentoPaciente = objDadosPaciente.getString("paciente_dataNascimento")
                    pacienteIdade = objDadosPaciente.getInt("paciente_idade").toString()
                    municipio = objDadosPaciente.getString("estabelecimento_municipio_nome")
                    nomeVacina = objDadosPaciente.getString("vacina_nome")
                    vacinaCategoria = objDadosPaciente.getString("vacina_categoria_nome")
                    vacinaData = objDadosPaciente.getString("vacina_dataAplicacao")

                    informacoes.add(
                        "\nIdade: " + pacienteIdade
                                + "\n\nMunicipio: " + municipio
                                + "\n\nVacina: " + nomeVacina
                                + "\n\nCategoria: " + vacinaCategoria
                                + "\n\nData: " + vacinaData
                    )
                }

                adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dados);

                listaPacientes.adapter = adapter;

            },
            Response.ErrorListener() { erro ->
                Toast.makeText(applicationContext, erro.toString(), Toast.LENGTH_SHORT).show()
                fun onErrorResponse(error: VolleyError?) {
                    println("Nao Funcionou")

                }
            }) {

            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()

                val credentials = "imunizacao_public:qlto5t&7r_@+#Tlstigi"
                val auth =
                    "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = auth
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}