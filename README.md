# n

like some->, except forms following n are treated as predicates that do not affect the value passed to the next form.

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.jpg"
 alt="Along the river" height="250" />

## Usage

Increment all the values in maps of maps:
```clojure
(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(n person
   (:age)
   (inc)
   (n (> 35))) => 38

(n person
   (:age)
   (n (> 40))
   (+ 10)) => nil

```

## License

MIT
