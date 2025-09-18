package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class Class1Activity : AppCompatActivity() {

    private lateinit var topicsRecyclerView: RecyclerView
    private lateinit var startLearningButton: MaterialButton

    // All 29 topics with actual Class 1 content
    private val allTopics = listOf(
        LearningTopic(1, "Position Words", "Above, Below, Inside, Outside"),
        LearningTopic(2, "Before and After", "Understanding Sequence"),
        LearningTopic(3, "Sorting Things", "Grouping Similar Objects"),
        LearningTopic(4, "Round and Long", "Identifying Shapes"),
        LearningTopic(5, "Rolling and Sliding", "How Objects Move"),
        LearningTopic(6, "Counting 1-9", "Learning Numbers"),
        LearningTopic(7, "More and Less", "Comparing Quantities"),
        LearningTopic(8, "Number Order", "Arranging Numbers"),
        LearningTopic(9, "Teen Numbers", "Numbers 10-20"),
        LearningTopic(10, "Number Writing", "Writing Numbers Correctly"),
        LearningTopic(11, "Adding Numbers", "Putting Together"),
        LearningTopic(12, "Addition Practice", "Simple Addition"),
        LearningTopic(13, "Taking Away", "Simple Subtraction"),
        LearningTopic(14, "Subtraction Practice", "Practice Subtraction"),
        LearningTopic(15, "Long and Short", "Comparing Length"),
        LearningTopic(16, "Tall and Short", "Comparing Height"),
        LearningTopic(17, "Heavy and Light", "Comparing Weight"),
        LearningTopic(18, "Counting to 50", "Bigger Numbers"),
        LearningTopic(19, "Number Patterns", "Skip Counting"),
        LearningTopic(20, "Color Patterns", "Making Patterns"),
        LearningTopic(21, "Shape Patterns", "Pattern Recognition"),
        LearningTopic(22, "Coins and Money", "Understanding Money"),
        LearningTopic(23, "Buying Things", "Using Money"),
        LearningTopic(24, "Making Groups", "Equal Groups"),
        LearningTopic(25, "Repeated Addition", "Adding Same Numbers"),
        LearningTopic(26, "Day and Night", "Understanding Time"),
        LearningTopic(27, "Yesterday, Today, Tomorrow", "Time Sequence"),
        LearningTopic(28, "Counting Objects", "Organizing Information"),
        LearningTopic(29, "Comparing Groups", "More, Less, Equal")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class1)

        setupViews()
        setupRecyclerView()
    }

    private fun setupViews() {
        topicsRecyclerView = findViewById(R.id.topicsRecyclerView)
        startLearningButton = findViewById(R.id.startLearningButton)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        val prefs = getSharedPreferences("class1_progress", 0)
        val currentTopic = prefs.getInt("current_topic", 1)
        val completedTopics = prefs.getInt("completed_topics", 0)

        findViewById<TextView>(R.id.progressText).text =
            "Progress: $completedTopics/${allTopics.size} topics completed"

        startLearningButton.text = if (currentTopic == 1) {
            "Start Learning!"
        } else {
            "Continue Learning (Topic $currentTopic)"
        }

        startLearningButton.setOnClickListener {
            startTopicLearning(currentTopic)
        }
    }

    private fun setupRecyclerView() {
        val adapter = TopicsOverviewAdapter(allTopics) { topic ->
            startTopicLearning(topic.id)
        }
        topicsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Class1Activity)
            this.adapter = adapter
        }
    }

    private fun startTopicLearning(topicNumber: Int) {
        val intent = Intent(this, TopicLearningActivity::class.java)
        intent.putExtra("topic_number", topicNumber)
        startActivity(intent)
    }
}

data class LearningTopic(
    val id: Int,
    val title: String,
    val description: String
)

// Fixed TopicsOverviewAdapter - Now with click functionality
class TopicsOverviewAdapter(
    private val topics: List<LearningTopic>,
    private val onTopicClick: (LearningTopic) -> Unit // Add click listener parameter
) : RecyclerView.Adapter<TopicsOverviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicTitle: TextView = view.findViewById(R.id.topicTitle)
        val topicDescription: TextView = view.findViewById(R.id.topicDescription)
        val completionIcon: View = view.findViewById(R.id.completionIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic_overview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.topicTitle.text = "${topic.id}. ${topic.title}"
        holder.topicDescription.text = topic.description

        // Check if completed
        val prefs = holder.itemView.context.getSharedPreferences("class1_progress", 0)
        val isCompleted = prefs.getBoolean("topic_${topic.id}_completed", false)
        holder.completionIcon.visibility = if (isCompleted) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onTopicClick(topic)
        }
    }

    override fun getItemCount() = topics.size
}