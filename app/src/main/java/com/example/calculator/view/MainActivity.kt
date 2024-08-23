package com.example.calculator.view

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.R
import com.example.calculator.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gridLayout = findViewById<GridLayout>(R.id.keypad_grid)
        val historyTextView = findViewById<TextView>(R.id.history)
        val resultTextView = findViewById<TextView>(R.id.result)

        val buttonLabels = listOf(
            "Clear", "<", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
        )

        buttonLabels.forEach { label ->
            val button = Button(this).apply {
                text = label
                textSize = 18f
                elevation = 4f

                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setGravity(Gravity.FILL)
                    setMargins(8, 8, 8, 8)
                }

                background = ContextCompat.getDrawable(context, R.drawable.button_background)

                setOnClickListener {
                    viewModel.onButtonClick(label)
                }
            }

            if (label == "0" || label == "Clear") {
                button.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(0, 2, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setGravity(Gravity.FILL)
                    setMargins(8, 8, 8, 8)
                }
            }

            gridLayout.addView(button)
        }

        viewModel.displayText.observe(this, Observer { text ->
            resultTextView.text = text
        })

        viewModel.historyText.observe(this, Observer { text ->
            historyTextView.text = text
        })
    }
}
