(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

;; basically interleave with the ability to skp a step


;; really if we have n we want to evaluate and if true return the previous value


(defmacro n
  [x & forms]
  (loop [x x, forms forms]
    (if forms
      (let [form     (first forms)
            threaded (if (seq? form)
                       (if (= (first form) 'n)
                         (let [post-n-form (second form)]
                           `(~(first post-n-form) ~x ~@(next post-n-form)))
                         `(~(first form) ~x ~@(next form)))
                       (list form x))]
        (recur threaded (next forms)))
      x)))

;; something like
;; eventually this part needs to return either nil or the thing that was passed in if it was true

;;; ideally our syntax should read
(n person
   (:age)
   (+ 1)
   (n (> 39)))


#_
(-> person
    :age
    (> 0))
#_
(n person
   (:age)
   n?
   inc)
