(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

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


;; the next step is that based on the boolean
;; true, we return x, false we return nil


(n person
   (:age)
   (+ 1)
   (n (> 39)))

