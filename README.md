# ground

## About

[![Build Status](https://travis-ci.org/rcullito/ground.svg?branch=master)](https://travis-ci.org/rcullito/ground)
[![Clojars Project](https://img.shields.io/clojars/v/ground.svg)](https://clojars.org/ground)

"The ground wire is an additional path for electrical current to return safely to the ground without danger to anyone in the event of a short circuit."

<img src="ground.png" alt="ground" width="50px" />

## Getting Started 

![](https://clojars.org/ground/latest-version.svg)

```clojure
(ns demo.core
  (:require [ground.core :refer :all]))
```

## Usage

The `n->` and `n->>` threading macros allow for predicates and side effects within their forms. 

### n? predicates

`n?` signals a predicate that will pass through the result of the
prior expression if true and return nil for the entire form if false

```clojure
(n-> 6
     (n? (> 5))
     (vector 7 8)) => [6 7 8]

(n-> 9
     (n? (> 10))
     (vector 11 12)) => nil
```


```clojure
(n->> [1 2 3]
      (n? (every? identity))
      (map inc)) => '(2 3 4)

(n->> [1 nil 3]
      (n? (every? identity))
      (map inc)) => nil
```

### n! side effects

`n!` signals a side effect, such as printing or logging, that will
always pass through the result of the prior expression

```clojure
(n-> 4
     (n! (println "is the best number")) ;; prints "4 is the best number"
     inc) => 5	

(n->> ["right" "blue" "humpback"]
      (n! (apply println "These are different types of whales:")) ;; prints "These are different types of whales: right blue humpback"
      sort) => '("blue" "humpback" "right")
```

## References

* [clojure.core threading](https://clojure.org/guides/threading_macros)
