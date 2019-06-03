package flowershop.greeting;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GreetingPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks().create("hello", Greeting.class, task -> {
            task.setGroup("flowershop");
            task.setMessage("Hello");
            task.setRecipient("World");
        });
    }
}