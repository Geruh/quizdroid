package edu.washington.geruh.quizdroid


import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.security.AccessController.getContext

class Notification: BroadcastReceiver() {
    var result = ""
    var contextTest: Context? = null
    override fun onReceive(context: Context?, intent: Intent?) {
//      val testing = "https://api.myjson.com/bins/buzo2"
        contextTest = context
        val message = intent!!.getStringExtra("URL")
        downloadURLAsyncTask().execute(message)
    }

    inner class downloadURLAsyncTask : AsyncTask<String, String, String> () {
        override fun doInBackground(vararg jsonURL: String?): String {
            val text: String
            val connection = URL(jsonURL[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i("Debugging", "onPostExecute")
            saveJSON(result)
        }
    }
    private fun saveJSON(json: String?) {
        try {
            if (ContextCompat.checkSelfPermission(contextTest!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val directory = Environment.getExternalStorageDirectory()
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "questions.json")
            val output = PrintWriter(FileWriter(file, true))
            output.println(json)
            output.close()
            result = "Succeed"
            Toast.makeText(contextTest, "${result}", Toast.LENGTH_LONG).show()
            Log.i("Debugging", "succeed")
            } 
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            Log.i("Debugging", ioe.toString())
            result = "Failed"
            Toast.makeText(contextTest, "${result}", Toast.LENGTH_LONG).show()
        }
    }
}

