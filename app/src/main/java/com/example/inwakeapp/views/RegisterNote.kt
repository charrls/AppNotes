package com.example.inwakeapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inwakeapp.R
import com.example.inwakeapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var colorCode by remember { mutableStateOf(1) }
    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .background(colorResource(id = R.color.Background)),
        containerColor = colorResource(id = R.color.Background),
        topBar = {
            TopAppBar(
                title = { Text(text = "Register Note", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {  navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            tint = Color.White,
                            contentDescription = "Notifications"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.Primary)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            FormNotes(
                title = title,
                onTitleChange = { title = it },
                description = description,
                onDescriptionChange = { description = it },
                colorCode = colorCode,
                onColorChange = { colorCode = it },
                modifier = Modifier.weight(1f)
            )
            ButtonSave(
                onSave = {
                    viewModel.updateNoteFields(
                        title.text,
                        description.text,
                        colorCode
                    )
                    viewModel.insertNote()
                    navController.popBackStack()

                },
                modifier = Modifier.padding(bottom = 62.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormNotes(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    colorCode: Int,
    onColorChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Title")
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors( containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text("Description")
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 10,
            maxLines = 11,
            colors = TextFieldDefaults.outlinedTextFieldColors( containerColor = Color.White)

        )
        Spacer(modifier = Modifier.height(8.dp))

        ColorPickerRow(
            selectedColor = colorCode,
            onColorChange = onColorChange
        )
    }
}

@Composable
fun ColorPickerRow(
    selectedColor: Int,
    onColorChange: (Int) -> Unit
) {
    val colorOptions = listOf(
        colorResource(id = R.color.Color1),
        colorResource(id = R.color.Color2),
        colorResource(id = R.color.Color3),
        colorResource(id = R.color.Color4),
        colorResource(id = R.color.Color5))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        colorOptions.forEachIndexed { index, color ->
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .background(color, shape = CircleShape)
                    .border(
                        width = if (selectedColor == index + 1) 2.dp else 0.dp,
                        color = if (selectedColor == index + 1) Color.Gray else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onColorChange(index + 1) }
            )
        }
    }
}

@Composable
fun ButtonSave(
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSave,
        modifier = modifier
            .width(180.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Primary))
    ) {
        Text("Save")
    }
}
