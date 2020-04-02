package cz.jalasoft.nio.socket;

import cz.jalasoft.nio.socket.dialogue.Dialogue;
import cz.jalasoft.nio.socket.dialogue.DialogueServer;

/**
 * @author Jan Lastovicka
 * @since 2019-02-22
 */
public class HoroscopeApp {

    public static void main(String[] args) throws Exception {

        Dialogue horoscope = Dialogue.forQuestions(
            "Jak se jmenujes?",
            "Kdy ses narodil?",
            "A kde pak to bylo?"
        );

        try(DialogueServer server = new DialogueServer(horoscope)) {

            server.start(8888);

            Thread.sleep(500000);

        }
    }
}
