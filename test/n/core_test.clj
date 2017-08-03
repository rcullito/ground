(ns n.core-test
  (:require [clojure.test :refer :all]
            [n.core :refer :all]))

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(deftest predicate-last-form
  (testing "that the predicate does not alter the passed value"
    (is (= 38 (n person
                 (:age)
                 (inc)
                 (n (> 35)))))))

(deftest predicate-returns-false
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n person
                  (:age)
                  (n (> 40))
                  (+ 10))))))

