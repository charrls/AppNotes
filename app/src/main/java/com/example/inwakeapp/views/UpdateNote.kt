package com.example.inwakeapp.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inwakeapp.R
import com.example.inwakeapp.model.Note
import com.example.inwakeapp.viewmodel.NotesViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel,
    noteId: Int
) {
    val noteState = viewModel.getNoteById(noteId).collectAsState(initial = null)

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var colorCode by remember { mutableStateOf(1) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteState.value) {
        noteState.value?.let { note ->
            title = TextFieldValue(note.title)
            description = TextFieldValue(note.description)
            colorCode = note.color
        }
    }

    if (showDeleteDialog) {
        noteState.value?.let {
            ConfirmDeleteDialog(
                titleNote = it.title,
                onConfirmDelete = {
                    viewModel.deleteNoteById(noteId)
                    navController.popBackStack()
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .background(colorResource(id = R.color.Background)),
        containerColor = colorResource(id = R.color.Background),
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Note", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
            Column {
                Spacer(modifier = Modifier.height(80.dp))

                FormUpdateNotes(
                    title = title,
                    onTitleChange = { title = it },
                    description = description,
                    onDescriptionChange = { description = it },
                    colorCode = colorCode,
                    onColorChange = { colorCode = it },
                    onDelete = { showDeleteDialog = true }
                )
            }

            ButtonUpdate(
                onSave = {
                    val updatedNote = Note(
                        id = noteId,
                        title = title.text,
                        description = description.text,
                        date = System.currentTimeMillis(),
                        color = colorCode
                    )
                    viewModel.updateNote(updatedNote)
                    navController.popBackStack()
                },
                modifier = Modifier.padding(bottom = 62.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUpdateNotes(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    colorCode: Int,
    onColorChange: (Int) -> Unit,
    onDelete: () -> Unit,
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

        ColorUpdatePickerRow(
            onDelete = onDelete,
            selectedColor = colorCode,
            onColorChange = onColorChange
        )
    }
}

@Composable
fun ColorUpdatePickerRow(
    onDelete: () -> Unit,
    selectedColor: Int,
    onColorChange: (Int) -> Unit
) {
    val colorOptions = listOf(
        colorResource(id = R.color.Color1),
        colorResource(id = R.color.Color2),
        colorResource(id = R.color.Color3),
        colorResource(id = R.color.Color4),
        colorResource(id = R.color.Color5)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonDelete(onDelete = onDelete)

        Row {
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
}


@Composable
fun ButtonUpdate(
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSave,
        modifier = modifier
            .width(180.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Primary)),
    ) {
        Text("Update", color = Color.White, fontSize = 14.sp)
    }
}
@Composable
fun ButtonDelete(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onDelete,
        modifier = modifier
            .width(140.dp)
            .height(28.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Color5)),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("Delete", color = Color.White, fontSize = 12.sp)
    }
}


@Composable
fun ConfirmDeleteDialog(
    titleNote: String,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Eliminar nota",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"$titleNote",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Esta acción no se puede deshacer.",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(color = Color.LightGray, thickness = 1.dp)
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botón de "Cancelar"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismiss() }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    // Línea divisoria entre los botones
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(45.dp)
                            .background(Color.LightGray)
                    )

                    // Botón de "Eliminar"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onConfirmDelete()
                                onDismiss()  // Cierra el diálogo después de confirmar
                            }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Eliminar",
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}
