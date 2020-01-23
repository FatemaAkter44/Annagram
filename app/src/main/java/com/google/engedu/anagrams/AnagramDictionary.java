/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int   MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    ArrayList<String> wordlist = new ArrayList<String>(); /*first task will be to advance the implementation of the AnagramDictionary's constructor. Each word that is read from the dictionary file should be stored in an ArrayList (called wordList).*/
    //faster
    HashSet<String> wordSet = new HashSet<String>();

    //
    HashMap<String, ArrayList<String>> letterstoWord = new HashMap<String, ArrayList<String>>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            wordSet.add(word);

            // get signature for word
            String key = sortLetters(word);

            // looping key on the table
            if (letterstoWord.containsKey(key)){

                //key is on the table, add word to the list for that key
                ArrayList<String> listofWords = letterstoWord.get(key);
                listofWords.add(word);
            }
            else {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(word);
                letterstoWord.put(key, temp);
            }
        }

    }

// takes as input istring,
    public String sortLetters (String inputword){
        char letters[] = inputword.toCharArray();
        Arrays.sort(letters);
        String signature = new String(letters);
        return signature;
    }


    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) & !word.contains(base)) { // To check word is in dicteneary and chek if the base inside the word
            return true;
        }
        else {
            Log.d( "Fatema", "Hello World" );
            return false;
        }

    }


    public List<String> getAnagrams(String targetWord) { // this is for take input from use
        ArrayList<String> result = new ArrayList<String>();

        //create signature for the dictionary
        String targetWordSignature = sortLetters(targetWord.toLowerCase());

       /*
       //
       old way
       for each word in the dictinary


       for (String word: wordlist){

           //grt signature of the word
           String signatureWord = sortLetters(word);

           //if signature of input word and dictionary word are wqual, add word to result

           if (targetWordSignature.equals(signatureWord)){
               result.add(word);
           }
       }
        */

        if (letterstoWord.containsKey(targetWordSignature)) {
            result = (letterstoWord.get(targetWordSignature));
        }
        result.remove(targetWord);
        return result;
    }

 /*  public List<String> getAnagramsWithOneMoreLetter(String word) {
       ArrayList<String> result = new ArrayList<String>();
       return result;
   }*/

    public String pickGoodStarterWord() {


        Integer size = wordlist.size();

        int numberofAnagrams = 0;
        //Log.d("Fatema", size.toString()); //this line will print the size of the wordlist . to see it go to logcat and surch
       String baseword = new String();  // declaring the a variavle to hold the goodstarterWord
       do {
           Random random = new Random(); // crating new random object.
           int randomindex = random.nextInt(size); // we want to get a random number between 0 to 5
           baseword = wordlist.get(randomindex);// get me the word at the position of the random number.

           //to get number of anagrams for baseword
           String key = sortLetters(baseword); //{  i am creating the signature for the fresh random word just janarated.

           numberofAnagrams = letterstoWord.get(key).size();

         } while (numberofAnagrams < 6);

        return baseword;
    }
}


