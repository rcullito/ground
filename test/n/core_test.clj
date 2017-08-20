(ns n.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [n.core :refer [n]]))

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(def people
  [{:name "Nancy"
   :dog  "Claire"
    :age 37}
   {:name "Robbie"
    :dog "Comet"
    :age 28}])

(deftest predicate-last-form
  (testing "that the predicate does not alter the passed value"
    (is (= 38 (n person
                 (:age)
                 (inc)
                 (n (> 35)))))))

(deftest predicate-returns-nil
  (testing "that a false predicate returns nil for the entire form"
    (is (= nil (n person
                  (:age)
                  (n (> 40))
                  (+ 10))))))

(deftest accept-non-seq-predicate
  (testing "a keyfn not wrapped in parens should be valid"
    (is (= 47 (n person
                  :age
                  (n (> 30))
                  (+ 10))))))

(deftest operate-on-collection
  (testing "if a predicate after n returns truthy for a collection, then continue"
    (is (= '(33 24) (n people
                       (n (every? #(:dog %)))
                       (map #(assoc % :age (- (:age %) 4)))
                       (map #(:age %)))))))

(deftest decrement-non-nils
  (testing "ensure that filter identity is thorough in removing nil values"
    (is '(0 1 2 3) (n [1 nil 2 3 4]
                      (filter identity)
                      (n (not-any? nil?))
                      (map dec)))))


