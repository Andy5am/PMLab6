package com.example.lab6

import android.app.Service
import android.content.Intent
import android.os.IBinder
import sun.audio.AudioPlayer.player
import java.util.Random;
import android.app.Notification;
import android.app.PendingIntent;



class MusicService : Service() {
    override fun onBind(arg0: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }

    fun getPosn(): Int {
        return player.getCurrentPosition()
    }

    fun getDur(): Int {
        return player.getDuration()
    }

    fun isPng(): Boolean {
        return player.isPlaying()
    }

    fun pausePlayer() {
        player.pause()
    }

    fun seek(posn: Int) {
        player.seekTo(posn)
    }

    fun go() {
        player.start()
    }
    fun playPrev():Unit{
        songPosn--
        if(songPosn&lt;0) songPosn=songs.size()-1;
        playSong();
    }
    //skip to next
    fun playNext():Unit{
        songPosn++;
        if(songPosn&gt;=songs.size()) songPosn=0;
        playSong();
    }
}