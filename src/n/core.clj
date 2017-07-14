(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

;; basically interleave with the ability to skp a step

(defn n?
  [form]
  (if form
    (second form)
    ;; by this we really mean the whole thing shoudl return nil, like some->
    nil))

(defn n?
  [number]
  (> number 0))

(defmacro n
  [x & forms]
  (loop [x x, forms forms]
    (if forms
      (let [form (first forms)
            threaded (if (seq? form)
                       `(~(first form) ~x ~@(next form))
                       (list form x))]
        (recur threaded (next forms)))
      x)))


;; something like
;; eventually this part needs to return either nil or the thing that was passed in if it was true

(if (= (form 'n1))
  (do (println "it's working")
      ))


;;; ideally our syntax should read
(n person
   (:age)
   n?)


#_
(-> person
    :age
    (> 0))
#_
(n person
   (:age)
   n?
   inc)
