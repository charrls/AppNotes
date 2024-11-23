package com.example.inwakeapp.repository

import com.example.inwakeapp.data.NotesDAO
import com.example.inwakeapp.model.Note
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDAO: NotesDAO) {
    fun getAllNotes(): Flow<List<Note>> = notesDAO.getAllNotes()

    suspend fun insert(note: Note) {
        notesDAO.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDAO.delete(note)
    }

    suspend fun deleteNoteById(noteId: Int) {
        notesDAO.deleteNoteById(noteId)
    }

    fun getNoteById(noteId: Int): Flow<Note?> {
        return notesDAO.getNoteById(noteId)
    }

    suspend fun update(note: Note) {
        notesDAO.update(note)
    }
}

