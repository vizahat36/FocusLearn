package com.focuslearn;

import com.focuslearn.controller.HelloController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloControllerTest {

    @Test
    void helloReturnsGreeting() {
        HelloController controller = new HelloController();
        String res = controller.hello();
        assertThat(res).contains("FocusLearn");
    }
}
