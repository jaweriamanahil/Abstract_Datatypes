package poet;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GraphPoetTest {

    // Ensure assertions are enabled
    @Test
    public void testAssertionsEnabled() {
        try {
            assert false; // This should throw an AssertionError
            fail("Assertions must be enabled!");
        } catch (AssertionError e) {
            // Expected behavior
        }
    }

    // Correct handling of input files for graph construction
    @Test
    public void testSimpleCorpus() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/simple.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Life is beautiful.";
        String expected = "Life is very beautiful."; // Assuming "very" is the bridge word
        assertEquals("Failed to construct graph and generate a poem correctly", expected, poet.poem(input));
    }

    // Correct generation of poems with and without bridge words
    @Test
    public void testBridgeWordsInMiddle() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/bridgewords.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "To be or not to be.";
        String expected = "To be or maybe not to be."; // Assuming "maybe" is the bridge word
        assertEquals("Bridge words generation failed", expected, poet.poem(input));
    }

    @Test
    public void testNoBridge() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/nobridge.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "There is no bridge.";
        assertEquals("Poem should match input when no bridge words exist", input, poet.poem(input));
    }

    // Proper validation for edge cases
    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/simple.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "";
        assertEquals("Empty input should return empty output", "", poet.poem(input));
    }

    // Ensure case insensitivity in graph creation and poem generation
    @Test
    public void testCaseInsensitiviteCorpus() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/caseinsensitive.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "It is a Test.";
        String expected = "It is a great Test."; // Assuming "great" is the bridge word, ignoring case
        assertEquals("Case insensitivity in corpus failed", expected, poet.poem(input));
    }

    // Special characters handling
    @Test
    public void testSpecialCharacters() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/specialchars.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Hello, world!";
        String expected = "Hello, beautiful world!"; // Assuming "beautiful" is the bridge word
        assertEquals("Special characters should be handled correctly", expected, poet.poem(input));
    }

    // Punctuation in corpus handling
    @Test
    public void testPunctuationCorpus() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/punctuation.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Time flies.";
        String expected = "Time truly flies."; // Assuming "truly" is the bridge word
        assertEquals("Punctuation in corpus should be handled correctly", expected, poet.poem(input));
    }

    // Handling repeated words in the corpus
    @Test
    public void testRepeatedWordsCorpus() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/repeated.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Love is love.";
        String expected = "Love is eternal love."; // Assuming "eternal" is the bridge word
        assertEquals("Repeated words in corpus handling failed", expected, poet.poem(input));
    }

    // Empty corpus handling
    @Test
    public void testEmptyCorpus() throws IOException {
        File corpus = new File("C:/Users/Jawer/OneDrive - National University of Sciences & Technology/Desktop/ps2/test/empty.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Nothing to add.";
        assertEquals("Empty corpus should produce no modifications", input, poet.poem(input));
    }
}
