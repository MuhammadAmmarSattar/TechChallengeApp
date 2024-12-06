package com.muhammad.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.muhammad.myapplication.forvia.core.presentation.navigation.MyNavigationHost
import com.muhammad.myapplication.ui.theme.TechChallengeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import vtsen.hashnode.dev.workmanagerdemo.ui.permission.PermissionNotification
import vtsen.hashnode.dev.workmanagerdemo.ui.permission.RuntimePermissionsDialog

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TechChallengeAppTheme {
                PermissionNotification()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyNavigationHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


