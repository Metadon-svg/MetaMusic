
package com.metamusic.app

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ImportMusicActivity : AppCompatActivity() {

    private var player: MediaPlayer? = null

    private val picker = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            play(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_music)

        findViewById<Button>(R.id.btnImport).setOnClickListener {
            picker.launch(arrayOf("audio/*"))
        }
    }

    private fun play(uri: Uri) {
        player?.release()
        player = MediaPlayer().apply {
            setDataSource(this@ImportMusicActivity, uri)
            prepare()
            start()
        }
    }

    override fun onDestroy() {
        player?.release()
        super.onDestroy()
    }
}
