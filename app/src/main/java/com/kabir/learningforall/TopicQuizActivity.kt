// TopicQuizActivity.kt - Complete implementation with actual quiz content
package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class TopicQuizActivity : AppCompatActivity() {

    private var currentTopicNumber = 1
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<QuizQuestion>
    private var selectedAnswer = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_quiz)

        currentTopicNumber = intent.getIntExtra("topic_number", 1)
        setupQuiz()
        loadQuestion()
    }

    private fun setupQuiz() {
        questions = getQuizQuestions(currentTopicNumber)

        findViewById<TextView>(R.id.quizTitle).text =
            "Topic $currentTopicNumber Quiz"

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.nextButton).setOnClickListener {
            if (selectedAnswer != -1) {
                checkAnswerAndProceed()
            }
        }

        // Option click listeners
        findViewById<CardView>(R.id.option1).setOnClickListener { selectOption(0) }
        findViewById<CardView>(R.id.option2).setOnClickListener { selectOption(1) }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]

            findViewById<TextView>(R.id.questionText).text = question.question
            findViewById<TextView>(R.id.option1Text).text = question.option1
            findViewById<TextView>(R.id.option2Text).text = question.option2
            findViewById<TextView>(R.id.progressText).text =
                "${currentQuestionIndex + 1}/${questions.size}"

            resetSelection()
        } else {
            finishQuiz()
        }
    }

    private fun selectOption(optionIndex: Int) {
        selectedAnswer = optionIndex

        // Visual feedback
        val option1 = findViewById<CardView>(R.id.option1)
        val option2 = findViewById<CardView>(R.id.option2)

        option1.setCardBackgroundColor(getColor(R.color.white))
        option2.setCardBackgroundColor(getColor(R.color.white))

        if (optionIndex == 0) {
            option1.setCardBackgroundColor(getColor(R.color.primary_blue_light))
        } else {
            option2.setCardBackgroundColor(getColor(R.color.primary_blue_light))
        }

        findViewById<MaterialButton>(R.id.nextButton).isEnabled = true
    }

    private fun resetSelection() {
        selectedAnswer = -1
        findViewById<CardView>(R.id.option1).setCardBackgroundColor(getColor(R.color.white))
        findViewById<CardView>(R.id.option2).setCardBackgroundColor(getColor(R.color.white))
        findViewById<MaterialButton>(R.id.nextButton).isEnabled = false
    }

    private fun checkAnswerAndProceed() {
        val question = questions[currentQuestionIndex]
        val isCorrect = selectedAnswer == question.correctAnswer

        if (isCorrect) {
            score++
            Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Try again next time!", Toast.LENGTH_SHORT).show()
        }

        currentQuestionIndex++

        // Small delay before next question
        findViewById<TextView>(R.id.questionText).postDelayed({
            loadQuestion()
        }, 1000)
    }

    private fun finishQuiz() {
        val percentage = (score * 100) / questions.size

        // Save progress
        val prefs = getSharedPreferences("class1_progress", 0)
        val editor = prefs.edit()

        // Mark topic as completed
        editor.putBoolean("topic_${currentTopicNumber}_completed", true)
        editor.putInt("topic_${currentTopicNumber}_score", percentage)

        // Update current topic to next one
        if (currentTopicNumber < 29) {
            editor.putInt("current_topic", currentTopicNumber + 1)
        }

        // Update completed count
        var completedCount = 0
        for (i in 1..29) {
            if (prefs.getBoolean("topic_${i}_completed", false)) {
                completedCount++
            }
        }
        editor.putInt("completed_topics", completedCount)
        editor.apply()

        // Show results and proceed to next topic
        showQuizResults(percentage)
    }

    private fun showQuizResults(percentage: Int) {
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("total", questions.size)
        intent.putExtra("percentage", percentage)
        intent.putExtra("current_topic", currentTopicNumber)
        startActivity(intent)
        finish()
    }

    private fun getQuizQuestions(topicNumber: Int): List<QuizQuestion> {
        return when(topicNumber) {
            1 -> listOf( // Position Words
                QuizQuestion("Where is the bird?", "Above the tree", "Below the tree", 0),
                QuizQuestion("Fish live:", "Inside water", "Outside water", 0),
                QuizQuestion("Where do we keep shoes?", "Inside house", "Outside house", 1)
            )
            2 -> listOf( // Before and After
                QuizQuestion("What comes BEFORE 5?", "4", "6", 0),
                QuizQuestion("What comes AFTER 7?", "6", "8", 1),
                QuizQuestion("We eat __ we sleep", "Before", "After", 0)
            )
            3 -> listOf( // Sorting
                QuizQuestion("We group apples with:", "Other apples", "Cars", 0),
                QuizQuestion("Sorting means:", "Mixing things", "Grouping similar things", 1),
                QuizQuestion("Books should go with:", "Other books", "Toys", 0)
            )
            4 -> listOf( // Shapes
                QuizQuestion("A ball is:", "Round", "Long", 0),
                QuizQuestion("A pencil is:", "Round", "Long", 1),
                QuizQuestion("A coin is:", "Round", "Long", 0)
            )
            5 -> listOf( // Rolling/Sliding
                QuizQuestion("What can roll?", "Book", "Ball", 1),
                QuizQuestion("What can slide?", "Flat book", "Round ball", 0),
                QuizQuestion("A ball can:", "Only slide", "Only roll", 1)
            )
            6 -> listOf( // Counting 1-9
                QuizQuestion("How many fingers on one hand?", "4", "5", 1),
                QuizQuestion("What comes after 6?", "5", "7", 1),
                QuizQuestion("What comes before 4?", "3", "5", 0)
            )
            7 -> listOf( // More/Less
                QuizQuestion("Which is more?", "3", "7", 1),
                QuizQuestion("Which is less?", "2", "6", 0),
                QuizQuestion("5 is __ than 8", "More", "Less", 1)
            )
            8 -> listOf( // Number Order
                QuizQuestion("What comes after 8?", "7", "9", 1),
                QuizQuestion("Put in order: 3,1,2", "1,2,3", "3,2,1", 0),
                QuizQuestion("Which is smallest?", "1", "9", 0)
            )
            9 -> listOf( // Teen Numbers
                QuizQuestion("What comes after 19?", "18", "20", 1),
                QuizQuestion("How do we write ten?", "01", "10", 1),
                QuizQuestion("Fifteen is written as:", "51", "15", 1)
            )
            10 -> listOf( // Number Writing
                QuizQuestion("Which number is this: 7?", "Six", "Seven", 1),
                QuizQuestion("How do we write 'three'?", "3", "30", 0),
                QuizQuestion("Which is correct?", "4", "backwards 4", 0)
            )
            11 -> listOf( // Addition
                QuizQuestion("2 + 1 = ?", "2", "3", 1),
                QuizQuestion("3 + 2 = ?", "5", "4", 0),
                QuizQuestion("1 + 4 = ?", "5", "6", 0)
            )
            12 -> listOf( // Addition Practice
                QuizQuestion("4 + 3 = ?", "7", "6", 0),
                QuizQuestion("2 + 6 = ?", "8", "7", 0),
                QuizQuestion("5 + 2 = ?", "6", "7", 1)
            )
            13 -> listOf( // Subtraction
                QuizQuestion("5 - 2 = ?", "3", "7", 0),
                QuizQuestion("4 - 1 = ?", "3", "5", 0),
                QuizQuestion("6 - 3 = ?", "3", "9", 0)
            )
            14 -> listOf( // Subtraction Practice
                QuizQuestion("8 - 3 = ?", "5", "6", 0),
                QuizQuestion("7 - 4 = ?", "3", "4", 0),
                QuizQuestion("9 - 2 = ?", "7", "6", 0)
            )
            15 -> listOf( // Long/Short
                QuizQuestion("Which is longer?", "Pencil", "Eraser", 0),
                QuizQuestion("A bus is __ than a car", "Longer", "Shorter", 0),
                QuizQuestion("Which is shorter?", "Snake", "Ant", 1)
            )
            16 -> listOf( // Tall/Short
                QuizQuestion("Who is taller?", "Adult", "Child", 0),
                QuizQuestion("Which is tallest?", "Tree", "Flower", 0),
                QuizQuestion("A mouse is __ than elephant", "Taller", "Shorter", 1)
            )
            17 -> listOf( // Heavy/Light
                QuizQuestion("Which is heavier?", "Feather", "Book", 1),
                QuizQuestion("Which is lighter?", "Elephant", "Butterfly", 1),
                QuizQuestion("A car is __ than bicycle", "Heavier", "Lighter", 0)
            )
            18 -> listOf( // Counting to 50
                QuizQuestion("What comes after 29?", "30", "28", 0),
                QuizQuestion("What comes before 40?", "39", "41", 0),
                QuizQuestion("How do we write forty?", "04", "40", 1)
            )
            19 -> listOf( // Number Patterns
                QuizQuestion("2, 4, 6, what comes next?", "8", "7", 0),
                QuizQuestion("5, 10, 15, what comes next?", "20", "16", 0),
                QuizQuestion("10, 20, 30, what comes next?", "40", "35", 0)
            )
            20 -> listOf( // Color Patterns
                QuizQuestion("Red, Blue, Red, Blue, what's next?", "Red", "Blue", 0),
                QuizQuestion("Green, Yellow, Green, what's next?", "Yellow", "Green", 0),
                QuizQuestion("In patterns, we __ colors", "Mix", "Repeat", 1)
            )
            21 -> listOf( // Shape Patterns
                QuizQuestion("Circle, Square, Circle, what's next?", "Square", "Triangle", 0),
                QuizQuestion("Big, Small, Big, what's next?", "Small", "Big", 0),
                QuizQuestion("Patterns help us __ what comes next", "Guess", "Know", 1)
            )
            22 -> listOf( // Money
                QuizQuestion("Which is more money?", "₹5", "₹2", 0),
                QuizQuestion("We use money to:", "Play", "Buy things", 1),
                QuizQuestion("₹10 is __ than ₹1", "More", "Less", 0)
            )
            23 -> listOf( // Buying Things
                QuizQuestion("Toy costs ₹5, you have ₹10. Money left?", "₹5", "₹15", 0),
                QuizQuestion("If candy costs ₹2, you need:", "₹2", "₹20", 0),
                QuizQuestion("₹3 + ₹4 = ?", "₹7", "₹1", 0)
            )
            24 -> listOf( // Making Groups
                QuizQuestion("6 toys, 2 groups. How many in each?", "3", "4", 0),
                QuizQuestion("8 books, 4 shelves. How many per shelf?", "2", "3", 0),
                QuizQuestion("Equal groups means __ in each", "Same number", "Different numbers", 0)
            )
            25 -> listOf( // Repeated Addition
                QuizQuestion("2 + 2 + 2 = ?", "6", "4", 0),
                QuizQuestion("3 + 3 = ?", "6", "9", 0),
                QuizQuestion("5 + 5 = ?", "10", "15", 0)
            )
            26 -> listOf( // Day/Night
                QuizQuestion("When do we see the sun?", "Day", "Night", 0),
                QuizQuestion("When do we sleep?", "Day", "Night", 1),
                QuizQuestion("We wake up in the:", "Morning", "Evening", 0)
            )
            27 -> listOf( // Time Sequence
                QuizQuestion("What comes after today?", "Yesterday", "Tomorrow", 1),
                QuizQuestion("What came before today?", "Yesterday", "Tomorrow", 0),
                QuizQuestion("Right now is:", "Today", "Yesterday", 0)
            )
            28 -> listOf( // Counting Objects
                QuizQuestion("5 red cars, 3 blue cars. Total cars?", "8", "2", 0),
                QuizQuestion("How many types of animals: 2 cats, 3 dogs?", "2", "5", 0),
                QuizQuestion("Organizing means putting things in:", "Order", "Boxes", 0)
            )
            29 -> listOf( // Comparing Groups
                QuizQuestion("6 cars, 4 bikes. Which is more?", "Cars", "Bikes", 0),
                QuizQuestion("3 books, 3 pens. They are:", "More", "Equal", 1),
                QuizQuestion("2 apples, 5 oranges. Which is less?", "Apples", "Oranges", 0)
            )
            else -> listOf(
                QuizQuestion("Sample question", "Option A", "Option B", 0)
            )
        }
    }
}

// QuizResultActivity.kt - Shows results and navigates to next topic
class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)
        val percentage = intent.getIntExtra("percentage", 0)
        val currentTopic = intent.getIntExtra("current_topic", 1)

        findViewById<TextView>(R.id.scoreText).text = "$score/$total"
        findViewById<TextView>(R.id.percentageText).text = "$percentage%"

        val message = when {
            percentage >= 80 -> "Excellent! Well done!"
            percentage >= 60 -> "Good job! Keep it up!"
            else -> "Keep practicing! You're learning!"
        }
        findViewById<TextView>(R.id.messageText).text = message

        val nextButton = findViewById<MaterialButton>(R.id.nextTopicButton)

        if (currentTopic >= 29) {
            nextButton.text = "🎉 Course Complete! 🎉"
            nextButton.setOnClickListener {
                // Go back to dashboard - all topics completed
                finish()
            }
        } else {
            nextButton.text = "Next Topic"
            nextButton.setOnClickListener {
                // Go to next topic
                val intent = Intent(this, TopicLearningActivity::class.java)
                intent.putExtra("topic_number", currentTopic + 1)
                startActivity(intent)
                finish()
            }
        }

        findViewById<MaterialButton>(R.id.backToDashboard).setOnClickListener {
            // Go back to Class 1 overview
            finish()
        }
    }
}

data class QuizQuestion(
    val question: String,
    val option1: String,
    val option2: String,
    val correctAnswer: Int // 0 for option1, 1 for option2
)