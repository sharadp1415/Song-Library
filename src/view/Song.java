/**
 * @author: Naman Bajaj and Sharad Prasad
 */

package view;

public class Song {
    String name;
    String artist;
    String album;
    int year;

    public Song() {
        this.name = null;
        this.artist = null;
        this.album = null;
        this.year = -1;
    }

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.album = null;
        this.year = -1;
    }

    public Song(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = -1;
    }

    public Song(String name, String artist, int year) {
        this.name = name;
        this.artist = artist;
        this.album = null;
        this.year = year;
    }

    public Song(String name, String artist, String album, int year) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public String toString() {
        String s = "";
        s = s + this.name;
        s = s + "|" + this.artist;
        s = this.album != null ? s + "|" + this.album : s;
        s = this.year != -1 ? s + "|" + this.year : s;
        return s;
    }

    public int compareTo(Song s) {
        return this.name.equalsIgnoreCase(s.name) ? this.artist.compareToIgnoreCase(s.artist)
                : this.name.compareToIgnoreCase(s.name);
    }

}
