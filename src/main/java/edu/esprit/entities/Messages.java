package edu.esprit.entities;

import java.sql.Date;

public class Messages {
    private int id;
    private int sender_id,receiver_id,reclamation_id;
    private String content;
    private Date date;
    private String name,role;

    public Messages(int id, String content, Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }
    public Messages() {

    }

    public Messages(int sender_id, int receiver_id, int reclamation_id, String content, Date date) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.reclamation_id = reclamation_id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                ", reclamation_id=" + reclamation_id +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
