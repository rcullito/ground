# n
```clojure

(n expr & forms)

```
like some-> or some->>, except forms following subsequent n's are treated as predicates that do not affect the value passed to the next form.

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.jpg"
 alt="Along the river" height="250" />

[![Build Status](https://travis-ci.org/rcullito/n.svg?branch=master)](https://travis-ci.org/rcullito/n)
[![Clojars Project](https://img.shields.io/clojars/v/n.core.svg)](https://clojars.org/n.core)

## About
`n` will thread first or last depending on whether `expr` passed to `n` is `sequential?`

`n` will short circuit with a nil if any intermediate expression evaulates to nil, or if a predicate following `n` evaluates to a non-truthy value.

## Usage

Make an assertion about your data.

```clojure

[n.core :refer [n]]

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(n person
   (:age)
   (inc)
   (n (> 35))) => 38
```

Short circuit before hitting a NullPointerException.

```clojure
(n person
   (:age)
   (n (> 40))
   (+ 10)) => nil
```

Verify your sequences and then proceed as usual.

```clojure
(->> [1 nil 2]
     (map inc)) => Unhandled java.lang.NullPointerException

(n [1 nil 3]
   (n (every? number?))
   (map inc)) => nil

(n [1 2 3]
   (n (every? number?))
   (map inc)) => (2 3 4)

```

