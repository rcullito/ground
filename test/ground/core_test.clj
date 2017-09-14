(ns ground.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [ground.core :refer [n-> n->> ground-> ground->>]]))

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(deftest predicate-last-form
  (testing "that the predicate passes through prior value if true"
    (is (= 38 (n-> person
                 (:age)
                 (n? (> 35))
                 (inc))))))

(deftest predicate-last-form
  (testing "that for backwards compatibility n functions the same as n?, indicating a subsequent predicate"
    (is (= 38 (n-> person
                 (:age)
                 (n (> 35))
                 (inc))))))

(deftest form-not-seq
  (testing "can handle the special use case when one of the forms is not a sequence"
    (is (= 5 (n-> 4
                  inc)))))

(deftest predicate-returns-nil
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n-> person
                  (:age)
                  (n? (> 40))
                  (+ 10))))))

(deftest side-effect-does-not-affect-result
  (testing "that n! can print the statement with 4 as the first arg"
    (is (= 5 (n-> 4
                  (n! (println "is the best number"))
                  inc)))))

(deftest side-effect-does-not-affect-result-of-list
  (testing "that n! will print the list and also return it unchanged to sort"
    (is (= '("blue" "humpback" "right")
           (n->> ["right" "blue" "humpback"]
                 (n! (apply println "These are different types of whales:"))
                 sort)))))

(deftest truthy-predicate-returns-collection
  (testing "that the predicate passes through prior value if true"
    (is (= '(2 3 4) (n->> [1 2 3]
                          (n? (every? identity))
                          (map inc))))))

(deftest predicate-collection-returns-nil
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n->> [1 2 nil 3]
                     (n? (every? identity))
                     (map inc))))))

(deftest the-all-is-well-thread
  (testing "that ground-> behaves like ->"
    (let [in-range 2]
      (is (= \a (ground-> "name"
                          reverse
                          (nth in-range)))))))

(deftest swallow-null-pointer->
  (testing "that ground-> can absorb ->'s exceptions"
    (let [out-of-range 4]
      (is (= nil (ground-> "name"
                           reverse
                           (nth out-of-range)))))))

(deftest the-all-is-well-thread
  (testing "that ground->> behaves like ->>"
    (is (= '(2 3) (ground->> [1 2]
                             (map inc))))))

(deftest swallow-null-pointer->
  (testing "that ground->> can absorb ->>'s exceptions"
    (is (= nil (ground->> [1 nil 2]
                          (map inc))))))
