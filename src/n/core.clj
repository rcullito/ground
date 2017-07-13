(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})



(defmacro n
  "takes a number of formes and magically transforms them"
  [& forms]
  `(do ~@forms))

(n (println "sure")
   (println "then came the war old sport!"))


;;; ideally our syntax should read
(when-let [val (n
                (n2 (:age person))
                (n? (> 0)))]
  ("it's working!!!"))

