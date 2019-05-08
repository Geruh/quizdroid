package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainTopics.layoutManager = LinearLayoutManager(this)
        mainTopics.adapter = RecycleAdapter()
    }

//    fun topicOverview(v: View) {
//        val intent = Intent(this, TopicActivity::class.java)
//        val button = v as Button
//        var quizTopic = button.text.toString()
//        intent.putExtra("quizTopic", quizTopic)
//        startActivity(intent)
//    }
}
