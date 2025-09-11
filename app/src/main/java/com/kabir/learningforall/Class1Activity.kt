package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Get current progress
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
        val adapter = TopicsOverviewAdapter(allTopics)
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

// TopicLearningActivity.kt - With actual educational content
class TopicLearningActivity : AppCompatActivity() {

    private var currentTopicNumber = 1
    private lateinit var currentTopic: LearningTopic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_learning)

        currentTopicNumber = intent.getIntExtra("topic_number", 1)
        loadTopicContent()
        setupViews()
    }

    private fun loadTopicContent() {
        currentTopic = getTopicContent(currentTopicNumber)

        findViewById<TextView>(R.id.topicTitle).text = currentTopic.title
        findViewById<TextView>(R.id.topicDescription).text = currentTopic.description
        findViewById<TextView>(R.id.topicNumber).text = "Topic $currentTopicNumber/29"

        // Load topic-specific content
        val contentText = findViewById<TextView>(R.id.contentText)
        val examplesText = findViewById<TextView>(R.id.examplesText)

        val (content, examples) = getTopicDetails(currentTopicNumber)
        contentText.text = content
        examplesText.text = examples
    }

    private fun setupViews() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.nextButton).setOnClickListener {
            startQuiz()
        }
    }

    private fun startQuiz() {
        val intent = Intent(this, TopicQuizActivity::class.java)
        intent.putExtra("topic_number", currentTopicNumber)
        startActivity(intent)
        finish()
    }

    private fun getTopicContent(topicNumber: Int): LearningTopic {
        return allTopics[topicNumber - 1]
    }

    private fun getTopicDetails(topicNumber: Int): Pair<String, String> {
        return when(topicNumber) {
            1 -> Pair(
                "We use special words to tell where things are:\n\n• ABOVE means on top\n• BELOW means underneath\n• INSIDE means within something\n• OUTSIDE means not within something",
                "Examples:\n• Bird flies ABOVE the tree\n• Cat sits BELOW the table\n• Books are INSIDE the bag\n• Children play OUTSIDE"
            )
            2 -> Pair(
                "We use words to show order:\n\n• BEFORE means comes first\n• AFTER means comes next\n• These help us understand sequence",
                "Examples:\n• 2 comes BEFORE 3\n• 5 comes AFTER 4\n• We eat BEFORE we play\n• We sleep AFTER dinner"
            )
            3 -> Pair(
                "Sorting means putting similar things together:\n\n• Group by color\n• Group by shape\n• Group by size\n• This makes things organized",
                "Examples:\n• All red things together\n• All round things together\n• All books in one place\n• All toys in another place"
            )
            4 -> Pair(
                "Objects have different shapes:\n\n• ROUND objects: balls, coins, wheels\n• LONG objects: pencils, rulers, sticks\n• We can identify shapes around us",
                "Examples:\n• Ball is ROUND\n• Pencil is LONG\n• Plate is ROUND\n• Ruler is LONG"
            )
            5 -> Pair(
                "Objects move in different ways:\n\n• Round things can ROLL\n• Flat things can SLIDE\n• Some things can do both",
                "Examples:\n• Ball can ROLL down a hill\n• Book can SLIDE on a table\n• Can can both roll and slide"
            )
            6 -> Pair(
                "Numbers help us count things:\n\n• 1, 2, 3, 4, 5, 6, 7, 8, 9\n• Each number shows how many\n• We count one by one",
                "Examples:\n• 1 apple\n• 2 cars\n• 3 birds\n• Count your fingers: 1, 2, 3, 4, 5"
            )
            7 -> Pair(
                "We can compare amounts:\n\n• MORE means bigger amount\n• LESS means smaller amount\n• EQUAL means same amount",
                "Examples:\n• 5 is MORE than 3\n• 2 is LESS than 7\n• 4 apples = 4 oranges (equal)"
            )
            8 -> Pair(
                "Numbers have a special order:\n\n• 1, 2, 3, 4, 5, 6, 7, 8, 9\n• They always go in the same order\n• We can arrange them correctly",
                "Examples:\n• 3 comes after 2\n• 7 comes before 8\n• Count in order: 1, 2, 3, 4, 5"
            )
            9 -> Pair(
                "Teen numbers are special:\n\n• 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20\n• They all have 'teen' in their name\n• 10 means one ten and zero ones",
                "Examples:\n• 13 = thirteen\n• 16 = sixteen\n• Count your fingers and toes = 20"
            )
            10 -> Pair(
                "Writing numbers correctly:\n\n• Each number has a special shape\n• Practice writing 1, 2, 3, 4, 5\n• Start from the top",
                "Examples:\n• 1 is a straight line\n• 2 curves and has a line\n• 3 has two curves"
            )
            11 -> Pair(
                "Addition means putting things together:\n\n• Use + sign for addition\n• Count all objects together\n• Start with small numbers",
                "Examples:\n• 2 + 3 = 5 (2 apples + 3 apples = 5 apples)\n• 1 + 1 = 2\n• 3 + 2 = 5"
            )
            12 -> Pair(
                "More addition practice:\n\n• Adding within 9\n• Use fingers to help count\n• Always count carefully",
                "Examples:\n• 4 + 2 = 6\n• 3 + 3 = 6\n• 5 + 1 = 6"
            )
            13 -> Pair(
                "Subtraction means taking away:\n\n• Use - sign for subtraction\n• Count what's left\n• Start with what you have",
                "Examples:\n• 5 - 2 = 3 (5 toys, take 2 away, 3 left)\n• 4 - 1 = 3\n• 6 - 3 = 3"
            )
            14 -> Pair(
                "More subtraction practice:\n\n• Taking away within 9\n• Use objects to help\n• Count carefully",
                "Examples:\n• 7 - 2 = 5\n• 8 - 3 = 5\n• 9 - 4 = 5"
            )
            15 -> Pair(
                "Comparing length:\n\n• LONG vs SHORT\n• Look and compare\n• Use words to describe",
                "Examples:\n• Pencil is LONGER than eraser\n• Bus is LONGER than car\n• Snake is LONG, mouse is SHORT"
            )
            16 -> Pair(
                "Comparing height:\n\n• TALL vs SHORT\n• Compare by looking\n• People and objects have height",
                "Examples:\n• Tree is TALLER than bush\n• Adult is TALLER than child\n• Giraffe is very TALL"
            )
            17 -> Pair(
                "Comparing weight:\n\n• HEAVY vs LIGHT\n• Some things are heavier\n• Feel the difference",
                "Examples:\n• Elephant is HEAVY\n• Feather is LIGHT\n• Book is HEAVIER than paper"
            )
            18 -> Pair(
                "Counting bigger numbers:\n\n• Count from 20 to 50\n• Count by tens: 10, 20, 30, 40, 50\n• Practice every day",
                "Examples:\n• 21, 22, 23, 24, 25...\n• 30, 31, 32, 33, 34...\n• Count your steps to 50"
            )
            19 -> Pair(
                "Number patterns:\n\n• Skip counting by 2s: 2, 4, 6, 8\n• Skip counting by 5s: 5, 10, 15, 20\n• Look for patterns",
                "Examples:\n• 2, 4, 6, 8, 10\n• 5, 10, 15, 20, 25\n• 10, 20, 30, 40, 50"
            )
            20 -> Pair(
                "Making color patterns:\n\n• Patterns repeat in order\n• Red, Blue, Red, Blue...\n• What comes next?",
                "Examples:\n• Red, Blue, Red, Blue, ?\n• Yellow, Green, Yellow, Green, ?\n• Make your own pattern"
            )
            21 -> Pair(
                "Shape patterns:\n\n• Shapes can make patterns too\n• Circle, Square, Circle, Square...\n• Find the pattern",
                "Examples:\n• Circle, Square, Circle, ?\n• Triangle, Circle, Triangle, ?\n• Big, Small, Big, Small, ?"
            )
            22 -> Pair(
                "Understanding money:\n\n• Money is used to buy things\n• Different coins have different values\n• Rupee 1, 2, 5, 10 coins",
                "Examples:\n• ₹1 coin\n• ₹5 coin\n• ₹10 note\n• Count your money"
            )
            23 -> Pair(
                "Using money to buy:\n\n• Choose what to buy\n• Count your money\n• Pay the right amount",
                "Examples:\n• Toy costs ₹5, you have ₹10\n• Candy costs ₹2\n• Book costs ₹15"
            )
            24 -> Pair(
                "Making equal groups:\n\n• Put same number in each group\n• 6 toys in 2 groups = 3 in each\n• Share equally",
                "Examples:\n• 4 apples, 2 children = 2 each\n• 6 pencils, 3 groups = 2 each\n• 8 books, 4 shelves = 2 each"
            )
            25 -> Pair(
                "Adding same numbers:\n\n• 2+2+2 = 6 (three 2s)\n• 3+3+3+3 = 12 (four 3s)\n• Count in groups",
                "Examples:\n• 2+2+2 = 6\n• 3+3 = 6\n• 5+5 = 10"
            )
            26 -> Pair(
                "Understanding time:\n\n• Day and Night are different\n• Sun comes in day, moon at night\n• We do different things",
                "Examples:\n• We wake up in MORNING\n• We eat lunch in AFTERNOON\n• We sleep at NIGHT"
            )
            27 -> Pair(
                "Time sequence:\n\n• Yesterday was before today\n• Today is now\n• Tomorrow comes after today",
                "Examples:\n• Yesterday I played\n• Today I am learning\n• Tomorrow I will visit grandma"
            )
            28 -> Pair(
                "Organizing information:\n\n• Count different types of things\n• Put information in order\n• Make lists",
                "Examples:\n• 5 red cars, 3 blue cars\n• 4 boys, 6 girls\n• 2 cats, 3 dogs"
            )
            29 -> Pair(
                "Comparing groups:\n\n• Which group has more?\n• Which group has less?\n• Are they equal?",
                "Examples:\n• 6 cars, 4 bikes - cars are MORE\n• 3 books, 7 pens - books are LESS\n• 5 balls, 5 bats - they are EQUAL"
            )
            else -> Pair(
                "Learning content for this topic.",
                "Examples will be shown here."
            )
        }
    }

    // Get the same allTopics list from Class1Activity
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
}

// Data classes
data class LearningTopic(
    val id: Int,
    val title: String,
    val description: String
)

// TopicsOverviewAdapter - Shows the list of all topics
class TopicsOverviewAdapter(
    private val topics: List<LearningTopic>
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
    }

    override fun getItemCount() = topics.size
}