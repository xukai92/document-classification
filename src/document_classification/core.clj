(ns document-classification.core
  (:require [clojure.java.io :refer :all]
            [clojure.string :as str]
            [clojure.core.matrix :refer :all])
  (:gen-class))

(set-current-implementation :vectorz)
(assert (= (class (array [1 2 3])) mikera.vectorz.Vector) "Not using mikera.vectorz.Vector")

(defn load-training-data
  "Load training data."
  [& args]
  (with-open [r (reader "/home/kai/projects/document-classification/trainingdata.txt")]
    (doall (line-seq r))))

(defn split-by-space
  "Split a line of text by space."
  [text]
  (str/split text #" "))

(defn count-word
  "Count a word in vocabulary counting dictionary."
  [vocab-dict word]
  (if (contains? vocab-dict word)
    (assoc vocab-dict word (+ (vocab-dict word) 1))
    (assoc vocab-dict word 1)))

(defn count-word-list
  "Count a list of word in vocabulary counting dictionary."
  [vocab-dict word-list]
  (if (empty? word-list)
    vocab-dict
    (count-word-list (count-word vocab-dict (first word-list)) (drop 1 word-list))))

(defn count-sentences
  "Count a list of lines (String)."
  [vocab-dict lines]
  (if (empty? lines)
    vocab-dict
    (count-sentences (count-word-list vocab-dict (split-by-space (first lines))) (drop 1 lines))))

; gen-vocab-dict should use loop to call count-sentences
; this avoids the problem that apply count-sentences to the whole input will cause stack overflow
(defn gen-vocab-dict
  "Gnerate the vocabulary counting dictionary."
  [data]
  (let [doc-num (read-string (first data))
        all-lines (take doc-num (drop 1 data))]
    (loop [vocab-dict nil
           lines all-lines]
      (if (empty? lines)
        vocab-dict
        (recur (count-sentences vocab-dict (take 100 lines)) (drop 100 lines))))))

(defn -main
  "Document classification main body."
  [& args]
  (let [raw-data (load-training-data)
        doc-num (read-string (first raw-data))
        vocab (gen-vocab-dict raw-data)]
    (println "Number of docs:" doc-num)
    (println "Number of words:" (reduce + (vals vocab)))
    (println "Number of unique words:" (count vocab))))
