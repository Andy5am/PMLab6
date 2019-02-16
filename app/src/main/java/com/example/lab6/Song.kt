package com.example.lab6
class Song(songID: Long, songTitle: String, songArtist: String) {

    private var id: Long = 0
    private var title: String = ""
    private var artist: String = ""

    fun getID(): Long {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getArtist(): String {
        return artist
    }

}