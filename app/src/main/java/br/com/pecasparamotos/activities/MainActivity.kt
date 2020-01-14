package br.com.pecasparamotos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.com.pecasparamotos.R

class MainActivity : AppCompatActivity() {

    private var btnGenerateReport: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGenerateReport = findViewById(R.id.btn_generate_report)
        btnGenerateReport!!.setOnClickListener {
            var it = Intent(this, ItemsListActivity::class.java)
            startActivity(it)
        }
    }
}
