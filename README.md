# ground

![](https://clojars.org/ground/latest-version.svg)

"The ground wire is an additional path for electrical current to return safely to the ground without danger to anyone in the event of a short circuit."

## Usage

[![Build Status](https://travis-ci.org/rcullito/ground.svg?branch=master)](https://travis-ci.org/rcullito/ground)
[![Clojars Project](https://img.shields.io/clojars/v/ground.svg)](https://clojars.org/ground)

### grounding

`ground->` will behave exactly as `->` if no exceptions are thrown. 

```clojure
(use 'ground)

(ground-> "name"
	reverse
	(nth 2)) => \a
```	

Otherwise it will suppress the exception and return nil.

```clojure
(ground-> "name"
	reverse
	(nth 8)) => nil
```

Whereas normal threading would throw:

```clojure
(-> "name"
    reverse
    (nth 8)) => java.lang.IndexOutOfBoundsException
```

`ground->>` performs the same behavior in place of `->>`.

### n predicates


Make an assertion about your data. Only proceed if that assertion is truthy.

```clojure
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

`n->>` performs the same behavior in place of `->>`.

