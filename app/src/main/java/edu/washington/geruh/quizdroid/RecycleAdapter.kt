package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_topic.view.*


class RecycleAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    val quizTopics = QuizApp.sharedInstance.topicRepository.getTopics()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_topic, p0, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quizTopics.size
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val topics = quizTopics[p1]
        p0.view.topicName.text = topics
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, QuizActivity::class.java)
            val string = view.topicName.text as String
            intent.putExtra("quizTopic", string)
            view.context.startActivity(intent)
        }
    }
}