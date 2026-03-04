package dam_a51694.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //println(this@MainActivity.localClassName + " onCreate")
        // System.out   I  MainActivity onCreate
        println(getString(R.string.activity_oncreate_msg, this@MainActivity.localClassName))
        // A mensagem agora usa o formato Activity %1$s onCreate do strings.xml, onde %1$s foi substituído por MainActivity
        // 4820-4820  System.out              dam_a51694.helloworld                I   Activity MainActivity onCreate

        // botão dark mode
        val btnDarkMode = findViewById<Button>(R.id.btnDarkMode)
        btnDarkMode.setOnClickListener {
            val currentMode = AppCompatDelegate.getDefaultNightMode()
            if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btnDarkMode.text = "Toggle Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btnDarkMode.text = "Toggle Light Mode"
            }
        }

        // botão para alterar titulo aleatoriamente
        val frases = listOf(
            "Hello Android World!",
            "Kotlin!",
            "Mobile Dev!",
            "Keep coding!",
            "Hello DAM!"
        )

        val tvHello = findViewById<TextView>(R.id.textHello) // id do teu TextView principal
        val btnChangeText = findViewById<Button>(R.id.btnChangeText)

        btnChangeText.setOnClickListener {
            tvHello.text = frases.random()
        }
    }
}