(ns n.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [n.core :refer [n-> n->>]]))

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})


(deftest predicate-last-form
  (testing "that the predicate passes through prior value if true"
    (is (= 38 (n-> person
                 (:age)
                 (n (> 35))
                 (inc))))))

(deftest predicate-returns-nil
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n-> person
                  (:age)
                  (n (> 40))
                  (+ 10))))))

(deftest truthy-predicate-returns-collection
  (testing "that the predicate passes through prior value if true"
    (is (= '(2 3 4) (n->> [1 2 3]
                     (n (every? identity))
                     (map inc))))))

(deftest predicate-collection-returns-nil
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n->> [1 2 nil 3]
                     (n (every? identity))
                     (map inc))))))
