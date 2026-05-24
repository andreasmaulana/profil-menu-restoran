package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val currentProfile = viewModel.profile.collectAsState().value
    val scrollState = rememberScrollState()

    // Form Field States
    var name by remember { mutableStateOf(currentProfile.name) }
    var address by remember { mutableStateOf(currentProfile.address) }
    var description by remember { mutableStateOf(currentProfile.description) }
    var openingHours by remember { mutableStateOf(currentProfile.openingHours) }

    // Error validation states
    var nameError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var openingHoursError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profil Restoran",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Batal dan Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(bottom = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Form container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Intro text
                Text(
                    text = "Perbarui informasi profil restoran Anda agar pelanggan mendapatkan info terbaru.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    lineHeight = 22.sp
                )

                // Name field
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.trim().isEmpty()
                    },
                    label = { Text("Nama Restoran") },
                    placeholder = { Text("Masukkan nama restoran") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = "Store icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    isError = nameError,
                    supportingText = {
                        if (nameError) {
                            Text("Nama restoran tidak boleh kosong!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_resto_name"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Address field
                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        addressError = it.trim().isEmpty()
                    },
                    label = { Text("Alamat") },
                    placeholder = { Text("Masukkan alamat restoran") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Address icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    isError = addressError,
                    supportingText = {
                        if (addressError) {
                            Text("Alamat tidak boleh kosong!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_resto_address"),
                    shape = RoundedCornerShape(12.dp)
                )

                // Opening hours field
                OutlinedTextField(
                    value = openingHours,
                    onValueChange = {
                        openingHours = it
                        openingHoursError = it.trim().isEmpty()
                    },
                    label = { Text("Jam Buka") },
                    placeholder = { Text("Contoh: 09:00 - 22:00 WIB") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Hours icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    isError = openingHoursError,
                    supportingText = {
                        if (openingHoursError) {
                            Text("Jam operasional tidak boleh kosong!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_resto_hours"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = it.trim().isEmpty()
                    },
                    label = { Text("Deskripsi") },
                    placeholder = { Text("Tulis deskripsi singkat restoran Anda") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Bio icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    isError = descriptionError,
                    supportingText = {
                        if (descriptionError) {
                            Text("Deskripsi tidak boleh kosong!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("input_resto_desc"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Row buttons: Batal and Simpan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Button Batal (Cancel)
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .testTag("cancel_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Batal icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Batal",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Button Simpan (Save)
                    Button(
                        onClick = {
                            // Trim inputs
                            val n = name.trim()
                            val a = address.trim()
                            val o = openingHours.trim()
                            val d = description.trim()

                            nameError = n.isEmpty()
                            addressError = a.isEmpty()
                            openingHoursError = o.isEmpty()
                            descriptionError = d.isEmpty()

                            if (n.isNotEmpty() && a.isNotEmpty() && o.isNotEmpty() && d.isNotEmpty()) {
                                viewModel.saveProfile(
                                    name = n,
                                    address = a,
                                    description = d,
                                    openingHours = o
                                )
                                Toast.makeText(context, "Profil berhasil disimpan! ✨", Toast.LENGTH_SHORT).show()
                                onNavigateBack() // Go back automatically
                            } else {
                                Toast.makeText(context, "Harap lengkapi semua isian form!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .testTag("save_button"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Simpan icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Simpan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
