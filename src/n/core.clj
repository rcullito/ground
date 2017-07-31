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
                           `(if (~(first post-n-form) ~x ~@(next post-n-form))
                             ~x
                             nil))
                         `(~(first form) ~x ~@(next form)))
                       (list form x))]
        (recur threaded (next forms)))
      x)))

(n person
   (:age)
   (+ 1)
   (n (> 35)))



;; todo we need to make sure that a nil with more steps will actually stop.
;; change the license to MIT
;; update readme with examples of how the macro is actually used in practice.
