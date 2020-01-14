package br.com.pecasparamotos.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter
import java.text.NumberFormat
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class Utils {

    companion object {
        @JvmStatic fun formatToCurrency(amount: Double): String {
            val locale = Locale("pt", "BR")
            val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

            return currencyFormatter.format(amount)
        }

        @JvmStatic fun readJson(inputStream: InputStream): String {
            val writer = StringWriter()
            try {
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                var line: String? = null
                while ({ line = reader.readLine(); line }() != null) {
                    writer.write(line)
                }
            } finally {
                inputStream.close()
            }

            return writer.toString()
        }

        /**
         *
         */
        @JvmStatic fun requestWriteExternalStoragePersmission(activity: Activity, requestCode: Int) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCode
                    )

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
        }
    }
}