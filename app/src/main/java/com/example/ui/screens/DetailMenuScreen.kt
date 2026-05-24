package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMenuScreen(
    viewModel: MainViewModel,
    menuId: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val menuItem = viewModel.menuList.find { it.id == menuId }
    val scrollState = rememberScrollState()

    // Extra functional interactive state (Vibe Coding [3] & State Management)
    var isLiked by remember { mutableStateOf(false) }
    var userRating by remember { mutableStateOf(0) }
    var ratingSubmittedMessage by remember { mutableStateOf("") }

    if (menuItem == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Menu tidak ditemukan", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateBack) {
                    Text(text = "Kembali ke Menu")
                }
            }
        }
        return
    }

    Scaffold(
        bottomBar = {
            // "Kembali ke Menu" sticky action button
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("back_to_menu_button"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Kembali ke Menu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            // Full Screen Bleed / Image Banner Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = menuItem.imageUrl,
                    contentDescription = menuItem.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark elegant overlay at top-bottom
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f)
                                )
                            )
                        )
                )

                // Floating Back Button Over Image
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Menu",
                        tint = Color.White
                    )
                }

                // Floating Favorite Icon
                IconButton(
                    onClick = { 
                        isLiked = !isLiked 
                        val toastMsg = if (isLiked) "Ditambahkan ke Favorit ❤️" else "Dihapus dari Favorit"
                        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Sukai menu ini",
                        tint = if (isLiked) Color.Red else Color.White
                    )
                }
            }

            // Content Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                // Category badge
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = menuItem.category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Menu Name & Price Row
                Text(
                    text = menuItem.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = menuItem.formattedPrice,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quality Rating Indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${menuItem.rating} / 5.0",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "Rasa Dijamin Enak 👍",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Description Title
                Text(
                    text = "Deskripsi Hidangan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = menuItem.description,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(16.dp))

                // Custom Vibe Coding Item [3]: Interactive Star Rating Panel
                Text(
                    text = "Berikan Rating Anda",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ketuk bintang untuk memberikan ulasan rasa",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Interactive Stars
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 1..5) {
                        IconButton(
                            onClick = {
                                userRating = i
                                ratingSubmittedMessage = "Terima kasih atas rating $i Bintang anda! ⭐"
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (i <= userRating) Icons.Default.Star else Icons.Outlined.StarOutline,
                                contentDescription = "$i Star Rating",
                                tint = if (i <= userRating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                // Toast/Subtext message on rating
                AnimatedVisibility(
                    visible = ratingSubmittedMessage.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ratingSubmittedMessage,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
