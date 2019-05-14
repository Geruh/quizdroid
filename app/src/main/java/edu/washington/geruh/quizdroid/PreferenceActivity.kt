package edu.washington.geruh.quizdroid
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val minuteList = arrayOf(1, 5, 15, 30, 60)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, minuteList)
        spinner.setAdapter(adapter)
        val input = findViewById<TextView>(R.id.inputURL)
        val button = findViewById<Button>(R.id.confirmButton)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}