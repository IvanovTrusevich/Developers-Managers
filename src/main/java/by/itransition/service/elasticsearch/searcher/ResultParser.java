package by.itransition.service.elasticsearch.searcher;

import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ResultParser {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> parse(String searchResult) throws JSONException {
        Map<String, String> result = new HashMap<>();
        String hits = searchResult.substring(searchResult.indexOf("["), searchResult.lastIndexOf("]") + 1);
        JSONArray resultArray = new JSONArray(hits);
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject hit = resultArray.getJSONObject(i);
            long id = Long.parseLong((String) hit.get("_id"));
            String type = (String) hit.get("_type");
            if (type.equals("project")) {
                Project project = projectRepository.findOne(id);
                result.put("projects/" + project.getProjectName(), project.getProjectName());
            } else if (type.equals("user")) {
                User user = userRepository.findOne(id);
                result.put("projects/" + user.getUsername(), user.getUsername());
            }
        }
        return result;
    }
}