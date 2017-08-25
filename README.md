# n

like some-> or some->>, except forms following subsequent n's are treated as predicates that do not affect the value passed to the next form.

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.jpg"
 alt="Along the river" height="250" />

[![Build Status](https://travis-ci.org/rcullito/n.svg?branch=master)](https://travis-ci.org/rcullito/n)
[![Clojars Project](https://img.shields.io/clojars/v/n.core.svg)](https://clojars.org/n.core)

## About
`n` will thread first or last depending on whether `expr` passed to `n` is `sequential?`

## Usage

```clojure

[n.core :refer [n]]

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

;; if the form following n evaluates to true, return previous value rather than the result of the prior expression.

(n person
   (:age)
   (inc)
   (n (> 35))) => 38

;; if the form following n evaluates to false, exit the thread with a nil
;; the following lines will not throw a NullPointerException

(n person
   (:age)
   (n (> 40))
   (+ 10)) => nil


;; thread last will throw an exception

(->> [1 nil 2]
     (map inc)) => Unhandled java.lang.NullPointerException

;; the same check can be gaurded against within the n threading operator

(n [1 nil 3]
   (n (every? number?))
   (map inc)) => nil

(n [1 2 3]
   (n (every? number?))
   (map inc)) => (2 3 4)

```

