package com.ziyad.recruitingspring.model;

import com.ziyad.recruitingspring.constants.Constants;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "jobs")
public class Job {

    @Id
    private ObjectId id;
    private String title;
    private String company;
    private String location;
    private Constants.JobType type;
    private Constants.Remote remote;
    private String description;

    public Job(String title, String company, String location, Constants.JobType type, Constants.Remote remote, String description) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.type = type;
        this.remote = remote;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Constants.JobType getType() {
        return type;
    }

    public void setType(Constants.JobType type) {
        this.type = type;
    }

    public Constants.Remote getRemote() {
        return remote;
    }

    public void setRemote(Constants.Remote remote) {
        this.remote = remote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}