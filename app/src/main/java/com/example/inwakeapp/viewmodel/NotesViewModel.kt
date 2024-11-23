package com.example.inwakeapp.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.inwakeapp.model.Note
import com.example.inwakeapp.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    private var noteTitle: String = ""
    private var noteDescription: String = ""
    private var noteColorCode: Int = 1

    private val _noteListState = MutableStateFlow<List<Note>>(emptyList())
    val noteListState: StateFlow<List<Note>> = _noteListState

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _noteListState.value = notes
            }
        }
    }

    fun updateNoteFields(title: String, description: String, colorCode: Int) {
        noteTitle = title
        noteDescription = description
        noteColorCode = colorCode
    }

    fun getNoteById(noteId: Int): Flow<Note?> {
        return repository.getNoteById(noteId)
    }


    fun insertNote() {
        viewModelScope.launch {
            try {
                val newNote = Note(
                    title = noteTitle,
                    description = noteDescription,
                    date = System.currentTimeMillis(),
                    color = noteColorCode
                )
                repository.insert(newNote)
                getAllNotes()
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Error al insertar la nota: ${e.message}")
            }
        }
    }

    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNoteById(noteId)
                getAllNotes()
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Error al eliminar la nota: ${e.message}")
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.update(note)
                getAllNotes()
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Error al actualizar la nota: ${e.message}")
            }
        }
    }
}

