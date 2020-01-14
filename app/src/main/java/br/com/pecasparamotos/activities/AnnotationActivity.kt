package br.com.pecasparamotos.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.Item

import kotlinx.android.synthetic.main.activity_annotation.*
import java.util.ArrayList

class AnnotationActivity : AppCompatActivity() {

    /**
     *
     */
    private var itemsList: ArrayList<Item>? = null

    /**
     *
     */
    private var etAnnotations: EditText? = null

    /**
     *
     */
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(resources.getString(R.string.title_activity_annotation))

        itemsList = intent.extras!!.getParcelableArrayList("items")

        etAnnotations = findViewById(R.id.etAnnotations)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSubmit!!.setOnClickListener {
            onSubmit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onSubmit() {
        val it = intent
        it.setClass(this, ReportPreviewActivity::class.java)
        it.putExtra("annotations", etAnnotations!!.getText().toString())
        it.putExtra("items", itemsList)

        startActivityForResult(it, 0)
    }
}
