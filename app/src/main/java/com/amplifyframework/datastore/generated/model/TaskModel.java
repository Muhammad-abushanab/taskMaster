package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskModels", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class TaskModel implements Model {
  public static final QueryField ID = field("TaskModel", "id");
  public static final QueryField TITLE = field("TaskModel", "title");
  public static final QueryField DESCRIPTION = field("TaskModel", "description");
  public static final QueryField DATE_CREATED = field("TaskModel", "dateCreated");
  public static final QueryField STATE = field("TaskModel", "state");
  public static final QueryField TASK_IMAGE_S3_KEY = field("TaskModel", "taskImageS3Key");
  public static final QueryField TASK_LATITUDE = field("TaskModel", "taskLatitude");
  public static final QueryField TASK_LONGITUDE = field("TaskModel", "taskLongitude");
  public static final QueryField TEAM = field("TaskModel", "teamTasksId");
  public static final QueryField TEAM_NAME = field("TaskModel", "teamName");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private final @ModelField(targetType="taskState") TaskState state;
  private final @ModelField(targetType="String") String taskImageS3Key;
  private final @ModelField(targetType="String") String taskLatitude;
  private final @ModelField(targetType="String") String taskLongitude;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamTasksId", type = Team.class) Team team;
  private final @ModelField(targetType="String") String teamName;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public TaskState getState() {
      return state;
  }
  
  public String getTaskImageS3Key() {
      return taskImageS3Key;
  }
  
  public String getTaskLatitude() {
      return taskLatitude;
  }
  
  public String getTaskLongitude() {
      return taskLongitude;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public String getTeamName() {
      return teamName;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskModel(String id, String title, String description, Temporal.DateTime dateCreated, TaskState state, String taskImageS3Key, String taskLatitude, String taskLongitude, Team team, String teamName) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.dateCreated = dateCreated;
    this.state = state;
    this.taskImageS3Key = taskImageS3Key;
    this.taskLatitude = taskLatitude;
    this.taskLongitude = taskLongitude;
    this.team = team;
    this.teamName = teamName;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskModel taskModel = (TaskModel) obj;
      return ObjectsCompat.equals(getId(), taskModel.getId()) &&
              ObjectsCompat.equals(getTitle(), taskModel.getTitle()) &&
              ObjectsCompat.equals(getDescription(), taskModel.getDescription()) &&
              ObjectsCompat.equals(getDateCreated(), taskModel.getDateCreated()) &&
              ObjectsCompat.equals(getState(), taskModel.getState()) &&
              ObjectsCompat.equals(getTaskImageS3Key(), taskModel.getTaskImageS3Key()) &&
              ObjectsCompat.equals(getTaskLatitude(), taskModel.getTaskLatitude()) &&
              ObjectsCompat.equals(getTaskLongitude(), taskModel.getTaskLongitude()) &&
              ObjectsCompat.equals(getTeam(), taskModel.getTeam()) &&
              ObjectsCompat.equals(getTeamName(), taskModel.getTeamName()) &&
              ObjectsCompat.equals(getCreatedAt(), taskModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .append(getDateCreated())
      .append(getState())
      .append(getTaskImageS3Key())
      .append(getTaskLatitude())
      .append(getTaskLongitude())
      .append(getTeam())
      .append(getTeamName())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("taskImageS3Key=" + String.valueOf(getTaskImageS3Key()) + ", ")
      .append("taskLatitude=" + String.valueOf(getTaskLatitude()) + ", ")
      .append("taskLongitude=" + String.valueOf(getTaskLongitude()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("teamName=" + String.valueOf(getTeamName()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TaskModel justId(String id) {
    return new TaskModel(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      description,
      dateCreated,
      state,
      taskImageS3Key,
      taskLatitude,
      taskLongitude,
      team,
      teamName);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    TaskModel build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
    BuildStep state(TaskState state);
    BuildStep taskImageS3Key(String taskImageS3Key);
    BuildStep taskLatitude(String taskLatitude);
    BuildStep taskLongitude(String taskLongitude);
    BuildStep team(Team team);
    BuildStep teamName(String teamName);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String description;
    private Temporal.DateTime dateCreated;
    private TaskState state;
    private String taskImageS3Key;
    private String taskLatitude;
    private String taskLongitude;
    private Team team;
    private String teamName;
    public Builder() {
      
    }
    
    private Builder(String id, String title, String description, Temporal.DateTime dateCreated, TaskState state, String taskImageS3Key, String taskLatitude, String taskLongitude, Team team, String teamName) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.dateCreated = dateCreated;
      this.state = state;
      this.taskImageS3Key = taskImageS3Key;
      this.taskLatitude = taskLatitude;
      this.taskLongitude = taskLongitude;
      this.team = team;
      this.teamName = teamName;
    }
    
    @Override
     public TaskModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskModel(
          id,
          title,
          description,
          dateCreated,
          state,
          taskImageS3Key,
          taskLatitude,
          taskLongitude,
          team,
          teamName);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
     public BuildStep state(TaskState state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep taskImageS3Key(String taskImageS3Key) {
        this.taskImageS3Key = taskImageS3Key;
        return this;
    }
    
    @Override
     public BuildStep taskLatitude(String taskLatitude) {
        this.taskLatitude = taskLatitude;
        return this;
    }
    
    @Override
     public BuildStep taskLongitude(String taskLongitude) {
        this.taskLongitude = taskLongitude;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        this.team = team;
        return this;
    }
    
    @Override
     public BuildStep teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String description, Temporal.DateTime dateCreated, TaskState state, String taskImageS3Key, String taskLatitude, String taskLongitude, Team team, String teamName) {
      super(id, title, description, dateCreated, state, taskImageS3Key, taskLatitude, taskLongitude, team, teamName);
      Objects.requireNonNull(title);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder state(TaskState state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder taskImageS3Key(String taskImageS3Key) {
      return (CopyOfBuilder) super.taskImageS3Key(taskImageS3Key);
    }
    
    @Override
     public CopyOfBuilder taskLatitude(String taskLatitude) {
      return (CopyOfBuilder) super.taskLatitude(taskLatitude);
    }
    
    @Override
     public CopyOfBuilder taskLongitude(String taskLongitude) {
      return (CopyOfBuilder) super.taskLongitude(taskLongitude);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
    
    @Override
     public CopyOfBuilder teamName(String teamName) {
      return (CopyOfBuilder) super.teamName(teamName);
    }
  }
  


  
}
