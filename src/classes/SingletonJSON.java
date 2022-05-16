package classes;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SingletonJSON {
    private static SingletonJSON instance = new SingletonJSON();
    private JSONObject usersJson;
    private JSONObject projectsJson;
    private JSONArray usersJsonArray;
    private JSONArray projectsJsonArray;

    private SingletonJSON() {
        this.setUsersJson();
    }

    public static SingletonJSON getInstance() {
        return instance;
    }

    private void setUsersJson() {
        Object usersData;
        Object projectsData;
        try {
            usersData = new JSONParser().parse(new FileReader("src/assets/data/users_data.json"));
            this.usersJson = (JSONObject) usersData;
            this.usersJsonArray = (JSONArray) this.usersJson.get("users");

            projectsData = new JSONParser().parse(new FileReader("src/assets/data/projects_data.json"));
            this.projectsJson = (JSONObject) projectsData;
            this.projectsJsonArray = (JSONArray) this.projectsJson.get("projects");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getProjectMapFromJsonObject(JSONObject jsonObject) {
        Map<String, Object> map = null;
        try {
            map = new ObjectMapper().readValue(jsonObject.toJSONString(), Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ArrayList<Map<String, Object>> getProjectListMapFromJsonArray(JSONArray jsonArray) {

        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (jsonArray != null) {
            int jsonSize = jsonArray.size();

            for (int i = 0; i < jsonSize; i++) {
                Map<String, Object> map = getProjectMapFromJsonObject((JSONObject) jsonArray.get(i));
                list.add(map);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserMapFromJsonObject(JSONObject jsonObject) {
        Map<String, Object> map = null;
        try {
            map = new ObjectMapper().readValue(jsonObject.toJSONString(), Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ArrayList<Map<String, Object>> getUserListMapFromJsonArray(JSONArray jsonArray) {

        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (jsonArray != null) {
            int jsonSize = jsonArray.size();

            for (int i = 0; i < jsonSize; i++) {
                Map<String, Object> map = getUserMapFromJsonObject((JSONObject) jsonArray.get(i));
                list.add(map);
            }
        }
        return list;
    }

    public Project getProject(String projectId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Map<String, Object> projectMap : getProjectListMapFromJsonArray(projectsJsonArray)) {
                if (projectMap.get("projectId").equals(projectId)) {
                    return mapper.convertValue(projectMap, Project.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Project();
    }

    public ArrayList<Project> getProjects(ArrayList<String> projectIds) {
        ArrayList<Project> projects = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String projectId : projectIds) {
            try {
                for (Map<String, Object> projectMap : getProjectListMapFromJsonArray(projectsJsonArray)) {
                    if (projectMap.get("projectId").equals(projectId)) {
                        projects.add(mapper.convertValue(projectMap, Project.class));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return projects;
    }

    public User getUser(String userId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Map<String, Object> userMap : getProjectListMapFromJsonArray(usersJsonArray)) {
                if (userMap.get("userId").equals(userId)) {
                    return mapper.convertValue(userMap, User.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }

    public ArrayList<User> getUsers(ArrayList<String> userIds) {
        ArrayList<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String userId : userIds) {
            try {
                for (Map<String, Object> userMap : getProjectListMapFromJsonArray(usersJsonArray)) {
                    if (userMap.get("userId").equals(userId)) {
                        users.add(mapper.convertValue(userMap, User.class));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
