package edu.washington.geruh.quizdroid


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.lang.ClassCastException


class TopicActivityFragment : Fragment() {

    companion object {
        fun newInstance(topic: Topic) =
            TopicActivityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("TOPIC", topic)
                }
            }
    }

    var listener: TopicactivityFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_topic_activity_fragement, container, false)
        val quizTopic = arguments?.getSerializable("TOPIC") as Topic
        val topicView = view.findViewById<TextView>(R.id.quizTopic)
        val topicDesc = view.findViewById<TextView>(R.id.topicDesc)
        val numberOfQuestions = view.findViewById<TextView>(R.id.questions)
        topicView.setText(quizTopic.title)
        topicDesc.setText(quizTopic.shortDescr)
        view.findViewById<Button>(R.id.startQuiz).setOnClickListener{ startQuiz(quizTopic) }
        numberOfQuestions.setText("5 questions")
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as TopicactivityFragmentListener
        } catch (e: ClassCastException) {
            throw RuntimeException(context.toString())
        }
    }

    interface TopicactivityFragmentListener {
        fun startQuiz(quizTopic: Topic)
    }

    fun startQuiz(quizTopic: Topic) {
        listener?.startQuiz(quizTopic)
    }

}
