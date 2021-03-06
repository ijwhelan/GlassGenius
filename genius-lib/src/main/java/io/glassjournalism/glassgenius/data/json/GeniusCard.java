package io.glassjournalism.glassgenius.data.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class GeniusCard {

    @Expose
    private String category;
    @Expose
    private Template template;
    @Expose
    private String name;
    @Expose
    private Variables variables;
    @Expose
    private List<String> triggerWords = new ArrayList<String>();
    @Expose
    private String createdAt;
    @Expose
    private String updatedAt;
    @Expose
    private String id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GeniusCard withCategory(String category) {
        this.category = category;
        return this;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public GeniusCard withTemplate(Template template) {
        this.template = template;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeniusCard withName(String name) {
        this.name = name;
        return this;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public GeniusCard withVariables(Variables variables) {
        this.variables = variables;
        return this;
    }

    public List<String> getTriggerWords() {
        return triggerWords;
    }

    public void setTriggerWords(List<String> triggerWords) {
        this.triggerWords = triggerWords;
    }

    public GeniusCard withTriggerWords(List<String> triggerWords) {
        this.triggerWords = triggerWords;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public GeniusCard withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GeniusCard withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeniusCard withId(String id) {
        this.id = id;
        return this;
    }
}