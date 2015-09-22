import java.util.*;

/**
 * Created by Tulskih Anton on 12.05.2015.
 *
 * Given an array of strings of latin letters in lowercase(a-z) sorted by alphabetical order.
 * It's necessary to find the longest compound word from the given array, that consists of words from the array either.
 * Example: given ["five", "fivetwo", "fourfive", "fourfivetwo", "one", "onefiveone", "two", "twofivefourone"]. The
 * longest compound word from this array will be "fourfivetwo" consists of 11 letters.
 *
 * Input data: an array of strings of latin letters in lowercase(a-z) sorted by alphabetical order.
 * Output data: the longest compound word from the given array, that consists of words from the array either.
 */
class LongestCompoundWordFinder {

    private Random random;
    // will store sorted by alphabetical order random set of words from the given array
    private TreeSet<String> abcSortedRandomSetOfWords;

    // will store same abcSortedRandomSetOfWords words in string length order
    private ArrayList<String> lengthSortedRandomListOfWords;

    // Input array. Length == 112.
    private String[] arrayOfStrings = {"aggressive", "ball", "barrow", "basket", "basketball", "big",
              "biggreeneye", "black", "blackboard", "blacklist", "board", "break", "breakwater", "broker", "bullet",
              "bulletproof", "butter", "butterfinger", "car", "coaster", "common", "commonwealth", "daughter", "down",
              "downgrade", "eye", "fast", "finger", "fisher", "fisherwoman", "grade", "grand", "granddaughter",
              "grandmother", "green", "hammer", "hammerhead", "handle", "head", "headquarter", "high", "highlight",
              "highway", "house", "housekeeper", "housewife", "keeper", "light", "lightspeed", "line", "list",
              "maker", "man", "manhandle", "market", "mint", "mother", "over", "overaggressive", "paper", "partner",
              "partnership", "pepper", "peppermint", "proof", "quarter", "roller", "rollercoaster", "sensitive",
              "ship", "shot", "shower", "slaughter", "slaughterhouse", "speed", "stock", "stockbroker", "struck",
              "structure", "super", "superballshot", "superfastcar", "superhighway", "supermarket", "supersensitive",
              "superstructure", "thunder", "thundershower", "thunderstruck", "trouble", "troublemaker", "tumble",
              "tumbledown", "under", "underline", "underworld", "wall", "wallpaper", "wash", "water", "weather",
              "weatherman", "whealth", "wheel", "wheelbarrow", "white", "whitewash", "wide", "wife", "woman",
              "world", "worldwide"};

    /**
     * No-arg constructor.
     * Initializes Random generator and creates alphabetically sorted random set of words from the given array.
     */
    LongestCompoundWordFinder() {

        random = new Random();
        createSortedRandomSet();

    }

    /**
     * Creates alphabetically sorted random set of words from the given array. Size of the set is random between
     * 50 and 80 words.
     */
    private void createSortedRandomSet() {

        int setSize = random.nextInt(31)+50; // define the size of new array between 50 and 80.

        abcSortedRandomSetOfWords = new TreeSet<String>();

        // fills the set with words until size of the set reaches specified setSize.
        do {

            abcSortedRandomSetOfWords.add(arrayOfStrings[random.nextInt(112)]);

        } while (abcSortedRandomSetOfWords.size() != setSize);

        // Output created alphabetically sorted random set of words from the given array.
        System.out.println("Input array of strings sorted by alphabet:\n" + abcSortedRandomSetOfWords.toString());

    }

    /**
     * Finds the longest compound word from the existing collection.
     * @return the longest compound word.
     */
    private String findLongestCompoundWord() {

        // creating list from let so we can sort it by strings' length.
        lengthSortedRandomListOfWords = new ArrayList<String>(abcSortedRandomSetOfWords);
        Collections.sort(lengthSortedRandomListOfWords, new StringLengthComparator());

        // output list sorted based on strings' lengths.
        System.out.println("\nSame sorted by lengths:\n" + lengthSortedRandomListOfWords.toString());

        /* starting from the longest word in the list(from the end of the list) and verifying, whether it's a
         * compound or not. Deleting this word from the list so if this word not a compound, the next longest word
         * in the list will be checked. If the word is compound - returning it.*/
        while (lengthSortedRandomListOfWords.size() > 0) {

            String word = lengthSortedRandomListOfWords.remove(lengthSortedRandomListOfWords.size()-1);

            if (isCompoundWord(word)) {return word;}

        }

        return "There is no compound words in the given array";

    }

    /**
     * Checks if the given word is a compound or not.
     * @param word is the given word.
     * @return true if the given word is a compound, otherwise returns false.
     */
    private boolean isCompoundWord(String word) {

        boolean isCompound = false;

        StringBuilder givenWord = new StringBuilder(word);

        /* make simple word letter by letter from start of the given word and check whether it is in the list of words.
         * If it is - delete this word from the given and start over with the word, remaining from the given. If after
         * several iteration length of the given word will be 0 - the given word is a compound. If simple word length
         * reaches the given word length and there is no such word in the list - the given word is not a compound
         *
         * Example:
         *
         * givenWord = "onetwothree", givenWord.length() == 11
         * simpleWord = "o"  ->  simpleWord.length() == 1 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "on"  ->  simpleWord.length() == 2 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "one"  ->  simpleWord.length() == 3 <= givenWord, there is such word in the list, so
         * delete it from the givenWord  ->  givenWord.length() > 0  ->  start over
         *
         * givenWord = "twothree", givenWord.length() == 8
         * simpleWord = "t"  ->  simpleWord.length() == 1 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "tw"  ->  simpleWord.length() == 2 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "two"  ->  simpleWord.length() == 3 <= givenWord, there is such word in the list, so
         * delete it from the givenWord  ->  givenWord.length() > 0  ->  start over
         *
         * givenWord = "three", givenWord.length() == 5
         * simpleWord = "t"  ->  simpleWord.length() == 1 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "th"  ->  simpleWord.length() == 2 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "thr"  ->  simpleWord.length() == 3 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "thre"  ->  simpleWord.length() == 4 <= givenWord, there is no such word in the list, so i++
         * simpleWord = "three"  ->  simpleWord.length() == 5 <= givenWord, there is such word in the list, so
         * delete it from the givenWord  ->  givenWord.length() = 0  ->  givenWord is a compound.*/
        loop: while (givenWord.length() > 0) {

            String simpleWord = "";

            for (int i = 1; i <= givenWord.length(); i++) {

                simpleWord = givenWord.substring(0, i);
                if (lengthSortedRandomListOfWords.contains(simpleWord)) {break;}
                if (simpleWord.length() == givenWord.length() && !(lengthSortedRandomListOfWords.contains(simpleWord))) {break loop;}

            }

            givenWord.delete(0, simpleWord.length());
            if (givenWord.length() == 0) {isCompound = true;}

        }

        return isCompound;

    }

    /**
     * Class used as a comparator in a list of strings. Compares lengths of strings.
     */
    class StringLengthComparator implements Comparator<String> {

        @Override
        public int compare(String firstWord, String secondWord) {

            return firstWord.length()-secondWord.length();

        }

    }

    public static void main(String[] args) {

        LongestCompoundWordFinder longestCompoundWordFinder = new LongestCompoundWordFinder();

        System.out.println("\nLongest compound word is: " + longestCompoundWordFinder.findLongestCompoundWord());

    }

}