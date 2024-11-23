import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inwakeapp.navigation.Screen
import com.example.inwakeapp.viewmodel.NotesViewModel
import com.example.inwakeapp.views.HomeScreen
import com.example.inwakeapp.views.ListNotesScreen
import com.example.inwakeapp.views.RegisterNoteScreen
import com.example.inwakeapp.views.UpdateNoteScreen

@Composable
fun MainNav(navController: NavHostController, notesViewModel: NotesViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        // RegisterNote
        composable(Screen.RegisterNote.route) {
            RegisterNoteScreen(navController, notesViewModel)
        }

        // ListNotes
        composable(Screen.ListNotesModule.route) {
            ListNotesScreen(navController, notesViewModel)
        }

        // ModifierNote
        composable(
            route = "${Screen.ModifierNote.route}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            UpdateNoteScreen(navController, notesViewModel, noteId)
        }
    }
}
