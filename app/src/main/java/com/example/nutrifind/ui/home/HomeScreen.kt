package com.example.nutrifind.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun HomeScreen(onGetStarted: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Floating food images
        FloatingFoodImages()
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            
            // Title
            Text(
                text = "Start Cooking With",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748),
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "NutriFind",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Subtitle
            Text(
                text = "Join our community",
                fontSize = 16.sp,
                color = Color(0xFF718096),
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "to cook better food!",
                fontSize = 16.sp,
                color = Color(0xFF718096),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Get Started Button
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun FloatingFoodImages() {
    // Create animated offsets for floating effect
    val infiniteTransition = rememberInfiniteTransition(label = "floating")
    
    // Different animations for each food item
    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset1"
    )
    
    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset2"
    )
    
    val offset3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset3"
    )
    
    val offset4 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset4"
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Top left - small
        FloatingFoodItem(
            modifier = Modifier
                .offset(x = 40.dp, y = 80.dp)
                .size(80.dp)
                .graphicsLayer { translationY = offset1 },
            color = Color(0xFF10B981)
        )
        
        // Top right - medium
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-40).dp, y = 60.dp)
                .size(100.dp)
                .graphicsLayer { translationY = offset2 },
            color = Color(0xFF3B82F6)
        )
        
        // Center left - large
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 20.dp, y = (-40).dp)
                .size(140.dp)
                .graphicsLayer { translationY = offset3 },
            color = Color(0xFFF59E0B)
        )
        
        // Center right - small
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-30).dp, y = (-60).dp)
                .size(70.dp)
                .graphicsLayer { translationY = offset4 },
            color = Color(0xFFEF4444)
        )
        
        // Bottom left - small
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 30.dp, y = (-200).dp)
                .size(75.dp)
                .graphicsLayer { translationY = offset1 },
            color = Color(0xFF8B5CF6)
        )
        
        // Bottom center - medium
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = (-50).dp, y = (-220).dp)
                .size(95.dp)
                .graphicsLayer { translationY = offset2 },
            color = Color(0xFF06B6D4)
        )
        
        // Bottom right - tiny
        FloatingFoodItem(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-40).dp, y = (-180).dp)
                .size(60.dp)
                .graphicsLayer { translationY = offset3 },
            color = Color(0xFFEC4899)
        )
    }
}

@Composable
fun FloatingFoodItem(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                ambientColor = color.copy(alpha = 0.3f),
                spotColor = color.copy(alpha = 0.3f)
            )
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder colored circle (you can replace with actual food images)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
        )
    }
}
