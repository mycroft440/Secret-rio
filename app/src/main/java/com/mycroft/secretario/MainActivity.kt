package com.mycroft.secretario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mycroft.secretario.ui.SecretarioApp
import com.mycroft.secretario.ui.SecretarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecretarioTheme {
                SecretarioApp()
            }
        }
    }
}
