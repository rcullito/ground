# n

![](https://clojars.org/n.core/latest-version.svg)

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.
jpg"
 alt="Along the river" height="250" />

## About

```clojure

(n-> expr & forms)

```

## Usage

like some->, except predicates prefixed with n either pass through the prior result or return nil for the entire form.


[![Build Status](https://travis-ci.org/rcullito/n.svg?branch=master)](https://travis-ci.org/rcullito/n)
[![Clojars Project](https://img.shields.io/clojars/v/n.core.svg)](https://clojars.org/n.core)

Make an assertion about your data. Only proceed if that assertion is truthy.

```clojure
(use 'n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(n-> person
   (:age)
   (n (> 30))
   (inc)) => 38

(n-> person
   (:age)
   (n (> 40))
   (inc)) => nil

```


