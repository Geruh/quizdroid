package edu.washington.geruh.quizdroid
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class PreferenceActivity : AppCompatActivity() {

    lateinit var time: Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val alarm: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, Notification::class.java)

        val spinner: Spinner = findViewById(R.id.spinner)
        val minuteList = arrayOf(1, 5, 15, 30, 60)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, minuteList)
        val input = findViewById<EditText>(R.id.inputURL)
        val button = findViewById<Button>(R.id.confirmButton)

        spinner.setAdapter(adapter)

        button.setOnClickListener {
            val timeString = spinner.getSelectedItem().toString()
                time = timeString.toInt()
                val inputedJson: String = input.text.toString()
                val timeConversion = time.toLong() * 60000
                intent.putExtra("URL", inputedJson)
            Toast.makeText(this, "Downloading Json File from ${inputedJson}", Toast.LENGTH_LONG).show()

            val pendingIntent = PendingIntent.getBroadcast(this,0, intent,0)
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeConversion, pendingIntent)
             val main = Intent(this, MainActivity::class.java)
             startActivity(main)
            }
        }
    }
