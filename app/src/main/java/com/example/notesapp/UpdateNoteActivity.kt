package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id", 0)

        val note = db.getNoteById(noteId)
        note?.let {
            binding.updateTitleEditText.setText(it.title)
            binding.updateContentEditText.setText(it.content)
        }

        binding.updateSaveButton.setOnClickListener {
            val title = binding.updateTitleEditText.text.toString().trim()
            val content = binding.updateContentEditText.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val updatedNote = Note(noteId, title, content)
                db.updateNote(updatedNote)
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
