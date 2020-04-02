package cz.jalasoft.nio.socket.dialogue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jan Lastovicka
 * @since 2019-02-22
 */
public class DialogueSession {

    private final Map<String, String> values;
    private Iterator<String> questionIterator;
    private String currentQuestion;

    DialogueSession(List<String> questions) {
        this.values = new HashMap<>();
        this.questionIterator = questions.iterator();
    }

    public boolean hasNextQuestion() {
        return questionIterator.hasNext();
    }

    public String nextQuestion() {
        currentQuestion = questionIterator.next();
        return currentQuestion + " ";
    }

    public void answer(String answer) {
        values.put(currentQuestion, answer);
    }

    public String finalResponse() {
        StringBuilder bldr = new StringBuilder("Rozhreseni je takove: \n");

        values.entrySet().forEach(s -> {
            String question = s.getKey();
            String value = s.getValue();

            bldr.append("na otazku '" + question + "' jsi odpovedel: " + value.toUpperCase() + "\n");
        });

        bldr.append("Podle hvezd na obloze bych rekl ze se ti bude darit!!!!!!\n\n");

        return bldr.toString();
    }
}
