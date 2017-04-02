(defproject document-classification "document-classification"
  :description "Document Classification Task from HackerRank"
  :url "https://www.hackerrank.com/challenges/document-classification"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/core.matrix "0.58.0"]
                 [net.mikera/vectorz-clj "0.46.0"]]
  :main ^:skip-aot document-classification.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
