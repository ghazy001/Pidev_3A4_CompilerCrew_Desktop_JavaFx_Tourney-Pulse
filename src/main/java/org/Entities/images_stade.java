package org.Entities;

public class images_stade {
    public int id,id_stade;
    public String url_image;

    public images_stade() {
    }

    public images_stade(int id_stade, String url_image) {
        this.id_stade = id_stade;
        this.url_image = url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_stade() {
        return id_stade;
    }

    public void setId_stade(int id_stade) {
        this.id_stade = id_stade;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    @Override
    public String toString() {
        return "images_stade{" +
                "id=" + id +
                ", id_stade=" + id_stade +
                ", url_image='" + url_image + '\'' +
                '}';
    }
}
