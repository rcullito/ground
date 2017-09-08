(def VERSION (.trim (slurp "VERSION")))

(defproject ground VERSION
  :description "library of threading macros to handle exceptions and intra-thread predicates"
  :url "https://rcullito.github.io/ground/index.html"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :plugins [[lein-codox "0.10.3"]])
