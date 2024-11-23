package com.example.inwakeapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inwakeapp.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.inwakeapp.model.Note
import com.example.inwakeapp.navigation.Screen
import com.example.inwakeapp.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.Background))
            .systemBarsPadding(),
        containerColor = colorResource(id = R.color.Background),
        topBar = {
            TopAppBar(
                title = { Text(text = "List Notes", fontSize = 20.sp, color = Color.White) },
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.RegisterNote.route) },
                containerColor = colorResource(id = R.color.Primary),
                modifier = Modifier.padding(bottom = 25.dp, end = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Note",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .padding(paddingValues)
        ) {
            icon()
            Box(modifier = Modifier.fillMaxSize()) {
                ViewListNotesContent(
                    navController,
                    modifier = Modifier.padding(bottom =120.dp),
                    notes = notesViewModel.noteListState.collectAsState().value
                )
            }
        }
    }
}

@Composable
fun icon(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.notes),
            contentDescription = "Notes Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 12.dp)
        )
    }
}

@Composable
fun ViewListNotesContent(navController: NavController,modifier: Modifier = Modifier, notes: List<Note>) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 36.dp)
            .padding(vertical = 20.dp)
    ) {
        items(notes) { note ->
            NoteCard(navController, note)
        }
    }
}

@Composable
fun NoteCard(navController: NavController,note: Note) {
    val backgroundColor = when (note.color) {
        1 -> colorResource(id = R.color.Color1)
        2 -> colorResource(id = R.color.Color2)
        3 -> colorResource(id = R.color.Color3)
        4 -> colorResource(id = R.color.Color4)
        5 -> colorResource(id = R.color.Color5)
        else -> Color.Gray
    }

    Card(
        onClick = { navController.navigate("modifier_notes/${note.id}") },
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(0.65f)
                    .clip(MaterialTheme.shapes.small) ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = note.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.description,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
            }
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(0.35f)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.small)
                    .background(color = backgroundColor)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = SimpleDateFormat("dd MMM yyyy HH:mm").format(Date(note.date)),
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(end = 8.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
