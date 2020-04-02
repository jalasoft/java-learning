package cz.jalasoft.nio.socket.dialogue;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jan Lastovicka
 * @since 2019-02-22
 */
public class Dialogue {

    public static Dialogue forQuestions(String... questions) {
        return new Dialogue(Arrays.asList(questions));
    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    private final List<String> questions;

    private Dialogue(List<String> questions) {
        this.questions = questions;
    }

    DialogueSession newSession() {
        return new DialogueSession(questions);
    }
}
