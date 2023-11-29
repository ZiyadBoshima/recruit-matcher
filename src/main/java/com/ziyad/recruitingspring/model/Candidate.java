package com.ziyad.recruitingspring.model;

import com.ziyad.recruitingspring.utility.Json;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "candidates")
public class Candidate {

    @Id
    private ObjectId id;
    private String name;
    private List<String> skills;
    private int yearsOfExperience;

    public Candidate(String name, List<String> skills, int yearsOfExperience) {
        this.name = name;
        this.skills = skills;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Candidate(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.skills = Json.jsonToList(jsonObject.getJSONArray("skills"));
            this.yearsOfExperience = jsonObject.getInt("yearsOfExperience");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}