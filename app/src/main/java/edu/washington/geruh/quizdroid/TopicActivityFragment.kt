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
        val TOPIC = "TOPIC"
        fun newInstance(topic: String): TopicActivityFragment {
            val args = Bundle().apply {
                putString(TOPIC, topic)
            }
            val fragment = TopicActivityFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    val quizDesc = listOf(
        "This will be a series of questions testing your skills in mathematics goodluck",
        "Do you know your Physics? Let's find out!",
        "Do you think you know your marvel superheroes well here is your chance to prove it"
    )
    var listener: TopicactivityFragmentListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val quizTopic = arguments?.getString("TOPIC") as String
        val view: View = inflater.inflate(R.layout.fragment_topic_activity_fragement, container, false)
        val topicView = view.findViewById<TextView>(R.id.quizTopic)
        val topicDesc = view.findViewById<TextView>(R.id.topicDesc)
        val numberOfQuestions = view.findViewById<TextView>(R.id.questions)
        view.findViewById<Button>(R.id.startQuiz).setOnClickListener { _ -> run { startQuiz(quizTopic) } }
        topicView.setText(quizTopic)
        setDesc(topicDesc, quizTopic)
        numberOfQuestions.setText("5 questions")
        return view
    }

    interface TopicactivityFragmentListener {
        fun startQuiz(topicCodeName: String)
    }

    fun startQuiz(quizTopic:String) {
        listener?.startQuiz(quizTopic)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as TopicactivityFragmentListener
        } catch (e: ClassCastException) {
            throw RuntimeException(context.toString())
        }
    }


    fun setDesc(topicDesc: TextView, topic: String) {
        if (topic == "Math") {
            topicDesc.setText(quizDesc[0])
        }
        if (topic == "Physics") {
            topicDesc.setText(quizDesc[1])
        }
        else {
            topicDesc.setText(quizDesc[2])
        }
    }

}
