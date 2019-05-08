package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_topic.view.*


class RecycleAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    val quizTopics = listOf("Math", "Physics", "Marvel Super Heroes")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_topic, p0, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quizTopics.size
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val topics = quizTopics.get(p1)
        p0.view.topicName.text = topics
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, TopicActivity::class.java)
            intent.putExtra("quizTopic", view.topicName.text)
            intent.putExtra("quizDesc", view.topicName.text)

            view.context.startActivity(intent)
        }
    }
}