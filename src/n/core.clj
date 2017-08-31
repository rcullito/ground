(ns n.core)

(defn thread-smart
  "if the current expr is sequential, thread last"
  [g step]
  (if (sequential? ~g)
    (->> ~g ~step)
    (-> ~g ~step)))

(defn handle-n-form
  [g step]
  (let [post-n-step (second step)]
    `(if (nil? ~g)
       nil
       (if (thread-smart g post-n-step)
         ;; this is the pass through
         ;; i.e. rather than evaluating the current form
         ;; we pass along the previous value: g
         ~g
         nil))))

(defn step-fn
  [g step]
  (if ((every-pred seq? #(= (first %) 'n)) step)
    (handle-n-form g step)
    `(if (nil? ~g)
       nil
       (thread-smart g step))))

(defmacro n
  "like some->, except forms following n are treated as predicates
  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        steps (map (partial step-fn g) forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

