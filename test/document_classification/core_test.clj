(ns document-classification.core-test
  (:require [clojure.test :refer :all]
            [document-classification.core :refer :all]))

(deftest doc-num-test
  (testing "Number of docs should be 5485."
    (is (= (read-string (first (load-training-data))) 5485))))

(deftest split-by-string-test
  (testing "Split a string of text by space."
    (is (= (split-by-space "Hi I'm Kai Xu") ["Hi", "I'm", "Kai", "Xu"]))))

(deftest gen-vocab-dict-test
  (testing "Gnerate the vocabulary counting dictionary."
    (is (= (count-word nil "a") {"a" 1}))
    (is (= (count-word {"a" 1} "a") {"a" 2}))
    (is (= (count-word-list nil ["a", "b", "c"]) {"a" 1 "b" 1 "c" 1}))
    (is (= (count-word-list nil (split-by-space "Hi I'm Kai Xu")) {"Hi" 1 "I'm" 1 "Kai" 1 "Xu" 1}))
    (is (= (count-sentences nil ["Hello my name is Kai Xu", "Kai Xu is a boy's name"])
           {"Hello" 1 "my" 1 "name" 2 "is" 2 "Kai" 2 "Xu" 2 "a" 1 "boy's" 1}))
    (is (= (count (gen-vocab-dict (load-training-data))) 19990)))) ; total number of unique words (without pre-processing)

