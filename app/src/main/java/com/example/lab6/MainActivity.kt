package com.example.lab6
import android.widget.MediaController.MediaPlayerControl
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import android.net.Uri
import android.content.ContentResolver
import android.database.Cursor
import android.view.View
import android.widget.ListView

class MainActivity : AppCompatActivity(), MediaPlayerControl {
    override fun getDuration(): Int {
        if(musicSrv!=null &amp;&amp; musicBound &amp;&amp; musicSrv.isPng())
        return musicSrv.getDur();
        else return 0;    }

    override fun pause() {
        musicSrv.pausePlayer()    }

    override fun getBufferPercentage(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seekTo(pos: Int) {
        musicSrv.seek(pos)    }

    override fun getCurrentPosition(): Int {
        if(musicSrv!=null &amp;&amp; musicBound &amp;&amp; musicSrv.isPng())
        return musicSrv.getPosn();
        else return 0;    }

    override fun canSeekBackward(): Boolean {
        return true    }

    override fun start() {
        musicSrv.go();    }

    override fun getAudioSessionId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun isPlaying(): Boolean {
        if(musicSrv!=null &amp;&amp; musicBound)
        return musicSrv.isPng();
    return false
    }

    private var songList: ArrayList<Song>? = null
    private var songView: ListView? = null
    var controller: MusicController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songView = findViewById<ListView>(R.id.song_list)
        songList = ArrayList()
        getSongList()
        songList!!.sortWith(Comparator { a, b -> a.getTitle().compareTo(b.getTitle()) })

        val songAdt = SongAdapter(this, songList)
        songView.adapter = songAdt

        setController()

    }

    fun getSongList() {
        //retrieve song info
        val musicResolver = contentResolver
        val musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor = musicResolver.query(musicUri, null, null, null, null)
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
            val idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
            val artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)
            //add songs to list
            do {
                val thisId = musicCursor.getLong(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = musicCursor.getString(artistColumn)
                songList!!.add(Song(thisId, thisTitle, thisArtist))
            } while (musicCursor.moveToNext())
        }
    }

    private fun setController() {
        //set the controller up
        controller = MusicController(this)
        controller!!.setPrevNextListeners(object : View.OnClickListener {
            override fun onClick(v: View) {
                playNext()
            }
        }, object : View.OnClickListener {
            override fun onClick(v: View) {
                playPrev()
            }
        })
        controller!!.setMediaPlayer(this);
        controller!!.setAnchorView(findViewById(R.id.song_list));
        controller!!.setEnabled(true);
    }

    //play next
    private fun playNext() {
        musicSrv.playNext()
        controller!!.show(0)
    }

    //play previous
    private fun playPrev() {
        musicSrv.playPrev()
        controller!!.show(0)
    }
}
