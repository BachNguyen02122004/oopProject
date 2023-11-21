package DictionaryApp.app;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeech {

    private static Synthesizer synthesizer;

    // Được thực hiện 1 lần khi tải lần đầu tiên
    static {
        try {
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void speakText(String text) {
        try {
            if (synthesizer == null) {
                throw new IllegalStateException("Synthesizer not initialized");
            }

            // Tiếp tục nếu nếu muốn nhấn tiếp
            synthesizer.resume();

            // Đưa từ vào để nói
            synthesizer.speakPlainText(text, null);

            // Chờ cho đến khi nói xong
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
