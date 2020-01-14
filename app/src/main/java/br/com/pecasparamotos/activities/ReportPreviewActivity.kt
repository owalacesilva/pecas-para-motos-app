package br.com.pecasparamotos.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import br.com.pecasparamotos.R
import br.com.pecasparamotos.adapters.ReportItemListAdapter
import br.com.pecasparamotos.models.Item
import br.com.pecasparamotos.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ReportPreviewActivity : AppCompatActivity() {

    private val TAG = "LOG_TAG"

    private val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    /**
     *
     */
    private var itemList: ArrayList<Item>? = null

    /**
     *
     */
    private var annotations: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_report_preview)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        annotations = intent.extras!!.getString("annotations")
        itemList = intent.extras!!.getParcelableArrayList<Item>("items")

        // View containers
        val divReviewedItems: ViewGroup = findViewById(R.id.divReviewedItems)
        val divAnnotations: ViewGroup = findViewById(R.id.divAnnotations)

        for (item in itemList!!) {
            // Iterate list items for save in file
            addTextViewInViewGroup("• " + item.name, item.price, divReviewedItems)
        }

        // Save comments
        drawAnnotationToLayout(annotations!!, divAnnotations)

        // Ceck permission
        Utils.requestWriteExternalStoragePersmission(this, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_btn_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
            R.id.action_save -> {
                onSubmit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     *
     */
    private fun onSubmit() {
        try {
            val cache = createBitmapFromView(findViewById(R.id.frame_review_report))

            // Compress bitmap file
            val fileOutputStream = FileOutputStream(getFileDist())
            cache.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            // share it
            shareIt(getFileDist().toString())
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        } finally {
            // send result and finish
            setResult(RESULT_OK)
            finish()
        }
    }

    /**
     * Get file path where it will be to saved
     *
     * @return File
     */
    fun getFileDist(): File {
        // Get the directory for the user's public pictures directory.
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )

        if (!storageDir.exists())
            storageDir.mkdirs()

        val file = File(storageDir, "Ultimo_Relatorio_Pecas_Para_Motos.jpg")
        try {
            file.createNewFile()
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }

        return file
    }

    /**
     * Create bitmap from view layout
     *
     * @param view
     *
     * @return Bitmap
     */
    fun createBitmapFromView(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        val c = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(c)

        return bitmap
    }

    /**
     * Sharing new file created
     *
     * @param filename
     */
    private fun shareIt(filename: String) {
        val file = File(filename)
        val fileUriProvider = FileProvider.getUriForFile(this, getString(R.string.file_provider_authority), file)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpg"
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUriProvider)

        startActivity(Intent.createChooser(shareIntent, "Compartilhar Relatório Mecânico"))
    }

    /**
     * Add textview component inside View group
     *
     * @param textContent
     * @param viewGroup
     */
    private fun addTextViewInViewGroup(itemName: String, itemPrice: Double, viewGroup: ViewGroup) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 20)
        layoutParams.weight = 1f

        val tvLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        tvLayoutParams.weight = 1f

        val container = LinearLayout(this)
        container.orientation = LinearLayout.HORIZONTAL

        val leftSide = LinearLayout(this)
        leftSide.orientation = LinearLayout.HORIZONTAL
        leftSide.gravity = Gravity.LEFT

        val rightSide = LinearLayout(this)
        rightSide.orientation = LinearLayout.HORIZONTAL
        rightSide.gravity = Gravity.RIGHT

        val typeface = ResourcesCompat.getFont(this, R.font.calibri)
        val tvItemName = TextView(this)
        tvItemName.text = itemName
        tvItemName.setTextColor(Color.BLACK)
        tvItemName.textSize = 12f
        tvItemName.typeface = typeface
        leftSide.addView(tvItemName)

        val tvItemPrice = TextView(this)
        tvItemPrice.text = Utils.formatToCurrency(itemPrice)
        tvItemPrice.setTextColor(Color.BLACK)
        tvItemPrice.textSize = 12f
        tvItemPrice.typeface = typeface
        rightSide.addView(tvItemPrice)

        container.addView(leftSide, tvLayoutParams)
        container.addView(rightSide, tvLayoutParams)
        viewGroup.addView(container, layoutParams)
    }

    /**
     * Add textview component inside View group
     *
     * @param textContent
     * @param viewGroup
     */
    private fun drawAnnotationToLayout(text: String, viewGroup: ViewGroup) {
        val layoutParams = LinearLayout.LayoutParams(
            960,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 20)
        layoutParams.weight = 1f

        val typeface = ResourcesCompat.getFont(this, R.font.calibri)
        val tv = TextView(this)
        tv.text = text
        tv.setTextColor(Color.BLACK)
        tv.textSize = 14f
        tv.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        tv.typeface = typeface

        viewGroup.addView(tv, layoutParams)
    }
}
